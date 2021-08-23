package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_ReceiptSchedule_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_ReceiptSchedule_Alloc 
{

	String Table_Name = "M_ReceiptSchedule_Alloc";

//	/** AD_Table_ID=540530 */
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
	 * Set Catch UOM.
	 * Catch weight UOM as taken from the product master data.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCatch_UOM_ID (int Catch_UOM_ID);

	/**
	 * Get Catch UOM.
	 * Catch weight UOM as taken from the product master data.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCatch_UOM_ID();

	String COLUMNNAME_Catch_UOM_ID = "Catch_UOM_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_Created = new ModelColumn<>(I_M_ReceiptSchedule_Alloc.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setDocStatus (@Nullable java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getDocStatus();

	ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_DocStatus = new ModelColumn<>(I_M_ReceiptSchedule_Alloc.class, "DocStatus", null);
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

	ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_IsActive = new ModelColumn<>(I_M_ReceiptSchedule_Alloc.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getM_InOut_ID();

	@Deprecated
	@Nullable org.compiere.model.I_M_InOut getM_InOut();

	@Deprecated
	void setM_InOut(@Nullable org.compiere.model.I_M_InOut M_InOut);

	ModelColumn<I_M_ReceiptSchedule_Alloc, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_M_ReceiptSchedule_Alloc.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
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

	ModelColumn<I_M_ReceiptSchedule_Alloc, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new ModelColumn<>(I_M_ReceiptSchedule_Alloc.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
	String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Wareneingangsdispo - Wareneingangszeile.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_ReceiptSchedule_Alloc_ID (int M_ReceiptSchedule_Alloc_ID);

	/**
	 * Get Wareneingangsdispo - Wareneingangszeile.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_ReceiptSchedule_Alloc_ID();

	ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_M_ReceiptSchedule_Alloc_ID = new ModelColumn<>(I_M_ReceiptSchedule_Alloc.class, "M_ReceiptSchedule_Alloc_ID", null);
	String COLUMNNAME_M_ReceiptSchedule_Alloc_ID = "M_ReceiptSchedule_Alloc_ID";

	/**
	 * Set Material Receipt Candidates.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_ReceiptSchedule_ID (int M_ReceiptSchedule_ID);

	/**
	 * Get Material Receipt Candidates.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_ReceiptSchedule_ID();

	de.metas.inoutcandidate.model.I_M_ReceiptSchedule getM_ReceiptSchedule();

	void setM_ReceiptSchedule(de.metas.inoutcandidate.model.I_M_ReceiptSchedule M_ReceiptSchedule);

	ModelColumn<I_M_ReceiptSchedule_Alloc, de.metas.inoutcandidate.model.I_M_ReceiptSchedule> COLUMN_M_ReceiptSchedule_ID = new ModelColumn<>(I_M_ReceiptSchedule_Alloc.class, "M_ReceiptSchedule_ID", de.metas.inoutcandidate.model.I_M_ReceiptSchedule.class);
	String COLUMNNAME_M_ReceiptSchedule_ID = "M_ReceiptSchedule_ID";

	/**
	 * Set Zugewiesene Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyAllocated (@Nullable BigDecimal QtyAllocated);

	/**
	 * Get Zugewiesene Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyAllocated();

	ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_QtyAllocated = new ModelColumn<>(I_M_ReceiptSchedule_Alloc.class, "QtyAllocated", null);
	String COLUMNNAME_QtyAllocated = "QtyAllocated";

	/**
	 * Set Allocated catch.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyAllocatedInCatchUOM (@Nullable BigDecimal QtyAllocatedInCatchUOM);

	/**
	 * Get Allocated catch.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyAllocatedInCatchUOM();

	ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_QtyAllocatedInCatchUOM = new ModelColumn<>(I_M_ReceiptSchedule_Alloc.class, "QtyAllocatedInCatchUOM", null);
	String COLUMNNAME_QtyAllocatedInCatchUOM = "QtyAllocatedInCatchUOM";

	/**
	 * Set Minderwertige Menge.
	 * Mengen-Summe der zugeordneten Lieferzeilen, die mit "im Disput" markiert sind und nicht fakturiert werden sollen.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyWithIssues (BigDecimal QtyWithIssues);

	/**
	 * Get Minderwertige Menge.
	 * Mengen-Summe der zugeordneten Lieferzeilen, die mit "im Disput" markiert sind und nicht fakturiert werden sollen.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyWithIssues();

	ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_QtyWithIssues = new ModelColumn<>(I_M_ReceiptSchedule_Alloc.class, "QtyWithIssues", null);
	String COLUMNNAME_QtyWithIssues = "QtyWithIssues";

	/**
	 * Set Catch quantity with issues.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyWithIssuesInCatchUOM (@Nullable BigDecimal QtyWithIssuesInCatchUOM);

	/**
	 * Get Catch quantity with issues.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyWithIssuesInCatchUOM();

	ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_QtyWithIssuesInCatchUOM = new ModelColumn<>(I_M_ReceiptSchedule_Alloc.class, "QtyWithIssuesInCatchUOM", null);
	String COLUMNNAME_QtyWithIssuesInCatchUOM = "QtyWithIssuesInCatchUOM";

	/**
	 * Set Qualitätsabzug %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setQualityDiscountPercent (@Nullable BigDecimal QualityDiscountPercent);

	/**
	 * Get Qualitätsabzug %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	BigDecimal getQualityDiscountPercent();

	ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_QualityDiscountPercent = new ModelColumn<>(I_M_ReceiptSchedule_Alloc.class, "QualityDiscountPercent", null);
	String COLUMNNAME_QualityDiscountPercent = "QualityDiscountPercent";

	/**
	 * Set Qualität-Notiz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setQualityNote (@Nullable java.lang.String QualityNote);

	/**
	 * Get Qualität-Notiz.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getQualityNote();

	ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_QualityNote = new ModelColumn<>(I_M_ReceiptSchedule_Alloc.class, "QualityNote", null);
	String COLUMNNAME_QualityNote = "QualityNote";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_ReceiptSchedule_Alloc, Object> COLUMN_Updated = new ModelColumn<>(I_M_ReceiptSchedule_Alloc.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
