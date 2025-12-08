package de.metas.material.cockpit.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for MD_AvailableForSales_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MD_AvailableForSales_Config 
{

	String Table_Name = "MD_AvailableForSales_Config";

//	/** AD_Table_ID=541343 */
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
	 * Set Async timeout (ms).
	 * Maximum time in milli seconds to wait for an asynchronour result, before the request is aborted with an error message.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAsyncTimeoutMillis (int AsyncTimeoutMillis);

	/**
	 * Get Async timeout (ms).
	 * Maximum time in milli seconds to wait for an asynchronour result, before the request is aborted with an error message.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAsyncTimeoutMillis();

	ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_AsyncTimeoutMillis = new ModelColumn<>(I_MD_AvailableForSales_Config.class, "AsyncTimeoutMillis", null);
	String COLUMNNAME_AsyncTimeoutMillis = "AsyncTimeoutMillis";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_Created = new ModelColumn<>(I_MD_AvailableForSales_Config.class, "Created", null);
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

	ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_Description = new ModelColumn<>(I_MD_AvailableForSales_Config.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Color for short-term availability problem.
	 * Color to use when flagging sale order lines where the current stock minus foreseeable shipments is not sufficient to fulfill the ordered quantity.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInsufficientQtyAvailableForSalesColor_ID (int InsufficientQtyAvailableForSalesColor_ID);

	/**
	 * Get Color for short-term availability problem.
	 * Color to use when flagging sale order lines where the current stock minus foreseeable shipments is not sufficient to fulfill the ordered quantity.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getInsufficientQtyAvailableForSalesColor_ID();

	org.compiere.model.I_AD_Color getInsufficientQtyAvailableForSalesColor();

	void setInsufficientQtyAvailableForSalesColor(org.compiere.model.I_AD_Color InsufficientQtyAvailableForSalesColor);

	ModelColumn<I_MD_AvailableForSales_Config, org.compiere.model.I_AD_Color> COLUMN_InsufficientQtyAvailableForSalesColor_ID = new ModelColumn<>(I_MD_AvailableForSales_Config.class, "InsufficientQtyAvailableForSalesColor_ID", org.compiere.model.I_AD_Color.class);
	String COLUMNNAME_InsufficientQtyAvailableForSalesColor_ID = "InsufficientQtyAvailableForSalesColor_ID";

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

	ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_MD_AvailableForSales_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Async.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAsync (boolean IsAsync);

	/**
	 * Get Async.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAsync();

	ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_IsAsync = new ModelColumn<>(I_MD_AvailableForSales_Config.class, "IsAsync", null);
	String COLUMNNAME_IsAsync = "IsAsync";

	/**
	 * Set Feature activated.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsFeatureActivated (boolean IsFeatureActivated);

	/**
	 * Get Feature activated.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isFeatureActivated();

	ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_IsFeatureActivated = new ModelColumn<>(I_MD_AvailableForSales_Config.class, "IsFeatureActivated", null);
	String COLUMNNAME_IsFeatureActivated = "IsFeatureActivated";

	/**
	 * Set MD_AvailableForSales_Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_AvailableForSales_Config_ID (int MD_AvailableForSales_Config_ID);

	/**
	 * Get MD_AvailableForSales_Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMD_AvailableForSales_Config_ID();

	ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_MD_AvailableForSales_Config_ID = new ModelColumn<>(I_MD_AvailableForSales_Config.class, "MD_AvailableForSales_Config_ID", null);
	String COLUMNNAME_MD_AvailableForSales_Config_ID = "MD_AvailableForSales_Config_ID";

	/**
	 * Set Lookbehind interval for uncompleted sales order lines (hr).
	 * The value contains the number of hours to look back when searching for uncompleted sales order lines to compute the quantity available for sales.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSalesOrderLookBehindHours (int SalesOrderLookBehindHours);

	/**
	 * Get Lookbehind interval for uncompleted sales order lines (hr).
	 * The value contains the number of hours to look back when searching for uncompleted sales order lines to compute the quantity available for sales.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSalesOrderLookBehindHours();

	ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_SalesOrderLookBehindHours = new ModelColumn<>(I_MD_AvailableForSales_Config.class, "SalesOrderLookBehindHours", null);
	String COLUMNNAME_SalesOrderLookBehindHours = "SalesOrderLookBehindHours";

	/**
	 * Set Lookahead interval for planned shipments (hr).
	 * The value contains the number of hours to look ahead when searching for foreseeable shipments to compute the quantity available for sales.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setShipmentDateLookAheadHours (int ShipmentDateLookAheadHours);

	/**
	 * Get Lookahead interval for planned shipments (hr).
	 * The value contains the number of hours to look ahead when searching for foreseeable shipments to compute the quantity available for sales.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getShipmentDateLookAheadHours();

	ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_ShipmentDateLookAheadHours = new ModelColumn<>(I_MD_AvailableForSales_Config.class, "ShipmentDateLookAheadHours", null);
	String COLUMNNAME_ShipmentDateLookAheadHours = "ShipmentDateLookAheadHours";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_MD_AvailableForSales_Config, Object> COLUMN_Updated = new ModelColumn<>(I_MD_AvailableForSales_Config.class, "Updated", null);
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
