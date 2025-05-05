package de.metas.handlingunits.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for QRCode_Configuration
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_QRCode_Configuration 
{

	String Table_Name = "QRCode_Configuration";

//	/** AD_Table_ID=542393 */
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

	ModelColumn<I_QRCode_Configuration, Object> COLUMN_Created = new ModelColumn<>(I_QRCode_Configuration.class, "Created", null);
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

	ModelColumn<I_QRCode_Configuration, Object> COLUMN_IsActive = new ModelColumn<>(I_QRCode_Configuration.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set One QR-Code for aggregated HUs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOneQRCodeForAggregatedHUs (boolean IsOneQRCodeForAggregatedHUs);

	/**
	 * Get One QR-Code for aggregated HUs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOneQRCodeForAggregatedHUs();

	ModelColumn<I_QRCode_Configuration, Object> COLUMN_IsOneQRCodeForAggregatedHUs = new ModelColumn<>(I_QRCode_Configuration.class, "IsOneQRCodeForAggregatedHUs", null);
	String COLUMNNAME_IsOneQRCodeForAggregatedHUs = "IsOneQRCodeForAggregatedHUs";

	/**
	 * Set One QR-Code for matching attributes.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOneQRCodeForMatchingAttributes (boolean IsOneQRCodeForMatchingAttributes);

	/**
	 * Get One QR-Code for matching attributes.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOneQRCodeForMatchingAttributes();

	ModelColumn<I_QRCode_Configuration, Object> COLUMN_IsOneQRCodeForMatchingAttributes = new ModelColumn<>(I_QRCode_Configuration.class, "IsOneQRCodeForMatchingAttributes", null);
	String COLUMNNAME_IsOneQRCodeForMatchingAttributes = "IsOneQRCodeForMatchingAttributes";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getName();

	ModelColumn<I_QRCode_Configuration, Object> COLUMN_Name = new ModelColumn<>(I_QRCode_Configuration.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set QR Code Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQRCode_Configuration_ID (int QRCode_Configuration_ID);

	/**
	 * Get QR Code Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getQRCode_Configuration_ID();

	ModelColumn<I_QRCode_Configuration, Object> COLUMN_QRCode_Configuration_ID = new ModelColumn<>(I_QRCode_Configuration.class, "QRCode_Configuration_ID", null);
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

	ModelColumn<I_QRCode_Configuration, Object> COLUMN_Updated = new ModelColumn<>(I_QRCode_Configuration.class, "Updated", null);
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
