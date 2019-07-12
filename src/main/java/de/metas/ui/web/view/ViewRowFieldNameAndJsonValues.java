package de.metas.ui.web.view;

import java.math.BigDecimal;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONNullValue;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.util.NumberUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
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

@ToString
@EqualsAndHashCode
public final class ViewRowFieldNameAndJsonValues
{
	public static ViewRowFieldNameAndJsonValues ofMap(@NonNull final ImmutableMap<String, Object> map)
	{
		if (map.isEmpty())
		{
			return EMPTY;
		}

		return new ViewRowFieldNameAndJsonValues(map);
	}

	private static final ViewRowFieldNameAndJsonValues EMPTY = new ViewRowFieldNameAndJsonValues();

	private final ImmutableMap<String, Object> map;

	private ViewRowFieldNameAndJsonValues(@NonNull final ImmutableMap<String, Object> map)
	{
		this.map = map;
	}

	private ViewRowFieldNameAndJsonValues()
	{
		map = ImmutableMap.of();
	}

	public ImmutableSet<String> getFieldNames()
	{
		return map.keySet();
	}

	public Object get(@NonNull final String fieldName)
	{
		return map.get(fieldName);
	}

	public Object convertAndGetValue(
			@NonNull final String fieldName,
			@NonNull final JSONOptions jsonOpts)
	{
		final Object valueObj = get(fieldName);

		if (valueObj == null)
		{
			return null;
		}
		else if (valueObj instanceof ITranslatableString)
		{
			return ((ITranslatableString)valueObj).translate(jsonOpts.getAD_Language());
		}
		else
		{
			return valueObj;
		}
	}

	public int getAsInt(@NonNull final String fieldName, final int defaultValueIfNotFound)
	{
		final Object jsonValueObj = get(fieldName);
		if (JSONNullValue.toNullIfInstance(jsonValueObj) == null)
		{
			return defaultValueIfNotFound;
		}
		else if (jsonValueObj instanceof Number)
		{
			return ((Number)jsonValueObj).intValue();
		}
		else if (jsonValueObj instanceof JSONLookupValue)
		{
			return ((JSONLookupValue)jsonValueObj).getKeyAsInt();
		}
		else
		{
			return Integer.parseInt(jsonValueObj.toString());
		}
	}

	public BigDecimal getAsBigDecimal(@NonNull final String fieldName, final BigDecimal defaultValueIfNotFoundOrError)
	{
		final Object jsonValueObj = get(fieldName);
		if (JSONNullValue.isNull(jsonValueObj))
		{
			return defaultValueIfNotFoundOrError;
		}
		else
		{
			return NumberUtils.asBigDecimal(jsonValueObj, defaultValueIfNotFoundOrError);
		}
	}
}
