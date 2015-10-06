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

/** Generated Model for M_AttributeValue_Mapping
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_AttributeValue_Mapping extends org.compiere.model.PO implements I_M_AttributeValue_Mapping, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2084544175L;

    /** Standard Constructor */
    public X_M_AttributeValue_Mapping (Properties ctx, int M_AttributeValue_Mapping_ID, String trxName)
    {
      super (ctx, M_AttributeValue_Mapping_ID, trxName);
      /** if (M_AttributeValue_Mapping_ID == 0)
        {
			setM_AttributeValue_ID (0);
			setM_AttributeValue_Mapping_ID (0);
			setM_AttributeValue_To_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_AttributeValue_Mapping (Properties ctx, ResultSet rs, String trxName)
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

    @Override
    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_M_AttributeValue_Mapping[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	@Override
	public org.compiere.model.I_M_AttributeValue getM_AttributeValue() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeValue_ID, org.compiere.model.I_M_AttributeValue.class);
	}

	@Override
	public void setM_AttributeValue(org.compiere.model.I_M_AttributeValue M_AttributeValue)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeValue_ID, org.compiere.model.I_M_AttributeValue.class, M_AttributeValue);
	}

	/** Set Merkmals-Wert.
		@param M_AttributeValue_ID 
		Product Attribute Value
	  */
	@Override
	public void setM_AttributeValue_ID (int M_AttributeValue_ID)
	{
		if (M_AttributeValue_ID < 1) 
			set_Value (COLUMNNAME_M_AttributeValue_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeValue_ID, Integer.valueOf(M_AttributeValue_ID));
	}

	/** Get Merkmals-Wert.
		@return Product Attribute Value
	  */
	@Override
	public int getM_AttributeValue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeValue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Attribut substitute.
		@param M_AttributeValue_Mapping_ID Attribut substitute	  */
	@Override
	public void setM_AttributeValue_Mapping_ID (int M_AttributeValue_Mapping_ID)
	{
		if (M_AttributeValue_Mapping_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeValue_Mapping_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeValue_Mapping_ID, Integer.valueOf(M_AttributeValue_Mapping_ID));
	}

	/** Get Attribut substitute.
		@return Attribut substitute	  */
	@Override
	public int getM_AttributeValue_Mapping_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeValue_Mapping_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_AttributeValue getM_AttributeValue_To() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeValue_To_ID, org.compiere.model.I_M_AttributeValue.class);
	}

	@Override
	public void setM_AttributeValue_To(org.compiere.model.I_M_AttributeValue M_AttributeValue_To)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeValue_To_ID, org.compiere.model.I_M_AttributeValue.class, M_AttributeValue_To);
	}

	/** Set Merkmals-Wert Nach.
		@param M_AttributeValue_To_ID 
		Product Attribute Value To
	  */
	@Override
	public void setM_AttributeValue_To_ID (int M_AttributeValue_To_ID)
	{
		if (M_AttributeValue_To_ID < 1) 
			set_Value (COLUMNNAME_M_AttributeValue_To_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeValue_To_ID, Integer.valueOf(M_AttributeValue_To_ID));
	}

	/** Get Merkmals-Wert Nach.
		@return Product Attribute Value To
	  */
	@Override
	public int getM_AttributeValue_To_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeValue_To_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}