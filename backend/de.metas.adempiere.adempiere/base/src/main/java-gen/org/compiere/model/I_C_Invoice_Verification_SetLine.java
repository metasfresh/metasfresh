package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_Invoice_Verification_SetLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Invoice_Verification_SetLine 
{

	String Table_Name = "C_Invoice_Verification_SetLine";

//	/** AD_Table_ID=541663 */
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
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

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

	ModelColumn<I_C_Invoice_Verification_SetLine, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_C_Invoice_Verification_SetLine.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Invoice Line.
	 * Rechnungszeile
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/**
	 * Get Invoice Line.
	 * Rechnungszeile
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_InvoiceLine_ID();

	@Nullable org.compiere.model.I_C_InvoiceLine getC_InvoiceLine();

	void setC_InvoiceLine(@Nullable org.compiere.model.I_C_InvoiceLine C_InvoiceLine);

	ModelColumn<I_C_Invoice_Verification_SetLine, org.compiere.model.I_C_InvoiceLine> COLUMN_C_InvoiceLine_ID = new ModelColumn<>(I_C_Invoice_Verification_SetLine.class, "C_InvoiceLine_ID", org.compiere.model.I_C_InvoiceLine.class);
	String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/**
	 * Set Tax.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_InvoiceLine_Tax_ID (int C_InvoiceLine_Tax_ID);

	/**
	 * Get Tax.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_InvoiceLine_Tax_ID();

	String COLUMNNAME_C_InvoiceLine_Tax_ID = "C_InvoiceLine_Tax_ID";

	/**
	 * Set Invoice Verification Set.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Verification_Set_ID (int C_Invoice_Verification_Set_ID);

	/**
	 * Get Invoice Verification Set.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Verification_Set_ID();

	@Nullable org.compiere.model.I_C_Invoice_Verification_Set getC_Invoice_Verification_Set();

	void setC_Invoice_Verification_Set(@Nullable org.compiere.model.I_C_Invoice_Verification_Set C_Invoice_Verification_Set);

	ModelColumn<I_C_Invoice_Verification_SetLine, org.compiere.model.I_C_Invoice_Verification_Set> COLUMN_C_Invoice_Verification_Set_ID = new ModelColumn<>(I_C_Invoice_Verification_SetLine.class, "C_Invoice_Verification_Set_ID", org.compiere.model.I_C_Invoice_Verification_Set.class);
	String COLUMNNAME_C_Invoice_Verification_Set_ID = "C_Invoice_Verification_Set_ID";

	/**
	 * Set Invoice Verification Element.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Verification_SetLine_ID (int C_Invoice_Verification_SetLine_ID);

	/**
	 * Get Invoice Verification Element.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Verification_SetLine_ID();

	ModelColumn<I_C_Invoice_Verification_SetLine, Object> COLUMN_C_Invoice_Verification_SetLine_ID = new ModelColumn<>(I_C_Invoice_Verification_SetLine.class, "C_Invoice_Verification_SetLine_ID", null);
	String COLUMNNAME_C_Invoice_Verification_SetLine_ID = "C_Invoice_Verification_SetLine_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Invoice_Verification_SetLine, Object> COLUMN_Created = new ModelColumn<>(I_C_Invoice_Verification_SetLine.class, "Created", null);
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

	ModelColumn<I_C_Invoice_Verification_SetLine, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Invoice_Verification_SetLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Relevant Date.
	 * Date by which the invoice's tax was selected.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRelevantDate (@Nullable java.sql.Timestamp RelevantDate);

	/**
	 * Get Relevant Date.
	 * Date by which the invoice's tax was selected.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getRelevantDate();

	ModelColumn<I_C_Invoice_Verification_SetLine, Object> COLUMN_RelevantDate = new ModelColumn<>(I_C_Invoice_Verification_SetLine.class, "RelevantDate", null);
	String COLUMNNAME_RelevantDate = "RelevantDate";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Invoice_Verification_SetLine, Object> COLUMN_Updated = new ModelColumn<>(I_C_Invoice_Verification_SetLine.class, "Updated", null);
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
