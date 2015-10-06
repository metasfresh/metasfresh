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

import org.compiere.util.CCache;
import org.compiere.util.CLogger;

/**
 *	Location City Model (Value Object)
 *
 *  @author 	Mario Calderon / Carlos Ruiz
 */
public final class MCity extends X_C_City
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8905525315954621942L;

	/**
	 * 	Get City (cached)
	 * 	@param ctx context
	 *	@param C_City_ID ID
	 *	@return City
	 */
	public static MCity get (Properties ctx, int C_City_ID)
	{
		Integer key = new Integer (C_City_ID);
		MCity r = s_Cities.get(key);
		if (r != null)
			return r;
		r = new MCity (ctx, C_City_ID, null);
		if (r.getC_City_ID() == C_City_ID)
		{
			s_Cities.put(key, r);
			return r;
		}
		return null;
	}	//	get

	/**	City Cache				*/
	private static CCache<Integer,MCity> s_Cities =  new CCache<Integer,MCity>("C_City", 20);;
//	/**	Static Logger				*/
//	private static CLogger		s_log = CLogger.getCLogger (MCity.class);

	/**	Region Cache				*/
	
	/**************************************************************************
	 *	Create empty City
	 * 	@param ctx context
	 * 	@param C_City_ID id
	 *	@param trxName transaction
	 */
	public MCity (Properties ctx, int C_City_ID, String trxName)
	{
		super (ctx, C_City_ID, trxName);
		if (C_City_ID == 0)
		{
		}
	}   //  MCity
	
	
	/**
	 *	Create City from current row in ResultSet
	 * 	@param ctx context
	 *  @param rs result set
	 *	@param trxName transaction
	 */
	public MCity (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MCity

	/**
	 * 	Parent Constructor
	 *	@param Region Region
	 *	@param CityName City Name
	 */
	public MCity (MRegion region, String cityName)
	{
		super (region.getCtx(), 0, region.get_TrxName());
		setC_Region_ID(region.getC_Region_ID());
		setName(cityName);
	}   //  MCity
	
	/**
	 *	Return Name
	 *  @return Name
	 */
	@Override
	public String toString()
	{
		return getName();
	}   //  toString

	/**
	 *  Compare
	 *  @param o1 object 1
	 *  @param o2 object 2
	 *  @return -1,0, 1
	 */
	@Override
	public int compare(Object o1, Object o2)
	{
		String s1 = o1.toString();
		if (s1 == null)
			s1 = "";
		String s2 = o2.toString();
		if (s2 == null)
			s2 = "";
		return s1.compareTo(s2);
	}	//	compare

}	//	MCity
