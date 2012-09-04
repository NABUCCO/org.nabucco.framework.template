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
package org.nabucco.framework.template.ui.web.communication.textmodule.produce;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.framework.template.facade.exception.textmodule.TextModuleProduceException;
import org.nabucco.framework.template.facade.message.textmodule.TextModuleListMsg;
import org.nabucco.framework.template.facade.message.textmodule.produce.TextModuleProduceRq;
import org.nabucco.framework.template.facade.service.textmodule.produce.ProduceTextModule;

/**
 * ProduceTextModuleDelegate<p/>Producer service for text modules<p/>
 *
 * @version 1.0
 * @author hlibrenz, PRODYNA AG, 2011-03-24
 */
public class ProduceTextModuleDelegate extends ServiceDelegateSupport {

    private ProduceTextModule service;

    /**
     * Constructs a new ProduceTextModuleDelegate instance.
     *
     * @param service the ProduceTextModule.
     */
    public ProduceTextModuleDelegate(ProduceTextModule service) {
        super();
        this.service = service;
    }

    /**
     * ProduceTextModule.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the TextModuleProduceRq.
     * @return the TextModuleListMsg.
     * @throws TextModuleProduceException
     */
    public TextModuleListMsg produceTextModule(TextModuleProduceRq message, NabuccoSession session,
            ServiceSubContext... subContexts) throws TextModuleProduceException {
        ServiceRequest<TextModuleProduceRq> request = new ServiceRequest<TextModuleProduceRq>(
                super.createServiceContext(session, subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TextModuleListMsg> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceTextModule(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceTextModule.class, "produceTextModule", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new TextModuleProduceException("Cannot execute service operation: ProduceTextModule.produceTextModule");
    }
}
