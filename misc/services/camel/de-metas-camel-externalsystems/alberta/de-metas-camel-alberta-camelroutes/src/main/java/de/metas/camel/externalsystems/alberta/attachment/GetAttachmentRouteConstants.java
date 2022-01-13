/*
 * #%L
 * de-metas-camel-alberta-camelroutes
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.camel.externalsystems.alberta.attachment;

public interface GetAttachmentRouteConstants
{
	String ROUTE_PROPERTY_GET_ATTACHMENT_CONTEXT = "GetAttachmentRouteContext";

	String ESR_TYPE_BPARTNER = "BPartner";
	String ESR_TYPE_USERID = "UserID";

	String ALBERTA_DOCUMENT_ID = "alberta_document_id";
	String ALBERTA_DOCUMENT_THERAPYID = "alberta_document_therapyId";
	String ALBERTA_DOCUMENT_THERAPYTYPEID = "alberta_document_therapyTypeId";
	String ALBERTA_DOCUMENT_ARCHIVED = "alberta_document_archived";
	String ALBERTA_DOCUMENT_CREATEDAT = "alberta_document_createdAt";
	String ALBERTA_DOCUMENT_UPDATEDAT = "alberta_document_updatedAt";
	String ALBERTA_DOCUMENT_ENDPOINT = "alberta_document_endpoint";
	String ALBERTA_DOCUMENT_ENDPOINT_VALUE = "/document";

	String ALBERTA_ATTACHMENT_ID = "alberta_attachment_id";
	String ALBERTA_ATTACHMENT_UPLOAD_DATE = "alberta_attachment_uploadDate";
	String ALBERTA_ATTACHMENT_TYPE = "alberta_attachment_type";
	String ALBERTA_ATTACHMENT_CREATEDAT = "alberta_attachment_createdAt";
	String ALBERTA_ATTACHMENT_ENDPOINT = "alberta_attachment_endpoint";
	String ALBERTA_ATTACHMENT_ENDPOINT_VALUE = "/attachment";

	String MIME_TYPE_PDF = "application/pdf";
}
