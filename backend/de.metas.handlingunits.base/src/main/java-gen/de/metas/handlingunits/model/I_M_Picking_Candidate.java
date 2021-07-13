package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Picking_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Picking_Candidate 
{

	String Table_Name = "M_Picking_Candidate";

//	/** AD_Table_ID=540831 */
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
	 * Set Approval Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setApprovalStatus (java.lang.String ApprovalStatus);

	/**
	 * Get Approval Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getApprovalStatus();

	ModelColumn<I_M_Picking_Candidate, Object> COLUMN_ApprovalStatus = new ModelColumn<>(I_M_Picking_Candidate.class, "ApprovalStatus", null);
	String COLUMNNAME_ApprovalStatus = "ApprovalStatus";

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

	ModelColumn<I_M_Picking_Candidate, Object> COLUMN_Created = new ModelColumn<>(I_M_Picking_Candidate.class, "Created", null);
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

	ModelColumn<I_M_Picking_Candidate, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Picking_Candidate.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU getM_HU();

	void setM_HU(@Nullable de.metas.handlingunits.model.I_M_HU M_HU);

	ModelColumn<I_M_Picking_Candidate, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_M_Picking_Candidate.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Picking candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Candidate_ID (int M_Picking_Candidate_ID);

	/**
	 * Get Picking candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Candidate_ID();

	ModelColumn<I_M_Picking_Candidate, Object> COLUMN_M_Picking_Candidate_ID = new ModelColumn<>(I_M_Picking_Candidate.class, "M_Picking_Candidate_ID", null);
	String COLUMNNAME_M_Picking_Candidate_ID = "M_Picking_Candidate_ID";

	/**
	 * Set Picking Slot.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PickingSlot_ID (int M_PickingSlot_ID);

	/**
	 * Get Picking Slot.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_PickingSlot_ID();

	ModelColumn<I_M_Picking_Candidate, Object> COLUMN_M_PickingSlot_ID = new ModelColumn<>(I_M_Picking_Candidate.class, "M_PickingSlot_ID", null);
	String COLUMNNAME_M_PickingSlot_ID = "M_PickingSlot_ID";

	/**
	 * Set Shipment Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Shipment Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_ShipmentSchedule_ID();

	ModelColumn<I_M_Picking_Candidate, Object> COLUMN_M_ShipmentSchedule_ID = new ModelColumn<>(I_M_Picking_Candidate.class, "M_ShipmentSchedule_ID", null);
	String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set Pack To.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPackTo_HU_PI_ID (int PackTo_HU_PI_ID);

	/**
	 * Get Pack To.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPackTo_HU_PI_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_PI getPackTo_HU_PI();

	void setPackTo_HU_PI(@Nullable de.metas.handlingunits.model.I_M_HU_PI PackTo_HU_PI);

	ModelColumn<I_M_Picking_Candidate, de.metas.handlingunits.model.I_M_HU_PI> COLUMN_PackTo_HU_PI_ID = new ModelColumn<>(I_M_Picking_Candidate.class, "PackTo_HU_PI_ID", de.metas.handlingunits.model.I_M_HU_PI.class);
	String COLUMNNAME_PackTo_HU_PI_ID = "PackTo_HU_PI_ID";

	/**
	 * Set Pick From HU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPickFrom_HU_ID (int PickFrom_HU_ID);

	/**
	 * Get Pick From HU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPickFrom_HU_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU getPickFrom_HU();

	void setPickFrom_HU(@Nullable de.metas.handlingunits.model.I_M_HU PickFrom_HU);

	ModelColumn<I_M_Picking_Candidate, de.metas.handlingunits.model.I_M_HU> COLUMN_PickFrom_HU_ID = new ModelColumn<>(I_M_Picking_Candidate.class, "PickFrom_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_PickFrom_HU_ID = "PickFrom_HU_ID";

	/**
	 * Set Pick From Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPickFrom_Order_ID (int PickFrom_Order_ID);

	/**
	 * Get Pick From Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPickFrom_Order_ID();

	@Nullable org.eevolution.model.I_PP_Order getPickFrom_Order();

	void setPickFrom_Order(@Nullable org.eevolution.model.I_PP_Order PickFrom_Order);

	ModelColumn<I_M_Picking_Candidate, org.eevolution.model.I_PP_Order> COLUMN_PickFrom_Order_ID = new ModelColumn<>(I_M_Picking_Candidate.class, "PickFrom_Order_ID", org.eevolution.model.I_PP_Order.class);
	String COLUMNNAME_PickFrom_Order_ID = "PickFrom_Order_ID";

	/**
	 * Set Pick Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPickStatus (java.lang.String PickStatus);

	/**
	 * Get Pick Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPickStatus();

	ModelColumn<I_M_Picking_Candidate, Object> COLUMN_PickStatus = new ModelColumn<>(I_M_Picking_Candidate.class, "PickStatus", null);
	String COLUMNNAME_PickStatus = "PickStatus";

	/**
	 * Set Quantity (stock unit).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyPicked (BigDecimal QtyPicked);

	/**
	 * Get Quantity (stock unit).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyPicked();

	ModelColumn<I_M_Picking_Candidate, Object> COLUMN_QtyPicked = new ModelColumn<>(I_M_Picking_Candidate.class, "QtyPicked", null);
	String COLUMNNAME_QtyPicked = "QtyPicked";

	/**
	 * Set Qty Review.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyReview (@Nullable BigDecimal QtyReview);

	/**
	 * Get Qty Review.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyReview();

	ModelColumn<I_M_Picking_Candidate, Object> COLUMN_QtyReview = new ModelColumn<>(I_M_Picking_Candidate.class, "QtyReview", null);
	String COLUMNNAME_QtyReview = "QtyReview";

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

	ModelColumn<I_M_Picking_Candidate, Object> COLUMN_Status = new ModelColumn<>(I_M_Picking_Candidate.class, "Status", null);
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

	ModelColumn<I_M_Picking_Candidate, Object> COLUMN_Updated = new ModelColumn<>(I_M_Picking_Candidate.class, "Updated", null);
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
