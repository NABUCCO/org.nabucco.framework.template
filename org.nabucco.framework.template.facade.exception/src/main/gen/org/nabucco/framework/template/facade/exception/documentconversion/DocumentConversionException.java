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
package org.nabucco.framework.template.facade.exception.documentconversion;

import org.nabucco.framework.base.facade.exception.NabuccoException;

/**
 * DocumentConversionException<p/>Exception thrown if any error occurs during document conversion<p/>
 *
 * @version 1.0
 * @author Holger Librenz, PRODYNA AG, 2011-04-04
 */
public class DocumentConversionException extends NabuccoException {

    private static final long serialVersionUID = 1L;

    /** Constructs a new DocumentConversionException instance. */
    public DocumentConversionException() {
        super();
    }

    /**
     * Constructs a new DocumentConversionException instance.
     *
     * @param message the String.
     */
    public DocumentConversionException(String message) {
        super(message);
    }

    /**
     * Constructs a new DocumentConversionException instance.
     *
     * @param cause the Throwable.
     */
    public DocumentConversionException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new DocumentConversionException instance.
     *
     * @param cause the Throwable.
     * @param message the String.
     */
    public DocumentConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
