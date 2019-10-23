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

import java.io.Serializable;
import java.util.List;
import java.util.Properties;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.validationRule.IValidationContext;
import org.compiere.util.DisplayType;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;

/**
 *	Address Loaction Lookup Model.
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: MLocationLookup.java,v 1.3 2006/07/30 00:58:18 jjanke Exp $
 */
public final class MLocationLookup extends Lookup
	implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7335118019935048922L;

	/**
	 *	Constructor
	 *  @param ctx context
	 *  @param WindowNo window no (to derive AD_Client/Org for new records)
	 */
	public MLocationLookup(Properties ctx, int WindowNo)
	{
		super (DisplayType.TableDir, WindowNo);
		m_ctx = ctx;
	}	//	MLocation

	/**	Context					*/
	private Properties 		m_ctx;

	/**
	 *	Get Display for Value (not cached)
	 *  @param value Location_ID
	 *  @return String Value
	 */
	@Override
	public String getDisplay (final IValidationContext evalCtx, final Object value)
	{
		if (value == null)
			return null;
		MLocation loc = getLocation (value, ITrx.TRXNAME_None);
		if (loc == null)
			return "<" + value.toString() + ">";
		return loc.toString();
	}	//	getDisplay

	/**
	 *	Get Object of Key Value
	 *  @param value value
	 *  @return Object or null
	 */
	@Override
	public NamePair get (final IValidationContext evalCtx, Object value)
	{
		if (value == null)
			return null;
		MLocation loc = getLocation (value, null);
		if (loc == null)
			return null;
		return new KeyNamePair (loc.getC_Location_ID(), loc.toString());
	}	//	get

	/**
	 *  The Lookup contains the key 
	 *  @param key Location_ID
	 *  @return true if key known
	 */
	@Override
	public boolean containsKey (final IValidationContext evalCtx, final Object key)
	{
		return getLocation(key, ITrx.TRXNAME_None) != null;
	}   //  containsKey

	
	/**************************************************************************
	 * 	Get Location
	 * 	@param key ID as string or integer
	 *	@param trxName transaction
	 * 	@return Location
	 */
	public MLocation getLocation (Object key, String trxName)
	{
		if (key == null)
			return null;
		int C_Location_ID = 0;
		if (key instanceof Integer)
			C_Location_ID = ((Integer)key).intValue();
		else if (key != null)
			C_Location_ID = Integer.parseInt(key.toString());
		//
		return getLocation(C_Location_ID, trxName);
	}	//	getLocation
	
	/**
	 * 	Get Location
	 * 	@param C_Location_ID id
	 *	@param trxName transaction
	 * 	@return Location
	 */
	public MLocation getLocation (int C_Location_ID, String trxName)
	{
		return MLocation.get(m_ctx, C_Location_ID, trxName);
	}	//	getC_Location_ID

	@Override
	public String getTableName()
	{
		return I_C_Location.Table_Name;
	}

	@Override
	public String getColumnName()
	{
		return I_C_Location.Table_Name + "." + I_C_Location.COLUMNNAME_C_Location_ID;
	}   //  getColumnName

	@Override
	public String getColumnNameNotFQ()
	{
		return I_C_Location.COLUMNNAME_C_Location_ID;
	}

	/**
	 *	Return data as sorted Array - not implemented
	 *  @param mandatory mandatory
	 *  @param onlyValidated only validated
	 *  @param onlyActive only active
	 * 	@param temporary force load for temporary display
	 *  @return null
	 */
	@Override
	public List<Object> getData (boolean mandatory, boolean onlyValidated, boolean onlyActive, boolean temporary)
	{
		log.error("not implemented");
		return null;
	}   //  getArray

}	//	MLocation
