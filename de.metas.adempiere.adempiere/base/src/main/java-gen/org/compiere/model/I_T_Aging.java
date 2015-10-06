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

/** Generated Interface for T_Aging
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_T_Aging 
{

    /** TableName=T_Aging */
    public static final String Table_Name = "T_Aging";

    /** AD_Table_ID=631 */
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

    /** Column name AD_PInstance_ID */
    public static final String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/** Set Process Instance.
	  * Instance of the process
	  */
	public void setAD_PInstance_ID (int AD_PInstance_ID);

	/** Get Process Instance.
	  * Instance of the process
	  */
	public int getAD_PInstance_ID();

	public I_AD_PInstance getAD_PInstance() throws RuntimeException;

    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/** Set Activity.
	  * Business Activity
	  */
	public void setC_Activity_ID (int C_Activity_ID);

	/** Get Activity.
	  * Business Activity
	  */
	public int getC_Activity_ID();

	public I_C_Activity getC_Activity() throws RuntimeException;

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

    /** Column name C_Campaign_ID */
    public static final String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/** Set Campaign.
	  * Marketing Campaign
	  */
	public void setC_Campaign_ID (int C_Campaign_ID);

	/** Get Campaign.
	  * Marketing Campaign
	  */
	public int getC_Campaign_ID();

	public I_C_Campaign getC_Campaign() throws RuntimeException;

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

    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/** Set Invoice.
	  * Invoice Identifier
	  */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/** Get Invoice.
	  * Invoice Identifier
	  */
	public int getC_Invoice_ID();

	public I_C_Invoice getC_Invoice() throws RuntimeException;

    /** Column name C_InvoicePaySchedule_ID */
    public static final String COLUMNNAME_C_InvoicePaySchedule_ID = "C_InvoicePaySchedule_ID";

	/** Set Invoice Payment Schedule.
	  * Invoice Payment Schedule
	  */
	public void setC_InvoicePaySchedule_ID (int C_InvoicePaySchedule_ID);

	/** Get Invoice Payment Schedule.
	  * Invoice Payment Schedule
	  */
	public int getC_InvoicePaySchedule_ID();

	public I_C_InvoicePaySchedule getC_InvoicePaySchedule() throws RuntimeException;

    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/** Set Project.
	  * Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID);

	/** Get Project.
	  * Financial Project
	  */
	public int getC_Project_ID();

	public I_C_Project getC_Project() throws RuntimeException;

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

    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/** Set Account Date.
	  * Accounting Date
	  */
	public void setDateAcct (boolean DateAcct);

	/** Get Account Date.
	  * Accounting Date
	  */
	public boolean isDateAcct();

    /** Column name DaysDue */
    public static final String COLUMNNAME_DaysDue = "DaysDue";

	/** Set Days due.
	  * Number of days due (negative: due in number of days)
	  */
	public void setDaysDue (int DaysDue);

	/** Get Days due.
	  * Number of days due (negative: due in number of days)
	  */
	public int getDaysDue();

    /** Column name Due0 */
    public static final String COLUMNNAME_Due0 = "Due0";

	/** Set Due Today	  */
	public void setDue0 (BigDecimal Due0);

	/** Get Due Today	  */
	public BigDecimal getDue0();

    /** Column name Due0_30 */
    public static final String COLUMNNAME_Due0_30 = "Due0_30";

	/** Set Due Today-30	  */
	public void setDue0_30 (BigDecimal Due0_30);

	/** Get Due Today-30	  */
	public BigDecimal getDue0_30();

    /** Column name Due0_7 */
    public static final String COLUMNNAME_Due0_7 = "Due0_7";

	/** Set Due Today-7	  */
	public void setDue0_7 (BigDecimal Due0_7);

	/** Get Due Today-7	  */
	public BigDecimal getDue0_7();

    /** Column name Due1_7 */
    public static final String COLUMNNAME_Due1_7 = "Due1_7";

	/** Set Due 1-7	  */
	public void setDue1_7 (BigDecimal Due1_7);

	/** Get Due 1-7	  */
	public BigDecimal getDue1_7();

    /** Column name Due31_60 */
    public static final String COLUMNNAME_Due31_60 = "Due31_60";

	/** Set Due 31-60	  */
	public void setDue31_60 (BigDecimal Due31_60);

	/** Get Due 31-60	  */
	public BigDecimal getDue31_60();

    /** Column name Due31_Plus */
    public static final String COLUMNNAME_Due31_Plus = "Due31_Plus";

	/** Set Due > 31	  */
	public void setDue31_Plus (BigDecimal Due31_Plus);

	/** Get Due > 31	  */
	public BigDecimal getDue31_Plus();

    /** Column name Due61_90 */
    public static final String COLUMNNAME_Due61_90 = "Due61_90";

	/** Set Due 61-90	  */
	public void setDue61_90 (BigDecimal Due61_90);

	/** Get Due 61-90	  */
	public BigDecimal getDue61_90();

    /** Column name Due61_Plus */
    public static final String COLUMNNAME_Due61_Plus = "Due61_Plus";

	/** Set Due > 61	  */
	public void setDue61_Plus (BigDecimal Due61_Plus);

	/** Get Due > 61	  */
	public BigDecimal getDue61_Plus();

    /** Column name Due8_30 */
    public static final String COLUMNNAME_Due8_30 = "Due8_30";

	/** Set Due 8-30	  */
	public void setDue8_30 (BigDecimal Due8_30);

	/** Get Due 8-30	  */
	public BigDecimal getDue8_30();

    /** Column name Due91_Plus */
    public static final String COLUMNNAME_Due91_Plus = "Due91_Plus";

	/** Set Due > 91	  */
	public void setDue91_Plus (BigDecimal Due91_Plus);

	/** Get Due > 91	  */
	public BigDecimal getDue91_Plus();

    /** Column name DueAmt */
    public static final String COLUMNNAME_DueAmt = "DueAmt";

	/** Set Amount due.
	  * Amount of the payment due
	  */
	public void setDueAmt (BigDecimal DueAmt);

	/** Get Amount due.
	  * Amount of the payment due
	  */
	public BigDecimal getDueAmt();

    /** Column name DueDate */
    public static final String COLUMNNAME_DueDate = "DueDate";

	/** Set Due Date.
	  * Date when the payment is due
	  */
	public void setDueDate (Timestamp DueDate);

	/** Get Due Date.
	  * Date when the payment is due
	  */
	public Timestamp getDueDate();

    /** Column name InvoicedAmt */
    public static final String COLUMNNAME_InvoicedAmt = "InvoicedAmt";

	/** Set Invoiced Amount.
	  * The amount invoiced
	  */
	public void setInvoicedAmt (BigDecimal InvoicedAmt);

	/** Get Invoiced Amount.
	  * The amount invoiced
	  */
	public BigDecimal getInvoicedAmt();

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

    /** Column name IsListInvoices */
    public static final String COLUMNNAME_IsListInvoices = "IsListInvoices";

	/** Set List Invoices.
	  * Include List of Invoices
	  */
	public void setIsListInvoices (boolean IsListInvoices);

	/** Get List Invoices.
	  * Include List of Invoices
	  */
	public boolean isListInvoices();

    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/** Set Sales Transaction.
	  * This is a Sales Transaction
	  */
	public void setIsSOTrx (boolean IsSOTrx);

	/** Get Sales Transaction.
	  * This is a Sales Transaction
	  */
	public boolean isSOTrx();

    /** Column name OpenAmt */
    public static final String COLUMNNAME_OpenAmt = "OpenAmt";

	/** Set Open Amount.
	  * Open item amount
	  */
	public void setOpenAmt (BigDecimal OpenAmt);

	/** Get Open Amount.
	  * Open item amount
	  */
	public BigDecimal getOpenAmt();

    /** Column name PastDue1_30 */
    public static final String COLUMNNAME_PastDue1_30 = "PastDue1_30";

	/** Set Past Due 1-30	  */
	public void setPastDue1_30 (BigDecimal PastDue1_30);

	/** Get Past Due 1-30	  */
	public BigDecimal getPastDue1_30();

    /** Column name PastDue1_7 */
    public static final String COLUMNNAME_PastDue1_7 = "PastDue1_7";

	/** Set Past Due 1-7	  */
	public void setPastDue1_7 (BigDecimal PastDue1_7);

	/** Get Past Due 1-7	  */
	public BigDecimal getPastDue1_7();

    /** Column name PastDue31_60 */
    public static final String COLUMNNAME_PastDue31_60 = "PastDue31_60";

	/** Set Past Due 31-60	  */
	public void setPastDue31_60 (BigDecimal PastDue31_60);

	/** Get Past Due 31-60	  */
	public BigDecimal getPastDue31_60();

    /** Column name PastDue31_Plus */
    public static final String COLUMNNAME_PastDue31_Plus = "PastDue31_Plus";

	/** Set Past Due > 31	  */
	public void setPastDue31_Plus (BigDecimal PastDue31_Plus);

	/** Get Past Due > 31	  */
	public BigDecimal getPastDue31_Plus();

    /** Column name PastDue61_90 */
    public static final String COLUMNNAME_PastDue61_90 = "PastDue61_90";

	/** Set Past Due 61-90	  */
	public void setPastDue61_90 (BigDecimal PastDue61_90);

	/** Get Past Due 61-90	  */
	public BigDecimal getPastDue61_90();

    /** Column name PastDue61_Plus */
    public static final String COLUMNNAME_PastDue61_Plus = "PastDue61_Plus";

	/** Set Past Due > 61	  */
	public void setPastDue61_Plus (BigDecimal PastDue61_Plus);

	/** Get Past Due > 61	  */
	public BigDecimal getPastDue61_Plus();

    /** Column name PastDue8_30 */
    public static final String COLUMNNAME_PastDue8_30 = "PastDue8_30";

	/** Set Past Due 8-30	  */
	public void setPastDue8_30 (BigDecimal PastDue8_30);

	/** Get Past Due 8-30	  */
	public BigDecimal getPastDue8_30();

    /** Column name PastDue91_Plus */
    public static final String COLUMNNAME_PastDue91_Plus = "PastDue91_Plus";

	/** Set Past Due > 91	  */
	public void setPastDue91_Plus (BigDecimal PastDue91_Plus);

	/** Get Past Due > 91	  */
	public BigDecimal getPastDue91_Plus();

    /** Column name PastDueAmt */
    public static final String COLUMNNAME_PastDueAmt = "PastDueAmt";

	/** Set Past Due	  */
	public void setPastDueAmt (BigDecimal PastDueAmt);

	/** Get Past Due	  */
	public BigDecimal getPastDueAmt();

    /** Column name StatementDate */
    public static final String COLUMNNAME_StatementDate = "StatementDate";

	/** Set Statement date.
	  * Date of the statement
	  */
	public void setStatementDate (Timestamp StatementDate);

	/** Get Statement date.
	  * Date of the statement
	  */
	public Timestamp getStatementDate();

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
