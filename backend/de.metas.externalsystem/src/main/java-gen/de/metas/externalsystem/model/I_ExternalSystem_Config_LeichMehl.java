package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for ExternalSystem_Config_LeichMehl
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_LeichMehl
{

	String Table_Name = "ExternalSystem_Config_LeichMehl";

	//	/** AD_Table_ID=542129 */
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

	ModelColumn<I_ExternalSystem_Config_LeichMehl, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "Created", null);
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
	 * Set External System Config.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_ID (int ExternalSystem_Config_ID);

	/**
	 * Get External System Config.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_ID();

	de.metas.externalsystem.model.I_ExternalSystem_Config getExternalSystem_Config();

	void setExternalSystem_Config(de.metas.externalsystem.model.I_ExternalSystem_Config ExternalSystem_Config);

	ModelColumn<I_ExternalSystem_Config_LeichMehl, de.metas.externalsystem.model.I_ExternalSystem_Config> COLUMN_ExternalSystem_Config_ID = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "ExternalSystem_Config_ID", de.metas.externalsystem.model.I_ExternalSystem_Config.class);
	String COLUMNNAME_ExternalSystem_Config_ID = "ExternalSystem_Config_ID";

	/**
	 * Set Leich + Mehl.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_LeichMehl_ID (int ExternalSystem_Config_LeichMehl_ID);

	/**
	 * Get Leich + Mehl.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_LeichMehl_ID();

	ModelColumn<I_ExternalSystem_Config_LeichMehl, Object> COLUMN_ExternalSystem_Config_LeichMehl_ID = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "ExternalSystem_Config_LeichMehl_ID", null);
	String COLUMNNAME_ExternalSystem_Config_LeichMehl_ID = "ExternalSystem_Config_LeichMehl_ID";

	/**
	 * Set Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystemValue (java.lang.String ExternalSystemValue);

	/**
	 * Get Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getExternalSystemValue();

	ModelColumn<I_ExternalSystem_Config_LeichMehl, Object> COLUMN_ExternalSystemValue = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "ExternalSystemValue", null);
	String COLUMNNAME_ExternalSystemValue = "ExternalSystemValue";

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

	ModelColumn<I_ExternalSystem_Config_LeichMehl, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Enable PLU-file export audit.
	 * If enabled, then all the changes done during the PLU-file export will be stored also in metasfresh i.e. the replaced keys from the PLU-file
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPluFileExportAuditEnabled (boolean IsPluFileExportAuditEnabled);

	/**
	 * Get Enable PLU-file export audit.
	 * If enabled, then all the changes done during the PLU-file export will be stored also in metasfresh i.e. the replaced keys from the PLU-file
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPluFileExportAuditEnabled();

	ModelColumn<I_ExternalSystem_Config_LeichMehl, Object> COLUMN_IsPluFileExportAuditEnabled = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "IsPluFileExportAuditEnabled", null);
	String COLUMNNAME_IsPluFileExportAuditEnabled = "IsPluFileExportAuditEnabled";

	/**
	 * Set Product directory.
	 * Root directly that contains all PLU Files
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProduct_BaseFolderName (java.lang.String Product_BaseFolderName);

	/**
	 * Get Product directory.
	 * Root directly that contains all PLU Files
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getProduct_BaseFolderName();

	ModelColumn<I_ExternalSystem_Config_LeichMehl, Object> COLUMN_Product_BaseFolderName = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "Product_BaseFolderName", null);
	String COLUMNNAME_Product_BaseFolderName = "Product_BaseFolderName";

	/**
	 * Set LANScale Address.
	 * IP-address or hostname of the Leich + Mehl device
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTCP_Host (java.lang.String TCP_Host);

	/**
	 * Get LANScale Address.
	 * IP-address or hostname of the Leich + Mehl device
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTCP_Host();

	ModelColumn<I_ExternalSystem_Config_LeichMehl, Object> COLUMN_TCP_Host = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "TCP_Host", null);
	String COLUMNNAME_TCP_Host = "TCP_Host";

	/**
	 * Set LANScale port.
	 * Port number of the Leich + Mehl device
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTCP_PortNumber (int TCP_PortNumber);

	/**
	 * Get LANScale port.
	 * Port number of the Leich + Mehl device
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getTCP_PortNumber();

	ModelColumn<I_ExternalSystem_Config_LeichMehl, Object> COLUMN_TCP_PortNumber = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "TCP_PortNumber", null);
	String COLUMNNAME_TCP_PortNumber = "TCP_PortNumber";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Config_LeichMehl, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "Updated", null);
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
