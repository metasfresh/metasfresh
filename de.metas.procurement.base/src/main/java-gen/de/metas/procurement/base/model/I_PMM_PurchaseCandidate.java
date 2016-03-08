package de.metas.procurement.base.model;


/** Generated Interface for PMM_PurchaseCandidate
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_PMM_PurchaseCandidate 
{

    /** TableName=PMM_PurchaseCandidate */
    public static final String Table_Name = "PMM_PurchaseCandidate";

    /** AD_Table_ID=540745 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_AD_Client>(I_PMM_PurchaseCandidate.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_AD_Org>(I_PMM_PurchaseCandidate.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_C_BPartner>(I_PMM_PurchaseCandidate.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_C_Currency>(I_PMM_PurchaseCandidate.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_C_UOM>(I_PMM_PurchaseCandidate.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object>(I_PMM_PurchaseCandidate.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_AD_User>(I_PMM_PurchaseCandidate.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Zugesagter Termin.
	 * Zugesagter Termin für diesen Auftrag
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDatePromised (java.sql.Timestamp DatePromised);

	/**
	 * Get Zugesagter Termin.
	 * Zugesagter Termin für diesen Auftrag
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDatePromised();

    /** Column definition for DatePromised */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object> COLUMN_DatePromised = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object>(I_PMM_PurchaseCandidate.class, "DatePromised", null);
    /** Column name DatePromised */
    public static final String COLUMNNAME_DatePromised = "DatePromised";

	/**
	 * Set Fehlermeldung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setErrorMsg (java.lang.String ErrorMsg);

	/**
	 * Get Fehlermeldung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getErrorMsg();

    /** Column definition for ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object> COLUMN_ErrorMsg = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object>(I_PMM_PurchaseCandidate.class, "ErrorMsg", null);
    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object>(I_PMM_PurchaseCandidate.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Last PMM Qty Report Event.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLast_QtyReport_Event_ID (int Last_QtyReport_Event_ID);

	/**
	 * Get Last PMM Qty Report Event.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLast_QtyReport_Event_ID();

	public de.metas.procurement.base.model.I_PMM_QtyReport_Event getLast_QtyReport_Event();

	public void setLast_QtyReport_Event(de.metas.procurement.base.model.I_PMM_QtyReport_Event Last_QtyReport_Event);

    /** Column definition for Last_QtyReport_Event_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, de.metas.procurement.base.model.I_PMM_QtyReport_Event> COLUMN_Last_QtyReport_Event_ID = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, de.metas.procurement.base.model.I_PMM_QtyReport_Event>(I_PMM_PurchaseCandidate.class, "Last_QtyReport_Event_ID", de.metas.procurement.base.model.I_PMM_QtyReport_Event.class);
    /** Column name Last_QtyReport_Event_ID */
    public static final String COLUMNNAME_Last_QtyReport_Event_ID = "Last_QtyReport_Event_ID";

	/**
	 * Set Packvorschrift-Produkt Zuordnung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID);

	/**
	 * Get Packvorschrift-Produkt Zuordnung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_Item_Product_ID();

	public de.metas.handlingunits.model.I_M_HU_PI_Item_Product getM_HU_PI_Item_Product();

	public void setM_HU_PI_Item_Product(de.metas.handlingunits.model.I_M_HU_PI_Item_Product M_HU_PI_Item_Product);

    /** Column definition for M_HU_PI_Item_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, de.metas.handlingunits.model.I_M_HU_PI_Item_Product> COLUMN_M_HU_PI_Item_Product_ID = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, de.metas.handlingunits.model.I_M_HU_PI_Item_Product>(I_PMM_PurchaseCandidate.class, "M_HU_PI_Item_Product_ID", de.metas.handlingunits.model.I_M_HU_PI_Item_Product.class);
    /** Column name M_HU_PI_Item_Product_ID */
    public static final String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

	/**
	 * Set Preisliste.
	 * Bezeichnung der Preisliste
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Preisliste.
	 * Bezeichnung der Preisliste
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_PriceList_ID();

	public org.compiere.model.I_M_PriceList getM_PriceList();

	public void setM_PriceList(org.compiere.model.I_M_PriceList M_PriceList);

    /** Column definition for M_PriceList_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_M_PriceList> COLUMN_M_PriceList_ID = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_M_PriceList>(I_PMM_PurchaseCandidate.class, "M_PriceList_ID", org.compiere.model.I_M_PriceList.class);
    /** Column name M_PriceList_ID */
    public static final String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/**
	 * Set Preissystem.
	 * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Preissystem.
	 * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_PricingSystem_ID();

	public org.compiere.model.I_M_PricingSystem getM_PricingSystem();

	public void setM_PricingSystem(org.compiere.model.I_M_PricingSystem M_PricingSystem);

    /** Column definition for M_PricingSystem_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_M_PricingSystem> COLUMN_M_PricingSystem_ID = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_M_PricingSystem>(I_PMM_PurchaseCandidate.class, "M_PricingSystem_ID", org.compiere.model.I_M_PricingSystem.class);
    /** Column name M_PricingSystem_ID */
    public static final String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

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
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_M_Product>(I_PMM_PurchaseCandidate.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Lager.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Lager.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse();

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_M_Warehouse>(I_PMM_PurchaseCandidate.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Purchase order candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPMM_PurchaseCandidate_ID (int PMM_PurchaseCandidate_ID);

	/**
	 * Get Purchase order candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPMM_PurchaseCandidate_ID();

    /** Column definition for PMM_PurchaseCandidate_ID */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object> COLUMN_PMM_PurchaseCandidate_ID = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object>(I_PMM_PurchaseCandidate.class, "PMM_PurchaseCandidate_ID", null);
    /** Column name PMM_PurchaseCandidate_ID */
    public static final String COLUMNNAME_PMM_PurchaseCandidate_ID = "PMM_PurchaseCandidate_ID";

	/**
	 * Set Preis.
	 * Preis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPrice (java.math.BigDecimal Price);

	/**
	 * Get Preis.
	 * Preis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPrice();

    /** Column definition for Price */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object> COLUMN_Price = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object>(I_PMM_PurchaseCandidate.class, "Price", null);
    /** Column name Price */
    public static final String COLUMNNAME_Price = "Price";

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
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object>(I_PMM_PurchaseCandidate.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object>(I_PMM_PurchaseCandidate.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Bestellte Menge.
	 * Bestellte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered);

	/**
	 * Get Bestellte Menge.
	 * Bestellte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered();

    /** Column definition for QtyOrdered */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object> COLUMN_QtyOrdered = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object>(I_PMM_PurchaseCandidate.class, "QtyOrdered", null);
    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set Bestellte Menge (next week).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setQtyOrdered_NextWeek (java.math.BigDecimal QtyOrdered_NextWeek);

	/**
	 * Get Bestellte Menge (next week).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.math.BigDecimal getQtyOrdered_NextWeek();

    /** Column definition for QtyOrdered_NextWeek */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object> COLUMN_QtyOrdered_NextWeek = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object>(I_PMM_PurchaseCandidate.class, "QtyOrdered_NextWeek", null);
    /** Column name QtyOrdered_NextWeek */
    public static final String COLUMNNAME_QtyOrdered_NextWeek = "QtyOrdered_NextWeek";

	/**
	 * Set Bestellte Menge (this week).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setQtyOrdered_ThisWeek (java.math.BigDecimal QtyOrdered_ThisWeek);

	/**
	 * Get Bestellte Menge (this week).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.math.BigDecimal getQtyOrdered_ThisWeek();

    /** Column definition for QtyOrdered_ThisWeek */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object> COLUMN_QtyOrdered_ThisWeek = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object>(I_PMM_PurchaseCandidate.class, "QtyOrdered_ThisWeek", null);
    /** Column name QtyOrdered_ThisWeek */
    public static final String COLUMNNAME_QtyOrdered_ThisWeek = "QtyOrdered_ThisWeek";

	/**
	 * Set Zusagbar.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyPromised (java.math.BigDecimal QtyPromised);

	/**
	 * Get Zusagbar.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyPromised();

    /** Column definition for QtyPromised */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object> COLUMN_QtyPromised = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object>(I_PMM_PurchaseCandidate.class, "QtyPromised", null);
    /** Column name QtyPromised */
    public static final String COLUMNNAME_QtyPromised = "QtyPromised";

	/**
	 * Set Zusagbar (next week).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setQtyPromised_NextWeek (java.math.BigDecimal QtyPromised_NextWeek);

	/**
	 * Get Zusagbar (next week).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.math.BigDecimal getQtyPromised_NextWeek();

    /** Column definition for QtyPromised_NextWeek */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object> COLUMN_QtyPromised_NextWeek = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object>(I_PMM_PurchaseCandidate.class, "QtyPromised_NextWeek", null);
    /** Column name QtyPromised_NextWeek */
    public static final String COLUMNNAME_QtyPromised_NextWeek = "QtyPromised_NextWeek";

	/**
	 * Set Zusagbar (this week).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public void setQtyPromised_ThisWeek (java.math.BigDecimal QtyPromised_ThisWeek);

	/**
	 * Get Zusagbar (this week).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public java.math.BigDecimal getQtyPromised_ThisWeek();

    /** Column definition for QtyPromised_ThisWeek */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object> COLUMN_QtyPromised_ThisWeek = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object>(I_PMM_PurchaseCandidate.class, "QtyPromised_ThisWeek", null);
    /** Column name QtyPromised_ThisWeek */
    public static final String COLUMNNAME_QtyPromised_ThisWeek = "QtyPromised_ThisWeek";

	/**
	 * Set Quantity to Order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyToOrder (java.math.BigDecimal QtyToOrder);

	/**
	 * Get Quantity to Order.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToOrder();

    /** Column definition for QtyToOrder */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object> COLUMN_QtyToOrder = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object>(I_PMM_PurchaseCandidate.class, "QtyToOrder", null);
    /** Column name QtyToOrder */
    public static final String COLUMNNAME_QtyToOrder = "QtyToOrder";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, Object>(I_PMM_PurchaseCandidate.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_PMM_PurchaseCandidate, org.compiere.model.I_AD_User>(I_PMM_PurchaseCandidate.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
