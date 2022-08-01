package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_InterimInvoice_FlatrateTerm_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_InterimInvoice_FlatrateTerm_Line 
{

	String Table_Name = "C_InterimInvoice_FlatrateTerm_Line";

//	/** AD_Table_ID=542194 */
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
	 * Set Interim Invoice Flatrate Term.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_InterimInvoice_FlatrateTerm_ID (int C_InterimInvoice_FlatrateTerm_ID);

	/**
	 * Get Interim Invoice Flatrate Term.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_InterimInvoice_FlatrateTerm_ID();

	@Nullable org.compiere.model.I_C_InterimInvoice_FlatrateTerm getC_InterimInvoice_FlatrateTerm();

	void setC_InterimInvoice_FlatrateTerm(@Nullable org.compiere.model.I_C_InterimInvoice_FlatrateTerm C_InterimInvoice_FlatrateTerm);

	ModelColumn<I_C_InterimInvoice_FlatrateTerm_Line, org.compiere.model.I_C_InterimInvoice_FlatrateTerm> COLUMN_C_InterimInvoice_FlatrateTerm_ID = new ModelColumn<>(I_C_InterimInvoice_FlatrateTerm_Line.class, "C_InterimInvoice_FlatrateTerm_ID", org.compiere.model.I_C_InterimInvoice_FlatrateTerm.class);
	String COLUMNNAME_C_InterimInvoice_FlatrateTerm_ID = "C_InterimInvoice_FlatrateTerm_ID";

	/**
	 * Set Interim Invoice Flatrate Term.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_InterimInvoice_FlatrateTerm_Line_ID (int C_InterimInvoice_FlatrateTerm_Line_ID);

	/**
	 * Get Interim Invoice Flatrate Term.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_InterimInvoice_FlatrateTerm_Line_ID();

	ModelColumn<I_C_InterimInvoice_FlatrateTerm_Line, Object> COLUMN_C_InterimInvoice_FlatrateTerm_Line_ID = new ModelColumn<>(I_C_InterimInvoice_FlatrateTerm_Line.class, "C_InterimInvoice_FlatrateTerm_Line_ID", null);
	String COLUMNNAME_C_InterimInvoice_FlatrateTerm_Line_ID = "C_InterimInvoice_FlatrateTerm_Line_ID";

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

	ModelColumn<I_C_InterimInvoice_FlatrateTerm_Line, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_C_InterimInvoice_FlatrateTerm_Line.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Invoice Line.
	 * Rechnungszeile
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/**
	 * Get Invoice Line.
	 * Rechnungszeile
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_InvoiceLine_ID();

	@Nullable org.compiere.model.I_C_InvoiceLine getC_InvoiceLine();

	void setC_InvoiceLine(@Nullable org.compiere.model.I_C_InvoiceLine C_InvoiceLine);

	ModelColumn<I_C_InterimInvoice_FlatrateTerm_Line, org.compiere.model.I_C_InvoiceLine> COLUMN_C_InvoiceLine_ID = new ModelColumn<>(I_C_InterimInvoice_FlatrateTerm_Line.class, "C_InvoiceLine_ID", org.compiere.model.I_C_InvoiceLine.class);
	String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_InterimInvoice_FlatrateTerm_Line, Object> COLUMN_Created = new ModelColumn<>(I_C_InterimInvoice_FlatrateTerm_Line.class, "Created", null);
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

	ModelColumn<I_C_InterimInvoice_FlatrateTerm_Line, Object> COLUMN_IsActive = new ModelColumn<>(I_C_InterimInvoice_FlatrateTerm_Line.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	@Nullable org.compiere.model.I_M_InOut getM_InOut();

	void setM_InOut(@Nullable org.compiere.model.I_M_InOut M_InOut);

	ModelColumn<I_C_InterimInvoice_FlatrateTerm_Line, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_C_InterimInvoice_FlatrateTerm_Line.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InOutLine_ID();

	@Nullable org.compiere.model.I_M_InOutLine getM_InOutLine();

	void setM_InOutLine(@Nullable org.compiere.model.I_M_InOutLine M_InOutLine);

	ModelColumn<I_C_InterimInvoice_FlatrateTerm_Line, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new ModelColumn<>(I_C_InterimInvoice_FlatrateTerm_Line.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
	String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_InterimInvoice_FlatrateTerm_Line, Object> COLUMN_Updated = new ModelColumn<>(I_C_InterimInvoice_FlatrateTerm_Line.class, "Updated", null);
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
