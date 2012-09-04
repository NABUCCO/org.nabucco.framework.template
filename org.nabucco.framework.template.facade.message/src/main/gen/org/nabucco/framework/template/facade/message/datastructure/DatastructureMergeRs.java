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
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.framework.template.facade.datatype.TemplateDatastructure;
import org.nabucco.framework.template.facade.datatype.datastructure.KeyValuePair;

/**
 * DatastructureMergeRs<p/>Message transporting a datastructure service responce for datastructure<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-04
 */
public class DatastructureMergeRs extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "m0,n;" };

    public static final String DATASTRUCTURE = "datastructure";

    public static final String ADDITIONALKEYS = "additionalKeys";

    /** The instance of the template datatstructure */
    private TemplateDatastructure datastructure;

    private NabuccoList<KeyValuePair> additionalKeys;

    /** Constructs a new DatastructureMergeRs instance. */
    public DatastructureMergeRs() {
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
        propertyMap.put(DATASTRUCTURE, PropertyDescriptorSupport.createDatatype(DATASTRUCTURE,
                TemplateDatastructure.class, 0, PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(ADDITIONALKEYS, PropertyDescriptorSupport.createCollection(ADDITIONALKEYS, KeyValuePair.class,
                1, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(DatastructureMergeRs.getPropertyDescriptor(DATASTRUCTURE),
                this.getDatastructure()));
        properties.add(super.createProperty(DatastructureMergeRs.getPropertyDescriptor(ADDITIONALKEYS),
                this.additionalKeys));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(DATASTRUCTURE) && (property.getType() == TemplateDatastructure.class))) {
            this.setDatastructure(((TemplateDatastructure) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ADDITIONALKEYS) && (property.getType() == KeyValuePair.class))) {
            this.additionalKeys = ((NabuccoList<KeyValuePair>) property.getInstance());
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
        final DatastructureMergeRs other = ((DatastructureMergeRs) obj);
        if ((this.datastructure == null)) {
            if ((other.datastructure != null))
                return false;
        } else if ((!this.datastructure.equals(other.datastructure)))
            return false;
        if ((this.additionalKeys == null)) {
            if ((other.additionalKeys != null))
                return false;
        } else if ((!this.additionalKeys.equals(other.additionalKeys)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.datastructure == null) ? 0 : this.datastructure.hashCode()));
        result = ((PRIME * result) + ((this.additionalKeys == null) ? 0 : this.additionalKeys.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * The instance of the template datatstructure
     *
     * @return the TemplateDatastructure.
     */
    public TemplateDatastructure getDatastructure() {
        return this.datastructure;
    }

    /**
     * The instance of the template datatstructure
     *
     * @param datastructure the TemplateDatastructure.
     */
    public void setDatastructure(TemplateDatastructure datastructure) {
        this.datastructure = datastructure;
    }

    /**
     * Missing description at method getAdditionalKeys.
     *
     * @return the NabuccoList<KeyValuePair>.
     */
    public NabuccoList<KeyValuePair> getAdditionalKeys() {
        if ((this.additionalKeys == null)) {
            this.additionalKeys = new NabuccoListImpl<KeyValuePair>(NabuccoCollectionState.INITIALIZED);
        }
        return this.additionalKeys;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DatastructureMergeRs.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DatastructureMergeRs.class).getAllProperties();
    }
}
