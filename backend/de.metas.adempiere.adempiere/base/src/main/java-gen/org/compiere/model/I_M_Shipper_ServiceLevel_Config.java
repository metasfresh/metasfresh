package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Shipper_ServiceLevel_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Shipper_ServiceLevel_Config 
{

	String Table_Name = "M_Shipper_ServiceLevel_Config";

//	/** AD_Table_ID=542606 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Shipper_ServiceLevel_Config, Object> COLUMN_Created = new ModelColumn<>(I_M_Shipper_ServiceLevel_Config.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Externes System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternal_System_ID (int External_System_ID);

	/**
	 * Get Externes System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getExternal_System_ID();

	ModelColumn<I_M_Shipper_ServiceLevel_Config, Object> COLUMN_External_System_ID = new ModelColumn<>(I_M_Shipper_ServiceLevel_Config.class, "External_System_ID", null);
	String COLUMNNAME_External_System_ID = "External_System_ID";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_M_Shipper_ServiceLevel_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Shipper_ServiceLevel_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Lieferweg.
	 * Methode oder Art der Warenlieferung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Shipper_ID();

	ModelColumn<I_M_Shipper_ServiceLevel_Config, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_M_Shipper_ServiceLevel_Config.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Service Level Konfiguration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Shipper_ServiceLevel_Config_ID (int M_Shipper_ServiceLevel_Config_ID);

	/**
	 * Get Service Level Konfiguration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Shipper_ServiceLevel_Config_ID();

	ModelColumn<I_M_Shipper_ServiceLevel_Config, Object> COLUMN_M_Shipper_ServiceLevel_Config_ID = new ModelColumn<>(I_M_Shipper_ServiceLevel_Config.class, "M_Shipper_ServiceLevel_Config_ID", null);
	String COLUMNNAME_M_Shipper_ServiceLevel_Config_ID = "M_Shipper_ServiceLevel_Config_ID";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_M_Shipper_ServiceLevel_Config, Object> COLUMN_SeqNo = new ModelColumn<>(I_M_Shipper_ServiceLevel_Config.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Service Level.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setServiceLevel (java.lang.String ServiceLevel);

	/**
	 * Get Service Level.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getServiceLevel();

	ModelColumn<I_M_Shipper_ServiceLevel_Config, Object> COLUMN_ServiceLevel = new ModelColumn<>(I_M_Shipper_ServiceLevel_Config.class, "ServiceLevel", null);
	String COLUMNNAME_ServiceLevel = "ServiceLevel";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Shipper_ServiceLevel_Config, Object> COLUMN_Updated = new ModelColumn<>(I_M_Shipper_ServiceLevel_Config.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
