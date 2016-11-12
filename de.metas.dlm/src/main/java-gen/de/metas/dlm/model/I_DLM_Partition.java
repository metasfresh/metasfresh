package de.metas.dlm.model;

/**
 * Generated Interface for DLM_Partition
 * 
 * @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public interface I_DLM_Partition
{

	/** TableName=DLM_Partition */
	public static final String Table_Name = "DLM_Partition";

	/** AD_Table_ID=540788 */
	// public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

	// org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

	/**
	 * AccessLevel = 7 - System - Client - Org
	 */
	// java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

	/** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

	/** Column definition for AD_Client_ID */
	public static final org.adempiere.model.ModelColumn<I_DLM_Partition, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<>(I_DLM_Partition.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
	/** Column name AD_Client_ID */
	public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setAD_Org_ID(int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>
	 * Type: TableDir
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

	/** Column definition for AD_Org_ID */
	public static final org.adempiere.model.ModelColumn<I_DLM_Partition, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<>(I_DLM_Partition.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
	/** Column name AD_Org_ID */
	public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

	/** Column definition for Created */
	public static final org.adempiere.model.ModelColumn<I_DLM_Partition, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<>(I_DLM_Partition.class, "Created", null);
	/** Column name Created */
	public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>
	 * Type: Table
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public int getCreatedBy();

	/** Column definition for CreatedBy */
	public static final org.adempiere.model.ModelColumn<I_DLM_Partition, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<>(I_DLM_Partition.class, "CreatedBy", org.compiere.model.I_AD_User.class);
	/** Column name CreatedBy */
	public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set aktueller DLM-Level.
	 *
	 * <br>
	 * Type: Integer
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setCurrent_DLM_Level(int Current_DLM_Level);

	/**
	 * Get aktueller DLM-Level.
	 *
	 * <br>
	 * Type: Integer
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getCurrent_DLM_Level();

	/** Column definition for Current_DLM_Level */
	public static final org.adempiere.model.ModelColumn<I_DLM_Partition, Object> COLUMN_Current_DLM_Level = new org.adempiere.model.ModelColumn<>(I_DLM_Partition.class, "Current_DLM_Level", null);
	/** Column name Current_DLM_Level */
	public static final String COLUMNNAME_Current_DLM_Level = "Current_DLM_Level";

	/**
	 * Set Nächte Prüfung des DLM-Levels.
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setDateNextInspection(java.sql.Timestamp DateNextInspection);

	/**
	 * Get Nächte Prüfung des DLM-Levels.
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getDateNextInspection();

	/** Column definition for DateNextInspection */
	public static final org.adempiere.model.ModelColumn<I_DLM_Partition, Object> COLUMN_DateNextInspection = new org.adempiere.model.ModelColumn<>(I_DLM_Partition.class, "DateNextInspection", null);
	/** Column name DateNextInspection */
	public static final String COLUMNNAME_DateNextInspection = "DateNextInspection";

	/**
	 * Set DLM Partitionierungskonfiguration.
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setDLM_Partition_Config_ID(int DLM_Partition_Config_ID);

	/**
	 * Get DLM Partitionierungskonfiguration.
	 *
	 * <br>
	 * Type: Search
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public int getDLM_Partition_Config_ID();

	public de.metas.dlm.model.I_DLM_Partition_Config getDLM_Partition_Config();

	public void setDLM_Partition_Config(de.metas.dlm.model.I_DLM_Partition_Config DLM_Partition_Config);

	/** Column definition for DLM_Partition_Config_ID */
	public static final org.adempiere.model.ModelColumn<I_DLM_Partition, de.metas.dlm.model.I_DLM_Partition_Config> COLUMN_DLM_Partition_Config_ID = new org.adempiere.model.ModelColumn<>(I_DLM_Partition.class, "DLM_Partition_Config_ID", de.metas.dlm.model.I_DLM_Partition_Config.class);
	/** Column name DLM_Partition_Config_ID */
	public static final String COLUMNNAME_DLM_Partition_Config_ID = "DLM_Partition_Config_ID";

	/**
	 * Set Partition.
	 *
	 * <br>
	 * Type: ID
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setDLM_Partition_ID(int DLM_Partition_ID);

	/**
	 * Get Partition.
	 *
	 * <br>
	 * Type: ID
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public int getDLM_Partition_ID();

	/** Column definition for DLM_Partition_ID */
	public static final org.adempiere.model.ModelColumn<I_DLM_Partition, Object> COLUMN_DLM_Partition_ID = new org.adempiere.model.ModelColumn<>(I_DLM_Partition.class, "DLM_Partition_ID", null);
	/** Column name DLM_Partition_ID */
	public static final String COLUMNNAME_DLM_Partition_ID = "DLM_Partition_ID";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setIsActive(boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public boolean isActive();

	/** Column definition for IsActive */
	public static final org.adempiere.model.ModelColumn<I_DLM_Partition, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<>(I_DLM_Partition.class, "IsActive", null);
	/** Column name IsActive */
	public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Partition is vollständig.
	 * Sagt aus, ob das System dieser Partition noch weitere Datensätze hinzufügen muss bevor sie als Ganzes verschoben werden kann.
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public void setIsPartitionComplete(boolean IsPartitionComplete);

	/**
	 * Get Partition is vollständig.
	 * Sagt aus, ob das System dieser Partition noch weitere Datensätze hinzufügen muss bevor sie als Ganzes verschoben werden kann.
	 *
	 * <br>
	 * Type: YesNo
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public boolean isPartitionComplete();

	/** Column definition for IsPartitionComplete */
	public static final org.adempiere.model.ModelColumn<I_DLM_Partition, Object> COLUMN_IsPartitionComplete = new org.adempiere.model.ModelColumn<>(I_DLM_Partition.class, "IsPartitionComplete", null);
	/** Column name IsPartitionComplete */
	public static final String COLUMNNAME_IsPartitionComplete = "IsPartitionComplete";

	/**
	 * Set Anz. zugeordneter Datensätze.
	 *
	 * <br>
	 * Type: Integer
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setPartitionSize(int PartitionSize);

	/**
	 * Get Anz. zugeordneter Datensätze.
	 *
	 * <br>
	 * Type: Integer
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getPartitionSize();

	/** Column definition for PartitionSize */
	public static final org.adempiere.model.ModelColumn<I_DLM_Partition, Object> COLUMN_PartitionSize = new org.adempiere.model.ModelColumn<>(I_DLM_Partition.class, "PartitionSize", null);
	/** Column name PartitionSize */
	public static final String COLUMNNAME_PartitionSize = "PartitionSize";

	/**
	 * Set Ziel-DLM-Level.
	 *
	 * <br>
	 * Type: Integer
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public void setTarget_DLM_Level(int Target_DLM_Level);

	/**
	 * Get Ziel-DLM-Level.
	 *
	 * <br>
	 * Type: Integer
	 * <br>
	 * Mandatory: false
	 * <br>
	 * Virtual Column: false
	 */
	public int getTarget_DLM_Level();

	/** Column definition for Target_DLM_Level */
	public static final org.adempiere.model.ModelColumn<I_DLM_Partition, Object> COLUMN_Target_DLM_Level = new org.adempiere.model.ModelColumn<>(I_DLM_Partition.class, "Target_DLM_Level", null);
	/** Column name Target_DLM_Level */
	public static final String COLUMNNAME_Target_DLM_Level = "Target_DLM_Level";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>
	 * Type: DateTime
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

	/** Column definition for Updated */
	public static final org.adempiere.model.ModelColumn<I_DLM_Partition, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<>(I_DLM_Partition.class, "Updated", null);
	/** Column name Updated */
	public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>
	 * Type: Table
	 * <br>
	 * Mandatory: true
	 * <br>
	 * Virtual Column: false
	 */
	public int getUpdatedBy();

	/** Column definition for UpdatedBy */
	public static final org.adempiere.model.ModelColumn<I_DLM_Partition, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<>(I_DLM_Partition.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
	/** Column name UpdatedBy */
	public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
