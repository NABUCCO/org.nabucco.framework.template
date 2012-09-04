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
package org.nabucco.framework.template.impl.service.textmodule.produce;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.injection.InjectionException;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.impl.service.ServiceSupport;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManagerFactory;
import org.nabucco.framework.template.facade.exception.textmodule.TextModuleProduceException;
import org.nabucco.framework.template.facade.message.textmodule.TextModuleListMsg;
import org.nabucco.framework.template.facade.message.textmodule.produce.TextModuleProduceRq;
import org.nabucco.framework.template.facade.service.textmodule.produce.ProduceTextModule;

/**
 * ProduceTextModuleImpl<p/>Producer service for text modules<p/>
 *
 * @version 1.0
 * @author hlibrenz, PRODYNA AG, 2011-03-24
 */
public class ProduceTextModuleImpl extends ServiceSupport implements ProduceTextModule {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ProduceTextModule";

    private static Map<String, String[]> ASPECTS;

    private ProduceTextModuleServiceHandler produceTextModuleServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new ProduceTextModuleImpl instance. */
    public ProduceTextModuleImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.produceTextModuleServiceHandler = injector.inject(ProduceTextModuleServiceHandler.getId());
        if ((this.produceTextModuleServiceHandler != null)) {
            this.produceTextModuleServiceHandler.setPersistenceManager(persistenceManager);
            this.produceTextModuleServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("produceTextModule", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<TextModuleListMsg> produceTextModule(ServiceRequest<TextModuleProduceRq> rq)
            throws TextModuleProduceException {
        if ((this.produceTextModuleServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for produceTextModule().");
            throw new InjectionException("No service implementation configured for produceTextModule().");
        }
        ServiceResponse<TextModuleListMsg> rs;
        this.produceTextModuleServiceHandler.init();
        rs = this.produceTextModuleServiceHandler.invoke(rq);
        this.produceTextModuleServiceHandler.finish();
        return rs;
    }
}
