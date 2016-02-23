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
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Request Callouts
 *	
 *  @author Jorg Janke
 *  @version $Id: CalloutRequest.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class CalloutRequest extends CalloutEngine
{
	/**
	 *  Request - Copy Mail Text - <b>Callout</b>
	 *
	 *  @param ctx      Context
	 *  @param WindowNo current Window No
	 *  @param mTab     Model Tab
	 *  @param mField   Model Field
	 *  @param value    The new value
	 *  @return Error message or ""
	 */
	public String copyMail (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		String colName = mField.getColumnName();
		log.info(colName + "=" + value);
		if (value == null)
			return "";

		Integer R_MailText_ID = (Integer)value;
		MMailText mailtext = new MMailText(ctx, R_MailText_ID.intValue(), null);

		Integer userID = (Integer) mTab.getValue("AD_User_ID");
		if (userID != null)
			mailtext.setUser(userID.intValue());
		Integer bpID = (Integer) mTab.getValue("C_BPartner_ID");
		if (bpID != null)
			mailtext.setBPartner(bpID.intValue());
		
		String txt = mailtext.getMailText();
		txt = Env.parseContext(ctx, WindowNo, txt, false, true);
		mTab.setValue("Result", txt);

		return "";
	}   //  copyText

	
	/**
	 *  Request - Copy Response Text - <b>Callout</b>
	 *
	 *  @param ctx      Context
	 *  @param WindowNo current Window No
	 *  @param mTab     Model Tab
	 *  @param mField   Model Field
	 *  @param value    The new value
	 *  @return Error message or ""
	 */
	public String copyResponse (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		String colName = mField.getColumnName();
		log.info(colName + "=" + value);
		if (value == null)
			return "";

		Integer R_StandardResponse_ID = (Integer)value;
		String sql = "SELECT Name, ResponseText FROM R_StandardResponse "
			+ "WHERE R_StandardResponse_ID=?";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, R_StandardResponse_ID.intValue());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				String txt = rs.getString(2);
				txt = Env.parseContext(ctx, WindowNo, txt, false, true);
				mTab.setValue("Result", txt);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		return "";
	}   //  copyResponse

	/**
	 *  Request - Chane of Request Type - <b>Callout</b>
	 *
	 *  @param ctx      Context
	 *  @param WindowNo current Window No
	 *  @param mTab     Model Tab
	 *  @param mField   Model Field
	 *  @param value    The new value
	 *  @return Error message or ""
	 */
	public String type (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		String colName = mField.getColumnName();
		log.info(colName + "=" + value);
		mTab.setValue("R_Status_ID", null);
		if (value == null)
			return "";
		int R_RequestType_ID = ((Integer)value).intValue();
		if (R_RequestType_ID == 0)
			return "";
		MRequestType rt = MRequestType.get(ctx, R_RequestType_ID);
		int R_Status_ID = rt.getDefaultR_Status_ID();
		if (R_Status_ID != 0)
			mTab.setValue("R_Status_ID", new Integer(R_Status_ID));
		
		return "";
	}	//	type
}	//	CalloutRequest
