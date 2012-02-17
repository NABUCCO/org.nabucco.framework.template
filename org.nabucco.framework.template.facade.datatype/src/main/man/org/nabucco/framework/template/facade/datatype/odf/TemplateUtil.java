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
package org.nabucco.framework.template.facade.datatype.odf;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.framework.base.facade.datatype.NabuccoSystem;
import org.nabucco.framework.template.facade.datatype.TemplateData;

/**
 * Little helper util class for Template component
 * 
 * @author Holger Librenz
 */
public class TemplateUtil {

	/**
	 * This method maps a given OpenDocument type to it's corresponding mime type.
	 * The mime types were found here: (@link http://de.wikipedia.org/wiki/OpenDocument#Dateiendungen_und_MIME-Typ).
	 * 
	 * @param type Document type, you needs the mime type for
	 * @return The corresponding mime type. Default: OOo text file mime type
	 */
	public static String getMimeType (OpenDocumentType type) {
		switch (type) {
		case OPENDOCUMENTTEXT:
			return "application/vnd.oasis.opendocument.text";
		case OPENDOCUMENTSPREADSHEET:
			return "application/vnd.oasis.opendocument.spreadsheet";
		case OPENDOCUMENTPRESENTATION:
			return "application/vnd.oasis.opendocument.presentation";
		case OPENDOCUMENTTEXTTEMPLATE:
			return "application/vnd.oasis.opendocument.text-template";
		case OPENDOCUMENTSPREADSHEETTEMPLATE:
			return "application/vnd.oasis.opendocument.spreadsheet-template";
		default:
			// well, we cannot do very much in this case, so let us guess it is a text file...
			return "application/vnd.oasis.opendocument.text";
		}
	}

	/**
	 * Creates a TemplateData instance based on the values passed thru as arguments.
	 * 
	 * @param data The byte array with the raw date.
	 * @param fileName The file name, that the byte array contains. The extension will be created by OpenDocumentType
	 * @param createdUserId The user ID of the creating user.
	 * @param docType The OpenDocument type you have created.
	 * 
	 * @return
	 */
	public static TemplateData generateOdfTemplateData (byte[] data, String fileName, String createdUserId, OpenDocumentType docType) {
		TemplateData output = new TemplateData();
		output.setDatatypeState(DatatypeState.INITIALIZED);

		output.setFileName(fileName);
		output.setFileExtension(docType.toString().toLowerCase());
		output.setData(data);
		output.setByteSize(data.length);
		output.setCreatedTimestamp(NabuccoSystem.getCurrentTimestamp());
		output.setCreatedUserId(createdUserId);
		output.setMimeType(TemplateUtil.getMimeType(docType));

		return output;
	}
	
}
