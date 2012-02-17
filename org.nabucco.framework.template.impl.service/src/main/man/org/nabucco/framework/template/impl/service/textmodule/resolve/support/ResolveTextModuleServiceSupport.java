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
package org.nabucco.framework.template.impl.service.textmodule.resolve.support;

import org.nabucco.framework.base.facade.exception.service.ResolveException;
import org.nabucco.framework.base.facade.message.context.ServiceMessageContext;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.template.facade.datatype.textmodule.TextModule;

/**
 * ResolveTextModuleServiceSupport
 * 
 * @author Holger Librenz, PRODYNA AG
 */
public class ResolveTextModuleServiceSupport<T extends TextModule> {

    private PersistenceManager persistenceManager;

    public ResolveTextModuleServiceSupport(PersistenceManager persistenceManager,
            ServiceMessageContext serviceMessageContext) {
        this.persistenceManager = persistenceManager;
    }

    public T resolve(T textModule) throws ResolveException {

        try {

            // attach
            textModule = persistenceManager.find(textModule);

            return textModule;

        } catch (Exception e) {
            throw new ResolveException("Cannot resolve "
                    + textModule.getClass().getSimpleName() + " with id " + textModule.getId(), e);
        }
    }
}
