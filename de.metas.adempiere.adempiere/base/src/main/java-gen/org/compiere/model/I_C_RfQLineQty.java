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

/** Generated Interface for C_RfQLineQty
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_RfQLineQty 
{

    /** TableName=C_RfQLineQty */
    public static final String Table_Name = "C_RfQLineQty";

    /** AD_Table_ID=675 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

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

    /** Column name BenchmarkPrice */
    public static final String COLUMNNAME_BenchmarkPrice = "BenchmarkPrice";

	/** Set Benchmark Price.
	  * Price to compare responses to
	  */
	public void setBenchmarkPrice (BigDecimal BenchmarkPrice);

	/** Get Benchmark Price.
	  * Price to compare responses to
	  */
	public BigDecimal getBenchmarkPrice();

    /** Column name BestResponseAmt */
    public static final String COLUMNNAME_BestResponseAmt = "BestResponseAmt";

	/** Set Best Response Amount.
	  * Best Response Amount
	  */
	public void setBestResponseAmt (BigDecimal BestResponseAmt);

	/** Get Best Response Amount.
	  * Best Response Amount
	  */
	public BigDecimal getBestResponseAmt();

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

    /** Column name C_RfQLine_ID */
    public static final String COLUMNNAME_C_RfQLine_ID = "C_RfQLine_ID";

	/** Set RfQ Line.
	  * Request for Quotation Line
	  */
	public void setC_RfQLine_ID (int C_RfQLine_ID);

	/** Get RfQ Line.
	  * Request for Quotation Line
	  */
	public int getC_RfQLine_ID();

	public I_C_RfQLine getC_RfQLine() throws RuntimeException;

    /** Column name C_RfQLineQty_ID */
    public static final String COLUMNNAME_C_RfQLineQty_ID = "C_RfQLineQty_ID";

	/** Set RfQ Line Quantity.
	  * Request for Quotation Line Quantity
	  */
	public void setC_RfQLineQty_ID (int C_RfQLineQty_ID);

	/** Get RfQ Line Quantity.
	  * Request for Quotation Line Quantity
	  */
	public int getC_RfQLineQty_ID();

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

    /** Column name IsOfferQty */
    public static final String COLUMNNAME_IsOfferQty = "IsOfferQty";

	/** Set Offer Quantity.
	  * This quantity is used in the Offer to the Customer
	  */
	public void setIsOfferQty (boolean IsOfferQty);

	/** Get Offer Quantity.
	  * This quantity is used in the Offer to the Customer
	  */
	public boolean isOfferQty();

    /** Column name IsPurchaseQty */
    public static final String COLUMNNAME_IsPurchaseQty = "IsPurchaseQty";

	/** Set Purchase Quantity.
	  * This quantity is used in the Purchase Order to the Supplier
	  */
	public void setIsPurchaseQty (boolean IsPurchaseQty);

	/** Get Purchase Quantity.
	  * This quantity is used in the Purchase Order to the Supplier
	  */
	public boolean isPurchaseQty();

    /** Column name IsRfQQty */
    public static final String COLUMNNAME_IsRfQQty = "IsRfQQty";

	/** Set RfQ Quantity.
	  * The quantity is used when generating RfQ Responses
	  */
	public void setIsRfQQty (boolean IsRfQQty);

	/** Get RfQ Quantity.
	  * The quantity is used when generating RfQ Responses
	  */
	public boolean isRfQQty();

    /** Column name Margin */
    public static final String COLUMNNAME_Margin = "Margin";

	/** Set Margin %.
	  * Margin for a product as a percentage
	  */
	public void setMargin (BigDecimal Margin);

	/** Get Margin %.
	  * Margin for a product as a percentage
	  */
	public BigDecimal getMargin();

    /** Column name OfferAmt */
    public static final String COLUMNNAME_OfferAmt = "OfferAmt";

	/** Set Offer Amount.
	  * Amount of the Offer
	  */
	public void setOfferAmt (BigDecimal OfferAmt);

	/** Get Offer Amount.
	  * Amount of the Offer
	  */
	public BigDecimal getOfferAmt();

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Quantity.
	  * Quantity
	  */
	public void setQty (BigDecimal Qty);

	/** Get Quantity.
	  * Quantity
	  */
	public BigDecimal getQty();

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
}
