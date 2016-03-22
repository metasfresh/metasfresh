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
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.compiere.util.DB;

/**
 *	Private Access
 *	
 *  @author Jorg Janke
 *  @version $Id: MPrivateAccess.java,v 1.3 2006/07/30 00:58:18 jjanke Exp $
 */
public class MPrivateAccess extends X_AD_Private_Access
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5649529789751432279L;

	/**
	 * 	Load Pricate Access
	 *	@param ctx context 
	 *	@param AD_User_ID user
	 *	@param AD_Table_ID table
	 *	@param Record_ID record
	 *	@return access or null if not found
	 */
	public static MPrivateAccess get (Properties ctx, int AD_User_ID, int AD_Table_ID, int Record_ID)
	{
		MPrivateAccess retValue = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM AD_Private_Access WHERE AD_User_ID=? AND AD_Table_ID=? AND Record_ID=?";
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_User_ID);
			pstmt.setInt(2, AD_Table_ID);
			pstmt.setInt(3, Record_ID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				retValue = new MPrivateAccess (ctx, rs, null); 
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error("MPrivateAccess", e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		return retValue;
	}	//	get

	/**
	 * 	Get Where Clause of Locked Records for Table
	 *	@param AD_Table_ID table
	 *	@param AD_User_ID user requesting info
	 *	@return "<>1" or " NOT IN (1,2)" or null
	 */
	public static String getLockedRecordWhere (int AD_Table_ID, int AD_User_ID)
	{
		String whereClause = " NOT IN ( SELECT Record_ID FROM AD_Private_Access WHERE AD_Table_ID = "
			+AD_Table_ID+" AND AD_User_ID <> "+AD_User_ID+" AND IsActive = 'Y' )";
		return whereClause;
	}	//	get

	
	/**	Logger					*/
	private static Logger		s_log = LogManager.getLogger(MPrivateAccess.class);
	
	/**
	 * 	Persistency Constructor
	 *	@param ctx context
	 *	@param ignored ignored
	 *	@param trxName transaction
	 */
	public MPrivateAccess (Properties ctx, int ignored, String trxName)
	{
		super(ctx, 0, trxName);
		if (ignored != 0)
			throw new IllegalArgumentException("Multi-Key");
	}	//	MPrivateAccess

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MPrivateAccess(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MPrivateAccess

	/**
	 * 	New Constructor
	 *	@param ctx context
	 *	@param AD_User_ID user
	 *	@param AD_Table_ID table
	 *	@param Record_ID record
	 */
	public MPrivateAccess (Properties ctx, int AD_User_ID, int AD_Table_ID, int Record_ID)
	{
		super(ctx, 0, null);
		setAD_User_ID (AD_User_ID);
		setAD_Table_ID (AD_Table_ID);
		setRecord_ID (Record_ID);
	}	//	MPrivateAccess

}	//	MPrivateAccess
