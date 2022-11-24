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
	void setAttributesKey (@Nullable java.lang.String AttributesKey);

	/**
	 * Get AttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAttributesKey();

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
	 * Set MDCandidateQtyStock.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMDCandidateQtyStock (@Nullable BigDecimal MDCandidateQtyStock);

	/**
	 * Get MDCandidateQtyStock.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getMDCandidateQtyStock();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_MDCandidateQtyStock = new ModelColumn<>(I_MD_Cockpit.class, "MDCandidateQtyStock", null);
	String COLUMNNAME_MDCandidateQtyStock = "MDCandidateQtyStock";

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
	 * Set Vendor Promised.
	 * Vom Lieferanten per Webapplikation zugesagte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPMM_QtyPromised_OnDate (@Nullable BigDecimal PMM_QtyPromised_OnDate);

	/**
	 * Get Vendor Promised.
	 * Vom Lieferanten per Webapplikation zugesagte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPMM_QtyPromised_OnDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_PMM_QtyPromised_OnDate = new ModelColumn<>(I_MD_Cockpit.class, "PMM_QtyPromised_OnDate", null);
	String COLUMNNAME_PMM_QtyPromised_OnDate = "PMM_QtyPromised_OnDate";

	/**
	 * Set Produktname.
	 * Name des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductName (@Nullable java.lang.String ProductName);

	/**
	 * Get Produktname.
	 * Name des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductName();

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
	void setProductValue (@Nullable java.lang.String ProductValue);

	/**
	 * Get Product Value.
	 * Product identifier;
 "val-<search key>", "ext-<external id>" or internal M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductValue();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_ProductValue = new ModelColumn<>(I_MD_Cockpit.class, "ProductValue", null);
	String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set Pending distribution source.
	 * Not-yet removed quantity from a distribution order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDemand_DD_Order (@Nullable BigDecimal QtyDemand_DD_Order);

	/**
	 * Get Pending distribution source.
	 * Not-yet removed quantity from a distribution order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDemand_DD_Order();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyDemand_DD_Order = new ModelColumn<>(I_MD_Cockpit.class, "QtyDemand_DD_Order", null);
	String COLUMNNAME_QtyDemand_DD_Order = "QtyDemand_DD_Order";

	/**
	 * Set Manufacturing issue - pending.
	 * Not-yet issued quantity from a manufacturing order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDemand_PP_Order (@Nullable BigDecimal QtyDemand_PP_Order);

	/**
	 * Get Manufacturing issue - pending.
	 * Not-yet issued quantity from a manufacturing order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDemand_PP_Order();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyDemand_PP_Order = new ModelColumn<>(I_MD_Cockpit.class, "QtyDemand_PP_Order", null);
	String COLUMNNAME_QtyDemand_PP_Order = "QtyDemand_PP_Order";

	/**
	 * Set Sold - pending.
	 * Quantity from sales orderes for the respective date that was not yet shipped.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDemand_SalesOrder (@Nullable BigDecimal QtyDemand_SalesOrder);

	/**
	 * Get Sold - pending.
	 * Quantity from sales orderes for the respective date that was not yet shipped.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDemand_SalesOrder();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyDemand_SalesOrder = new ModelColumn<>(I_MD_Cockpit.class, "QtyDemand_SalesOrder", null);
	String COLUMNNAME_QtyDemand_SalesOrder = "QtyDemand_SalesOrder";

	/**
	 * Set Pending demands.
	 * Sum of the planned demands from purchase orders, manufacturing orders and destribution orders
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDemandSum (@Nullable BigDecimal QtyDemandSum);

	/**
	 * Get Pending demands.
	 * Sum of the planned demands from purchase orders, manufacturing orders and destribution orders
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDemandSum();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyDemandSum = new ModelColumn<>(I_MD_Cockpit.class, "QtyDemandSum", null);
	String COLUMNNAME_QtyDemandSum = "QtyDemandSum";

	/**
	 * Set Expected surplus.
	 * Stock with added/subtracted pending supplies and issues
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyExpectedSurplus (@Nullable BigDecimal QtyExpectedSurplus);

	/**
	 * Get Expected surplus.
	 * Stock with added/subtracted pending supplies and issues
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyExpectedSurplus();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyExpectedSurplus = new ModelColumn<>(I_MD_Cockpit.class, "QtyExpectedSurplus", null);
	String COLUMNNAME_QtyExpectedSurplus = "QtyExpectedSurplus";

	/**
	 * Set Inventory count.
	 * Bestand laut der letzten Inventur
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyInventoryCount (@Nullable BigDecimal QtyInventoryCount);

	/**
	 * Get Inventory count.
	 * Bestand laut der letzten Inventur
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyInventoryCount();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyInventoryCount = new ModelColumn<>(I_MD_Cockpit.class, "QtyInventoryCount", null);
	String COLUMNNAME_QtyInventoryCount = "QtyInventoryCount";

	/**
	 * Set Inventory time.
	 * Zeipunkt, an dem die Inventur fertig gestellt wurde.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyInventoryTime (@Nullable java.sql.Timestamp QtyInventoryTime);

	/**
	 * Get Inventory time.
	 * Zeipunkt, an dem die Inventur fertig gestellt wurde.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getQtyInventoryTime();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyInventoryTime = new ModelColumn<>(I_MD_Cockpit.class, "QtyInventoryTime", null);
	String COLUMNNAME_QtyInventoryTime = "QtyInventoryTime";

	/**
	 * Set Internal Usage.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyMaterialentnahme (@Nullable BigDecimal QtyMaterialentnahme);

	/**
	 * Get Internal Usage.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyMaterialentnahme();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyMaterialentnahme = new ModelColumn<>(I_MD_Cockpit.class, "QtyMaterialentnahme", null);
	String COLUMNNAME_QtyMaterialentnahme = "QtyMaterialentnahme";

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
	 * Set Stock.
	 * On-hand quantity for the respective date, with added/subtracted material increases and decreases since the count or inventory.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyStockCurrent (@Nullable BigDecimal QtyStockCurrent);

	/**
	 * Get Stock.
	 * On-hand quantity for the respective date, with added/subtracted material increases and decreases since the count or inventory.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyStockCurrent();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyStockCurrent = new ModelColumn<>(I_MD_Cockpit.class, "QtyStockCurrent", null);
	String COLUMNNAME_QtyStockCurrent = "QtyStockCurrent";

	/**
	 * Set Stock count.
	 * Menge laut "grober" Zählung.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyStockEstimateCount (@Nullable BigDecimal QtyStockEstimateCount);

	/**
	 * Get Stock count.
	 * Menge laut "grober" Zählung.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyStockEstimateCount();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyStockEstimateCount = new ModelColumn<>(I_MD_Cockpit.class, "QtyStockEstimateCount", null);
	String COLUMNNAME_QtyStockEstimateCount = "QtyStockEstimateCount";

	/**
	 * Set Zählbestand Reihenfolge.
	 * "Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyStockEstimateSeqNo (int QtyStockEstimateSeqNo);

	/**
	 * Get Zählbestand Reihenfolge.
	 * "Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getQtyStockEstimateSeqNo();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyStockEstimateSeqNo = new ModelColumn<>(I_MD_Cockpit.class, "QtyStockEstimateSeqNo", null);
	String COLUMNNAME_QtyStockEstimateSeqNo = "QtyStockEstimateSeqNo";

	/**
	 * Set Stock estimate time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyStockEstimateTime (@Nullable java.sql.Timestamp QtyStockEstimateTime);

	/**
	 * Get Stock estimate time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getQtyStockEstimateTime();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyStockEstimateTime = new ModelColumn<>(I_MD_Cockpit.class, "QtyStockEstimateTime", null);
	String COLUMNNAME_QtyStockEstimateTime = "QtyStockEstimateTime";

	/**
	 * Set Pending distribution target.
	 * Not-yet added quantity from a distribution order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtySupply_DD_Order (@Nullable BigDecimal QtySupply_DD_Order);

	/**
	 * Get Pending distribution target.
	 * Not-yet added quantity from a distribution order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtySupply_DD_Order();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupply_DD_Order = new ModelColumn<>(I_MD_Cockpit.class, "QtySupply_DD_Order", null);
	String COLUMNNAME_QtySupply_DD_Order = "QtySupply_DD_Order";

	/**
	 * Set Pending manufacturing receipt.
	 * Not-yet received quantity from a manufacturing order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtySupply_PP_Order (@Nullable BigDecimal QtySupply_PP_Order);

	/**
	 * Get Pending manufacturing receipt.
	 * Not-yet received quantity from a manufacturing order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtySupply_PP_Order();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupply_PP_Order = new ModelColumn<>(I_MD_Cockpit.class, "QtySupply_PP_Order", null);
	String COLUMNNAME_QtySupply_PP_Order = "QtySupply_PP_Order";

	/**
	 * Set Purchased - pending.
	 * Quantity from purchase orders for the respective date that was not yet received
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtySupply_PurchaseOrder (@Nullable BigDecimal QtySupply_PurchaseOrder);

	/**
	 * Get Purchased - pending.
	 * Quantity from purchase orders for the respective date that was not yet received
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtySupply_PurchaseOrder();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupply_PurchaseOrder = new ModelColumn<>(I_MD_Cockpit.class, "QtySupply_PurchaseOrder", null);
	String COLUMNNAME_QtySupply_PurchaseOrder = "QtySupply_PurchaseOrder";

	/**
	 * Set Required supplies.
	 * Sum of all required supplies, where the planned stock is below the planned shippings
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtySupplyRequired (@Nullable BigDecimal QtySupplyRequired);

	/**
	 * Get Required supplies.
	 * Sum of all required supplies, where the planned stock is below the planned shippings
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtySupplyRequired();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupplyRequired = new ModelColumn<>(I_MD_Cockpit.class, "QtySupplyRequired", null);
	String COLUMNNAME_QtySupplyRequired = "QtySupplyRequired";

	/**
	 * Set Pending supplies.
	 * Sum of the planned supplies from purchase orders, manufacturing orders and destribution orders
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtySupplySum (@Nullable BigDecimal QtySupplySum);

	/**
	 * Get Pending supplies.
	 * Sum of the planned supplies from purchase orders, manufacturing orders and destribution orders
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtySupplySum();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupplySum = new ModelColumn<>(I_MD_Cockpit.class, "QtySupplySum", null);
	String COLUMNNAME_QtySupplySum = "QtySupplySum";

	/**
	 * Set Open requriements.
	 * Required supplies that are not yet covered by purchase, production or distribution.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtySupplyToSchedule (@Nullable BigDecimal QtySupplyToSchedule);

	/**
	 * Get Open requriements.
	 * Required supplies that are not yet covered by purchase, production or distribution.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtySupplyToSchedule();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupplyToSchedule = new ModelColumn<>(I_MD_Cockpit.class, "QtySupplyToSchedule", null);
	String COLUMNNAME_QtySupplyToSchedule = "QtySupplyToSchedule";

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
