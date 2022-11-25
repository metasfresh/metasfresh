package de.metas.invoicecandidate.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_Invoice_Line_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Invoice_Line_Alloc 
{

	String Table_Name = "C_Invoice_Line_Alloc";

//	/** AD_Table_ID=540321 */
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
	 * Set Aggregator.
	 * Definiert Richtlinien zur Aggregation von Datensätzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Candidate_Agg_ID (int C_Invoice_Candidate_Agg_ID);

	/**
	 * Get Aggregator.
	 * Definiert Richtlinien zur Aggregation von Datensätzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Candidate_Agg_ID();

	@Nullable de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg getC_Invoice_Candidate_Agg();

	void setC_Invoice_Candidate_Agg(@Nullable de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg C_Invoice_Candidate_Agg);

	ModelColumn<I_C_Invoice_Line_Alloc, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg> COLUMN_C_Invoice_Candidate_Agg_ID = new ModelColumn<>(I_C_Invoice_Line_Alloc.class, "C_Invoice_Candidate_Agg_ID", de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg.class);
	String COLUMNNAME_C_Invoice_Candidate_Agg_ID = "C_Invoice_Candidate_Agg_ID";

	/**
	 * Set Invoice candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID);

	/**
	 * Get Invoice candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Candidate_ID();

	de.metas.invoicecandidate.model.I_C_Invoice_Candidate getC_Invoice_Candidate();

	void setC_Invoice_Candidate(de.metas.invoicecandidate.model.I_C_Invoice_Candidate C_Invoice_Candidate);

	ModelColumn<I_C_Invoice_Line_Alloc, de.metas.invoicecandidate.model.I_C_Invoice_Candidate> COLUMN_C_Invoice_Candidate_ID = new ModelColumn<>(I_C_Invoice_Line_Alloc.class, "C_Invoice_Candidate_ID", de.metas.invoicecandidate.model.I_C_Invoice_Candidate.class);
	String COLUMNNAME_C_Invoice_Candidate_ID = "C_Invoice_Candidate_ID";

	/**
	 * Set Rechnungskandidat - Rechungszeile.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Line_Alloc_ID (int C_Invoice_Line_Alloc_ID);

	/**
	 * Get Rechnungskandidat - Rechungszeile.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Line_Alloc_ID();

	ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_C_Invoice_Line_Alloc_ID = new ModelColumn<>(I_C_Invoice_Line_Alloc.class, "C_Invoice_Line_Alloc_ID", null);
	String COLUMNNAME_C_Invoice_Line_Alloc_ID = "C_Invoice_Line_Alloc_ID";

	/**
	 * Set Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Line_Alloc_Type (@Nullable java.lang.String C_Invoice_Line_Alloc_Type);

	/**
	 * Get Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getC_Invoice_Line_Alloc_Type();

	ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_C_Invoice_Line_Alloc_Type = new ModelColumn<>(I_C_Invoice_Line_Alloc.class, "C_Invoice_Line_Alloc_Type", null);
	String COLUMNNAME_C_Invoice_Line_Alloc_Type = "C_Invoice_Line_Alloc_Type";

	/**
	 * Set Invoice Line.
	 * Rechnungszeile
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/**
	 * Get Invoice Line.
	 * Rechnungszeile
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_InvoiceLine_ID();

	org.compiere.model.I_C_InvoiceLine getC_InvoiceLine();

	void setC_InvoiceLine(org.compiere.model.I_C_InvoiceLine C_InvoiceLine);

	ModelColumn<I_C_Invoice_Line_Alloc, org.compiere.model.I_C_InvoiceLine> COLUMN_C_InvoiceLine_ID = new ModelColumn<>(I_C_Invoice_Line_Alloc.class, "C_InvoiceLine_ID", org.compiere.model.I_C_InvoiceLine.class);
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

	ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_Created = new ModelColumn<>(I_C_Invoice_Line_Alloc.class, "Created", null);
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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setDocStatus (@Nullable java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getDocStatus();

	ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_DocStatus = new ModelColumn<>(I_C_Invoice_Line_Alloc.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

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

	ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Invoice_Line_Alloc.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNote (@Nullable java.lang.String Note);

	/**
	 * Get Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNote();

	ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_Note = new ModelColumn<>(I_C_Invoice_Line_Alloc.class, "Note", null);
	String COLUMNNAME_Note = "Note";

	/**
	 * Set Price override.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceEntered_Override (@Nullable BigDecimal PriceEntered_Override);

	/**
	 * Get Price override.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceEntered_Override();

	ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_PriceEntered_Override = new ModelColumn<>(I_C_Invoice_Line_Alloc.class, "PriceEntered_Override", null);
	String COLUMNNAME_PriceEntered_Override = "PriceEntered_Override";

	/**
	 * Set Quantity Invoiced.
	 * Invoiced quantity in the product's UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyInvoiced (BigDecimal QtyInvoiced);

	/**
	 * Get Quantity Invoiced.
	 * Invoiced quantity in the product's UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyInvoiced();

	ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_QtyInvoiced = new ModelColumn<>(I_C_Invoice_Line_Alloc.class, "QtyInvoiced", null);
	String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

	/**
	 * Set Invoiced.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyInvoicedInUOM (@Nullable BigDecimal QtyInvoicedInUOM);

	/**
	 * Get Invoiced.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyInvoicedInUOM();

	ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_QtyInvoicedInUOM = new ModelColumn<>(I_C_Invoice_Line_Alloc.class, "QtyInvoicedInUOM", null);
	String COLUMNNAME_QtyInvoicedInUOM = "QtyInvoicedInUOM";

	/**
	 * Set Zu berechn. Menge abw..
	 * Der Benutzer kann eine abweichende zu berechnede Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyToInvoice_Override (@Nullable BigDecimal QtyToInvoice_Override);

	/**
	 * Get Zu berechn. Menge abw..
	 * Der Benutzer kann eine abweichende zu berechnede Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToInvoice_Override();

	ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_QtyToInvoice_Override = new ModelColumn<>(I_C_Invoice_Line_Alloc.class, "QtyToInvoice_Override", null);
	String COLUMNNAME_QtyToInvoice_Override = "QtyToInvoice_Override";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Invoice_Line_Alloc, Object> COLUMN_Updated = new ModelColumn<>(I_C_Invoice_Line_Alloc.class, "Updated", null);
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
