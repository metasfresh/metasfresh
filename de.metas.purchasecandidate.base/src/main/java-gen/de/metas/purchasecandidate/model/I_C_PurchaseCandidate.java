package de.metas.purchasecandidate.model;


/** Generated Interface for C_PurchaseCandidate
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_PurchaseCandidate 
{

    /** TableName=C_PurchaseCandidate */
    public static final String Table_Name = "C_PurchaseCandidate";

    /** AD_Table_ID=540861 */
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
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_AD_Client>(I_C_PurchaseCandidate.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_AD_Org>(I_C_PurchaseCandidate.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * Die Währung für diesen Eintrag
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_C_Currency>(I_C_PurchaseCandidate.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderLineSO_ID (int C_OrderLineSO_ID);

	/**
	 * Get Auftragsposition.
	 * Auftragsposition
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderLineSO_ID();

	public org.compiere.model.I_C_OrderLine getC_OrderLineSO();

	public void setC_OrderLineSO(org.compiere.model.I_C_OrderLine C_OrderLineSO);

    /** Column definition for C_OrderLineSO_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLineSO_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_C_OrderLine>(I_C_PurchaseCandidate.class, "C_OrderLineSO_ID", org.compiere.model.I_C_OrderLine.class);
    /** Column name C_OrderLineSO_ID */
    public static final String COLUMNNAME_C_OrderLineSO_ID = "C_OrderLineSO_ID";

	/**
	 * Set Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_OrderSO_ID (int C_OrderSO_ID);

	/**
	 * Get Auftrag.
	 * Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_OrderSO_ID();

	public org.compiere.model.I_C_Order getC_OrderSO();

	public void setC_OrderSO(org.compiere.model.I_C_Order C_OrderSO);

    /** Column definition for C_OrderSO_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_C_Order> COLUMN_C_OrderSO_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_C_Order>(I_C_PurchaseCandidate.class, "C_OrderSO_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_OrderSO_ID */
    public static final String COLUMNNAME_C_OrderSO_ID = "C_OrderSO_ID";

	/**
	 * Set Purchase candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_PurchaseCandidate_ID (int C_PurchaseCandidate_ID);

	/**
	 * Get Purchase candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_PurchaseCandidate_ID();

    /** Column definition for C_PurchaseCandidate_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_C_PurchaseCandidate_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object>(I_C_PurchaseCandidate.class, "C_PurchaseCandidate_ID", null);
    /** Column name C_PurchaseCandidate_ID */
    public static final String COLUMNNAME_C_PurchaseCandidate_ID = "C_PurchaseCandidate_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object>(I_C_PurchaseCandidate.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_AD_User>(I_C_PurchaseCandidate.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_C_UOM>(I_C_PurchaseCandidate.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Referenz.
	 * Bestelldispo-Zeilen, die den selben Bedarf (z.b. die selbe Auftragszeile) addressieren habe den selben Referenz-Wert
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDemandReference (java.lang.String DemandReference);

	/**
	 * Get Referenz.
	 * Bestelldispo-Zeilen, die den selben Bedarf (z.b. die selbe Auftragszeile) addressieren habe den selben Referenz-Wert
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDemandReference();

    /** Column definition for DemandReference */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_DemandReference = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object>(I_C_PurchaseCandidate.class, "DemandReference", null);
    /** Column name DemandReference */
    public static final String COLUMNNAME_DemandReference = "DemandReference";

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
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object>(I_C_PurchaseCandidate.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Aggregate Purchase Orders.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAggregatePO (boolean IsAggregatePO);

	/**
	 * Get Aggregate Purchase Orders.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAggregatePO();

    /** Column definition for IsAggregatePO */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_IsAggregatePO = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object>(I_C_PurchaseCandidate.class, "IsAggregatePO", null);
    /** Column name IsAggregatePO */
    public static final String COLUMNNAME_IsAggregatePO = "IsAggregatePO";

	/**
	 * Set Prepared.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPrepared (boolean IsPrepared);

	/**
	 * Get Prepared.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPrepared();

    /** Column definition for IsPrepared */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_IsPrepared = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object>(I_C_PurchaseCandidate.class, "IsPrepared", null);
    /** Column name IsPrepared */
    public static final String COLUMNNAME_IsPrepared = "IsPrepared";

	/**
	 * Set Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Merkmale.
	 * Merkmals Ausprägungen zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_M_AttributeSetInstance>(I_C_PurchaseCandidate.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_M_Product>(I_C_PurchaseCandidate.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Liefer-Lager.
	 * Lager, an das der Lieferant eine Bestellung liefern soll.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_WarehousePO_ID (int M_WarehousePO_ID);

	/**
	 * Get Liefer-Lager.
	 * Lager, an das der Lieferant eine Bestellung liefern soll.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_WarehousePO_ID();

	public org.compiere.model.I_M_Warehouse getM_WarehousePO();

	public void setM_WarehousePO(org.compiere.model.I_M_Warehouse M_WarehousePO);

    /** Column definition for M_WarehousePO_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_M_Warehouse> COLUMN_M_WarehousePO_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_M_Warehouse>(I_C_PurchaseCandidate.class, "M_WarehousePO_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_WarehousePO_ID */
    public static final String COLUMNNAME_M_WarehousePO_ID = "M_WarehousePO_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object>(I_C_PurchaseCandidate.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set EK Ertrag netto.
	 * Effektiver Einkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProfitPurchasePriceActual (java.math.BigDecimal ProfitPurchasePriceActual);

	/**
	 * Get EK Ertrag netto.
	 * Effektiver Einkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getProfitPurchasePriceActual();

    /** Column definition for ProfitPurchasePriceActual */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_ProfitPurchasePriceActual = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object>(I_C_PurchaseCandidate.class, "ProfitPurchasePriceActual", null);
    /** Column name ProfitPurchasePriceActual */
    public static final String COLUMNNAME_ProfitPurchasePriceActual = "ProfitPurchasePriceActual";

	/**
	 * Set VK Ertrag netto.
	 * Effektiver Verkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProfitSalesPriceActual (java.math.BigDecimal ProfitSalesPriceActual);

	/**
	 * Get VK Ertrag netto.
	 * Effektiver Verkaufspreis pro Einheit, minus erwartetem Skonto und vertraglicher Rückerstattung
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getProfitSalesPriceActual();

    /** Column definition for ProfitSalesPriceActual */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_ProfitSalesPriceActual = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object>(I_C_PurchaseCandidate.class, "ProfitSalesPriceActual", null);
    /** Column name ProfitSalesPriceActual */
    public static final String COLUMNNAME_ProfitSalesPriceActual = "ProfitSalesPriceActual";

	/**
	 * Set Bestelldatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPurchaseDateOrdered (java.sql.Timestamp PurchaseDateOrdered);

	/**
	 * Get Bestelldatum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPurchaseDateOrdered();

    /** Column definition for PurchaseDateOrdered */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_PurchaseDateOrdered = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object>(I_C_PurchaseCandidate.class, "PurchaseDateOrdered", null);
    /** Column name PurchaseDateOrdered */
    public static final String COLUMNNAME_PurchaseDateOrdered = "PurchaseDateOrdered";

	/**
	 * Set Liefer-Zusagedatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPurchaseDatePromised (java.sql.Timestamp PurchaseDatePromised);

	/**
	 * Get Liefer-Zusagedatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getPurchaseDatePromised();

    /** Column definition for PurchaseDatePromised */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_PurchaseDatePromised = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object>(I_C_PurchaseCandidate.class, "PurchaseDatePromised", null);
    /** Column name PurchaseDatePromised */
    public static final String COLUMNNAME_PurchaseDatePromised = "PurchaseDatePromised";

	/**
	 * Set Bestellte Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPurchasedQty (java.math.BigDecimal PurchasedQty);

	/**
	 * Get Bestellte Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPurchasedQty();

    /** Column definition for PurchasedQty */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_PurchasedQty = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object>(I_C_PurchaseCandidate.class, "PurchasedQty", null);
    /** Column name PurchasedQty */
    public static final String COLUMNNAME_PurchasedQty = "PurchasedQty";

	/**
	 * Set EK-Preis.
	 * Einkaufspreis pro Einheit, nach Abzug des Rabattes.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPurchasePriceActual (java.math.BigDecimal PurchasePriceActual);

	/**
	 * Get EK-Preis.
	 * Einkaufspreis pro Einheit, nach Abzug des Rabattes.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPurchasePriceActual();

    /** Column definition for PurchasePriceActual */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_PurchasePriceActual = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object>(I_C_PurchaseCandidate.class, "PurchasePriceActual", null);
    /** Column name PurchasePriceActual */
    public static final String COLUMNNAME_PurchasePriceActual = "PurchasePriceActual";

	/**
	 * Set Bestellmenge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyToPurchase (java.math.BigDecimal QtyToPurchase);

	/**
	 * Get Bestellmenge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyToPurchase();

    /** Column definition for QtyToPurchase */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_QtyToPurchase = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object>(I_C_PurchaseCandidate.class, "QtyToPurchase", null);
    /** Column name QtyToPurchase */
    public static final String COLUMNNAME_QtyToPurchase = "QtyToPurchase";

	/**
	 * Set Wiedervorlage Datum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReminderDate (java.sql.Timestamp ReminderDate);

	/**
	 * Get Wiedervorlage Datum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getReminderDate();

    /** Column definition for ReminderDate */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_ReminderDate = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object>(I_C_PurchaseCandidate.class, "ReminderDate", null);
    /** Column name ReminderDate */
    public static final String COLUMNNAME_ReminderDate = "ReminderDate";

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
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, Object>(I_C_PurchaseCandidate.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_AD_User>(I_C_PurchaseCandidate.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Lieferant.
	 * The Vendor of the product/service
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setVendor_ID (int Vendor_ID);

	/**
	 * Get Lieferant.
	 * The Vendor of the product/service
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getVendor_ID();

	public org.compiere.model.I_C_BPartner getVendor();

	public void setVendor(org.compiere.model.I_C_BPartner Vendor);

    /** Column definition for Vendor_ID */
    public static final org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_C_BPartner> COLUMN_Vendor_ID = new org.adempiere.model.ModelColumn<I_C_PurchaseCandidate, org.compiere.model.I_C_BPartner>(I_C_PurchaseCandidate.class, "Vendor_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name Vendor_ID */
    public static final String COLUMNNAME_Vendor_ID = "Vendor_ID";
}
