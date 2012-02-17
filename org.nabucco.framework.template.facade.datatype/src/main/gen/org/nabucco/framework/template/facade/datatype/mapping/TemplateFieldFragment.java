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
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.template.facade.datatype.mapping.TemplateField;
import org.nabucco.framework.template.facade.datatype.mapping.TemplateFieldSet;

/**
 * TemplateFieldFragment<p/>Template Fragment.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-10
 */
public class TemplateFieldFragment extends TemplateField implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String FIELDSETS = "fieldSets";

    /** Subset of fields. */
    private NabuccoList<TemplateFieldSet> fieldSets;

    /** Constructs a new TemplateFieldFragment instance. */
    public TemplateFieldFragment() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TemplateFieldFragment.
     */
    protected void cloneObject(TemplateFieldFragment clone) {
        super.cloneObject(clone);
        if ((this.fieldSets != null)) {
            clone.fieldSets = this.fieldSets.cloneCollection();
        }
    }

    /**
     * Getter for the FieldSetsJPA.
     *
     * @return the List<TemplateFieldSet>.
     */
    List<TemplateFieldSet> getFieldSetsJPA() {
        if ((this.fieldSets == null)) {
            this.fieldSets = new NabuccoListImpl<TemplateFieldSet>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<TemplateFieldSet>) this.fieldSets).getDelegate();
    }

    /**
     * Setter for the FieldSetsJPA.
     *
     * @param fieldSets the List<TemplateFieldSet>.
     */
    void setFieldSetsJPA(List<TemplateFieldSet> fieldSets) {
        if ((this.fieldSets == null)) {
            this.fieldSets = new NabuccoListImpl<TemplateFieldSet>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<TemplateFieldSet>) this.fieldSets).setDelegate(fieldSets);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(TemplateField.class).getPropertyMap());
        propertyMap.put(FIELDSETS, PropertyDescriptorSupport.createCollection(FIELDSETS, TemplateFieldSet.class, 1,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TemplateFieldFragment.getPropertyDescriptor(FIELDSETS), this.fieldSets,
                null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(FIELDSETS) && (property.getType() == TemplateFieldSet.class))) {
            this.fieldSets = ((NabuccoList<TemplateFieldSet>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public TemplateFieldFragment cloneObject() {
        TemplateFieldFragment clone = new TemplateFieldFragment();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Subset of fields.
     *
     * @return the NabuccoList<TemplateFieldSet>.
     */
    public NabuccoList<TemplateFieldSet> getFieldSets() {
        if ((this.fieldSets == null)) {
            this.fieldSets = new NabuccoListImpl<TemplateFieldSet>(NabuccoCollectionState.INITIALIZED);
        }
        return this.fieldSets;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TemplateFieldFragment.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TemplateFieldFragment.class).getAllProperties();
    }
}
