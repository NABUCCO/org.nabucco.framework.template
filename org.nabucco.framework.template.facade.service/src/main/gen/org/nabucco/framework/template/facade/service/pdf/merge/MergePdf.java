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
package org.nabucco.framework.template.facade.service.pdf.merge;

import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.base.facade.service.Service;
import org.nabucco.framework.template.facade.exception.TemplateException;
import org.nabucco.framework.template.facade.message.pdf.MergePdfRq;
import org.nabucco.framework.template.facade.message.pdf.MergePdfRs;

/**
 * MergePdf<p/>Merges PDF files.<p/>
 *
 * @version 1.0
 * @author Raffael Bieniek, PRODYNA AG, 2011-07-04
 */
public interface MergePdf extends Service {

    /**
     * Produces a new PDF file based on several PDF Files.
     *
     * @param rq the ServiceRequest<MergePdfRq>.
     * @return the ServiceResponse<MergePdfRs>.
     * @throws TemplateException
     */
    ServiceResponse<MergePdfRs> mergePdfFiles(ServiceRequest<MergePdfRq> rq) throws TemplateException;
}
