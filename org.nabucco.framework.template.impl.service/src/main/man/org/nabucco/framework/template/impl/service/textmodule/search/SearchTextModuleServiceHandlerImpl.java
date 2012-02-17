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
package org.nabucco.framework.template.impl.service.textmodule.search;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.code.CodeFacade;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.impl.service.maintain.NabuccoQuery;
import org.nabucco.framework.template.facade.datatype.textmodule.TextModule;
import org.nabucco.framework.template.facade.exception.textmodule.TextModuleSearchException;
import org.nabucco.framework.template.facade.message.textmodule.TextModuleListMsg;
import org.nabucco.framework.template.facade.message.textmodule.search.TextModuleSearchRq;

/**
 * SearchTextModuleServiceHandlerImpl
 * 
 * @author Holger Librenz, PRODYNA AG
 */
public class SearchTextModuleServiceHandlerImpl extends SearchTextModuleServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected TextModuleListMsg searchTextModule(TextModuleSearchRq msg) throws TextModuleSearchException {
        TextModuleListMsg result = new TextModuleListMsg();

        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT d FROM TextModule d");

        NabuccoQuery<TextModule> query = null;
        try {
            query = getPersistenceManager().createQuery(queryString.toString());
        } catch (PersistenceException e) {
            throw new TextModuleSearchException("Error during search service call.", e);
        }

        List<TextModule> resultList;
        try {
            resultList = query.getResultList();

            CodeFacade facade = CodeFacade.getInstance();

            // resolve DynamicCode
            for (TextModule item : resultList) {
                if (item.getLanguageRefId() != null) {
                    item.setLanguage(facade.getCode(item.getLanguageRefId()));
                }

                item.setModuleType(facade.getCode(item.getModuleTypeRefId()));
                item.setModuleCategoryType(facade.getCode(item.getModuleCategoryTypeRefId()));
            }
        } catch (Exception e) {
            throw new TextModuleSearchException(e);
        }

        result.getTextModuleList().addAll(resultList);

        return result;
    }

}
