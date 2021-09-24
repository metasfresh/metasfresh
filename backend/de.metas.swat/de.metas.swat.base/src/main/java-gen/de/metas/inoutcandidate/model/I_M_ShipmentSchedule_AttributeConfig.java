package de.metas.inoutcandidate.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for M_ShipmentSchedule_AttributeConfig
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_ShipmentSchedule_AttributeConfig 
{

	String Table_Name = "M_ShipmentSchedule_AttributeConfig";

//	/** AD_Table_ID=540951 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_ShipmentSchedule_AttributeConfig, Object> COLUMN_Created = new ModelColumn<>(I_M_ShipmentSchedule_AttributeConfig.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_M_ShipmentSchedule_AttributeConfig, Object> COLUMN_IsActive = new ModelColumn<>(I_M_ShipmentSchedule_AttributeConfig.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Attribute_ID (int M_Attribute_ID);

	/**
	 * Get Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Attribute_ID();

	String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

	/**
	 * Set M_IolCandHandler.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_IolCandHandler_ID (int M_IolCandHandler_ID);

	/**
	 * Get M_IolCandHandler.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_IolCandHandler_ID();

	de.metas.inoutcandidate.model.I_M_IolCandHandler getM_IolCandHandler();

	void setM_IolCandHandler(de.metas.inoutcandidate.model.I_M_IolCandHandler M_IolCandHandler);

	ModelColumn<I_M_ShipmentSchedule_AttributeConfig, de.metas.inoutcandidate.model.I_M_IolCandHandler> COLUMN_M_IolCandHandler_ID = new ModelColumn<>(I_M_ShipmentSchedule_AttributeConfig.class, "M_IolCandHandler_ID", de.metas.inoutcandidate.model.I_M_IolCandHandler.class);
	String COLUMNNAME_M_IolCandHandler_ID = "M_IolCandHandler_ID";

	/**
	 * Set M_ShipmentSchedule_HUAttributeConfig.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_ShipmentSchedule_AttributeConfig_ID (int M_ShipmentSchedule_AttributeConfig_ID);

	/**
	 * Get M_ShipmentSchedule_HUAttributeConfig.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_ShipmentSchedule_AttributeConfig_ID();

	ModelColumn<I_M_ShipmentSchedule_AttributeConfig, Object> COLUMN_M_ShipmentSchedule_AttributeConfig_ID = new ModelColumn<>(I_M_ShipmentSchedule_AttributeConfig.class, "M_ShipmentSchedule_AttributeConfig_ID", null);
	String COLUMNNAME_M_ShipmentSchedule_AttributeConfig_ID = "M_ShipmentSchedule_AttributeConfig_ID";

	/**
	 * Set Nur falls in ref. Datensatz.
	 * Das Attribut wird nur berücksichtigt, wenn es in der Merkmalssatzinstanz des referenzierenden Datensatzes vorkommt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnlyIfInReferencedASI (boolean OnlyIfInReferencedASI);

	/**
	 * Get Nur falls in ref. Datensatz.
	 * Das Attribut wird nur berücksichtigt, wenn es in der Merkmalssatzinstanz des referenzierenden Datensatzes vorkommt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnlyIfInReferencedASI();

	ModelColumn<I_M_ShipmentSchedule_AttributeConfig, Object> COLUMN_OnlyIfInReferencedASI = new ModelColumn<>(I_M_ShipmentSchedule_AttributeConfig.class, "OnlyIfInReferencedASI", null);
	String COLUMNNAME_OnlyIfInReferencedASI = "OnlyIfInReferencedASI";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_ShipmentSchedule_AttributeConfig, Object> COLUMN_Updated = new ModelColumn<>(I_M_ShipmentSchedule_AttributeConfig.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
