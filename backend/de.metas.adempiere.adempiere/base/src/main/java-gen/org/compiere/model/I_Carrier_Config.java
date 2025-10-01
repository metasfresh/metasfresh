package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for Carrier_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Carrier_Config 
{

	String Table_Name = "Carrier_Config";

//	/** AD_Table_ID=542540 */
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
	 * Set URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBase_url (@Nullable java.lang.String Base_url);

	/**
	 * Get URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBase_url();

	ModelColumn<I_Carrier_Config, Object> COLUMN_Base_url = new ModelColumn<>(I_Carrier_Config.class, "Base_url", null);
	String COLUMNNAME_Base_url = "Base_url";

	/**
	 * Set Carrier Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCarrier_Config_ID (int Carrier_Config_ID);

	/**
	 * Get Carrier Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCarrier_Config_ID();

	ModelColumn<I_Carrier_Config, Object> COLUMN_Carrier_Config_ID = new ModelColumn<>(I_Carrier_Config.class, "Carrier_Config_ID", null);
	String COLUMNNAME_Carrier_Config_ID = "Carrier_Config_ID";

	/**
	 * Set Access key ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setClient_Id (@Nullable java.lang.String Client_Id);

	/**
	 * Get Access key ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getClient_Id();

	ModelColumn<I_Carrier_Config, Object> COLUMN_Client_Id = new ModelColumn<>(I_Carrier_Config.class, "Client_Id", null);
	String COLUMNNAME_Client_Id = "Client_Id";

	/**
	 * Set Secret access key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setClient_Secret (@Nullable java.lang.String Client_Secret);

	/**
	 * Get Secret access key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getClient_Secret();

	ModelColumn<I_Carrier_Config, Object> COLUMN_Client_Secret = new ModelColumn<>(I_Carrier_Config.class, "Client_Secret", null);
	String COLUMNNAME_Client_Secret = "Client_Secret";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_Carrier_Config, Object> COLUMN_Created = new ModelColumn<>(I_Carrier_Config.class, "Created", null);
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
	 * Set Gateway ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGatewayId (@Nullable java.lang.String GatewayId);

	/**
	 * Get Gateway ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGatewayId();

	ModelColumn<I_Carrier_Config, Object> COLUMN_GatewayId = new ModelColumn<>(I_Carrier_Config.class, "GatewayId", null);
	String COLUMNNAME_GatewayId = "GatewayId";

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

	ModelColumn<I_Carrier_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_Carrier_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Shipper_ID();

	ModelColumn<I_Carrier_Config, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_Carrier_Config.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Password.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPassword (@Nullable java.lang.String Password);

	/**
	 * Get Password.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPassword();

	ModelColumn<I_Carrier_Config, Object> COLUMN_Password = new ModelColumn<>(I_Carrier_Config.class, "Password", null);
	String COLUMNNAME_Password = "Password";

	/**
	 * Set Tracking URL.
	 * URL of the shipper to track shipments
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTrackingURL (@Nullable java.lang.String TrackingURL);

	/**
	 * Get Tracking URL.
	 * URL of the shipper to track shipments
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTrackingURL();

	ModelColumn<I_Carrier_Config, Object> COLUMN_TrackingURL = new ModelColumn<>(I_Carrier_Config.class, "TrackingURL", null);
	String COLUMNNAME_TrackingURL = "TrackingURL";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_Carrier_Config, Object> COLUMN_Updated = new ModelColumn<>(I_Carrier_Config.class, "Updated", null);
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

	/**
	 * Set UserName.
	 * UserName / Login to use for login
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserName (@Nullable java.lang.String UserName);

	/**
	 * Get UserName.
	 * UserName / Login to use for login
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserName();

	ModelColumn<I_Carrier_Config, Object> COLUMN_UserName = new ModelColumn<>(I_Carrier_Config.class, "UserName", null);
	String COLUMNNAME_UserName = "UserName";
}
