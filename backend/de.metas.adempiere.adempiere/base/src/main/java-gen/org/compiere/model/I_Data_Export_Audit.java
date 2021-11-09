package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for Data_Export_Audit
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Data_Export_Audit 
{

	String Table_Name = "Data_Export_Audit";

//	/** AD_Table_ID=541804 */
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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_Data_Export_Audit, Object> COLUMN_Created = new ModelColumn<>(I_Data_Export_Audit.class, "Created", null);
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
	 * Set Exported Data Audit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setData_Export_Audit_ID (int Data_Export_Audit_ID);

	/**
	 * Get Exported Data Audit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getData_Export_Audit_ID();

	ModelColumn<I_Data_Export_Audit, Object> COLUMN_Data_Export_Audit_ID = new ModelColumn<>(I_Data_Export_Audit.class, "Data_Export_Audit_ID", null);
	String COLUMNNAME_Data_Export_Audit_ID = "Data_Export_Audit_ID";

	/**
	 * Set Data_Export_Audit_Parent_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setData_Export_Audit_Parent_ID (int Data_Export_Audit_Parent_ID);

	/**
	 * Get Data_Export_Audit_Parent_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getData_Export_Audit_Parent_ID();

	@Nullable org.compiere.model.I_Data_Export_Audit getData_Export_Audit_Parent();

	void setData_Export_Audit_Parent(@Nullable org.compiere.model.I_Data_Export_Audit Data_Export_Audit_Parent);

	ModelColumn<I_Data_Export_Audit, org.compiere.model.I_Data_Export_Audit> COLUMN_Data_Export_Audit_Parent_ID = new ModelColumn<>(I_Data_Export_Audit.class, "Data_Export_Audit_Parent_ID", org.compiere.model.I_Data_Export_Audit.class);
	String COLUMNNAME_Data_Export_Audit_Parent_ID = "Data_Export_Audit_Parent_ID";

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

	ModelColumn<I_Data_Export_Audit, Object> COLUMN_IsActive = new ModelColumn<>(I_Data_Export_Audit.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_Data_Export_Audit, Object> COLUMN_Record_ID = new ModelColumn<>(I_Data_Export_Audit.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_Data_Export_Audit, Object> COLUMN_Updated = new ModelColumn<>(I_Data_Export_Audit.class, "Updated", null);
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
