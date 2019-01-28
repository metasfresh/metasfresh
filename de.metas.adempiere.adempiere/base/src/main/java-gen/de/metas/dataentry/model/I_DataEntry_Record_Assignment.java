package de.metas.dataentry.model;


/** Generated Interface for DataEntry_Record_Assignment
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DataEntry_Record_Assignment 
{

    /** TableName=DataEntry_Record_Assignment */
    public static final String Table_Name = "DataEntry_Record_Assignment";

    /** AD_Table_ID=541170 */
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record_Assignment, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DataEntry_Record_Assignment, org.compiere.model.I_AD_Client>(I_DataEntry_Record_Assignment.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record_Assignment, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DataEntry_Record_Assignment, org.compiere.model.I_AD_Org>(I_DataEntry_Record_Assignment.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record_Assignment, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DataEntry_Record_Assignment, Object>(I_DataEntry_Record_Assignment.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record_Assignment, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DataEntry_Record_Assignment, org.compiere.model.I_AD_User>(I_DataEntry_Record_Assignment.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set DataEntry_Record_Assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_Record_Assignment_ID (int DataEntry_Record_Assignment_ID);

	/**
	 * Get DataEntry_Record_Assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDataEntry_Record_Assignment_ID();

    /** Column definition for DataEntry_Record_Assignment_ID */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record_Assignment, Object> COLUMN_DataEntry_Record_Assignment_ID = new org.adempiere.model.ModelColumn<I_DataEntry_Record_Assignment, Object>(I_DataEntry_Record_Assignment.class, "DataEntry_Record_Assignment_ID", null);
    /** Column name DataEntry_Record_Assignment_ID */
    public static final String COLUMNNAME_DataEntry_Record_Assignment_ID = "DataEntry_Record_Assignment_ID";

	/**
	 * Set DataEntry_Record.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_Record_ID (int DataEntry_Record_ID);

	/**
	 * Get DataEntry_Record.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDataEntry_Record_ID();

	public de.metas.dataentry.model.I_DataEntry_Record getDataEntry_Record();

	public void setDataEntry_Record(de.metas.dataentry.model.I_DataEntry_Record DataEntry_Record);

    /** Column definition for DataEntry_Record_ID */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record_Assignment, de.metas.dataentry.model.I_DataEntry_Record> COLUMN_DataEntry_Record_ID = new org.adempiere.model.ModelColumn<I_DataEntry_Record_Assignment, de.metas.dataentry.model.I_DataEntry_Record>(I_DataEntry_Record_Assignment.class, "DataEntry_Record_ID", de.metas.dataentry.model.I_DataEntry_Record.class);
    /** Column name DataEntry_Record_ID */
    public static final String COLUMNNAME_DataEntry_Record_ID = "DataEntry_Record_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record_Assignment, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DataEntry_Record_Assignment, Object>(I_DataEntry_Record_Assignment.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record_Assignment, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DataEntry_Record_Assignment, Object>(I_DataEntry_Record_Assignment.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record_Assignment, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DataEntry_Record_Assignment, org.compiere.model.I_AD_User>(I_DataEntry_Record_Assignment.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
