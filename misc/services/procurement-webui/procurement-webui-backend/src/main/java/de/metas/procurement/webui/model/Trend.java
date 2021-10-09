package de.metas.procurement.webui.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.NoSuchElementException;






/*
 * #%L
 * de.metas.procurement.webui
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public enum Trend
{
	UP("U", "trend-up"),
	DOWN("D", "trend-down"),
	EVEN("E", "trend-even"),
	ZERO("Z", "trend-zero");

	private static final ImmutableMap<String, Trend> valuesByCode = Maps.uniqueIndex(Arrays.asList(values()), Trend::getCode);
	private static final ImmutableMap<String, Trend> valuesByJson = Maps.uniqueIndex(Arrays.asList(values()), Trend::toJson);

	@Getter
	private final String code;
	private final String json;

	Trend(@NonNull final String code, @NonNull final String json)
	{
		this.code = code;
		this.json = json;
	}

	@Nullable
	public static Trend ofNullableCode(@Nullable final String code)
	{
		return code != null ? valuesByCode.get(code) : null;
	}

	@Nullable
	public static Trend ofNullableJson(@Nullable final String json)
	{
		return json != null ? ofJson(json) : null;
	}

	@JsonCreator
	public static Trend ofJson(@NonNull final String json)
	{
		final Trend value = valuesByJson.get(json);
		if (value == null)
		{
			throw new NoSuchElementException("No Trend found for json `" + json + "`. Available values are: " + valuesByJson.keySet());
		}
		return value;
	}

	@JsonValue
	public String toJson()
	{
		return json;
	}
}
