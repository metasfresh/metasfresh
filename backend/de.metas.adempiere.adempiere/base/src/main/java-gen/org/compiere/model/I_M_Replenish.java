package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Replenish
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Replenish 
{

	String Table_Name = "M_Replenish";

//	/** AD_Table_ID=249 */
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
	 * Set Calendar.
	 * Accounting Calendar Name
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Calendar_ID (int C_Calendar_ID);

	/**
	 * Get Calendar.
	 * Accounting Calendar Name
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Calendar_ID();

	@Nullable org.compiere.model.I_C_Calendar getC_Calendar();

	void setC_Calendar(@Nullable org.compiere.model.I_C_Calendar C_Calendar);

	ModelColumn<I_M_Replenish, org.compiere.model.I_C_Calendar> COLUMN_C_Calendar_ID = new ModelColumn<>(I_M_Replenish.class, "C_Calendar_ID", org.compiere.model.I_C_Calendar.class);
	String COLUMNNAME_C_Calendar_ID = "C_Calendar_ID";

	/**
	 * Set Period.
	 * Period of the Calendar
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Period.
	 * Period of the Calendar
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Period_ID();

	@Nullable org.compiere.model.I_C_Period getC_Period();

	void setC_Period(@Nullable org.compiere.model.I_C_Period C_Period);

	ModelColumn<I_M_Replenish, org.compiere.model.I_C_Period> COLUMN_C_Period_ID = new ModelColumn<>(I_M_Replenish.class, "C_Period_ID", org.compiere.model.I_C_Period.class);
	String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Replenish, Object> COLUMN_Created = new ModelColumn<>(I_M_Replenish.class, "Created", null);
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

	ModelColumn<I_M_Replenish, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Replenish.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set High Priority.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsHighPriority (boolean IsHighPriority);

	/**
	 * Get High Priority.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isHighPriority();

	ModelColumn<I_M_Replenish, Object> COLUMN_IsHighPriority = new ModelColumn<>(I_M_Replenish.class, "IsHighPriority", null);
	String COLUMNNAME_IsHighPriority = "IsHighPriority";

	/**
	 * Set Maximum Level.
	 * Maximum Inventory level for this product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLevel_Max (@Nullable BigDecimal Level_Max);

	/**
	 * Get Maximum Level.
	 * Maximum Inventory level for this product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getLevel_Max();

	ModelColumn<I_M_Replenish, Object> COLUMN_Level_Max = new ModelColumn<>(I_M_Replenish.class, "Level_Max", null);
	String COLUMNNAME_Level_Max = "Level_Max";

	/**
	 * Set Target quantity.
	 * Minimum Inventory level for this product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLevel_Min (BigDecimal Level_Min);

	/**
	 * Get Target quantity.
	 * Minimum Inventory level for this product
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getLevel_Min();

	ModelColumn<I_M_Replenish, Object> COLUMN_Level_Min = new ModelColumn<>(I_M_Replenish.class, "Level_Min", null);
	String COLUMNNAME_Level_Min = "Level_Min";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

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
	 * Set M_Replenish.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Replenish_ID (int M_Replenish_ID);

	/**
	 * Get M_Replenish.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Replenish_ID();

	ModelColumn<I_M_Replenish, Object> COLUMN_M_Replenish_ID = new ModelColumn<>(I_M_Replenish.class, "M_Replenish_ID", null);
	String COLUMNNAME_M_Replenish_ID = "M_Replenish_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Replenish Type.
	 * Method for re-ordering a product
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setReplenishType (java.lang.String ReplenishType);

	/**
	 * Get Replenish Type.
	 * Method for re-ordering a product
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getReplenishType();

	ModelColumn<I_M_Replenish, Object> COLUMN_ReplenishType = new ModelColumn<>(I_M_Replenish.class, "ReplenishType", null);
	String COLUMNNAME_ReplenishType = "ReplenishType";

	/**
	 * Set Time to Market.
	 * Time to Market (in Wochen)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTimeToMarket (int TimeToMarket);

	/**
	 * Get Time to Market.
	 * Time to Market (in Wochen)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getTimeToMarket();

	ModelColumn<I_M_Replenish, Object> COLUMN_TimeToMarket = new ModelColumn<>(I_M_Replenish.class, "TimeToMarket", null);
	String COLUMNNAME_TimeToMarket = "TimeToMarket";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Replenish, Object> COLUMN_Updated = new ModelColumn<>(I_M_Replenish.class, "Updated", null);
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
