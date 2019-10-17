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
	 * Set Sprache.
	 * Sprache für diesen Eintrag
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Language (java.lang.String AD_Language);

	/**
	 * Get Sprache.
	 * Sprache für diesen Eintrag
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAD_Language();

    /** Column definition for AD_Language */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_AD_Language = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "AD_Language", null);
    /** Column name AD_Language */
    public static final String COLUMNNAME_AD_Language = "AD_Language";

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
	 * Set Adresse.
	 * Anschrift
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setAddress (java.lang.String Address);

	/**
	 * Get Adresse.
	 * Anschrift
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getAddress();

    /** Column definition for Address */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Address = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Address", null);
    /** Column name Address */
    public static final String COLUMNNAME_Address = "Address";

	/**
	 * Set Avatar.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAvatar_ID (int Avatar_ID);

	/**
	 * Get Avatar.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAvatar_ID();

	public org.compiere.model.I_AD_Image getAvatar();

	public void setAvatar(org.compiere.model.I_AD_Image Avatar);

    /** Column definition for Avatar_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_Image> COLUMN_Avatar_ID = new org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_Image>(I_AD_User.class, "Avatar_ID", org.compiere.model.I_AD_Image.class);
    /** Column name Avatar_ID */
    public static final String COLUMNNAME_Avatar_ID = "Avatar_ID";

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
	 * Set Partner Parent.
	 * Business Partner Parent
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setBPartner_Parent_ID (int BPartner_Parent_ID);

	/**
	 * Get Partner Parent.
	 * Business Partner Parent
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getBPartner_Parent_ID();

	public org.compiere.model.I_C_BPartner getBPartner_Parent();

	@Deprecated
	public void setBPartner_Parent(org.compiere.model.I_C_BPartner BPartner_Parent);

    /** Column definition for BPartner_Parent_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_C_BPartner> COLUMN_BPartner_Parent_ID = new org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_C_BPartner>(I_AD_User.class, "BPartner_Parent_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name BPartner_Parent_ID */
    public static final String COLUMNNAME_BPartner_Parent_ID = "BPartner_Parent_ID";

	/**
	 * Set Geschäftspartnergruppe.
	 * Geschäftspartnergruppe
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setC_BP_Group_ID (int C_BP_Group_ID);

	/**
	 * Get Geschäftspartnergruppe.
	 * Geschäftspartnergruppe
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getC_BP_Group_ID();

	public org.compiere.model.I_C_BP_Group getC_BP_Group();

	@Deprecated
	public void setC_BP_Group(org.compiere.model.I_C_BP_Group C_BP_Group);

    /** Column definition for C_BP_Group_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_C_BP_Group> COLUMN_C_BP_Group_ID = new org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_C_BP_Group>(I_AD_User.class, "C_BP_Group_ID", org.compiere.model.I_C_BP_Group.class);
    /** Column name C_BP_Group_ID */
    public static final String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

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
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: Search
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
	 * Set Land.
	 * Land
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Land.
	 * Land
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country();

	@Deprecated
	public void setC_Country(org.compiere.model.I_C_Country C_Country);

    /** Column definition for C_Country_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_C_Country>(I_AD_User.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Set Anrede (ID).
	 * Anrede zum Druck auf Korrespondenz
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Greeting_ID (int C_Greeting_ID);

	/**
	 * Get Anrede (ID).
	 * Anrede zum Druck auf Korrespondenz
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
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Job_ID (int C_Job_ID);

	/**
	 * Get Position.
	 * Job Position
	 *
	 * <br>Type: Search
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
	 * Set Region.
	 * Identifiziert eine geographische Region
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setC_Region_ID (int C_Region_ID);

	/**
	 * Get Region.
	 * Identifiziert eine geographische Region
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getC_Region_ID();

	public org.compiere.model.I_C_Region getC_Region();

	@Deprecated
	public void setC_Region(org.compiere.model.I_C_Region C_Region);

    /** Column definition for C_Region_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_C_Region> COLUMN_C_Region_ID = new org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_C_Region>(I_AD_User.class, "C_Region_ID", org.compiere.model.I_C_Region.class);
    /** Column name C_Region_ID */
    public static final String COLUMNNAME_C_Region_ID = "C_Region_ID";

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
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExternalId (java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalId();

    /** Column definition for ExternalId */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_ExternalId = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "ExternalId", null);
    /** Column name ExternalId */
    public static final String COLUMNNAME_ExternalId = "ExternalId";

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
	 * Set Anrede.
	 * Für Briefe - z.B. "Sehr geehrter 
{
0}
" oder "Sehr geehrter Herr 
{
0}
" - Zur Laufzeit wird  "
{
0}
" durch den Namen ersetzt
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setGreeting (java.lang.String Greeting);

	/**
	 * Get Anrede.
	 * Für Briefe - z.B. "Sehr geehrter 
{
0}
" oder "Sehr geehrter Herr 
{
0}
" - Zur Laufzeit wird  "
{
0}
" durch den Namen ersetzt
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getGreeting();

    /** Column definition for Greeting */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Greeting = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Greeting", null);
    /** Column name Greeting */
    public static final String COLUMNNAME_Greeting = "Greeting";

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
	 * Set Rechnungskontakt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsBillToContact_Default (boolean IsBillToContact_Default);

	/**
	 * Get Rechnungskontakt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isBillToContact_Default();

    /** Column definition for IsBillToContact_Default */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsBillToContact_Default = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsBillToContact_Default", null);
    /** Column name IsBillToContact_Default */
    public static final String COLUMNNAME_IsBillToContact_Default = "IsBillToContact_Default";

	/**
	 * Set Kunde.
	 * Zeigt an, ob dieser Geschäftspartner ein Kunde ist
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setIsCustomer (boolean IsCustomer);

	/**
	 * Get Kunde.
	 * Zeigt an, ob dieser Geschäftspartner ein Kunde ist
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public boolean isCustomer();

    /** Column definition for IsCustomer */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsCustomer = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsCustomer", null);
    /** Column name IsCustomer */
    public static final String COLUMNNAME_IsCustomer = "IsCustomer";

	/**
	 * Set Bereinigt.
	 * Nur bereinigte Daten ausgeben
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDataClarified (boolean IsDataClarified);

	/**
	 * Get Bereinigt.
	 * Nur bereinigte Daten ausgeben
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDataClarified();

    /** Column definition for IsDataClarified */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsDataClarified = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsDataClarified", null);
    /** Column name IsDataClarified */
    public static final String COLUMNNAME_IsDataClarified = "IsDataClarified";

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
	 * Set hat zentralen Partner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setIsHavingParentPartner (boolean IsHavingParentPartner);

	/**
	 * Get hat zentralen Partner.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public boolean isHavingParentPartner();

    /** Column definition for IsHavingParentPartner */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsHavingParentPartner = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsHavingParentPartner", null);
    /** Column name IsHavingParentPartner */
    public static final String COLUMNNAME_IsHavingParentPartner = "IsHavingParentPartner";

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
	 * Set Login As HostKey .
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsLoginAsHostKey (boolean IsLoginAsHostKey);

	/**
	 * Get Login As HostKey .
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isLoginAsHostKey();

    /** Column definition for IsLoginAsHostKey */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsLoginAsHostKey = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsLoginAsHostKey", null);
    /** Column name IsLoginAsHostKey */
    public static final String COLUMNNAME_IsLoginAsHostKey = "IsLoginAsHostKey";

	/**
	 * Set Newsletter.
	 * Template or container uses news channels
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsNews (boolean IsNews);

	/**
	 * Get Newsletter.
	 * Template or container uses news channels
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isNews();

    /** Column definition for IsNews */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsNews = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsNews", null);
    /** Column name IsNews */
    public static final String COLUMNNAME_IsNews = "IsNews";

	/**
	 * Set Newsletter.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsNewsletter (boolean IsNewsletter);

	/**
	 * Get Newsletter.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isNewsletter();

    /** Column definition for IsNewsletter */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsNewsletter = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsNewsletter", null);
    /** Column name IsNewsletter */
    public static final String COLUMNNAME_IsNewsletter = "IsNewsletter";

	/**
	 * Set Zielkunde.
	 * Kennzeichnet einen Interessenten oder Kunden
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setIsProspect (boolean IsProspect);

	/**
	 * Get Zielkunde.
	 * Kennzeichnet einen Interessenten oder Kunden
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public boolean isProspect();

    /** Column definition for IsProspect */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsProspect = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsProspect", null);
    /** Column name IsProspect */
    public static final String COLUMNNAME_IsProspect = "IsProspect";

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
	 * Set Verkaufskontakt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsSalesContact (boolean IsSalesContact);

	/**
	 * Get Verkaufskontakt.
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
	 * Set Selbstregistrierung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setIsSelfService (boolean IsSelfService);

	/**
	 * Get Selbstregistrierung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public boolean isSelfService();

    /** Column definition for IsSelfService */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsSelfService = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsSelfService", null);
    /** Column name IsSelfService */
    public static final String COLUMNNAME_IsSelfService = "IsSelfService";

	/**
	 * Set Lieferkontakt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsShipToContact_Default (boolean IsShipToContact_Default);

	/**
	 * Get Lieferkontakt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isShipToContact_Default();

    /** Column definition for IsShipToContact_Default */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_IsShipToContact_Default = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "IsShipToContact_Default", null);
    /** Column name IsShipToContact_Default */
    public static final String COLUMNNAME_IsShipToContact_Default = "IsShipToContact_Default";

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
	 * Set Sprachregion.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setLanguage (java.lang.String Language);

	/**
	 * Get Sprachregion.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.lang.String getLanguage();

    /** Column definition for Language */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Language = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Language", null);
    /** Column name Language */
    public static final String COLUMNNAME_Language = "Language";

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
	 * Set Memo.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMemo (java.lang.String Memo);

	/**
	 * Get Memo.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMemo();

    /** Column definition for Memo */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Memo = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Memo", null);
    /** Column name Memo */
    public static final String COLUMNNAME_Memo = "Memo";

	/**
	 * Set Handynummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMobilePhone (java.lang.String MobilePhone);

	/**
	 * Get Handynummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMobilePhone();

    /** Column definition for MobilePhone */
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_MobilePhone = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "MobilePhone", null);
    /** Column name MobilePhone */
    public static final String COLUMNNAME_MobilePhone = "MobilePhone";

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
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPassword (java.lang.String Password);

	/**
	 * Get Kennwort.
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
    public static final org.adempiere.model.ModelColumn<I_AD_User, Object> COLUMN_Phone = new org.adempiere.model.ModelColumn<I_AD_User, Object>(I_AD_User.class, "Phone", null);
    /** Column name Phone */
    public static final String COLUMNNAME_Phone = "Phone";

	/**
	 * Set Mobil.
	 * Alternative Mobile Telefonnummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPhone2 (java.lang.String Phone2);

	/**
	 * Get Mobil.
	 * Alternative Mobile Telefonnummer
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
	 * Set Beratung.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Beratung.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public int getSalesRep_ID();

	public org.compiere.model.I_AD_User getSalesRep();

	@Deprecated
	public void setSalesRep(org.compiere.model.I_AD_User SalesRep);

    /** Column definition for SalesRep_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_User> COLUMN_SalesRep_ID = new org.adempiere.model.ModelColumn<I_AD_User, org.compiere.model.I_AD_User>(I_AD_User.class, "SalesRep_ID", org.compiere.model.I_AD_User.class);
    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

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
	 * Set Benutzer PIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUserPIN (java.lang.String UserPIN);

	/**
	 * Get Benutzer PIN.
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
