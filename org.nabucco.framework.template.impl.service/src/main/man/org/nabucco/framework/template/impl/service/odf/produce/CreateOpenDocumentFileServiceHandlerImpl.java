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
package org.nabucco.framework.template.impl.service.odf.produce;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.template.facade.datatype.TemplateData;
import org.nabucco.framework.template.facade.datatype.dictionary.DocumentValueDictionaryRoot;
import org.nabucco.framework.template.facade.datatype.odf.OpenDocumentType;
import org.nabucco.framework.template.facade.datatype.odf.TemplateUtil;
import org.nabucco.framework.template.facade.exception.TemplateException;
import org.nabucco.framework.template.facade.message.odf.ProduceOdfRq;
import org.nabucco.framework.template.facade.message.odf.ProduceOdfRs;

/**
 * This service creates a ODF file.
 * 
 * @author Holger Librenz
 */
public class CreateOpenDocumentFileServiceHandlerImpl extends CreateOpenDocumentFileServiceHandler {

    protected static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
            CreateOpenDocumentFileServiceHandlerImpl.class);

    private static final long serialVersionUID = 1L;

    @Override
    protected ProduceOdfRs createOpenDocumentFile(ProduceOdfRq msg) throws TemplateException {

        DocumentValueDictionaryRoot dict = msg.getDict();
        if (dict == null) {
            TemplateException tse = new TemplateException("Empty dictionary found! Could not fill template w/o data!");
            logger.error(tse, tse.getMessage());
            throw tse;
        }

        if (msg.getInputFileData() == null || msg.getInputFileData().getData() == null) {
            String errMsg = "No input file (OOo template) provided!";
            logger.error(errMsg);
            throw new TemplateException(errMsg);
        }

        ByteArrayInputStream src = new ByteArrayInputStream(msg.getInputFileData().getData().getValue());

        try {
            // try to build document
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            OdfProduceUtil.createOpenDocument(src, out, dict);

            byte[] data = new byte[] {};
            out.flush();
            data = out.toByteArray();
            out.close();

            String outFileName = msg.getOutputFileName().getValueAsString();
            String createUserId = this.getContext().getSubject().getUserId().getValueAsString();
            OpenDocumentType outType = msg.getOutputType();
            TemplateData output = TemplateUtil.generateOdfTemplateData(data, outFileName, createUserId, outType);

            // map result File instance values into result message type
            ProduceOdfRs response = new ProduceOdfRs();
            response.setTemplateData(output);

            // return result
            return response;

        } catch (Exception e) {
            throw new TemplateException("Error creating open document file.", e);
        }
    }

}
