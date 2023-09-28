package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_CostDetail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_CostDetail 
{

	String Table_Name = "M_CostDetail";

//	/** AD_Table_ID=808 */
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
	 * Set Amount.
	 * Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAmt (BigDecimal Amt);

	/**
	 * Get Amount.
	 * Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmt();

	ModelColumn<I_M_CostDetail, Object> COLUMN_Amt = new ModelColumn<>(I_M_CostDetail.class, "Amt", null);
	String COLUMNNAME_Amt = "Amt";

	/**
	 * Set Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_AcctSchema_ID();

	org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

	ModelColumn<I_M_CostDetail, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new ModelColumn<>(I_M_CostDetail.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
	String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Invoice Line.
	 * Rechnungszeile
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_InvoiceLine_ID (int C_InvoiceLine_ID);

	/**
	 * Get Invoice Line.
	 * Rechnungszeile
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_InvoiceLine_ID();

	@Nullable org.compiere.model.I_C_InvoiceLine getC_InvoiceLine();

	void setC_InvoiceLine(@Nullable org.compiere.model.I_C_InvoiceLine C_InvoiceLine);

	ModelColumn<I_M_CostDetail, org.compiere.model.I_C_InvoiceLine> COLUMN_C_InvoiceLine_ID = new ModelColumn<>(I_M_CostDetail.class, "C_InvoiceLine_ID", org.compiere.model.I_C_InvoiceLine.class);
	String COLUMNNAME_C_InvoiceLine_ID = "C_InvoiceLine_ID";

	/**
	 * Set Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_ID();

	@Nullable org.compiere.model.I_C_OrderLine getC_OrderLine();

	void setC_OrderLine(@Nullable org.compiere.model.I_C_OrderLine C_OrderLine);

	ModelColumn<I_M_CostDetail, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_M_CostDetail.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set Project Issue.
	 * Project Issues (Material, Labor)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ProjectIssue_ID (int C_ProjectIssue_ID);

	/**
	 * Get Project Issue.
	 * Project Issues (Material, Labor)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ProjectIssue_ID();

	@Nullable org.compiere.model.I_C_ProjectIssue getC_ProjectIssue();

	void setC_ProjectIssue(@Nullable org.compiere.model.I_C_ProjectIssue C_ProjectIssue);

	ModelColumn<I_M_CostDetail, org.compiere.model.I_C_ProjectIssue> COLUMN_C_ProjectIssue_ID = new ModelColumn<>(I_M_CostDetail.class, "C_ProjectIssue_ID", org.compiere.model.I_C_ProjectIssue.class);
	String COLUMNNAME_C_ProjectIssue_ID = "C_ProjectIssue_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_CostDetail, Object> COLUMN_Created = new ModelColumn<>(I_M_CostDetail.class, "Created", null);
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
	 * Set Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateAcct();

	ModelColumn<I_M_CostDetail, Object> COLUMN_DateAcct = new ModelColumn<>(I_M_CostDetail.class, "DateAcct", null);
	String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Delta Amount.
	 * Difference Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeltaAmt (@Nullable BigDecimal DeltaAmt);

	/**
	 * Get Delta Amount.
	 * Difference Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDeltaAmt();

	ModelColumn<I_M_CostDetail, Object> COLUMN_DeltaAmt = new ModelColumn<>(I_M_CostDetail.class, "DeltaAmt", null);
	String COLUMNNAME_DeltaAmt = "DeltaAmt";

	/**
	 * Set Delta Quantity.
	 * Quantity Difference
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeltaQty (@Nullable BigDecimal DeltaQty);

	/**
	 * Get Delta Quantity.
	 * Quantity Difference
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDeltaQty();

	ModelColumn<I_M_CostDetail, Object> COLUMN_DeltaQty = new ModelColumn<>(I_M_CostDetail.class, "DeltaQty", null);
	String COLUMNNAME_DeltaQty = "DeltaQty";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_M_CostDetail, Object> COLUMN_Description = new ModelColumn<>(I_M_CostDetail.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_M_CostDetail, Object> COLUMN_IsActive = new ModelColumn<>(I_M_CostDetail.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Changing costs.
	 * Set if this record is changing the costs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsChangingCosts (boolean IsChangingCosts);

	/**
	 * Get Changing costs.
	 * Set if this record is changing the costs.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isChangingCosts();

	ModelColumn<I_M_CostDetail, Object> COLUMN_IsChangingCosts = new ModelColumn<>(I_M_CostDetail.class, "IsChangingCosts", null);
	String COLUMNNAME_IsChangingCosts = "IsChangingCosts";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSOTrx();

	ModelColumn<I_M_CostDetail, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_M_CostDetail.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstance_ID();

	org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

	ModelColumn<I_M_CostDetail, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_M_CostDetail.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Cost Detail.
	 * Cost Detail Information
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_CostDetail_ID (int M_CostDetail_ID);

	/**
	 * Get Cost Detail.
	 * Cost Detail Information
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_CostDetail_ID();

	ModelColumn<I_M_CostDetail, Object> COLUMN_M_CostDetail_ID = new ModelColumn<>(I_M_CostDetail.class, "M_CostDetail_ID", null);
	String COLUMNNAME_M_CostDetail_ID = "M_CostDetail_ID";

	/**
	 * Set Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_CostDetail_Type (java.lang.String M_CostDetail_Type);

	/**
	 * Get Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getM_CostDetail_Type();

	ModelColumn<I_M_CostDetail, Object> COLUMN_M_CostDetail_Type = new ModelColumn<>(I_M_CostDetail.class, "M_CostDetail_Type", null);
	String COLUMNNAME_M_CostDetail_Type = "M_CostDetail_Type";

	/**
	 * Set Cost Element.
	 * Product Cost Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_CostElement_ID (int M_CostElement_ID);

	/**
	 * Get Cost Element.
	 * Product Cost Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_CostElement_ID();

	@Nullable org.compiere.model.I_M_CostElement getM_CostElement();

	void setM_CostElement(@Nullable org.compiere.model.I_M_CostElement M_CostElement);

	ModelColumn<I_M_CostDetail, org.compiere.model.I_M_CostElement> COLUMN_M_CostElement_ID = new ModelColumn<>(I_M_CostDetail.class, "M_CostElement_ID", org.compiere.model.I_M_CostElement.class);
	String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";

	/**
	 * Set Cost Revaluation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_CostRevaluation_ID (int M_CostRevaluation_ID);

	/**
	 * Get Cost Revaluation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_CostRevaluation_ID();

	@Nullable org.compiere.model.I_M_CostRevaluation getM_CostRevaluation();

	void setM_CostRevaluation(@Nullable org.compiere.model.I_M_CostRevaluation M_CostRevaluation);

	ModelColumn<I_M_CostDetail, org.compiere.model.I_M_CostRevaluation> COLUMN_M_CostRevaluation_ID = new ModelColumn<>(I_M_CostDetail.class, "M_CostRevaluation_ID", org.compiere.model.I_M_CostRevaluation.class);
	String COLUMNNAME_M_CostRevaluation_ID = "M_CostRevaluation_ID";

	/**
	 * Set Cost Revaluation Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_CostRevaluationLine_ID (int M_CostRevaluationLine_ID);

	/**
	 * Get Cost Revaluation Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_CostRevaluationLine_ID();

	@Nullable org.compiere.model.I_M_CostRevaluationLine getM_CostRevaluationLine();

	void setM_CostRevaluationLine(@Nullable org.compiere.model.I_M_CostRevaluationLine M_CostRevaluationLine);

	ModelColumn<I_M_CostDetail, org.compiere.model.I_M_CostRevaluationLine> COLUMN_M_CostRevaluationLine_ID = new ModelColumn<>(I_M_CostDetail.class, "M_CostRevaluationLine_ID", org.compiere.model.I_M_CostRevaluationLine.class);
	String COLUMNNAME_M_CostRevaluationLine_ID = "M_CostRevaluationLine_ID";

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

	ModelColumn<I_M_CostDetail, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new ModelColumn<>(I_M_CostDetail.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
	String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Phys.Inventory Line.
	 * Unique line in an Inventory document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InventoryLine_ID (int M_InventoryLine_ID);

	/**
	 * Get Phys.Inventory Line.
	 * Unique line in an Inventory document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InventoryLine_ID();

	@Nullable org.compiere.model.I_M_InventoryLine getM_InventoryLine();

	void setM_InventoryLine(@Nullable org.compiere.model.I_M_InventoryLine M_InventoryLine);

	ModelColumn<I_M_CostDetail, org.compiere.model.I_M_InventoryLine> COLUMN_M_InventoryLine_ID = new ModelColumn<>(I_M_CostDetail.class, "M_InventoryLine_ID", org.compiere.model.I_M_InventoryLine.class);
	String COLUMNNAME_M_InventoryLine_ID = "M_InventoryLine_ID";

	/**
	 * Set Match Invoice.
	 * Match Shipment/Receipt to Invoice
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_MatchInv_ID (int M_MatchInv_ID);

	/**
	 * Get Match Invoice.
	 * Match Shipment/Receipt to Invoice
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_MatchInv_ID();

	@Nullable org.compiere.model.I_M_MatchInv getM_MatchInv();

	void setM_MatchInv(@Nullable org.compiere.model.I_M_MatchInv M_MatchInv);

	ModelColumn<I_M_CostDetail, org.compiere.model.I_M_MatchInv> COLUMN_M_MatchInv_ID = new ModelColumn<>(I_M_CostDetail.class, "M_MatchInv_ID", org.compiere.model.I_M_MatchInv.class);
	String COLUMNNAME_M_MatchInv_ID = "M_MatchInv_ID";

	/**
	 * Set Match PO.
	 * Match Purchase Order to Shipment/Receipt and Invoice
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_MatchPO_ID (int M_MatchPO_ID);

	/**
	 * Get Match PO.
	 * Match Purchase Order to Shipment/Receipt and Invoice
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_MatchPO_ID();

	@Nullable org.compiere.model.I_M_MatchPO getM_MatchPO();

	void setM_MatchPO(@Nullable org.compiere.model.I_M_MatchPO M_MatchPO);

	ModelColumn<I_M_CostDetail, org.compiere.model.I_M_MatchPO> COLUMN_M_MatchPO_ID = new ModelColumn<>(I_M_CostDetail.class, "M_MatchPO_ID", org.compiere.model.I_M_MatchPO.class);
	String COLUMNNAME_M_MatchPO_ID = "M_MatchPO_ID";

	/**
	 * Set Move Line.
	 * Inventory Move document Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_MovementLine_ID (int M_MovementLine_ID);

	/**
	 * Get Move Line.
	 * Inventory Move document Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_MovementLine_ID();

	@Nullable org.compiere.model.I_M_MovementLine getM_MovementLine();

	void setM_MovementLine(@Nullable org.compiere.model.I_M_MovementLine M_MovementLine);

	ModelColumn<I_M_CostDetail, org.compiere.model.I_M_MovementLine> COLUMN_M_MovementLine_ID = new ModelColumn<>(I_M_CostDetail.class, "M_MovementLine_ID", org.compiere.model.I_M_MovementLine.class);
	String COLUMNNAME_M_MovementLine_ID = "M_MovementLine_ID";

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
	 * Set Shipping Notification Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Shipping_NotificationLine_ID (int M_Shipping_NotificationLine_ID);

	/**
	 * Get Shipping Notification Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Shipping_NotificationLine_ID();

	ModelColumn<I_M_CostDetail, Object> COLUMN_M_Shipping_NotificationLine_ID = new ModelColumn<>(I_M_CostDetail.class, "M_Shipping_NotificationLine_ID", null);
	String COLUMNNAME_M_Shipping_NotificationLine_ID = "M_Shipping_NotificationLine_ID";

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

	ModelColumn<I_M_CostDetail, org.eevolution.model.I_PP_Cost_Collector> COLUMN_PP_Cost_Collector_ID = new ModelColumn<>(I_M_CostDetail.class, "PP_Cost_Collector_ID", org.eevolution.model.I_PP_Cost_Collector.class);
	String COLUMNNAME_PP_Cost_Collector_ID = "PP_Cost_Collector_ID";

	/**
	 * Set Previous Cumulated Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPrev_CumulatedAmt (BigDecimal Prev_CumulatedAmt);

	/**
	 * Get Previous Cumulated Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPrev_CumulatedAmt();

	ModelColumn<I_M_CostDetail, Object> COLUMN_Prev_CumulatedAmt = new ModelColumn<>(I_M_CostDetail.class, "Prev_CumulatedAmt", null);
	String COLUMNNAME_Prev_CumulatedAmt = "Prev_CumulatedAmt";

	/**
	 * Set Previous Cumulated Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPrev_CumulatedQty (BigDecimal Prev_CumulatedQty);

	/**
	 * Get Previous Cumulated Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPrev_CumulatedQty();

	ModelColumn<I_M_CostDetail, Object> COLUMN_Prev_CumulatedQty = new ModelColumn<>(I_M_CostDetail.class, "Prev_CumulatedQty", null);
	String COLUMNNAME_Prev_CumulatedQty = "Prev_CumulatedQty";

	/**
	 * Set Previous Current Cost Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPrev_CurrentCostPrice (BigDecimal Prev_CurrentCostPrice);

	/**
	 * Get Previous Current Cost Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPrev_CurrentCostPrice();

	ModelColumn<I_M_CostDetail, Object> COLUMN_Prev_CurrentCostPrice = new ModelColumn<>(I_M_CostDetail.class, "Prev_CurrentCostPrice", null);
	String COLUMNNAME_Prev_CurrentCostPrice = "Prev_CurrentCostPrice";

	/**
	 * Set Previous Current Cost Price LL.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPrev_CurrentCostPriceLL (BigDecimal Prev_CurrentCostPriceLL);

	/**
	 * Get Previous Current Cost Price LL.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPrev_CurrentCostPriceLL();

	ModelColumn<I_M_CostDetail, Object> COLUMN_Prev_CurrentCostPriceLL = new ModelColumn<>(I_M_CostDetail.class, "Prev_CurrentCostPriceLL", null);
	String COLUMNNAME_Prev_CurrentCostPriceLL = "Prev_CurrentCostPriceLL";

	/**
	 * Set Previous Current Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPrev_CurrentQty (BigDecimal Prev_CurrentQty);

	/**
	 * Get Previous Current Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPrev_CurrentQty();

	ModelColumn<I_M_CostDetail, Object> COLUMN_Prev_CurrentQty = new ModelColumn<>(I_M_CostDetail.class, "Prev_CurrentQty", null);
	String COLUMNNAME_Prev_CurrentQty = "Prev_CurrentQty";

	/**
	 * Set Price.
	 * Price
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setPrice (@Nullable BigDecimal Price);

	/**
	 * Get Price.
	 * Price
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	BigDecimal getPrice();

	ModelColumn<I_M_CostDetail, Object> COLUMN_Price = new ModelColumn<>(I_M_CostDetail.class, "Price", null);
	String COLUMNNAME_Price = "Price";

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

	ModelColumn<I_M_CostDetail, Object> COLUMN_Processed = new ModelColumn<>(I_M_CostDetail.class, "Processed", null);
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

	ModelColumn<I_M_CostDetail, Object> COLUMN_Qty = new ModelColumn<>(I_M_CostDetail.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Amount in document currency.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSourceAmt (@Nullable BigDecimal SourceAmt);

	/**
	 * Get Amount in document currency.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getSourceAmt();

	ModelColumn<I_M_CostDetail, Object> COLUMN_SourceAmt = new ModelColumn<>(I_M_CostDetail.class, "SourceAmt", null);
	String COLUMNNAME_SourceAmt = "SourceAmt";

	/**
	 * Set Document currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSource_Currency_ID (int Source_Currency_ID);

	/**
	 * Get Document currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSource_Currency_ID();

	String COLUMNNAME_Source_Currency_ID = "Source_Currency_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_CostDetail, Object> COLUMN_Updated = new ModelColumn<>(I_M_CostDetail.class, "Updated", null);
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
