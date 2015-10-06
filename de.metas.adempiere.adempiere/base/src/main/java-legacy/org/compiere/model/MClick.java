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
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.Adempiere;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * 	Actual Click
 *
 *  @author Jorg Janke
 *  @version $Id: MClick.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MClick extends X_W_Click
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 850591754099789308L;


	/**
	 * 	Get Unprocessed Clicks
	 *	@param ctx context
	 *	@return array of unprocessed clicks
	 */
	public static MClick[] getUnprocessed(Properties ctx)
	{
		ArrayList<MClick> list = new ArrayList<MClick> ();
		String sql = "SELECT * FROM W_Click WHERE AD_Client_ID=? AND Processed = 'N'";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, Env.getAD_Client_ID(ctx));
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				list.add (new MClick (ctx, rs, null));
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.log (Level.SEVERE, sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		//
		MClick[] retValue = new MClick[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getUnprocessed
	
	/**	Logger	*/
	private static CLogger	s_log	= CLogger.getCLogger (MClick.class);
	
	
	/**************************************************************************
	 * 	Actual Click
	 *	@param ctx context
	 *	@param W_Click_ID ID
	 *	@param trxName transaction
	 */
	public MClick (Properties ctx, int W_Click_ID, String trxName)
	{
		super (ctx, W_Click_ID, trxName);
		if (W_Click_ID == 0)
			setProcessed (false);
	}	//	MClick

	/**
	 * 	Actual Click
	 *	@param ctx context
	 *	@param TargetURL url
	 *	@param trxName transaction
	 */
	public MClick (Properties ctx, String TargetURL, String trxName)
	{
		this (ctx, 0, trxName);
		setTargetURL(TargetURL);
	}	//	MClick

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MClick (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MClick
	
	/**
	 * 	Set Target URL. Reset Click Count
	 *	@param TargetURL
	 */
	public void setTargetURL(String TargetURL)
	{
		super.setTargetURL(TargetURL);
		super.setW_ClickCount_ID(0);
	}	//	setTargetURL

	/**
	 * 	Find Click Count.
	 */
	public void setW_ClickCount_ID ()
	{
		//	clean up url
		String url = getTargetURL();
		if (url == null || url.length() == 0)
			return;
		String exactURL = url;
		//	remove everything before first / .
		if (url.startsWith("http://"))
			url = url.substring(7);
		int dot = url.indexOf('.');
		int slash = url.indexOf('/');
		while (dot > slash && slash != -1)
		{
			url = url.substring(slash+1);
			dot = url.indexOf('.');
			slash = url.indexOf('/');
		}
		//	remove everything after /
		if (slash != -1)
			url = url.substring(0, slash);
		log.fine(exactURL + " -> " + url);
		int W_ClickCount_ID = search (url, exactURL);
		//	try minumum
		if (W_ClickCount_ID == 0)
		{
			int lastDot = url.lastIndexOf('.');
			int firstDot = url.indexOf('.');
			while (lastDot != firstDot)
			{
				url = url.substring(firstDot+1);
				lastDot = url.lastIndexOf('.');
				firstDot = url.indexOf('.');
			}
			log.fine(exactURL + " -> " + url);
			W_ClickCount_ID = search (url, exactURL);
		}
		//	Not found
		if (W_ClickCount_ID == 0)
		{
			log.warning ("Not found: " + url 
				+ " (" + exactURL + ") Referrer=" + getReferrer());
			return;
		}
		//	OK
		setProcessed(true);
		super.setW_ClickCount_ID (W_ClickCount_ID);
	}	//	setW_ClickCount_ID

	/**
	 * 	Search for Click Count
	 *	@param url url
	 *	@param exactURL original url
	 *	@return W_ClickCount_ID
	 */
	private int search (String url, String exactURL)
	{
		String sql = "SELECT W_ClickCount_ID, TargetURL FROM W_ClickCount WHERE TargetURL LIKE ?";
		int W_ClickCount_ID = 0;
		int exactW_ClickCount_ID = 0;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, "%" + url + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				W_ClickCount_ID = rs.getInt(1);
				if (exactURL.equals(rs.getString(2)))
				{
					exactW_ClickCount_ID = W_ClickCount_ID;
					break;
				}
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (SQLException ex)
		{
			log.log(Level.SEVERE, sql, ex);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
		}
		catch (SQLException ex1)
		{
		}
		pstmt = null;
		//	Set Click Count
		if (exactW_ClickCount_ID != 0)
			W_ClickCount_ID = exactW_ClickCount_ID;
		return W_ClickCount_ID;
	}	//	search
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		if (getW_ClickCount_ID() == 0)
			setW_ClickCount_ID();
		return true;
	}	//	beforeSave
	
	/**************************************************************************
	 * 	Test
	 *	@param args ignored
	 */
	public static void main (String[] args)
	{
		Adempiere.startup(true);
		Env.setContext(Env.getCtx(), "#AD_Client_ID", 1000000);
		MClick[] clicks = getUnprocessed(Env.getCtx());
		int counter = 0;
		for (int i = 0; i < clicks.length; i++)
		{
			MClick click = clicks[i];
			if (click.getW_ClickCount_ID() == 0)
			{
				click.setW_ClickCount_ID();
				if (click.getW_ClickCount_ID() != 0)
				{
					click.save();
					counter++;
				}
			}
		}
		System.out.println("#" + counter);
	}	//	main
	
}	//	MClick
