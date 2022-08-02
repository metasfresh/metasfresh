/*
 * #%L
 * de-metas-common-rest_api
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

package de.metas.common.rest_api.v2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = JsonError.JsonErrorBuilder.class)
public class JsonError
{
	public static JsonError ofSingleItem(@NonNull final JsonErrorItem item)
	{
		return JsonError.builder().error(item).build();
	}

	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	JsonMetasfreshId requestId;

	List<JsonErrorItem> errors;

	@Builder(toBuilder = true)
	@JsonCreator
	private JsonError(
			@JsonProperty("requestId") @Nullable final JsonMetasfreshId requestId,
			@JsonProperty("errors") @Singular final List<JsonErrorItem> errors)
	{
		this.errors = errors;
		this.requestId = requestId;
	}
}
