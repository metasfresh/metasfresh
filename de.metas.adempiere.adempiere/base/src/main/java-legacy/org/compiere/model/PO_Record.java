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
package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * 	Maintain AD_Table_ID/Record_ID constraint
 *	
 *  @author Jorg Janke
 *  @version $Id: PO_Record.java,v 1.4 2006/07/30 00:58:04 jjanke Exp $
 */
public class PO_Record
{
	/**	Parent Tables		*/
	private static int[]	s_parents =	new int[]{
		X_C_Order.Table_ID,
		X_CM_Container.Table_ID
	};
	private static String[]	s_parentNames = new String[]{
		X_C_Order.Table_Name,
		X_CM_Container.Table_Name
	};
	private static int[]	s_parentChilds = new int[]{
		X_C_OrderLine.Table_ID,
		X_CM_Container_Element.Table_ID
	};
	private static String[]	s_parentChildNames = new String[]{
		X_C_OrderLine.Table_Name,
		X_CM_Container_Element.Table_Name
	};
	
	
	
	/**	Cascade Table ID			*/
	private static int[]	s_cascades =	new int[]{
		X_AD_Attachment.Table_ID,
		InterfaceWrapperHelper.getTableId(I_AD_Archive.class),
	//	X_CM_ContainerTTable.Table_ID,
	//	X_CM_CStageTTable.Table_ID,
		X_K_Index.Table_ID,
		X_AD_Note.Table_ID
	};
	/**	Cascade Table Names			*/
	private static String[]	s_cascadeNames = new String[]{
		X_AD_Attachment.Table_Name,
		X_AD_Archive.Table_Name,
	//	X_CM_ContainerTTable.Table_Name,
	//	X_CM_CStageTTable.Table_Name,
		X_K_Index.Table_Name,
		X_AD_Note.Table_Name
	};

	/**	Restrict Table ID			*/
	private static int[]	s_restricts =	new int[]{
		InterfaceWrapperHelper.getTableId(I_R_Request.class),
		X_CM_Chat.Table_ID
	//	X_Fact_Acct.Table_ID
	};
	/**	Restrict Table Names			*/
	private static String[]	s_restrictNames = new String[]{
		X_R_Request.Table_Name,
		X_CM_Chat.Table_Name
	//	X_Fact_Acct.Table_Name
	};

	/**	Logger	*/
	private static Logger log = LogManager.getLogger(PO_Record.class);
	
	/**
	 * 	Delete Cascade including (selected)parent relationships
	 *	@param AD_Table_ID table
	 *	@param Record_ID record
	 *	@param trxName transaction
	 *	@return false if could not be deleted
	 */
	static boolean deleteCascade (int AD_Table_ID, int Record_ID, String trxName)
	{
		//	Table Loop
		for (int i = 0; i < s_cascades.length; i++)
		{
			//	DELETE FROM table WHERE AD_Table_ID=#1 AND Record_ID=#2
			if (s_cascades[i] != AD_Table_ID)
			{
				Object[] params = new Object[]{new Integer(AD_Table_ID), new Integer(Record_ID)};
				final StringBuilder sql = new StringBuilder("DELETE FROM ")
					.append(s_cascadeNames[i])
					.append(" WHERE AD_Table_ID=? AND Record_ID=?");
				int no = DB.executeUpdate(sql.toString(), params, false, trxName);
				if (no > 0)
					log.info(s_cascadeNames[i] + " (" + AD_Table_ID + "/" + Record_ID + ") #" + no);
				else if (no < 0)
				{
					log.error(s_cascadeNames[i] + " (" + AD_Table_ID + "/" + Record_ID + ") #" + no);
					return false;
				}
			}
		}
		//	Parent Loop
		for (int j = 0; j < s_parents.length; j++)
		{
			// DELETE FROM AD_Attachment WHERE AD_Table_ID=1 AND Record_ID IN 
			//	(SELECT C_InvoiceLine_ID FROM C_InvoiceLine WHERE C_Invoice_ID=1)
			if (s_parents[j] == AD_Table_ID)
			{
				int AD_Table_IDchild = s_parentChilds[j];
				Object[] params = new Object[]{new Integer(AD_Table_IDchild), new Integer(Record_ID)};
				for (int i = 0; i < s_cascades.length; i++)
				{
					final StringBuilder sql = new StringBuilder("DELETE FROM ")
						.append(s_cascadeNames[i])
						.append(" WHERE AD_Table_ID=? AND Record_ID IN (SELECT ")
						.append(s_parentChildNames[j]).append("_ID FROM ")
						.append(s_parentChildNames[j]).append(" WHERE ")
						.append(s_parentNames[j]).append("_ID=?)");
					int no = DB.executeUpdate(sql.toString(), params, false, trxName);
					if (no > 0)
						log.info(s_cascadeNames[i] + " " + s_parentNames[j]  
		                   + " (" + AD_Table_ID + "/" + Record_ID + ") #" + no);
					else if (no < 0)
					{
						log.error(s_cascadeNames[i] + " " + s_parentNames[j]
						   + " (" + AD_Table_ID + "/" + Record_ID + ") #" + no);
						return false;
					}
				}
			}
		}
		return true;
	}	//	deleteCascase

	/**
	 * 	An entry Exists for restrict table/record combination
	 *	@param AD_Table_ID table
	 *	@param Record_ID record
	 *	@param trxName transaction
	 *	@return error message (Table Name) or null
	 */
	static String exists (int AD_Table_ID, int Record_ID, String trxName)
	{
		//	Table Loop only
		for (int i = 0; i < s_restricts.length; i++)
		{
			//	SELECT COUNT(*) FROM table WHERE AD_Table_ID=#1 AND Record_ID=#2
			final StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ")
				.append(s_restrictNames[i])
				.append(" WHERE AD_Table_ID=? AND Record_ID=?");
			final int no = DB.getSQLValue(trxName, sql.toString(), AD_Table_ID, Record_ID);
			if (no > 0)
				return s_restrictNames[i];
		}
		return null;
	}	//	exists

	/**
	 * 	Validate all tables for AD_Table/Record_ID relationships
	 */
	static void validate ()
	{
		String sql = "SELECT AD_Table_ID, TableName FROM AD_Table WHERE IsView='N' ORDER BY TableName";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				validate (rs.getInt(1), rs.getString(2));
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
	}	//	validate
	
	/**
	 * 	Validate all tables for AD_Table/Record_ID relationships
	 *	@param AD_Table_ID table
	 */
	static void validate (int AD_Table_ID)
	{
		MTable table = new MTable(Env.getCtx(), AD_Table_ID, null);
		if (table.isView())
			log.warn("Ignored - View " + table.getTableName());
		else
			validate (table.getAD_Table_ID(), table.getTableName());
	}	//	validate

	/**
	 * 	Validate Table for Table/Record
	 *	@param AD_Table_ID table
	 *	@param TableName Name
	 */
	static private void validate (int AD_Table_ID, String TableName)
	{
		for (int i = 0; i < s_cascades.length; i++)
		{
			final StringBuilder sql = new StringBuilder("DELETE FROM ")
				.append(s_cascadeNames[i])
				.append(" WHERE AD_Table_ID=").append(AD_Table_ID)
				.append(" AND Record_ID NOT IN (SELECT ")
				.append(TableName).append("_ID FROM ").append(TableName).append(")");
			int no = DB.executeUpdate(sql.toString(), null);
			if (no > 0)
				log.info(s_cascadeNames[i] + " (" + AD_Table_ID + "/" + TableName 
					+ ") Invalid #" + no);
		}
	}	//	validate
	
	
}	//	PO_Record
