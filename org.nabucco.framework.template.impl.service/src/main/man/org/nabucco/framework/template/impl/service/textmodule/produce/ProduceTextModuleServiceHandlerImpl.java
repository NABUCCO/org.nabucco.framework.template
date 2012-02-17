/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.template.impl.service.textmodule.produce;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.template.facade.datatype.textmodule.TextModule;
import org.nabucco.framework.template.facade.exception.textmodule.TextModuleProduceException;
import org.nabucco.framework.template.facade.message.textmodule.TextModuleListMsg;
import org.nabucco.framework.template.facade.message.textmodule.produce.TextModuleProduceRq;

/**
 * ProduceTextModuleServiceHandlerImpl
 * 
 * @author Holger Librenz, PRODYNA AG
 */
public class ProduceTextModuleServiceHandlerImpl extends ProduceTextModuleServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected TextModuleListMsg produceTextModule(TextModuleProduceRq msg) throws TextModuleProduceException {

        TextModuleListMsg response = new TextModuleListMsg();

        for (int i = 0; i < msg.getAmount().getValue(); i++) {
            TextModule datatype = new TextModule();
            datatype.setDatatypeState(DatatypeState.INITIALIZED);
            response.getTextModuleList().add(datatype);

        }

        return response;
    }

}
