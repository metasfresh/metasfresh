package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for ExternalSystem_Config_ProCareManagement_LocalFile
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_ProCareManagement_LocalFile 
{

	String Table_Name = "ExternalSystem_Config_ProCareManagement_LocalFile";

//	/** AD_Table_ID=542401 */
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
	 * Set Business Partner File Name Pattern.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartnerFileNamePattern (@Nullable String BPartnerFileNamePattern);

	/**
	 * Get Business Partner File Name Pattern.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getBPartnerFileNamePattern();

	ModelColumn<I_ExternalSystem_Config_ProCareManagement_LocalFile, Object> COLUMN_BPartnerFileNamePattern = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement_LocalFile.class, "BPartnerFileNamePattern", null);
	String COLUMNNAME_BPartnerFileNamePattern = "BPartnerFileNamePattern";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ExternalSystem_Config_ProCareManagement_LocalFile, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement_LocalFile.class, "Created", null);
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
	 * Set Errored Directory.
	 * Defines where files should be moved after attempting to process them with error.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setErroredDirectory (String ErroredDirectory);

	/**
	 * Get Errored Directory.
	 * Defines where files should be moved after attempting to process them with error.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getErroredDirectory();

	ModelColumn<I_ExternalSystem_Config_ProCareManagement_LocalFile, Object> COLUMN_ErroredDirectory = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement_LocalFile.class, "ErroredDirectory", null);
	String COLUMNNAME_ErroredDirectory = "ErroredDirectory";

	/**
	 * Set Pro Care Management.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_ProCareManagement_ID (int ExternalSystem_Config_ProCareManagement_ID);

	/**
	 * Get Pro Care Management.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_ProCareManagement_ID();

	I_ExternalSystem_Config_ProCareManagement getExternalSystem_Config_ProCareManagement();

	void setExternalSystem_Config_ProCareManagement(I_ExternalSystem_Config_ProCareManagement ExternalSystem_Config_ProCareManagement);

	ModelColumn<I_ExternalSystem_Config_ProCareManagement_LocalFile, I_ExternalSystem_Config_ProCareManagement> COLUMN_ExternalSystem_Config_ProCareManagement_ID = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement_LocalFile.class, "ExternalSystem_Config_ProCareManagement_ID", I_ExternalSystem_Config_ProCareManagement.class);
	String COLUMNNAME_ExternalSystem_Config_ProCareManagement_ID = "ExternalSystem_Config_ProCareManagement_ID";

	/**
	 * Set Local File.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_ProCareManagement_LocalFile_ID (int ExternalSystem_Config_ProCareManagement_LocalFile_ID);

	/**
	 * Get Local File.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_ProCareManagement_LocalFile_ID();

	ModelColumn<I_ExternalSystem_Config_ProCareManagement_LocalFile, Object> COLUMN_ExternalSystem_Config_ProCareManagement_LocalFile_ID = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement_LocalFile.class, "ExternalSystem_Config_ProCareManagement_LocalFile_ID", null);
	String COLUMNNAME_ExternalSystem_Config_ProCareManagement_LocalFile_ID = "ExternalSystem_Config_ProCareManagement_LocalFile_ID";

	/**
	 * Set Frequency.
	 * Frequency of events
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFrequency (int Frequency);

	/**
	 * Get Frequency.
	 * Frequency of events
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getFrequency();

	ModelColumn<I_ExternalSystem_Config_ProCareManagement_LocalFile, Object> COLUMN_Frequency = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement_LocalFile.class, "Frequency", null);
	String COLUMNNAME_Frequency = "Frequency";

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

	ModelColumn<I_ExternalSystem_Config_ProCareManagement_LocalFile, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement_LocalFile.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Local Root Location.
	 * Local machine root location
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLocalRootLocation (String LocalRootLocation);

	/**
	 * Get Local Root Location.
	 * Local machine root location
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getLocalRootLocation();

	ModelColumn<I_ExternalSystem_Config_ProCareManagement_LocalFile, Object> COLUMN_LocalRootLocation = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement_LocalFile.class, "LocalRootLocation", null);
	String COLUMNNAME_LocalRootLocation = "LocalRootLocation";

	/**
	 * Set Processed Directory.
	 * Defines where files should be moved after being successfully processed.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessedDirectory (String ProcessedDirectory);

	/**
	 * Get Processed Directory.
	 * Defines where files should be moved after being successfully processed.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getProcessedDirectory();

	ModelColumn<I_ExternalSystem_Config_ProCareManagement_LocalFile, Object> COLUMN_ProcessedDirectory = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement_LocalFile.class, "ProcessedDirectory", null);
	String COLUMNNAME_ProcessedDirectory = "ProcessedDirectory";

	/**
	 * Set Product File Name Pattern.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductFileNamePattern (@Nullable String ProductFileNamePattern);

	/**
	 * Get Product File Name Pattern.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getProductFileNamePattern();

	ModelColumn<I_ExternalSystem_Config_ProCareManagement_LocalFile, Object> COLUMN_ProductFileNamePattern = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement_LocalFile.class, "ProductFileNamePattern", null);
	String COLUMNNAME_ProductFileNamePattern = "ProductFileNamePattern";

	/**
	 * Set Purchase Order File Name Pattern.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPurchaseOrderFileNamePattern (@Nullable String PurchaseOrderFileNamePattern);

	/**
	 * Get Purchase Order File Name Pattern.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getPurchaseOrderFileNamePattern();

	ModelColumn<I_ExternalSystem_Config_ProCareManagement_LocalFile, Object> COLUMN_PurchaseOrderFileNamePattern = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement_LocalFile.class, "PurchaseOrderFileNamePattern", null);
	String COLUMNNAME_PurchaseOrderFileNamePattern = "PurchaseOrderFileNamePattern";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Config_ProCareManagement_LocalFile, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement_LocalFile.class, "Updated", null);
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
	 * Set Warehouse File Name Pattern.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWarehouseFileNamePattern (@Nullable String WarehouseFileNamePattern);

	/**
	 * Get Warehouse File Name Pattern.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getWarehouseFileNamePattern();

	ModelColumn<I_ExternalSystem_Config_ProCareManagement_LocalFile, Object> COLUMN_WarehouseFileNamePattern = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement_LocalFile.class, "WarehouseFileNamePattern", null);
	String COLUMNNAME_WarehouseFileNamePattern = "WarehouseFileNamePattern";
}
