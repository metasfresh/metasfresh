package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for GPLR_Report
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_GPLR_Report 
{

	String Table_Name = "GPLR_Report";

//	/** AD_Table_ID=542341 */
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

	org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_GPLR_Report, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_GPLR_Report.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_GPLR_Report, Object> COLUMN_Created = new ModelColumn<>(I_GPLR_Report.class, "Created", null);
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
	 * Set Created By.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreatedByName (@Nullable java.lang.String CreatedByName);

	/**
	 * Get Created By.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreatedByName();

	ModelColumn<I_GPLR_Report, Object> COLUMN_CreatedByName = new ModelColumn<>(I_GPLR_Report.class, "CreatedByName", null);
	String COLUMNNAME_CreatedByName = "CreatedByName";

	/**
	 * Set Currency Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurrencyRate (@Nullable BigDecimal CurrencyRate);

	/**
	 * Get Currency Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCurrencyRate();

	ModelColumn<I_GPLR_Report, Object> COLUMN_CurrencyRate = new ModelColumn<>(I_GPLR_Report.class, "CurrencyRate", null);
	String COLUMNNAME_CurrencyRate = "CurrencyRate";

	/**
	 * Set Document Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateDoc (@Nullable java.sql.Timestamp DateDoc);

	/**
	 * Get Document Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateDoc();

	ModelColumn<I_GPLR_Report, Object> COLUMN_DateDoc = new ModelColumn<>(I_GPLR_Report.class, "DateDoc", null);
	String COLUMNNAME_DateDoc = "DateDoc";

	/**
	 * Set Department.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDepartmentName (@Nullable java.lang.String DepartmentName);

	/**
	 * Get Department.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDepartmentName();

	ModelColumn<I_GPLR_Report, Object> COLUMN_DepartmentName = new ModelColumn<>(I_GPLR_Report.class, "DepartmentName", null);
	String COLUMNNAME_DepartmentName = "DepartmentName";

	/**
	 * Set Document Create Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocCreateDate (@Nullable java.sql.Timestamp DocCreateDate);

	/**
	 * Get Document Create Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDocCreateDate();

	ModelColumn<I_GPLR_Report, Object> COLUMN_DocCreateDate = new ModelColumn<>(I_GPLR_Report.class, "DocCreateDate", null);
	String COLUMNNAME_DocCreateDate = "DocCreateDate";

	/**
	 * Set Document Type Name.
	 * Name of the Document Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocTypeName (@Nullable java.lang.String DocTypeName);

	/**
	 * Get Document Type Name.
	 * Name of the Document Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocTypeName();

	ModelColumn<I_GPLR_Report, Object> COLUMN_DocTypeName = new ModelColumn<>(I_GPLR_Report.class, "DocTypeName", null);
	String COLUMNNAME_DocTypeName = "DocTypeName";

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

	ModelColumn<I_GPLR_Report, Object> COLUMN_DueDate = new ModelColumn<>(I_GPLR_Report.class, "DueDate", null);
	String COLUMNNAME_DueDate = "DueDate";

	/**
	 * Set FEC Document No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFEC_DocumentNo (@Nullable java.lang.String FEC_DocumentNo);

	/**
	 * Get FEC Document No.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFEC_DocumentNo();

	ModelColumn<I_GPLR_Report, Object> COLUMN_FEC_DocumentNo = new ModelColumn<>(I_GPLR_Report.class, "FEC_DocumentNo", null);
	String COLUMNNAME_FEC_DocumentNo = "FEC_DocumentNo";

	/**
	 * Set Foreign Currency.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setForeignCurrencyCode (java.lang.String ForeignCurrencyCode);

	/**
	 * Get Foreign Currency.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getForeignCurrencyCode();

	ModelColumn<I_GPLR_Report, Object> COLUMN_ForeignCurrencyCode = new ModelColumn<>(I_GPLR_Report.class, "ForeignCurrencyCode", null);
	String COLUMNNAME_ForeignCurrencyCode = "ForeignCurrencyCode";

	/**
	 * Set GPLR Report.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGPLR_Report_ID (int GPLR_Report_ID);

	/**
	 * Get GPLR Report.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGPLR_Report_ID();

	ModelColumn<I_GPLR_Report, Object> COLUMN_GPLR_Report_ID = new ModelColumn<>(I_GPLR_Report.class, "GPLR_Report_ID", null);
	String COLUMNNAME_GPLR_Report_ID = "GPLR_Report_ID";

	/**
	 * Set Invoice Document No.
	 * Document Number of the Invoice
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoiceDocumentNo (@Nullable java.lang.String InvoiceDocumentNo);

	/**
	 * Get Invoice Document No.
	 * Document Number of the Invoice
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInvoiceDocumentNo();

	ModelColumn<I_GPLR_Report, Object> COLUMN_InvoiceDocumentNo = new ModelColumn<>(I_GPLR_Report.class, "InvoiceDocumentNo", null);
	String COLUMNNAME_InvoiceDocumentNo = "InvoiceDocumentNo";

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

	ModelColumn<I_GPLR_Report, Object> COLUMN_IsActive = new ModelColumn<>(I_GPLR_Report.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Payment Term.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPaymentTermInfo (java.lang.String PaymentTermInfo);

	/**
	 * Get Payment Term.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPaymentTermInfo();

	ModelColumn<I_GPLR_Report, Object> COLUMN_PaymentTermInfo = new ModelColumn<>(I_GPLR_Report.class, "PaymentTermInfo", null);
	String COLUMNNAME_PaymentTermInfo = "PaymentTermInfo";

	/**
	 * Set Section Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSectionCodeAndName (@Nullable java.lang.String SectionCodeAndName);

	/**
	 * Get Section Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSectionCodeAndName();

	ModelColumn<I_GPLR_Report, Object> COLUMN_SectionCodeAndName = new ModelColumn<>(I_GPLR_Report.class, "SectionCodeAndName", null);
	String COLUMNNAME_SectionCodeAndName = "SectionCodeAndName";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_GPLR_Report, Object> COLUMN_Updated = new ModelColumn<>(I_GPLR_Report.class, "Updated", null);
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
