package de.metas.ui.web.base.model;


/** Generated Interface for WEBUI_DashboardItem
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_WEBUI_DashboardItem 
{

    /** TableName=WEBUI_DashboardItem */
    public static final String Table_Name = "WEBUI_DashboardItem";

    /** AD_Table_ID=540796 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, org.compiere.model.I_AD_Client>(I_WEBUI_DashboardItem.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, org.compiere.model.I_AD_Org>(I_WEBUI_DashboardItem.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, Object>(I_WEBUI_DashboardItem.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, org.compiere.model.I_AD_User>(I_WEBUI_DashboardItem.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, Object>(I_WEBUI_DashboardItem.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, Object>(I_WEBUI_DashboardItem.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, Object>(I_WEBUI_DashboardItem.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, org.compiere.model.I_AD_User>(I_WEBUI_DashboardItem.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set URL.
	 * Full URL address - e.g. http://www.adempiere.org
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setURL (java.lang.String URL);

	/**
	 * Get URL.
	 * Full URL address - e.g. http://www.adempiere.org
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getURL();

    /** Column definition for URL */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, Object> COLUMN_URL = new org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, Object>(I_WEBUI_DashboardItem.class, "URL", null);
    /** Column name URL */
    public static final String COLUMNNAME_URL = "URL";

	/**
	 * Set Dashboard.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_Dashboard_ID (int WEBUI_Dashboard_ID);

	/**
	 * Get Dashboard.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getWEBUI_Dashboard_ID();

	public de.metas.ui.web.base.model.I_WEBUI_Dashboard getWEBUI_Dashboard();

	public void setWEBUI_Dashboard(de.metas.ui.web.base.model.I_WEBUI_Dashboard WEBUI_Dashboard);

    /** Column definition for WEBUI_Dashboard_ID */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, de.metas.ui.web.base.model.I_WEBUI_Dashboard> COLUMN_WEBUI_Dashboard_ID = new org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, de.metas.ui.web.base.model.I_WEBUI_Dashboard>(I_WEBUI_DashboardItem.class, "WEBUI_Dashboard_ID", de.metas.ui.web.base.model.I_WEBUI_Dashboard.class);
    /** Column name WEBUI_Dashboard_ID */
    public static final String COLUMNNAME_WEBUI_Dashboard_ID = "WEBUI_Dashboard_ID";

	/**
	 * Set Dashboard item.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_DashboardItem_ID (int WEBUI_DashboardItem_ID);

	/**
	 * Get Dashboard item.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getWEBUI_DashboardItem_ID();

    /** Column definition for WEBUI_DashboardItem_ID */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, Object> COLUMN_WEBUI_DashboardItem_ID = new org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, Object>(I_WEBUI_DashboardItem.class, "WEBUI_DashboardItem_ID", null);
    /** Column name WEBUI_DashboardItem_ID */
    public static final String COLUMNNAME_WEBUI_DashboardItem_ID = "WEBUI_DashboardItem_ID";

	/**
	 * Set Gang (X).
	 * X-Dimension, z.B. Gang
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setX (int X);

	/**
	 * Get Gang (X).
	 * X-Dimension, z.B. Gang
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getX();

    /** Column definition for X */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, Object> COLUMN_X = new org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, Object>(I_WEBUI_DashboardItem.class, "X", null);
    /** Column name X */
    public static final String COLUMNNAME_X = "X";

	/**
	 * Set Fach (Y).
	 * Y-Dimension, z.B. Fach
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setY (int Y);

	/**
	 * Get Fach (Y).
	 * Y-Dimension, z.B. Fach
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getY();

    /** Column definition for Y */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, Object> COLUMN_Y = new org.adempiere.model.ModelColumn<I_WEBUI_DashboardItem, Object>(I_WEBUI_DashboardItem.class, "Y", null);
    /** Column name Y */
    public static final String COLUMNNAME_Y = "Y";
}
