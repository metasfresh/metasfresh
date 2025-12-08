package de.metas.pos.repository.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_POS_Payment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_POS_Payment 
{

	String Table_Name = "C_POS_Payment";

//	/** AD_Table_ID=542437 */
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

	ModelColumn<I_C_POS_Payment, Object> COLUMN_Amount = new ModelColumn<>(I_C_POS_Payment.class, "Amount", null);
	String COLUMNNAME_Amount = "Amount";

	/**
	 * Set Tendered Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAmountTendered (BigDecimal AmountTendered);

	/**
	 * Get Tendered Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmountTendered();

	ModelColumn<I_C_POS_Payment, Object> COLUMN_AmountTendered = new ModelColumn<>(I_C_POS_Payment.class, "AmountTendered", null);
	String COLUMNNAME_AmountTendered = "AmountTendered";

	/**
	 * Set Change.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setChangeBackAmount (BigDecimal ChangeBackAmount);

	/**
	 * Get Change.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getChangeBackAmount();

	ModelColumn<I_C_POS_Payment, Object> COLUMN_ChangeBackAmount = new ModelColumn<>(I_C_POS_Payment.class, "ChangeBackAmount", null);
	String COLUMNNAME_ChangeBackAmount = "ChangeBackAmount";

	/**
	 * Set Payment.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Payment_ID (int C_Payment_ID);

	/**
	 * Get Payment.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Payment_ID();

	String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/**
	 * Set POS Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_POS_Order_ID (int C_POS_Order_ID);

	/**
	 * Get POS Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_POS_Order_ID();

	de.metas.pos.repository.model.I_C_POS_Order getC_POS_Order();

	void setC_POS_Order(de.metas.pos.repository.model.I_C_POS_Order C_POS_Order);

	ModelColumn<I_C_POS_Payment, de.metas.pos.repository.model.I_C_POS_Order> COLUMN_C_POS_Order_ID = new ModelColumn<>(I_C_POS_Payment.class, "C_POS_Order_ID", de.metas.pos.repository.model.I_C_POS_Order.class);
	String COLUMNNAME_C_POS_Order_ID = "C_POS_Order_ID";

	/**
	 * Set POS Payment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_POS_Payment_ID (int C_POS_Payment_ID);

	/**
	 * Get POS Payment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_POS_Payment_ID();

	ModelColumn<I_C_POS_Payment, Object> COLUMN_C_POS_Payment_ID = new ModelColumn<>(I_C_POS_Payment.class, "C_POS_Payment_ID", null);
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

	ModelColumn<I_C_POS_Payment, Object> COLUMN_Created = new ModelColumn<>(I_C_POS_Payment.class, "Created", null);
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

	ModelColumn<I_C_POS_Payment, Object> COLUMN_ExternalId = new ModelColumn<>(I_C_POS_Payment.class, "ExternalId", null);
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

	ModelColumn<I_C_POS_Payment, Object> COLUMN_IsActive = new ModelColumn<>(I_C_POS_Payment.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Card Reader External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOS_CardReader_ExternalId (@Nullable java.lang.String POS_CardReader_ExternalId);

	/**
	 * Get Card Reader External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOS_CardReader_ExternalId();

	ModelColumn<I_C_POS_Payment, Object> COLUMN_POS_CardReader_ExternalId = new ModelColumn<>(I_C_POS_Payment.class, "POS_CardReader_ExternalId", null);
	String COLUMNNAME_POS_CardReader_ExternalId = "POS_CardReader_ExternalId";

	/**
	 * Set Card Reader Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOS_CardReader_Name (@Nullable java.lang.String POS_CardReader_Name);

	/**
	 * Get Card Reader Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOS_CardReader_Name();

	ModelColumn<I_C_POS_Payment, Object> COLUMN_POS_CardReader_Name = new ModelColumn<>(I_C_POS_Payment.class, "POS_CardReader_Name", null);
	String COLUMNNAME_POS_CardReader_Name = "POS_CardReader_Name";

	/**
	 * Set POS Payment Method.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPOSPaymentMethod (java.lang.String POSPaymentMethod);

	/**
	 * Get POS Payment Method.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPOSPaymentMethod();

	ModelColumn<I_C_POS_Payment, Object> COLUMN_POSPaymentMethod = new ModelColumn<>(I_C_POS_Payment.class, "POSPaymentMethod", null);
	String COLUMNNAME_POSPaymentMethod = "POSPaymentMethod";

	/**
	 * Set Payment Processing Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPOSPaymentProcessingStatus (java.lang.String POSPaymentProcessingStatus);

	/**
	 * Get Payment Processing Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPOSPaymentProcessingStatus();

	ModelColumn<I_C_POS_Payment, Object> COLUMN_POSPaymentProcessingStatus = new ModelColumn<>(I_C_POS_Payment.class, "POSPaymentProcessingStatus", null);
	String COLUMNNAME_POSPaymentProcessingStatus = "POSPaymentProcessingStatus";

	/**
	 * Set Payment Processing Summary.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOSPaymentProcessingSummary (@Nullable java.lang.String POSPaymentProcessingSummary);

	/**
	 * Get Payment Processing Summary.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOSPaymentProcessingSummary();

	ModelColumn<I_C_POS_Payment, Object> COLUMN_POSPaymentProcessingSummary = new ModelColumn<>(I_C_POS_Payment.class, "POSPaymentProcessingSummary", null);
	String COLUMNNAME_POSPaymentProcessingSummary = "POSPaymentProcessingSummary";

	/**
	 * Set Payment Processing Trx ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOSPaymentProcessing_TrxId (@Nullable java.lang.String POSPaymentProcessing_TrxId);

	/**
	 * Get Payment Processing Trx ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOSPaymentProcessing_TrxId();

	ModelColumn<I_C_POS_Payment, Object> COLUMN_POSPaymentProcessing_TrxId = new ModelColumn<>(I_C_POS_Payment.class, "POSPaymentProcessing_TrxId", null);
	String COLUMNNAME_POSPaymentProcessing_TrxId = "POSPaymentProcessing_TrxId";

	/**
	 * Set Payment Processor.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOSPaymentProcessor (@Nullable java.lang.String POSPaymentProcessor);

	/**
	 * Get Payment Processor.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOSPaymentProcessor();

	ModelColumn<I_C_POS_Payment, Object> COLUMN_POSPaymentProcessor = new ModelColumn<>(I_C_POS_Payment.class, "POSPaymentProcessor", null);
	String COLUMNNAME_POSPaymentProcessor = "POSPaymentProcessor";

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

	ModelColumn<I_C_POS_Payment, Object> COLUMN_SUMUP_Config_ID = new ModelColumn<>(I_C_POS_Payment.class, "SUMUP_Config_ID", null);
	String COLUMNNAME_SUMUP_Config_ID = "SUMUP_Config_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_POS_Payment, Object> COLUMN_Updated = new ModelColumn<>(I_C_POS_Payment.class, "Updated", null);
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
