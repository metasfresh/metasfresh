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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;

import org.compiere.util.DB;
import org.compiere.util.WebUtil;

/**
 *	Asset Registration Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MRegistration.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MRegistration extends X_A_Registration
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5178339895228217372L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param A_Registration_ID id
	 */
	public MRegistration(Properties ctx, int A_Registration_ID, String trxName)
	{
		super(ctx, A_Registration_ID, trxName);
		if (A_Registration_ID == 0)
			setIsRegistered (true);
	}	//	MRegistration

	/**
	 * 	New Constructor
	 *	@param ctx context
	 *	@param Name name
	 *	@param IsAllowPublish allow publication
	 *	@param IsInProduction production
	 *	@param AssetServiceDate start date
	 *	@param trxName trx
	 */
	public MRegistration (Properties ctx, String Name, boolean IsAllowPublish,
		boolean IsInProduction, Timestamp AssetServiceDate, String trxName)
	{
		this (ctx, 0, trxName);
		setName (Name);
		setIsAllowPublish (IsAllowPublish);
		setIsInProduction (IsInProduction);
		setAssetServiceDate (AssetServiceDate);
	}	//	MRegistration
	
	
	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 */
	public MRegistration(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRegistration

	/**	All Attributes						*/
	private MRegistrationAttribute[] m_allAttributes = null;

	/**
	 * 	Get All Attributes
	 *	@return Registration Attributes
	 */
	public MRegistrationAttribute[] getAttributes()
	{
		if (m_allAttributes == null)
			m_allAttributes = MRegistrationAttribute.getAll(getCtx());
		return m_allAttributes;
	}	//	getAttributes

	/**
	 * 	Get All active Self Service Attribute Values
	 *	@return Registration Attribute Values
	 */
	public MRegistrationValue[] getValues()
	{
		return getValues (true);
	}	//	getValues
	
	/**
	 * 	Get All Attribute Values
	 * 	@param onlySelfService only Active Self Service
	 *	@return sorted Registration Attribute Values
	 */
	public MRegistrationValue[] getValues (boolean onlySelfService)
	{
		createMissingValues();
		//
		String sql = "SELECT * FROM A_RegistrationValue rv "
			+ "WHERE A_Registration_ID=?";
		if (onlySelfService)
			sql += " AND EXISTS (SELECT * FROM A_RegistrationAttribute ra WHERE rv.A_RegistrationAttribute_ID=ra.A_RegistrationAttribute_ID"
				+ " AND ra.IsActive='Y' AND ra.IsSelfService='Y')";
	//	sql += " ORDER BY A_RegistrationAttribute_ID";
				
		ArrayList<MRegistrationValue> list = new ArrayList<MRegistrationValue>();
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getA_Registration_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new MRegistrationValue(getCtx(), rs, get_TrxName()));
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		//	Convert and Sort
		MRegistrationValue[] retValue = new MRegistrationValue[list.size()];
		list.toArray(retValue);
		Arrays.sort(retValue);
		return retValue;
	}	//	getValues
	
	/**
	 * 	Create Missing Attribute Values
	 */
	private void createMissingValues()
	{
		String sql = "SELECT ra.A_RegistrationAttribute_ID "
			+ "FROM A_RegistrationAttribute ra"
			+ " LEFT OUTER JOIN A_RegistrationProduct rp ON (rp.A_RegistrationAttribute_ID=ra.A_RegistrationAttribute_ID)"
			+ " LEFT OUTER JOIN A_Registration r ON (r.M_Product_ID=rp.M_Product_ID) "
			+ "WHERE r.A_Registration_ID=?"
			//	Not in Registration
			+ " AND NOT EXISTS (SELECT A_RegistrationAttribute_ID FROM A_RegistrationValue v "
				+ "WHERE ra.A_RegistrationAttribute_ID=v.A_RegistrationAttribute_ID AND r.A_Registration_ID=v.A_Registration_ID)";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, getA_Registration_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				MRegistrationValue v = new MRegistrationValue (this, rs.getInt(1), "?");
				v.save();
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, null, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
	}	//	createMissingValues

	/**
	 * 	Load Attributes from Request
	 *	@param request request
	 *	@return number of attributes read
	 */
	public int loadAttributeValues (HttpServletRequest request)
	{
		//	save if not saved
		if (get_ID() == 0)
			save();
		int count = 0;
		//	read values for all attributes
		MRegistrationAttribute[] attributes = getAttributes();
		for (int i = 0; i < attributes.length; i++)
		{
			MRegistrationAttribute attribute = attributes[i];
			String value = WebUtil.getParameter (request, attribute.getName());
			if (value == null)
				continue;
			MRegistrationValue regValue = new MRegistrationValue (this, 
				attribute.getA_RegistrationAttribute_ID(), value);
			if (regValue.save())
				count++;
		}
		log.fine("loadAttributeValues - #" + count + " (of " + attributes.length + ")");
		return count;
	}	//	loadAttrubuteValues

	/**
	 * 	Update Attributes from Request
	 *	@param request request
	 *	@return number of attributes read
	 */
	public int updateAttributeValues (HttpServletRequest request)
	{
		//	save if not saved
		if (get_ID() == 0)
			save();
		int count = 0;

		//	Get All Values
		MRegistrationValue[] regValues = getValues(false);
		for (int i = 0; i < regValues.length; i++)
		{
			MRegistrationValue regValue = regValues[i];
			String attributeName = regValue.getRegistrationAttribute();
			//	
			String dataValue = WebUtil.getParameter (request, attributeName);
			if (dataValue == null)
				continue;
			regValue.setDescription("Previous=" + regValue.getName());
			regValue.setName(dataValue);
			if (regValue.save())
				count++;
		}
		log.fine("updateAttributeValues - #" + count + " (of " + regValues.length + ")");
		return count;
	}	//	updateAttrubuteValues

}	//	MRegistration
