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

/**
 * PathItem
 * 
 * Utility class holding and building the complete path
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
class PathItem {

    private static final String PATH_SEPARATOR = ".";

    private StringBuilder pathBuilder;

    /**
     * 
     * Creates a new {@link PathItem} instance.
     * 
     */
    PathItem() {
        pathBuilder = new StringBuilder();
    }

    /**
     * 
     * Creates a new {@link PathItem} instance.
     * 
     */
    public PathItem(String path) {
        if (path == null) {
            pathBuilder = new StringBuilder();
        } else {
            pathBuilder = new StringBuilder(path);
        }
    }

    /**
     * Adds a part to the path
     * 
     * @param name
     *            part to add
     */
    public void addPathPart(String name) {
        if (pathBuilder.length() == 0) {
            pathBuilder.append(name);
        } else {
            pathBuilder.append(PATH_SEPARATOR);
            pathBuilder.append(name);
        }
    }

    /**
     * Returns the path
     * 
     * @return current path
     */
    public String getPath() {
        return pathBuilder.toString();
    }

}
