package de.metas.datev.model;


/** Generated Interface for RV_DATEV_Export_Fact_Acct_Invoice
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_RV_DATEV_Export_Fact_Acct_Invoice 
{

    /** TableName=RV_DATEV_Export_Fact_Acct_Invoice */
    public static final String Table_Name = "RV_DATEV_Export_Fact_Acct_Invoice";

    /** AD_Table_ID=540936 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Activity Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setActivityName (java.lang.String ActivityName);

	/**
	 * Get Activity Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getActivityName();

    /** Column definition for ActivityName */
    public static final org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object> COLUMN_ActivityName = new org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object>(I_RV_DATEV_Export_Fact_Acct_Invoice.class, "ActivityName", null);
    /** Column name ActivityName */
    public static final String COLUMNNAME_ActivityName = "ActivityName";

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
    public static final org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, org.compiere.model.I_AD_Client>(I_RV_DATEV_Export_Fact_Acct_Invoice.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, org.compiere.model.I_AD_Org>(I_RV_DATEV_Export_Fact_Acct_Invoice.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Betrag.
	 * Betrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAmt (java.math.BigDecimal Amt);

	/**
	 * Get Betrag.
	 * Betrag
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getAmt();

    /** Column definition for Amt */
    public static final org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object> COLUMN_Amt = new org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object>(I_RV_DATEV_Export_Fact_Acct_Invoice.class, "Amt", null);
    /** Column name Amt */
    public static final String COLUMNNAME_Amt = "Amt";

	/**
	 * Set Name.
	 * Name des Sponsors.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPName (java.lang.String BPName);

	/**
	 * Get Name.
	 * Name des Sponsors.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPName();

    /** Column definition for BPName */
    public static final org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object> COLUMN_BPName = new org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object>(I_RV_DATEV_Export_Fact_Acct_Invoice.class, "BPName", null);
    /** Column name BPName */
    public static final String COLUMNNAME_BPName = "BPName";

	/**
	 * Set Nr..
	 * Sponsor-Nr.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPValue (java.lang.String BPValue);

	/**
	 * Get Nr..
	 * Sponsor-Nr.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPValue();

    /** Column definition for BPValue */
    public static final org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object> COLUMN_BPValue = new org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object>(I_RV_DATEV_Export_Fact_Acct_Invoice.class, "BPValue", null);
    /** Column name BPValue */
    public static final String COLUMNNAME_BPValue = "BPValue";

	/**
	 * Set Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Activity_ID();

	public org.compiere.model.I_C_Activity getC_Activity();

	public void setC_Activity(org.compiere.model.I_C_Activity C_Activity);

    /** Column definition for C_Activity_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, org.compiere.model.I_C_Activity> COLUMN_C_Activity_ID = new org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, org.compiere.model.I_C_Activity>(I_RV_DATEV_Export_Fact_Acct_Invoice.class, "C_Activity_ID", org.compiere.model.I_C_Activity.class);
    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

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
    public static final org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, org.compiere.model.I_C_BPartner>(I_RV_DATEV_Export_Fact_Acct_Invoice.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

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
    public static final org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, org.compiere.model.I_C_Invoice>(I_RV_DATEV_Export_Fact_Acct_Invoice.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Credit Account.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCR_Account (java.lang.String CR_Account);

	/**
	 * Get Credit Account.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCR_Account();

    /** Column definition for CR_Account */
    public static final org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object> COLUMN_CR_Account = new org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object>(I_RV_DATEV_Export_Fact_Acct_Invoice.class, "CR_Account", null);
    /** Column name CR_Account */
    public static final String COLUMNNAME_CR_Account = "CR_Account";

	/**
	 * Set Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Buchungsdatum.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateAcct();

    /** Column definition for DateAcct */
    public static final org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object> COLUMN_DateAcct = new org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object>(I_RV_DATEV_Export_Fact_Acct_Invoice.class, "DateAcct", null);
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
    public static final org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object>(I_RV_DATEV_Export_Fact_Acct_Invoice.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Dokument Basis Typ.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocBaseType (java.lang.String DocBaseType);

	/**
	 * Get Dokument Basis Typ.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocBaseType();

    /** Column definition for DocBaseType */
    public static final org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object> COLUMN_DocBaseType = new org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object>(I_RV_DATEV_Export_Fact_Acct_Invoice.class, "DocBaseType", null);
    /** Column name DocBaseType */
    public static final String COLUMNNAME_DocBaseType = "DocBaseType";

	/**
	 * Set Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Nr..
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentNo();

    /** Column definition for DocumentNo */
    public static final org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object> COLUMN_DocumentNo = new org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object>(I_RV_DATEV_Export_Fact_Acct_Invoice.class, "DocumentNo", null);
    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Debit Account.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDR_Account (java.lang.String DR_Account);

	/**
	 * Get Debit Account.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDR_Account();

    /** Column definition for DR_Account */
    public static final org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object> COLUMN_DR_Account = new org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object>(I_RV_DATEV_Export_Fact_Acct_Invoice.class, "DR_Account", null);
    /** Column name DR_Account */
    public static final String COLUMNNAME_DR_Account = "DR_Account";

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
    public static final org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object> COLUMN_DueDate = new org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object>(I_RV_DATEV_Export_Fact_Acct_Invoice.class, "DueDate", null);
    /** Column name DueDate */
    public static final String COLUMNNAME_DueDate = "DueDate";

	/**
	 * Set Accounting Fact.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFact_Acct_ID (int Fact_Acct_ID);

	/**
	 * Get Accounting Fact.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getFact_Acct_ID();

	public org.compiere.model.I_Fact_Acct getFact_Acct();

	public void setFact_Acct(org.compiere.model.I_Fact_Acct Fact_Acct);

    /** Column definition for Fact_Acct_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, org.compiere.model.I_Fact_Acct> COLUMN_Fact_Acct_ID = new org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, org.compiere.model.I_Fact_Acct>(I_RV_DATEV_Export_Fact_Acct_Invoice.class, "Fact_Acct_ID", org.compiere.model.I_Fact_Acct.class);
    /** Column name Fact_Acct_ID */
    public static final String COLUMNNAME_Fact_Acct_ID = "Fact_Acct_ID";

	/**
	 * Set RV_DATEV_Export_Fact_Acct_Invoice.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRV_DATEV_Export_Fact_Acct_Invoice_ID (int RV_DATEV_Export_Fact_Acct_Invoice_ID);

	/**
	 * Get RV_DATEV_Export_Fact_Acct_Invoice.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRV_DATEV_Export_Fact_Acct_Invoice_ID();

    /** Column definition for RV_DATEV_Export_Fact_Acct_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object> COLUMN_RV_DATEV_Export_Fact_Acct_Invoice_ID = new org.adempiere.model.ModelColumn<I_RV_DATEV_Export_Fact_Acct_Invoice, Object>(I_RV_DATEV_Export_Fact_Acct_Invoice.class, "RV_DATEV_Export_Fact_Acct_Invoice_ID", null);
    /** Column name RV_DATEV_Export_Fact_Acct_Invoice_ID */
    public static final String COLUMNNAME_RV_DATEV_Export_Fact_Acct_Invoice_ID = "RV_DATEV_Export_Fact_Acct_Invoice_ID";
}
