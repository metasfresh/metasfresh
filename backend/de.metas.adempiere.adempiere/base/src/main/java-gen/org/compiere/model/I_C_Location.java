package org.compiere.model;


/** Generated Interface for C_Location
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Location 
{

    /** TableName=C_Location */
    public static final String Table_Name = "C_Location";

    /** AD_Table_ID=162 */
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
    public static final org.adempiere.model.ModelColumn<I_C_Location, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Location, org.compiere.model.I_AD_Client>(I_C_Location.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Location, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Location, org.compiere.model.I_AD_Org>(I_C_Location.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Straße und Nr..
	 * Adresszeile 1 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAddress1 (java.lang.String Address1);

	/**
	 * Get Straße und Nr..
	 * Adresszeile 1 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAddress1();

    /** Column definition for Address1 */
    public static final org.adempiere.model.ModelColumn<I_C_Location, Object> COLUMN_Address1 = new org.adempiere.model.ModelColumn<I_C_Location, Object>(I_C_Location.class, "Address1", null);
    /** Column name Address1 */
    public static final String COLUMNNAME_Address1 = "Address1";

	/**
	 * Set Adresszusatz.
	 * Adresszeile 2 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAddress2 (java.lang.String Address2);

	/**
	 * Get Adresszusatz.
	 * Adresszeile 2 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAddress2();

    /** Column definition for Address2 */
    public static final org.adempiere.model.ModelColumn<I_C_Location, Object> COLUMN_Address2 = new org.adempiere.model.ModelColumn<I_C_Location, Object>(I_C_Location.class, "Address2", null);
    /** Column name Address2 */
    public static final String COLUMNNAME_Address2 = "Address2";

	/**
	 * Set Adresszeile 3.
	 * Address Line 3 for the location
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAddress3 (java.lang.String Address3);

	/**
	 * Get Adresszeile 3.
	 * Address Line 3 for the location
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAddress3();

    /** Column definition for Address3 */
    public static final org.adempiere.model.ModelColumn<I_C_Location, Object> COLUMN_Address3 = new org.adempiere.model.ModelColumn<I_C_Location, Object>(I_C_Location.class, "Address3", null);
    /** Column name Address3 */
    public static final String COLUMNNAME_Address3 = "Address3";

	/**
	 * Set Adresszusatz.
	 * Adresszeile 4 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAddress4 (java.lang.String Address4);

	/**
	 * Get Adresszusatz.
	 * Adresszeile 4 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAddress4();

    /** Column definition for Address4 */
    public static final org.adempiere.model.ModelColumn<I_C_Location, Object> COLUMN_Address4 = new org.adempiere.model.ModelColumn<I_C_Location, Object>(I_C_Location.class, "Address4", null);
    /** Column name Address4 */
    public static final String COLUMNNAME_Address4 = "Address4";

	/**
	 * Set Ort.
	 * City
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_City_ID (int C_City_ID);

	/**
	 * Get Ort.
	 * City
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_City_ID();

	public org.compiere.model.I_C_City getC_City();

	public void setC_City(org.compiere.model.I_C_City C_City);

    /** Column definition for C_City_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Location, org.compiere.model.I_C_City> COLUMN_C_City_ID = new org.adempiere.model.ModelColumn<I_C_Location, org.compiere.model.I_C_City>(I_C_Location.class, "C_City_ID", org.compiere.model.I_C_City.class);
    /** Column name C_City_ID */
    public static final String COLUMNNAME_C_City_ID = "C_City_ID";

	/**
	 * Set Land.
	 * Country
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Land.
	 * Country
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country();

	public void setC_Country(org.compiere.model.I_C_Country C_Country);

    /** Column definition for C_Country_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Location, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new org.adempiere.model.ModelColumn<I_C_Location, org.compiere.model.I_C_Country>(I_C_Location.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Set Anschrift.
	 * Location or Address
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Location_ID (int C_Location_ID);

	/**
	 * Get Anschrift.
	 * Location or Address
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Location_ID();

    /** Column definition for C_Location_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Location, Object> COLUMN_C_Location_ID = new org.adempiere.model.ModelColumn<I_C_Location, Object>(I_C_Location.class, "C_Location_ID", null);
    /** Column name C_Location_ID */
    public static final String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/**
	 * Set Postal codes.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Postal_ID (int C_Postal_ID);

	/**
	 * Get Postal codes.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Postal_ID();

	public org.compiere.model.I_C_Postal getC_Postal();

	public void setC_Postal(org.compiere.model.I_C_Postal C_Postal);

    /** Column definition for C_Postal_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Location, org.compiere.model.I_C_Postal> COLUMN_C_Postal_ID = new org.adempiere.model.ModelColumn<I_C_Location, org.compiere.model.I_C_Postal>(I_C_Location.class, "C_Postal_ID", org.compiere.model.I_C_Postal.class);
    /** Column name C_Postal_ID */
    public static final String COLUMNNAME_C_Postal_ID = "C_Postal_ID";

	/**
	 * Set Region.
	 * Identifies a geographical Region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Region_ID (int C_Region_ID);

	/**
	 * Get Region.
	 * Identifies a geographical Region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Region_ID();

	public org.compiere.model.I_C_Region getC_Region();

	public void setC_Region(org.compiere.model.I_C_Region C_Region);

    /** Column definition for C_Region_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Location, org.compiere.model.I_C_Region> COLUMN_C_Region_ID = new org.adempiere.model.ModelColumn<I_C_Location, org.compiere.model.I_C_Region>(I_C_Location.class, "C_Region_ID", org.compiere.model.I_C_Region.class);
    /** Column name C_Region_ID */
    public static final String COLUMNNAME_C_Region_ID = "C_Region_ID";

	/**
	 * Set C/O.
	 * In care of
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCareOf (java.lang.String CareOf);

	/**
	 * Get C/O.
	 * In care of
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCareOf();

    /** Column definition for CareOf */
    public static final org.adempiere.model.ModelColumn<I_C_Location, Object> COLUMN_CareOf = new org.adempiere.model.ModelColumn<I_C_Location, Object>(I_C_Location.class, "CareOf", null);
    /** Column name CareOf */
    public static final String COLUMNNAME_CareOf = "CareOf";

	/**
	 * Set Ort.
	 * Identifies a City
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCity (java.lang.String City);

	/**
	 * Get Ort.
	 * Identifies a City
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCity();

    /** Column definition for City */
    public static final org.adempiere.model.ModelColumn<I_C_Location, Object> COLUMN_City = new org.adempiere.model.ModelColumn<I_C_Location, Object>(I_C_Location.class, "City", null);
    /** Column name City */
    public static final String COLUMNNAME_City = "City";

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
    public static final org.adempiere.model.ModelColumn<I_C_Location, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Location, Object>(I_C_Location.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Location, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Location, org.compiere.model.I_AD_User>(I_C_Location.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Geocoding Error.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGeocoding_Issue_ID (int Geocoding_Issue_ID);

	/**
	 * Get Geocoding Error.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getGeocoding_Issue_ID();

	public org.compiere.model.I_AD_Issue getGeocoding_Issue();

	public void setGeocoding_Issue(org.compiere.model.I_AD_Issue Geocoding_Issue);

    /** Column definition for Geocoding_Issue_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Location, org.compiere.model.I_AD_Issue> COLUMN_Geocoding_Issue_ID = new org.adempiere.model.ModelColumn<I_C_Location, org.compiere.model.I_AD_Issue>(I_C_Location.class, "Geocoding_Issue_ID", org.compiere.model.I_AD_Issue.class);
    /** Column name Geocoding_Issue_ID */
    public static final String COLUMNNAME_Geocoding_Issue_ID = "Geocoding_Issue_ID";

	/**
	 * Set Geocoding Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGeocodingStatus (java.lang.String GeocodingStatus);

	/**
	 * Get Geocoding Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGeocodingStatus();

    /** Column definition for GeocodingStatus */
    public static final org.adempiere.model.ModelColumn<I_C_Location, Object> COLUMN_GeocodingStatus = new org.adempiere.model.ModelColumn<I_C_Location, Object>(I_C_Location.class, "GeocodingStatus", null);
    /** Column name GeocodingStatus */
    public static final String COLUMNNAME_GeocodingStatus = "GeocodingStatus";

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
    public static final org.adempiere.model.ModelColumn<I_C_Location, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Location, Object>(I_C_Location.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set PLZ verifiziert.
	 * Sagt aus, ob Postleitzahl der Adresse verifiziert wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPostalValidated (boolean IsPostalValidated);

	/**
	 * Get PLZ verifiziert.
	 * Sagt aus, ob Postleitzahl der Adresse verifiziert wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPostalValidated();

    /** Column definition for IsPostalValidated */
    public static final org.adempiere.model.ModelColumn<I_C_Location, Object> COLUMN_IsPostalValidated = new org.adempiere.model.ModelColumn<I_C_Location, Object>(I_C_Location.class, "IsPostalValidated", null);
    /** Column name IsPostalValidated */
    public static final String COLUMNNAME_IsPostalValidated = "IsPostalValidated";

	/**
	 * Set Latitude.
	 * Geographical latitude
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLatitude (java.math.BigDecimal Latitude);

	/**
	 * Get Latitude.
	 * Geographical latitude
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLatitude();

    /** Column definition for Latitude */
    public static final org.adempiere.model.ModelColumn<I_C_Location, Object> COLUMN_Latitude = new org.adempiere.model.ModelColumn<I_C_Location, Object>(I_C_Location.class, "Latitude", null);
    /** Column name Latitude */
    public static final String COLUMNNAME_Latitude = "Latitude";

	/**
	 * Set Längengrad.
	 * Geographical longitude
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLongitude (java.math.BigDecimal Longitude);

	/**
	 * Get Längengrad.
	 * Geographical longitude
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getLongitude();

    /** Column definition for Longitude */
    public static final org.adempiere.model.ModelColumn<I_C_Location, Object> COLUMN_Longitude = new org.adempiere.model.ModelColumn<I_C_Location, Object>(I_C_Location.class, "Longitude", null);
    /** Column name Longitude */
    public static final String COLUMNNAME_Longitude = "Longitude";

	/**
	 * Set Postfach.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPOBox (java.lang.String POBox);

	/**
	 * Get Postfach.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPOBox();

    /** Column definition for POBox */
    public static final org.adempiere.model.ModelColumn<I_C_Location, Object> COLUMN_POBox = new org.adempiere.model.ModelColumn<I_C_Location, Object>(I_C_Location.class, "POBox", null);
    /** Column name POBox */
    public static final String COLUMNNAME_POBox = "POBox";

	/**
	 * Set PLZ.
	 * Postal code
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPostal (java.lang.String Postal);

	/**
	 * Get PLZ.
	 * Postal code
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPostal();

    /** Column definition for Postal */
    public static final org.adempiere.model.ModelColumn<I_C_Location, Object> COLUMN_Postal = new org.adempiere.model.ModelColumn<I_C_Location, Object>(I_C_Location.class, "Postal", null);
    /** Column name Postal */
    public static final String COLUMNNAME_Postal = "Postal";

	/**
	 * Set -.
	 * Additional ZIP or Postal code
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPostal_Add (java.lang.String Postal_Add);

	/**
	 * Get -.
	 * Additional ZIP or Postal code
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPostal_Add();

    /** Column definition for Postal_Add */
    public static final org.adempiere.model.ModelColumn<I_C_Location, Object> COLUMN_Postal_Add = new org.adempiere.model.ModelColumn<I_C_Location, Object>(I_C_Location.class, "Postal_Add", null);
    /** Column name Postal_Add */
    public static final String COLUMNNAME_Postal_Add = "Postal_Add";

	/**
	 * Set Region.
	 * Name of the Region
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRegionName (java.lang.String RegionName);

	/**
	 * Get Region.
	 * Name of the Region
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRegionName();

    /** Column definition for RegionName */
    public static final org.adempiere.model.ModelColumn<I_C_Location, Object> COLUMN_RegionName = new org.adempiere.model.ModelColumn<I_C_Location, Object>(I_C_Location.class, "RegionName", null);
    /** Column name RegionName */
    public static final String COLUMNNAME_RegionName = "RegionName";

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
    public static final org.adempiere.model.ModelColumn<I_C_Location, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Location, Object>(I_C_Location.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Location, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Location, org.compiere.model.I_AD_User>(I_C_Location.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
