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
package org.nabucco.framework.template.facade.message.documentconversion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Data;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.file.FileExtension;
import org.nabucco.framework.base.facade.datatype.file.FileName;
import org.nabucco.framework.base.facade.datatype.file.FileSize;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

/**
 * ConvertRq<p/>Request for document conversion via adapter<p/>
 *
 * @version 1.0
 * @author hlibrenz, PRODYNA AG, 2011-04-04
 */
public class ConvertRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;",
            "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l0,128;u0,n;m1,1;" };

    public static final String SOURCEEXTENSION = "sourceExtension";

    public static final String TARGETEXTENSION = "targetExtension";

    public static final String DATA = "data";

    public static final String PROVIDEDATA = "provideData";

    public static final String BYTESIZE = "byteSize";

    public static final String FILENAME = "fileName";

    /** The source file extension. */
    private FileExtension sourceExtension;

    /** The target file extension (base for Mime-type guessing) */
    private FileExtension targetExtension;

    /** Content as byte array */
    private Data data;

    /** If set, the content of the generated file is returned in the response message */
    private Flag provideData;

    /** The given size of the byte array (or better: the file size) */
    private FileSize byteSize;

    private FileName fileName;

    /** Constructs a new ConvertRq instance. */
    public ConvertRq() {
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
        propertyMap.put(SOURCEEXTENSION, PropertyDescriptorSupport.createBasetype(SOURCEEXTENSION, FileExtension.class,
                0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(TARGETEXTENSION, PropertyDescriptorSupport.createBasetype(TARGETEXTENSION, FileExtension.class,
                1, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(DATA,
                PropertyDescriptorSupport.createBasetype(DATA, Data.class, 2, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(PROVIDEDATA,
                PropertyDescriptorSupport.createBasetype(PROVIDEDATA, Flag.class, 3, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(BYTESIZE,
                PropertyDescriptorSupport.createBasetype(BYTESIZE, FileSize.class, 4, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(FILENAME,
                PropertyDescriptorSupport.createBasetype(FILENAME, FileName.class, 5, PROPERTY_CONSTRAINTS[5], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ConvertRq.getPropertyDescriptor(SOURCEEXTENSION), this.sourceExtension));
        properties.add(super.createProperty(ConvertRq.getPropertyDescriptor(TARGETEXTENSION), this.targetExtension));
        properties.add(super.createProperty(ConvertRq.getPropertyDescriptor(DATA), this.data));
        properties.add(super.createProperty(ConvertRq.getPropertyDescriptor(PROVIDEDATA), this.provideData));
        properties.add(super.createProperty(ConvertRq.getPropertyDescriptor(BYTESIZE), this.byteSize));
        properties.add(super.createProperty(ConvertRq.getPropertyDescriptor(FILENAME), this.fileName));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(SOURCEEXTENSION) && (property.getType() == FileExtension.class))) {
            this.setSourceExtension(((FileExtension) property.getInstance()));
            return true;
        } else if ((property.getName().equals(TARGETEXTENSION) && (property.getType() == FileExtension.class))) {
            this.setTargetExtension(((FileExtension) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DATA) && (property.getType() == Data.class))) {
            this.setData(((Data) property.getInstance()));
            return true;
        } else if ((property.getName().equals(PROVIDEDATA) && (property.getType() == Flag.class))) {
            this.setProvideData(((Flag) property.getInstance()));
            return true;
        } else if ((property.getName().equals(BYTESIZE) && (property.getType() == FileSize.class))) {
            this.setByteSize(((FileSize) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FILENAME) && (property.getType() == FileName.class))) {
            this.setFileName(((FileName) property.getInstance()));
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
        final ConvertRq other = ((ConvertRq) obj);
        if ((this.sourceExtension == null)) {
            if ((other.sourceExtension != null))
                return false;
        } else if ((!this.sourceExtension.equals(other.sourceExtension)))
            return false;
        if ((this.targetExtension == null)) {
            if ((other.targetExtension != null))
                return false;
        } else if ((!this.targetExtension.equals(other.targetExtension)))
            return false;
        if ((this.data == null)) {
            if ((other.data != null))
                return false;
        } else if ((!this.data.equals(other.data)))
            return false;
        if ((this.provideData == null)) {
            if ((other.provideData != null))
                return false;
        } else if ((!this.provideData.equals(other.provideData)))
            return false;
        if ((this.byteSize == null)) {
            if ((other.byteSize != null))
                return false;
        } else if ((!this.byteSize.equals(other.byteSize)))
            return false;
        if ((this.fileName == null)) {
            if ((other.fileName != null))
                return false;
        } else if ((!this.fileName.equals(other.fileName)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.sourceExtension == null) ? 0 : this.sourceExtension.hashCode()));
        result = ((PRIME * result) + ((this.targetExtension == null) ? 0 : this.targetExtension.hashCode()));
        result = ((PRIME * result) + ((this.data == null) ? 0 : this.data.hashCode()));
        result = ((PRIME * result) + ((this.provideData == null) ? 0 : this.provideData.hashCode()));
        result = ((PRIME * result) + ((this.byteSize == null) ? 0 : this.byteSize.hashCode()));
        result = ((PRIME * result) + ((this.fileName == null) ? 0 : this.fileName.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * The source file extension.
     *
     * @return the FileExtension.
     */
    public FileExtension getSourceExtension() {
        return this.sourceExtension;
    }

    /**
     * The source file extension.
     *
     * @param sourceExtension the FileExtension.
     */
    public void setSourceExtension(FileExtension sourceExtension) {
        this.sourceExtension = sourceExtension;
    }

    /**
     * The target file extension (base for Mime-type guessing)
     *
     * @return the FileExtension.
     */
    public FileExtension getTargetExtension() {
        return this.targetExtension;
    }

    /**
     * The target file extension (base for Mime-type guessing)
     *
     * @param targetExtension the FileExtension.
     */
    public void setTargetExtension(FileExtension targetExtension) {
        this.targetExtension = targetExtension;
    }

    /**
     * Content as byte array
     *
     * @return the Data.
     */
    public Data getData() {
        return this.data;
    }

    /**
     * Content as byte array
     *
     * @param data the Data.
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * If set, the content of the generated file is returned in the response message
     *
     * @return the Flag.
     */
    public Flag getProvideData() {
        return this.provideData;
    }

    /**
     * If set, the content of the generated file is returned in the response message
     *
     * @param provideData the Flag.
     */
    public void setProvideData(Flag provideData) {
        this.provideData = provideData;
    }

    /**
     * The given size of the byte array (or better: the file size)
     *
     * @return the FileSize.
     */
    public FileSize getByteSize() {
        return this.byteSize;
    }

    /**
     * The given size of the byte array (or better: the file size)
     *
     * @param byteSize the FileSize.
     */
    public void setByteSize(FileSize byteSize) {
        this.byteSize = byteSize;
    }

    /**
     * Missing description at method getFileName.
     *
     * @return the FileName.
     */
    public FileName getFileName() {
        return this.fileName;
    }

    /**
     * Missing description at method setFileName.
     *
     * @param fileName the FileName.
     */
    public void setFileName(FileName fileName) {
        this.fileName = fileName;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ConvertRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ConvertRq.class).getAllProperties();
    }
}
