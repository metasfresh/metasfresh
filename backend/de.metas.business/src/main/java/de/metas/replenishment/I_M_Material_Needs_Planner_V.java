package de.metas.replenishment;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for M_Material_Needs_Planner_V
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Material_Needs_Planner_V 
{

	String Table_Name = "M_Material_Needs_Planner_V";

//	/** AD_Table_ID=542466 */
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
	 * Set 6 Wo-Schnitt.
	 * Average quantity in the last six weeks
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAverage_Qty_Last_Six_Weeks (@Nullable BigDecimal Average_Qty_Last_Six_Weeks);

	/**
	 * Get 6 Wo-Schnitt.
	 * Average quantity in the last six weeks
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAverage_Qty_Last_Six_Weeks();

	ModelColumn<I_M_Material_Needs_Planner_V, Object> COLUMN_Average_Qty_Last_Six_Weeks = new ModelColumn<>(I_M_Material_Needs_Planner_V.class, "Average_Qty_Last_Six_Weeks", null);
	String COLUMNNAME_Average_Qty_Last_Six_Weeks = "Average_Qty_Last_Six_Weeks";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Material_Needs_Planner_V, Object> COLUMN_Created = new ModelColumn<>(I_M_Material_Needs_Planner_V.class, "Created", null);
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
	 * Set Demand.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDemand (@Nullable BigDecimal Demand);

	/**
	 * Get Demand.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDemand();

	ModelColumn<I_M_Material_Needs_Planner_V, Object> COLUMN_Demand = new ModelColumn<>(I_M_Material_Needs_Planner_V.class, "Demand", null);
	String COLUMNNAME_Demand = "Demand";

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

	ModelColumn<I_M_Material_Needs_Planner_V, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Material_Needs_Planner_V.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Product Category.
	 * Category of a Product
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Product Category.
	 * Category of a Product
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_Category_ID();

	String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

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
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Onhand Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQuantityOnHand (@Nullable BigDecimal QuantityOnHand);

	/**
	 * Get Onhand Quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQuantityOnHand();

	ModelColumn<I_M_Material_Needs_Planner_V, Object> COLUMN_QuantityOnHand = new ModelColumn<>(I_M_Material_Needs_Planner_V.class, "QuantityOnHand", null);
	String COLUMNNAME_QuantityOnHand = "QuantityOnHand";

	/**
	 * Set -5.
	 * Five weeks ago
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTotal_Qty_Five_Weeks_Ago (@Nullable BigDecimal Total_Qty_Five_Weeks_Ago);

	/**
	 * Get -5.
	 * Five weeks ago
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotal_Qty_Five_Weeks_Ago();

	ModelColumn<I_M_Material_Needs_Planner_V, Object> COLUMN_Total_Qty_Five_Weeks_Ago = new ModelColumn<>(I_M_Material_Needs_Planner_V.class, "Total_Qty_Five_Weeks_Ago", null);
	String COLUMNNAME_Total_Qty_Five_Weeks_Ago = "Total_Qty_Five_Weeks_Ago";

	/**
	 * Set -4.
	 * Four weeks ago
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTotal_Qty_Four_Weeks_Ago (@Nullable BigDecimal Total_Qty_Four_Weeks_Ago);

	/**
	 * Get -4.
	 * Four weeks ago
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotal_Qty_Four_Weeks_Ago();

	ModelColumn<I_M_Material_Needs_Planner_V, Object> COLUMN_Total_Qty_Four_Weeks_Ago = new ModelColumn<>(I_M_Material_Needs_Planner_V.class, "Total_Qty_Four_Weeks_Ago", null);
	String COLUMNNAME_Total_Qty_Four_Weeks_Ago = "Total_Qty_Four_Weeks_Ago";

	/**
	 * Set -1.
	 * Last week
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTotal_Qty_One_Week_Ago (@Nullable BigDecimal Total_Qty_One_Week_Ago);

	/**
	 * Get -1.
	 * Last week
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotal_Qty_One_Week_Ago();

	ModelColumn<I_M_Material_Needs_Planner_V, Object> COLUMN_Total_Qty_One_Week_Ago = new ModelColumn<>(I_M_Material_Needs_Planner_V.class, "Total_Qty_One_Week_Ago", null);
	String COLUMNNAME_Total_Qty_One_Week_Ago = "Total_Qty_One_Week_Ago";

	/**
	 * Set -6.
	 * Six weeks ago
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTotal_Qty_Six_Weeks_Ago (@Nullable BigDecimal Total_Qty_Six_Weeks_Ago);

	/**
	 * Get -6.
	 * Six weeks ago
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotal_Qty_Six_Weeks_Ago();

	ModelColumn<I_M_Material_Needs_Planner_V, Object> COLUMN_Total_Qty_Six_Weeks_Ago = new ModelColumn<>(I_M_Material_Needs_Planner_V.class, "Total_Qty_Six_Weeks_Ago", null);
	String COLUMNNAME_Total_Qty_Six_Weeks_Ago = "Total_Qty_Six_Weeks_Ago";

	/**
	 * Set -3.
	 * Three weeks ago
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTotal_Qty_Three_Weeks_Ago (@Nullable BigDecimal Total_Qty_Three_Weeks_Ago);

	/**
	 * Get -3.
	 * Three weeks ago
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotal_Qty_Three_Weeks_Ago();

	ModelColumn<I_M_Material_Needs_Planner_V, Object> COLUMN_Total_Qty_Three_Weeks_Ago = new ModelColumn<>(I_M_Material_Needs_Planner_V.class, "Total_Qty_Three_Weeks_Ago", null);
	String COLUMNNAME_Total_Qty_Three_Weeks_Ago = "Total_Qty_Three_Weeks_Ago";

	/**
	 * Set -2.
	 * Two weeks ago
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTotal_Qty_Two_Weeks_Ago (@Nullable BigDecimal Total_Qty_Two_Weeks_Ago);

	/**
	 * Get -2.
	 * Two weeks ago
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotal_Qty_Two_Weeks_Ago();

	ModelColumn<I_M_Material_Needs_Planner_V, Object> COLUMN_Total_Qty_Two_Weeks_Ago = new ModelColumn<>(I_M_Material_Needs_Planner_V.class, "Total_Qty_Two_Weeks_Ago", null);
	String COLUMNNAME_Total_Qty_Two_Weeks_Ago = "Total_Qty_Two_Weeks_Ago";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Material_Needs_Planner_V, Object> COLUMN_Updated = new ModelColumn<>(I_M_Material_Needs_Planner_V.class, "Updated", null);
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
