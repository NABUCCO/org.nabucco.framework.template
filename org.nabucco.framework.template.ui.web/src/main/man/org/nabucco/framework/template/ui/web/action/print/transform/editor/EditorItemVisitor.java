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
package org.nabucco.framework.template.ui.web.action.print.transform.editor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.work.editor.EditArea;
import org.nabucco.framework.base.ui.web.component.work.editor.EditTab;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorItem;
import org.nabucco.framework.base.ui.web.component.work.editor.RelationArea;
import org.nabucco.framework.base.ui.web.component.work.editor.RelationTab;
import org.nabucco.framework.template.facade.datatype.mapping.TemplateFieldFragment;
import org.nabucco.framework.template.facade.datatype.mapping.TemplateFieldSet;
import org.nabucco.framework.template.ui.web.action.print.transform.TemplateVisitorContext;
import org.nabucco.framework.template.ui.web.action.print.transform.editor.grid.EditItemGridVisitor;
import org.nabucco.framework.template.ui.web.action.print.transform.editor.grid.RelationTabGridVisitor;

/**
 * EditorItemVisitor
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class EditorItemVisitor extends WebElementTemplatingVisitor {

    private static final String TEMPLATE_NAME = "template.name";

    private static final String TEMPLATE_DATE = "template.date";

    private static final String SECTOR_LIST_FRAGMENT = "template.sectorList";

    private static final String SECTOR_TABLE_FRAGMENT = "sector.table";

    private static final String SECTOR_BORDERED_TABLE_FRAGMENT = "sector.bordered-table";

    private static final String SECTOR_NAME = "sector.name";

    private static final String DATEFORMAT = "dd.MM.yyyy";

    @Override
    public void visit(EditorItem element, TemplateVisitorContext context) throws VisitorException {
        TemplateFieldSet mapping = context.getMapping();

        String label = element.getModel().getLabel();
        TemplateFieldFragment sectorFragment = new TemplateFieldFragment();
        sectorFragment.setFieldName(SECTOR_LIST_FRAGMENT);

        mapping.getFragments().add(sectorFragment);

        this.createValue(TEMPLATE_NAME, label, mapping);

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATEFORMAT);

        String date = dateFormat.format(new Date());
        this.createValue(TEMPLATE_DATE, date, mapping);

        EditArea editArea = element.getEditArea();
        RelationArea relationArea = element.getRelationArea();

        TemplateVisitorContext subContext = new TemplateVisitorContext(sectorFragment);
        this.visit(editArea, subContext);
        this.visit(relationArea, subContext);
    }

    @Override
    public void visit(EditTab element, TemplateVisitorContext context) throws VisitorException {
        if (element.isTechnical()) {
            return;
        }

        // Header
        TemplateFieldSet tabMapping = new TemplateFieldSet();
        context.getFragment().getFieldSets().add(tabMapping);

        String label = element.getLabel();
        this.createValue(SECTOR_NAME, label, tabMapping);

        // Table
        TemplateFieldFragment tabTableFragment = new TemplateFieldFragment();
        tabTableFragment.setFieldName(SECTOR_TABLE_FRAGMENT);
        tabMapping.getFragments().add(tabTableFragment);

        TemplateFieldSet tabTableMapping = new TemplateFieldSet();
        tabTableFragment.getFieldSets().add(tabTableMapping);
        TemplateVisitorContext editTabContext = new TemplateVisitorContext(tabTableMapping);

        // Editor Visitor
        EditItemGridVisitor gridVisitor = new EditItemGridVisitor();
        element.accept(gridVisitor, editTabContext);
    }

    @Override
    public void visit(RelationTab element, TemplateVisitorContext context) throws VisitorException {
        if (element.isTechnical() || element.getModel().getTableModel().getCurrentPage().size() == 0) {
            return;
        }
        // Header
        TemplateFieldSet tabMapping = new TemplateFieldSet();
        context.getFragment().getFieldSets().add(tabMapping);

        String label = element.getLabel();
        this.createValue(SECTOR_NAME, label, tabMapping);

        // Table
        TemplateFieldFragment tabTableFragment = new TemplateFieldFragment();
        tabTableFragment.setFieldName(SECTOR_BORDERED_TABLE_FRAGMENT);
        tabMapping.getFragments().add(tabTableFragment);

        TemplateFieldSet tabTableMapping = new TemplateFieldSet();
        tabTableFragment.getFieldSets().add(tabTableMapping);
        TemplateVisitorContext editTabContext = new TemplateVisitorContext(tabTableMapping);

        // Editor Visitor
        RelationTabGridVisitor gridVisitor = new RelationTabGridVisitor();
        element.accept(gridVisitor, editTabContext);
    }

}
