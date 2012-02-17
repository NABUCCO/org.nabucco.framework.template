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
package org.nabucco.framework.template.ui.rcp.communication.textmodule.search;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.template.facade.message.textmodule.TextModuleListMsg;
import org.nabucco.framework.template.facade.message.textmodule.search.TextModuleSearchRq;
import org.nabucco.framework.template.facade.service.textmodule.search.SearchTextModule;

/**
 * SearchTextModuleDelegate<p/>TODO<p/>
 *
 * @version TODO
 * @author hlibrenz, PRODYNA AG, 2011-03-25
 */
public class SearchTextModuleDelegate extends ServiceDelegateSupport {

    private SearchTextModule service;

    /**
     * Constructs a new SearchTextModuleDelegate instance.
     *
     * @param service the SearchTextModule.
     */
    public SearchTextModuleDelegate(SearchTextModule service) {
        super();
        this.service = service;
    }

    /**
     * SearchTextModule.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the TextModuleSearchRq.
     * @return the TextModuleListMsg.
     * @throws ClientException
     */
    public TextModuleListMsg searchTextModule(TextModuleSearchRq message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<TextModuleSearchRq> request = new ServiceRequest<TextModuleSearchRq>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<TextModuleListMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.searchTextModule(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(SearchTextModule.class, "searchTextModule", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: SearchTextModule.searchTextModule");
    }
}
