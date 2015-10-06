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
 * 	Web Project Domain
 *	
 *  @author Jorg Janke
 *  @version $Id: MWebProjectDomain.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class MWebProjectDomain extends X_CM_WebProject_Domain
{
	/**	serialVersionUID	*/
	private static final long serialVersionUID = 5134789895039452551L;

	/** Logger */
	private static CLogger s_log = CLogger.getCLogger (MContainer.class);

	/**
	 * 	Web Project Domain Constructor
	 *	@param ctx context
	 *	@param CM_WebProject_Domain_ID id
	 *	@param trxName transaction
	 */
	public MWebProjectDomain (Properties ctx, int CM_WebProject_Domain_ID, String trxName)
	{
		super (ctx, CM_WebProject_Domain_ID, trxName);
	}	//	MWebProjectDomain
	
	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MWebProjectDomain (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MWebProjectDomain

	/**
	 * 	get WebProjectDomain by Name
	 *	@param ctx
	 *	@param ServerName
	 *	@param trxName
	 *	@return ContainerElement
	 */
	public static MWebProjectDomain get(Properties ctx, String ServerName, String trxName) {
		MWebProjectDomain thisWebProjectDomain = null;
		String sql = "SELECT * FROM CM_WebProject_Domain WHERE lower(FQDN) LIKE ? ORDER by CM_WebProject_Domain_ID DESC";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setString(1, ServerName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				thisWebProjectDomain = (new MWebProjectDomain(ctx, rs, trxName));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, sql, e);
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
		return thisWebProjectDomain;
	}

	
}	//	MWebProjectDomain
