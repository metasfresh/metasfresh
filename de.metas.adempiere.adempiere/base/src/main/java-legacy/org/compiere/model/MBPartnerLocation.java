/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
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
import java.util.ArrayList;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;

import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.DB;


/**
 *	Partner Location Model
 *
 *  @author Jorg Janke
 *  @version $Id: MBPartnerLocation.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MBPartnerLocation extends X_C_BPartner_Location
{
	private static final long serialVersionUID = 1L;


	/**
	 * 	Get Locations for BPartner
	 *	@param ctx context
	 *	@param C_BPartner_ID bp
	 *	@return array of locations
	 */
	public static MBPartnerLocation[] getForBPartner (Properties ctx, int C_BPartner_ID)
	{
		ArrayList<MBPartnerLocation> list = new ArrayList<MBPartnerLocation>();
		
		// metas-ts: column belongs to de.metas.terminable and can't be assumed to be available
		String sql = "SELECT * FROM C_BPartner_Location WHERE C_BPartner_ID=?";
		sql += " ORDER BY /*ValidTo DESC,*/ IsActive DESC, IsShipTo DESC, IsBillTo DESC"; // metas: task 03537 cg: shipTo has priority
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, C_BPartner_ID);
			rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add(new MBPartnerLocation(ctx, rs, null));
		}
		catch (Exception e)
		{
			s_log.error("getForBPartner", e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		MBPartnerLocation[] retValue = new MBPartnerLocation[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getForBPartner
	
	/**	Static Logger					*/
	private static Logger	s_log	= LogManager.getLogger(MBPartnerLocation.class);

	
	/**************************************************************************
	 * 	Default Constructor
	 *	@param ctx context
	 *	@param C_BPartner_Location_ID id
	 *	@param trxName transaction
	 */
	public MBPartnerLocation (Properties ctx, int C_BPartner_Location_ID, String trxName)
	{
		super (ctx, C_BPartner_Location_ID, trxName);
		if (C_BPartner_Location_ID == 0)
		{
			setName (".");
			//
			setIsShipTo (true);
			setIsRemitTo (true);
			setIsPayFrom (true);
			setIsBillTo (true);
		}
	}	//	MBPartner_Location

	/**
	 * 	BP Parent Constructor
	 * 	@param bp partner
	 */
	public MBPartnerLocation (I_C_BPartner bp)
	{
		this(InterfaceWrapperHelper.getCtx(bp),
				0,
				InterfaceWrapperHelper.getTrxName(bp));
		setClientOrg(InterfaceWrapperHelper.getPO(bp));
		//	may (still) be 0
		set_ValueNoCheck ("C_BPartner_ID", bp.getC_BPartner_ID());
	}	//	MBPartner_Location

	/**
	 * 	Constructor from ResultSet row
	 *	@param ctx context
	 * 	@param rs current row of result set to be loaded
	 *	@param trxName transaction
	 */
	public MBPartnerLocation (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MBPartner_Location

	/**	Cached Location			*/
	private MLocation 	m_location = null;
	/**	Unique Name				*/
	private String		m_uniqueName = null;
	private int			m_unique = 0;
	
	/**
	 * 	Get Location/Address
	 * 	@param requery requery
	 *	@return location
	 * @deprecated Please use {@link #getC_Location()}
	 */
	@Deprecated
	public MLocation getLocation (boolean requery)
	{
		if (m_location == null)
			m_location = MLocation.get (getCtx(), getC_Location_ID(), get_TrxName());
		return m_location;
	}	//	getLoaction

	/**
	 *	String Representation
	 * 	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MBPartner_Location[ID=")
			.append(get_ID())
			.append(",C_Location_ID=").append(getC_Location_ID())
			.append(",Name=").append(getName())
			.append ("]");
		return sb.toString ();
	}	//	toString

	
	/**************************************************************************
	 * 	Before Save.
	 * 	- Set Name
	 *	@param newRecord new
	 *	@return save
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (getC_Location_ID() <= 0)
		{
			throw new FillMandatoryException(COLUMNNAME_C_Location_ID);
		}

		//	Set New Name
		if (!newRecord)
		{
			return true;
		}

		MLocation address = getLocation(true);
		m_uniqueName = getName();
		if (m_uniqueName != null && m_uniqueName.equals("."))	//	default
			m_uniqueName = null;
		m_unique = 0;
		
		// metas: if possible, we want to preserve the location name as it is
		if (Check.isEmpty(m_uniqueName, true)) {
			makeUnique(address);
		}
		
		//	Check uniqueness
		MBPartnerLocation[] locations = getForBPartner(getCtx(), getC_BPartner_ID());
		boolean unique = locations.length == 0;
		
		//metas: this code can hang if not enough location fields are set
		while (!unique)
		{
			unique = true;
			for (int i = 0; i < locations.length; i++)
			{
				MBPartnerLocation location = locations[i];
				if (location.getC_BPartner_Location_ID() == get_ID())
					continue;
				if (m_uniqueName.equals(location.getName()))
				{
					makeUnique(address);
					unique = false;
					break;
				}
			}
		}
		setName (m_uniqueName);
		return true;
	}	//	beforeSave
	
	/**
	 * 	Make name Unique
	 * 	@param address address
	 */
	private void makeUnique (MLocation address)
	{
		
		if (m_uniqueName == null)
			m_uniqueName = "";
		m_unique++;
		
		//	0 - City
		if (m_uniqueName.length() == 0)
		{
			String xx = address.getCity(); 
			if (xx != null && xx.length() > 0)
				m_uniqueName = xx;
			m_unique = 1;
		}
		//	1 + Address1
		if (m_unique == 1 ||  m_uniqueName.length() == 0)
		{
			String xx = address.getAddress1();
			if (xx != null && xx.length() > 0)
			{
				if (m_uniqueName.length() > 0)
					m_uniqueName += " ";
				m_uniqueName += xx;
			}
			m_unique = 2;
		}
		//	2 + Address2
		if (m_unique == 2 ||  m_uniqueName.length() == 0)
		{
			String xx = address.getAddress2();
			if (xx != null && xx.length() > 0)
			{
				if (m_uniqueName.length() > 0)
					m_uniqueName += " ";
				m_uniqueName += xx;
			}
			m_unique = 3;
		}
		//	3 + Address3
		if (m_unique == 3 ||  m_uniqueName.length() == 0)
		{
			String xx = address.getAddress3();
			if (xx != null && xx.length() > 0)
			{
				if (m_uniqueName.length() > 0)
					m_uniqueName += " ";
				m_uniqueName += xx;
			}
			m_unique = 4;
		}
		//	4 + Address4
		if (m_unique == 4 ||  m_uniqueName.length() == 0)
		{
			String xx = address.getAddress4();
			if (xx != null && xx.length() > 0)
			{
				if (m_uniqueName.length() > 0)
					m_uniqueName += " ";
				m_uniqueName += xx;
			}
			m_unique = 4;
		}
		//	5 - Region	
		if (m_unique == 5 ||  m_uniqueName.length() == 0)
		{
			String xx = address.getRegionName(true);
			{
				if (m_uniqueName.length() > 0 && xx.length() > 0)
					m_uniqueName += " ";
				m_uniqueName += xx;
			}
			m_unique = 5;
		}
		//	6 - ID	
		if (m_unique == 6 ||  m_uniqueName.length() == 0)
		{
			int id = get_ID();
			if (id == 0)
				id = address.get_ID();
			m_uniqueName += " #" + id;		
			m_unique = 6;
		}
		// metas: we need to make sure this will return a unique result anyway and not end in an infinite loop
		if (m_unique > 6)
		{
			m_uniqueName += "-"+(m_unique - 6);
		}
	}	//	makeUnique
	
}	//	MBPartnerLocation
