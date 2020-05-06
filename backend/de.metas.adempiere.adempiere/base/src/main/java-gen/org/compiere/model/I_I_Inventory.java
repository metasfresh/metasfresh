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

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set System-Problem.
	 * Automatically created or manually entered System Issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get System-Problem.
	 * Automatically created or manually entered System Issue
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Issue_ID();

	public org.compiere.model.I_AD_Issue getAD_Issue();

	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue);

    /** Column definition for AD_Issue_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_AD_Issue> COLUMN_AD_Issue_ID = new org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_AD_Issue>(I_I_Inventory.class, "AD_Issue_ID", org.compiere.model.I_AD_Issue.class);
    /** Column name AD_Issue_ID */
    public static final String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Daten Import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DataImport_ID (int C_DataImport_ID);

	/**
	 * Get Daten Import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DataImport_ID();

	public org.compiere.model.I_C_DataImport getC_DataImport();

	public void setC_DataImport(org.compiere.model.I_C_DataImport C_DataImport);

    /** Column definition for C_DataImport_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_C_DataImport> COLUMN_C_DataImport_ID = new org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_C_DataImport>(I_I_Inventory.class, "C_DataImport_ID", org.compiere.model.I_C_DataImport.class);
    /** Column name C_DataImport_ID */
    public static final String COLUMNNAME_C_DataImport_ID = "C_DataImport_ID";

	/**
	 * Set Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_DataImport_Run_ID (int C_DataImport_Run_ID);

	/**
	 * Get Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_DataImport_Run_ID();

	public org.compiere.model.I_C_DataImport_Run getC_DataImport_Run();

	public void setC_DataImport_Run(org.compiere.model.I_C_DataImport_Run C_DataImport_Run);

    /** Column definition for C_DataImport_Run_ID */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_C_DataImport_Run> COLUMN_C_DataImport_Run_ID = new org.adempiere.model.ModelColumn<I_I_Inventory, org.compiere.model.I_C_DataImport_Run>(I_I_Inventory.class, "C_DataImport_Run_ID", org.compiere.model.I_C_DataImport_Run.class);
    /** Column name C_DataImport_Run_ID */
    public static final String COLUMNNAME_C_DataImport_Run_ID = "C_DataImport_Run_ID";

	/**
	 * Set Cost Preise.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCostPrice (java.math.BigDecimal CostPrice);

	/**
	 * Get Cost Preise.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getCostPrice();

    /** Column definition for CostPrice */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_CostPrice = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "CostPrice", null);
    /** Column name CostPrice */
    public static final String COLUMNNAME_CostPrice = "CostPrice";

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
	 * Set Externe Datensatz-Kopf-ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExternalHeaderId (java.lang.String ExternalHeaderId);

	/**
	 * Get Externe Datensatz-Kopf-ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalHeaderId();

    /** Column definition for ExternalHeaderId */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_ExternalHeaderId = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "ExternalHeaderId", null);
    /** Column name ExternalHeaderId */
    public static final String COLUMNNAME_ExternalHeaderId = "ExternalHeaderId";

	/**
	 * Set Externe Datensatz-Zeilen-ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExternalLineId (java.lang.String ExternalLineId);

	/**
	 * Get Externe Datensatz-Zeilen-ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalLineId();

    /** Column definition for ExternalLineId */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_ExternalLineId = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "ExternalLineId", null);
    /** Column name ExternalLineId */
    public static final String COLUMNNAME_ExternalLineId = "ExternalLineId";

	/**
	 * Set Mindesthaltbarkeit.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHU_BestBeforeDate (java.sql.Timestamp HU_BestBeforeDate);

	/**
	 * Get Mindesthaltbarkeit.
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
	 * Set Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_LineContent (java.lang.String I_LineContent);

	/**
	 * Get Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_LineContent();

    /** Column definition for I_LineContent */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_I_LineContent = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "I_LineContent", null);
    /** Column name I_LineContent */
    public static final String COLUMNNAME_I_LineContent = "I_LineContent";

	/**
	 * Set Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_LineNo (int I_LineNo);

	/**
	 * Get Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getI_LineNo();

    /** Column definition for I_LineNo */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_I_LineNo = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "I_LineNo", null);
    /** Column name I_LineNo */
    public static final String COLUMNNAME_I_LineNo = "I_LineNo";

	/**
	 * Set Inventurdatum.
	 * Datum zu dem die Inventur gilt, d.h. Belegedatum des Inventurbelegs
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInventoryDate (java.sql.Timestamp InventoryDate);

	/**
	 * Get Inventurdatum.
	 * Datum zu dem die Inventur gilt, d.h. Belegedatum des Inventurbelegs
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getInventoryDate();

    /** Column definition for InventoryDate */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_InventoryDate = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "InventoryDate", null);
    /** Column name InventoryDate */
    public static final String COLUMNNAME_InventoryDate = "InventoryDate";

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
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Inventory_ID (int M_Inventory_ID);

	/**
	 * Get Inventur.
	 * Parameters for a Physical Inventory
	 *
	 * <br>Type: Table
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
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_InventoryLine_ID (int M_InventoryLine_ID);

	/**
	 * Get Inventur-Position.
	 * Unique line in an Inventory document
	 *
	 * <br>Type: Table
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

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

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
	 * Set Produktschlüssel.
	 * Produkt-Identifikator;
 "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductValue (java.lang.String ProductValue);

	/**
	 * Get Produktschlüssel.
	 * Produkt-Identifikator;
 "val-<Suchschlüssel>", "ext-<Externe Id>" oder interne M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductValue();

    /** Column definition for ProductValue */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_ProductValue = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "ProductValue", null);
    /** Column name ProductValue */
    public static final String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set Zählmenge.
	 * Gezählte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyCount (java.math.BigDecimal QtyCount);

	/**
	 * Get Zählmenge.
	 * Gezählte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyCount();

    /** Column definition for QtyCount */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_QtyCount = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "QtyCount", null);
    /** Column name QtyCount */
    public static final String COLUMNNAME_QtyCount = "QtyCount";

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
	 * Set UPC.
	 * Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUPC (java.lang.String UPC);

	/**
	 * Get UPC.
	 * Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)
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

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Warehouse Locator Identifier.
	 * Text that contains identifier of warehouse. locator and dimensions
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWarehouseLocatorIdentifier (java.lang.String WarehouseLocatorIdentifier);

	/**
	 * Get Warehouse Locator Identifier.
	 * Text that contains identifier of warehouse. locator and dimensions
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWarehouseLocatorIdentifier();

    /** Column definition for WarehouseLocatorIdentifier */
    public static final org.adempiere.model.ModelColumn<I_I_Inventory, Object> COLUMN_WarehouseLocatorIdentifier = new org.adempiere.model.ModelColumn<I_I_Inventory, Object>(I_I_Inventory.class, "WarehouseLocatorIdentifier", null);
    /** Column name WarehouseLocatorIdentifier */
    public static final String COLUMNNAME_WarehouseLocatorIdentifier = "WarehouseLocatorIdentifier";

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
