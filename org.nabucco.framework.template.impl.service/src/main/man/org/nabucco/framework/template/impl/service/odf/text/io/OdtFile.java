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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.utils.StreamUtil;

/**
 * OdtFile
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class OdtFile {

    /** Name of ODT zip entry 'content.xml' */
    public static final String ENTRY_CONTENT = "content.xml";

    /** Name of ODT zip entry 'styles.xml' */
    public static final String ENTRY_STYLES = "styles.xml";

    /** Name of ODT zip entry 'manifest.xml' */
    public static final String ENTRY_MANIFEST = "META-INF/manifest.xml";

    /** ODT Character Encoding */
    private static final String CHARSET = "UTF-8";

    private Map<String, byte[]> entries;

    /**
     * Creates a new {@link OdtFile} instance.
     * 
     * @param entries
     *            the odt template entries
     */
    OdtFile(Map<String, byte[]> entries) {
        if (entries == null) {
            throw new IllegalStateException("Cannot create ODT for entries 'null'.");
        }
        this.entries = entries;
    }

    /**
     * Getter for the entries.
     * 
     * @return Returns the entries.
     */
    Map<String, byte[]> getEntries() {
        return this.entries;
    }

    /**
     * Load the data of a given odt entry as string.
     * 
     * @param name
     *            name of the odt entry to load
     * 
     * @return the loaded file content
     * 
     * @throws OdtFileException
     *             when the content cannot be loaded
     */
    public String getFileContent(String name) throws OdtFileException {

        try {

            InputStream input = this.getInput(name);
            InputStreamReader reader = new InputStreamReader(input, CHARSET);
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuilder result = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }

            return result.toString();

        } catch (Exception e) {
            throw new OdtFileException("Error parsing content.xml file.", e);
        }
    }

    /**
     * Set the given string as file content into the odt file.
     * 
     * @param name
     *            name of the file
     * @param value
     *            value of the file
     * 
     * @throws OdtFileException
     *             when the file content cannot be added
     */
    public void setFileContent(String name, String value) throws OdtFileException {

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream stream = new PrintStream(out, true, CHARSET);
            stream.append(value);

            byte[] data = out.toByteArray();
            this.entries.put(name, data);

            stream.close();
        } catch (Exception e) {
            throw new OdtFileException("Error adding '" + name + "' to odt file.", e);
        }
    }

    /**
     * Add a serialized resource to the ODT archive.
     * 
     * @param path
     *            path of the resource in the ODT
     * @param data
     *            the data blob
     * 
     * @throws OdtFileException
     *             when the resource cannot be added
     */
    public void addResource(String path, byte[] data) throws OdtFileException {
        this.entries.put(path, data);
    }

    /**
     * Add a resource to the ODT archive.
     * 
     * @param path
     *            path of the resource in the ODT
     * @param in
     *            the input stream to add
     * 
     * @throws OdtFileException
     *             when the resource cannot be added
     */
    public void addResource(String path, InputStream in) throws OdtFileException {

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            StreamUtil.copy(in, out);

            this.entries.put(path, out.toByteArray());

            out.close();
            in.close();
        } catch (Exception e) {
            throw new OdtFileException("Error adding '" + path + "' to odt file.", e);
        }
    }

    /**
     * Load the data of a given odt entry.
     * 
     * @param name
     *            name of the odt to load
     * 
     * @return the loaded input stream
     */
    private InputStream getInput(String name) {
        byte[] data = this.entries.get(name);
        ByteArrayInputStream input = new ByteArrayInputStream(data);

        return input;
    }
}
