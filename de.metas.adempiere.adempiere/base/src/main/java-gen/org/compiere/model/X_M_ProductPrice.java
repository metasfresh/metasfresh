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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for M_ProductPrice
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_ProductPrice extends org.compiere.model.PO implements I_M_ProductPrice, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 799201189L;

    /** Standard Constructor */
    public X_M_ProductPrice (Properties ctx, int M_ProductPrice_ID, String trxName)
    {
      super (ctx, M_ProductPrice_ID, trxName);
      /** if (M_ProductPrice_ID == 0)
        {
			setC_TaxCategory_ID (0);
			setM_PriceList_Version_ID (0);
			setM_Product_ID (0);
			setM_ProductPrice_ID (0);
			setPriceLimit (Env.ZERO);
			setPriceList (Env.ZERO);
			setPriceStd (Env.ZERO);
			setSeqNo (0);
// @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM M_ProductPrice WHERE M_PriceList_Version_ID=@M_PriceList_Version_ID@
        } */
    }

    /** Load Constructor */
    public X_M_ProductPrice (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_M_ProductPrice[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_C_TaxCategory getC_TaxCategory() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_TaxCategory_ID, org.compiere.model.I_C_TaxCategory.class);
	}

	@Override
	public void setC_TaxCategory(org.compiere.model.I_C_TaxCategory C_TaxCategory)
	{
		set_ValueFromPO(COLUMNNAME_C_TaxCategory_ID, org.compiere.model.I_C_TaxCategory.class, C_TaxCategory);
	}

	/** Set Steuerkategorie.
		@param C_TaxCategory_ID 
		Steuerkategorie
	  */
	@Override
	public void setC_TaxCategory_ID (int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1) 
			set_Value (COLUMNNAME_C_TaxCategory_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxCategory_ID, Integer.valueOf(C_TaxCategory_ID));
	}

	/** Get Steuerkategorie.
		@return Steuerkategorie
	  */
	@Override
	public int getC_TaxCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Maßeinheit
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PriceList_Version getM_PriceList_Version() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_PriceList_Version_ID, org.compiere.model.I_M_PriceList_Version.class);
	}

	@Override
	public void setM_PriceList_Version(org.compiere.model.I_M_PriceList_Version M_PriceList_Version)
	{
		set_ValueFromPO(COLUMNNAME_M_PriceList_Version_ID, org.compiere.model.I_M_PriceList_Version.class, M_PriceList_Version);
	}

	/** Set Version Preisliste.
		@param M_PriceList_Version_ID 
		Identifies a unique instance of a Price List
	  */
	@Override
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID)
	{
		if (M_PriceList_Version_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PriceList_Version_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PriceList_Version_ID, Integer.valueOf(M_PriceList_Version_ID));
	}

	/** Get Version Preisliste.
		@return Identifies a unique instance of a Price List
	  */
	@Override
	public int getM_PriceList_Version_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PriceList_Version_ID);
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
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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

	/** Set M_ProductPrice_ID.
		@param M_ProductPrice_ID M_ProductPrice_ID	  */
	@Override
	public void setM_ProductPrice_ID (int M_ProductPrice_ID)
	{
		if (M_ProductPrice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_ID, Integer.valueOf(M_ProductPrice_ID));
	}

	/** Get M_ProductPrice_ID.
		@return M_ProductPrice_ID	  */
	@Override
	public int getM_ProductPrice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductPrice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mindestpreis.
		@param PriceLimit 
		Lowest price for a product
	  */
	@Override
	public void setPriceLimit (java.math.BigDecimal PriceLimit)
	{
		set_Value (COLUMNNAME_PriceLimit, PriceLimit);
	}

	/** Get Mindestpreis.
		@return Lowest price for a product
	  */
	@Override
	public java.math.BigDecimal getPriceLimit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceLimit);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Auszeichnungspreis.
		@param PriceList 
		Auszeichnungspreis
	  */
	@Override
	public void setPriceList (java.math.BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	/** Get Auszeichnungspreis.
		@return Auszeichnungspreis
	  */
	@Override
	public java.math.BigDecimal getPriceList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceList);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Standardpreis.
		@param PriceStd 
		Standard Price
	  */
	@Override
	public void setPriceStd (java.math.BigDecimal PriceStd)
	{
		set_Value (COLUMNNAME_PriceStd, PriceStd);
	}

	/** Get Standardpreis.
		@return Standard Price
	  */
	@Override
	public java.math.BigDecimal getPriceStd () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceStd);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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