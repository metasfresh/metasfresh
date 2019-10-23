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


/** Generated Interface for C_PaymentProcessor
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_PaymentProcessor 
{

    /** TableName=C_PaymentProcessor */
    public static final String Table_Name = "C_PaymentProcessor";

    /** AD_Table_ID=398 */
    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Accept AMEX.
	 * Accept American Express Card
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAcceptAMEX (boolean AcceptAMEX);

	/**
	 * Get Accept AMEX.
	 * Accept American Express Card
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAcceptAMEX();

    /** Column definition for AcceptAMEX */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_AcceptAMEX = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "AcceptAMEX", null);
    /** Column name AcceptAMEX */
    public static final String COLUMNNAME_AcceptAMEX = "AcceptAMEX";

	/**
	 * Set Accept ATM.
	 * Accept Bank ATM Card
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAcceptATM (boolean AcceptATM);

	/**
	 * Get Accept ATM.
	 * Accept Bank ATM Card
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAcceptATM();

    /** Column definition for AcceptATM */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_AcceptATM = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "AcceptATM", null);
    /** Column name AcceptATM */
    public static final String COLUMNNAME_AcceptATM = "AcceptATM";

	/**
	 * Set Accept Electronic Check.
	 * Accept ECheck (Electronic Checks)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAcceptCheck (boolean AcceptCheck);

	/**
	 * Get Accept Electronic Check.
	 * Accept ECheck (Electronic Checks)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAcceptCheck();

    /** Column definition for AcceptCheck */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_AcceptCheck = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "AcceptCheck", null);
    /** Column name AcceptCheck */
    public static final String COLUMNNAME_AcceptCheck = "AcceptCheck";

	/**
	 * Set Accept Corporate.
	 * Accept Corporate Purchase Cards
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAcceptCorporate (boolean AcceptCorporate);

	/**
	 * Get Accept Corporate.
	 * Accept Corporate Purchase Cards
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAcceptCorporate();

    /** Column definition for AcceptCorporate */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_AcceptCorporate = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "AcceptCorporate", null);
    /** Column name AcceptCorporate */
    public static final String COLUMNNAME_AcceptCorporate = "AcceptCorporate";

	/**
	 * Set Accept Diners.
	 * Accept Diner's Club
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAcceptDiners (boolean AcceptDiners);

	/**
	 * Get Accept Diners.
	 * Accept Diner's Club
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAcceptDiners();

    /** Column definition for AcceptDiners */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_AcceptDiners = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "AcceptDiners", null);
    /** Column name AcceptDiners */
    public static final String COLUMNNAME_AcceptDiners = "AcceptDiners";

	/**
	 * Set Accept Direct Debit.
	 * Accept Direct Debits (vendor initiated)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAcceptDirectDebit (boolean AcceptDirectDebit);

	/**
	 * Get Accept Direct Debit.
	 * Accept Direct Debits (vendor initiated)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAcceptDirectDebit();

    /** Column definition for AcceptDirectDebit */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_AcceptDirectDebit = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "AcceptDirectDebit", null);
    /** Column name AcceptDirectDebit */
    public static final String COLUMNNAME_AcceptDirectDebit = "AcceptDirectDebit";

	/**
	 * Set Accept Direct Deposit.
	 * Accept Direct Deposit (payee initiated)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAcceptDirectDeposit (boolean AcceptDirectDeposit);

	/**
	 * Get Accept Direct Deposit.
	 * Accept Direct Deposit (payee initiated)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAcceptDirectDeposit();

    /** Column definition for AcceptDirectDeposit */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_AcceptDirectDeposit = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "AcceptDirectDeposit", null);
    /** Column name AcceptDirectDeposit */
    public static final String COLUMNNAME_AcceptDirectDeposit = "AcceptDirectDeposit";

	/**
	 * Set Accept Discover.
	 * Accept Discover Card
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAcceptDiscover (boolean AcceptDiscover);

	/**
	 * Get Accept Discover.
	 * Accept Discover Card
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAcceptDiscover();

    /** Column definition for AcceptDiscover */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_AcceptDiscover = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "AcceptDiscover", null);
    /** Column name AcceptDiscover */
    public static final String COLUMNNAME_AcceptDiscover = "AcceptDiscover";

	/**
	 * Set Accept MasterCard.
	 * Accept Master Card
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAcceptMC (boolean AcceptMC);

	/**
	 * Get Accept MasterCard.
	 * Accept Master Card
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAcceptMC();

    /** Column definition for AcceptMC */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_AcceptMC = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "AcceptMC", null);
    /** Column name AcceptMC */
    public static final String COLUMNNAME_AcceptMC = "AcceptMC";

	/**
	 * Set Accept Visa.
	 * Accept Visa Cards
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAcceptVisa (boolean AcceptVisa);

	/**
	 * Get Accept Visa.
	 * Accept Visa Cards
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAcceptVisa();

    /** Column definition for AcceptVisa */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_AcceptVisa = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "AcceptVisa", null);
    /** Column name AcceptVisa */
    public static final String COLUMNNAME_AcceptVisa = "AcceptVisa";

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
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, org.compiere.model.I_AD_Client>(I_C_PaymentProcessor.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, org.compiere.model.I_AD_Org>(I_C_PaymentProcessor.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Reihenfolge.
	 * Document Sequence
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Sequence_ID (int AD_Sequence_ID);

	/**
	 * Get Reihenfolge.
	 * Document Sequence
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Sequence_ID();

	public org.compiere.model.I_AD_Sequence getAD_Sequence();

	public void setAD_Sequence(org.compiere.model.I_AD_Sequence AD_Sequence);

    /** Column definition for AD_Sequence_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, org.compiere.model.I_AD_Sequence> COLUMN_AD_Sequence_ID = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, org.compiere.model.I_AD_Sequence>(I_C_PaymentProcessor.class, "AD_Sequence_ID", org.compiere.model.I_AD_Sequence.class);
    /** Column name AD_Sequence_ID */
    public static final String COLUMNNAME_AD_Sequence_ID = "AD_Sequence_ID";

	/**
	 * Set Bankverbindung.
	 * Bankverbindung des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Bankverbindung.
	 * Bankverbindung des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BP_BankAccount_ID();

	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount();

	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount);

    /** Column definition for C_BP_BankAccount_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccount_ID = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, org.compiere.model.I_C_BP_BankAccount>(I_C_PaymentProcessor.class, "C_BP_BankAccount_ID", org.compiere.model.I_C_BP_BankAccount.class);
    /** Column name C_BP_BankAccount_ID */
    public static final String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, org.compiere.model.I_C_Currency>(I_C_PaymentProcessor.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Prov. %.
	 * Commission stated as a percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCommission (java.math.BigDecimal Commission);

	/**
	 * Get Prov. %.
	 * Commission stated as a percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCommission();

    /** Column definition for Commission */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_Commission = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "Commission", null);
    /** Column name Commission */
    public static final String COLUMNNAME_Commission = "Commission";

	/**
	 * Set Kosten pro Transaktion.
	 * Fixed cost per transaction
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCostPerTrx (java.math.BigDecimal CostPerTrx);

	/**
	 * Get Kosten pro Transaktion.
	 * Fixed cost per transaction
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCostPerTrx();

    /** Column definition for CostPerTrx */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_CostPerTrx = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "CostPerTrx", null);
    /** Column name CostPerTrx */
    public static final String COLUMNNAME_CostPerTrx = "CostPerTrx";

	/**
	 * Set Zahlungs-Prozessor.
	 * Payment processor for electronic payments
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_PaymentProcessor_ID (int C_PaymentProcessor_ID);

	/**
	 * Get Zahlungs-Prozessor.
	 * Payment processor for electronic payments
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_PaymentProcessor_ID();

    /** Column definition for C_PaymentProcessor_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_C_PaymentProcessor_ID = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "C_PaymentProcessor_ID", null);
    /** Column name C_PaymentProcessor_ID */
    public static final String COLUMNNAME_C_PaymentProcessor_ID = "C_PaymentProcessor_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, org.compiere.model.I_AD_User>(I_C_PaymentProcessor.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Host Address.
	 * Host Address URL or DNS
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHostAddress (java.lang.String HostAddress);

	/**
	 * Get Host Address.
	 * Host Address URL or DNS
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHostAddress();

    /** Column definition for HostAddress */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_HostAddress = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "HostAddress", null);
    /** Column name HostAddress */
    public static final String COLUMNNAME_HostAddress = "HostAddress";

	/**
	 * Set Host port.
	 * Host Communication Port
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHostPort (int HostPort);

	/**
	 * Get Host port.
	 * Host Communication Port
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getHostPort();

    /** Column definition for HostPort */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_HostPort = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "HostPort", null);
    /** Column name HostPort */
    public static final String COLUMNNAME_HostPort = "HostPort";

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
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Minimum Amt.
	 * Minumum Amout in Document Currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMinimumAmt (java.math.BigDecimal MinimumAmt);

	/**
	 * Get Minimum Amt.
	 * Minumum Amout in Document Currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getMinimumAmt();

    /** Column definition for MinimumAmt */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_MinimumAmt = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "MinimumAmt", null);
    /** Column name MinimumAmt */
    public static final String COLUMNNAME_MinimumAmt = "MinimumAmt";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Partner ID.
	 * Partner ID or Account for the Payment Processor
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPartnerID (java.lang.String PartnerID);

	/**
	 * Get Partner ID.
	 * Partner ID or Account for the Payment Processor
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPartnerID();

    /** Column definition for PartnerID */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_PartnerID = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "PartnerID", null);
    /** Column name PartnerID */
    public static final String COLUMNNAME_PartnerID = "PartnerID";

	/**
	 * Set Kennwort.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPassword (java.lang.String Password);

	/**
	 * Get Kennwort.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPassword();

    /** Column definition for Password */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_Password = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "Password", null);
    /** Column name Password */
    public static final String COLUMNNAME_Password = "Password";

	/**
	 * Set Payment Processor Class.
	 * Payment Processor Java Class
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPayProcessorClass (java.lang.String PayProcessorClass);

	/**
	 * Get Payment Processor Class.
	 * Payment Processor Java Class
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPayProcessorClass();

    /** Column definition for PayProcessorClass */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_PayProcessorClass = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "PayProcessorClass", null);
    /** Column name PayProcessorClass */
    public static final String COLUMNNAME_PayProcessorClass = "PayProcessorClass";

	/**
	 * Set Proxy address.
	 * Address of your proxy server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProxyAddress (java.lang.String ProxyAddress);

	/**
	 * Get Proxy address.
	 * Address of your proxy server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProxyAddress();

    /** Column definition for ProxyAddress */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_ProxyAddress = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "ProxyAddress", null);
    /** Column name ProxyAddress */
    public static final String COLUMNNAME_ProxyAddress = "ProxyAddress";

	/**
	 * Set Proxy logon.
	 * Logon of your proxy server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProxyLogon (java.lang.String ProxyLogon);

	/**
	 * Get Proxy logon.
	 * Logon of your proxy server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProxyLogon();

    /** Column definition for ProxyLogon */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_ProxyLogon = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "ProxyLogon", null);
    /** Column name ProxyLogon */
    public static final String COLUMNNAME_ProxyLogon = "ProxyLogon";

	/**
	 * Set Proxy-Passwort.
	 * Password of your proxy server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProxyPassword (java.lang.String ProxyPassword);

	/**
	 * Get Proxy-Passwort.
	 * Password of your proxy server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProxyPassword();

    /** Column definition for ProxyPassword */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_ProxyPassword = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "ProxyPassword", null);
    /** Column name ProxyPassword */
    public static final String COLUMNNAME_ProxyPassword = "ProxyPassword";

	/**
	 * Set Proxy port.
	 * Port of your proxy server
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProxyPort (int ProxyPort);

	/**
	 * Get Proxy port.
	 * Port of your proxy server
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getProxyPort();

    /** Column definition for ProxyPort */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_ProxyPort = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "ProxyPort", null);
    /** Column name ProxyPort */
    public static final String COLUMNNAME_ProxyPort = "ProxyPort";

	/**
	 * Set Require CreditCard Verification Code.
	 * Require 3/4 digit Credit Verification Code
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRequireVV (boolean RequireVV);

	/**
	 * Get Require CreditCard Verification Code.
	 * Require 3/4 digit Credit Verification Code
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isRequireVV();

    /** Column definition for RequireVV */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_RequireVV = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "RequireVV", null);
    /** Column name RequireVV */
    public static final String COLUMNNAME_RequireVV = "RequireVV";

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
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, org.compiere.model.I_AD_User>(I_C_PaymentProcessor.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set User ID.
	 * User ID or account number
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUserID (java.lang.String UserID);

	/**
	 * Get User ID.
	 * User ID or account number
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUserID();

    /** Column definition for UserID */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_UserID = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "UserID", null);
    /** Column name UserID */
    public static final String COLUMNNAME_UserID = "UserID";

	/**
	 * Set Vendor ID.
	 * Vendor ID for the Payment Processor
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVendorID (java.lang.String VendorID);

	/**
	 * Get Vendor ID.
	 * Vendor ID for the Payment Processor
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVendorID();

    /** Column definition for VendorID */
    public static final org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object> COLUMN_VendorID = new org.adempiere.model.ModelColumn<I_C_PaymentProcessor, Object>(I_C_PaymentProcessor.class, "VendorID", null);
    /** Column name VendorID */
    public static final String COLUMNNAME_VendorID = "VendorID";
}
