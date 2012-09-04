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
package org.nabucco.framework.template.impl.component;

/**
 * TemplateComponentJndiNames<p/>Component for template maintainence and mapping.<p/>
 *
 * @version 1.0
 * @author Holger Librenz, PRODYNA AG, 2011-03-15
 */
public interface TemplateComponentJndiNames {

    final String COMPONENT_RELATION_SERVICE_LOCAL = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.component.ComponentRelationService/local";

    final String COMPONENT_RELATION_SERVICE_REMOTE = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.component.ComponentRelationService/remote";

    final String QUERY_FILTER_SERVICE_LOCAL = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.component.QueryFilterService/local";

    final String QUERY_FILTER_SERVICE_REMOTE = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.component.QueryFilterService/remote";

    final String PRODUCE_DATASTRUCTURE_LOCAL = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.datastructure.produce.ProduceDatastructure/local";

    final String PRODUCE_DATASTRUCTURE_REMOTE = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.datastructure.produce.ProduceDatastructure/remote";

    final String MAINTAIN_DATASTRUCTURE_LOCAL = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.datastructure.maintain.MaintainDatastructure/local";

    final String MAINTAIN_DATASTRUCTURE_REMOTE = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.datastructure.maintain.MaintainDatastructure/remote";

    final String RESOLVE_DATASTRUCTURE_LOCAL = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.datastructure.resolve.ResolveDatastructure/local";

    final String RESOLVE_DATASTRUCTURE_REMOTE = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.datastructure.resolve.ResolveDatastructure/remote";

    final String TRANSFER_DATASTRUCTURE_DATA_LOCAL = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.datastructure.transfer.TransferDatastructureData/local";

    final String TRANSFER_DATASTRUCTURE_DATA_REMOTE = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.datastructure.transfer.TransferDatastructureData/remote";

    final String PRODUCE_TEXT_MODULE_LOCAL = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.textmodule.produce.ProduceTextModule/local";

    final String PRODUCE_TEXT_MODULE_REMOTE = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.textmodule.produce.ProduceTextModule/remote";

    final String MAINTAIN_TEXT_MODULE_LOCAL = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.textmodule.maintain.MaintainTextModule/local";

    final String MAINTAIN_TEXT_MODULE_REMOTE = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.textmodule.maintain.MaintainTextModule/remote";

    final String RESOLVE_TEXT_MODULE_LOCAL = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.textmodule.resolve.ResolveTextModule/local";

    final String RESOLVE_TEXT_MODULE_REMOTE = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.textmodule.resolve.ResolveTextModule/remote";

    final String SEARCH_TEXT_MODULE_LOCAL = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.textmodule.search.SearchTextModule/local";

    final String SEARCH_TEXT_MODULE_REMOTE = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.textmodule.search.SearchTextModule/remote";

    final String PRODUCE_ODF_LOCAL = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.odf.produce.ProduceOdf/local";

    final String PRODUCE_ODF_REMOTE = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.odf.produce.ProduceOdf/remote";

    final String MERGE_PDF_LOCAL = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.pdf.merge.MergePdf/local";

    final String MERGE_PDF_REMOTE = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.pdf.merge.MergePdf/remote";

    final String ODT_TEMPLATE_SERVICE_LOCAL = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.odf.text.OdtTemplateService/local";

    final String ODT_TEMPLATE_SERVICE_REMOTE = "nabucco/org.nabucco.framework.template/org.nabucco.framework.template.facade.service.odf.text.OdtTemplateService/remote";
}
