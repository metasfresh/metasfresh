package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for I_Inventory
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_I_Inventory 
{

	String Table_Name = "I_Inventory";

//	/** AD_Table_ID=572 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Issue_ID();

	String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Attribute Code 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAttributeCode1 (@Nullable java.lang.String AttributeCode1);

	/**
	 * Get Attribute Code 1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAttributeCode1();

	ModelColumn<I_I_Inventory, Object> COLUMN_AttributeCode1 = new ModelColumn<>(I_I_Inventory.class, "AttributeCode1", null);
	String COLUMNNAME_AttributeCode1 = "AttributeCode1";

	/**
	 * Set Attribute Code 2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAttributeCode2 (@Nullable java.lang.String AttributeCode2);

	/**
	 * Get Attribute Code 2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAttributeCode2();

	ModelColumn<I_I_Inventory, Object> COLUMN_AttributeCode2 = new ModelColumn<>(I_I_Inventory.class, "AttributeCode2", null);
	String COLUMNNAME_AttributeCode2 = "AttributeCode2";

	/**
	 * Set Attribute Code 3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAttributeCode3 (@Nullable java.lang.String AttributeCode3);

	/**
	 * Get Attribute Code 3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAttributeCode3();

	ModelColumn<I_I_Inventory, Object> COLUMN_AttributeCode3 = new ModelColumn<>(I_I_Inventory.class, "AttributeCode3", null);
	String COLUMNNAME_AttributeCode3 = "AttributeCode3";

	/**
	 * Set Attribute Code 4.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAttributeCode4 (@Nullable java.lang.String AttributeCode4);

	/**
	 * Get Attribute Code 4.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAttributeCode4();

	ModelColumn<I_I_Inventory, Object> COLUMN_AttributeCode4 = new ModelColumn<>(I_I_Inventory.class, "AttributeCode4", null);
	String COLUMNNAME_AttributeCode4 = "AttributeCode4";

	/**
	 * Set Attribute Value String 1.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAttributeValueString1 (@Nullable java.lang.String AttributeValueString1);

	/**
	 * Get Attribute Value String 1.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAttributeValueString1();

	ModelColumn<I_I_Inventory, Object> COLUMN_AttributeValueString1 = new ModelColumn<>(I_I_Inventory.class, "AttributeValueString1", null);
	String COLUMNNAME_AttributeValueString1 = "AttributeValueString1";

	/**
	 * Set Attribute Value String 2.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAttributeValueString2 (@Nullable java.lang.String AttributeValueString2);

	/**
	 * Get Attribute Value String 2.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAttributeValueString2();

	ModelColumn<I_I_Inventory, Object> COLUMN_AttributeValueString2 = new ModelColumn<>(I_I_Inventory.class, "AttributeValueString2", null);
	String COLUMNNAME_AttributeValueString2 = "AttributeValueString2";

	/**
	 * Set Attribute Value String 3.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAttributeValueString3 (@Nullable java.lang.String AttributeValueString3);

	/**
	 * Get Attribute Value String 3.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAttributeValueString3();

	ModelColumn<I_I_Inventory, Object> COLUMN_AttributeValueString3 = new ModelColumn<>(I_I_Inventory.class, "AttributeValueString3", null);
	String COLUMNNAME_AttributeValueString3 = "AttributeValueString3";

	/**
	 * Set Attribute Value String 4.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAttributeValueString4 (@Nullable java.lang.String AttributeValueString4);

	/**
	 * Get Attribute Value String 4.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAttributeValueString4();

	ModelColumn<I_I_Inventory, Object> COLUMN_AttributeValueString4 = new ModelColumn<>(I_I_Inventory.class, "AttributeValueString4", null);
	String COLUMNNAME_AttributeValueString4 = "AttributeValueString4";

	/**
	 * Set Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DataImport_ID (int C_DataImport_ID);

	/**
	 * Get Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DataImport_ID();

	@Nullable org.compiere.model.I_C_DataImport getC_DataImport();

	void setC_DataImport(@Nullable org.compiere.model.I_C_DataImport C_DataImport);

	ModelColumn<I_I_Inventory, org.compiere.model.I_C_DataImport> COLUMN_C_DataImport_ID = new ModelColumn<>(I_I_Inventory.class, "C_DataImport_ID", org.compiere.model.I_C_DataImport.class);
	String COLUMNNAME_C_DataImport_ID = "C_DataImport_ID";

	/**
	 * Set Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DataImport_Run_ID (int C_DataImport_Run_ID);

	/**
	 * Get Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DataImport_Run_ID();

	@Nullable org.compiere.model.I_C_DataImport_Run getC_DataImport_Run();

	void setC_DataImport_Run(@Nullable org.compiere.model.I_C_DataImport_Run C_DataImport_Run);

	ModelColumn<I_I_Inventory, org.compiere.model.I_C_DataImport_Run> COLUMN_C_DataImport_Run_ID = new ModelColumn<>(I_I_Inventory.class, "C_DataImport_Run_ID", org.compiere.model.I_C_DataImport_Run.class);
	String COLUMNNAME_C_DataImport_Run_ID = "C_DataImport_Run_ID";

	/**
	 * Set Cost Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCostPrice (@Nullable BigDecimal CostPrice);

	/**
	 * Get Cost Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCostPrice();

	ModelColumn<I_I_Inventory, Object> COLUMN_CostPrice = new ModelColumn<>(I_I_Inventory.class, "CostPrice", null);
	String COLUMNNAME_CostPrice = "CostPrice";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_I_Inventory, Object> COLUMN_Created = new ModelColumn<>(I_I_Inventory.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Date last inventory count.
	 * Date of Last Inventory Count
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateLastInventory (@Nullable java.sql.Timestamp DateLastInventory);

	/**
	 * Get Date last inventory count.
	 * Date of Last Inventory Count
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateLastInventory();

	ModelColumn<I_I_Inventory, Object> COLUMN_DateLastInventory = new ModelColumn<>(I_I_Inventory.class, "DateLastInventory", null);
	String COLUMNNAME_DateLastInventory = "DateLastInventory";

	/**
	 * Set Date received.
	 * Date a product was received
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateReceived (@Nullable java.sql.Timestamp DateReceived);

	/**
	 * Get Date received.
	 * Date a product was received
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateReceived();

	ModelColumn<I_I_Inventory, Object> COLUMN_DateReceived = new ModelColumn<>(I_I_Inventory.class, "DateReceived", null);
	String COLUMNNAME_DateReceived = "DateReceived";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_I_Inventory, Object> COLUMN_Description = new ModelColumn<>(I_I_Inventory.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Override Cost Price.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExplicitCostPrice (java.lang.String ExplicitCostPrice);

	/**
	 * Get Override Cost Price.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getExplicitCostPrice();

	ModelColumn<I_I_Inventory, Object> COLUMN_ExplicitCostPrice = new ModelColumn<>(I_I_Inventory.class, "ExplicitCostPrice", null);
	String COLUMNNAME_ExplicitCostPrice = "ExplicitCostPrice";

	/**
	 * Set External Header ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalHeaderId (@Nullable java.lang.String ExternalHeaderId);

	/**
	 * Get External Header ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalHeaderId();

	ModelColumn<I_I_Inventory, Object> COLUMN_ExternalHeaderId = new ModelColumn<>(I_I_Inventory.class, "ExternalHeaderId", null);
	String COLUMNNAME_ExternalHeaderId = "ExternalHeaderId";

	/**
	 * Set External Line ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalLineId (@Nullable java.lang.String ExternalLineId);

	/**
	 * Get External Line ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalLineId();

	ModelColumn<I_I_Inventory, Object> COLUMN_ExternalLineId = new ModelColumn<>(I_I_Inventory.class, "ExternalLineId", null);
	String COLUMNNAME_ExternalLineId = "ExternalLineId";

	/**
	 * Set HU aggregation.
	 * Specifies whether the respective line is about one HU or about potientielly many HUs.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHUAggregationType (@Nullable java.lang.String HUAggregationType);

	/**
	 * Get HU aggregation.
	 * Specifies whether the respective line is about one HU or about potientielly many HUs.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHUAggregationType();

	ModelColumn<I_I_Inventory, Object> COLUMN_HUAggregationType = new ModelColumn<>(I_I_Inventory.class, "HUAggregationType", null);
	String COLUMNNAME_HUAggregationType = "HUAggregationType";

	/**
	 * Set HU best before date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHU_BestBeforeDate (@Nullable java.sql.Timestamp HU_BestBeforeDate);

	/**
	 * Get HU best before date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getHU_BestBeforeDate();

	ModelColumn<I_I_Inventory, Object> COLUMN_HU_BestBeforeDate = new ModelColumn<>(I_I_Inventory.class, "HU_BestBeforeDate", null);
	String COLUMNNAME_HU_BestBeforeDate = "HU_BestBeforeDate";

	/**
	 * Set Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_ErrorMsg (@Nullable java.lang.String I_ErrorMsg);

	/**
	 * Get Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getI_ErrorMsg();

	ModelColumn<I_I_Inventory, Object> COLUMN_I_ErrorMsg = new ModelColumn<>(I_I_Inventory.class, "I_ErrorMsg", null);
	String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Import Inventory.
	 * Import Inventory Transactions
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setI_Inventory_ID (int I_Inventory_ID);

	/**
	 * Get Import Inventory.
	 * Import Inventory Transactions
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getI_Inventory_ID();

	ModelColumn<I_I_Inventory, Object> COLUMN_I_Inventory_ID = new ModelColumn<>(I_I_Inventory.class, "I_Inventory_ID", null);
	String COLUMNNAME_I_Inventory_ID = "I_Inventory_ID";

	/**
	 * Set Imported.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setI_IsImported (boolean I_IsImported);

	/**
	 * Get Imported.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isI_IsImported();

	ModelColumn<I_I_Inventory, Object> COLUMN_I_IsImported = new ModelColumn<>(I_I_Inventory.class, "I_IsImported", null);
	String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_LineContent (@Nullable java.lang.String I_LineContent);

	/**
	 * Get Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getI_LineContent();

	ModelColumn<I_I_Inventory, Object> COLUMN_I_LineContent = new ModelColumn<>(I_I_Inventory.class, "I_LineContent", null);
	String COLUMNNAME_I_LineContent = "I_LineContent";

	/**
	 * Set Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_LineNo (int I_LineNo);

	/**
	 * Get Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getI_LineNo();

	ModelColumn<I_I_Inventory, Object> COLUMN_I_LineNo = new ModelColumn<>(I_I_Inventory.class, "I_LineNo", null);
	String COLUMNNAME_I_LineNo = "I_LineNo";

	/**
	 * Set Date of inventory.
	 * Document date of the inventory document
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInventoryDate (@Nullable java.sql.Timestamp InventoryDate);

	/**
	 * Get Date of inventory.
	 * Document date of the inventory document
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getInventoryDate();

	ModelColumn<I_I_Inventory, Object> COLUMN_InventoryDate = new ModelColumn<>(I_I_Inventory.class, "InventoryDate", null);
	String COLUMNNAME_InventoryDate = "InventoryDate";

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

	ModelColumn<I_I_Inventory, Object> COLUMN_IsActive = new ModelColumn<>(I_I_Inventory.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lot Blocked.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsLotBlocked (boolean IsLotBlocked);

	/**
	 * Get Lot Blocked.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isLotBlocked();

	ModelColumn<I_I_Inventory, Object> COLUMN_IsLotBlocked = new ModelColumn<>(I_I_Inventory.class, "IsLotBlocked", null);
	String COLUMNNAME_IsLotBlocked = "IsLotBlocked";

	/**
	 * Set Update Qty Booked From FactAcct.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsUpdateQtyBookedFromFactAcct (boolean IsUpdateQtyBookedFromFactAcct);

	/**
	 * Get Update Qty Booked From FactAcct.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUpdateQtyBookedFromFactAcct();

	ModelColumn<I_I_Inventory, Object> COLUMN_IsUpdateQtyBookedFromFactAcct = new ModelColumn<>(I_I_Inventory.class, "IsUpdateQtyBookedFromFactAcct", null);
	String COLUMNNAME_IsUpdateQtyBookedFromFactAcct = "IsUpdateQtyBookedFromFactAcct";

	/**
	 * Set Locator Key.
	 * Key of the Warehouse Locator
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLocatorValue (@Nullable java.lang.String LocatorValue);

	/**
	 * Get Locator Key.
	 * Key of the Warehouse Locator
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLocatorValue();

	ModelColumn<I_I_Inventory, Object> COLUMN_LocatorValue = new ModelColumn<>(I_I_Inventory.class, "LocatorValue", null);
	String COLUMNNAME_LocatorValue = "LocatorValue";

	/**
	 * Set Lot No..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLot (@Nullable java.lang.String Lot);

	/**
	 * Get Lot No..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLot();

	ModelColumn<I_I_Inventory, Object> COLUMN_Lot = new ModelColumn<>(I_I_Inventory.class, "Lot", null);
	String COLUMNNAME_Lot = "Lot";

	/**
	 * Set Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstance_ID();

	@Nullable org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	void setM_AttributeSetInstance(@Nullable org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

	ModelColumn<I_I_Inventory, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_I_Inventory.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Phys. Inventory.
	 * Parameters for a Physical Inventory
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Inventory_ID (int M_Inventory_ID);

	/**
	 * Get Phys. Inventory.
	 * Parameters for a Physical Inventory
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Inventory_ID();

	@Nullable org.compiere.model.I_M_Inventory getM_Inventory();

	void setM_Inventory(@Nullable org.compiere.model.I_M_Inventory M_Inventory);

	ModelColumn<I_I_Inventory, org.compiere.model.I_M_Inventory> COLUMN_M_Inventory_ID = new ModelColumn<>(I_I_Inventory.class, "M_Inventory_ID", org.compiere.model.I_M_Inventory.class);
	String COLUMNNAME_M_Inventory_ID = "M_Inventory_ID";

	/**
	 * Set Phys.Inventory Line.
	 * Unique line in an Inventory document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InventoryLine_ID (int M_InventoryLine_ID);

	/**
	 * Get Phys.Inventory Line.
	 * Unique line in an Inventory document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InventoryLine_ID();

	@Nullable org.compiere.model.I_M_InventoryLine getM_InventoryLine();

	void setM_InventoryLine(@Nullable org.compiere.model.I_M_InventoryLine M_InventoryLine);

	ModelColumn<I_I_Inventory, org.compiere.model.I_M_InventoryLine> COLUMN_M_InventoryLine_ID = new ModelColumn<>(I_I_Inventory.class, "M_InventoryLine_ID", org.compiere.model.I_M_InventoryLine.class);
	String COLUMNNAME_M_InventoryLine_ID = "M_InventoryLine_ID";

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
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_I_Inventory, Object> COLUMN_Processed = new ModelColumn<>(I_I_Inventory.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_I_Inventory, Object> COLUMN_Processing = new ModelColumn<>(I_I_Inventory.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Product Account Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductAcctValue (@Nullable java.lang.String ProductAcctValue);

	/**
	 * Get Product Account Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductAcctValue();

	ModelColumn<I_I_Inventory, Object> COLUMN_ProductAcctValue = new ModelColumn<>(I_I_Inventory.class, "ProductAcctValue", null);
	String COLUMNNAME_ProductAcctValue = "ProductAcctValue";

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

	ModelColumn<I_I_Inventory, Object> COLUMN_ProductValue = new ModelColumn<>(I_I_Inventory.class, "ProductValue", null);
	String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set Qty Count.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyCount (@Nullable BigDecimal QtyCount);

	/**
	 * Get Qty Count.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyCount();

	ModelColumn<I_I_Inventory, Object> COLUMN_QtyCount = new ModelColumn<>(I_I_Inventory.class, "QtyCount", null);
	String COLUMNNAME_QtyCount = "QtyCount";

	/**
	 * Set Serial No.
	 * Product Serial Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSerNo (@Nullable java.lang.String SerNo);

	/**
	 * Get Serial No.
	 * Product Serial Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSerNo();

	ModelColumn<I_I_Inventory, Object> COLUMN_SerNo = new ModelColumn<>(I_I_Inventory.class, "SerNo", null);
	String COLUMNNAME_SerNo = "SerNo";

	/**
	 * Set Vendor.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSubProducer_BPartner_ID (int SubProducer_BPartner_ID);

	/**
	 * Get Vendor.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSubProducer_BPartner_ID();

	String COLUMNNAME_SubProducer_BPartner_ID = "SubProducer_BPartner_ID";

	/**
	 * Set SubProducerBPartner_Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSubProducerBPartner_Value (@Nullable java.lang.String SubProducerBPartner_Value);

	/**
	 * Get SubProducerBPartner_Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSubProducerBPartner_Value();

	ModelColumn<I_I_Inventory, Object> COLUMN_SubProducerBPartner_Value = new ModelColumn<>(I_I_Inventory.class, "SubProducerBPartner_Value", null);
	String COLUMNNAME_SubProducerBPartner_Value = "SubProducerBPartner_Value";

	/**
	 * Set TE.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTE (@Nullable java.lang.String TE);

	/**
	 * Get TE.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTE();

	ModelColumn<I_I_Inventory, Object> COLUMN_TE = new ModelColumn<>(I_I_Inventory.class, "TE", null);
	String COLUMNNAME_TE = "TE";

	/**
	 * Set UPC.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUPC (@Nullable java.lang.String UPC);

	/**
	 * Get UPC.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUPC();

	ModelColumn<I_I_Inventory, Object> COLUMN_UPC = new ModelColumn<>(I_I_Inventory.class, "UPC", null);
	String COLUMNNAME_UPC = "UPC";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_I_Inventory, Object> COLUMN_Updated = new ModelColumn<>(I_I_Inventory.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Warehouse Locator Identifier.
	 * Text that contains identifier of earehosue and locator
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWarehouseLocatorIdentifier (@Nullable java.lang.String WarehouseLocatorIdentifier);

	/**
	 * Get Warehouse Locator Identifier.
	 * Text that contains identifier of earehosue and locator
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWarehouseLocatorIdentifier();

	ModelColumn<I_I_Inventory, Object> COLUMN_WarehouseLocatorIdentifier = new ModelColumn<>(I_I_Inventory.class, "WarehouseLocatorIdentifier", null);
	String COLUMNNAME_WarehouseLocatorIdentifier = "WarehouseLocatorIdentifier";

	/**
	 * Set Warehouse Key.
	 * Key of the Warehouse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWarehouseValue (@Nullable java.lang.String WarehouseValue);

	/**
	 * Get Warehouse Key.
	 * Key of the Warehouse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWarehouseValue();

	ModelColumn<I_I_Inventory, Object> COLUMN_WarehouseValue = new ModelColumn<>(I_I_Inventory.class, "WarehouseValue", null);
	String COLUMNNAME_WarehouseValue = "WarehouseValue";

	/**
	 * Set Aisle.
	 * X dimension, e.g., Aisle
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setX (@Nullable java.lang.String X);

	/**
	 * Get Aisle.
	 * X dimension, e.g., Aisle
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getX();

	ModelColumn<I_I_Inventory, Object> COLUMN_X = new ModelColumn<>(I_I_Inventory.class, "X", null);
	String COLUMNNAME_X = "X";

	/**
	 * Set Rack.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setX1 (@Nullable java.lang.String X1);

	/**
	 * Get Rack.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getX1();

	ModelColumn<I_I_Inventory, Object> COLUMN_X1 = new ModelColumn<>(I_I_Inventory.class, "X1", null);
	String COLUMNNAME_X1 = "X1";

	/**
	 * Set Tray.
	 * Y dimension, e.g., Bin
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setY (@Nullable java.lang.String Y);

	/**
	 * Get Tray.
	 * Y dimension, e.g., Bin
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getY();

	ModelColumn<I_I_Inventory, Object> COLUMN_Y = new ModelColumn<>(I_I_Inventory.class, "Y", null);
	String COLUMNNAME_Y = "Y";

	/**
	 * Set Level.
	 * Z dimension, e.g., Level
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setZ (@Nullable java.lang.String Z);

	/**
	 * Get Level.
	 * Z dimension, e.g., Level
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getZ();

	ModelColumn<I_I_Inventory, Object> COLUMN_Z = new ModelColumn<>(I_I_Inventory.class, "Z", null);
	String COLUMNNAME_Z = "Z";
}
