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

/** Generated Model for C_BPartner_Product
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_BPartner_Product extends org.compiere.model.PO implements I_C_BPartner_Product, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1333517629L;

    /** Standard Constructor */
    public X_C_BPartner_Product (Properties ctx, int C_BPartner_Product_ID, String trxName)
    {
      super (ctx, C_BPartner_Product_ID, trxName);
      /** if (C_BPartner_Product_ID == 0)
        {
			setC_BPartner_ID (0);
			setIsCurrentVendor (false);
// N
			setM_Product_ID (0);
			setShelfLifeMinDays (0);
			setShelfLifeMinPct (0);
        } */
    }

    /** Load Constructor */
    public X_C_BPartner_Product (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_C_BPartner_Product[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Identifies a Business Partner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner_Vendor() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Vendor_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner_Vendor(org.compiere.model.I_C_BPartner C_BPartner_Vendor)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Vendor_ID, org.compiere.model.I_C_BPartner.class, C_BPartner_Vendor);
	}

	/** Set C_BPartner_Vendor_ID.
		@param C_BPartner_Vendor_ID C_BPartner_Vendor_ID	  */
	@Override
	public void setC_BPartner_Vendor_ID (int C_BPartner_Vendor_ID)
	{
		if (C_BPartner_Vendor_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Vendor_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Vendor_ID, Integer.valueOf(C_BPartner_Vendor_ID));
	}

	/** Get C_BPartner_Vendor_ID.
		@return C_BPartner_Vendor_ID	  */
	@Override
	public int getC_BPartner_Vendor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Vendor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zugesicherte Lieferzeit.
		@param DeliveryTime_Promised 
		Zugesicherte Anzahl Tage zwischen Bestellung und Lieferung
	  */
	@Override
	public void setDeliveryTime_Promised (int DeliveryTime_Promised)
	{
		set_Value (COLUMNNAME_DeliveryTime_Promised, Integer.valueOf(DeliveryTime_Promised));
	}

	/** Get Zugesicherte Lieferzeit.
		@return Zugesicherte Anzahl Tage zwischen Bestellung und Lieferung
	  */
	@Override
	public int getDeliveryTime_Promised () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DeliveryTime_Promised);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Gegenwärtiger Lieferant.
		@param IsCurrentVendor 
		Diesen Lieferanten für Bepreisung und Lagerauffüllung verwenden
	  */
	@Override
	public void setIsCurrentVendor (boolean IsCurrentVendor)
	{
		set_Value (COLUMNNAME_IsCurrentVendor, Boolean.valueOf(IsCurrentVendor));
	}

	/** Get Gegenwärtiger Lieferant.
		@return Diesen Lieferanten für Bepreisung und Lagerauffüllung verwenden
	  */
	@Override
	public boolean isCurrentVendor () 
	{
		Object oo = get_Value(COLUMNNAME_IsCurrentVendor);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Hersteller.
		@param Manufacturer 
		Manufacturer of the Product
	  */
	@Override
	public void setManufacturer (java.lang.String Manufacturer)
	{
		set_Value (COLUMNNAME_Manufacturer, Manufacturer);
	}

	/** Get Hersteller.
		@return Manufacturer of the Product
	  */
	@Override
	public java.lang.String getManufacturer () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Manufacturer);
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

	/** Set Mindestbestellmenge.
		@param Order_Min 
		Mindestbestellmenge in Mengeneinheit
	  */
	@Override
	public void setOrder_Min (java.math.BigDecimal Order_Min)
	{
		set_Value (COLUMNNAME_Order_Min, Order_Min);
	}

	/** Get Mindestbestellmenge.
		@return Mindestbestellmenge in Mengeneinheit
	  */
	@Override
	public java.math.BigDecimal getOrder_Min () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Order_Min);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Packungsgröße.
		@param Order_Pack 
		Größe einer Bestellpackung in Mengeneinheit (z.B. Satz von 5 Einheiten)
	  */
	@Override
	public void setOrder_Pack (java.math.BigDecimal Order_Pack)
	{
		set_Value (COLUMNNAME_Order_Pack, Order_Pack);
	}

	/** Get Packungsgröße.
		@return Größe einer Bestellpackung in Mengeneinheit (z.B. Satz von 5 Einheiten)
	  */
	@Override
	public java.math.BigDecimal getOrder_Pack () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Order_Pack);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Qualitäts-Einstufung.
		@param QualityRating 
		Method for rating vendors
	  */
	@Override
	public void setQualityRating (java.math.BigDecimal QualityRating)
	{
		set_Value (COLUMNNAME_QualityRating, QualityRating);
	}

	/** Get Qualitäts-Einstufung.
		@return Method for rating vendors
	  */
	@Override
	public java.math.BigDecimal getQualityRating () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QualityRating);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Mindesthaltbarkeit Tage.
		@param ShelfLifeMinDays 
		Minimum Shelf Life in days based on Product Instance Guarantee Date
	  */
	@Override
	public void setShelfLifeMinDays (int ShelfLifeMinDays)
	{
		set_Value (COLUMNNAME_ShelfLifeMinDays, Integer.valueOf(ShelfLifeMinDays));
	}

	/** Get Mindesthaltbarkeit Tage.
		@return Minimum Shelf Life in days based on Product Instance Guarantee Date
	  */
	@Override
	public int getShelfLifeMinDays () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShelfLifeMinDays);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mindesthaltbarkeit %.
		@param ShelfLifeMinPct 
		Minimum Shelf Life in percent based on Product Instance Guarantee Date
	  */
	@Override
	public void setShelfLifeMinPct (int ShelfLifeMinPct)
	{
		set_Value (COLUMNNAME_ShelfLifeMinPct, Integer.valueOf(ShelfLifeMinPct));
	}

	/** Get Mindesthaltbarkeit %.
		@return Minimum Shelf Life in percent based on Product Instance Guarantee Date
	  */
	@Override
	public int getShelfLifeMinPct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ShelfLifeMinPct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Produkt-Kategorie Geschäftspartner.
		@param VendorCategory 
		Product Category of the Business Partner
	  */
	@Override
	public void setVendorCategory (java.lang.String VendorCategory)
	{
		set_Value (COLUMNNAME_VendorCategory, VendorCategory);
	}

	/** Get Produkt-Kategorie Geschäftspartner.
		@return Product Category of the Business Partner
	  */
	@Override
	public java.lang.String getVendorCategory () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VendorCategory);
	}

	/** Set Produkt-Nr. Geschäftspartner.
		@param VendorProductNo 
		Product Key of the Business Partner
	  */
	@Override
	public void setVendorProductNo (java.lang.String VendorProductNo)
	{
		set_Value (COLUMNNAME_VendorProductNo, VendorProductNo);
	}

	/** Get Produkt-Nr. Geschäftspartner.
		@return Product Key of the Business Partner
	  */
	@Override
	public java.lang.String getVendorProductNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VendorProductNo);
	}
}