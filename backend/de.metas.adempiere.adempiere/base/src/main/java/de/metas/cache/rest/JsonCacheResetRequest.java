/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.cache.rest;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.metas.util.StringUtils;
import lombok.NonNull;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class JsonCacheResetRequest
{
	@JsonAnySetter
	@JsonAnyGetter
	@NonNull private final HashMap<String, String> map = new HashMap<>();

	public static JsonCacheResetRequest extractFrom(HttpServletRequest httpRequest)
	{
		final JsonCacheResetRequest request = new JsonCacheResetRequest();
		httpRequest.getParameterMap()
				.forEach((name, values) -> request.setValue(name, values != null && values.length > 0 ? values[0] : null));

		return request;
	}

	public JsonCacheResetRequest setValue(@NonNull String name, @Nullable String value)
	{
		map.put(name, value);
		return this;
	}

	public JsonCacheResetRequest setValue(@NonNull String name, boolean value)
	{
		return setValue(name, String.valueOf(value));
	}

	public boolean getValueAsBoolean(@NonNull final String name)
	{
		return StringUtils.toBoolean(map.get(name));
	}
}
