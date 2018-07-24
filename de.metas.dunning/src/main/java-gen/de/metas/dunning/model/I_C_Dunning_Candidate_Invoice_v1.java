package de.metas.dunning.model;


/** Generated Interface for C_Dunning_Candidate_Invoice_v1
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Dunning_Candidate_Invoice_v1 
{

    /** TableName=C_Dunning_Candidate_Invoice_v1 */
    public static final String Table_Name = "C_Dunning_Candidate_Invoice_v1";

    /** AD_Table_ID=540498 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_AD_Client>(I_C_Dunning_Candidate_Invoice_v1.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_AD_Org>(I_C_Dunning_Candidate_Invoice_v1.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User();

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_AD_User>(I_C_Dunning_Candidate_Invoice_v1.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_C_BPartner>(I_C_Dunning_Candidate_Invoice_v1.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifiziert die (Liefer-) Adresse des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_C_BPartner_Location>(I_C_Dunning_Candidate_Invoice_v1.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_C_Currency>(I_C_Dunning_Candidate_Invoice_v1.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Mahnung.
	 * Dunning Rules for overdue invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Dunning_ID (int C_Dunning_ID);

	/**
	 * Get Mahnung.
	 * Dunning Rules for overdue invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Dunning_ID();

	public org.compiere.model.I_C_Dunning getC_Dunning();

	public void setC_Dunning(org.compiere.model.I_C_Dunning C_Dunning);

    /** Column definition for C_Dunning_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_C_Dunning> COLUMN_C_Dunning_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_C_Dunning>(I_C_Dunning_Candidate_Invoice_v1.class, "C_Dunning_ID", org.compiere.model.I_C_Dunning.class);
    /** Column name C_Dunning_ID */
    public static final String COLUMNNAME_C_Dunning_ID = "C_Dunning_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_C_Invoice>(I_C_Dunning_Candidate_Invoice_v1.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Zahlungsplan.
	 * Zahlungsplan
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_InvoicePaySchedule_ID (int C_InvoicePaySchedule_ID);

	/**
	 * Get Zahlungsplan.
	 * Zahlungsplan
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_InvoicePaySchedule_ID();

	public org.compiere.model.I_C_InvoicePaySchedule getC_InvoicePaySchedule();

	public void setC_InvoicePaySchedule(org.compiere.model.I_C_InvoicePaySchedule C_InvoicePaySchedule);

    /** Column definition for C_InvoicePaySchedule_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_C_InvoicePaySchedule> COLUMN_C_InvoicePaySchedule_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_C_InvoicePaySchedule>(I_C_Dunning_Candidate_Invoice_v1.class, "C_InvoicePaySchedule_ID", org.compiere.model.I_C_InvoicePaySchedule.class);
    /** Column name C_InvoicePaySchedule_ID */
    public static final String COLUMNNAME_C_InvoicePaySchedule_ID = "C_InvoicePaySchedule_ID";

	/**
	 * Set Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_PaymentTerm_ID();

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm();

	public void setC_PaymentTerm(org.compiere.model.I_C_PaymentTerm C_PaymentTerm);

    /** Column definition for C_PaymentTerm_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_C_PaymentTerm> COLUMN_C_PaymentTerm_ID = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_C_PaymentTerm>(I_C_Dunning_Candidate_Invoice_v1.class, "C_PaymentTerm_ID", org.compiere.model.I_C_PaymentTerm.class);
    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/**
	 * Set Rechnungsdatum.
	 * Datum auf der Rechnung
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateInvoiced (java.sql.Timestamp DateInvoiced);

	/**
	 * Get Rechnungsdatum.
	 * Datum auf der Rechnung
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateInvoiced();

    /** Column definition for DateInvoiced */
    public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, Object> COLUMN_DateInvoiced = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, Object>(I_C_Dunning_Candidate_Invoice_v1.class, "DateInvoiced", null);
    /** Column name DateInvoiced */
    public static final String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/**
	 * Set Datum Fälligkeit.
	 * Datum, zu dem Zahlung fällig wird
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDueDate (java.sql.Timestamp DueDate);

	/**
	 * Get Datum Fälligkeit.
	 * Datum, zu dem Zahlung fällig wird
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDueDate();

    /** Column definition for DueDate */
    public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, Object> COLUMN_DueDate = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, Object>(I_C_Dunning_Candidate_Invoice_v1.class, "DueDate", null);
    /** Column name DueDate */
    public static final String COLUMNNAME_DueDate = "DueDate";

	/**
	 * Set Dunning Grace Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDunningGrace (java.sql.Timestamp DunningGrace);

	/**
	 * Get Dunning Grace Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDunningGrace();

    /** Column definition for DunningGrace */
    public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, Object> COLUMN_DunningGrace = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, Object>(I_C_Dunning_Candidate_Invoice_v1.class, "DunningGrace", null);
    /** Column name DunningGrace */
    public static final String COLUMNNAME_DunningGrace = "DunningGrace";

	/**
	 * Set Summe Gesamt.
	 * Summe über Alles zu diesem Beleg
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGrandTotal (java.math.BigDecimal GrandTotal);

	/**
	 * Get Summe Gesamt.
	 * Summe über Alles zu diesem Beleg
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getGrandTotal();

    /** Column definition for GrandTotal */
    public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, Object> COLUMN_GrandTotal = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, Object>(I_C_Dunning_Candidate_Invoice_v1.class, "GrandTotal", null);
    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";

	/**
	 * Set In Dispute.
	 * Document is in dispute
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsInDispute (boolean IsInDispute);

	/**
	 * Get In Dispute.
	 * Document is in dispute
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isInDispute();

    /** Column definition for IsInDispute */
    public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, Object> COLUMN_IsInDispute = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, Object>(I_C_Dunning_Candidate_Invoice_v1.class, "IsInDispute", null);
    /** Column name IsInDispute */
    public static final String COLUMNNAME_IsInDispute = "IsInDispute";

	/**
	 * Set Offener Betrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOpenAmt (java.math.BigDecimal OpenAmt);

	/**
	 * Get Offener Betrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getOpenAmt();

    /** Column definition for OpenAmt */
    public static final org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, Object> COLUMN_OpenAmt = new org.adempiere.model.ModelColumn<I_C_Dunning_Candidate_Invoice_v1, Object>(I_C_Dunning_Candidate_Invoice_v1.class, "OpenAmt", null);
    /** Column name OpenAmt */
    public static final String COLUMNNAME_OpenAmt = "OpenAmt";
}
