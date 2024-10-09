package de.metas.payment.sumup.repository.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for SUMUP_Transaction
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_SUMUP_Transaction 
{

	String Table_Name = "SUMUP_Transaction";

//	/** AD_Table_ID=542443 */
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
	 * Set Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAmount (BigDecimal Amount);

	/**
	 * Get Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmount();

	ModelColumn<I_SUMUP_Transaction, Object> COLUMN_Amount = new ModelColumn<>(I_SUMUP_Transaction.class, "Amount", null);
	String COLUMNNAME_Amount = "Amount";

	/**
	 * Set Card Last 4 Digits.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCardLast4Digits (@Nullable java.lang.String CardLast4Digits);

	/**
	 * Get Card Last 4 Digits.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCardLast4Digits();

	ModelColumn<I_SUMUP_Transaction, Object> COLUMN_CardLast4Digits = new ModelColumn<>(I_SUMUP_Transaction.class, "CardLast4Digits", null);
	String COLUMNNAME_CardLast4Digits = "CardLast4Digits";

	/**
	 * Set Card Type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCardType (@Nullable java.lang.String CardType);

	/**
	 * Get Card Type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCardType();

	ModelColumn<I_SUMUP_Transaction, Object> COLUMN_CardType = new ModelColumn<>(I_SUMUP_Transaction.class, "CardType", null);
	String COLUMNNAME_CardType = "CardType";

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

	ModelColumn<I_SUMUP_Transaction, Object> COLUMN_C_POS_Order_ID = new ModelColumn<>(I_SUMUP_Transaction.class, "C_POS_Order_ID", null);
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

	ModelColumn<I_SUMUP_Transaction, Object> COLUMN_C_POS_Payment_ID = new ModelColumn<>(I_SUMUP_Transaction.class, "C_POS_Payment_ID", null);
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

	ModelColumn<I_SUMUP_Transaction, Object> COLUMN_Created = new ModelColumn<>(I_SUMUP_Transaction.class, "Created", null);
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
	 * Set Currency Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCurrencyCode (java.lang.String CurrencyCode);

	/**
	 * Get Currency Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getCurrencyCode();

	ModelColumn<I_SUMUP_Transaction, Object> COLUMN_CurrencyCode = new ModelColumn<>(I_SUMUP_Transaction.class, "CurrencyCode", null);
	String COLUMNNAME_CurrencyCode = "CurrencyCode";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalId (java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getExternalId();

	ModelColumn<I_SUMUP_Transaction, Object> COLUMN_ExternalId = new ModelColumn<>(I_SUMUP_Transaction.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

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

	ModelColumn<I_SUMUP_Transaction, Object> COLUMN_IsActive = new ModelColumn<>(I_SUMUP_Transaction.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set JSON Response.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setJsonResponse (@Nullable java.lang.String JsonResponse);

	/**
	 * Get JSON Response.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getJsonResponse();

	ModelColumn<I_SUMUP_Transaction, Object> COLUMN_JsonResponse = new ModelColumn<>(I_SUMUP_Transaction.class, "JsonResponse", null);
	String COLUMNNAME_JsonResponse = "JsonResponse";

	/**
	 * Set Refund amount.
	 * Refund amount per product unit
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRefundAmt (BigDecimal RefundAmt);

	/**
	 * Get Refund amount.
	 * Refund amount per product unit
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getRefundAmt();

	ModelColumn<I_SUMUP_Transaction, Object> COLUMN_RefundAmt = new ModelColumn<>(I_SUMUP_Transaction.class, "RefundAmt", null);
	String COLUMNNAME_RefundAmt = "RefundAmt";

	/**
	 * Set Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStatus (java.lang.String Status);

	/**
	 * Get Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getStatus();

	ModelColumn<I_SUMUP_Transaction, Object> COLUMN_Status = new ModelColumn<>(I_SUMUP_Transaction.class, "Status", null);
	String COLUMNNAME_Status = "Status";

	/**
	 * Set Client Transaction Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSUMUP_ClientTransactionId (java.lang.String SUMUP_ClientTransactionId);

	/**
	 * Get Client Transaction Id.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getSUMUP_ClientTransactionId();

	ModelColumn<I_SUMUP_Transaction, Object> COLUMN_SUMUP_ClientTransactionId = new ModelColumn<>(I_SUMUP_Transaction.class, "SUMUP_ClientTransactionId", null);
	String COLUMNNAME_SUMUP_ClientTransactionId = "SUMUP_ClientTransactionId";

	/**
	 * Set SumUp Configuration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSUMUP_Config_ID (int SUMUP_Config_ID);

	/**
	 * Get SumUp Configuration.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSUMUP_Config_ID();

	de.metas.payment.sumup.repository.model.I_SUMUP_Config getSUMUP_Config();

	void setSUMUP_Config(de.metas.payment.sumup.repository.model.I_SUMUP_Config SUMUP_Config);

	ModelColumn<I_SUMUP_Transaction, de.metas.payment.sumup.repository.model.I_SUMUP_Config> COLUMN_SUMUP_Config_ID = new ModelColumn<>(I_SUMUP_Transaction.class, "SUMUP_Config_ID", de.metas.payment.sumup.repository.model.I_SUMUP_Config.class);
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

	ModelColumn<I_SUMUP_Transaction, Object> COLUMN_SUMUP_merchant_code = new ModelColumn<>(I_SUMUP_Transaction.class, "SUMUP_merchant_code", null);
	String COLUMNNAME_SUMUP_merchant_code = "SUMUP_merchant_code";

	/**
	 * Set SumUp Transaction.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSUMUP_Transaction_ID (int SUMUP_Transaction_ID);

	/**
	 * Get SumUp Transaction.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSUMUP_Transaction_ID();

	ModelColumn<I_SUMUP_Transaction, Object> COLUMN_SUMUP_Transaction_ID = new ModelColumn<>(I_SUMUP_Transaction.class, "SUMUP_Transaction_ID", null);
	String COLUMNNAME_SUMUP_Transaction_ID = "SUMUP_Transaction_ID";

	/**
	 * Set Timestamp.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTimestamp (java.sql.Timestamp Timestamp);

	/**
	 * Get Timestamp.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getTimestamp();

	ModelColumn<I_SUMUP_Transaction, Object> COLUMN_Timestamp = new ModelColumn<>(I_SUMUP_Transaction.class, "Timestamp", null);
	String COLUMNNAME_Timestamp = "Timestamp";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_SUMUP_Transaction, Object> COLUMN_Updated = new ModelColumn<>(I_SUMUP_Transaction.class, "Updated", null);
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
