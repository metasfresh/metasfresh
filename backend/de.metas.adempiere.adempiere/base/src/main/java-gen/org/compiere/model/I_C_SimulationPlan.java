package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_SimulationPlan
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_SimulationPlan 
{

	String Table_Name = "C_SimulationPlan";

//	/** AD_Table_ID=542173 */
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
	 * Set Verantwortlicher Benutzer.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_User_Responsible_ID (int AD_User_Responsible_ID);

	/**
	 * Get Verantwortlicher Benutzer.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_User_Responsible_ID();

	String COLUMNNAME_AD_User_Responsible_ID = "AD_User_Responsible_ID";

	/**
	 * Set Simulation Plan.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_SimulationPlan_ID (int C_SimulationPlan_ID);

	/**
	 * Get Simulation Plan.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_SimulationPlan_ID();

	ModelColumn<I_C_SimulationPlan, Object> COLUMN_C_SimulationPlan_ID = new ModelColumn<>(I_C_SimulationPlan.class, "C_SimulationPlan_ID", null);
	String COLUMNNAME_C_SimulationPlan_ID = "C_SimulationPlan_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_SimulationPlan, Object> COLUMN_Created = new ModelColumn<>(I_C_SimulationPlan.class, "Created", null);
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
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocStatus();

	ModelColumn<I_C_SimulationPlan, Object> COLUMN_DocStatus = new ModelColumn<>(I_C_SimulationPlan.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

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

	ModelColumn<I_C_SimulationPlan, Object> COLUMN_IsActive = new ModelColumn<>(I_C_SimulationPlan.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Main simulation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsMainSimulation (boolean IsMainSimulation);

	/**
	 * Get Main simulation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isMainSimulation();

	ModelColumn<I_C_SimulationPlan, Object> COLUMN_IsMainSimulation = new ModelColumn<>(I_C_SimulationPlan.class, "IsMainSimulation", null);
	String COLUMNNAME_IsMainSimulation = "IsMainSimulation";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_C_SimulationPlan, Object> COLUMN_Name = new ModelColumn<>(I_C_SimulationPlan.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_C_SimulationPlan, Object> COLUMN_Processed = new ModelColumn<>(I_C_SimulationPlan.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_SimulationPlan, Object> COLUMN_Updated = new ModelColumn<>(I_C_SimulationPlan.class, "Updated", null);
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
