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
package org.nabucco.framework.template.facade.datatype.dictionary;

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

/**
 * DocumentValueDictionaryRoot<p/><p/>
 *
 * @version 1.0
 * @author jweismueller, PRODYNA AG, 2011-07-01
 */
public class DocumentValueDictionaryRoot extends DocumentValueDictionary implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m0,n;" };

    public static final String LISTLIST = "listList";

    private NabuccoList<DocumentValueDictionaryList> listList;

    /** Constructs a new DocumentValueDictionaryRoot instance. */
    public DocumentValueDictionaryRoot() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the DocumentValueDictionaryRoot.
     */
    protected void cloneObject(DocumentValueDictionaryRoot clone) {
        super.cloneObject(clone);
        if ((this.listList != null)) {
            clone.listList = this.listList.cloneCollection();
        }
    }

    /**
     * Getter for the ListListJPA.
     *
     * @return the List<DocumentValueDictionaryList>.
     */
    List<DocumentValueDictionaryList> getListListJPA() {
        if ((this.listList == null)) {
            this.listList = new NabuccoListImpl<DocumentValueDictionaryList>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<DocumentValueDictionaryList>) this.listList).getDelegate();
    }

    /**
     * Setter for the ListListJPA.
     *
     * @param listList the List<DocumentValueDictionaryList>.
     */
    void setListListJPA(List<DocumentValueDictionaryList> listList) {
        if ((this.listList == null)) {
            this.listList = new NabuccoListImpl<DocumentValueDictionaryList>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<DocumentValueDictionaryList>) this.listList).setDelegate(listList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(DocumentValueDictionary.class).getPropertyMap());
        propertyMap.put(LISTLIST, PropertyDescriptorSupport.createCollection(LISTLIST,
                DocumentValueDictionaryList.class, 4, PROPERTY_CONSTRAINTS[0], false,
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
        properties.add(super.createProperty(DocumentValueDictionaryRoot.getPropertyDescriptor(LISTLIST), this.listList,
                null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(LISTLIST) && (property.getType() == DocumentValueDictionaryList.class))) {
            this.listList = ((NabuccoList<DocumentValueDictionaryList>) property.getInstance());
            return true;
        }
        return false;
    }

    @Override
    public DocumentValueDictionaryRoot cloneObject() {
        DocumentValueDictionaryRoot clone = new DocumentValueDictionaryRoot();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getListList.
     *
     * @return the NabuccoList<DocumentValueDictionaryList>.
     */
    public NabuccoList<DocumentValueDictionaryList> getListList() {
        if ((this.listList == null)) {
            this.listList = new NabuccoListImpl<DocumentValueDictionaryList>(NabuccoCollectionState.INITIALIZED);
        }
        return this.listList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DocumentValueDictionaryRoot.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DocumentValueDictionaryRoot.class).getAllProperties();
    }
}
