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
package org.nabucco.framework.template.ui.web.action.print.transform.editor;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.ui.web.component.work.visitor.DefaultWebElementVisitor;
import org.nabucco.framework.template.facade.datatype.mapping.TemplateFieldSet;
import org.nabucco.framework.template.facade.datatype.mapping.TemplateFieldValue;
import org.nabucco.framework.template.ui.web.action.print.transform.TemplateVisitorContext;

/**
 * GridVisitor
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class WebElementTemplatingVisitor extends DefaultWebElementVisitor<TemplateVisitorContext> {

    protected NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(WebElementTemplatingVisitor.class);

    /**
     * Create a new template value for the given key value pair.
     * 
     * @param key
     *            the mapping key
     * @param value
     *            the mapping value
     * @param parent
     *            the parent field mapping
     */
    protected void createValue(String key, String value, TemplateFieldSet parent) {
        TemplateFieldValue templateValue = new TemplateFieldValue();
        templateValue.setFieldName(key);
        templateValue.setValue(value);

        parent.getValues().add(templateValue);
    }

}
