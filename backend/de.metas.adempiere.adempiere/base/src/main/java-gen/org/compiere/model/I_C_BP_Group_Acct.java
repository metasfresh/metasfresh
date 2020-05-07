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

/** Generated Interface for C_BP_Group_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_BP_Group_Acct 
{

    /** TableName=C_BP_Group_Acct */
    public static final String Table_Name = "C_BP_Group_Acct";

    /** AD_Table_ID=395 */
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

    /** Column name C_BP_Group_ID */
    public static final String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/** Set Business Partner Group.
	  * Business Partner Group
	  */
	public void setC_BP_Group_ID (int C_BP_Group_ID);

	/** Get Business Partner Group.
	  * Business Partner Group
	  */
	public int getC_BP_Group_ID();

	public I_C_BP_Group getC_BP_Group() throws RuntimeException;

    /** Column name C_Prepayment_Acct */
    public static final String COLUMNNAME_C_Prepayment_Acct = "C_Prepayment_Acct";

	/** Set Customer Prepayment.
	  * Account for customer prepayments
	  */
	public void setC_Prepayment_Acct (int C_Prepayment_Acct);

	/** Get Customer Prepayment.
	  * Account for customer prepayments
	  */
	public int getC_Prepayment_Acct();

	public I_C_ValidCombination getC_Prepayment_A() throws RuntimeException;

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

    /** Column name C_Receivable_Acct */
    public static final String COLUMNNAME_C_Receivable_Acct = "C_Receivable_Acct";

	/** Set Customer Receivables.
	  * Account for Customer Receivables
	  */
	public void setC_Receivable_Acct (int C_Receivable_Acct);

	/** Get Customer Receivables.
	  * Account for Customer Receivables
	  */
	public int getC_Receivable_Acct();

	public I_C_ValidCombination getC_Receivable_A() throws RuntimeException;

    /** Column name C_Receivable_Services_Acct */
    public static final String COLUMNNAME_C_Receivable_Services_Acct = "C_Receivable_Services_Acct";

	/** Set Receivable Services.
	  * Customer Accounts Receivables Services Account
	  */
	public void setC_Receivable_Services_Acct (int C_Receivable_Services_Acct);

	/** Get Receivable Services.
	  * Customer Accounts Receivables Services Account
	  */
	public int getC_Receivable_Services_Acct();

	public I_C_ValidCombination getC_Receivable_Services_A() throws RuntimeException;

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

    /** Column name NotInvoicedReceipts_Acct */
    public static final String COLUMNNAME_NotInvoicedReceipts_Acct = "NotInvoicedReceipts_Acct";

	/** Set Not-invoiced Receipts.
	  * Account for not-invoiced Material Receipts
	  */
	public void setNotInvoicedReceipts_Acct (int NotInvoicedReceipts_Acct);

	/** Get Not-invoiced Receipts.
	  * Account for not-invoiced Material Receipts
	  */
	public int getNotInvoicedReceipts_Acct();

	public I_C_ValidCombination getNotInvoicedReceipts_A() throws RuntimeException;

    /** Column name NotInvoicedReceivables_Acct */
    public static final String COLUMNNAME_NotInvoicedReceivables_Acct = "NotInvoicedReceivables_Acct";

	/** Set Not-invoiced Receivables.
	  * Account for not invoiced Receivables
	  */
	public void setNotInvoicedReceivables_Acct (int NotInvoicedReceivables_Acct);

	/** Get Not-invoiced Receivables.
	  * Account for not invoiced Receivables
	  */
	public int getNotInvoicedReceivables_Acct();

	public I_C_ValidCombination getNotInvoicedReceivables_A() throws RuntimeException;

    /** Column name NotInvoicedRevenue_Acct */
    public static final String COLUMNNAME_NotInvoicedRevenue_Acct = "NotInvoicedRevenue_Acct";

	/** Set Not-invoiced Revenue.
	  * Account for not invoiced Revenue
	  */
	public void setNotInvoicedRevenue_Acct (int NotInvoicedRevenue_Acct);

	/** Get Not-invoiced Revenue.
	  * Account for not invoiced Revenue
	  */
	public int getNotInvoicedRevenue_Acct();

	public I_C_ValidCombination getNotInvoicedRevenue_A() throws RuntimeException;

    /** Column name PayDiscount_Exp_Acct */
    public static final String COLUMNNAME_PayDiscount_Exp_Acct = "PayDiscount_Exp_Acct";

	/** Set Payment Discount Expense.
	  * Payment Discount Expense Account
	  */
	public void setPayDiscount_Exp_Acct (int PayDiscount_Exp_Acct);

	/** Get Payment Discount Expense.
	  * Payment Discount Expense Account
	  */
	public int getPayDiscount_Exp_Acct();

	public I_C_ValidCombination getPayDiscount_Exp_A() throws RuntimeException;

    /** Column name PayDiscount_Rev_Acct */
    public static final String COLUMNNAME_PayDiscount_Rev_Acct = "PayDiscount_Rev_Acct";

	/** Set Payment Discount Revenue.
	  * Payment Discount Revenue Account
	  */
	public void setPayDiscount_Rev_Acct (int PayDiscount_Rev_Acct);

	/** Get Payment Discount Revenue.
	  * Payment Discount Revenue Account
	  */
	public int getPayDiscount_Rev_Acct();

	public I_C_ValidCombination getPayDiscount_Rev_A() throws RuntimeException;

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

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

    /** Column name V_Liability_Acct */
    public static final String COLUMNNAME_V_Liability_Acct = "V_Liability_Acct";

	/** Set Vendor Liability.
	  * Account for Vendor Liability
	  */
	public void setV_Liability_Acct (int V_Liability_Acct);

	/** Get Vendor Liability.
	  * Account for Vendor Liability
	  */
	public int getV_Liability_Acct();

	public I_C_ValidCombination getV_Liability_A() throws RuntimeException;

    /** Column name V_Liability_Services_Acct */
    public static final String COLUMNNAME_V_Liability_Services_Acct = "V_Liability_Services_Acct";

	/** Set Vendor Service Liability.
	  * Account for Vender Service Liability
	  */
	public void setV_Liability_Services_Acct (int V_Liability_Services_Acct);

	/** Get Vendor Service Liability.
	  * Account for Vender Service Liability
	  */
	public int getV_Liability_Services_Acct();

	public I_C_ValidCombination getV_Liability_Services_A() throws RuntimeException;

    /** Column name V_Prepayment_Acct */
    public static final String COLUMNNAME_V_Prepayment_Acct = "V_Prepayment_Acct";

	/** Set Vendor Prepayment.
	  * Account for Vendor Prepayments
	  */
	public void setV_Prepayment_Acct (int V_Prepayment_Acct);

	/** Get Vendor Prepayment.
	  * Account for Vendor Prepayments
	  */
	public int getV_Prepayment_Acct();

	public I_C_ValidCombination getV_Prepayment_A() throws RuntimeException;

    /** Column name WriteOff_Acct */
    public static final String COLUMNNAME_WriteOff_Acct = "WriteOff_Acct";

	/** Set Write-off.
	  * Account for Receivables write-off
	  */
	public void setWriteOff_Acct (int WriteOff_Acct);

	/** Get Write-off.
	  * Account for Receivables write-off
	  */
	public int getWriteOff_Acct();

	public I_C_ValidCombination getWriteOff_A() throws RuntimeException;
}
