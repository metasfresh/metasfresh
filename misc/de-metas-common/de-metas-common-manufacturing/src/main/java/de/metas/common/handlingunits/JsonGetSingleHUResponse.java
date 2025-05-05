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

package de.metas.common.handlingunits;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.metas.common.rest_api.v2.JsonErrorItem;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonDeserialize(builder = JsonGetSingleHUResponse.JsonGetSingleHUResponseBuilder.class)
public class JsonGetSingleHUResponse
{
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	JsonHU result;

	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	JsonErrorItem error;

	boolean multipleHUsFound;

	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonGetSingleHUResponseBuilder {}

	public static JsonGetSingleHUResponse ofResult(@NonNull final JsonHU result)
	{
		return builder().result(result).build();
	}

	public static JsonGetSingleHUResponse ofError(@NonNull final JsonErrorItem error)
	{
		return builder().error(error).build();
	}

}
