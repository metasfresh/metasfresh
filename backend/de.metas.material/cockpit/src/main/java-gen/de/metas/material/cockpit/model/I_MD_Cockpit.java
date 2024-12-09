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
<<<<<<< HEAD
	void setAttributesKey (@Nullable java.lang.String AttributesKey);
=======
	void setAttributesKey (@Nullable String AttributesKey);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get AttributesKey (technical).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	@Nullable java.lang.String getAttributesKey();
=======
	@Nullable String getAttributesKey();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
	 * Set MDCandidateQtyStock.
=======
	 * Set 📆 MDCandidateQtyStock.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setMDCandidateQtyStock (@Nullable BigDecimal MDCandidateQtyStock);

	/**
	 * Get MDCandidateQtyStock.
=======
	void setMDCandidateQtyStock_AtDate (@Nullable BigDecimal MDCandidateQtyStock_AtDate);

	/**
	 * Get 📆 MDCandidateQtyStock.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	BigDecimal getMDCandidateQtyStock();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_MDCandidateQtyStock = new ModelColumn<>(I_MD_Cockpit.class, "MDCandidateQtyStock", null);
	String COLUMNNAME_MDCandidateQtyStock = "MDCandidateQtyStock";
=======
	BigDecimal getMDCandidateQtyStock_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_MDCandidateQtyStock_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "MDCandidateQtyStock_AtDate", null);
	String COLUMNNAME_MDCandidateQtyStock_AtDate = "MDCandidateQtyStock_AtDate";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
	 * Set Vendor Promised.
	 * Vom Lieferanten per Webapplikation zugesagte Menge
=======
	 * Set Vendor promised for next day.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setPMM_QtyPromised_OnDate (@Nullable BigDecimal PMM_QtyPromised_OnDate);

	/**
	 * Get Vendor Promised.
	 * Vom Lieferanten per Webapplikation zugesagte Menge
=======
	void setPMM_QtyPromised_NextDay (@Nullable BigDecimal PMM_QtyPromised_NextDay);

	/**
	 * Get Vendor promised for next day.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	BigDecimal getPMM_QtyPromised_OnDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_PMM_QtyPromised_OnDate = new ModelColumn<>(I_MD_Cockpit.class, "PMM_QtyPromised_OnDate", null);
	String COLUMNNAME_PMM_QtyPromised_OnDate = "PMM_QtyPromised_OnDate";
=======
	BigDecimal getPMM_QtyPromised_NextDay();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_PMM_QtyPromised_NextDay = new ModelColumn<>(I_MD_Cockpit.class, "PMM_QtyPromised_NextDay", null);
	String COLUMNNAME_PMM_QtyPromised_NextDay = "PMM_QtyPromised_NextDay";

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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Produktname.
	 * Name des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setProductName (@Nullable java.lang.String ProductName);
=======
	void setProductName (@Nullable String ProductName);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Produktname.
	 * Name des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	@Nullable java.lang.String getProductName();
=======
	@Nullable String getProductName();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
	void setProductValue (@Nullable java.lang.String ProductValue);
=======
	void setProductValue (@Nullable String ProductValue);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Product Value.
	 * Product identifier;
 "val-<search key>", "ext-<external id>" or internal M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	@Nullable java.lang.String getProductValue();
=======
	@Nullable String getProductValue();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	ModelColumn<I_MD_Cockpit, Object> COLUMN_ProductValue = new ModelColumn<>(I_MD_Cockpit.class, "ProductValue", null);
	String COLUMNNAME_ProductValue = "ProductValue";

	/**
<<<<<<< HEAD
	 * Set Pending distribution source.
=======
	 * Set 📆 Pending distribution source.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Not-yet removed quantity from a distribution order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setQtyDemand_DD_Order (@Nullable BigDecimal QtyDemand_DD_Order);

	/**
	 * Get Pending distribution source.
=======
	void setQtyDemand_DD_Order_AtDate (@Nullable BigDecimal QtyDemand_DD_Order_AtDate);

	/**
	 * Get 📆 Pending distribution source.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Not-yet removed quantity from a distribution order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	BigDecimal getQtyDemand_DD_Order();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyDemand_DD_Order = new ModelColumn<>(I_MD_Cockpit.class, "QtyDemand_DD_Order", null);
	String COLUMNNAME_QtyDemand_DD_Order = "QtyDemand_DD_Order";

	/**
	 * Set Manufacturing issue - pending.
=======
	BigDecimal getQtyDemand_DD_Order_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyDemand_DD_Order_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyDemand_DD_Order_AtDate", null);
	String COLUMNNAME_QtyDemand_DD_Order_AtDate = "QtyDemand_DD_Order_AtDate";

	/**
	 * Set 📆 Manufacturing issue - pending.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Not-yet issued quantity from a manufacturing order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setQtyDemand_PP_Order (@Nullable BigDecimal QtyDemand_PP_Order);

	/**
	 * Get Manufacturing issue - pending.
=======
	void setQtyDemand_PP_Order_AtDate (@Nullable BigDecimal QtyDemand_PP_Order_AtDate);

	/**
	 * Get 📆 Manufacturing issue - pending.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Not-yet issued quantity from a manufacturing order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	BigDecimal getQtyDemand_PP_Order();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyDemand_PP_Order = new ModelColumn<>(I_MD_Cockpit.class, "QtyDemand_PP_Order", null);
	String COLUMNNAME_QtyDemand_PP_Order = "QtyDemand_PP_Order";

	/**
	 * Set Sold - pending.
=======
	BigDecimal getQtyDemand_PP_Order_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyDemand_PP_Order_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyDemand_PP_Order_AtDate", null);
	String COLUMNNAME_QtyDemand_PP_Order_AtDate = "QtyDemand_PP_Order_AtDate";

	/**
	 * Set 📆 Sold - pending.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Quantity from sales orderes for the respective date that was not yet shipped.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setQtyDemand_SalesOrder (@Nullable BigDecimal QtyDemand_SalesOrder);

	/**
	 * Get Sold - pending.
=======
	void setQtyDemand_SalesOrder_AtDate (@Nullable BigDecimal QtyDemand_SalesOrder_AtDate);

	/**
	 * Get 📆 Sold - pending.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Quantity from sales orderes for the respective date that was not yet shipped.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	BigDecimal getQtyDemand_SalesOrder();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyDemand_SalesOrder = new ModelColumn<>(I_MD_Cockpit.class, "QtyDemand_SalesOrder", null);
	String COLUMNNAME_QtyDemand_SalesOrder = "QtyDemand_SalesOrder";

	/**
	 * Set Pending demands.
=======
	BigDecimal getQtyDemand_SalesOrder_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyDemand_SalesOrder_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyDemand_SalesOrder_AtDate", null);
	String COLUMNNAME_QtyDemand_SalesOrder_AtDate = "QtyDemand_SalesOrder_AtDate";

	/**
	 * Set 📆 Pending demands.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Sum of the planned demands from purchase orders, manufacturing orders and destribution orders
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setQtyDemandSum (@Nullable BigDecimal QtyDemandSum);

	/**
	 * Get Pending demands.
=======
	void setQtyDemandSum_AtDate (@Nullable BigDecimal QtyDemandSum_AtDate);

	/**
	 * Get 📆 Pending demands.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Sum of the planned demands from purchase orders, manufacturing orders and destribution orders
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	BigDecimal getQtyDemandSum();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyDemandSum = new ModelColumn<>(I_MD_Cockpit.class, "QtyDemandSum", null);
	String COLUMNNAME_QtyDemandSum = "QtyDemandSum";

	/**
	 * Set Expected surplus.
=======
	BigDecimal getQtyDemandSum_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyDemandSum_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyDemandSum_AtDate", null);
	String COLUMNNAME_QtyDemandSum_AtDate = "QtyDemandSum_AtDate";

	/**
	 * Set 📆 Expected surplus.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Stock with added/subtracted pending supplies and issues
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setQtyExpectedSurplus (@Nullable BigDecimal QtyExpectedSurplus);

	/**
	 * Get Expected surplus.
=======
	void setQtyExpectedSurplus_AtDate (@Nullable BigDecimal QtyExpectedSurplus_AtDate);

	/**
	 * Get 📆 Expected surplus.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Stock with added/subtracted pending supplies and issues
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	BigDecimal getQtyExpectedSurplus();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyExpectedSurplus = new ModelColumn<>(I_MD_Cockpit.class, "QtyExpectedSurplus", null);
	String COLUMNNAME_QtyExpectedSurplus = "QtyExpectedSurplus";

	/**
	 * Set Inventory count.
=======
	BigDecimal getQtyExpectedSurplus_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyExpectedSurplus_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyExpectedSurplus_AtDate", null);
	String COLUMNNAME_QtyExpectedSurplus_AtDate = "QtyExpectedSurplus_AtDate";

	/**
	 * Set 📆 Inventory count.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Bestand laut der letzten Inventur
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setQtyInventoryCount (@Nullable BigDecimal QtyInventoryCount);

	/**
	 * Get Inventory count.
=======
	void setQtyInventoryCount_AtDate (@Nullable BigDecimal QtyInventoryCount_AtDate);

	/**
	 * Get 📆 Inventory count.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Bestand laut der letzten Inventur
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	BigDecimal getQtyInventoryCount();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyInventoryCount = new ModelColumn<>(I_MD_Cockpit.class, "QtyInventoryCount", null);
	String COLUMNNAME_QtyInventoryCount = "QtyInventoryCount";

	/**
	 * Set Inventory time.
=======
	BigDecimal getQtyInventoryCount_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyInventoryCount_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyInventoryCount_AtDate", null);
	String COLUMNNAME_QtyInventoryCount_AtDate = "QtyInventoryCount_AtDate";

	/**
	 * Set 📆 Inventory time.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Zeipunkt, an dem die Inventur fertig gestellt wurde.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setQtyInventoryTime (@Nullable java.sql.Timestamp QtyInventoryTime);

	/**
	 * Get Inventory time.
=======
	void setQtyInventoryTime_AtDate (@Nullable java.sql.Timestamp QtyInventoryTime_AtDate);

	/**
	 * Get 📆 Inventory time.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Zeipunkt, an dem die Inventur fertig gestellt wurde.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	@Nullable java.sql.Timestamp getQtyInventoryTime();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyInventoryTime = new ModelColumn<>(I_MD_Cockpit.class, "QtyInventoryTime", null);
	String COLUMNNAME_QtyInventoryTime = "QtyInventoryTime";

	/**
	 * Set Internal Usage.
=======
	@Nullable java.sql.Timestamp getQtyInventoryTime_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyInventoryTime_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyInventoryTime_AtDate", null);
	String COLUMNNAME_QtyInventoryTime_AtDate = "QtyInventoryTime_AtDate";

	/**
	 * Set 📆 Internal Usage.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setQtyMaterialentnahme (@Nullable BigDecimal QtyMaterialentnahme);

	/**
	 * Get Internal Usage.
=======
	void setQtyMaterialentnahme_AtDate (@Nullable BigDecimal QtyMaterialentnahme_AtDate);

	/**
	 * Get 📆 Internal Usage.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	BigDecimal getQtyMaterialentnahme();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyMaterialentnahme = new ModelColumn<>(I_MD_Cockpit.class, "QtyMaterialentnahme", null);
	String COLUMNNAME_QtyMaterialentnahme = "QtyMaterialentnahme";
=======
	BigDecimal getQtyMaterialentnahme_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyMaterialentnahme_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyMaterialentnahme_AtDate", null);
	String COLUMNNAME_QtyMaterialentnahme_AtDate = "QtyMaterialentnahme_AtDate";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
=======
	 * Set 📆 Purchased.
	 * Quantity from purchase orders for the respective date
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyOrdered_PurchaseOrder_AtDate (@Nullable BigDecimal QtyOrdered_PurchaseOrder_AtDate);

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
	void setQtyOrdered_SalesOrder_AtDate (@Nullable BigDecimal QtyOrdered_SalesOrder_AtDate);

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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	 * Set Stock.
=======
	 * Set 📆 Stock.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * On-hand quantity for the respective date, with added/subtracted material increases and decreases since the count or inventory.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setQtyStockCurrent (@Nullable BigDecimal QtyStockCurrent);

	/**
	 * Get Stock.
=======
	void setQtyStockCurrent_AtDate (@Nullable BigDecimal QtyStockCurrent_AtDate);

	/**
	 * Get 📆 Stock.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * On-hand quantity for the respective date, with added/subtracted material increases and decreases since the count or inventory.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	BigDecimal getQtyStockCurrent();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyStockCurrent = new ModelColumn<>(I_MD_Cockpit.class, "QtyStockCurrent", null);
	String COLUMNNAME_QtyStockCurrent = "QtyStockCurrent";

	/**
	 * Set Stock count.
=======
	BigDecimal getQtyStockCurrent_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyStockCurrent_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyStockCurrent_AtDate", null);
	String COLUMNNAME_QtyStockCurrent_AtDate = "QtyStockCurrent_AtDate";

	/**
	 * Set 📆 Stock count.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Menge laut "grober" Zählung.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setQtyStockEstimateCount (@Nullable BigDecimal QtyStockEstimateCount);

	/**
	 * Get Stock count.
=======
	void setQtyStockEstimateCount_AtDate (@Nullable BigDecimal QtyStockEstimateCount_AtDate);

	/**
	 * Get 📆 Stock count.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Menge laut "grober" Zählung.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	BigDecimal getQtyStockEstimateCount();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyStockEstimateCount = new ModelColumn<>(I_MD_Cockpit.class, "QtyStockEstimateCount", null);
	String COLUMNNAME_QtyStockEstimateCount = "QtyStockEstimateCount";

	/**
	 * Set Zählbestand Reihenfolge.
=======
	BigDecimal getQtyStockEstimateCount_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyStockEstimateCount_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyStockEstimateCount_AtDate", null);
	String COLUMNNAME_QtyStockEstimateCount_AtDate = "QtyStockEstimateCount_AtDate";

	/**
	 * Set 📆 Zählbestand Reihenfolge.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * "Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setQtyStockEstimateSeqNo (int QtyStockEstimateSeqNo);

	/**
	 * Get Zählbestand Reihenfolge.
=======
	void setQtyStockEstimateSeqNo_AtDate (int QtyStockEstimateSeqNo_AtDate);

	/**
	 * Get 📆 Zählbestand Reihenfolge.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * "Reihenfolge"-Wert mit dem die entsprechende Position im Zählbestand erfasst wurde
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	int getQtyStockEstimateSeqNo();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyStockEstimateSeqNo = new ModelColumn<>(I_MD_Cockpit.class, "QtyStockEstimateSeqNo", null);
	String COLUMNNAME_QtyStockEstimateSeqNo = "QtyStockEstimateSeqNo";

	/**
	 * Set Stock estimate time.
=======
	int getQtyStockEstimateSeqNo_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyStockEstimateSeqNo_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyStockEstimateSeqNo_AtDate", null);
	String COLUMNNAME_QtyStockEstimateSeqNo_AtDate = "QtyStockEstimateSeqNo_AtDate";

	/**
	 * Set 📆 Stock estimate time.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setQtyStockEstimateTime (@Nullable java.sql.Timestamp QtyStockEstimateTime);

	/**
	 * Get Stock estimate time.
=======
	void setQtyStockEstimateTime_AtDate (@Nullable java.sql.Timestamp QtyStockEstimateTime_AtDate);

	/**
	 * Get 📆 Stock estimate time.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	@Nullable java.sql.Timestamp getQtyStockEstimateTime();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyStockEstimateTime = new ModelColumn<>(I_MD_Cockpit.class, "QtyStockEstimateTime", null);
	String COLUMNNAME_QtyStockEstimateTime = "QtyStockEstimateTime";

	/**
	 * Set Pending distribution target.
=======
	@Nullable java.sql.Timestamp getQtyStockEstimateTime_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtyStockEstimateTime_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtyStockEstimateTime_AtDate", null);
	String COLUMNNAME_QtyStockEstimateTime_AtDate = "QtyStockEstimateTime_AtDate";

	/**
	 * Set 📆 Pending distribution target.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Not-yet added quantity from a distribution order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setQtySupply_DD_Order (@Nullable BigDecimal QtySupply_DD_Order);

	/**
	 * Get Pending distribution target.
=======
	void setQtySupply_DD_Order_AtDate (@Nullable BigDecimal QtySupply_DD_Order_AtDate);

	/**
	 * Get 📆 Pending distribution target.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Not-yet added quantity from a distribution order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	BigDecimal getQtySupply_DD_Order();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupply_DD_Order = new ModelColumn<>(I_MD_Cockpit.class, "QtySupply_DD_Order", null);
	String COLUMNNAME_QtySupply_DD_Order = "QtySupply_DD_Order";

	/**
	 * Set Pending manufacturing receipt.
=======
	BigDecimal getQtySupply_DD_Order_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupply_DD_Order_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtySupply_DD_Order_AtDate", null);
	String COLUMNNAME_QtySupply_DD_Order_AtDate = "QtySupply_DD_Order_AtDate";

	/**
	 * Set 📆 Pending manufacturing receipt.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Not-yet received quantity from a manufacturing order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setQtySupply_PP_Order (@Nullable BigDecimal QtySupply_PP_Order);

	/**
	 * Get Pending manufacturing receipt.
=======
	void setQtySupply_PP_Order_AtDate (@Nullable BigDecimal QtySupply_PP_Order_AtDate);

	/**
	 * Get 📆 Pending manufacturing receipt.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Not-yet received quantity from a manufacturing order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	BigDecimal getQtySupply_PP_Order();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupply_PP_Order = new ModelColumn<>(I_MD_Cockpit.class, "QtySupply_PP_Order", null);
	String COLUMNNAME_QtySupply_PP_Order = "QtySupply_PP_Order";

	/**
	 * Set Purchased - pending.
=======
	BigDecimal getQtySupply_PP_Order_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupply_PP_Order_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtySupply_PP_Order_AtDate", null);
	String COLUMNNAME_QtySupply_PP_Order_AtDate = "QtySupply_PP_Order_AtDate";

	/**
	 * Set 📆 Purchased - pending.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Quantity from purchase orders for the respective date that was not yet received
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setQtySupply_PurchaseOrder (@Nullable BigDecimal QtySupply_PurchaseOrder);

	/**
	 * Get Purchased - pending.
=======
	void setQtySupply_PurchaseOrder_AtDate (@Nullable BigDecimal QtySupply_PurchaseOrder_AtDate);

	/**
	 * Get 📆 Purchased - pending.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Quantity from purchase orders for the respective date that was not yet received
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	BigDecimal getQtySupply_PurchaseOrder();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupply_PurchaseOrder = new ModelColumn<>(I_MD_Cockpit.class, "QtySupply_PurchaseOrder", null);
	String COLUMNNAME_QtySupply_PurchaseOrder = "QtySupply_PurchaseOrder";

	/**
	 * Set Required supplies.
=======
	BigDecimal getQtySupply_PurchaseOrder_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupply_PurchaseOrder_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtySupply_PurchaseOrder_AtDate", null);
	String COLUMNNAME_QtySupply_PurchaseOrder_AtDate = "QtySupply_PurchaseOrder_AtDate";

	/**
	 * Set 📆 Required supplies.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Sum of all required supplies, where the planned stock is below the planned shippings
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setQtySupplyRequired (@Nullable BigDecimal QtySupplyRequired);

	/**
	 * Get Required supplies.
=======
	void setQtySupplyRequired_AtDate (@Nullable BigDecimal QtySupplyRequired_AtDate);

	/**
	 * Get 📆 Required supplies.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Sum of all required supplies, where the planned stock is below the planned shippings
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	BigDecimal getQtySupplyRequired();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupplyRequired = new ModelColumn<>(I_MD_Cockpit.class, "QtySupplyRequired", null);
	String COLUMNNAME_QtySupplyRequired = "QtySupplyRequired";

	/**
	 * Set Pending supplies.
=======
	BigDecimal getQtySupplyRequired_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupplyRequired_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtySupplyRequired_AtDate", null);
	String COLUMNNAME_QtySupplyRequired_AtDate = "QtySupplyRequired_AtDate";

	/**
	 * Set 📆 Pending supplies.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Sum of the planned supplies from purchase orders, manufacturing orders and destribution orders
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setQtySupplySum (@Nullable BigDecimal QtySupplySum);

	/**
	 * Get Pending supplies.
=======
	void setQtySupplySum_AtDate (@Nullable BigDecimal QtySupplySum_AtDate);

	/**
	 * Get 📆 Pending supplies.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Sum of the planned supplies from purchase orders, manufacturing orders and destribution orders
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	BigDecimal getQtySupplySum();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupplySum = new ModelColumn<>(I_MD_Cockpit.class, "QtySupplySum", null);
	String COLUMNNAME_QtySupplySum = "QtySupplySum";

	/**
	 * Set Open requriements.
=======
	BigDecimal getQtySupplySum_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupplySum_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtySupplySum_AtDate", null);
	String COLUMNNAME_QtySupplySum_AtDate = "QtySupplySum_AtDate";

	/**
	 * Set 📆 Open requriements.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Required supplies that are not yet covered by purchase, production or distribution.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	void setQtySupplyToSchedule (@Nullable BigDecimal QtySupplyToSchedule);

	/**
	 * Get Open requriements.
=======
	void setQtySupplyToSchedule_AtDate (@Nullable BigDecimal QtySupplyToSchedule_AtDate);

	/**
	 * Get 📆 Open requriements.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Required supplies that are not yet covered by purchase, production or distribution.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	BigDecimal getQtySupplyToSchedule();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupplyToSchedule = new ModelColumn<>(I_MD_Cockpit.class, "QtySupplyToSchedule", null);
	String COLUMNNAME_QtySupplyToSchedule = "QtySupplyToSchedule";
=======
	BigDecimal getQtySupplyToSchedule_AtDate();

	ModelColumn<I_MD_Cockpit, Object> COLUMN_QtySupplyToSchedule_AtDate = new ModelColumn<>(I_MD_Cockpit.class, "QtySupplyToSchedule_AtDate", null);
	String COLUMNNAME_QtySupplyToSchedule_AtDate = "QtySupplyToSchedule_AtDate";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
