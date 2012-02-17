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
package org.nabucco.framework.template.impl.service.textmodule.produce;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.template.facade.exception.textmodule.TextModuleProduceException;
import org.nabucco.framework.template.facade.message.textmodule.TextModuleListMsg;
import org.nabucco.framework.template.facade.message.textmodule.produce.TextModuleProduceRq;

/**
 * ProduceTextModuleServiceHandler<p/>Producer service for text modules<p/>
 *
 * @version 1.0
 * @author hlibrenz, PRODYNA AG, 2011-03-24
 */
public abstract class ProduceTextModuleServiceHandler extends PersistenceServiceHandlerSupport implements
        ServiceHandler, PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.template.impl.service.textmodule.produce.ProduceTextModuleServiceHandler";

    /** Constructs a new ProduceTextModuleServiceHandler instance. */
    public ProduceTextModuleServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<TextModuleProduceRq>.
     * @return the ServiceResponse<TextModuleListMsg>.
     * @throws TextModuleProduceException
     */
    protected ServiceResponse<TextModuleListMsg> invoke(ServiceRequest<TextModuleProduceRq> rq)
            throws TextModuleProduceException {
        ServiceResponse<TextModuleListMsg> rs;
        TextModuleListMsg msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.produceTextModule(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<TextModuleListMsg>(rq.getContext());
            rs.setResponseMessage(msg);
            return rs;
        } catch (TextModuleProduceException e) {
            super.getLogger().error(e);
            throw e;
        } catch (NabuccoException e) {
            super.getLogger().error(e);
            TextModuleProduceException wrappedException = new TextModuleProduceException(e);
            throw wrappedException;
        } catch (Exception e) {
            super.getLogger().error(e);
            throw new TextModuleProduceException("Error during service invocation.", e);
        }
    }

    /**
     * Missing description at method produceTextModule.
     *
     * @param msg the TextModuleProduceRq.
     * @return the TextModuleListMsg.
     * @throws TextModuleProduceException
     */
    protected abstract TextModuleListMsg produceTextModule(TextModuleProduceRq msg) throws TextModuleProduceException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
