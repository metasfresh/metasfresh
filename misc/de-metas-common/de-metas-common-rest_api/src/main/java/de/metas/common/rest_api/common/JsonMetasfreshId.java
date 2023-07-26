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
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class JsonMetasfreshId
{
	int value;

	@JsonCreator
	public static JsonMetasfreshId of(@NonNull final Integer value)
	{
		return new JsonMetasfreshId(value);
	}

	@Nullable
	public static JsonMetasfreshId ofOrNull(@Nullable final Integer value)
	{
		if (isEmpty(value))
		{
			return null;
		}
		return new JsonMetasfreshId(value);
	}

	private static boolean isEmpty(@Nullable final Integer value)
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
}
