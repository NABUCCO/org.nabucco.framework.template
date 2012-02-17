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
package org.nabucco.framework.template.facade.datatype.mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.DatatypeSupport;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Path;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * TemplateContent<p/>Variable in a template.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-10
 */
public class TemplateContent extends DatatypeSupport implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;" };

    public static final String NAME = "name";

    public static final String CONTENTID = "contentId";

    public static final String ARCHIVEPATH = "archivePath";

    /** Name of the content file. */
    private Name name;

    /** ID of the content element. */
    private Identifier contentId;

    /** Path in the target archive. */
    private Path archivePath;

    /** Constructs a new TemplateContent instance. */
    public TemplateContent() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TemplateContent.
     */
    protected void cloneObject(TemplateContent clone) {
        super.cloneObject(clone);
        if ((this.getName() != null)) {
            clone.setName(this.getName().cloneObject());
        }
        if ((this.getContentId() != null)) {
            clone.setContentId(this.getContentId().cloneObject());
        }
        if ((this.getArchivePath() != null)) {
            clone.setArchivePath(this.getArchivePath().cloneObject());
        }
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.put(NAME,
                PropertyDescriptorSupport.createBasetype(NAME, Name.class, 0, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(CONTENTID, PropertyDescriptorSupport.createBasetype(CONTENTID, Identifier.class, 1,
                PROPERTY_CONSTRAINTS[1], false));
        propertyMap.put(ARCHIVEPATH,
                PropertyDescriptorSupport.createBasetype(ARCHIVEPATH, Path.class, 2, PROPERTY_CONSTRAINTS[2], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TemplateContent.getPropertyDescriptor(NAME), this.name, null));
        properties.add(super.createProperty(TemplateContent.getPropertyDescriptor(CONTENTID), this.contentId, null));
        properties
                .add(super.createProperty(TemplateContent.getPropertyDescriptor(ARCHIVEPATH), this.archivePath, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(NAME) && (property.getType() == Name.class))) {
            this.setName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTENTID) && (property.getType() == Identifier.class))) {
            this.setContentId(((Identifier) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ARCHIVEPATH) && (property.getType() == Path.class))) {
            this.setArchivePath(((Path) property.getInstance()));
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
        final TemplateContent other = ((TemplateContent) obj);
        if ((this.name == null)) {
            if ((other.name != null))
                return false;
        } else if ((!this.name.equals(other.name)))
            return false;
        if ((this.contentId == null)) {
            if ((other.contentId != null))
                return false;
        } else if ((!this.contentId.equals(other.contentId)))
            return false;
        if ((this.archivePath == null)) {
            if ((other.archivePath != null))
                return false;
        } else if ((!this.archivePath.equals(other.archivePath)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.name == null) ? 0 : this.name.hashCode()));
        result = ((PRIME * result) + ((this.contentId == null) ? 0 : this.contentId.hashCode()));
        result = ((PRIME * result) + ((this.archivePath == null) ? 0 : this.archivePath.hashCode()));
        return result;
    }

    @Override
    public TemplateContent cloneObject() {
        TemplateContent clone = new TemplateContent();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Name of the content file.
     *
     * @return the Name.
     */
    public Name getName() {
        return this.name;
    }

    /**
     * Name of the content file.
     *
     * @param name the Name.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Name of the content file.
     *
     * @param name the String.
     */
    public void setName(String name) {
        if ((this.name == null)) {
            if ((name == null)) {
                return;
            }
            this.name = new Name();
        }
        this.name.setValue(name);
    }

    /**
     * ID of the content element.
     *
     * @return the Identifier.
     */
    public Identifier getContentId() {
        return this.contentId;
    }

    /**
     * ID of the content element.
     *
     * @param contentId the Identifier.
     */
    public void setContentId(Identifier contentId) {
        this.contentId = contentId;
    }

    /**
     * ID of the content element.
     *
     * @param contentId the Long.
     */
    public void setContentId(Long contentId) {
        if ((this.contentId == null)) {
            if ((contentId == null)) {
                return;
            }
            this.contentId = new Identifier();
        }
        this.contentId.setValue(contentId);
    }

    /**
     * Path in the target archive.
     *
     * @return the Path.
     */
    public Path getArchivePath() {
        return this.archivePath;
    }

    /**
     * Path in the target archive.
     *
     * @param archivePath the Path.
     */
    public void setArchivePath(Path archivePath) {
        this.archivePath = archivePath;
    }

    /**
     * Path in the target archive.
     *
     * @param archivePath the String.
     */
    public void setArchivePath(String archivePath) {
        if ((this.archivePath == null)) {
            if ((archivePath == null)) {
                return;
            }
            this.archivePath = new Path();
        }
        this.archivePath.setValue(archivePath);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TemplateContent.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TemplateContent.class).getAllProperties();
    }
}
