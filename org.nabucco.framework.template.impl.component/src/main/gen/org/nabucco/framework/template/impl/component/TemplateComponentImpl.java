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
package org.nabucco.framework.template.impl.component;

import org.nabucco.framework.base.facade.component.handler.PostConstructHandler;
import org.nabucco.framework.base.facade.component.handler.PreDestroyHandler;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;
import org.nabucco.framework.base.facade.service.injection.InjectionProvider;
import org.nabucco.framework.base.facade.service.queryfilter.QueryFilterService;
import org.nabucco.framework.base.impl.component.ComponentSupport;
import org.nabucco.framework.template.facade.component.TemplateComponentLocal;
import org.nabucco.framework.template.facade.component.TemplateComponentRemote;
import org.nabucco.framework.template.facade.service.odf.produce.ProduceOdf;
import org.nabucco.framework.template.facade.service.odf.text.OdtTemplateService;
import org.nabucco.framework.template.facade.service.pdf.merge.MergePdf;
import org.nabucco.framework.template.facade.service.textmodule.maintain.MaintainTextModule;
import org.nabucco.framework.template.facade.service.textmodule.produce.ProduceTextModule;
import org.nabucco.framework.template.facade.service.textmodule.resolve.ResolveTextModule;
import org.nabucco.framework.template.facade.service.textmodule.search.SearchTextModule;

/**
 * TemplateComponentImpl<p/>Component for template maintainence and mapping.<p/>
 *
 * @version 1.0
 * @author Holger Librenz, PRODYNA AG, 2011-03-15
 */
public class TemplateComponentImpl extends ComponentSupport implements TemplateComponentLocal, TemplateComponentRemote {

    private static final long serialVersionUID = 1L;

    private static final String ID = "TemplateComponent";

    /** Constructs a new TemplateComponentImpl instance. */
    public TemplateComponentImpl() {
        super();
    }

    @Override
    public void postConstruct() {
        super.postConstruct();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PostConstructHandler handler = injector.inject(PostConstructHandler.getId());
        if ((handler == null)) {
            if (super.getLogger().isDebugEnabled()) {
                super.getLogger().debug("No post construct handler configured for \'", ID, "\'.");
            }
            return;
        }
        handler.setLocatable(this);
        handler.setLogger(super.getLogger());
        handler.invoke();
    }

    @Override
    public void preDestroy() {
        super.preDestroy();
        InjectionProvider injector = InjectionProvider.getInstance(ID);
        PreDestroyHandler handler = injector.inject(PreDestroyHandler.getId());
        if ((handler == null)) {
            if (super.getLogger().isDebugEnabled()) {
                super.getLogger().debug("No pre destroy handler configured for \'", ID, "\'.");
            }
            return;
        }
        handler.setLocatable(this);
        handler.setLogger(super.getLogger());
        handler.invoke();
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return COMPONENT_NAME;
    }

    @Override
    public String getJndiName() {
        return JNDI_NAME;
    }

    @Override
    public ComponentRelationService getComponentRelationService() throws ServiceException {
        return super.lookup(TemplateComponentJndiNames.COMPONENT_RELATION_SERVICE_REMOTE,
                ComponentRelationService.class);
    }

    @Override
    public ComponentRelationService getComponentRelationServiceLocal() throws ServiceException {
        return super
                .lookup(TemplateComponentJndiNames.COMPONENT_RELATION_SERVICE_LOCAL, ComponentRelationService.class);
    }

    @Override
    public QueryFilterService getQueryFilterService() throws ServiceException {
        return super.lookup(TemplateComponentJndiNames.QUERY_FILTER_SERVICE_REMOTE, QueryFilterService.class);
    }

    @Override
    public QueryFilterService getQueryFilterServiceLocal() throws ServiceException {
        return super.lookup(TemplateComponentJndiNames.QUERY_FILTER_SERVICE_LOCAL, QueryFilterService.class);
    }

    @Override
    public ProduceTextModule getProduceTextModuleLocal() throws ServiceException {
        return super.lookup(TemplateComponentJndiNames.PRODUCE_TEXT_MODULE_LOCAL, ProduceTextModule.class);
    }

    @Override
    public ProduceTextModule getProduceTextModule() throws ServiceException {
        return super.lookup(TemplateComponentJndiNames.PRODUCE_TEXT_MODULE_REMOTE, ProduceTextModule.class);
    }

    @Override
    public MaintainTextModule getMaintainTextModuleLocal() throws ServiceException {
        return super.lookup(TemplateComponentJndiNames.MAINTAIN_TEXT_MODULE_LOCAL, MaintainTextModule.class);
    }

    @Override
    public MaintainTextModule getMaintainTextModule() throws ServiceException {
        return super.lookup(TemplateComponentJndiNames.MAINTAIN_TEXT_MODULE_REMOTE, MaintainTextModule.class);
    }

    @Override
    public ResolveTextModule getResolveTextModuleLocal() throws ServiceException {
        return super.lookup(TemplateComponentJndiNames.RESOLVE_TEXT_MODULE_LOCAL, ResolveTextModule.class);
    }

    @Override
    public ResolveTextModule getResolveTextModule() throws ServiceException {
        return super.lookup(TemplateComponentJndiNames.RESOLVE_TEXT_MODULE_REMOTE, ResolveTextModule.class);
    }

    @Override
    public SearchTextModule getSearchTextModuleLocal() throws ServiceException {
        return super.lookup(TemplateComponentJndiNames.SEARCH_TEXT_MODULE_LOCAL, SearchTextModule.class);
    }

    @Override
    public SearchTextModule getSearchTextModule() throws ServiceException {
        return super.lookup(TemplateComponentJndiNames.SEARCH_TEXT_MODULE_REMOTE, SearchTextModule.class);
    }

    @Override
    public ProduceOdf getProduceOdfLocal() throws ServiceException {
        return super.lookup(TemplateComponentJndiNames.PRODUCE_ODF_LOCAL, ProduceOdf.class);
    }

    @Override
    public ProduceOdf getProduceOdf() throws ServiceException {
        return super.lookup(TemplateComponentJndiNames.PRODUCE_ODF_REMOTE, ProduceOdf.class);
    }

    @Override
    public MergePdf getMergePdfLocal() throws ServiceException {
        return super.lookup(TemplateComponentJndiNames.MERGE_PDF_LOCAL, MergePdf.class);
    }

    @Override
    public MergePdf getMergePdf() throws ServiceException {
        return super.lookup(TemplateComponentJndiNames.MERGE_PDF_REMOTE, MergePdf.class);
    }

    @Override
    public OdtTemplateService getOdtTemplateServiceLocal() throws ServiceException {
        return super.lookup(TemplateComponentJndiNames.ODT_TEMPLATE_SERVICE_LOCAL, OdtTemplateService.class);
    }

    @Override
    public OdtTemplateService getOdtTemplateService() throws ServiceException {
        return super.lookup(TemplateComponentJndiNames.ODT_TEMPLATE_SERVICE_REMOTE, OdtTemplateService.class);
    }
}
