package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for MobileUI_UserProfile_Picking
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MobileUI_UserProfile_Picking 
{

	String Table_Name = "MobileUI_UserProfile_Picking";

//	/** AD_Table_ID=542373 */
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

	ModelColumn<I_MobileUI_UserProfile_Picking, Object> COLUMN_Created = new ModelColumn<>(I_MobileUI_UserProfile_Picking.class, "Created", null);
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
	 * Set Create Shipment Policy.
	 * Create Shipment Policy - Don't Create, Create Draft, Create and Complete
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCreateShipmentPolicy (java.lang.String CreateShipmentPolicy);

	/**
	 * Get Create Shipment Policy.
	 * Create Shipment Policy - Don't Create, Create Draft, Create and Complete
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getCreateShipmentPolicy();

	ModelColumn<I_MobileUI_UserProfile_Picking, Object> COLUMN_CreateShipmentPolicy = new ModelColumn<>(I_MobileUI_UserProfile_Picking.class, "CreateShipmentPolicy", null);
	String COLUMNNAME_CreateShipmentPolicy = "CreateShipmentPolicy";

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

	ModelColumn<I_MobileUI_UserProfile_Picking, Object> COLUMN_IsActive = new ModelColumn<>(I_MobileUI_UserProfile_Picking.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Allow picking any HU.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowPickingAnyHU (boolean IsAllowPickingAnyHU);

	/**
	 * Get Allow picking any HU.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowPickingAnyHU();

	ModelColumn<I_MobileUI_UserProfile_Picking, Object> COLUMN_IsAllowPickingAnyHU = new ModelColumn<>(I_MobileUI_UserProfile_Picking.class, "IsAllowPickingAnyHU", null);
	String COLUMNNAME_IsAllowPickingAnyHU = "IsAllowPickingAnyHU";

	/**
	 * Set Always split HU.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAlwaysSplitHUsEnabled (boolean IsAlwaysSplitHUsEnabled);

	/**
	 * Get Always split HU.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAlwaysSplitHUsEnabled();

	ModelColumn<I_MobileUI_UserProfile_Picking, Object> COLUMN_IsAlwaysSplitHUsEnabled = new ModelColumn<>(I_MobileUI_UserProfile_Picking.class, "IsAlwaysSplitHUsEnabled", null);
	String COLUMNNAME_IsAlwaysSplitHUsEnabled = "IsAlwaysSplitHUsEnabled";

	/**
	 * Set Mobile UI Picking Profile.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMobileUI_UserProfile_Picking_ID (int MobileUI_UserProfile_Picking_ID);

	/**
	 * Get Mobile UI Picking Profile.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMobileUI_UserProfile_Picking_ID();

	ModelColumn<I_MobileUI_UserProfile_Picking, Object> COLUMN_MobileUI_UserProfile_Picking_ID = new ModelColumn<>(I_MobileUI_UserProfile_Picking.class, "MobileUI_UserProfile_Picking_ID", null);
	String COLUMNNAME_MobileUI_UserProfile_Picking_ID = "MobileUI_UserProfile_Picking_ID";

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

	ModelColumn<I_MobileUI_UserProfile_Picking, Object> COLUMN_Name = new ModelColumn<>(I_MobileUI_UserProfile_Picking.class, "Name", null);
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

	ModelColumn<I_MobileUI_UserProfile_Picking, Object> COLUMN_Updated = new ModelColumn<>(I_MobileUI_UserProfile_Picking.class, "Updated", null);
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
