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
package org.nabucco.framework.template.ui.web.communication.odf.produce;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.datatype.session.NabuccoSession;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.ui.web.communication.ServiceDelegateSupport;
import org.nabucco.framework.template.facade.exception.TemplateException;
import org.nabucco.framework.template.facade.message.odf.ProduceOdfRq;
import org.nabucco.framework.template.facade.message.odf.ProduceOdfRs;
import org.nabucco.framework.template.facade.service.odf.produce.ProduceOdf;

/**
 * ProduceOdfDelegate<p/>Produces ODF files.<p/>
 *
 * @version 1.1
 * @author Holger Librenz, PRODYNA AG, 2011-06-15
 */
public class ProduceOdfDelegate extends ServiceDelegateSupport {

    private ProduceOdf service;

    /**
     * Constructs a new ProduceOdfDelegate instance.
     *
     * @param service the ProduceOdf.
     */
    public ProduceOdfDelegate(ProduceOdf service) {
        super();
        this.service = service;
    }

    /**
     * CreateOpenDocumentFile.
     *
     * @param subContexts the ServiceSubContext....
     * @param session the NabuccoSession.
     * @param message the ProduceOdfRq.
     * @return the ProduceOdfRs.
     * @throws TemplateException
     */
    public ProduceOdfRs createOpenDocumentFile(ProduceOdfRq message, NabuccoSession session,
            ServiceSubContext... subContexts) throws TemplateException {
        ServiceRequest<ProduceOdfRq> request = new ServiceRequest<ProduceOdfRq>(super.createServiceContext(session,
                subContexts));
        request.setRequestMessage(message);
        ServiceResponse<ProduceOdfRs> response = null;
        Exception exception = null;
        if ((this.service != null)) {
            super.handleRequest(request, session);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.createOpenDocumentFile(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceOdf.class, "createOpenDocumentFile", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response, session);
                return response.getResponseMessage();
            }
        }
        throw new TemplateException("Cannot execute service operation: ProduceOdf.createOpenDocumentFile");
    }
}
