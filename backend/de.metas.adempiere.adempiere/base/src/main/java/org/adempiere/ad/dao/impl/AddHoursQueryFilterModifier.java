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

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryFilterModifier;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.ModelColumn;
import org.compiere.util.TimeUtil;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;

/**
 * Column Value Modifier which adds hours (from specified SQL Column) to the main column value.
 */
@EqualsAndHashCode
public final class AddHoursQueryFilterModifier implements IQueryFilterModifier
{
	private static final String SQL_FUNC_AddHours = "addHours";

	private final String hoursColumnSql;

	/**
	 *
	 * @param hoursColumn model column which contains the hours to add
	 */
	public AddHoursQueryFilterModifier(final String hoursColumnName)
	{
		Check.assumeNotEmpty(hoursColumnName, "hoursColumnName not empty");
		this.hoursColumnSql = hoursColumnName;
	}

	public AddHoursQueryFilterModifier(@NonNull final ModelColumn<?, ?> hoursColumn)
	{
		this.hoursColumnSql = hoursColumn.getColumnName();
	}

	@Override
	public @NonNull String getColumnSql(@NonNull String columnName)
	{
		final StringBuilder sb = new StringBuilder();

		sb.append(SQL_FUNC_AddHours).append("(");
		sb.append(columnName); // Param 1: Date Column
		sb.append(", ");
		sb.append(hoursColumnSql); // Param 2: Hours Column
		sb.append(")");

		return sb.toString();
	}

	@Override
	public String getValueSql(Object value, List<Object> params)
	{
		return NullQueryFilterModifier.instance.getValueSql(value, params);
	}

	@Nullable
	@Override
	public Object convertValue(@Nullable final String columnName, @Nullable Object value, final @Nullable Object model)
	{
		//
		// Constant: don't change the value
		// because this is the second operand with which we are comparing...
		if (columnName == null || columnName == COLUMNNAME_Constant)
		{
			return value;
		}
		//
		// ColumnName: add Hours to it
		else
		{
			final Date dateTime = toDate(value);
			if (dateTime == null)
			{
				return null;
			}
			final int hoursToAdd = getHours(model);
			return TimeUtil.addHours(dateTime, hoursToAdd);
		}
	}

	private int getHours(final Object model)
	{
		final Optional<Number> hours = InterfaceWrapperHelper.getValue(model, hoursColumnSql);
		if (!hours.isPresent())
		{
			return 0;
		}

		return hours.get().intValue();
	}

	private Date toDate(final Object value)
	{
		final Date date = (Date)value;
		return date;
	}

}
