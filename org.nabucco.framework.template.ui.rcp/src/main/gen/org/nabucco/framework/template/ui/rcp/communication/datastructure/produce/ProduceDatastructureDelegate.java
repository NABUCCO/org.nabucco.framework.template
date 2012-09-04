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
package org.nabucco.framework.template.ui.rcp.communication.datastructure.produce;

import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.context.ServiceSubContext;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.plugin.base.component.communication.ServiceDelegateSupport;
import org.nabucco.framework.template.facade.message.datastructure.ProduceDSRq;
import org.nabucco.framework.template.facade.message.datastructure.ProduceDSRs;
import org.nabucco.framework.template.facade.service.datastructure.produce.ProduceDatastructure;

/**
 * ProduceDatastructureDelegate<p/>Producer service for datastructure<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-04
 */
public class ProduceDatastructureDelegate extends ServiceDelegateSupport {

    private ProduceDatastructure service;

    /**
     * Constructs a new ProduceDatastructureDelegate instance.
     *
     * @param service the ProduceDatastructure.
     */
    public ProduceDatastructureDelegate(ProduceDatastructure service) {
        super();
        this.service = service;
    }

    /**
     * ProduceDatastructureFromTemplate.
     *
     * @param subContexts the ServiceSubContext....
     * @param message the ProduceDSRq.
     * @return the ProduceDSRs.
     * @throws ClientException
     */
    public ProduceDSRs produceDatastructureFromTemplate(ProduceDSRq message, ServiceSubContext... subContexts)
            throws ClientException {
        ServiceRequest<ProduceDSRq> request = new ServiceRequest<ProduceDSRq>(super.createServiceContext(subContexts));
        request.setRequestMessage(message);
        ServiceResponse<ProduceDSRs> response = null;
        Exception exception = null;
        if ((service != null)) {
            super.handleRequest(request);
            long start = NabuccoSystem.getCurrentTimeMillis();
            try {
                response = service.produceDatastructureFromTemplate(request);
            } catch (Exception e) {
                exception = e;
            } finally {
                long end = NabuccoSystem.getCurrentTimeMillis();
                long duration = (end - start);
                super.monitorResult(ProduceDatastructure.class, "produceDatastructureFromTemplate", duration, exception);
            }
            if ((response != null)) {
                super.handleResponse(response);
                return response.getResponseMessage();
            }
        }
        throw new ClientException(
                "Cannot execute service operation: ProduceDatastructure.produceDatastructureFromTemplate");
    }
}
