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
 * 	Wab Store Mail Message Model
 *  @author Jorg Janke
 *  @version $Id: MMailMsg.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class MMailMsg extends X_W_MailMsg
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6181691633960939054L;

	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param W_MailMsg_ID id
	 *	@param trxName trx
	 */
	public MMailMsg (Properties ctx, int W_MailMsg_ID, String trxName)
	{
		super (ctx, W_MailMsg_ID, trxName);
		if (W_MailMsg_ID == 0)
		{
		//	setW_Store_ID (0);
		//	setMailMsgType (null);
		//	setName (null);
		//	setSubject (null);
		//	setMessage (null);
		}
	}	//	MMailMsg

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MMailMsg (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MMailMsg
	
	/**
	 * 	Full Constructor
	 *	@param parent store
	 *	@param MailMsgType msg type
	 *	@param Name name
	 *	@param Subject subject
	 *	@param Message message
	 *	@param Message2 message
	 *	@param Message3 message
	 */
	public MMailMsg (MStore parent, String MailMsgType, 
		String Name, String Subject, String Message, String Message2, String Message3)
	{
		this (parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setW_Store_ID(parent.getW_Store_ID());
		setMailMsgType (MailMsgType);
		setName (Name);
		setSubject (Subject);
		setMessage (Message);
		setMessage2 (Message2);
		setMessage3 (Message3);
	}	//	MMailMsg
	
}	//	MMailMsg
