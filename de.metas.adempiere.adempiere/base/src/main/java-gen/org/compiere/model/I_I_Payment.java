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


/** Generated Interface for I_Payment
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_I_Payment 
{

    /** TableName=I_Payment */
    public static final String Table_Name = "I_Payment";

    /** AD_Table_ID=597 */
    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

    /** Load Meta Data */

	/**
	 * Set Konto-Nr..
	 * Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAccountNo (java.lang.String AccountNo);

	/**
	 * Get Konto-Nr..
	 * Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAccountNo();

    /** Column definition for AccountNo */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_AccountNo = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "AccountNo", null);
    /** Column name AccountNo */
    public static final String COLUMNNAME_AccountNo = "AccountNo";

	/**
	 * Set Ort.
	 * City or the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_City (java.lang.String A_City);

	/**
	 * Get Ort.
	 * City or the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getA_City();

    /** Column definition for A_City */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_A_City = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "A_City", null);
    /** Column name A_City */
    public static final String COLUMNNAME_A_City = "A_City";

	/**
	 * Set Land.
	 * Country
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_Country (java.lang.String A_Country);

	/**
	 * Get Land.
	 * Country
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getA_Country();

    /** Column definition for A_Country */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_A_Country = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "A_Country", null);
    /** Column name A_Country */
    public static final String COLUMNNAME_A_Country = "A_Country";

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_AD_Client>(I_I_Payment.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_AD_Org>(I_I_Payment.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set EMail.
	 * Email Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_EMail (java.lang.String A_EMail);

	/**
	 * Get EMail.
	 * Email Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getA_EMail();

    /** Column definition for A_EMail */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_A_EMail = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "A_EMail", null);
    /** Column name A_EMail */
    public static final String COLUMNNAME_A_EMail = "A_EMail";

	/**
	 * Set Führerschein-Nr..
	 * Payment Identification - Driver License
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_Ident_DL (java.lang.String A_Ident_DL);

	/**
	 * Get Führerschein-Nr..
	 * Payment Identification - Driver License
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getA_Ident_DL();

    /** Column definition for A_Ident_DL */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_A_Ident_DL = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "A_Ident_DL", null);
    /** Column name A_Ident_DL */
    public static final String COLUMNNAME_A_Ident_DL = "A_Ident_DL";

	/**
	 * Set Ausweis-Nr..
	 * Payment Identification - Social Security No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_Ident_SSN (java.lang.String A_Ident_SSN);

	/**
	 * Get Ausweis-Nr..
	 * Payment Identification - Social Security No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getA_Ident_SSN();

    /** Column definition for A_Ident_SSN */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_A_Ident_SSN = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "A_Ident_SSN", null);
    /** Column name A_Ident_SSN */
    public static final String COLUMNNAME_A_Ident_SSN = "A_Ident_SSN";

	/**
	 * Set Name.
	 * Name on Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_Name (java.lang.String A_Name);

	/**
	 * Get Name.
	 * Name on Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getA_Name();

    /** Column definition for A_Name */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_A_Name = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "A_Name", null);
    /** Column name A_Name */
    public static final String COLUMNNAME_A_Name = "A_Name";

	/**
	 * Set Bundesstaat / -land.
	 * State of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_State (java.lang.String A_State);

	/**
	 * Get Bundesstaat / -land.
	 * State of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getA_State();

    /** Column definition for A_State */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_A_State = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "A_State", null);
    /** Column name A_State */
    public static final String COLUMNNAME_A_State = "A_State";

	/**
	 * Set Straße.
	 * Street address of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_Street (java.lang.String A_Street);

	/**
	 * Get Straße.
	 * Street address of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getA_Street();

    /** Column definition for A_Street */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_A_Street = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "A_Street", null);
    /** Column name A_Street */
    public static final String COLUMNNAME_A_Street = "A_Street";

	/**
	 * Set Postleitzahl.
	 * Zip Code of the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setA_Zip (java.lang.String A_Zip);

	/**
	 * Get Postleitzahl.
	 * Zip Code of the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getA_Zip();

    /** Column definition for A_Zip */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_A_Zip = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "A_Zip", null);
    /** Column name A_Zip */
    public static final String COLUMNNAME_A_Zip = "A_Zip";

	/**
	 * Set Bank Account No.
	 * Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBankAccountNo (java.lang.String BankAccountNo);

	/**
	 * Get Bank Account No.
	 * Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBankAccountNo();

    /** Column definition for BankAccountNo */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_BankAccountNo = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "BankAccountNo", null);
    /** Column name BankAccountNo */
    public static final String COLUMNNAME_BankAccountNo = "BankAccountNo";

	/**
	 * Set Geschäftspartner-Schlüssel.
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPartnerValue (java.lang.String BPartnerValue);

	/**
	 * Get Geschäftspartner-Schlüssel.
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPartnerValue();

    /** Column definition for BPartnerValue */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_BPartnerValue = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "BPartnerValue", null);
    /** Column name BPartnerValue */
    public static final String COLUMNNAME_BPartnerValue = "BPartnerValue";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_C_BPartner>(I_I_Payment.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Bankverbindung.
	 * Bankverbindung des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Bankverbindung.
	 * Bankverbindung des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BP_BankAccount_ID();

	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount();

	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount);

    /** Column definition for C_BP_BankAccount_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccount_ID = new org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_C_BP_BankAccount>(I_I_Payment.class, "C_BP_BankAccount_ID", org.compiere.model.I_C_BP_BankAccount.class);
    /** Column name C_BP_BankAccount_ID */
    public static final String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Charge_ID();

	public org.compiere.model.I_C_Charge getC_Charge();

	public void setC_Charge(org.compiere.model.I_C_Charge C_Charge);

    /** Column definition for C_Charge_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_C_Charge> COLUMN_C_Charge_ID = new org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_C_Charge>(I_I_Payment.class, "C_Charge_ID", org.compiere.model.I_C_Charge.class);
    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_C_Currency>(I_I_Payment.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Belegart.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Belegart.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType();

	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType);

    /** Column definition for C_DocType_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_C_DocType> COLUMN_C_DocType_ID = new org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_C_DocType>(I_I_Payment.class, "C_DocType_ID", org.compiere.model.I_C_DocType.class);
    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setChargeAmt (java.math.BigDecimal ChargeAmt);

	/**
	 * Get Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getChargeAmt();

    /** Column definition for ChargeAmt */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_ChargeAmt = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "ChargeAmt", null);
    /** Column name ChargeAmt */
    public static final String COLUMNNAME_ChargeAmt = "ChargeAmt";

	/**
	 * Set Gebühren-Bezeichnung.
	 * Name of the Charge
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setChargeName (java.lang.String ChargeName);

	/**
	 * Get Gebühren-Bezeichnung.
	 * Name of the Charge
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getChargeName();

    /** Column definition for ChargeName */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_ChargeName = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "ChargeName", null);
    /** Column name ChargeName */
    public static final String COLUMNNAME_ChargeName = "ChargeName";

	/**
	 * Set Check No.
	 * Check Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCheckNo (java.lang.String CheckNo);

	/**
	 * Get Check No.
	 * Check Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCheckNo();

    /** Column definition for CheckNo */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_CheckNo = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "CheckNo", null);
    /** Column name CheckNo */
    public static final String COLUMNNAME_CheckNo = "CheckNo";

	/**
	 * Set Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice();

	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

    /** Column definition for C_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_C_Invoice>(I_I_Payment.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Zahlung.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Payment_ID (int C_Payment_ID);

	/**
	 * Get Zahlung.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Payment_ID();

	public org.compiere.model.I_C_Payment getC_Payment();

	public void setC_Payment(org.compiere.model.I_C_Payment C_Payment);

    /** Column definition for C_Payment_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_C_Payment> COLUMN_C_Payment_ID = new org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_C_Payment>(I_I_Payment.class, "C_Payment_ID", org.compiere.model.I_C_Payment.class);
    /** Column name C_Payment_ID */
    public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_AD_User>(I_I_Payment.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Exp. Monat.
	 * Expiry Month
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreditCardExpMM (int CreditCardExpMM);

	/**
	 * Get Exp. Monat.
	 * Expiry Month
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreditCardExpMM();

    /** Column definition for CreditCardExpMM */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_CreditCardExpMM = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "CreditCardExpMM", null);
    /** Column name CreditCardExpMM */
    public static final String COLUMNNAME_CreditCardExpMM = "CreditCardExpMM";

	/**
	 * Set Exp. Jahr.
	 * Expiry Year
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreditCardExpYY (int CreditCardExpYY);

	/**
	 * Get Exp. Jahr.
	 * Expiry Year
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreditCardExpYY();

    /** Column definition for CreditCardExpYY */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_CreditCardExpYY = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "CreditCardExpYY", null);
    /** Column name CreditCardExpYY */
    public static final String COLUMNNAME_CreditCardExpYY = "CreditCardExpYY";

	/**
	 * Set Kreditkarten-Nummer.
	 * Kreditkarten-Nummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreditCardNumber (java.lang.String CreditCardNumber);

	/**
	 * Get Kreditkarten-Nummer.
	 * Kreditkarten-Nummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCreditCardNumber();

    /** Column definition for CreditCardNumber */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_CreditCardNumber = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "CreditCardNumber", null);
    /** Column name CreditCardNumber */
    public static final String COLUMNNAME_CreditCardNumber = "CreditCardNumber";

	/**
	 * Set Kreditkarte.
	 * Credit Card (Visa, MC, AmEx)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreditCardType (java.lang.String CreditCardType);

	/**
	 * Get Kreditkarte.
	 * Credit Card (Visa, MC, AmEx)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCreditCardType();

    /** Column definition for CreditCardType */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_CreditCardType = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "CreditCardType", null);
    /** Column name CreditCardType */
    public static final String COLUMNNAME_CreditCardType = "CreditCardType";

	/**
	 * Set Verifizierungs-Code.
	 * Credit Card Verification code on credit card
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreditCardVV (java.lang.String CreditCardVV);

	/**
	 * Get Verifizierungs-Code.
	 * Credit Card Verification code on credit card
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCreditCardVV();

    /** Column definition for CreditCardVV */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_CreditCardVV = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "CreditCardVV", null);
    /** Column name CreditCardVV */
    public static final String COLUMNNAME_CreditCardVV = "CreditCardVV";

	/**
	 * Set Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateAcct();

    /** Column definition for DateAcct */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_DateAcct = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "DateAcct", null);
    /** Column name DateAcct */
    public static final String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Vorgangsdatum.
	 * Transaction Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateTrx (java.sql.Timestamp DateTrx);

	/**
	 * Get Vorgangsdatum.
	 * Transaction Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateTrx();

    /** Column definition for DateTrx */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_DateTrx = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "DateTrx", null);
    /** Column name DateTrx */
    public static final String COLUMNNAME_DateTrx = "DateTrx";

	/**
	 * Set Discount Amount.
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDiscountAmt (java.math.BigDecimal DiscountAmt);

	/**
	 * Get Discount Amount.
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getDiscountAmt();

    /** Column definition for DiscountAmt */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_DiscountAmt = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "DiscountAmt", null);
    /** Column name DiscountAmt */
    public static final String COLUMNNAME_DiscountAmt = "DiscountAmt";

	/**
	 * Set Belegart-Bezeichnung.
	 * Name of the Document Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocTypeName (java.lang.String DocTypeName);

	/**
	 * Get Belegart-Bezeichnung.
	 * Name of the Document Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocTypeName();

    /** Column definition for DocTypeName */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_DocTypeName = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "DocTypeName", null);
    /** Column name DocTypeName */
    public static final String COLUMNNAME_DocTypeName = "DocTypeName";

	/**
	 * Set Beleg Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Beleg Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Import-Fehlermeldung.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg);

	/**
	 * Get Import-Fehlermeldung.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_ErrorMsg();

    /** Column definition for I_ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_I_ErrorMsg = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "I_ErrorMsg", null);
    /** Column name I_ErrorMsg */
    public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Importiert.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_IsImported (boolean I_IsImported);

	/**
	 * Get Importiert.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isI_IsImported();

    /** Column definition for I_IsImported */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_I_IsImported = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "I_IsImported", null);
    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set Invoice Document No.
	 * Document Number of the Invoice
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInvoiceDocumentNo (java.lang.String InvoiceDocumentNo);

	/**
	 * Get Invoice Document No.
	 * Document Number of the Invoice
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInvoiceDocumentNo();

    /** Column definition for InvoiceDocumentNo */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_InvoiceDocumentNo = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "InvoiceDocumentNo", null);
    /** Column name InvoiceDocumentNo */
    public static final String COLUMNNAME_InvoiceDocumentNo = "InvoiceDocumentNo";

	/**
	 * Set Import - Zahlung.
	 * Import Payment
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_Payment_ID (int I_Payment_ID);

	/**
	 * Get Import - Zahlung.
	 * Import Payment
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getI_Payment_ID();

    /** Column definition for I_Payment_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_I_Payment_ID = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "I_Payment_ID", null);
    /** Column name I_Payment_ID */
    public static final String COLUMNNAME_I_Payment_ID = "I_Payment_ID";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Freigegeben.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsApproved (boolean IsApproved);

	/**
	 * Get Freigegeben.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isApproved();

    /** Column definition for IsApproved */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_IsApproved = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "IsApproved", null);
    /** Column name IsApproved */
    public static final String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Delayed Capture.
	 * Charge after Shipment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsDelayedCapture (boolean IsDelayedCapture);

	/**
	 * Get Delayed Capture.
	 * Charge after Shipment
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isDelayedCapture();

    /** Column definition for IsDelayedCapture */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_IsDelayedCapture = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "IsDelayedCapture", null);
    /** Column name IsDelayedCapture */
    public static final String COLUMNNAME_IsDelayedCapture = "IsDelayedCapture";

	/**
	 * Set ISO Währungscode.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setISO_Code (java.lang.String ISO_Code);

	/**
	 * Get ISO Währungscode.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getISO_Code();

    /** Column definition for ISO_Code */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_ISO_Code = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "ISO_Code", null);
    /** Column name ISO_Code */
    public static final String COLUMNNAME_ISO_Code = "ISO_Code";

	/**
	 * Set Over/Under Payment.
	 * Over-Payment (unallocated) or Under-Payment (partial payment)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsOverUnderPayment (boolean IsOverUnderPayment);

	/**
	 * Get Over/Under Payment.
	 * Over-Payment (unallocated) or Under-Payment (partial payment)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isOverUnderPayment();

    /** Column definition for IsOverUnderPayment */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_IsOverUnderPayment = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "IsOverUnderPayment", null);
    /** Column name IsOverUnderPayment */
    public static final String COLUMNNAME_IsOverUnderPayment = "IsOverUnderPayment";

	/**
	 * Set Zahlungseingang.
	 * This is a sales transaction (receipt)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsReceipt (boolean IsReceipt);

	/**
	 * Get Zahlungseingang.
	 * This is a sales transaction (receipt)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isReceipt();

    /** Column definition for IsReceipt */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_IsReceipt = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "IsReceipt", null);
    /** Column name IsReceipt */
    public static final String COLUMNNAME_IsReceipt = "IsReceipt";

	/**
	 * Set Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsSelfService (boolean IsSelfService);

	/**
	 * Get Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isSelfService();

    /** Column definition for IsSelfService */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_IsSelfService = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "IsSelfService", null);
    /** Column name IsSelfService */
    public static final String COLUMNNAME_IsSelfService = "IsSelfService";

	/**
	 * Set Micr.
	 * Combination of routing no, account and check no
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMicr (java.lang.String Micr);

	/**
	 * Get Micr.
	 * Combination of routing no, account and check no
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMicr();

    /** Column definition for Micr */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_Micr = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "Micr", null);
    /** Column name Micr */
    public static final String COLUMNNAME_Micr = "Micr";

	/**
	 * Set Original Transaction ID.
	 * Original Transaction ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrig_TrxID (java.lang.String Orig_TrxID);

	/**
	 * Get Original Transaction ID.
	 * Original Transaction ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOrig_TrxID();

    /** Column definition for Orig_TrxID */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_Orig_TrxID = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "Orig_TrxID", null);
    /** Column name Orig_TrxID */
    public static final String COLUMNNAME_Orig_TrxID = "Orig_TrxID";

	/**
	 * Set Over/Under Payment.
	 * Over-Payment (unallocated) or Under-Payment (partial payment) Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOverUnderAmt (java.math.BigDecimal OverUnderAmt);

	/**
	 * Get Over/Under Payment.
	 * Over-Payment (unallocated) or Under-Payment (partial payment) Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getOverUnderAmt();

    /** Column definition for OverUnderAmt */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_OverUnderAmt = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "OverUnderAmt", null);
    /** Column name OverUnderAmt */
    public static final String COLUMNNAME_OverUnderAmt = "OverUnderAmt";

	/**
	 * Set Zahlungsbetrag.
	 * Amount being paid
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPayAmt (java.math.BigDecimal PayAmt);

	/**
	 * Get Zahlungsbetrag.
	 * Amount being paid
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPayAmt();

    /** Column definition for PayAmt */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_PayAmt = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "PayAmt", null);
    /** Column name PayAmt */
    public static final String COLUMNNAME_PayAmt = "PayAmt";

	/**
	 * Set PO Number.
	 * Purchase Order Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPONum (java.lang.String PONum);

	/**
	 * Get PO Number.
	 * Purchase Order Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPONum();

    /** Column definition for PONum */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_PONum = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "PONum", null);
    /** Column name PONum */
    public static final String COLUMNNAME_PONum = "PONum";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Authorization Code.
	 * Authorization Code returned
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_AuthCode (java.lang.String R_AuthCode);

	/**
	 * Get Authorization Code.
	 * Authorization Code returned
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getR_AuthCode();

    /** Column definition for R_AuthCode */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_R_AuthCode = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "R_AuthCode", null);
    /** Column name R_AuthCode */
    public static final String COLUMNNAME_R_AuthCode = "R_AuthCode";

	/**
	 * Set Info.
	 * Response info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_Info (java.lang.String R_Info);

	/**
	 * Get Info.
	 * Response info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getR_Info();

    /** Column definition for R_Info */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_R_Info = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "R_Info", null);
    /** Column name R_Info */
    public static final String COLUMNNAME_R_Info = "R_Info";

	/**
	 * Set BLZ.
	 * Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRoutingNo (java.lang.String RoutingNo);

	/**
	 * Get BLZ.
	 * Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRoutingNo();

    /** Column definition for RoutingNo */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_RoutingNo = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "RoutingNo", null);
    /** Column name RoutingNo */
    public static final String COLUMNNAME_RoutingNo = "RoutingNo";

	/**
	 * Set Referenz.
	 * Payment reference
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_PnRef (java.lang.String R_PnRef);

	/**
	 * Get Referenz.
	 * Payment reference
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getR_PnRef();

    /** Column definition for R_PnRef */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_R_PnRef = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "R_PnRef", null);
    /** Column name R_PnRef */
    public static final String COLUMNNAME_R_PnRef = "R_PnRef";

	/**
	 * Set Response Message.
	 * Response message
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_RespMsg (java.lang.String R_RespMsg);

	/**
	 * Get Response Message.
	 * Response message
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getR_RespMsg();

    /** Column definition for R_RespMsg */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_R_RespMsg = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "R_RespMsg", null);
    /** Column name R_RespMsg */
    public static final String COLUMNNAME_R_RespMsg = "R_RespMsg";

	/**
	 * Set Ergebnis.
	 * Result of transmission
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_Result (java.lang.String R_Result);

	/**
	 * Get Ergebnis.
	 * Result of transmission
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getR_Result();

    /** Column definition for R_Result */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_R_Result = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "R_Result", null);
    /** Column name R_Result */
    public static final String COLUMNNAME_R_Result = "R_Result";

	/**
	 * Set Swipe.
	 * Track 1 and 2 of the Credit Card
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSwipe (java.lang.String Swipe);

	/**
	 * Get Swipe.
	 * Track 1 and 2 of the Credit Card
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSwipe();

    /** Column definition for Swipe */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_Swipe = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "Swipe", null);
    /** Column name Swipe */
    public static final String COLUMNNAME_Swipe = "Swipe";

	/**
	 * Set Steuerbetrag.
	 * Tax Amount for a document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTaxAmt (java.math.BigDecimal TaxAmt);

	/**
	 * Get Steuerbetrag.
	 * Tax Amount for a document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getTaxAmt();

    /** Column definition for TaxAmt */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_TaxAmt = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "TaxAmt", null);
    /** Column name TaxAmt */
    public static final String COLUMNNAME_TaxAmt = "TaxAmt";

	/**
	 * Set Zahlmittel.
	 * Method of Payment
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTenderType (java.lang.String TenderType);

	/**
	 * Get Zahlmittel.
	 * Method of Payment
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTenderType();

    /** Column definition for TenderType */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_TenderType = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "TenderType", null);
    /** Column name TenderType */
    public static final String COLUMNNAME_TenderType = "TenderType";

	/**
	 * Set Transaction Type.
	 * Type of credit card transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTrxType (java.lang.String TrxType);

	/**
	 * Get Transaction Type.
	 * Type of credit card transaction
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTrxType();

    /** Column definition for TrxType */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_TrxType = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "TrxType", null);
    /** Column name TrxType */
    public static final String COLUMNNAME_TrxType = "TrxType";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_I_Payment, org.compiere.model.I_AD_User>(I_I_Payment.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Prüfziffer.
	 * Voice Authorization Code from credit card company
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVoiceAuthCode (java.lang.String VoiceAuthCode);

	/**
	 * Get Prüfziffer.
	 * Voice Authorization Code from credit card company
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVoiceAuthCode();

    /** Column definition for VoiceAuthCode */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_VoiceAuthCode = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "VoiceAuthCode", null);
    /** Column name VoiceAuthCode */
    public static final String COLUMNNAME_VoiceAuthCode = "VoiceAuthCode";

	/**
	 * Set Write-off Amount.
	 * Amount to write-off
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWriteOffAmt (java.math.BigDecimal WriteOffAmt);

	/**
	 * Get Write-off Amount.
	 * Amount to write-off
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getWriteOffAmt();

    /** Column definition for WriteOffAmt */
    public static final org.adempiere.model.ModelColumn<I_I_Payment, Object> COLUMN_WriteOffAmt = new org.adempiere.model.ModelColumn<I_I_Payment, Object>(I_I_Payment.class, "WriteOffAmt", null);
    /** Column name WriteOffAmt */
    public static final String COLUMNNAME_WriteOffAmt = "WriteOffAmt";
}
