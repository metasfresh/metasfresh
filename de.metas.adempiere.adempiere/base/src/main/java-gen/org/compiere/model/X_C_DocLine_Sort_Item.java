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

/** Generated Model for C_DocLine_Sort_Item
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_DocLine_Sort_Item extends org.compiere.model.PO implements I_C_DocLine_Sort_Item, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 2023378452L;

    /** Standard Constructor */
    public X_C_DocLine_Sort_Item (Properties ctx, int C_DocLine_Sort_Item_ID, String trxName)
    {
      super (ctx, C_DocLine_Sort_Item_ID, trxName);
      /** if (C_DocLine_Sort_Item_ID == 0)
        {
			setC_DocLine_Sort_ID (0);
			setC_DocLine_Sort_Item_ID (0);
			setM_Product_ID (0);
			setSeqNo (0);
        } */
    }

    /** Load Constructor */
    public X_C_DocLine_Sort_Item (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_DocLine_Sort getC_DocLine_Sort() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocLine_Sort_ID, org.compiere.model.I_C_DocLine_Sort.class);
	}

	@Override
	public void setC_DocLine_Sort(org.compiere.model.I_C_DocLine_Sort C_DocLine_Sort)
	{
		set_ValueFromPO(COLUMNNAME_C_DocLine_Sort_ID, org.compiere.model.I_C_DocLine_Sort.class, C_DocLine_Sort);
	}

	/** Set Document Line Sorting Preferences.
		@param C_DocLine_Sort_ID Document Line Sorting Preferences	  */
	@Override
	public void setC_DocLine_Sort_ID (int C_DocLine_Sort_ID)
	{
		if (C_DocLine_Sort_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DocLine_Sort_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocLine_Sort_ID, Integer.valueOf(C_DocLine_Sort_ID));
	}

	/** Get Document Line Sorting Preferences.
		@return Document Line Sorting Preferences	  */
	@Override
	public int getC_DocLine_Sort_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocLine_Sort_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Document Line Sorting Preferences Item.
		@param C_DocLine_Sort_Item_ID Document Line Sorting Preferences Item	  */
	@Override
	public void setC_DocLine_Sort_Item_ID (int C_DocLine_Sort_Item_ID)
	{
		if (C_DocLine_Sort_Item_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DocLine_Sort_Item_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocLine_Sort_Item_ID, Integer.valueOf(C_DocLine_Sort_Item_ID));
	}

	/** Get Document Line Sorting Preferences Item.
		@return Document Line Sorting Preferences Item	  */
	@Override
	public int getC_DocLine_Sort_Item_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocLine_Sort_Item_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product(org.compiere.model.I_M_Product M_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class, M_Product);
	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	@Override
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}