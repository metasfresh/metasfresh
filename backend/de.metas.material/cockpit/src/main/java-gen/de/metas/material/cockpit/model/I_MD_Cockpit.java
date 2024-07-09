package de.metas.material.cockpit.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for MD_Cockpit
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_MD_Cockpit 
{

	String Table_Name = "MD_Cockpit";

//	/** AD_Table_ID=540863 */
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
	 * Set AttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAttributesKey (@Nullable String AttributesKey);

	/**
	 * Get AttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getAttributesKey();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_AttributesKey = new ModelColumn<>(I_MD_Cockpit.class, "AttributesKey", null);
	String COLUMNNAME_AttributesKey = "AttributesKey";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_Created = new ModelColumn<>(I_MD_Cockpit.class, "Created", null);
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
	 * Set Datum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateGeneral (java.sql.Timestamp DateGeneral);

	/**
	 * Get Datum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateGeneral();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_DateGeneral = new ModelColumn<>(I_MD_Cockpit.class, "DateGeneral", null);
	String COLUMNNAME_DateGeneral = "DateGeneral";

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

	ModelColumn<I_MD_Cockpit, Object> COLUMN_IsActive = new ModelColumn<>(I_MD_Cockpit.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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
	 * Set Material Cockpit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMD_Cockpit_ID (int MD_Cockpit_ID);

	/**
	 * Get Material Cockpit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getMD_Cockpit_ID();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_MD_Cockpit_ID = new ModelColumn<>(I_MD_Cockpit.class, "MD_Cockpit_ID", null);
	String COLUMNNAME_MD_Cockpit_ID = "MD_Cockpit_ID";

	/**
	 * Set 📆 MDCandidateQtyStock.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMDCandidateQtyStock_AtDate (@Nullable BigDecimal MDCandidateQtyStock_AtDate);

	/**
	 * Get 📆 MDCandidateQtyStock.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getMDCandidateQtyStock_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_MDCandidateQtyStock_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "MDCandidateQtyStock_AtDate", null);
	String COLUMNNAME_MDCandidateQtyStock_AtDate = "MDCandidateQtyStock_AtDate";

	/**
	 * Set 📆 Vendor Promised.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPMM_QtyPromised_OnDate_AtDate (@Nullable BigDecimal PMM_QtyPromised_OnDate_AtDate);

	/**
	 * Get 📆 Vendor Promised.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPMM_QtyPromised_OnDate_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_PMM_QtyPromised_OnDate_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "PMM_QtyPromised_OnDate_AtDate", null);
	String COLUMNNAME_PMM_QtyPromised_OnDate_AtDate = "PMM_QtyPromised_OnDate_AtDate";

	/**
	 * Set Vendor promised for next day.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPMM_QtyPromised_NextDay(@Nullable BigDecimal PMM_QtyPromised_NextDay);

	/**
	 * Get Vendor promised for next day.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPMM_QtyPromised_NextDay();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_PMM_QtyPromised_NextDay = new ModelColumn<>(I_MD_Cockpit.class, "PMM_QtyPromised_NextDay", null);
	String COLUMNNAME_PMM_QtyPromised_NextDay = "PMM_QtyPromised_NextDay";

	/**
	 * Set Produktname.
	 * Name des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductName (@Nullable String ProductName);

	/**
	 * Get Produktname.
	 * Name des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getProductName();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_ProductName = new ModelColumn<>(I_MD_Cockpit.class, "ProductName", null);
	String COLUMNNAME_ProductName = "ProductName";

	/**
	 * Set Product Value.
	 * Product identifier;
 "val-<search key>", "ext-<external id>" or internal M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductValue (@Nullable String ProductValue);

	/**
	 * Get Product Value.
	 * Product identifier;
 "val-<search key>", "ext-<external id>" or internal M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getProductValue();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_ProductValue = new ModelColumn<>(I_MD_Cockpit.class, "ProductValue", null);
	String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set 📆 Pending distribution source.
	 * Not-yet removed quantity from a distribution order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDemand_DD_Order_AtDate (@Nullable BigDecimal QtyDemand_DD_Order_AtDate);

	/**
	 * Get 📆 Pending distribution source.
	 * Not-yet removed quantity from a distribution order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDemand_DD_Order_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyDemand_DD_Order_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyDemand_DD_Order_AtDate", null);
	String COLUMNNAME_QtyDemand_DD_Order_AtDate = "QtyDemand_DD_Order_AtDate";

	/**
	 * Set 📆 Manufacturing issue - pending.
	 * Not-yet issued quantity from a manufacturing order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDemand_PP_Order_AtDate (@Nullable BigDecimal QtyDemand_PP_Order_AtDate);

	/**
	 * Get 📆 Manufacturing issue - pending.
	 * Not-yet issued quantity from a manufacturing order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDemand_PP_Order_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyDemand_PP_Order_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyDemand_PP_Order_AtDate", null);
	String COLUMNNAME_QtyDemand_PP_Order_AtDate = "QtyDemand_PP_Order_AtDate";

	/**
	 * Set 📆 Sold - pending.
	 * Quantity from sales orderes for the respective date that was not yet shipped.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDemand_SalesOrder_AtDate (@Nullable BigDecimal QtyDemand_SalesOrder_AtDate);

	/**
	 * Get 📆 Sold - pending.
	 * Quantity from sales orderes for the respective date that was not yet shipped.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDemand_SalesOrder_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyDemand_SalesOrder_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyDemand_SalesOrder_AtDate", null);
	String COLUMNNAME_QtyDemand_SalesOrder_AtDate = "QtyDemand_SalesOrder_AtDate";

	/**
	 * Set 📆 Pending demands.
	 * Sum of the planned demands from purchase orders, manufacturing orders and destribution orders
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDemandSum_AtDate (@Nullable BigDecimal QtyDemandSum_AtDate);

	/**
	 * Get 📆 Pending demands.
	 * Sum of the planned demands from purchase orders, manufacturing orders and destribution orders
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDemandSum_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyDemandSum_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyDemandSum_AtDate", null);
	String COLUMNNAME_QtyDemandSum_AtDate = "QtyDemandSum_AtDate";

	/**
	 * Set 📆 Expected surplus.
	 * Stock with added/subtracted pending supplies and issues
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyExpectedSurplus_AtDate (@Nullable BigDecimal QtyExpectedSurplus_AtDate);

	/**
	 * Get 📆 Expected surplus.
	 * Stock with added/subtracted pending supplies and issues
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyExpectedSurplus_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyExpectedSurplus_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyExpectedSurplus_AtDate", null);
	String COLUMNNAME_QtyExpectedSurplus_AtDate = "QtyExpectedSurplus_AtDate";

	/**
	 * Set 📆 Inventory count.
	 * Bestand laut der letzten Inventur
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyInventoryCount_AtDate (@Nullable BigDecimal QtyInventoryCount_AtDate);

	/**
	 * Get 📆 Inventory count.
	 * Bestand laut der letzten Inventur
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyInventoryCount_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyInventoryCount_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyInventoryCount_AtDate", null);
	String COLUMNNAME_QtyInventoryCount_AtDate = "QtyInventoryCount_AtDate";

	/**
	 * Set 📆 Inventory time.
	 * Zeipunkt, an dem die Inventur fertig gestellt wurde.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyInventoryTime_AtDate (@Nullable java.sql.Timestamp QtyInventoryTime_AtDate);

	/**
	 * Get 📆 Inventory time.
	 * Zeipunkt, an dem die Inventur fertig gestellt wurde.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getQtyInventoryTime_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyInventoryTime_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyInventoryTime_AtDate", null);
	String COLUMNNAME_QtyInventoryTime_AtDate = "QtyInventoryTime_AtDate";

	/**
	 * Set 📆 Internal Usage.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyMaterialentnahme_AtDate (@Nullable BigDecimal QtyMaterialentnahme_AtDate);

	/**
	 * Get 📆 Internal Usage.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyMaterialentnahme_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyMaterialentnahme_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyMaterialentnahme_AtDate", null);
	String COLUMNNAME_QtyMaterialentnahme_AtDate = "QtyMaterialentnahme_AtDate";

	/**
	 * Set Zählmenge.
	 * Für den jeweiligen Tag gezählte/geschätze Bestandsmenge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyOnHandCount (@Nullable BigDecimal QtyOnHandCount);

	/**
	 * Get Zählmenge.
	 * Für den jeweiligen Tag gezählte/geschätze Bestandsmenge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOnHandCount();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyOnHandCount = new ModelColumn<>(I_MD_Cockpit.class, "QtyOnHandCount", null);
	String COLUMNNAME_QtyOnHandCount = "QtyOnHandCount";

	/**
	 * Set 📆 Purchased.
	 * Quantity from purchase orders for the respective date
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyOrdered_PurchaseOrder_AtDate(@Nullable BigDecimal QtyOrdered_PurchaseOrder_AtDate);

	/**
	 * Get 📆 Purchased.
	 * Quantity from purchase orders for the respective date
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrdered_PurchaseOrder_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyOrdered_PurchaseOrder_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyOrdered_PurchaseOrder_AtDate", null);
	String COLUMNNAME_QtyOrdered_PurchaseOrder_AtDate = "QtyOrdered_PurchaseOrder_AtDate";

	/**
	 * Set 📆 Sold.
	 * Quantity from sales orders for the respective date
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyOrdered_SalesOrder_AtDate(@Nullable BigDecimal QtyOrdered_SalesOrder_AtDate);

	/**
	 * Get 📆 Sold.
	 * Quantity from sales orders for the respective date
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrdered_SalesOrder_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyOrdered_SalesOrder_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyOrdered_SalesOrder_AtDate", null);
	String COLUMNNAME_QtyOrdered_SalesOrder_AtDate = "QtyOrdered_SalesOrder_AtDate";

	/**
	 * Set Bestandsänderung.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyStockChange (@Nullable BigDecimal QtyStockChange);

	/**
	 * Get Bestandsänderung.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyStockChange();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyStockChange = new ModelColumn<>(I_MD_Cockpit.class, "QtyStockChange", null);
	String COLUMNNAME_QtyStockChange = "QtyStockChange";

	/**
	 * Set 📆 Stock.
	 * On-hand quantity for the respective date, with added/subtracted material increases and decreases since the count or inventory.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyStockCurrent_AtDate (@Nullable BigDecimal QtyStockCurrent_AtDate);

	/**
	 * Get 📆 Stock.
	 * On-hand quantity for the respective date, with added/subtracted material increases and decreases since the count or inventory.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyStockCurrent_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyStockCurrent_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyStockCurrent_AtDate", null);
	String COLUMNNAME_QtyStockCurrent_AtDate = "QtyStockCurrent_AtDate";

	/**
	 * Set 📆 Stock count.
	 * Menge laut "grober" Zählung.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyStockEstimateCount_AtDate (@Nullable BigDecimal QtyStockEstimateCount_AtDate);

	/**
	 * Get 📆 Stock count.
	 * Menge laut "grober" Zählung.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyStockEstimateCount_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyStockEstimateCount_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyStockEstimateCount_AtDate", null);
	String COLUMNNAME_QtyStockEstimateCount_AtDate = "QtyStockEstimateCount_AtDate";

	/**
	 * Set 📆 Zählbestand Reihenfolge.
	 * "Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyStockEstimateSeqNo_AtDate (int QtyStockEstimateSeqNo_AtDate);

	/**
	 * Get 📆 Zählbestand Reihenfolge.
	 * "Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getQtyStockEstimateSeqNo_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyStockEstimateSeqNo_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyStockEstimateSeqNo_AtDate", null);
	String COLUMNNAME_QtyStockEstimateSeqNo_AtDate = "QtyStockEstimateSeqNo_AtDate";

	/**
	 * Set 📆 Stock estimate time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyStockEstimateTime_AtDate (@Nullable java.sql.Timestamp QtyStockEstimateTime_AtDate);

	/**
	 * Get 📆 Stock estimate time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getQtyStockEstimateTime_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyStockEstimateTime_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyStockEstimateTime_AtDate", null);
	String COLUMNNAME_QtyStockEstimateTime_AtDate = "QtyStockEstimateTime_AtDate";

	/**
	 * Set 📆 Pending distribution target.
	 * Not-yet added quantity from a distribution order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtySupply_DD_Order_AtDate (@Nullable BigDecimal QtySupply_DD_Order_AtDate);

	/**
	 * Get 📆 Pending distribution target.
	 * Not-yet added quantity from a distribution order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtySupply_DD_Order_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupply_DD_Order_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtySupply_DD_Order_AtDate", null);
	String COLUMNNAME_QtySupply_DD_Order_AtDate = "QtySupply_DD_Order_AtDate";

	/**
	 * Set 📆 Pending manufacturing receipt.
	 * Not-yet received quantity from a manufacturing order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtySupply_PP_Order_AtDate (@Nullable BigDecimal QtySupply_PP_Order_AtDate);

	/**
	 * Get 📆 Pending manufacturing receipt.
	 * Not-yet received quantity from a manufacturing order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtySupply_PP_Order_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupply_PP_Order_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtySupply_PP_Order_AtDate", null);
	String COLUMNNAME_QtySupply_PP_Order_AtDate = "QtySupply_PP_Order_AtDate";

	/**
	 * Set 📆 Purchased - pending.
	 * Quantity from purchase orders for the respective date that was not yet received
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtySupply_PurchaseOrder_AtDate (@Nullable BigDecimal QtySupply_PurchaseOrder_AtDate);

	/**
	 * Get 📆 Purchased - pending.
	 * Quantity from purchase orders for the respective date that was not yet received
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtySupply_PurchaseOrder_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupply_PurchaseOrder_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtySupply_PurchaseOrder_AtDate", null);
	String COLUMNNAME_QtySupply_PurchaseOrder_AtDate = "QtySupply_PurchaseOrder_AtDate";

	/**
	 * Set 📆 Required supplies.
	 * Sum of all required supplies, where the planned stock is below the planned shippings
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtySupplyRequired_AtDate (@Nullable BigDecimal QtySupplyRequired_AtDate);

	/**
	 * Get 📆 Required supplies.
	 * Sum of all required supplies, where the planned stock is below the planned shippings
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtySupplyRequired_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupplyRequired_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtySupplyRequired_AtDate", null);
	String COLUMNNAME_QtySupplyRequired_AtDate = "QtySupplyRequired_AtDate";

	/**
	 * Set 📆 Pending supplies.
	 * Sum of the planned supplies from purchase orders, manufacturing orders and destribution orders
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtySupplySum_AtDate (@Nullable BigDecimal QtySupplySum_AtDate);

	/**
	 * Get 📆 Pending supplies.
	 * Sum of the planned supplies from purchase orders, manufacturing orders and destribution orders
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtySupplySum_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupplySum_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtySupplySum_AtDate", null);
	String COLUMNNAME_QtySupplySum_AtDate = "QtySupplySum_AtDate";

	/**
	 * Set 📆 Open requriements.
	 * Required supplies that are not yet covered by purchase, production or distribution.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtySupplyToSchedule_AtDate (@Nullable BigDecimal QtySupplyToSchedule_AtDate);

	/**
	 * Get 📆 Open requriements.
	 * Required supplies that are not yet covered by purchase, production or distribution.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtySupplyToSchedule_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupplyToSchedule_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtySupplyToSchedule_AtDate", null);
	String COLUMNNAME_QtySupplyToSchedule_AtDate = "QtySupplyToSchedule_AtDate";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_Updated = new ModelColumn<>(I_MD_Cockpit.class, "Updated", null);
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
