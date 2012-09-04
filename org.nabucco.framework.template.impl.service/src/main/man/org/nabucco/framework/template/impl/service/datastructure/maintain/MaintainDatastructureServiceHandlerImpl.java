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
package org.nabucco.framework.template.impl.service.datastructure.maintain;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationException;
import org.nabucco.framework.base.facade.datatype.template.datastructure.TemplateDSInstanceElement;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.template.facade.datatype.TemplateDatastructure;
import org.nabucco.framework.template.facade.datatype.TemplateDatastructureReferenceItem;
import org.nabucco.framework.template.facade.message.datastructure.DatastructureMsg;
import org.nabucco.framework.template.impl.service.datastructure.util.DatastructureSerializationSupport;
import org.nabucco.framework.template.impl.service.datastructure.util.DatastructureStateVisitor;

/**
 * MaintainDatastructureServiceHandlerImpl
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class MaintainDatastructureServiceHandlerImpl extends MaintainDatastructureServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected DatastructureMsg maintainDatastructure(DatastructureMsg msg) throws MaintainException {

        TemplateDatastructure datastructure = msg.getDatastructure();

        TemplateDatastructure maintainedDS;
        try {
            maintainedDS = this.maintainDS(datastructure);
        } catch (PersistenceException e) {
            throw new MaintainException("Canot maintait datatstructure", e);
        } catch (VisitorException e) {
            throw new MaintainException("Cannot update the DB state of the DS", e);
        } catch (SerializationException e) {
            throw new MaintainException("Canot serialize datatstructure", e);
        }

        DatastructureMsg retVal = new DatastructureMsg();
        retVal.setDatastructure(maintainedDS);
        return retVal;
    }

    /**
     * Maintains the given datatstructure
     * 
     * @param datastructure
     *            datastructure to be maintained
     * @return maintained DS
     * @throws org.nabucco.framework.base.facade.exception.persistence.PersistenceException
     * @throws MaintainException
     *             if problems by maintaining
     */
    private TemplateDatastructure maintainDS(TemplateDatastructure datastructure) throws VisitorException,
            SerializationException, PersistenceException {

        NabuccoList<TemplateDatastructureReferenceItem> keyReferences = datastructure.getKeyReferences();
        if (datastructure.getDatatypeState() == DatatypeState.DELETED) {
            datastructure = super.getPersistenceManager().persist(datastructure);

            for (TemplateDatastructureReferenceItem item : keyReferences) {
                item.setDatatypeState(DatatypeState.DELETED);
                super.getPersistenceManager().persist(item);
            }
            return datastructure;
        }

        TemplateDSInstanceElement transientDS = datastructure.getDatatstructure();

        // Make the datastructure PERSISTANT
        DatastructureStateVisitor visitor = new DatastructureStateVisitor(DatatypeState.PERSISTENT);
        transientDS.accept(visitor);

        String oldXmlValue = null;
        if (datastructure.getXml() != null) {
            oldXmlValue = datastructure.getXml().getValue();
        }

        // Serialize Datastructure
        DatastructureSerializationSupport.serialize(datastructure);

        if (datastructure.getDatatypeState() != DatatypeState.INITIALIZED) {
            String newXmlValue = datastructure.getXml().getValue();
            if (oldXmlValue == null && newXmlValue != null) {
                datastructure.setDatatypeState(DatatypeState.MODIFIED);
            } else if (oldXmlValue != null && (oldXmlValue.equals(newXmlValue) == false)) {
                datastructure.setDatatypeState(DatatypeState.MODIFIED);
            }
        }

        // Maintain it
        for (TemplateDatastructureReferenceItem item : keyReferences) {
            if (item.getDatatypeState() != DatatypeState.PERSISTENT) {
                TemplateDatastructureReferenceItem persistedItem = super.getPersistenceManager().persist(item);
                keyReferences.set(keyReferences.indexOf(item), persistedItem);
            }
        }

        TemplateDatastructure persistedDS = super.getPersistenceManager().persist(datastructure);
        persistedDS.getKeyReferences().size();
        persistedDS.setDatatstructure(transientDS);
        return persistedDS;

    }
}
