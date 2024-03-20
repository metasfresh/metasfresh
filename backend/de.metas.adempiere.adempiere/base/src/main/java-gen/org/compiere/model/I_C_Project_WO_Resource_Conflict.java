package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Project_WO_Resource_Conflict
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Project_WO_Resource_Conflict 
{

	String Table_Name = "C_Project_WO_Resource_Conflict";

//	/** AD_Table_ID=542186 */
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
	 * Set Projekt.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project2_ID (int C_Project2_ID);

	/**
	 * Get Projekt.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project2_ID();

	String COLUMNNAME_C_Project2_ID = "C_Project2_ID";

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
	void setC_Project_WO_Resource2_ID (int C_Project_WO_Resource2_ID);

	/**
	 * Get Project Resource.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_WO_Resource2_ID();

	org.compiere.model.I_C_Project_WO_Resource getC_Project_WO_Resource2();

	void setC_Project_WO_Resource2(org.compiere.model.I_C_Project_WO_Resource C_Project_WO_Resource2);

	ModelColumn<I_C_Project_WO_Resource_Conflict, org.compiere.model.I_C_Project_WO_Resource> COLUMN_C_Project_WO_Resource2_ID = new ModelColumn<>(I_C_Project_WO_Resource_Conflict.class, "C_Project_WO_Resource2_ID", org.compiere.model.I_C_Project_WO_Resource.class);
	String COLUMNNAME_C_Project_WO_Resource2_ID = "C_Project_WO_Resource2_ID";

	/**
	 * Set Work Order Resource Conflict.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_WO_Resource_Conflict_ID (int C_Project_WO_Resource_Conflict_ID);

	/**
	 * Get Work Order Resource Conflict.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_WO_Resource_Conflict_ID();

	ModelColumn<I_C_Project_WO_Resource_Conflict, Object> COLUMN_C_Project_WO_Resource_Conflict_ID = new ModelColumn<>(I_C_Project_WO_Resource_Conflict.class, "C_Project_WO_Resource_Conflict_ID", null);
	String COLUMNNAME_C_Project_WO_Resource_Conflict_ID = "C_Project_WO_Resource_Conflict_ID";

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

	ModelColumn<I_C_Project_WO_Resource_Conflict, org.compiere.model.I_C_Project_WO_Resource> COLUMN_C_Project_WO_Resource_ID = new ModelColumn<>(I_C_Project_WO_Resource_Conflict.class, "C_Project_WO_Resource_ID", org.compiere.model.I_C_Project_WO_Resource.class);
	String COLUMNNAME_C_Project_WO_Resource_ID = "C_Project_WO_Resource_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Project_WO_Resource_Conflict, Object> COLUMN_Created = new ModelColumn<>(I_C_Project_WO_Resource_Conflict.class, "Created", null);
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_SimulationPlan_ID (int C_SimulationPlan_ID);

	/**
	 * Get Simulation Plan.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_SimulationPlan_ID();

	@Nullable org.compiere.model.I_C_SimulationPlan getC_SimulationPlan();

	void setC_SimulationPlan(@Nullable org.compiere.model.I_C_SimulationPlan C_SimulationPlan);

	ModelColumn<I_C_Project_WO_Resource_Conflict, org.compiere.model.I_C_SimulationPlan> COLUMN_C_SimulationPlan_ID = new ModelColumn<>(I_C_Project_WO_Resource_Conflict.class, "C_SimulationPlan_ID", org.compiere.model.I_C_SimulationPlan.class);
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

	ModelColumn<I_C_Project_WO_Resource_Conflict, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Project_WO_Resource_Conflict.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsApproved (boolean IsApproved);

	/**
	 * Get Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isApproved();

	ModelColumn<I_C_Project_WO_Resource_Conflict, Object> COLUMN_IsApproved = new ModelColumn<>(I_C_Project_WO_Resource_Conflict.class, "IsApproved", null);
	String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStatus (java.lang.String Status);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getStatus();

	ModelColumn<I_C_Project_WO_Resource_Conflict, Object> COLUMN_Status = new ModelColumn<>(I_C_Project_WO_Resource_Conflict.class, "Status", null);
	String COLUMNNAME_Status = "Status";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Project_WO_Resource_Conflict, Object> COLUMN_Updated = new ModelColumn<>(I_C_Project_WO_Resource_Conflict.class, "Updated", null);
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
