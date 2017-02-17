package org.compiere.model;


/** Generated Interface for AD_User
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_User 
{

    /** TableName=AD_User */
    public static final String Table_Name = "AD_User";

    /** AD_Table_ID=114 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

    /** Load Meta Data */

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
    public static final org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_Client>(I_AD_User.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_Org>(I_AD_User.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Buchende Organisation.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/**
	 * Get Buchende Organisation.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_OrgTrx_ID();

	public org.compiere.model.I_AD_Org getAD_OrgTrx();

	public void setAD_OrgTrx(org.compiere.model.I_AD_Org AD_OrgTrx);

    /** Column definition for AD_OrgTrx_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_Org> COLUMN_AD_OrgTrx_ID = new org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_Org>(I_AD_User.class, "AD_OrgTrx_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_OrgTrx_ID */
    public static final String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "AD_User_ID", null);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Betreuer.
	 * Person, die bei einem fachlichen Problem vom System informiert wird.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_InCharge_ID (int AD_User_InCharge_ID);

	/**
	 * Get Betreuer.
	 * Person, die bei einem fachlichen Problem vom System informiert wird.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_InCharge_ID();

	public org.compiere.model.I_AD_User getAD_User_InCharge();

	public void setAD_User_InCharge(org.compiere.model.I_AD_User AD_User_InCharge);

    /** Column definition for AD_User_InCharge_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_User> COLUMN_AD_User_InCharge_ID = new org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_User>(I_AD_User.class, "AD_User_InCharge_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_InCharge_ID */
    public static final String COLUMNNAME_AD_User_InCharge_ID = "AD_User_InCharge_ID";

	/**
	 * Set Geburtstag.
	 * Birthday or Anniversary day
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBirthday (java.sql.Timestamp Birthday);

	/**
	 * Get Geburtstag.
	 * Birthday or Anniversary day
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getBirthday();

    /** Column definition for Birthday */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Birthday = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Birthday", null);
    /** Column name Birthday */
    public static final String COLUMNNAME_Birthday = "Birthday";

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
    public static final org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_C_BPartner>(I_AD_User.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_C_BPartner_Location>(I_AD_User.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Anrede.
	 * Greeting to print on correspondence
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Greeting_ID (int C_Greeting_ID);

	/**
	 * Get Anrede.
	 * Greeting to print on correspondence
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Greeting_ID();

	public org.compiere.model.I_C_Greeting getC_Greeting();

	public void setC_Greeting(org.compiere.model.I_C_Greeting C_Greeting);

    /** Column definition for C_Greeting_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_C_Greeting> COLUMN_C_Greeting_ID = new org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_C_Greeting>(I_AD_User.class, "C_Greeting_ID", org.compiere.model.I_C_Greeting.class);
    /** Column name C_Greeting_ID */
    public static final String COLUMNNAME_C_Greeting_ID = "C_Greeting_ID";

	/**
	 * Set Position.
	 * Job Position
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Job_ID (int C_Job_ID);

	/**
	 * Get Position.
	 * Job Position
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Job_ID();

	public org.compiere.model.I_C_Job getC_Job();

	public void setC_Job(org.compiere.model.I_C_Job C_Job);

    /** Column definition for C_Job_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_C_Job> COLUMN_C_Job_ID = new org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_C_Job>(I_AD_User.class, "C_Job_ID", org.compiere.model.I_C_Job.class);
    /** Column name C_Job_ID */
    public static final String COLUMNNAME_C_Job_ID = "C_Job_ID";

	/**
	 * Set Bemerkungen.
	 * Comments or additional information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setComments (java.lang.String Comments);

	/**
	 * Get Bemerkungen.
	 * Comments or additional information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getComments();

    /** Column definition for Comments */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Comments = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Comments", null);
    /** Column name Comments */
    public static final String COLUMNNAME_Comments = "Comments";

	/**
	 * Set Verbindungsart.
	 * How a Java Client connects to the server(s)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setConnectionProfile (java.lang.String ConnectionProfile);

	/**
	 * Get Verbindungsart.
	 * How a Java Client connects to the server(s)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getConnectionProfile();

    /** Column definition for ConnectionProfile */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_ConnectionProfile = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "ConnectionProfile", null);
    /** Column name ConnectionProfile */
    public static final String COLUMNNAME_ConnectionProfile = "ConnectionProfile";

	/**
	 * Set Kontakt Einschränkung.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setContactLimitation (java.lang.String ContactLimitation);

	/**
	 * Get Kontakt Einschränkung.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getContactLimitation();

    /** Column definition for ContactLimitation */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_ContactLimitation = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "ContactLimitation", null);
    /** Column name ContactLimitation */
    public static final String COLUMNNAME_ContactLimitation = "ContactLimitation";

	/**
	 * Set Begründung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setContactLimitationReason (java.lang.String ContactLimitationReason);

	/**
	 * Get Begründung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getContactLimitationReason();

    /** Column definition for ContactLimitationReason */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_ContactLimitationReason = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "ContactLimitationReason", null);
    /** Column name ContactLimitationReason */
    public static final String COLUMNNAME_ContactLimitationReason = "ContactLimitationReason";

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
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_User>(I_AD_User.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Löschdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDelDate (java.sql.Timestamp DelDate);

	/**
	 * Get Löschdatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDelDate();

    /** Column definition for DelDate */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_DelDate = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "DelDate", null);
    /** Column name DelDate */
    public static final String COLUMNNAME_DelDate = "DelDate";

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
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set EMail.
	 * Electronic Mail Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMail (java.lang.String EMail);

	/**
	 * Get EMail.
	 * Electronic Mail Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMail();

    /** Column definition for EMail */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_EMail = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "EMail", null);
    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

	/**
	 * Set EMail Nutzer-ID.
	 * User Name (ID) in the Mail System
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMailUser (java.lang.String EMailUser);

	/**
	 * Get EMail Nutzer-ID.
	 * User Name (ID) in the Mail System
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMailUser();

    /** Column definition for EMailUser */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_EMailUser = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "EMailUser", null);
    /** Column name EMailUser */
    public static final String COLUMNNAME_EMailUser = "EMailUser";

	/**
	 * Set Passwort EMail-Nutzer.
	 * Passwort Ihrer EMail Nutzer-ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMailUserPW (java.lang.String EMailUserPW);

	/**
	 * Get Passwort EMail-Nutzer.
	 * Passwort Ihrer EMail Nutzer-ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMailUserPW();

    /** Column definition for EMailUserPW */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_EMailUserPW = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "EMailUserPW", null);
    /** Column name EMailUserPW */
    public static final String COLUMNNAME_EMailUserPW = "EMailUserPW";

	/**
	 * Set Überprüfung EMail.
	 * Verification information of EMail Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMailVerify (java.lang.String EMailVerify);

	/**
	 * Get Überprüfung EMail.
	 * Verification information of EMail Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMailVerify();

    /** Column definition for EMailVerify */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_EMailVerify = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "EMailVerify", null);
    /** Column name EMailVerify */
    public static final String COLUMNNAME_EMailVerify = "EMailVerify";

	/**
	 * Set EMail überprüft.
	 * Date Email was verified
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMailVerifyDate (java.sql.Timestamp EMailVerifyDate);

	/**
	 * Get EMail überprüft.
	 * Date Email was verified
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getEMailVerifyDate();

    /** Column definition for EMailVerifyDate */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_EMailVerifyDate = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "EMailVerifyDate", null);
    /** Column name EMailVerifyDate */
    public static final String COLUMNNAME_EMailVerifyDate = "EMailVerifyDate";

	/**
	 * Set Fax.
	 * Facsimile number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFax (java.lang.String Fax);

	/**
	 * Get Fax.
	 * Facsimile number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFax();

    /** Column definition for Fax */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Fax = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Fax", null);
    /** Column name Fax */
    public static final String COLUMNNAME_Fax = "Fax";

	/**
	 * Set Vorname.
	 * Vorname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFirstname (java.lang.String Firstname);

	/**
	 * Get Vorname.
	 * Vorname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFirstname();

    /** Column definition for Firstname */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Firstname = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Firstname", null);
    /** Column name Firstname */
    public static final String COLUMNNAME_Firstname = "Firstname";

	/**
	 * Set Weihnachtsgeschenk.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFresh_xmas_Gift (java.lang.String Fresh_xmas_Gift);

	/**
	 * Get Weihnachtsgeschenk.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFresh_xmas_Gift();

    /** Column definition for Fresh_xmas_Gift */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Fresh_xmas_Gift = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Fresh_xmas_Gift", null);
    /** Column name Fresh_xmas_Gift */
    public static final String COLUMNNAME_Fresh_xmas_Gift = "Fresh_xmas_Gift";

	/**
	 * Set Included Tab.
	 * Included Tab in this Tab (Master Dateail)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIncluded_Tab_ID (java.lang.String Included_Tab_ID);

	/**
	 * Get Included Tab.
	 * Included Tab in this Tab (Master Dateail)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIncluded_Tab_ID();

    /** Column definition for Included_Tab_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Included_Tab_ID = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Included_Tab_ID", null);
    /** Column name Included_Tab_ID */
    public static final String COLUMNNAME_Included_Tab_ID = "Included_Tab_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Standard-Ansprechpartner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDefaultContact (boolean IsDefaultContact);

	/**
	 * Get Standard-Ansprechpartner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDefaultContact();

    /** Column definition for IsDefaultContact */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsDefaultContact = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsDefaultContact", null);
    /** Column name IsDefaultContact */
    public static final String COLUMNNAME_IsDefaultContact = "IsDefaultContact";

	/**
	 * Set Full BP Access.
	 * The user/contact has full access to Business Partner information and resources
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsFullBPAccess (boolean IsFullBPAccess);

	/**
	 * Get Full BP Access.
	 * The user/contact has full access to Business Partner information and resources
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isFullBPAccess();

    /** Column definition for IsFullBPAccess */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsFullBPAccess = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsFullBPAccess", null);
    /** Column name IsFullBPAccess */
    public static final String COLUMNNAME_IsFullBPAccess = "IsFullBPAccess";

	/**
	 * Set Is In Payroll.
	 * Defined if any User Contact will be used for Calculate Payroll
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInPayroll (boolean IsInPayroll);

	/**
	 * Get Is In Payroll.
	 * Defined if any User Contact will be used for Calculate Payroll
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInPayroll();

    /** Column definition for IsInPayroll */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsInPayroll = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsInPayroll", null);
    /** Column name IsInPayroll */
    public static final String COLUMNNAME_IsInPayroll = "IsInPayroll";

	/**
	 * Set Mengenmeldung-WebUI.
	 * Entscheidet, ob sich der betreffende Nutzer, sofern eine Mail-Adresse und eine Liefervereinbarung hinterlegt ist, bei der Mengenmeldung-WebUI anmelden kann
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsMFProcurementUser (boolean IsMFProcurementUser);

	/**
	 * Get Mengenmeldung-WebUI.
	 * Entscheidet, ob sich der betreffende Nutzer, sofern eine Mail-Adresse und eine Liefervereinbarung hinterlegt ist, bei der Mengenmeldung-WebUI anmelden kann
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isMFProcurementUser();

    /** Column definition for IsMFProcurementUser */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsMFProcurementUser = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsMFProcurementUser", null);
    /** Column name IsMFProcurementUser */
    public static final String COLUMNNAME_IsMFProcurementUser = "IsMFProcurementUser";

	/**
	 * Set Einkaufskontakt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsPurchaseContact (boolean IsPurchaseContact);

	/**
	 * Get Einkaufskontakt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isPurchaseContact();

    /** Column definition for IsPurchaseContact */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsPurchaseContact = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsPurchaseContact", null);
    /** Column name IsPurchaseContact */
    public static final String COLUMNNAME_IsPurchaseContact = "IsPurchaseContact";

	/**
	 * Set IsPurchaseContact_Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPurchaseContact_Default (boolean IsPurchaseContact_Default);

	/**
	 * Get IsPurchaseContact_Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPurchaseContact_Default();

    /** Column definition for IsPurchaseContact_Default */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsPurchaseContact_Default = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsPurchaseContact_Default", null);
    /** Column name IsPurchaseContact_Default */
    public static final String COLUMNNAME_IsPurchaseContact_Default = "IsPurchaseContact_Default";

	/**
	 * Set IsSalesContact.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsSalesContact (boolean IsSalesContact);

	/**
	 * Get IsSalesContact.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isSalesContact();

    /** Column definition for IsSalesContact */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsSalesContact = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsSalesContact", null);
    /** Column name IsSalesContact */
    public static final String COLUMNNAME_IsSalesContact = "IsSalesContact";

	/**
	 * Set IsSalesContact_Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSalesContact_Default (boolean IsSalesContact_Default);

	/**
	 * Get IsSalesContact_Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSalesContact_Default();

    /** Column definition for IsSalesContact_Default */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsSalesContact_Default = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsSalesContact_Default", null);
    /** Column name IsSalesContact_Default */
    public static final String COLUMNNAME_IsSalesContact_Default = "IsSalesContact_Default";

	/**
	 * Set IsSubjectMatterContact.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsSubjectMatterContact (boolean IsSubjectMatterContact);

	/**
	 * Get IsSubjectMatterContact.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isSubjectMatterContact();

    /** Column definition for IsSubjectMatterContact */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsSubjectMatterContact = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsSubjectMatterContact", null);
    /** Column name IsSubjectMatterContact */
    public static final String COLUMNNAME_IsSubjectMatterContact = "IsSubjectMatterContact";

	/**
	 * Set Nachname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastname (java.lang.String Lastname);

	/**
	 * Get Nachname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLastname();

    /** Column definition for Lastname */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Lastname = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Lastname", null);
    /** Column name Lastname */
    public static final String COLUMNNAME_Lastname = "Lastname";

	/**
	 * Set Berechtigen über LDAP.
	 * User Name used for authorization via LDAP (directory) services
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLDAPUser (java.lang.String LDAPUser);

	/**
	 * Get Berechtigen über LDAP.
	 * User Name used for authorization via LDAP (directory) services
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLDAPUser();

    /** Column definition for LDAPUser */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_LDAPUser = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "LDAPUser", null);
    /** Column name LDAPUser */
    public static final String COLUMNNAME_LDAPUser = "LDAPUser";

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
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Benachrichtigungs-Art.
	 * Type of Notifications
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setNotificationType (java.lang.String NotificationType);

	/**
	 * Get Benachrichtigungs-Art.
	 * Type of Notifications
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getNotificationType();

    /** Column definition for NotificationType */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_NotificationType = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "NotificationType", null);
    /** Column name NotificationType */
    public static final String COLUMNNAME_NotificationType = "NotificationType";

	/**
	 * Set Kennwort.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPassword (java.lang.String Password);

	/**
	 * Get Kennwort.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPassword();

    /** Column definition for Password */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Password = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Password", null);
    /** Column name Password */
    public static final String COLUMNNAME_Password = "Password";

	/**
	 * Set Portalpasswort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setpasswordportal (java.lang.String passwordportal);

	/**
	 * Get Portalpasswort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getpasswordportal();

    /** Column definition for passwordportal */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_passwordportal = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "passwordportal", null);
    /** Column name passwordportal */
    public static final String COLUMNNAME_passwordportal = "passwordportal";

	/**
	 * Set Code für Passwort-Änderung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPasswordResetCode (java.lang.String PasswordResetCode);

	/**
	 * Get Code für Passwort-Änderung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPasswordResetCode();

    /** Column definition for PasswordResetCode */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_PasswordResetCode = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "PasswordResetCode", null);
    /** Column name PasswordResetCode */
    public static final String COLUMNNAME_PasswordResetCode = "PasswordResetCode";

	/**
	 * Set Phone.
	 * Identifies a telephone number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPhone (java.lang.String Phone);

	/**
	 * Get Phone.
	 * Identifies a telephone number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPhone();

    /** Column definition for Phone */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Phone = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Phone", null);
    /** Column name Phone */
    public static final String COLUMNNAME_Phone = "Phone";

	/**
	 * Set Telefon (alternativ).
	 * Identifies an alternate telephone number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPhone2 (java.lang.String Phone2);

	/**
	 * Get Telefon (alternativ).
	 * Identifies an alternate telephone number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPhone2();

    /** Column definition for Phone2 */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Phone2 = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Phone2", null);
    /** Column name Phone2 */
    public static final String COLUMNNAME_Phone2 = "Phone2";

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
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Handelsregister.
	 * Handelsregister
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRegistry (java.lang.String Registry);

	/**
	 * Get Handelsregister.
	 * Handelsregister
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRegistry();

    /** Column definition for Registry */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Registry = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Registry", null);
    /** Column name Registry */
    public static final String COLUMNNAME_Registry = "Registry";

	/**
	 * Set Vorgesetzter.
	 * Supervisor for this user/organization - used for escalation and approval
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSupervisor_ID (int Supervisor_ID);

	/**
	 * Get Vorgesetzter.
	 * Supervisor for this user/organization - used for escalation and approval
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSupervisor_ID();

	public org.compiere.model.I_AD_User getSupervisor();

	public void setSupervisor(org.compiere.model.I_AD_User Supervisor);

    /** Column definition for Supervisor_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_User> COLUMN_Supervisor_ID = new org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_User>(I_AD_User.class, "Supervisor_ID", org.compiere.model.I_AD_User.class);
    /** Column name Supervisor_ID */
    public static final String COLUMNNAME_Supervisor_ID = "Supervisor_ID";

	/**
	 * Set Titel.
	 * Name this entity is referred to as
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTitle (java.lang.String Title);

	/**
	 * Get Titel.
	 * Name this entity is referred to as
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTitle();

    /** Column definition for Title */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Title = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Title", null);
    /** Column name Title */
    public static final String COLUMNNAME_Title = "Title";

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
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_User>(I_AD_User.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set UserPIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUserPIN (java.lang.String UserPIN);

	/**
	 * Get UserPIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUserPIN();

    /** Column definition for UserPIN */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_UserPIN = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "UserPIN", null);
    /** Column name UserPIN */
    public static final String COLUMNNAME_UserPIN = "UserPIN";

	/**
	 * Set Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
