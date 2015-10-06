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

import org.compiere.util.Env;

/**
 *	Attachment Note
 *	
 *  @author Jorg Janke
 *  @version $Id: MAttachmentNote.java,v 1.3 2006/07/30 00:58:18 jjanke Exp $
 */
public class MAttachmentNote extends X_AD_AttachmentNote
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5902789937430504039L;

	/** 
	 * 	Standard Constructor
	 * 	@param ctx context
	 * 	@param AD_AttachmentNote_ID id
	 *	@param trxName transaction
	 */
	public MAttachmentNote (Properties ctx, int AD_AttachmentNote_ID, String trxName)
	{
		super (ctx, AD_AttachmentNote_ID, trxName);
		/**
		if (AD_AttachmentNote_ID == 0)
		{
			setAD_Attachment_ID (0);
			setAD_User_ID (0);
			setTextMsg (null);
			setTitle (null);
		}
		/**/
	}	//	MAttachmentNote
	
	/** 
	 * 	Load Constructor 
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MAttachmentNote (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MAttachmentNote

	/**
	 * 	Parent Constructor.
	 * 	Sets current user.
	 *	@param attach attachment
	 *	@param Title title
	 *	@param TextMsg ext message
	 */
	public MAttachmentNote (MAttachment attach, String Title, String TextMsg)
	{
		this (attach.getCtx(), 0, attach.get_TrxName());
		setClientOrg(attach);
		setAD_Attachment_ID (attach.getAD_Attachment_ID());
		setAD_User_ID(Env.getAD_User_ID(attach.getCtx()));
		setTitle (Title);
		setTextMsg (TextMsg);
	}	//	MAttachmentNote
	
}	//	MAttachmentNote
