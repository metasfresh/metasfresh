package org.compiere.model;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.SecureEngine;
import org.slf4j.Logger;

import de.metas.adempiere.service.IColumnBL;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@UtilityClass
final class GridTableUtils
{
	private static final Logger log = LogManager.getLogger(GridTableUtils.class);

	/**************************************************************************
	 * Read Data from RecordSet
	 *
	 * @param rs result set
	 * @return Data Array
	 */
	public static Object[] readData(final ResultSet rs, final List<GridField> gridFields)
	{
		final int size = gridFields.size();
		final Object[] rowData = new Object[size];

		// Types see also MField.createDefault
		String columnName = null;
		int displayType = -1;
		try
		{
			// get row data
			for (int j = 0; j < size; j++)
			{
				// Column Info
				final GridField field = gridFields.get(j);
				columnName = field.getColumnName();
				displayType = field.getDisplayType();
				// Integer, ID, Lookup (UpdatedBy is a numeric column)
				if (displayType == DisplayType.Integer
						|| DisplayType.isID(displayType)
								&& (columnName.endsWith("_ID") || columnName.endsWith("_Acct")
										|| columnName.equals("AD_Key") || columnName.equals("AD_Display"))
						|| columnName.endsWith("atedBy")
						|| Services.get(IColumnBL.class).isRecordIdColumnName(columnName) && DisplayType.Button == displayType // metas: Record_ID buttons are Integer IDs
				)
				{
					rowData[j] = new Integer(rs.getInt(j + 1));	// Integer
					if (rs.wasNull())
					{
						rowData[j] = null;
					}
				}
				// Number
				else if (DisplayType.isNumeric(displayType))
				{
					rowData[j] = rs.getBigDecimal(j + 1);			// BigDecimal
				}
				// Date
				else if (DisplayType.isDate(displayType))
				{
					rowData[j] = rs.getTimestamp(j + 1);			// Timestamp
				}
				// RowID or Key (and Selection)
				else if (displayType == DisplayType.RowID)
				{
					rowData[j] = null;
				}
				// YesNo
				else if (displayType == DisplayType.YesNo)
				{
					String str = rs.getString(j + 1);
					if (field.isEncryptedColumn())
					{
						str = (String)decrypt(str);
					}
					rowData[j] = StringUtils.toBoolean(str);	// Boolean
				}
				// LOB
				else if (DisplayType.isLOB(displayType))
				{
					final Object value = rs.getObject(j + 1);
					if (rs.wasNull())
					{
						rowData[j] = null;
					}
					else if (value instanceof Clob)
					{
						final Clob lob = (Clob)value;
						final long length = lob.length();
						rowData[j] = lob.getSubString(1, (int)length);
					}
					else if (value instanceof Blob)
					{
						final Blob lob = (Blob)value;
						final long length = lob.length();
						rowData[j] = lob.getBytes(1, (int)length);
					}
					else if (value instanceof String)
					{
						rowData[j] = value;
					}
					else if (value instanceof byte[])
					{
						rowData[j] = value;
					}
				}
				// String
				else
				{
					rowData[j] = rs.getString(j + 1);				// String
				}
				// Encrypted
				if (field.isEncryptedColumn() && displayType != DisplayType.YesNo)
				{
					rowData[j] = decrypt(rowData[j]);
				}
			}
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex)
					.setParameter("ColumnName", columnName)
					.setParameter("DisplayType", displayType)
					.setParameter("rowData", Arrays.asList(rowData))
					.appendParametersToMessage();
		}
		return rowData;
	}

	/**
	 * Get Record Where Clause from data (single key or multi-parent)
	 *
	 * @return where clause or null
	 */
	public static String getWhereClause(final Object[] rowData, final List<GridField> gridFields)
	{
		final int size = gridFields.size();
		StringBuilder singleRowWHERE = null;
		StringBuilder multiRowWHERE = null;
		for (int col = 0; col < size; col++)
		{
			final GridField field = gridFields.get(col);
			if (field.isKey())
			{
				final String columnName = field.getColumnName();
				final Object value = rowData[col];
				if (value == null)
				{
					log.warn("PK data is null - {}", columnName);
					return null;
				}
				if (columnName.endsWith("_ID"))
				{
					singleRowWHERE = new StringBuilder(columnName)
							.append("=").append(value);
				}
				else
				{
					singleRowWHERE = new StringBuilder(columnName)
							.append("=").append(DB.TO_STRING(value.toString()));
				}
			}
			else if (field.isParentColumn())
			{
				final String columnName = field.getColumnName();
				final Object value = rowData[col];
				if (value == null)
				{
					log.warn("FK data is null - {}", columnName);
					continue;
				}
				//
				if (multiRowWHERE == null)
				{
					multiRowWHERE = new StringBuilder();
				}
				else
				{
					multiRowWHERE.append(" AND ");
				}
				//
				if (columnName.endsWith("_ID"))
				{
					multiRowWHERE.append(columnName)
							.append("=").append(value);
				}
				else
				{
					multiRowWHERE.append(columnName)
							.append("=").append(DB.TO_STRING(value.toString()));
				}
			}
		}	// for all columns
		if (singleRowWHERE != null)
		{
			return singleRowWHERE.toString();
		}
		if (multiRowWHERE != null)
		{
			return multiRowWHERE.toString();
		}

		log.warn("No key Found. Returning NULL.");
		return null;
	}

	private static Object decrypt(final Object encryptedValue)
	{
		if (encryptedValue == null)
		{
			return null;
		}
		return SecureEngine.decrypt(encryptedValue);
	}

	public static boolean isValueChanged(Object oldValue, Object value)
	{
		if (isNotNullAndIsEmpty(oldValue))
		{
			oldValue = null;
		}

		if (isNotNullAndIsEmpty(value))
		{
			value = null;
		}

		boolean bChanged = oldValue == null && value != null
				|| oldValue != null && value == null;

		if (!bChanged && oldValue != null)
		{
			if (oldValue.getClass().equals(value.getClass()))
			{
				if (oldValue instanceof Comparable<?>)
				{
					bChanged = ((Comparable<Object>)oldValue).compareTo(value) != 0;
				}
				else
				{
					bChanged = !oldValue.equals(value);
				}
			}
			else if (value != null)
			{
				bChanged = !oldValue.toString().equals(value.toString());
			}
		}
		return bChanged;
	}

	private static boolean isNotNullAndIsEmpty(final Object value)
	{
		if (value != null
				&& value instanceof String
				&& value.toString().equals(""))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
