package de.metas.ui.web.document.filter.sql;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import de.metas.util.GuavaCollectors;
import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class SqlDocumentFilterConverterContext
{
	public static SqlDocumentFilterConverterContext ofMap(final Map<String, Object> map)
	{
		if (map == null || map.isEmpty())
		{
			return EMPTY;
		}
		return new SqlDocumentFilterConverterContext(toImmutableMap(map));
	}

	private static ImmutableMap<String, Object> toImmutableMap(final Map<String, Object> map)
	{
		if (map instanceof ImmutableMap)
		{
			return (ImmutableMap<String, Object>)map;
		}
		else
		{
			return map.entrySet()
					.stream()
					.filter(entry -> entry.getKey() != null && entry.getValue() != null)
					.collect(GuavaCollectors.toImmutableMap());
		}
	}

	public static final SqlDocumentFilterConverterContext EMPTY = new SqlDocumentFilterConverterContext();

	private final ImmutableMap<String, Object> params;

	@Builder
	private SqlDocumentFilterConverterContext(
			@NonNull @Singular final ImmutableMap<String, Object> params)
	{
		this.params = ImmutableMap.copyOf(params);
	}

	private SqlDocumentFilterConverterContext()
	{
		params = ImmutableMap.of();
	}

	public int getPropertyAsInt(@NonNull final String name, final int defaultValue)
	{
		final ImmutableMap<String, Object> params = getParams();
		final Object value = params.get(name);
		return NumberUtils.asInt(value, defaultValue);
	}
}
