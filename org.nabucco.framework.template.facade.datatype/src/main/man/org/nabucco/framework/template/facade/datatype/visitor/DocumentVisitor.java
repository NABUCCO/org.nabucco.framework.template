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
package org.nabucco.framework.template.facade.datatype.visitor;

import org.nabucco.framework.base.facade.datatype.extension.schema.template.TemplateExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.fields.DynamicElement;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.fields.GroupComposite;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.fields.StaticTextElement;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.fields.TemplateRootComposite;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.template.facade.datatype.context.TemplateContext;
import org.nabucco.framework.template.facade.datatype.dictionary.DocumentValueDictionary;
import org.nabucco.framework.template.facade.datatype.dictionary.DocumentValueDictionaryItem;
import org.nabucco.framework.template.facade.datatype.dictionary.DocumentValueDictionaryRoot;

/**
 * 
 * @author Holger Librenz
 */
public class DocumentVisitor extends AbstractTemplateStructureVisitor {

	protected static final NabuccoLogger logger = NabuccoLoggingFactory
			.getInstance().getLogger(DocumentVisitor.class);

	private static final String EXCEPTION_TEXT_NO_ID = 
		"Attribut 'id' must be set in Template if you want to transform it to documents.";

	private final DocumentValueDictionary dict; 
	private final TemplateContext context;

	public DocumentVisitor (TemplateContext context) {
		super();
		this.dict = new DocumentValueDictionaryRoot();
		this.context = context;
	}
	
	public DocumentVisitor (DocumentValueDictionary dict) {
		super();
		this.dict = dict;
		this.context = new TemplateContext();
	}

	public DocumentVisitor (DocumentValueDictionary dict, TemplateContext context) {
		super();
		this.dict = dict;
		this.context = context;
	}

	@Override
	public void visit (TemplateExtension extension) throws VisitorException, IllegalStateException {
		logger.debug("Visiting TemplateExtension.");
		visitChildren(extension);
	}

	@Override
	public void visit (TemplateRootComposite composite) throws VisitorException {
		logger.debug ("Visiting TemplateRootComposite");
		this.visitChildren(composite);
	}

	public void visit (GroupComposite composite) throws VisitorException {
		logger.debug("Visiting GroupComposite.");
		this.visitChildren(composite);
	}

	public void visit (StaticTextElement leaf) throws VisitorException {
		logger.debug("Visiting StaticTextElement");

		String key;
		String value;

		if (leaf.getItemId() == null) {
			VisitorException ve = new VisitorException(EXCEPTION_TEXT_NO_ID);
			logger.error (ve, ve.getMessage());
			throw ve;
		}

		key = leaf.getItemId().getValue().getValueAsString();
		
		if (leaf.getContent() == null) {
			value = "";
		} else {
			value = leaf.getContent().getValue().getValueAsString();
		}

		this.dict.getItemList().add (this.createDictionaryItem(key, value));
	}

	public void visit (DynamicElement leaf) throws VisitorException {
		logger.debug("Visiting DynamicElement");

		if (leaf.getItemId() == null) {
			VisitorException ve = new VisitorException(EXCEPTION_TEXT_NO_ID);
			logger.error (ve, ve.getMessage());
			throw ve;			
		}

		String key;
		String value;
		
		key = leaf.getItemId().getValue().getValueAsString();
		
		if (leaf.getValue() == null)  {
			value = "";
		} else {
			value = leaf.getValue().getValueAsString();
		}
		
		this.dict.getItemList().add(this.createDictionaryItem(key, value));
	}

	protected DocumentValueDictionaryItem createDictionaryItem (String key, String value) {
		DocumentValueDictionaryItem item = new DocumentValueDictionaryItem();
		item.setKey(key);
		
		if (value.substring(0, 1).equals("$")) {
			item.setValue(this.context.getAttributeAsString(value));
		} else {
			item.setValue(value);
		}

		return item;
	}

	/**
	 * @return the dict
	 */
	public DocumentValueDictionary getDict() {
		return dict;
	}

	/**
	 * @return the context
	 */
	public TemplateContext getContext() {
		return context;
	}

}
