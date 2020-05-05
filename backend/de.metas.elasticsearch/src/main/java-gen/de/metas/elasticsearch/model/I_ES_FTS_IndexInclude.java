package de.metas.elasticsearch.model;


/** Generated Interface for ES_FTS_IndexInclude
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_ES_FTS_IndexInclude 
{

    /** TableName=ES_FTS_IndexInclude */
    public static final String Table_Name = "ES_FTS_IndexInclude";

    /** AD_Table_ID=540991 */
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
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, org.compiere.model.I_AD_Client>(I_ES_FTS_IndexInclude.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, org.compiere.model.I_AD_Org>(I_ES_FTS_IndexInclude.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAttributeName (java.lang.String AttributeName);

	/**
	 * Get Attribute Name.
	 * Name of the Attribute
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAttributeName();

    /** Column definition for AttributeName */
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, Object> COLUMN_AttributeName = new org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, Object>(I_ES_FTS_IndexInclude.class, "AttributeName", null);
    /** Column name AttributeName */
    public static final String COLUMNNAME_AttributeName = "AttributeName";

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
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, Object>(I_ES_FTS_IndexInclude.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, org.compiere.model.I_AD_User>(I_ES_FTS_IndexInclude.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, Object>(I_ES_FTS_IndexInclude.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set FTS Index.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setES_FTS_Index_ID (int ES_FTS_Index_ID);

	/**
	 * Get FTS Index.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getES_FTS_Index_ID();

	public de.metas.elasticsearch.model.I_ES_FTS_Index getES_FTS_Index();

	public void setES_FTS_Index(de.metas.elasticsearch.model.I_ES_FTS_Index ES_FTS_Index);

    /** Column definition for ES_FTS_Index_ID */
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, de.metas.elasticsearch.model.I_ES_FTS_Index> COLUMN_ES_FTS_Index_ID = new org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, de.metas.elasticsearch.model.I_ES_FTS_Index>(I_ES_FTS_IndexInclude.class, "ES_FTS_Index_ID", de.metas.elasticsearch.model.I_ES_FTS_Index.class);
    /** Column name ES_FTS_Index_ID */
    public static final String COLUMNNAME_ES_FTS_Index_ID = "ES_FTS_Index_ID";

	/**
	 * Set FTS Index Include.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setES_FTS_IndexInclude_ID (int ES_FTS_IndexInclude_ID);

	/**
	 * Get FTS Index Include.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getES_FTS_IndexInclude_ID();

    /** Column definition for ES_FTS_IndexInclude_ID */
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, Object> COLUMN_ES_FTS_IndexInclude_ID = new org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, Object>(I_ES_FTS_IndexInclude.class, "ES_FTS_IndexInclude_ID", null);
    /** Column name ES_FTS_IndexInclude_ID */
    public static final String COLUMNNAME_ES_FTS_IndexInclude_ID = "ES_FTS_IndexInclude_ID";

	/**
	 * Set Link column.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInclude_LinkColumn_ID (int Include_LinkColumn_ID);

	/**
	 * Get Link column.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getInclude_LinkColumn_ID();

	public org.compiere.model.I_AD_Column getInclude_LinkColumn();

	public void setInclude_LinkColumn(org.compiere.model.I_AD_Column Include_LinkColumn);

    /** Column definition for Include_LinkColumn_ID */
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, org.compiere.model.I_AD_Column> COLUMN_Include_LinkColumn_ID = new org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, org.compiere.model.I_AD_Column>(I_ES_FTS_IndexInclude.class, "Include_LinkColumn_ID", org.compiere.model.I_AD_Column.class);
    /** Column name Include_LinkColumn_ID */
    public static final String COLUMNNAME_Include_LinkColumn_ID = "Include_LinkColumn_ID";

	/**
	 * Set Include table.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInclude_Table_ID (int Include_Table_ID);

	/**
	 * Get Include table.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getInclude_Table_ID();

	public org.compiere.model.I_AD_Table getInclude_Table();

	public void setInclude_Table(org.compiere.model.I_AD_Table Include_Table);

    /** Column definition for Include_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, org.compiere.model.I_AD_Table> COLUMN_Include_Table_ID = new org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, org.compiere.model.I_AD_Table>(I_ES_FTS_IndexInclude.class, "Include_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name Include_Table_ID */
    public static final String COLUMNNAME_Include_Table_ID = "Include_Table_ID";

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
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, Object>(I_ES_FTS_IndexInclude.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, Object>(I_ES_FTS_IndexInclude.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_ES_FTS_IndexInclude, org.compiere.model.I_AD_User>(I_ES_FTS_IndexInclude.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
