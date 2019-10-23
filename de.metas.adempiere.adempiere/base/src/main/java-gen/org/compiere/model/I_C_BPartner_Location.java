package org.compiere.model;


/** Generated Interface for C_BPartner_Location
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_BPartner_Location 
{

    /** TableName=C_BPartner_Location */
    public static final String Table_Name = "C_BPartner_Location";

    /** AD_Table_ID=293 */
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
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, org.compiere.model.I_AD_Client>(I_C_BPartner_Location.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Adresse.
	 * Anschrift
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAddress (java.lang.String Address);

	/**
	 * Get Adresse.
	 * Anschrift
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAddress();

    /** Column definition for Address */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_Address = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "Address", null);
    /** Column name Address */
    public static final String COLUMNNAME_Address = "Address";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, org.compiere.model.I_AD_Org>(I_C_BPartner_Location.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, org.compiere.model.I_C_BPartner>(I_C_BPartner_Location.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Standort.
	 * Identifies the (ship to) address for this Business Partner
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

    /** Column definition for C_BPartner_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_C_BPartner_Location_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "C_BPartner_Location_ID", null);
    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Anschrift.
	 * Location or Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Location_ID (int C_Location_ID);

	/**
	 * Get Anschrift.
	 * Location or Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Location_ID();

	public org.compiere.model.I_C_Location getC_Location();

	public void setC_Location(org.compiere.model.I_C_Location C_Location);

    /** Column definition for C_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, org.compiere.model.I_C_Location> COLUMN_C_Location_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, org.compiere.model.I_C_Location>(I_C_BPartner_Location.class, "C_Location_ID", org.compiere.model.I_C_Location.class);
    /** Column name C_Location_ID */
    public static final String COLUMNNAME_C_Location_ID = "C_Location_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, org.compiere.model.I_AD_User>(I_C_BPartner_Location.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Vertriebsgebiet.
	 * Sales coverage region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_SalesRegion_ID (int C_SalesRegion_ID);

	/**
	 * Get Vertriebsgebiet.
	 * Sales coverage region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_SalesRegion_ID();

	public org.compiere.model.I_C_SalesRegion getC_SalesRegion();

	public void setC_SalesRegion(org.compiere.model.I_C_SalesRegion C_SalesRegion);

    /** Column definition for C_SalesRegion_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, org.compiere.model.I_C_SalesRegion> COLUMN_C_SalesRegion_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, org.compiere.model.I_C_SalesRegion>(I_C_BPartner_Location.class, "C_SalesRegion_ID", org.compiere.model.I_C_SalesRegion.class);
    /** Column name C_SalesRegion_ID */
    public static final String COLUMNNAME_C_SalesRegion_ID = "C_SalesRegion_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_EMail = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "EMail", null);
    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

	/**
	 * Set Alternative eMail.
	 * EMail-Adresse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMail2 (java.lang.String EMail2);

	/**
	 * Get Alternative eMail.
	 * EMail-Adresse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMail2();

    /** Column definition for EMail2 */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_EMail2 = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "EMail2", null);
    /** Column name EMail2 */
    public static final String COLUMNNAME_EMail2 = "EMail2";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_ExternalId = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "ExternalId", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_Fax = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "Fax", null);
    /** Column name Fax */
    public static final String COLUMNNAME_Fax = "Fax";

	/**
	 * Set Alternative Fax.
	 * Faxnummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFax2 (java.lang.String Fax2);

	/**
	 * Get Alternative Fax.
	 * Faxnummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFax2();

    /** Column definition for Fax2 */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_Fax2 = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "Fax2", null);
    /** Column name Fax2 */
    public static final String COLUMNNAME_Fax2 = "Fax2";

	/**
	 * Set GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGLN (java.lang.String GLN);

	/**
	 * Get GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGLN();

    /** Column definition for GLN */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_GLN = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "GLN", null);
    /** Column name GLN */
    public static final String COLUMNNAME_GLN = "GLN";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Vorbelegung Rechnung.
	 * Rechnungs-Adresse für diesen Geschäftspartner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsBillTo (boolean IsBillTo);

	/**
	 * Get Vorbelegung Rechnung.
	 * Rechnungs-Adresse für diesen Geschäftspartner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isBillTo();

    /** Column definition for IsBillTo */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsBillTo = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "IsBillTo", null);
    /** Column name IsBillTo */
    public static final String COLUMNNAME_IsBillTo = "IsBillTo";

	/**
	 * Set Rechnung Standard Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsBillToDefault (boolean IsBillToDefault);

	/**
	 * Get Rechnung Standard Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isBillToDefault();

    /** Column definition for IsBillToDefault */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsBillToDefault = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "IsBillToDefault", null);
    /** Column name IsBillToDefault */
    public static final String COLUMNNAME_IsBillToDefault = "IsBillToDefault";

	/**
	 * Set Provisionsadresse.
	 * Provisionsabrechnungen werden hierhin geschickt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsCommissionTo (boolean IsCommissionTo);

	/**
	 * Get Provisionsadresse.
	 * Provisionsabrechnungen werden hierhin geschickt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isCommissionTo();

    /** Column definition for IsCommissionTo */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsCommissionTo = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "IsCommissionTo", null);
    /** Column name IsCommissionTo */
    public static final String COLUMNNAME_IsCommissionTo = "IsCommissionTo";

	/**
	 * Set Provision Standard Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsCommissionToDefault (boolean IsCommissionToDefault);

	/**
	 * Get Provision Standard Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isCommissionToDefault();

    /** Column definition for IsCommissionToDefault */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsCommissionToDefault = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "IsCommissionToDefault", null);
    /** Column name IsCommissionToDefault */
    public static final String COLUMNNAME_IsCommissionToDefault = "IsCommissionToDefault";

	/**
	 * Set ISDN.
	 * ISDN or modem line
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setISDN (java.lang.String ISDN);

	/**
	 * Get ISDN.
	 * ISDN or modem line
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getISDN();

    /** Column definition for ISDN */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_ISDN = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "ISDN", null);
    /** Column name ISDN */
    public static final String COLUMNNAME_ISDN = "ISDN";

	/**
	 * Set Abladeort.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsHandOverLocation (boolean IsHandOverLocation);

	/**
	 * Get Abladeort.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isHandOverLocation();

    /** Column definition for IsHandOverLocation */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsHandOverLocation = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "IsHandOverLocation", null);
    /** Column name IsHandOverLocation */
    public static final String COLUMNNAME_IsHandOverLocation = "IsHandOverLocation";

	/**
	 * Set Zahlungs-Adresse.
	 * Business Partner pays from that address and we'll send dunning letters there
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPayFrom (boolean IsPayFrom);

	/**
	 * Get Zahlungs-Adresse.
	 * Business Partner pays from that address and we'll send dunning letters there
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPayFrom();

    /** Column definition for IsPayFrom */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsPayFrom = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "IsPayFrom", null);
    /** Column name IsPayFrom */
    public static final String COLUMNNAME_IsPayFrom = "IsPayFrom";

	/**
	 * Set Erstattungs-Adresse.
	 * Business Partner payment address
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsRemitTo (boolean IsRemitTo);

	/**
	 * Get Erstattungs-Adresse.
	 * Business Partner payment address
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isRemitTo();

    /** Column definition for IsRemitTo */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsRemitTo = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "IsRemitTo", null);
    /** Column name IsRemitTo */
    public static final String COLUMNNAME_IsRemitTo = "IsRemitTo";

	/**
	 * Set Lieferstandard.
	 * Liefer-Adresse für den Geschäftspartner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsShipTo (boolean IsShipTo);

	/**
	 * Get Lieferstandard.
	 * Liefer-Adresse für den Geschäftspartner
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isShipTo();

    /** Column definition for IsShipTo */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsShipTo = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "IsShipTo", null);
    /** Column name IsShipTo */
    public static final String COLUMNNAME_IsShipTo = "IsShipTo";

	/**
	 * Set Liefer Standard Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsShipToDefault (boolean IsShipToDefault);

	/**
	 * Get Liefer Standard Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isShipToDefault();

    /** Column definition for IsShipToDefault */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsShipToDefault = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "IsShipToDefault", null);
    /** Column name IsShipToDefault */
    public static final String COLUMNNAME_IsShipToDefault = "IsShipToDefault";

	/**
	 * Set Abo Adresse.
	 * An diese Adresse werden Abos geschickt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsSubscriptionTo (boolean IsSubscriptionTo);

	/**
	 * Get Abo Adresse.
	 * An diese Adresse werden Abos geschickt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isSubscriptionTo();

    /** Column definition for IsSubscriptionTo */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsSubscriptionTo = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "IsSubscriptionTo", null);
    /** Column name IsSubscriptionTo */
    public static final String COLUMNNAME_IsSubscriptionTo = "IsSubscriptionTo";

	/**
	 * Set Abo Standard Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsSubscriptionToDefault (boolean IsSubscriptionToDefault);

	/**
	 * Get Abo Standard Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isSubscriptionToDefault();

    /** Column definition for IsSubscriptionToDefault */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_IsSubscriptionToDefault = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "IsSubscriptionToDefault", null);
    /** Column name IsSubscriptionToDefault */
    public static final String COLUMNNAME_IsSubscriptionToDefault = "IsSubscriptionToDefault";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_Phone = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "Phone", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_Phone2 = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "Phone2", null);
    /** Column name Phone2 */
    public static final String COLUMNNAME_Phone2 = "Phone2";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, org.compiere.model.I_AD_User>(I_C_BPartner_Location.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Visitors Address.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setVisitorsAddress (boolean VisitorsAddress);

	/**
	 * Get Visitors Address.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isVisitorsAddress();

    /** Column definition for VisitorsAddress */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object> COLUMN_VisitorsAddress = new org.adempiere.model.ModelColumn<I_C_BPartner_Location, Object>(I_C_BPartner_Location.class, "VisitorsAddress", null);
    /** Column name VisitorsAddress */
    public static final String COLUMNNAME_VisitorsAddress = "VisitorsAddress";
}
