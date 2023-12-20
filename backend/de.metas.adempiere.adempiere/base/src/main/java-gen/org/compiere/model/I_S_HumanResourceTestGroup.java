package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for S_HumanResourceTestGroup
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_S_HumanResourceTestGroup 
{

	String Table_Name = "S_HumanResourceTestGroup";

//	/** AD_Table_ID=542326 */
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
	 * Set Capacity in hours.
	 * Personnel capacity hours per week
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCapacityInHours (int CapacityInHours);

	/**
	 * Get Capacity in hours.
	 * Personnel capacity hours per week
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCapacityInHours();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_CapacityInHours = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "CapacityInHours", null);
	String COLUMNNAME_CapacityInHours = "CapacityInHours";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_Created = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "Created", null);
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
	 * Set Department.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDepartment (java.lang.String Department);

	/**
	 * Get Department.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDepartment();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_Department = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "Department", null);
	String COLUMNNAME_Department = "Department";

	/**
	 * Set LPG.
	 * Average weekly capacity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGroupIdentifier (java.lang.String GroupIdentifier);

	/**
	 * Get LPG.
	 * Average weekly capacity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getGroupIdentifier();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_GroupIdentifier = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "GroupIdentifier", null);
	String COLUMNNAME_GroupIdentifier = "GroupIdentifier";

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

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_IsActive = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_Name = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Test facility group.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_HumanResourceTestGroup_ID (int S_HumanResourceTestGroup_ID);

	/**
	 * Get Test facility group.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_HumanResourceTestGroup_ID();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_S_HumanResourceTestGroup_ID = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "S_HumanResourceTestGroup_ID", null);
	String COLUMNNAME_S_HumanResourceTestGroup_ID = "S_HumanResourceTestGroup_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_S_HumanResourceTestGroup, Object> COLUMN_Updated = new ModelColumn<>(I_S_HumanResourceTestGroup.class, "Updated", null);
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
