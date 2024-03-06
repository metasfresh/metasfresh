/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.calendar.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.ui.web.window.datatypes.json.DateTimeConverters;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static de.metas.ui.web.window.datatypes.json.DateTimeConverters.fromObjectToInstant;

/**
 * A helper class to precisely control how {@link java.time.ZonedDateTime}s are serialized/deserialized from/to JSON.
 */
@Value
public class JsonDateTime
{
	String string;

	private JsonDateTime(@NonNull final String string)
	{
		this.string = string;
	}

	@Nullable
	@JsonCreator
	public static JsonDateTime ofNullableString(@Nullable final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		return stringNorm != null ? new JsonDateTime(stringNorm) : null;
	}

	@NonNull
	public static JsonDateTime ofZonedDateTime(@NonNull final ZonedDateTime dateTime, @NonNull final ZoneId timeZone)
	{
		final String string = DateTimeConverters.toJson(dateTime, timeZone);
		return new JsonDateTime(string);
	}

	@NonNull
	public static JsonDateTime ofInstant(@NonNull final Instant dateTime, @NonNull final ZoneId timeZone)
	{
		final String string = DateTimeConverters.toJson(dateTime, timeZone);
		return new JsonDateTime(string);
	}

	@JsonValue
	public String toJson() {return string;}

	@Override
	@Deprecated
	public String toString() {return toJson();}

	public Instant toInstant() {return fromObjectToInstant(string);}
}
