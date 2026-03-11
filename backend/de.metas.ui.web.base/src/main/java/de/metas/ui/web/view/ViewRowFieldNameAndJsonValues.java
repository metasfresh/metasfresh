package de.metas.ui.web.view;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONNullValue;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.util.NumberUtils;
import de.metas.util.StringUtils;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

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

	public Comparable<?> getAsComparable(
			@NonNull final String fieldName,
			@NonNull final JSONOptions jsonOpts)
	{
		final Object valueObj = map.get(fieldName);

		if (JSONNullValue.isNull(valueObj))
		{
			return null;
		}
		else if (valueObj instanceof ITranslatableString)
		{
			return ((ITranslatableString)valueObj).translate(jsonOpts.getAdLanguage());
		}
		else if (valueObj instanceof Comparable)
		{
			return (Comparable<?>)valueObj;
		}
		else
		{
			return valueObj.toString();
		}
	}

	public Object getAsJsonObject(
			@NonNull final String fieldName,
			@NonNull final JSONOptions jsonOpts)
	{
		final Object valueObj = map.get(fieldName);

		if (JSONNullValue.isNull(valueObj))
		{
			return null;
		}
		else if (valueObj instanceof ITranslatableString)
		{
			return ((ITranslatableString)valueObj).translate(jsonOpts.getAdLanguage());
		}
		else
		{
			return Values.valueToJsonObject(valueObj, jsonOpts);
		}
	}

	public int getAsInt(@NonNull final String fieldName, final int defaultValueIfNotFoundOrError)
	{
		final Object valueObj = map.get(fieldName);

		if (JSONNullValue.toNullIfInstance(valueObj) == null)
		{
			return defaultValueIfNotFoundOrError;
		}
		else if (valueObj instanceof Number)
		{
			return ((Number)valueObj).intValue();
		}
		else if (valueObj instanceof LookupValue)
		{
			return ((LookupValue)valueObj).getIdAsInt();
		}
		else if (valueObj instanceof JSONLookupValue)
		{
			return ((JSONLookupValue)valueObj).getKeyAsInt();
		}
		else
		{
			return NumberUtils.asInt(valueObj, defaultValueIfNotFoundOrError);
		}
	}

	public BigDecimal getAsBigDecimal(@NonNull final String fieldName, final BigDecimal defaultValueIfNotFoundOrError)
	{
		final Object valueObj = map.get(fieldName);

		if (JSONNullValue.isNull(valueObj))
		{
			return defaultValueIfNotFoundOrError;
		}
		else
		{
			return NumberUtils.asBigDecimal(valueObj, defaultValueIfNotFoundOrError);
		}
	}

	public boolean getAsBoolean(@NonNull final String fieldName, final boolean defaultValueIfNotFoundOrError)
	{
		final Object valueObj = map.get(fieldName);
		//noinspection DataFlowIssue
		return StringUtils.toBoolean(valueObj, defaultValueIfNotFoundOrError);
	}

	@NonNull
	public <T extends ReferenceListAwareEnum> T getAsEnum(@NonNull final String fieldName, @NonNull final Class<T> type)
	{
		final Object valueObj = map.get(fieldName);

		if (valueObj == null || JSONNullValue.isNull(valueObj))
		{
			throw new AdempiereException("Value is null for " + fieldName);
		}
		else if (type.isInstance(valueObj))
		{
			return type.cast(valueObj);
		}
		else if (valueObj instanceof LookupValue)
		{
			final String code = ((LookupValue)valueObj).getIdAsString();
			return ReferenceListAwareEnums.ofCode(code, type);
		}
		else
		{
			final String code = valueObj.toString();
			return ReferenceListAwareEnums.ofCode(code, type);
		}
	}

	@Nullable
	public Instant getAsInstant(@NonNull final String fieldName)
	{
		final Object valueObj = map.get(fieldName);
		return TimeUtil.asInstant(valueObj);
	}

	@Nullable
	public String getAsString(@NonNull final String fieldName)
	{
		final Object valueObj = map.get(fieldName);

		if (valueObj == null || JSONNullValue.isNull(valueObj))
		{
			return null;
		}
		else if (valueObj instanceof LookupValue)
		{
			return ((LookupValue)valueObj).getIdAsString();
		}
		else if (valueObj instanceof ReferenceListAwareEnum)
		{
			return ((ReferenceListAwareEnum)valueObj).getCode();
		}
		else
		{
			return valueObj.toString();
		}
	}

	public boolean isEmpty(@NonNull final String fieldName)
	{
		final Object valueObj = map.get(fieldName);
		return valueObj == null || JSONNullValue.isNull(valueObj) || valueObj.toString().isEmpty();
	}

	@Nullable
	public Object get(@NonNull final String fieldName)
	{
		final Object valueObj = map.get(fieldName);
		return JSONNullValue.isNull(valueObj) ? null : valueObj;
	}
}
