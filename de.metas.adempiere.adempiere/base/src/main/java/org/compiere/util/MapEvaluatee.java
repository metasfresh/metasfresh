package org.compiere.util;

import java.util.Map;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class MapEvaluatee implements Evaluatee2
{
	public static MapEvaluatee of(final Map<String, ? extends Object> map)
	{
		return new MapEvaluatee(map);
	}

	private final Map<String, ? extends Object> map;

	private MapEvaluatee(final Map<String, ? extends Object> map)
	{
		super();
		Check.assumeNotNull(map, "map not null");
		this.map = map;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("map", map)
				.toString();
	}

	@Override
	public String get_ValueAsString(final String variableName)
	{
		final Object value = map.get(variableName);
		return value == null ? null : value.toString();
	}

	@Override
	public boolean has_Variable(final String variableName)
	{
		return map.containsKey(variableName);
	}

	@Override
	public String get_ValueOldAsString(final String variableName)
	{
		return null;
	}
}
