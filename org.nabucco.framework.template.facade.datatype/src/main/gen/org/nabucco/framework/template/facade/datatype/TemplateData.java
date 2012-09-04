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
package org.nabucco.framework.template.facade.datatype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Data;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Number;
import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.file.FileExtension;
import org.nabucco.framework.base.facade.datatype.file.FileName;
import org.nabucco.framework.base.facade.datatype.file.MimeType;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.security.UserId;

/**
 * TemplateData<p/>Contains content as byte array and some optional meta data<p/>
 *
 * @version 1.0
 * @author Holger Librenz, PRODYNA AG, 2011-06-15
 */
public class TemplateData extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l3,32;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;",
            "l0,n;u0,n;m1,1;", "l0,128;u0,n;m0,1;", "l0,128;u0,n;m0,1;", "l0,n;u0,n;m0,1;" };

    public static final String CREATEDUSERID = "createdUserId";

    public static final String CREATEDTIMESTAMP = "createdTimestamp";

    public static final String DATA = "data";

    public static final String BYTESIZE = "byteSize";

    public static final String MIMETYPE = "mimeType";

    public static final String FILENAME = "fileName";

    public static final String FILEEXTENSION = "fileExtension";

    private UserId createdUserId;

    private Timestamp createdTimestamp;

    private Data data;

    private Number byteSize;

    private MimeType mimeType;

    private FileName fileName;

    private FileExtension fileExtension;

    /** Constructs a new TemplateData instance. */
    public TemplateData() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TemplateData.
     */
    protected void cloneObject(TemplateData clone) {
        super.cloneObject(clone);
        if ((this.getCreatedUserId() != null)) {
            clone.setCreatedUserId(this.getCreatedUserId().cloneObject());
        }
        if ((this.getCreatedTimestamp() != null)) {
            clone.setCreatedTimestamp(this.getCreatedTimestamp().cloneObject());
        }
        if ((this.getData() != null)) {
            clone.setData(this.getData().cloneObject());
        }
        if ((this.getByteSize() != null)) {
            clone.setByteSize(this.getByteSize().cloneObject());
        }
        if ((this.getMimeType() != null)) {
            clone.setMimeType(this.getMimeType().cloneObject());
        }
        if ((this.getFileName() != null)) {
            clone.setFileName(this.getFileName().cloneObject());
        }
        if ((this.getFileExtension() != null)) {
            clone.setFileExtension(this.getFileExtension().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(CREATEDUSERID, PropertyDescriptorSupport.createBasetype(CREATEDUSERID, UserId.class, 3,
                PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(CREATEDTIMESTAMP, PropertyDescriptorSupport.createBasetype(CREATEDTIMESTAMP, Timestamp.class,
                4, PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(DATA,
                PropertyDescriptorSupport.createBasetype(DATA, Data.class, 5, PROPERTY_CONSTRAINTS[2], false));
        propertyMap.put(BYTESIZE,
                PropertyDescriptorSupport.createBasetype(BYTESIZE, Number.class, 6, PROPERTY_CONSTRAINTS[3], false));
        propertyMap.put(MIMETYPE,
                PropertyDescriptorSupport.createBasetype(MIMETYPE, MimeType.class, 7, PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(FILENAME,
                PropertyDescriptorSupport.createBasetype(FILENAME, FileName.class, 8, PROPERTY_CONSTRAINTS[5], false));
        propertyMap.put(FILEEXTENSION, PropertyDescriptorSupport.createBasetype(FILEEXTENSION, FileExtension.class, 9,
                PROPERTY_CONSTRAINTS[6], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties
                .add(super.createProperty(TemplateData.getPropertyDescriptor(CREATEDUSERID), this.createdUserId, null));
        properties.add(super.createProperty(TemplateData.getPropertyDescriptor(CREATEDTIMESTAMP),
                this.createdTimestamp, null));
        properties.add(super.createProperty(TemplateData.getPropertyDescriptor(DATA), this.data, null));
        properties.add(super.createProperty(TemplateData.getPropertyDescriptor(BYTESIZE), this.byteSize, null));
        properties.add(super.createProperty(TemplateData.getPropertyDescriptor(MIMETYPE), this.mimeType, null));
        properties.add(super.createProperty(TemplateData.getPropertyDescriptor(FILENAME), this.fileName, null));
        properties
                .add(super.createProperty(TemplateData.getPropertyDescriptor(FILEEXTENSION), this.fileExtension, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(CREATEDUSERID) && (property.getType() == UserId.class))) {
            this.setCreatedUserId(((UserId) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CREATEDTIMESTAMP) && (property.getType() == Timestamp.class))) {
            this.setCreatedTimestamp(((Timestamp) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DATA) && (property.getType() == Data.class))) {
            this.setData(((Data) property.getInstance()));
            return true;
        } else if ((property.getName().equals(BYTESIZE) && (property.getType() == Number.class))) {
            this.setByteSize(((Number) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MIMETYPE) && (property.getType() == MimeType.class))) {
            this.setMimeType(((MimeType) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FILENAME) && (property.getType() == FileName.class))) {
            this.setFileName(((FileName) property.getInstance()));
            return true;
        } else if ((property.getName().equals(FILEEXTENSION) && (property.getType() == FileExtension.class))) {
            this.setFileExtension(((FileExtension) property.getInstance()));
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
        final TemplateData other = ((TemplateData) obj);
        if ((this.createdUserId == null)) {
            if ((other.createdUserId != null))
                return false;
        } else if ((!this.createdUserId.equals(other.createdUserId)))
            return false;
        if ((this.createdTimestamp == null)) {
            if ((other.createdTimestamp != null))
                return false;
        } else if ((!this.createdTimestamp.equals(other.createdTimestamp)))
            return false;
        if ((this.data == null)) {
            if ((other.data != null))
                return false;
        } else if ((!this.data.equals(other.data)))
            return false;
        if ((this.byteSize == null)) {
            if ((other.byteSize != null))
                return false;
        } else if ((!this.byteSize.equals(other.byteSize)))
            return false;
        if ((this.mimeType == null)) {
            if ((other.mimeType != null))
                return false;
        } else if ((!this.mimeType.equals(other.mimeType)))
            return false;
        if ((this.fileName == null)) {
            if ((other.fileName != null))
                return false;
        } else if ((!this.fileName.equals(other.fileName)))
            return false;
        if ((this.fileExtension == null)) {
            if ((other.fileExtension != null))
                return false;
        } else if ((!this.fileExtension.equals(other.fileExtension)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.createdUserId == null) ? 0 : this.createdUserId.hashCode()));
        result = ((PRIME * result) + ((this.createdTimestamp == null) ? 0 : this.createdTimestamp.hashCode()));
        result = ((PRIME * result) + ((this.data == null) ? 0 : this.data.hashCode()));
        result = ((PRIME * result) + ((this.byteSize == null) ? 0 : this.byteSize.hashCode()));
        result = ((PRIME * result) + ((this.mimeType == null) ? 0 : this.mimeType.hashCode()));
        result = ((PRIME * result) + ((this.fileName == null) ? 0 : this.fileName.hashCode()));
        result = ((PRIME * result) + ((this.fileExtension == null) ? 0 : this.fileExtension.hashCode()));
        return result;
    }

    @Override
    public TemplateData cloneObject() {
        TemplateData clone = new TemplateData();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getCreatedUserId.
     *
     * @return the UserId.
     */
    public UserId getCreatedUserId() {
        return this.createdUserId;
    }

    /**
     * Missing description at method setCreatedUserId.
     *
     * @param createdUserId the UserId.
     */
    public void setCreatedUserId(UserId createdUserId) {
        this.createdUserId = createdUserId;
    }

    /**
     * Missing description at method setCreatedUserId.
     *
     * @param createdUserId the String.
     */
    public void setCreatedUserId(String createdUserId) {
        if ((this.createdUserId == null)) {
            if ((createdUserId == null)) {
                return;
            }
            this.createdUserId = new UserId();
        }
        this.createdUserId.setValue(createdUserId);
    }

    /**
     * Missing description at method getCreatedTimestamp.
     *
     * @return the Timestamp.
     */
    public Timestamp getCreatedTimestamp() {
        return this.createdTimestamp;
    }

    /**
     * Missing description at method setCreatedTimestamp.
     *
     * @param createdTimestamp the Timestamp.
     */
    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    /**
     * Missing description at method setCreatedTimestamp.
     *
     * @param createdTimestamp the Long.
     */
    public void setCreatedTimestamp(Long createdTimestamp) {
        if ((this.createdTimestamp == null)) {
            if ((createdTimestamp == null)) {
                return;
            }
            this.createdTimestamp = new Timestamp();
        }
        this.createdTimestamp.setValue(createdTimestamp);
    }

    /**
     * Missing description at method getData.
     *
     * @return the Data.
     */
    public Data getData() {
        return this.data;
    }

    /**
     * Missing description at method setData.
     *
     * @param data the Data.
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * Missing description at method setData.
     *
     * @param data the byte[].
     */
    public void setData(byte[] data) {
        if ((this.data == null)) {
            if ((data == null)) {
                return;
            }
            this.data = new Data();
        }
        this.data.setValue(data);
    }

    /**
     * Missing description at method getByteSize.
     *
     * @return the Number.
     */
    public Number getByteSize() {
        return this.byteSize;
    }

    /**
     * Missing description at method setByteSize.
     *
     * @param byteSize the Number.
     */
    public void setByteSize(Number byteSize) {
        this.byteSize = byteSize;
    }

    /**
     * Missing description at method setByteSize.
     *
     * @param byteSize the Integer.
     */
    public void setByteSize(Integer byteSize) {
        if ((this.byteSize == null)) {
            if ((byteSize == null)) {
                return;
            }
            this.byteSize = new Number();
        }
        this.byteSize.setValue(byteSize);
    }

    /**
     * Missing description at method getMimeType.
     *
     * @return the MimeType.
     */
    public MimeType getMimeType() {
        return this.mimeType;
    }

    /**
     * Missing description at method setMimeType.
     *
     * @param mimeType the MimeType.
     */
    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * Missing description at method setMimeType.
     *
     * @param mimeType the String.
     */
    public void setMimeType(String mimeType) {
        if ((this.mimeType == null)) {
            if ((mimeType == null)) {
                return;
            }
            this.mimeType = new MimeType();
        }
        this.mimeType.setValue(mimeType);
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
     * Missing description at method setFileName.
     *
     * @param fileName the String.
     */
    public void setFileName(String fileName) {
        if ((this.fileName == null)) {
            if ((fileName == null)) {
                return;
            }
            this.fileName = new FileName();
        }
        this.fileName.setValue(fileName);
    }

    /**
     * Missing description at method getFileExtension.
     *
     * @return the FileExtension.
     */
    public FileExtension getFileExtension() {
        return this.fileExtension;
    }

    /**
     * Missing description at method setFileExtension.
     *
     * @param fileExtension the FileExtension.
     */
    public void setFileExtension(FileExtension fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * Missing description at method setFileExtension.
     *
     * @param fileExtension the String.
     */
    public void setFileExtension(String fileExtension) {
        if ((this.fileExtension == null)) {
            if ((fileExtension == null)) {
                return;
            }
            this.fileExtension = new FileExtension();
        }
        this.fileExtension.setValue(fileExtension);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TemplateData.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TemplateData.class).getAllProperties();
    }
}
