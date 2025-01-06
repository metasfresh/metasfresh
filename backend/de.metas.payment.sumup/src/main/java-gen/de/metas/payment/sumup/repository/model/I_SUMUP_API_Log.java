package de.metas.payment.sumup.repository.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for SUMUP_API_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_SUMUP_API_Log 
{

	String Table_Name = "SUMUP_API_Log";

//	/** AD_Table_ID=542442 */
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
	 * Set Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Issue_ID();

	String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

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
	 * Set POS Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_POS_Order_ID (int C_POS_Order_ID);

	/**
	 * Get POS Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_POS_Order_ID();

	ModelColumn<I_SUMUP_API_Log, Object> COLUMN_C_POS_Order_ID = new ModelColumn<>(I_SUMUP_API_Log.class, "C_POS_Order_ID", null);
	String COLUMNNAME_C_POS_Order_ID = "C_POS_Order_ID";

	/**
	 * Set POS Payment.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_POS_Payment_ID (int C_POS_Payment_ID);

	/**
	 * Get POS Payment.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_POS_Payment_ID();

	ModelColumn<I_SUMUP_API_Log, Object> COLUMN_C_POS_Payment_ID = new ModelColumn<>(I_SUMUP_API_Log.class, "C_POS_Payment_ID", null);
	String COLUMNNAME_C_POS_Payment_ID = "C_POS_Payment_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_SUMUP_API_Log, Object> COLUMN_Created = new ModelColumn<>(I_SUMUP_API_Log.class, "Created", null);
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

	ModelColumn<I_SUMUP_API_Log, Object> COLUMN_IsActive = new ModelColumn<>(I_SUMUP_API_Log.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Method.
	 * HTTP method matched by this line. An empty value matches all methods.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMethod (@Nullable java.lang.String Method);

	/**
	 * Get Method.
	 * HTTP method matched by this line. An empty value matches all methods.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMethod();

	ModelColumn<I_SUMUP_API_Log, Object> COLUMN_Method = new ModelColumn<>(I_SUMUP_API_Log.class, "Method", null);
	String COLUMNNAME_Method = "Method";

	/**
	 * Set Request Body.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRequestBody (@Nullable java.lang.String RequestBody);

	/**
	 * Get Request Body.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRequestBody();

	ModelColumn<I_SUMUP_API_Log, Object> COLUMN_RequestBody = new ModelColumn<>(I_SUMUP_API_Log.class, "RequestBody", null);
	String COLUMNNAME_RequestBody = "RequestBody";

	/**
	 * Set URI.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRequestURI (@Nullable java.lang.String RequestURI);

	/**
	 * Get URI.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRequestURI();

	ModelColumn<I_SUMUP_API_Log, Object> COLUMN_RequestURI = new ModelColumn<>(I_SUMUP_API_Log.class, "RequestURI", null);
	String COLUMNNAME_RequestURI = "RequestURI";

	/**
	 * Set Response Body.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setResponseBody (@Nullable java.lang.String ResponseBody);

	/**
	 * Get Response Body.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getResponseBody();

	ModelColumn<I_SUMUP_API_Log, Object> COLUMN_ResponseBody = new ModelColumn<>(I_SUMUP_API_Log.class, "ResponseBody", null);
	String COLUMNNAME_ResponseBody = "ResponseBody";

	/**
	 * Set Response .
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setResponseCode (int ResponseCode);

	/**
	 * Get Response .
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getResponseCode();

	ModelColumn<I_SUMUP_API_Log, Object> COLUMN_ResponseCode = new ModelColumn<>(I_SUMUP_API_Log.class, "ResponseCode", null);
	String COLUMNNAME_ResponseCode = "ResponseCode";

	/**
	 * Set SumUp API Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSUMUP_API_Log_ID (int SUMUP_API_Log_ID);

	/**
	 * Get SumUp API Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSUMUP_API_Log_ID();

	ModelColumn<I_SUMUP_API_Log, Object> COLUMN_SUMUP_API_Log_ID = new ModelColumn<>(I_SUMUP_API_Log.class, "SUMUP_API_Log_ID", null);
	String COLUMNNAME_SUMUP_API_Log_ID = "SUMUP_API_Log_ID";

	/**
	 * Set SumUp Configuration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSUMUP_Config_ID (int SUMUP_Config_ID);

	/**
	 * Get SumUp Configuration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSUMUP_Config_ID();

	@Nullable de.metas.payment.sumup.repository.model.I_SUMUP_Config getSUMUP_Config();

	void setSUMUP_Config(@Nullable de.metas.payment.sumup.repository.model.I_SUMUP_Config SUMUP_Config);

	ModelColumn<I_SUMUP_API_Log, de.metas.payment.sumup.repository.model.I_SUMUP_Config> COLUMN_SUMUP_Config_ID = new ModelColumn<>(I_SUMUP_API_Log.class, "SUMUP_Config_ID", de.metas.payment.sumup.repository.model.I_SUMUP_Config.class);
	String COLUMNNAME_SUMUP_Config_ID = "SUMUP_Config_ID";

	/**
	 * Set Merchant Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSUMUP_merchant_code (@Nullable java.lang.String SUMUP_merchant_code);

	/**
	 * Get Merchant Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSUMUP_merchant_code();

	ModelColumn<I_SUMUP_API_Log, Object> COLUMN_SUMUP_merchant_code = new ModelColumn<>(I_SUMUP_API_Log.class, "SUMUP_merchant_code", null);
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

	ModelColumn<I_SUMUP_API_Log, Object> COLUMN_Updated = new ModelColumn<>(I_SUMUP_API_Log.class, "Updated", null);
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
