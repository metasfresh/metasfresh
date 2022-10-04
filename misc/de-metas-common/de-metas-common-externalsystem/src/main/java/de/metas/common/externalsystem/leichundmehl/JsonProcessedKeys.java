/*
 * #%L
 * de-metas-common-externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.common.externalsystem.leichundmehl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = JsonProcessedKeys.JsonProcessedKeysBuilder.class)
public class JsonProcessedKeys
{
	@NonNull
	@JsonProperty("key")
	String key;

	@NonNull
	@JsonProperty("oldValue")
	String oldValue;

	@NonNull
	@JsonProperty("newValue")
	String newValue;

	@Builder
	@JsonCreator
	public JsonProcessedKeys(
			@JsonProperty("key") @NonNull final String key,
			@JsonProperty("oldValue") @NonNull final String oldValue,
			@JsonProperty("newValue") @NonNull final String newValue)
	{
		this.key = key;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
}
