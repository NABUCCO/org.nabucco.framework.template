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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * OdtFileReader
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class OdtFileReader implements Closeable {

    private ZipInputStream stream;

    /**
     * Creates a new {@link OdtFileReader} instance.
     * 
     * @param file
     *            the file to load
     * 
     * @throws FileNotFoundException
     *             when the file does not exist
     */
    public OdtFileReader(File file) throws FileNotFoundException {
        this(new FileInputStream(file));
    }

    /**
     * Creates a new {@link OdtFileReader} instance.
     * 
     * @param data
     *            the odt file as byte array
     */
    public OdtFileReader(byte[] data) {
        this(new ByteArrayInputStream(data));
    }

    /**
     * Creates a new {@link OdtFileReader} instance.
     * 
     * @param in
     *            the input stream
     */
    public OdtFileReader(InputStream in) {
        if (in == null) {
            throw new IllegalArgumentException("Cannot create ODT template loader for input stream 'null'.");
        }
        this.stream = new ZipInputStream(in);
    }

    /**
     * Load the given ODT template.
     * 
     * @return the loaded template
     * 
     * @throws OdtFileException
     *             when the template cannot be loaded
     */
    public OdtFile readFile() throws OdtFileException {

        try {
            Map<String, byte[]> entries = new HashMap<String, byte[]>();

            ZipEntry entry = null;
            while ((entry = this.stream.getNextEntry()) != null) {
                String name = entry.getName();
                byte[] data = this.readEntry();

                entries.put(name, data);

                this.stream.closeEntry();
            }

            return new OdtFile(entries);
        } catch (Exception e) {
            throw new OdtFileException("Error loading ODT template.", e);
        }
    }

    /**
     * Read the content of a ZIP entry.
     * 
     * @return the zip entry content
     * 
     * @throws IOException
     */
    private byte[] readEntry() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int bytesRead;
        byte[] tempBuffer = new byte[8192 * 2];
        while ((bytesRead = this.stream.read(tempBuffer)) != -1) {
            out.write(tempBuffer, 0, bytesRead);
        }

        return out.toByteArray();
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }

}
