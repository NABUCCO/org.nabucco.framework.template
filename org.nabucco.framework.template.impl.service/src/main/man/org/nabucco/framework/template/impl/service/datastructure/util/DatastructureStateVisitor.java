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
package org.nabucco.framework.template.impl.service.datastructure.util;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * DatastructureStateVisitor
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DatastructureStateVisitor extends DatatypeVisitor {

    private DatatypeState state;

    private boolean changed = false;

    /**
     * 
     * Creates a new {@link DatastructureStateVisitor} instance.
     * 
     * @param state
     *            state to be setted in the structure
     */
    public DatastructureStateVisitor(DatatypeState state) {
        if (state == null) {
            throw new IllegalArgumentException("DatatypeState must not be [null].");
        }
        this.state = state;
    }

    @Override
    public void visit(Datatype datatype) throws VisitorException {
        super.visit(datatype);
        if (datatype.getDatatypeState() != state) {
            datatype.setDatatypeState(this.state);
            changed = true;
        }
    }

    /**
     * Returns true is the visitor made min 1 change
     * 
     * @return
     */
    public boolean changesMade() {
        return changed;
    }

}
