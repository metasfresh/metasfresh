package de.metas.dataentry.model;


/** Generated Interface for DataEntry_SubGroup
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DataEntry_SubGroup 
{

    /** TableName=DataEntry_SubGroup */
    public static final String Table_Name = "DataEntry_SubGroup";

    /** AD_Table_ID=541166 */
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, org.compiere.model.I_AD_Client>(I_DataEntry_SubGroup.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, org.compiere.model.I_AD_Org>(I_DataEntry_SubGroup.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, Object>(I_DataEntry_SubGroup.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, org.compiere.model.I_AD_User>(I_DataEntry_SubGroup.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Eingabegruppe.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_Group_ID (int DataEntry_Group_ID);

	/**
	 * Get Eingabegruppe.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDataEntry_Group_ID();

	public de.metas.dataentry.model.I_DataEntry_Group getDataEntry_Group();

	public void setDataEntry_Group(de.metas.dataentry.model.I_DataEntry_Group DataEntry_Group);

    /** Column definition for DataEntry_Group_ID */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, de.metas.dataentry.model.I_DataEntry_Group> COLUMN_DataEntry_Group_ID = new org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, de.metas.dataentry.model.I_DataEntry_Group>(I_DataEntry_SubGroup.class, "DataEntry_Group_ID", de.metas.dataentry.model.I_DataEntry_Group.class);
    /** Column name DataEntry_Group_ID */
    public static final String COLUMNNAME_DataEntry_Group_ID = "DataEntry_Group_ID";

	/**
	 * Set Untergruppe.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_SubGroup_ID (int DataEntry_SubGroup_ID);

	/**
	 * Get Untergruppe.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDataEntry_SubGroup_ID();

    /** Column definition for DataEntry_SubGroup_ID */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, Object> COLUMN_DataEntry_SubGroup_ID = new org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, Object>(I_DataEntry_SubGroup.class, "DataEntry_SubGroup_ID", null);
    /** Column name DataEntry_SubGroup_ID */
    public static final String COLUMNNAME_DataEntry_SubGroup_ID = "DataEntry_SubGroup_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, Object>(I_DataEntry_SubGroup.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, Object>(I_DataEntry_SubGroup.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, Object>(I_DataEntry_SubGroup.class, "Name", null);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, Object>(I_DataEntry_SubGroup.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Registername.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTabName (java.lang.String TabName);

	/**
	 * Get Registername.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTabName();

    /** Column definition for TabName */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, Object> COLUMN_TabName = new org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, Object>(I_DataEntry_SubGroup.class, "TabName", null);
    /** Column name TabName */
    public static final String COLUMNNAME_TabName = "TabName";

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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, Object>(I_DataEntry_SubGroup.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DataEntry_SubGroup, org.compiere.model.I_AD_User>(I_DataEntry_SubGroup.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
