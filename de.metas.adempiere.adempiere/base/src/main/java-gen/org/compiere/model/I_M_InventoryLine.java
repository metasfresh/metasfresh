package org.compiere.model;


/** Generated Interface for M_InventoryLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_InventoryLine 
{

    /** TableName=M_InventoryLine */
    public static final String Table_Name = "M_InventoryLine";

    /** AD_Table_ID=322 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_AD_Client>(I_M_InventoryLine.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_AD_Org>(I_M_InventoryLine.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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

	public org.compiere.model.I_C_Charge getC_Charge();

	public void setC_Charge(org.compiere.model.I_C_Charge C_Charge);

    /** Column definition for C_Charge_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_C_Charge> COLUMN_C_Charge_ID = new org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_C_Charge>(I_M_InventoryLine.class, "C_Charge_ID", org.compiere.model.I_C_Charge.class);
    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_C_UOM>(I_M_InventoryLine.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Get Erstellt.
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
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_AD_User>(I_M_InventoryLine.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
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
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
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
	 * Set Zeile Nr..
	 * Unique line for this document
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Unique line for this document
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
	 * Set Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Merkmale.
	 * Merkmals Ausprägungen zum Produkt
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
	 * Set Versand-/Wareneingangsposition.
	 * Position auf Versand- oder Wareneingangsbeleg
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InOutLine_ID (int M_InOutLine_ID);

	/**
	 * Get Versand-/Wareneingangsposition.
	 * Position auf Versand- oder Wareneingangsbeleg
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
	 * Set Inventur.
	 * Parameters for a Physical Inventory
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Inventory_ID (int M_Inventory_ID);

	/**
	 * Get Inventur.
	 * Parameters for a Physical Inventory
	 *
	 * <br>Type: TableDir
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
	 * Set Inventur-Position.
	 * Unique line in an Inventory document
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_InventoryLine_ID (int M_InventoryLine_ID);

	/**
	 * Get Inventur-Position.
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
	 * Set Lagerort.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Lagerort.
	 * Warehouse Locator
	 *
	 * <br>Type: Locator
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator();

	public void setM_Locator(org.compiere.model.I_M_Locator M_Locator);

    /** Column definition for M_Locator_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_M_Locator> COLUMN_M_Locator_ID = new org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_M_Locator>(I_M_InventoryLine.class, "M_Locator_ID", org.compiere.model.I_M_Locator.class);
    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_M_Product>(I_M_InventoryLine.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
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
	 * Set Buchmenge.
	 * Book Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyBook (java.math.BigDecimal QtyBook);

	/**
	 * Get Buchmenge.
	 * Book Quantity
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
	 * Set Zählmenge.
	 * Counted Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyCount (java.math.BigDecimal QtyCount);

	/**
	 * Get Zählmenge.
	 * Counted Quantity
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
	 * Set Internal Use Qty.
	 * Internal Use Quantity removed from Inventory
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyInternalUse (java.math.BigDecimal QtyInternalUse);

	/**
	 * Get Internal Use Qty.
	 * Internal Use Quantity removed from Inventory
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
	 * Set UPC/EAN.
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
	 * Get UPC/EAN.
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
	 * Get Aktualisiert.
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
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_InventoryLine, org.compiere.model.I_AD_User>(I_M_InventoryLine.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
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
	 * Get Suchschlüssel.
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
