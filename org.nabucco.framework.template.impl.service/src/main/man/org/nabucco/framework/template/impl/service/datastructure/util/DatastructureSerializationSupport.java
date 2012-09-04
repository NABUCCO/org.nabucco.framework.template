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
package org.nabucco.framework.template.impl.service.datastructure.util;

import java.util.List;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.serialization.DeserializationData;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationException;
import org.nabucco.framework.base.facade.datatype.serialization.SerializationResult;
import org.nabucco.framework.base.facade.datatype.serialization.xml.XmlSerializer;
import org.nabucco.framework.base.facade.datatype.template.datastructure.TemplateDSInstanceElement;
import org.nabucco.framework.template.facade.datatype.TemplateDatastructure;

/**
 * DatastructureXmlSupport
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class DatastructureSerializationSupport {

    /**
     * Serialize the datastructure to xml.
     * 
     * @param ds
     *            the datastructure to serialize
     * 
     * @return the serialized datastructure
     * 
     * @throws SerializationException
     *             when the datastructure serialization fails
     */
    public static void serialize(TemplateDatastructure ds) throws SerializationException {
        if (ds == null || ds.getDatatstructure() == null) {
            return;
        }

        XmlSerializer serializer = new XmlSerializer();
        SerializationResult result = serializer.serialize(ds.getDatatstructure());
        ds.setXml(result.getContent());
    }

    /**
     * Deserializes the datastructure from xml.
     * 
     * @param ds
     *            the datastructure to deserialize
     * 
     * @return the deserialized datastructure
     * 
     * @throws SerializationException
     *             when the datastructure deserialization fails
     */
    public static void deserialize(TemplateDatastructure ds) throws SerializationException {
        if (ds == null || ds.getXml() == null || ds.getXml().getValue() == null) {
            return;
        }

        XmlSerializer serializer = new XmlSerializer();
        List<Datatype> datatypes = serializer.deserialize(new DeserializationData(ds.getXml().getValue()));

        if (datatypes.isEmpty()) {
            return;
        }

        Datatype datatype = datatypes.get(0);

        if (!(datatype instanceof TemplateDSInstanceElement)) {
            throw new SerializationException(
                    "Error deserializing Datastructure. Serialized datatype is not of type TemplateDSInstanceElement.");
        }

        ds.setDatatstructure((TemplateDSInstanceElement) datatype);
    }
}
