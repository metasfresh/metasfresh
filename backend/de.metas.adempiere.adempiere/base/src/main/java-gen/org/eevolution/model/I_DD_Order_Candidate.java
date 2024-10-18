package org.eevolution.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for DD_Order_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_DD_Order_Candidate 
{

	String Table_Name = "DD_Order_Candidate";

//	/** AD_Table_ID=542424 */
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
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Sales Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLineSO_ID (int C_OrderLineSO_ID);

	/**
	 * Get Sales Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLineSO_ID();

	@Nullable org.compiere.model.I_C_OrderLine getC_OrderLineSO();

	void setC_OrderLineSO(@Nullable org.compiere.model.I_C_OrderLine C_OrderLineSO);

	ModelColumn<I_DD_Order_Candidate, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLineSO_ID = new ModelColumn<>(I_DD_Order_Candidate.class, "C_OrderLineSO_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLineSO_ID = "C_OrderLineSO_ID";

	/**
	 * Set Order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderSO_ID (int C_OrderSO_ID);

	/**
	 * Get Order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderSO_ID();

	@Nullable org.compiere.model.I_C_Order getC_OrderSO();

	void setC_OrderSO(@Nullable org.compiere.model.I_C_Order C_OrderSO);

	ModelColumn<I_DD_Order_Candidate, org.compiere.model.I_C_Order> COLUMN_C_OrderSO_ID = new ModelColumn<>(I_DD_Order_Candidate.class, "C_OrderSO_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_OrderSO_ID = "C_OrderSO_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_DD_Order_Candidate, Object> COLUMN_Created = new ModelColumn<>(I_DD_Order_Candidate.class, "Created", null);
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
	 * Set Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateOrdered (java.sql.Timestamp DateOrdered);

	/**
	 * Get Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateOrdered();

	ModelColumn<I_DD_Order_Candidate, Object> COLUMN_DateOrdered = new ModelColumn<>(I_DD_Order_Candidate.class, "DateOrdered", null);
	String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Network Distribution.
	 * Identifies a distribution network, distribution networks are used to establish the source and target of the materials in the supply chain
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDD_NetworkDistribution_ID (int DD_NetworkDistribution_ID);

	/**
	 * Get Network Distribution.
	 * Identifies a distribution network, distribution networks are used to establish the source and target of the materials in the supply chain
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDD_NetworkDistribution_ID();

	@Nullable org.eevolution.model.I_DD_NetworkDistribution getDD_NetworkDistribution();

	void setDD_NetworkDistribution(@Nullable org.eevolution.model.I_DD_NetworkDistribution DD_NetworkDistribution);

	ModelColumn<I_DD_Order_Candidate, org.eevolution.model.I_DD_NetworkDistribution> COLUMN_DD_NetworkDistribution_ID = new ModelColumn<>(I_DD_Order_Candidate.class, "DD_NetworkDistribution_ID", org.eevolution.model.I_DD_NetworkDistribution.class);
	String COLUMNNAME_DD_NetworkDistribution_ID = "DD_NetworkDistribution_ID";

	/**
	 * Set Network Distribution Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDD_NetworkDistributionLine_ID (int DD_NetworkDistributionLine_ID);

	/**
	 * Get Network Distribution Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDD_NetworkDistributionLine_ID();

	@Nullable org.eevolution.model.I_DD_NetworkDistributionLine getDD_NetworkDistributionLine();

	void setDD_NetworkDistributionLine(@Nullable org.eevolution.model.I_DD_NetworkDistributionLine DD_NetworkDistributionLine);

	ModelColumn<I_DD_Order_Candidate, org.eevolution.model.I_DD_NetworkDistributionLine> COLUMN_DD_NetworkDistributionLine_ID = new ModelColumn<>(I_DD_Order_Candidate.class, "DD_NetworkDistributionLine_ID", org.eevolution.model.I_DD_NetworkDistributionLine.class);
	String COLUMNNAME_DD_NetworkDistributionLine_ID = "DD_NetworkDistributionLine_ID";

	/**
	 * Set Distribution Candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDD_Order_Candidate_ID (int DD_Order_Candidate_ID);

	/**
	 * Get Distribution Candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDD_Order_Candidate_ID();

	ModelColumn<I_DD_Order_Candidate, Object> COLUMN_DD_Order_Candidate_ID = new ModelColumn<>(I_DD_Order_Candidate.class, "DD_Order_Candidate_ID", null);
	String COLUMNNAME_DD_Order_Candidate_ID = "DD_Order_Candidate_ID";

	/**
	 * Set Demand Date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDemandDate (java.sql.Timestamp DemandDate);

	/**
	 * Get Demand Date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDemandDate();

	ModelColumn<I_DD_Order_Candidate, Object> COLUMN_DemandDate = new ModelColumn<>(I_DD_Order_Candidate.class, "DemandDate", null);
	String COLUMNNAME_DemandDate = "DemandDate";

	/**
	 * Set Forward Manufacturing Order BOM Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setForward_PP_Order_BOMLine_ID (int Forward_PP_Order_BOMLine_ID);

	/**
	 * Get Forward Manufacturing Order BOM Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getForward_PP_Order_BOMLine_ID();

	@Nullable org.eevolution.model.I_PP_Order_BOMLine getForward_PP_Order_BOMLine();

	void setForward_PP_Order_BOMLine(@Nullable org.eevolution.model.I_PP_Order_BOMLine Forward_PP_Order_BOMLine);

	ModelColumn<I_DD_Order_Candidate, org.eevolution.model.I_PP_Order_BOMLine> COLUMN_Forward_PP_Order_BOMLine_ID = new ModelColumn<>(I_DD_Order_Candidate.class, "Forward_PP_Order_BOMLine_ID", org.eevolution.model.I_PP_Order_BOMLine.class);
	String COLUMNNAME_Forward_PP_Order_BOMLine_ID = "Forward_PP_Order_BOMLine_ID";

	/**
	 * Set Produktionsdisposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setForward_PP_Order_Candidate_ID (int Forward_PP_Order_Candidate_ID);

	/**
	 * Get Produktionsdisposition.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getForward_PP_Order_Candidate_ID();

	@Nullable org.eevolution.model.I_PP_Order_Candidate getForward_PP_Order_Candidate();

	void setForward_PP_Order_Candidate(@Nullable org.eevolution.model.I_PP_Order_Candidate Forward_PP_Order_Candidate);

	ModelColumn<I_DD_Order_Candidate, org.eevolution.model.I_PP_Order_Candidate> COLUMN_Forward_PP_Order_Candidate_ID = new ModelColumn<>(I_DD_Order_Candidate.class, "Forward_PP_Order_Candidate_ID", org.eevolution.model.I_PP_Order_Candidate.class);
	String COLUMNNAME_Forward_PP_Order_Candidate_ID = "Forward_PP_Order_Candidate_ID";

	/**
	 * Set Forward Manufacturing Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setForward_PP_Order_ID (int Forward_PP_Order_ID);

	/**
	 * Get Forward Manufacturing Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getForward_PP_Order_ID();

	@Nullable org.eevolution.model.I_PP_Order getForward_PP_Order();

	void setForward_PP_Order(@Nullable org.eevolution.model.I_PP_Order Forward_PP_Order);

	ModelColumn<I_DD_Order_Candidate, org.eevolution.model.I_PP_Order> COLUMN_Forward_PP_Order_ID = new ModelColumn<>(I_DD_Order_Candidate.class, "Forward_PP_Order_ID", org.eevolution.model.I_PP_Order.class);
	String COLUMNNAME_Forward_PP_Order_ID = "Forward_PP_Order_ID";

	/**
	 * Set Manufacturing Order Line Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setForward_PP_OrderLine_Candidate_ID (int Forward_PP_OrderLine_Candidate_ID);

	/**
	 * Get Manufacturing Order Line Candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getForward_PP_OrderLine_Candidate_ID();

	@Nullable org.eevolution.model.I_PP_OrderLine_Candidate getForward_PP_OrderLine_Candidate();

	void setForward_PP_OrderLine_Candidate(@Nullable org.eevolution.model.I_PP_OrderLine_Candidate Forward_PP_OrderLine_Candidate);

	ModelColumn<I_DD_Order_Candidate, org.eevolution.model.I_PP_OrderLine_Candidate> COLUMN_Forward_PP_OrderLine_Candidate_ID = new ModelColumn<>(I_DD_Order_Candidate.class, "Forward_PP_OrderLine_Candidate_ID", org.eevolution.model.I_PP_OrderLine_Candidate.class);
	String COLUMNNAME_Forward_PP_OrderLine_Candidate_ID = "Forward_PP_OrderLine_Candidate_ID";

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

	ModelColumn<I_DD_Order_Candidate, Object> COLUMN_IsActive = new ModelColumn<>(I_DD_Order_Candidate.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Simulated.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSimulated (boolean IsSimulated);

	/**
	 * Get Simulated.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSimulated();

	ModelColumn<I_DD_Order_Candidate, Object> COLUMN_IsSimulated = new ModelColumn<>(I_DD_Order_Candidate.class, "IsSimulated", null);
	String COLUMNNAME_IsSimulated = "IsSimulated";

	/**
	 * Set Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstance_ID();

	@Nullable org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	void setM_AttributeSetInstance(@Nullable org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

	ModelColumn<I_DD_Order_Candidate, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_DD_Order_Candidate.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

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

	ModelColumn<I_DD_Order_Candidate, Object> COLUMN_M_HU_PI_Item_Product_ID = new ModelColumn<>(I_DD_Order_Candidate.class, "M_HU_PI_Item_Product_ID", null);
	String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

	/**
	 * Set Von Lagerort.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_LocatorFrom_ID (int M_LocatorFrom_ID);

	/**
	 * Get Von Lagerort.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_LocatorFrom_ID();

	String COLUMNNAME_M_LocatorFrom_ID = "M_LocatorFrom_ID";

	/**
	 * Set Locator to.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_LocatorTo_ID (int M_LocatorTo_ID);

	/**
	 * Get Locator to.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_LocatorTo_ID();

	String COLUMNNAME_M_LocatorTo_ID = "M_LocatorTo_ID";

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
	 * Set Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Shipper_ID();

	org.compiere.model.I_M_Shipper getM_Shipper();

	void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper);

	ModelColumn<I_DD_Order_Candidate, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_DD_Order_Candidate.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Warehouse from.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_From_ID (int M_Warehouse_From_ID);

	/**
	 * Get Warehouse from.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_From_ID();

	String COLUMNNAME_M_Warehouse_From_ID = "M_Warehouse_From_ID";

	/**
	 * Set Warehouse to.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_WarehouseTo_ID (int M_WarehouseTo_ID);

	/**
	 * Get Warehouse to.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_WarehouseTo_ID();

	String COLUMNNAME_M_WarehouseTo_ID = "M_WarehouseTo_ID";

	/**
	 * Set Plant from.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Plant_From_ID (int PP_Plant_From_ID);

	/**
	 * Get Plant from.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Plant_From_ID();

	@Nullable org.compiere.model.I_S_Resource getPP_Plant_From();

	void setPP_Plant_From(@Nullable org.compiere.model.I_S_Resource PP_Plant_From);

	ModelColumn<I_DD_Order_Candidate, org.compiere.model.I_S_Resource> COLUMN_PP_Plant_From_ID = new ModelColumn<>(I_DD_Order_Candidate.class, "PP_Plant_From_ID", org.compiere.model.I_S_Resource.class);
	String COLUMNNAME_PP_Plant_From_ID = "PP_Plant_From_ID";

	/**
	 * Set Plant To.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Plant_To_ID (int PP_Plant_To_ID);

	/**
	 * Get Plant To.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Plant_To_ID();

	@Nullable org.compiere.model.I_S_Resource getPP_Plant_To();

	void setPP_Plant_To(@Nullable org.compiere.model.I_S_Resource PP_Plant_To);

	ModelColumn<I_DD_Order_Candidate, org.compiere.model.I_S_Resource> COLUMN_PP_Plant_To_ID = new ModelColumn<>(I_DD_Order_Candidate.class, "PP_Plant_To_ID", org.compiere.model.I_S_Resource.class);
	String COLUMNNAME_PP_Plant_To_ID = "PP_Plant_To_ID";

	/**
	 * Set Product Planning.
	 * Product Planning
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Product_Planning_ID (int PP_Product_Planning_ID);

	/**
	 * Get Product Planning.
	 * Product Planning
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Product_Planning_ID();

	String COLUMNNAME_PP_Product_Planning_ID = "PP_Product_Planning_ID";

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

	ModelColumn<I_DD_Order_Candidate, Object> COLUMN_Processed = new ModelColumn<>(I_DD_Order_Candidate.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyEntered (BigDecimal QtyEntered);

	/**
	 * Get Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEntered();

	ModelColumn<I_DD_Order_Candidate, Object> COLUMN_QtyEntered = new ModelColumn<>(I_DD_Order_Candidate.class, "QtyEntered", null);
	String COLUMNNAME_QtyEntered = "QtyEntered";

	/**
	 * Set Quantity TU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyEnteredTU (@Nullable BigDecimal QtyEnteredTU);

	/**
	 * Get Quantity TU.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEnteredTU();

	ModelColumn<I_DD_Order_Candidate, Object> COLUMN_QtyEnteredTU = new ModelColumn<>(I_DD_Order_Candidate.class, "QtyEnteredTU", null);
	String COLUMNNAME_QtyEnteredTU = "QtyEnteredTU";

	/**
	 * Set Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyOrdered (BigDecimal QtyOrdered);

	/**
	 * Get Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrdered();

	ModelColumn<I_DD_Order_Candidate, Object> COLUMN_QtyOrdered = new ModelColumn<>(I_DD_Order_Candidate.class, "QtyOrdered", null);
	String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set Quantity Processed.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyProcessed (BigDecimal QtyProcessed);

	/**
	 * Get Quantity Processed.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyProcessed();

	ModelColumn<I_DD_Order_Candidate, Object> COLUMN_QtyProcessed = new ModelColumn<>(I_DD_Order_Candidate.class, "QtyProcessed", null);
	String COLUMNNAME_QtyProcessed = "QtyProcessed";

	/**
	 * Set Quantity To Process.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyToProcess (BigDecimal QtyToProcess);

	/**
	 * Get Quantity To Process.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToProcess();

	ModelColumn<I_DD_Order_Candidate, Object> COLUMN_QtyToProcess = new ModelColumn<>(I_DD_Order_Candidate.class, "QtyToProcess", null);
	String COLUMNNAME_QtyToProcess = "QtyToProcess";

	/**
	 * Set Supply Date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSupplyDate (java.sql.Timestamp SupplyDate);

	/**
	 * Get Supply Date.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getSupplyDate();

	ModelColumn<I_DD_Order_Candidate, Object> COLUMN_SupplyDate = new ModelColumn<>(I_DD_Order_Candidate.class, "SupplyDate", null);
	String COLUMNNAME_SupplyDate = "SupplyDate";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_DD_Order_Candidate, Object> COLUMN_Updated = new ModelColumn<>(I_DD_Order_Candidate.class, "Updated", null);
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
