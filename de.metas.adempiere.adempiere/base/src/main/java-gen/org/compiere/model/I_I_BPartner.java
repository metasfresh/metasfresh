package org.compiere.model;

/**
 * Generated Interface for I_BPartner
 * 
 * @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public interface I_I_BPartner
{

	/** TableName=I_BPartner */
	public static final String Table_Name = "I_BPartner";

	/** AD_Table_ID=533 */
	// public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

	// org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

	/**
	 * AccessLevel = 2 - Client
	 */
	// java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

	/** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

	/** Column definition for AD_Client_ID */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_Client>(I_I_BPartner.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
	/** Column name AD_Client_ID */
	public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setAD_Org_ID(int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

	/** Column definition for AD_Org_ID */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_Org>(I_I_BPartner.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
	/** Column name AD_Org_ID */
	public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setAD_User_ID(int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User();

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

	/** Column definition for AD_User_ID */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_User>(I_I_BPartner.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
	/** Column name AD_User_ID */
	public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Straße und Nr..
	 * Adresszeile 1 für diesen Standort
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setAddress1(java.lang.String Address1);

	/**
	 * Get Straße und Nr..
	 * Adresszeile 1 für diesen Standort
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getAddress1();

	/** Column definition for Address1 */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Address1 = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Address1", null);
	/** Column name Address1 */
	public static final String COLUMNNAME_Address1 = "Address1";

	/**
	 * Set Adresszusatz.
	 * Adresszeile 2 für diesen Standort
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setAddress2(java.lang.String Address2);

	/**
	 * Get Adresszusatz.
	 * Adresszeile 2 für diesen Standort
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getAddress2();

	/** Column definition for Address2 */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Address2 = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Address2", null);
	/** Column name Address2 */
	public static final String COLUMNNAME_Address2 = "Address2";

	/**
	 * Set Geburtstag.
	 * Birthday or Anniversary day
	 *
	 * <br>
	 * Type: Date
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setBirthday(java.sql.Timestamp Birthday);

	/**
	 * Get Geburtstag.
	 * Birthday or Anniversary day
	 *
	 * <br>
	 * Type: Date
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getBirthday();

	/** Column definition for Birthday */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Birthday = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Birthday", null);
	/** Column name Birthday */
	public static final String COLUMNNAME_Birthday = "Birthday";

	/**
	 * Set Kontakt-Anrede.
	 * Greeting for Business Partner Contact
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setBPContactGreeting(java.lang.String BPContactGreeting);

	/**
	 * Get Kontakt-Anrede.
	 * Greeting for Business Partner Contact
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getBPContactGreeting();

	/** Column definition for BPContactGreeting */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_BPContactGreeting = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "BPContactGreeting", null);
	/** Column name BPContactGreeting */
	public static final String COLUMNNAME_BPContactGreeting = "BPContactGreeting";

	/**
	 * Set Geschäftspartnergruppe.
	 * Business Partner Group
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setC_BP_Group_ID(int C_BP_Group_ID);

	/**
	 * Get Geschäftspartnergruppe.
	 * Business Partner Group
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getC_BP_Group_ID();

	public org.compiere.model.I_C_BP_Group getC_BP_Group();

	public void setC_BP_Group(org.compiere.model.I_C_BP_Group C_BP_Group);

	/** Column definition for C_BP_Group_ID */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_BP_Group> COLUMN_C_BP_Group_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_BP_Group>(I_I_BPartner.class, "C_BP_Group_ID", org.compiere.model.I_C_BP_Group.class);
	/** Column name C_BP_Group_ID */
	public static final String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setC_BPartner_ID(int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

	/** Column definition for C_BPartner_ID */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_BPartner>(I_I_BPartner.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
	/** Column name C_BPartner_ID */
	public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setC_BPartner_Location_ID(int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

	/** Column definition for C_BPartner_Location_ID */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_BPartner_Location>(I_I_BPartner.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
	/** Column name C_BPartner_Location_ID */
	public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Land.
	 * Country
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setC_Country_ID(int C_Country_ID);

	/**
	 * Get Land.
	 * Country
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country();

	public void setC_Country(org.compiere.model.I_C_Country C_Country);

	/** Column definition for C_Country_ID */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_Country>(I_I_BPartner.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
	/** Column name C_Country_ID */
	public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Set Anrede.
	 * Greeting to print on correspondence
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setC_Greeting_ID(int C_Greeting_ID);

	/**
	 * Get Anrede.
	 * Greeting to print on correspondence
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getC_Greeting_ID();

	public org.compiere.model.I_C_Greeting getC_Greeting();

	public void setC_Greeting(org.compiere.model.I_C_Greeting C_Greeting);

	/** Column definition for C_Greeting_ID */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_Greeting> COLUMN_C_Greeting_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_Greeting>(I_I_BPartner.class, "C_Greeting_ID", org.compiere.model.I_C_Greeting.class);
	/** Column name C_Greeting_ID */
	public static final String COLUMNNAME_C_Greeting_ID = "C_Greeting_ID";

	/**
	 * Set Region.
	 * Identifies a geographical Region
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setC_Region_ID(int C_Region_ID);

	/**
	 * Get Region.
	 * Identifies a geographical Region
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getC_Region_ID();

	public org.compiere.model.I_C_Region getC_Region();

	public void setC_Region(org.compiere.model.I_C_Region C_Region);

	/** Column definition for C_Region_ID */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_Region> COLUMN_C_Region_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_C_Region>(I_I_BPartner.class, "C_Region_ID", org.compiere.model.I_C_Region.class);
	/** Column name C_Region_ID */
	public static final String COLUMNNAME_C_Region_ID = "C_Region_ID";

	/**
	 * Set Ort.
	 * Identifies a City
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setCity(java.lang.String City);

	/**
	 * Get Ort.
	 * Identifies a City
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getCity();

	/** Column definition for City */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_City = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "City", null);
	/** Column name City */
	public static final String COLUMNNAME_City = "City";

	/**
	 * Set Bemerkungen.
	 * Comments or additional information
	 *
	 * <br>
	 * Type: Text
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setComments(java.lang.String Comments);

	/**
	 * Get Bemerkungen.
	 * Comments or additional information
	 *
	 * <br>
	 * Type: Text
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getComments();

	/** Column definition for Comments */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Comments = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Comments", null);
	/** Column name Comments */
	public static final String COLUMNNAME_Comments = "Comments";

	/**
	 * Set Firmenname.
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setCompanyname(java.lang.String Companyname);

	/**
	 * Get Firmenname.
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getCompanyname();

	/** Column definition for Companyname */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Companyname = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Companyname", null);
	/** Column name Companyname */
	public static final String COLUMNNAME_Companyname = "Companyname";

	/**
	 * Set Kontakt-Beschreibung.
	 * Description of Contact
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setContactDescription(java.lang.String ContactDescription);

	/**
	 * Get Kontakt-Beschreibung.
	 * Description of Contact
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getContactDescription();

	/** Column definition for ContactDescription */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_ContactDescription = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "ContactDescription", null);
	/** Column name ContactDescription */
	public static final String COLUMNNAME_ContactDescription = "ContactDescription";

	/** Column name ContactName */
	public static final String COLUMNNAME_ContactName = "ContactName";

	/**
	 * Set Contact Name.
	 * Business Partner Contact Name
	 */
	public void setContactName(String ContactName);

	/**
	 * Get Contact Name.
	 * Business Partner Contact Name
	 */
	public String getContactName();

	/**
	 * Set ISO Ländercode.
	 * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setCountryCode(java.lang.String CountryCode);

	/**
	 * Get ISO Ländercode.
	 * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getCountryCode();

	/** Column definition for CountryCode */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_CountryCode = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "CountryCode", null);
	/** Column name CountryCode */
	public static final String COLUMNNAME_CountryCode = "CountryCode";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

	/** Column definition for Created */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Created", null);
	/** Column name Created */
	public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>
	 * Type: Table
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getCreatedBy();

	/** Column definition for CreatedBy */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_User>(I_I_BPartner.class, "CreatedBy", org.compiere.model.I_AD_User.class);
	/** Column name CreatedBy */
	public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setDescription(java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getDescription();

	/** Column definition for Description */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Description", null);
	/** Column name Description */
	public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set D-U-N-S.
	 * Dun & Bradstreet Number
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setDUNS(java.lang.String DUNS);

	/**
	 * Get D-U-N-S.
	 * Dun & Bradstreet Number
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getDUNS();

	/** Column definition for DUNS */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_DUNS = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "DUNS", null);
	/** Column name DUNS */
	public static final String COLUMNNAME_DUNS = "DUNS";

	/**
	 * Set EMail.
	 * Electronic Mail Address
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setEMail(java.lang.String EMail);

	/**
	 * Get EMail.
	 * Electronic Mail Address
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getEMail();

	/** Column definition for EMail */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_EMail = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "EMail", null);
	/** Column name EMail */
	public static final String COLUMNNAME_EMail = "EMail";

	/**
	 * Set Fax.
	 * Facsimile number
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setFax(java.lang.String Fax);

	/**
	 * Get Fax.
	 * Facsimile number
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getFax();

	/** Column definition for Fax */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Fax = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Fax", null);
	/** Column name Fax */
	public static final String COLUMNNAME_Fax = "Fax";

	/**
	 * Set Vorname.
	 * Vorname
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setFirstname(java.lang.String Firstname);

	/**
	 * Get Vorname.
	 * Vorname
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getFirstname();

	/** Column definition for Firstname */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Firstname = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Firstname", null);
	/** Column name Firstname */
	public static final String COLUMNNAME_Firstname = "Firstname";

	/**
	 * Set Gruppen-Schlüssel.
	 * Business Partner Group Key
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setGroupValue(java.lang.String GroupValue);

	/**
	 * Get Gruppen-Schlüssel.
	 * Business Partner Group Key
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getGroupValue();

	/** Column definition for GroupValue */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_GroupValue = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "GroupValue", null);
	/** Column name GroupValue */
	public static final String COLUMNNAME_GroupValue = "GroupValue";

	/**
	 * Set Import - Geschäftspartner.
	 *
	 * <br>
	 * Type: ID
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setI_BPartner_ID(int I_BPartner_ID);

	/**
	 * Get Import - Geschäftspartner.
	 *
	 * <br>
	 * Type: ID
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public int getI_BPartner_ID();

	/** Column definition for I_BPartner_ID */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_I_BPartner_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "I_BPartner_ID", null);
	/** Column name I_BPartner_ID */
	public static final String COLUMNNAME_I_BPartner_ID = "I_BPartner_ID";

	/**
	 * Set Import-Fehlermeldung.
	 * Messages generated from import process
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setI_ErrorMsg(java.lang.String I_ErrorMsg);

	/**
	 * Get Import-Fehlermeldung.
	 * Messages generated from import process
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getI_ErrorMsg();

	/** Column definition for I_ErrorMsg */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_I_ErrorMsg = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "I_ErrorMsg", null);
	/** Column name I_ErrorMsg */
	public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Importiert.
	 * Has this import been processed
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setI_IsImported(boolean I_IsImported);

	/**
	 * Get Importiert.
	 * Has this import been processed
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public boolean isI_IsImported();

	/** Column definition for I_IsImported */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_I_IsImported = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "I_IsImported", null);
	/** Column name I_IsImported */
	public static final String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set Interessengebiet.
	 * Name of the Interest Area
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setInterestAreaName(java.lang.String InterestAreaName);

	/**
	 * Get Interessengebiet.
	 * Name of the Interest Area
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getInterestAreaName();

	/** Column definition for InterestAreaName */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_InterestAreaName = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "InterestAreaName", null);
	/** Column name InterestAreaName */
	public static final String COLUMNNAME_InterestAreaName = "InterestAreaName";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setIsActive(boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public boolean isActive();

	/** Column definition for IsActive */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IsActive", null);
	/** Column name IsActive */
	public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Customer.
	 * Indicates if this Business Partner is a Customer
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setIsCustomer(boolean IsCustomer);

	/**
	 * Get Customer.
	 * Indicates if this Business Partner is a Customer
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public boolean isCustomer();

	/** Column definition for IsCustomer */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IsCustomer = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IsCustomer", null);
	/** Column name IsCustomer */
	public static final String COLUMNNAME_IsCustomer = "IsCustomer";

	/**
	 * Set Employee.
	 * Indicates if this Business Partner is an employee
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setIsEmployee(boolean IsEmployee);

	/**
	 * Get Employee.
	 * Indicates if this Business Partner is an employee
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public boolean isEmployee();

	/** Column definition for IsEmployee */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IsEmployee = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IsEmployee", null);
	/** Column name IsEmployee */
	public static final String COLUMNNAME_IsEmployee = "IsEmployee";

	/**
	 * Set Vendor.
	 * Indicates if this Business Partner is a Vendor
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setIsVendor(boolean IsVendor);

	/**
	 * Get Vendor.
	 * Indicates if this Business Partner is a Vendor
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public boolean isVendor();

	/** Column definition for IsVendor */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IsVendor = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IsVendor", null);
	/** Column name IsVendor */
	public static final String COLUMNNAME_IsVendor = "IsVendor";

	/**
	 * Set Nachname.
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setLastname(java.lang.String Lastname);

	/**
	 * Get Nachname.
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getLastname();

	/** Column definition for Lastname */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Lastname = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Lastname", null);
	/** Column name Lastname */
	public static final String COLUMNNAME_Lastname = "Lastname";

	/**
	 * Set NAICS/SIC.
	 * Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setNAICS(java.lang.String NAICS);

	/**
	 * Get NAICS/SIC.
	 * Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getNAICS();

	/** Column definition for NAICS */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_NAICS = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "NAICS", null);
	/** Column name NAICS */
	public static final String COLUMNNAME_NAICS = "NAICS";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setName(java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getName();

	/** Column definition for Name */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Name", null);
	/** Column name Name */
	public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Name 2.
	 * Additional Name
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setName2(java.lang.String Name2);

	/**
	 * Get Name 2.
	 * Additional Name
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getName2();

	/** Column definition for Name2 */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Name2 = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Name2", null);
	/** Column name Name2 */
	public static final String COLUMNNAME_Name2 = "Name2";

	/**
	 * Set Kennwort.
	 * Password of any length (case sensitive)
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setPassword(java.lang.String Password);

	/**
	 * Get Kennwort.
	 * Password of any length (case sensitive)
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getPassword();

	/** Column definition for Password */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Password = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Password", null);
	/** Column name Password */
	public static final String COLUMNNAME_Password = "Password";

	/**
	 * Set Phone.
	 * Identifies a telephone number
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setPhone(java.lang.String Phone);

	/**
	 * Get Phone.
	 * Identifies a telephone number
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getPhone();

	/** Column definition for Phone */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Phone = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Phone", null);
	/** Column name Phone */
	public static final String COLUMNNAME_Phone = "Phone";

	/**
	 * Set Telefon (alternativ).
	 * Identifies an alternate telephone number.
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setPhone2(java.lang.String Phone2);

	/**
	 * Get Telefon (alternativ).
	 * Identifies an alternate telephone number.
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getPhone2();

	/** Column definition for Phone2 */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Phone2 = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Phone2", null);
	/** Column name Phone2 */
	public static final String COLUMNNAME_Phone2 = "Phone2";

	/**
	 * Set PLZ.
	 * Postal code
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setPostal(java.lang.String Postal);

	/**
	 * Get PLZ.
	 * Postal code
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getPostal();

	/** Column definition for Postal */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Postal = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Postal", null);
	/** Column name Postal */
	public static final String COLUMNNAME_Postal = "Postal";

	/**
	 * Set -.
	 * Additional ZIP or Postal code
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setPostal_Add(java.lang.String Postal_Add);

	/**
	 * Get -.
	 * Additional ZIP or Postal code
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getPostal_Add();

	/** Column definition for Postal_Add */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Postal_Add = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Postal_Add", null);
	/** Column name Postal_Add */
	public static final String COLUMNNAME_Postal_Add = "Postal_Add";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setProcessed(boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public boolean isProcessed();

	/** Column definition for Processed */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Processed", null);
	/** Column name Processed */
	public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Verarbeiten.
	 *
	 * <br>
	 * Type: Button
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setProcessing(boolean Processing);

	/**
	 * Get Verarbeiten.
	 *
	 * <br>
	 * Type: Button
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public boolean isProcessing();

	/** Column definition for Processing */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Processing", null);
	/** Column name Processing */
	public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Interessengebiet.
	 * Interest Area or Topic
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setR_InterestArea_ID(int R_InterestArea_ID);

	/**
	 * Get Interessengebiet.
	 * Interest Area or Topic
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getR_InterestArea_ID();

	public org.compiere.model.I_R_InterestArea getR_InterestArea();

	public void setR_InterestArea(org.compiere.model.I_R_InterestArea R_InterestArea);

	/** Column definition for R_InterestArea_ID */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_R_InterestArea> COLUMN_R_InterestArea_ID = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_R_InterestArea>(I_I_BPartner.class, "R_InterestArea_ID", org.compiere.model.I_R_InterestArea.class);
	/** Column name R_InterestArea_ID */
	public static final String COLUMNNAME_R_InterestArea_ID = "R_InterestArea_ID";

	/**
	 * Set Region.
	 * Name of the Region
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setRegionName(java.lang.String RegionName);

	/**
	 * Get Region.
	 * Name of the Region
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getRegionName();

	/** Column definition for RegionName */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_RegionName = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "RegionName", null);
	/** Column name RegionName */
	public static final String COLUMNNAME_RegionName = "RegionName";

	/**
	 * Set Steuer-ID.
	 * Tax Identification
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setTaxID(java.lang.String TaxID);

	/**
	 * Get Steuer-ID.
	 * Tax Identification
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getTaxID();

	/** Column definition for TaxID */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_TaxID = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "TaxID", null);
	/** Column name TaxID */
	public static final String COLUMNNAME_TaxID = "TaxID";

	/**
	 * Set Titel.
	 * Name this entity is referred to as
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setTitle(java.lang.String Title);

	/**
	 * Get Titel.
	 * Name this entity is referred to as
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getTitle();

	/** Column definition for Title */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Title = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Title", null);
	/** Column name Title */
	public static final String COLUMNNAME_Title = "Title";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

	/** Column definition for Updated */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Updated", null);
	/** Column name Updated */
	public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>
	 * Type: Table
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getUpdatedBy();

	/** Column definition for UpdatedBy */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_I_BPartner, org.compiere.model.I_AD_User>(I_I_BPartner.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
	/** Column name UpdatedBy */
	public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setValue(java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>
	 * Type: String
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.lang.String getValue();

	/** Column definition for Value */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "Value", null);
	/** Column name Value */
	public static final String COLUMNNAME_Value = "Value";

	/**
	 * Set Vorbelegung Rechnung.
	 * Rechnungs-Adresse für diesen Geschäftspartner
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setIsBillTo(boolean IsBillTo);

	/**
	 * Get Vorbelegung Rechnung.
	 * Rechnungs-Adresse für diesen Geschäftspartner
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public boolean isBillTo();

	/** Column definition for IsBillTo */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IsBillTo = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IsBillTo", null);
	/** Column name IsBillTo */
	public static final String COLUMNNAME_IsBillTo = "IsBillTo";

	/**
	 * Set Lieferstandard.
	 * Liefer-Adresse für den Geschäftspartner
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setIsShipTo(boolean IsShipTo);

	/**
	 * Get Lieferstandard.
	 * Liefer-Adresse für den Geschäftspartner
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public boolean isShipTo();

	/** Column definition for IsShipTo */
	public static final org.adempiere.model.ModelColumn<I_I_BPartner, Object> COLUMN_IsShipTo = new org.adempiere.model.ModelColumn<I_I_BPartner, Object>(I_I_BPartner.class, "IsShipTo", null);
	/** Column name IsShipTo */
	public static final String COLUMNNAME_IsShipTo = "IsShipTo";

}
