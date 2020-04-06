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
 * News ItemModel
 * 
 * @author Yves Sandfort
 * @version $Id$
 */
public class MNewsItem extends X_CM_NewsItem
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8217571535161436997L;

	/***
     * Standard Constructor
     * 
     * @param ctx context
     * @param CM_NewsItem_ID id
     * @param trxName transaction
     */
	public MNewsItem (Properties ctx, int CM_NewsItem_ID, String trxName)
	{
		super (ctx, CM_NewsItem_ID, trxName);
	}	// MNewsItem

	/**
     * Load Constructor
     * @param ctx context
     * @param rs result set
     * @param trxName transaction
     */
	public MNewsItem (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	} // MNewsItem
	
	/**
	 * 	getNewsChannel
	 *	@return NewsChannel
	 */
	public MNewsChannel getNewsChannel() 
	{
		int[] thisNewsChannel = MNewsChannel.getAllIDs("CM_NewsChannel","CM_NewsChannel_ID=" + this.getCM_NewsChannel_ID(), get_TrxName());
		if (thisNewsChannel!=null) 
		{
			if (thisNewsChannel.length==1)
				return new MNewsChannel(getCtx(), thisNewsChannel[0], get_TrxName());
		}
		return null;
	} // getNewsChannel

	/**
	 * 	Get rss2 Item Code
	 *	@param xmlCode xml
	 *	@param thisChannel channel
	 *	@return rss item code
	 */
	public StringBuffer get_rss2ItemCode(StringBuffer xmlCode, MNewsChannel thisChannel) 
	{
		if (this != null)	//	never null ??
		{
			xmlCode.append ("<item>");
			xmlCode.append ("<CM_NewsItem_ID>"+ this.get_ID() + "</CM_NewsItem_ID>");
			xmlCode.append ("  <title><![CDATA["
				+ this.getTitle () + "]]></title>");
			xmlCode.append ("  <description><![CDATA["
				+ this.getDescription ()
				+ "]]></description>");
			xmlCode.append ("  <content><![CDATA["
				+ this.getContentHTML ()
				+ "]]></content>");
			xmlCode.append ("  <link>"
				+ thisChannel.getLink ()
				+ "?CM_NewsItem_ID=" + this.get_ID() + "</link>");
			xmlCode.append ("  <author><![CDATA["
				+ this.getAuthor () + "]]></author>");
			xmlCode.append ("  <pubDate>"
				+ this.getPubDate () + "</pubDate>");
			xmlCode.append ("</item>");
		}
		return xmlCode;
	}
	
	/**
	 * 	After Save.
	 * 	Insert
	 * 	- create / update index
	 *	@param newRecord insert
	 *	@param success save success
	 *	@return true if saved
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		if (!newRecord)
		{
			MIndex.cleanUp(get_TrxName(), getAD_Client_ID(), get_Table_ID(), get_ID());
		}
		reIndex(newRecord);
		return success;
	}	//	afterSave
	
	/**
	 * 	reIndex
	 *	@param newRecord
	 */
	public void reIndex(boolean newRecord)
	{
		int CMWebProjectID = 0;
		if (getNewsChannel()!=null)
			CMWebProjectID = getNewsChannel().getCM_WebProject_ID();
		String [] toBeIndexed = new String[4];
		toBeIndexed[0] = this.getAuthor();
		toBeIndexed[1] = this.getDescription();
		toBeIndexed[2] = this.getTitle();
		toBeIndexed[3] = this.getContentHTML();
		MIndex.reIndex (newRecord, toBeIndexed, getCtx(), getAD_Client_ID(), get_Table_ID(), get_ID(), CMWebProjectID, this.getUpdated());
	} // reIndex
}
