package org.compiere.model;


/** Generated Interface for C_Mail
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Mail 
{

    /** TableName=C_Mail */
    public static final String Table_Name = "C_Mail";

    /** AD_Table_ID=541012 */
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
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Mail, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Mail, org.compiere.model.I_AD_Client>(I_C_Mail.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Mail, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Mail, org.compiere.model.I_AD_Org>(I_C_Mail.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Mail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Mail_ID (int C_Mail_ID);

	/**
	 * Get Mail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Mail_ID();

    /** Column definition for C_Mail_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Mail, Object> COLUMN_C_Mail_ID = new org.adempiere.model.ModelColumn<I_C_Mail, Object>(I_C_Mail.class, "C_Mail_ID", null);
    /** Column name C_Mail_ID */
    public static final String COLUMNNAME_C_Mail_ID = "C_Mail_ID";

	/**
	 * Set Inhalt.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setContentText (java.lang.String ContentText);

	/**
	 * Get Inhalt.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getContentText();

    /** Column definition for ContentText */
    public static final org.adempiere.model.ModelColumn<I_C_Mail, Object> COLUMN_ContentText = new org.adempiere.model.ModelColumn<I_C_Mail, Object>(I_C_Mail.class, "ContentText", null);
    /** Column name ContentText */
    public static final String COLUMNNAME_ContentText = "ContentText";

	/**
	 * Set Content type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setContentType (java.lang.String ContentType);

	/**
	 * Get Content type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getContentType();

    /** Column definition for ContentType */
    public static final org.adempiere.model.ModelColumn<I_C_Mail, Object> COLUMN_ContentType = new org.adempiere.model.ModelColumn<I_C_Mail, Object>(I_C_Mail.class, "ContentType", null);
    /** Column name ContentType */
    public static final String COLUMNNAME_ContentType = "ContentType";

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
    public static final org.adempiere.model.ModelColumn<I_C_Mail, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Mail, Object>(I_C_Mail.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Mail, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Mail, org.compiere.model.I_AD_User>(I_C_Mail.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Eingangsdatum.
	 * Datum, zu dem ein Produkt empfangen wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateReceived (java.sql.Timestamp DateReceived);

	/**
	 * Get Eingangsdatum.
	 * Datum, zu dem ein Produkt empfangen wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateReceived();

    /** Column definition for DateReceived */
    public static final org.adempiere.model.ModelColumn<I_C_Mail, Object> COLUMN_DateReceived = new org.adempiere.model.ModelColumn<I_C_Mail, Object>(I_C_Mail.class, "DateReceived", null);
    /** Column name DateReceived */
    public static final String COLUMNNAME_DateReceived = "DateReceived";

	/**
	 * Set EMail Bcc.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMail_Bcc (java.lang.String EMail_Bcc);

	/**
	 * Get EMail Bcc.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMail_Bcc();

    /** Column definition for EMail_Bcc */
    public static final org.adempiere.model.ModelColumn<I_C_Mail, Object> COLUMN_EMail_Bcc = new org.adempiere.model.ModelColumn<I_C_Mail, Object>(I_C_Mail.class, "EMail_Bcc", null);
    /** Column name EMail_Bcc */
    public static final String COLUMNNAME_EMail_Bcc = "EMail_Bcc";

	/**
	 * Set EMail Cc.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMail_Cc (java.lang.String EMail_Cc);

	/**
	 * Get EMail Cc.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMail_Cc();

    /** Column definition for EMail_Cc */
    public static final org.adempiere.model.ModelColumn<I_C_Mail, Object> COLUMN_EMail_Cc = new org.adempiere.model.ModelColumn<I_C_Mail, Object>(I_C_Mail.class, "EMail_Cc", null);
    /** Column name EMail_Cc */
    public static final String COLUMNNAME_EMail_Cc = "EMail_Cc";

	/**
	 * Set EMail Absender.
	 * Full EMail address used to send requests - e.g. edi@organization.com
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMail_From (java.lang.String EMail_From);

	/**
	 * Get EMail Absender.
	 * Full EMail address used to send requests - e.g. edi@organization.com
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMail_From();

    /** Column definition for EMail_From */
    public static final org.adempiere.model.ModelColumn<I_C_Mail, Object> COLUMN_EMail_From = new org.adempiere.model.ModelColumn<I_C_Mail, Object>(I_C_Mail.class, "EMail_From", null);
    /** Column name EMail_From */
    public static final String COLUMNNAME_EMail_From = "EMail_From";

	/**
	 * Set EMail Empfänger.
	 * EMail address to send requests to - e.g. edi@manufacturer.com
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMail_To (java.lang.String EMail_To);

	/**
	 * Get EMail Empfänger.
	 * EMail address to send requests to - e.g. edi@manufacturer.com
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMail_To();

    /** Column definition for EMail_To */
    public static final org.adempiere.model.ModelColumn<I_C_Mail, Object> COLUMN_EMail_To = new org.adempiere.model.ModelColumn<I_C_Mail, Object>(I_C_Mail.class, "EMail_To", null);
    /** Column name EMail_To */
    public static final String COLUMNNAME_EMail_To = "EMail_To";

	/**
	 * Set EMail Headers.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMailHeadersJSON (java.lang.String EMailHeadersJSON);

	/**
	 * Get EMail Headers.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMailHeadersJSON();

    /** Column definition for EMailHeadersJSON */
    public static final org.adempiere.model.ModelColumn<I_C_Mail, Object> COLUMN_EMailHeadersJSON = new org.adempiere.model.ModelColumn<I_C_Mail, Object>(I_C_Mail.class, "EMailHeadersJSON", null);
    /** Column name EMailHeadersJSON */
    public static final String COLUMNNAME_EMailHeadersJSON = "EMailHeadersJSON";

	/**
	 * Set From User.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFrom_User_ID (int From_User_ID);

	/**
	 * Get From User.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getFrom_User_ID();

	public org.compiere.model.I_AD_User getFrom_User();

	public void setFrom_User(org.compiere.model.I_AD_User From_User);

    /** Column definition for From_User_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Mail, org.compiere.model.I_AD_User> COLUMN_From_User_ID = new org.adempiere.model.ModelColumn<I_C_Mail, org.compiere.model.I_AD_User>(I_C_Mail.class, "From_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name From_User_ID */
    public static final String COLUMNNAME_From_User_ID = "From_User_ID";

	/**
	 * Set Initial Message-ID.
	 * EMail Initial Message-ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInitialMessageID (java.lang.String InitialMessageID);

	/**
	 * Get Initial Message-ID.
	 * EMail Initial Message-ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInitialMessageID();

    /** Column definition for InitialMessageID */
    public static final org.adempiere.model.ModelColumn<I_C_Mail, Object> COLUMN_InitialMessageID = new org.adempiere.model.ModelColumn<I_C_Mail, Object>(I_C_Mail.class, "InitialMessageID", null);
    /** Column name InitialMessageID */
    public static final String COLUMNNAME_InitialMessageID = "InitialMessageID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Mail, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Mail, Object>(I_C_Mail.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Inbound EMail.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInboundEMail (boolean IsInboundEMail);

	/**
	 * Get Inbound EMail.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInboundEMail();

    /** Column definition for IsInboundEMail */
    public static final org.adempiere.model.ModelColumn<I_C_Mail, Object> COLUMN_IsInboundEMail = new org.adempiere.model.ModelColumn<I_C_Mail, Object>(I_C_Mail.class, "IsInboundEMail", null);
    /** Column name IsInboundEMail */
    public static final String COLUMNNAME_IsInboundEMail = "IsInboundEMail";

	/**
	 * Set Message-ID.
	 * EMail Message-ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMessageID (java.lang.String MessageID);

	/**
	 * Get Message-ID.
	 * EMail Message-ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMessageID();

    /** Column definition for MessageID */
    public static final org.adempiere.model.ModelColumn<I_C_Mail, Object> COLUMN_MessageID = new org.adempiere.model.ModelColumn<I_C_Mail, Object>(I_C_Mail.class, "MessageID", null);
    /** Column name MessageID */
    public static final String COLUMNNAME_MessageID = "MessageID";

	/**
	 * Set Aufgabe.
	 * Request from a Business Partner or Prospect
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_Request_ID (int R_Request_ID);

	/**
	 * Get Aufgabe.
	 * Request from a Business Partner or Prospect
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getR_Request_ID();

	public org.compiere.model.I_R_Request getR_Request();

	public void setR_Request(org.compiere.model.I_R_Request R_Request);

    /** Column definition for R_Request_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Mail, org.compiere.model.I_R_Request> COLUMN_R_Request_ID = new org.adempiere.model.ModelColumn<I_C_Mail, org.compiere.model.I_R_Request>(I_C_Mail.class, "R_Request_ID", org.compiere.model.I_R_Request.class);
    /** Column name R_Request_ID */
    public static final String COLUMNNAME_R_Request_ID = "R_Request_ID";

	/**
	 * Set Betreff.
	 * Mail Betreff
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSubject (java.lang.String Subject);

	/**
	 * Get Betreff.
	 * Mail Betreff
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSubject();

    /** Column definition for Subject */
    public static final org.adempiere.model.ModelColumn<I_C_Mail, Object> COLUMN_Subject = new org.adempiere.model.ModelColumn<I_C_Mail, Object>(I_C_Mail.class, "Subject", null);
    /** Column name Subject */
    public static final String COLUMNNAME_Subject = "Subject";

	/**
	 * Set To User.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTo_User_ID (int To_User_ID);

	/**
	 * Get To User.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getTo_User_ID();

	public org.compiere.model.I_AD_User getTo_User();

	public void setTo_User(org.compiere.model.I_AD_User To_User);

    /** Column definition for To_User_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Mail, org.compiere.model.I_AD_User> COLUMN_To_User_ID = new org.adempiere.model.ModelColumn<I_C_Mail, org.compiere.model.I_AD_User>(I_C_Mail.class, "To_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name To_User_ID */
    public static final String COLUMNNAME_To_User_ID = "To_User_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Mail, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Mail, Object>(I_C_Mail.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Mail, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Mail, org.compiere.model.I_AD_User>(I_C_Mail.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
