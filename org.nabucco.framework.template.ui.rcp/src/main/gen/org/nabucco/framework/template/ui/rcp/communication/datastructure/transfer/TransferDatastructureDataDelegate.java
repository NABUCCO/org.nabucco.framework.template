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
package org.nabucco.framework.template.ui.rcp.communication.datastructure.transfer;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.template.facade.message.datastructure.DatastructureMergeRq;
import org.nabucco.framework.template.facade.message.datastructure.DatastructureMergeRs;
import org.nabucco.framework.template.facade.service.datastructure.transfer.TransferDatastructureData;

/**
 * TransferDatastructureDataDelegate<p/>Transfer the data to and from datastructures<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-04
 */
public class TransferDatastructureDataDelegate extends ServiceDelegateSupport {

    private TransferDatastructureData service;

    /**
     * Constructs a new TransferDatastructureDataDelegate instance.
     *
     * @param service the TransferDatastructureData.
     */
    public TransferDatastructureDataDelegate(TransferDatastructureData service) {
        super();
        this.service = service;
    }

    /**
     * MergeDatastructure.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the DatastructureMergeRq.
     * @return the DatastructureMergeRs.
     * @throws ClientException
     */
    public DatastructureMergeRs mergeDatastructure(DatastructureMergeRq message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<DatastructureMergeRq> request = new ServiceRequest<DatastructureMergeRq>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<DatastructureMergeRs> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.mergeDatastructure(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(TransferDatastructureData.class, "mergeDatastructure", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: TransferDatastructureData.mergeDatastructure");
    }
}
