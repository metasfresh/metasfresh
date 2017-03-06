package de.metas.ui.web.base.model;


/** Generated Interface for WEBUI_KPI_Field
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_WEBUI_KPI_Field 
{

    /** TableName=WEBUI_KPI_Field */
    public static final String Table_Name = "WEBUI_KPI_Field";

    /** AD_Table_ID=540802 */
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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, org.compiere.model.I_AD_Client>(I_WEBUI_KPI_Field.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set System-Element.
	 * Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Element_ID (int AD_Element_ID);

	/**
	 * Get System-Element.
	 * Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Element_ID();

	public org.compiere.model.I_AD_Element getAD_Element();

	public void setAD_Element(org.compiere.model.I_AD_Element AD_Element);

    /** Column definition for AD_Element_ID */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, org.compiere.model.I_AD_Element> COLUMN_AD_Element_ID = new org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, org.compiere.model.I_AD_Element>(I_WEBUI_KPI_Field.class, "AD_Element_ID", org.compiere.model.I_AD_Element.class);
    /** Column name AD_Element_ID */
    public static final String COLUMNNAME_AD_Element_ID = "AD_Element_ID";

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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, org.compiere.model.I_AD_Org>(I_WEBUI_KPI_Field.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Referenz.
	 * Systemreferenz und Validierung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Reference_ID (int AD_Reference_ID);

	/**
	 * Get Referenz.
	 * Systemreferenz und Validierung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Reference_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference();

	public void setAD_Reference(org.compiere.model.I_AD_Reference AD_Reference);

    /** Column definition for AD_Reference_ID */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_ID = new org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, org.compiere.model.I_AD_Reference>(I_WEBUI_KPI_Field.class, "AD_Reference_ID", org.compiere.model.I_AD_Reference.class);
    /** Column name AD_Reference_ID */
    public static final String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, Object>(I_WEBUI_KPI_Field.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, org.compiere.model.I_AD_User>(I_WEBUI_KPI_Field.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Elasticsearch field path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setES_FieldPath (java.lang.String ES_FieldPath);

	/**
	 * Get Elasticsearch field path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getES_FieldPath();

    /** Column definition for ES_FieldPath */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, Object> COLUMN_ES_FieldPath = new org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, Object>(I_WEBUI_KPI_Field.class, "ES_FieldPath", null);
    /** Column name ES_FieldPath */
    public static final String COLUMNNAME_ES_FieldPath = "ES_FieldPath";

	/**
	 * Set Elasticseach time field.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setES_TimeField (boolean ES_TimeField);

	/**
	 * Get Elasticseach time field.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isES_TimeField();

    /** Column definition for ES_TimeField */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, Object> COLUMN_ES_TimeField = new org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, Object>(I_WEBUI_KPI_Field.class, "ES_TimeField", null);
    /** Column name ES_TimeField */
    public static final String COLUMNNAME_ES_TimeField = "ES_TimeField";

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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, Object>(I_WEBUI_KPI_Field.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, Object>(I_WEBUI_KPI_Field.class, "Name", null);
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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, Object>(I_WEBUI_KPI_Field.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, org.compiere.model.I_AD_User>(I_WEBUI_KPI_Field.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set WEBUI_KPI_Field.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_KPI_Field_ID (int WEBUI_KPI_Field_ID);

	/**
	 * Get WEBUI_KPI_Field.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getWEBUI_KPI_Field_ID();

    /** Column definition for WEBUI_KPI_Field_ID */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, Object> COLUMN_WEBUI_KPI_Field_ID = new org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, Object>(I_WEBUI_KPI_Field.class, "WEBUI_KPI_Field_ID", null);
    /** Column name WEBUI_KPI_Field_ID */
    public static final String COLUMNNAME_WEBUI_KPI_Field_ID = "WEBUI_KPI_Field_ID";

	/**
	 * Set KPI.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_KPI_ID (int WEBUI_KPI_ID);

	/**
	 * Get KPI.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getWEBUI_KPI_ID();

	public de.metas.ui.web.base.model.I_WEBUI_KPI getWEBUI_KPI();

	public void setWEBUI_KPI(de.metas.ui.web.base.model.I_WEBUI_KPI WEBUI_KPI);

    /** Column definition for WEBUI_KPI_ID */
    public static final org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, de.metas.ui.web.base.model.I_WEBUI_KPI> COLUMN_WEBUI_KPI_ID = new org.adempiere.model.ModelColumn<I_WEBUI_KPI_Field, de.metas.ui.web.base.model.I_WEBUI_KPI>(I_WEBUI_KPI_Field.class, "WEBUI_KPI_ID", de.metas.ui.web.base.model.I_WEBUI_KPI.class);
    /** Column name WEBUI_KPI_ID */
    public static final String COLUMNNAME_WEBUI_KPI_ID = "WEBUI_KPI_ID";
}
