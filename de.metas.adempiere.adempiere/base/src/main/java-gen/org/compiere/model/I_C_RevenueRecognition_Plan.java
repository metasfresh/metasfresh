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

/** Generated Interface for C_RevenueRecognition_Plan
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_RevenueRecognition_Plan 
{

    /** TableName=C_RevenueRecognition_Plan */
    public static final String Table_Name = "C_RevenueRecognition_Plan";

    /** AD_Table_ID=443 */
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

    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/** Set Accounting Schema.
	  * Rules for accounting
	  */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/** Get Accounting Schema.
	  * Rules for accounting
	  */
	public int getC_AcctSchema_ID();

	public I_C_AcctSchema getC_AcctSchema() throws RuntimeException;

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

    /** Column name C_RevenueRecognition_ID */
    public static final String COLUMNNAME_C_RevenueRecognition_ID = "C_RevenueRecognition_ID";

	/** Set Revenue Recognition.
	  * Method for recording revenue
	  */
	public void setC_RevenueRecognition_ID (int C_RevenueRecognition_ID);

	/** Get Revenue Recognition.
	  * Method for recording revenue
	  */
	public int getC_RevenueRecognition_ID();

	public I_C_RevenueRecognition getC_RevenueRecognition() throws RuntimeException;

    /** Column name C_RevenueRecognition_Plan_ID */
    public static final String COLUMNNAME_C_RevenueRecognition_Plan_ID = "C_RevenueRecognition_Plan_ID";

	/** Set Revenue Recognition Plan.
	  * Plan for recognizing or recording revenue
	  */
	public void setC_RevenueRecognition_Plan_ID (int C_RevenueRecognition_Plan_ID);

	/** Get Revenue Recognition Plan.
	  * Plan for recognizing or recording revenue
	  */
	public int getC_RevenueRecognition_Plan_ID();

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

    /** Column name P_Revenue_Acct */
    public static final String COLUMNNAME_P_Revenue_Acct = "P_Revenue_Acct";

	/** Set Product Revenue.
	  * Account for Product Revenue (Sales Account)
	  */
	public void setP_Revenue_Acct (int P_Revenue_Acct);

	/** Get Product Revenue.
	  * Account for Product Revenue (Sales Account)
	  */
	public int getP_Revenue_Acct();

	public I_C_ValidCombination getP_Revenue_A() throws RuntimeException;

    /** Column name RecognizedAmt */
    public static final String COLUMNNAME_RecognizedAmt = "RecognizedAmt";

	/** Set Recognized Amount	  */
	public void setRecognizedAmt (BigDecimal RecognizedAmt);

	/** Get Recognized Amount	  */
	public BigDecimal getRecognizedAmt();

    /** Column name TotalAmt */
    public static final String COLUMNNAME_TotalAmt = "TotalAmt";

	/** Set Total Amount.
	  * Total Amount
	  */
	public void setTotalAmt (BigDecimal TotalAmt);

	/** Get Total Amount.
	  * Total Amount
	  */
	public BigDecimal getTotalAmt();

    /** Column name UnEarnedRevenue_Acct */
    public static final String COLUMNNAME_UnEarnedRevenue_Acct = "UnEarnedRevenue_Acct";

	/** Set Unearned Revenue.
	  * Account for unearned revenue
	  */
	public void setUnEarnedRevenue_Acct (int UnEarnedRevenue_Acct);

	/** Get Unearned Revenue.
	  * Account for unearned revenue
	  */
	public int getUnEarnedRevenue_Acct();

	public I_C_ValidCombination getUnEarnedRevenue_A() throws RuntimeException;

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
