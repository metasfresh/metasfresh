package org.adempiere.ad.security.impl;

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


import org.adempiere.ad.security.TableAccessLevel;
import org.compiere.model.POInfo;

/**
 * Supporting service for role permissions which basically provide table informations.
 * 
 * NOTE: we are keeping it isolated because we intent to make it pluggable in future.
 * 
 * @author tsa
 *
 */
public class TablesAccessInfo
{
	public static final transient TablesAccessInfo instance = new TablesAccessInfo();

	// private final transient CLogger logger = CLogger.getCLogger(getClass());

	private TablesAccessInfo()
	{
		super();
	}

	/**
	 * Gets table's access level.
	 * 
	 * @param adTableId
	 * @return table's access level or null if no table was found.
	 */
	public final TableAccessLevel getTableAccessLevel(final int adTableId)
	{
		final POInfo poInfo = POInfo.getPOInfo(adTableId);
		if (poInfo == null)
		{
			return null;
		}
		return poInfo.getAccessLevel();
	}

	/**
	 * Check if tableName is a view
	 *
	 * @param tableName
	 * @return boolean
	 */
	public boolean isView(final String tableName)
	{
		final POInfo poInfo = POInfo.getPOInfo(tableName);
		return poInfo == null ? false : poInfo.isView();
	}

	public String getIdColumnName(final String tableName)
	{
		final POInfo poInfo = POInfo.getPOInfo(tableName);
		return poInfo == null ? null : poInfo.getKeyColumnName();
	}

	public int getAD_Table_ID(final String tableName)
	{
		final POInfo poInfo = POInfo.getPOInfo(tableName);
		return poInfo == null ? 0 : poInfo.getAD_Table_ID();
	}

	public boolean isPhysicalColumn(final String tableName, final String columnName)
	{
		final POInfo poInfo = POInfo.getPOInfo(tableName);
		if (poInfo == null)
		{
			return false;
		}
		if (!poInfo.hasColumnName(columnName))
		{
			return false;
		}
		if (poInfo.isVirtualColumn(columnName))
		{
			return false;
		}

		return true;
	}
}
