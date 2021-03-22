package de.metas.payment.paypal.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for PayPal_Order
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PayPal_Order 
{

	String Table_Name = "PayPal_Order";

//	/** AD_Table_ID=541389 */
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
	 * Set Payment Reservation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Payment_Reservation_ID (int C_Payment_Reservation_ID);

	/**
	 * Get Payment Reservation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Payment_Reservation_ID();

	org.compiere.model.I_C_Payment_Reservation getC_Payment_Reservation();

	void setC_Payment_Reservation(org.compiere.model.I_C_Payment_Reservation C_Payment_Reservation);

	ModelColumn<I_PayPal_Order, org.compiere.model.I_C_Payment_Reservation> COLUMN_C_Payment_Reservation_ID = new ModelColumn<>(I_PayPal_Order.class, "C_Payment_Reservation_ID", org.compiere.model.I_C_Payment_Reservation.class);
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

	ModelColumn<I_PayPal_Order, Object> COLUMN_Created = new ModelColumn<>(I_PayPal_Order.class, "Created", null);
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalId (@Nullable java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalId();

	ModelColumn<I_PayPal_Order, Object> COLUMN_ExternalId = new ModelColumn<>(I_PayPal_Order.class, "ExternalId", null);
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

	ModelColumn<I_PayPal_Order, Object> COLUMN_IsActive = new ModelColumn<>(I_PayPal_Order.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set PayPal AuthorizationId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPayPal_AuthorizationId (@Nullable java.lang.String PayPal_AuthorizationId);

	/**
	 * Get PayPal AuthorizationId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPayPal_AuthorizationId();

	ModelColumn<I_PayPal_Order, Object> COLUMN_PayPal_AuthorizationId = new ModelColumn<>(I_PayPal_Order.class, "PayPal_AuthorizationId", null);
	String COLUMNNAME_PayPal_AuthorizationId = "PayPal_AuthorizationId";

	/**
	 * Set PayPal Order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPayPal_Order_ID (int PayPal_Order_ID);

	/**
	 * Get PayPal Order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPayPal_Order_ID();

	ModelColumn<I_PayPal_Order, Object> COLUMN_PayPal_Order_ID = new ModelColumn<>(I_PayPal_Order.class, "PayPal_Order_ID", null);
	String COLUMNNAME_PayPal_Order_ID = "PayPal_Order_ID";

	/**
	 * Set Order JSON.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPayPal_OrderJSON (@Nullable java.lang.String PayPal_OrderJSON);

	/**
	 * Get Order JSON.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPayPal_OrderJSON();

	ModelColumn<I_PayPal_Order, Object> COLUMN_PayPal_OrderJSON = new ModelColumn<>(I_PayPal_Order.class, "PayPal_OrderJSON", null);
	String COLUMNNAME_PayPal_OrderJSON = "PayPal_OrderJSON";

	/**
	 * Set Payer Approve URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPayPal_PayerApproveUrl (@Nullable java.lang.String PayPal_PayerApproveUrl);

	/**
	 * Get Payer Approve URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPayPal_PayerApproveUrl();

	ModelColumn<I_PayPal_Order, Object> COLUMN_PayPal_PayerApproveUrl = new ModelColumn<>(I_PayPal_Order.class, "PayPal_PayerApproveUrl", null);
	String COLUMNNAME_PayPal_PayerApproveUrl = "PayPal_PayerApproveUrl";

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

	ModelColumn<I_PayPal_Order, Object> COLUMN_Status = new ModelColumn<>(I_PayPal_Order.class, "Status", null);
	String COLUMNNAME_Status = "Status";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PayPal_Order, Object> COLUMN_Updated = new ModelColumn<>(I_PayPal_Order.class, "Updated", null);
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
