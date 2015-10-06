package de.metas.web.component.trl;

/*
 * #%L
 * de.metas.swat.zkwebui
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
import java.util.List;

public class TrlElement
{
	private String tableName;
	private int recordId;
	private final List<TrlElementItem> items = new ArrayList<TrlElementItem>();

	private String name;
	private String description;

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(final String tableName)
	{
		this.tableName = tableName;
	}

	public int getRecordId()
	{
		return recordId;
	}

	public void setRecordId(final int recordId)
	{
		this.recordId = recordId;
	}

	public List<TrlElementItem> getItems()
	{
		return items;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}

}
