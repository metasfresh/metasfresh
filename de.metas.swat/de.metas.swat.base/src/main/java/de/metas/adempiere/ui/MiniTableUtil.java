package de.metas.adempiere.ui;

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
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.minigrid.ColumnInfo;
import org.compiere.minigrid.IDColumn;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Column;
import org.compiere.model.MColumn;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupFactory.LanguageInfo;
import org.compiere.model.MTable;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

public class MiniTableUtil
{
	private static final Logger log = LogManager.getLogger(MiniTableUtil.class);

	public static ColumnInfo[] createColumnInfo(Class<?> cl, String... columnNames)
	{
		final List<ColumnInfo> list = new ArrayList<ColumnInfo>();

		final String tableName = getTableName(cl);
		final MTable table = MTable.get(Env.getCtx(), tableName);
		for (String columnName : columnNames)
		{
			MColumn column = table.getColumn(columnName);
			if (column == null)
			{
				log.warn("Column not found: " + columnName);
				continue;
			}

			ColumnInfo ci = createColumnInfo(tableName, column);
			if (ci != null)
				list.add(ci);
		}

		return list.toArray(new ColumnInfo[list.size()]);
	}

	public static ColumnInfo createColumnInfo(String tableName, I_AD_Column column)
	{
		final String header = Msg.translate(Env.getCtx(), column.getColumnName());
		final int displayType = column.getAD_Reference_ID();
		final int referenceId = column.getAD_Reference_Value_ID();
		final String columnName = column.getColumnName();
		final String columnSQL = getColumnSQL(column);
		return createColumnInfo(header, tableName, columnName, columnSQL, displayType, referenceId);
	}
	private static ColumnInfo createColumnInfo(String header,
			String tableName, String columnName, String columnSQL,
			int displayType, int referenceId)
	{
		final Class<?> cl = DisplayType.getClass(displayType, true);

		if (DisplayType.isNumeric(displayType)
				|| DisplayType.isText(displayType)
				|| DisplayType.isDate(displayType))
		{
			return new ColumnInfo(header, columnSQL, cl);
		}
		if (DisplayType.List == displayType)
		{
			final LanguageInfo languageInfo = LanguageInfo.ofSpecificLanguage(Env.getCtx());
			String sql = MLookupFactory.getLookup_ListEmbed(languageInfo, referenceId, columnSQL);
			sql = "(" + sql + ")";
			return new ColumnInfo(header, sql, KeyNamePair.class, columnSQL);
		}
		else if (DisplayType.isLookup(displayType) && referenceId <= 0)
		{
			final LanguageInfo languageInfo = LanguageInfo.ofSpecificLanguage(Env.getCtx());
			String sql = MLookupFactory.getLookup_TableDirEmbed(languageInfo, columnName, tableName, columnSQL);
			sql = "(" + sql + ")";
			return new ColumnInfo(header, sql, KeyNamePair.class, columnSQL);
		}
		else if (DisplayType.isLookup(displayType) && referenceId > 0)
		{
			final LanguageInfo languageInfo = LanguageInfo.ofSpecificLanguage(Env.getCtx());
			String sql = MLookupFactory.getLookup_TableEmbed(languageInfo, columnSQL, tableName, referenceId);
			sql = "(" + sql + ")";
			return new ColumnInfo(header, sql, KeyNamePair.class, columnSQL);
		}
		else if (DisplayType.ID == displayType)
		{
			return new ColumnInfo(" ", columnName, IDColumn.class);

		}
		else
		{
			return new ColumnInfo(header, columnSQL, cl);
		}
	}

	private static final String getColumnSQL(I_AD_Column column)
	{
		String columnSQL = column.getColumnName();
		if (!Util.isEmpty(column.getColumnSQL()))
			columnSQL = column.getColumnSQL();
		return columnSQL;
	}

	public static String getTableName(Class<?> cl)
	{
		try
		{
			return (String)cl.getField("Table_Name").get(null);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
	}
	
	public static ColumnInfo[] createColumnInfo(GridTab gridTab)
	{
		final List<ColumnInfo> list = new ArrayList<ColumnInfo>();
		
		for (GridField gridField : gridTab.getFields())
		{
			final boolean isID = gridField.getDisplayType() == DisplayType.ID;
			if (!gridField.isDisplayed() && !isID)
				continue;
			ColumnInfo ci = createColumnInfo(gridField);
			if (ci != null)
			{
				if (isID)
					list.add(0, ci);
				else
					list.add(ci);
			}
		}
		return list.toArray(new ColumnInfo[list.size()]);
	}

	public static ColumnInfo createColumnInfo(GridField gridField)
	{
		final String tableName = gridField.getGridTab().getTableName();
		final String header = gridField.getHeader();
		final int displayType = gridField.getDisplayType();
		final int referenceId = gridField.getAD_Reference_Value_ID();
		final String columnName = gridField.getColumnName();
		final String columnSQL = gridField.getColumnSQL(false);
		return createColumnInfo(header, tableName, columnName, columnSQL, displayType, referenceId);
	}

}
