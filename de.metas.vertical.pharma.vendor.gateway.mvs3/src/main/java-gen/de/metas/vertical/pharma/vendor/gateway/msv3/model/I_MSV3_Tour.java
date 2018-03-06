package de.metas.vertical.pharma.vendor.gateway.msv3.model;


/** Generated Interface for MSV3_Tour
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MSV3_Tour 
{

    /** TableName=MSV3_Tour */
    public static final String Table_Name = "MSV3_Tour";

    /** AD_Table_ID=540910 */
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Tour, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MSV3_Tour, org.compiere.model.I_AD_Client>(I_MSV3_Tour.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Tour, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MSV3_Tour, org.compiere.model.I_AD_Org>(I_MSV3_Tour.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Tour, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MSV3_Tour, Object>(I_MSV3_Tour.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Tour, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MSV3_Tour, org.compiere.model.I_AD_User>(I_MSV3_Tour.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Tour, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MSV3_Tour, Object>(I_MSV3_Tour.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set MSV3_BestellungAnteil.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_BestellungAnteil_ID (int MSV3_BestellungAnteil_ID);

	/**
	 * Get MSV3_BestellungAnteil.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMSV3_BestellungAnteil_ID();

	public de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAnteil getMSV3_BestellungAnteil();

	public void setMSV3_BestellungAnteil(de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAnteil MSV3_BestellungAnteil);

    /** Column definition for MSV3_BestellungAnteil_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_Tour, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAnteil> COLUMN_MSV3_BestellungAnteil_ID = new org.adempiere.model.ModelColumn<I_MSV3_Tour, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAnteil>(I_MSV3_Tour.class, "MSV3_BestellungAnteil_ID", de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_BestellungAnteil.class);
    /** Column name MSV3_BestellungAnteil_ID */
    public static final String COLUMNNAME_MSV3_BestellungAnteil_ID = "MSV3_BestellungAnteil_ID";

	/**
	 * Set Tour.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Tour (java.lang.String MSV3_Tour);

	/**
	 * Get Tour.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_Tour();

    /** Column definition for MSV3_Tour */
    public static final org.adempiere.model.ModelColumn<I_MSV3_Tour, Object> COLUMN_MSV3_Tour = new org.adempiere.model.ModelColumn<I_MSV3_Tour, Object>(I_MSV3_Tour.class, "MSV3_Tour", null);
    /** Column name MSV3_Tour */
    public static final String COLUMNNAME_MSV3_Tour = "MSV3_Tour";

	/**
	 * Set MSV3_Tour.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Tour_ID (int MSV3_Tour_ID);

	/**
	 * Get MSV3_Tour.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_Tour_ID();

    /** Column definition for MSV3_Tour_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_Tour, Object> COLUMN_MSV3_Tour_ID = new org.adempiere.model.ModelColumn<I_MSV3_Tour, Object>(I_MSV3_Tour.class, "MSV3_Tour_ID", null);
    /** Column name MSV3_Tour_ID */
    public static final String COLUMNNAME_MSV3_Tour_ID = "MSV3_Tour_ID";

	/**
	 * Set MSV3_VerfuegbarkeitAnteil.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_VerfuegbarkeitAnteil_ID (int MSV3_VerfuegbarkeitAnteil_ID);

	/**
	 * Get MSV3_VerfuegbarkeitAnteil.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMSV3_VerfuegbarkeitAnteil_ID();

	public de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitAnteil getMSV3_VerfuegbarkeitAnteil();

	public void setMSV3_VerfuegbarkeitAnteil(de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitAnteil MSV3_VerfuegbarkeitAnteil);

    /** Column definition for MSV3_VerfuegbarkeitAnteil_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_Tour, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitAnteil> COLUMN_MSV3_VerfuegbarkeitAnteil_ID = new org.adempiere.model.ModelColumn<I_MSV3_Tour, de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitAnteil>(I_MSV3_Tour.class, "MSV3_VerfuegbarkeitAnteil_ID", de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitAnteil.class);
    /** Column name MSV3_VerfuegbarkeitAnteil_ID */
    public static final String COLUMNNAME_MSV3_VerfuegbarkeitAnteil_ID = "MSV3_VerfuegbarkeitAnteil_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Tour, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MSV3_Tour, Object>(I_MSV3_Tour.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_Tour, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MSV3_Tour, org.compiere.model.I_AD_User>(I_MSV3_Tour.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
