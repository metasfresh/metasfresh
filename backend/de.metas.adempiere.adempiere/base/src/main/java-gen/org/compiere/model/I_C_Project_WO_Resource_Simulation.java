package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Project_WO_Resource_Simulation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Project_WO_Resource_Simulation 
{

	String Table_Name = "C_Project_WO_Resource_Simulation";

//	/** AD_Table_ID=542176 */
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
	 * Set Assign From.
	 * Assign resource from
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAssignDateFrom (java.sql.Timestamp AssignDateFrom);

	/**
	 * Get Assign From.
	 * Assign resource from
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getAssignDateFrom();

	ModelColumn<I_C_Project_WO_Resource_Simulation, Object> COLUMN_AssignDateFrom = new ModelColumn<>(I_C_Project_WO_Resource_Simulation.class, "AssignDateFrom", null);
	String COLUMNNAME_AssignDateFrom = "AssignDateFrom";

	/**
	 * Set Zuordnung von (previous).
	 * Ressource zuordnen ab
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAssignDateFrom_Prev (@Nullable java.sql.Timestamp AssignDateFrom_Prev);

	/**
	 * Get Zuordnung von (previous).
	 * Ressource zuordnen ab
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getAssignDateFrom_Prev();

	ModelColumn<I_C_Project_WO_Resource_Simulation, Object> COLUMN_AssignDateFrom_Prev = new ModelColumn<>(I_C_Project_WO_Resource_Simulation.class, "AssignDateFrom_Prev", null);
	String COLUMNNAME_AssignDateFrom_Prev = "AssignDateFrom_Prev";

	/**
	 * Set Assign To.
	 * Assign resource until
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAssignDateTo (java.sql.Timestamp AssignDateTo);

	/**
	 * Get Assign To.
	 * Assign resource until
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getAssignDateTo();

	ModelColumn<I_C_Project_WO_Resource_Simulation, Object> COLUMN_AssignDateTo = new ModelColumn<>(I_C_Project_WO_Resource_Simulation.class, "AssignDateTo", null);
	String COLUMNNAME_AssignDateTo = "AssignDateTo";

	/**
	 * Set Zuordnung bis (previous).
	 * Ressource zuordnen bis
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAssignDateTo_Prev (@Nullable java.sql.Timestamp AssignDateTo_Prev);

	/**
	 * Get Zuordnung bis (previous).
	 * Ressource zuordnen bis
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getAssignDateTo_Prev();

	ModelColumn<I_C_Project_WO_Resource_Simulation, Object> COLUMN_AssignDateTo_Prev = new ModelColumn<>(I_C_Project_WO_Resource_Simulation.class, "AssignDateTo_Prev", null);
	String COLUMNNAME_AssignDateTo_Prev = "AssignDateTo_Prev";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Project Resource.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_WO_Resource_ID (int C_Project_WO_Resource_ID);

	/**
	 * Get Project Resource.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_WO_Resource_ID();

	org.compiere.model.I_C_Project_WO_Resource getC_Project_WO_Resource();

	void setC_Project_WO_Resource(org.compiere.model.I_C_Project_WO_Resource C_Project_WO_Resource);

	ModelColumn<I_C_Project_WO_Resource_Simulation, org.compiere.model.I_C_Project_WO_Resource> COLUMN_C_Project_WO_Resource_ID = new ModelColumn<>(I_C_Project_WO_Resource_Simulation.class, "C_Project_WO_Resource_ID", org.compiere.model.I_C_Project_WO_Resource.class);
	String COLUMNNAME_C_Project_WO_Resource_ID = "C_Project_WO_Resource_ID";

	/**
	 * Set WO Project Resource Simulation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_WO_Resource_Simulation_ID (int C_Project_WO_Resource_Simulation_ID);

	/**
	 * Get WO Project Resource Simulation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_WO_Resource_Simulation_ID();

	ModelColumn<I_C_Project_WO_Resource_Simulation, Object> COLUMN_C_Project_WO_Resource_Simulation_ID = new ModelColumn<>(I_C_Project_WO_Resource_Simulation.class, "C_Project_WO_Resource_Simulation_ID", null);
	String COLUMNNAME_C_Project_WO_Resource_Simulation_ID = "C_Project_WO_Resource_Simulation_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Project_WO_Resource_Simulation, Object> COLUMN_Created = new ModelColumn<>(I_C_Project_WO_Resource_Simulation.class, "Created", null);
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
	 * Set Simulation Plan.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_SimulationPlan_ID (int C_SimulationPlan_ID);

	/**
	 * Get Simulation Plan.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_SimulationPlan_ID();

	org.compiere.model.I_C_SimulationPlan getC_SimulationPlan();

	void setC_SimulationPlan(org.compiere.model.I_C_SimulationPlan C_SimulationPlan);

	ModelColumn<I_C_Project_WO_Resource_Simulation, org.compiere.model.I_C_SimulationPlan> COLUMN_C_SimulationPlan_ID = new ModelColumn<>(I_C_Project_WO_Resource_Simulation.class, "C_SimulationPlan_ID", org.compiere.model.I_C_SimulationPlan.class);
	String COLUMNNAME_C_SimulationPlan_ID = "C_SimulationPlan_ID";

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

	ModelColumn<I_C_Project_WO_Resource_Simulation, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Project_WO_Resource_Simulation.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set All day.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllDay (boolean IsAllDay);

	/**
	 * Get All day.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllDay();

	ModelColumn<I_C_Project_WO_Resource_Simulation, Object> COLUMN_IsAllDay = new ModelColumn<>(I_C_Project_WO_Resource_Simulation.class, "IsAllDay", null);
	String COLUMNNAME_IsAllDay = "IsAllDay";

	/**
	 * Set All day (previous).
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllDay_Prev (boolean IsAllDay_Prev);

	/**
	 * Get All day (previous).
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllDay_Prev();

	ModelColumn<I_C_Project_WO_Resource_Simulation, Object> COLUMN_IsAllDay_Prev = new ModelColumn<>(I_C_Project_WO_Resource_Simulation.class, "IsAllDay_Prev", null);
	String COLUMNNAME_IsAllDay_Prev = "IsAllDay_Prev";

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

	ModelColumn<I_C_Project_WO_Resource_Simulation, Object> COLUMN_Processed = new ModelColumn<>(I_C_Project_WO_Resource_Simulation.class, "Processed", null);
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

	ModelColumn<I_C_Project_WO_Resource_Simulation, Object> COLUMN_Updated = new ModelColumn<>(I_C_Project_WO_Resource_Simulation.class, "Updated", null);
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
