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
package org.nabucco.framework.template.impl.service.datastructure.produce;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodeFacade;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSChoiseItem;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSDateItem;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSDescriptionItem;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSElement;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSElementGroup;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSFlagItem;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSKeyExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSTextItem;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.template.datastructure.TemplateDSInstanceChoiseEntry;
import org.nabucco.framework.base.facade.datatype.template.datastructure.TemplateDSInstanceDateEntry;
import org.nabucco.framework.base.facade.datatype.template.datastructure.TemplateDSInstanceDescriptionEntry;
import org.nabucco.framework.base.facade.datatype.template.datastructure.TemplateDSInstanceElement;
import org.nabucco.framework.base.facade.datatype.template.datastructure.TemplateDSInstanceFlagEntry;
import org.nabucco.framework.base.facade.datatype.template.datastructure.TemplateDSInstanceGroup;
import org.nabucco.framework.base.facade.datatype.template.datastructure.TemplateDSInstanceTextEntry;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.service.ProduceException;
import org.nabucco.framework.template.facade.datatype.TemplateDatastructure;
import org.nabucco.framework.template.facade.datatype.TemplateDatastructureReferenceItem;
import org.nabucco.framework.template.facade.message.datastructure.ProduceDSRq;
import org.nabucco.framework.template.facade.message.datastructure.ProduceDSRs;
import org.nabucco.framework.template.impl.service.datastructure.produce.util.DatastructureExtensionKeyFinder;
import org.nabucco.framework.template.impl.service.datastructure.util.DatastructureStateVisitor;

/**
 * ProduceDatastructureFromTemplateServiceHandlerImpl
 * 
 * Produce of an composite instance according to the given extension or extension name
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ProduceDatastructureFromTemplateServiceHandlerImpl extends ProduceDatastructureFromTemplateServiceHandler {

    private static final long serialVersionUID = 1L;

    private static NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            ProduceDatastructureFromTemplateServiceHandlerImpl.class);

    @Override
    protected ProduceDSRs produceDatastructureFromTemplate(ProduceDSRq msg) throws ProduceException {
        try {
            ProduceDSRs retVal = new ProduceDSRs();

            Name structureName = msg.getStructureName();
            TemplateDSExtension extension = msg.getExtension();

            if (extension == null && structureName == null) {

                throw new ProduceException("Cannot create instance. extension and extension name are both 'null'.");

            } else if (structureName != null) {
                String id = structureName.getValue();
                extension = (TemplateDSExtension) NabuccoSystem.getExtensionResolver().resolveExtension(
                        ExtensionPointType.ORG_NABUCCO_FRAMEWORK_TEMPLATE_DATASTRUCTURE, id);

            }

            if (extension == null) {
                throw new ProduceException("Cannot find datastructure extension");
            }

            TemplateDatastructure instance = this.produceInstance(extension);

            try {
                DatastructureStateVisitor visitor = new DatastructureStateVisitor(DatatypeState.INITIALIZED);
                instance.accept(visitor);
            } catch (VisitorException e) {
                throw new ProduceException("Cannot visit produced structure", e);
            }

            retVal.setInstance(instance);

            return retVal;

        } catch (ExtensionException e) {
            throw new ProduceException("Cannot locate referenced template name", e);
        }
    }

    /**
     * Produces the composite structure based on the given extension instance
     * 
     * @param ext
     *            extension to use as base
     */
    private TemplateDatastructure produceInstance(TemplateDSExtension ext) {
        TemplateDatastructure instance = new TemplateDatastructure();
        instance.setDatatypeState(DatatypeState.INITIALIZED);

        instance.setExtensionName(ext.getName());
        instance.setExtensionVersion(ext.getVersion());

        TemplateDSInstanceElement datatstructure = this.parseComposite(ext.getStructure());
        instance.setDatatstructure(datatstructure);

        List<TemplateDatastructureReferenceItem> keys = this.parseKeys(ext.getKeyList(), ext.getStructure());
        instance.getKeyReferences().addAll(keys);

        return instance;
    }

    /**
     * Resolves the list of the key reference items of the currenct datastructure
     * 
     * @param keyList
     *            the list of keys to be located
     * @param structure
     *            the datastructure extension
     * 
     * @return the list of key references
     */
    private List<TemplateDatastructureReferenceItem> parseKeys(NabuccoList<TemplateDSKeyExtension> keyList,
            TemplateDSElement structure) {

        Set<String> keySet = new HashSet<String>();

        // Prepare the list of possible keys
        if (keyList != null) {
            for (TemplateDSKeyExtension key : keyList) {
                String keyString = PropertyLoader.loadProperty(key.getId());
                keySet.add(keyString);
            }
        }
        // Locate maches
        DatastructureExtensionKeyFinder finder = new DatastructureExtensionKeyFinder(keySet);
        Map<String, String> keyMap = finder.locateKey(structure, null);

        if (keyMap == null) {
            throw new IllegalArgumentException("The key map cannot be null");
        }

        // Create list of availiable keys
        List<TemplateDatastructureReferenceItem> retVal = new ArrayList<TemplateDatastructureReferenceItem>();
        for (String key : keyMap.keySet()) {

            TemplateDatastructureReferenceItem item = new TemplateDatastructureReferenceItem();
            item.setKeyName(key);
            item.setKeyPath(keyMap.get(key));

            retVal.add(item);
        }

        return retVal;
    }

    /**
     * Parses the composite item.
     * 
     * Parsing is based on the polymorphy according to the given extension type
     * 
     * @param element
     *            extension to be parsed
     * @return the parsed element or exception if not known element type
     */
    protected TemplateDSInstanceElement parseComposite(TemplateDSElement element) {
        if (element instanceof TemplateDSElementGroup) {
            return this.parseComposite((TemplateDSElementGroup) element);
        } else if (element instanceof TemplateDSChoiseItem) {
            return this.parseComposite((TemplateDSChoiseItem) element);
        } else if (element instanceof TemplateDSDateItem) {
            return this.parseComposite((TemplateDSDateItem) element);
        } else if (element instanceof TemplateDSTextItem) {
            return this.parseComposite((TemplateDSTextItem) element);
        } else if (element instanceof TemplateDSDescriptionItem) {
            return this.parseComposite((TemplateDSDescriptionItem) element);
        } else if (element instanceof TemplateDSFlagItem) {
            return this.parseComposite((TemplateDSFlagItem) element);
        } else {
            throw new IllegalArgumentException(
                    "Cannot create instance of the ds element. The type of the element is not supported");
        }

    }

    /**
     * Parses the Group composite item.
     * 
     * Parsing is based on the polymorphy according to the given extension type
     * 
     * @param element
     *            extension to be parsed
     * @return the parsed element or exception if not known element type
     */
    protected TemplateDSInstanceElement parseComposite(TemplateDSElementGroup element) {
        TemplateDSInstanceGroup retVal = new TemplateDSInstanceGroup();
        for (TemplateDSElement groupChild : element.getChildrenList()) {
            retVal.getChildrenList().add(this.parseComposite(groupChild));
        }
        retVal.setName(PropertyLoader.loadProperty(element.getName()));
        return retVal;
    }

    /**
     * Parses the Choise item.
     * 
     * Parsing is based on the polymorphy according to the given extension type
     * 
     * @param element
     *            extension to be parsed
     * @return the parsed element or exception if not known element type
     */
    protected TemplateDSInstanceElement parseComposite(TemplateDSChoiseItem element) {
        TemplateDSInstanceChoiseEntry retVal = new TemplateDSInstanceChoiseEntry();
        retVal.setName(PropertyLoader.loadProperty(element.getName()));
        String selectionPath = PropertyLoader.loadProperty(element.getSelectionPath());
        String defaultValue = PropertyLoader.loadProperty(element.getDefaultValue());

        retVal.setCodePath(selectionPath);

        if (defaultValue != null && defaultValue.isEmpty() == false) {
            Code code = CodeFacade.getInstance().getCode(new CodePath(selectionPath), new Name(defaultValue));
            retVal.setValue(code);
        }

        return retVal;
    }

    /**
     * Parses the Date item.
     * 
     * Parsing is based on the polymorphy according to the given extension type
     * 
     * @param element
     *            extension to be parsed
     * @return the parsed element or exception if not known element type
     */
    protected TemplateDSInstanceElement parseComposite(TemplateDSDateItem element) {
        TemplateDSInstanceDateEntry retVal = new TemplateDSInstanceDateEntry();
        retVal.setName(PropertyLoader.loadProperty(element.getName()));

        String defaultValue = PropertyLoader.loadProperty(element.getDefaultValue());
        if (defaultValue != null && defaultValue.isEmpty() == false) {
            if (defaultValue.equals("today")) {
                GregorianCalendar calendar = new GregorianCalendar();
                retVal.setValue(calendar.getTime());
            } else {
                try {
                    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy");
                    Date parsedDate = df.parse(defaultValue);
                    retVal.setValue(parsedDate);
                } catch (ParseException e) {
                    logger.debug("Cannot parse the given default date default value:", defaultValue);
                }
            }
        }

        return retVal;
    }

    /**
     * Parses the Text item.
     * 
     * Parsing is based on the polymorphy according to the given extension type
     * 
     * @param element
     *            extension to be parsed
     * @return the parsed element or exception if not known element type
     */
    protected TemplateDSInstanceElement parseComposite(TemplateDSTextItem element) {
        TemplateDSInstanceTextEntry retVal = new TemplateDSInstanceTextEntry();
        retVal.setName(PropertyLoader.loadProperty(element.getName()));

        String defaultValue = PropertyLoader.loadProperty(element.getDefaultValue());
        if (defaultValue != null && defaultValue.isEmpty() == false) {
            retVal.setValue(defaultValue);
        }

        return retVal;
    }

    /**
     * Parses the Description item.
     * 
     * Parsing is based on the polymorphy according to the given extension type
     * 
     * @param element
     *            extension to be parsed
     * @return the parsed element or exception if not known element type
     */
    protected TemplateDSInstanceElement parseComposite(TemplateDSDescriptionItem element) {
        TemplateDSInstanceDescriptionEntry retVal = new TemplateDSInstanceDescriptionEntry();
        retVal.setName(PropertyLoader.loadProperty(element.getName()));

        String defaultValue = PropertyLoader.loadProperty(element.getDefaultValue());
        if (defaultValue != null && defaultValue.isEmpty() == false) {
            retVal.setValue(defaultValue);
        }

        return retVal;
    }

    /**
     * Parses the Flag item.
     * 
     * Parsing is based on the polymorphy according to the given extension type
     * 
     * @param element
     *            extension to be parsed
     * @return the parsed element or exception if not known element type
     */
    protected TemplateDSInstanceElement parseComposite(TemplateDSFlagItem element) {
        TemplateDSInstanceFlagEntry retVal = new TemplateDSInstanceFlagEntry();
        retVal.setName(PropertyLoader.loadProperty(element.getName()));

        String defaultValue = PropertyLoader.loadProperty(element.getDefaultValue());
        if (defaultValue != null && defaultValue.isEmpty() == false) {
            boolean value = Boolean.parseBoolean(defaultValue);
            retVal.setValue(value);
        }

        return retVal;
    }
}
