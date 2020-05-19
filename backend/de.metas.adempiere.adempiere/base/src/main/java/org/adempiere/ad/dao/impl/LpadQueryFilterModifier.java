/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.adempiere.ad.dao.impl;

import java.util.List;

import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilterModifier;
import org.apache.commons.lang3.StringUtils;
import org.compiere.util.DB;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;

import javax.annotation.Nullable;

@EqualsAndHashCode
public class LpadQueryFilterModifier implements IQueryFilterModifier
{
	private final int size;
	private final String padStr;

	public LpadQueryFilterModifier(final int size, final String padStr)
	{
		Check.assume(size > 0, "size > 0");
		this.size = size;

		Check.assumeNotEmpty(padStr, "padStr not empty");
		this.padStr = padStr;
	}

	@Override
	public String toString()
	{
		return "LPAD[size=" + size + ", padStr=" + padStr + "]";
	}

	private final String buildSql(final String column)
	{
		return "LPAD(TRIM(" + column + "), " + size + ", " + DB.TO_STRING(padStr) + ")";
	}

	@Override
	public @NonNull String getColumnSql(@NonNull String columnName)
	{
		return buildSql(columnName);
	}

	@Override
	public String getValueSql(Object value, List<Object> params)
	{
		final String valueSql;
		if (value instanceof ModelColumnNameValue<?>)
		{
			final ModelColumnNameValue<?> modelValue = (ModelColumnNameValue<?>)value;
			valueSql = modelValue.getColumnName();
		}
		else
		{
			valueSql = "?";
			params.add(value);
		}

		return buildSql(valueSql);
	}

	@Nullable
	@Override
	public Object convertValue(@Nullable final String columnName, @Nullable final Object value, final @Nullable Object model)
	{
		if (value == null)
		{
			return null;
		}

		final String str = (String)value;

		return StringUtils.leftPad(str.trim(), size, padStr).subSequence(0,10);
	}
}
