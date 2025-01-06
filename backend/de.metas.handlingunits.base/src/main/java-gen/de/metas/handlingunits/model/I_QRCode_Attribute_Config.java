package de.metas.handlingunits.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for QRCode_Attribute_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_QRCode_Attribute_Config 
{

	String Table_Name = "QRCode_Attribute_Config";

//	/** AD_Table_ID=542394 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set System Attribute.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Attribute_ID (int AD_Attribute_ID);

	/**
	 * Get System Attribute.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Attribute_ID();

	String COLUMNNAME_AD_Attribute_ID = "AD_Attribute_ID";

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

	ModelColumn<I_QRCode_Attribute_Config, Object> COLUMN_Created = new ModelColumn<>(I_QRCode_Attribute_Config.class, "Created", null);
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

	ModelColumn<I_QRCode_Attribute_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_QRCode_Attribute_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set QR-Code Attribute Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQRCode_Attribute_Config_ID (int QRCode_Attribute_Config_ID);

	/**
	 * Get QR-Code Attribute Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getQRCode_Attribute_Config_ID();

	ModelColumn<I_QRCode_Attribute_Config, Object> COLUMN_QRCode_Attribute_Config_ID = new ModelColumn<>(I_QRCode_Attribute_Config.class, "QRCode_Attribute_Config_ID", null);
	String COLUMNNAME_QRCode_Attribute_Config_ID = "QRCode_Attribute_Config_ID";

	/**
	 * Set QR Code Configuration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQRCode_Configuration_ID (int QRCode_Configuration_ID);

	/**
	 * Get QR Code Configuration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getQRCode_Configuration_ID();

	I_QRCode_Configuration getQRCode_Configuration();

	void setQRCode_Configuration(I_QRCode_Configuration QRCode_Configuration);

	ModelColumn<I_QRCode_Attribute_Config, I_QRCode_Configuration> COLUMN_QRCode_Configuration_ID = new ModelColumn<>(I_QRCode_Attribute_Config.class, "QRCode_Configuration_ID", I_QRCode_Configuration.class);
	String COLUMNNAME_QRCode_Configuration_ID = "QRCode_Configuration_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_QRCode_Attribute_Config, Object> COLUMN_Updated = new ModelColumn<>(I_QRCode_Attribute_Config.class, "Updated", null);
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
