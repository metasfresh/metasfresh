/********************************************************************** 
 * This file is part of Adempiere ERP Bazaar                          * 
 * http://www.adempiere.org                                           * 
 *                                                                    * 
 * Copyright (C) 1999 - 2006 Compiere Inc.                            * 
 * Copyright (C) Contributors                                         * 
 *                                                                    * 
 * This program is free software; you can redistribute it and/or      * 
 * modify it under the terms of the GNU General Public License        * 
 * as published by the Free Software Foundation; either version 2     * 
 * of the License, or (at your option) any later version.             * 
 *                                                                    * 
 * This program is distributed in the hope that it will be useful,    * 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of     * 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the       * 
 * GNU General Public License for more details.                       * 
 *                                                                    * 
 * You should have received a copy of the GNU General Public License  * 
 * along with this program; if not, write to the Free Software        * 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         * 
 * MA 02110-1301, USA.                                                * 
 *                                                                    * 
 * Contributors:                                                      * 
 *  - Bahman Movaqar (bmovaqar AT users.sf.net)                       * 
 **********************************************************************/
package org.compiere.process;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.SecureEngine;
import org.compiere.util.Trx;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * Column Encryption Test
 * 
 * @author Jorg Janke
 * @version $Id: ColumnEncryption.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class ColumnEncryption extends JavaProcess {
	/** Enable/Disable Encryption */
	private boolean p_IsEncrypted = false;

	/** Change Encryption Settings */
	private boolean p_ChangeSetting = false;

	/** Maximum Length */
	private int p_MaxLength = 0;

	/** Test Value */
	private String p_TestValue = null;

	/** The Column */
	private int p_AD_Column_ID = 0;

	/**
	 * All the resizing and encrypting database are managed by this
	 * transaction.
	 */
	private Trx m_trx;
	
	/**
	 * All the resizing and encrypting database work goes through this
	 * connection.
	 */
	private Connection m_conn;
	
	/**
	 * Prepare - e.g., get Parameters.
	 */
	protected void prepare() {
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("IsEncrypted"))
				p_IsEncrypted = "Y".equals(para[i].getParameter());
			else if (name.equals("ChangeSetting"))
				p_ChangeSetting = "Y".equals(para[i].getParameter());
			else if (name.equals("MaxLength"))
				p_MaxLength = para[i].getParameterAsInt();
			else if (name.equals("TestValue"))
				p_TestValue = (String) para[i].getParameter();
			else
				log.error("Unknown Parameter: " + name);
		}
		p_AD_Column_ID = getRecord_ID();
	} // prepare

	/**
	 * Process
	 * 
	 * @return info
	 * @throws Exception
	 */
	protected String doIt() throws Exception {
		log.info("AD_Column_ID=" + p_AD_Column_ID + ", IsEncrypted="
				+ p_IsEncrypted + ", ChangeSetting=" + p_ChangeSetting
				+ ", MaxLength=" + p_MaxLength);
		MColumn column = new MColumn(getCtx(), p_AD_Column_ID, get_TrxName());
		if (column.get_ID() == 0 || column.get_ID() != p_AD_Column_ID)
			throw new AdempiereUserError("@NotFound@ @AD_Column_ID@ - "
					+ p_AD_Column_ID);
		//
		String columnName = column.getColumnName();
		int dt = column.getAD_Reference_ID();

		// Can it be enabled?
		if (column.isKey() || column.isParent() || column.isStandardColumn()
				|| column.isVirtualColumn() || column.isIdentifier()
				|| column.isTranslated() || DisplayType.isLookup(dt)
				|| DisplayType.isLOB(dt)
				|| "DocumentNo".equalsIgnoreCase(column.getColumnName())
				|| "Value".equalsIgnoreCase(column.getColumnName())
				|| "Name".equalsIgnoreCase(column.getColumnName())) {
			if (column.isEncrypted()) {
				column.setIsEncrypted(false);
				column.save();
			}
			return columnName + ": cannot be encrypted";
		}

		// Start
		addLog(0, null, null, "Encryption Class = "
				+ SecureEngine.getClassName());
		boolean error = false;

		// Test Value
		if (p_TestValue != null && p_TestValue.length() > 0) {
			String encString = SecureEngine.encrypt(p_TestValue);
			addLog(0, null, null, "Encrypted Test Value=" + encString);
			String clearString = SecureEngine.decrypt(encString);
			if (p_TestValue.equals(clearString))
				addLog(0, null, null, "Decrypted=" + clearString
						+ " (same as test value)");
			else {
				addLog(0, null, null, "Decrypted=" + clearString
						+ " (NOT the same as test value - check algorithm)");
				error = true;
			}
			int encLength = encString.length();
			addLog(0, null, null, "Test Length=" + p_TestValue.length()
					+ " -> " + encLength);
			if (encLength <= column.getFieldLength())
				addLog(0, null, null, "Encrypted Length (" + encLength
						+ ") fits into field (" + column.getFieldLength() + ")");
			else {
				addLog(0, null, null, "Encrypted Length (" + encLength
						+ ") does NOT fit into field ("
						+ column.getFieldLength() + ") - resize field");
				error = true;
			}
		}

		// Length Test
		if (p_MaxLength != 0) {
			String testClear = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			while (testClear.length() < p_MaxLength)
				testClear += testClear;
			testClear = testClear.substring(0, p_MaxLength);
			log.info("Test=" + testClear + " (" + p_MaxLength + ")");
			//
			String encString = SecureEngine.encrypt(testClear);
			int encLength = encString.length();
			addLog(0, null, null, "Test Max Length=" + testClear.length()
					+ " -> " + encLength);
			if (encLength <= column.getFieldLength())
				addLog(0, null, null, "Encrypted Max Length (" + encLength
						+ ") fits into field (" + column.getFieldLength() + ")");
			else {
				addLog(0, null, null, "Encrypted Max Length (" + encLength
						+ ") does NOT fit into field ("
						+ column.getFieldLength() + ") - resize field");
				error = true;
			}
		}

		// If only user chooses both encrypt the contents and override current
		// settings resize the physical column and encrypt all its contents.
		if (p_IsEncrypted && p_ChangeSetting) {
			// If the column has already been encrypted, show a warning message
			// and exit.
			if (column.isEncrypted()) {
				log.error("EncryptError: Column already encrypted.");
				throw new Exception();
			}
			// Init the transaction and setup the connection.
			m_trx = Trx.get(get_TrxName(), true);
			if ((m_conn = m_trx.getConnection()) == null) {
				log.warn("EncryptError: No connections available");
				throw new Exception();
			}
			m_conn.setAutoCommit(false);

			int columnID = column.get_ID();
			MTable table = MTable.get(getCtx(), column.getAD_Table_ID());
			String tableName = table.getTableName();

			// Check if the encryption exceeds the current length.
			int oldLength = column.getFieldLength();
			int newLength = encryptedColumnLength(oldLength);
			if (newLength > oldLength)
				if (changeFieldLength(columnID, columnName, newLength,
						tableName) == -1) {
					log.warn("EncryptError [ChangeFieldLength]: "
							+ "ColumnID=" + columnID + ", NewLength="
							+ newLength);
					throw new Exception();
				}

			// Encrypt column contents.
			if (encryptColumnContents(columnName, column.getAD_Table_ID()) == -1) {
				log.warn("EncryptError: No records encrypted.");
				throw new Exception();
			}
			
			if (p_IsEncrypted != column.isEncrypted()) {
				if (error || !p_ChangeSetting)
					addLog(0, null, null, "Encryption NOT changed - Encryption="
							+ column.isEncrypted());
				else {
					column.setIsEncrypted(p_IsEncrypted);
					if (column.save())
						addLog(0, null, null, "Encryption CHANGED - Encryption="
								+ column.isEncrypted());
					else
						addLog(0, null, null, "Save Error");
				}
			}
		}
		
		return "Encryption=" + column.isEncrypted();
	} // doIt

	/**
	 * Encrypt all the contents of a database column.
	 * 
	 * @param columnName
	 *            The ID of the column to be encrypted.
	 * @param tableID
	 *            The ID of the table which owns the column.
	 * @return The number of rows effected or -1 in case of errors.
	 * @throws Exception
	 */
	private int encryptColumnContents(String columnName, int tableID)
			throws Exception {
		// Find the table name
		String tableName = MTable.getTableName(getCtx(), tableID);

		return encryptColumnContents(columnName, tableName);
	} // encryptColumnContents

	/**
	 * Encrypt all the contents of a database column.
	 * 
	 * @param columnName
	 *            The ID of the column to be encrypted.
	 * @param tableName
	 *            The name of the table which owns the column.
	 * @return The number of rows effected or -1 in case of errors.
	 */
	private int encryptColumnContents(String columnName, String tableName)
			throws Exception {
		int recordsEncrypted = 0;
		String idColumnName = tableName + "_ID";

		StringBuffer selectSql = new StringBuffer();
		selectSql.append("SELECT " + idColumnName + "," + columnName);
		selectSql.append(" FROM " + tableName);
		selectSql.append(" ORDER BY " + idColumnName);

		StringBuffer updateSql = new StringBuffer();
		updateSql.append("UPDATE " + tableName);
		updateSql.append(" SET " + columnName + "=?");
		updateSql.append(" WHERE " + idColumnName + "=?");

		PreparedStatement selectStmt = null;
		PreparedStatement updateStmt = null;

		selectStmt = m_conn.prepareStatement(selectSql.toString(),
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
		updateStmt = m_conn.prepareStatement(updateSql.toString());

		ResultSet rs = selectStmt.executeQuery();

		for (recordsEncrypted = 0; rs.next(); ++recordsEncrypted) {
			// Get the row id and column value
			int id = rs.getInt(1);
			String value = rs.getString(2);
			// Encrypt the value
			value = SecureEngine.encrypt(value);
			// Update the row
			updateStmt.setString(1, value);
			updateStmt.setInt(2, id);
			if (updateStmt.executeUpdate() != 1) {
				log.warn("EncryptError: Table=" + tableName + ", ID=" + id);
				throw new Exception();
			}
		}

		rs.close();
		selectStmt.close();
		updateStmt.close();

		return recordsEncrypted;
	} // encryptColumnContents

	/**
	 * Determines the length of the encrypted column.
	 * 
	 * @param currentColSize
	 *            Current column size
	 * @return The length of the encrypted column.
	 */
	private int encryptedColumnLength(int colLength) {
		String str = "";

		for (int i = 0; i < colLength; i++) {
			str += "1";
		}
		str = SecureEngine.encrypt(str);

		return str.length();
	} // encryptedColumnLength

	/**
	 * Change the column length.
	 * 
	 * @param columnID
	 *            ID of the column
	 * @param tableName
	 *            The name of the table which owns the column
	 * @param length
	 *            New length of the column
	 * @return The number of rows effected, 1 upon success and -1 for failure.
	 */
	private int changeFieldLength(int columnID, String columnName, int length,
			String tableName) throws Exception {
		int rowsEffected = -1;

		// Select SQL
		StringBuffer selectSql = new StringBuffer();
		selectSql.append("SELECT FieldLength");
		selectSql.append(" FROM AD_Column");
		selectSql.append(" WHERE AD_Column_ID=?");

		// Alter SQL
		StringBuffer alterSql = new StringBuffer();
		alterSql.append("ALTER TABLE " + tableName);
		alterSql.append(" MODIFY " + columnName);
		alterSql.append(" NVARCHAR2(");
		alterSql.append(length + ") ");

		// Update SQL
		StringBuffer updateSql = new StringBuffer();
		updateSql.append("UPDATE AD_Column");
		updateSql.append(" SET FieldLength=" + length);
		updateSql.append(" WHERE AD_Column_ID=" + columnID);

		PreparedStatement selectStmt = null;

		selectStmt = m_conn.prepareStatement(selectSql.toString(),
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);

		selectStmt.setInt(1, columnID);
		ResultSet rs = selectStmt.executeQuery();

		if (rs.next()) {
			// Change the column size physically.
			if (DB.executeUpdate(alterSql.toString(), false, m_trx
							.getTrxName()) == -1) {
				log.warn("EncryptError [ChangeFieldLength]: ColumnID="
						+ columnID + ", NewLength=" + length);
				throw new Exception();
			}

			// Change the column size in AD.
			if (DB.executeUpdate(updateSql.toString(), false, m_trx
					.getTrxName()) == -1) {
				log.warn("EncryptError [ChangeFieldLength]: ColumnID="
						+ columnID + ", NewLength=" + length);
				throw new Exception();
			}
		}

		rs.close();
		selectStmt.close();

		// Update number of rows effected.
		rowsEffected++;

		return rowsEffected;
	} // changeFieldLength

} // EncryptionTest
