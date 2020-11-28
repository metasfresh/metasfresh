package org.eevolution.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

import java.math.BigDecimal;

/** Generated Interface for PP_Order_Cost
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PP_Order_Cost 
{

	String Table_Name = "PP_Order_Cost";

//	/** AD_Table_ID=53024 */
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

	ModelColumn<I_PP_Order_Cost, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new ModelColumn<>(I_PP_Order_Cost.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
	String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

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
	 * Set Cost Distribution Percent.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCostDistributionPercent (@Nullable BigDecimal CostDistributionPercent);

	/**
	 * Get Cost Distribution Percent.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCostDistributionPercent();

	ModelColumn<I_PP_Order_Cost, Object> COLUMN_CostDistributionPercent = new ModelColumn<>(I_PP_Order_Cost.class, "CostDistributionPercent", null);
	String COLUMNNAME_CostDistributionPercent = "CostDistributionPercent";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_PP_Order_Cost, Object> COLUMN_Created = new ModelColumn<>(I_PP_Order_Cost.class, "Created", null);
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
	 * Set Accumulated Amt.
	 * Gesamt Betrag
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCumulatedAmt (@Nullable BigDecimal CumulatedAmt);

	/**
	 * Get Accumulated Amt.
	 * Gesamt Betrag
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCumulatedAmt();

	ModelColumn<I_PP_Order_Cost, Object> COLUMN_CumulatedAmt = new ModelColumn<>(I_PP_Order_Cost.class, "CumulatedAmt", null);
	String COLUMNNAME_CumulatedAmt = "CumulatedAmt";

	/**
	 * Set Accumulated Qty.
	 * Gesamt Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCumulatedQty (@Nullable BigDecimal CumulatedQty);

	/**
	 * Get Accumulated Qty.
	 * Gesamt Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCumulatedQty();

	ModelColumn<I_PP_Order_Cost, Object> COLUMN_CumulatedQty = new ModelColumn<>(I_PP_Order_Cost.class, "CumulatedQty", null);
	String COLUMNNAME_CumulatedQty = "CumulatedQty";

	/**
	 * Set Gegenwärtiger Kostenpreis.
	 * Der gegenwärtig verwendete Kostenpreis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurrentCostPrice (@Nullable BigDecimal CurrentCostPrice);

	/**
	 * Get Gegenwärtiger Kostenpreis.
	 * Der gegenwärtig verwendete Kostenpreis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCurrentCostPrice();

	ModelColumn<I_PP_Order_Cost, Object> COLUMN_CurrentCostPrice = new ModelColumn<>(I_PP_Order_Cost.class, "CurrentCostPrice", null);
	String COLUMNNAME_CurrentCostPrice = "CurrentCostPrice";

	/**
	 * Set Current Cost Price Lower Level.
	 * Current Price Lower Level Is the sum of the costs of the components of this product manufactured for this level.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurrentCostPriceLL (@Nullable BigDecimal CurrentCostPriceLL);

	/**
	 * Get Current Cost Price Lower Level.
	 * Current Price Lower Level Is the sum of the costs of the components of this product manufactured for this level.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCurrentCostPriceLL();

	ModelColumn<I_PP_Order_Cost, Object> COLUMN_CurrentCostPriceLL = new ModelColumn<>(I_PP_Order_Cost.class, "CurrentCostPriceLL", null);
	String COLUMNNAME_CurrentCostPriceLL = "CurrentCostPriceLL";

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

	ModelColumn<I_PP_Order_Cost, Object> COLUMN_IsActive = new ModelColumn<>(I_PP_Order_Cost.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_PP_Order_Cost, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_PP_Order_Cost.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Kostenart.
	 * Product Cost Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_CostElement_ID (int M_CostElement_ID);

	/**
	 * Get Kostenart.
	 * Product Cost Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_CostElement_ID();

	@Nullable org.compiere.model.I_M_CostElement getM_CostElement();

	void setM_CostElement(@Nullable org.compiere.model.I_M_CostElement M_CostElement);

	ModelColumn<I_PP_Order_Cost, org.compiere.model.I_M_CostElement> COLUMN_M_CostElement_ID = new ModelColumn<>(I_PP_Order_Cost.class, "M_CostElement_ID", org.compiere.model.I_M_CostElement.class);
	String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";

	/**
	 * Set Kostenkategorie.
	 * Type of Cost (e.g. Current, Plan, Future)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_CostType_ID (int M_CostType_ID);

	/**
	 * Get Kostenkategorie.
	 * Type of Cost (e.g. Current, Plan, Future)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_CostType_ID();

	org.compiere.model.I_M_CostType getM_CostType();

	void setM_CostType(org.compiere.model.I_M_CostType M_CostType);

	ModelColumn<I_PP_Order_Cost, org.compiere.model.I_M_CostType> COLUMN_M_CostType_ID = new ModelColumn<>(I_PP_Order_Cost.class, "M_CostType_ID", org.compiere.model.I_M_CostType.class);
	String COLUMNNAME_M_CostType_ID = "M_CostType_ID";

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
	 * Set Post Calculation Amount.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPostCalculationAmt (BigDecimal PostCalculationAmt);

	/**
	 * Get Post Calculation Amount.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getPostCalculationAmt();

	ModelColumn<I_PP_Order_Cost, Object> COLUMN_PostCalculationAmt = new ModelColumn<>(I_PP_Order_Cost.class, "PostCalculationAmt", null);
	String COLUMNNAME_PostCalculationAmt = "PostCalculationAmt";

	/**
	 * Set Manufacturing Order Cost.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Order_Cost_ID (int PP_Order_Cost_ID);

	/**
	 * Get Manufacturing Order Cost.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Order_Cost_ID();

	ModelColumn<I_PP_Order_Cost, Object> COLUMN_PP_Order_Cost_ID = new ModelColumn<>(I_PP_Order_Cost.class, "PP_Order_Cost_ID", null);
	String COLUMNNAME_PP_Order_Cost_ID = "PP_Order_Cost_ID";

	/**
	 * Set Transaction Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Order_Cost_TrxType (@Nullable java.lang.String PP_Order_Cost_TrxType);

	/**
	 * Get Transaction Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPP_Order_Cost_TrxType();

	ModelColumn<I_PP_Order_Cost, Object> COLUMN_PP_Order_Cost_TrxType = new ModelColumn<>(I_PP_Order_Cost.class, "PP_Order_Cost_TrxType", null);
	String COLUMNNAME_PP_Order_Cost_TrxType = "PP_Order_Cost_TrxType";

	/**
	 * Set Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPP_Order_ID (int PP_Order_ID);

	/**
	 * Get Manufacturing Order.
	 * Manufacturing Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPP_Order_ID();

	org.eevolution.model.I_PP_Order getPP_Order();

	void setPP_Order(org.eevolution.model.I_PP_Order PP_Order);

	ModelColumn<I_PP_Order_Cost, org.eevolution.model.I_PP_Order> COLUMN_PP_Order_ID = new ModelColumn<>(I_PP_Order_Cost.class, "PP_Order_ID", org.eevolution.model.I_PP_Order.class);
	String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PP_Order_Cost, Object> COLUMN_Updated = new ModelColumn<>(I_PP_Order_Cost.class, "Updated", null);
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
