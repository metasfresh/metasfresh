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
*                                                                     *
* Sponsors:                                                           *
* - Schaeffer                                                         *
**********************************************************************/

package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * @author Jan Roessler, jr@schaeffer-ag.de
 *
 */
public class MSearchDefinition extends X_AD_SearchDefinition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2448668218372939766L;
	/** Constant for the searchtype table */
	public static final String SEARCHTYPE_TABLE = "T";
	/** Constant for the searchtype query */
	public static final String SEARCHTYPE_QUERY = "Q";
	/** Constant for the datatype String */
	public static final String DATATYPE_STRING = "S";
	/** Constant for the datatype Integer */
	public static final String DATATYPE_INTEGER = "I";
	
	
	/**
	 * @param ctx
	 * @param AD_SearchDefinition_ID
	 * @param trxName
	 */
	public MSearchDefinition(Properties ctx, int AD_SearchDefinition_ID, String trxName) {
		super(ctx, AD_SearchDefinition_ID, trxName);
	}

	/**
	 * Returns all SearchDefinition objects with the given transaction code
	 * 
	 * @param transactionCode
	 * @return
	 * @throws SQLException
	 */
	public static List<MSearchDefinition> getForCode(String transactionCode) throws SQLException {

		List<MSearchDefinition> list = new ArrayList<MSearchDefinition>();

		String sql = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		sql = "SELECT AD_SearchDefinition_ID FROM AD_SearchDefinition WHERE IsActive = 'Y' ";

		if (transactionCode != null) {
			sql += "AND UPPER(TransactionCode) = ?";
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, transactionCode.toUpperCase());
		} else {
			sql += "AND IsDefault = 'Y'";
			pstmt = DB.prepareStatement(sql, null);
		}

		if (pstmt != null) {
			rs = pstmt.executeQuery();

			while (rs.next()) {
				MSearchDefinition msd = new MSearchDefinition(Env.getCtx(), rs.getInt(1), null);
				if (msd.isActive()) {
					list.add(msd);
				}
			}
		}
		DB.close(rs, pstmt);
		return list;

	}

	public static boolean isValidTransactionCode(String transactionCode) throws SQLException {

		boolean found = false;

		if (transactionCode != null) {
			String sql = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			sql = "SELECT AD_SearchDefinition_ID FROM AD_SearchDefinition WHERE UPPER(TransactionCode) = ? AND IsActive = 'Y'";

			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, transactionCode.toUpperCase());

			if (pstmt != null) {
				rs = pstmt.executeQuery();

				while (rs.next()) {
					found = true;
				}
			}
			DB.close(rs, pstmt);
		}
		return found;
	}
}