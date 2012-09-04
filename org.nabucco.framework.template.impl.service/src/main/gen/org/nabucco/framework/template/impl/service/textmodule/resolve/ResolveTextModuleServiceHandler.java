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
package org.nabucco.framework.template.impl.service.textmodule.resolve;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.template.facade.exception.textmodule.TextModuleResolveException;
import org.nabucco.framework.template.facade.message.textmodule.TextModuleMsg;

/**
 * ResolveTextModuleServiceHandler<p/>Resolve service for text modules<p/>
 *
 * @version 1.0
 * @author hlibrenz, PRODYNA AG, 2011-03-25
 */
public abstract class ResolveTextModuleServiceHandler extends PersistenceServiceHandlerSupport implements
        ServiceHandler, PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.template.impl.service.textmodule.resolve.ResolveTextModuleServiceHandler";

    /** Constructs a new ResolveTextModuleServiceHandler instance. */
    public ResolveTextModuleServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<TextModuleMsg>.
     * @return the ServiceResponse<TextModuleMsg>.
     * @throws TextModuleResolveException
     */
    protected ServiceResponse<TextModuleMsg> invoke(ServiceRequest<TextModuleMsg> rq) throws TextModuleResolveException {
        ServiceResponse<TextModuleMsg> rs;
        TextModuleMsg msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.resolveTextModule(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<TextModuleMsg>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (TextModuleResolveException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            TextModuleResolveException wrappedException = new TextModuleResolveException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new TextModuleResolveException("Error during service invocation.", e);
        }
    }

    /**
     * Missing description at method resolveTextModule.
     *
     * @param msg the TextModuleMsg.
     * @return the TextModuleMsg.
     * @throws TextModuleResolveException
     */
    protected abstract TextModuleMsg resolveTextModule(TextModuleMsg msg) throws TextModuleResolveException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
