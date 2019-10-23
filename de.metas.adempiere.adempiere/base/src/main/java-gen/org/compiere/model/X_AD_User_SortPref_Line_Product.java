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

/** Generated Model for AD_User_SortPref_Line_Product
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_User_SortPref_Line_Product extends org.compiere.model.PO implements I_AD_User_SortPref_Line_Product, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1584748980L;

    /** Standard Constructor */
    public X_AD_User_SortPref_Line_Product (Properties ctx, int AD_User_SortPref_Line_Product_ID, String trxName)
    {
      super (ctx, AD_User_SortPref_Line_Product_ID, trxName);
      /** if (AD_User_SortPref_Line_Product_ID == 0)
        {
			setAD_User_SortPref_Line_ID (0);
			setAD_User_SortPref_Line_Product_ID (0);
			setM_Product_ID (0);
			setSeqNo (0);
// @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM AD_User_SortPref_Line_Product WHERE AD_User_SortPref_Line_ID=@AD_User_SortPref_Line_ID@
        } */
    }

    /** Load Constructor */
    public X_AD_User_SortPref_Line_Product (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_AD_User_SortPref_Line_Product[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_AD_User_SortPref_Line getAD_User_SortPref_Line() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_SortPref_Line_ID, org.compiere.model.I_AD_User_SortPref_Line.class);
	}

	@Override
	public void setAD_User_SortPref_Line(org.compiere.model.I_AD_User_SortPref_Line AD_User_SortPref_Line)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_SortPref_Line_ID, org.compiere.model.I_AD_User_SortPref_Line.class, AD_User_SortPref_Line);
	}

	/** Set Sortierbegriff pro Benutzer Position.
		@param AD_User_SortPref_Line_ID Sortierbegriff pro Benutzer Position	  */
	@Override
	public void setAD_User_SortPref_Line_ID (int AD_User_SortPref_Line_ID)
	{
		if (AD_User_SortPref_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_SortPref_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_SortPref_Line_ID, Integer.valueOf(AD_User_SortPref_Line_ID));
	}

	/** Get Sortierbegriff pro Benutzer Position.
		@return Sortierbegriff pro Benutzer Position	  */
	@Override
	public int getAD_User_SortPref_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_SortPref_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sortierbegriff pro Benutzer Position Produkt.
		@param AD_User_SortPref_Line_Product_ID Sortierbegriff pro Benutzer Position Produkt	  */
	@Override
	public void setAD_User_SortPref_Line_Product_ID (int AD_User_SortPref_Line_Product_ID)
	{
		if (AD_User_SortPref_Line_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_SortPref_Line_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_SortPref_Line_Product_ID, Integer.valueOf(AD_User_SortPref_Line_Product_ID));
	}

	/** Get Sortierbegriff pro Benutzer Position Produkt.
		@return Sortierbegriff pro Benutzer Position Produkt	  */
	@Override
	public int getAD_User_SortPref_Line_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_SortPref_Line_Product_ID);
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