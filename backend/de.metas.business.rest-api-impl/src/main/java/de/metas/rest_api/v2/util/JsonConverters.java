/*
 * #%L
 * de.metas.business.rest-api-impl
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

package de.metas.rest_api.v2.util;

import de.metas.attachments.AttachmentEntryType;
import de.metas.common.rest_api.v2.JsonPagingDescriptor;
import de.metas.common.rest_api.v2.JsonPagingDescriptor.JsonPagingDescriptorBuilder;
import de.metas.common.rest_api.v2.attachment.JsonAttachmentSourceType;
import de.metas.dao.selection.pagination.PageDescriptor;
import de.metas.dao.selection.pagination.QueryResultPage;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

@UtilityClass
public class JsonConverters
{
	public JsonPagingDescriptor createJsonPagingDescriptor(@NonNull final QueryResultPage<?> page)
	{
		final JsonPagingDescriptorBuilder jsonPagingDescriptor = JsonPagingDescriptor.builder()
				.pageSize(page.getItems().size())
				.resultTimestamp(page.getResultTimestamp().toEpochMilli())
				.totalSize(page.getTotalSize());

		final PageDescriptor nextPageDescriptor = page.getNextPageDescriptor();
		if (nextPageDescriptor != null)
		{
			jsonPagingDescriptor.nextPage(nextPageDescriptor.getPageIdentifier().getCombinedUid());
		}
		return jsonPagingDescriptor.build();
	}

	@NonNull
	public JsonAttachmentSourceType toJsonAttachmentSourceType(@NonNull final AttachmentEntryType type)
	{
		switch (type)
		{
			case Data:
				return JsonAttachmentSourceType.Data;
			case URL:
				return JsonAttachmentSourceType.URL;
			case LocalFileURL:
				return JsonAttachmentSourceType.LocalFileURL;
			default:
				throw new AdempiereException("Unknown AttachmentEntryType")
						.appendParametersToMessage()
						.setParameter("type", type);
		}
	}

	@NonNull
	public AttachmentEntryType toAttachmentType(@NonNull final JsonAttachmentSourceType type)
	{
		switch (type)
		{
			case Data:
				return AttachmentEntryType.Data;
			case URL:
				return AttachmentEntryType.URL;
			case LocalFileURL:
				return AttachmentEntryType.LocalFileURL;
			default:
				throw new AdempiereException("Unknown JsonAttachmentSourceType")
						.appendParametersToMessage()
						.setParameter("type", type);
		}
	}
}
