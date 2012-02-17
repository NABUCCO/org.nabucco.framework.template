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
package org.nabucco.framework.template.impl.service.textmodule.maintain;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.template.facade.exception.textmodule.TextModuleMaintainException;
import org.nabucco.framework.template.facade.message.textmodule.TextModuleMsg;

/**
 * MaintainTextModuleServiceHandler<p/>Maintain service for text modules<p/>
 *
 * @version 1.0
 * @author Holger Librenz, PRODYNA AG, 2011-03-24
 */
public abstract class MaintainTextModuleServiceHandler extends PersistenceServiceHandlerSupport implements
        ServiceHandler, PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.template.impl.service.textmodule.maintain.MaintainTextModuleServiceHandler";

    /** Constructs a new MaintainTextModuleServiceHandler instance. */
    public MaintainTextModuleServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<TextModuleMsg>.
     * @return the ServiceResponse<TextModuleMsg>.
     * @throws TextModuleMaintainException
     */
    protected ServiceResponse<TextModuleMsg> invoke(ServiceRequest<TextModuleMsg> rq)
            throws TextModuleMaintainException {
        ServiceResponse<TextModuleMsg> rs;
        TextModuleMsg msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.maintainTextModule(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<TextModuleMsg>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (TextModuleMaintainException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            TextModuleMaintainException wrappedException = new TextModuleMaintainException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new TextModuleMaintainException("Error during service invocation.", e);
        }
    }

    /**
     * Missing description at method maintainTextModule.
     *
     * @param msg the TextModuleMsg.
     * @return the TextModuleMsg.
     * @throws TextModuleMaintainException
     */
    protected abstract TextModuleMsg maintainTextModule(TextModuleMsg msg) throws TextModuleMaintainException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
