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
package org.compiere.print;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Print Format Utilities.
 *	- Add Missing Columns for all Print Format
 *	
 *  @author Jorg Janke
 *  @version $Id: PrintFormatUtil.java,v 1.2 2006/07/30 00:53:02 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>FR [ 1841834 ] PrintFormatUtil: work in transaction
 */
public class PrintFormatUtil
{
	/**
	 * 	Print Format Utility
	 *	@param ctx context
	 */
	public PrintFormatUtil(Properties ctx)
	{
		super();
		m_ctx = ctx;
	}	//	PrintFormatUtil

	/**	Logger					*/
	private CLogger			log = CLogger.getCLogger (getClass());
	/** Context					*/
	private Properties		m_ctx;
	

	/**
	 * @deprecated use {@link #addMissingColumns(String)}
	 */
	public void addMissingColumns ()
	{
		addMissingColumns((String)null);
	}
	/**
	 * 	Add Missing Columns for all Print Format
	 */
	public void addMissingColumns (String trxName)
	{
		int total = 0;
		String sql = "SELECT * FROM AD_PrintFormat pf "
			+ "ORDER BY Name";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			rs = pstmt.executeQuery();
			while (rs.next())
				total += addMissingColumns(new MPrintFormat (m_ctx, rs, trxName));
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		log.info ("Total = " + total);
	}	//	addMissingColumns


	/**
	 * 	Add Missing Columns for Print Format
	 *	@param pf print format
	 *	@return no of columns created
	 */
	public int addMissingColumns (MPrintFormat pf)
	{
		log.config(pf.toString());
		String sql = "SELECT c.AD_Column_ID, c.ColumnName "
			+ "FROM AD_Column c "
			+ "WHERE NOT EXISTS "
				+ "(SELECT * "
				+ "FROM AD_PrintFormatItem pfi"
				+ " INNER JOIN AD_PrintFormat pf ON (pfi.AD_PrintFormat_ID=pf.AD_PrintFormat_ID) "
				+ "WHERE pf.AD_Table_ID=c.AD_Table_ID"
				+ " AND pfi.AD_Column_ID=c.AD_Column_ID"
				+ " AND pfi.AD_PrintFormat_ID=?)"	//	1 
			+ " AND c.AD_Table_ID=? "				//	2
			+ "ORDER BY c.AD_Column_ID";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int counter = 0;
		try
		{
			pstmt = DB.prepareStatement(sql, pf.get_TrxName());
			pstmt.setInt(1, pf.getAD_PrintFormat_ID());
			pstmt.setInt(2, pf.getAD_Table_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				int AD_Column_ID = rs.getInt(1);
				String ColumnName = rs.getString(2);
				MPrintFormatItem pfi = MPrintFormatItem.createFromColumn (pf, AD_Column_ID, 0);
				if (pfi.get_ID() != 0)
					log.fine("#" + ++counter + " - added " + ColumnName);
				else
					log.warning("Not added: " + ColumnName);
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		if (counter == 0)
			log.fine("None"
				/**
				+ " - " + sql 
				+ " - AD_PrintFormat_ID=" + pf.getAD_PrintFormat_ID()
				+ " - AD_Table_ID=" + pf.getAD_Table_ID()
				*/
				);
		else
			log.fine("Added=" + counter);
		return counter;
	}	//	addMissingColumns


	/**************************************************************************
	 * 	Main
	 *	@param args arguments
	 */
	public static void main(String[] args)
	{
		org.compiere.Adempiere.startupEnvironment(true);
		//
		PrintFormatUtil pfu = new PrintFormatUtil (Env.getCtx());
		pfu.addMissingColumns((String)null);
	}	//	main
	
}	//	PrintFormatUtils
