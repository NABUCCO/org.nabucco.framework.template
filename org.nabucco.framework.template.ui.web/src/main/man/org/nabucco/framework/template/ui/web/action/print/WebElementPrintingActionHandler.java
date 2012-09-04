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
package org.nabucco.framework.template.ui.web.action.print;

import org.nabucco.framework.base.facade.datatype.extension.ExtensionId;
import org.nabucco.framework.base.facade.datatype.net.Uri;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorException;
import org.nabucco.framework.base.facade.exception.client.ClientException;
import org.nabucco.framework.base.ui.web.action.WebActionHandlerSupport;
import org.nabucco.framework.base.ui.web.action.parameter.WebActionParameter;
import org.nabucco.framework.base.ui.web.action.result.OpenItem;
import org.nabucco.framework.base.ui.web.action.result.WebActionResult;
import org.nabucco.framework.base.ui.web.component.WebElement;
import org.nabucco.framework.base.ui.web.component.WebElementType;
import org.nabucco.framework.base.ui.web.component.dialog.Dialog;
import org.nabucco.framework.base.ui.web.model.dialog.GridDialogModel;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogGridModelElement;
import org.nabucco.framework.base.ui.web.model.dialog.controls.DialogLinkModel;
import org.nabucco.framework.base.ui.web.servlet.util.NabuccoServletUtil;
import org.nabucco.framework.template.facade.datatype.mapping.TemplateFieldSet;
import org.nabucco.framework.template.facade.exception.TemplateException;
import org.nabucco.framework.template.facade.message.odf.text.OdtTemplateRq;
import org.nabucco.framework.template.facade.message.odf.text.OdtTemplateRs;
import org.nabucco.framework.template.ui.web.action.print.transform.TemplateVisitorContext;
import org.nabucco.framework.template.ui.web.action.print.transform.editor.EditorItemVisitor;
import org.nabucco.framework.template.ui.web.communication.TemplateComponentServiceDelegateFactory;

/**
 * EditorPrintingActionHandler
 * 
 * @author Leonid Agranovskiy, PRODYNA AG
 */
public class WebElementPrintingActionHandler extends WebActionHandlerSupport {

    private static final String TEMPLATE_EXTENSION = "Document";

    private static final String RESOURCE_SERVLET_PREFIX = "./resource/";

    private static final String DIALOG_CONTROL_LINK = "link";

    private static final String DIALOG_ID = "DownloadDialog";

    @Override
    public WebActionResult execute(WebActionParameter parameter) throws ClientException {
        WebElement element = parameter.getElement();

        if (element == null) {
            return null;
        }

        TemplateFieldSet prepareFieldTree = this.prepareFieldTree(element);
        String documentUrl = this.generateFile(prepareFieldTree, parameter);

        Dialog dialog = this.prepareDownloadDialog(documentUrl);

        WebActionResult result = new WebActionResult();
        result.addItem(new OpenItem(WebElementType.DIALOG, dialog.getInstanceId()));
        return result;
    }

    /**
     * Prepares the Field tree
     * 
     * @param element
     *            the element to be transfered
     * @return tree field set
     */
    private TemplateFieldSet prepareFieldTree(WebElement element) throws ClientException {
        try {
            EditorItemVisitor editorVisitor = new EditorItemVisitor();
            TemplateFieldSet templateFieldSet = new TemplateFieldSet();
            TemplateVisitorContext context = new TemplateVisitorContext(templateFieldSet);
            element.accept(editorVisitor, context);

            return templateFieldSet;
        } catch (VisitorException e) {
            throw new ClientException("Problem by visitor of web element", e);
        }
    }

    /**
     * Generates the export file for the given tree
     * 
     * @param fieldTree
     */
    private String generateFile(TemplateFieldSet rootField, WebActionParameter parameter) throws ClientException {
        try {
            OdtTemplateRq rq = new OdtTemplateRq();
            rq.setMappingId(new ExtensionId(TEMPLATE_EXTENSION));
            rq.setRootField(rootField);

            OdtTemplateRs rs = TemplateComponentServiceDelegateFactory.getInstance().getOdtTemplateService()
                    .createTextFile(rq, parameter.getSession());

            return this.createUrl(rs.getResourceLocation());
        } catch (TemplateException te) {
            throw new ClientException("Cannot create Printing for editor '" + rootField.getFieldName() + "'.");
        }
    }

    /**
     * Prepares download dialog
     * 
     * @param url
     *            the url to be downloaded
     * @return dialog instance
     * @throws ClientException
     *             if cannot prepare dialog
     */
    private Dialog prepareDownloadDialog(String url) throws ClientException {
        Dialog dialog = NabuccoServletUtil.getDialogController().createDialog(DIALOG_ID);

        if (dialog == null) {
            throw new ClientException("Cannot create dialog with id '" + DIALOG_ID + "'.");
        }

        DialogGridModelElement control = ((GridDialogModel) dialog.getModel()).getControl(DIALOG_CONTROL_LINK);
        if (control instanceof DialogLinkModel == false) {
            throw new ClientException("The dialog window has no element Link or it has a wrong type");
        }
        DialogLinkModel downloadLinkControl = (DialogLinkModel) control;
        downloadLinkControl.setUrl(url);
        return dialog;
    }

    /**
     * Create the profile url to the created profile.
     * 
     * @param uri
     *            the relative uri
     * 
     * @return the correct servlet mapped uri
     * 
     * @throws ClientException
     *             when the uri is not valid
     */
    private String createUrl(Uri uri) throws ClientException {
        if (uri == null || uri.getValue() == null) {
            throw new ClientException("No valid URL returned!");
        }
        return RESOURCE_SERVLET_PREFIX + uri;
    }

}
