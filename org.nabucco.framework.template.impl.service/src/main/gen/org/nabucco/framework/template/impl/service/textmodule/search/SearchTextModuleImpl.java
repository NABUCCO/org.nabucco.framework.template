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
package org.nabucco.framework.template.impl.service.textmodule.search;

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
import org.nabucco.framework.template.facade.exception.textmodule.TextModuleSearchException;
import org.nabucco.framework.template.facade.message.textmodule.TextModuleListMsg;
import org.nabucco.framework.template.facade.message.textmodule.search.TextModuleSearchRq;
import org.nabucco.framework.template.facade.service.textmodule.search.SearchTextModule;

/**
 * SearchTextModuleImpl<p/>TODO<p/>
 *
 * @version TODO
 * @author hlibrenz, PRODYNA AG, 2011-03-25
 */
public class SearchTextModuleImpl extends ServiceSupport implements SearchTextModule {

    private static final long serialVersionUID = 1L;

    private static final String ID = "SearchTextModule";

    private static Map<String, String[]> ASPECTS;

    private SearchTextModuleServiceHandler searchTextModuleServiceHandler;

    private EntityManager entityManager;

    /** Constructs a new SearchTextModuleImpl instance. */
    public SearchTextModuleImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PersistenceManager persistenceManager = PersistenceManagerFactory.getInstance().createPersistenceManager(
                this.entityManager, super.getLogger());
        this.searchTextModuleServiceHandler = injector.inject(SearchTextModuleServiceHandler.getId());
        if ((this.searchTextModuleServiceHandler != null)) {
            this.searchTextModuleServiceHandler.setPersistenceManager(persistenceManager);
            this.searchTextModuleServiceHandler.setLogger(super.getLogger());
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
            ASPECTS.put("searchTextModule", NO_ASPECTS);
        }
        String[] aspects = ASPECTS.get(operationName);
        if ((aspects == null)) {
            return ServiceSupport.NO_ASPECTS;
        }
        return Arrays.copyOf(aspects, aspects.length);
    }

    @Override
    public ServiceResponse<TextModuleListMsg> searchTextModule(ServiceRequest<TextModuleSearchRq> rq)
            throws TextModuleSearchException {
        if ((this.searchTextModuleServiceHandler == null)) {
            super.getLogger().error("No service implementation configured for searchTextModule().");
            throw new InjectionException("No service implementation configured for searchTextModule().");
        }
        ServiceResponse<TextModuleListMsg> rs;
        this.searchTextModuleServiceHandler.init();
        rs = this.searchTextModuleServiceHandler.invoke(rq);
        this.searchTextModuleServiceHandler.finish();
        return rs;
    }
}
