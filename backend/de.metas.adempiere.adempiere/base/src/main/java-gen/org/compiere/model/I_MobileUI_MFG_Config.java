package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for MobileUI_MFG_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MobileUI_MFG_Config 
{

	String Table_Name = "MobileUI_MFG_Config";

//	/** AD_Table_ID=542397 */
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

	ModelColumn<I_MobileUI_MFG_Config, Object> COLUMN_Created = new ModelColumn<>(I_MobileUI_MFG_Config.class, "Created", null);
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

	ModelColumn<I_MobileUI_MFG_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_MobileUI_MFG_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Scan Resource QR Code.
	 * User needs to scan the resource QR code first
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsScanResourceRequired (boolean IsScanResourceRequired);

	/**
	 * Get Scan Resource QR Code.
	 * User needs to scan the resource QR code first
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isScanResourceRequired();

	ModelColumn<I_MobileUI_MFG_Config, Object> COLUMN_IsScanResourceRequired = new ModelColumn<>(I_MobileUI_MFG_Config.class, "IsScanResourceRequired", null);
	String COLUMNNAME_IsScanResourceRequired = "IsScanResourceRequired";

	/**
	 * Set MobileUI Manufacturing Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMobileUI_MFG_Config_ID (int MobileUI_MFG_Config_ID);

	/**
	 * Get MobileUI Manufacturing Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMobileUI_MFG_Config_ID();

	ModelColumn<I_MobileUI_MFG_Config, Object> COLUMN_MobileUI_MFG_Config_ID = new ModelColumn<>(I_MobileUI_MFG_Config.class, "MobileUI_MFG_Config_ID", null);
	String COLUMNNAME_MobileUI_MFG_Config_ID = "MobileUI_MFG_Config_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_MobileUI_MFG_Config, Object> COLUMN_Updated = new ModelColumn<>(I_MobileUI_MFG_Config.class, "Updated", null);
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
