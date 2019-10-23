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
package de.metas.product.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product_Mapping
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Product_Mapping extends org.compiere.model.PO implements I_M_Product_Mapping, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 13779372L;

    /** Standard Constructor */
    public X_M_Product_Mapping (Properties ctx, int M_Product_Mapping_ID, String trxName)
    {
      super (ctx, M_Product_Mapping_ID, trxName);
      /** if (M_Product_Mapping_ID == 0)
        {
			setM_Product_Mapping_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_Product_Mapping (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Produkt-Zuordnung.
		@param M_Product_Mapping_ID Produkt-Zuordnung	  */
	@Override
	public void setM_Product_Mapping_ID (int M_Product_Mapping_ID)
	{
		if (M_Product_Mapping_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_Mapping_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_Mapping_ID, Integer.valueOf(M_Product_Mapping_ID));
	}

	/** Get Produkt-Zuordnung.
		@return Produkt-Zuordnung	  */
	@Override
	public int getM_Product_Mapping_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Mapping_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Suchschlüssel.
		@param Value 
		Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}