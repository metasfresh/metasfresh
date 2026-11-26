package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_Table_Access
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Table_Access 
{

	String Table_Name = "AD_Table_Access";

//	/** AD_Table_ID=565 */
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
	 * Set Role.
	 * Responsibility Role
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Role_ID (int AD_Role_ID);

	/**
	 * Get Role.
	 * Responsibility Role
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Role_ID();

	org.compiere.model.I_AD_Role getAD_Role();

	void setAD_Role(org.compiere.model.I_AD_Role AD_Role);

	ModelColumn<I_AD_Table_Access, org.compiere.model.I_AD_Role> COLUMN_AD_Role_ID = new ModelColumn<>(I_AD_Table_Access.class, "AD_Role_ID", org.compiere.model.I_AD_Role.class);
	String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/**
	 * Set AD_Table_Access.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_Access_ID (int AD_Table_Access_ID);

	/**
	 * Get AD_Table_Access.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_Access_ID();

	ModelColumn<I_AD_Table_Access, Object> COLUMN_AD_Table_Access_ID = new ModelColumn<>(I_AD_Table_Access.class, "AD_Table_Access_ID", null);
	String COLUMNNAME_AD_Table_Access_ID = "AD_Table_Access_ID";

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

	ModelColumn<I_AD_Table_Access, Object> COLUMN_Created = new ModelColumn<>(I_AD_Table_Access.class, "Created", null);
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

	ModelColumn<I_AD_Table_Access, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Table_Access.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Create new records.
	 * When set to No, the desktop WebUI will not offer or accept creating a new record for this role in this table. Warning: a row added to an included role for an unrelated reason defaults to Yes here and can, in a role-inclusion chain, lift another role's restriction.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCanCreateNewRecords (boolean IsCanCreateNewRecords);

	/**
	 * Get Create new records.
	 * When set to No, the desktop WebUI will not offer or accept creating a new record for this role in this table. Warning: a row added to an included role for an unrelated reason defaults to Yes here and can, in a role-inclusion chain, lift another role's restriction.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCanCreateNewRecords();

	ModelColumn<I_AD_Table_Access, Object> COLUMN_IsCanCreateNewRecords = new ModelColumn<>(I_AD_Table_Access.class, "IsCanCreateNewRecords", null);
	String COLUMNNAME_IsCanCreateNewRecords = "IsCanCreateNewRecords";

	/**
	 * Set Can Export.
	 * Users with this role can export data
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCanExport (boolean IsCanExport);

	/**
	 * Get Can Export.
	 * Users with this role can export data
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCanExport();

	ModelColumn<I_AD_Table_Access, Object> COLUMN_IsCanExport = new ModelColumn<>(I_AD_Table_Access.class, "IsCanExport", null);
	String COLUMNNAME_IsCanExport = "IsCanExport";

	/**
	 * Set Can Report.
	 * Users with this role can create reports
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCanReport (boolean IsCanReport);

	/**
	 * Get Can Report.
	 * Users with this role can create reports
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCanReport();

	ModelColumn<I_AD_Table_Access, Object> COLUMN_IsCanReport = new ModelColumn<>(I_AD_Table_Access.class, "IsCanReport", null);
	String COLUMNNAME_IsCanReport = "IsCanReport";

	/**
	 * Set Read-only.
	 * Field / entry / area is read-only.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReadOnly (boolean IsReadOnly);

	/**
	 * Get Read-only.
	 * Field / entry / area is read-only.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReadOnly();

	ModelColumn<I_AD_Table_Access, Object> COLUMN_IsReadOnly = new ModelColumn<>(I_AD_Table_Access.class, "IsReadOnly", null);
	String COLUMNNAME_IsReadOnly = "IsReadOnly";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Table_Access, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Table_Access.class, "Updated", null);
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
