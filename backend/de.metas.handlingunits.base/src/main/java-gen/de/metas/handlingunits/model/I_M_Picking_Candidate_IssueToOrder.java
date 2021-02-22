package de.metas.handlingunits.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Picking_Candidate_IssueToOrder
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Picking_Candidate_IssueToOrder 
{

	String Table_Name = "M_Picking_Candidate_IssueToOrder";

//	/** AD_Table_ID=541430 */
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

	ModelColumn<I_M_Picking_Candidate_IssueToOrder, Object> COLUMN_Created = new ModelColumn<>(I_M_Picking_Candidate_IssueToOrder.class, "Created", null);
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

	ModelColumn<I_M_Picking_Candidate_IssueToOrder, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Picking_Candidate_IssueToOrder.class, "IsActive", null);
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

	ModelColumn<I_M_Picking_Candidate_IssueToOrder, de.metas.handlingunits.model.I_M_HU> COLUMN_M_HU_ID = new ModelColumn<>(I_M_Picking_Candidate_IssueToOrder.class, "M_HU_ID", de.metas.handlingunits.model.I_M_HU.class);
	String COLUMNNAME_M_HU_ID = "M_HU_ID";

	/**
	 * Set Picking candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Candidate_ID (int M_Picking_Candidate_ID);

	/**
	 * Get Picking candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Candidate_ID();

	de.metas.handlingunits.model.I_M_Picking_Candidate getM_Picking_Candidate();

	void setM_Picking_Candidate(de.metas.handlingunits.model.I_M_Picking_Candidate M_Picking_Candidate);

	ModelColumn<I_M_Picking_Candidate_IssueToOrder, de.metas.handlingunits.model.I_M_Picking_Candidate> COLUMN_M_Picking_Candidate_ID = new ModelColumn<>(I_M_Picking_Candidate_IssueToOrder.class, "M_Picking_Candidate_ID", de.metas.handlingunits.model.I_M_Picking_Candidate.class);
	String COLUMNNAME_M_Picking_Candidate_ID = "M_Picking_Candidate_ID";

	/**
	 * Set Picking Candidate - issue to manufacturing order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Picking_Candidate_IssueToOrder_ID (int M_Picking_Candidate_IssueToOrder_ID);

	/**
	 * Get Picking Candidate - issue to manufacturing order.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Picking_Candidate_IssueToOrder_ID();

	ModelColumn<I_M_Picking_Candidate_IssueToOrder, Object> COLUMN_M_Picking_Candidate_IssueToOrder_ID = new ModelColumn<>(I_M_Picking_Candidate_IssueToOrder.class, "M_Picking_Candidate_IssueToOrder_ID", null);
	String COLUMNNAME_M_Picking_Candidate_IssueToOrder_ID = "M_Picking_Candidate_IssueToOrder_ID";

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

	ModelColumn<I_M_Picking_Candidate_IssueToOrder, org.eevolution.model.I_PP_Cost_Collector> COLUMN_PP_Cost_Collector_ID = new ModelColumn<>(I_M_Picking_Candidate_IssueToOrder.class, "PP_Cost_Collector_ID", org.eevolution.model.I_PP_Cost_Collector.class);
	String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";

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

	ModelColumn<I_M_Picking_Candidate_IssueToOrder, org.eevolution.model.I_PP_Order_BOMLine> COLUMN_PP_Order_BOMLine_ID = new ModelColumn<>(I_M_Picking_Candidate_IssueToOrder.class, "PP_Order_BOMLine_ID", org.eevolution.model.I_PP_Order_BOMLine.class);
	String COLUMNNAME_PP_Order_BOMLine_ID = "PP_Order_BOMLine_ID";

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

	ModelColumn<I_M_Picking_Candidate_IssueToOrder, Object> COLUMN_QtyToIssue = new ModelColumn<>(I_M_Picking_Candidate_IssueToOrder.class, "QtyToIssue", null);
	String COLUMNNAME_QtyToIssue = "QtyToIssue";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Picking_Candidate_IssueToOrder, Object> COLUMN_Updated = new ModelColumn<>(I_M_Picking_Candidate_IssueToOrder.class, "Updated", null);
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
