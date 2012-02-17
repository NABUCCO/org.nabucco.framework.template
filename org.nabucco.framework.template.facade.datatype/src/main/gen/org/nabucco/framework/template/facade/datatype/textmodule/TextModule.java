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
package org.nabucco.framework.template.facade.datatype.textmodule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.Description;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.code.Code;
import org.nabucco.framework.base.facade.datatype.code.CodePath;
import org.nabucco.framework.base.facade.datatype.date.Timestamp;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyContainer;
import org.nabucco.framework.base.facade.datatype.property.NabuccoPropertyDescriptor;
import org.nabucco.framework.base.facade.datatype.property.PropertyAssociationType;
import org.nabucco.framework.base.facade.datatype.property.PropertyCache;
import org.nabucco.framework.base.facade.datatype.property.PropertyDescriptorSupport;
import org.nabucco.framework.base.facade.datatype.text.TextContent;

/**
 * TextModule<p/>Represents one text module<p/>
 *
 * @version 1.0
 * @author hlibrenz, PRODYNA AG, 2011-03-24
 */
public class TextModule extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_CONSTRAINTS = { "m1,1;", "m1,1;", "m0,1;", "l0,255;u0,n;m0,1;",
            "l0,2147483647;u0,n;m1,1;", "l0,n;u0,n;m0,1;" };

    public static final String MODULETYPE = "moduleType";

    public static final String MODULECATEGORYTYPE = "moduleCategoryType";

    public static final String LANGUAGE = "language";

    public static final String DESCRIPTION = "description";

    public static final String CONTENT = "content";

    public static final String LASTMODIFIED = "lastModified";

    /** Tags the module for one of preconfigured category */
    private Code moduleType;

    private Long moduleTypeRefId;

    protected static final String MODULETYPE_CODEPATH = "nabucco.framework.template.textmodule.moduletype";

    private Code moduleCategoryType;

    private Long moduleCategoryTypeRefId;

    protected static final String MODULECATEGORYTYPE_CODEPATH = "nabucco.framework.template.textmodule.modulecategorytype";

    /** For that language is this text module for? */
    private Code language;

    private Long languageRefId;

    protected static final String LANGUAGE_CODEPATH = "nabucco.framework.language";

    /** Short description */
    private Description description;

    /** The text module content itself. */
    private TextContent content;

    /** The last time, the content was modified */
    private Timestamp lastModified;

    /** Constructs a new TextModule instance. */
    public TextModule() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
    }

    /**
     * CloneObject.
     *
     * @param clone the TextModule.
     */
    protected void cloneObject(TextModule clone) {
        super.cloneObject(clone);
        if ((this.getModuleType() != null)) {
            clone.setModuleType(this.getModuleType().cloneObject());
        }
        if ((this.getModuleTypeRefId() != null)) {
            clone.setModuleTypeRefId(this.getModuleTypeRefId());
        }
        if ((this.getModuleCategoryType() != null)) {
            clone.setModuleCategoryType(this.getModuleCategoryType().cloneObject());
        }
        if ((this.getModuleCategoryTypeRefId() != null)) {
            clone.setModuleCategoryTypeRefId(this.getModuleCategoryTypeRefId());
        }
        if ((this.getLanguage() != null)) {
            clone.setLanguage(this.getLanguage().cloneObject());
        }
        if ((this.getLanguageRefId() != null)) {
            clone.setLanguageRefId(this.getLanguageRefId());
        }
        if ((this.getDescription() != null)) {
            clone.setDescription(this.getDescription().cloneObject());
        }
        if ((this.getContent() != null)) {
            clone.setContent(this.getContent().cloneObject());
        }
        if ((this.getLastModified() != null)) {
            clone.setLastModified(this.getLastModified().cloneObject());
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
        propertyMap.put(MODULETYPE, PropertyDescriptorSupport.createDatatype(MODULETYPE, Code.class, 3,
                PROPERTY_CONSTRAINTS[0], false, PropertyAssociationType.COMPONENT, MODULETYPE_CODEPATH));
        propertyMap.put(MODULECATEGORYTYPE, PropertyDescriptorSupport.createDatatype(MODULECATEGORYTYPE, Code.class, 4,
                PROPERTY_CONSTRAINTS[1], false, PropertyAssociationType.COMPONENT, MODULECATEGORYTYPE_CODEPATH));
        propertyMap.put(LANGUAGE, PropertyDescriptorSupport.createDatatype(LANGUAGE, Code.class, 5,
                PROPERTY_CONSTRAINTS[2], false, PropertyAssociationType.COMPONENT, LANGUAGE_CODEPATH));
        propertyMap.put(DESCRIPTION, PropertyDescriptorSupport.createBasetype(DESCRIPTION, Description.class, 6,
                PROPERTY_CONSTRAINTS[3], false));
        propertyMap
                .put(CONTENT, PropertyDescriptorSupport.createBasetype(CONTENT, TextContent.class, 7,
                        PROPERTY_CONSTRAINTS[4], false));
        propertyMap.put(LASTMODIFIED, PropertyDescriptorSupport.createBasetype(LASTMODIFIED, Timestamp.class, 8,
                PROPERTY_CONSTRAINTS[5], false));
        return new NabuccoPropertyContainer(propertyMap);
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public Set<NabuccoProperty> getProperties() {
        Set<NabuccoProperty> properties = super.getProperties();
        properties.add(super.createProperty(TextModule.getPropertyDescriptor(MODULETYPE), this.getModuleType(),
                this.moduleTypeRefId));
        properties.add(super.createProperty(TextModule.getPropertyDescriptor(MODULECATEGORYTYPE),
                this.getModuleCategoryType(), this.moduleCategoryTypeRefId));
        properties.add(super.createProperty(TextModule.getPropertyDescriptor(LANGUAGE), this.getLanguage(),
                this.languageRefId));
        properties.add(super.createProperty(TextModule.getPropertyDescriptor(DESCRIPTION), this.description, null));
        properties.add(super.createProperty(TextModule.getPropertyDescriptor(CONTENT), this.content, null));
        properties.add(super.createProperty(TextModule.getPropertyDescriptor(LASTMODIFIED), this.lastModified, null));
        return properties;
    }

    @Override
    public boolean setProperty(NabuccoProperty property) {
        if (super.setProperty(property)) {
            return true;
        }
        if ((property.getName().equals(MODULETYPE) && (property.getType() == Code.class))) {
            this.setModuleType(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(MODULECATEGORYTYPE) && (property.getType() == Code.class))) {
            this.setModuleCategoryType(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LANGUAGE) && (property.getType() == Code.class))) {
            this.setLanguage(((Code) property.getInstance()));
            return true;
        } else if ((property.getName().equals(DESCRIPTION) && (property.getType() == Description.class))) {
            this.setDescription(((Description) property.getInstance()));
            return true;
        } else if ((property.getName().equals(CONTENT) && (property.getType() == TextContent.class))) {
            this.setContent(((TextContent) property.getInstance()));
            return true;
        } else if ((property.getName().equals(LASTMODIFIED) && (property.getType() == Timestamp.class))) {
            this.setLastModified(((Timestamp) property.getInstance()));
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
        final TextModule other = ((TextModule) obj);
        if ((this.moduleType == null)) {
            if ((other.moduleType != null))
                return false;
        } else if ((!this.moduleType.equals(other.moduleType)))
            return false;
        if ((this.moduleTypeRefId == null)) {
            if ((other.moduleTypeRefId != null))
                return false;
        } else if ((!this.moduleTypeRefId.equals(other.moduleTypeRefId)))
            return false;
        if ((this.moduleCategoryType == null)) {
            if ((other.moduleCategoryType != null))
                return false;
        } else if ((!this.moduleCategoryType.equals(other.moduleCategoryType)))
            return false;
        if ((this.moduleCategoryTypeRefId == null)) {
            if ((other.moduleCategoryTypeRefId != null))
                return false;
        } else if ((!this.moduleCategoryTypeRefId.equals(other.moduleCategoryTypeRefId)))
            return false;
        if ((this.language == null)) {
            if ((other.language != null))
                return false;
        } else if ((!this.language.equals(other.language)))
            return false;
        if ((this.languageRefId == null)) {
            if ((other.languageRefId != null))
                return false;
        } else if ((!this.languageRefId.equals(other.languageRefId)))
            return false;
        if ((this.description == null)) {
            if ((other.description != null))
                return false;
        } else if ((!this.description.equals(other.description)))
            return false;
        if ((this.content == null)) {
            if ((other.content != null))
                return false;
        } else if ((!this.content.equals(other.content)))
            return false;
        if ((this.lastModified == null)) {
            if ((other.lastModified != null))
                return false;
        } else if ((!this.lastModified.equals(other.lastModified)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.moduleType == null) ? 0 : this.moduleType.hashCode()));
        result = ((PRIME * result) + ((this.moduleTypeRefId == null) ? 0 : this.moduleTypeRefId.hashCode()));
        result = ((PRIME * result) + ((this.moduleCategoryType == null) ? 0 : this.moduleCategoryType.hashCode()));
        result = ((PRIME * result) + ((this.moduleCategoryTypeRefId == null) ? 0 : this.moduleCategoryTypeRefId
                .hashCode()));
        result = ((PRIME * result) + ((this.language == null) ? 0 : this.language.hashCode()));
        result = ((PRIME * result) + ((this.languageRefId == null) ? 0 : this.languageRefId.hashCode()));
        result = ((PRIME * result) + ((this.description == null) ? 0 : this.description.hashCode()));
        result = ((PRIME * result) + ((this.content == null) ? 0 : this.content.hashCode()));
        result = ((PRIME * result) + ((this.lastModified == null) ? 0 : this.lastModified.hashCode()));
        return result;
    }

    @Override
    public TextModule cloneObject() {
        TextModule clone = new TextModule();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * Tags the module for one of preconfigured category
     *
     * @param moduleType the Code.
     */
    public void setModuleType(Code moduleType) {
        this.moduleType = moduleType;
        if ((moduleType != null)) {
            this.setModuleTypeRefId(moduleType.getId());
        } else {
            this.setModuleTypeRefId(null);
        }
    }

    /**
     * Tags the module for one of preconfigured category
     *
     * @return the Code.
     */
    public Code getModuleType() {
        return this.moduleType;
    }

    /**
     * Getter for the ModuleTypeRefId.
     *
     * @return the Long.
     */
    public Long getModuleTypeRefId() {
        return this.moduleTypeRefId;
    }

    /**
     * Setter for the ModuleTypeRefId.
     *
     * @param moduleTypeRefId the Long.
     */
    public void setModuleTypeRefId(Long moduleTypeRefId) {
        this.moduleTypeRefId = moduleTypeRefId;
    }

    /**
     * Missing description at method setModuleCategoryType.
     *
     * @param moduleCategoryType the Code.
     */
    public void setModuleCategoryType(Code moduleCategoryType) {
        this.moduleCategoryType = moduleCategoryType;
        if ((moduleCategoryType != null)) {
            this.setModuleCategoryTypeRefId(moduleCategoryType.getId());
        } else {
            this.setModuleCategoryTypeRefId(null);
        }
    }

    /**
     * Missing description at method getModuleCategoryType.
     *
     * @return the Code.
     */
    public Code getModuleCategoryType() {
        return this.moduleCategoryType;
    }

    /**
     * Getter for the ModuleCategoryTypeRefId.
     *
     * @return the Long.
     */
    public Long getModuleCategoryTypeRefId() {
        return this.moduleCategoryTypeRefId;
    }

    /**
     * Setter for the ModuleCategoryTypeRefId.
     *
     * @param moduleCategoryTypeRefId the Long.
     */
    public void setModuleCategoryTypeRefId(Long moduleCategoryTypeRefId) {
        this.moduleCategoryTypeRefId = moduleCategoryTypeRefId;
    }

    /**
     * For that language is this text module for?
     *
     * @param language the Code.
     */
    public void setLanguage(Code language) {
        this.language = language;
        if ((language != null)) {
            this.setLanguageRefId(language.getId());
        } else {
            this.setLanguageRefId(null);
        }
    }

    /**
     * For that language is this text module for?
     *
     * @return the Code.
     */
    public Code getLanguage() {
        return this.language;
    }

    /**
     * Getter for the LanguageRefId.
     *
     * @return the Long.
     */
    public Long getLanguageRefId() {
        return this.languageRefId;
    }

    /**
     * Setter for the LanguageRefId.
     *
     * @param languageRefId the Long.
     */
    public void setLanguageRefId(Long languageRefId) {
        this.languageRefId = languageRefId;
    }

    /**
     * Short description
     *
     * @return the Description.
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * Short description
     *
     * @param description the Description.
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * Short description
     *
     * @param description the String.
     */
    public void setDescription(String description) {
        if ((this.description == null)) {
            if ((description == null)) {
                return;
            }
            this.description = new Description();
        }
        this.description.setValue(description);
    }

    /**
     * The text module content itself.
     *
     * @return the TextContent.
     */
    public TextContent getContent() {
        return this.content;
    }

    /**
     * The text module content itself.
     *
     * @param content the TextContent.
     */
    public void setContent(TextContent content) {
        this.content = content;
    }

    /**
     * The text module content itself.
     *
     * @param content the String.
     */
    public void setContent(String content) {
        if ((this.content == null)) {
            if ((content == null)) {
                return;
            }
            this.content = new TextContent();
        }
        this.content.setValue(content);
    }

    /**
     * The last time, the content was modified
     *
     * @return the Timestamp.
     */
    public Timestamp getLastModified() {
        return this.lastModified;
    }

    /**
     * The last time, the content was modified
     *
     * @param lastModified the Timestamp.
     */
    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * The last time, the content was modified
     *
     * @param lastModified the Long.
     */
    public void setLastModified(Long lastModified) {
        if ((this.lastModified == null)) {
            if ((lastModified == null)) {
                return;
            }
            this.lastModified = new Timestamp();
        }
        this.lastModified.setValue(lastModified);
    }

    /**
     * Getter for the PropertyDescriptor.
     *
     * @param propertyName the String.
     * @return the NabuccoPropertyDescriptor.
     */
    public static NabuccoPropertyDescriptor getPropertyDescriptor(String propertyName) {
        return PropertyCache.getInstance().retrieve(TextModule.class).getProperty(propertyName);
    }

    /**
     * Getter for the PropertyDescriptorList.
     *
     * @return the List<NabuccoPropertyDescriptor>.
     */
    public static List<NabuccoPropertyDescriptor> getPropertyDescriptorList() {
        return PropertyCache.getInstance().retrieve(TextModule.class).getAllProperties();
    }

    /**
     * Getter for the ModuleTypeCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getModuleTypeCodePath() {
        return new CodePath(MODULETYPE_CODEPATH);
    }

    /**
     * Getter for the ModuleCategoryTypeCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getModuleCategoryTypeCodePath() {
        return new CodePath(MODULECATEGORYTYPE_CODEPATH);
    }

    /**
     * Getter for the LanguageCodePath.
     *
     * @return the CodePath.
     */
    public static CodePath getLanguageCodePath() {
        return new CodePath(LANGUAGE_CODEPATH);
    }
}
