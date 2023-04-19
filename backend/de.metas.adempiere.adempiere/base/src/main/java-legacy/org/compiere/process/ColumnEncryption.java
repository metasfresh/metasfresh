/**********************************************************************
 * This file is part of Adempiere ERP Bazaar *
 * http://www.adempiere.org *
 * *
 * Copyright (C) 1999 - 2006 Compiere Inc. *
 * Copyright (C) Contributors *
 * *
 * This program is free software; you can redistribute it and/or *
 * modify it under the terms of the GNU General Public License *
 * as published by the Free Software Foundation; either version 2 *
 * of the License, or (at your option) any later version. *
 * *
 * This program is distributed in the hope that it will be useful, *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the *
 * GNU General Public License for more details. *
 * *
 * You should have received a copy of the GNU General Public License *
 * along with this program; if not, write to the Free Software *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, *
 * MA 02110-1301, USA. *
 * *
 * Contributors: *
 * - Bahman Movaqar (bmovaqar AT users.sf.net) *
 **********************************************************************/
package org.compiere.process;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.X_AD_Column;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.SecureEngine;

import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Services;

/**
 * Column Encryption Test
 *
 * @author Jorg Janke
 * @version $Id: ColumnEncryption.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class ColumnEncryption extends JavaProcess
{
	private final IADTableDAO adTablesRepo = Services.get(IADTableDAO.class);

	/** Enable/Disable Encryption */
	@Param(parameterName = "IsEncrypted")
	private boolean p_IsEncrypted = false;

	/** Change Encryption Settings */
	@Param(parameterName = "ChangeSetting")
	private boolean p_ChangeSetting = false;

	/** Maximum Length */
	@Param(parameterName = "MaxLength")
	private int p_MaxLength = 0;

	/** Test Value */
	@Param(parameterName = "TestValue")
	private String p_TestValue = null;

	@Override
	protected String doIt() throws Exception
	{
		final I_AD_Column column = getRecord(I_AD_Column.class);
		final String columnName = column.getColumnName();
		final int displayType = column.getAD_Reference_ID();

		// Can it be enabled?
		if (column.isKey()
				|| column.isParent()
				|| adTablesRepo.isStandardColumn(columnName)
				|| adTablesRepo.isVirtualColumn(column)
				|| column.isIdentifier()
				|| column.isTranslated()
				|| DisplayType.isLookup(displayType)
				|| DisplayType.isLOB(displayType)
				|| "DocumentNo".equalsIgnoreCase(column.getColumnName())
				|| "Value".equalsIgnoreCase(column.getColumnName())
				|| "Name".equalsIgnoreCase(column.getColumnName()))
		{
			if (isEncrypted(column))
			{
				setNotEncrypted(column);
				InterfaceWrapperHelper.save(column);
			}

			throw new AdempiereException(columnName + ": cannot be encrypted");
		}

		// Start
		addLog("Encryption Class = " + SecureEngine.getClassName());
		boolean error = false;

		// Test Value
		if (!checkTestValue(column))
		{
			error = true;
		}

		// Length Test
		if (!checkMaxLength(column))
		{
			error = true;
		}

		// If only user chooses both encrypt the contents and override current
		// settings resize the physical column and encrypt all its contents.
		if (p_IsEncrypted && p_ChangeSetting)
		{
			// If the column has already been encrypted, show a warning message and exit.
			if (isEncrypted(column))
			{
				throw new AdempiereException("EncryptError: Column already encrypted.");
			}

			final int adColumnId = column.getAD_Column_ID();
			final String tableName = Services.get(IADTableDAO.class).retrieveTableName(column.getAD_Table_ID());

			// Check if the encryption exceeds the current length.
			final int oldLength = column.getFieldLength();
			final int newLength = encryptedColumnLength(oldLength);
			if (newLength > oldLength)
			{
				if (changeFieldLength(adColumnId, columnName, newLength, tableName) == -1)
				{
					throw new AdempiereException("EncryptError [ChangeFieldLength]: "
							+ "AD_Column_ID=" + adColumnId
							+ ", NewLength=" + newLength);
				}
			}

			// Encrypt column contents.
			encryptColumnContents(columnName, column.getAD_Table_ID());

			if (p_IsEncrypted != isEncrypted(column))
			{
				if (error || !p_ChangeSetting)
				{
					addLog("Encryption NOT changed - Encryption=" + isEncrypted(column));
				}
				else
				{
					setEncrypted(column, p_IsEncrypted);
				}
			}
		}

		return "Encryption=" + isEncrypted(column);
	}

	private static boolean isEncrypted(final I_AD_Column adColumn)
	{
		return X_AD_Column.ISENCRYPTED_Encrypted.equals(adColumn.getIsEncrypted());
	}

	private static void setEncrypted(final I_AD_Column adColumn, final boolean encrypted)
	{
		if (encrypted)
		{
			setEncrypted(adColumn);
		}
		else
		{
			setNotEncrypted(adColumn);
		}
	}

	private static void setEncrypted(final I_AD_Column adColumn)
	{
		adColumn.setIsEncrypted(X_AD_Column.ISENCRYPTED_Encrypted);
		InterfaceWrapperHelper.save(adColumn);
	}

	private static void setNotEncrypted(final I_AD_Column adColumn)
	{
		adColumn.setIsEncrypted(X_AD_Column.ISENCRYPTED_NichtVerschluesselt);
		InterfaceWrapperHelper.save(adColumn);
	}

	private boolean checkTestValue(final I_AD_Column column)
	{
		boolean ok = true;

		if (p_TestValue == null || p_TestValue.isEmpty())
		{
			return ok;
		}

		final String encString = SecureEngine.encrypt(p_TestValue);
		addLog("Encrypted Test Value=" + encString);
		final String clearString = SecureEngine.decrypt(encString);
		if (p_TestValue.equals(clearString))
		{
			addLog("Decrypted=" + clearString + " (same as test value)");
		}
		else
		{
			addLog("Decrypted=" + clearString + " (NOT the same as test value - check algorithm)");
			ok = false;
		}

		final int encLength = encString.length();
		addLog("Test Length=" + p_TestValue.length() + " -> " + encLength);
		if (encLength <= column.getFieldLength())
		{
			addLog("Encrypted Length (" + encLength + ") fits into field (" + column.getFieldLength() + ")");
		}
		else
		{
			addLog("Encrypted Length (" + encLength + ") does NOT fit into field (" + column.getFieldLength() + ") - resize field");
			ok = false;
		}

		return ok;
	}

	private boolean checkMaxLength(final I_AD_Column column)
	{
		boolean ok = true;

		if (p_MaxLength <= 0)
		{
			return ok;
		}

		String testClear = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		while (testClear.length() < p_MaxLength)
		{
			testClear += testClear;
		}
		testClear = testClear.substring(0, p_MaxLength);
		log.info("Test=" + testClear + " (" + p_MaxLength + ")");
		//
		final String encString = SecureEngine.encrypt(testClear);
		final int encLength = encString.length();
		addLog("Test Max Length=" + testClear.length() + " -> " + encLength);
		if (encLength <= column.getFieldLength())
		{
			addLog("Encrypted Max Length (" + encLength + ") fits into field (" + column.getFieldLength() + ")");
		}
		else
		{
			addLog("Encrypted Max Length (" + encLength + ") does NOT fit into field (" + column.getFieldLength() + ") - resize field");
			ok = false;
		}

		return ok;
	}

	/**
	 * @return the number of rows effected
	 */
	private int encryptColumnContents(final String columnName, final int tableID)
	{
		final String tableName = adTablesRepo.retrieveTableName(tableID);
		return encryptColumnContents(columnName, tableName);
	}

	/**
	 * @return the number of rows effected
	 */
	private int encryptColumnContents(final String columnName, final String tableName)
	{
		final String idColumnName = InterfaceWrapperHelper.getKeyColumnName(tableName);

		final String selectSql = "SELECT " + idColumnName + "," + columnName + " FROM " + tableName + " ORDER BY " + idColumnName;
		final String updateSql = "UPDATE " + tableName + " SET " + columnName + "=? WHERE " + idColumnName + "=?";

		PreparedStatement selectStmt = null;
		ResultSet rs = null;
		PreparedStatement updateStmt = null;

		try
		{
			selectStmt = DB.prepareStatement(selectSql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE, ITrx.TRXNAME_ThreadInherited);
			updateStmt = DB.prepareStatement(updateSql, ITrx.TRXNAME_ThreadInherited);
			rs = selectStmt.executeQuery();

			int recordsEncrypted = 0;
			while (rs.next())
			{
				final int id = rs.getInt(1);
				final String valuePlain = rs.getString(2);
				final String valueEncrypted = SecureEngine.encrypt(valuePlain);

				// Update the row
				try
				{
					updateStmt.setString(1, valueEncrypted);
					updateStmt.setInt(2, id);
					if (updateStmt.executeUpdate() != 1)
					{
						throw new AdempiereException("EncryptError: Table=" + tableName + ", ID=" + id);
					}
				}
				catch (SQLException ex)
				{
					throw new DBException(ex, updateSql);
				}

				//
				recordsEncrypted++;
			}

			return recordsEncrypted;
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, selectSql);
		}
		finally
		{
			DB.close(rs, selectStmt);
			DB.close(updateStmt);
		}
	}

	/**
	 * Determines the length of the encrypted column.
	 *
	 * @param currentColSize
	 *            Current column size
	 * @return The length of the encrypted column.
	 */
	private int encryptedColumnLength(final int colLength)
	{
		String str = "";

		for (int i = 0; i < colLength; i++)
		{
			str += "1";
		}
		str = SecureEngine.encrypt(str);

		return str.length();
	} // encryptedColumnLength

	/**
	 * Change the column length.
	 *
	 * @param adColumnId
	 *            ID of the column
	 * @param tableName
	 *            The name of the table which owns the column
	 * @param length
	 *            New length of the column
	 * @return The number of rows effected, 1 upon success and -1 for failure.
	 */
	private int changeFieldLength(final int adColumnId, final String columnName, final int length, final String tableName)
	{
		int rowsEffected = -1;

		final String selectSql = "SELECT FieldLength FROM AD_Column WHERE AD_Column_ID=" + adColumnId;
		final String alterSql = "ALTER TABLE " + tableName + " MODIFY " + columnName + " NVARCHAR2(" + length + ") ";
		final String updateSql = "UPDATE AD_Column SET FieldLength=" + length + " WHERE AD_Column_ID=" + adColumnId;

		PreparedStatement selectStmt = null;
		ResultSet rs = null;

		try
		{
			selectStmt = DB.prepareStatement(selectSql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE, ITrx.TRXNAME_ThreadInherited);
			rs = selectStmt.executeQuery();

			if (rs.next())
			{
				// Change the column size physically.
				DB.executeUpdateAndThrowExceptionOnFail(alterSql, ITrx.TRXNAME_ThreadInherited);

				// Change the column size in AD.
				DB.executeUpdateAndThrowExceptionOnFail(updateSql, ITrx.TRXNAME_ThreadInherited);
			}
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, selectSql);
		}
		finally
		{
			DB.close(rs, selectStmt);
		}

		// Update number of rows effected.
		rowsEffected++;

		return rowsEffected;
	} // changeFieldLength

} // EncryptionTest
