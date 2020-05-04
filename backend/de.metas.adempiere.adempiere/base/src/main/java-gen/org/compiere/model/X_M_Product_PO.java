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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for M_Product_PO
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_M_Product_PO extends PO implements I_M_Product_PO, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_M_Product_PO (Properties ctx, int M_Product_PO_ID, String trxName)
    {
      super (ctx, M_Product_PO_ID, trxName);
      /** if (M_Product_PO_ID == 0)
        {
			setC_BPartner_ID (0);
// 0
			setIsCurrentVendor (true);
// Y
			setM_Product_ID (0);
// @M_Product_ID@
			setVendorProductNo (null);
// @Value@
        } */
    }

    /** Load Constructor */
    public X_M_Product_PO (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_Product_PO[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Currency getC_Currency() throws RuntimeException
    {
		return (I_C_Currency)MTable.get(getCtx(), I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Currency.
		@param C_Currency_ID 
		The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Currency.
		@return The Currency for this record
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Cost per Order.
		@param CostPerOrder 
		Fixed Cost Per Order
	  */
	public void setCostPerOrder (BigDecimal CostPerOrder)
	{
		set_Value (COLUMNNAME_CostPerOrder, CostPerOrder);
	}

	/** Get Cost per Order.
		@return Fixed Cost Per Order
	  */
	public BigDecimal getCostPerOrder () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CostPerOrder);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_C_UOM getC_UOM() throws RuntimeException
    {
		return (I_C_UOM)MTable.get(getCtx(), I_C_UOM.Table_Name)
			.getPO(getC_UOM_ID(), get_TrxName());	}

	/** Set UOM.
		@param C_UOM_ID 
		Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get UOM.
		@return Unit of Measure
	  */
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Actual Delivery Time.
		@param DeliveryTime_Actual 
		Actual days between order and delivery
	  */
	public void setDeliveryTime_Actual (int DeliveryTime_Actual)
	{
		set_Value (COLUMNNAME_DeliveryTime_Actual, Integer.valueOf(DeliveryTime_Actual));
	}

	/** Get Actual Delivery Time.
		@return Actual days between order and delivery
	  */
	public int getDeliveryTime_Actual () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DeliveryTime_Actual);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Promised Delivery Time.
		@param DeliveryTime_Promised 
		Promised days between order and delivery
	  */
	public void setDeliveryTime_Promised (int DeliveryTime_Promised)
	{
		set_Value (COLUMNNAME_DeliveryTime_Promised, Integer.valueOf(DeliveryTime_Promised));
	}

	/** Get Promised Delivery Time.
		@return Promised days between order and delivery
	  */
	public int getDeliveryTime_Promised () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DeliveryTime_Promised);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Discontinued.
		@param Discontinued 
		This product is no longer available
	  */
	public void setDiscontinued (boolean Discontinued)
	{
		set_Value (COLUMNNAME_Discontinued, Boolean.valueOf(Discontinued));
	}

	/** Get Discontinued.
		@return This product is no longer available
	  */
	public boolean isDiscontinued () 
	{
		Object oo = get_Value(COLUMNNAME_Discontinued);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Discontinued by.
		@param DiscontinuedBy 
		Discontinued By
	  */
	public void setDiscontinuedBy (Timestamp DiscontinuedBy)
	{
		set_Value (COLUMNNAME_DiscontinuedBy, DiscontinuedBy);
	}

	/** Get Discontinued by.
		@return Discontinued By
	  */
	public Timestamp getDiscontinuedBy () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DiscontinuedBy);
	}

	/** Set Current vendor.
		@param IsCurrentVendor 
		Use this Vendor for pricing and stock replenishment
	  */
	public void setIsCurrentVendor (boolean IsCurrentVendor)
	{
		set_Value (COLUMNNAME_IsCurrentVendor, Boolean.valueOf(IsCurrentVendor));
	}

	/** Get Current vendor.
		@return Use this Vendor for pricing and stock replenishment
	  */
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

	/** Set Manufacturer.
		@param Manufacturer 
		Manufacturer of the Product
	  */
	public void setManufacturer (String Manufacturer)
	{
		set_Value (COLUMNNAME_Manufacturer, Manufacturer);
	}

	/** Get Manufacturer.
		@return Manufacturer of the Product
	  */
	public String getManufacturer () 
	{
		return (String)get_Value(COLUMNNAME_Manufacturer);
	}

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Minimum Order Qty.
		@param Order_Min 
		Minimum order quantity in UOM
	  */
	public void setOrder_Min (BigDecimal Order_Min)
	{
		set_Value (COLUMNNAME_Order_Min, Order_Min);
	}

	/** Get Minimum Order Qty.
		@return Minimum order quantity in UOM
	  */
	public BigDecimal getOrder_Min () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Order_Min);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Order Pack Qty.
		@param Order_Pack 
		Package order size in UOM (e.g. order set of 5 units)
	  */
	public void setOrder_Pack (BigDecimal Order_Pack)
	{
		set_Value (COLUMNNAME_Order_Pack, Order_Pack);
	}

	/** Get Order Pack Qty.
		@return Package order size in UOM (e.g. order set of 5 units)
	  */
	public BigDecimal getOrder_Pack () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Order_Pack);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Price effective.
		@param PriceEffective 
		Effective Date of Price
	  */
	public void setPriceEffective (Timestamp PriceEffective)
	{
		set_Value (COLUMNNAME_PriceEffective, PriceEffective);
	}

	/** Get Price effective.
		@return Effective Date of Price
	  */
	public Timestamp getPriceEffective () 
	{
		return (Timestamp)get_Value(COLUMNNAME_PriceEffective);
	}

	/** Set Last Invoice Price.
		@param PriceLastInv 
		Price of the last invoice for the product
	  */
	public void setPriceLastInv (BigDecimal PriceLastInv)
	{
		set_ValueNoCheck (COLUMNNAME_PriceLastInv, PriceLastInv);
	}

	/** Get Last Invoice Price.
		@return Price of the last invoice for the product
	  */
	public BigDecimal getPriceLastInv () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceLastInv);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Last PO Price.
		@param PriceLastPO 
		Price of the last purchase order for the product
	  */
	public void setPriceLastPO (BigDecimal PriceLastPO)
	{
		set_ValueNoCheck (COLUMNNAME_PriceLastPO, PriceLastPO);
	}

	/** Get Last PO Price.
		@return Price of the last purchase order for the product
	  */
	public BigDecimal getPriceLastPO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceLastPO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set List Price.
		@param PriceList 
		List Price
	  */
	public void setPriceList (BigDecimal PriceList)
	{
		set_Value (COLUMNNAME_PriceList, PriceList);
	}

	/** Get List Price.
		@return List Price
	  */
	public BigDecimal getPriceList () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceList);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PO Price.
		@param PricePO 
		Price based on a purchase order
	  */
	public void setPricePO (BigDecimal PricePO)
	{
		set_Value (COLUMNNAME_PricePO, PricePO);
	}

	/** Get PO Price.
		@return Price based on a purchase order
	  */
	public BigDecimal getPricePO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PricePO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quality Rating.
		@param QualityRating 
		Method for rating vendors
	  */
	public void setQualityRating (int QualityRating)
	{
		set_Value (COLUMNNAME_QualityRating, Integer.valueOf(QualityRating));
	}

	/** Get Quality Rating.
		@return Method for rating vendors
	  */
	public int getQualityRating () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_QualityRating);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Royalty Amount.
		@param RoyaltyAmt 
		(Included) Amount for copyright, etc.
	  */
	public void setRoyaltyAmt (BigDecimal RoyaltyAmt)
	{
		set_Value (COLUMNNAME_RoyaltyAmt, RoyaltyAmt);
	}

	/** Get Royalty Amount.
		@return (Included) Amount for copyright, etc.
	  */
	public BigDecimal getRoyaltyAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RoyaltyAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UPC/EAN.
		@param UPC 
		Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public void setUPC (String UPC)
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	/** Get UPC/EAN.
		@return Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public String getUPC () 
	{
		return (String)get_Value(COLUMNNAME_UPC);
	}

	/** Set Partner Category.
		@param VendorCategory 
		Product Category of the Business Partner
	  */
	public void setVendorCategory (String VendorCategory)
	{
		set_Value (COLUMNNAME_VendorCategory, VendorCategory);
	}

	/** Get Partner Category.
		@return Product Category of the Business Partner
	  */
	public String getVendorCategory () 
	{
		return (String)get_Value(COLUMNNAME_VendorCategory);
	}

	/** Set Partner Product Key.
		@param VendorProductNo 
		Product Key of the Business Partner
	  */
	public void setVendorProductNo (String VendorProductNo)
	{
		set_Value (COLUMNNAME_VendorProductNo, VendorProductNo);
	}

	/** Get Partner Product Key.
		@return Product Key of the Business Partner
	  */
	public String getVendorProductNo () 
	{
		return (String)get_Value(COLUMNNAME_VendorProductNo);
	}
}