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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;

import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.util.Services;


/**
 *	Document Translation Sync 
 *	
 *  @author Jorg Janke
 *  @version $Id: TranslationDocSync.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class TranslationDocSync extends JavaProcess
{
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (ProcessInfoParameter element : para)
		{
			String name = element.getParameterName();
			if (element.getParameter() == null)
			{
				
			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		final ClientId clientId = ClientId.ofRepoId(getAD_Client_ID());
		if(Services.get(IClientDAO.class).isMultilingualDocumentsEnabled(clientId))
		{
			throw new AdempiereException("@AD_Client_ID@: @IsMultiLingualDocument@");
		}
		//
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
				processTable (new MTable(getCtx(), rs, null), clientId);
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
	private void processTable (MTable table, ClientId clientId)
	{
		StringBuffer sql = new StringBuffer();
		MColumn[] columns = table.getColumns(false);
		for (MColumn column : columns)
		{
			if (column.getAD_Reference_ID() == DisplayType.String
				|| column.getAD_Reference_ID() == DisplayType.Text)
			{
				String columnName = column.getColumnName();
				if (sql.length() != 0)
				{
					sql.append(",");
				}
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
			.append(clientId.getRepoId());
		
		int no = DB.executeUpdateAndSaveErrorOnFail(DB.convertSqlToNative(sql.toString()), get_TrxName());
		addLog(0, null, new BigDecimal(no), baseTable);
	}	//	processTable
	
}	//	TranslationDocSync
