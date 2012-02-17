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
package org.nabucco.framework.template.facade.component;

import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.service.componentrelation.ComponentRelationService;
import org.nabucco.framework.base.facade.service.queryfilter.QueryFilterService;
import org.nabucco.framework.template.facade.component.TemplateComponent;
import org.nabucco.framework.template.facade.service.odf.produce.ProduceOdf;
import org.nabucco.framework.template.facade.service.odf.text.OdtTemplateService;
import org.nabucco.framework.template.facade.service.pdf.merge.MergePdf;
import org.nabucco.framework.template.facade.service.textmodule.maintain.MaintainTextModule;
import org.nabucco.framework.template.facade.service.textmodule.produce.ProduceTextModule;
import org.nabucco.framework.template.facade.service.textmodule.resolve.ResolveTextModule;
import org.nabucco.framework.template.facade.service.textmodule.search.SearchTextModule;

/**
 * TemplateComponentLocalProxy<p/>Component for template maintainence and mapping.<p/>
 *
 * @version 1.0
 * @author Holger Librenz, PRODYNA AG, 2011-03-15
 */
public class TemplateComponentLocalProxy implements TemplateComponent {

    private static final long serialVersionUID = 1L;

    private final TemplateComponentLocal delegate;

    /**
     * Constructs a new TemplateComponentLocalProxy instance.
     *
     * @param delegate the TemplateComponentLocal.
     */
    public TemplateComponentLocalProxy(TemplateComponentLocal delegate) {
        super();
        if ((delegate == null)) {
            throw new IllegalArgumentException("Cannot create local proxy for component [null].");
        }
        this.delegate = delegate;
    }

    @Override
    public String getId() {
        return this.delegate.getId();
    }

    @Override
    public String getName() {
        return this.delegate.getName();
    }

    @Override
    public String getJndiName() {
        return this.delegate.getJndiName();
    }

    @Override
    public ComponentRelationService getComponentRelationService() throws ServiceException {
        return this.delegate.getComponentRelationServiceLocal();
    }

    @Override
    public QueryFilterService getQueryFilterService() throws ServiceException {
        return this.delegate.getQueryFilterServiceLocal();
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }

    @Override
    public ProduceTextModule getProduceTextModule() throws ServiceException {
        return this.delegate.getProduceTextModuleLocal();
    }

    @Override
    public MaintainTextModule getMaintainTextModule() throws ServiceException {
        return this.delegate.getMaintainTextModuleLocal();
    }

    @Override
    public ResolveTextModule getResolveTextModule() throws ServiceException {
        return this.delegate.getResolveTextModuleLocal();
    }

    @Override
    public SearchTextModule getSearchTextModule() throws ServiceException {
        return this.delegate.getSearchTextModuleLocal();
    }

    @Override
    public ProduceOdf getProduceOdf() throws ServiceException {
        return this.delegate.getProduceOdfLocal();
    }

    @Override
    public MergePdf getMergePdf() throws ServiceException {
        return this.delegate.getMergePdfLocal();
    }

    @Override
    public OdtTemplateService getOdtTemplateService() throws ServiceException {
        return this.delegate.getOdtTemplateServiceLocal();
    }
}
