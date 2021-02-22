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

package de.metas.common.rest_api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.common.util.EmptyUtil;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class JsonExternalId
{
	String value;

	@JsonCreator
	public static JsonExternalId of(@NonNull final String value)
	{
		return new JsonExternalId(value);
	}

	@Nullable
	public static JsonExternalId ofOrNull(@Nullable final String value)
	{
		if (EmptyUtil.isBlank(value))
		{
			return null;
		}
		return new JsonExternalId(value);
	}

	private JsonExternalId(@NonNull final String value)
	{
		if (value.trim().isEmpty())
		{
			throw new RuntimeException("Param value=" + value + " may not be empty");
		}
		this.value = value;
	}

	@JsonValue
	public String getValue()
	{
		return value;
	}

	public static boolean equals(@Nullable final JsonExternalId id1, @Nullable final JsonExternalId id2)
	{
		return Objects.equals(id1, id2);
	}

	public static String toValue(@Nullable final JsonExternalId externalId)
	{
		if (externalId == null)
		{
			return null;
		}
		return externalId.getValue();
	}

}
