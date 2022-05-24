package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for CRM_Occupation
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_CRM_Occupation 
{

	String Table_Name = "CRM_Occupation";

//	/** AD_Table_ID=541771 */
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
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
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

	ModelColumn<I_CRM_Occupation, Object> COLUMN_Created = new ModelColumn<>(I_CRM_Occupation.class, "Created", null);
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
	 * Set Speciality.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCRM_Occupation_ID (int CRM_Occupation_ID);

	/**
	 * Get Speciality.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCRM_Occupation_ID();

	ModelColumn<I_CRM_Occupation, Object> COLUMN_CRM_Occupation_ID = new ModelColumn<>(I_CRM_Occupation.class, "CRM_Occupation_ID", null);
	String COLUMNNAME_CRM_Occupation_ID = "CRM_Occupation_ID";

	/**
	 * Set Parent Specialization.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCRM_Occupation_Parent_ID (int CRM_Occupation_Parent_ID);

	/**
	 * Get Parent Specialization.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCRM_Occupation_Parent_ID();

	@Nullable org.compiere.model.I_CRM_Occupation getCRM_Occupation_Parent();

	void setCRM_Occupation_Parent(@Nullable org.compiere.model.I_CRM_Occupation CRM_Occupation_Parent);

	ModelColumn<I_CRM_Occupation, org.compiere.model.I_CRM_Occupation> COLUMN_CRM_Occupation_Parent_ID = new ModelColumn<>(I_CRM_Occupation.class, "CRM_Occupation_Parent_ID", org.compiere.model.I_CRM_Occupation.class);
	String COLUMNNAME_CRM_Occupation_Parent_ID = "CRM_Occupation_Parent_ID";

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

	ModelColumn<I_CRM_Occupation, Object> COLUMN_IsActive = new ModelColumn<>(I_CRM_Occupation.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Job Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setJobType (@Nullable java.lang.String JobType);

	/**
	 * Get Job Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getJobType();

	ModelColumn<I_CRM_Occupation, Object> COLUMN_JobType = new ModelColumn<>(I_CRM_Occupation.class, "JobType", null);
	String COLUMNNAME_JobType = "JobType";

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

	ModelColumn<I_CRM_Occupation, Object> COLUMN_Name = new ModelColumn<>(I_CRM_Occupation.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_CRM_Occupation, Object> COLUMN_Updated = new ModelColumn<>(I_CRM_Occupation.class, "Updated", null);
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
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: Text
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: Text
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_CRM_Occupation, Object> COLUMN_Value = new ModelColumn<>(I_CRM_Occupation.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
