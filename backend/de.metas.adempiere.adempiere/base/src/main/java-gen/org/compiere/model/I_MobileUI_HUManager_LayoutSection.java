package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for MobileUI_HUManager_LayoutSection
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MobileUI_HUManager_LayoutSection 
{

	String Table_Name = "MobileUI_HUManager_LayoutSection";

//	/** AD_Table_ID=542524 */
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

	ModelColumn<I_MobileUI_HUManager_LayoutSection, Object> COLUMN_Created = new ModelColumn<>(I_MobileUI_HUManager_LayoutSection.class, "Created", null);
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

	ModelColumn<I_MobileUI_HUManager_LayoutSection, Object> COLUMN_IsActive = new ModelColumn<>(I_MobileUI_HUManager_LayoutSection.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Layout Section.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLayoutSection (java.lang.String LayoutSection);

	/**
	 * Get Layout Section.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getLayoutSection();

	ModelColumn<I_MobileUI_HUManager_LayoutSection, Object> COLUMN_LayoutSection = new ModelColumn<>(I_MobileUI_HUManager_LayoutSection.class, "LayoutSection", null);
	String COLUMNNAME_LayoutSection = "LayoutSection";

	/**
	 * Set Mobile UI HU Manager.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMobileUI_HUManager_ID (int MobileUI_HUManager_ID);

	/**
	 * Get Mobile UI HU Manager.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMobileUI_HUManager_ID();

	org.compiere.model.I_MobileUI_HUManager getMobileUI_HUManager();

	void setMobileUI_HUManager(org.compiere.model.I_MobileUI_HUManager MobileUI_HUManager);

	ModelColumn<I_MobileUI_HUManager_LayoutSection, org.compiere.model.I_MobileUI_HUManager> COLUMN_MobileUI_HUManager_ID = new ModelColumn<>(I_MobileUI_HUManager_LayoutSection.class, "MobileUI_HUManager_ID", org.compiere.model.I_MobileUI_HUManager.class);
	String COLUMNNAME_MobileUI_HUManager_ID = "MobileUI_HUManager_ID";

	/**
	 * Set Layout Sections.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMobileUI_HUManager_LayoutSection_ID (int MobileUI_HUManager_LayoutSection_ID);

	/**
	 * Get Layout Sections.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMobileUI_HUManager_LayoutSection_ID();

	ModelColumn<I_MobileUI_HUManager_LayoutSection, Object> COLUMN_MobileUI_HUManager_LayoutSection_ID = new ModelColumn<>(I_MobileUI_HUManager_LayoutSection.class, "MobileUI_HUManager_LayoutSection_ID", null);
	String COLUMNNAME_MobileUI_HUManager_LayoutSection_ID = "MobileUI_HUManager_LayoutSection_ID";

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

	ModelColumn<I_MobileUI_HUManager_LayoutSection, Object> COLUMN_SeqNo = new ModelColumn<>(I_MobileUI_HUManager_LayoutSection.class, "SeqNo", null);
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

	ModelColumn<I_MobileUI_HUManager_LayoutSection, Object> COLUMN_Updated = new ModelColumn<>(I_MobileUI_HUManager_LayoutSection.class, "Updated", null);
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
