package de.metas.shipper.gateway.dhl.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for DHL_Shipper_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_DHL_Shipper_Config 
{

	String Table_Name = "DHL_Shipper_Config";

//	/** AD_Table_ID=541411 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Account Number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAccountNumber (@Nullable java.lang.String AccountNumber);

	/**
	 * Get Account Number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAccountNumber();

	ModelColumn<I_DHL_Shipper_Config, Object> COLUMN_AccountNumber = new ModelColumn<>(I_DHL_Shipper_Config.class, "AccountNumber", null);
	String COLUMNNAME_AccountNumber = "AccountNumber";

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
	 * Set Application ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setapplicationID (@Nullable java.lang.String applicationID);

	/**
	 * Get Application ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getapplicationID();

	ModelColumn<I_DHL_Shipper_Config, Object> COLUMN_applicationID = new ModelColumn<>(I_DHL_Shipper_Config.class, "applicationID", null);
	String COLUMNNAME_applicationID = "applicationID";

	/**
	 * Set Application Token.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setApplicationToken (@Nullable java.lang.String ApplicationToken);

	/**
	 * Get Application Token.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getApplicationToken();

	ModelColumn<I_DHL_Shipper_Config, Object> COLUMN_ApplicationToken = new ModelColumn<>(I_DHL_Shipper_Config.class, "ApplicationToken", null);
	String COLUMNNAME_ApplicationToken = "ApplicationToken";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_DHL_Shipper_Config, Object> COLUMN_Created = new ModelColumn<>(I_DHL_Shipper_Config.class, "Created", null);
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
	 * Set DHL API URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setdhl_api_url (@Nullable java.lang.String dhl_api_url);

	/**
	 * Get DHL API URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getdhl_api_url();

	ModelColumn<I_DHL_Shipper_Config, Object> COLUMN_dhl_api_url = new ModelColumn<>(I_DHL_Shipper_Config.class, "dhl_api_url", null);
	String COLUMNNAME_dhl_api_url = "dhl_api_url";

	/**
	 * Set Dhl Lenght UOM ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDhl_LenghtUOM_ID (int Dhl_LenghtUOM_ID);

	/**
	 * Get Dhl Lenght UOM ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDhl_LenghtUOM_ID();

	String COLUMNNAME_Dhl_LenghtUOM_ID = "Dhl_LenghtUOM_ID";

	/**
	 * Set DHL Shipper Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDHL_Shipper_Config_ID (int DHL_Shipper_Config_ID);

	/**
	 * Get DHL Shipper Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDHL_Shipper_Config_ID();

	ModelColumn<I_DHL_Shipper_Config, Object> COLUMN_DHL_Shipper_Config_ID = new ModelColumn<>(I_DHL_Shipper_Config.class, "DHL_Shipper_Config_ID", null);
	String COLUMNNAME_DHL_Shipper_Config_ID = "DHL_Shipper_Config_ID";

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

	ModelColumn<I_DHL_Shipper_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_DHL_Shipper_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Shipper_ID();

	@Nullable org.compiere.model.I_M_Shipper getM_Shipper();

	void setM_Shipper(@Nullable org.compiere.model.I_M_Shipper M_Shipper);

	ModelColumn<I_DHL_Shipper_Config, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_DHL_Shipper_Config.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Signature.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSignature (@Nullable java.lang.String Signature);

	/**
	 * Get Signature.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSignature();

	ModelColumn<I_DHL_Shipper_Config, Object> COLUMN_Signature = new ModelColumn<>(I_DHL_Shipper_Config.class, "Signature", null);
	String COLUMNNAME_Signature = "Signature";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_DHL_Shipper_Config, Object> COLUMN_Updated = new ModelColumn<>(I_DHL_Shipper_Config.class, "Updated", null);
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

	ModelColumn<I_DHL_Shipper_Config, Object> COLUMN_UserName = new ModelColumn<>(I_DHL_Shipper_Config.class, "UserName", null);
	String COLUMNNAME_UserName = "UserName";
}
