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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for M_Product_PO
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_M_Product_PO 
{

    /** TableName=M_Product_PO */
    public static final String Table_Name = "M_Product_PO";

    /** AD_Table_ID=210 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name CostPerOrder */
    public static final String COLUMNNAME_CostPerOrder = "CostPerOrder";

	/** Set Cost per Order.
	  * Fixed Cost Per Order
	  */
	public void setCostPerOrder (BigDecimal CostPerOrder);

	/** Get Cost per Order.
	  * Fixed Cost Per Order
	  */
	public BigDecimal getCostPerOrder();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/** Set UOM.
	  * Unit of Measure
	  */
	public void setC_UOM_ID (int C_UOM_ID);

	/** Get UOM.
	  * Unit of Measure
	  */
	public int getC_UOM_ID();

	public I_C_UOM getC_UOM() throws RuntimeException;

    /** Column name DeliveryTime_Actual */
    public static final String COLUMNNAME_DeliveryTime_Actual = "DeliveryTime_Actual";

	/** Set Actual Delivery Time.
	  * Actual days between order and delivery
	  */
	public void setDeliveryTime_Actual (int DeliveryTime_Actual);

	/** Get Actual Delivery Time.
	  * Actual days between order and delivery
	  */
	public int getDeliveryTime_Actual();

    /** Column name DeliveryTime_Promised */
    public static final String COLUMNNAME_DeliveryTime_Promised = "DeliveryTime_Promised";

	/** Set Promised Delivery Time.
	  * Promised days between order and delivery
	  */
	public void setDeliveryTime_Promised (int DeliveryTime_Promised);

	/** Get Promised Delivery Time.
	  * Promised days between order and delivery
	  */
	public int getDeliveryTime_Promised();

    /** Column name Discontinued */
    public static final String COLUMNNAME_Discontinued = "Discontinued";

	/** Set Discontinued.
	  * This product is no longer available
	  */
	public void setDiscontinued (boolean Discontinued);

	/** Get Discontinued.
	  * This product is no longer available
	  */
	public boolean isDiscontinued();

    /** Column name DiscontinuedBy */
    public static final String COLUMNNAME_DiscontinuedBy = "DiscontinuedBy";

	/** Set Discontinued by.
	  * Discontinued By
	  */
	public void setDiscontinuedBy (Timestamp DiscontinuedBy);

	/** Get Discontinued by.
	  * Discontinued By
	  */
	public Timestamp getDiscontinuedBy();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsCurrentVendor */
    public static final String COLUMNNAME_IsCurrentVendor = "IsCurrentVendor";

	/** Set Current vendor.
	  * Use this Vendor for pricing and stock replenishment
	  */
	public void setIsCurrentVendor (boolean IsCurrentVendor);

	/** Get Current vendor.
	  * Use this Vendor for pricing and stock replenishment
	  */
	public boolean isCurrentVendor();

    /** Column name Manufacturer */
    public static final String COLUMNNAME_Manufacturer = "Manufacturer";

	/** Set Manufacturer.
	  * Manufacturer of the Product
	  */
	public void setManufacturer (String Manufacturer);

	/** Get Manufacturer.
	  * Manufacturer of the Product
	  */
	public String getManufacturer();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public I_M_Product getM_Product() throws RuntimeException;

    /** Column name Order_Min */
    public static final String COLUMNNAME_Order_Min = "Order_Min";

	/** Set Minimum Order Qty.
	  * Minimum order quantity in UOM
	  */
	public void setOrder_Min (BigDecimal Order_Min);

	/** Get Minimum Order Qty.
	  * Minimum order quantity in UOM
	  */
	public BigDecimal getOrder_Min();

    /** Column name Order_Pack */
    public static final String COLUMNNAME_Order_Pack = "Order_Pack";

	/** Set Order Pack Qty.
	  * Package order size in UOM (e.g. order set of 5 units)
	  */
	public void setOrder_Pack (BigDecimal Order_Pack);

	/** Get Order Pack Qty.
	  * Package order size in UOM (e.g. order set of 5 units)
	  */
	public BigDecimal getOrder_Pack();

    /** Column name PriceEffective */
    public static final String COLUMNNAME_PriceEffective = "PriceEffective";

	/** Set Price effective.
	  * Effective Date of Price
	  */
	public void setPriceEffective (Timestamp PriceEffective);

	/** Get Price effective.
	  * Effective Date of Price
	  */
	public Timestamp getPriceEffective();

    /** Column name PriceLastInv */
    public static final String COLUMNNAME_PriceLastInv = "PriceLastInv";

	/** Set Last Invoice Price.
	  * Price of the last invoice for the product
	  */
	public void setPriceLastInv (BigDecimal PriceLastInv);

	/** Get Last Invoice Price.
	  * Price of the last invoice for the product
	  */
	public BigDecimal getPriceLastInv();

    /** Column name PriceLastPO */
    public static final String COLUMNNAME_PriceLastPO = "PriceLastPO";

	/** Set Last PO Price.
	  * Price of the last purchase order for the product
	  */
	public void setPriceLastPO (BigDecimal PriceLastPO);

	/** Get Last PO Price.
	  * Price of the last purchase order for the product
	  */
	public BigDecimal getPriceLastPO();

    /** Column name PriceList */
    public static final String COLUMNNAME_PriceList = "PriceList";

	/** Set List Price.
	  * List Price
	  */
	public void setPriceList (BigDecimal PriceList);

	/** Get List Price.
	  * List Price
	  */
	public BigDecimal getPriceList();

    /** Column name PricePO */
    public static final String COLUMNNAME_PricePO = "PricePO";

	/** Set PO Price.
	  * Price based on a purchase order
	  */
	public void setPricePO (BigDecimal PricePO);

	/** Get PO Price.
	  * Price based on a purchase order
	  */
	public BigDecimal getPricePO();

    /** Column name QualityRating */
    public static final String COLUMNNAME_QualityRating = "QualityRating";

	/** Set Quality Rating.
	  * Method for rating vendors
	  */
	public void setQualityRating (int QualityRating);

	/** Get Quality Rating.
	  * Method for rating vendors
	  */
	public int getQualityRating();

    /** Column name RoyaltyAmt */
    public static final String COLUMNNAME_RoyaltyAmt = "RoyaltyAmt";

	/** Set Royalty Amount.
	  * (Included) Amount for copyright, etc.
	  */
	public void setRoyaltyAmt (BigDecimal RoyaltyAmt);

	/** Get Royalty Amount.
	  * (Included) Amount for copyright, etc.
	  */
	public BigDecimal getRoyaltyAmt();

    /** Column name UPC */
    public static final String COLUMNNAME_UPC = "UPC";

	/** Set UPC/EAN.
	  * Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public void setUPC (String UPC);

	/** Get UPC/EAN.
	  * Bar Code (Universal Product Code or its superset European Article Number)
	  */
	public String getUPC();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name VendorCategory */
    public static final String COLUMNNAME_VendorCategory = "VendorCategory";

	/** Set Partner Category.
	  * Product Category of the Business Partner
	  */
	public void setVendorCategory (String VendorCategory);

	/** Get Partner Category.
	  * Product Category of the Business Partner
	  */
	public String getVendorCategory();

    /** Column name VendorProductNo */
    public static final String COLUMNNAME_VendorProductNo = "VendorProductNo";

	/** Set Partner Product Key.
	  * Product Key of the Business Partner
	  */
	public void setVendorProductNo (String VendorProductNo);

	/** Get Partner Product Key.
	  * Product Key of the Business Partner
	  */
	public String getVendorProductNo();
}
