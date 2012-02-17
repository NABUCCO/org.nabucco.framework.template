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
package org.nabucco.framework.template.facade.exception.textmodule;

import org.nabucco.framework.base.facade.exception.NabuccoException;

/**
 * TextModuleProduceException<p/>Exception for the facade of the template component.<p/>
 *
 * @version 1.0
 * @author Holger Librenz, PRODYNA AG, 2011-03-24
 */
public class TextModuleProduceException extends NabuccoException {

    private static final long serialVersionUID = 1L;

    /** Constructs a new TextModuleProduceException instance. */
    public TextModuleProduceException() {
        super();
    }

    /**
     * Constructs a new TextModuleProduceException instance.
     *
     * @param message the String.
     */
    public TextModuleProduceException(String message) {
        super(message);
    }

    /**
     * Constructs a new TextModuleProduceException instance.
     *
     * @param cause the Throwable.
     */
    public TextModuleProduceException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new TextModuleProduceException instance.
     *
     * @param cause the Throwable.
     * @param message the String.
     */
    public TextModuleProduceException(String message, Throwable cause) {
        super(message, cause);
    }
}
