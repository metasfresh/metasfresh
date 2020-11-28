package de.metas.elasticsearch.model;


/** Generated Interface for ES_FTS_Index
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_ES_FTS_Index 
{

    /** TableName=ES_FTS_Index */
    public static final String Table_Name = "ES_FTS_Index";

    /** AD_Table_ID=540990 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

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
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_Index, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_ES_FTS_Index, org.compiere.model.I_AD_Client>(I_ES_FTS_Index.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_Index, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_ES_FTS_Index, org.compiere.model.I_AD_Org>(I_ES_FTS_Index.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_Index, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_ES_FTS_Index, org.compiere.model.I_AD_Table>(I_ES_FTS_Index.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

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
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_Index, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_ES_FTS_Index, Object>(I_ES_FTS_Index.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_Index, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_ES_FTS_Index, org.compiere.model.I_AD_User>(I_ES_FTS_Index.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set FTS Index.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setES_FTS_Index_ID (int ES_FTS_Index_ID);

	/**
	 * Get FTS Index.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getES_FTS_Index_ID();

    /** Column definition for ES_FTS_Index_ID */
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_Index, Object> COLUMN_ES_FTS_Index_ID = new org.adempiere.model.ModelColumn<I_ES_FTS_Index, Object>(I_ES_FTS_Index.class, "ES_FTS_Index_ID", null);
    /** Column name ES_FTS_Index_ID */
    public static final String COLUMNNAME_ES_FTS_Index_ID = "ES_FTS_Index_ID";

	/**
	 * Set Full Text Search Template.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setES_FTS_Template_ID (int ES_FTS_Template_ID);

	/**
	 * Get Full Text Search Template.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getES_FTS_Template_ID();

	public de.metas.elasticsearch.model.I_ES_FTS_Template getES_FTS_Template();

	public void setES_FTS_Template(de.metas.elasticsearch.model.I_ES_FTS_Template ES_FTS_Template);

    /** Column definition for ES_FTS_Template_ID */
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_Index, de.metas.elasticsearch.model.I_ES_FTS_Template> COLUMN_ES_FTS_Template_ID = new org.adempiere.model.ModelColumn<I_ES_FTS_Index, de.metas.elasticsearch.model.I_ES_FTS_Template>(I_ES_FTS_Index.class, "ES_FTS_Template_ID", de.metas.elasticsearch.model.I_ES_FTS_Template.class);
    /** Column name ES_FTS_Template_ID */
    public static final String COLUMNNAME_ES_FTS_Template_ID = "ES_FTS_Template_ID";

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
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_Index, Object> COLUMN_ES_Index = new org.adempiere.model.ModelColumn<I_ES_FTS_Index, Object>(I_ES_FTS_Index.class, "ES_Index", null);
    /** Column name ES_Index */
    public static final String COLUMNNAME_ES_Index = "ES_Index";

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
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_Index, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_ES_FTS_Index, Object>(I_ES_FTS_Index.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_Index, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_ES_FTS_Index, Object>(I_ES_FTS_Index.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_Index, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_ES_FTS_Index, org.compiere.model.I_AD_User>(I_ES_FTS_Index.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
