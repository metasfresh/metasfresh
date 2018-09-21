package de.metas.adempiere.form.terminal.table;

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


import java.lang.reflect.Method;

import org.adempiere.util.lang.ObjectUtils;

import de.metas.util.Check;

/* package */class TableColumnInfo implements ITableColumnInfo
{
	private final String columnName;
	private Class<?> columnClass;
	private String displayName;
	private boolean editable;
	private final Method readMethod;
	private Method writeMethod;
	private int seqNo = Integer.MAX_VALUE;
	private String lookupTableName = null;
	private String lookupColumnName = null;
	private String prototypeValue = null;

	public TableColumnInfo(String columnName, Class<?> columnClass, Method readMethod)
	{
		super();

		Check.assumeNotEmpty(columnName, "columnName not empty");
		this.columnName = columnName;

		this.columnClass = columnClass;

		Check.assumeNotNull(readMethod, "readMethod not null");
		this.readMethod = readMethod;
	}
	
	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public String getColumnName()
	{
		return columnName;
	}

	@Override
	public Class<?> getColumnClass()
	{
		return columnClass;
	}

	public void setColumnClass(final Class<?> columnClass)
	{
		Check.assumeNotNull(columnClass, "columnClass not null");
		this.columnClass = columnClass;
	}

	@Override
	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	@Override
	public boolean isEditable()
	{
		return editable;
	}

	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}

	@Override
	public Method getReadMethod()
	{
		return readMethod;
	}

	@Override
	public Method getWriteMethod()
	{
		return writeMethod;
	}

	public void setWriteMethod(Method writeMethod)
	{
		this.writeMethod = writeMethod;
	}

	public void setSeqNo(int seqNo)
	{
		this.seqNo = seqNo;
	}

	@Override
	public int getSeqNo()
	{
		return this.seqNo;
	}

	@Override
	public String getLookupTableName()
	{
		return lookupTableName;
	}

	public void setLookupTableName(String lookupTableName)
	{
		this.lookupTableName = lookupTableName;
	}

	@Override
	public String getLookupColumnName()
	{
		return lookupColumnName;
	}

	public void setLookupColumnName(String lookupColumnName)
	{
		this.lookupColumnName = lookupColumnName;
	}

	@Override
	public String getPrototypeValue()
	{
		return prototypeValue;
	}

	public void setPrototypeValue(String prototypeValue)
	{
		this.prototypeValue = prototypeValue;
	}
}
