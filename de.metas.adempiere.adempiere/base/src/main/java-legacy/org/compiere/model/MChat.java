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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.apache.ecs.xhtml.b;
import org.apache.ecs.xhtml.hr;
import org.apache.ecs.xhtml.p;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Util;

/**
 * 	Chat Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MChat.java,v 1.4 2006/07/30 00:51:05 jjanke Exp $
 */
public class MChat extends X_CM_Chat
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5053130533036069784L;


	/**
	 * 	Get Chats Of Table - of client in context
	 *	@param ctx context
	 *	@param AD_Table_ID table
	 *	@return array of chats
	 */
	public static MChat[] getOfTable (Properties ctx, int AD_Table_ID)
	{
		int AD_Client_ID = Env.getAD_Client_ID(ctx);
		ArrayList<MChat> list = new ArrayList<MChat>();
		//
		String sql = "SELECT * FROM CM_Chat "
			+ "WHERE AD_Client_ID=? AND AD_Table_ID=? ORDER BY Record_ID";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, AD_Client_ID);
			pstmt.setInt (2, AD_Table_ID);
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				list.add (new MChat (ctx, rs, null));
			}
		}
		catch (Exception e)
		{
			s_log.log (Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		//
		MChat[] retValue = new MChat[list.size()];
		list.toArray (retValue);
		return retValue;
	}	//	get
	
	/**	Logger	*/
	private static CLogger s_log = CLogger.getCLogger (MChat.class);
	
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param CM_Chat_ID id
	 *	@param trxName transcation
	 */
	public MChat (Properties ctx, int CM_Chat_ID, String trxName)
	{
		super (ctx, CM_Chat_ID, trxName);
		if (CM_Chat_ID == 0)
		{
		//	setAD_Table_ID (0);
		//	setRecord_ID (0);
			setConfidentialType (CONFIDENTIALTYPE_PublicInformation);
			setModerationType (MODERATIONTYPE_NotModerated);
		//	setDescription (null);
		}
	}	//	MChat

	/**
	 * 	Full Constructor
	 *	@param ctx context
	 *	@param AD_Table_ID table
	 *	@param Record_ID record
	 *	@param Description description
	 *	@param trxName transaction
	 */
	public MChat (Properties ctx, int AD_Table_ID, int Record_ID, 
		String Description, String trxName)
	{
		this (ctx, 0, trxName);
		setAD_Table_ID (AD_Table_ID);
		setRecord_ID (Record_ID);
		setDescription (Description);
	}	//	MChat

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MChat (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MChat
	
	/**	The Lines						*/
	private MChatEntry[] 		m_entries = null;
	/**	Date Format						*/
	private SimpleDateFormat	m_format = null;
	
	
	/**
	 *	Get Entries
	 *	@param reload reload data
	 *	@return array of lines
	 */
	public MChatEntry[] getEntries (boolean reload)
	{
		if (m_entries != null && !reload)
			return m_entries;
		ArrayList<MChatEntry> list = new ArrayList<MChatEntry>();
		String sql = "SELECT * FROM CM_ChatEntry WHERE CM_Chat_ID=? ORDER BY Created";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, getCM_Chat_ID());
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				list.add (new MChatEntry (getCtx(), rs, get_TrxName()));
			}
 		}
		catch (Exception e)
		{
			log.log (Level.SEVERE, sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		//
		m_entries = new MChatEntry[list.size ()];
		list.toArray (m_entries);
		return m_entries;
	}	// getEntries

	
	/**
     * Set Description
     * 
     * @param Description
     */
	public void setDescription (String Description)
	{
		if (Description != null && Description.length() > 0)
			super.setDescription (Description);
		else
			super.setDescription (getAD_Table_ID() + "#" + getRecord_ID());
	}	//	setDescription
	
	/**
	 * 	Get History as htlp paragraph
	 * 	@param ConfidentialType confidentiality
	 *	@return html paragraph
	 */
	public p getHistory (String ConfidentialType)
	{
		p history = new p();
		getEntries(false);
		boolean first = true;
		for (int i = 0; i < m_entries.length; i++)
		{
			MChatEntry entry = m_entries[i];
			if (!entry.isActive() || !entry.isConfidentialType(ConfidentialType))
				continue;
			if (first)
				first = false;
			else
				history.addElement(new hr());
			//	User & Date
			b b = new b();
			MUser user = MUser.get(getCtx(), entry.getCreatedBy());
			b.addElement(user.getName());
			b.addElement(" \t");
			Timestamp created = entry.getCreated();
			if (m_format == null)
				m_format = DisplayType.getDateFormat(DisplayType.DateTime);
			b.addElement(m_format.format(created));
			history.addElement(b);
		//	history.addElement(new br());
			//
			p p = new p();
			String data = entry.getCharacterData();
			data = Util.maskHTML(data, true);
			p.addElement(data);
			history.addElement(p);
		}	//	entry
		//
		return history;
	}	//	getHistory
	
	
}	//	MChat
