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


/** Generated Interface for GL_JournalLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_GL_JournalLine 
{

    /** TableName=GL_JournalLine */
    public static final String Table_Name = "GL_JournalLine";

    /** AD_Table_ID=226 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

	/**
	 * Set Asset-Gruppe.
	 * Group of Assets
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_Asset_Group_ID (int A_Asset_Group_ID);

	/**
	 * Get Asset-Gruppe.
	 * Group of Assets
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getA_Asset_Group_ID();

	public org.compiere.model.I_A_Asset_Group getA_Asset_Group();

	public void setA_Asset_Group(org.compiere.model.I_A_Asset_Group A_Asset_Group);

    /** Column definition for A_Asset_Group_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_A_Asset_Group> COLUMN_A_Asset_Group_ID = new org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_A_Asset_Group>(I_GL_JournalLine.class, "A_Asset_Group_ID", org.compiere.model.I_A_Asset_Group.class);
    /** Column name A_Asset_Group_ID */
    public static final String COLUMNNAME_A_Asset_Group_ID = "A_Asset_Group_ID";

	/**
	 * Set Asset.
	 * Asset used internally or by customers
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_Asset_ID (int A_Asset_ID);

	/**
	 * Get Asset.
	 * Asset used internally or by customers
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getA_Asset_ID();

	public org.compiere.model.I_A_Asset getA_Asset();

	public void setA_Asset(org.compiere.model.I_A_Asset A_Asset);

    /** Column definition for A_Asset_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_A_Asset> COLUMN_A_Asset_ID = new org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_A_Asset>(I_GL_JournalLine.class, "A_Asset_ID", org.compiere.model.I_A_Asset.class);
    /** Column name A_Asset_ID */
    public static final String COLUMNNAME_A_Asset_ID = "A_Asset_ID";

	/**
	 * Set Kombination-Haben.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAccount_CR_ID (int Account_CR_ID);

	/**
	 * Get Kombination-Haben.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAccount_CR_ID();

	public org.compiere.model.I_C_ValidCombination getAccount_CR();

	public void setAccount_CR(org.compiere.model.I_C_ValidCombination Account_CR);

    /** Column definition for Account_CR_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_ValidCombination> COLUMN_Account_CR_ID = new org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_ValidCombination>(I_GL_JournalLine.class, "Account_CR_ID", org.compiere.model.I_C_ValidCombination.class);
    /** Column name Account_CR_ID */
    public static final String COLUMNNAME_Account_CR_ID = "Account_CR_ID";

	/**
	 * Set Kombination-Soll.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAccount_DR_ID (int Account_DR_ID);

	/**
	 * Get Kombination-Soll.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAccount_DR_ID();

	public org.compiere.model.I_C_ValidCombination getAccount_DR();

	public void setAccount_DR(org.compiere.model.I_C_ValidCombination Account_DR);

    /** Column definition for Account_DR_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_ValidCombination> COLUMN_Account_DR_ID = new org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_ValidCombination>(I_GL_JournalLine.class, "Account_DR_ID", org.compiere.model.I_C_ValidCombination.class);
    /** Column name Account_DR_ID */
    public static final String COLUMNNAME_Account_DR_ID = "Account_DR_ID";

	/**
	 * Set Asset Related?.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_CreateAsset (boolean A_CreateAsset);

	/**
	 * Get Asset Related?.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isA_CreateAsset();

    /** Column definition for A_CreateAsset */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_A_CreateAsset = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "A_CreateAsset", null);
    /** Column name A_CreateAsset */
    public static final String COLUMNNAME_A_CreateAsset = "A_CreateAsset";

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
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_AD_Client>(I_GL_JournalLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_AD_Org>(I_GL_JournalLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Haben.
	 * Ausgewiesener Forderungsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAmtAcctCr (java.math.BigDecimal AmtAcctCr);

	/**
	 * Get Haben.
	 * Ausgewiesener Forderungsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmtAcctCr();

    /** Column definition for AmtAcctCr */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_AmtAcctCr = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "AmtAcctCr", null);
    /** Column name AmtAcctCr */
    public static final String COLUMNNAME_AmtAcctCr = "AmtAcctCr";

	/**
	 * Set Soll.
	 * Ausgewiesener Verbindlichkeitsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAmtAcctDr (java.math.BigDecimal AmtAcctDr);

	/**
	 * Get Soll.
	 * Ausgewiesener Verbindlichkeitsbetrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmtAcctDr();

    /** Column definition for AmtAcctDr */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_AmtAcctDr = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "AmtAcctDr", null);
    /** Column name AmtAcctDr */
    public static final String COLUMNNAME_AmtAcctDr = "AmtAcctDr";

	/**
	 * Set Betrag Haben (Gruppe).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setAmtAcctGroupCr (java.math.BigDecimal AmtAcctGroupCr);

	/**
	 * Get Betrag Haben (Gruppe).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.math.BigDecimal getAmtAcctGroupCr();

    /** Column definition for AmtAcctGroupCr */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_AmtAcctGroupCr = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "AmtAcctGroupCr", null);
    /** Column name AmtAcctGroupCr */
    public static final String COLUMNNAME_AmtAcctGroupCr = "AmtAcctGroupCr";

	/**
	 * Set Betrag Soll (Gruppe).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setAmtAcctGroupDr (java.math.BigDecimal AmtAcctGroupDr);

	/**
	 * Get Betrag Soll (Gruppe).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.math.BigDecimal getAmtAcctGroupDr();

    /** Column definition for AmtAcctGroupDr */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_AmtAcctGroupDr = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "AmtAcctGroupDr", null);
    /** Column name AmtAcctGroupDr */
    public static final String COLUMNNAME_AmtAcctGroupDr = "AmtAcctGroupDr";

	/**
	 * Set Ausgangsforderung.
	 * Source Credit Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAmtSourceCr (java.math.BigDecimal AmtSourceCr);

	/**
	 * Get Ausgangsforderung.
	 * Source Credit Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmtSourceCr();

    /** Column definition for AmtSourceCr */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_AmtSourceCr = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "AmtSourceCr", null);
    /** Column name AmtSourceCr */
    public static final String COLUMNNAME_AmtSourceCr = "AmtSourceCr";

	/**
	 * Set Ausgangsverbindlichkeit.
	 * Source Debit Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAmtSourceDr (java.math.BigDecimal AmtSourceDr);

	/**
	 * Get Ausgangsverbindlichkeit.
	 * Source Debit Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmtSourceDr();

    /** Column definition for AmtSourceDr */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_AmtSourceDr = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "AmtSourceDr", null);
    /** Column name AmtSourceDr */
    public static final String COLUMNNAME_AmtSourceDr = "AmtSourceDr";

	/**
	 * Set A_Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_Processed (boolean A_Processed);

	/**
	 * Get A_Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isA_Processed();

    /** Column definition for A_Processed */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_A_Processed = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "A_Processed", null);
    /** Column name A_Processed */
    public static final String COLUMNNAME_A_Processed = "A_Processed";

	/**
	 * Set Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Activity_ID();

	public org.compiere.model.I_C_Activity getC_Activity();

	public void setC_Activity(org.compiere.model.I_C_Activity C_Activity);

    /** Column definition for C_Activity_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_Activity> COLUMN_C_Activity_ID = new org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_Activity>(I_GL_JournalLine.class, "C_Activity_ID", org.compiere.model.I_C_Activity.class);
    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Kursart.
	 * Currency Conversion Rate Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_ConversionType_ID (int C_ConversionType_ID);

	/**
	 * Get Kursart.
	 * Currency Conversion Rate Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_ConversionType_ID();

	public org.compiere.model.I_C_ConversionType getC_ConversionType();

	public void setC_ConversionType(org.compiere.model.I_C_ConversionType C_ConversionType);

    /** Column definition for C_ConversionType_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_ConversionType> COLUMN_C_ConversionType_ID = new org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_ConversionType>(I_GL_JournalLine.class, "C_ConversionType_ID", org.compiere.model.I_C_ConversionType.class);
    /** Column name C_ConversionType_ID */
    public static final String COLUMNNAME_C_ConversionType_ID = "C_ConversionType_ID";

	/**
	 * Set Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_Currency>(I_GL_JournalLine.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Automatic tax account (Credit).
	 * If an account is flagged as automatic tax account, the system will allow and help user to do manual tax bookings in GL journal
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCR_AutoTaxAccount (boolean CR_AutoTaxAccount);

	/**
	 * Get Automatic tax account (Credit).
	 * If an account is flagged as automatic tax account, the system will allow and help user to do manual tax bookings in GL journal
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCR_AutoTaxAccount();

    /** Column definition for CR_AutoTaxAccount */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_CR_AutoTaxAccount = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "CR_AutoTaxAccount", null);
    /** Column name CR_AutoTaxAccount */
    public static final String COLUMNNAME_CR_AutoTaxAccount = "CR_AutoTaxAccount";

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
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_AD_User>(I_GL_JournalLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Tax account (credit).
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCR_Tax_Acct_ID (int CR_Tax_Acct_ID);

	/**
	 * Get Tax account (credit).
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCR_Tax_Acct_ID();

	public org.compiere.model.I_C_ValidCombination getCR_Tax_Acct();

	public void setCR_Tax_Acct(org.compiere.model.I_C_ValidCombination CR_Tax_Acct);

    /** Column definition for CR_Tax_Acct_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_ValidCombination> COLUMN_CR_Tax_Acct_ID = new org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_ValidCombination>(I_GL_JournalLine.class, "CR_Tax_Acct_ID", org.compiere.model.I_C_ValidCombination.class);
    /** Column name CR_Tax_Acct_ID */
    public static final String COLUMNNAME_CR_Tax_Acct_ID = "CR_Tax_Acct_ID";

	/**
	 * Set Steuerbetrag (Haben).
	 * Steuerbetrag für diesen Beleg
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCR_TaxAmt (java.math.BigDecimal CR_TaxAmt);

	/**
	 * Get Steuerbetrag (Haben).
	 * Steuerbetrag für diesen Beleg
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCR_TaxAmt();

    /** Column definition for CR_TaxAmt */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_CR_TaxAmt = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "CR_TaxAmt", null);
    /** Column name CR_TaxAmt */
    public static final String COLUMNNAME_CR_TaxAmt = "CR_TaxAmt";

	/**
	 * Set Bezugswert (Haben).
	 * Bezugswert für die Berechnung der Steuer
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCR_TaxBaseAmt (java.math.BigDecimal CR_TaxBaseAmt);

	/**
	 * Get Bezugswert (Haben).
	 * Bezugswert für die Berechnung der Steuer
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCR_TaxBaseAmt();

    /** Column definition for CR_TaxBaseAmt */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_CR_TaxBaseAmt = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "CR_TaxBaseAmt", null);
    /** Column name CR_TaxBaseAmt */
    public static final String COLUMNNAME_CR_TaxBaseAmt = "CR_TaxBaseAmt";

	/**
	 * Set Steuer (Haben).
	 * Steuerart
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCR_Tax_ID (int CR_Tax_ID);

	/**
	 * Get Steuer (Haben).
	 * Steuerart
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCR_Tax_ID();

	public org.compiere.model.I_C_Tax getCR_Tax();

	public void setCR_Tax(org.compiere.model.I_C_Tax CR_Tax);

    /** Column definition for CR_Tax_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_Tax> COLUMN_CR_Tax_ID = new org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_Tax>(I_GL_JournalLine.class, "CR_Tax_ID", org.compiere.model.I_C_Tax.class);
    /** Column name CR_Tax_ID */
    public static final String COLUMNNAME_CR_Tax_ID = "CR_Tax_ID";

	/**
	 * Set Summe (Haben).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCR_TaxTotalAmt (java.math.BigDecimal CR_TaxTotalAmt);

	/**
	 * Get Summe (Haben).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCR_TaxTotalAmt();

    /** Column definition for CR_TaxTotalAmt */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_CR_TaxTotalAmt = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "CR_TaxTotalAmt", null);
    /** Column name CR_TaxTotalAmt */
    public static final String COLUMNNAME_CR_TaxTotalAmt = "CR_TaxTotalAmt";

	/**
	 * Set Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_UOM>(I_GL_JournalLine.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Wechselkurs.
	 * Currency Conversion Rate
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCurrencyRate (java.math.BigDecimal CurrencyRate);

	/**
	 * Get Wechselkurs.
	 * Currency Conversion Rate
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCurrencyRate();

    /** Column definition for CurrencyRate */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_CurrencyRate = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "CurrencyRate", null);
    /** Column name CurrencyRate */
    public static final String COLUMNNAME_CurrencyRate = "CurrencyRate";

	/**
	 * Set Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateAcct();

    /** Column definition for DateAcct */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_DateAcct = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "DateAcct", null);
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
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Automatic tax account (Debit).
	 * If an account is flagged as automatic tax account, the system will allow and help user to do manual tax bookings in GL journal
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDR_AutoTaxAccount (boolean DR_AutoTaxAccount);

	/**
	 * Get Automatic tax account (Debit).
	 * If an account is flagged as automatic tax account, the system will allow and help user to do manual tax bookings in GL journal
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDR_AutoTaxAccount();

    /** Column definition for DR_AutoTaxAccount */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_DR_AutoTaxAccount = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "DR_AutoTaxAccount", null);
    /** Column name DR_AutoTaxAccount */
    public static final String COLUMNNAME_DR_AutoTaxAccount = "DR_AutoTaxAccount";

	/**
	 * Set Tax account (debit).
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDR_Tax_Acct_ID (int DR_Tax_Acct_ID);

	/**
	 * Get Tax account (debit).
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDR_Tax_Acct_ID();

	public org.compiere.model.I_C_ValidCombination getDR_Tax_Acct();

	public void setDR_Tax_Acct(org.compiere.model.I_C_ValidCombination DR_Tax_Acct);

    /** Column definition for DR_Tax_Acct_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_ValidCombination> COLUMN_DR_Tax_Acct_ID = new org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_ValidCombination>(I_GL_JournalLine.class, "DR_Tax_Acct_ID", org.compiere.model.I_C_ValidCombination.class);
    /** Column name DR_Tax_Acct_ID */
    public static final String COLUMNNAME_DR_Tax_Acct_ID = "DR_Tax_Acct_ID";

	/**
	 * Set Steuerbetrag (Soll).
	 * Steuerbetrag für diesen Beleg
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDR_TaxAmt (java.math.BigDecimal DR_TaxAmt);

	/**
	 * Get Steuerbetrag (Soll).
	 * Steuerbetrag für diesen Beleg
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDR_TaxAmt();

    /** Column definition for DR_TaxAmt */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_DR_TaxAmt = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "DR_TaxAmt", null);
    /** Column name DR_TaxAmt */
    public static final String COLUMNNAME_DR_TaxAmt = "DR_TaxAmt";

	/**
	 * Set Bezugswert (Soll).
	 * Bezugswert für die Berechnung der Steuer
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDR_TaxBaseAmt (java.math.BigDecimal DR_TaxBaseAmt);

	/**
	 * Get Bezugswert (Soll).
	 * Bezugswert für die Berechnung der Steuer
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDR_TaxBaseAmt();

    /** Column definition for DR_TaxBaseAmt */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_DR_TaxBaseAmt = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "DR_TaxBaseAmt", null);
    /** Column name DR_TaxBaseAmt */
    public static final String COLUMNNAME_DR_TaxBaseAmt = "DR_TaxBaseAmt";

	/**
	 * Set Steuer (Soll).
	 * Steuerart
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDR_Tax_ID (int DR_Tax_ID);

	/**
	 * Get Steuer (Soll).
	 * Steuerart
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDR_Tax_ID();

	public org.compiere.model.I_C_Tax getDR_Tax();

	public void setDR_Tax(org.compiere.model.I_C_Tax DR_Tax);

    /** Column definition for DR_Tax_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_Tax> COLUMN_DR_Tax_ID = new org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_Tax>(I_GL_JournalLine.class, "DR_Tax_ID", org.compiere.model.I_C_Tax.class);
    /** Column name DR_Tax_ID */
    public static final String COLUMNNAME_DR_Tax_ID = "DR_Tax_ID";

	/**
	 * Set Summe (Soll).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDR_TaxTotalAmt (java.math.BigDecimal DR_TaxTotalAmt);

	/**
	 * Get Summe (Soll).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDR_TaxTotalAmt();

    /** Column definition for DR_TaxTotalAmt */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_DR_TaxTotalAmt = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "DR_TaxTotalAmt", null);
    /** Column name DR_TaxTotalAmt */
    public static final String COLUMNNAME_DR_TaxTotalAmt = "DR_TaxTotalAmt";

	/**
	 * Set Journal.
	 * General Ledger Journal
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGL_Journal_ID (int GL_Journal_ID);

	/**
	 * Get Journal.
	 * General Ledger Journal
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGL_Journal_ID();

	public org.compiere.model.I_GL_Journal getGL_Journal();

	public void setGL_Journal(org.compiere.model.I_GL_Journal GL_Journal);

    /** Column definition for GL_Journal_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_GL_Journal> COLUMN_GL_Journal_ID = new org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_GL_Journal>(I_GL_JournalLine.class, "GL_Journal_ID", org.compiere.model.I_GL_Journal.class);
    /** Column name GL_Journal_ID */
    public static final String COLUMNNAME_GL_Journal_ID = "GL_Journal_ID";

	/**
	 * Set Journal-Position (Gruppe).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGL_JournalLine_Group (int GL_JournalLine_Group);

	/**
	 * Get Journal-Position (Gruppe).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGL_JournalLine_Group();

    /** Column definition for GL_JournalLine_Group */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_GL_JournalLine_Group = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "GL_JournalLine_Group", null);
    /** Column name GL_JournalLine_Group */
    public static final String COLUMNNAME_GL_JournalLine_Group = "GL_JournalLine_Group";

	/**
	 * Set Journal-Position.
	 * General Ledger Journal Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGL_JournalLine_ID (int GL_JournalLine_ID);

	/**
	 * Get Journal-Position.
	 * General Ledger Journal Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGL_JournalLine_ID();

    /** Column definition for GL_JournalLine_ID */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_GL_JournalLine_ID = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "GL_JournalLine_ID", null);
    /** Column name GL_JournalLine_ID */
    public static final String COLUMNNAME_GL_JournalLine_ID = "GL_JournalLine_ID";

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
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Haben erlaubt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAllowAccountCR (boolean IsAllowAccountCR);

	/**
	 * Get Haben erlaubt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllowAccountCR();

    /** Column definition for IsAllowAccountCR */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_IsAllowAccountCR = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "IsAllowAccountCR", null);
    /** Column name IsAllowAccountCR */
    public static final String COLUMNNAME_IsAllowAccountCR = "IsAllowAccountCR";

	/**
	 * Set Soll erlaubt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAllowAccountDR (boolean IsAllowAccountDR);

	/**
	 * Get Soll erlaubt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllowAccountDR();

    /** Column definition for IsAllowAccountDR */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_IsAllowAccountDR = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "IsAllowAccountDR", null);
    /** Column name IsAllowAccountDR */
    public static final String COLUMNNAME_IsAllowAccountDR = "IsAllowAccountDR";

	/**
	 * Set Generated.
	 * This Line is generated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsGenerated (boolean IsGenerated);

	/**
	 * Get Generated.
	 * This Line is generated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isGenerated();

    /** Column definition for IsGenerated */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_IsGenerated = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "IsGenerated", null);
    /** Column name IsGenerated */
    public static final String COLUMNNAME_IsGenerated = "IsGenerated";

	/**
	 * Set Splitbuchung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSplitAcctTrx (boolean IsSplitAcctTrx);

	/**
	 * Get Splitbuchung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSplitAcctTrx();

    /** Column definition for IsSplitAcctTrx */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_IsSplitAcctTrx = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "IsSplitAcctTrx", null);
    /** Column name IsSplitAcctTrx */
    public static final String COLUMNNAME_IsSplitAcctTrx = "IsSplitAcctTrx";

	/**
	 * Set Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Menge.
	 * Quantity
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Menge.
	 * Quantity
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Art.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setType (java.lang.String Type);

	/**
	 * Get Art.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getType();

    /** Column definition for Type */
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_Type = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "Type", null);
    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

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
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_GL_JournalLine, Object>(I_GL_JournalLine.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_GL_JournalLine, org.compiere.model.I_AD_User>(I_GL_JournalLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
