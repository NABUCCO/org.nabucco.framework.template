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
package org.nabucco.framework.template.impl.service.odf.text;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.template.facade.exception.TemplateException;
import org.nabucco.framework.template.facade.message.odf.text.OdtTemplateRq;
import org.nabucco.framework.template.facade.message.odf.text.OdtTemplateRs;

/**
 * CreateTextFileServiceHandler<p/>Handles Open Document Textfiles.<p/>
 *
 * @version 1.1
 * @author Nicolas Moser, PRODYNA AG, 2011-06-15
 */
public abstract class CreateTextFileServiceHandler extends PersistenceServiceHandlerSupport implements ServiceHandler,
        PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.template.impl.service.odf.text.CreateTextFileServiceHandler";

    /** Constructs a new CreateTextFileServiceHandler instance. */
    public CreateTextFileServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<OdtTemplateRq>.
     * @return the ServiceResponse<OdtTemplateRs>.
     * @throws TemplateException
     */
    protected ServiceResponse<OdtTemplateRs> invoke(ServiceRequest<OdtTemplateRq> rq) throws TemplateException {
        ServiceResponse<OdtTemplateRs> rs;
        OdtTemplateRs msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.createTextFile(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<OdtTemplateRs>(rq.getContext());
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
     * Creates a new ODT file based on a given ODT template
     *
     * @param msg the OdtTemplateRq.
     * @return the OdtTemplateRs.
     * @throws TemplateException
     */
    protected abstract OdtTemplateRs createTextFile(OdtTemplateRq msg) throws TemplateException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
