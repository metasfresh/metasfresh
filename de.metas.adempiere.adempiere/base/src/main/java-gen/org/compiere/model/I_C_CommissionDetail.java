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

/** Generated Interface for C_CommissionDetail
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_CommissionDetail 
{

    /** TableName=C_CommissionDetail */
    public static final String Table_Name = "C_CommissionDetail";

    /** AD_Table_ID=437 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

    /** Load Meta Data */

    /** Column name ActualAmt */
    public static final String COLUMNNAME_ActualAmt = "ActualAmt";

	/** Set Actual Amount.
	  * The actual amount
	  */
	public void setActualAmt (BigDecimal ActualAmt);

	/** Get Actual Amount.
	  * The actual amount
	  */
	public BigDecimal getActualAmt();

    /** Column name ActualQty */
    public static final String COLUMNNAME_ActualQty = "ActualQty";

	/** Set Actual Quantity.
	  * The actual quantity
	  */
	public void setActualQty (BigDecimal ActualQty);

	/** Get Actual Quantity.
	  * The actual quantity
	  */
	public BigDecimal getActualQty();

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

    /** Column name C_CommissionAmt_ID */
    public static final String COLUMNNAME_C_CommissionAmt_ID = "C_CommissionAmt_ID";

	/** Set Commission Amount.
	  * Generated Commission Amount 
	  */
	public void setC_CommissionAmt_ID (int C_CommissionAmt_ID);

	/** Get Commission Amount.
	  * Generated Commission Amount 
	  */
	public int getC_CommissionAmt_ID();

	public I_C_CommissionAmt getC_CommissionAmt() throws RuntimeException;

    /** Column name C_CommissionDetail_ID */
    public static final String COLUMNNAME_C_CommissionDetail_ID = "C_CommissionDetail_ID";

	/** Set Commission Detail.
	  * Supporting information for Commission Amounts
	  */
	public void setC_CommissionDetail_ID (int C_CommissionDetail_ID);

	/** Get Commission Detail.
	  * Supporting information for Commission Amounts
	  */
	public int getC_CommissionDetail_ID();

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

    /** Column name C_InvoiceLine_ID */
    public static final String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/** Set Invoice Line.
	  * Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/** Get Invoice Line.
	  * Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID();

	public I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException;

    /** Column name ConvertedAmt */
    public static final String COLUMNNAME_ConvertedAmt = "ConvertedAmt";

	/** Set Converted Amount.
	  * Converted Amount
	  */
	public void setConvertedAmt (BigDecimal ConvertedAmt);

	/** Get Converted Amount.
	  * Converted Amount
	  */
	public BigDecimal getConvertedAmt();

    /** Column name C_OrderLine_ID */
    public static final String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/** Set Sales Order Line.
	  * Sales Order Line
	  */
	public void setC_OrderLine_ID (int C_OrderLine_ID);

	/** Get Sales Order Line.
	  * Sales Order Line
	  */
	public int getC_OrderLine_ID();

	public I_C_OrderLine getC_OrderLine() throws RuntimeException;

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

    /** Column name Info */
    public static final String COLUMNNAME_Info = "Info";

	/** Set Info.
	  * Information
	  */
	public void setInfo (String Info);

	/** Get Info.
	  * Information
	  */
	public String getInfo();

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

    /** Column name Reference */
    public static final String COLUMNNAME_Reference = "Reference";

	/** Set Reference.
	  * Reference for this record
	  */
	public void setReference (String Reference);

	/** Get Reference.
	  * Reference for this record
	  */
	public String getReference();

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
