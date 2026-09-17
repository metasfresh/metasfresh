package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_OrderPaySchedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_OrderPaySchedule 
{

	String Table_Name = "C_OrderPaySchedule";

//	/** AD_Table_ID=542539 */
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
	 * Set Berechtigter Betrag.
	 * Betrag, auf den der Prozentsatz angewendet wird
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBaseAmt (@Nullable BigDecimal BaseAmt);

	/**
	 * Get Berechtigter Betrag.
	 * Betrag, auf den der Prozentsatz angewendet wird
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getBaseAmt();

	ModelColumn<I_C_OrderPaySchedule, Object> COLUMN_BaseAmt = new ModelColumn<>(I_C_OrderPaySchedule.class, "BaseAmt", null);
	String COLUMNNAME_BaseAmt = "BaseAmt";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

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

	ModelColumn<I_C_OrderPaySchedule, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_C_OrderPaySchedule.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	ModelColumn<I_C_OrderPaySchedule, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_C_OrderPaySchedule.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Order Payment Schedule.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_OrderPaySchedule_ID (int C_OrderPaySchedule_ID);

	/**
	 * Get Order Payment Schedule.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_OrderPaySchedule_ID();

	ModelColumn<I_C_OrderPaySchedule, Object> COLUMN_C_OrderPaySchedule_ID = new ModelColumn<>(I_C_OrderPaySchedule.class, "C_OrderPaySchedule_ID", null);
	String COLUMNNAME_C_OrderPaySchedule_ID = "C_OrderPaySchedule_ID";

	/**
	 * Set Payment Term Break.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_PaymentTerm_Break_ID (int C_PaymentTerm_Break_ID);

	/**
	 * Get Payment Term Break.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_PaymentTerm_Break_ID();

	ModelColumn<I_C_OrderPaySchedule, org.compiere.model.I_C_PaymentTerm_Break> COLUMN_C_PaymentTerm_Break_ID = new ModelColumn<>(I_C_OrderPaySchedule.class, "C_PaymentTerm_Break_ID", org.compiere.model.I_C_PaymentTerm_Break.class);
	String COLUMNNAME_C_PaymentTerm_Break_ID = "C_PaymentTerm_Break_ID";

	/**
	 * Set Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_PaymentTerm_ID();

	String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_OrderPaySchedule, Object> COLUMN_Created = new ModelColumn<>(I_C_OrderPaySchedule.class, "Created", null);
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

	ModelColumn<I_C_OrderPaySchedule, Object> COLUMN_DueAmt = new ModelColumn<>(I_C_OrderPaySchedule.class, "DueAmt", null);
	String COLUMNNAME_DueAmt = "DueAmt";

	/**
	 * Set Tatsächlich fälliger Betrag.
	 * Tatsächlicher Betrag, der diesem Zahlungsplan-Schritt zugewiesen wurde
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDueAmt_Actual (@Nullable BigDecimal DueAmt_Actual);

	/**
	 * Get Tatsächlich fälliger Betrag.
	 * Tatsächlicher Betrag, der diesem Zahlungsplan-Schritt zugewiesen wurde
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDueAmt_Actual();

	ModelColumn<I_C_OrderPaySchedule, Object> COLUMN_DueAmt_Actual = new ModelColumn<>(I_C_OrderPaySchedule.class, "DueAmt_Actual", null);
	String COLUMNNAME_DueAmt_Actual = "DueAmt_Actual";

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

	ModelColumn<I_C_OrderPaySchedule, Object> COLUMN_DueDate = new ModelColumn<>(I_C_OrderPaySchedule.class, "DueDate", null);
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

	ModelColumn<I_C_OrderPaySchedule, Object> COLUMN_IsActive = new ModelColumn<>(I_C_OrderPaySchedule.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Paid.
	 * When checked, this pay-schedule line is paid.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPaid (boolean IsPaid);

	/**
	 * Get Paid.
	 * When checked, this pay-schedule line is paid.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPaid();

	ModelColumn<I_C_OrderPaySchedule, Object> COLUMN_IsPaid = new ModelColumn<>(I_C_OrderPaySchedule.class, "IsPaid", null);
	String COLUMNNAME_IsPaid = "IsPaid";

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InOut_ID();

	ModelColumn<I_C_OrderPaySchedule, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_C_OrderPaySchedule.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Offset days.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOffsetDays (int OffsetDays);

	/**
	 * Get Offset days.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getOffsetDays();

	ModelColumn<I_C_OrderPaySchedule, Object> COLUMN_OffsetDays = new ModelColumn<>(I_C_OrderPaySchedule.class, "OffsetDays", null);
	String COLUMNNAME_OffsetDays = "OffsetDays";

	/**
	 * Set Percent.
	 * Percentage
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPercent (int Percent);

	/**
	 * Get Percent.
	 * Percentage
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPercent();

	ModelColumn<I_C_OrderPaySchedule, Object> COLUMN_Percent = new ModelColumn<>(I_C_OrderPaySchedule.class, "Percent", null);
	String COLUMNNAME_Percent = "Percent";

	/**
	 * Set Reference date.
	 * Date the due date is computed from (DueDate = ReferenceDate + OffsetDays).
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReferenceDate (@Nullable java.sql.Timestamp ReferenceDate);

	/**
	 * Get Reference date.
	 * Date the due date is computed from (DueDate = ReferenceDate + OffsetDays).
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getReferenceDate();

	ModelColumn<I_C_OrderPaySchedule, Object> COLUMN_ReferenceDate = new ModelColumn<>(I_C_OrderPaySchedule.class, "ReferenceDate", null);
	String COLUMNNAME_ReferenceDate = "ReferenceDate";

	/**
	 * Set Reference Date Type.
	 * Specifies the base date type used to calculate the due date of a payment term break (e.g., Invoice Date, Bill of Lading Date, Order Date, Letter of Credit Date, Estimated Arrival Date).
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setReferenceDateType (java.lang.String ReferenceDateType);

	/**
	 * Get Reference Date Type.
	 * Specifies the base date type used to calculate the due date of a payment term break (e.g., Invoice Date, Bill of Lading Date, Order Date, Letter of Credit Date, Estimated Arrival Date).
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getReferenceDateType();

	ModelColumn<I_C_OrderPaySchedule, Object> COLUMN_ReferenceDateType = new ModelColumn<>(I_C_OrderPaySchedule.class, "ReferenceDateType", null);
	String COLUMNNAME_ReferenceDateType = "ReferenceDateType";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_C_OrderPaySchedule, Object> COLUMN_SeqNo = new ModelColumn<>(I_C_OrderPaySchedule.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStatus (java.lang.String Status);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getStatus();

	ModelColumn<I_C_OrderPaySchedule, Object> COLUMN_Status = new ModelColumn<>(I_C_OrderPaySchedule.class, "Status", null);
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

	ModelColumn<I_C_OrderPaySchedule, Object> COLUMN_Updated = new ModelColumn<>(I_C_OrderPaySchedule.class, "Updated", null);
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
