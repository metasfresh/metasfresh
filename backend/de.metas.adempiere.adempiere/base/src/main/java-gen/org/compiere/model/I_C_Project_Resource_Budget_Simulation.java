package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Project_Resource_Budget_Simulation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Project_Resource_Budget_Simulation 
{

	String Table_Name = "C_Project_Resource_Budget_Simulation";

//	/** AD_Table_ID=542177 */
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
	 * Set Project Resource Budget.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_Resource_Budget_ID (int C_Project_Resource_Budget_ID);

	/**
	 * Get Project Resource Budget.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_Resource_Budget_ID();

	org.compiere.model.I_C_Project_Resource_Budget getC_Project_Resource_Budget();

	void setC_Project_Resource_Budget(org.compiere.model.I_C_Project_Resource_Budget C_Project_Resource_Budget);

	ModelColumn<I_C_Project_Resource_Budget_Simulation, org.compiere.model.I_C_Project_Resource_Budget> COLUMN_C_Project_Resource_Budget_ID = new ModelColumn<>(I_C_Project_Resource_Budget_Simulation.class, "C_Project_Resource_Budget_ID", org.compiere.model.I_C_Project_Resource_Budget.class);
	String COLUMNNAME_C_Project_Resource_Budget_ID = "C_Project_Resource_Budget_ID";

	/**
	 * Set Project Resource Budget Simulation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_Resource_Budget_Simulation_ID (int C_Project_Resource_Budget_Simulation_ID);

	/**
	 * Get Project Resource Budget Simulation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_Resource_Budget_Simulation_ID();

	ModelColumn<I_C_Project_Resource_Budget_Simulation, Object> COLUMN_C_Project_Resource_Budget_Simulation_ID = new ModelColumn<>(I_C_Project_Resource_Budget_Simulation.class, "C_Project_Resource_Budget_Simulation_ID", null);
	String COLUMNNAME_C_Project_Resource_Budget_Simulation_ID = "C_Project_Resource_Budget_Simulation_ID";

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

	ModelColumn<I_C_Project_Resource_Budget_Simulation, org.compiere.model.I_C_SimulationPlan> COLUMN_C_SimulationPlan_ID = new ModelColumn<>(I_C_Project_Resource_Budget_Simulation.class, "C_SimulationPlan_ID", org.compiere.model.I_C_SimulationPlan.class);
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

	ModelColumn<I_C_Project_Resource_Budget_Simulation, Object> COLUMN_Created = new ModelColumn<>(I_C_Project_Resource_Budget_Simulation.class, "Created", null);
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
	 * Set Finish Plan.
	 * Planned Finish Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateFinishPlan (java.sql.Timestamp DateFinishPlan);

	/**
	 * Get Finish Plan.
	 * Planned Finish Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateFinishPlan();

	ModelColumn<I_C_Project_Resource_Budget_Simulation, Object> COLUMN_DateFinishPlan = new ModelColumn<>(I_C_Project_Resource_Budget_Simulation.class, "DateFinishPlan", null);
	String COLUMNNAME_DateFinishPlan = "DateFinishPlan";

	/**
	 * Set Finish Plan (Previous).
	 * Planned Finish Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateFinishPlan_Prev (@Nullable java.sql.Timestamp DateFinishPlan_Prev);

	/**
	 * Get Finish Plan (Previous).
	 * Planned Finish Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateFinishPlan_Prev();

	ModelColumn<I_C_Project_Resource_Budget_Simulation, Object> COLUMN_DateFinishPlan_Prev = new ModelColumn<>(I_C_Project_Resource_Budget_Simulation.class, "DateFinishPlan_Prev", null);
	String COLUMNNAME_DateFinishPlan_Prev = "DateFinishPlan_Prev";

	/**
	 * Set Start Plan.
	 * Planned Start Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateStartPlan (java.sql.Timestamp DateStartPlan);

	/**
	 * Get Start Plan.
	 * Planned Start Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateStartPlan();

	ModelColumn<I_C_Project_Resource_Budget_Simulation, Object> COLUMN_DateStartPlan = new ModelColumn<>(I_C_Project_Resource_Budget_Simulation.class, "DateStartPlan", null);
	String COLUMNNAME_DateStartPlan = "DateStartPlan";

	/**
	 * Set Start Plan (Previous).
	 * Planned Start Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateStartPlan_Prev (@Nullable java.sql.Timestamp DateStartPlan_Prev);

	/**
	 * Get Start Plan (Previous).
	 * Planned Start Date
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateStartPlan_Prev();

	ModelColumn<I_C_Project_Resource_Budget_Simulation, Object> COLUMN_DateStartPlan_Prev = new ModelColumn<>(I_C_Project_Resource_Budget_Simulation.class, "DateStartPlan_Prev", null);
	String COLUMNNAME_DateStartPlan_Prev = "DateStartPlan_Prev";

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

	ModelColumn<I_C_Project_Resource_Budget_Simulation, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Project_Resource_Budget_Simulation.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_Project_Resource_Budget_Simulation, Object> COLUMN_Processed = new ModelColumn<>(I_C_Project_Resource_Budget_Simulation.class, "Processed", null);
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

	ModelColumn<I_C_Project_Resource_Budget_Simulation, Object> COLUMN_Updated = new ModelColumn<>(I_C_Project_Resource_Budget_Simulation.class, "Updated", null);
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
