package de.metas.pos.repository.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_POS_InvoiceCardSettlement
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_POS_InvoiceCardSettlement 
{

	String Table_Name = "C_POS_InvoiceCardSettlement";

//	/** AD_Table_ID=542659 */
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

	ModelColumn<I_C_POS_InvoiceCardSettlement, Object> COLUMN_Amount = new ModelColumn<>(I_C_POS_InvoiceCardSettlement.class, "Amount", null);
	String COLUMNNAME_Amount = "Amount";

	/**
	 * Set Booking Attempts.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBookingAttempts (int BookingAttempts);

	/**
	 * Get Booking Attempts.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getBookingAttempts();

	ModelColumn<I_C_POS_InvoiceCardSettlement, Object> COLUMN_BookingAttempts = new ModelColumn<>(I_C_POS_InvoiceCardSettlement.class, "BookingAttempts", null);
	String COLUMNNAME_BookingAttempts = "BookingAttempts";

	/**
	 * Set Cashier.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCashier_ID (int Cashier_ID);

	/**
	 * Get Cashier.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCashier_ID();

	String COLUMNNAME_Cashier_ID = "Cashier_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
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
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_ID();

	ModelColumn<I_C_POS_InvoiceCardSettlement, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_C_POS_InvoiceCardSettlement.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

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
	 * Set POS Terminal.
	 * Point of Sales Terminal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_POS_ID (int C_POS_ID);

	/**
	 * Get POS Terminal.
	 * Point of Sales Terminal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_POS_ID();

	ModelColumn<I_C_POS_InvoiceCardSettlement, org.compiere.model.I_C_POS> COLUMN_C_POS_ID = new ModelColumn<>(I_C_POS_InvoiceCardSettlement.class, "C_POS_ID", org.compiere.model.I_C_POS.class);
	String COLUMNNAME_C_POS_ID = "C_POS_ID";

	/**
	 * Set POS Invoice Card Settlement.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_POS_InvoiceCardSettlement_ID (int C_POS_InvoiceCardSettlement_ID);

	/**
	 * Get POS Invoice Card Settlement.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_POS_InvoiceCardSettlement_ID();

	ModelColumn<I_C_POS_InvoiceCardSettlement, Object> COLUMN_C_POS_InvoiceCardSettlement_ID = new ModelColumn<>(I_C_POS_InvoiceCardSettlement.class, "C_POS_InvoiceCardSettlement_ID", null);
	String COLUMNNAME_C_POS_InvoiceCardSettlement_ID = "C_POS_InvoiceCardSettlement_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_POS_InvoiceCardSettlement, Object> COLUMN_Created = new ModelColumn<>(I_C_POS_InvoiceCardSettlement.class, "Created", null);
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
	 * Set Error Message.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setErrorMsg (@Nullable java.lang.String ErrorMsg);

	/**
	 * Get Error Message.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getErrorMsg();

	ModelColumn<I_C_POS_InvoiceCardSettlement, Object> COLUMN_ErrorMsg = new ModelColumn<>(I_C_POS_InvoiceCardSettlement.class, "ErrorMsg", null);
	String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/**
	 * Set Abandoned.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAbandoned (boolean IsAbandoned);

	/**
	 * Get Abandoned.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAbandoned();

	ModelColumn<I_C_POS_InvoiceCardSettlement, Object> COLUMN_IsAbandoned = new ModelColumn<>(I_C_POS_InvoiceCardSettlement.class, "IsAbandoned", null);
	String COLUMNNAME_IsAbandoned = "IsAbandoned";

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

	ModelColumn<I_C_POS_InvoiceCardSettlement, Object> COLUMN_IsActive = new ModelColumn<>(I_C_POS_InvoiceCardSettlement.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNote (@Nullable java.lang.String Note);

	/**
	 * Get Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNote();

	ModelColumn<I_C_POS_InvoiceCardSettlement, Object> COLUMN_Note = new ModelColumn<>(I_C_POS_InvoiceCardSettlement.class, "Note", null);
	String COLUMNNAME_Note = "Note";

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

	ModelColumn<I_C_POS_InvoiceCardSettlement, Object> COLUMN_Status = new ModelColumn<>(I_C_POS_InvoiceCardSettlement.class, "Status", null);
	String COLUMNNAME_Status = "Status";

	/**
	 * Set SumUp Transaction.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSUMUP_Transaction_ID (int SUMUP_Transaction_ID);

	/**
	 * Get SumUp Transaction.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSUMUP_Transaction_ID();

	ModelColumn<I_C_POS_InvoiceCardSettlement, Object> COLUMN_SUMUP_Transaction_ID = new ModelColumn<>(I_C_POS_InvoiceCardSettlement.class, "SUMUP_Transaction_ID", null);
	String COLUMNNAME_SUMUP_Transaction_ID = "SUMUP_Transaction_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_POS_InvoiceCardSettlement, Object> COLUMN_Updated = new ModelColumn<>(I_C_POS_InvoiceCardSettlement.class, "Updated", null);
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
