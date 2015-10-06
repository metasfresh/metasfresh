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
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;


/**
 *	User Query Model
 *	
 *  @author Jorg Janke
 *  @version $Id$
 */
public class MUserQuery extends X_AD_UserQuery
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6279689761765405320L;

	/**
	 * 	Get all active queries of client for Tab
	 *	@param ctx context
	 *	@param AD_Tab_ID tab
	 *	@return array of queries
	 */
	public static MUserQuery[] get (Properties ctx, int AD_Tab_ID)
	{
		int AD_User_ID = Env.getAD_User_ID(ctx);
		String sql = "SELECT * FROM AD_UserQuery "
			 + "WHERE AD_Client_ID=? AND AD_Tab_ID=? AND IsActive='Y' "
			 + "AND AD_User_ID in (0, " + AD_User_ID + ") "
			 + "ORDER BY Name";
		int AD_Client_ID = Env.getAD_Client_ID (ctx);
		ArrayList<MUserQuery> list = new ArrayList<MUserQuery>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, AD_Client_ID);
			pstmt.setInt (2, AD_Tab_ID);
			rs = pstmt.executeQuery();
			while (rs.next ())
				list.add(new MUserQuery (ctx, rs, null));
		}
		catch (Exception e)
		{
			s_log.log (Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		MUserQuery[] retValue = new MUserQuery[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	get
	
	/**
	 * 	Get Specific Tab Query
	 *	@param ctx context
	 *	@param AD_Tab_ID tab
	 *	@param name name
	 *	@return query or null
	 */
	public static MUserQuery get (Properties ctx, int AD_Tab_ID, String name)
	{
		int AD_User_ID = Env.getAD_User_ID(ctx);
		String sql = "SELECT * FROM AD_UserQuery "
			 + "WHERE AD_Client_ID=? AND AD_Tab_ID=? AND UPPER(Name) LIKE ? AND IsActive='Y' "
			 + "AND AD_User_ID in (0, " + AD_User_ID + ") "
			 + "ORDER BY Name";
		int AD_Client_ID = Env.getAD_Client_ID (ctx);
		if (name == null)
			name = "%";
		MUserQuery retValue = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, AD_Client_ID);
			pstmt.setInt (2, AD_Tab_ID);
			pstmt.setString (3, name.toUpperCase());
			rs = pstmt.executeQuery ();
			if (rs.next ())
				retValue = new MUserQuery (ctx, rs, null);
		}
		catch (Exception e)
		{
			s_log.log (Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return retValue;
	}	//	get

	/**	Logger	*/
	private static CLogger s_log = CLogger.getCLogger (MUserQuery.class);
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_UserQuery_ID id
	 *	@param trxName trx
	 */
	public MUserQuery(Properties ctx, int AD_UserQuery_ID, String trxName)
	{
		super (ctx, AD_UserQuery_ID, trxName);
	}	//	MUserQuery

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MUserQuery(Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MUserQuery
	
}	//	MUserQuery
