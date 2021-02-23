package de.metas.util.lang;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business.rest-api
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

@Value
public class ExternalId
{
	String value;

	@JsonCreator
	@NonNull
	public static ExternalId of(@NonNull final String value)
	{
		return new ExternalId(value);
	}

	@Nullable
	public static ExternalId ofOrNull(@Nullable final String value)
	{
		if (Check.isBlank(value))
		{
			return null;
		}
		return new ExternalId(value);
	}

	@Nullable
	public static String toValue(@Nullable final ExternalId externalId)
	{
		if (externalId == null)
		{
			return null;
		}
		return externalId.getValue();
	}

	@JsonValue
	public String getValue()
	{
		return value;
	}
}
