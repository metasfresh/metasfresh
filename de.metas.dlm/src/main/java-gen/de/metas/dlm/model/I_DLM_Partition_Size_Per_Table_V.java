package de.metas.dlm.model;


/** Generated Interface for DLM_Partition_Size_Per_Table_V
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DLM_Partition_Size_Per_Table_V 
{

    /** TableName=DLM_Partition_Size_Per_Table_V */
    public static final String Table_Name = "DLM_Partition_Size_Per_Table_V";

    /** AD_Table_ID=540800 */
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
    public static final org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, org.compiere.model.I_AD_Client>(I_DLM_Partition_Size_Per_Table_V.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, org.compiere.model.I_AD_Org>(I_DLM_Partition_Size_Per_Table_V.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, org.compiere.model.I_AD_Table>(I_DLM_Partition_Size_Per_Table_V.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
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
    public static final org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, Object>(I_DLM_Partition_Size_Per_Table_V.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, org.compiere.model.I_AD_User>(I_DLM_Partition_Size_Per_Table_V.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Partition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDLM_Partition_ID (int DLM_Partition_ID);

	/**
	 * Get Partition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDLM_Partition_ID();

	public de.metas.dlm.model.I_DLM_Partition getDLM_Partition();

	public void setDLM_Partition(de.metas.dlm.model.I_DLM_Partition DLM_Partition);

    /** Column definition for DLM_Partition_ID */
    public static final org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, de.metas.dlm.model.I_DLM_Partition> COLUMN_DLM_Partition_ID = new org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, de.metas.dlm.model.I_DLM_Partition>(I_DLM_Partition_Size_Per_Table_V.class, "DLM_Partition_ID", de.metas.dlm.model.I_DLM_Partition.class);
    /** Column name DLM_Partition_ID */
    public static final String COLUMNNAME_DLM_Partition_ID = "DLM_Partition_ID";

	/**
	 * Set DLM_Partition_Size_Per_Table_V.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDLM_Partition_Size_Per_Table_V_ID (int DLM_Partition_Size_Per_Table_V_ID);

	/**
	 * Get DLM_Partition_Size_Per_Table_V.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDLM_Partition_Size_Per_Table_V_ID();

    /** Column definition for DLM_Partition_Size_Per_Table_V_ID */
    public static final org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, Object> COLUMN_DLM_Partition_Size_Per_Table_V_ID = new org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, Object>(I_DLM_Partition_Size_Per_Table_V.class, "DLM_Partition_Size_Per_Table_V_ID", null);
    /** Column name DLM_Partition_Size_Per_Table_V_ID */
    public static final String COLUMNNAME_DLM_Partition_Size_Per_Table_V_ID = "DLM_Partition_Size_Per_Table_V_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, Object>(I_DLM_Partition_Size_Per_Table_V.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Anz. zugeordneter Datensätze.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPartitionSize (int PartitionSize);

	/**
	 * Get Anz. zugeordneter Datensätze.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPartitionSize();

    /** Column definition for PartitionSize */
    public static final org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, Object> COLUMN_PartitionSize = new org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, Object>(I_DLM_Partition_Size_Per_Table_V.class, "PartitionSize", null);
    /** Column name PartitionSize */
    public static final String COLUMNNAME_PartitionSize = "PartitionSize";

	/**
	 * Set Name der DB-Tabelle.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTableName (java.lang.String TableName);

	/**
	 * Get Name der DB-Tabelle.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTableName();

    /** Column definition for TableName */
    public static final org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, Object> COLUMN_TableName = new org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, Object>(I_DLM_Partition_Size_Per_Table_V.class, "TableName", null);
    /** Column name TableName */
    public static final String COLUMNNAME_TableName = "TableName";

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
    public static final org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, Object>(I_DLM_Partition_Size_Per_Table_V.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DLM_Partition_Size_Per_Table_V, org.compiere.model.I_AD_User>(I_DLM_Partition_Size_Per_Table_V.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
