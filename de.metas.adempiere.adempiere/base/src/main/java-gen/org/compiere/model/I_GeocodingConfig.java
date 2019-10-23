package org.compiere.model;


/** Generated Interface for GeocodingConfig
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_GeocodingConfig 
{

    /** TableName=GeocodingConfig */
    public static final String Table_Name = "GeocodingConfig";

    /** AD_Table_ID=541410 */
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

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_GeocodingConfig, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_GeocodingConfig, org.compiere.model.I_AD_Client>(I_GeocodingConfig.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

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

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_GeocodingConfig, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_GeocodingConfig, org.compiere.model.I_AD_Org>(I_GeocodingConfig.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Cache-Kapazität.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setcacheCapacity (int cacheCapacity);

	/**
	 * Get Cache-Kapazität.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getcacheCapacity();

    /** Column definition for cacheCapacity */
    public static final org.adempiere.model.ModelColumn<I_GeocodingConfig, Object> COLUMN_cacheCapacity = new org.adempiere.model.ModelColumn<I_GeocodingConfig, Object>(I_GeocodingConfig.class, "cacheCapacity", null);
    /** Column name cacheCapacity */
    public static final String COLUMNNAME_cacheCapacity = "cacheCapacity";

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
    public static final org.adempiere.model.ModelColumn<I_GeocodingConfig, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_GeocodingConfig, Object>(I_GeocodingConfig.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_GeocodingConfig, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_GeocodingConfig, org.compiere.model.I_AD_User>(I_GeocodingConfig.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Geocoding Konfiguration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGeocodingConfig_ID (int GeocodingConfig_ID);

	/**
	 * Get Geocoding Konfiguration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getGeocodingConfig_ID();

    /** Column definition for GeocodingConfig_ID */
    public static final org.adempiere.model.ModelColumn<I_GeocodingConfig, Object> COLUMN_GeocodingConfig_ID = new org.adempiere.model.ModelColumn<I_GeocodingConfig, Object>(I_GeocodingConfig.class, "GeocodingConfig_ID", null);
    /** Column name GeocodingConfig_ID */
    public static final String COLUMNNAME_GeocodingConfig_ID = "GeocodingConfig_ID";

	/**
	 * Set Geocoding Dienst Provider.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGeocodingProvider (java.lang.String GeocodingProvider);

	/**
	 * Get Geocoding Dienst Provider.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGeocodingProvider();

    /** Column definition for GeocodingProvider */
    public static final org.adempiere.model.ModelColumn<I_GeocodingConfig, Object> COLUMN_GeocodingProvider = new org.adempiere.model.ModelColumn<I_GeocodingConfig, Object>(I_GeocodingConfig.class, "GeocodingProvider", null);
    /** Column name GeocodingProvider */
    public static final String COLUMNNAME_GeocodingProvider = "GeocodingProvider";

	/**
	 * Set Google Maps - API-Schlüssel.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setgmaps_ApiKey (java.lang.String gmaps_ApiKey);

	/**
	 * Get Google Maps - API-Schlüssel.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getgmaps_ApiKey();

    /** Column definition for gmaps_ApiKey */
    public static final org.adempiere.model.ModelColumn<I_GeocodingConfig, Object> COLUMN_gmaps_ApiKey = new org.adempiere.model.ModelColumn<I_GeocodingConfig, Object>(I_GeocodingConfig.class, "gmaps_ApiKey", null);
    /** Column name gmaps_ApiKey */
    public static final String COLUMNNAME_gmaps_ApiKey = "gmaps_ApiKey";

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
    public static final org.adempiere.model.ModelColumn<I_GeocodingConfig, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_GeocodingConfig, Object>(I_GeocodingConfig.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Open Street Maps - Basis-URL.
	 * The Base URL after which all parameters are added
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setosm_baseURL (java.lang.String osm_baseURL);

	/**
	 * Get Open Street Maps - Basis-URL.
	 * The Base URL after which all parameters are added
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getosm_baseURL();

    /** Column definition for osm_baseURL */
    public static final org.adempiere.model.ModelColumn<I_GeocodingConfig, Object> COLUMN_osm_baseURL = new org.adempiere.model.ModelColumn<I_GeocodingConfig, Object>(I_GeocodingConfig.class, "osm_baseURL", null);
    /** Column name osm_baseURL */
    public static final String COLUMNNAME_osm_baseURL = "osm_baseURL";

	/**
	 * Set Open Street Maps - Millisekunden zwischen den Anfragen.
	 * How many milliseconds to wait between 2 consecutive requests
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setosm_millisBetweenRequests (int osm_millisBetweenRequests);

	/**
	 * Get Open Street Maps - Millisekunden zwischen den Anfragen.
	 * How many milliseconds to wait between 2 consecutive requests
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getosm_millisBetweenRequests();

    /** Column definition for osm_millisBetweenRequests */
    public static final org.adempiere.model.ModelColumn<I_GeocodingConfig, Object> COLUMN_osm_millisBetweenRequests = new org.adempiere.model.ModelColumn<I_GeocodingConfig, Object>(I_GeocodingConfig.class, "osm_millisBetweenRequests", null);
    /** Column name osm_millisBetweenRequests */
    public static final String COLUMNNAME_osm_millisBetweenRequests = "osm_millisBetweenRequests";

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
    public static final org.adempiere.model.ModelColumn<I_GeocodingConfig, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_GeocodingConfig, Object>(I_GeocodingConfig.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_GeocodingConfig, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_GeocodingConfig, org.compiere.model.I_AD_User>(I_GeocodingConfig.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
