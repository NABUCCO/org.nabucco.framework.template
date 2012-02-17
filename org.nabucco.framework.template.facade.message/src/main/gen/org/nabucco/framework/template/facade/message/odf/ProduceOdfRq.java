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
package org.nabucco.framework.template.facade.message.odf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.file.FileName;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.framework.template.facade.datatype.TemplateData;
import org.nabucco.framework.template.facade.datatype.dictionary.DocumentValueDictionaryRoot;
import org.nabucco.framework.template.facade.datatype.odf.OpenDocumentType;

/**
 * ProduceOdfRq<p/>Requests the creation of an ODF file, based on information inside<p/>
 *
 * @version 1.3
 * @author Holger Librenz, PRODYNA AG, 2011-06-15
 */
public class ProduceOdfRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "l0,128;u0,n;m1,1;", "m1,1;", "m1,1;" };

    public static final String INPUTFILEDATA = "inputFileData";

    public static final String OUTPUTFILENAME = "outputFileName";

    public static final String OUTPUTTYPE = "outputType";

    public static final String DICT = "dict";

    /** Provides the OOo template to use */
    private TemplateData inputFileData;

    /** Name out output file. */
    private FileName outputFileName;

    /** OOo file type of output file */
    private OpenDocumentType outputType;

    /** The information, the ODF will contain */
    private DocumentValueDictionaryRoot dict;

    /** Constructs a new ProduceOdfRq instance. */
    public ProduceOdfRq() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(INPUTFILEDATA, PropertyDescriptorSupport.createDatatype(INPUTFILEDATA, TemplateData.class, 0,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(OUTPUTFILENAME, PropertyDescriptorSupport.createBasetype(OUTPUTFILENAME, FileName.class, 1,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(OUTPUTTYPE, PropertyDescriptorSupport.createEnumeration(OUTPUTTYPE, OpenDocumentType.class, 2,
                PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(DICT, PropertyDescriptorSupport.createDatatype(DICT, DocumentValueDictionaryRoot.class, 3,
                PROPERTY_CONSTRAINTS[3], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties
                .add(super.createProperty(ProduceOdfRq.getPropertyDescriptor(INPUTFILEDATA), this.getInputFileData()));
        properties.add(super.createProperty(ProduceOdfRq.getPropertyDescriptor(OUTPUTFILENAME), this.outputFileName));
        properties.add(super.createProperty(ProduceOdfRq.getPropertyDescriptor(OUTPUTTYPE), this.getOutputType()));
        properties.add(super.createProperty(ProduceOdfRq.getPropertyDescriptor(DICT), this.getDict()));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(INPUTFILEDATA) && (property.getType() == TemplateData.class))) {
            this.setInputFileData(((TemplateData) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OUTPUTFILENAME) && (property.getType() == FileName.class))) {
            this.setOutputFileName(((FileName) property.getInstance()));
            return true;
        } else if ((property.getName().equals(OUTPUTTYPE) && (property.getType() == OpenDocumentType.class))) {
            this.setOutputType(((OpenDocumentType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DICT) && (property.getType() == DocumentValueDictionaryRoot.class))) {
            this.setDict(((DocumentValueDictionaryRoot) property.getInstance()));
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if ((this == obj)) {
            return true;
        }
        if ((obj == null)) {
            return false;
        }
        if ((this.getClass() != obj.getClass())) {
            return false;
        }
        if ((!super.equals(obj))) {
            return false;
        }
        final ProduceOdfRq other = ((ProduceOdfRq) obj);
        if ((this.inputFileData == null)) {
            if ((other.inputFileData != null))
                return false;
        } else if ((!this.inputFileData.equals(other.inputFileData)))
            return false;
        if ((this.outputFileName == null)) {
            if ((other.outputFileName != null))
                return false;
        } else if ((!this.outputFileName.equals(other.outputFileName)))
            return false;
        if ((this.outputType == null)) {
            if ((other.outputType != null))
                return false;
        } else if ((!this.outputType.equals(other.outputType)))
            return false;
        if ((this.dict == null)) {
            if ((other.dict != null))
                return false;
        } else if ((!this.dict.equals(other.dict)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.inputFileData == null) ? 0 : this.inputFileData.hashCode()));
        result = ((PRIME * result) + ((this.outputFileName == null) ? 0 : this.outputFileName.hashCode()));
        result = ((PRIME * result) + ((this.outputType == null) ? 0 : this.outputType.hashCode()));
        result = ((PRIME * result) + ((this.dict == null) ? 0 : this.dict.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Provides the OOo template to use
     *
     * @return the TemplateData.
     */
    public TemplateData getInputFileData() {
        return this.inputFileData;
    }

    /**
     * Provides the OOo template to use
     *
     * @param inputFileData the TemplateData.
     */
    public void setInputFileData(TemplateData inputFileData) {
        this.inputFileData = inputFileData;
    }

    /**
     * Name out output file.
     *
     * @return the FileName.
     */
    public FileName getOutputFileName() {
        return this.outputFileName;
    }

    /**
     * Name out output file.
     *
     * @param outputFileName the FileName.
     */
    public void setOutputFileName(FileName outputFileName) {
        this.outputFileName = outputFileName;
    }

    /**
     * OOo file type of output file
     *
     * @return the OpenDocumentType.
     */
    public OpenDocumentType getOutputType() {
        return this.outputType;
    }

    /**
     * OOo file type of output file
     *
     * @param outputType the OpenDocumentType.
     */
    public void setOutputType(OpenDocumentType outputType) {
        this.outputType = outputType;
    }

    /**
     * The information, the ODF will contain
     *
     * @return the DocumentValueDictionaryRoot.
     */
    public DocumentValueDictionaryRoot getDict() {
        return this.dict;
    }

    /**
     * The information, the ODF will contain
     *
     * @param dict the DocumentValueDictionaryRoot.
     */
    public void setDict(DocumentValueDictionaryRoot dict) {
        this.dict = dict;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ProduceOdfRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ProduceOdfRq.class).getAllProperties();
    }
}
