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

/** Generated Model for M_Locator
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Locator extends org.compiere.model.PO implements I_M_Locator, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 86737907L;

    /** Standard Constructor */
    public X_M_Locator (Properties ctx, int M_Locator_ID, String trxName)
    {
      super (ctx, M_Locator_ID, trxName);
      /** if (M_Locator_ID == 0)
        {
			setIsDefault (false);
			setM_Locator_ID (0);
			setM_Warehouse_ID (0);
			setPriorityNo (0);
// 50
			setValue (null);
			setX (null);
			setY (null);
			setZ (null);
        } */
    }

    /** Load Constructor */
    public X_M_Locator (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
      */
    @Override
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    @Override
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_M_Locator[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Standard.
		@param IsDefault 
		Default value
	  */
	@Override
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Standard.
		@return Default value
	  */
	@Override
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Lagerort.
		@param M_Locator_ID 
		Warehouse Locator
	  */
	@Override
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Lagerort.
		@return Warehouse Locator
	  */
	@Override
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class, M_Warehouse);
	}

	/** Set Lager.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Lager.
		@return Storage Warehouse and Service Point
	  */
	@Override
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Relative Priorität.
		@param PriorityNo 
		Where inventory should be picked from first
	  */
	@Override
	public void setPriorityNo (int PriorityNo)
	{
		set_Value (COLUMNNAME_PriorityNo, Integer.valueOf(PriorityNo));
	}

	/** Get Relative Priorität.
		@return Where inventory should be picked from first
	  */
	@Override
	public int getPriorityNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PriorityNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Suchschlüssel.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Search key for the record in the format required - must be unique
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}

	/** Set Gang (X).
		@param X 
		X dimension, e.g., Aisle
	  */
	@Override
	public void setX (java.lang.String X)
	{
		set_Value (COLUMNNAME_X, X);
	}

	/** Get Gang (X).
		@return X dimension, e.g., Aisle
	  */
	@Override
	public java.lang.String getX () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_X);
	}

	/** Set Fach (Y).
		@param Y 
		Y dimension, e.g., Bin
	  */
	@Override
	public void setY (java.lang.String Y)
	{
		set_Value (COLUMNNAME_Y, Y);
	}

	/** Get Fach (Y).
		@return Y dimension, e.g., Bin
	  */
	@Override
	public java.lang.String getY () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Y);
	}

	/** Set Ebene (Z).
		@param Z 
		Z dimension, e.g., Level
	  */
	@Override
	public void setZ (java.lang.String Z)
	{
		set_Value (COLUMNNAME_Z, Z);
	}

	/** Get Ebene (Z).
		@return Z dimension, e.g., Level
	  */
	@Override
	public java.lang.String getZ () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Z);
	}
}