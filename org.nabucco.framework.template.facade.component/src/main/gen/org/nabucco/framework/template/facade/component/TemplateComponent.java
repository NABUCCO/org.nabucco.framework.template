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

import org.nabucco.framework.base.facade.component.Component;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.template.facade.service.odf.produce.ProduceOdf;
import org.nabucco.framework.template.facade.service.odf.text.OdtTemplateService;
import org.nabucco.framework.template.facade.service.pdf.merge.MergePdf;
import org.nabucco.framework.template.facade.service.textmodule.maintain.MaintainTextModule;
import org.nabucco.framework.template.facade.service.textmodule.produce.ProduceTextModule;
import org.nabucco.framework.template.facade.service.textmodule.resolve.ResolveTextModule;
import org.nabucco.framework.template.facade.service.textmodule.search.SearchTextModule;

/**
 * TemplateComponent<p/>Component for template maintainence and mapping.<p/>
 *
 * @version 1.0
 * @author Holger Librenz, PRODYNA AG, 2011-03-15
 */
public interface TemplateComponent extends Component {

    final String COMPONENT_NAME = "org.nabucco.framework.template";

    final String COMPONENT_PREFIX = "temp";

    final String JNDI_NAME = ((((JNDI_PREFIX + "/") + COMPONENT_NAME) + "/") + "org.nabucco.framework.template.facade.component.TemplateComponent");

    /**
     * Getter for the ProduceTextModule.
     *
     * @return the ProduceTextModule.
     * @throws ServiceException
     */
    ProduceTextModule getProduceTextModule() throws ServiceException;

    /**
     * Getter for the MaintainTextModule.
     *
     * @return the MaintainTextModule.
     * @throws ServiceException
     */
    MaintainTextModule getMaintainTextModule() throws ServiceException;

    /**
     * Getter for the ResolveTextModule.
     *
     * @return the ResolveTextModule.
     * @throws ServiceException
     */
    ResolveTextModule getResolveTextModule() throws ServiceException;

    /**
     * Getter for the SearchTextModule.
     *
     * @return the SearchTextModule.
     * @throws ServiceException
     */
    SearchTextModule getSearchTextModule() throws ServiceException;

    /**
     * Getter for the ProduceOdf.
     *
     * @return the ProduceOdf.
     * @throws ServiceException
     */
    ProduceOdf getProduceOdf() throws ServiceException;

    /**
     * Getter for the MergePdf.
     *
     * @return the MergePdf.
     * @throws ServiceException
     */
    MergePdf getMergePdf() throws ServiceException;

    /**
     * Getter for the OdtTemplateService.
     *
     * @return the OdtTemplateService.
     * @throws ServiceException
     */
    OdtTemplateService getOdtTemplateService() throws ServiceException;
}
