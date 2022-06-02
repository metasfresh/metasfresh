package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for S_Resource_Group_Assignment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_S_Resource_Group_Assignment 
{

	String Table_Name = "S_Resource_Group_Assignment";

//	/** AD_Table_ID=542155 */
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

	ModelColumn<I_S_Resource_Group_Assignment, Object> COLUMN_AssignDateFrom = new ModelColumn<>(I_S_Resource_Group_Assignment.class, "AssignDateFrom", null);
	String COLUMNNAME_AssignDateFrom = "AssignDateFrom";

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

	ModelColumn<I_S_Resource_Group_Assignment, Object> COLUMN_AssignDateTo = new ModelColumn<>(I_S_Resource_Group_Assignment.class, "AssignDateTo", null);
	String COLUMNNAME_AssignDateTo = "AssignDateTo";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_S_Resource_Group_Assignment, Object> COLUMN_Created = new ModelColumn<>(I_S_Resource_Group_Assignment.class, "Created", null);
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
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_S_Resource_Group_Assignment, Object> COLUMN_Description = new ModelColumn<>(I_S_Resource_Group_Assignment.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_S_Resource_Group_Assignment, Object> COLUMN_IsActive = new ModelColumn<>(I_S_Resource_Group_Assignment.class, "IsActive", null);
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

	ModelColumn<I_S_Resource_Group_Assignment, Object> COLUMN_IsAllDay = new ModelColumn<>(I_S_Resource_Group_Assignment.class, "IsAllDay", null);
	String COLUMNNAME_IsAllDay = "IsAllDay";

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

	ModelColumn<I_S_Resource_Group_Assignment, Object> COLUMN_Name = new ModelColumn<>(I_S_Resource_Group_Assignment.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Resource Group Assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_Resource_Group_Assignment_ID (int S_Resource_Group_Assignment_ID);

	/**
	 * Get Resource Group Assignment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_Resource_Group_Assignment_ID();

	ModelColumn<I_S_Resource_Group_Assignment, Object> COLUMN_S_Resource_Group_Assignment_ID = new ModelColumn<>(I_S_Resource_Group_Assignment.class, "S_Resource_Group_Assignment_ID", null);
	String COLUMNNAME_S_Resource_Group_Assignment_ID = "S_Resource_Group_Assignment_ID";

	/**
	 * Set Resource Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setS_Resource_Group_ID (int S_Resource_Group_ID);

	/**
	 * Get Resource Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getS_Resource_Group_ID();

	String COLUMNNAME_S_Resource_Group_ID = "S_Resource_Group_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_S_Resource_Group_Assignment, Object> COLUMN_Updated = new ModelColumn<>(I_S_Resource_Group_Assignment.class, "Updated", null);
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
