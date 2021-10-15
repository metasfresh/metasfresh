package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Picking_Job_Step
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Picking_Job_Step 
{

	String Table_Name = "M_Picking_Job_Step";

//	/** AD_Table_ID=541908 */
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

	ModelColumn<I_M_Picking_Job_Step, Object> COLUMN_Created = new ModelColumn<>(I_M_Picking_Job_Step.class, "Created", null);
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

	ModelColumn<I_M_Picking_Job_Step, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Picking_Job_Step.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_HU_ID (int M_HU_ID);

	/**
	 * Get Handling Unit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_HU_ID();

	de.metas.handlingunits.model.I_M_HU getM_HU();

	void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU);

	ModelColumn<I_M_Picking_Job_Step, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_M_Picking_Job_Step.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Picking candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Candidate_ID (int M_Picking_Candidate_ID);

	/**
	 * Get Picking candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Candidate_ID();

	@Nullable de.metas.handlingunits.model.I_M_Picking_Candidate getM_Picking_Candidate();

	void setM_Picking_Candidate(@Nullable de.metas.handlingunits.model.I_M_Picking_Candidate M_Picking_Candidate);

	ModelColumn<I_M_Picking_Job_Step, de.metas.handlingunits.model.I_M_Picking_Candidate> COLUMN_M_Picking_Candidate_ID = new ModelColumn<>(I_M_Picking_Job_Step.class, "M_Picking_Candidate_ID", de.metas.handlingunits.model.I_M_Picking_Candidate.class);
	String COLUMNNAME_M_Picking_Candidate_ID = "M_Picking_Candidate_ID";

	/**
	 * Set Picking Job.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Job_ID (int M_Picking_Job_ID);

	/**
	 * Get Picking Job.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Job_ID();

	de.metas.handlingunits.model.I_M_Picking_Job getM_Picking_Job();

	void setM_Picking_Job(de.metas.handlingunits.model.I_M_Picking_Job M_Picking_Job);

	ModelColumn<I_M_Picking_Job_Step, de.metas.handlingunits.model.I_M_Picking_Job> COLUMN_M_Picking_Job_ID = new ModelColumn<>(I_M_Picking_Job_Step.class, "M_Picking_Job_ID", de.metas.handlingunits.model.I_M_Picking_Job.class);
	String COLUMNNAME_M_Picking_Job_ID = "M_Picking_Job_ID";

	/**
	 * Set Picking Job Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Job_Line_ID (int M_Picking_Job_Line_ID);

	/**
	 * Get Picking Job Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Job_Line_ID();

	de.metas.handlingunits.model.I_M_Picking_Job_Line getM_Picking_Job_Line();

	void setM_Picking_Job_Line(de.metas.handlingunits.model.I_M_Picking_Job_Line M_Picking_Job_Line);

	ModelColumn<I_M_Picking_Job_Step, de.metas.handlingunits.model.I_M_Picking_Job_Line> COLUMN_M_Picking_Job_Line_ID = new ModelColumn<>(I_M_Picking_Job_Step.class, "M_Picking_Job_Line_ID", de.metas.handlingunits.model.I_M_Picking_Job_Line.class);
	String COLUMNNAME_M_Picking_Job_Line_ID = "M_Picking_Job_Line_ID";

	/**
	 * Set Picking Job Step.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Job_Step_ID (int M_Picking_Job_Step_ID);

	/**
	 * Get Picking Job Step.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Job_Step_ID();

	ModelColumn<I_M_Picking_Job_Step, Object> COLUMN_M_Picking_Job_Step_ID = new ModelColumn<>(I_M_Picking_Job_Step.class, "M_Picking_Job_Step_ID", null);
	String COLUMNNAME_M_Picking_Job_Step_ID = "M_Picking_Job_Step_ID";

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

	ModelColumn<I_M_Picking_Job_Step, Object> COLUMN_M_PickingSlot_ID = new ModelColumn<>(I_M_Picking_Job_Step.class, "M_PickingSlot_ID", null);
	String COLUMNNAME_M_PickingSlot_ID = "M_PickingSlot_ID";

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

	ModelColumn<I_M_Picking_Job_Step, Object> COLUMN_M_ShipmentSchedule_ID = new ModelColumn<>(I_M_Picking_Job_Step.class, "M_ShipmentSchedule_ID", null);
	String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

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

	ModelColumn<I_M_Picking_Job_Step, Object> COLUMN_Processed = new ModelColumn<>(I_M_Picking_Job_Step.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

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

	ModelColumn<I_M_Picking_Job_Step, Object> COLUMN_QtyPicked = new ModelColumn<>(I_M_Picking_Job_Step.class, "QtyPicked", null);
	String COLUMNNAME_QtyPicked = "QtyPicked";

	/**
	 * Set Qty Rejected To Pick.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyRejectedToPick (BigDecimal QtyRejectedToPick);

	/**
	 * Get Qty Rejected To Pick.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyRejectedToPick();

	ModelColumn<I_M_Picking_Job_Step, Object> COLUMN_QtyRejectedToPick = new ModelColumn<>(I_M_Picking_Job_Step.class, "QtyRejectedToPick", null);
	String COLUMNNAME_QtyRejectedToPick = "QtyRejectedToPick";

	/**
	 * Set Qty To Pick.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyToPick (BigDecimal QtyToPick);

	/**
	 * Get Qty To Pick.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToPick();

	ModelColumn<I_M_Picking_Job_Step, Object> COLUMN_QtyToPick = new ModelColumn<>(I_M_Picking_Job_Step.class, "QtyToPick", null);
	String COLUMNNAME_QtyToPick = "QtyToPick";

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

	ModelColumn<I_M_Picking_Job_Step, Object> COLUMN_RejectReason = new ModelColumn<>(I_M_Picking_Job_Step.class, "RejectReason", null);
	String COLUMNNAME_RejectReason = "RejectReason";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Picking_Job_Step, Object> COLUMN_Updated = new ModelColumn<>(I_M_Picking_Job_Step.class, "Updated", null);
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
