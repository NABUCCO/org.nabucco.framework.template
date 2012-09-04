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

/**
 * DatastructureMergeRq<p/>Message transporting a datastructures to be merged<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-04
 */
public class DatastructureMergeRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,1;", "m0,n;" };

    public static final String TARGET = "target";

    public static final String SOURCEDATASTRUCTURELIST = "sourceDatastructureList";

    /** The target datastructure */
    private TemplateDatastructure target;

    /** The source datastructure list */
    private NabuccoList<TemplateDatastructure> sourceDatastructureList;

    /** Constructs a new DatastructureMergeRq instance. */
    public DatastructureMergeRq() {
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
        propertyMap.put(TARGET, PropertyDescriptorSupport.createDatatype(TARGET, TemplateDatastructure.class, 0,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(SOURCEDATASTRUCTURELIST, PropertyDescriptorSupport.createCollection(SOURCEDATASTRUCTURELIST,
                TemplateDatastructure.class, 1, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(DatastructureMergeRq.getPropertyDescriptor(TARGET), this.getTarget()));
        properties.add(super.createProperty(DatastructureMergeRq.getPropertyDescriptor(SOURCEDATASTRUCTURELIST),
                this.sourceDatastructureList));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(TARGET) && (property.getType() == TemplateDatastructure.class))) {
            this.setTarget(((TemplateDatastructure) property.getInstance()));
            return true;
        } else if ((property.getName().equals(SOURCEDATASTRUCTURELIST) && (property.getType() == TemplateDatastructure.class))) {
            this.sourceDatastructureList = ((NabuccoList<TemplateDatastructure>) property.getInstance());
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
        final DatastructureMergeRq other = ((DatastructureMergeRq) obj);
        if ((this.target == null)) {
            if ((other.target != null))
                return false;
        } else if ((!this.target.equals(other.target)))
            return false;
        if ((this.sourceDatastructureList == null)) {
            if ((other.sourceDatastructureList != null))
                return false;
        } else if ((!this.sourceDatastructureList.equals(other.sourceDatastructureList)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.target == null) ? 0 : this.target.hashCode()));
        result = ((PRIME * result) + ((this.sourceDatastructureList == null) ? 0 : this.sourceDatastructureList
                .hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * The target datastructure
     *
     * @return the TemplateDatastructure.
     */
    public TemplateDatastructure getTarget() {
        return this.target;
    }

    /**
     * The target datastructure
     *
     * @param target the TemplateDatastructure.
     */
    public void setTarget(TemplateDatastructure target) {
        this.target = target;
    }

    /**
     * The source datastructure list
     *
     * @return the NabuccoList<TemplateDatastructure>.
     */
    public NabuccoList<TemplateDatastructure> getSourceDatastructureList() {
        if ((this.sourceDatastructureList == null)) {
            this.sourceDatastructureList = new NabuccoListImpl<TemplateDatastructure>(
                    NabuccoCollectionState.INITIALIZED);
        }
        return this.sourceDatastructureList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DatastructureMergeRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DatastructureMergeRq.class).getAllProperties();
    }
}
