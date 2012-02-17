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
package org.nabucco.framework.template.facade.service.util;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Set;

import org.nabucco.framework.base.facade.datatype.Basetype;
import org.nabucco.framework.base.facade.datatype.Flag;
import org.nabucco.framework.base.facade.datatype.NDate;
import org.nabucco.framework.base.facade.datatype.NDecimal;
import org.nabucco.framework.base.facade.datatype.NFloat;
import org.nabucco.framework.base.facade.datatype.NTime;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.template.facade.datatype.dictionary.DocumentValueDictionary;
import org.nabucco.framework.template.facade.datatype.dictionary.DocumentValueDictionaryItem;
import org.nabucco.framework.template.facade.datatype.dictionary.DocumentValueDictionaryList;
import org.nabucco.framework.template.facade.datatype.dictionary.DocumentValueDictionaryRecord;
import org.nabucco.framework.template.facade.datatype.dictionary.DocumentValueDictionaryRoot;

/**
 * TemplateDictionaryHelper.
 * 
 * @author Holger Librenz, PRODYNA AG
 * @author Juergen Weismueller, PRODYNA AG
 */
public class TemplateDictionaryHelper {

	protected static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(
			TemplateDictionaryHelper.class);

	/**
	 * The jOpenDocument API needs every field, defined in OOo template, in the
	 * map it uses to generate an document based on given values and the
	 * template. This method will produce empty string entries in the
	 * dictionary, that is used to transport the values to Template's produce
	 * service, for any not available field name.
	 * 
	 * @param dict
	 *            The prepared dictionary.
	 * @param fieldDefClass
	 *            The class that holds all template form fields as static class
	 *            fields.
	 * @return The "completed" dictionary.
	 */
	@SuppressWarnings("rawtypes")
	public static void completeDictionary(DocumentValueDictionaryRoot dict, Class fieldDefClazz) {
		{
			// the root dictionary itself
			completeDictionaryValues(dict, fieldDefClazz);
		}
		{
			// the list items
			Set<String> listKeys = new HashSet<String>();
			for (DocumentValueDictionaryList list : dict.getListList()) {
				if (!listKeys.add(list.getKey().getValue())) {
					logger.error("ListKey [" + list.getKey() + "] duplicate in DocumentValueDictionary.");
				}
			}
			Class[] declaredCls = fieldDefClazz.getDeclaredClasses();
			for (Class listCls : declaredCls) {
				String key = listCls.getSimpleName();
				if (!listKeys.contains(key)) {
					// add one record into list and fill with defaults
					DocumentValueDictionaryRecord record = new DocumentValueDictionaryRecord();
					DocumentValueDictionaryList list = new DocumentValueDictionaryList();
					list.setKey(key);
					list.getRecordList().add(record);
					dict.getListList().add(list);
					completeDictionaryValues(record, listCls);
				}
			}
		}
	}

	private static void completeDictionaryValues(DocumentValueDictionary dict, Class<?> cls) {
		Field[] declaredFields = cls.getFields();
		NabuccoList<DocumentValueDictionaryItem> itemList = dict.getItemList();
		Set<String> itemKeys = new HashSet<String>();
		for (DocumentValueDictionaryItem item : itemList) {
			if (!itemKeys.add(item.getKey().getValue())) {
				logger.error("Key [" + item.getKey()
						+ "] already contained in DocumentValueDictionary for definition [" + cls.getSimpleName()
						+ "].");
			}
		}
		for (Field field : declaredFields) {
			String key = value(field);
			if (!itemKeys.contains(key)) {
				defaultField(dict, key);
			}
		}

	}

	private static void defaultField(DocumentValueDictionary dict, String key) {
		if (key.matches("is[A-Z].*")) {
			createCheckboxField(dict, key, false);
		} else if (key.matches("test[A-Z].*")) {
			createTestField(dict, key, false);
		} else {
			createTextField(dict, key, (String) null);
		}
	}

	private static String value(Field field) {
		try {
			field.setAccessible(true);
			return (String) field.get(null);
		} catch (Exception e) {
			logger.warning(e.toString());
			return null;
		}
	}

	public static void createTextField(DocumentValueDictionary dict, String key, Basetype value) {
		if (value == null) {
			logger.debug("Given value for [" + key + "] is null.");
			createTextField(dict, key, "");
		} else if (value.getValue() == null) {
			logger.debug("Given value for [" + key + "] is null.");
			createTextField(dict, key, "");
		} else {
			createTextField(dict, key, value.getValueAsString().trim());
		}
	}
	
	public static void createTextField(DocumentValueDictionary dict, String key, String value) {
		DocumentValueDictionaryItem dictItem = new DocumentValueDictionaryItem();
		dictItem.setKey(key);
		if (value == null) {
			dictItem.setValue("");
		} else {
			dictItem.setValue(value);
		}
		dict.getItemList().add(dictItem);
	}

	public static void createDateField(DocumentValueDictionary dict, String key, NDate value, DateFormat format) {
		if (value == null) {
			logger.debug("Given value for [" + key + "] is null.");
			createTextField(dict, key, "");
		} else if (value.getValue() == null) {
			logger.debug("Given value for [" + key + "] is null.");
			createTextField(dict, key, "");
		} else {
			createTextField(dict, key, format.format(value.getValue()));
		}
	}
	
	public static void createTimeField(DocumentValueDictionary dict, String key, NTime value, DateFormat format) {
		if (value == null) {
			logger.debug("Given value for [" + key + "] is null.");
			createTextField(dict, key, "");
		} else if (value.getValue() == null) {
			logger.debug("Given value for [" + key + "] is null.");
			createTextField(dict, key, "");
		} else {
			createTextField(dict, key, format.format(value.getValue()));
		}
	}

	public static void createCheckboxField(DocumentValueDictionary dict, String key, boolean value) {
		DocumentValueDictionaryItem dictItem = new DocumentValueDictionaryItem();
		dictItem.setKey(key);
		dictItem.setValue(value ? "\u2612" : "\u2610");
		dict.getItemList().add(dictItem);
	}

	public static void createCheckboxField(DocumentValueDictionary dict, String key, Flag value) {
		DocumentValueDictionaryItem dictItem = new DocumentValueDictionaryItem();
		dictItem.setKey(key);
		if (value == null) {
			dictItem.setValue("\u2610");
		} else if (value.getValue() == null) {
			dictItem.setValue("\u2610");
		} else {
			dictItem.setValue(value.getValue() ? "\u2612" : "\u2610");
		}
		dict.getItemList().add(dictItem);
	}

	public static void createDecimalField(DocumentValueDictionary dict, String key, NDecimal value,
			DecimalFormat formatter) {
		DocumentValueDictionaryItem dictItem = new DocumentValueDictionaryItem();
		dictItem.setKey(key);
		if (value == null) {
			createTextField(dict, key, "");
		} else {
			String decimal = "";
			if (formatter != null) {
				decimal = formatter.format(value.getValue());
			} else {
				decimal = value.getValueAsString();
			}
			createTextField(dict, key, decimal);
		}
	}

	public static void createFloatField(DocumentValueDictionary dict, String key, NFloat value, DecimalFormat formatter) {
		DocumentValueDictionaryItem dictItem = new DocumentValueDictionaryItem();
		dictItem.setKey(key);
		if (value == null) {
			createTextField(dict, key, "");
		} else {
			String decimal = "";
			if (formatter != null) {
				decimal = formatter.format(value.getValue());
			} else {
				decimal = value.getValueAsString();
			}
			createTextField(dict, key, decimal);
		}
	}

	public static void createNumberField(DocumentValueDictionary dict, String key, Number value, NumberFormat formatter) {
		DocumentValueDictionaryItem dictItem = new DocumentValueDictionaryItem();
		dictItem.setKey(key);
		if (value == null) {
			createTextField(dict, key, "");
		} else {
			String decimal = "";
			if (formatter != null) {
				decimal = formatter.format(value);
			} else {
				decimal = value.toString();
			}
			createTextField(dict, key, decimal);
		}
	}

	public static void addListRecord(DocumentValueDictionaryRoot dict, Class<?> listCls,
			DocumentValueDictionaryRecord record) {
		DocumentValueDictionaryList list = getDocumentValueDictionaryList(dict, listCls);
		list.getRecordList().add(record);
	}

	private static DocumentValueDictionaryList getDocumentValueDictionaryList(DocumentValueDictionaryRoot dict,
			Class<?> listCls) {
		DocumentValueDictionaryList out = null;
		for (DocumentValueDictionaryList list : dict.getListList()) {
			if (listCls.getSimpleName().equals(list.getKey().getValue())) {
				out = list;
				break;
			}
		}
		if (out == null) {
			out = new DocumentValueDictionaryList();
			out.setKey(listCls.getSimpleName());
			dict.getListList().add(out);
		}
		return out;
	}

	public static void createTestField(DocumentValueDictionary dict, String key, boolean value) {
		DocumentValueDictionaryItem dictItem = new DocumentValueDictionaryItem();
		dictItem.setKey(key);
		dictItem.setValue(Boolean.toString(value));
		dict.getItemList().add(dictItem);
	}

}
