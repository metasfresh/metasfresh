package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_CostRevaluationLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_CostRevaluationLine 
{

	String Table_Name = "M_CostRevaluationLine";

//	/** AD_Table_ID=542191 */
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
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_CostRevaluationLine, Object> COLUMN_Created = new ModelColumn<>(I_M_CostRevaluationLine.class, "Created", null);
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Current Cost Price.
	 * The currently used cost price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurrentCostPrice (@Nullable BigDecimal CurrentCostPrice);

	/**
	 * Get Current Cost Price.
	 * The currently used cost price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCurrentCostPrice();

	ModelColumn<I_M_CostRevaluationLine, Object> COLUMN_CurrentCostPrice = new ModelColumn<>(I_M_CostRevaluationLine.class, "CurrentCostPrice", null);
	String COLUMNNAME_CurrentCostPrice = "CurrentCostPrice";

	/**
	 * Set Current Quantity.
	 * Current Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurrentQty (@Nullable BigDecimal CurrentQty);

	/**
	 * Get Current Quantity.
	 * Current Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCurrentQty();

	ModelColumn<I_M_CostRevaluationLine, Object> COLUMN_CurrentQty = new ModelColumn<>(I_M_CostRevaluationLine.class, "CurrentQty", null);
	String COLUMNNAME_CurrentQty = "CurrentQty";

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

	ModelColumn<I_M_CostRevaluationLine, Object> COLUMN_IsActive = new ModelColumn<>(I_M_CostRevaluationLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Cost Revaluation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_CostRevaluation_ID (int M_CostRevaluation_ID);

	/**
	 * Get Cost Revaluation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_CostRevaluation_ID();

	org.compiere.model.I_M_CostRevaluation getM_CostRevaluation();

	void setM_CostRevaluation(org.compiere.model.I_M_CostRevaluation M_CostRevaluation);

	ModelColumn<I_M_CostRevaluationLine, org.compiere.model.I_M_CostRevaluation> COLUMN_M_CostRevaluation_ID = new ModelColumn<>(I_M_CostRevaluationLine.class, "M_CostRevaluation_ID", org.compiere.model.I_M_CostRevaluation.class);
	String COLUMNNAME_M_CostRevaluation_ID = "M_CostRevaluation_ID";

	/**
	 * Set Cost Revaluation Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_CostRevaluationLine_ID (int M_CostRevaluationLine_ID);

	/**
	 * Get Cost Revaluation Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_CostRevaluationLine_ID();

	ModelColumn<I_M_CostRevaluationLine, Object> COLUMN_M_CostRevaluationLine_ID = new ModelColumn<>(I_M_CostRevaluationLine.class, "M_CostRevaluationLine_ID", null);
	String COLUMNNAME_M_CostRevaluationLine_ID = "M_CostRevaluationLine_ID";

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

	ModelColumn<I_M_CostRevaluationLine, Object> COLUMN_NewCostPrice = new ModelColumn<>(I_M_CostRevaluationLine.class, "NewCostPrice", null);
	String COLUMNNAME_NewCostPrice = "NewCostPrice";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_CostRevaluationLine, Object> COLUMN_Updated = new ModelColumn<>(I_M_CostRevaluationLine.class, "Updated", null);
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
