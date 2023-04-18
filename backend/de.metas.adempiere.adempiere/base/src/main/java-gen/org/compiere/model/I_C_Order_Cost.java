package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Order_Cost
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Order_Cost 
{

	String Table_Name = "C_Order_Cost";

//	/** AD_Table_ID=542296 */
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
	 * Set Cost Type.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Cost_Type_ID (int C_Cost_Type_ID);

	/**
	 * Get Cost Type.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Cost_Type_ID();

	org.compiere.model.I_C_Cost_Type getC_Cost_Type();

	void setC_Cost_Type(org.compiere.model.I_C_Cost_Type C_Cost_Type);

	ModelColumn<I_C_Order_Cost, org.compiere.model.I_C_Cost_Type> COLUMN_C_Cost_Type_ID = new ModelColumn<>(I_C_Order_Cost.class, "C_Cost_Type_ID", org.compiere.model.I_C_Cost_Type.class);
	String COLUMNNAME_C_Cost_Type_ID = "C_Cost_Type_ID";

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
	 * Set Order Cost.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Order_Cost_ID (int C_Order_Cost_ID);

	/**
	 * Get Order Cost.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Order_Cost_ID();

	ModelColumn<I_C_Order_Cost, Object> COLUMN_C_Order_Cost_ID = new ModelColumn<>(I_C_Order_Cost.class, "C_Order_Cost_ID", null);
	String COLUMNNAME_C_Order_Cost_ID = "C_Order_Cost_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	org.compiere.model.I_C_Order getC_Order();

	void setC_Order(org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_C_Order_Cost, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_C_Order_Cost.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Cost Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCostAmount (BigDecimal CostAmount);

	/**
	 * Get Cost Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCostAmount();

	ModelColumn<I_C_Order_Cost, Object> COLUMN_CostAmount = new ModelColumn<>(I_C_Order_Cost.class, "CostAmount", null);
	String COLUMNNAME_CostAmount = "CostAmount";

	/**
	 * Set Fixed Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCostCalculation_FixedAmount (@Nullable BigDecimal CostCalculation_FixedAmount);

	/**
	 * Get Fixed Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCostCalculation_FixedAmount();

	ModelColumn<I_C_Order_Cost, Object> COLUMN_CostCalculation_FixedAmount = new ModelColumn<>(I_C_Order_Cost.class, "CostCalculation_FixedAmount", null);
	String COLUMNNAME_CostCalculation_FixedAmount = "CostCalculation_FixedAmount";

	/**
	 * Set Calculation Method.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCostCalculationMethod (java.lang.String CostCalculationMethod);

	/**
	 * Get Calculation Method.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getCostCalculationMethod();

	ModelColumn<I_C_Order_Cost, Object> COLUMN_CostCalculationMethod = new ModelColumn<>(I_C_Order_Cost.class, "CostCalculationMethod", null);
	String COLUMNNAME_CostCalculationMethod = "CostCalculationMethod";

	/**
	 * Set Percentage.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCostCalculation_Percentage (@Nullable BigDecimal CostCalculation_Percentage);

	/**
	 * Get Percentage.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCostCalculation_Percentage();

	ModelColumn<I_C_Order_Cost, Object> COLUMN_CostCalculation_Percentage = new ModelColumn<>(I_C_Order_Cost.class, "CostCalculation_Percentage", null);
	String COLUMNNAME_CostCalculation_Percentage = "CostCalculation_Percentage";

	/**
	 * Set Distribution.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCostDistributionMethod (java.lang.String CostDistributionMethod);

	/**
	 * Get Distribution.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getCostDistributionMethod();

	ModelColumn<I_C_Order_Cost, Object> COLUMN_CostDistributionMethod = new ModelColumn<>(I_C_Order_Cost.class, "CostDistributionMethod", null);
	String COLUMNNAME_CostDistributionMethod = "CostDistributionMethod";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Order_Cost, Object> COLUMN_Created = new ModelColumn<>(I_C_Order_Cost.class, "Created", null);
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
	 * Set Created Order Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreated_OrderLine_ID (int Created_OrderLine_ID);

	/**
	 * Get Created Order Line.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreated_OrderLine_ID();

	@Nullable org.compiere.model.I_C_OrderLine getCreated_OrderLine();

	void setCreated_OrderLine(@Nullable org.compiere.model.I_C_OrderLine Created_OrderLine);

	ModelColumn<I_C_Order_Cost, org.compiere.model.I_C_OrderLine> COLUMN_Created_OrderLine_ID = new ModelColumn<>(I_C_Order_Cost.class, "Created_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_Created_OrderLine_ID = "Created_OrderLine_ID";

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

	ModelColumn<I_C_Order_Cost, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Order_Cost.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_Order_Cost, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_C_Order_Cost.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

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

	ModelColumn<I_C_Order_Cost, org.compiere.model.I_M_CostElement> COLUMN_M_CostElement_ID = new ModelColumn<>(I_C_Order_Cost.class, "M_CostElement_ID", org.compiere.model.I_M_CostElement.class);
	String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Order_Cost, Object> COLUMN_Updated = new ModelColumn<>(I_C_Order_Cost.class, "Updated", null);
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
