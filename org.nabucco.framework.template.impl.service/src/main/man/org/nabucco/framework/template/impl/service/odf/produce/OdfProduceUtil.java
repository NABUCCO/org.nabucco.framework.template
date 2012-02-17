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
package org.nabucco.framework.template.impl.service.odf.produce;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import javax.script.ScriptEngineManager;

import org.jopendocument.dom.OOXML;
import org.jopendocument.dom.XMLVersion;
import org.jopendocument.dom.template.Template;
import org.jopendocument.dom.template.TemplateException;
import org.jopendocument.dom.template.engine.DataModel;
import org.jopendocument.dom.template.engine.ScriptEngineDataModel;
import org.jopendocument.util.JDOMUtils;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLogger;
import org.nabucco.framework.base.facade.datatype.logger.NabuccoLoggingFactory;
import org.nabucco.framework.template.facade.datatype.dictionary.DocumentValueDictionaryItem;
import org.nabucco.framework.template.facade.datatype.dictionary.DocumentValueDictionaryList;
import org.nabucco.framework.template.facade.datatype.dictionary.DocumentValueDictionaryRecord;
import org.nabucco.framework.template.facade.datatype.dictionary.DocumentValueDictionaryRoot;

/**
 * OdtProduceUtil.
 * 
 * @author Juergen Weismueller, PRODYNA AG
 */
public class OdfProduceUtil {

    protected static final NabuccoLogger logger = NabuccoLoggingFactory.getInstance().getLogger(OdfProduceUtil.class);

    private static final String PREFIX = "nabucco.framework.template.odf.style";

    public static void createOpenDocument(InputStream in, OutputStream out, DocumentValueDictionaryRoot dict)
            throws TemplateException {

        try {
            Template jOpenDocumentTmpl = new Template(in);

            ScriptEngineManager m = new ScriptEngineManager();
            DataModel model = new ScriptEngineDataModel(m.getEngineByName("javascript"));

            // itemList
            for (DocumentValueDictionaryItem item : dict.getItemList()) {
                if (item.getKey().getValue().matches("test[A-Z].*")) {
                    // put real boolean for test-fields
                    model.put(item.getKey().getValueAsString(),
                            Boolean.parseBoolean(item.getValue().getValueAsString()));
                } else if (item.getKey().getValue().matches("rich[A-Z].*")) {
                    Map<String, String> styles = getStyles();
                    String ooxmlRs = JDOMUtils.output(OOXML.get(XMLVersion.OD).encodeRT(
                            item.getValue().getValueAsString(), styles));
                    ooxmlRs = ooxmlRs.replaceAll(Pattern.quote("\n"), "<text:line-break />");
                    model.put(item.getKey().getValueAsString(), ooxmlRs);
                } else {
                    // else put normal string
                    model.put(item.getKey().getValueAsString(), item.getValue().getValueAsString());
                }
            }

            // listList with recordList
            for (DocumentValueDictionaryList list : dict.getListList()) {
                List<Map<String, String>> items = new ArrayList<Map<String, String>>();
                for (DocumentValueDictionaryRecord record : list.getRecordList()) {
                    Map<String, String> map = new HashMap<String, String>();
                    for (DocumentValueDictionaryItem item : record.getItemList()) {
                        map.put(item.getKey().getValueAsString(), item.getValue().getValueAsString());
                    }
                    items.add(map);
                }
                model.put(list.getKey().getValueAsString(), items);
            }

            jOpenDocumentTmpl.createDocument(model, out);
        } catch (IOException ex) {
            logger.error(ex, "I/O error during ODF creation.");
            throw new TemplateException(ex);
        } catch (TemplateException te) {
            logger.error(te, "Template exception occured during ODF template transformation.");
            throw new TemplateException(te);
        }
    }

    private static Map<String, String> getStyles() {
        Map<String, String> out = new HashMap<String, String>();
        Properties properties = System.getProperties();
        Set<Object> keySet = properties.keySet();
        for (Object object : keySet) {
            String key = String.valueOf(object);
            if (key.startsWith(PREFIX)) {
                String value = System.getProperty(key);
                String style = key.substring(PREFIX.length() + 1);
                out.put(style, value);
            }
        }
        return out;
    }

}
