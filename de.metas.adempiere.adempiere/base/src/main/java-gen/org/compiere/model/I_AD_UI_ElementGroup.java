package org.compiere.model;


/** Generated Interface for AD_UI_ElementGroup
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_UI_ElementGroup 
{

    /** TableName=AD_UI_ElementGroup */
    public static final String Table_Name = "AD_UI_ElementGroup";

    /** AD_Table_ID=540782 */
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
    public static final org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, org.compiere.model.I_AD_Client>(I_AD_UI_ElementGroup.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, org.compiere.model.I_AD_Org>(I_AD_UI_ElementGroup.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set UI Column.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_UI_Column_ID (int AD_UI_Column_ID);

	/**
	 * Get UI Column.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_UI_Column_ID();

	public org.compiere.model.I_AD_UI_Column getAD_UI_Column();

	public void setAD_UI_Column(org.compiere.model.I_AD_UI_Column AD_UI_Column);

    /** Column definition for AD_UI_Column_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, org.compiere.model.I_AD_UI_Column> COLUMN_AD_UI_Column_ID = new org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, org.compiere.model.I_AD_UI_Column>(I_AD_UI_ElementGroup.class, "AD_UI_Column_ID", org.compiere.model.I_AD_UI_Column.class);
    /** Column name AD_UI_Column_ID */
    public static final String COLUMNNAME_AD_UI_Column_ID = "AD_UI_Column_ID";

	/**
	 * Set UI Element Group.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_UI_ElementGroup_ID (int AD_UI_ElementGroup_ID);

	/**
	 * Get UI Element Group.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_UI_ElementGroup_ID();

    /** Column definition for AD_UI_ElementGroup_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, Object> COLUMN_AD_UI_ElementGroup_ID = new org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, Object>(I_AD_UI_ElementGroup.class, "AD_UI_ElementGroup_ID", null);
    /** Column name AD_UI_ElementGroup_ID */
    public static final String COLUMNNAME_AD_UI_ElementGroup_ID = "AD_UI_ElementGroup_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, Object>(I_AD_UI_ElementGroup.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, org.compiere.model.I_AD_User>(I_AD_UI_ElementGroup.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, Object>(I_AD_UI_ElementGroup.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, Object>(I_AD_UI_ElementGroup.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, Object>(I_AD_UI_ElementGroup.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set UI Style.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUIStyle (java.lang.String UIStyle);

	/**
	 * Get UI Style.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUIStyle();

    /** Column definition for UIStyle */
    public static final org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, Object> COLUMN_UIStyle = new org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, Object>(I_AD_UI_ElementGroup.class, "UIStyle", null);
    /** Column name UIStyle */
    public static final String COLUMNNAME_UIStyle = "UIStyle";

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
    public static final org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, Object>(I_AD_UI_ElementGroup.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_UI_ElementGroup, org.compiere.model.I_AD_User>(I_AD_UI_ElementGroup.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
