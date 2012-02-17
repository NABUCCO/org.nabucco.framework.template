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
import org.nabucco.framework.template.facade.service.odf.produce.ProduceOdf;
import org.nabucco.framework.template.facade.service.odf.text.OdtTemplateService;
import org.nabucco.framework.template.facade.service.pdf.merge.MergePdf;
import org.nabucco.framework.template.facade.service.textmodule.maintain.MaintainTextModule;
import org.nabucco.framework.template.facade.service.textmodule.produce.ProduceTextModule;
import org.nabucco.framework.template.facade.service.textmodule.resolve.ResolveTextModule;
import org.nabucco.framework.template.facade.service.textmodule.search.SearchTextModule;

/**
 * TemplateComponentLocal<p/>Component for template maintainence and mapping.<p/>
 *
 * @version 1.0
 * @author Holger Librenz, PRODYNA AG, 2011-03-15
 */
public interface TemplateComponentLocal extends TemplateComponent {

    /**
     * Getter for the ComponentRelationServiceLocal.
     *
     * @return the ComponentRelationService.
     * @throws ServiceException
     */
    ComponentRelationService getComponentRelationServiceLocal() throws ServiceException;

    /**
     * Getter for the QueryFilterServiceLocal.
     *
     * @return the QueryFilterService.
     * @throws ServiceException
     */
    QueryFilterService getQueryFilterServiceLocal() throws ServiceException;

    /**
     * Getter for the ProduceTextModuleLocal.
     *
     * @return the ProduceTextModule.
     * @throws ServiceException
     */
    ProduceTextModule getProduceTextModuleLocal() throws ServiceException;

    /**
     * Getter for the MaintainTextModuleLocal.
     *
     * @return the MaintainTextModule.
     * @throws ServiceException
     */
    MaintainTextModule getMaintainTextModuleLocal() throws ServiceException;

    /**
     * Getter for the ResolveTextModuleLocal.
     *
     * @return the ResolveTextModule.
     * @throws ServiceException
     */
    ResolveTextModule getResolveTextModuleLocal() throws ServiceException;

    /**
     * Getter for the SearchTextModuleLocal.
     *
     * @return the SearchTextModule.
     * @throws ServiceException
     */
    SearchTextModule getSearchTextModuleLocal() throws ServiceException;

    /**
     * Getter for the ProduceOdfLocal.
     *
     * @return the ProduceOdf.
     * @throws ServiceException
     */
    ProduceOdf getProduceOdfLocal() throws ServiceException;

    /**
     * Getter for the MergePdfLocal.
     *
     * @return the MergePdf.
     * @throws ServiceException
     */
    MergePdf getMergePdfLocal() throws ServiceException;

    /**
     * Getter for the OdtTemplateServiceLocal.
     *
     * @return the OdtTemplateService.
     * @throws ServiceException
     */
    OdtTemplateService getOdtTemplateServiceLocal() throws ServiceException;
}
