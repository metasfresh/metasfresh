package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for MobileUI_HUManager_Attribute
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MobileUI_HUManager_Attribute 
{

	String Table_Name = "MobileUI_HUManager_Attribute";

//	/** AD_Table_ID=542423 */
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

	ModelColumn<I_MobileUI_HUManager_Attribute, Object> COLUMN_Created = new ModelColumn<>(I_MobileUI_HUManager_Attribute.class, "Created", null);
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

	ModelColumn<I_MobileUI_HUManager_Attribute, Object> COLUMN_IsActive = new ModelColumn<>(I_MobileUI_HUManager_Attribute.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Attribute.
	 * Produkt-Merkmal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Attribute_ID (int M_Attribute_ID);

	/**
	 * Get Attribute.
	 * Produkt-Merkmal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Attribute_ID();

	String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

	/**
	 * Set Merkmale.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMobileUI_HUManager_Attribute_ID (int MobileUI_HUManager_Attribute_ID);

	/**
	 * Get Merkmale.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMobileUI_HUManager_Attribute_ID();

	ModelColumn<I_MobileUI_HUManager_Attribute, Object> COLUMN_MobileUI_HUManager_Attribute_ID = new ModelColumn<>(I_MobileUI_HUManager_Attribute.class, "MobileUI_HUManager_Attribute_ID", null);
	String COLUMNNAME_MobileUI_HUManager_Attribute_ID = "MobileUI_HUManager_Attribute_ID";

	/**
	 * Set Mobile UI HU Manager.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMobileUI_HUManager_ID (int MobileUI_HUManager_ID);

	/**
	 * Get Mobile UI HU Manager.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMobileUI_HUManager_ID();

	org.compiere.model.I_MobileUI_HUManager getMobileUI_HUManager();

	void setMobileUI_HUManager(org.compiere.model.I_MobileUI_HUManager MobileUI_HUManager);

	ModelColumn<I_MobileUI_HUManager_Attribute, org.compiere.model.I_MobileUI_HUManager> COLUMN_MobileUI_HUManager_ID = new ModelColumn<>(I_MobileUI_HUManager_Attribute.class, "MobileUI_HUManager_ID", org.compiere.model.I_MobileUI_HUManager.class);
	String COLUMNNAME_MobileUI_HUManager_ID = "MobileUI_HUManager_ID";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_MobileUI_HUManager_Attribute, Object> COLUMN_SeqNo = new ModelColumn<>(I_MobileUI_HUManager_Attribute.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_MobileUI_HUManager_Attribute, Object> COLUMN_Updated = new ModelColumn<>(I_MobileUI_HUManager_Attribute.class, "Updated", null);
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
