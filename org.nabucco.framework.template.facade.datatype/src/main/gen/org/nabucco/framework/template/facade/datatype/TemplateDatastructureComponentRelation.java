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
package org.nabucco.framework.template.facade.datatype;

import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelation;
import org.nabucco.framework.base.facade.datatype.componentrelation.ComponentRelationType;

/**
 * TemplateDatastructureComponentRelation<p/>Instance of the tempalte datastrcuture<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-06
 */
public class TemplateDatastructureComponentRelation extends ComponentRelation<TemplateDatastructure> {

    private static final long serialVersionUID = 1L;

    /** Constructs a new TemplateDatastructureComponentRelation instance. */
    protected TemplateDatastructureComponentRelation() {
        super();
    }

    /**
     * Constructs a new TemplateDatastructureComponentRelation instance.
     *
     * @param relationType the ComponentRelationType.
     */
    public TemplateDatastructureComponentRelation(ComponentRelationType relationType) {
        super(relationType);
    }

    /**
     * Getter for the Tarthe .
     *
     * @return the TemplateDatastructure.
     */
    public TemplateDatastructure getTarget() {
        return super.getTarget();
    }

    /**
     * Setter for the Target.
     *
     * @param target the TemplateDatastructure.
     */
    public void setTarget(TemplateDatastructure target) {
        super.setTarget(target);
    }

    @Override
    public TemplateDatastructureComponentRelation cloneObject() {
        TemplateDatastructureComponentRelation clone = new TemplateDatastructureComponentRelation();
        super.cloneObject(clone);
        return clone;
    }
}
