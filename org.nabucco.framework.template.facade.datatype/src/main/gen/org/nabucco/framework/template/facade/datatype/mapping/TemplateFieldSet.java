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
import org.nabucco.framework.template.facade.datatype.mapping.TemplateFieldFragment;
import org.nabucco.framework.template.facade.datatype.mapping.TemplateFieldValue;

/**
 * TemplateFieldSet<p/>Set of template fields.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-12-10
 */
public class TemplateFieldSet extends TemplateField implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;", "m0,n;" };

    public static final String VALUES = "values";

    public static final String FRAGMENTS = "fragments";

    /** The child field values. */
    private NabuccoList<TemplateFieldValue> values;

    /** Fragments of this field set. */
    private NabuccoList<TemplateFieldFragment> fragments;

    /** Constructs a new TemplateFieldSet instance. */
    public TemplateFieldSet() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TemplateFieldSet.
     */
    protected void cloneObject(TemplateFieldSet clone) {
        super.cloneObject(clone);
        if ((this.values != null)) {
            clone.values = this.values.cloneCollection();
        }
        if ((this.fragments != null)) {
            clone.fragments = this.fragments.cloneCollection();
        }
    }

    /**
     * Getter for the ValuesJPA.
     *
     * @return the List<TemplateFieldValue>.
     */
    List<TemplateFieldValue> getValuesJPA() {
        if ((this.values == null)) {
            this.values = new NabuccoListImpl<TemplateFieldValue>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<TemplateFieldValue>) this.values).getDelegate();
    }

    /**
     * Setter for the ValuesJPA.
     *
     * @param values the List<TemplateFieldValue>.
     */
    void setValuesJPA(List<TemplateFieldValue> values) {
        if ((this.values == null)) {
            this.values = new NabuccoListImpl<TemplateFieldValue>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<TemplateFieldValue>) this.values).setDelegate(values);
    }

    /**
     * Getter for the FragmentsJPA.
     *
     * @return the List<TemplateFieldFragment>.
     */
    List<TemplateFieldFragment> getFragmentsJPA() {
        if ((this.fragments == null)) {
            this.fragments = new NabuccoListImpl<TemplateFieldFragment>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<TemplateFieldFragment>) this.fragments).getDelegate();
    }

    /**
     * Setter for the FragmentsJPA.
     *
     * @param fragments the List<TemplateFieldFragment>.
     */
    void setFragmentsJPA(List<TemplateFieldFragment> fragments) {
        if ((this.fragments == null)) {
            this.fragments = new NabuccoListImpl<TemplateFieldFragment>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<TemplateFieldFragment>) this.fragments).setDelegate(fragments);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(TemplateField.class).getPropertyMap());
        propertyMap.put(VALUES, PropertyDescriptorSupport.createCollection(VALUES, TemplateFieldValue.class, 1,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(FRAGMENTS, PropertyDescriptorSupport.createCollection(FRAGMENTS, TemplateFieldFragment.class,
                2, PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TemplateFieldSet.getPropertyDescriptor(VALUES), this.values, null));
        properties.add(super.createProperty(TemplateFieldSet.getPropertyDescriptor(FRAGMENTS), this.fragments, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(VALUES) && (property.getType() == TemplateFieldValue.class))) {
            this.values = ((NabuccoList<TemplateFieldValue>) property.getInstance());
            return true;
        } else if ((property.getName().equals(FRAGMENTS) && (property.getType() == TemplateFieldFragment.class))) {
            this.fragments = ((NabuccoList<TemplateFieldFragment>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public TemplateFieldSet cloneObject() {
        TemplateFieldSet clone = new TemplateFieldSet();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The child field values.
     *
     * @return the NabuccoList<TemplateFieldValue>.
     */
    public NabuccoList<TemplateFieldValue> getValues() {
        if ((this.values == null)) {
            this.values = new NabuccoListImpl<TemplateFieldValue>(NabuccoCollectionState.INITIALIZED);
        }
        return this.values;
    }

    /**
     * Fragments of this field set.
     *
     * @return the NabuccoList<TemplateFieldFragment>.
     */
    public NabuccoList<TemplateFieldFragment> getFragments() {
        if ((this.fragments == null)) {
            this.fragments = new NabuccoListImpl<TemplateFieldFragment>(NabuccoCollectionState.INITIALIZED);
        }
        return this.fragments;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TemplateFieldSet.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TemplateFieldSet.class).getAllProperties();
    }
}
