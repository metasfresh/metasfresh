package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_InventoryLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_InventoryLine 
{

	String Table_Name = "M_InventoryLine";

//	/** AD_Table_ID=322 */
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
	 * Set Assigned to.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAssignedTo (@Nullable java.lang.String AssignedTo);

	/**
	 * Get Assigned to.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAssignedTo();

	ModelColumn<I_M_InventoryLine, Object> COLUMN_AssignedTo = new ModelColumn<>(I_M_InventoryLine.class, "AssignedTo", null);
	String COLUMNNAME_AssignedTo = "AssignedTo";

	/**
	 * Set Costs.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Costs.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Charge_ID();

	String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

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

	ModelColumn<I_M_InventoryLine, Object> COLUMN_CostPrice = new ModelColumn<>(I_M_InventoryLine.class, "CostPrice", null);
	String COLUMNNAME_CostPrice = "CostPrice";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_InventoryLine, Object> COLUMN_Created = new ModelColumn<>(I_M_InventoryLine.class, "Created", null);
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
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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

	ModelColumn<I_M_InventoryLine, Object> COLUMN_Description = new ModelColumn<>(I_M_InventoryLine.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalId (@Nullable java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalId();

	ModelColumn<I_M_InventoryLine, Object> COLUMN_ExternalId = new ModelColumn<>(I_M_InventoryLine.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set HU aggregation.
	 * Specifies whether the respective line is about one HU or about potientielly many HUs.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHUAggregationType (java.lang.String HUAggregationType);

	/**
	 * Get HU aggregation.
	 * Specifies whether the respective line is about one HU or about potientielly many HUs.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getHUAggregationType();

	ModelColumn<I_M_InventoryLine, Object> COLUMN_HUAggregationType = new ModelColumn<>(I_M_InventoryLine.class, "HUAggregationType", null);
	String COLUMNNAME_HUAggregationType = "HUAggregationType";

	/**
	 * Set Inventory Type.
	 * Type of inventory difference
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInventoryType (java.lang.String InventoryType);

	/**
	 * Get Inventory Type.
	 * Type of inventory difference
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getInventoryType();

	ModelColumn<I_M_InventoryLine, Object> COLUMN_InventoryType = new ModelColumn<>(I_M_InventoryLine.class, "InventoryType", null);
	String COLUMNNAME_InventoryType = "InventoryType";

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

	ModelColumn<I_M_InventoryLine, Object> COLUMN_IsActive = new ModelColumn<>(I_M_InventoryLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Counted.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCounted (boolean IsCounted);

	/**
	 * Get Counted.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCounted();

	ModelColumn<I_M_InventoryLine, Object> COLUMN_IsCounted = new ModelColumn<>(I_M_InventoryLine.class, "IsCounted", null);
	String COLUMNNAME_IsCounted = "IsCounted";

	/**
	 * Set Override Cost Price.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsExplicitCostPrice (boolean IsExplicitCostPrice);

	/**
	 * Get Override Cost Price.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isExplicitCostPrice();

	ModelColumn<I_M_InventoryLine, Object> COLUMN_IsExplicitCostPrice = new ModelColumn<>(I_M_InventoryLine.class, "IsExplicitCostPrice", null);
	String COLUMNNAME_IsExplicitCostPrice = "IsExplicitCostPrice";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLine();

	ModelColumn<I_M_InventoryLine, Object> COLUMN_Line = new ModelColumn<>(I_M_InventoryLine.class, "Line", null);
	String COLUMNNAME_Line = "Line";

	/**
	 * Set Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstance_ID();

	org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

	ModelColumn<I_M_InventoryLine, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_M_InventoryLine.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InOutLine_ID();

	@Nullable org.compiere.model.I_M_InOutLine getM_InOutLine();

	void setM_InOutLine(@Nullable org.compiere.model.I_M_InOutLine M_InOutLine);

	ModelColumn<I_M_InventoryLine, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new ModelColumn<>(I_M_InventoryLine.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
	String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Phys. Inventory.
	 * Parameters for a Physical Inventory
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Inventory_ID (int M_Inventory_ID);

	/**
	 * Get Phys. Inventory.
	 * Parameters for a Physical Inventory
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Inventory_ID();

	org.compiere.model.I_M_Inventory getM_Inventory();

	void setM_Inventory(org.compiere.model.I_M_Inventory M_Inventory);

	ModelColumn<I_M_InventoryLine, org.compiere.model.I_M_Inventory> COLUMN_M_Inventory_ID = new ModelColumn<>(I_M_InventoryLine.class, "M_Inventory_ID", org.compiere.model.I_M_Inventory.class);
	String COLUMNNAME_M_Inventory_ID = "M_Inventory_ID";

	/**
	 * Set Phys.Inventory Line.
	 * Unique line in an Inventory document
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_InventoryLine_ID (int M_InventoryLine_ID);

	/**
	 * Get Phys.Inventory Line.
	 * Unique line in an Inventory document
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_InventoryLine_ID();

	ModelColumn<I_M_InventoryLine, Object> COLUMN_M_InventoryLine_ID = new ModelColumn<>(I_M_InventoryLine.class, "M_InventoryLine_ID", null);
	String COLUMNNAME_M_InventoryLine_ID = "M_InventoryLine_ID";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Modular Contract.
	 * Document lines linked to a modular contract will generate contract module logs.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setModular_Flatrate_Term_ID (int Modular_Flatrate_Term_ID);

	/**
	 * Get Modular Contract.
	 * Document lines linked to a modular contract will generate contract module logs.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getModular_Flatrate_Term_ID();

	ModelColumn<I_M_InventoryLine, Object> COLUMN_Modular_Flatrate_Term_ID = new ModelColumn<>(I_M_InventoryLine.class, "Modular_Flatrate_Term_ID", null);
	String COLUMNNAME_Modular_Flatrate_Term_ID = "Modular_Flatrate_Term_ID";

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
	 * Set Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_SectionCode_ID (int M_SectionCode_ID);

	/**
	 * Get Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_SectionCode_ID();

	@Nullable org.compiere.model.I_M_SectionCode getM_SectionCode();

	void setM_SectionCode(@Nullable org.compiere.model.I_M_SectionCode M_SectionCode);

	ModelColumn<I_M_InventoryLine, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_M_InventoryLine.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_M_InventoryLine, Object> COLUMN_Processed = new ModelColumn<>(I_M_InventoryLine.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Qty Book.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyBook (BigDecimal QtyBook);

	/**
	 * Get Qty Book.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyBook();

	ModelColumn<I_M_InventoryLine, Object> COLUMN_QtyBook = new ModelColumn<>(I_M_InventoryLine.class, "QtyBook", null);
	String COLUMNNAME_QtyBook = "QtyBook";

	/**
	 * Set Qty Count.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyCount (BigDecimal QtyCount);

	/**
	 * Get Qty Count.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyCount();

	ModelColumn<I_M_InventoryLine, Object> COLUMN_QtyCount = new ModelColumn<>(I_M_InventoryLine.class, "QtyCount", null);
	String COLUMNNAME_QtyCount = "QtyCount";

	/**
	 * Set QtyCsv.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyCsv (BigDecimal QtyCsv);

	/**
	 * Get QtyCsv.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyCsv();

	ModelColumn<I_M_InventoryLine, Object> COLUMN_QtyCsv = new ModelColumn<>(I_M_InventoryLine.class, "QtyCsv", null);
	String COLUMNNAME_QtyCsv = "QtyCsv";

	/**
	 * Set Quantity count.
	 * Counted Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyInternalUse (@Nullable BigDecimal QtyInternalUse);

	/**
	 * Get Quantity count.
	 * Counted Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyInternalUse();

	ModelColumn<I_M_InventoryLine, Object> COLUMN_QtyInternalUse = new ModelColumn<>(I_M_InventoryLine.class, "QtyInternalUse", null);
	String COLUMNNAME_QtyInternalUse = "QtyInternalUse";

	/**
	 * Set Rendered QR Code.
	 * It's the QR code which is directly incorporated in the QR code image. Nothing more, nothing less.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRenderedQRCode (@Nullable java.lang.String RenderedQRCode);

	/**
	 * Get Rendered QR Code.
	 * It's the QR code which is directly incorporated in the QR code image. Nothing more, nothing less.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRenderedQRCode();

	ModelColumn<I_M_InventoryLine, Object> COLUMN_RenderedQRCode = new ModelColumn<>(I_M_InventoryLine.class, "RenderedQRCode", null);
	String COLUMNNAME_RenderedQRCode = "RenderedQRCode";

	/**
	 * Set Reverse Line.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReversalLine_ID (int ReversalLine_ID);

	/**
	 * Get Reverse Line.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getReversalLine_ID();

	@Nullable org.compiere.model.I_M_InventoryLine getReversalLine();

	void setReversalLine(@Nullable org.compiere.model.I_M_InventoryLine ReversalLine);

	ModelColumn<I_M_InventoryLine, org.compiere.model.I_M_InventoryLine> COLUMN_ReversalLine_ID = new ModelColumn<>(I_M_InventoryLine.class, "ReversalLine_ID", org.compiere.model.I_M_InventoryLine.class);
	String COLUMNNAME_ReversalLine_ID = "ReversalLine_ID";

	/**
	 * Set UPC.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setUPC (@Nullable java.lang.String UPC);

	/**
	 * Get UPC.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getUPC();

	ModelColumn<I_M_InventoryLine, Object> COLUMN_UPC = new ModelColumn<>(I_M_InventoryLine.class, "UPC", null);
	String COLUMNNAME_UPC = "UPC";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_InventoryLine, Object> COLUMN_Updated = new ModelColumn<>(I_M_InventoryLine.class, "Updated", null);
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setValue (@Nullable java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getValue();

	ModelColumn<I_M_InventoryLine, Object> COLUMN_Value = new ModelColumn<>(I_M_InventoryLine.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
