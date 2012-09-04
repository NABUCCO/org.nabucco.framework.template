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
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.Path;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * TemplateDatastructureReferenceItem<p/>Key reference in the current datastructure<p/>
 *
 * @version 1.0
 * @author Leonid Agranosvkiy, PRODYNA AG, 2012-07-24
 */
public class TemplateDatastructureReferenceItem extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,255;u0,n;m1,1;", "l0,n;u0,n;m1,1;" };

    public static final String KEYNAME = "keyName";

    public static final String KEYPATH = "keyPath";

    /** The key name */
    private Name keyName;

    /** The key path in the datastructure */
    private Path keyPath;

    /** Constructs a new TemplateDatastructureReferenceItem instance. */
    public TemplateDatastructureReferenceItem() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TemplateDatastructureReferenceItem.
     */
    protected void cloneObject(TemplateDatastructureReferenceItem clone) {
        super.cloneObject(clone);
        if ((this.getKeyName() != null)) {
            clone.setKeyName(this.getKeyName().cloneObject());
        }
        if ((this.getKeyPath() != null)) {
            clone.setKeyPath(this.getKeyPath().cloneObject());
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
        propertyMap.put(KEYNAME,
                PropertyDescriptorSupport.createBasetype(KEYNAME, Name.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(KEYPATH,
                PropertyDescriptorSupport.createBasetype(KEYPATH, Path.class, 4, PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TemplateDatastructureReferenceItem.getPropertyDescriptor(KEYNAME),
                this.keyName, null));
        properties.add(super.createProperty(TemplateDatastructureReferenceItem.getPropertyDescriptor(KEYPATH),
                this.keyPath, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(KEYNAME) && (property.getType() == Name.class))) {
            this.setKeyName(((Name) property.getInstance()));
            return true;
        } else if ((property.getName().equals(KEYPATH) && (property.getType() == Path.class))) {
            this.setKeyPath(((Path) property.getInstance()));
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
        final TemplateDatastructureReferenceItem other = ((TemplateDatastructureReferenceItem) obj);
        if ((this.keyName == null)) {
            if ((other.keyName != null))
                return false;
        } else if ((!this.keyName.equals(other.keyName)))
            return false;
        if ((this.keyPath == null)) {
            if ((other.keyPath != null))
                return false;
        } else if ((!this.keyPath.equals(other.keyPath)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.keyName == null) ? 0 : this.keyName.hashCode()));
        result = ((PRIME * result) + ((this.keyPath == null) ? 0 : this.keyPath.hashCode()));
        return result;
    }

    @Override
    public TemplateDatastructureReferenceItem cloneObject() {
        TemplateDatastructureReferenceItem clone = new TemplateDatastructureReferenceItem();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The key name
     *
     * @return the Name.
     */
    public Name getKeyName() {
        return this.keyName;
    }

    /**
     * The key name
     *
     * @param keyName the Name.
     */
    public void setKeyName(Name keyName) {
        this.keyName = keyName;
    }

    /**
     * The key name
     *
     * @param keyName the String.
     */
    public void setKeyName(String keyName) {
        if ((this.keyName == null)) {
            if ((keyName == null)) {
                return;
            }
            this.keyName = new Name();
        }
        this.keyName.setValue(keyName);
    }

    /**
     * The key path in the datastructure
     *
     * @return the Path.
     */
    public Path getKeyPath() {
        return this.keyPath;
    }

    /**
     * The key path in the datastructure
     *
     * @param keyPath the Path.
     */
    public void setKeyPath(Path keyPath) {
        this.keyPath = keyPath;
    }

    /**
     * The key path in the datastructure
     *
     * @param keyPath the String.
     */
    public void setKeyPath(String keyPath) {
        if ((this.keyPath == null)) {
            if ((keyPath == null)) {
                return;
            }
            this.keyPath = new Path();
        }
        this.keyPath.setValue(keyPath);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TemplateDatastructureReferenceItem.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TemplateDatastructureReferenceItem.class).getAllProperties();
    }
}
