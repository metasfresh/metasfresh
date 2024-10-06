package de.metas.payment.sumup.repository.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for SUMUP_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_SUMUP_Config 
{

	String Table_Name = "SUMUP_Config";

//	/** AD_Table_ID=542440 */
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
	 * Set API-Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setApiKey (java.lang.String ApiKey);

	/**
	 * Get API-Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getApiKey();

	ModelColumn<I_SUMUP_Config, Object> COLUMN_ApiKey = new ModelColumn<>(I_SUMUP_Config.class, "ApiKey", null);
	String COLUMNNAME_ApiKey = "ApiKey";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_SUMUP_Config, Object> COLUMN_Created = new ModelColumn<>(I_SUMUP_Config.class, "Created", null);
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

	ModelColumn<I_SUMUP_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_SUMUP_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Card Reader.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSUMUP_CardReader_ID (int SUMUP_CardReader_ID);

	/**
	 * Get Card Reader.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSUMUP_CardReader_ID();

	@Nullable de.metas.payment.sumup.repository.model.I_SUMUP_CardReader getSUMUP_CardReader();

	void setSUMUP_CardReader(@Nullable de.metas.payment.sumup.repository.model.I_SUMUP_CardReader SUMUP_CardReader);

	ModelColumn<I_SUMUP_Config, de.metas.payment.sumup.repository.model.I_SUMUP_CardReader> COLUMN_SUMUP_CardReader_ID = new ModelColumn<>(I_SUMUP_Config.class, "SUMUP_CardReader_ID", de.metas.payment.sumup.repository.model.I_SUMUP_CardReader.class);
	String COLUMNNAME_SUMUP_CardReader_ID = "SUMUP_CardReader_ID";

	/**
	 * Set SumUp Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSUMUP_Config_ID (int SUMUP_Config_ID);

	/**
	 * Get SumUp Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSUMUP_Config_ID();

	ModelColumn<I_SUMUP_Config, Object> COLUMN_SUMUP_Config_ID = new ModelColumn<>(I_SUMUP_Config.class, "SUMUP_Config_ID", null);
	String COLUMNNAME_SUMUP_Config_ID = "SUMUP_Config_ID";

	/**
	 * Set Merchant Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSUMUP_merchant_code (java.lang.String SUMUP_merchant_code);

	/**
	 * Get Merchant Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getSUMUP_merchant_code();

	ModelColumn<I_SUMUP_Config, Object> COLUMN_SUMUP_merchant_code = new ModelColumn<>(I_SUMUP_Config.class, "SUMUP_merchant_code", null);
	String COLUMNNAME_SUMUP_merchant_code = "SUMUP_merchant_code";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_SUMUP_Config, Object> COLUMN_Updated = new ModelColumn<>(I_SUMUP_Config.class, "Updated", null);
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
