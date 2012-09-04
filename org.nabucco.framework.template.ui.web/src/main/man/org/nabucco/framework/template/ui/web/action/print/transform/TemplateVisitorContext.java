/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.template.ui.web.action.print.transform;

import org.nabucco.framework.base.ui.web.component.work.visitor.WebElementVisitorContext;
import org.nabucco.framework.template.facade.datatype.mapping.TemplateFieldFragment;
import org.nabucco.framework.template.facade.datatype.mapping.TemplateFieldSet;

/**
 * TemplateVisitorContext
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class TemplateVisitorContext implements WebElementVisitorContext {

    private TemplateFieldSet mapping;

    private TemplateFieldFragment fragment;

    /**
     * 
     * Creates a new {@link TemplateVisitorContext} instance.
     * 
     * @param mapping
     */
    public TemplateVisitorContext(TemplateFieldSet mapping) {
        this.mapping = mapping;
    }

    /**
     * 
     * Creates a new {@link TemplateVisitorContext} instance.
     * 
     * @param fragment
     */
    public TemplateVisitorContext(TemplateFieldFragment fragment) {
        this.fragment = fragment;
    }

    /**
     * Returns the mapping element
     * 
     * @return mapping
     */
    public TemplateFieldSet getMapping() {
        return mapping;
    }

    /**
     * 
     * 
     * @return
     */
    public TemplateFieldFragment getFragment() {
        return fragment;
    }
}
