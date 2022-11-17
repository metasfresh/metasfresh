package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for ExternalSystem_Config_SAP
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_SAP 
{

	String Table_Name = "ExternalSystem_Config_SAP";

//	/** AD_Table_ID=542238 */
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

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "Created", null);
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
	 * Defines where files should be moved after attempting to process them with error, relative to the current sftp target location.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setErroredDirectory (String ErroredDirectory);

	/**
	 * Get Errored Directory.
	 * Defines where files should be moved after attempting to process them with error, relative to the current sftp target location.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getErroredDirectory();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_ErroredDirectory = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "ErroredDirectory", null);
	String COLUMNNAME_ErroredDirectory = "ErroredDirectory";

	/**
	 * Set External System Config.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_ID (int ExternalSystem_Config_ID);

	/**
	 * Get External System Config.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_ID();

	@Nullable I_ExternalSystem_Config getExternalSystem_Config();

	void setExternalSystem_Config(@Nullable I_ExternalSystem_Config ExternalSystem_Config);

	ModelColumn<I_ExternalSystem_Config_SAP, I_ExternalSystem_Config> COLUMN_ExternalSystem_Config_ID = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "ExternalSystem_Config_ID", I_ExternalSystem_Config.class);
	String COLUMNNAME_ExternalSystem_Config_ID = "ExternalSystem_Config_ID";

	/**
	 * Set ExternalSystem_Config_SAP.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_SAP_ID (int ExternalSystem_Config_SAP_ID);

	/**
	 * Get ExternalSystem_Config_SAP.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_SAP_ID();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_ExternalSystem_Config_SAP_ID = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "ExternalSystem_Config_SAP_ID", null);
	String COLUMNNAME_ExternalSystem_Config_SAP_ID = "ExternalSystem_Config_SAP_ID";

	/**
	 * Set Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystemValue (String ExternalSystemValue);

	/**
	 * Get Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getExternalSystemValue();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_ExternalSystemValue = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "ExternalSystemValue", null);
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

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_PollingFrequencyInMs = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "PollingFrequencyInMs", null);
	String COLUMNNAME_PollingFrequencyInMs = "PollingFrequencyInMs";

	/**
	 * Set Processed Directory.
	 * Defines where files should be moved after being successfully processed relative to the current sftp target location.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessedDirectory (String ProcessedDirectory);

	/**
	 * Get Processed Directory.
	 * Defines where files should be moved after being successfully processed relative to the current sftp target location.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getProcessedDirectory();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_ProcessedDirectory = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "ProcessedDirectory", null);
	String COLUMNNAME_ProcessedDirectory = "ProcessedDirectory";

	/**
	 * Set SFTP Business Partner File Name Pattern.
	 * The pattern used for the business partner file names when fetching multiple types of files from the same location.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSFTP_BPartner_FileName_Pattern (@Nullable java.lang.String SFTP_BPartner_FileName_Pattern);

	/**
	 * Get SFTP Business Partner File Name Pattern.
	 * The pattern used for the business partner file names when fetching multiple types of files from the same location.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSFTP_BPartner_FileName_Pattern();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_SFTP_BPartner_FileName_Pattern = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "SFTP_BPartner_FileName_Pattern", null);
	String COLUMNNAME_SFTP_BPartner_FileName_Pattern = "SFTP_BPartner_FileName_Pattern";

	/**
	 * Set SFTP Business Partner Target Directory.
	 * The directory used to pull business partners from the sftp server. (If no value set here, the files will be pulled from the root location of the sftp server.)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSFTP_BPartner_TargetDirectory (@Nullable java.lang.String SFTP_BPartner_TargetDirectory);

	/**
	 * Get SFTP Business Partner Target Directory.
	 * The directory used to pull business partners from the sftp server. (If no value set here, the files will be pulled from the root location of the sftp server.)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSFTP_BPartner_TargetDirectory();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_SFTP_BPartner_TargetDirectory = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "SFTP_BPartner_TargetDirectory", null);
	String COLUMNNAME_SFTP_BPartner_TargetDirectory = "SFTP_BPartner_TargetDirectory";

	/**
	 * Set SFTP Credit Limit File Name Pattern.
	 * Pattern used to find the file from which credit limits are pulled from the SFTP server - the filename must match the given pattern.(If not provided, there is no constraint on the filename)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSFTP_CreditLimit_FileName_Pattern (@Nullable String SFTP_CreditLimit_FileName_Pattern);

	/**
	 * Get SFTP Credit Limit File Name Pattern.
	 * Pattern used to find the file from which credit limits are pulled from the SFTP server - the filename must match the given pattern.(If not provided, there is no constraint on the filename)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getSFTP_CreditLimit_FileName_Pattern();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_SFTP_CreditLimit_FileName_Pattern = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "SFTP_CreditLimit_FileName_Pattern", null);
	String COLUMNNAME_SFTP_CreditLimit_FileName_Pattern = "SFTP_CreditLimit_FileName_Pattern";

	/**
	 * Set SFTP Credit Limit TargetDirectory.
	 * The directory used to retrieve credit limits from the sftp server. (If no value is specified here, the files are pulled from the root directory of the sftp server).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSFTP_CreditLimit_TargetDirectory (@Nullable String SFTP_CreditLimit_TargetDirectory);

	/**
	 * Get SFTP Credit Limit TargetDirectory.
	 * The directory used to retrieve credit limits from the sftp server. (If no value is specified here, the files are pulled from the root directory of the sftp server).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getSFTP_CreditLimit_TargetDirectory();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_SFTP_CreditLimit_TargetDirectory = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "SFTP_CreditLimit_TargetDirectory", null);
	String COLUMNNAME_SFTP_CreditLimit_TargetDirectory = "SFTP_CreditLimit_TargetDirectory";

	/**
	 * Set SFTP Hostname.
	 * The host of the sftp server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSFTP_HostName (String SFTP_HostName);

	/**
	 * Get SFTP Hostname.
	 * The host of the sftp server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getSFTP_HostName();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_SFTP_HostName = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "SFTP_HostName", null);
	String COLUMNNAME_SFTP_HostName = "SFTP_HostName";

	/**
	 * Set SFTP Password.
	 * The password used to authenticate into the sftp server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSFTP_Password (String SFTP_Password);

	/**
	 * Get SFTP Password.
	 * The password used to authenticate into the sftp server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getSFTP_Password();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_SFTP_Password = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "SFTP_Password", null);
	String COLUMNNAME_SFTP_Password = "SFTP_Password";

	/**
	 * Set SFTP Port.
	 * The port of the sftp server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSFTP_Port (String SFTP_Port);

	/**
	 * Get SFTP Port.
	 * The port of the sftp server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getSFTP_Port();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_SFTP_Port = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "SFTP_Port", null);
	String COLUMNNAME_SFTP_Port = "SFTP_Port";

	/**
	 * Set SFTP Product File Name Pattern.
	 * The pattern used for the product file names when fetching multiple types of files from the same location.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSFTP_Product_FileName_Pattern (@Nullable java.lang.String SFTP_Product_FileName_Pattern);

	/**
	 * Get SFTP Product File Name Pattern.
	 * The pattern used for the product file names when fetching multiple types of files from the same location.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSFTP_Product_FileName_Pattern();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_SFTP_Product_FileName_Pattern = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "SFTP_Product_FileName_Pattern", null);
	String COLUMNNAME_SFTP_Product_FileName_Pattern = "SFTP_Product_FileName_Pattern";

	/**
	 * Set SFTP Product Target Directory.
	 * The directory used to pull products from the sftp server. (If no value set here, the files will be pulled from the root location of the sftp server.)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSFTP_Product_TargetDirectory (@Nullable java.lang.String SFTP_Product_TargetDirectory);

	/**
	 * Get SFTP Product Target Directory.
	 * The directory used to pull products from the sftp server. (If no value set here, the files will be pulled from the root location of the sftp server.)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSFTP_Product_TargetDirectory();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_SFTP_Product_TargetDirectory = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "SFTP_Product_TargetDirectory", null);
	String COLUMNNAME_SFTP_Product_TargetDirectory = "SFTP_Product_TargetDirectory";

	/**
	 * Set SFTP Username.
	 * The username used to authenticate into the sftp server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSFTP_Username (String SFTP_Username);

	/**
	 * Get SFTP Username.
	 * The username used to authenticate into the sftp server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getSFTP_Username();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_SFTP_Username = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "SFTP_Username", null);
	String COLUMNNAME_SFTP_Username = "SFTP_Username";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "Updated", null);
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
