package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for Mobile_Application_Action
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Mobile_Application_Action 
{

	String Table_Name = "Mobile_Application_Action";

//	/** AD_Table_ID=542533 */
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

	ModelColumn<I_Mobile_Application_Action, Object> COLUMN_Created = new ModelColumn<>(I_Mobile_Application_Action.class, "Created", null);
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
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_Mobile_Application_Action, Object> COLUMN_Description = new ModelColumn<>(I_Mobile_Application_Action.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInternalName (java.lang.String InternalName);

	/**
	 * Get Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getInternalName();

	ModelColumn<I_Mobile_Application_Action, Object> COLUMN_InternalName = new ModelColumn<>(I_Mobile_Application_Action.class, "InternalName", null);
	String COLUMNNAME_InternalName = "InternalName";

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

	ModelColumn<I_Mobile_Application_Action, Object> COLUMN_IsActive = new ModelColumn<>(I_Mobile_Application_Action.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Mobile Application Action.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMobile_Application_Action_ID (int Mobile_Application_Action_ID);

	/**
	 * Get Mobile Application Action.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMobile_Application_Action_ID();

	ModelColumn<I_Mobile_Application_Action, Object> COLUMN_Mobile_Application_Action_ID = new ModelColumn<>(I_Mobile_Application_Action.class, "Mobile_Application_Action_ID", null);
	String COLUMNNAME_Mobile_Application_Action_ID = "Mobile_Application_Action_ID";

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

	ModelColumn<I_Mobile_Application_Action, org.compiere.model.I_Mobile_Application> COLUMN_Mobile_Application_ID = new ModelColumn<>(I_Mobile_Application_Action.class, "Mobile_Application_ID", org.compiere.model.I_Mobile_Application.class);
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

	ModelColumn<I_Mobile_Application_Action, Object> COLUMN_Updated = new ModelColumn<>(I_Mobile_Application_Action.class, "Updated", null);
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
