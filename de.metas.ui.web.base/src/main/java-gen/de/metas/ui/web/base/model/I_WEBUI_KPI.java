package de.metas.ui.web.base.model;


/** Generated Interface for WEBUI_KPI
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_WEBUI_KPI 
{

    /** TableName=WEBUI_KPI */
    public static final String Table_Name = "WEBUI_KPI";

    /** AD_Table_ID=540801 */
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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_WEBUI_KPI, org.compiere.model.I_AD_Client>(I_WEBUI_KPI.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_WEBUI_KPI, org.compiere.model.I_AD_Org>(I_WEBUI_KPI.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Chart Type.
	 * Type fo chart to render
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setChartType (java.lang.String ChartType);

	/**
	 * Get Chart Type.
	 * Type fo chart to render
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getChartType();

    /** Column definition for ChartType */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object> COLUMN_ChartType = new org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object>(I_WEBUI_KPI.class, "ChartType", null);
    /** Column name ChartType */
    public static final String COLUMNNAME_ChartType = "ChartType";

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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object>(I_WEBUI_KPI.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_WEBUI_KPI, org.compiere.model.I_AD_User>(I_WEBUI_KPI.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object>(I_WEBUI_KPI.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Elasticsearch Index.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setES_Index (java.lang.String ES_Index);

	/**
	 * Get Elasticsearch Index.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getES_Index();

    /** Column definition for ES_Index */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object> COLUMN_ES_Index = new org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object>(I_WEBUI_KPI.class, "ES_Index", null);
    /** Column name ES_Index */
    public static final String COLUMNNAME_ES_Index = "ES_Index";

	/**
	 * Set Elasticsearch query.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setES_Query (java.lang.String ES_Query);

	/**
	 * Get Elasticsearch query.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getES_Query();

    /** Column definition for ES_Query */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object> COLUMN_ES_Query = new org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object>(I_WEBUI_KPI.class, "ES_Query", null);
    /** Column name ES_Query */
    public static final String COLUMNNAME_ES_Query = "ES_Query";

	/**
	 * Set Time range.
	 * Time range using format 'PnDTnHnMn.nS'
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setES_TimeRange (java.lang.String ES_TimeRange);

	/**
	 * Get Time range.
	 * Time range using format 'PnDTnHnMn.nS'
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getES_TimeRange();

    /** Column definition for ES_TimeRange */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object> COLUMN_ES_TimeRange = new org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object>(I_WEBUI_KPI.class, "ES_TimeRange", null);
    /** Column name ES_TimeRange */
    public static final String COLUMNNAME_ES_TimeRange = "ES_TimeRange";

	/**
	 * Set Elasticsearch Type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setES_Type (java.lang.String ES_Type);

	/**
	 * Get Elasticsearch Type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getES_Type();

    /** Column definition for ES_Type */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object> COLUMN_ES_Type = new org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object>(I_WEBUI_KPI.class, "ES_Type", null);
    /** Column name ES_Type */
    public static final String COLUMNNAME_ES_Type = "ES_Type";

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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object>(I_WEBUI_KPI.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object>(I_WEBUI_KPI.class, "Name", null);
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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object>(I_WEBUI_KPI.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_WEBUI_KPI, org.compiere.model.I_AD_User>(I_WEBUI_KPI.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set KPI.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_KPI_ID (int WEBUI_KPI_ID);

	/**
	 * Get KPI.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getWEBUI_KPI_ID();

    /** Column definition for WEBUI_KPI_ID */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object> COLUMN_WEBUI_KPI_ID = new org.adempiere.model.ModelColumn<I_WEBUI_KPI, Object>(I_WEBUI_KPI.class, "WEBUI_KPI_ID", null);
    /** Column name WEBUI_KPI_ID */
    public static final String COLUMNNAME_WEBUI_KPI_ID = "WEBUI_KPI_ID";
}
