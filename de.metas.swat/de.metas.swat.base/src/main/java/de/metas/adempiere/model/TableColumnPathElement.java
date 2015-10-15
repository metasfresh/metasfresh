package de.metas.adempiere.model;

/*
 * #%L
 * de.metas.swat.base
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


public class TableColumnPathElement implements ITableColumnPathElement
{
	private String tableName;
	private String columnName;
	private String parentTableName;
	private String parentColumnName;
	private String parentColumnSQL;

	public TableColumnPathElement(String tableName, String columnName,
			String parentTableName, String parentColumnName, String parentColumnSQL)
	{
		super();
		this.tableName = tableName;
		this.columnName = columnName;
		this.parentTableName = parentTableName;
		this.parentColumnName = parentColumnName;
		this.parentColumnSQL = parentColumnSQL;
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	@Override
	public String getColumnName()
	{
		return columnName;
	}

	@Override
	public String getParentTableName()
	{
		return parentTableName;
	}

	@Override
	public String getParentColumnName()
	{
		return parentColumnName;
	}

	@Override
	public String getParentColumnSQL()
	{
		return parentColumnSQL;
	}

	@Override
	public String toString()
	{
		return "TableColumnPathElement ["
		+"tableName=" + tableName + ", columnName=" + columnName
		+ ", parentTableName=" + parentTableName + ", parentColumnName=" + parentColumnName
		+ ", parentColumnSQL=" + parentColumnSQL
		+ "]";
	}

}
