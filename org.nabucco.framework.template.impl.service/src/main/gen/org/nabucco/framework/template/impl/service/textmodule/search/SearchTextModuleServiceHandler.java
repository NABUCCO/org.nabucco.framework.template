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
package org.nabucco.framework.template.impl.service.textmodule.search;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.template.facade.exception.textmodule.TextModuleSearchException;
import org.nabucco.framework.template.facade.message.textmodule.TextModuleListMsg;
import org.nabucco.framework.template.facade.message.textmodule.search.TextModuleSearchRq;

/**
 * SearchTextModuleServiceHandler<p/>TODO<p/>
 *
 * @version TODO
 * @author hlibrenz, PRODYNA AG, 2011-03-25
 */
public abstract class SearchTextModuleServiceHandler extends PersistenceServiceHandlerSupport implements
        ServiceHandler, PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.template.impl.service.textmodule.search.SearchTextModuleServiceHandler";

    /** Constructs a new SearchTextModuleServiceHandler instance. */
    public SearchTextModuleServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<TextModuleSearchRq>.
     * @return the ServiceResponse<TextModuleListMsg>.
     * @throws TextModuleSearchException
     */
    protected ServiceResponse<TextModuleListMsg> invoke(ServiceRequest<TextModuleSearchRq> rq)
            throws TextModuleSearchException {
        ServiceResponse<TextModuleListMsg> rs;
        TextModuleListMsg msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.searchTextModule(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<TextModuleListMsg>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (TextModuleSearchException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            TextModuleSearchException wrappedException = new TextModuleSearchException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new TextModuleSearchException("Error during service invocation.", e);
        }
    }

    /**
     * Missing description at method searchTextModule.
     *
     * @param msg the TextModuleSearchRq.
     * @return the TextModuleListMsg.
     * @throws TextModuleSearchException
     */
    protected abstract TextModuleListMsg searchTextModule(TextModuleSearchRq msg) throws TextModuleSearchException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
