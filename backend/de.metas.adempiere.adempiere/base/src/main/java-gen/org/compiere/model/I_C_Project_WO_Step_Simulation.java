package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Project_WO_Step_Simulation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Project_WO_Step_Simulation 
{

	String Table_Name = "C_Project_WO_Step_Simulation";

//	/** AD_Table_ID=542175 */
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
	 * Set Project Step.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_WO_Step_ID (int C_Project_WO_Step_ID);

	/**
	 * Get Project Step.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_WO_Step_ID();

	org.compiere.model.I_C_Project_WO_Step getC_Project_WO_Step();

	void setC_Project_WO_Step(org.compiere.model.I_C_Project_WO_Step C_Project_WO_Step);

	ModelColumn<I_C_Project_WO_Step_Simulation, org.compiere.model.I_C_Project_WO_Step> COLUMN_C_Project_WO_Step_ID = new ModelColumn<>(I_C_Project_WO_Step_Simulation.class, "C_Project_WO_Step_ID", org.compiere.model.I_C_Project_WO_Step.class);
	String COLUMNNAME_C_Project_WO_Step_ID = "C_Project_WO_Step_ID";

	/**
	 * Set WO Project Step Simulation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_WO_Step_Simulation_ID (int C_Project_WO_Step_Simulation_ID);

	/**
	 * Get WO Project Step Simulation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_WO_Step_Simulation_ID();

	ModelColumn<I_C_Project_WO_Step_Simulation, Object> COLUMN_C_Project_WO_Step_Simulation_ID = new ModelColumn<>(I_C_Project_WO_Step_Simulation.class, "C_Project_WO_Step_Simulation_ID", null);
	String COLUMNNAME_C_Project_WO_Step_Simulation_ID = "C_Project_WO_Step_Simulation_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Project_WO_Step_Simulation, Object> COLUMN_Created = new ModelColumn<>(I_C_Project_WO_Step_Simulation.class, "Created", null);
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

	ModelColumn<I_C_Project_WO_Step_Simulation, org.compiere.model.I_C_SimulationPlan> COLUMN_C_SimulationPlan_ID = new ModelColumn<>(I_C_Project_WO_Step_Simulation.class, "C_SimulationPlan_ID", org.compiere.model.I_C_SimulationPlan.class);
	String COLUMNNAME_C_SimulationPlan_ID = "C_SimulationPlan_ID";

	/**
	 * Set Date End.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateEnd (java.sql.Timestamp DateEnd);

	/**
	 * Get Date End.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateEnd();

	ModelColumn<I_C_Project_WO_Step_Simulation, Object> COLUMN_DateEnd = new ModelColumn<>(I_C_Project_WO_Step_Simulation.class, "DateEnd", null);
	String COLUMNNAME_DateEnd = "DateEnd";

	/**
	 * Set Start Date.
	 * Indicate the real date to start
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateStart (java.sql.Timestamp DateStart);

	/**
	 * Get Start Date.
	 * Indicate the real date to start
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateStart();

	ModelColumn<I_C_Project_WO_Step_Simulation, Object> COLUMN_DateStart = new ModelColumn<>(I_C_Project_WO_Step_Simulation.class, "DateStart", null);
	String COLUMNNAME_DateStart = "DateStart";

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

	ModelColumn<I_C_Project_WO_Step_Simulation, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Project_WO_Step_Simulation.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Project_WO_Step_Simulation, Object> COLUMN_Updated = new ModelColumn<>(I_C_Project_WO_Step_Simulation.class, "Updated", null);
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
