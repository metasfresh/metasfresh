/**********************************************************************
* This file is part of Adempiere ERP Bazaar                           *
* http://www.adempiere.org                                            *
*                                                                     *
* Copyright (C) Jan Roessler - Schaeffer                              *
* Copyright (C) Contributors                                          *
*                                                                     *
* This program is free software; you can redistribute it and/or       *
* modify it under the terms of the GNU General Public License         *
* as published by the Free Software Foundation; either version 2      *
* of the License, or (at your option) any later version.              *
*                                                                     *
* This program is distributed in the hope that it will be useful,     *
* but WITHOUT ANY WARRANTY; without even the implied warranty of      *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
* GNU General Public License for more details.                        *
*                                                                     *
* You should have received a copy of the GNU General Public License   *
* along with this program; if not, write to the Free Software         *
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
* MA 02110-1301, USA.                                                 *
*                                                                     *
* Contributors:                                                       *
* - Jan Roessler                                                      *
* - Heng Sin Low                                                      *
*                                                                     *
* Sponsors:                                                           *
* - Schaeffer                                                         *
**********************************************************************/
package org.adempiere.util;

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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.adempiere.ad.security.IUserRolePermissions;
import org.compiere.model.MColumn;
import org.compiere.model.MQuery;
import org.compiere.model.MSearchDefinition;
import org.compiere.model.MTable;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * Executes search and opens windows for defined transaction codes
 *
 * @author Jan Roessler, jr@schaeffer-ag.de
 *
 */
public abstract class AbstractDocumentSearch {

	/** the logger */
	static CLogger log = CLogger.getCLogger(AbstractDocumentSearch.class);
	protected boolean windowOpened = false;

	/**
	 * @param searchString
	 */
	public boolean openDocumentsByDocumentNo(String searchString) {
		windowOpened = false;

		log.fine("Search started with String: " + searchString);

		// Check if / how many transaction-codes are used
		if (searchString != null && !"".equals(searchString)) {
			String[] codes = searchString.trim().replaceAll("  ", " ").split(" ");

			List<String> codeList = new ArrayList<String>();
			boolean codeSearch = true;
			searchString = "";

			// Analyze String to separate transactionCodes from searchString
			for (int i = 0; i < codes.length; i++) {
				try {
					String s = codes[i];
					if (MSearchDefinition.isValidTransactionCode(s) && codeSearch) {
						codeList.add(s);
					} else {
						// Build the searchString with eventually appearing
						// whitespaces
						codeSearch = false;
						searchString += s;
						if (i != (codes.length - 1)) {
							searchString += " ";
						}
					}
				} catch (SQLException e) {
					log.severe(e.toString());
					e.printStackTrace();
				}
			}

			// Start the search for every single code
			if (codeList.size() > 0) {
				for (int i = 0; i < codeList.size(); i++) {
					log.fine("Search with Transaction: '" + codeList.get(i) + "' for: '"
							+ searchString + "'");
					getID(codeList.get(i), searchString);
				}
			} else {
				log.fine("Search without Transaction: " + searchString);
				getID(null, searchString);
			}
		} else {
			log.fine("Search String is invalid");
		}
		return windowOpened;
	}

	/**
	 * search for id's that fit the searchString
	 *
	 * @param transactionCode
	 * @param searchString
	 */
	private void getID(String transactionCode, String searchString) {

		ResultSet rsSO = null;
		ResultSet rsPO = null;
		PreparedStatement pstmtSO = null;
		PreparedStatement pstmtPO = null;
		String sqlSO = null;
		String sqlPO = null;

		final IUserRolePermissions role = Env.getUserRolePermissions();

		try {
			for (MSearchDefinition msd : MSearchDefinition.getForCode(transactionCode)) {

				MTable table = new MTable(Env.getCtx(), msd.getAD_Table_ID(), null);
				// SearchDefinition with a given table and column
				if (msd.getSearchType().equals(MSearchDefinition.SEARCHTYPE_TABLE)) {
					MColumn column = new MColumn(Env.getCtx(), msd.getAD_Column_ID(), null);
					sqlSO = "SELECT " + table.getTableName() + "_ID FROM " + table.getTableName() + " ";
					// search for an Integer
					if (msd.getDataType().equals(MSearchDefinition.DATATYPE_INTEGER)) {
						sqlSO += "WHERE " + column.getColumnName() + "=?";
						// search for a String
					} else {
						sqlSO += "WHERE UPPER(" + column.getColumnName()+ ") LIKE UPPER(?)";
					}

					if (msd.getPO_Window_ID() != 0) {
						sqlPO = sqlSO + " AND IsSOTrx='N'";
						sqlSO += " AND IsSOTrx='Y'";
					}
					pstmtSO = DB.prepareStatement(sqlSO, null);
					pstmtPO = DB.prepareStatement(sqlPO, null);
					// search for a Integer
					if (msd.getDataType().equals(MSearchDefinition.DATATYPE_INTEGER)) {
						pstmtSO.setInt(1, Integer.valueOf(searchString.replaceAll("\\D", "")));
						if (msd.getPO_Window_ID() != 0) {
							pstmtPO.setInt(1, Integer.valueOf(searchString.replaceAll("\\D", "")));
						}
						// search for a String
					} else if (msd.getDataType().equals(MSearchDefinition.DATATYPE_STRING)) {
						pstmtSO.setString(1, searchString);
						if (msd.getPO_Window_ID() != 0) {
							pstmtPO.setString(1, searchString);
						}
					}
					// SearchDefinition with a special query
				} else if (msd.getSearchType().equals(MSearchDefinition.SEARCHTYPE_QUERY)) {
					sqlSO = msd.getQuery();
					pstmtSO = DB.prepareStatement(sqlSO, null);
					// count '?' in statement
					int count = 1;
					for (char c : sqlSO.toCharArray()) {
						if (c == '?') {
							count++;
						}
					}
					for (int i = 1; i < count; i++) {
						if (msd.getDataType().equals(MSearchDefinition.DATATYPE_INTEGER)) {
							pstmtSO.setInt(i, Integer.valueOf(searchString.replaceAll("\\D", "")));
						} else if (msd.getDataType().equals(MSearchDefinition.DATATYPE_STRING)) {
							pstmtSO.setString(i, searchString);
						}
					}
				}
				if (pstmtSO != null) {
					log.fine("SQL Sales: " + sqlSO);
					rsSO = pstmtSO.executeQuery();
					Vector<Integer> idSO = new Vector<Integer>();
					while (rsSO.next()) {
						idSO.add(new Integer(rsSO.getInt(1)));
					}
					if (role.getWindowAccess(msd.getAD_Window_ID()) != null) {
						log.fine("Open Window: " + msd.getAD_Window_ID() + " / Table: "
								+ table.getTableName() + " / Number of Results: " + idSO.size());

						if (idSO.size() == 0 && (searchString == null || searchString.trim().length() == 0)) {
							// No search string - open the window with new record
							idSO.add(new Integer(0));
						}

						openWindow(idSO, table.getTableName(), msd.getAD_Window_ID());
					} else {
						log.warning("Role is not allowed to view this window");
					}
				}
				if (pstmtPO != null) {
					log.fine("SQL Purchase: " + sqlPO);
					rsPO = pstmtPO.executeQuery();
					Vector<Integer> idPO = new Vector<Integer>();
					while (rsPO.next()) {
						idPO.add(new Integer(rsPO.getInt(1)));
					}
					if (role.getWindowAccess(msd.getPO_Window_ID()) != null) {
						log.fine("Open Window: " + msd.getPO_Window_ID() + " / Table: "
								+ table.getTableName() + " / Number of Results: " + idPO.size());
						openWindow(idPO, table.getTableName(), msd.getPO_Window_ID());
					} else {
						log.warning("Role is not allowed to view this window");
					}
				}
				DB.close(rsSO, pstmtSO);
				DB.close(rsPO, pstmtPO);
				pstmtSO = null;
				pstmtPO = null;
				rsSO = null;
				rsPO = null;
			}
		} catch (Exception e) {
			log.severe(e.toString());
			e.printStackTrace();
		} finally {
			DB.close(rsSO, pstmtSO);
			DB.close(rsPO, pstmtPO);
			rsSO = null;
			rsPO = null;
			pstmtSO = null;
			pstmtPO = null;
		}
	}

	/**
	 * opens window with the given documents
	 *
	 * @param ids
	 *            - document id's
	 * @param tableName
	 * @param windowId
	 */
	private void openWindow(Vector<Integer> ids, String tableName, int windowId) {
		if (ids == null || ids.size() == 0) {
			return;
		}
		String whereString = " " + tableName + "_ID";
		// create query string
		if (ids.size() == 1) {
			if (ids.get(0).intValue() == 0) {
				whereString = null;
			} else {
				whereString += "=" + ids.get(0).intValue();
			}
		} else {
			whereString += " IN (";
			for (int i = 0; i < ids.size(); i++) {
				whereString += ids.get(i).intValue();
				if (i < ids.size() - 1) {
					whereString += ",";
				} else {
					whereString += ") ";
				}
			}
		}
		log.fine(whereString);

		final MQuery query = new MQuery(tableName);
		query.addRestriction(whereString);
		final boolean ok = openWindow(windowId, query);
		if (!ok) {
			log.severe("Unable to open window: " + whereString);
		}
		if (!windowOpened && ok)
			windowOpened = true;
	}

	/**
	 * @param windowId
	 * @param query
	 * @return true if windowId open successfully
	 */
	protected abstract boolean openWindow(int windowId, MQuery query);
}
