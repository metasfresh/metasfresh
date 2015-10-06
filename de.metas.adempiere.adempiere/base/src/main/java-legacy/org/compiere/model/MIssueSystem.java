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
import java.util.logging.Level;

import org.compiere.util.CLogger;
import org.compiere.util.DB;

/**
 * 	Issue System Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MIssueSystem.java,v 1.3 2006/07/30 00:58:04 jjanke Exp $
 */
public class MIssueSystem extends X_R_IssueSystem
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1652388382466287269L;

	/**
	 * 	Get/Set System
	 *	@param issue issue
	 *	@return system
	 */
	static public MIssueSystem get (MIssue issue)
	{
		if (issue.getDBAddress() == null)
			return null;
		MIssueSystem system = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM R_IssueSystem WHERE DBAddress=?";
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString (1, issue.getDBAddress());
			ResultSet rs = pstmt.executeQuery ();
			if (rs.next ())
				system = new MIssueSystem(issue.getCtx(), rs, null);
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.log (Level.SEVERE, sql, e);
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
		//	New
		if (system == null)
		{
			system = new MIssueSystem(issue.getCtx(), 0, null);
			system.setDBAddress(issue.getDBAddress());
			system.setA_Asset_ID(issue.getA_Asset_ID());
		}
		system.setSystemStatus(issue.getSystemStatus());
		system.setStatisticsInfo(issue.getStatisticsInfo());
		system.setProfileInfo(issue.getProfileInfo());
		if (issue.getA_Asset_ID() != 0 
			&& system.getA_Asset_ID() != issue.getA_Asset_ID())
			system.setA_Asset_ID(issue.getA_Asset_ID());
		//
		if (!system.save())
			return null;
		
		//	Set 
		issue.setR_IssueSystem_ID(system.getR_IssueSystem_ID());
		if (system.getA_Asset_ID() != 0)
			issue.setA_Asset_ID(system.getA_Asset_ID());
		return system;
	}	//	get
	
	/**	Logger	*/
	private static CLogger s_log = CLogger.getCLogger (MIssueSystem.class);
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param R_IssueSystem_ID id
	 *	@param trxName trx
	 */
	public MIssueSystem (Properties ctx, int R_IssueSystem_ID, String trxName)
	{
		super (ctx, R_IssueSystem_ID, trxName);
	}	//	MIssueSystem

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MIssueSystem (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MIssueSystem
	
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MIssueSystem[");
		sb.append(get_ID())
			.append ("-").append (getDBAddress())
			.append(",A_Asset_ID=").append(getA_Asset_ID())
			.append ("]");
		return sb.toString ();
	}	//	toString
}	//	MIssueSystem
