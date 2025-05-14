package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for Mobile_Application_Access
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Mobile_Application_Access 
{

	String Table_Name = "Mobile_Application_Access";

//	/** AD_Table_ID=542446 */
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
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Role_ID (int AD_Role_ID);

	/**
	 * Get Role.
	 * Responsibility Role
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Role_ID();

	org.compiere.model.I_AD_Role getAD_Role();

	void setAD_Role(org.compiere.model.I_AD_Role AD_Role);

	ModelColumn<I_Mobile_Application_Access, org.compiere.model.I_AD_Role> COLUMN_AD_Role_ID = new ModelColumn<>(I_Mobile_Application_Access.class, "AD_Role_ID", org.compiere.model.I_AD_Role.class);
	String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_Mobile_Application_Access, Object> COLUMN_Created = new ModelColumn<>(I_Mobile_Application_Access.class, "Created", null);
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

	ModelColumn<I_Mobile_Application_Access, Object> COLUMN_IsActive = new ModelColumn<>(I_Mobile_Application_Access.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Mobile Application Role Access.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMobile_Application_Access_ID (int Mobile_Application_Access_ID);

	/**
	 * Get Mobile Application Role Access.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMobile_Application_Access_ID();

	ModelColumn<I_Mobile_Application_Access, Object> COLUMN_Mobile_Application_Access_ID = new ModelColumn<>(I_Mobile_Application_Access.class, "Mobile_Application_Access_ID", null);
	String COLUMNNAME_Mobile_Application_Access_ID = "Mobile_Application_Access_ID";

	/**
	 * Set Mobile Application.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMobile_Application_ID (int Mobile_Application_ID);

	/**
	 * Get Mobile Application.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMobile_Application_ID();

	org.compiere.model.I_Mobile_Application getMobile_Application();

	void setMobile_Application(org.compiere.model.I_Mobile_Application Mobile_Application);

	ModelColumn<I_Mobile_Application_Access, org.compiere.model.I_Mobile_Application> COLUMN_Mobile_Application_ID = new ModelColumn<>(I_Mobile_Application_Access.class, "Mobile_Application_ID", org.compiere.model.I_Mobile_Application.class);
	String COLUMNNAME_Mobile_Application_ID = "Mobile_Application_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_Mobile_Application_Access, Object> COLUMN_Updated = new ModelColumn<>(I_Mobile_Application_Access.class, "Updated", null);
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
