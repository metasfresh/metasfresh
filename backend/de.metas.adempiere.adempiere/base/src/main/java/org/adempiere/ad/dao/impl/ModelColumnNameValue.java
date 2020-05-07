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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.ModelColumn;
import org.adempiere.util.Check;

public final class ModelColumnNameValue<T>
{
	public static <ModelType> ModelColumnNameValue<ModelType> forColumn(final ModelColumn<ModelType, ?> column)
	{
		Check.assumeNotNull(column, "column not null");
		return new ModelColumnNameValue<ModelType>(column.getColumnName());
	}

	public static <ModelType> ModelColumnNameValue<ModelType> forColumnName(final String columnName)
	{
		return new ModelColumnNameValue<ModelType>(columnName);
	}

	/**
	 * Creates a fully qualified column name
	 *
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public static <ModelType> ModelColumnNameValue<ModelType> forColumnName(final String tableName, final String columnName)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		Check.assumeNotEmpty(columnName, "columnName not empty");

		final String columnNameFQ = tableName + "." + columnName;
		return new ModelColumnNameValue<ModelType>(columnNameFQ);
	}

	private final String columnName;

	private ModelColumnNameValue(final String columnName)
	{
		super();
		Check.assumeNotEmpty(columnName, "columnName not empty");
		this.columnName = columnName;
	}

	public String getColumnName()
	{
		return this.columnName;
	}

	/**
	 * <b>Might return <code>null</code>!</b>
	 * @param model
	 * @return
	 */
	public Object getValue(final T model)
	{
		Check.assumeNotNull(model, "model not null");

		final String columnName = getColumnName();
		if (InterfaceWrapperHelper.isNull(model, columnName))
		{
			return null;
		}
		return InterfaceWrapperHelper.getValue(model, columnName).orNull();
	}

	@Override
	public String toString()
	{
		return "ModelColumnNameValue [columnName=" + columnName + "]";
	}
}
