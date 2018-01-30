package de.metas.vertical.pharma.vendor.gateway.mvs3.model;


/** Generated Interface for MSV3_VerfuegbarkeitAnteil
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MSV3_VerfuegbarkeitAnteil 
{

    /** TableName=MSV3_VerfuegbarkeitAnteil */
    public static final String Table_Name = "MSV3_VerfuegbarkeitAnteil";

    /** AD_Table_ID=540909 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, org.compiere.model.I_AD_Client>(I_MSV3_VerfuegbarkeitAnteil.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, org.compiere.model.I_AD_Org>(I_MSV3_VerfuegbarkeitAnteil.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, Object>(I_MSV3_VerfuegbarkeitAnteil.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, org.compiere.model.I_AD_User>(I_MSV3_VerfuegbarkeitAnteil.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, Object>(I_MSV3_VerfuegbarkeitAnteil.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Grund.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Grund (java.lang.String MSV3_Grund);

	/**
	 * Get Grund.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_Grund();

    /** Column definition for MSV3_Grund */
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, Object> COLUMN_MSV3_Grund = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, Object>(I_MSV3_VerfuegbarkeitAnteil.class, "MSV3_Grund", null);
    /** Column name MSV3_Grund */
    public static final String COLUMNNAME_MSV3_Grund = "MSV3_Grund";

	/**
	 * Set Lieferzeitpunkt.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Lieferzeitpunkt (java.sql.Timestamp MSV3_Lieferzeitpunkt);

	/**
	 * Get Lieferzeitpunkt.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getMSV3_Lieferzeitpunkt();

    /** Column definition for MSV3_Lieferzeitpunkt */
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, Object> COLUMN_MSV3_Lieferzeitpunkt = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, Object>(I_MSV3_VerfuegbarkeitAnteil.class, "MSV3_Lieferzeitpunkt", null);
    /** Column name MSV3_Lieferzeitpunkt */
    public static final String COLUMNNAME_MSV3_Lieferzeitpunkt = "MSV3_Lieferzeitpunkt";

	/**
	 * Set MSV3_Menge.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Menge (int MSV3_Menge);

	/**
	 * Get MSV3_Menge.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMSV3_Menge();

    /** Column definition for MSV3_Menge */
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, Object> COLUMN_MSV3_Menge = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, Object>(I_MSV3_VerfuegbarkeitAnteil.class, "MSV3_Menge", null);
    /** Column name MSV3_Menge */
    public static final String COLUMNNAME_MSV3_Menge = "MSV3_Menge";

	/**
	 * Set Tourabweichung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Tourabweichung (boolean MSV3_Tourabweichung);

	/**
	 * Get Tourabweichung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isMSV3_Tourabweichung();

    /** Column definition for MSV3_Tourabweichung */
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, Object> COLUMN_MSV3_Tourabweichung = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, Object>(I_MSV3_VerfuegbarkeitAnteil.class, "MSV3_Tourabweichung", null);
    /** Column name MSV3_Tourabweichung */
    public static final String COLUMNNAME_MSV3_Tourabweichung = "MSV3_Tourabweichung";

	/**
	 * Set Typ.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_Typ (java.lang.String MSV3_Typ);

	/**
	 * Get Typ.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMSV3_Typ();

    /** Column definition for MSV3_Typ */
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, Object> COLUMN_MSV3_Typ = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, Object>(I_MSV3_VerfuegbarkeitAnteil.class, "MSV3_Typ", null);
    /** Column name MSV3_Typ */
    public static final String COLUMNNAME_MSV3_Typ = "MSV3_Typ";

	/**
	 * Set MSV3_VerfuegbarkeitAnteil.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_VerfuegbarkeitAnteil_ID (int MSV3_VerfuegbarkeitAnteil_ID);

	/**
	 * Get MSV3_VerfuegbarkeitAnteil.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_VerfuegbarkeitAnteil_ID();

    /** Column definition for MSV3_VerfuegbarkeitAnteil_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, Object> COLUMN_MSV3_VerfuegbarkeitAnteil_ID = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, Object>(I_MSV3_VerfuegbarkeitAnteil.class, "MSV3_VerfuegbarkeitAnteil_ID", null);
    /** Column name MSV3_VerfuegbarkeitAnteil_ID */
    public static final String COLUMNNAME_MSV3_VerfuegbarkeitAnteil_ID = "MSV3_VerfuegbarkeitAnteil_ID";

	/**
	 * Set VerfuegbarkeitsantwortArtikel.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMSV3_VerfuegbarkeitsantwortArtikel_ID (int MSV3_VerfuegbarkeitsantwortArtikel_ID);

	/**
	 * Get VerfuegbarkeitsantwortArtikel.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMSV3_VerfuegbarkeitsantwortArtikel_ID();

	public de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsantwortArtikel getMSV3_VerfuegbarkeitsantwortArtikel();

	public void setMSV3_VerfuegbarkeitsantwortArtikel(de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsantwortArtikel MSV3_VerfuegbarkeitsantwortArtikel);

    /** Column definition for MSV3_VerfuegbarkeitsantwortArtikel_ID */
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsantwortArtikel> COLUMN_MSV3_VerfuegbarkeitsantwortArtikel_ID = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsantwortArtikel>(I_MSV3_VerfuegbarkeitAnteil.class, "MSV3_VerfuegbarkeitsantwortArtikel_ID", de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_VerfuegbarkeitsantwortArtikel.class);
    /** Column name MSV3_VerfuegbarkeitsantwortArtikel_ID */
    public static final String COLUMNNAME_MSV3_VerfuegbarkeitsantwortArtikel_ID = "MSV3_VerfuegbarkeitsantwortArtikel_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, Object>(I_MSV3_VerfuegbarkeitAnteil.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MSV3_VerfuegbarkeitAnteil, org.compiere.model.I_AD_User>(I_MSV3_VerfuegbarkeitAnteil.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
