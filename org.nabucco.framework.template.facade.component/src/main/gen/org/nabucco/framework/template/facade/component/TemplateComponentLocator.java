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

import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.component.locator.ComponentLocator;
import org.nabucco.framework.base.facade.component.locator.ComponentLocatorSupport;

/**
 * Locator for TemplateComponent.
 *
 * @author NABUCCO Generator, PRODYNA AG
 */
public class TemplateComponentLocator extends ComponentLocatorSupport<TemplateComponent> implements
        ComponentLocator<TemplateComponent> {

    private static TemplateComponentLocator instance;

    /**
     * Constructs a new TemplateComponentLocator instance.
     *
     * @param component the Class<TemplateComponent>.
     * @param jndiName the String.
     */
    private TemplateComponentLocator(String jndiName, Class<TemplateComponent> component) {
        super(jndiName, component);
    }

    @Override
    public TemplateComponent getComponent() throws ConnectionException {
        TemplateComponent component = super.getComponent();
        if ((component instanceof TemplateComponentLocal)) {
            return new TemplateComponentLocalProxy(((TemplateComponentLocal) component));
        }
        return component;
    }

    /**
     * Getter for the Instance.
     *
     * @return the TemplateComponentLocator.
     */
    public static TemplateComponentLocator getInstance() {
        if ((instance == null)) {
            instance = new TemplateComponentLocator(TemplateComponent.JNDI_NAME, TemplateComponent.class);
        }
        return instance;
    }
}
