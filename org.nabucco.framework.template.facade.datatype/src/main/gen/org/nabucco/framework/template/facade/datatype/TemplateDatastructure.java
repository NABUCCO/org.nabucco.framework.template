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
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.template.datastructure.TemplateDSInstance;
import org.nabucco.framework.base.facade.datatype.xml.XmlDocument;
import org.nabucco.framework.template.facade.datatype.TemplateDatastructureReferenceItem;

/**
 * TemplateDatastructure<p/>Instance of the tempalte datastrcuture<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-06
 */
public class TemplateDatastructure extends TemplateDSInstance implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,2147483647;u0,n;m0,1;", "m0,n;" };

    public static final String XML = "xml";

    public static final String KEYREFERENCES = "keyReferences";

    /** The serialized datatype. */
    private XmlDocument xml;

    /** The list with key list in the current datastructure */
    private NabuccoList<TemplateDatastructureReferenceItem> keyReferences;

    /** Constructs a new TemplateDatastructure instance. */
    public TemplateDatastructure() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TemplateDatastructure.
     */
    protected void cloneObject(TemplateDatastructure clone) {
        super.cloneObject(clone);
        if ((this.getXml() != null)) {
            clone.setXml(this.getXml().cloneObject());
        }
        if ((this.keyReferences != null)) {
            clone.keyReferences = this.keyReferences.cloneCollection();
        }
    }

    /**
     * Getter for the KeyReferencesJPA.
     *
     * @return the List<TemplateDatastructureReferenceItem>.
     */
    List<TemplateDatastructureReferenceItem> getKeyReferencesJPA() {
        if ((this.keyReferences == null)) {
            this.keyReferences = new NabuccoListImpl<TemplateDatastructureReferenceItem>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<TemplateDatastructureReferenceItem>) this.keyReferences).getDelegate();
    }

    /**
     * Setter for the KeyReferencesJPA.
     *
     * @param keyReferences the List<TemplateDatastructureReferenceItem>.
     */
    void setKeyReferencesJPA(List<TemplateDatastructureReferenceItem> keyReferences) {
        if ((this.keyReferences == null)) {
            this.keyReferences = new NabuccoListImpl<TemplateDatastructureReferenceItem>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<TemplateDatastructureReferenceItem>) this.keyReferences).setDelegate(keyReferences);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(TemplateDSInstance.class).getPropertyMap());
        propertyMap.put(XML,
                PropertyDescriptorSupport.createBasetype(XML, XmlDocument.class, 6, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(KEYREFERENCES, PropertyDescriptorSupport.createCollection(KEYREFERENCES,
                TemplateDatastructureReferenceItem.class, 7, PROPERTY_CONSTRAINTS[1], false,
                PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TemplateDatastructure.getPropertyDescriptor(XML), this.xml, null));
        properties.add(super.createProperty(TemplateDatastructure.getPropertyDescriptor(KEYREFERENCES),
                this.keyReferences, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(XML) && (property.getType() == XmlDocument.class))) {
            this.setXml(((XmlDocument) property.getInstance()));
            return true;
        } else if ((property.getName().equals(KEYREFERENCES) && (property.getType() == TemplateDatastructureReferenceItem.class))) {
            this.keyReferences = ((NabuccoList<TemplateDatastructureReferenceItem>) property.getInstance());
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
        final TemplateDatastructure other = ((TemplateDatastructure) obj);
        if ((this.xml == null)) {
            if ((other.xml != null))
                return false;
        } else if ((!this.xml.equals(other.xml)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.xml == null) ? 0 : this.xml.hashCode()));
        return result;
    }

    @Override
    public TemplateDatastructure cloneObject() {
        TemplateDatastructure clone = new TemplateDatastructure();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The serialized datatype.
     *
     * @return the XmlDocument.
     */
    public XmlDocument getXml() {
        return this.xml;
    }

    /**
     * The serialized datatype.
     *
     * @param xml the XmlDocument.
     */
    public void setXml(XmlDocument xml) {
        this.xml = xml;
    }

    /**
     * The serialized datatype.
     *
     * @param xml the String.
     */
    public void setXml(String xml) {
        if ((this.xml == null)) {
            if ((xml == null)) {
                return;
            }
            this.xml = new XmlDocument();
        }
        this.xml.setValue(xml);
    }

    /**
     * The list with key list in the current datastructure
     *
     * @return the NabuccoList<TemplateDatastructureReferenceItem>.
     */
    public NabuccoList<TemplateDatastructureReferenceItem> getKeyReferences() {
        if ((this.keyReferences == null)) {
            this.keyReferences = new NabuccoListImpl<TemplateDatastructureReferenceItem>(
                    NabuccoCollectionState.INITIALIZED);
        }
        return this.keyReferences;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TemplateDatastructure.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TemplateDatastructure.class).getAllProperties();
    }
}
