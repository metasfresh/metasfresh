package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for PP_Workstation_UserAssign
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PP_Workstation_UserAssign 
{

	String Table_Name = "PP_Workstation_UserAssign";

//	/** AD_Table_ID=542398 */
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
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_PP_Workstation_UserAssign, Object> COLUMN_Created = new ModelColumn<>(I_PP_Workstation_UserAssign.class, "Created", null);
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

	ModelColumn<I_PP_Workstation_UserAssign, Object> COLUMN_IsActive = new ModelColumn<>(I_PP_Workstation_UserAssign.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Workstation User Assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Workstation_UserAssign_ID (int PP_Workstation_UserAssign_ID);

	/**
	 * Get Workstation User Assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Workstation_UserAssign_ID();

	ModelColumn<I_PP_Workstation_UserAssign, Object> COLUMN_PP_Workstation_UserAssign_ID = new ModelColumn<>(I_PP_Workstation_UserAssign.class, "PP_Workstation_UserAssign_ID", null);
	String COLUMNNAME_PP_Workstation_UserAssign_ID = "PP_Workstation_UserAssign_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PP_Workstation_UserAssign, Object> COLUMN_Updated = new ModelColumn<>(I_PP_Workstation_UserAssign.class, "Updated", null);
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

	/**
	 * Set Work Station.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWorkStation_ID (int WorkStation_ID);

	/**
	 * Get Work Station.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getWorkStation_ID();

	org.compiere.model.I_S_Resource getWorkStation();

	void setWorkStation(org.compiere.model.I_S_Resource WorkStation);

	ModelColumn<I_PP_Workstation_UserAssign, org.compiere.model.I_S_Resource> COLUMN_WorkStation_ID = new ModelColumn<>(I_PP_Workstation_UserAssign.class, "WorkStation_ID", org.compiere.model.I_S_Resource.class);
	String COLUMNNAME_WorkStation_ID = "WorkStation_ID";
}
