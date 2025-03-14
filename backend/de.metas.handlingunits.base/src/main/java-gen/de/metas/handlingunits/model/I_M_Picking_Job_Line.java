package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Picking_Job_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Picking_Job_Line 
{

	String Table_Name = "M_Picking_Job_Line";

//	/** AD_Table_ID=541907 */
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
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Location.
	 * Identifies the address of the business partner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 * Identifies the address of the business partner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

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

	ModelColumn<I_M_Picking_Job_Line, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_M_Picking_Job_Line.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
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

	ModelColumn<I_M_Picking_Job_Line, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_M_Picking_Job_Line.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
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

	ModelColumn<I_M_Picking_Job_Line, Object> COLUMN_Created = new ModelColumn<>(I_M_Picking_Job_Line.class, "Created", null);
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
	 * Set Current Pick To LU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurrent_PickTo_LU_ID (int Current_PickTo_LU_ID);

	/**
	 * Get Current Pick To LU.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCurrent_PickTo_LU_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU getCurrent_PickTo_LU();

	void setCurrent_PickTo_LU(@Nullable de.metas.handlingunits.model.I_M_HU Current_PickTo_LU);

	ModelColumn<I_M_Picking_Job_Line, de.metas.handlingunits.model.I_M_HU> COLUMN_Current_PickTo_LU_ID = new ModelColumn<>(I_M_Picking_Job_Line.class, "Current_PickTo_LU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_Current_PickTo_LU_ID = "Current_PickTo_LU_ID";

	/**
	 * Set Current Pick To LU PI.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurrent_PickTo_LU_PI_ID (int Current_PickTo_LU_PI_ID);

	/**
	 * Get Current Pick To LU PI.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCurrent_PickTo_LU_PI_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_PI getCurrent_PickTo_LU_PI();

	void setCurrent_PickTo_LU_PI(@Nullable de.metas.handlingunits.model.I_M_HU_PI Current_PickTo_LU_PI);

	ModelColumn<I_M_Picking_Job_Line, de.metas.handlingunits.model.I_M_HU_PI> COLUMN_Current_PickTo_LU_PI_ID = new ModelColumn<>(I_M_Picking_Job_Line.class, "Current_PickTo_LU_PI_ID", de.metas.handlingunits.model.I_M_HU_PI.class);
	String COLUMNNAME_Current_PickTo_LU_PI_ID = "Current_PickTo_LU_PI_ID";

	/**
	 * Set Current Pick To LU QR Code.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurrent_PickTo_LU_QRCode (@Nullable java.lang.String Current_PickTo_LU_QRCode);

	/**
	 * Get Current Pick To LU QR Code.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCurrent_PickTo_LU_QRCode();

	ModelColumn<I_M_Picking_Job_Line, Object> COLUMN_Current_PickTo_LU_QRCode = new ModelColumn<>(I_M_Picking_Job_Line.class, "Current_PickTo_LU_QRCode", null);
	String COLUMNNAME_Current_PickTo_LU_QRCode = "Current_PickTo_LU_QRCode";

	/**
	 * Set Current Pick To TU PI.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurrent_PickTo_TU_PI_ID (int Current_PickTo_TU_PI_ID);

	/**
	 * Get Current Pick To TU PI.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCurrent_PickTo_TU_PI_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU_PI getCurrent_PickTo_TU_PI();

	void setCurrent_PickTo_TU_PI(@Nullable de.metas.handlingunits.model.I_M_HU_PI Current_PickTo_TU_PI);

	ModelColumn<I_M_Picking_Job_Line, de.metas.handlingunits.model.I_M_HU_PI> COLUMN_Current_PickTo_TU_PI_ID = new ModelColumn<>(I_M_Picking_Job_Line.class, "Current_PickTo_TU_PI_ID", de.metas.handlingunits.model.I_M_HU_PI.class);
	String COLUMNNAME_Current_PickTo_TU_PI_ID = "Current_PickTo_TU_PI_ID";

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

	ModelColumn<I_M_Picking_Job_Line, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Picking_Job_Line.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Manually closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManuallyClosed (boolean IsManuallyClosed);

	/**
	 * Get Manually closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManuallyClosed();

	ModelColumn<I_M_Picking_Job_Line, Object> COLUMN_IsManuallyClosed = new ModelColumn<>(I_M_Picking_Job_Line.class, "IsManuallyClosed", null);
	String COLUMNNAME_IsManuallyClosed = "IsManuallyClosed";

	/**
	 * Set Packing Instruction.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID);

	/**
	 * Get Packing Instruction.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Item_Product_ID();

	String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

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

	ModelColumn<I_M_Picking_Job_Line, de.metas.handlingunits.model.I_M_Picking_Job> COLUMN_M_Picking_Job_ID = new ModelColumn<>(I_M_Picking_Job_Line.class, "M_Picking_Job_ID", de.metas.handlingunits.model.I_M_Picking_Job.class);
	String COLUMNNAME_M_Picking_Job_ID = "M_Picking_Job_ID";

	/**
	 * Set Picking Job Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Job_Line_ID (int M_Picking_Job_Line_ID);

	/**
	 * Get Picking Job Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Job_Line_ID();

	ModelColumn<I_M_Picking_Job_Line, Object> COLUMN_M_Picking_Job_Line_ID = new ModelColumn<>(I_M_Picking_Job_Line.class, "M_Picking_Job_Line_ID", null);
	String COLUMNNAME_M_Picking_Job_Line_ID = "M_Picking_Job_Line_ID";

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

	ModelColumn<I_M_Picking_Job_Line, Object> COLUMN_M_PickingSlot_ID = new ModelColumn<>(I_M_Picking_Job_Line.class, "M_PickingSlot_ID", null);
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Shipment Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_ShipmentSchedule_ID();

	ModelColumn<I_M_Picking_Job_Line, Object> COLUMN_M_ShipmentSchedule_ID = new ModelColumn<>(I_M_Picking_Job_Line.class, "M_ShipmentSchedule_ID", null);
	String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Order_ID (int PP_Order_ID);

	/**
	 * Get Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Order_ID();

	@Nullable org.eevolution.model.I_PP_Order getPP_Order();

	void setPP_Order(@Nullable org.eevolution.model.I_PP_Order PP_Order);

	ModelColumn<I_M_Picking_Job_Line, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new ModelColumn<>(I_M_Picking_Job_Line.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
	String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

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

	ModelColumn<I_M_Picking_Job_Line, Object> COLUMN_Processed = new ModelColumn<>(I_M_Picking_Job_Line.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

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

	ModelColumn<I_M_Picking_Job_Line, Object> COLUMN_QtyToPick = new ModelColumn<>(I_M_Picking_Job_Line.class, "QtyToPick", null);
	String COLUMNNAME_QtyToPick = "QtyToPick";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Picking_Job_Line, Object> COLUMN_Updated = new ModelColumn<>(I_M_Picking_Job_Line.class, "Updated", null);
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
