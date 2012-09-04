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
package org.nabucco.framework.template.impl.service.odf.produce;

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
import org.nabucco.framework.template.facade.exception.TemplateException;
import org.nabucco.framework.template.facade.message.odf.ProduceOdfRq;
import org.nabucco.framework.template.facade.message.odf.ProduceOdfRs;
import org.nabucco.framework.template.facade.service.odf.produce.ProduceOdf;

/**
 * ProduceOdfImpl<p/>Produces ODF files.<p/>
 *
 * @version 1.1
 * @author Holger Librenz, PRODYNA AG, 2011-06-15
 */
public class ProduceOdfImpl extends ServiceSupport implements ProduceOdf {

    private static final long serialVersionUID = 1L;

    private static final String ID = "ProduceOdf";

    private static Map<String, String[]> ASPECTS;

    private CreateOpenDocumentFileServiceHandler createOpenDocumentFileServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new ProduceOdfImpl instance. */
    public ProduceOdfImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.createOpenDocumentFileServiceHandler = injector.inject(CreateOpenDocumentFileServiceHandler.getId());
        if ((this.createOpenDocumentFileServiceHandler != null)) {
            this.createOpenDocumentFileServiceHandler.setPersistenceManager(persistenceManager);
            this.createOpenDocumentFileServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("createOpenDocumentFile", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<ProduceOdfRs> createOpenDocumentFile(ServiceRequest<ProduceOdfRq> rq)
            throws TemplateException {
        if ((this.createOpenDocumentFileServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for createOpenDocumentFile().");
            throw new InjectionException("No service implementation configured for createOpenDocumentFile().");
        }
        ServiceResponse<ProduceOdfRs> rs;
        this.createOpenDocumentFileServiceHandler.init();
        rs = this.createOpenDocumentFileServiceHandler.invoke(rq);
        this.createOpenDocumentFileServiceHandler.finish();
        return rs;
    }
}
