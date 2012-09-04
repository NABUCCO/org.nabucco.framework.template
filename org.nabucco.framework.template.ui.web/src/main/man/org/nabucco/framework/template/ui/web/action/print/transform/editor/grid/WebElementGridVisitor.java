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

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.template.facade.datatype.mapping.TemplateFieldSet;
import org.nabucco.framework.template.ui.web.action.print.transform.editor.WebElementTemplatingVisitor;

/**
 * WebElementGridVisitor
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public abstract class WebElementGridVisitor extends WebElementTemplatingVisitor {

    protected static final String CELL_SPAN_COLUMNS = "cell.span.columns";

    protected static final String CELL_SPAN_ROWS = "cell.span.rows";

    protected static final String CELL_LABEL = "cell.label";

    protected static final String CELL_VALUE = "cell.value";

    protected static final String TABLE_ROW_CELL_LIST = "table.row.cellList";

    protected static final String TABLE_ROW_LIST = "table.rowList";

    protected static final String TABLE_WIDTH = "table.width";

    /**
     * Returns the used width of the map
     * 
     * @param map
     *            the map to calcualte
     * @return
     */
    protected int calculateUsedGridWidth(Map<Integer, Map<Integer, TemplateFieldSet>> map) {
        int width = 0;
        for (Integer y : map.keySet()) {
            Map<Integer, TemplateFieldSet> row = map.get(y);
            for (Integer x : row.keySet()) {
                TemplateFieldSet cell = row.get(x);
                if (cell != null && (x + 1) > width) {
                    width = x + 1;
                }
            }
        }

        return width;
    }

    /**
     * Produces the cell and returns its fieldSet
     * 
     * @param x
     *            x position
     * @param y
     *            y position
     * @return fields set or null if skipping
     */
    protected TemplateFieldSet createCell(Map<Integer, Map<Integer, TemplateFieldSet>> map, int x, int y) {
        if (map.get(y).get(x) == null) {
            TemplateFieldSet item = new TemplateFieldSet();
            map.get(y).put(x, item);
            return item;
        } else {
            logger.debug("Problem by printing of editor grid. The grid is not conform");
        }
        return null;
    }

    /**
     * Produces empty cell and returns its fieldSet
     * 
     * @param x
     *            x position
     * @param y
     *            y position
     * @return
     */
    protected void createEmptyCell(Map<Integer, Map<Integer, TemplateFieldSet>> map, int x, int y) {
        if (map.get(y).get(x) == null) {
            EmptyCell item = new EmptyCell();
            map.get(y).put(x, item);
        } else {
            logger.debug("Problem by printing of editor grid. The grid is not conform");
        }
    }

    /**
     * Initializes the grid map
     * 
     * @param element
     */
    protected Map<Integer, Map<Integer, TemplateFieldSet>> initializeGridMap(int height, int width) {
        Map<Integer, Map<Integer, TemplateFieldSet>> map = new HashMap<Integer, Map<Integer, TemplateFieldSet>>();
        for (int y = 0; y < height; y++) {
            HashMap<Integer, TemplateFieldSet> row = new HashMap<Integer, TemplateFieldSet>();
            map.put(y, row);

            for (int x = 0; x < width; x++) {
                row.put(x, null);
            }
        }

        return map;
    }
}
