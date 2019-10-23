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


/**
 *	Alert Recipient
 *	
 *  @author Jorg Janke
 *  @version $Id: MAlertRecipient.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MAlertRecipient extends X_AD_AlertRecipient
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7388195934030609324L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_AlertRecipient_ID id
	 *	@param trxName transaction
	 */
	public MAlertRecipient (Properties ctx, int AD_AlertRecipient_ID, String trxName)
	{
		super (ctx, AD_AlertRecipient_ID, trxName);
	}	//	MAlertRecipient

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MAlertRecipient (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MAlertRecipient

	
	
	/**
	 * 	Get User
	 *	@return	AD_User_ID or -1 if none
	 */
	public int getAD_User_ID ()
	{
		Integer ii = (Integer)get_Value("AD_User_ID");
		if (ii == null) 
			return -1;
		return ii.intValue();
	}	//	getAD_User_ID
	
	
	/**
	 * 	Get Role
	 *	@return AD_Role_ID or -1 if none
	 */
	public int getAD_Role_ID ()
	{
		Integer ii = (Integer)get_Value("AD_Role_ID");
		if (ii == null) 
			return -1;
		return ii.intValue();
	}	//	getAD_Role_ID
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MAlertRecipient[");
		sb.append(get_ID())
			.append(",AD_User_ID=").append(getAD_User_ID())
			.append(",AD_Role_ID=").append(getAD_Role_ID())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
}	//	MAlertRecipient
