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
package org.nabucco.framework.template.impl.service.textmodule.maintain.support;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.exception.persistence.PersistenceException;
import org.nabucco.framework.base.impl.service.maintain.PersistenceManager;
import org.nabucco.framework.template.facade.exception.textmodule.TextModuleMaintainException;

/**
 * TextModuleMaintainSupport
 * 
 * @author Holger Librenz, PRODYNA AG
 */
public class TextModuleMaintainSupport {

    private PersistenceManager persistenceManager;

    public TextModuleMaintainSupport(PersistenceManager entityManager) {
        this.persistenceManager = entityManager;
    }

    public <T extends NabuccoDatatype> T maintain(T datatype) throws TextModuleMaintainException {

        try {
            datatype = persistenceManager.persist(datatype);
        } catch (PersistenceException pe) {
            throw new TextModuleMaintainException("Error maintaining " + datatype.getClass().getSimpleName() + "!", pe);
        }

        return datatype;
    }

    public <T extends NabuccoDatatype> List<T> maintainList(List<T> datatypeList) throws TextModuleMaintainException {

        for (T datatype : datatypeList) {
            T maintain = maintain(datatype);
            datatypeList.set(datatypeList.indexOf(datatype), maintain);
        }

        return datatypeList;
    }

}
