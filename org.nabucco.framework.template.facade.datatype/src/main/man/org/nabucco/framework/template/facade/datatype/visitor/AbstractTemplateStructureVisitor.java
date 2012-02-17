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

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.TemplateComposite;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.TemplateExtension;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.TemplateStructureItem;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.fields.DynamicElement;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.fields.GroupComposite;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.fields.StaticTextElement;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.fields.TemplateRootComposite;
import org.nabucco.framework.base.facade.datatype.visitor.DatatypeVisitor;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;

/**
 * @author hlibrenz
 *
 */
public abstract class AbstractTemplateStructureVisitor extends DatatypeVisitor  {

	public abstract void visit (TemplateExtension extension) throws VisitorException, IllegalStateException;
	public abstract void visit (TemplateRootComposite composite) throws VisitorException;
	public abstract void visit (GroupComposite composite) throws VisitorException;
	public abstract void visit (StaticTextElement leaf) throws VisitorException;
	public abstract void visit (DynamicElement leaf) throws VisitorException;
	
	/**
	 * Overrides the origin visit method and calls more specific visit methods for
	 * all of our datatypes in the template component.
	 * 
	 * TODO add missing datatype instanceof checks
	 */
	@Override
	public void visit (Datatype datatype) throws VisitorException {
		if (datatype instanceof TemplateExtension) {
			visit ((TemplateExtension) datatype);
		} else if (datatype instanceof TemplateRootComposite) {
			visit ((TemplateRootComposite) datatype);
		} else if (datatype instanceof GroupComposite) {
			visit ((GroupComposite) datatype);
		} else if (datatype instanceof StaticTextElement) {
			visit ((StaticTextElement) datatype);
		} else if (datatype instanceof DynamicElement) {
			visit ((DynamicElement) datatype);
		}

		super.visit(datatype);
	}

	/**
	 * Iterates over all "child" elements in the given composite and calls the accept method on them.
	 * 
	 * @param composite
	 * @throws VisitorException
	 */
	protected void visitChildren(TemplateComposite composite) throws VisitorException {
		NabuccoList<TemplateStructureItem> items = composite.getItemList();

		for (TemplateStructureItem item : items) {
			item.accept(this);
		}
	}

}
