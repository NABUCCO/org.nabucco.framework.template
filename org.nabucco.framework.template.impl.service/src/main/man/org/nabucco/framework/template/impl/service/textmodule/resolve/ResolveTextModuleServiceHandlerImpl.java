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
package org.nabucco.framework.template.impl.service.textmodule.resolve;

import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodeFacade;
import org.nabucco.framework.template.facade.datatype.textmodule.TextModule;
import org.nabucco.framework.template.facade.exception.textmodule.TextModuleResolveException;
import org.nabucco.framework.template.facade.message.textmodule.TextModuleMsg;
import org.nabucco.framework.template.impl.service.textmodule.resolve.support.ResolveTextModuleServiceSupport;

/**
 * ResolveTextModuleServiceHandlerImpl
 * 
 * @author Holger Librenz, PRODYNA AG
 */
public class ResolveTextModuleServiceHandlerImpl extends ResolveTextModuleServiceHandler {

    private static final long serialVersionUID = 1L;

    @Override
    protected TextModuleMsg resolveTextModule(TextModuleMsg msg) throws TextModuleResolveException {

        ResolveTextModuleServiceSupport<TextModule> support = new ResolveTextModuleServiceSupport<TextModule>(
                this.getPersistenceManager(), this.getContext());

        TextModule textModule = msg.getTextModule();

        try {
            textModule = support.resolve(textModule);

            // and last, but not least, retrieve the direct dynamic codes
            CodeFacade codeFacade = CodeFacade.getInstance();
            Code languageCode = codeFacade.getCode(textModule.getLanguageRefId());
            Code moduleTypeCode = codeFacade.getCode(textModule.getModuleTypeRefId());
            Code moduleCategoryType = codeFacade.getCode(textModule.getModuleCategoryTypeRefId());
            textModule.setLanguage(languageCode);
            textModule.setModuleType(moduleTypeCode);
            textModule.setModuleCategoryType(moduleCategoryType);

        } catch (Exception e) {
            // we only wrap all upcoming exceptions.
            throw new TextModuleResolveException("Error resolving TextModule.", e);
        }

        TextModuleMsg response = new TextModuleMsg();
        response.setTextModule(textModule);
        return response;
    }

}
