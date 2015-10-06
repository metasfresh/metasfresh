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

/** Generated Interface for C_DunningLevel
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_DunningLevel 
{

    /** TableName=C_DunningLevel */
    public static final String Table_Name = "C_DunningLevel";

    /** AD_Table_ID=331 */
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

    /** Column name C_Dunning_ID */
    public static final String COLUMNNAME_C_Dunning_ID = "C_Dunning_ID";

	/** Set Dunning.
	  * Dunning Rules for overdue invoices
	  */
	public void setC_Dunning_ID (int C_Dunning_ID);

	/** Get Dunning.
	  * Dunning Rules for overdue invoices
	  */
	public int getC_Dunning_ID();

	public I_C_Dunning getC_Dunning() throws RuntimeException;

    /** Column name C_DunningLevel_ID */
    public static final String COLUMNNAME_C_DunningLevel_ID = "C_DunningLevel_ID";

	/** Set Dunning Level	  */
	public void setC_DunningLevel_ID (int C_DunningLevel_ID);

	/** Get Dunning Level	  */
	public int getC_DunningLevel_ID();

    /** Column name ChargeFee */
    public static final String COLUMNNAME_ChargeFee = "ChargeFee";

	/** Set Charge fee.
	  * Indicates if fees will be charged for overdue invoices
	  */
	public void setChargeFee (boolean ChargeFee);

	/** Get Charge fee.
	  * Indicates if fees will be charged for overdue invoices
	  */
	public boolean isChargeFee();

    /** Column name ChargeInterest */
    public static final String COLUMNNAME_ChargeInterest = "ChargeInterest";

	/** Set Charge Interest.
	  * Indicates if interest will be charged on overdue invoices
	  */
	public void setChargeInterest (boolean ChargeInterest);

	/** Get Charge Interest.
	  * Indicates if interest will be charged on overdue invoices
	  */
	public boolean isChargeInterest();

    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/** Set Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/** Get Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID();

	public I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException;

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

    /** Column name DaysAfterDue */
    public static final String COLUMNNAME_DaysAfterDue = "DaysAfterDue";

	/** Set Days after due date.
	  * Days after due date to dun (if negative days until due)
	  */
	public void setDaysAfterDue (BigDecimal DaysAfterDue);

	/** Get Days after due date.
	  * Days after due date to dun (if negative days until due)
	  */
	public BigDecimal getDaysAfterDue();

    /** Column name DaysBetweenDunning */
    public static final String COLUMNNAME_DaysBetweenDunning = "DaysBetweenDunning";

	/** Set Days between dunning.
	  * Days between sending dunning notices
	  */
	public void setDaysBetweenDunning (int DaysBetweenDunning);

	/** Get Days between dunning.
	  * Days between sending dunning notices
	  */
	public int getDaysBetweenDunning();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name Dunning_PrintFormat_ID */
    public static final String COLUMNNAME_Dunning_PrintFormat_ID = "Dunning_PrintFormat_ID";

	/** Set Dunning Print Format.
	  * Print Format for printing Dunning Letters
	  */
	public void setDunning_PrintFormat_ID (int Dunning_PrintFormat_ID);

	/** Get Dunning Print Format.
	  * Print Format for printing Dunning Letters
	  */
	public int getDunning_PrintFormat_ID();

	public I_AD_PrintFormat getDunning_PrintFormat() throws RuntimeException;

    /** Column name FeeAmt */
    public static final String COLUMNNAME_FeeAmt = "FeeAmt";

	/** Set Fee Amount.
	  * Fee amount in invoice currency
	  */
	public void setFeeAmt (BigDecimal FeeAmt);

	/** Get Fee Amount.
	  * Fee amount in invoice currency
	  */
	public BigDecimal getFeeAmt();

    /** Column name InterestPercent */
    public static final String COLUMNNAME_InterestPercent = "InterestPercent";

	/** Set Interest in percent.
	  * Percentage interest to charge on overdue invoices
	  */
	public void setInterestPercent (BigDecimal InterestPercent);

	/** Get Interest in percent.
	  * Percentage interest to charge on overdue invoices
	  */
	public BigDecimal getInterestPercent();

    /** Column name InvoiceCollectionType */
    public static final String COLUMNNAME_InvoiceCollectionType = "InvoiceCollectionType";

	/** Set Collection Status.
	  * Invoice Collection Status
	  */
	public void setInvoiceCollectionType (String InvoiceCollectionType);

	/** Get Collection Status.
	  * Invoice Collection Status
	  */
	public String getInvoiceCollectionType();

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

    /** Column name IsSetCreditStop */
    public static final String COLUMNNAME_IsSetCreditStop = "IsSetCreditStop";

	/** Set Credit Stop.
	  * Set the business partner to credit stop
	  */
	public void setIsSetCreditStop (boolean IsSetCreditStop);

	/** Get Credit Stop.
	  * Set the business partner to credit stop
	  */
	public boolean isSetCreditStop();

    /** Column name IsSetPaymentTerm */
    public static final String COLUMNNAME_IsSetPaymentTerm = "IsSetPaymentTerm";

	/** Set Set Payment Term.
	  * Set the payment term of the Business Partner
	  */
	public void setIsSetPaymentTerm (boolean IsSetPaymentTerm);

	/** Get Set Payment Term.
	  * Set the payment term of the Business Partner
	  */
	public boolean isSetPaymentTerm();

    /** Column name IsShowAllDue */
    public static final String COLUMNNAME_IsShowAllDue = "IsShowAllDue";

	/** Set Show All Due.
	  * Show/print all due invoices
	  */
	public void setIsShowAllDue (boolean IsShowAllDue);

	/** Get Show All Due.
	  * Show/print all due invoices
	  */
	public boolean isShowAllDue();

    /** Column name IsShowNotDue */
    public static final String COLUMNNAME_IsShowNotDue = "IsShowNotDue";

	/** Set Show Not Due.
	  * Show/print all invoices which are not due (yet).
	  */
	public void setIsShowNotDue (boolean IsShowNotDue);

	/** Get Show Not Due.
	  * Show/print all invoices which are not due (yet).
	  */
	public boolean isShowNotDue();

    /** Column name IsStatement */
    public static final String COLUMNNAME_IsStatement = "IsStatement";

	/** Set Is Statement.
	  * Dunning Level is a definition of a statement
	  */
	public void setIsStatement (boolean IsStatement);

	/** Get Is Statement.
	  * Dunning Level is a definition of a statement
	  */
	public boolean isStatement();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Note */
    public static final String COLUMNNAME_Note = "Note";

	/** Set Note.
	  * Optional additional user defined information
	  */
	public void setNote (String Note);

	/** Get Note.
	  * Optional additional user defined information
	  */
	public String getNote();

    /** Column name PrintName */
    public static final String COLUMNNAME_PrintName = "PrintName";

	/** Set Print Text.
	  * The label text to be printed on a document or correspondence.
	  */
	public void setPrintName (String PrintName);

	/** Get Print Text.
	  * The label text to be printed on a document or correspondence.
	  */
	public String getPrintName();

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
