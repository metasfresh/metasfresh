package de.metas.adempiere.service.impl;

import de.metas.adempiere.service.IColumnBL;
import lombok.NonNull;
import org.adempiere.ad.table.exception.NoSingleKeyColumnException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.apache.commons.lang3.StringUtils;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Column;
import org.compiere.model.POInfo;
import org.compiere.util.Env;

import java.util.List;
import java.util.Properties;

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
	public int getContextADTableID(final Properties m_ctx, final int m_curWindowNo, final String columnName)
	{
		if (columnName == null)
		{
			// should not happen
			return 0;
		}

		if (!IColumnBL.isRecordIdColumnName(columnName))
		{
			return 0;
		}

		final int recordIdIdx = columnName.indexOf(ITableRecordReference.COLUMNNAME_Record_ID);
		if (recordIdIdx < 0)
		{
			return 0;
		}

		final String prefix = columnName.substring(0, recordIdIdx);

		String tableColumnName;
		int contextADTableID;

		// Try with Prefix_AD_Table_ID
		tableColumnName = prefix + ITableRecordReference.COLUMNNAME_AD_Table_ID;

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
	public String getSingleKeyColumn(final String tableName)
	{
		if (Adempiere.isUnitTestMode())
		{
			return InterfaceWrapperHelper.getKeyColumnName(tableName);
		}

		final POInfo poInfo = POInfo.getPOInfoNotNull(tableName);
		final List<String> keyColumnNames = poInfo.getKeyColumnNames();

		if (keyColumnNames.size() != 1)
		{
			throw new NoSingleKeyColumnException(poInfo);
		}
		return keyColumnNames.get(0);
	}

	@Override
	public boolean getDefaultAllowLoggingByColumnName(@NonNull final String columnName)
	{

		if (columnName.equalsIgnoreCase(I_AD_Column.COLUMNNAME_Created)
				|| columnName.equalsIgnoreCase(I_AD_Column.COLUMNNAME_CreatedBy)
				|| columnName.equalsIgnoreCase(I_AD_Column.COLUMNNAME_Updated)
				|| columnName.equalsIgnoreCase(I_AD_Column.COLUMNNAME_UpdatedBy))
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean getDefaultIsCalculatedByColumnName(@NonNull final String columnName)
	{
		return columnName.equalsIgnoreCase("Value")
				|| columnName.equalsIgnoreCase("DocumentNo")
				|| columnName.equalsIgnoreCase("DocStatus")
				|| columnName.equalsIgnoreCase("Docaction")
				|| columnName.equalsIgnoreCase("Processed")
				|| columnName.equalsIgnoreCase("Processing")
				|| StringUtils.containsIgnoreCase(columnName, "ExternalID")
				|| columnName.equalsIgnoreCase("ExternalHeaderId")
				|| columnName.equalsIgnoreCase("ExternalLineId")
				|| columnName.equalsIgnoreCase("IsReconciled")
				;
	}

}
