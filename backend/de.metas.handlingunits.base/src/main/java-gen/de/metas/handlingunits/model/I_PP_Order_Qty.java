package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for PP_Order_Qty
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PP_Order_Qty 
{

	String Table_Name = "PP_Order_Qty";

//	/** AD_Table_ID=540807 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_PP_Order_Qty, Object> COLUMN_Created = new ModelColumn<>(I_PP_Order_Qty.class, "Created", null);
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
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
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

	ModelColumn<I_PP_Order_Qty, Object> COLUMN_IsActive = new ModelColumn<>(I_PP_Order_Qty.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Receipt.
	 * This is a sales transaction (receipt)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReceipt (boolean IsReceipt);

	/**
	 * Get Receipt.
	 * This is a sales transaction (receipt)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReceipt();

	ModelColumn<I_PP_Order_Qty, Object> COLUMN_IsReceipt = new ModelColumn<>(I_PP_Order_Qty.class, "IsReceipt", null);
	String COLUMNNAME_IsReceipt = "IsReceipt";

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

	ModelColumn<I_PP_Order_Qty, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_PP_Order_Qty.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Date.
	 * Date a product was moved in or out of inventory
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMovementDate (java.sql.Timestamp MovementDate);

	/**
	 * Get Date.
	 * Date a product was moved in or out of inventory
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getMovementDate();

	ModelColumn<I_PP_Order_Qty, Object> COLUMN_MovementDate = new ModelColumn<>(I_PP_Order_Qty.class, "MovementDate", null);
	String COLUMNNAME_MovementDate = "MovementDate";

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

	ModelColumn<I_PP_Order_Qty, de.metas.handlingunits.model.I_M_Picking_Candidate> COLUMN_M_Picking_Candidate_ID = new ModelColumn<>(I_PP_Order_Qty.class, "M_Picking_Candidate_ID", de.metas.handlingunits.model.I_M_Picking_Candidate.class);
	String COLUMNNAME_M_Picking_Candidate_ID = "M_Picking_Candidate_ID";

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
	 * Set New LU.
	 * New Loading Unit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNew_LU_ID (int New_LU_ID);

	/**
	 * Get New LU.
	 * New Loading Unit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getNew_LU_ID();

	@Nullable de.metas.handlingunits.model.I_M_HU getNew_LU();

	void setNew_LU(@Nullable de.metas.handlingunits.model.I_M_HU New_LU);

	ModelColumn<I_PP_Order_Qty, de.metas.handlingunits.model.I_M_HU> COLUMN_New_LU_ID = new ModelColumn<>(I_PP_Order_Qty.class, "New_LU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_New_LU_ID = "New_LU_ID";

	/**
	 * Set Manufacturing Cost Collector.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Cost_Collector_ID (int PP_Cost_Collector_ID);

	/**
	 * Get Manufacturing Cost Collector.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Cost_Collector_ID();

	@Nullable org.eevolution.model.I_PP_Cost_Collector getPP_Cost_Collector();

	void setPP_Cost_Collector(@Nullable org.eevolution.model.I_PP_Cost_Collector PP_Cost_Collector);

	ModelColumn<I_PP_Order_Qty, org.eevolution.model.I_PP_Cost_Collector> COLUMN_PP_Cost_Collector_ID = new ModelColumn<>(I_PP_Order_Qty.class, "PP_Cost_Collector_ID", org.eevolution.model.I_PP_Cost_Collector.class);
	String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";

	/**
	 * Set Manufacturing Order BOM Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Order_BOMLine_ID (int PP_Order_BOMLine_ID);

	/**
	 * Get Manufacturing Order BOM Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Order_BOMLine_ID();

	@Nullable org.eevolution.model.I_PP_Order_BOMLine getPP_Order_BOMLine();

	void setPP_Order_BOMLine(@Nullable org.eevolution.model.I_PP_Order_BOMLine PP_Order_BOMLine);

	ModelColumn<I_PP_Order_Qty, org.eevolution.model.I_PP_Order_BOMLine> COLUMN_PP_Order_BOMLine_ID = new ModelColumn<>(I_PP_Order_Qty.class, "PP_Order_BOMLine_ID", org.eevolution.model.I_PP_Order_BOMLine.class);
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

	ModelColumn<I_PP_Order_Qty, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new ModelColumn<>(I_PP_Order_Qty.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
	String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/**
	 * Set Manufacturing Order Issue Schedule.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Order_IssueSchedule_ID (int PP_Order_IssueSchedule_ID);

	/**
	 * Get Manufacturing Order Issue Schedule.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Order_IssueSchedule_ID();

	@Nullable de.metas.handlingunits.model.I_PP_Order_IssueSchedule getPP_Order_IssueSchedule();

	void setPP_Order_IssueSchedule(@Nullable de.metas.handlingunits.model.I_PP_Order_IssueSchedule PP_Order_IssueSchedule);

	ModelColumn<I_PP_Order_Qty, de.metas.handlingunits.model.I_PP_Order_IssueSchedule> COLUMN_PP_Order_IssueSchedule_ID = new ModelColumn<>(I_PP_Order_Qty.class, "PP_Order_IssueSchedule_ID", de.metas.handlingunits.model.I_PP_Order_IssueSchedule.class);
	String COLUMNNAME_PP_Order_IssueSchedule_ID = "PP_Order_IssueSchedule_ID";

	/**
	 * Set Manufacturing order Issue/Receipt quantity.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Order_Qty_ID (int PP_Order_Qty_ID);

	/**
	 * Get Manufacturing order Issue/Receipt quantity.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Order_Qty_ID();

	ModelColumn<I_PP_Order_Qty, Object> COLUMN_PP_Order_Qty_ID = new ModelColumn<>(I_PP_Order_Qty.class, "PP_Order_Qty_ID", null);
	String COLUMNNAME_PP_Order_Qty_ID = "PP_Order_Qty_ID";

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

	ModelColumn<I_PP_Order_Qty, Object> COLUMN_Processed = new ModelColumn<>(I_PP_Order_Qty.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQty (BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_PP_Order_Qty, Object> COLUMN_Qty = new ModelColumn<>(I_PP_Order_Qty.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PP_Order_Qty, Object> COLUMN_Updated = new ModelColumn<>(I_PP_Order_Qty.class, "Updated", null);
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
