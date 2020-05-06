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

/** Generated Interface for T_InvoiceGL
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_T_InvoiceGL 
{

    /** TableName=T_InvoiceGL */
    public static final String Table_Name = "T_InvoiceGL";

    /** AD_Table_ID=803 */
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

    /** Column name AmtAcctBalance */
    public static final String COLUMNNAME_AmtAcctBalance = "AmtAcctBalance";

	/** Set Accounted Balance.
	  * Accounted Balance Amount
	  */
	public void setAmtAcctBalance (BigDecimal AmtAcctBalance);

	/** Get Accounted Balance.
	  * Accounted Balance Amount
	  */
	public BigDecimal getAmtAcctBalance();

    /** Column name AmtRevalCr */
    public static final String COLUMNNAME_AmtRevalCr = "AmtRevalCr";

	/** Set Revaluated Amount Cr.
	  * Revaluated Cr Amount
	  */
	public void setAmtRevalCr (BigDecimal AmtRevalCr);

	/** Get Revaluated Amount Cr.
	  * Revaluated Cr Amount
	  */
	public BigDecimal getAmtRevalCr();

    /** Column name AmtRevalCrDiff */
    public static final String COLUMNNAME_AmtRevalCrDiff = "AmtRevalCrDiff";

	/** Set Revaluated Difference Cr.
	  * Revaluated Cr Amount Difference
	  */
	public void setAmtRevalCrDiff (BigDecimal AmtRevalCrDiff);

	/** Get Revaluated Difference Cr.
	  * Revaluated Cr Amount Difference
	  */
	public BigDecimal getAmtRevalCrDiff();

    /** Column name AmtRevalDr */
    public static final String COLUMNNAME_AmtRevalDr = "AmtRevalDr";

	/** Set Revaluated Amount Dr.
	  * Revaluated Dr Amount
	  */
	public void setAmtRevalDr (BigDecimal AmtRevalDr);

	/** Get Revaluated Amount Dr.
	  * Revaluated Dr Amount
	  */
	public BigDecimal getAmtRevalDr();

    /** Column name AmtRevalDrDiff */
    public static final String COLUMNNAME_AmtRevalDrDiff = "AmtRevalDrDiff";

	/** Set Revaluated Difference Dr.
	  * Revaluated Dr Amount Difference
	  */
	public void setAmtRevalDrDiff (BigDecimal AmtRevalDrDiff);

	/** Get Revaluated Difference Dr.
	  * Revaluated Dr Amount Difference
	  */
	public BigDecimal getAmtRevalDrDiff();

    /** Column name AmtSourceBalance */
    public static final String COLUMNNAME_AmtSourceBalance = "AmtSourceBalance";

	/** Set Source Balance.
	  * Source Balance Amount
	  */
	public void setAmtSourceBalance (BigDecimal AmtSourceBalance);

	/** Get Source Balance.
	  * Source Balance Amount
	  */
	public BigDecimal getAmtSourceBalance();

    /** Column name APAR */
    public static final String COLUMNNAME_APAR = "APAR";

	/** Set AP - AR.
	  * Include Receivables and/or Payables transactions
	  */
	public void setAPAR (String APAR);

	/** Get AP - AR.
	  * Include Receivables and/or Payables transactions
	  */
	public String getAPAR();

    /** Column name C_ConversionTypeReval_ID */
    public static final String COLUMNNAME_C_ConversionTypeReval_ID = "C_ConversionTypeReval_ID";

	/** Set Revaluation Conversion Type.
	  * Revaluation Currency Conversion Type
	  */
	public void setC_ConversionTypeReval_ID (int C_ConversionTypeReval_ID);

	/** Get Revaluation Conversion Type.
	  * Revaluation Currency Conversion Type
	  */
	public int getC_ConversionTypeReval_ID();

	public I_C_ConversionType getC_ConversionTypeReval() throws RuntimeException;

    /** Column name C_DocTypeReval_ID */
    public static final String COLUMNNAME_C_DocTypeReval_ID = "C_DocTypeReval_ID";

	/** Set Revaluation Document Type.
	  * Document Type for Revaluation Journal
	  */
	public void setC_DocTypeReval_ID (int C_DocTypeReval_ID);

	/** Get Revaluation Document Type.
	  * Document Type for Revaluation Journal
	  */
	public int getC_DocTypeReval_ID();

	public I_C_DocType getC_DocTypeReval() throws RuntimeException;

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

    /** Column name DateReval */
    public static final String COLUMNNAME_DateReval = "DateReval";

	/** Set Revaluation Date.
	  * Date of Revaluation
	  */
	public void setDateReval (Timestamp DateReval);

	/** Get Revaluation Date.
	  * Date of Revaluation
	  */
	public Timestamp getDateReval();

    /** Column name Fact_Acct_ID */
    public static final String COLUMNNAME_Fact_Acct_ID = "Fact_Acct_ID";

	/** Set Accounting Fact	  */
	public void setFact_Acct_ID (int Fact_Acct_ID);

	/** Get Accounting Fact	  */
	public int getFact_Acct_ID();

    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";

	/** Set Grand Total.
	  * Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal);

	/** Get Grand Total.
	  * Total amount of document
	  */
	public BigDecimal getGrandTotal();

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

    /** Column name IsAllCurrencies */
    public static final String COLUMNNAME_IsAllCurrencies = "IsAllCurrencies";

	/** Set Include All Currencies.
	  * Report not just foreign currency Invoices
	  */
	public void setIsAllCurrencies (boolean IsAllCurrencies);

	/** Get Include All Currencies.
	  * Report not just foreign currency Invoices
	  */
	public boolean isAllCurrencies();

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

    /** Column name Percent */
    public static final String COLUMNNAME_Percent = "Percent";

	/** Set Percent.
	  * Percentage
	  */
	public void setPercent (BigDecimal Percent);

	/** Get Percent.
	  * Percentage
	  */
	public BigDecimal getPercent();

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
