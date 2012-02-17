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
package org.nabucco.framework.template.impl.service.util.mapping;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.mapping.TemplateMappingExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.mapping.TemplateMappingField;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.mapping.TemplateMappingFieldSet;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.mapping.TemplateMappingFragment;
import org.nabucco.framework.template.facade.datatype.mapping.TemplateFieldFragment;
import org.nabucco.framework.template.facade.datatype.mapping.TemplateFieldSet;
import org.nabucco.framework.template.facade.datatype.mapping.TemplateFieldValue;
import org.nabucco.framework.template.facade.exception.TemplateException;

/**
 * TemplateMapper
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class TemplateMapper {

    private static final String DEFAULT_VALUE = "";

    private TemplateMappingExtension extension;

    /**
     * Creates a new {@link TemplateMapper} instance.
     * 
     * @param extensionId
     *            the template mapping extension id
     * 
     * @throws ExtensionException
     *             when the template mapping extension cannot be loaded
     */
    public TemplateMapper(TemplateMappingExtension extension) {
        if (extension == null) {
            throw new IllegalArgumentException("Cannot create template mapper for extension 'null'.");
        }
        this.extension = extension;
    }

    /**
     * Map the given fields into the template text.
     * 
     * @param original
     *            the original template text
     * @param rootField
     *            the root field to map into the text
     * 
     * @return the new text after the mapping
     * 
     * @throws TemplateException
     *             when the template cannot be mapped
     */
    public String mapTemplate(String original, TemplateFieldSet rootField) throws TemplateException {
        try {
            TemplateMappingFieldSet rootMapping = this.extension.getRoot();
            return this.replace(original, rootMapping, rootField);
        } catch (Exception e) {
            throw new TemplateException("Error mapping template '" + this.extension.getIdentifier() + "'.", e);
        }
    }

    /**
     * Replace the given template text by the field set configured in the given mapping.
     * 
     * @param original
     *            the original text
     * @param mappingSet
     *            the template mapping
     * @param fieldSet
     *            the template values
     * 
     * @return the replaced text
     * 
     * @throws TemplateException
     *             when the text cannot be replaced
     */
    private String replace(String original, TemplateMappingFieldSet mappingSet, TemplateFieldSet fieldSet)
            throws TemplateException {

        String text = original;

        for (TemplateMappingField mapping : mappingSet.getFieldList()) {
            String key = PropertyLoader.loadProperty(mapping.getKey());
            if (key == null) {
                continue;
            }

            if (mapping instanceof TemplateMappingFieldSet) {

                TemplateMappingFieldSet childMapping = (TemplateMappingFieldSet) mapping;

                TemplateFieldFragment fragment = this.getFragment(fieldSet, key);

                String fragmentText = this.getFragmentText(childMapping);

                StringBuilder value = new StringBuilder();
                if (fragment != null) {
                    for (TemplateFieldSet set : fragment.getFieldSets()) {
                        value.append(this.replace(fragmentText, childMapping, set));
                    }
                }

                text = this.replace(text, key, value.toString());

            } else {
                TemplateFieldValue field = this.getValue(fieldSet, key);
                String value = PropertyLoader.loadProperty(mapping.getDefaultValue());
                if (field != null && field.getValue() != null && field.getValue().getValue() != null) {
                    value = field.getValue().getValue();
                }
                text = this.replace(text, key, value);
            }
        }

        return text;
    }

    /**
     * Getter for the field fragment in the given field set parent.
     * 
     * @param parent
     *            the parent field set
     * @param key
     *            the field key
     * 
     * @return the given field or null if none is defined
     */
    private TemplateFieldFragment getFragment(TemplateFieldSet parent, String key) {
        if (parent == null) {
            return null;
        }
        for (TemplateFieldFragment fragment : parent.getFragments()) {
            if (fragment.getFieldName() == null || fragment.getFieldName().getValue() == null) {
                continue;
            }
            if (fragment.getFieldName().getValue().equals(key)) {
                return fragment;
            }
        }
        return null;
    }

    /**
     * Getter for the field value in the given field set parent.
     * 
     * @param parent
     *            the parent field set
     * @param key
     *            the field key
     * 
     * @return the given field or null if none is defined
     */
    private TemplateFieldValue getValue(TemplateFieldSet parent, String key) {
        if (parent == null) {
            return null;
        }
        for (TemplateFieldValue value : parent.getValues()) {
            if (value.getFieldName() == null || value.getFieldName().getValue() == null) {
                continue;
            }
            if (value.getFieldName().getValue().equals(key)) {
                return value;
            }
        }
        return null;
    }

    /**
     * Getter for the fragment string configured in the extension configuration.
     * 
     * @param fieldSetExtension
     *            the field set extension to load the fragment for
     * 
     * @return the loaded fragment
     */
    private String getFragmentText(TemplateMappingFieldSet fieldSetExtension) {

        String fragmentId = PropertyLoader.loadProperty(fieldSetExtension.getFragmentId());
        if (fragmentId == null) {
            return null;
        }

        for (TemplateMappingFragment fragment : this.extension.getFragments()) {
            if (fragment.getIdentifier() == null || fragment.getIdentifier().getValue() == null) {
                continue;
            }
            if (fragment.getIdentifier().getValue().equals(fragmentId)) {
                return PropertyLoader.loadProperty(fragment.getContent());
            }
        }

        return null;
    }

    /**
     * Replace the given key with the given replacement.
     * 
     * @param original
     *            the original text
     * @param key
     *            the key to replace
     * @param replacement
     *            the replacement string
     * 
     * @return the new text
     */
    private String replace(String original, String key, String replacement) {
        Pattern pattern = Pattern.compile("\\$\\{" + key + "\\}");
        Matcher matcher = pattern.matcher(original);

        if (replacement == null) {
            replacement = DEFAULT_VALUE;
        }

        return matcher.replaceAll(replacement);
    }
}
