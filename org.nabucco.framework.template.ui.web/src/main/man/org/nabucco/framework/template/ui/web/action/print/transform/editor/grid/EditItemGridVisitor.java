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

import org.nabucco.framework.base.facade.datatype.NBoolean;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.ui.web.component.work.editor.EditTab;
import org.nabucco.framework.base.ui.web.component.work.editor.EditorGridElement;
import org.nabucco.framework.base.ui.web.component.work.editor.control.CheckBoxControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.CurrencyControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.DateControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.DropDownControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.EditorControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.FileControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.ImageControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.PasswordControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.PickerControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.RadioButtonControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.SimpleLabelControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.TextAreaInputControl;
import org.nabucco.framework.base.ui.web.component.work.editor.control.TextInputControl;
import org.nabucco.framework.base.ui.web.component.work.editor.gridelement.LinkElement;
import org.nabucco.framework.base.ui.web.model.editor.control.property.input.CheckBoxControlModel;
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
public class EditItemGridVisitor extends WebElementGridVisitor {

    private static final String CELL_CONTENT_SEPARATOR = ": ";

    /**
     * Creates a new {@link EditItemGridVisitor} instance.
     * 
     */
    public EditItemGridVisitor() {
    }

    @Override
    public void visit(EditTab element, TemplateVisitorContext context) throws VisitorException {
        Integer tabHeight = element.getHeight();
        Integer tabWidth = element.getWidth();
        Map<Integer, Map<Integer, TemplateFieldSet>> map = this.initializeGridMap(tabHeight, tabWidth);

        List<EditorGridElement> allGridElements = element.getAllGridElements();
        for (EditorGridElement gridElement : allGridElements) {
            if (gridElement.isVisible() == false) {
                continue;
            }
            Integer startRow = gridElement.getStartRow();
            Integer startColumn = gridElement.getStartColumn();

            Integer width = gridElement.getWidth();
            Integer height = gridElement.getHeight();

            TemplateFieldSet newCell = this.createCell(map, startColumn, startRow);
            if (newCell == null) {
                continue;
            }
            this.createValue(CELL_SPAN_ROWS, String.valueOf(height), newCell);
            this.createValue(CELL_SPAN_COLUMNS, String.valueOf(width), newCell);

            TemplateVisitorContext itemContext = new TemplateVisitorContext(newCell);
            gridElement.accept(this, itemContext);

            // Fill grid with empty cells
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (x > 0 || y > 0) {
                        this.createEmptyCell(map, startColumn + x, startRow + y);
                    }
                }
            }
        }

        // Remove unused rows -> save place on the paper
        for (int y = 0; y < tabHeight; y++) {
            boolean rowUsed = false;
            for (int x = 0; x < tabWidth; x++) {
                if (map.get(y).get(x) != null) {
                    rowUsed = true;
                    break;
                }
            }

            if (!rowUsed) {
                map.remove(y);
            }
        }

        // Finalize grid to the root structure
        int calculateUsedGridWidth = this.calculateUsedGridWidth(map);
        for (Integer y : map.keySet()) {
            Map<Integer, TemplateFieldSet> row = map.get(y);
            for (Integer x : row.keySet()) {
                TemplateFieldSet cell = row.get(x);
                if (cell == null) {
                    this.createCell(map, x, y);
                }
            }
        }

        // Finalize columns
        this.createValue(TABLE_WIDTH, String.valueOf(calculateUsedGridWidth - 1), context.getMapping());

        // Finalize rows with values
        TemplateFieldFragment rowFragment = new TemplateFieldFragment();
        rowFragment.setFieldName(TABLE_ROW_LIST);
        context.getMapping().getFragments().add(rowFragment);

        for (Integer y : map.keySet()) {
            Map<Integer, TemplateFieldSet> row = map.get(y);
            TemplateFieldSet rowFieldSet = new TemplateFieldSet();
            rowFragment.getFieldSets().add(rowFieldSet);

            TemplateFieldFragment cellListFragment = new TemplateFieldFragment();
            cellListFragment.setFieldName(TABLE_ROW_CELL_LIST);
            rowFieldSet.getFragments().add(cellListFragment);
            for (TemplateFieldSet cell : row.values()) {
                // The empty cells should net be added to the aim map because templating doesnt
                // support polymorphie yet
                if (cell instanceof EmptyCell) {
                    continue;
                } else {
                    cellListFragment.getFieldSets().add(cell);
                }
            }
        }
    }

    @Override
    public void visit(SimpleLabelControl element, TemplateVisitorContext context) throws VisitorException {
        this.createValue(CELL_LABEL, element.getLabel(), context.getMapping());
    }

    @Override
    public void visit(LinkElement element, TemplateVisitorContext context) throws VisitorException {
        this.createValue(CELL_VALUE, element.getLabel(), context.getMapping());
    }

    @Override
    public void visit(TextInputControl element, TemplateVisitorContext context) throws VisitorException {
        this.fillControlValue(element, context);

    }

    @Override
    public void visit(TextAreaInputControl element, TemplateVisitorContext context) throws VisitorException {
        this.fillControlValue(element, context);
    }

    @Override
    public void visit(CheckBoxControl element, TemplateVisitorContext context) throws VisitorException {
        StringBuilder builder = new StringBuilder();

        NBoolean value = ((CheckBoxControlModel) element.getModel()).getValue();
        if (value == null) {
            builder.append("");
        } else if (value.getValue().booleanValue() == true) {
            builder.append("Ja");
        } else if (value.getValue().booleanValue() == false) {
            builder.append("Nein");
        }

        this.createValue(CELL_LABEL, element.getLabel().trim() + CELL_CONTENT_SEPARATOR, context.getMapping());
        this.createValue(CELL_VALUE, builder.toString(), context.getMapping());

    }

    @Override
    public void visit(DateControl element, TemplateVisitorContext context) throws VisitorException {
        this.fillControlValue(element, context);
    }

    @Override
    public void visit(CurrencyControl element, TemplateVisitorContext context) throws VisitorException {
        this.fillControlValue(element, context);
    }

    @Override
    public void visit(DropDownControl element, TemplateVisitorContext context) throws VisitorException {
        this.fillControlValue(element, context);
    }

    @Override
    public void visit(FileControl element, TemplateVisitorContext context) throws VisitorException {
        this.fillControlValue(element, context);
    }

    @Override
    public void visit(ImageControl element, TemplateVisitorContext context) throws VisitorException {
        this.fillControlValue(element, context);
    }

    @Override
    public void visit(PasswordControl element, TemplateVisitorContext context) throws VisitorException {
        this.fillControlValue(element, context);
    }

    @Override
    public void visit(PickerControl element, TemplateVisitorContext context) throws VisitorException {
        this.fillControlValue(element, context);
    }

    @Override
    public void visit(RadioButtonControl element, TemplateVisitorContext context) throws VisitorException {
        this.fillControlValue(element, context);
    }

    /**
     * Fill control values
     * 
     * @param element
     * @param context
     */
    private void fillControlValue(EditorControl element, TemplateVisitorContext context) {
        String label = element.getLabel().trim();
        if (label.length() > 0) {
            label += CELL_CONTENT_SEPARATOR;
        }
        this.createValue(CELL_LABEL, label, context.getMapping());
        this.createValue(CELL_VALUE, element.getModel().getStringValue(), context.getMapping());
    }

}
