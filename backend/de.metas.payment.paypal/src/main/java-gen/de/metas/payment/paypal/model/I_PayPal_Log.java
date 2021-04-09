package de.metas.payment.paypal.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for PayPal_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PayPal_Log 
{

	String Table_Name = "PayPal_Log";

//	/** AD_Table_ID=541387 */
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
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_ID();

	@Nullable org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(@Nullable org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_PayPal_Log, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_PayPal_Log.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	@Nullable org.compiere.model.I_C_Order getC_Order();

	void setC_Order(@Nullable org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_PayPal_Log, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_PayPal_Log.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

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
	 * Set Payment Reservation Capture.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Payment_Reservation_Capture_ID (int C_Payment_Reservation_Capture_ID);

	/**
	 * Get Payment Reservation Capture.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Payment_Reservation_Capture_ID();

	@Nullable org.compiere.model.I_C_Payment_Reservation_Capture getC_Payment_Reservation_Capture();

	void setC_Payment_Reservation_Capture(@Nullable org.compiere.model.I_C_Payment_Reservation_Capture C_Payment_Reservation_Capture);

	ModelColumn<I_PayPal_Log, org.compiere.model.I_C_Payment_Reservation_Capture> COLUMN_C_Payment_Reservation_Capture_ID = new ModelColumn<>(I_PayPal_Log.class, "C_Payment_Reservation_Capture_ID", org.compiere.model.I_C_Payment_Reservation_Capture.class);
	String COLUMNNAME_C_Payment_Reservation_Capture_ID = "C_Payment_Reservation_Capture_ID";

	/**
	 * Set Payment Reservation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Payment_Reservation_ID (int C_Payment_Reservation_ID);

	/**
	 * Get Payment Reservation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Payment_Reservation_ID();

	@Nullable org.compiere.model.I_C_Payment_Reservation getC_Payment_Reservation();

	void setC_Payment_Reservation(@Nullable org.compiere.model.I_C_Payment_Reservation C_Payment_Reservation);

	ModelColumn<I_PayPal_Log, org.compiere.model.I_C_Payment_Reservation> COLUMN_C_Payment_Reservation_ID = new ModelColumn<>(I_PayPal_Log.class, "C_Payment_Reservation_ID", org.compiere.model.I_C_Payment_Reservation.class);
	String COLUMNNAME_C_Payment_Reservation_ID = "C_Payment_Reservation_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_PayPal_Log, Object> COLUMN_Created = new ModelColumn<>(I_PayPal_Log.class, "Created", null);
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

	ModelColumn<I_PayPal_Log, Object> COLUMN_IsActive = new ModelColumn<>(I_PayPal_Log.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set PayPal Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPayPal_Log_ID (int PayPal_Log_ID);

	/**
	 * Get PayPal Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPayPal_Log_ID();

	ModelColumn<I_PayPal_Log, Object> COLUMN_PayPal_Log_ID = new ModelColumn<>(I_PayPal_Log.class, "PayPal_Log_ID", null);
	String COLUMNNAME_PayPal_Log_ID = "PayPal_Log_ID";

	/**
	 * Set PayPal Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPayPal_Order_ID (int PayPal_Order_ID);

	/**
	 * Get PayPal Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPayPal_Order_ID();

	@Nullable de.metas.payment.paypal.model.I_PayPal_Order getPayPal_Order();

	void setPayPal_Order(@Nullable de.metas.payment.paypal.model.I_PayPal_Order PayPal_Order);

	ModelColumn<I_PayPal_Log, de.metas.payment.paypal.model.I_PayPal_Order> COLUMN_PayPal_Order_ID = new ModelColumn<>(I_PayPal_Log.class, "PayPal_Order_ID", de.metas.payment.paypal.model.I_PayPal_Order.class);
	String COLUMNNAME_PayPal_Order_ID = "PayPal_Order_ID";

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

	ModelColumn<I_PayPal_Log, Object> COLUMN_RequestBody = new ModelColumn<>(I_PayPal_Log.class, "RequestBody", null);
	String COLUMNNAME_RequestBody = "RequestBody";

	/**
	 * Set Request Headers.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRequestHeaders (@Nullable java.lang.String RequestHeaders);

	/**
	 * Get Request Headers.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRequestHeaders();

	ModelColumn<I_PayPal_Log, Object> COLUMN_RequestHeaders = new ModelColumn<>(I_PayPal_Log.class, "RequestHeaders", null);
	String COLUMNNAME_RequestHeaders = "RequestHeaders";

	/**
	 * Set Request Method.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRequestMethod (@Nullable java.lang.String RequestMethod);

	/**
	 * Get Request Method.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRequestMethod();

	ModelColumn<I_PayPal_Log, Object> COLUMN_RequestMethod = new ModelColumn<>(I_PayPal_Log.class, "RequestMethod", null);
	String COLUMNNAME_RequestMethod = "RequestMethod";

	/**
	 * Set Request Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRequestPath (@Nullable java.lang.String RequestPath);

	/**
	 * Get Request Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRequestPath();

	ModelColumn<I_PayPal_Log, Object> COLUMN_RequestPath = new ModelColumn<>(I_PayPal_Log.class, "RequestPath", null);
	String COLUMNNAME_RequestPath = "RequestPath";

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

	ModelColumn<I_PayPal_Log, Object> COLUMN_ResponseBody = new ModelColumn<>(I_PayPal_Log.class, "ResponseBody", null);
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

	ModelColumn<I_PayPal_Log, Object> COLUMN_ResponseCode = new ModelColumn<>(I_PayPal_Log.class, "ResponseCode", null);
	String COLUMNNAME_ResponseCode = "ResponseCode";

	/**
	 * Set Response Headers.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setResponseHeaders (@Nullable java.lang.String ResponseHeaders);

	/**
	 * Get Response Headers.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getResponseHeaders();

	ModelColumn<I_PayPal_Log, Object> COLUMN_ResponseHeaders = new ModelColumn<>(I_PayPal_Log.class, "ResponseHeaders", null);
	String COLUMNNAME_ResponseHeaders = "ResponseHeaders";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PayPal_Log, Object> COLUMN_Updated = new ModelColumn<>(I_PayPal_Log.class, "Updated", null);
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
