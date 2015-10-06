/**
 * 
 */
package org.adempiere.model;

/*
 * #%L
 * ADempiere ERP - Base
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


import org.compiere.model.GridTab;

/**
 * @author Cristina Ghita, METAS.RO
 * 
 */
public class TableInfoVO
{
	/** Translated name */
	private String name;
	public String tableName;
	public String linkColumnName;
	public String parentTableName;
	public String parentColumnName;
	public GridTab gridTab;
	
	public String getName()
	{
		return name;
	}
	
	public void setName(final String name)
	{
		this.name = name;
	}
	
	private final int getAD_Tab_ID()
	{
		return gridTab != null ? gridTab.getAD_Tab_ID() : -1;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
//		result = prime * result + getAD_Tab_ID();
		result = prime * result + ((linkColumnName == null) ? 0 : linkColumnName.hashCode());
		result = prime * result + ((parentTableName == null) ? 0 : parentTableName.hashCode());
		result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableInfoVO other = (TableInfoVO)obj;
		if (getAD_Tab_ID() != other.getAD_Tab_ID() && (getAD_Tab_ID() != -1 && other.getAD_Tab_ID() != -1))
			return false;
		if (linkColumnName == null)
		{
			if (other.linkColumnName != null)
				return false;
		}
		else if (!linkColumnName.equals(other.linkColumnName))
			return false;
		if (parentTableName == null)
		{
			if (other.parentTableName != null)
				return false;
		}
		else if (!parentTableName.equals(other.parentTableName))
			return false;
		if (tableName == null)
		{
			if (other.tableName != null)
				return false;
		}
		else if (!tableName.equals(other.tableName))
			return false;
		return true;
	}
}
