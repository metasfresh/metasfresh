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

import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import io.swagger.client.api.AttachmentApi;
import io.swagger.client.api.DocumentApi;
import io.swagger.client.api.UserApi;
import io.swagger.client.model.Attachment;
import io.swagger.client.model.Document;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.Instant;

@Data
@Builder
public class GetAttachmentRouteContext
{
	@NonNull
	private final String orgCode;

	@NonNull
	private final String apiKey;

	@NonNull
	private final String tenant;

	@NonNull
	private final DocumentApi documentApi;

	@NonNull
	private final AttachmentApi attachmentApi;

	@NonNull
	private final UserApi userApi;

	@NonNull
	private final String createdAfterDocument;

	@NonNull
	private final String createdAfterAttachment;

	@NonNull
	private final JsonMetasfreshId rootBPartnerIdForUsers;

	@NonNull
	private final JsonExternalSystemRequest request;

	@NonNull
	private Instant nextDocumentImportStartDate;

	@NonNull
	private Instant nextAttachmentImportStartDate;

	@Nullable
	private Document document;

	@Nullable
	private Attachment attachment;

	public void setNextDocumentImportStartDate(@NonNull final Instant candidate)
	{
		if (candidate.isAfter(this.nextDocumentImportStartDate))
		{
			this.nextDocumentImportStartDate = candidate;
		}
	}

	public void setNextAttachmentImportStartDate(@NonNull final Instant candidate)
	{
		if (candidate.isAfter(this.nextAttachmentImportStartDate))
		{
			this.nextAttachmentImportStartDate = candidate;
		}
	}
}