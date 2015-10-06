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
package org.compiere.impexp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.X_AD_ImpFormat;
import org.compiere.util.DB;


/**
 *	Import Format Model 
 *	
 *  @author Jorg Janke
 *  @version $Id: MImpFormat.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MImpFormat extends X_AD_ImpFormat
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3768339618622673968L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_ImpFormat_ID id
	 *  @param trxName transaction
	 */
	public MImpFormat (Properties ctx, int AD_ImpFormat_ID, String trxName)
	{
		super (ctx, AD_ImpFormat_ID, trxName);
	}	//	MImpFormat

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *  @param trxName transaction
	 */
	public MImpFormat (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MImpFormat
	
	/**
	 * 	Get (all) Rows
	 *	@return array of Rows
	 */
	public MImpFormatRow[] getRows()
	{
		ArrayList<MImpFormatRow> list = new ArrayList<MImpFormatRow>();
		String sql = "SELECT * FROM AD_ImpFormat_Row "
			+ "WHERE AD_ImpFormat_ID=? "
			+ "ORDER BY SeqNo";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, getAD_ImpFormat_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add(new MImpFormatRow (getCtx(), rs, get_TrxName()));
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "getRows", e);
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
		MImpFormatRow[] retValue = new MImpFormatRow[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getRows
	
}	//	MImpFormat
