package org.compiere.model;


/** Generated Interface for M_InventoryLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_InventoryLine 
{

    /** TableName=M_InventoryLine */
    public static final String Table_Name = "M_InventoryLine";

    /** AD_Table_ID=322 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Zugewiesen an.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAssignedTo (java.lang.String AssignedTo);

	/**
	 * Get Zugewiesen an.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAssignedTo();

    /** Column definition for AssignedTo */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, Object> COLUMN_AssignedTo = new org.adempiere.model.ModelColumn<I_M_InventoryLine, Object>(I_M_InventoryLine.class, "AssignedTo", null);
    /** Column name AssignedTo */
    public static final String COLUMNNAME_AssignedTo = "AssignedTo";

	/**
	 * Set Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Kosten.
	 * Additional document charges
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Charge_ID();

    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Cost Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCostPrice (java.math.BigDecimal CostPrice);

	/**
	 * Get Cost Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCostPrice();

    /** Column definition for CostPrice */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, Object> COLUMN_CostPrice = new org.adempiere.model.ModelColumn<I_M_InventoryLine, Object>(I_M_InventoryLine.class, "CostPrice", null);
    /** Column name CostPrice */
    public static final String COLUMNNAME_CostPrice = "CostPrice";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_InventoryLine, Object>(I_M_InventoryLine.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_InventoryLine, Object>(I_M_InventoryLine.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExternalId (java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalId();

    /** Column definition for ExternalId */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, Object> COLUMN_ExternalId = new org.adempiere.model.ModelColumn<I_M_InventoryLine, Object>(I_M_InventoryLine.class, "ExternalId", null);
    /** Column name ExternalId */
    public static final String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set HU aggregation.
	 * Specifies whether the respective line is about one HU or about potientielly many HUs.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHUAggregationType (java.lang.String HUAggregationType);

	/**
	 * Get HU aggregation.
	 * Specifies whether the respective line is about one HU or about potientielly many HUs.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHUAggregationType();

    /** Column definition for HUAggregationType */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, Object> COLUMN_HUAggregationType = new org.adempiere.model.ModelColumn<I_M_InventoryLine, Object>(I_M_InventoryLine.class, "HUAggregationType", null);
    /** Column name HUAggregationType */
    public static final String COLUMNNAME_HUAggregationType = "HUAggregationType";

	/**
	 * Set Inventory Type.
	 * Type of inventory difference
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInventoryType (java.lang.String InventoryType);

	/**
	 * Get Inventory Type.
	 * Type of inventory difference
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInventoryType();

    /** Column definition for InventoryType */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, Object> COLUMN_InventoryType = new org.adempiere.model.ModelColumn<I_M_InventoryLine, Object>(I_M_InventoryLine.class, "InventoryType", null);
    /** Column name InventoryType */
    public static final String COLUMNNAME_InventoryType = "InventoryType";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_InventoryLine, Object>(I_M_InventoryLine.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Counted.
	 * Count number of not empty elements
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCounted (boolean IsCounted);

	/**
	 * Get Counted.
	 * Count number of not empty elements
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCounted();

    /** Column definition for IsCounted */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, Object> COLUMN_IsCounted = new org.adempiere.model.ModelColumn<I_M_InventoryLine, Object>(I_M_InventoryLine.class, "IsCounted", null);
    /** Column name IsCounted */
    public static final String COLUMNNAME_IsCounted = "IsCounted";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_M_InventoryLine, Object>(I_M_InventoryLine.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_M_AttributeSetInstance>(I_M_InventoryLine.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Receipt Line.
	 * Line on Receipt document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InOutLine_ID();

	public org.compiere.model.I_M_InOutLine getM_InOutLine();

	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine);

    /** Column definition for M_InOutLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_M_InOutLine> COLUMN_M_InOutLine_ID = new org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_M_InOutLine>(I_M_InventoryLine.class, "M_InOutLine_ID", org.compiere.model.I_M_InOutLine.class);
    /** Column name M_InOutLine_ID */
    public static final String COLUMNNAME_M_InOutLine_ID = "M_InOutLine_ID";

	/**
	 * Set Phys. Inventory.
	 * Parameters for a Physical Inventory
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Inventory_ID (int M_Inventory_ID);

	/**
	 * Get Phys. Inventory.
	 * Parameters for a Physical Inventory
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Inventory_ID();

	public org.compiere.model.I_M_Inventory getM_Inventory();

	public void setM_Inventory(org.compiere.model.I_M_Inventory M_Inventory);

    /** Column definition for M_Inventory_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_M_Inventory> COLUMN_M_Inventory_ID = new org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_M_Inventory>(I_M_InventoryLine.class, "M_Inventory_ID", org.compiere.model.I_M_Inventory.class);
    /** Column name M_Inventory_ID */
    public static final String COLUMNNAME_M_Inventory_ID = "M_Inventory_ID";

	/**
	 * Set Phys.Inventory Line.
	 * Unique line in an Inventory document
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_InventoryLine_ID (int M_InventoryLine_ID);

	/**
	 * Get Phys.Inventory Line.
	 * Unique line in an Inventory document
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_InventoryLine_ID();

    /** Column definition for M_InventoryLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, Object> COLUMN_M_InventoryLine_ID = new org.adempiere.model.ModelColumn<I_M_InventoryLine, Object>(I_M_InventoryLine.class, "M_InventoryLine_ID", null);
    /** Column name M_InventoryLine_ID */
    public static final String COLUMNNAME_M_InventoryLine_ID = "M_InventoryLine_ID";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Locator_ID();

    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_M_InventoryLine, Object>(I_M_InventoryLine.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Qty Book.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyBook (java.math.BigDecimal QtyBook);

	/**
	 * Get Qty Book.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyBook();

    /** Column definition for QtyBook */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, Object> COLUMN_QtyBook = new org.adempiere.model.ModelColumn<I_M_InventoryLine, Object>(I_M_InventoryLine.class, "QtyBook", null);
    /** Column name QtyBook */
    public static final String COLUMNNAME_QtyBook = "QtyBook";

	/**
	 * Set Qty Count.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyCount (java.math.BigDecimal QtyCount);

	/**
	 * Get Qty Count.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyCount();

    /** Column definition for QtyCount */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, Object> COLUMN_QtyCount = new org.adempiere.model.ModelColumn<I_M_InventoryLine, Object>(I_M_InventoryLine.class, "QtyCount", null);
    /** Column name QtyCount */
    public static final String COLUMNNAME_QtyCount = "QtyCount";

	/**
	 * Set QtyCsv.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyCsv (java.math.BigDecimal QtyCsv);

	/**
	 * Get QtyCsv.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyCsv();

    /** Column definition for QtyCsv */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, Object> COLUMN_QtyCsv = new org.adempiere.model.ModelColumn<I_M_InventoryLine, Object>(I_M_InventoryLine.class, "QtyCsv", null);
    /** Column name QtyCsv */
    public static final String COLUMNNAME_QtyCsv = "QtyCsv";

	/**
	 * Set Quantity count.
	 * Counted Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyInternalUse (java.math.BigDecimal QtyInternalUse);

	/**
	 * Get Quantity count.
	 * Counted Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyInternalUse();

    /** Column definition for QtyInternalUse */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, Object> COLUMN_QtyInternalUse = new org.adempiere.model.ModelColumn<I_M_InventoryLine, Object>(I_M_InventoryLine.class, "QtyInternalUse", null);
    /** Column name QtyInternalUse */
    public static final String COLUMNNAME_QtyInternalUse = "QtyInternalUse";

	/**
	 * Set Storno-Zeile.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReversalLine_ID (int ReversalLine_ID);

	/**
	 * Get Storno-Zeile.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getReversalLine_ID();

	public org.compiere.model.I_M_InventoryLine getReversalLine();

	public void setReversalLine(org.compiere.model.I_M_InventoryLine ReversalLine);

    /** Column definition for ReversalLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_M_InventoryLine> COLUMN_ReversalLine_ID = new org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_M_InventoryLine>(I_M_InventoryLine.class, "ReversalLine_ID", org.compiere.model.I_M_InventoryLine.class);
    /** Column name ReversalLine_ID */
    public static final String COLUMNNAME_ReversalLine_ID = "ReversalLine_ID";

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
	public void setUPC (java.lang.String UPC);

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
	public java.lang.String getUPC();

    /** Column definition for UPC */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, Object> COLUMN_UPC = new org.adempiere.model.ModelColumn<I_M_InventoryLine, Object>(I_M_InventoryLine.class, "UPC", null);
    /** Column name UPC */
    public static final String COLUMNNAME_UPC = "UPC";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_InventoryLine, Object>(I_M_InventoryLine.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

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
	public void setValue (java.lang.String Value);

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
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_M_InventoryLine, Object>(I_M_InventoryLine.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
