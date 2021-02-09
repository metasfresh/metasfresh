package de.metas.rest_api.utils;

import javax.annotation.Nullable;

import de.metas.dao.selection.pagination.PageDescriptor;
import de.metas.dao.selection.pagination.QueryResultPage;
import de.metas.common.rest_api.JsonExternalId;
import de.metas.common.rest_api.JsonPagingDescriptor;
import de.metas.common.rest_api.JsonPagingDescriptor.JsonPagingDescriptorBuilder;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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

	public JsonExternalId toJsonOrNull(@Nullable final ExternalId externalId)
	{
		if (externalId == null)
		{
			return null;
		}

		return JsonExternalId.of(externalId.getValue());
	}

	public ExternalId fromJsonOrNull(@Nullable final JsonExternalId jsonExternalId)
	{
		if (jsonExternalId == null)
		{
			return null;
		}
		return ExternalId.of(jsonExternalId.getValue());
	}
}
