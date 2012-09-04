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
package org.nabucco.framework.template.impl.service.datastructure.transfer;

import org.nabucco.framework.base.facade.exception.NabuccoException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.impl.service.ServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandler;
import org.nabucco.framework.base.impl.service.maintain.PersistenceServiceHandlerSupport;
import org.nabucco.framework.template.facade.exception.TemplateException;
import org.nabucco.framework.template.facade.message.datastructure.DatastructureMergeRq;
import org.nabucco.framework.template.facade.message.datastructure.DatastructureMergeRs;

/**
 * MergeDatastructureServiceHandler<p/>Transfer the data to and from datastructures<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-04
 */
public abstract class MergeDatastructureServiceHandler extends PersistenceServiceHandlerSupport implements
        ServiceHandler, PersistenceServiceHandler {

    private static final long serialVersionUID = 1L;

    private static final String ID = "org.nabucco.framework.template.impl.service.datastructure.transfer.MergeDatastructureServiceHandler";

    /** Constructs a new MergeDatastructureServiceHandler instance. */
    public MergeDatastructureServiceHandler() {
        super();
    }

    /**
     * Invokes the service handler method.
     *
     * @param rq the ServiceRequest<DatastructureMergeRq>.
     * @return the ServiceResponse<DatastructureMergeRs>.
     * @throws TemplateException
     */
    protected ServiceResponse<DatastructureMergeRs> invoke(ServiceRequest<DatastructureMergeRq> rq)
            throws TemplateException {
        ServiceResponse<DatastructureMergeRs> rs;
        DatastructureMergeRs msg;
        try {
            this.validateRequest(rq);
            this.setContext(rq.getContext());
            msg = this.mergeDatastructure(rq.getRequestMessage());
            if ((msg == null)) {
                super.getLogger().warning("No response message defined.");
            } else {
                super.cleanServiceMessage(msg);
            }
            rs = new ServiceResponse<DatastructureMergeRs>(rq.getContext());
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
     * Merges the target datastructure with source. Keys from source will be copied to the target
     *
     * @param msg the DatastructureMergeRq.
     * @return the DatastructureMergeRs.
     * @throws TemplateException
     */
    protected abstract DatastructureMergeRs mergeDatastructure(DatastructureMergeRq msg) throws TemplateException;

    /**
     * Getter for the Id.
     *
     * @return the String.
     */
    protected static String getId() {
        return ID;
    }
}
