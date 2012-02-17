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
package org.nabucco.framework.template.ui.rcp.communication.pdf.merge;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.template.facade.message.pdf.MergePdfRq;
import org.nabucco.framework.template.facade.message.pdf.MergePdfRs;
import org.nabucco.framework.template.facade.service.pdf.merge.MergePdf;

/**
 * MergePdfDelegate<p/>Merges PDF files.<p/>
 *
 * @version 1.0
 * @author Raffael Bieniek, PRODYNA AG, 2011-07-04
 */
public class MergePdfDelegate extends ServiceDelegateSupport {

    private MergePdf service;

    /**
     * Constructs a new MergePdfDelegate instance.
     *
     * @param service the MergePdf.
     */
    public MergePdfDelegate(MergePdf service) {
        super();
        this.service = service;
    }

    /**
     * MergePdfFiles.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the MergePdfRq.
     * @return the MergePdfRs.
     * @throws ClientException
     */
    public MergePdfRs mergePdfFiles(MergePdfRq message, ServiceSubContext... subContexts) throws ClientException {
        ServiceRequest<MergePdfRq> request = new ServiceRequest<MergePdfRq>(super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<MergePdfRs> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.mergePdfFiles(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(MergePdf.class, "mergePdfFiles", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: MergePdf.mergePdfFiles");
    }
}
