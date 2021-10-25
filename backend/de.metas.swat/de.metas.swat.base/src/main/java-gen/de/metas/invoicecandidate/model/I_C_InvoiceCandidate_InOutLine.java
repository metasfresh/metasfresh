package de.metas.invoicecandidate.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_InvoiceCandidate_InOutLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_InvoiceCandidate_InOutLine 
{

	String Table_Name = "C_InvoiceCandidate_InOutLine";

//	/** AD_Table_ID=540579 */
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
	 * Set Invoice candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID);

	/**
	 * Get Invoice candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Candidate_ID();

	@Nullable de.metas.invoicecandidate.model.I_C_Invoice_Candidate getC_Invoice_Candidate();

	void setC_Invoice_Candidate(@Nullable de.metas.invoicecandidate.model.I_C_Invoice_Candidate C_Invoice_Candidate);

	ModelColumn<I_C_InvoiceCandidate_InOutLine, de.metas.invoicecandidate.model.I_C_Invoice_Candidate> COLUMN_C_Invoice_Candidate_ID = new ModelColumn<>(I_C_InvoiceCandidate_InOutLine.class, "C_Invoice_Candidate_ID", de.metas.invoicecandidate.model.I_C_Invoice_Candidate.class);
	String COLUMNNAME_C_Invoice_Candidate_ID = "C_Invoice_Candidate_ID";

	/**
	 * Set C_InvoiceCandidate_InOutLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_InvoiceCandidate_InOutLine_ID (int C_InvoiceCandidate_InOutLine_ID);

	/**
	 * Get C_InvoiceCandidate_InOutLine.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_InvoiceCandidate_InOutLine_ID();

	ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_C_InvoiceCandidate_InOutLine_ID = new ModelColumn<>(I_C_InvoiceCandidate_InOutLine.class, "C_InvoiceCandidate_InOutLine_ID", null);
	String COLUMNNAME_C_InvoiceCandidate_InOutLine_ID = "C_InvoiceCandidate_InOutLine_ID";

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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_Created = new ModelColumn<>(I_C_InvoiceCandidate_InOutLine.class, "Created", null);
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

	ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_IsActive = new ModelColumn<>(I_C_InvoiceCandidate_InOutLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lieferung/ Wareneingang freigeben.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsInOutApprovedForInvoicing (boolean IsInOutApprovedForInvoicing);

	/**
	 * Get Lieferung/ Wareneingang freigeben.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isInOutApprovedForInvoicing();

	ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_IsInOutApprovedForInvoicing = new ModelColumn<>(I_C_InvoiceCandidate_InOutLine.class, "IsInOutApprovedForInvoicing", null);
	String COLUMNNAME_IsInOutApprovedForInvoicing = "IsInOutApprovedForInvoicing";

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

	ModelColumn<I_C_InvoiceCandidate_InOutLine, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new ModelColumn<>(I_C_InvoiceCandidate_InOutLine.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
	String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Gelieferte Menge.
	 * Gelieferte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDelivered (@Nullable BigDecimal QtyDelivered);

	/**
	 * Get Gelieferte Menge.
	 * Gelieferte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDelivered();

	ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_QtyDelivered = new ModelColumn<>(I_C_InvoiceCandidate_InOutLine.class, "QtyDelivered", null);
	String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/**
	 * Set Delivered catch.
	 * Actually delivered catch quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDeliveredInUOM_Catch (@Nullable BigDecimal QtyDeliveredInUOM_Catch);

	/**
	 * Get Delivered catch.
	 * Actually delivered catch quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDeliveredInUOM_Catch();

	ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_QtyDeliveredInUOM_Catch = new ModelColumn<>(I_C_InvoiceCandidate_InOutLine.class, "QtyDeliveredInUOM_Catch", null);
	String COLUMNNAME_QtyDeliveredInUOM_Catch = "QtyDeliveredInUOM_Catch";

	/**
	 * Set Delivered nominal.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDeliveredInUOM_Nominal (@Nullable BigDecimal QtyDeliveredInUOM_Nominal);

	/**
	 * Get Delivered nominal.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDeliveredInUOM_Nominal();

	ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_QtyDeliveredInUOM_Nominal = new ModelColumn<>(I_C_InvoiceCandidate_InOutLine.class, "QtyDeliveredInUOM_Nominal", null);
	String COLUMNNAME_QtyDeliveredInUOM_Nominal = "QtyDeliveredInUOM_Nominal";

	/**
	 * Set Delivered override.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDeliveredInUOM_Override (@Nullable BigDecimal QtyDeliveredInUOM_Override);

	/**
	 * Get Delivered override.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDeliveredInUOM_Override();

	ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_QtyDeliveredInUOM_Override = new ModelColumn<>(I_C_InvoiceCandidate_InOutLine.class, "QtyDeliveredInUOM_Override", null);
	String COLUMNNAME_QtyDeliveredInUOM_Override = "QtyDeliveredInUOM_Override";

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

	ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_QtyInvoiced = new ModelColumn<>(I_C_InvoiceCandidate_InOutLine.class, "QtyInvoiced", null);
	String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_InvoiceCandidate_InOutLine, Object> COLUMN_Updated = new ModelColumn<>(I_C_InvoiceCandidate_InOutLine.class, "Updated", null);
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
