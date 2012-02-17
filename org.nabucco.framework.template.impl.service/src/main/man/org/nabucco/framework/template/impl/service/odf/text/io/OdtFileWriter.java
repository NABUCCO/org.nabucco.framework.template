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
package org.nabucco.framework.template.impl.service.odf.text.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * OdtFileWriter
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class OdtFileWriter {

    private static final String COMMENT = "NABUCCO Business Framework";

    private OdtFile file;

    /**
     * Creates a new {@link OdtFileWriter} instance.
     * 
     * @param file
     *            the file to write
     */
    public OdtFileWriter(OdtFile file) {
        if (file == null) {
            throw new IllegalArgumentException("Cannot write file 'null'.");
        }
        this.file = file;
    }

    /**
     * Write the given file into the zip stream.
     * 
     * @param out
     *            the output stream to write to
     * 
     * @throws OdtFileException
     *             when the odt cannot be written
     */
    public void writeFile(OutputStream out) throws OdtFileException {

        try {
            ZipOutputStream stream = new ZipOutputStream(out);
            stream.setLevel(ZipOutputStream.STORED);
            stream.setComment(COMMENT);

            for (Entry<String, byte[]> entry : this.file.getEntries().entrySet()) {

                try {
                    ZipEntry zipEntry = new ZipEntry(entry.getKey());
                    stream.putNextEntry(zipEntry);
                    stream.write(entry.getValue());
                    stream.closeEntry();
                } catch (Exception e) {
                    throw new OdtFileException("Error writing " + entry.getKey() + " into ODT file.", e);
                }
            }

            stream.close();

        } catch (OdtFileException ofe) {
            throw ofe;
        } catch (Exception e) {
            throw new OdtFileException("Error writing into ODT file.", e);
        }
    }

    /**
     * Write the ODT into a file.
     * 
     * @param path
     *            the file path
     * 
     * @return the created file
     * 
     * @throws OdtFileException
     *             when the odt cannot be written
     */
    public synchronized File toFile(String path) throws OdtFileException {
        try {
            File file = new File(path);

            if (!file.exists()) {
                File parent = file.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                file.createNewFile();
            }

            FileOutputStream out = new FileOutputStream(file);
            this.writeFile(out);
            return file;
        } catch (OdtFileException ofe) {
            throw ofe;
        } catch (Exception e) {
            throw new OdtFileException("Error writing ODT to file '" + path + "'.", e);
        }
    }

    /**
     * Write the file into a byte array.
     * 
     * @return the created byte array
     * 
     * @throws OdtFileException
     *             when the odt cannot be written
     */
    public synchronized byte[] toByteArray() throws OdtFileException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        this.writeFile(out);

        return out.toByteArray();
    }

}
