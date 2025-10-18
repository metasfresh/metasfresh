/*
 * #%L
 * de-metas-common-delivery
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.common.delivery.v1.json.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Value
public class JsonMappingConfigList
{
	public static final JsonMappingConfigList EMPTY = new JsonMappingConfigList(ImmutableList.of());

	@JsonValue
	@NonNull
	List<JsonMappingConfig> configs;

	@JsonCreator
	public static JsonMappingConfigList ofList(@NonNull final List<JsonMappingConfig> items)
	{
		if (items.isEmpty())
		{
			return EMPTY;
		}
		return new JsonMappingConfigList(ImmutableList.copyOf(items));
	}
}