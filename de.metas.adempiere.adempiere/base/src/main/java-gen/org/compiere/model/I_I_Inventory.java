package org.compiere.model;


/** Generated Interface for I_Inventory
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_I_Inventory 
{

    /** TableName=I_Inventory */
    public static final String Table_Name = "I_Inventory";

    /** AD_Table_ID=572 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_AD_Client>(I_I_Inventory.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_AD_Org>(I_I_Inventory.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_AD_User>(I_I_Inventory.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Datum der letzten Inventur.
	 * Datum der letzten Inventur
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateLastInventory (java.sql.Timestamp DateLastInventory);

	/**
	 * Get Datum der letzten Inventur.
	 * Datum der letzten Inventur
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateLastInventory();

    /** Column definition for DateLastInventory */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_DateLastInventory = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "DateLastInventory", null);
    /** Column name DateLastInventory */
    public static final String COLUMNNAME_DateLastInventory = "DateLastInventory";

	/**
	 * Set Eingangsdatum.
	 * Datum, zu dem ein Produkt empfangen wurde
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateReceived (java.sql.Timestamp DateReceived);

	/**
	 * Get Eingangsdatum.
	 * Datum, zu dem ein Produkt empfangen wurde
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateReceived();

    /** Column definition for DateReceived */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_DateReceived = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "DateReceived", null);
    /** Column name DateReceived */
    public static final String COLUMNNAME_DateReceived = "DateReceived";

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
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Best Before Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHU_BestBeforeDate (java.sql.Timestamp HU_BestBeforeDate);

	/**
	 * Get Best Before Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getHU_BestBeforeDate();

    /** Column definition for HU_BestBeforeDate */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_HU_BestBeforeDate = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "HU_BestBeforeDate", null);
    /** Column name HU_BestBeforeDate */
    public static final String COLUMNNAME_HU_BestBeforeDate = "HU_BestBeforeDate";

	/**
	 * Set Import-Fehlermeldung.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg);

	/**
	 * Get Import-Fehlermeldung.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_ErrorMsg();

    /** Column definition for I_ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_I_ErrorMsg = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "I_ErrorMsg", null);
    /** Column name I_ErrorMsg */
    public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Import - Warenbestand.
	 * Import Inventory Transactions
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_Inventory_ID (int I_Inventory_ID);

	/**
	 * Get Import - Warenbestand.
	 * Import Inventory Transactions
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getI_Inventory_ID();

    /** Column definition for I_Inventory_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_I_Inventory_ID = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "I_Inventory_ID", null);
    /** Column name I_Inventory_ID */
    public static final String COLUMNNAME_I_Inventory_ID = "I_Inventory_ID";

	/**
	 * Set Importiert.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_IsImported (boolean I_IsImported);

	/**
	 * Get Importiert.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isI_IsImported();

    /** Column definition for I_IsImported */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_I_IsImported = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "I_IsImported", null);
    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Lot Blocked.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsLotBlocked (boolean IsLotBlocked);

	/**
	 * Get Lot Blocked.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isLotBlocked();

    /** Column definition for IsLotBlocked */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_IsLotBlocked = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "IsLotBlocked", null);
    /** Column name IsLotBlocked */
    public static final String COLUMNNAME_IsLotBlocked = "IsLotBlocked";

	/**
	 * Set Lagerort-Schlüssel.
	 * Key of the Warehouse Locator
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLocatorValue (java.lang.String LocatorValue);

	/**
	 * Get Lagerort-Schlüssel.
	 * Key of the Warehouse Locator
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLocatorValue();

    /** Column definition for LocatorValue */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_LocatorValue = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "LocatorValue", null);
    /** Column name LocatorValue */
    public static final String COLUMNNAME_LocatorValue = "LocatorValue";

	/**
	 * Set Los-Nr..
	 * Los-Nummer (alphanumerisch)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLot (java.lang.String Lot);

	/**
	 * Get Los-Nr..
	 * Los-Nummer (alphanumerisch)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLot();

    /** Column definition for Lot */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_Lot = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "Lot", null);
    /** Column name Lot */
    public static final String COLUMNNAME_Lot = "Lot";

	/**
	 * Set Inventur.
	 * Parameters for a Physical Inventory
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Inventory_ID (int M_Inventory_ID);

	/**
	 * Get Inventur.
	 * Parameters for a Physical Inventory
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Inventory_ID();

	public org.compiere.model.I_M_Inventory getM_Inventory();

	public void setM_Inventory(org.compiere.model.I_M_Inventory M_Inventory);

    /** Column definition for M_Inventory_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_M_Inventory> COLUMN_M_Inventory_ID = new org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_M_Inventory>(I_I_Inventory.class, "M_Inventory_ID", org.compiere.model.I_M_Inventory.class);
    /** Column name M_Inventory_ID */
    public static final String COLUMNNAME_M_Inventory_ID = "M_Inventory_ID";

	/**
	 * Set Inventur-Position.
	 * Unique line in an Inventory document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InventoryLine_ID (int M_InventoryLine_ID);

	/**
	 * Get Inventur-Position.
	 * Unique line in an Inventory document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_InventoryLine_ID();

	public org.compiere.model.I_M_InventoryLine getM_InventoryLine();

	public void setM_InventoryLine(org.compiere.model.I_M_InventoryLine M_InventoryLine);

    /** Column definition for M_InventoryLine_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_M_InventoryLine> COLUMN_M_InventoryLine_ID = new org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_M_InventoryLine>(I_I_Inventory.class, "M_InventoryLine_ID", org.compiere.model.I_M_InventoryLine.class);
    /** Column name M_InventoryLine_ID */
    public static final String COLUMNNAME_M_InventoryLine_ID = "M_InventoryLine_ID";

	/**
	 * Set Lagerort.
	 * Warehouse Locator
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Lagerort.
	 * Warehouse Locator
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Locator_ID();

	public org.compiere.model.I_M_Locator getM_Locator();

	public void setM_Locator(org.compiere.model.I_M_Locator M_Locator);

    /** Column definition for M_Locator_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_M_Locator> COLUMN_M_Locator_ID = new org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_M_Locator>(I_I_Inventory.class, "M_Locator_ID", org.compiere.model.I_M_Locator.class);
    /** Column name M_Locator_ID */
    public static final String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_M_Product>(I_I_Inventory.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Lager.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse();

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_M_Warehouse>(I_I_Inventory.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Bewegungsdatum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMovementDate (java.sql.Timestamp MovementDate);

	/**
	 * Get Bewegungsdatum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getMovementDate();

    /** Column definition for MovementDate */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_MovementDate = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "MovementDate", null);
    /** Column name MovementDate */
    public static final String COLUMNNAME_MovementDate = "MovementDate";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

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
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_QtyInternalUse = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "QtyInternalUse", null);
    /** Column name QtyInternalUse */
    public static final String COLUMNNAME_QtyInternalUse = "QtyInternalUse";

	/**
	 * Set Serien-Nr..
	 * Product Serial Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSerNo (java.lang.String SerNo);

	/**
	 * Get Serien-Nr..
	 * Product Serial Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSerNo();

    /** Column definition for SerNo */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_SerNo = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "SerNo", null);
    /** Column name SerNo */
    public static final String COLUMNNAME_SerNo = "SerNo";

	/**
	 * Set Unterlieferanten.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSubProducer_BPartner_ID (int SubProducer_BPartner_ID);

	/**
	 * Get Unterlieferanten.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSubProducer_BPartner_ID();

	public org.compiere.model.I_C_BPartner getSubProducer_BPartner();

	public void setSubProducer_BPartner(org.compiere.model.I_C_BPartner SubProducer_BPartner);

    /** Column definition for SubProducer_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_C_BPartner> COLUMN_SubProducer_BPartner_ID = new org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_C_BPartner>(I_I_Inventory.class, "SubProducer_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name SubProducer_BPartner_ID */
    public static final String COLUMNNAME_SubProducer_BPartner_ID = "SubProducer_BPartner_ID";

	/**
	 * Set SubProducerBPartner_Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSubProducerBPartner_Value (java.lang.String SubProducerBPartner_Value);

	/**
	 * Get SubProducerBPartner_Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSubProducerBPartner_Value();

    /** Column definition for SubProducerBPartner_Value */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_SubProducerBPartner_Value = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "SubProducerBPartner_Value", null);
    /** Column name SubProducerBPartner_Value */
    public static final String COLUMNNAME_SubProducerBPartner_Value = "SubProducerBPartner_Value";

	/**
	 * Set TE.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTE (java.lang.String TE);

	/**
	 * Get TE.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTE();

    /** Column definition for TE */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_TE = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "TE", null);
    /** Column name TE */
    public static final String COLUMNNAME_TE = "TE";

	/**
	 * Set UPC/EAN.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUPC (java.lang.String UPC);

	/**
	 * Get UPC/EAN.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUPC();

    /** Column definition for UPC */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_UPC = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "UPC", null);
    /** Column name UPC */
    public static final String COLUMNNAME_UPC = "UPC";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_AD_User>(I_I_Inventory.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/**
	 * Set Lager-Schlüssel.
	 * Key of the Warehouse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWarehouseValue (java.lang.String WarehouseValue);

	/**
	 * Get Lager-Schlüssel.
	 * Key of the Warehouse
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWarehouseValue();

    /** Column definition for WarehouseValue */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_WarehouseValue = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "WarehouseValue", null);
    /** Column name WarehouseValue */
    public static final String COLUMNNAME_WarehouseValue = "WarehouseValue";

	/**
	 * Set Gang.
	 * X-Dimension, z.B. Gang
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setX (java.lang.String X);

	/**
	 * Get Gang.
	 * X-Dimension, z.B. Gang
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getX();

    /** Column definition for X */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_X = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "X", null);
    /** Column name X */
    public static final String COLUMNNAME_X = "X";

	/**
	 * Set Regal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setX1 (java.lang.String X1);

	/**
	 * Get Regal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getX1();

    /** Column definition for X1 */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_X1 = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "X1", null);
    /** Column name X1 */
    public static final String COLUMNNAME_X1 = "X1";

	/**
	 * Set Fach.
	 * Y-Dimension, z.B. Fach
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setY (java.lang.String Y);

	/**
	 * Get Fach.
	 * Y-Dimension, z.B. Fach
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getY();

    /** Column definition for Y */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_Y = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "Y", null);
    /** Column name Y */
    public static final String COLUMNNAME_Y = "Y";

	/**
	 * Set Ebene.
	 * Z-Dimension, z.B. Ebene
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setZ (java.lang.String Z);

	/**
	 * Get Ebene.
	 * Z-Dimension, z.B. Ebene
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getZ();

    /** Column definition for Z */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_Z = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "Z", null);
    /** Column name Z */
    public static final String COLUMNNAME_Z = "Z";
}
