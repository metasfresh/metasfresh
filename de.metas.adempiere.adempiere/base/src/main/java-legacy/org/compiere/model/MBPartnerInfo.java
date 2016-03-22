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
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.security.IUserRolePermissions;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Business Partner Info Model for Query
 *	
 *  @author Jorg Janke
 *  @version $Id: MBPartnerInfo.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MBPartnerInfo extends X_RV_BPartner
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7947336480731180299L;

	/**
	 * 	Find BPartners
	 *	@param ctx context
	 *	@param Value Business Partner Value
	 *	@param Name Business Partner Name
	 *	@param Contact Contact/User Name
	 *	@param EMail Contact/User EMail
	 *	@param Phone phone
	 *	@param City city
	 *	@return array if of info
	 */
	public static MBPartnerInfo[] find (Properties ctx, 
		String Value, String Name, String Contact, String EMail, String Phone, String City)
	{
		StringBuffer sql = new StringBuffer ("SELECT * FROM RV_BPartner WHERE IsActive='Y'");
		StringBuffer sb = new StringBuffer();
		Value = getFindParameter (Value);
		if (Value != null)
			sb.append("UPPER(Value) LIKE ?");
		Name = getFindParameter (Name);
		if (Name != null)
		{
			if (sb.length() > 0)
				sb.append(" OR ");
			sb.append("UPPER(Name) LIKE ?");
		}
		Contact = getFindParameter (Contact);
		if (Contact != null)
		{
			if (sb.length() > 0)
				sb.append(" OR ");
			sb.append("UPPER(ContactName) LIKE ?");
		}
		EMail = getFindParameter (EMail);
		if (EMail != null)
		{
			if (sb.length() > 0)
				sb.append(" OR ");
			sb.append("UPPER(EMail) LIKE ?");
		}
		Phone = getFindParameter (Phone);
		if (Phone != null)
		{
			if (sb.length() > 0)
				sb.append(" OR ");
			sb.append("UPPER(Phone) LIKE ?");
		}
		City = getFindParameter (City);
		if (City != null)
		{
			if (sb.length() > 0)
				sb.append(" OR ");
			sb.append("UPPER(City) LIKE ?");
		}
		if (sb.length() > 0)
			sql.append(" AND (").append(sb).append(")");
		sql.append(" ORDER BY Value");
		//
		String finalSQL = Env.getUserRolePermissions().addAccessSQL(sql.toString(), 
			"RV_BPartner", IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		ArrayList<MBPartnerInfo> list = new ArrayList<MBPartnerInfo>();
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(finalSQL, null);
			int index = 1;
			if (Value != null)
				pstmt.setString(index++, Value);
			if (Name != null)
				pstmt.setString(index++, Name);
			if (Contact != null)
				pstmt.setString(index++, Contact);
			if (EMail != null)
				pstmt.setString(index++, EMail);
			if (Phone != null)
				pstmt.setString(index++, Phone);
			if (City != null)
				pstmt.setString(index++, City);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MBPartnerInfo (ctx, rs, null));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error("find - " + finalSQL, e);
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
		//	Return
		MBPartnerInfo[] retValue = new MBPartnerInfo[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	find
	
	
	/**	Static Logger	*/
	private static Logger	s_log	= LogManager.getLogger(MBPartnerInfo.class);
	
	/**************************************************************************
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MBPartnerInfo (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MBPartnerInfo

}	//	MBPartnerInfo
