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
package org.nabucco.framework.template.impl.service.pdf.merge;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.template.facade.exception.TemplateException;
import org.nabucco.framework.template.facade.message.pdf.MergePdfRq;
import org.nabucco.framework.template.facade.message.pdf.MergePdfRs;

/**
 * MergePdfFilesServiceHandler<p/>Merges PDF files.<p/>
 *
 * @version 1.0
 * @author Raffael Bieniek, PRODYNA AG, 2011-07-04
 */
public abstract class MergePdfFilesServiceHandler extends PersistenceServiceHandlerSupport implements ServiceHandler,
        PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.template.impl.service.pdf.merge.MergePdfFilesServiceHandler";

    /** Constructs a new MergePdfFilesServiceHandler instance. */
    public MergePdfFilesServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<MergePdfRq>.
     * @return the ServiceResponse<MergePdfRs>.
     * @throws TemplateException
     */
    protected ServiceResponse<MergePdfRs> invoke(ServiceRequest<MergePdfRq> rq) throws TemplateException {
        ServiceResponse<MergePdfRs> rs;
        MergePdfRs msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.mergePdfFiles(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<MergePdfRs>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (TemplateException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            TemplateException wrappedException = new TemplateException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new TemplateException("Error during service invocation.", e);
        }
    }

    /**
     * Produces a new PDF file based on several PDF Files.
     *
     * @param msg the MergePdfRq.
     * @return the MergePdfRs.
     * @throws TemplateException
     */
    protected abstract MergePdfRs mergePdfFiles(MergePdfRq msg) throws TemplateException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
