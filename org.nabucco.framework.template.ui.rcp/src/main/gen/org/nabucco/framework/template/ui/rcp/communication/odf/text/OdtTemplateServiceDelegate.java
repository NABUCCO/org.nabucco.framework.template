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
package org.nabucco.framework.template.ui.rcp.communication.odf.text;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.template.facade.message.odf.text.OdtTemplateRq;
import org.nabucco.framework.template.facade.message.odf.text.OdtTemplateRs;
import org.nabucco.framework.template.facade.service.odf.text.OdtTemplateService;

/**
 * OdtTemplateServiceDelegate<p/>Handles Open Document Textfiles.<p/>
 *
 * @version 1.1
 * @author Nicolas Moser, PRODYNA AG, 2011-06-15
 */
public class OdtTemplateServiceDelegate extends ServiceDelegateSupport {

    private OdtTemplateService service;

    /**
     * Constructs a new OdtTemplateServiceDelegate instance.
     *
     * @param service the OdtTemplateService.
     */
    public OdtTemplateServiceDelegate(OdtTemplateService service) {
        super();
        this.service = service;
    }

    /**
     * CreateTextFile.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the OdtTemplateRq.
     * @return the OdtTemplateRs.
     * @throws ClientException
     */
    public OdtTemplateRs createTextFile(OdtTemplateRq message, ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<OdtTemplateRq> request = new ServiceRequest<OdtTemplateRq>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<OdtTemplateRs> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.createTextFile(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(OdtTemplateService.class, "createTextFile", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: OdtTemplateService.createTextFile");
    }
}
