/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.template.impl.service.datastructure.transfer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NType;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.template.datastructure.TemplateDatastructurePropertyAccessor;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.template.facade.datatype.TemplateDatastructure;
import org.nabucco.framework.template.facade.datatype.TemplateDatastructureReferenceItem;
import org.nabucco.framework.template.facade.datatype.datastructure.KeyValuePair;
import org.nabucco.framework.template.facade.exception.TemplateException;
import org.nabucco.framework.template.facade.message.datastructure.DatastructureMergeRq;
import org.nabucco.framework.template.facade.message.datastructure.DatastructureMergeRs;

/**
 * MergeDatastructureServiceHandlerImpl
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class MergeDatastructureServiceHandlerImpl extends MergeDatastructureServiceHandler {

    private static final long serialVersionUID = 1L;

    private NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            MergeDatastructureServiceHandlerImpl.class);

    @Override
    protected DatastructureMergeRs mergeDatastructure(DatastructureMergeRq msg) throws TemplateException {
        TemplateDatastructure target = msg.getTarget();

        NabuccoList<TemplateDatastructure> sourceDatastructureList = msg.getSourceDatastructureList();

        Map<String, String> additionalKeys = new HashMap<String, String>();

        boolean transfered = this.transferKeyData(target, sourceDatastructureList, additionalKeys);

        if (transfered && target.getDatatypeState() == DatatypeState.PERSISTENT) {
            target.setDatatypeState(DatatypeState.MODIFIED);
        }
        DatastructureMergeRs retVal = new DatastructureMergeRs();
        retVal.setDatastructure(target);

        for (String key : additionalKeys.keySet()) {
            String value = additionalKeys.get(key);

            KeyValuePair pair = new KeyValuePair();
            pair.setDsKey(key);
            pair.setDsValue(value);

            retVal.getAdditionalKeys().add(pair);
        }
        return retVal;
    }

    /**
     * Transfers the data from sources to the target
     * 
     * @param target
     *            target datatype
     * @param sourceList
     *            source datatype list * @throws MaintainException if problems by resolving
     * @param additionalKeys
     *            the keys that are availiable in the sources but not in the target
     * 
     * @return true if changes made
     */
    private boolean transferKeyData(TemplateDatastructure target, List<TemplateDatastructure> sourceList,
            Map<String, String> additionalKeys) throws TemplateException {

        boolean transfered = false;
        Map<String, String> targetKeys = this.resolveKeys(target);

        for (TemplateDatastructure source : sourceList) {
            Map<String, String> sourceKeys = this.resolveKeys(source);

            // Search if the ds has matching keys
            for (String key : sourceKeys.keySet()) {

                if (targetKeys.keySet().contains(key)) {
                    // Transfer the data if the key was found
                    boolean result = this.transferItem(target, targetKeys.get(key), source, sourceKeys.get(key));

                    if (result && !transfered) {
                        transfered = true;
                    }
                } else {
                    NType value = this.resolveKey(source, sourceKeys.get(key));
                    if (value != null) {
                        String stringValue = null;

                        if (value instanceof Basetype) {
                            stringValue = ((Basetype) value).getValueAsString();
                        } else if (value instanceof Code) {
                            stringValue = ((Code) value).getName().getValue();
                        } else {
                            stringValue = value.toString();
                        }

                        additionalKeys.put(key, stringValue);
                    }
                }

            }

        }

        return transfered;
    }

    /**
     * Transfers the data from source path of source to target path of the target datastructure
     * 
     * @param target
     *            target DS
     * @param targetPath
     *            target DS Path
     * @param source
     *            source DS
     * @param sourcePath
     *            source DS Path
     * 
     * @return true if transfer succeded
     */
    private boolean transferItem(TemplateDatastructure target, String targetPath, TemplateDatastructure source,
            String sourcePath) {

        TemplateDatastructurePropertyAccessor accessor = new TemplateDatastructurePropertyAccessor();
        NabuccoProperty sourceProperty = accessor.resolveProperty(sourcePath, source);
        NabuccoProperty targetProperty = accessor.resolveProperty(targetPath, target);

        if (targetProperty == null || sourceProperty == null) {
            return false;
        }

        Object sourceInstance = sourceProperty.getInstance();
        if (sourceInstance == null || sourceInstance instanceof List<?>) {
            return false;
        }

        if (targetProperty.getInstance() == null) {
            try {
                Object newInstance = targetProperty.getType().newInstance();
                NabuccoProperty createProperty = targetProperty.createProperty(newInstance);
                targetProperty.getParent().setProperty(createProperty);
                targetProperty = createProperty;
            } catch (InstantiationException e) {
                logger.debug("Cannot instanciate property of target ds", e);
                return false;
            } catch (IllegalAccessException e) {
                logger.debug("Cannot instanciate property of target ds", e);
                return false;
            }
        }

        Object targetInstance = targetProperty.getInstance();
        if (targetInstance.getClass().isInstance(sourceInstance) == false) {
            return false;
        }

        NabuccoProperty transferedProperty = targetProperty.createProperty(sourceInstance);
        targetProperty.getParent().setProperty(transferedProperty);

        return true;
    }

    /**
     * Resolves the single value from the ds
     * 
     * @param ds
     *            ds to be resolved from
     * @param path
     *            the path to resolve
     * @return NTYpe or null
     */
    private NType resolveKey(TemplateDatastructure ds, String path) {
        TemplateDatastructurePropertyAccessor accessor = new TemplateDatastructurePropertyAccessor();
        NabuccoProperty property = accessor.resolveProperty(path, ds);

        Object instance = property.getInstance();
        if (instance instanceof NType) {
            return (NType) instance;
        }

        logger.debug("Cannot resolve value pointed by the property path ", path, " in the datastructure ",
                ds.getExtensionName());
        return null;
    }

    /**
     * Resolves the keys of the given datatype
     * 
     * @param ds
     *            daatype to be resolved
     * @return the list of key references
     * @throws MaintainException
     *             if problems by resolving
     */
    private Map<String, String> resolveKeys(TemplateDatastructure ds) throws TemplateException {
        try {
            Map<String, String> keyMap = new HashMap<String, String>();

            List<TemplateDatastructureReferenceItem> list = null;

            if (ds == null || ds.getKeyReferences() == null) {
                logger.debug("DS", ds.getExtensionName(), " doesnt has any keys that can be used");
                return keyMap;
            }

            if (ds.getKeyReferences().isTraversable()) {
                list = ds.getKeyReferences();
            } else {
                TemplateDatastructure found;
                found = super.getPersistenceManager().find(ds);
                found.getKeyReferences().size();

                list = found.getKeyReferences();
            }

            for (TemplateDatastructureReferenceItem item : list) {
                keyMap.put(item.getKeyName().getValue(), item.getKeyPath().getValue());
            }

            return keyMap;
        } catch (PersistenceException e) {
            throw new TemplateException("Cannot resolve datastructure keys", e);
        }

    }
}
