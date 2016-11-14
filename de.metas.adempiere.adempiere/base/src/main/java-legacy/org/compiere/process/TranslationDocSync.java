/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.compiere.model.MClient;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;


/**
 *	Document Translation Sync 
 *	
 *  @author Jorg Janke
 *  @version $Id: TranslationDocSync.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class TranslationDocSync extends SvrProcess
{
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else
				log.error("Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	protected String doIt() throws Exception
	{
		MClient client = MClient.get(getCtx());
		if (client.isMultiLingualDocument())
			throw new AdempiereUserError("@AD_Client_ID@: @IsMultiLingualDocument@");
		//
		log.info("" + client);
		String sql = "SELECT * FROM AD_Table "
			+ "WHERE TableName LIKE '%_Trl' AND TableName NOT LIKE 'AD%' "
			+ "ORDER BY TableName";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				processTable (new MTable(getCtx(), rs, null), client.getAD_Client_ID());
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		
		
		return "OK";
	}	//	doIt
	
	/**
	 * 	Process Translation Table
	 *	@param table table
	 */
	private void processTable (MTable table, int AD_Client_ID)
	{
		StringBuffer sql = new StringBuffer();
		MColumn[] columns = table.getColumns(false);
		for (int i = 0; i < columns.length; i++)
		{
			MColumn column = columns[i];
			if (column.getAD_Reference_ID() == DisplayType.String
				|| column.getAD_Reference_ID() == DisplayType.Text)
			{
				String columnName = column.getColumnName();
				if (sql.length() != 0)
					sql.append(",");
				sql.append(columnName);
			}
		}
		String baseTable = table.getTableName();
		baseTable = baseTable.substring(0, baseTable.length()-4);
		
		log.info(baseTable + ": " + sql);
		String columnNames = sql.toString();
		
		sql = new StringBuffer();
		sql.append("UPDATE ").append(table.getTableName()).append(" t SET (")
			.append(columnNames).append(") = (SELECT ").append(columnNames)
			.append(" FROM ").append(baseTable).append(" b WHERE t.")
			.append(baseTable).append("_ID=b.").append(baseTable).append("_ID) WHERE AD_Client_ID=")
			.append(AD_Client_ID);
		
		int no = DB.executeUpdate(DB.convertSqlToNative(sql.toString()), get_TrxName());
		addLog(0, null, new BigDecimal(no), baseTable);
	}	//	processTable
	
}	//	TranslationDocSync
