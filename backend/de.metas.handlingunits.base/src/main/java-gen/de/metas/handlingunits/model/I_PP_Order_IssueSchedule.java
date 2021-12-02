package de.metas.handlingunits.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for PP_Order_IssueSchedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PP_Order_IssueSchedule 
{

	String Table_Name = "PP_Order_IssueSchedule";

//	/** AD_Table_ID=541932 */
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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
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

	ModelColumn<I_PP_Order_IssueSchedule, Object> COLUMN_Created = new ModelColumn<>(I_PP_Order_IssueSchedule.class, "Created", null);
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

	ModelColumn<I_PP_Order_IssueSchedule, Object> COLUMN_IsActive = new ModelColumn<>(I_PP_Order_IssueSchedule.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Alternative HU.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAlternativeHU (boolean IsAlternativeHU);

	/**
	 * Get Alternative HU.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAlternativeHU();

	ModelColumn<I_PP_Order_IssueSchedule, Object> COLUMN_IsAlternativeHU = new ModelColumn<>(I_PP_Order_IssueSchedule.class, "IsAlternativeHU", null);
	String COLUMNNAME_IsAlternativeHU = "IsAlternativeHU";

	/**
	 * Set Issue From HU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIssueFrom_HU_ID (int IssueFrom_HU_ID);

	/**
	 * Get Issue From HU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getIssueFrom_HU_ID();

	de.metas.handlingunits.model.I_M_HU getIssueFrom_HU();

	void setIssueFrom_HU(de.metas.handlingunits.model.I_M_HU IssueFrom_HU);

	ModelColumn<I_PP_Order_IssueSchedule, de.metas.handlingunits.model.I_M_HU> COLUMN_IssueFrom_HU_ID = new ModelColumn<>(I_PP_Order_IssueSchedule.class, "IssueFrom_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_IssueFrom_HU_ID = "IssueFrom_HU_ID";

	/**
	 * Set Issue from Locator.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIssueFrom_Locator_ID (int IssueFrom_Locator_ID);

	/**
	 * Get Issue from Locator.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getIssueFrom_Locator_ID();

	String COLUMNNAME_IssueFrom_Locator_ID = "IssueFrom_Locator_ID";

	/**
	 * Set Issue from Warehouse.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIssueFrom_Warehouse_ID (int IssueFrom_Warehouse_ID);

	/**
	 * Get Issue from Warehouse.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getIssueFrom_Warehouse_ID();

	String COLUMNNAME_IssueFrom_Warehouse_ID = "IssueFrom_Warehouse_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Manufacturing Order BOM Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Order_BOMLine_ID (int PP_Order_BOMLine_ID);

	/**
	 * Get Manufacturing Order BOM Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Order_BOMLine_ID();

	org.eevolution.model.I_PP_Order_BOMLine getPP_Order_BOMLine();

	void setPP_Order_BOMLine(org.eevolution.model.I_PP_Order_BOMLine PP_Order_BOMLine);

	ModelColumn<I_PP_Order_IssueSchedule, org.eevolution.model.I_PP_Order_BOMLine> COLUMN_PP_Order_BOMLine_ID = new ModelColumn<>(I_PP_Order_IssueSchedule.class, "PP_Order_BOMLine_ID", org.eevolution.model.I_PP_Order_BOMLine.class);
	String COLUMNNAME_PP_Order_BOMLine_ID = "PP_Order_BOMLine_ID";

	/**
	 * Set Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Order_ID (int PP_Order_ID);

	/**
	 * Get Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Order_ID();

	org.eevolution.model.I_PP_Order getPP_Order();

	void setPP_Order(org.eevolution.model.I_PP_Order PP_Order);

	ModelColumn<I_PP_Order_IssueSchedule, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new ModelColumn<>(I_PP_Order_IssueSchedule.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
	String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/**
	 * Set Manufacturing Order Issue Schedule.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Order_IssueSchedule_ID (int PP_Order_IssueSchedule_ID);

	/**
	 * Get Manufacturing Order Issue Schedule.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Order_IssueSchedule_ID();

	ModelColumn<I_PP_Order_IssueSchedule, Object> COLUMN_PP_Order_IssueSchedule_ID = new ModelColumn<>(I_PP_Order_IssueSchedule.class, "PP_Order_IssueSchedule_ID", null);
	String COLUMNNAME_PP_Order_IssueSchedule_ID = "PP_Order_IssueSchedule_ID";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_PP_Order_IssueSchedule, Object> COLUMN_Processed = new ModelColumn<>(I_PP_Order_IssueSchedule.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Quantity Issued.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyIssued (BigDecimal QtyIssued);

	/**
	 * Get Quantity Issued.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyIssued();

	ModelColumn<I_PP_Order_IssueSchedule, Object> COLUMN_QtyIssued = new ModelColumn<>(I_PP_Order_IssueSchedule.class, "QtyIssued", null);
	String COLUMNNAME_QtyIssued = "QtyIssued";

	/**
	 * Set Qty Reject.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyReject (@Nullable BigDecimal QtyReject);

	/**
	 * Get Qty Reject.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyReject();

	ModelColumn<I_PP_Order_IssueSchedule, Object> COLUMN_QtyReject = new ModelColumn<>(I_PP_Order_IssueSchedule.class, "QtyReject", null);
	String COLUMNNAME_QtyReject = "QtyReject";

	/**
	 * Set Quantity To Issue.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyToIssue (BigDecimal QtyToIssue);

	/**
	 * Get Quantity To Issue.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToIssue();

	ModelColumn<I_PP_Order_IssueSchedule, Object> COLUMN_QtyToIssue = new ModelColumn<>(I_PP_Order_IssueSchedule.class, "QtyToIssue", null);
	String COLUMNNAME_QtyToIssue = "QtyToIssue";

	/**
	 * Set Reject Reason.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRejectReason (@Nullable java.lang.String RejectReason);

	/**
	 * Get Reject Reason.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRejectReason();

	ModelColumn<I_PP_Order_IssueSchedule, Object> COLUMN_RejectReason = new ModelColumn<>(I_PP_Order_IssueSchedule.class, "RejectReason", null);
	String COLUMNNAME_RejectReason = "RejectReason";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_PP_Order_IssueSchedule, Object> COLUMN_SeqNo = new ModelColumn<>(I_PP_Order_IssueSchedule.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PP_Order_IssueSchedule, Object> COLUMN_Updated = new ModelColumn<>(I_PP_Order_IssueSchedule.class, "Updated", null);
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
