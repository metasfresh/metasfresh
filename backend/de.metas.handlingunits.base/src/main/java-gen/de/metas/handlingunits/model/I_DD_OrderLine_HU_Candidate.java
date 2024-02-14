package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for DD_OrderLine_HU_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_DD_OrderLine_HU_Candidate 
{

	String Table_Name = "DD_OrderLine_HU_Candidate";

//	/** AD_Table_ID=540686 */
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

	ModelColumn<I_DD_OrderLine_HU_Candidate, Object> COLUMN_Created = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "Created", null);
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
	 * Set Distribution Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDD_Order_ID (int DD_Order_ID);

	/**
	 * Get Distribution Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDD_Order_ID();

	org.eevolution.model.I_DD_Order getDD_Order();

	void setDD_Order(org.eevolution.model.I_DD_Order DD_Order);

	ModelColumn<I_DD_OrderLine_HU_Candidate, org.eevolution.model.I_DD_Order> COLUMN_DD_Order_ID = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "DD_Order_ID", org.eevolution.model.I_DD_Order.class);
	String COLUMNNAME_DD_Order_ID = "DD_Order_ID";

	/**
	 * Set Distribution Order Move Schedule.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDD_Order_MoveSchedule_ID (int DD_Order_MoveSchedule_ID);

	/**
	 * Get Distribution Order Move Schedule.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDD_Order_MoveSchedule_ID();

	@Nullable de.metas.handlingunits.model.I_DD_Order_MoveSchedule getDD_Order_MoveSchedule();

	void setDD_Order_MoveSchedule(@Nullable de.metas.handlingunits.model.I_DD_Order_MoveSchedule DD_Order_MoveSchedule);

	ModelColumn<I_DD_OrderLine_HU_Candidate, de.metas.handlingunits.model.I_DD_Order_MoveSchedule> COLUMN_DD_Order_MoveSchedule_ID = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "DD_Order_MoveSchedule_ID", de.metas.handlingunits.model.I_DD_Order_MoveSchedule.class);
	String COLUMNNAME_DD_Order_MoveSchedule_ID = "DD_Order_MoveSchedule_ID";

	/**
	 * Set Distribution Order Line HU Candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDD_OrderLine_HU_Candidate_ID (int DD_OrderLine_HU_Candidate_ID);

	/**
	 * Get Distribution Order Line HU Candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDD_OrderLine_HU_Candidate_ID();

	ModelColumn<I_DD_OrderLine_HU_Candidate, Object> COLUMN_DD_OrderLine_HU_Candidate_ID = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "DD_OrderLine_HU_Candidate_ID", null);
	String COLUMNNAME_DD_OrderLine_HU_Candidate_ID = "DD_OrderLine_HU_Candidate_ID";

	/**
	 * Set Distribution Order Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDD_OrderLine_ID (int DD_OrderLine_ID);

	/**
	 * Get Distribution Order Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDD_OrderLine_ID();

	org.eevolution.model.I_DD_OrderLine getDD_OrderLine();

	void setDD_OrderLine(org.eevolution.model.I_DD_OrderLine DD_OrderLine);

	ModelColumn<I_DD_OrderLine_HU_Candidate, org.eevolution.model.I_DD_OrderLine> COLUMN_DD_OrderLine_ID = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "DD_OrderLine_ID", org.eevolution.model.I_DD_OrderLine.class);
	String COLUMNNAME_DD_OrderLine_ID = "DD_OrderLine_ID";

	/**
	 * Set DropTo Locator.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDropTo_Locator_ID (int DropTo_Locator_ID);

	/**
	 * Get DropTo Locator.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDropTo_Locator_ID();

	String COLUMNNAME_DropTo_Locator_ID = "DropTo_Locator_ID";

	/**
	 * Set Drop To Movement.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropTo_Movement_ID (int DropTo_Movement_ID);

	/**
	 * Get Drop To Movement.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropTo_Movement_ID();

	@Nullable org.compiere.model.I_M_Movement getDropTo_Movement();

	void setDropTo_Movement(@Nullable org.compiere.model.I_M_Movement DropTo_Movement);

	ModelColumn<I_DD_OrderLine_HU_Candidate, org.compiere.model.I_M_Movement> COLUMN_DropTo_Movement_ID = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "DropTo_Movement_ID", org.compiere.model.I_M_Movement.class);
	String COLUMNNAME_DropTo_Movement_ID = "DropTo_Movement_ID";

	/**
	 * Set Drop To Movement Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropTo_MovementLine_ID (int DropTo_MovementLine_ID);

	/**
	 * Get Drop To Movement Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropTo_MovementLine_ID();

	@Nullable org.compiere.model.I_M_MovementLine getDropTo_MovementLine();

	void setDropTo_MovementLine(@Nullable org.compiere.model.I_M_MovementLine DropTo_MovementLine);

	ModelColumn<I_DD_OrderLine_HU_Candidate, org.compiere.model.I_M_MovementLine> COLUMN_DropTo_MovementLine_ID = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "DropTo_MovementLine_ID", org.compiere.model.I_M_MovementLine.class);
	String COLUMNNAME_DropTo_MovementLine_ID = "DropTo_MovementLine_ID";

	/**
	 * Set Drop To Warehouse.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDropTo_Warehouse_ID (int DropTo_Warehouse_ID);

	/**
	 * Get Drop To Warehouse.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDropTo_Warehouse_ID();

	String COLUMNNAME_DropTo_Warehouse_ID = "DropTo_Warehouse_ID";

	/**
	 * Set In Transit Locator.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInTransit_Locator_ID (int InTransit_Locator_ID);

	/**
	 * Get In Transit Locator.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getInTransit_Locator_ID();

	String COLUMNNAME_InTransit_Locator_ID = "InTransit_Locator_ID";

	/**
	 * Set In Transit Warehouse.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInTransit_Warehouse_ID (int InTransit_Warehouse_ID);

	/**
	 * Get In Transit Warehouse.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getInTransit_Warehouse_ID();

	String COLUMNNAME_InTransit_Warehouse_ID = "InTransit_Warehouse_ID";

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

	ModelColumn<I_DD_OrderLine_HU_Candidate, Object> COLUMN_IsActive = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Pick Whole HU.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPickWholeHU (boolean IsPickWholeHU);

	/**
	 * Get Pick Whole HU.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPickWholeHU();

	ModelColumn<I_DD_OrderLine_HU_Candidate, Object> COLUMN_IsPickWholeHU = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "IsPickWholeHU", null);
	String COLUMNNAME_IsPickWholeHU = "IsPickWholeHU";

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

	ModelColumn<I_DD_OrderLine_HU_Candidate, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

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

	ModelColumn<I_DD_OrderLine_HU_Candidate, de.metas.handlingunits.model.I_M_HU> COLUMN_PickFrom_HU_ID = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "PickFrom_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
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
	 * Set Pick From Movement.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPickFrom_Movement_ID (int PickFrom_Movement_ID);

	/**
	 * Get Pick From Movement.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPickFrom_Movement_ID();

	@Nullable org.compiere.model.I_M_Movement getPickFrom_Movement();

	void setPickFrom_Movement(@Nullable org.compiere.model.I_M_Movement PickFrom_Movement);

	ModelColumn<I_DD_OrderLine_HU_Candidate, org.compiere.model.I_M_Movement> COLUMN_PickFrom_Movement_ID = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "PickFrom_Movement_ID", org.compiere.model.I_M_Movement.class);
	String COLUMNNAME_PickFrom_Movement_ID = "PickFrom_Movement_ID";

	/**
	 * Set Pick From Movement Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPickFrom_MovementLine_ID (int PickFrom_MovementLine_ID);

	/**
	 * Get Pick From Movement Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPickFrom_MovementLine_ID();

	@Nullable org.compiere.model.I_M_MovementLine getPickFrom_MovementLine();

	void setPickFrom_MovementLine(@Nullable org.compiere.model.I_M_MovementLine PickFrom_MovementLine);

	ModelColumn<I_DD_OrderLine_HU_Candidate, org.compiere.model.I_M_MovementLine> COLUMN_PickFrom_MovementLine_ID = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "PickFrom_MovementLine_ID", org.compiere.model.I_M_MovementLine.class);
	String COLUMNNAME_PickFrom_MovementLine_ID = "PickFrom_MovementLine_ID";

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

	ModelColumn<I_DD_OrderLine_HU_Candidate, Object> COLUMN_QtyPicked = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "QtyPicked", null);
	String COLUMNNAME_QtyPicked = "QtyPicked";

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

	ModelColumn<I_DD_OrderLine_HU_Candidate, Object> COLUMN_QtyToPick = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "QtyToPick", null);
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

	ModelColumn<I_DD_OrderLine_HU_Candidate, Object> COLUMN_RejectReason = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "RejectReason", null);
	String COLUMNNAME_RejectReason = "RejectReason";

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

	ModelColumn<I_DD_OrderLine_HU_Candidate, Object> COLUMN_Status = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "Status", null);
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

	ModelColumn<I_DD_OrderLine_HU_Candidate, Object> COLUMN_Updated = new ModelColumn<>(I_DD_OrderLine_HU_Candidate.class, "Updated", null);
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
