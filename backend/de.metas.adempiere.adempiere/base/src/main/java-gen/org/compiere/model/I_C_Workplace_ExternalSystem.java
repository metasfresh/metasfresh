package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Workplace_ExternalSystem
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Workplace_ExternalSystem 
{

	String Table_Name = "C_Workplace_ExternalSystem";

//	/** AD_Table_ID=542553 */
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

	ModelColumn<I_C_Workplace_ExternalSystem, Object> COLUMN_Created = new ModelColumn<>(I_C_Workplace_ExternalSystem.class, "Created", null);
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
	 * Set Workplace ExternalSystem.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Workplace_ExternalSystem_ID (int C_Workplace_ExternalSystem_ID);

	/**
	 * Get Workplace ExternalSystem.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Workplace_ExternalSystem_ID();

	ModelColumn<I_C_Workplace_ExternalSystem, Object> COLUMN_C_Workplace_ExternalSystem_ID = new ModelColumn<>(I_C_Workplace_ExternalSystem.class, "C_Workplace_ExternalSystem_ID", null);
	String COLUMNNAME_C_Workplace_ExternalSystem_ID = "C_Workplace_ExternalSystem_ID";

	/**
	 * Set Workplace.
	 * The assignment applies to all users assigned to this workstation
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Workplace_ID (int C_Workplace_ID);

	/**
	 * Get Workplace.
	 * The assignment applies to all users assigned to this workstation
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Workplace_ID();

	ModelColumn<I_C_Workplace_ExternalSystem, org.compiere.model.I_C_Workplace> COLUMN_C_Workplace_ID = new ModelColumn<>(I_C_Workplace_ExternalSystem.class, "C_Workplace_ID", org.compiere.model.I_C_Workplace.class);
	String COLUMNNAME_C_Workplace_ID = "C_Workplace_ID";

	/**
	 * Set External System.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_ID (int ExternalSystem_ID);

	/**
	 * Get External System.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_ID();

	ModelColumn<I_C_Workplace_ExternalSystem, Object> COLUMN_ExternalSystem_ID = new ModelColumn<>(I_C_Workplace_ExternalSystem.class, "ExternalSystem_ID", null);
	String COLUMNNAME_ExternalSystem_ID = "ExternalSystem_ID";

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

	ModelColumn<I_C_Workplace_ExternalSystem, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Workplace_ExternalSystem.class, "IsActive", null);
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

	ModelColumn<I_C_Workplace_ExternalSystem, Object> COLUMN_Updated = new ModelColumn<>(I_C_Workplace_ExternalSystem.class, "Updated", null);
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
