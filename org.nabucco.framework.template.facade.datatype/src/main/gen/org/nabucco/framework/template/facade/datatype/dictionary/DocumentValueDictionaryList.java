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
import org.nabucco.framework.base.facade.datatype.Key;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
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
 * DocumentValueDictionaryList<p/>n/a<p/>
 *
 * @version 1.0
 * @author jweismueller, PRODYNA AG, 2011-06-30
 */
public class DocumentValueDictionaryList extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m1,1;", "m0,n;" };

    public static final String KEY = "key";

    public static final String RECORDLIST = "recordList";

    public Key key;

    public NabuccoList<DocumentValueDictionaryRecord> recordList;

    /** Constructs a new DocumentValueDictionaryList instance. */
    public DocumentValueDictionaryList() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the DocumentValueDictionaryList.
     */
    protected void cloneObject(DocumentValueDictionaryList clone) {
        super.cloneObject(clone);
        if ((this.getKey() != null)) {
            clone.setKey(this.getKey().cloneObject());
        }
        if ((this.recordList != null)) {
            clone.recordList = this.recordList.cloneCollection();
        }
    }

    /**
     * Getter for the RecordListJPA.
     *
     * @return the List<DocumentValueDictionaryRecord>.
     */
    List<DocumentValueDictionaryRecord> getRecordListJPA() {
        if ((this.recordList == null)) {
            this.recordList = new NabuccoListImpl<DocumentValueDictionaryRecord>(NabuccoCollectionState.LAZY);
        }
        return ((NabuccoListImpl<DocumentValueDictionaryRecord>) this.recordList).getDelegate();
    }

    /**
     * Setter for the RecordListJPA.
     *
     * @param recordList the List<DocumentValueDictionaryRecord>.
     */
    void setRecordListJPA(List<DocumentValueDictionaryRecord> recordList) {
        if ((this.recordList == null)) {
            this.recordList = new NabuccoListImpl<DocumentValueDictionaryRecord>(NabuccoCollectionState.LAZY);
        }
        ((NabuccoListImpl<DocumentValueDictionaryRecord>) this.recordList).setDelegate(recordList);
    }

    /**
     * CreatePropertyContainer.
     *
     * @return the NabuccoPropertyContainer.
     */
    protected static NabuccoPropertyContainer createPropertyContainer() {
        Map<String, NabuccoPropertyDescriptor> propertyMap = new HashMap<String, NabuccoPropertyDescriptor>();
        propertyMap.putAll(PropertyCache.getInstance().retrieve(NabuccoDatatype.class).getPropertyMap());
        propertyMap.put(KEY,
                PropertyDescriptorSupport.createBasetype(KEY, Key.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(RECORDLIST, PropertyDescriptorSupport.createCollection(RECORDLIST,
                DocumentValueDictionaryRecord.class, 4, PROPERTY_CONSTRAINTS[1], false,
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
        properties.add(super.createProperty(DocumentValueDictionaryList.getPropertyDescriptor(KEY), this.key, null));
        properties.add(super.createProperty(DocumentValueDictionaryList.getPropertyDescriptor(RECORDLIST),
                this.recordList, null));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(KEY) && (property.getType() == Key.class))) {
            this.setKey(((Key) property.getInstance()));
            return true;
        } else if ((property.getName().equals(RECORDLIST) && (property.getType() == DocumentValueDictionaryRecord.class))) {
            this.recordList = ((NabuccoList<DocumentValueDictionaryRecord>) property.getInstance());
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
        final DocumentValueDictionaryList other = ((DocumentValueDictionaryList) obj);
        if ((this.key == null)) {
            if ((other.key != null))
                return false;
        } else if ((!this.key.equals(other.key)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.key == null) ? 0 : this.key.hashCode()));
        return result;
    }

    @Override
    public DocumentValueDictionaryList cloneObject() {
        DocumentValueDictionaryList clone = new DocumentValueDictionaryList();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getKey.
     *
     * @return the Key.
     */
    public Key getKey() {
        return this.key;
    }

    /**
     * Missing description at method setKey.
     *
     * @param key the Key.
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Missing description at method setKey.
     *
     * @param key the String.
     */
    public void setKey(String key) {
        if ((this.key == null)) {
            if ((key == null)) {
                return;
            }
            this.key = new Key();
        }
        this.key.setValue(key);
    }

    /**
     * Missing description at method getRecordList.
     *
     * @return the NabuccoList<DocumentValueDictionaryRecord>.
     */
    public NabuccoList<DocumentValueDictionaryRecord> getRecordList() {
        if ((this.recordList == null)) {
            this.recordList = new NabuccoListImpl<DocumentValueDictionaryRecord>(NabuccoCollectionState.INITIALIZED);
        }
        return this.recordList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(DocumentValueDictionaryList.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(DocumentValueDictionaryList.class).getAllProperties();
    }
}
