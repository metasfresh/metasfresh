package de.metas.handlingunits.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

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
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	org.compiere.model.I_C_Order getC_Order();

	void setC_Order(org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_M_Picking_Job_Step, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_M_Picking_Job_Step.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_ID();

	org.compiere.model.I_C_OrderLine getC_OrderLine();

	void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine);

	ModelColumn<I_M_Picking_Job_Step, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_M_Picking_Job_Step.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

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
	 * Set Is Dynamic.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDynamic (boolean IsDynamic);

	/**
	 * Get Is Dynamic.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDynamic();

	ModelColumn<I_M_Picking_Job_Step, Object> COLUMN_IsDynamic = new ModelColumn<>(I_M_Picking_Job_Step.class, "IsDynamic", null);
	String COLUMNNAME_IsDynamic = "IsDynamic";

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
	 * Set Pick To Packing Instructions.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPackTo_HU_PI_Item_Product_ID (int PackTo_HU_PI_Item_Product_ID);

	/**
	 * Get Pick To Packing Instructions.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPackTo_HU_PI_Item_Product_ID();

	String COLUMNNAME_PackTo_HU_PI_Item_Product_ID = "PackTo_HU_PI_Item_Product_ID";

	/**
	 * Set Picked HU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPicked_HU_ID (int Picked_HU_ID);

	/**
	 * Get Picked HU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPicked_HU_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU getPicked_HU();

	void setPicked_HU(@Nullable de.metas.handlingunits.model.I_M_HU Picked_HU);

	ModelColumn<I_M_Picking_Job_Step, de.metas.handlingunits.model.I_M_HU> COLUMN_Picked_HU_ID = new ModelColumn<>(I_M_Picking_Job_Step.class, "Picked_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_Picked_HU_ID = "Picked_HU_ID";

	/**
	 * Set Pick From HU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPickFrom_HU_ID (int PickFrom_HU_ID);

	/**
	 * Get Pick From HU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPickFrom_HU_ID();

	de.metas.handlingunits.model.I_M_HU getPickFrom_HU();

	void setPickFrom_HU(de.metas.handlingunits.model.I_M_HU PickFrom_HU);

	ModelColumn<I_M_Picking_Job_Step, de.metas.handlingunits.model.I_M_HU> COLUMN_PickFrom_HU_ID = new ModelColumn<>(I_M_Picking_Job_Step.class, "PickFrom_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_PickFrom_HU_ID = "PickFrom_HU_ID";

	/**
	 * Set Pick From Locator.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPickFrom_Locator_ID (int PickFrom_Locator_ID);

	/**
	 * Get Pick From Locator.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPickFrom_Locator_ID();

	String COLUMNNAME_PickFrom_Locator_ID = "PickFrom_Locator_ID";

	/**
	 * Set Pick From Warehouse.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPickFrom_Warehouse_ID (int PickFrom_Warehouse_ID);

	/**
	 * Get Pick From Warehouse.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPickFrom_Warehouse_ID();

	String COLUMNNAME_PickFrom_Warehouse_ID = "PickFrom_Warehouse_ID";

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
