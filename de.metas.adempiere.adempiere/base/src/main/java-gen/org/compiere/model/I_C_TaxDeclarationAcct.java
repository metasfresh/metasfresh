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


/** Generated Interface for C_TaxDeclarationAcct
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_TaxDeclarationAcct 
{

    /** TableName=C_TaxDeclarationAcct */
    public static final String Table_Name = "C_TaxDeclarationAcct";

    /** AD_Table_ID=820 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Konto.
	 * Account used
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setAccount_ID (int Account_ID);

	/**
	 * Get Konto.
	 * Account used
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getAccount_ID();

	public org.compiere.model.I_C_ElementValue getAccount();

	public void setAccount(org.compiere.model.I_C_ElementValue Account);

    /** Column definition for Account_ID */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_C_ElementValue> COLUMN_Account_ID = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_C_ElementValue>(I_C_TaxDeclarationAcct.class, "Account_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name Account_ID */
    public static final String COLUMNNAME_Account_ID = "Account_ID";

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_AD_Client>(I_C_TaxDeclarationAcct.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_AD_Org>(I_C_TaxDeclarationAcct.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Haben.
	 * Ausgewiesener Forderungsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setAmtAcctCr (java.math.BigDecimal AmtAcctCr);

	/**
	 * Get Haben.
	 * Ausgewiesener Forderungsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.math.BigDecimal getAmtAcctCr();

    /** Column definition for AmtAcctCr */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object> COLUMN_AmtAcctCr = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object>(I_C_TaxDeclarationAcct.class, "AmtAcctCr", null);
    /** Column name AmtAcctCr */
    public static final String COLUMNNAME_AmtAcctCr = "AmtAcctCr";

	/**
	 * Set Soll.
	 * Ausgewiesener Verbindlichkeitsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setAmtAcctDr (java.math.BigDecimal AmtAcctDr);

	/**
	 * Get Soll.
	 * Ausgewiesener Verbindlichkeitsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.math.BigDecimal getAmtAcctDr();

    /** Column definition for AmtAcctDr */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object> COLUMN_AmtAcctDr = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object>(I_C_TaxDeclarationAcct.class, "AmtAcctDr", null);
    /** Column name AmtAcctDr */
    public static final String COLUMNNAME_AmtAcctDr = "AmtAcctDr";

	/**
	 * Set Ausgangsforderung.
	 * Source Credit Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setAmtSourceCr (java.math.BigDecimal AmtSourceCr);

	/**
	 * Get Ausgangsforderung.
	 * Source Credit Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.math.BigDecimal getAmtSourceCr();

    /** Column definition for AmtSourceCr */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object> COLUMN_AmtSourceCr = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object>(I_C_TaxDeclarationAcct.class, "AmtSourceCr", null);
    /** Column name AmtSourceCr */
    public static final String COLUMNNAME_AmtSourceCr = "AmtSourceCr";

	/**
	 * Set Ausgangsverbindlichkeit.
	 * Source Debit Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setAmtSourceDr (java.math.BigDecimal AmtSourceDr);

	/**
	 * Get Ausgangsverbindlichkeit.
	 * Source Debit Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.math.BigDecimal getAmtSourceDr();

    /** Column definition for AmtSourceDr */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object> COLUMN_AmtSourceDr = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object>(I_C_TaxDeclarationAcct.class, "AmtSourceDr", null);
    /** Column name AmtSourceDr */
    public static final String COLUMNNAME_AmtSourceDr = "AmtSourceDr";

	/**
	 * Set Buchführungs-Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Buchführungs-Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_AcctSchema_ID();

	public org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

    /** Column definition for C_AcctSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_C_AcctSchema>(I_C_TaxDeclarationAcct.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_C_BPartner>(I_C_TaxDeclarationAcct.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Währung.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_C_Currency>(I_C_TaxDeclarationAcct.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Steuer.
	 * Tax identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setC_Tax_ID (int C_Tax_ID);

	/**
	 * Get Steuer.
	 * Tax identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getC_Tax_ID();

	public org.compiere.model.I_C_Tax getC_Tax();

	public void setC_Tax(org.compiere.model.I_C_Tax C_Tax);

    /** Column definition for C_Tax_ID */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_C_Tax> COLUMN_C_Tax_ID = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_C_Tax>(I_C_TaxDeclarationAcct.class, "C_Tax_ID", org.compiere.model.I_C_Tax.class);
    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/**
	 * Set Steuererklärung.
	 * Define the declaration to the tax authorities
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_TaxDeclaration_ID (int C_TaxDeclaration_ID);

	/**
	 * Get Steuererklärung.
	 * Define the declaration to the tax authorities
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_TaxDeclaration_ID();

	public org.compiere.model.I_C_TaxDeclaration getC_TaxDeclaration();

	public void setC_TaxDeclaration(org.compiere.model.I_C_TaxDeclaration C_TaxDeclaration);

    /** Column definition for C_TaxDeclaration_ID */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_C_TaxDeclaration> COLUMN_C_TaxDeclaration_ID = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_C_TaxDeclaration>(I_C_TaxDeclarationAcct.class, "C_TaxDeclaration_ID", org.compiere.model.I_C_TaxDeclaration.class);
    /** Column name C_TaxDeclaration_ID */
    public static final String COLUMNNAME_C_TaxDeclaration_ID = "C_TaxDeclaration_ID";

	/**
	 * Set Tax Declaration Accounting.
	 * Tax Accounting Reconciliation
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_TaxDeclarationAcct_ID (int C_TaxDeclarationAcct_ID);

	/**
	 * Get Tax Declaration Accounting.
	 * Tax Accounting Reconciliation
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_TaxDeclarationAcct_ID();

    /** Column definition for C_TaxDeclarationAcct_ID */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object> COLUMN_C_TaxDeclarationAcct_ID = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object>(I_C_TaxDeclarationAcct.class, "C_TaxDeclarationAcct_ID", null);
    /** Column name C_TaxDeclarationAcct_ID */
    public static final String COLUMNNAME_C_TaxDeclarationAcct_ID = "C_TaxDeclarationAcct_ID";

	/**
	 * Set Tax Declaration Line.
	 * Tax Declaration Document Information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_TaxDeclarationLine_ID (int C_TaxDeclarationLine_ID);

	/**
	 * Get Tax Declaration Line.
	 * Tax Declaration Document Information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_TaxDeclarationLine_ID();

	public org.compiere.model.I_C_TaxDeclarationLine getC_TaxDeclarationLine();

	public void setC_TaxDeclarationLine(org.compiere.model.I_C_TaxDeclarationLine C_TaxDeclarationLine);

    /** Column definition for C_TaxDeclarationLine_ID */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_C_TaxDeclarationLine> COLUMN_C_TaxDeclarationLine_ID = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_C_TaxDeclarationLine>(I_C_TaxDeclarationAcct.class, "C_TaxDeclarationLine_ID", org.compiere.model.I_C_TaxDeclarationLine.class);
    /** Column name C_TaxDeclarationLine_ID */
    public static final String COLUMNNAME_C_TaxDeclarationLine_ID = "C_TaxDeclarationLine_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object>(I_C_TaxDeclarationAcct.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_AD_User>(I_C_TaxDeclarationAcct.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.sql.Timestamp getDateAcct();

    /** Column definition for DateAcct */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object> COLUMN_DateAcct = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object>(I_C_TaxDeclarationAcct.class, "DateAcct", null);
    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object>(I_C_TaxDeclarationAcct.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Accounting Fact.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFact_Acct_ID (int Fact_Acct_ID);

	/**
	 * Get Accounting Fact.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getFact_Acct_ID();

	public org.compiere.model.I_Fact_Acct getFact_Acct();

	public void setFact_Acct(org.compiere.model.I_Fact_Acct Fact_Acct);

    /** Column definition for Fact_Acct_ID */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_Fact_Acct> COLUMN_Fact_Acct_ID = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_Fact_Acct>(I_C_TaxDeclarationAcct.class, "Fact_Acct_ID", org.compiere.model.I_Fact_Acct.class);
    /** Column name Fact_Acct_ID */
    public static final String COLUMNNAME_Fact_Acct_ID = "Fact_Acct_ID";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object>(I_C_TaxDeclarationAcct.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object>(I_C_TaxDeclarationAcct.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, Object>(I_C_TaxDeclarationAcct.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_TaxDeclarationAcct, org.compiere.model.I_AD_User>(I_C_TaxDeclarationAcct.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
