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
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.CCache;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

/**
 *	Warehouse Locator Object
 *
 *  @author 	Jorg Janke
 *  @author victor.perez@e-evolution.com
 *  @see [ 1966333 ] New Method to get the Default Locator based in Warehouse http://sourceforge.net/tracker/index.php?func=detail&aid=1966333&group_id=176962&atid=879335
 *  @version 	$Id: MLocator.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 */
public class MLocator extends X_M_Locator
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6019655556196171287L;


	/**
	 * 	Get oldest Default Locator of warehouse with locator
	 *	@param ctx context
	 *	@param M_Locator_ID locator
	 *	@return locator or null
	 */
	public static MLocator getDefault (Properties ctx, int M_Locator_ID)
	{
		String trxName = null;
		MLocator retValue = null;
		String sql = "SELECT * FROM M_Locator l "
			+ "WHERE IsActive = 'Y' AND  IsDefault='Y'"
			+ " AND EXISTS (SELECT * FROM M_Locator lx "
				+ "WHERE l.M_Warehouse_ID=lx.M_Warehouse_ID AND lx.M_Locator_ID=?) "
			+ "ORDER BY Created";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt (1, M_Locator_ID);
			rs = pstmt.executeQuery ();
			while (rs.next ())
				retValue = new MLocator (ctx, rs, trxName);
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
		
		return retValue;
	}	//	getDefault
	
	/**
	 *  FR  [ 1966333 ]
	 * 	Get oldest Default Locator of warehouse with locator
	 *	@param ctx context
	 *	@param M_Locator_ID locator
	 *	@return locator or null
	 */
	public static MLocator getDefault (MWarehouse warehouse)
	{
		String trxName = null;
		MLocator retValue = null;
		String sql = "SELECT * FROM M_Locator l "
			+ "WHERE IsActive = 'Y' AND IsDefault='Y' AND l.M_Warehouse_ID=? "
			+ "ORDER BY PriorityNo";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt (1, warehouse.getM_Warehouse_ID());
			rs = pstmt.executeQuery ();
			while (rs.next ())
				retValue = new MLocator (warehouse.getCtx(), rs, trxName);
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
		
		return retValue;
	}	//	getDefault
	
	
	/**
	 * 	Get the Locator with the combination or create new one
	 *	@param ctx Context
	 *	@param M_Warehouse_ID warehouse
	 *	@param Value value
	 *	@param X x
	 *	@param Y y
	 *	@param Z z
	 * 	@return locator
	 */
	 public static MLocator get (Properties ctx, int M_Warehouse_ID, String Value,
		 String X, String Y, String Z)
	 {
		MLocator retValue = null;
		String sql = "SELECT * FROM M_Locator WHERE IsActive = 'Y' AND M_Warehouse_ID=? AND X=? AND Y=? AND Z=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, M_Warehouse_ID);
			pstmt.setString(2, X);
			pstmt.setString(3, Y);
			pstmt.setString(4, Z);
			rs = pstmt.executeQuery();
			if (rs.next())
				retValue = new MLocator (ctx, rs, null);
		}
		catch (SQLException ex)
		{
			s_log.log(Level.SEVERE, "get", ex);
		}
		finally {
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//
		if (retValue == null)
		{
			MWarehouse wh = MWarehouse.get (ctx, M_Warehouse_ID);
			retValue = new MLocator (wh, Value);
			retValue.setXYZ(X, Y, Z);
			retValue.save();
		}
		return retValue;
	 }	//	get

	/**
	 * 	Get Locator from Cache
	 *	@param ctx context
	 *	@param M_Locator_ID id
	 *	@return MLocator
	 */
	public static MLocator get (Properties ctx, int M_Locator_ID)
	{
		if (s_cache == null)
			s_cache	= new CCache<Integer,MLocator>("M_Locator", 20);
		Integer key = new Integer (M_Locator_ID);
		MLocator retValue = (MLocator) s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MLocator (ctx, M_Locator_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	} //	get

	/**	Cache						*/
	private static CCache<Integer,MLocator> s_cache; 
	 
	/**	Logger						*/
	private static CLogger		s_log = CLogger.getCLogger (MLocator.class);
	
	
	/**************************************************************************
	 * 	Standard Locator Constructor
	 *	@param ctx Context
	 *	@param M_Locator_ID id
	 *	@param trxName transaction
	 */
	public MLocator (Properties ctx, int M_Locator_ID, String trxName)
	{
		super (ctx, M_Locator_ID, trxName);
		if (M_Locator_ID == 0)
		{
		//	setM_Locator_ID (0);		//	PK
		//	setM_Warehouse_ID (0);		//	Parent
			setIsDefault (false);
			setPriorityNo (50);
		//	setValue (null);
		//	setX (null);
		//	setY (null);
		//	setZ (null);
		}
	}	//	MLocator

	/**
	 * 	New Locator Constructor with XYZ=000
	 *	@param warehouse parent
	 *	@param Value value
	 */
	public MLocator (MWarehouse warehouse, String Value)
	{
		this (warehouse.getCtx(), 0, warehouse.get_TrxName());
		setClientOrg(warehouse);
		setM_Warehouse_ID (warehouse.getM_Warehouse_ID());		//	Parent
		setValue (Value);
		setXYZ("0","0","0");
	}	//	MLocator

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MLocator (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MLocator

	/**
	 *	Get String Representation
	 * 	@return Value
	 */
	public String toString()
	{
		return getValue();
	}	//	getValue

	/**
	 * 	Set Location
	 *	@param X x
	 *	@param Y y
	 *	@param Z z
	 */
	public void setXYZ (String X, String Y, String Z)
	{
		setX (X);
		setY (Y);
		setZ (Z);
	}	//	setXYZ
	
	
	/**
	 * 	Get Warehouse Name
	 * 	@return name
	 */
	public String getWarehouseName()
	{
		MWarehouse wh = MWarehouse.get(getCtx(), getM_Warehouse_ID());
		if (wh.get_ID() == 0)
			return "<" + getM_Warehouse_ID() + ">";
		return wh.getName();
	}	//	getWarehouseName

	/**
	 * 	Can Locator Store Product
	 *	@param M_Product_ID id
	 *	@return true if can be stored
	 */
	public boolean isCanStoreProduct (int M_Product_ID)
	{
		// BF [ 1759245 ] Locator field cleared in Physical Inventory
		// CarlosRuiz - globalqss comments:
		// The algorithm to search if a product can be stored is wrong, it looks for:
		// * M_Storage to see if the product is already in the locator
		// * If the product has this locator defined as default
		// This implies that every time you create a new product you must create initial inventory zero for all locators where the product can be stored.
		// A good enhancement could be a new table to indicate when a locator is exclusive for some products, but I consider current approach not working.
		return true;

		/*
		//	Default Locator
		if (M_Product_ID == 0 || isDefault())
			return true;
		
		int count = 0;
		PreparedStatement pstmt = null;
		//	Already Stored
		String sql = "SELECT COUNT(*) FROM M_Storage s WHERE s.M_Locator_ID=? AND s.M_Product_ID=?";
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, getM_Locator_ID());
			pstmt.setInt (2, M_Product_ID);
			ResultSet rs = pstmt.executeQuery ();
			if (rs.next ())
				count = rs.getInt(1);
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log (Level.SEVERE, sql, e);
		}
		//	Default Product Locator
		if (count == 0)
		{
			sql = "SELECT COUNT(*) FROM M_Product s WHERE s.M_Locator_ID=? AND s.M_Product_ID=?";
			try
			{
				pstmt = DB.prepareStatement (sql, null);
				pstmt.setInt (1, getM_Locator_ID());
				pstmt.setInt (2, M_Product_ID);
				ResultSet rs = pstmt.executeQuery ();
				if (rs.next ())
					count = rs.getInt(1);
				rs.close ();
				pstmt.close ();
				pstmt = null;
			}
			catch (Exception e)
			{
				log.log (Level.SEVERE, sql, e);
			}
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
		
		return count != 0;
		*/
	}	//	isCanStoreProduct
	
}	//	MLocator
