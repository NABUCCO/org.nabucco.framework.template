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
package org.nabucco.framework.template.facade.datatype.datastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Key;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.Value;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;

/**
 * KeyValuePair<p/>Key Value Pair of key to value in ds<p/>
 *
 * @version 1.0
 * @author Leonid Agranovskiy, PRODYNA AG, 2012-07-25
 */
public class KeyValuePair extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;u0,n;m1,1;", "l0,n;u0,n;m1,1;" };

    public static final String DSKEY = "dsKey";

    public static final String DSVALUE = "dsValue";

    private Key dsKey;

    private Value dsValue;

    /** Constructs a new KeyValuePair instance. */
    public KeyValuePair() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the KeyValuePair.
     */
    protected void cloneObject(KeyValuePair clone) {
        super.cloneObject(clone);
        if ((this.getDsKey() != null)) {
            clone.setDsKey(this.getDsKey().cloneObject());
        }
        if ((this.getDsValue() != null)) {
            clone.setDsValue(this.getDsValue().cloneObject());
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
        propertyMap.put(DSKEY,
                PropertyDescriptorSupport.createBasetype(DSKEY, Key.class, 3, PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(DSVALUE,
                PropertyDescriptorSupport.createBasetype(DSVALUE, Value.class, 4, PROPERTY_CONSTRAINTS[1], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(KeyValuePair.getPropertyDescriptor(DSKEY), this.dsKey, null));
        properties.add(super.createProperty(KeyValuePair.getPropertyDescriptor(DSVALUE), this.dsValue, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(DSKEY) && (property.getType() == Key.class))) {
            this.setDsKey(((Key) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DSVALUE) && (property.getType() == Value.class))) {
            this.setDsValue(((Value) property.getInstance()));
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
        final KeyValuePair other = ((KeyValuePair) obj);
        if ((this.dsKey == null)) {
            if ((other.dsKey != null))
                return false;
        } else if ((!this.dsKey.equals(other.dsKey)))
            return false;
        if ((this.dsValue == null)) {
            if ((other.dsValue != null))
                return false;
        } else if ((!this.dsValue.equals(other.dsValue)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.dsKey == null) ? 0 : this.dsKey.hashCode()));
        result = ((PRIME * result) + ((this.dsValue == null) ? 0 : this.dsValue.hashCode()));
        return result;
    }

    @Override
    public KeyValuePair cloneObject() {
        KeyValuePair clone = new KeyValuePair();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Missing description at method getDsKey.
     *
     * @return the Key.
     */
    public Key getDsKey() {
        return this.dsKey;
    }

    /**
     * Missing description at method setDsKey.
     *
     * @param dsKey the Key.
     */
    public void setDsKey(Key dsKey) {
        this.dsKey = dsKey;
    }

    /**
     * Missing description at method setDsKey.
     *
     * @param dsKey the String.
     */
    public void setDsKey(String dsKey) {
        if ((this.dsKey == null)) {
            if ((dsKey == null)) {
                return;
            }
            this.dsKey = new Key();
        }
        this.dsKey.setValue(dsKey);
    }

    /**
     * Missing description at method getDsValue.
     *
     * @return the Value.
     */
    public Value getDsValue() {
        return this.dsValue;
    }

    /**
     * Missing description at method setDsValue.
     *
     * @param dsValue the Value.
     */
    public void setDsValue(Value dsValue) {
        this.dsValue = dsValue;
    }

    /**
     * Missing description at method setDsValue.
     *
     * @param dsValue the String.
     */
    public void setDsValue(String dsValue) {
        if ((this.dsValue == null)) {
            if ((dsValue == null)) {
                return;
            }
            this.dsValue = new Value();
        }
        this.dsValue.setValue(dsValue);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(KeyValuePair.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(KeyValuePair.class).getAllProperties();
    }
}
