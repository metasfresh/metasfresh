package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_CostRevaluation_Detail
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_CostRevaluation_Detail 
{

	String Table_Name = "M_CostRevaluation_Detail";

//	/** AD_Table_ID=542204 */
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
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_AcctSchema_ID();

	org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

	ModelColumn<I_M_CostRevaluation_Detail, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
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
	 * Set Costing Level.
	 * The lowest level to accumulate Costing Information
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCostingLevel (java.lang.String CostingLevel);

	/**
	 * Get Costing Level.
	 * The lowest level to accumulate Costing Information
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getCostingLevel();

	ModelColumn<I_M_CostRevaluation_Detail, Object> COLUMN_CostingLevel = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "CostingLevel", null);
	String COLUMNNAME_CostingLevel = "CostingLevel";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_CostRevaluation_Detail, Object> COLUMN_Created = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "Created", null);
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
	 * Set Delta Amount.
	 * Difference Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDeltaAmt (BigDecimal DeltaAmt);

	/**
	 * Get Delta Amount.
	 * Difference Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getDeltaAmt();

	ModelColumn<I_M_CostRevaluation_Detail, Object> COLUMN_DeltaAmt = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "DeltaAmt", null);
	String COLUMNNAME_DeltaAmt = "DeltaAmt";

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

	ModelColumn<I_M_CostRevaluation_Detail, Object> COLUMN_IsActive = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_M_CostRevaluation_Detail, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Cost Detail.
	 * Cost Detail Information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_CostDetail_ID (int M_CostDetail_ID);

	/**
	 * Get Cost Detail.
	 * Cost Detail Information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_CostDetail_ID();

	@Nullable org.compiere.model.I_M_CostDetail getM_CostDetail();

	void setM_CostDetail(@Nullable org.compiere.model.I_M_CostDetail M_CostDetail);

	ModelColumn<I_M_CostRevaluation_Detail, org.compiere.model.I_M_CostDetail> COLUMN_M_CostDetail_ID = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "M_CostDetail_ID", org.compiere.model.I_M_CostDetail.class);
	String COLUMNNAME_M_CostDetail_ID = "M_CostDetail_ID";

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

	ModelColumn<I_M_CostRevaluation_Detail, org.compiere.model.I_M_CostElement> COLUMN_M_CostElement_ID = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "M_CostElement_ID", org.compiere.model.I_M_CostElement.class);
	String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";

	/**
	 * Set Cost Revaluation Detail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_CostRevaluation_Detail_ID (int M_CostRevaluation_Detail_ID);

	/**
	 * Get Cost Revaluation Detail.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_CostRevaluation_Detail_ID();

	ModelColumn<I_M_CostRevaluation_Detail, Object> COLUMN_M_CostRevaluation_Detail_ID = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "M_CostRevaluation_Detail_ID", null);
	String COLUMNNAME_M_CostRevaluation_Detail_ID = "M_CostRevaluation_Detail_ID";

	/**
	 * Set Cost Revaluation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_CostRevaluation_ID (int M_CostRevaluation_ID);

	/**
	 * Get Cost Revaluation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_CostRevaluation_ID();

	org.compiere.model.I_M_CostRevaluation getM_CostRevaluation();

	void setM_CostRevaluation(org.compiere.model.I_M_CostRevaluation M_CostRevaluation);

	ModelColumn<I_M_CostRevaluation_Detail, org.compiere.model.I_M_CostRevaluation> COLUMN_M_CostRevaluation_ID = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "M_CostRevaluation_ID", org.compiere.model.I_M_CostRevaluation.class);
	String COLUMNNAME_M_CostRevaluation_ID = "M_CostRevaluation_ID";

	/**
	 * Set Cost Revaluation Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_CostRevaluationLine_ID (int M_CostRevaluationLine_ID);

	/**
	 * Get Cost Revaluation Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_CostRevaluationLine_ID();

	org.compiere.model.I_M_CostRevaluationLine getM_CostRevaluationLine();

	void setM_CostRevaluationLine(org.compiere.model.I_M_CostRevaluationLine M_CostRevaluationLine);

	ModelColumn<I_M_CostRevaluation_Detail, org.compiere.model.I_M_CostRevaluationLine> COLUMN_M_CostRevaluationLine_ID = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "M_CostRevaluationLine_ID", org.compiere.model.I_M_CostRevaluationLine.class);
	String COLUMNNAME_M_CostRevaluationLine_ID = "M_CostRevaluationLine_ID";

	/**
	 * Set Cost Type.
	 * Type of Cost (e.g. Current, Plan, Future)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_CostType_ID (int M_CostType_ID);

	/**
	 * Get Cost Type.
	 * Type of Cost (e.g. Current, Plan, Future)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_CostType_ID();

	org.compiere.model.I_M_CostType getM_CostType();

	void setM_CostType(org.compiere.model.I_M_CostType M_CostType);

	ModelColumn<I_M_CostRevaluation_Detail, org.compiere.model.I_M_CostType> COLUMN_M_CostType_ID = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "M_CostType_ID", org.compiere.model.I_M_CostType.class);
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
	 * Set New Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setNewAmt (BigDecimal NewAmt);

	/**
	 * Get New Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getNewAmt();

	ModelColumn<I_M_CostRevaluation_Detail, Object> COLUMN_NewAmt = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "NewAmt", null);
	String COLUMNNAME_NewAmt = "NewAmt";

	/**
	 * Set New Cost Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setNewCostPrice (BigDecimal NewCostPrice);

	/**
	 * Get New Cost Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getNewCostPrice();

	ModelColumn<I_M_CostRevaluation_Detail, Object> COLUMN_NewCostPrice = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "NewCostPrice", null);
	String COLUMNNAME_NewCostPrice = "NewCostPrice";

	/**
	 * Set Old Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOldAmt (BigDecimal OldAmt);

	/**
	 * Get Old Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getOldAmt();

	ModelColumn<I_M_CostRevaluation_Detail, Object> COLUMN_OldAmt = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "OldAmt", null);
	String COLUMNNAME_OldAmt = "OldAmt";

	/**
	 * Set Old Cost Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOldCostPrice (BigDecimal OldCostPrice);

	/**
	 * Get Old Cost Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getOldCostPrice();

	ModelColumn<I_M_CostRevaluation_Detail, Object> COLUMN_OldCostPrice = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "OldCostPrice", null);
	String COLUMNNAME_OldCostPrice = "OldCostPrice";

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

	ModelColumn<I_M_CostRevaluation_Detail, Object> COLUMN_Qty = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Revaluation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRevaluationType (java.lang.String RevaluationType);

	/**
	 * Get Revaluation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getRevaluationType();

	ModelColumn<I_M_CostRevaluation_Detail, Object> COLUMN_RevaluationType = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "RevaluationType", null);
	String COLUMNNAME_RevaluationType = "RevaluationType";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_M_CostRevaluation_Detail, Object> COLUMN_SeqNo = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_CostRevaluation_Detail, Object> COLUMN_Updated = new ModelColumn<>(I_M_CostRevaluation_Detail.class, "Updated", null);
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
