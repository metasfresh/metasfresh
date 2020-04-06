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

/** Generated Interface for C_CashBook_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_CashBook_Acct 
{

    /** TableName=C_CashBook_Acct */
    public static final String Table_Name = "C_CashBook_Acct";

    /** AD_Table_ID=409 */
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

    /** Column name CB_Asset_Acct */
    public static final String COLUMNNAME_CB_Asset_Acct = "CB_Asset_Acct";

	/** Set Cash Book Asset.
	  * Cash Book Asset Account
	  */
	public void setCB_Asset_Acct (int CB_Asset_Acct);

	/** Get Cash Book Asset.
	  * Cash Book Asset Account
	  */
	public int getCB_Asset_Acct();

	public I_C_ValidCombination getCB_Asset_A() throws RuntimeException;

    /** Column name CB_CashTransfer_Acct */
    public static final String COLUMNNAME_CB_CashTransfer_Acct = "CB_CashTransfer_Acct";

	/** Set Cash Transfer.
	  * Cash Transfer Clearing Account
	  */
	public void setCB_CashTransfer_Acct (int CB_CashTransfer_Acct);

	/** Get Cash Transfer.
	  * Cash Transfer Clearing Account
	  */
	public int getCB_CashTransfer_Acct();

	public I_C_ValidCombination getCB_CashTransfer_A() throws RuntimeException;

    /** Column name CB_Differences_Acct */
    public static final String COLUMNNAME_CB_Differences_Acct = "CB_Differences_Acct";

	/** Set Cash Book Differences.
	  * Cash Book Differences Account
	  */
	public void setCB_Differences_Acct (int CB_Differences_Acct);

	/** Get Cash Book Differences.
	  * Cash Book Differences Account
	  */
	public int getCB_Differences_Acct();

	public I_C_ValidCombination getCB_Differences_A() throws RuntimeException;

    /** Column name CB_Expense_Acct */
    public static final String COLUMNNAME_CB_Expense_Acct = "CB_Expense_Acct";

	/** Set Cash Book Expense.
	  * Cash Book Expense Account
	  */
	public void setCB_Expense_Acct (int CB_Expense_Acct);

	/** Get Cash Book Expense.
	  * Cash Book Expense Account
	  */
	public int getCB_Expense_Acct();

	public I_C_ValidCombination getCB_Expense_A() throws RuntimeException;

    /** Column name CB_Receipt_Acct */
    public static final String COLUMNNAME_CB_Receipt_Acct = "CB_Receipt_Acct";

	/** Set Cash Book Receipt.
	  * Cash Book Receipts Account
	  */
	public void setCB_Receipt_Acct (int CB_Receipt_Acct);

	/** Get Cash Book Receipt.
	  * Cash Book Receipts Account
	  */
	public int getCB_Receipt_Acct();

	public I_C_ValidCombination getCB_Receipt_A() throws RuntimeException;

    /** Column name C_CashBook_ID */
    public static final String COLUMNNAME_C_CashBook_ID = "C_CashBook_ID";

	/** Set Cash Book.
	  * Cash Book for recording petty cash transactions
	  */
	public void setC_CashBook_ID (int C_CashBook_ID);

	/** Get Cash Book.
	  * Cash Book for recording petty cash transactions
	  */
	public int getC_CashBook_ID();

	public I_C_CashBook getC_CashBook() throws RuntimeException;

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
