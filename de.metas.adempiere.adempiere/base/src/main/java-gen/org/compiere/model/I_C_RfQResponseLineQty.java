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

/** Generated Interface for C_RfQResponseLineQty
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_RfQResponseLineQty 
{

    /** TableName=C_RfQResponseLineQty */
    public static final String Table_Name = "C_RfQResponseLineQty";

    /** AD_Table_ID=672 */
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

	public I_C_RfQLineQty getC_RfQLineQty() throws RuntimeException;

    /** Column name C_RfQResponseLine_ID */
    public static final String COLUMNNAME_C_RfQResponseLine_ID = "C_RfQResponseLine_ID";

	/** Set RfQ Response Line.
	  * Request for Quotation Response Line
	  */
	public void setC_RfQResponseLine_ID (int C_RfQResponseLine_ID);

	/** Get RfQ Response Line.
	  * Request for Quotation Response Line
	  */
	public int getC_RfQResponseLine_ID();

	public I_C_RfQResponseLine getC_RfQResponseLine() throws RuntimeException;

    /** Column name C_RfQResponseLineQty_ID */
    public static final String COLUMNNAME_C_RfQResponseLineQty_ID = "C_RfQResponseLineQty_ID";

	/** Set RfQ Response Line Qty.
	  * Request for Quotation Response Line Quantity
	  */
	public void setC_RfQResponseLineQty_ID (int C_RfQResponseLineQty_ID);

	/** Get RfQ Response Line Qty.
	  * Request for Quotation Response Line Quantity
	  */
	public int getC_RfQResponseLineQty_ID();

    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

	/** Set Discount %.
	  * Discount in percent
	  */
	public void setDiscount (BigDecimal Discount);

	/** Get Discount %.
	  * Discount in percent
	  */
	public BigDecimal getDiscount();

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

    /** Column name Price */
    public static final String COLUMNNAME_Price = "Price";

	/** Set Price.
	  * Price
	  */
	public void setPrice (BigDecimal Price);

	/** Get Price.
	  * Price
	  */
	public BigDecimal getPrice();

    /** Column name Ranking */
    public static final String COLUMNNAME_Ranking = "Ranking";

	/** Set Ranking.
	  * Relative Rank Number
	  */
	public void setRanking (int Ranking);

	/** Get Ranking.
	  * Relative Rank Number
	  */
	public int getRanking();

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
