/*
 * Copyright 2012 PRODYNA AG
 * 
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.nabucco.framework.template.ui.web.communication.textmodule.maintain;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.framework.template.facade.exception.textmodule.TextModuleMaintainException;
import org.nabucco.framework.template.facade.message.textmodule.TextModuleMsg;
import org.nabucco.framework.template.facade.service.textmodule.maintain.MaintainTextModule;

/**
 * MaintainTextModuleDelegate<p/>Maintain service for text modules<p/>
 *
 * @version 1.0
 * @author Holger Librenz, PRODYNA AG, 2011-03-24
 */
public class MaintainTextModuleDelegate extends ServiceDelegateSupport {

    private MaintainTextModule service;

    /**
     * Constructs a new MaintainTextModuleDelegate instance.
     *
     * @param service the MaintainTextModule.
     */
    public MaintainTextModuleDelegate(MaintainTextModule service) {
        super();
        this.service = service;
    }

    /**
     * MaintainTextModule.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the TextModuleMsg.
     * @return the TextModuleMsg.
     * @throws TextModuleMaintainException
     */
    public TextModuleMsg maintainTextModule(TextModuleMsg message, NabuccoSession session,
            ServiceSubContext... subContexts) throws TextModuleMaintainException {
        ServiceRequest<TextModuleMsg> request = new ServiceRequest<TextModuleMsg>(super.createServiceContext(session,
                subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TextModuleMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.maintainTextModule(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(MaintainTextModule.class, "maintainTextModule", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new TextModuleMaintainException("Cannot execute service operation: MaintainTextModule.maintainTextModule");
    }
}
