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
package org.nabucco.framework.template.facade.message.datastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Name;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSExtension;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;

/**
 * ProduceDSRq<p/>Producer service request for datastructure<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-04
 */
public class ProduceDSRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "l0,255;u0,n;m0,1;" };

    public static final String EXTENSION = "extension";

    public static final String STRUCTURENAME = "structureName";

    /** Template structure extension to use for instanciation */
    public TemplateDSExtension extension;

    /** The name of the structure to be used */
    public Name structureName;

    /** Constructs a new ProduceDSRq instance. */
    public ProduceDSRq() {
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
        propertyMap.put(EXTENSION, PropertyDescriptorSupport.createDatatype(EXTENSION, TemplateDSExtension.class, 0,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPONENT));
        propertyMap.put(STRUCTURENAME,
                PropertyDescriptorSupport.createBasetype(STRUCTURENAME, Name.class, 1, PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(ProduceDSRq.getPropertyDescriptor(EXTENSION), this.getExtension()));
        properties.add(super.createProperty(ProduceDSRq.getPropertyDescriptor(STRUCTURENAME), this.structureName));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(EXTENSION) && (property.getType() == TemplateDSExtension.class))) {
            this.setExtension(((TemplateDSExtension) property.getInstance()));
            return true;
        } else if ((property.getName().equals(STRUCTURENAME) && (property.getType() == Name.class))) {
            this.setStructureName(((Name) property.getInstance()));
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
        final ProduceDSRq other = ((ProduceDSRq) obj);
        if ((this.extension == null)) {
            if ((other.extension != null))
                return false;
        } else if ((!this.extension.equals(other.extension)))
            return false;
        if ((this.structureName == null)) {
            if ((other.structureName != null))
                return false;
        } else if ((!this.structureName.equals(other.structureName)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.extension == null) ? 0 : this.extension.hashCode()));
        result = ((PRIME * result) + ((this.structureName == null) ? 0 : this.structureName.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * Template structure extension to use for instanciation
     *
     * @return the TemplateDSExtension.
     */
    public TemplateDSExtension getExtension() {
        return this.extension;
    }

    /**
     * Template structure extension to use for instanciation
     *
     * @param extension the TemplateDSExtension.
     */
    public void setExtension(TemplateDSExtension extension) {
        this.extension = extension;
    }

    /**
     * The name of the structure to be used
     *
     * @return the Name.
     */
    public Name getStructureName() {
        return this.structureName;
    }

    /**
     * The name of the structure to be used
     *
     * @param structureName the Name.
     */
    public void setStructureName(Name structureName) {
        this.structureName = structureName;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(ProduceDSRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(ProduceDSRq.class).getAllProperties();
    }
}
