package de.metas.dunning.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_Dunning_Candidate_Invoice_v1
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Dunning_Candidate_Invoice_v1
{

	String Table_Name = "C_Dunning_Candidate_Invoice_v1";

//	/** AD_Table_ID=540498 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

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
	 * Set Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Dunning.
	 * Dunning Rules for overdue invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Dunning_ID (int C_Dunning_ID);

	/**
	 * Get Dunning.
	 * Dunning Rules for overdue invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Dunning_ID();

	@Nullable org.compiere.model.I_C_Dunning getC_Dunning();

	void setC_Dunning(@Nullable org.compiere.model.I_C_Dunning C_Dunning);

	ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_C_Dunning> COLUMN_C_Dunning_ID = new ModelColumn<>(I_C_Dunning_Candidate_Invoice_v1.class, "C_Dunning_ID", org.compiere.model.I_C_Dunning.class);
	String COLUMNNAME_C_Dunning_ID = "C_Dunning_ID";

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

	ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_C_Dunning_Candidate_Invoice_v1.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Invoice Payment Schedule.
	 * Invoice Payment Schedule
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_InvoicePaySchedule_ID (int C_InvoicePaySchedule_ID);

	/**
	 * Get Invoice Payment Schedule.
	 * Invoice Payment Schedule
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_InvoicePaySchedule_ID();

	@Nullable org.compiere.model.I_C_InvoicePaySchedule getC_InvoicePaySchedule();

	void setC_InvoicePaySchedule(@Nullable org.compiere.model.I_C_InvoicePaySchedule C_InvoicePaySchedule);

	ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_C_InvoicePaySchedule> COLUMN_C_InvoicePaySchedule_ID = new ModelColumn<>(I_C_Dunning_Candidate_Invoice_v1.class, "C_InvoicePaySchedule_ID", org.compiere.model.I_C_InvoicePaySchedule.class);
	String COLUMNNAME_C_InvoicePaySchedule_ID = "C_InvoicePaySchedule_ID";

	/**
	 * Set Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_PaymentTerm_ID();

	String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/**
	 * Set Date.
	 * Date printed on Invoice
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateInvoiced (@Nullable java.sql.Timestamp DateInvoiced);

	/**
	 * Get Date.
	 * Date printed on Invoice
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateInvoiced();

	ModelColumn<I_C_Dunning_Candidate_Invoice_v1, Object> COLUMN_DateInvoiced = new ModelColumn<>(I_C_Dunning_Candidate_Invoice_v1.class, "DateInvoiced", null);
	String COLUMNNAME_DateInvoiced = "DateInvoiced";

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

	ModelColumn<I_C_Dunning_Candidate_Invoice_v1, Object> COLUMN_DueDate = new ModelColumn<>(I_C_Dunning_Candidate_Invoice_v1.class, "DueDate", null);
	String COLUMNNAME_DueDate = "DueDate";

	/**
	 * Set Dunning Grace Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDunningGrace (@Nullable java.sql.Timestamp DunningGrace);

	/**
	 * Get Dunning Grace Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDunningGrace();

	ModelColumn<I_C_Dunning_Candidate_Invoice_v1, Object> COLUMN_DunningGrace = new ModelColumn<>(I_C_Dunning_Candidate_Invoice_v1.class, "DunningGrace", null);
	String COLUMNNAME_DunningGrace = "DunningGrace";

	/**
	 * Set Grand Total.
	 * Total amount of document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGrandTotal (@Nullable BigDecimal GrandTotal);

	/**
	 * Get Grand Total.
	 * Total amount of document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getGrandTotal();

	ModelColumn<I_C_Dunning_Candidate_Invoice_v1, Object> COLUMN_GrandTotal = new ModelColumn<>(I_C_Dunning_Candidate_Invoice_v1.class, "GrandTotal", null);
	String COLUMNNAME_GrandTotal = "GrandTotal";

	/**
	 * Set In Dispute.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsInDispute (boolean IsInDispute);

	/**
	 * Get In Dispute.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isInDispute();

	ModelColumn<I_C_Dunning_Candidate_Invoice_v1, Object> COLUMN_IsInDispute = new ModelColumn<>(I_C_Dunning_Candidate_Invoice_v1.class, "IsInDispute", null);
	String COLUMNNAME_IsInDispute = "IsInDispute";

	/**
	 * Set Section Code.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_SectionCode_ID (int M_SectionCode_ID);

	/**
	 * Get Section Code.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_SectionCode_ID();

	@Nullable org.compiere.model.I_M_SectionCode getM_SectionCode();

	void setM_SectionCode(@Nullable org.compiere.model.I_M_SectionCode M_SectionCode);

	ModelColumn<I_C_Dunning_Candidate_Invoice_v1, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_C_Dunning_Candidate_Invoice_v1.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set Open Amount.
	 * Open item amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOpenAmt (@Nullable BigDecimal OpenAmt);

	/**
	 * Get Open Amount.
	 * Open item amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getOpenAmt();

	ModelColumn<I_C_Dunning_Candidate_Invoice_v1, Object> COLUMN_OpenAmt = new ModelColumn<>(I_C_Dunning_Candidate_Invoice_v1.class, "OpenAmt", null);
	String COLUMNNAME_OpenAmt = "OpenAmt";
}
