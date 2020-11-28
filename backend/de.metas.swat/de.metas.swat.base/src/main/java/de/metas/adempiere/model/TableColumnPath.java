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


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableColumnPath implements ITableColumnPath
{
	private String valueTableName;
	private String valueColumnName;
	private int valueDisplayType;
	private String keyTableName;
	private String keyColumnName;
	private int recordId;
	final private List<ITableColumnPathElement> elements = new ArrayList<ITableColumnPathElement>();

	public TableColumnPath(String keyTableName, String keyColumnName, int recordId)
	{
		this.keyTableName = keyTableName;
		this.keyColumnName = keyColumnName;
		this.recordId = recordId;
	}

	public void setValueColumnName(String valueTableName, String valueColumnName, int valueDisplayType)
	{
		this.valueTableName = valueTableName;
		this.valueColumnName = valueColumnName;
		this.valueDisplayType = valueDisplayType;
	}

	public void addElement(TableColumnPathElement e)
	{
		elements.add(e);
	}

	@Override
	public List<ITableColumnPathElement> getElements()
	{
		return Collections.unmodifiableList(this.elements);
	}

	@Override
	public String getValueTableName()
	{
		return valueTableName;
	}

	@Override
	public String getValueColumnName()
	{
		return valueColumnName;
	}

	@Override
	public int getValueDisplayType()
	{
		return valueDisplayType;
	}

	@Override
	public String getKeyTableName()
	{
		return keyTableName;
	}

	@Override
	public String getKeyColumnName()
	{
		return keyColumnName;
	}

	@Override
	public int getRecordId()
	{
		return recordId;
	}

	@Override
	public String toString()
	{
		return "TableColumnPath [valueTableName=" + valueTableName + ", valueColumnName=" + valueColumnName + ", keyTableName=" + keyTableName + ", keyColumnName=" + keyColumnName + ", recordId="
				+ recordId + ", elements=" + elements + "]";
	}

}
