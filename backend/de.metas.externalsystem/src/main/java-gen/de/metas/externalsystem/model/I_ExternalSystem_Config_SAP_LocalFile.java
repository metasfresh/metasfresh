package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for ExternalSystem_Config_SAP_LocalFile
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_SAP_LocalFile 
{

	String Table_Name = "ExternalSystem_Config_SAP_LocalFile";

//	/** AD_Table_ID=542258 */
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
	 * Set Approved By.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setApprovedBy_ID (int ApprovedBy_ID);

	/**
	 * Get Approved By.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getApprovedBy_ID();

	String COLUMNNAME_ApprovedBy_ID = "ApprovedBy_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ExternalSystem_Config_SAP_LocalFile, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_SAP_LocalFile.class, "Created", null);
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
	 * Defines where files should be moved after being processed with error. (The path should be relative to the current target location)
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setErroredDirectory (java.lang.String ErroredDirectory);

	/**
	 * Get Errored Directory.
	 * Defines where files should be moved after being processed with error. (The path should be relative to the current target location)
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getErroredDirectory();

	ModelColumn<I_ExternalSystem_Config_SAP_LocalFile, Object> COLUMN_ErroredDirectory = new ModelColumn<>(I_ExternalSystem_Config_SAP_LocalFile.class, "ErroredDirectory", null);
	String COLUMNNAME_ErroredDirectory = "ErroredDirectory";

	/**
	 * Set ExternalSystem_Config_SAP.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_SAP_ID (int ExternalSystem_Config_SAP_ID);

	/**
	 * Get ExternalSystem_Config_SAP.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_SAP_ID();

	de.metas.externalsystem.model.I_ExternalSystem_Config_SAP getExternalSystem_Config_SAP();

	void setExternalSystem_Config_SAP(de.metas.externalsystem.model.I_ExternalSystem_Config_SAP ExternalSystem_Config_SAP);

	ModelColumn<I_ExternalSystem_Config_SAP_LocalFile, de.metas.externalsystem.model.I_ExternalSystem_Config_SAP> COLUMN_ExternalSystem_Config_SAP_ID = new ModelColumn<>(I_ExternalSystem_Config_SAP_LocalFile.class, "ExternalSystem_Config_SAP_ID", de.metas.externalsystem.model.I_ExternalSystem_Config_SAP.class);
	String COLUMNNAME_ExternalSystem_Config_SAP_ID = "ExternalSystem_Config_SAP_ID";

	/**
	 * Set External System Config SAP Local File.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_SAP_LocalFile_ID (int ExternalSystem_Config_SAP_LocalFile_ID);

	/**
	 * Get External System Config SAP Local File.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_SAP_LocalFile_ID();

	ModelColumn<I_ExternalSystem_Config_SAP_LocalFile, Object> COLUMN_ExternalSystem_Config_SAP_LocalFile_ID = new ModelColumn<>(I_ExternalSystem_Config_SAP_LocalFile.class, "ExternalSystem_Config_SAP_LocalFile_ID", null);
	String COLUMNNAME_ExternalSystem_Config_SAP_LocalFile_ID = "ExternalSystem_Config_SAP_LocalFile_ID";

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

	ModelColumn<I_ExternalSystem_Config_SAP_LocalFile, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_SAP_LocalFile.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Local Root Location.
	 * Local machine root location.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLocal_Root_Location (java.lang.String Local_Root_Location);

	/**
	 * Get Local Root Location.
	 * Local machine root location.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getLocal_Root_Location();

	ModelColumn<I_ExternalSystem_Config_SAP_LocalFile, Object> COLUMN_Local_Root_Location = new ModelColumn<>(I_ExternalSystem_Config_SAP_LocalFile.class, "Local_Root_Location", null);
	String COLUMNNAME_Local_Root_Location = "Local_Root_Location";

	/**
	 * Set Business Partner File Name Pattern.
	 * Ant-style pattern used to identify business partner files on the local machine. (If not set, all files are considered)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLocalFile_BPartner_FileName_Pattern (@Nullable java.lang.String LocalFile_BPartner_FileName_Pattern);

	/**
	 * Get Business Partner File Name Pattern.
	 * Ant-style pattern used to identify business partner files on the local machine. (If not set, all files are considered)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLocalFile_BPartner_FileName_Pattern();

	ModelColumn<I_ExternalSystem_Config_SAP_LocalFile, Object> COLUMN_LocalFile_BPartner_FileName_Pattern = new ModelColumn<>(I_ExternalSystem_Config_SAP_LocalFile.class, "LocalFile_BPartner_FileName_Pattern", null);
	String COLUMNNAME_LocalFile_BPartner_FileName_Pattern = "LocalFile_BPartner_FileName_Pattern";

	/**
	 * Set Business Partner Target Directory.
	 * Directory used to pull business partners from the local machine. (If not set, the files will be pulled from the configured local root location)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLocalFile_BPartner_TargetDirectory (@Nullable java.lang.String LocalFile_BPartner_TargetDirectory);

	/**
	 * Get Business Partner Target Directory.
	 * Directory used to pull business partners from the local machine. (If not set, the files will be pulled from the configured local root location)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLocalFile_BPartner_TargetDirectory();

	ModelColumn<I_ExternalSystem_Config_SAP_LocalFile, Object> COLUMN_LocalFile_BPartner_TargetDirectory = new ModelColumn<>(I_ExternalSystem_Config_SAP_LocalFile.class, "LocalFile_BPartner_TargetDirectory", null);
	String COLUMNNAME_LocalFile_BPartner_TargetDirectory = "LocalFile_BPartner_TargetDirectory";

	/**
	 * Set Conversion Rate File Name Pattern.
	 * Ant-style pattern used to identify conversion rate files on the local machine. (If not set, all files are considered)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLocalFile_ConversionRate_FileName_Pattern (@Nullable java.lang.String LocalFile_ConversionRate_FileName_Pattern);

	/**
	 * Get Conversion Rate File Name Pattern.
	 * Ant-style pattern used to identify conversion rate files on the local machine. (If not set, all files are considered)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLocalFile_ConversionRate_FileName_Pattern();

	ModelColumn<I_ExternalSystem_Config_SAP_LocalFile, Object> COLUMN_LocalFile_ConversionRate_FileName_Pattern = new ModelColumn<>(I_ExternalSystem_Config_SAP_LocalFile.class, "LocalFile_ConversionRate_FileName_Pattern", null);
	String COLUMNNAME_LocalFile_ConversionRate_FileName_Pattern = "LocalFile_ConversionRate_FileName_Pattern";

	/**
	 * Set Conversion Rate Target Directory.
	 * Directory used to pull conversion rates from the local machine. (If no value is specified here, the files are pulled from the configured local root location)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLocalFile_ConversionRate_TargetDirectory (@Nullable java.lang.String LocalFile_ConversionRate_TargetDirectory);

	/**
	 * Get Conversion Rate Target Directory.
	 * Directory used to pull conversion rates from the local machine. (If no value is specified here, the files are pulled from the configured local root location)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLocalFile_ConversionRate_TargetDirectory();

	ModelColumn<I_ExternalSystem_Config_SAP_LocalFile, Object> COLUMN_LocalFile_ConversionRate_TargetDirectory = new ModelColumn<>(I_ExternalSystem_Config_SAP_LocalFile.class, "LocalFile_ConversionRate_TargetDirectory", null);
	String COLUMNNAME_LocalFile_ConversionRate_TargetDirectory = "LocalFile_ConversionRate_TargetDirectory";

	/**
	 * Set Credit Limit File Name Pattern.
	 * Ant-style pattern used to identify credit limit files on the local machine. (If not set, all files are considered)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLocalFile_CreditLimit_FileName_Pattern (@Nullable java.lang.String LocalFile_CreditLimit_FileName_Pattern);

	/**
	 * Get Credit Limit File Name Pattern.
	 * Ant-style pattern used to identify credit limit files on the local machine. (If not set, all files are considered)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLocalFile_CreditLimit_FileName_Pattern();

	ModelColumn<I_ExternalSystem_Config_SAP_LocalFile, Object> COLUMN_LocalFile_CreditLimit_FileName_Pattern = new ModelColumn<>(I_ExternalSystem_Config_SAP_LocalFile.class, "LocalFile_CreditLimit_FileName_Pattern", null);
	String COLUMNNAME_LocalFile_CreditLimit_FileName_Pattern = "LocalFile_CreditLimit_FileName_Pattern";

	/**
	 * Set Credit Limit Target Directory.
	 * The directory used to retrieve credit limits from the local machine. (If no value is specified here, the files are pulled from the configured local root location)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLocalFile_CreditLimit_TargetDirectory (@Nullable java.lang.String LocalFile_CreditLimit_TargetDirectory);

	/**
	 * Get Credit Limit Target Directory.
	 * The directory used to retrieve credit limits from the local machine. (If no value is specified here, the files are pulled from the configured local root location)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLocalFile_CreditLimit_TargetDirectory();

	ModelColumn<I_ExternalSystem_Config_SAP_LocalFile, Object> COLUMN_LocalFile_CreditLimit_TargetDirectory = new ModelColumn<>(I_ExternalSystem_Config_SAP_LocalFile.class, "LocalFile_CreditLimit_TargetDirectory", null);
	String COLUMNNAME_LocalFile_CreditLimit_TargetDirectory = "LocalFile_CreditLimit_TargetDirectory";

	/**
	 * Set Product File Name Pattern.
	 * Ant-style pattern used to identify product files on the local machine. (If not set, all files are considered)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLocalFile_Product_FileName_Pattern (@Nullable java.lang.String LocalFile_Product_FileName_Pattern);

	/**
	 * Get Product File Name Pattern.
	 * Ant-style pattern used to identify product files on the local machine. (If not set, all files are considered)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLocalFile_Product_FileName_Pattern();

	ModelColumn<I_ExternalSystem_Config_SAP_LocalFile, Object> COLUMN_LocalFile_Product_FileName_Pattern = new ModelColumn<>(I_ExternalSystem_Config_SAP_LocalFile.class, "LocalFile_Product_FileName_Pattern", null);
	String COLUMNNAME_LocalFile_Product_FileName_Pattern = "LocalFile_Product_FileName_Pattern";

	/**
	 * Set Product Target Directory.
	 * Directory used to pull products from the local machine. (If no value is specified here, the files are pulled from the configured local root location)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLocalFile_Product_TargetDirectory (@Nullable java.lang.String LocalFile_Product_TargetDirectory);

	/**
	 * Get Product Target Directory.
	 * Directory used to pull products from the local machine. (If no value is specified here, the files are pulled from the configured local root location)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLocalFile_Product_TargetDirectory();

	ModelColumn<I_ExternalSystem_Config_SAP_LocalFile, Object> COLUMN_LocalFile_Product_TargetDirectory = new ModelColumn<>(I_ExternalSystem_Config_SAP_LocalFile.class, "LocalFile_Product_TargetDirectory", null);
	String COLUMNNAME_LocalFile_Product_TargetDirectory = "LocalFile_Product_TargetDirectory";

	/**
	 * Set Frequency In Milliseconds.
	 * Defines how frequently should the process poll for new files.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPollingFrequencyInMs (int PollingFrequencyInMs);

	/**
	 * Get Frequency In Milliseconds.
	 * Defines how frequently should the process poll for new files.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPollingFrequencyInMs();

	ModelColumn<I_ExternalSystem_Config_SAP_LocalFile, Object> COLUMN_PollingFrequencyInMs = new ModelColumn<>(I_ExternalSystem_Config_SAP_LocalFile.class, "PollingFrequencyInMs", null);
	String COLUMNNAME_PollingFrequencyInMs = "PollingFrequencyInMs";

	/**
	 * Set Processed Directory.
	 * Defines where files should be moved after being successfully processed. (The path should be relative to the current target location)
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessedDirectory (java.lang.String ProcessedDirectory);

	/**
	 * Get Processed Directory.
	 * Defines where files should be moved after being successfully processed. (The path should be relative to the current target location)
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getProcessedDirectory();

	ModelColumn<I_ExternalSystem_Config_SAP_LocalFile, Object> COLUMN_ProcessedDirectory = new ModelColumn<>(I_ExternalSystem_Config_SAP_LocalFile.class, "ProcessedDirectory", null);
	String COLUMNNAME_ProcessedDirectory = "ProcessedDirectory";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Config_SAP_LocalFile, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_SAP_LocalFile.class, "Updated", null);
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
