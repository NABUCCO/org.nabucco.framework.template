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
package org.nabucco.framework.template.impl.service.pdf.merge;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.nabucco.framework.base.facade.datatype.Data;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.template.facade.datatype.pdf.PdfPrintRequest;
import org.nabucco.framework.template.facade.exception.TemplateException;
import org.nabucco.framework.template.facade.message.pdf.MergePdfRq;
import org.nabucco.framework.template.facade.message.pdf.MergePdfRs;
import org.nabucco.framework.template.impl.service.pdf.util.MergePdfUtil;

/**
 * MergePdfFilesServiceHandlerImpl
 * 
 * @author Raffael Bieniek, PRODYNA AG
 */
public class MergePdfFilesServiceHandlerImpl extends MergePdfFilesServiceHandler {

    /**
     * Default serial version UID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected MergePdfRs mergePdfFiles(MergePdfRq msg) throws TemplateException {

        NabuccoList<PdfPrintRequest> pdfPrintRequestList = msg.getPdfPrintRequestList();

        if (pdfPrintRequestList == null || pdfPrintRequestList.isEmpty()) {
            TemplateException tse = new TemplateException("No printRequests found!");
            getLogger().error(tse, tse.getMessage());
            throw tse;
        }

        Data data = null;
        InputStream input = null;
        OutputStream output = null;

        try {
            List<InputStream> pdfs = new ArrayList<InputStream>();

            for (PdfPrintRequest pdfPrintRequest : pdfPrintRequestList) {

                Integer numberOfPrints = pdfPrintRequest.getNumberOfPrints().getValue();

                for (int i = 0; i < numberOfPrints; i++) {

                    InputStream dis = new DataInputStream(new FileInputStream(pdfPrintRequest.getFileName()
                            .getValueAsString()));

                    pdfs.add(dis);
                }

            }

            File out = File.createTempFile(UUID.randomUUID().toString(), ".pdf");

            output = new FileOutputStream(out);
            MergePdfUtil.concatPDFs(pdfs, output, true);

            // Get the number of bytes in the file
            long fileLength = out.length();
            byte fileContent[] = new byte[(int) fileLength];

            input = new FileInputStream(out);

            input.read(fileContent);

            data = new Data(fileContent);

            input.close();
            output.close();

            // delete TEMP-file
            boolean success = out.delete();

            if (!success)
                throw new IllegalArgumentException("Deletion failed");

        } catch (IOException e) {
            String errorMsg = "IOException occurred!";
            getLogger().error(errorMsg);
            throw new TemplateException(errorMsg);
        }

        MergePdfRs rs = new MergePdfRs();
        rs.setData(data);

        return rs;
    }
}
