/*
 * #%L
 * de-metas-common-manufacturing
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

package de.metas.common.manufacturing.v2;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.collect.ImmutableList;

import de.metas.common.rest_api.v2.JsonError;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonDeserialize(builder = JsonRequestSetOrdersExportStatusBulk.JsonRequestSetOrdersExportStatusBulkBuilder.class)
public class JsonRequestSetOrdersExportStatusBulk
{
	@NonNull
	String transactionKey;

	@NonNull
	@Singular
	List<JsonRequestSetOrderExportStatus> items;

	/** If not null, then this error applies to all included items */
	JsonError error;

	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonRequestSetOrdersExportStatusBulkBuilder
	{
	}

	public JsonRequestSetOrdersExportStatusBulk withError(@NonNull final JsonError error)
	{
		return toBuilder()
				.error(error)
				.clearItems().items(this.items.stream()
						.map(JsonRequestSetOrderExportStatus::withError)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

}
