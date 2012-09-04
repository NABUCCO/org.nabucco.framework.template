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
package org.nabucco.framework.template.ui.web.action.print.transform.editor.grid;

import java.util.List;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.work.editor.RelationTab;
import org.nabucco.framework.base.ui.web.model.common.renderer.WebLabelProvider;
import org.nabucco.framework.base.ui.web.model.common.renderer.WebLabelProviderFactory;
import org.nabucco.framework.base.ui.web.model.table.TableColumn;
import org.nabucco.framework.template.facade.datatype.mapping.TemplateFieldFragment;
import org.nabucco.framework.template.facade.datatype.mapping.TemplateFieldSet;
import org.nabucco.framework.template.ui.web.action.print.transform.TemplateVisitorContext;

/**
 * EditItemGridVisitor
 * 
 * This editor analyses the grid and prepares a transformation to the table
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class RelationTabGridVisitor extends WebElementGridVisitor {

    /**
     * Creates a new {@link RelationTabGridVisitor} instance.
     * 
     */
    public RelationTabGridVisitor() {
    }

    @Override
    public void visit(RelationTab element, TemplateVisitorContext context) throws VisitorException {
        List<Datatype> currentPage = element.getModel().getTableModel().getCurrentPage();
        List<TableColumn> columnList = element.getModel().getTableModel().getColumnList();

        Integer tabHeight = currentPage.size();
        Integer tabWidth = columnList.size();

        // Prepare data in the virtual map
        Map<Integer, Map<Integer, TemplateFieldSet>> map = this.initializeGridMap(tabHeight, tabWidth);
        WebLabelProviderFactory<Datatype> labelProviderFactory = element.getModel().getTableModel()
                .getLabelProviderFactory();

        for (int y = 0; y < tabHeight; y++) {
            Datatype datatype = currentPage.get(y);
            WebLabelProvider<Datatype> labelProvider = labelProviderFactory.createLabelProvider(datatype);
            for (int x = 0; x < tabWidth; x++) {
                TableColumn column = columnList.get(x);
                TemplateFieldSet newCell = this.createCell(map, x, y);
                if (newCell == null) {
                    throw new VisitorException("Problem by visiting of relation tab grid, grid is inconsistent");
                }
                String label = labelProvider.getLabel(column.getPropertyPath(), column.getRenderer());
                this.createValue(CELL_SPAN_ROWS, String.valueOf(1), newCell);
                this.createValue(CELL_SPAN_COLUMNS, String.valueOf(1), newCell);
                this.createValue(CELL_VALUE, label, newCell);

            }
        }

        // Finalize columns
        this.createValue(TABLE_WIDTH, String.valueOf(tabWidth - 1), context.getMapping());

        // Finalize rows with values
        TemplateFieldFragment rowFragment = new TemplateFieldFragment();
        rowFragment.setFieldName(TABLE_ROW_LIST);
        context.getMapping().getFragments().add(rowFragment);

        // Print column header names
        TemplateFieldSet headerRowFieldSet = new TemplateFieldSet();
        rowFragment.getFieldSets().add(headerRowFieldSet);

        TemplateFieldFragment headerRowCellFragment = new TemplateFieldFragment();
        headerRowCellFragment.setFieldName(TABLE_ROW_CELL_LIST);
        headerRowFieldSet.getFragments().add(headerRowCellFragment);

        for (int x = 0; x < tabWidth; x++) {
            TableColumn column = columnList.get(x);

            TemplateFieldSet headerCell = new TemplateFieldSet();
            this.createValue(CELL_SPAN_ROWS, String.valueOf(1), headerCell);
            this.createValue(CELL_SPAN_COLUMNS, String.valueOf(1), headerCell);
            this.createValue(CELL_LABEL, column.getLabel(), headerCell);

            headerRowCellFragment.getFieldSets().add(headerCell);
        }

        // Print visible datarows
        for (int y = 0; y < tabHeight; y++) {
            TemplateFieldSet rowFieldSet = new TemplateFieldSet();
            rowFragment.getFieldSets().add(rowFieldSet);

            TemplateFieldFragment cellListFragment = new TemplateFieldFragment();
            cellListFragment.setFieldName(TABLE_ROW_CELL_LIST);
            rowFieldSet.getFragments().add(cellListFragment);

            for (int x = 0; x < tabWidth; x++) {
                TemplateFieldSet cell = map.get(y).get(x);
                if (cell instanceof EmptyCell) {
                    throw new VisitorException("Illegal empty cell in relation grid");
                } else {
                    cellListFragment.getFieldSets().add(cell);
                }
            }

        }
    }

}
