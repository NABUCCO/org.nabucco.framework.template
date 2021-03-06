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
package org.nabucco.framework.template.ui.rcp.communication.textmodule.resolve;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.template.facade.message.textmodule.TextModuleMsg;
import org.nabucco.framework.template.facade.service.textmodule.resolve.ResolveTextModule;

/**
 * ResolveTextModuleDelegate<p/>Resolve service for text modules<p/>
 *
 * @version 1.0
 * @author hlibrenz, PRODYNA AG, 2011-03-25
 */
public class ResolveTextModuleDelegate extends ServiceDelegateSupport {

    private ResolveTextModule service;

    /**
     * Constructs a new ResolveTextModuleDelegate instance.
     *
     * @param service the ResolveTextModule.
     */
    public ResolveTextModuleDelegate(ResolveTextModule service) {
        super();
        this.service = service;
    }

    /**
     * ResolveTextModule.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the TextModuleMsg.
     * @return the TextModuleMsg.
     * @throws ClientException
     */
    public TextModuleMsg resolveTextModule(TextModuleMsg message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<TextModuleMsg> request = new ServiceRequest<TextModuleMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TextModuleMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.resolveTextModule(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ResolveTextModule.class, "resolveTextModule", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: ResolveTextModule.resolveTextModule");
    }
}
