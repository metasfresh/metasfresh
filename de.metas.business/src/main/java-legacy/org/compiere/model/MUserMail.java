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

import org.adempiere.ad.trx.api.ITrx;

import de.metas.email.EMail;
import de.metas.email.EMailSentStatus;

/**
 * 	User Mail Model
 *  @author Jorg Janke
 *  @version $Id: MUserMail.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class MUserMail extends X_AD_UserMail
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1019980049099249013L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_UserMail_ID id
	 *	@param trxName trx
	 */
	public MUserMail (Properties ctx, int AD_UserMail_ID, String trxName)
	{
		super (ctx, AD_UserMail_ID, trxName);
	}	//	MUserMail

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MUserMail (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MUserMail
	
	/**
	 * 	User Mail
	 *	@param parent Request Mail Text
	 *	@param AD_User_ID recipient user
	 *	@param mail email
	 */
	public MUserMail (Properties ctx, int R_MailText_ID, int AD_User_ID, final EMail mail, final EMailSentStatus mailSentStatus)
	{
		this (ctx, 0, ITrx.TRXNAME_ThreadInherited);
		setAD_User_ID(AD_User_ID);
		setR_MailText_ID(R_MailText_ID);
		//
		if (mailSentStatus.isSentOK())
			setMessageID(mailSentStatus.getMessageId());
		else
		{
			setMessageID(mailSentStatus.getSentMsg());
			setIsDelivered(ISDELIVERED_No);
		}
	}	//	MUserMail

	/**
	 * 	New User Mail (no trx)
	 *	@param po persistent object
	 *	@param AD_User_ID recipient user
	 *	@param mail email
	 */
	public MUserMail (PO po, int AD_User_ID, EMail mail, EMailSentStatus mailSentStatus)
	{
		this (po.getCtx(), 0, ITrx.TRXNAME_None);
		setClientOrg(po);
		setAD_User_ID(AD_User_ID);
		setSubject(mail.getSubject());
		setMailText(mail.getMessageCRLF());
		//
		if (mailSentStatus.isSentOK())
			setMessageID(mailSentStatus.getMessageId());
		else
		{
			setMessageID(mailSentStatus.getSentMsg());
			setIsDelivered(ISDELIVERED_No);
		}
	}	//	MUserMail
}	//	MUserMail
