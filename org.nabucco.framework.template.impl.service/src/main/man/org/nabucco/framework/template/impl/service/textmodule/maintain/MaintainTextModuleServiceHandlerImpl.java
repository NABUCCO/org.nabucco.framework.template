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
package org.nabucco.framework.template.impl.service.textmodule.maintain;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.template.facade.datatype.textmodule.TextModule;
import org.nabucco.framework.template.facade.exception.textmodule.TextModuleMaintainException;
import org.nabucco.framework.template.facade.message.textmodule.TextModuleMsg;
import org.nabucco.framework.template.impl.service.textmodule.maintain.support.TextModuleMaintainSupport;

/**
 * MaintainTextModuleServiceHandlerImpl
 * 
 * @author Holger Librenz, PRODYNA AG
 */
public class MaintainTextModuleServiceHandlerImpl extends MaintainTextModuleServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected TextModuleMsg maintainTextModule(TextModuleMsg msg) throws TextModuleMaintainException {

        TextModule textModule = msg.getTextModule();

        if (textModule != null) {
            textModule.setLastModified(NabuccoSystem.getCurrentTimeMillis());
        }
        TextModuleMaintainSupport support = new TextModuleMaintainSupport(getPersistenceManager());
        textModule = support.maintain(textModule);

        TextModuleMsg response = new TextModuleMsg();

        response.setTextModule(textModule);
        return response;
    }

}
