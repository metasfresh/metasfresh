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

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Msg;

/**
 *	Request History Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MRequestAction.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class MRequestAction extends X_R_RequestAction
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2902231219773596011L;


	/**
	 * 	Persistency Constructor
	 *	@param ctx context
	 *	@param R_RequestAction_ID id
	 */
	public MRequestAction (Properties ctx, int R_RequestAction_ID, String trxName)
	{
		super (ctx, R_RequestAction_ID, trxName);
	}	//	MRequestAction

	/**
	 * 	Load Construtor
	 *	@param ctx context
	 *	@param rs result set
	 */
	public MRequestAction(Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MRequestAction

	/**
	 * 	Parent Action Constructor
	 *	@param request parent
	 *	@param newRecord new (copy all)
	 */
	public MRequestAction (MRequest request, boolean newRecord)
	{
		this (request.getCtx(), 0, request.get_TrxName());
		setClientOrg(request);
		setR_Request_ID (request.getR_Request_ID());
	}	//	MRequestAction

	/**
	 * 	Add Null Column
	 *	@param columnName
	 */
	public void addNullColumn (String columnName)
	{
		String nc = getNullColumns();
		if (nc == null)
			setNullColumns(columnName);
		else
			setNullColumns(nc + ";" + columnName);
	}	//	addNullColumn
	

	/**
	 * 	Get Name of creator
	 *	@return name
	 */
	public String getCreatedByName()
	{
		MUser user = MUser.get(getCtx(), getCreatedBy());
		return user.getName();
	}	//	getCreatedByName

	/**
	 * 	Get Changes as HTML string
	 *	@return changes
	 */
	public String getChangesHTML()
	{
		StringBuffer sb = new StringBuffer();
		getChangeHTML(sb, "Priority");
		getChangeHTML(sb, "PriorityUser");
		getChangeHTML(sb, "R_Category_ID");
		getChangeHTML(sb, "R_Group_ID");
		getChangeHTML(sb, "R_RequestType_ID");
		getChangeHTML(sb, "R_Resolution_ID");
		getChangeHTML(sb, "R_Status_ID");
		getChangeHTML(sb, "SalesRep_ID");
		getChangeHTML(sb, "Summary");
		//
	//	getChangeHTML(sb, "AD_Org_ID");		//	always stored
		getChangeHTML(sb, "AD_Role_ID");
		getChangeHTML(sb, "AD_User_ID");
		getChangeHTML(sb, "C_Activity_ID");
		getChangeHTML(sb, "C_BPartner_ID");
		getChangeHTML(sb, "C_Invoice_ID");
		getChangeHTML(sb, "C_Order_ID");
		getChangeHTML(sb, "C_Payment_ID");
		getChangeHTML(sb, "C_Project_ID");
		getChangeHTML(sb, "DateNextAction");
		getChangeHTML(sb, "IsEscalated");
		getChangeHTML(sb, "IsInvoiced");
		getChangeHTML(sb, "IsSelfService");
		getChangeHTML(sb, "M_InOut_ID");
		getChangeHTML(sb, "M_Product_ID");
		getChangeHTML(sb, "M_RMA_ID");
		getChangeHTML(sb, "A_Asset_ID");
		
		if (sb.length() == 0)
			sb.append("./.");
		//	Unicode check
		char[] chars = sb.toString().toCharArray();
		sb = new StringBuffer(chars.length);
		for (int i = 0; i < chars.length; i++)
		{
			char c = chars[i];
			int ii = (int)c;
			if (ii > 255)
				sb.append("&#").append(ii).append(";");
			else
				sb.append(c);
		}
		return sb.toString();
	}	//	getChangesHTML
	
	/**
	 * 	Get individual Change HTML
	 *	@param sb string to append to
	 *	@param columnName column name
	 */
	private void getChangeHTML (StringBuffer sb, String columnName)
	{
		if (get_Value(columnName) != null)
		{
			if (sb.length() > 0)
				sb.append("<br>");
			sb.append(Msg.getElement(getCtx(), columnName))
				.append(": ").append(get_DisplayValue(columnName, true));
		}
		else
		{
			String nc = getNullColumns();
			if (nc != null && nc.indexOf(columnName) != -1)
			{
				if (sb.length() > 0)
					sb.append("<br>");
				sb.append("(")
					.append(Msg.getElement(getCtx(), columnName))
					.append(")");
			}
		}
	}	//	getChangeHTML
	
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		return true;
	}	//	beforeSave
}	//	MRequestAction
