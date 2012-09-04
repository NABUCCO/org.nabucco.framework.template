/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco-source.org/nabucco-license.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.framework.template.impl.service.datastructure.produce.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSElement;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSElementGroup;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.datastructure.TemplateDSElementItem;

/**
 * DatastructureExtensionKeyVisitor
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DatastructureExtensionKeyFinder {

    private Map<String, String> keyPathMap = new HashMap<String, String>();

    private Set<String> keySet = new HashSet<String>();

    /**
     * 
     * Creates a new {@link DatastructureExtensionKeyFinder} instance.
     * 
     * @param keySet
     *            the list of the keys to locate
     */
    public DatastructureExtensionKeyFinder(Set<String> keySet) {
        if (keySet == null) {
            throw new IllegalArgumentException("Cannot locate key pairs without set of possible keys. It is null.");
        }
        this.keySet = keySet;

    }

    /**
     * Locate keys
     * 
     * @param structure
     *            the structure to be checked
     * @param prefix
     *            the optional prefix to the found paths
     * @return map with KEY->PATH pairs like SALARY->person.general.salary
     */
    public Map<String, String> locateKey(TemplateDSElement structure, String prefix) {
        keyPathMap.clear();

        this.locateKeyRecursively(prefix, structure);

        return keyPathMap;
    }

    /**
     * Traverce the tree and searches for the keys. Only keys that are in the map will be saved
     * 
     * @param currentPath
     *            the starting path or null
     * @param structureItem
     */
    private void locateKeyRecursively(String currentPath, TemplateDSElement structureItem) {
        PathItem path = new PathItem(currentPath);

        if (structureItem instanceof TemplateDSElementGroup) {
            TemplateDSElementGroup group = (TemplateDSElementGroup) structureItem;
            String name = PropertyLoader.loadProperty(group.getName());
            path.addPathPart(name);

            for (TemplateDSElement child : group.getChildrenList()) {
                this.locateKeyRecursively(path.getPath(), child);
            }
        } else if (structureItem instanceof TemplateDSElementItem) {
            TemplateDSElementItem item = (TemplateDSElementItem) structureItem;

            if (item.getKey() != null) {
                String key = PropertyLoader.loadProperty(item.getKey());

                if (key.isEmpty() == false && keySet.contains(key)) {
                    String name = PropertyLoader.loadProperty(item.getName());
                    path.addPathPart(name);
                    keyPathMap.put(key, path.getPath());
                }
            }
        }
    }
}
