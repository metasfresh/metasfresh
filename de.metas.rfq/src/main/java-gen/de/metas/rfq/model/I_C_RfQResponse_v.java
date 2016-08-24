package de.metas.rfq.model;


/** Generated Interface for C_RfQResponse_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_RfQResponse_v 
{

    /** TableName=C_RfQResponse_v */
    public static final String Table_Name = "C_RfQResponse_v";

    /** AD_Table_ID=725 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_AD_Client>(I_C_RfQResponse_v.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sprache.
	 * Language for this entity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Language (java.lang.String AD_Language);

	/**
	 * Get Sprache.
	 * Language for this entity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAD_Language();

    /** Column definition for AD_Language */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object> COLUMN_AD_Language = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object>(I_C_RfQResponse_v.class, "AD_Language", null);
    /** Column name AD_Language */
    public static final String COLUMNNAME_AD_Language = "AD_Language";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_AD_Org>(I_C_RfQResponse_v.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Ansprechpartner.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_ID();

	public org.compiere.model.I_AD_User getAD_User();

	public void setAD_User(org.compiere.model.I_AD_User AD_User);

    /** Column definition for AD_User_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_AD_User> COLUMN_AD_User_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_AD_User>(I_C_RfQResponse_v.class, "AD_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Name.
	 * Name des Sponsors.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setBPName (java.lang.String BPName);

	/**
	 * Get Name.
	 * Name des Sponsors.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPName();

    /** Column definition for BPName */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object> COLUMN_BPName = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object>(I_C_RfQResponse_v.class, "BPName", null);
    /** Column name BPName */
    public static final String COLUMNNAME_BPName = "BPName";

	/**
	 * Set BP Name2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPName2 (java.lang.String BPName2);

	/**
	 * Get BP Name2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPName2();

    /** Column definition for BPName2 */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object> COLUMN_BPName2 = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object>(I_C_RfQResponse_v.class, "BPName2", null);
    /** Column name BPName2 */
    public static final String COLUMNNAME_BPName2 = "BPName2";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_C_BPartner>(I_C_RfQResponse_v.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location();

	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location);

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_C_BPartner_Location> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_C_BPartner_Location>(I_C_RfQResponse_v.class, "C_BPartner_Location_ID", org.compiere.model.I_C_BPartner_Location.class);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Währung.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_C_Currency>(I_C_RfQResponse_v.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Anschrift.
	 * Location or Address
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Location_ID (int C_Location_ID);

	/**
	 * Get Anschrift.
	 * Location or Address
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Location_ID();

	public org.compiere.model.I_C_Location getC_Location();

	public void setC_Location(org.compiere.model.I_C_Location C_Location);

    /** Column definition for C_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_C_Location> COLUMN_C_Location_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_C_Location>(I_C_RfQResponse_v.class, "C_Location_ID", org.compiere.model.I_C_Location.class);
    /** Column name C_Location_ID */
    public static final String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/**
	 * Set Ausschreibung.
	 * Request for Quotation
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQ_ID (int C_RfQ_ID);

	/**
	 * Get Ausschreibung.
	 * Request for Quotation
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQ_ID();

	public de.metas.rfq.model.I_C_RfQ getC_RfQ();

	public void setC_RfQ(de.metas.rfq.model.I_C_RfQ C_RfQ);

    /** Column definition for C_RfQ_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, de.metas.rfq.model.I_C_RfQ> COLUMN_C_RfQ_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, de.metas.rfq.model.I_C_RfQ>(I_C_RfQResponse_v.class, "C_RfQ_ID", de.metas.rfq.model.I_C_RfQ.class);
    /** Column name C_RfQ_ID */
    public static final String COLUMNNAME_C_RfQ_ID = "C_RfQ_ID";

	/**
	 * Set Ausschreibungs-Antwort.
	 * Request for Quotation Response from a potential Vendor
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_RfQResponse_ID (int C_RfQResponse_ID);

	/**
	 * Get Ausschreibungs-Antwort.
	 * Request for Quotation Response from a potential Vendor
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_RfQResponse_ID();

    /** Column definition for C_RfQResponse_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, de.metas.rfq.model.I_C_RfQResponse> COLUMN_C_RfQResponse_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, de.metas.rfq.model.I_C_RfQResponse>(I_C_RfQResponse_v.class, "C_RfQResponse_ID", de.metas.rfq.model.I_C_RfQResponse.class);
    /** Column name C_RfQResponse_ID */
    public static final String COLUMNNAME_C_RfQResponse_ID = "C_RfQResponse_ID";

	/**
	 * Set Kontakt-Name.
	 * Business Partner Contact Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setContactName (java.lang.String ContactName);

	/**
	 * Get Kontakt-Name.
	 * Business Partner Contact Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getContactName();

    /** Column definition for ContactName */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object> COLUMN_ContactName = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object>(I_C_RfQResponse_v.class, "ContactName", null);
    /** Column name ContactName */
    public static final String COLUMNNAME_ContactName = "ContactName";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object>(I_C_RfQResponse_v.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_AD_User>(I_C_RfQResponse_v.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Antwort-datum.
	 * Date of the Response
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDateResponse (java.sql.Timestamp DateResponse);

	/**
	 * Get Antwort-datum.
	 * Date of the Response
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateResponse();

    /** Column definition for DateResponse */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object> COLUMN_DateResponse = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object>(I_C_RfQResponse_v.class, "DateResponse", null);
    /** Column name DateResponse */
    public static final String COLUMNNAME_DateResponse = "DateResponse";

	/**
	 * Set Arbeitsbeginn.
	 * Date when work is (planned to be) started
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateWorkStart (java.sql.Timestamp DateWorkStart);

	/**
	 * Get Arbeitsbeginn.
	 * Date when work is (planned to be) started
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateWorkStart();

    /** Column definition for DateWorkStart */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object> COLUMN_DateWorkStart = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object>(I_C_RfQResponse_v.class, "DateWorkStart", null);
    /** Column name DateWorkStart */
    public static final String COLUMNNAME_DateWorkStart = "DateWorkStart";

	/**
	 * Set Auslieferungstage.
	 * Number of Days (planned) until Delivery
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryDays (int DeliveryDays);

	/**
	 * Get Auslieferungstage.
	 * Number of Days (planned) until Delivery
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDeliveryDays();

    /** Column definition for DeliveryDays */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object> COLUMN_DeliveryDays = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object>(I_C_RfQResponse_v.class, "DeliveryDays", null);
    /** Column name DeliveryDays */
    public static final String COLUMNNAME_DeliveryDays = "DeliveryDays";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object>(I_C_RfQResponse_v.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHelp (java.lang.String Help);

	/**
	 * Get Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object>(I_C_RfQResponse_v.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object>(I_C_RfQResponse_v.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set ISO Währungscode.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setISO_Code (java.lang.String ISO_Code);

	/**
	 * Get ISO Währungscode.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getISO_Code();

    /** Column definition for ISO_Code */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object> COLUMN_ISO_Code = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object>(I_C_RfQResponse_v.class, "ISO_Code", null);
    /** Column name ISO_Code */
    public static final String COLUMNNAME_ISO_Code = "ISO_Code";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object>(I_C_RfQResponse_v.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Org Address.
	 * Organization Location/Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrg_Location_ID (int Org_Location_ID);

	/**
	 * Get Org Address.
	 * Organization Location/Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getOrg_Location_ID();

	public org.compiere.model.I_C_Location getOrg_Location();

	public void setOrg_Location(org.compiere.model.I_C_Location Org_Location);

    /** Column definition for Org_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_C_Location> COLUMN_Org_Location_ID = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_C_Location>(I_C_RfQResponse_v.class, "Org_Location_ID", org.compiere.model.I_C_Location.class);
    /** Column name Org_Location_ID */
    public static final String COLUMNNAME_Org_Location_ID = "Org_Location_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object> COLUMN_Phone = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object>(I_C_RfQResponse_v.class, "Phone", null);
    /** Column name Phone */
    public static final String COLUMNNAME_Phone = "Phone";

	/**
	 * Set Steuer-ID.
	 * Tax Identification
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTaxID (java.lang.String TaxID);

	/**
	 * Get Steuer-ID.
	 * Tax Identification
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTaxID();

    /** Column definition for TaxID */
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object> COLUMN_TaxID = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object>(I_C_RfQResponse_v.class, "TaxID", null);
    /** Column name TaxID */
    public static final String COLUMNNAME_TaxID = "TaxID";

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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object> COLUMN_Title = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object>(I_C_RfQResponse_v.class, "Title", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, Object>(I_C_RfQResponse_v.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_RfQResponse_v, org.compiere.model.I_AD_User>(I_C_RfQResponse_v.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
