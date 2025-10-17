package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_InvoicePaySchedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_InvoicePaySchedule 
{

	String Table_Name = "C_InvoicePaySchedule";

//	/** AD_Table_ID=551 */
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

	ModelColumn<I_C_InvoicePaySchedule, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_C_InvoicePaySchedule.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Invoice Payment Schedule.
	 * Invoice Payment Schedule
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_InvoicePaySchedule_ID (int C_InvoicePaySchedule_ID);

	/**
	 * Get Invoice Payment Schedule.
	 * Invoice Payment Schedule
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_InvoicePaySchedule_ID();

	ModelColumn<I_C_InvoicePaySchedule, Object> COLUMN_C_InvoicePaySchedule_ID = new ModelColumn<>(I_C_InvoicePaySchedule.class, "C_InvoicePaySchedule_ID", null);
	String COLUMNNAME_C_InvoicePaySchedule_ID = "C_InvoicePaySchedule_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	@Nullable org.compiere.model.I_C_Order getC_Order();

	void setC_Order(@Nullable org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_C_InvoicePaySchedule, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_C_InvoicePaySchedule.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Order Payment Schedule.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderPaySchedule_ID (int C_OrderPaySchedule_ID);

	/**
	 * Get Order Payment Schedule.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderPaySchedule_ID();

	@Nullable org.compiere.model.I_C_OrderPaySchedule getC_OrderPaySchedule();

	void setC_OrderPaySchedule(@Nullable org.compiere.model.I_C_OrderPaySchedule C_OrderPaySchedule);

	ModelColumn<I_C_InvoicePaySchedule, org.compiere.model.I_C_OrderPaySchedule> COLUMN_C_OrderPaySchedule_ID = new ModelColumn<>(I_C_InvoicePaySchedule.class, "C_OrderPaySchedule_ID", org.compiere.model.I_C_OrderPaySchedule.class);
	String COLUMNNAME_C_OrderPaySchedule_ID = "C_OrderPaySchedule_ID";

	/**
	 * Set Payment Schedule.
	 * Payment Schedule Template
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_PaySchedule_ID (int C_PaySchedule_ID);

	/**
	 * Get Payment Schedule.
	 * Payment Schedule Template
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_PaySchedule_ID();

	@Nullable org.compiere.model.I_C_PaySchedule getC_PaySchedule();

	void setC_PaySchedule(@Nullable org.compiere.model.I_C_PaySchedule C_PaySchedule);

	ModelColumn<I_C_InvoicePaySchedule, org.compiere.model.I_C_PaySchedule> COLUMN_C_PaySchedule_ID = new ModelColumn<>(I_C_InvoicePaySchedule.class, "C_PaySchedule_ID", org.compiere.model.I_C_PaySchedule.class);
	String COLUMNNAME_C_PaySchedule_ID = "C_PaySchedule_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_InvoicePaySchedule, Object> COLUMN_Created = new ModelColumn<>(I_C_InvoicePaySchedule.class, "Created", null);
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
	 * Set Discount.
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDiscountAmt (BigDecimal DiscountAmt);

	/**
	 * Get Discount.
	 * Calculated amount of discount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscountAmt();

	ModelColumn<I_C_InvoicePaySchedule, Object> COLUMN_DiscountAmt = new ModelColumn<>(I_C_InvoicePaySchedule.class, "DiscountAmt", null);
	String COLUMNNAME_DiscountAmt = "DiscountAmt";

	/**
	 * Set Discount Date.
	 * Last Date for payments with discount
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDiscountDate (java.sql.Timestamp DiscountDate);

	/**
	 * Get Discount Date.
	 * Last Date for payments with discount
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDiscountDate();

	ModelColumn<I_C_InvoicePaySchedule, Object> COLUMN_DiscountDate = new ModelColumn<>(I_C_InvoicePaySchedule.class, "DiscountDate", null);
	String COLUMNNAME_DiscountDate = "DiscountDate";

	/**
	 * Set Amount due.
	 * Amount of the payment due
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDueAmt (BigDecimal DueAmt);

	/**
	 * Get Amount due.
	 * Amount of the payment due
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getDueAmt();

	ModelColumn<I_C_InvoicePaySchedule, Object> COLUMN_DueAmt = new ModelColumn<>(I_C_InvoicePaySchedule.class, "DueAmt", null);
	String COLUMNNAME_DueAmt = "DueAmt";

	/**
	 * Set Due Date.
	 * Date when the payment is due
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDueDate (java.sql.Timestamp DueDate);

	/**
	 * Get Due Date.
	 * Date when the payment is due
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDueDate();

	ModelColumn<I_C_InvoicePaySchedule, Object> COLUMN_DueDate = new ModelColumn<>(I_C_InvoicePaySchedule.class, "DueDate", null);
	String COLUMNNAME_DueDate = "DueDate";

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

	ModelColumn<I_C_InvoicePaySchedule, Object> COLUMN_IsActive = new ModelColumn<>(I_C_InvoicePaySchedule.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Is Valid.
	 * The element is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsValid (boolean IsValid);

	/**
	 * Get Is Valid.
	 * The element is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isValid();

	ModelColumn<I_C_InvoicePaySchedule, Object> COLUMN_IsValid = new ModelColumn<>(I_C_InvoicePaySchedule.class, "IsValid", null);
	String COLUMNNAME_IsValid = "IsValid";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_C_InvoicePaySchedule, Object> COLUMN_Processed = new ModelColumn<>(I_C_InvoicePaySchedule.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_C_InvoicePaySchedule, Object> COLUMN_Processing = new ModelColumn<>(I_C_InvoicePaySchedule.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_InvoicePaySchedule, Object> COLUMN_Updated = new ModelColumn<>(I_C_InvoicePaySchedule.class, "Updated", null);
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
