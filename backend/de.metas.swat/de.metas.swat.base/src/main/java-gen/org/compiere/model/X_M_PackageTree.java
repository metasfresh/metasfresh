/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_PackageTree
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_M_PackageTree extends PO implements I_M_PackageTree, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130209L;

    /** Standard Constructor */
    public X_M_PackageTree (Properties ctx, int M_PackageTree_ID, String trxName)
    {
      super (ctx, M_PackageTree_ID, trxName);
      /** if (M_PackageTree_ID == 0)
        {
			setC_BPartner_Location_ID (0);
			setIsClosed (false);
// N
			setM_PackageTree_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_PackageTree (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_M_PackageTree[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner_Location)MTable.get(getCtx(), org.compiere.model.I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

	/** Set Standort.
		@param C_BPartner_Location_ID 
		Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
		@return Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Geschlossen.
		@param IsClosed 
		The status is closed
	  */
	public void setIsClosed (boolean IsClosed)
	{
		set_Value (COLUMNNAME_IsClosed, Boolean.valueOf(IsClosed));
	}

	/** Get Geschlossen.
		@return The status is closed
	  */
	public boolean isClosed () 
	{
		Object oo = get_Value(COLUMNNAME_IsClosed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public org.compiere.model.I_M_Package getM_Package() throws RuntimeException
    {
		return (org.compiere.model.I_M_Package)MTable.get(getCtx(), org.compiere.model.I_M_Package.Table_Name)
			.getPO(getM_Package_ID(), get_TrxName());	}

	/** Set PackstĂĽck.
		@param M_Package_ID 
		Shipment Package
	  */
	public void setM_Package_ID (int M_Package_ID)
	{
		if (M_Package_ID < 1) 
			set_Value (COLUMNNAME_M_Package_ID, null);
		else 
			set_Value (COLUMNNAME_M_Package_ID, Integer.valueOf(M_Package_ID));
	}

	/** Get PackstĂĽck.
		@return Shipment Package
	  */
	public int getM_Package_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Package_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Virtual Package.
		@param M_PackageTree_ID Virtual Package	  */
	public void setM_PackageTree_ID (int M_PackageTree_ID)
	{
		if (M_PackageTree_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PackageTree_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PackageTree_ID, Integer.valueOf(M_PackageTree_ID));
	}

	/** Get Virtual Package.
		@return Virtual Package	  */
	public int getM_PackageTree_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PackageTree_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}
