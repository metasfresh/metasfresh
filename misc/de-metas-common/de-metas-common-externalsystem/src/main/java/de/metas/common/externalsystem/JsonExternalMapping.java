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

package de.metas.common.externalsystem;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
@JsonDeserialize(builder = JsonExternalMapping.JsonExternalMappingBuilder.class)
public class JsonExternalMapping
{
	@NonNull
	@JsonProperty("externalValue")
	String externalValue;

	@Nullable
	@JsonProperty("metasfreshId")
	JsonMetasfreshId metasfreshId;

	@Nullable
	@JsonProperty("value")
	String value;

	@NonNull
	public static JsonExternalMapping of(@NonNull final String externalReference, @NonNull final JsonMetasfreshId metasfreshId)
	{
		return JsonExternalMapping.builder()
				.metasfreshId(metasfreshId)
				.externalValue(externalReference)
				.build();
	}

	@NonNull
	public static JsonExternalMapping of(@NonNull final String externalReference, @Nullable final String value)
	{
		return JsonExternalMapping.builder()
				.externalValue(externalReference)
				.value(value)
				.build();
	}

	@Builder
	public JsonExternalMapping(
			@NonNull @JsonProperty("externalValue") final String externalValue,
			@Nullable @JsonProperty("metasfreshId") final JsonMetasfreshId metasfreshId,
			@Nullable @JsonProperty("value") final String value)
	{
		Check.assume(metasfreshId != null || value != null, "JsonExternalMapping.Value and JsonExternalMapping.MetasfreshId cannot be missing in the same time!");

		this.externalValue = externalValue;
		this.metasfreshId = metasfreshId;
		this.value = value;
	}
}
