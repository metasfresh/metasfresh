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
 * 	Issue Project (and Asset Link)
 *	
 *  @author Jorg Janke
 *  @version $Id: MIssueProject.java,v 1.2 2006/07/30 00:58:18 jjanke Exp $
 */
public class MIssueProject extends X_R_IssueProject
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9115162283984109370L;


	/**
	 * 	Get/Set Project
	 *	@param issue issue
	 *	@return project
	 */
	static public MIssueProject get (MIssue issue)
	{
		if (issue.getName() == null)
			return null;
		MIssueProject pj = null;
		String sql = "SELECT * FROM R_IssueProject WHERE Name=?";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setString (1, issue.getName());
			ResultSet rs = pstmt.executeQuery ();
			if (rs.next ())
				pj = new MIssueProject(issue.getCtx(), rs, null);
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
		if (pj == null)
		{
			pj = new MIssueProject(issue.getCtx(), 0, null);
			pj.setName(issue.getName());
			pj.setA_Asset_ID(issue);
		}
		pj.setSystemStatus(issue.getSystemStatus());
		pj.setStatisticsInfo(issue.getStatisticsInfo());
		pj.setProfileInfo(issue.getProfileInfo());
		if (!pj.save())
			return null;
		
		//	Set 
		issue.setR_IssueProject_ID(pj.getR_IssueProject_ID());
		if (pj.getA_Asset_ID() != 0)
			issue.setA_Asset_ID(pj.getA_Asset_ID());
		return pj;
	}	//	get
	
	/**	Logger	*/
	private static CLogger s_log = CLogger.getCLogger (MIssueProject.class);
	
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param R_IssueProject_ID id
	 *	@param trxName trx
	 */
	public MIssueProject (Properties ctx, int R_IssueProject_ID, String trxName)
	{
		super (ctx, R_IssueProject_ID, trxName);
	}	//	MIssueProject

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MIssueProject (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MIssueProject
	
	/**
	 * 	Set A_Asset_ID
	 *	@param issue issue
	 */
	public void setA_Asset_ID (MIssue issue)
	{
		int A_Asset_ID = 0;
		String sql = "SELECT * FROM A_Asset a "
			+ "WHERE EXISTS (SELECT * FROM A_Asset_Group ag "	//	Tracking Assets
				+ "WHERE a.A_Asset_Group_ID=ag.A_Asset_Group_ID AND ag.IsTrackIssues='Y')"
			+ " AND EXISTS (SELECT * FROM AD_User u "
				+ "WHERE (a.C_BPartner_ID=u.C_BPartner_ID OR a.C_BPartnerSR_ID=u.C_BPartner_ID)"
				+ " AND u.EMail=?)"					//	#1 EMail
			+ " AND (SerNo IS NULL OR SerNo=?)";	//	#2 Name
		
		
		
		
		super.setA_Asset_ID (A_Asset_ID);
	}	//	setA_Asset_ID
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MIssueProject[");
		sb.append (get_ID())
			.append ("-").append (getName())
			.append(",A_Asset_ID=").append(getA_Asset_ID())
			.append(",C_Project_ID=").append(getC_Project_ID())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
}	//	MIssueProject
