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
package org.nabucco.framework.template.impl.service.datastructure.resolve;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.template.facade.message.datastructure.DatastructureMsg;
import org.nabucco.framework.template.facade.service.datastructure.resolve.ResolveDatastructure;

/**
 * ResolveDatastructureImpl<p/>Resolve service for datastructure<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-04
 */
public class ResolveDatastructureImpl extends ServiceSupport implements ResolveDatastructure {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ResolveDatastructure";

    private static Map<String, String[]> ASPECTS;

    private ResolveDatastructureServiceHandler resolveDatastructureServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new ResolveDatastructureImpl instance. */
    public ResolveDatastructureImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.resolveDatastructureServiceHandler = injector.inject(ResolveDatastructureServiceHandler.getId());
        if ((this.resolveDatastructureServiceHandler != null)) {
            this.resolveDatastructureServiceHandler.setPersistenceManager(persistenceManager);
            this.resolveDatastructureServiceHandler.setLogger(super.getLogger());
        }
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
    }

    @Override
    public String[] getAspects(String operationName) {
        if ((ASPECTS == null)) {
            ASPECTS = new HashMap<String, String[]>();
            ASPECTS.put("resolveDatastructure", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<DatastructureMsg> resolveDatastructure(ServiceRequest<DatastructureMsg> rq)
            throws ResolveException {
        if ((this.resolveDatastructureServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for resolveDatastructure().");
            throw new InjectionException("No service implementation configured for resolveDatastructure().");
        }
        ServiceResponse<DatastructureMsg> rs;
        this.resolveDatastructureServiceHandler.init();
        rs = this.resolveDatastructureServiceHandler.invoke(rq);
        this.resolveDatastructureServiceHandler.finish();
        return rs;
    }
}
