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
 * 	Chat Entry Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MChatEntry.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public class MChatEntry extends X_CM_ChatEntry
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -158924400098841023L;

	/**
	 * 	Standard Constructor
	 *	@param ctx cintext
	 *	@param CM_ChatEntry_ID id
	 *	@param trxName transaction
	 */
	public MChatEntry (Properties ctx, int CM_ChatEntry_ID, String trxName)
	{
		super (ctx, CM_ChatEntry_ID, trxName);
		if (CM_ChatEntry_ID == 0)
		{
			setChatEntryType (CHATENTRYTYPE_NoteFlat);	// N
			setConfidentialType (CONFIDENTIALTYPE_PublicInformation);
		}
	}	//	MChatEntry

	/**
	 * 	Parent Constructor
	 *	@param chat parent
	 *	@param data text
	 */
	public MChatEntry (MChat chat, String data)
	{
		this (chat.getCtx(), 0, chat.get_TrxName());
		setCM_Chat_ID(chat.getCM_Chat_ID());
		setConfidentialType(chat.getConfidentialType());
		setCharacterData(data);
		setChatEntryType (CHATENTRYTYPE_NoteFlat);	// N
	}	//	MChatEntry

	/**
	 * 	Thread Constructor
	 *	@param entry peer
	 *	@param data text
	 */
	public MChatEntry (MChatEntry peer, String data)
	{
		this (peer.getCtx(), 0, peer.get_TrxName());
		setCM_Chat_ID(peer.getCM_Chat_ID());
		setCM_ChatEntryParent_ID (peer.getCM_ChatEntryParent_ID());
		//	Set GrandParent
		int id = peer.getCM_ChatEntryGrandParent_ID();
		if (id == 0)
			id = peer.getCM_ChatEntryParent_ID();
		setCM_ChatEntryGrandParent_ID (id);
		setConfidentialType(peer.getConfidentialType());
		setCharacterData(data);
		setChatEntryType (CHATENTRYTYPE_ForumThreaded);	
	}	//	MChatEntry

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MChatEntry (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MChatEntry

	/**
	 * 	Can be published
	 *	@param ConfidentialType minimum confidential type
	 *	@return true if withing confidentiality
	 */
	public boolean isConfidentialType(String ConfidentialType)
	{
		String ct = getConfidentialType();
		if (ConfidentialType == null 
			|| CONFIDENTIALTYPE_PublicInformation.equals(ct))
			return true;
		if (CONFIDENTIALTYPE_PartnerConfidential.equals(ct))
		{
			return CONFIDENTIALTYPE_PartnerConfidential.equals(ConfidentialType);
		}
		else if (CONFIDENTIALTYPE_PrivateInformation.equals(ct))
		{
			return CONFIDENTIALTYPE_Internal.equals(ConfidentialType)
				|| CONFIDENTIALTYPE_PrivateInformation.equals(ConfidentialType);
		}
		else if (CONFIDENTIALTYPE_Internal.equals(ct))
		{
			return CONFIDENTIALTYPE_Internal.equals(ConfidentialType);
		}
		return false;
	}	//	
	
}	//	MChatEntry
