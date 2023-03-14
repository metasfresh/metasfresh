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

package de.metas.common.rest_api.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.common.util.Check;
import de.metas.common.util.NumberUtils;
import io.swagger.v3.oas.models.media.IntegerSchema;
import lombok.NonNull;
import lombok.Value;
import org.springdoc.core.SpringDocUtils;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Value
public class JsonMetasfreshId
{
	int value;

	static {
		SpringDocUtils.getConfig().replaceWithSchema(JsonMetasfreshId.class, new IntegerSchema());
	}

	@JsonCreator
	public static JsonMetasfreshId of(@NonNull final Object value)
	{
		try
		{
			return new JsonMetasfreshId(NumberUtils.asInt(value));
		}
		catch (Exception ex)
		{
			throw Check.mkEx("Invalid " + JsonMetasfreshId.class.getSimpleName() + ": `" + value + "` (" + value.getClass() + ")", ex);
		}
	}

	@Nullable
	public static JsonMetasfreshId ofOrNull(@Nullable final Integer value)
	{
		if (isNullOrNegative(value))
		{
			return null;
		}
		return new JsonMetasfreshId(value);
	}

	@Nullable
	public static JsonMetasfreshId ofOrNull(@Nullable final String value)
	{
		return Optional.ofNullable(value)
				.map(Integer::parseInt)
				.map(JsonMetasfreshId::ofOrNull)
				.orElse(null);
	}

	private static boolean isNullOrNegative(@Nullable final Integer value)
	{
		return value == null || value < 0;
	}

	private JsonMetasfreshId(@NonNull final Integer value)
	{
		if (value < 0)
		{
			throw new RuntimeException("Given value=" + value + " needs to be >=0");
		}
		this.value = value;
	}

	@JsonValue
	public int getValue()
	{
		return value;
	}

	@NonNull
	public <T> T mapValue(@NonNull final Function<Integer, T> mapper) {
		return mapper.apply(value);
	}

	public static boolean equals(@Nullable final JsonMetasfreshId id1, @Nullable final JsonMetasfreshId id2)
	{
		return Objects.equals(id1, id2);
	}

	@Nullable
	public static Integer toValue(@Nullable final JsonMetasfreshId externalId)
	{
		if (externalId == null)
		{
			return null;
		}
		return externalId.getValue();
	}

	public static int toValueInt(@Nullable final JsonMetasfreshId externalId)
	{
		if (externalId == null)
		{
			return -1;
		}
		return externalId.getValue();
	}

	public static String toValueStr(@Nullable final JsonMetasfreshId externalId)
	{
		if (externalId == null)
		{
			return "-1";
		}
		return String.valueOf(externalId.getValue());
	}

	@NonNull
	public static Optional<Integer> toValueOptional(@Nullable final JsonMetasfreshId externalId)
	{
		return Optional.ofNullable(toValue(externalId));
	}

	@Nullable
	public static <T> T mapToOrNull(@Nullable final JsonMetasfreshId externalId, @NonNull final Function<Integer, T> mapper)
	{
		return toValueOptional(externalId).map(mapper).orElse(null);
	}

	@Nullable
	public static String toValueStrOrNull(@Nullable final JsonMetasfreshId externalId)
	{
		if (externalId == null)
		{
			return null;
		}
		return String.valueOf(externalId.getValue());
	}
}
