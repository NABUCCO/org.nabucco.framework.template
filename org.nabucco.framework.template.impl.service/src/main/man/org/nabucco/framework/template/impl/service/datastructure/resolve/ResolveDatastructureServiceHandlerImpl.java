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
package org.nabucco.framework.template.impl.service.datastructure.resolve;

import org.nabucco.framework.base.facade.datatype.serialization.SerializationException;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.template.facade.datatype.TemplateDatastructure;
import org.nabucco.framework.template.facade.message.datastructure.DatastructureMsg;
import org.nabucco.framework.template.impl.service.datastructure.util.DatastructureSerializationSupport;

/**
 * ResolveDatastructureServiceHandlerImpl
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class ResolveDatastructureServiceHandlerImpl extends ResolveDatastructureServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected DatastructureMsg resolveDatastructure(DatastructureMsg msg) throws ResolveException {
        try {
            TemplateDatastructure datastructure = msg.getDatastructure();

            TemplateDatastructure resolvedDS = super.getPersistenceManager().find(datastructure);
            resolvedDS.getKeyReferences().size();

            DatastructureSerializationSupport.deserialize(resolvedDS);

            DatastructureMsg retVal = new DatastructureMsg();
            retVal.setDatastructure(resolvedDS);
            return retVal;

        } catch (PersistenceException e) {
            throw new ResolveException("Cannot resolve datastructure", e);
        } catch (SerializationException e) {
            throw new ResolveException("Cannot deserialize datastructure", e);
        }
    }

}
