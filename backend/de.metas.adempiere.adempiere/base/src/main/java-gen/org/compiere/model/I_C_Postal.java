package org.compiere.model;


/** Generated Interface for C_Postal
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Postal 
{

    /** TableName=C_Postal */
    public static final String Table_Name = "C_Postal";

    /** AD_Table_ID=540317 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

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
    public static final org.adempiere.model.ModelColumn<I_C_Postal, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Postal, org.compiere.model.I_AD_Client>(I_C_Postal.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Postal, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Postal, org.compiere.model.I_AD_Org>(I_C_Postal.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Ort.
	 * Ort
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_City_ID (int C_City_ID);

	/**
	 * Get Ort.
	 * Ort
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_City_ID();

	public org.compiere.model.I_C_City getC_City();

	public void setC_City(org.compiere.model.I_C_City C_City);

    /** Column definition for C_City_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Postal, org.compiere.model.I_C_City> COLUMN_C_City_ID = new org.adempiere.model.ModelColumn<I_C_Postal, org.compiere.model.I_C_City>(I_C_Postal.class, "C_City_ID", org.compiere.model.I_C_City.class);
    /** Column name C_City_ID */
    public static final String COLUMNNAME_C_City_ID = "C_City_ID";

	/**
	 * Set Land.
	 * Land
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Land.
	 * Land
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Country_ID();

	public org.compiere.model.I_C_Country getC_Country();

	public void setC_Country(org.compiere.model.I_C_Country C_Country);

    /** Column definition for C_Country_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Postal, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new org.adempiere.model.ModelColumn<I_C_Postal, org.compiere.model.I_C_Country>(I_C_Postal.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Set Ort.
	 * Name des Ortes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCity (java.lang.String City);

	/**
	 * Get Ort.
	 * Name des Ortes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCity();

    /** Column definition for City */
    public static final org.adempiere.model.ModelColumn<I_C_Postal, Object> COLUMN_City = new org.adempiere.model.ModelColumn<I_C_Postal, Object>(I_C_Postal.class, "City", null);
    /** Column name City */
    public static final String COLUMNNAME_City = "City";

	/**
	 * Set Postal codes.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Postal_ID (int C_Postal_ID);

	/**
	 * Get Postal codes.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Postal_ID();

    /** Column definition for C_Postal_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Postal, Object> COLUMN_C_Postal_ID = new org.adempiere.model.ModelColumn<I_C_Postal, Object>(I_C_Postal.class, "C_Postal_ID", null);
    /** Column name C_Postal_ID */
    public static final String COLUMNNAME_C_Postal_ID = "C_Postal_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Postal, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Postal, Object>(I_C_Postal.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Postal, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Postal, org.compiere.model.I_AD_User>(I_C_Postal.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Region.
	 * Identifiziert eine geographische Region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Region_ID (int C_Region_ID);

	/**
	 * Get Region.
	 * Identifiziert eine geographische Region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Region_ID();

	public org.compiere.model.I_C_Region getC_Region();

	public void setC_Region(org.compiere.model.I_C_Region C_Region);

    /** Column definition for C_Region_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Postal, org.compiere.model.I_C_Region> COLUMN_C_Region_ID = new org.adempiere.model.ModelColumn<I_C_Postal, org.compiere.model.I_C_Region>(I_C_Postal.class, "C_Region_ID", org.compiere.model.I_C_Region.class);
    /** Column name C_Region_ID */
    public static final String COLUMNNAME_C_Region_ID = "C_Region_ID";

	/**
	 * Set Bezirk.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDistrict (java.lang.String District);

	/**
	 * Get Bezirk.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDistrict();

    /** Column definition for District */
    public static final org.adempiere.model.ModelColumn<I_C_Postal, Object> COLUMN_District = new org.adempiere.model.ModelColumn<I_C_Postal, Object>(I_C_Postal.class, "District", null);
    /** Column name District */
    public static final String COLUMNNAME_District = "District";

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
    public static final org.adempiere.model.ModelColumn<I_C_Postal, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Postal, Object>(I_C_Postal.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Manuell.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsManual (boolean IsManual);

	/**
	 * Get Manuell.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isManual();

    /** Column definition for IsManual */
    public static final org.adempiere.model.ModelColumn<I_C_Postal, Object> COLUMN_IsManual = new org.adempiere.model.ModelColumn<I_C_Postal, Object>(I_C_Postal.class, "IsManual", null);
    /** Column name IsManual */
    public static final String COLUMNNAME_IsManual = "IsManual";

	/**
	 * Set DPD Validated.
	 * Record was validated on DPD database
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsValidDPD (boolean IsValidDPD);

	/**
	 * Get DPD Validated.
	 * Record was validated on DPD database
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isValidDPD();

    /** Column definition for IsValidDPD */
    public static final org.adempiere.model.ModelColumn<I_C_Postal, Object> COLUMN_IsValidDPD = new org.adempiere.model.ModelColumn<I_C_Postal, Object>(I_C_Postal.class, "IsValidDPD", null);
    /** Column name IsValidDPD */
    public static final String COLUMNNAME_IsValidDPD = "IsValidDPD";

	/**
	 * Set Bereinigung notwendig.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setNonStdAddress (boolean NonStdAddress);

	/**
	 * Get Bereinigung notwendig.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isNonStdAddress();

    /** Column definition for NonStdAddress */
    public static final org.adempiere.model.ModelColumn<I_C_Postal, Object> COLUMN_NonStdAddress = new org.adempiere.model.ModelColumn<I_C_Postal, Object>(I_C_Postal.class, "NonStdAddress", null);
    /** Column name NonStdAddress */
    public static final String COLUMNNAME_NonStdAddress = "NonStdAddress";

	/**
	 * Set PLZ.
	 * Postleitzahl
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPostal (java.lang.String Postal);

	/**
	 * Get PLZ.
	 * Postleitzahl
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPostal();

    /** Column definition for Postal */
    public static final org.adempiere.model.ModelColumn<I_C_Postal, Object> COLUMN_Postal = new org.adempiere.model.ModelColumn<I_C_Postal, Object>(I_C_Postal.class, "Postal", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Postal, Object> COLUMN_Postal_Add = new org.adempiere.model.ModelColumn<I_C_Postal, Object>(I_C_Postal.class, "Postal_Add", null);
    /** Column name Postal_Add */
    public static final String COLUMNNAME_Postal_Add = "Postal_Add";

	/**
	 * Set Region.
	 * Name der Region
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRegionName (java.lang.String RegionName);

	/**
	 * Get Region.
	 * Name der Region
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRegionName();

    /** Column definition for RegionName */
    public static final org.adempiere.model.ModelColumn<I_C_Postal, Object> COLUMN_RegionName = new org.adempiere.model.ModelColumn<I_C_Postal, Object>(I_C_Postal.class, "RegionName", null);
    /** Column name RegionName */
    public static final String COLUMNNAME_RegionName = "RegionName";

	/**
	 * Set Gemeinde.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTownship (java.lang.String Township);

	/**
	 * Get Gemeinde.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTownship();

    /** Column definition for Township */
    public static final org.adempiere.model.ModelColumn<I_C_Postal, Object> COLUMN_Township = new org.adempiere.model.ModelColumn<I_C_Postal, Object>(I_C_Postal.class, "Township", null);
    /** Column name Township */
    public static final String COLUMNNAME_Township = "Township";

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
    public static final org.adempiere.model.ModelColumn<I_C_Postal, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Postal, Object>(I_C_Postal.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Postal, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Postal, org.compiere.model.I_AD_User>(I_C_Postal.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
