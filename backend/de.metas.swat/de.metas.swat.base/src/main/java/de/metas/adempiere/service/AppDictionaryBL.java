/**
 * 
 */
package de.metas.adempiere.service;

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


import java.util.Properties;

import org.compiere.model.MColumn;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;

/**
 * @author tsa
 *
 */
public class AppDictionaryBL implements IAppDictionaryBL
{
	//	private final Logger log = CLogMgt.getLogger(getClass());
	
	@Override
	public MTable getReferencedTable(Properties ctx, String tableName, String columnName)
	{
		final MTable table = MTable.get(ctx, tableName);
		return getReferencedTable(table, columnName);
	}
	@Override
	public MTable getReferencedTable(MTable parentTable, String columnName)
	{
		final Properties ctx = parentTable.getCtx();
		final MColumn parentColumn = parentTable.getColumn(columnName);
		if (parentColumn == null)
			return null;
		final int dt = parentColumn.getAD_Reference_ID();
		final int referenceId = parentColumn.getAD_Reference_Value_ID();

		// Check first if is a custom lookup (e.g. Location, PAttribute etc)
		String tableName = DisplayType.getTableName(dt);
		if (tableName != null)
		{
			return MTable.get(ctx, tableName);
		}
		// TableDir
		if (dt == DisplayType.TableDir || (dt == DisplayType.Search && referenceId <= 0))
		{
			tableName = MQuery.getZoomTableName(columnName);
			return MTable.get(ctx, tableName);
		}
		// Table
		if (dt == DisplayType.Table || (dt == DisplayType.Search && referenceId > 0))
		{
			// TODO improve, cache
			int tableId = DB.getSQLValueEx(null, "SELECT AD_Table_ID FROM AD_Ref_Table WHERE AD_Reference_ID=?", referenceId);
			return MTable.get(ctx, tableId);
		}

		return null;
	}
}
