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
package org.nabucco.framework.template.facade.adapter;

import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.template.facade.exception.documentconversion.DocumentConversionException;
import org.nabucco.framework.template.facade.message.documentconversion.ConvertRq;
import org.nabucco.framework.template.facade.message.documentconversion.ConvertRs;

/**
 * Interface for document conversion adapter(s).
 * 
 * @author Holger Librenz
 */
public interface DocumentConversionAdapter extends Service {
    
    static final String JNDI_NAME = 
    	"adapter/org.nabucco.framework.template.facade.adapter.DocumentConversionAdapter";
    

    ConvertRs generateReport(ConvertRq reportJobRq) throws DocumentConversionException;

}
