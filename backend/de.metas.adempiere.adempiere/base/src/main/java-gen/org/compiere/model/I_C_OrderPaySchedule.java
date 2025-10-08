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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_PaymentTerm_Break_ID (int C_PaymentTerm_Break_ID);

	/**
	 * Get Payment Term Break.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
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
	 * Set Due Date.
	 * Date when the payment is due
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDueDate (@Nullable java.sql.Timestamp DueDate);

	/**
	 * Get Due Date.
	 * Date when the payment is due
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDueDate();

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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStatus (@Nullable java.lang.String Status);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getStatus();

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
