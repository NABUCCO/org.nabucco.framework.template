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
package org.nabucco.framework.template.facade.message.odf.text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoCollectionState;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoListImpl;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.message.ServiceMessage;
import org.nabucco.framework.base.facade.message.ServiceMessageSupport;
import org.nabucco.framework.template.facade.datatype.mapping.TemplateContent;
import org.nabucco.framework.template.facade.datatype.mapping.TemplateFieldSet;

/**
 * OdtTemplateRq<p/>Request Message for creating Open Document Textfiles.<p/>
 *
 * @version 1.0
 * @author Nicolas Moser, PRODYNA AG, 2011-11-15
 */
public class OdtTemplateRq extends ServiceMessageSupport implements ServiceMessage {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "l1,512;u0,n;m1,1;", "m1,1;", "m0,n;" };

    public static final String MAPPINGID = "mappingId";

    public static final String ROOTFIELD = "rootField";

    public static final String CONTENTLIST = "contentList";

    /** ID of the template mapping extension. */
    private ExtensionId mappingId;

    /** The root field Set. */
    private TemplateFieldSet rootField;

    /** List of template contents. */
    private NabuccoList<TemplateContent> contentList;

    /** Constructs a new OdtTemplateRq instance. */
    public OdtTemplateRq() {
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
        propertyMap.put(MAPPINGID, PropertyDescriptorSupport.createBasetype(MAPPINGID, ExtensionId.class, 0,
                PROPERTY_CONSTRAINTS[0], false));
        propertyMap.put(ROOTFIELD, PropertyDescriptorSupport.createDatatype(ROOTFIELD, TemplateFieldSet.class, 1,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPOSITION));
        propertyMap.put(CONTENTLIST, PropertyDescriptorSupport.createCollection(CONTENTLIST, TemplateContent.class, 2,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPOSITION));
        return new NabuccoPropertyContainer(propertyMap);
    }

    /** Init. */
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(OdtTemplateRq.getPropertyDescriptor(MAPPINGID), this.mappingId));
        properties.add(super.createProperty(OdtTemplateRq.getPropertyDescriptor(ROOTFIELD), this.getRootField()));
        properties.add(super.createProperty(OdtTemplateRq.getPropertyDescriptor(CONTENTLIST), this.contentList));
        return properties;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(MAPPINGID) && (property.getType() == ExtensionId.class))) {
            this.setMappingId(((ExtensionId) property.getInstance()));
            return true;
        } else if ((property.getName().equals(ROOTFIELD) && (property.getType() == TemplateFieldSet.class))) {
            this.setRootField(((TemplateFieldSet) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTENTLIST) && (property.getType() == TemplateContent.class))) {
            this.contentList = ((NabuccoList<TemplateContent>) property.getInstance());
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
        final OdtTemplateRq other = ((OdtTemplateRq) obj);
        if ((this.mappingId == null)) {
            if ((other.mappingId != null))
                return false;
        } else if ((!this.mappingId.equals(other.mappingId)))
            return false;
        if ((this.rootField == null)) {
            if ((other.rootField != null))
                return false;
        } else if ((!this.rootField.equals(other.rootField)))
            return false;
        if ((this.contentList == null)) {
            if ((other.contentList != null))
                return false;
        } else if ((!this.contentList.equals(other.contentList)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.mappingId == null) ? 0 : this.mappingId.hashCode()));
        result = ((PRIME * result) + ((this.rootField == null) ? 0 : this.rootField.hashCode()));
        result = ((PRIME * result) + ((this.contentList == null) ? 0 : this.contentList.hashCode()));
        return result;
    }

    @Override
    public ServiceMessage cloneObject() {
        return this;
    }

    /**
     * ID of the template mapping extension.
     *
     * @return the ExtensionId.
     */
    public ExtensionId getMappingId() {
        return this.mappingId;
    }

    /**
     * ID of the template mapping extension.
     *
     * @param mappingId the ExtensionId.
     */
    public void setMappingId(ExtensionId mappingId) {
        this.mappingId = mappingId;
    }

    /**
     * The root field Set.
     *
     * @return the TemplateFieldSet.
     */
    public TemplateFieldSet getRootField() {
        return this.rootField;
    }

    /**
     * The root field Set.
     *
     * @param rootField the TemplateFieldSet.
     */
    public void setRootField(TemplateFieldSet rootField) {
        this.rootField = rootField;
    }

    /**
     * List of template contents.
     *
     * @return the NabuccoList<TemplateContent>.
     */
    public NabuccoList<TemplateContent> getContentList() {
        if ((this.contentList == null)) {
            this.contentList = new NabuccoListImpl<TemplateContent>(NabuccoCollectionState.INITIALIZED);
        }
        return this.contentList;
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(OdtTemplateRq.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(OdtTemplateRq.class).getAllProperties();
    }
}
