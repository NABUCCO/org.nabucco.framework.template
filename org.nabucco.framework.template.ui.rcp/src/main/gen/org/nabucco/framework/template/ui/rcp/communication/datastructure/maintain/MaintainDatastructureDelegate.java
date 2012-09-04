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
package org.nabucco.framework.template.ui.rcp.communication.datastructure.maintain;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.template.facade.message.datastructure.DatastructureMsg;
import org.nabucco.framework.template.facade.service.datastructure.maintain.MaintainDatastructure;

/**
 * MaintainDatastructureDelegate<p/>Maintain service for datastructure<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-04
 */
public class MaintainDatastructureDelegate extends ServiceDelegateSupport {

    private MaintainDatastructure service;

    /**
     * Constructs a new MaintainDatastructureDelegate instance.
     *
     * @param service the MaintainDatastructure.
     */
    public MaintainDatastructureDelegate(MaintainDatastructure service) {
        super();
        this.service = service;
    }

    /**
     * MaintainDatastructure.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the DatastructureMsg.
     * @return the DatastructureMsg.
     * @throws ClientException
     */
    public DatastructureMsg maintainDatastructure(DatastructureMsg message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<DatastructureMsg> request = new ServiceRequest<DatastructureMsg>(
                super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<DatastructureMsg> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.maintainDatastructure(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(MaintainDatastructure.class, "maintainDatastructure", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException("Cannot execute service operation: MaintainDatastructure.maintainDatastructure");
    }
}
