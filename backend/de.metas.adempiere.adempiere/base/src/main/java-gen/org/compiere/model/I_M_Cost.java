package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Cost
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Cost 
{

	String Table_Name = "M_Cost";

//	/** AD_Table_ID=771 */
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

	ModelColumn<I_M_Cost, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new ModelColumn<>(I_M_Cost.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
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
	 * Set Costing Method.
	 * Indicates how Costs will be calculated
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setCostingMethod (@Nullable java.lang.String CostingMethod);

	/**
	 * Get Costing Method.
	 * Indicates how Costs will be calculated
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getCostingMethod();

	ModelColumn<I_M_Cost, Object> COLUMN_CostingMethod = new ModelColumn<>(I_M_Cost.class, "CostingMethod", null);
	String COLUMNNAME_CostingMethod = "CostingMethod";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Cost, Object> COLUMN_Created = new ModelColumn<>(I_M_Cost.class, "Created", null);
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
	 * Total Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCumulatedAmt (@Nullable BigDecimal CumulatedAmt);

	/**
	 * Get Accumulated Amt.
	 * Total Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCumulatedAmt();

	ModelColumn<I_M_Cost, Object> COLUMN_CumulatedAmt = new ModelColumn<>(I_M_Cost.class, "CumulatedAmt", null);
	String COLUMNNAME_CumulatedAmt = "CumulatedAmt";

	/**
	 * Set Accumulated Qty.
	 * Total Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCumulatedQty (@Nullable BigDecimal CumulatedQty);

	/**
	 * Get Accumulated Qty.
	 * Total Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCumulatedQty();

	ModelColumn<I_M_Cost, Object> COLUMN_CumulatedQty = new ModelColumn<>(I_M_Cost.class, "CumulatedQty", null);
	String COLUMNNAME_CumulatedQty = "CumulatedQty";

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
	 * Set Current Cost Price.
	 * The currently used cost price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCurrentCostPrice (BigDecimal CurrentCostPrice);

	/**
	 * Get Current Cost Price.
	 * The currently used cost price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCurrentCostPrice();

	ModelColumn<I_M_Cost, Object> COLUMN_CurrentCostPrice = new ModelColumn<>(I_M_Cost.class, "CurrentCostPrice", null);
	String COLUMNNAME_CurrentCostPrice = "CurrentCostPrice";

	/**
	 * Set Current Cost Price LL.
	 * Current Price Lower Level Is the sum of the costs of the components of this product manufactured for this level.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCurrentCostPriceLL (BigDecimal CurrentCostPriceLL);

	/**
	 * Get Current Cost Price LL.
	 * Current Price Lower Level Is the sum of the costs of the components of this product manufactured for this level.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCurrentCostPriceLL();

	ModelColumn<I_M_Cost, Object> COLUMN_CurrentCostPriceLL = new ModelColumn<>(I_M_Cost.class, "CurrentCostPriceLL", null);
	String COLUMNNAME_CurrentCostPriceLL = "CurrentCostPriceLL";

	/**
	 * Set Current Quantity.
	 * Current Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCurrentQty (BigDecimal CurrentQty);

	/**
	 * Get Current Quantity.
	 * Current Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCurrentQty();

	ModelColumn<I_M_Cost, Object> COLUMN_CurrentQty = new ModelColumn<>(I_M_Cost.class, "CurrentQty", null);
	String COLUMNNAME_CurrentQty = "CurrentQty";

	/**
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_M_Cost, Object> COLUMN_Description = new ModelColumn<>(I_M_Cost.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Future Cost Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFutureCostPrice (BigDecimal FutureCostPrice);

	/**
	 * Get Future Cost Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getFutureCostPrice();

	ModelColumn<I_M_Cost, Object> COLUMN_FutureCostPrice = new ModelColumn<>(I_M_Cost.class, "FutureCostPrice", null);
	String COLUMNNAME_FutureCostPrice = "FutureCostPrice";

	/**
	 * Set Future Cost Price LL.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFutureCostPriceLL (@Nullable BigDecimal FutureCostPriceLL);

	/**
	 * Get Future Cost Price LL.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getFutureCostPriceLL();

	ModelColumn<I_M_Cost, Object> COLUMN_FutureCostPriceLL = new ModelColumn<>(I_M_Cost.class, "FutureCostPriceLL", null);
	String COLUMNNAME_FutureCostPriceLL = "FutureCostPriceLL";

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

	ModelColumn<I_M_Cost, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Cost.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Cost Frozen.
	 * Indicated that the Standard Cost is frozen
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsCostFrozen (boolean IsCostFrozen);

	/**
	 * Get Cost Frozen.
	 * Indicated that the Standard Cost is frozen
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isCostFrozen();

	ModelColumn<I_M_Cost, Object> COLUMN_IsCostFrozen = new ModelColumn<>(I_M_Cost.class, "IsCostFrozen", null);
	String COLUMNNAME_IsCostFrozen = "IsCostFrozen";

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

	ModelColumn<I_M_Cost, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_M_Cost.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Cost Element.
	 * Product Cost Element
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_CostElement_ID (int M_CostElement_ID);

	/**
	 * Get Cost Element.
	 * Product Cost Element
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_CostElement_ID();

	org.compiere.model.I_M_CostElement getM_CostElement();

	void setM_CostElement(org.compiere.model.I_M_CostElement M_CostElement);

	ModelColumn<I_M_Cost, org.compiere.model.I_M_CostElement> COLUMN_M_CostElement_ID = new ModelColumn<>(I_M_Cost.class, "M_CostElement_ID", org.compiere.model.I_M_CostElement.class);
	String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";

	/**
	 * Set Current Product Costs.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Cost_ID (int M_Cost_ID);

	/**
	 * Get Current Product Costs.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Cost_ID();

	ModelColumn<I_M_Cost, Object> COLUMN_M_Cost_ID = new ModelColumn<>(I_M_Cost.class, "M_Cost_ID", null);
	String COLUMNNAME_M_Cost_ID = "M_Cost_ID";

	/**
	 * Set Cost Type.
	 * Type of Cost (e.g. Current, Plan, Future)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_CostType_ID (int M_CostType_ID);

	/**
	 * Get Cost Type.
	 * Type of Cost (e.g. Current, Plan, Future)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_CostType_ID();

	org.compiere.model.I_M_CostType getM_CostType();

	void setM_CostType(org.compiere.model.I_M_CostType M_CostType);

	ModelColumn<I_M_Cost, org.compiere.model.I_M_CostType> COLUMN_M_CostType_ID = new ModelColumn<>(I_M_Cost.class, "M_CostType_ID", org.compiere.model.I_M_CostType.class);
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
	 * Set Percent.
	 * Percentage
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPercent (int Percent);

	/**
	 * Get Percent.
	 * Percentage
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPercent();

	ModelColumn<I_M_Cost, Object> COLUMN_Percent = new ModelColumn<>(I_M_Cost.class, "Percent", null);
	String COLUMNNAME_Percent = "Percent";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	boolean isProcessed();

	ModelColumn<I_M_Cost, Object> COLUMN_Processed = new ModelColumn<>(I_M_Cost.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Cost, Object> COLUMN_Updated = new ModelColumn<>(I_M_Cost.class, "Updated", null);
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
