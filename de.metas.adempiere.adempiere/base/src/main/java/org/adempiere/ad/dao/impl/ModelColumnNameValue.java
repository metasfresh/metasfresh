package org.adempiere.ad.dao.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.ModelColumn;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class ModelColumnNameValue<T>
{
	public static <ModelType> ModelColumnNameValue<ModelType> forColumn(@NonNull final ModelColumn<ModelType, ?> column)
	{
		return new ModelColumnNameValue<>(column.getColumnName());
	}

	public static <ModelType> ModelColumnNameValue<ModelType> forColumnName(final String columnName)
	{
		return new ModelColumnNameValue<>(columnName);
	}

	/**
	 * Creates a fully qualified column name
	 */
	public static <ModelType> ModelColumnNameValue<ModelType> forColumnName(final String tableName, final String columnName)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		Check.assumeNotEmpty(columnName, "columnName not empty");

		final String columnNameFQ = tableName + "." + columnName;
		return new ModelColumnNameValue<>(columnNameFQ);
	}

	@Getter
	private final String columnName;

	private ModelColumnNameValue(final String columnName)
	{
		Check.assumeNotEmpty(columnName, "columnName not empty");
		this.columnName = columnName;
	}

	/**
	 * <b>Might return <code>null</code>!</b>
	 * 
	 * @param model
	 * @return
	 */
	public Object getValue(@NonNull final T model)
	{
		final String columnName = getColumnName();
		if (InterfaceWrapperHelper.isNull(model, columnName))
		{
			return null;
		}
		return InterfaceWrapperHelper.getValue(model, columnName).orElse(null);
	}
}
