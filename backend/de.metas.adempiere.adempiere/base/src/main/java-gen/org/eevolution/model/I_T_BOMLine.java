package org.eevolution.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for T_BOMLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_T_BOMLine 
{

	String Table_Name = "T_BOMLine";

//	/** AD_Table_ID=53045 */
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
	 * Set Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_PInstance_ID (int AD_PInstance_ID);

	/**
	 * Get Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_PInstance_ID();

	@Nullable org.compiere.model.I_AD_PInstance getAD_PInstance();

	void setAD_PInstance(@Nullable org.compiere.model.I_AD_PInstance AD_PInstance);

	ModelColumn<I_T_BOMLine, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ID = new ModelColumn<>(I_T_BOMLine.class, "AD_PInstance_ID", org.compiere.model.I_AD_PInstance.class);
	String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/**
	 * Set Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_AcctSchema_ID();

	@Nullable org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	void setC_AcctSchema(@Nullable org.compiere.model.I_C_AcctSchema C_AcctSchema);

	ModelColumn<I_T_BOMLine, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new ModelColumn<>(I_T_BOMLine.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
	String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Kosten.
	 * Cost information
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCost (@Nullable BigDecimal Cost);

	/**
	 * Get Kosten.
	 * Cost information
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCost();

	ModelColumn<I_T_BOMLine, Object> COLUMN_Cost = new ModelColumn<>(I_T_BOMLine.class, "Cost", null);
	String COLUMNNAME_Cost = "Cost";

	/**
	 * Set Kostenrechnungsmethode.
	 * Indicates how Costs will be calculated
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCostingMethod (@Nullable java.lang.String CostingMethod);

	/**
	 * Get Kostenrechnungsmethode.
	 * Indicates how Costs will be calculated
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCostingMethod();

	ModelColumn<I_T_BOMLine, Object> COLUMN_CostingMethod = new ModelColumn<>(I_T_BOMLine.class, "CostingMethod", null);
	String COLUMNNAME_CostingMethod = "CostingMethod";

	/**
	 * Set Standardkosten.
	 * Standard Costs
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCostStandard (@Nullable BigDecimal CostStandard);

	/**
	 * Get Standardkosten.
	 * Standard Costs
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCostStandard();

	ModelColumn<I_T_BOMLine, Object> COLUMN_CostStandard = new ModelColumn<>(I_T_BOMLine.class, "CostStandard", null);
	String COLUMNNAME_CostStandard = "CostStandard";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_T_BOMLine, Object> COLUMN_Created = new ModelColumn<>(I_T_BOMLine.class, "Created", null);
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

	ModelColumn<I_T_BOMLine, Object> COLUMN_CurrentCostPrice = new ModelColumn<>(I_T_BOMLine.class, "CurrentCostPrice", null);
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

	ModelColumn<I_T_BOMLine, Object> COLUMN_CurrentCostPriceLL = new ModelColumn<>(I_T_BOMLine.class, "CurrentCostPriceLL", null);
	String COLUMNNAME_CurrentCostPriceLL = "CurrentCostPriceLL";

	/**
	 * Set Zukünftiger Kostenpreis.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFutureCostPrice (@Nullable BigDecimal FutureCostPrice);

	/**
	 * Get Zukünftiger Kostenpreis.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getFutureCostPrice();

	ModelColumn<I_T_BOMLine, Object> COLUMN_FutureCostPrice = new ModelColumn<>(I_T_BOMLine.class, "FutureCostPrice", null);
	String COLUMNNAME_FutureCostPrice = "FutureCostPrice";

	/**
	 * Set Future Cost Price Lower Level.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFutureCostPriceLL (@Nullable BigDecimal FutureCostPriceLL);

	/**
	 * Get Future Cost Price Lower Level.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getFutureCostPriceLL();

	ModelColumn<I_T_BOMLine, Object> COLUMN_FutureCostPriceLL = new ModelColumn<>(I_T_BOMLine.class, "FutureCostPriceLL", null);
	String COLUMNNAME_FutureCostPriceLL = "FutureCostPriceLL";

	/**
	 * Set Implosion.
	 * Implosion of a Bill of Materials refers to finding all the BOM''s in which a component is used.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setImplosion (boolean Implosion);

	/**
	 * Get Implosion.
	 * Implosion of a Bill of Materials refers to finding all the BOM''s in which a component is used.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isImplosion();

	ModelColumn<I_T_BOMLine, Object> COLUMN_Implosion = new ModelColumn<>(I_T_BOMLine.class, "Implosion", null);
	String COLUMNNAME_Implosion = "Implosion";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_T_BOMLine, Object> COLUMN_IsActive = new ModelColumn<>(I_T_BOMLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Cost Frozen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsCostFrozen (boolean IsCostFrozen);

	/**
	 * Get Cost Frozen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isCostFrozen();

	ModelColumn<I_T_BOMLine, Object> COLUMN_IsCostFrozen = new ModelColumn<>(I_T_BOMLine.class, "IsCostFrozen", null);
	String COLUMNNAME_IsCostFrozen = "IsCostFrozen";

	/**
	 * Set Level no.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLevelNo (int LevelNo);

	/**
	 * Get Level no.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLevelNo();

	ModelColumn<I_T_BOMLine, Object> COLUMN_LevelNo = new ModelColumn<>(I_T_BOMLine.class, "LevelNo", null);
	String COLUMNNAME_LevelNo = "LevelNo";

	/**
	 * Set Levels.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLevels (@Nullable java.lang.String Levels);

	/**
	 * Get Levels.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLevels();

	ModelColumn<I_T_BOMLine, Object> COLUMN_Levels = new ModelColumn<>(I_T_BOMLine.class, "Levels", null);
	String COLUMNNAME_Levels = "Levels";

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

	ModelColumn<I_T_BOMLine, org.compiere.model.I_M_CostElement> COLUMN_M_CostElement_ID = new ModelColumn<>(I_T_BOMLine.class, "M_CostElement_ID", org.compiere.model.I_M_CostElement.class);
	String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";

	/**
	 * Set Kostenkategorie.
	 * Type of Cost (e.g. Current, Plan, Future)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_CostType_ID (int M_CostType_ID);

	/**
	 * Get Kostenkategorie.
	 * Type of Cost (e.g. Current, Plan, Future)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_CostType_ID();

	@Nullable org.compiere.model.I_M_CostType getM_CostType();

	void setM_CostType(@Nullable org.compiere.model.I_M_CostType M_CostType);

	ModelColumn<I_T_BOMLine, org.compiere.model.I_M_CostType> COLUMN_M_CostType_ID = new ModelColumn<>(I_T_BOMLine.class, "M_CostType_ID", org.compiere.model.I_M_CostType.class);
	String COLUMNNAME_M_CostType_ID = "M_CostType_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set BOM & Formula Version.
	 * BOM & Formula
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Product_BOM_ID (int PP_Product_BOM_ID);

	/**
	 * Get BOM & Formula Version.
	 * BOM & Formula
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Product_BOM_ID();

	@Nullable org.eevolution.model.I_PP_Product_BOM getPP_Product_BOM();

	void setPP_Product_BOM(@Nullable org.eevolution.model.I_PP_Product_BOM PP_Product_BOM);

	ModelColumn<I_T_BOMLine, org.eevolution.model.I_PP_Product_BOM> COLUMN_PP_Product_BOM_ID = new ModelColumn<>(I_T_BOMLine.class, "PP_Product_BOM_ID", org.eevolution.model.I_PP_Product_BOM.class);
	String COLUMNNAME_PP_Product_BOM_ID = "PP_Product_BOM_ID";

	/**
	 * Set BOM Line.
	 * BOM Line
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPP_Product_BOMLine_ID (int PP_Product_BOMLine_ID);

	/**
	 * Get BOM Line.
	 * BOM Line
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPP_Product_BOMLine_ID();

	@Nullable org.eevolution.model.I_PP_Product_BOMLine getPP_Product_BOMLine();

	void setPP_Product_BOMLine(@Nullable org.eevolution.model.I_PP_Product_BOMLine PP_Product_BOMLine);

	ModelColumn<I_T_BOMLine, org.eevolution.model.I_PP_Product_BOMLine> COLUMN_PP_Product_BOMLine_ID = new ModelColumn<>(I_T_BOMLine.class, "PP_Product_BOMLine_ID", org.eevolution.model.I_PP_Product_BOMLine.class);
	String COLUMNNAME_PP_Product_BOMLine_ID = "PP_Product_BOMLine_ID";

	/**
	 * Set Quantity.
	 * Indicate the Quantity  use in this BOM
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyBOM (@Nullable BigDecimal QtyBOM);

	/**
	 * Get Quantity.
	 * Indicate the Quantity  use in this BOM
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyBOM();

	ModelColumn<I_T_BOMLine, Object> COLUMN_QtyBOM = new ModelColumn<>(I_T_BOMLine.class, "QtyBOM", null);
	String COLUMNNAME_QtyBOM = "QtyBOM";

	/**
	 * Set Selected Product.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSel_Product_ID (int Sel_Product_ID);

	/**
	 * Get Selected Product.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSel_Product_ID();

	ModelColumn<I_T_BOMLine, Object> COLUMN_Sel_Product_ID = new ModelColumn<>(I_T_BOMLine.class, "Sel_Product_ID", null);
	String COLUMNNAME_Sel_Product_ID = "Sel_Product_ID";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_T_BOMLine, Object> COLUMN_SeqNo = new ModelColumn<>(I_T_BOMLine.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Temporal BOM Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setT_BOMLine_ID (int T_BOMLine_ID);

	/**
	 * Get Temporal BOM Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getT_BOMLine_ID();

	ModelColumn<I_T_BOMLine, Object> COLUMN_T_BOMLine_ID = new ModelColumn<>(I_T_BOMLine.class, "T_BOMLine_ID", null);
	String COLUMNNAME_T_BOMLine_ID = "T_BOMLine_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_T_BOMLine, Object> COLUMN_Updated = new ModelColumn<>(I_T_BOMLine.class, "Updated", null);
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
