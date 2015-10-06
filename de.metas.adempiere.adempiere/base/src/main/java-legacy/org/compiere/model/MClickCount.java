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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.ValueNamePair;

/**
 * 	Click Count (header)
 *
 *  @author Jorg Janke
 *  @version $Id: MClickCount.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class MClickCount extends X_W_ClickCount
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5233509415147834823L;


	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param W_ClickCount_ID id
	 *	@param trxName transaction
	 */
	public MClickCount (Properties ctx, int W_ClickCount_ID, String trxName)
	{
		super (ctx, W_ClickCount_ID, trxName);
		if (W_ClickCount_ID == 0)
		{
		//	setName (null);
		//	setTargetURL (null);
		}
	}	//	MClickCount
	
	/** 
	 * 	Load Constructor
	 * 	@param ctx context
	 * 	@param rs result set 
	 *	@param trxName transaction
	 */
	public MClickCount (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MClickCount

	/** 
	 * 	Parent Constructor
	 * 	@param ad parent
	 */
	public MClickCount (MAdvertisement ad)
	{
		this (ad.getCtx(), 0, ad.get_TrxName());
		setName(ad.getName());
		setTargetURL("#");
		setC_BPartner_ID(ad.getC_BPartner_ID());
	}	//	MClickCount
	
	private SimpleDateFormat		m_dateFormat = DisplayType.getDateFormat(DisplayType.Date);
	private DecimalFormat			m_intFormat = DisplayType.getNumberFormat(DisplayType.Integer);

	
	/**************************************************************************
	 * 	Get Clicks
	 *	@return clicks
	 */
	public MClick[] getMClicks()
	{
		ArrayList<MClick> list = new ArrayList<MClick>();
		/** @todo Clicks */
		//
		MClick[] retValue = new MClick[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getMClicks

	/**
	 * 	Get Count for date format
	 *	@param DateFormat valid TRUNC date format
	 *	@return count
	 */
	protected ValueNamePair[] getCount (String DateFormat)
	{
		ArrayList<ValueNamePair> list = new ArrayList<ValueNamePair>();
		String sql = "SELECT TRUNC(Created, '" + DateFormat + "'), Count(*) "
			+ "FROM W_Click "
			+ "WHERE W_ClickCount_ID=? "
			+ "GROUP BY TRUNC(Created, '" + DateFormat + "')";
		//
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, getW_ClickCount_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				String value = m_dateFormat.format(rs.getTimestamp(1));
				String name = m_intFormat.format(rs.getInt(2));
				ValueNamePair pp = new ValueNamePair (value, name);
				list.add(pp);
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
		//
		ValueNamePair[] retValue = new ValueNamePair[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getCount

	/**
	 * 	Get Monthly Count
	 *	@return monthly count
	 */
	public ValueNamePair[] getCountQuarter ()
	{
		return getCount("Q");
	}	//	getCountQuarter

	/**
	 * 	Get Monthly Count
	 *	@return monthly count
	 */
	public ValueNamePair[] getCountMonth ()
	{
		return getCount("MM");
	}	//	getCountMonth

	/**
	 * 	Get Weekly Count
	 *	@return weekly count
	 */
	public ValueNamePair[] getCountWeek ()
	{
		return getCount("DY");
	}	//	getCountWeek

	/**
	 * 	Get Daily Count
	 *	@return dailt count
	 */
	public ValueNamePair[] getCountDay ()
	{
		return getCount("J");
	}	//	getCountDay

}	//	MClickCount
