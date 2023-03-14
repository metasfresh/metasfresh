package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Cost_Type
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Cost_Type 
{

	String Table_Name = "C_Cost_Type";

//	/** AD_Table_ID=542294 */
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
	 * Set Cost Type.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Cost_Type_ID (int C_Cost_Type_ID);

	/**
	 * Get Cost Type.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Cost_Type_ID();

	ModelColumn<I_C_Cost_Type, Object> COLUMN_C_Cost_Type_ID = new ModelColumn<>(I_C_Cost_Type.class, "C_Cost_Type_ID", null);
	String COLUMNNAME_C_Cost_Type_ID = "C_Cost_Type_ID";

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

	ModelColumn<I_C_Cost_Type, Object> COLUMN_CostCalculationMethod = new ModelColumn<>(I_C_Cost_Type.class, "CostCalculationMethod", null);
	String COLUMNNAME_CostCalculationMethod = "CostCalculationMethod";

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

	ModelColumn<I_C_Cost_Type, Object> COLUMN_CostDistributionMethod = new ModelColumn<>(I_C_Cost_Type.class, "CostDistributionMethod", null);
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

	ModelColumn<I_C_Cost_Type, Object> COLUMN_Created = new ModelColumn<>(I_C_Cost_Type.class, "Created", null);
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
	 * Set Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_Cost_Type, Object> COLUMN_Description = new ModelColumn<>(I_C_Cost_Type.class, "Description", null);
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

	ModelColumn<I_C_Cost_Type, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Cost_Type.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Allow Invoicing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowInvoicing (boolean IsAllowInvoicing);

	/**
	 * Get Allow Invoicing.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowInvoicing();

	ModelColumn<I_C_Cost_Type, Object> COLUMN_IsAllowInvoicing = new ModelColumn<>(I_C_Cost_Type.class, "IsAllowInvoicing", null);
	String COLUMNNAME_IsAllowInvoicing = "IsAllowInvoicing";

	/**
	 * Set Allow on Purchase.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowOnPurchase (boolean IsAllowOnPurchase);

	/**
	 * Get Allow on Purchase.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowOnPurchase();

	ModelColumn<I_C_Cost_Type, Object> COLUMN_IsAllowOnPurchase = new ModelColumn<>(I_C_Cost_Type.class, "IsAllowOnPurchase", null);
	String COLUMNNAME_IsAllowOnPurchase = "IsAllowOnPurchase";

	/**
	 * Set Allow on Sales.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowOnSales (boolean IsAllowOnSales);

	/**
	 * Get Allow on Sales.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowOnSales();

	ModelColumn<I_C_Cost_Type, Object> COLUMN_IsAllowOnSales = new ModelColumn<>(I_C_Cost_Type.class, "IsAllowOnSales", null);
	String COLUMNNAME_IsAllowOnSales = "IsAllowOnSales";

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

	ModelColumn<I_C_Cost_Type, org.compiere.model.I_M_CostElement> COLUMN_M_CostElement_ID = new ModelColumn<>(I_C_Cost_Type.class, "M_CostElement_ID", org.compiere.model.I_M_CostElement.class);
	String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";

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
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_C_Cost_Type, Object> COLUMN_Name = new ModelColumn<>(I_C_Cost_Type.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Cost_Type, Object> COLUMN_Updated = new ModelColumn<>(I_C_Cost_Type.class, "Updated", null);
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

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_C_Cost_Type, Object> COLUMN_Value = new ModelColumn<>(I_C_Cost_Type.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
