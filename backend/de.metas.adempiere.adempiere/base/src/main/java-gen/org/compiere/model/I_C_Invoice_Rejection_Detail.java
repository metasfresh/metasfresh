package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_Invoice_Rejection_Detail
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Invoice_Rejection_Detail 
{

    /** TableName=C_Invoice_Rejection_Detail */
    public static final String Table_Name = "C_Invoice_Rejection_Detail";

    /** AD_Table_ID=541365 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, org.compiere.model.I_AD_Client>(I_C_Invoice_Rejection_Detail.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, org.compiere.model.I_AD_Org>(I_C_Invoice_Rejection_Detail.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Rechnung.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice();

	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

    /** Column definition for C_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, org.compiere.model.I_C_Invoice>(I_C_Invoice_Rejection_Detail.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Zurückweisungsdetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Rejection_Detail_ID (int C_Invoice_Rejection_Detail_ID);

	/**
	 * Get Zurückweisungsdetail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Rejection_Detail_ID();

    /** Column definition for C_Invoice_Rejection_Detail_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object> COLUMN_C_Invoice_Rejection_Detail_ID = new org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object>(I_C_Invoice_Rejection_Detail.class, "C_Invoice_Rejection_Detail_ID", null);
    /** Column name C_Invoice_Rejection_Detail_ID */
    public static final String COLUMNNAME_C_Invoice_Rejection_Detail_ID = "C_Invoice_Rejection_Detail_ID";

	/**
	 * Set Klient/in.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setClient (java.lang.String Client);

	/**
	 * Get Klient/in.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getClient();

    /** Column definition for Client */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object> COLUMN_Client = new org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object>(I_C_Invoice_Rejection_Detail.class, "Client", null);
    /** Column name Client */
    public static final String COLUMNNAME_Client = "Client";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object>(I_C_Invoice_Rejection_Detail.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, org.compiere.model.I_AD_User>(I_C_Invoice_Rejection_Detail.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set eMail.
	 * EMail-Adresse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMail (java.lang.String EMail);

	/**
	 * Get eMail.
	 * EMail-Adresse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMail();

    /** Column definition for EMail */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object> COLUMN_EMail = new org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object>(I_C_Invoice_Rejection_Detail.class, "EMail", null);
    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

	/**
	 * Set Erklärung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExplanation (java.lang.String Explanation);

	/**
	 * Get Erklärung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExplanation();

    /** Column definition for Explanation */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object> COLUMN_Explanation = new org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object>(I_C_Invoice_Rejection_Detail.class, "Explanation", null);
    /** Column name Explanation */
    public static final String COLUMNNAME_Explanation = "Explanation";

	/**
	 * Set Rechnung Nr.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInvoiceNumber (java.lang.String InvoiceNumber);

	/**
	 * Get Rechnung Nr.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInvoiceNumber();

    /** Column definition for InvoiceNumber */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object> COLUMN_InvoiceNumber = new org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object>(I_C_Invoice_Rejection_Detail.class, "InvoiceNumber", null);
    /** Column name InvoiceNumber */
    public static final String COLUMNNAME_InvoiceNumber = "InvoiceNumber";

	/**
	 * Set Rechnungsempfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInvoiceRecipient (java.lang.String InvoiceRecipient);

	/**
	 * Get Rechnungsempfänger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInvoiceRecipient();

    /** Column definition for InvoiceRecipient */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object> COLUMN_InvoiceRecipient = new org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object>(I_C_Invoice_Rejection_Detail.class, "InvoiceRecipient", null);
    /** Column name InvoiceRecipient */
    public static final String COLUMNNAME_InvoiceRecipient = "InvoiceRecipient";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object>(I_C_Invoice_Rejection_Detail.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Erledigt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDone (boolean IsDone);

	/**
	 * Get Erledigt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDone();

    /** Column definition for IsDone */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object> COLUMN_IsDone = new org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object>(I_C_Invoice_Rejection_Detail.class, "IsDone", null);
    /** Column name IsDone */
    public static final String COLUMNNAME_IsDone = "IsDone";

	/**
	 * Set Telefon.
	 * Beschreibt eine Telefon Nummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPhone (java.lang.String Phone);

	/**
	 * Get Telefon.
	 * Beschreibt eine Telefon Nummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPhone();

    /** Column definition for Phone */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object> COLUMN_Phone = new org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object>(I_C_Invoice_Rejection_Detail.class, "Phone", null);
    /** Column name Phone */
    public static final String COLUMNNAME_Phone = "Phone";

	/**
	 * Set Grund.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReason (java.lang.String Reason);

	/**
	 * Get Grund.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReason();

    /** Column definition for Reason */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object> COLUMN_Reason = new org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object>(I_C_Invoice_Rejection_Detail.class, "Reason", null);
    /** Column name Reason */
    public static final String COLUMNNAME_Reason = "Reason";

	/**
	 * Set Sachbearbeiter.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setResponsiblePerson (java.lang.String ResponsiblePerson);

	/**
	 * Get Sachbearbeiter.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getResponsiblePerson();

    /** Column definition for ResponsiblePerson */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object> COLUMN_ResponsiblePerson = new org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object>(I_C_Invoice_Rejection_Detail.class, "ResponsiblePerson", null);
    /** Column name ResponsiblePerson */
    public static final String COLUMNNAME_ResponsiblePerson = "ResponsiblePerson";

	/**
	 * Set Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStatus (java.lang.String Status);

	/**
	 * Get Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStatus();

    /** Column definition for Status */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object> COLUMN_Status = new org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object>(I_C_Invoice_Rejection_Detail.class, "Status", null);
    /** Column name Status */
    public static final String COLUMNNAME_Status = "Status";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, Object>(I_C_Invoice_Rejection_Detail.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Invoice_Rejection_Detail, org.compiere.model.I_AD_User>(I_C_Invoice_Rejection_Detail.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Date received.
	 * Date a product was received
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateReceived (@Nullable java.sql.Timestamp DateReceived);

	/**
	 * Get Date received.
	 * Date a product was received
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateReceived();

	ModelColumn<I_C_Invoice_Rejection_Detail, Object> COLUMN_DateReceived = new ModelColumn<>(I_C_Invoice_Rejection_Detail.class, "DateReceived", null);
	String COLUMNNAME_DateReceived = "DateReceived";
}
