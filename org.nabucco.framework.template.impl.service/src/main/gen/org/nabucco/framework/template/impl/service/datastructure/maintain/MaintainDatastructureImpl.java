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
package org.nabucco.framework.template.impl.service.datastructure.maintain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.exception.service.MaintainException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.template.facade.message.datastructure.DatastructureMsg;
import org.nabucco.framework.template.facade.service.datastructure.maintain.MaintainDatastructure;

/**
 * MaintainDatastructureImpl<p/>Maintain service for datastructure<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-04
 */
public class MaintainDatastructureImpl extends ServiceSupport implements MaintainDatastructure {

    private static final long serialVersionUID = 1L;

    private static final String ID = "MaintainDatastructure";

    private static Map<String, String[]> ASPECTS;

    private MaintainDatastructureServiceHandler maintainDatastructureServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new MaintainDatastructureImpl instance. */
    public MaintainDatastructureImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.maintainDatastructureServiceHandler = injector.inject(MaintainDatastructureServiceHandler.getId());
        if ((this.maintainDatastructureServiceHandler != null)) {
            this.maintainDatastructureServiceHandler.setPersistenceManager(persistenceManager);
            this.maintainDatastructureServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("maintainDatastructure", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<DatastructureMsg> maintainDatastructure(ServiceRequest<DatastructureMsg> rq)
            throws MaintainException {
        if ((this.maintainDatastructureServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for maintainDatastructure().");
            throw new InjectionException("No service implementation configured for maintainDatastructure().");
        }
        ServiceResponse<DatastructureMsg> rs;
        this.maintainDatastructureServiceHandler.init();
        rs = this.maintainDatastructureServiceHandler.invoke(rq);
        this.maintainDatastructureServiceHandler.finish();
        return rs;
    }
}
