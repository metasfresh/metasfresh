/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                          * 
 * http://www.adempiere.org                                           * 
 *                                                                    * 
 * Copyright (C) Trifon Trifonov.                                     * 
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
 *  - Trifon Trifonov (trifonnt@users.sourceforge.net)                *
 *                                                                    *
 * Sponsors:                                                          *
 *  - e-Evolution (http://www.e-evolution.com/)                       *
 *********************************************************************/

package org.compiere.model;

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
import java.util.Properties;
import org.slf4j.Logger;

import de.metas.cache.CacheMgt;
import de.metas.logging.LogManager;

import org.compiere.util.DB;

/**
 * @author Trifon N. Trifonov
 */
public class MEXPFormatLine extends X_EXP_FormatLine {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1855089248134520749L;
	/**	Static Logger	*/
	private static Logger	s_log	= LogManager.getLogger(X_EXP_FormatLine.class);
	
	
	
	public MEXPFormatLine(Properties ctx, int C_EDIFormat_Line_ID, String trxName) {
		super(ctx, C_EDIFormat_Line_ID, trxName);
	}
	
	public MEXPFormatLine (Properties ctx, ResultSet rs, String trxName) {
		super (ctx, rs, trxName);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer ("X_EXP_FormatLine[ID=").append(get_ID()).append("; Value="+getValue()+"; Type="+getType()+"]");
		return sb.toString();
	}
	
	public static MEXPFormatLine getFormatLineByValue(Properties ctx, String value, int EXP_Format_ID, String trxName) 
	throws SQLException 
	{
	MEXPFormatLine result = null;
	                   
	StringBuffer sql = new StringBuffer("SELECT * ")
		.append(" FROM ").append(X_EXP_FormatLine.Table_Name)
		.append(" WHERE ").append(X_EXP_Format.COLUMNNAME_Value).append("=?")
		//.append(" AND IsActive = ?")
		//.append(" AND AD_Client_ID = ?")
		.append(" AND ").append(X_EXP_Format.COLUMNNAME_EXP_Format_ID).append(" = ?")
	;
	PreparedStatement pstmt = null;
	try {
		pstmt = DB.prepareStatement (sql.toString(), trxName);
		pstmt.setString(1, value);
		pstmt.setInt(2, EXP_Format_ID);
		ResultSet rs = pstmt.executeQuery ();
		if ( rs.next() ) {
			result = new MEXPFormatLine (ctx, rs, trxName);
		}
		rs.close ();
		pstmt.close ();
		pstmt = null;
	} catch (SQLException e) {
		s_log.error(sql.toString(), e);
		throw e;
	} finally {
		try	{
			if (pstmt != null) pstmt.close ();
			pstmt = null;
		} catch (Exception e) {	pstmt = null; }
	}
	
	return result;
	}

	@Override
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if(!success)
		{
			return false;
		}
		
		CacheMgt.get().reset(I_EXP_Format.Table_Name);
		
		return true;
	}

}
