package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for VATaxID_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_VATaxID_Config 
{

	String Table_Name = "VATaxID_Config";

//	/** AD_Table_ID=542638 */
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

	ModelColumn<I_VATaxID_Config, Object> COLUMN_Created = new ModelColumn<>(I_VATaxID_Config.class, "Created", null);
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

	ModelColumn<I_VATaxID_Config, Object> COLUMN_CreatedBy = new ModelColumn<>(I_VATaxID_Config.class, "CreatedBy", null);
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

	ModelColumn<I_VATaxID_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_VATaxID_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Format Check Enabled.
	 * Determines whether the local format and check-digit validation of the VAT-ID is performed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsFormatCheckEnabled (boolean IsFormatCheckEnabled);

	/**
	 * Get Format Check Enabled.
	 * Determines whether the local format and check-digit validation of the VAT-ID is performed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isFormatCheckEnabled();

	ModelColumn<I_VATaxID_Config, Object> COLUMN_IsFormatCheckEnabled = new ModelColumn<>(I_VATaxID_Config.class, "IsFormatCheckEnabled", null);
	String COLUMNNAME_IsFormatCheckEnabled = "IsFormatCheckEnabled";

	/**
	 * Set VIES Check Enabled.
	 * Determines whether the VAT-ID is checked online via the VIES service.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsVIESCheckEnabled (boolean IsVIESCheckEnabled);

	/**
	 * Get VIES Check Enabled.
	 * Determines whether the VAT-ID is checked online via the VIES service.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isVIESCheckEnabled();

	ModelColumn<I_VATaxID_Config, Object> COLUMN_IsVIESCheckEnabled = new ModelColumn<>(I_VATaxID_Config.class, "IsVIESCheckEnabled", null);
	String COLUMNNAME_IsVIESCheckEnabled = "IsVIESCheckEnabled";

	/**
	 * Set On Service Unavailable.
	 * Status assumed when the VIES service is unreachable and the last result is older than the configured interval.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnServiceUnavailable (java.lang.String OnServiceUnavailable);

	/**
	 * Get On Service Unavailable.
	 * Status assumed when the VIES service is unreachable and the last result is older than the configured interval.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getOnServiceUnavailable();

	ModelColumn<I_VATaxID_Config, Object> COLUMN_OnServiceUnavailable = new ModelColumn<>(I_VATaxID_Config.class, "OnServiceUnavailable", null);
	String COLUMNNAME_OnServiceUnavailable = "OnServiceUnavailable";

	/**
	 * Set Recheck After (Days).
	 * Number of days a successful check result stays valid before a recheck is triggered.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecheckAfterDays (int RecheckAfterDays);

	/**
	 * Get Recheck After (Days).
	 * Number of days a successful check result stays valid before a recheck is triggered.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecheckAfterDays();

	ModelColumn<I_VATaxID_Config, Object> COLUMN_RecheckAfterDays = new ModelColumn<>(I_VATaxID_Config.class, "RecheckAfterDays", null);
	String COLUMNNAME_RecheckAfterDays = "RecheckAfterDays";

	/**
	 * Set Requester Member State.
	 * Country code of our own VAT-ID, sent as the requester on the VIES request.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRequesterMemberStateCode (@Nullable java.lang.String RequesterMemberStateCode);

	/**
	 * Get Requester Member State.
	 * Country code of our own VAT-ID, sent as the requester on the VIES request.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRequesterMemberStateCode();

	ModelColumn<I_VATaxID_Config, Object> COLUMN_RequesterMemberStateCode = new ModelColumn<>(I_VATaxID_Config.class, "RequesterMemberStateCode", null);
	String COLUMNNAME_RequesterMemberStateCode = "RequesterMemberStateCode";

	/**
	 * Set Requester VAT Number.
	 * Our own VAT number (without the country prefix), sent as the requester on the VIES request.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRequesterNumber (@Nullable java.lang.String RequesterNumber);

	/**
	 * Get Requester VAT Number.
	 * Our own VAT number (without the country prefix), sent as the requester on the VIES request.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRequesterNumber();

	ModelColumn<I_VATaxID_Config, Object> COLUMN_RequesterNumber = new ModelColumn<>(I_VATaxID_Config.class, "RequesterNumber", null);
	String COLUMNNAME_RequesterNumber = "RequesterNumber";

	/**
	 * Set REST API URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRestApiBaseURL (@Nullable java.lang.String RestApiBaseURL);

	/**
	 * Get REST API URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRestApiBaseURL();

	ModelColumn<I_VATaxID_Config, Object> COLUMN_RestApiBaseURL = new ModelColumn<>(I_VATaxID_Config.class, "RestApiBaseURL", null);
	String COLUMNNAME_RestApiBaseURL = "RestApiBaseURL";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_VATaxID_Config, Object> COLUMN_Updated = new ModelColumn<>(I_VATaxID_Config.class, "Updated", null);
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

	ModelColumn<I_VATaxID_Config, Object> COLUMN_UpdatedBy = new ModelColumn<>(I_VATaxID_Config.class, "UpdatedBy", null);
	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set VAT-ID Check Configuration.
	 * Configuration of the VAT-ID check, per organisation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setVATaxID_Config_ID (int VATaxID_Config_ID);

	/**
	 * Get VAT-ID Check Configuration.
	 * Configuration of the VAT-ID check, per organisation.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getVATaxID_Config_ID();

	ModelColumn<I_VATaxID_Config, Object> COLUMN_VATaxID_Config_ID = new ModelColumn<>(I_VATaxID_Config.class, "VATaxID_Config_ID", null);
	String COLUMNNAME_VATaxID_Config_ID = "VATaxID_Config_ID";
}
