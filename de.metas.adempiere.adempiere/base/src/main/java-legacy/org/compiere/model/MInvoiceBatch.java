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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.DB;
import org.compiere.util.Env;


/**
 *	Invoice Batch Header Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MInvoiceBatch.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MInvoiceBatch extends X_C_InvoiceBatch
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3449653049236263604L;


	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_InvoiceBatch_ID id
	 *	@param trxName trx
	 */
	public MInvoiceBatch (Properties ctx, int C_InvoiceBatch_ID, String trxName)
	{
		super (ctx, C_InvoiceBatch_ID, trxName);
		if (C_InvoiceBatch_ID == 0)
		{
		//	setDocumentNo (null);
		//	setC_Currency_ID (0);	// @$C_Currency_ID@
			setControlAmt (Env.ZERO);	// 0
			setDateDoc (new Timestamp(System.currentTimeMillis()));	// @#Date@
			setDocumentAmt (Env.ZERO);
			setIsSOTrx (false);	// N
			setProcessed (false);
		//	setSalesRep_ID (0);
		}
	}	//	MInvoiceBatch

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MInvoiceBatch (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MInvoiceBatch
	
	/**	The Lines						*/
	private MInvoiceBatchLine[]	m_lines	= null;

	
	/**
	 * 	Get Lines
	 *	@param reload reload data
	 *	@return array of lines
	 */
	public MInvoiceBatchLine[] getLines (boolean reload)
	{
		if (m_lines != null && !reload) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		String sql = "SELECT * FROM C_InvoiceBatchLine WHERE C_InvoiceBatch_ID=? ORDER BY Line";
		ArrayList<MInvoiceBatchLine> list = new ArrayList<MInvoiceBatchLine>();
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, getC_InvoiceBatch_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				list.add (new MInvoiceBatchLine (getCtx(), rs, get_TrxName()));
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log (Level.SEVERE, sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		//
		m_lines = new MInvoiceBatchLine[list.size ()];
		list.toArray (m_lines);
		return m_lines;
	}	//	getLines

	
	/**
	 * 	Set Processed
	 *	@param processed processed
	 */
	public void setProcessed (boolean processed)
	{
		super.setProcessed (processed);
		if (get_ID() == 0)
			return;
		String set = "SET Processed='"
			+ (processed ? "Y" : "N")
			+ "' WHERE C_InvoiceBatch_ID=" + getC_InvoiceBatch_ID();
		int noLine = DB.executeUpdate("UPDATE C_InvoiceBatchLine " + set, get_TrxName());
		m_lines = null;
		log.fine(processed + " - Lines=" + noLine);
	}	//	setProcessed
	
}	//	MInvoiceBatch
