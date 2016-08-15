package de.metas.ui.web.window.util;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

/*
 * #%L
 * metasfresh-webui-api
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

@SuppressWarnings("serial")
public final class JSONLookupValue implements Serializable
{
	public static final JSONLookupValue of(final String key, final String value)
	{
		return new JSONLookupValue(ImmutableMap.of(key, value));
	}

	private final Map<String, String> map;

	private JSONLookupValue(final ImmutableMap<String, String> map)
	{
		this.map = map;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(map)
				.toString();
	}

	@JsonAnyGetter
	public Map<String, String> getMap()
	{
		return map;
	}

}
