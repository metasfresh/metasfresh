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

/** Generated Model for M_AttributeSetInstance
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_AttributeSetInstance extends org.compiere.model.PO implements I_M_AttributeSetInstance, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1495472315L;

    /** Standard Constructor */
    public X_M_AttributeSetInstance (Properties ctx, int M_AttributeSetInstance_ID, String trxName)
    {
      super (ctx, M_AttributeSetInstance_ID, trxName);
      /** if (M_AttributeSetInstance_ID == 0)
        {
			setM_AttributeSet_ID (0);
			setM_AttributeSetInstance_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_AttributeSetInstance (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Garantie-Datum.
		@param GuaranteeDate 
		Date when guarantee expires
	  */
	@Override
	public void setGuaranteeDate (java.sql.Timestamp GuaranteeDate)
	{
		set_Value (COLUMNNAME_GuaranteeDate, GuaranteeDate);
	}

	/** Get Garantie-Datum.
		@return Date when guarantee expires
	  */
	@Override
	public java.sql.Timestamp getGuaranteeDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_GuaranteeDate);
	}

	/** Set Los-Nr..
		@param Lot 
		Los-Nummer (alphanumerisch)
	  */
	@Override
	public void setLot (java.lang.String Lot)
	{
		set_Value (COLUMNNAME_Lot, Lot);
	}

	/** Get Los-Nr..
		@return Los-Nummer (alphanumerisch)
	  */
	@Override
	public java.lang.String getLot () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Lot);
	}

	@Override
	public org.compiere.model.I_M_AttributeSet getM_AttributeSet() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSet_ID, org.compiere.model.I_M_AttributeSet.class);
	}

	@Override
	public void setM_AttributeSet(org.compiere.model.I_M_AttributeSet M_AttributeSet)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSet_ID, org.compiere.model.I_M_AttributeSet.class, M_AttributeSet);
	}

	/** Set Merkmals-Satz.
		@param M_AttributeSet_ID 
		Product Attribute Set
	  */
	@Override
	public void setM_AttributeSet_ID (int M_AttributeSet_ID)
	{
		if (M_AttributeSet_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSet_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSet_ID, Integer.valueOf(M_AttributeSet_ID));
	}

	/** Get Merkmals-Satz.
		@return Product Attribute Set
	  */
	@Override
	public int getM_AttributeSet_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSet_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Ausprägung Merkmals-Satz.
		@param M_AttributeSetInstance_ID 
		Product Attribute Set Instance
	  */
	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Ausprägung Merkmals-Satz.
		@return Product Attribute Set Instance
	  */
	@Override
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Lot getM_Lot() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Lot_ID, org.compiere.model.I_M_Lot.class);
	}

	@Override
	public void setM_Lot(org.compiere.model.I_M_Lot M_Lot)
	{
		set_ValueFromPO(COLUMNNAME_M_Lot_ID, org.compiere.model.I_M_Lot.class, M_Lot);
	}

	/** Set Los.
		@param M_Lot_ID 
		Product Lot Definition
	  */
	@Override
	public void setM_Lot_ID (int M_Lot_ID)
	{
		if (M_Lot_ID < 1) 
			set_Value (COLUMNNAME_M_Lot_ID, null);
		else 
			set_Value (COLUMNNAME_M_Lot_ID, Integer.valueOf(M_Lot_ID));
	}

	/** Get Los.
		@return Product Lot Definition
	  */
	@Override
	public int getM_Lot_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Lot_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Serien-Nr..
		@param SerNo 
		Product Serial Number 
	  */
	@Override
	public void setSerNo (java.lang.String SerNo)
	{
		set_Value (COLUMNNAME_SerNo, SerNo);
	}

	/** Get Serien-Nr..
		@return Product Serial Number 
	  */
	@Override
	public java.lang.String getSerNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SerNo);
	}
}