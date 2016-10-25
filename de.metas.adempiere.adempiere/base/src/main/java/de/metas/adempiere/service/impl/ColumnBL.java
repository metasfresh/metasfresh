package de.metas.adempiere.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.Adempiere;
import org.compiere.model.POInfo;
import org.compiere.util.Env;

import de.metas.adempiere.service.IColumnBL;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class ColumnBL implements IColumnBL
{

	@Override
	public boolean isRecordColumnName(final String columnName)
	{
		if (columnName == null)
		{
			// should not happen
			return false;
		}

		// name must end with "Record_ID"
		if (!columnName.endsWith("Record_ID"))
		{
			return false;
		}

		// classical case
		if (columnName.equals("Record_ID"))
		{
			return true;
		}

		// Column name must end with "_Record_ID"
		if (!columnName.endsWith("_Record_ID"))
		{
			return false;
		}

		return true;
	}

	@Override
	public int getContextADTableID(final Properties m_ctx, final int m_curWindowNo, final String columnName)
	{
		if (columnName == null)
		{
			// should not happen
			return 0;
		}

		if (!isRecordColumnName(columnName))
		{
			return 0;
		}

		final String prefix = extractPrefixFromRecordColumn(columnName);

		String tableColumnName;
		int contextADTableID;

		// Try with Prefix_AD_Table_ID
		tableColumnName = prefix + "AD_Table_ID";

		contextADTableID = Env.getContextAsInt(m_ctx, m_curWindowNo, tableColumnName);

		if (contextADTableID > 0)
		{
			return contextADTableID;
		}

		// try with Prefix_Table_ID
		tableColumnName = prefix + "Table_ID";

		contextADTableID = Env.getContextAsInt(m_ctx, tableColumnName);

		// the found context table ID or 0 if not found
		return contextADTableID;
	}

	@Override
	public Optional<String> getTableColumnName(final String tableName, final String recordColumnName)
	{
		Check.assumeNotEmpty(tableName, "Paramter 'tableName' is empty; recordColumnName={}", tableName, recordColumnName);
		Check.assumeNotEmpty(tableName, "Paramter 'recordColumnName' is empty; tableName={}", recordColumnName, tableName);

		final String prefix = extractPrefixFromRecordColumn(recordColumnName);

		if (Adempiere.isUnitTestMode())
		{
			return Optional.of(prefix + "AD_Table_ID");
		}

		final POInfo poInfo = POInfo.getPOInfo(tableName);

		// Try with Prefix_AD_Table_ID
		String tableColumnName = prefix + "AD_Table_ID";
		if (poInfo.hasColumnName(tableColumnName))
		{
			return Optional.of(tableColumnName);
		}

		// try with Prefix_Table_ID
		tableColumnName = prefix + "Table_ID";
		if (poInfo.hasColumnName(tableColumnName))
		{
			return Optional.of(tableColumnName);
		}
		return Optional.empty();
	}

	private String extractPrefixFromRecordColumn(final String columnName)
	{
		final int recordStringIndex = columnName.indexOf("Record_ID");

		final String prefix = columnName.substring(0, recordStringIndex);

		return prefix;
	}

	@Override
	public String getSingleKeyColumn(final String tableName)
	{
		if (Adempiere.isUnitTestMode())
		{
			return InterfaceWrapperHelper.getKeyColumnName(tableName);
		}

		final POInfo poInfo = POInfo.getPOInfo(tableName);
		final List<String> keyColumnNames = poInfo.getKeyColumnNames();

		Check.errorIf(keyColumnNames.size() != 1, "Table={} has these {} key columns: {}; it shall have exactly one", tableName, keyColumnNames.size(), keyColumnNames);

		return keyColumnNames.get(0);
	}
}
