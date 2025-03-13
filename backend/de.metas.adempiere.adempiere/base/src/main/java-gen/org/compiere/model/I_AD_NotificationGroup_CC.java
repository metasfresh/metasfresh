package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_NotificationGroup_CC
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_NotificationGroup_CC 
{

	String Table_Name = "AD_NotificationGroup_CC";

//	/** AD_Table_ID=542467 */
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
	 * Set Notification Group CC.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_NotificationGroup_CC_ID (int AD_NotificationGroup_CC_ID);

	/**
	 * Get Notification Group CC.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_NotificationGroup_CC_ID();

	ModelColumn<I_AD_NotificationGroup_CC, Object> COLUMN_AD_NotificationGroup_CC_ID = new ModelColumn<>(I_AD_NotificationGroup_CC.class, "AD_NotificationGroup_CC_ID", null);
	String COLUMNNAME_AD_NotificationGroup_CC_ID = "AD_NotificationGroup_CC_ID";

	/**
	 * Set Notification group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_NotificationGroup_ID (int AD_NotificationGroup_ID);

	/**
	 * Get Notification group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_NotificationGroup_ID();

	org.compiere.model.I_AD_NotificationGroup getAD_NotificationGroup();

	void setAD_NotificationGroup(org.compiere.model.I_AD_NotificationGroup AD_NotificationGroup);

	ModelColumn<I_AD_NotificationGroup_CC, org.compiere.model.I_AD_NotificationGroup> COLUMN_AD_NotificationGroup_ID = new ModelColumn<>(I_AD_NotificationGroup_CC.class, "AD_NotificationGroup_ID", org.compiere.model.I_AD_NotificationGroup.class);
	String COLUMNNAME_AD_NotificationGroup_ID = "AD_NotificationGroup_ID";

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

	ModelColumn<I_AD_NotificationGroup_CC, Object> COLUMN_Created = new ModelColumn<>(I_AD_NotificationGroup_CC.class, "Created", null);
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

	ModelColumn<I_AD_NotificationGroup_CC, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_NotificationGroup_CC.class, "IsActive", null);
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

	ModelColumn<I_AD_NotificationGroup_CC, Object> COLUMN_Updated = new ModelColumn<>(I_AD_NotificationGroup_CC.class, "Updated", null);
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
