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
package org.nabucco.framework.template.facade.datatype.context;

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * TemplateContext.
 * 
 * @author Holger Librenz, PRODYNA AG
 */
public class TemplateContext {

	private final Map<String, NabuccoDatatype> context = new HashMap<String, NabuccoDatatype>();
	
	public TemplateContext() {
	}

	public void put(String key, NabuccoDatatype datatype) {
		this.context.put(key, datatype);
	}

	public NabuccoDatatype getDatatype(String key) {
		return this.context.get(key);
	}

	public String getAttributeAsString(String expression) {
		String[] paths = expression.split("\\.");
		// cut dollar sign
		NabuccoDatatype datatype = this.getDatatype(paths[0].substring(1));
		String result = "ERROR";
		for (int i = 1; i < paths.length; i++) {
			NabuccoProperty property = datatype.getProperty(paths[i]);
			if (property != null) {
				if (i + 1 < paths.length) {
					datatype = (NabuccoDatatype) property.getInstance();
				} else {
					result = String.valueOf(property.getInstance());
				}
			}
		}
		return result;
	}
	
	// TODO getAttributes for other types than string
}
