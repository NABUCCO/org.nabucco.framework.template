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
package org.nabucco.framework.template.impl.service.odf.text;

import org.nabucco.common.extension.ExtensionException;
import org.nabucco.framework.base.facade.component.connection.ConnectionException;
import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.base.facade.datatype.collection.NabuccoList;
import org.nabucco.framework.base.facade.datatype.content.ContentEntryType;
import org.nabucco.framework.base.facade.datatype.extension.ExtensionPointType;
import org.nabucco.framework.base.facade.datatype.extension.property.PropertyLoader;
import org.nabucco.framework.base.facade.datatype.extension.schema.template.mapping.TemplateMappingExtension;
import org.nabucco.framework.base.facade.datatype.net.Uri;
import org.nabucco.framework.base.facade.exception.service.ServiceException;
import org.nabucco.framework.base.facade.message.ServiceRequest;
import org.nabucco.framework.base.facade.message.ServiceResponse;
import org.nabucco.framework.content.facade.component.ContentComponent;
import org.nabucco.framework.content.facade.component.ContentComponentLocator;
import org.nabucco.framework.content.facade.datatype.ContentData;
import org.nabucco.framework.content.facade.datatype.ContentEntryElement;
import org.nabucco.framework.content.facade.datatype.ExternalData;
import org.nabucco.framework.content.facade.datatype.InternalData;
import org.nabucco.framework.content.facade.datatype.path.ContentEntryPath;
import org.nabucco.framework.content.facade.message.ContentEntryMaintainPathMsg;
import org.nabucco.framework.content.facade.message.ContentEntryMsg;
import org.nabucco.framework.content.facade.message.ContentEntryPathMsg;
import org.nabucco.framework.content.facade.message.produce.ContentEntryPrototypeRq;
import org.nabucco.framework.content.facade.service.produce.ProduceContent;
import org.nabucco.framework.template.facade.datatype.mapping.TemplateContent;
import org.nabucco.framework.template.facade.datatype.mapping.TemplateFieldSet;
import org.nabucco.framework.template.facade.exception.TemplateException;
import org.nabucco.framework.template.facade.message.odf.text.OdtTemplateRq;
import org.nabucco.framework.template.facade.message.odf.text.OdtTemplateRs;
import org.nabucco.framework.template.impl.service.odf.text.io.OdtFile;
import org.nabucco.framework.template.impl.service.odf.text.io.OdtFileException;
import org.nabucco.framework.template.impl.service.odf.text.io.OdtFileReader;
import org.nabucco.framework.template.impl.service.odf.text.io.OdtFileWriter;
import org.nabucco.framework.template.impl.service.util.mapping.TemplateMapper;

/**
 * CreateTextFileServiceHandlerImpl
 * 
 * @author Nicolas Moser, PRODYNA AG
 */
public class CreateTextFileServiceHandlerImpl extends CreateTextFileServiceHandler {

    /**
     * Comment for <code>TEMP_PROFIL_ODT</code>
     */
    private static final long serialVersionUID = 1L;

    private static final String TEMP = "temp/";

    @Override
    protected OdtTemplateRs createTextFile(OdtTemplateRq msg) throws TemplateException {

        if (msg.getMappingId() == null || msg.getMappingId().getValue() == null) {
            throw new TemplateException("Cannot find template mapping for id 'null'.");
        }

        String mappingId = msg.getMappingId().getValue();

        OdtTemplateRs rs = new OdtTemplateRs();

        try {
            TemplateMappingExtension extension = (TemplateMappingExtension) NabuccoSystem.getExtensionResolver()
                    .resolveExtension(ExtensionPointType.ORG_NABUCCO_FRAMEWORK_TEMPLATE_MAPPING, mappingId);
            String templateName = PropertyLoader.loadProperty(extension.getTemplate());

            OdtFile template = this.loadTemplate(templateName);

            this.modifyTemplate(mappingId, template, msg.getRootField());
            this.addResources(msg.getContentList(), template);

            String url = this.writeContent(template);
            rs.setResourceLocation(new Uri(url));

        } catch (TemplateException te) {
            throw te;
        } catch (Exception e) {
            throw new TemplateException("Error modifying template of extension '" + mappingId + "'.", e);
        }

        return rs;
    }

    /**
     * Add content to the template by resolving data from the content component.
     * 
     * @param contentList
     *            the list of content entries
     * @param template
     *            the template to add the resources to
     * 
     * @throws TemplateException
     *             when a resource cannot be added
     */
    private void addResources(NabuccoList<TemplateContent> contentList, OdtFile template) throws TemplateException {

        for (TemplateContent content : contentList) {

            String path = content.getArchivePath().getValue();

            try {
                ContentEntryElement entry = this.loadContent(content.getContentId());
                byte[] data = this.resolveData(entry);
                template.addResource(path, data);
            } catch (Exception e) {
                throw new TemplateException("Error adding resource '" + path + "' to ODT template.", e);
            }
        }
    }

    /**
     * Resolve the template for the given path.
     * 
     * @param path
     *            the path locating the template in the content structure
     * 
     * @return the ODT Template
     * 
     * @throws TemplateException
     *             when the template cannot be resolved
     */
    private OdtFile loadTemplate(String path) throws TemplateException {

        try {
            ContentEntryElement entry = this.loadContent(path);
            byte[] data = this.resolveData(entry);

            OdtFileReader reader = new OdtFileReader(data);
            OdtFile template = reader.readFile();

            return template;

        } catch (TemplateException te) {
            throw te;
        } catch (ConnectionException ce) {
            throw new TemplateException("Error connecting to content component.", ce);
        } catch (Exception e) {
            throw new TemplateException("Error resolving template '" + path + "'.", e);
        }
    }

    /**
     * Modify the ODT template.
     * 
     * @param mappingId
     *            id of the template mapping extension
     * @param template
     *            the template to modify
     * @param root
     *            the root field set
     * 
     * @throws TemplateException
     *             when the template cannot be modified
     */
    private void modifyTemplate(String mappingId, OdtFile template, TemplateFieldSet rootField)
            throws TemplateException {

        try {
            TemplateMappingExtension extension = (TemplateMappingExtension) NabuccoSystem.getExtensionResolver()
                    .resolveExtension(ExtensionPointType.ORG_NABUCCO_FRAMEWORK_TEMPLATE_MAPPING, mappingId);

            if (extension == null) {
                throw new ExtensionException("No template mapping extension found for mapping id '" + mappingId + "'.");
            }

            TemplateMapper mapper = new TemplateMapper(extension);

            String content = template.getFileContent(OdtFile.ENTRY_CONTENT);
            content = mapper.mapTemplate(content, rootField);
            template.setFileContent(OdtFile.ENTRY_CONTENT, content);

            String styles = template.getFileContent(OdtFile.ENTRY_STYLES);
            styles = mapper.mapTemplate(styles, rootField);
            template.setFileContent(OdtFile.ENTRY_STYLES, styles);

            String manifest = template.getFileContent(OdtFile.ENTRY_MANIFEST);
            manifest = mapper.mapTemplate(manifest, rootField);
            template.setFileContent(OdtFile.ENTRY_MANIFEST, manifest);

        } catch (OdtFileException ofe) {
            throw new TemplateException("Error loading ODT File Template for Extension'" + mappingId + "'.", ofe);
        } catch (ExtensionException ee) {
            throw new TemplateException("Error loading Template Mapping Extension '" + mappingId + "'.", ee);
        }
    }

    /**
     * Loads a content entry for the given path.
     * 
     * @param id
     *            id of the content entry to load
     * 
     * @return the content entry for the given path
     * 
     * @throws TemplateException
     *             when the content cannot be resolved
     */
    private ContentEntryElement loadContent(Identifier id) throws TemplateException {

        try {
            ContentComponent component = ContentComponentLocator.getInstance().getComponent();

            ServiceRequest<ContentEntryMsg> rq = new ServiceRequest<ContentEntryMsg>(super.getContext());
            ContentEntryMsg msg = new ContentEntryMsg();
            InternalData data = new InternalData();
            data.setId(id);
            msg.setContentEntry(data);
            rq.setRequestMessage(msg);

            ServiceResponse<ContentEntryMsg> rs = component.getResolveContent().resolveContentEntry(rq);

            if (rs == null || rs.getResponseMessage() == null) {
                throw new TemplateException("Cannot resolve content for id '" + id + "'.");
            }

            if (rs.getResponseMessage().getContentEntry() == null) {
                throw new TemplateException("Cannot resolve content for id '" + id + "'.");
            }

            return rs.getResponseMessage().getContentEntry();
        } catch (Exception e) {
            throw new TemplateException("Error resolving content with id '" + id + "'.", e);
        }
    }

    /**
     * Loads a content entry for the given path.
     * 
     * @param path
     *            path to the content entry to load
     * 
     * @return the content entry for the given path
     * 
     * @throws TemplateException
     *             when the content cannot be resolved
     */
    private ContentEntryElement loadContent(String path) throws TemplateException {

        try {
            ContentComponent component = ContentComponentLocator.getInstance().getComponent();

            ServiceRequest<ContentEntryPathMsg> rq = new ServiceRequest<ContentEntryPathMsg>(super.getContext());
            ContentEntryPathMsg msg = new ContentEntryPathMsg();
            msg.setPath(new ContentEntryPath(path));
            rq.setRequestMessage(msg);

            ServiceResponse<ContentEntryMsg> rs = component.getResolveContent().resolveContentEntryByPath(rq);

            if (rs == null || rs.getResponseMessage() == null) {
                throw new TemplateException("Cannot resolve content for path '" + path + "'.");
            }

            if (rs.getResponseMessage().getContentEntry() == null) {
                throw new TemplateException("Cannot resolve content for path '" + path + "'.");
            }

            return rs.getResponseMessage().getContentEntry();
        } catch (Exception e) {
            throw new TemplateException("Error resolving content path '" + path + "'.", e);
        }
    }

    /**
     * Resolve the data of a content entry.
     * 
     * @param entry
     *            the entry holding the data
     * 
     * @return the resolved data as byte array
     * 
     * @throws TemplateException
     *             when the template is not valid
     * @throws ServiceException
     *             when an error curing the service call occurs
     * @throws ConnectionException
     *             when the content component cannot be aquired
     */
    private byte[] resolveData(ContentEntryElement entry) throws TemplateException, ServiceException,
            ConnectionException {
        if (entry == null || entry.getType() == ContentEntryType.FOLDER) {
            return null;
        }

        ServiceRequest<ContentEntryMsg> rq = new ServiceRequest<ContentEntryMsg>(super.getContext());
        ContentEntryMsg msg = new ContentEntryMsg();
        msg.setContentEntry(entry);
        rq.setRequestMessage(msg);

        ContentComponent contentComponent = ContentComponentLocator.getInstance().getComponent();
        ServiceResponse<ContentEntryMsg> rs = contentComponent.getResolveContent().resolveContentEntryData(rq);

        if (rs == null || rs.getResponseMessage() == null) {
            throw new TemplateException("Cannot resolve data of content entry '" + entry.getName() + "'.");
        }

        entry = rs.getResponseMessage().getContentEntry();

        switch (entry.getType()) {

        case INTERNAL_DATA: {
            InternalData internal = (InternalData) entry;
            ContentData data = internal.getData();
            return data.getData().getValue();
        }

        case EXTERNAL_DATA:
            ExternalData external = (ExternalData) entry;
            return external.getData().getValue();
        }

        throw new TemplateException("Cannot resolve data of content entry '" + entry.getName() + "'.");
    }

    /**
     * Write the odt file to content component.
     * 
     * @param file
     *            the odt file to write
     * 
     * @return the url to the created file
     * 
     * @throws TemplateException
     *             when the template cannot be written
     */
    private String writeContent(OdtFile file) throws TemplateException {
        String path = TEMP + NabuccoSystem.createUUID() + "/Profile.odt";
        try {

            ContentEntryPrototypeRq produceMessage = new ContentEntryPrototypeRq();
            produceMessage.setType(ContentEntryType.INTERNAL_DATA);

            ServiceRequest<ContentEntryPrototypeRq> rq = new ServiceRequest<ContentEntryPrototypeRq>(super.getContext());
            rq.setRequestMessage(produceMessage);

            ProduceContent produceContent = ContentComponentLocator.getInstance().getComponent().getProduceContent();
            ServiceResponse<ContentEntryMsg> rs = produceContent.produceContentEntry(rq);
            InternalData entry = (InternalData) rs.getResponseMessage().getContentEntry();

            entry.setName("Profil.odt");
            entry.setFileExtension("odt");
            entry.setOwner(super.getContext().getOwner());
            entry.setDatatypeState(DatatypeState.INITIALIZED);

            OdtFileWriter writer = new OdtFileWriter(file);
            byte[] data = writer.toByteArray();

            ContentData contentData = new ContentData();
            contentData.setDatatypeState(DatatypeState.INITIALIZED);
            contentData.setData(data);
            contentData.setByteSize(data.length);

            entry.setData(contentData);

            ContentEntryMaintainPathMsg maintainMessage = new ContentEntryMaintainPathMsg();
            maintainMessage.setPath(new ContentEntryPath(path));
            maintainMessage.setEntry(entry);
            ServiceRequest<ContentEntryMaintainPathMsg> maintainRq = new ServiceRequest<ContentEntryMaintainPathMsg>(
                    super.getContext());
            maintainRq.setRequestMessage(maintainMessage);
            ContentComponent component = ContentComponentLocator.getInstance().getComponent();
            ServiceResponse<ContentEntryMsg> maintainRs = component.getMaintainContent().maintainContentEntryByPath(
                    maintainRq);

            if (maintainRs == null || maintainRs.getResponseMessage() == null) {
                throw new TemplateException("Error writing ODT file '" + path + "' to content.");
            }

            return path.toString();

        } catch (OdtFileException ofe) {
            throw new TemplateException("Error serializing ODT file '" + path + "'.", ofe);
        } catch (Exception e) {
            throw new TemplateException("Error writing ODT file '" + path + "' to content.", e);
        }

    }
}
