package org.compiere.model;


/** Generated Interface for C_BPartner_Product
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public interface I_C_BPartner_Product
{

    /** TableName=C_BPartner_Product */
    String Table_Name = "C_BPartner_Product";

    /** AD_Table_ID=632 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_AD_Client>(I_C_BPartner_Product.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	org.compiere.model.I_AD_Org getAD_Org();

	void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_AD_Org>(I_C_BPartner_Product.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	org.compiere.model.I_C_BPartner getC_BPartner();

	void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_C_BPartner>(I_C_BPartner_Product.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Geschäftspartner-Produkt.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Product_ID (int C_BPartner_Product_ID);

	/**
	 * Get Geschäftspartner-Produkt.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Product_ID();

    /** Column definition for C_BPartner_Product_ID */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_C_BPartner_Product_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "C_BPartner_Product_ID", null);
    /** Column name C_BPartner_Product_ID */
    String COLUMNNAME_C_BPartner_Product_ID = "C_BPartner_Product_ID";

	/**
	 * Set C_BPartner_Vendor_ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Vendor_ID (int C_BPartner_Vendor_ID);

	/**
	 * Get C_BPartner_Vendor_ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Vendor_ID();

	org.compiere.model.I_C_BPartner getC_BPartner_Vendor();

	void setC_BPartner_Vendor(org.compiere.model.I_C_BPartner C_BPartner_Vendor);

    /** Column definition for C_BPartner_Vendor_ID */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_Vendor_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_C_BPartner>(I_C_BPartner_Product.class, "C_BPartner_Vendor_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_Vendor_ID */
    String COLUMNNAME_C_BPartner_Vendor_ID = "C_BPartner_Vendor_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

    /** Column definition for Created */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "Created", null);
    /** Column name Created */
    String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

    /** Column definition for CreatedBy */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_AD_User>(I_C_BPartner_Product.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Zugesicherte Lieferzeit.
	 * Zugesicherte Anzahl Tage zwischen Bestellung und Lieferung
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryTime_Promised (int DeliveryTime_Promised);

	/**
	 * Get Zugesicherte Lieferzeit.
	 * Zugesicherte Anzahl Tage zwischen Bestellung und Lieferung
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDeliveryTime_Promised();

    /** Column definition for DeliveryTime_Promised */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_DeliveryTime_Promised = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "DeliveryTime_Promised", null);
    /** Column name DeliveryTime_Promised */
    String COLUMNNAME_DeliveryTime_Promised = "DeliveryTime_Promised";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getDescription();

    /** Column definition for Description */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "Description", null);
    /** Column name Description */
    String COLUMNNAME_Description = "Description";

	/** Set CU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEAN_CU (java.lang.String EAN_CU);

	/**
	 * Get CU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getEAN_CU();

    /** Column definition for EAN_CU */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_EAN_CU = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "EAN_CU", null);
    /** Column name EAN_CU */
    String COLUMNNAME_EAN_CU = "EAN_CU";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

    /** Column definition for IsActive */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "IsActive", null);
    /** Column name IsActive */
    String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Gegenwärtiger Lieferant.
	 * Diesen Lieferanten für Bepreisung und Lagerauffüllung verwenden
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCurrentVendor (boolean IsCurrentVendor);

	/**
	 * Get Gegenwärtiger Lieferant.
	 * Diesen Lieferanten für Bepreisung und Lagerauffüllung verwenden
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCurrentVendor();

    /** Column definition for IsCurrentVendor */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_IsCurrentVendor = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "IsCurrentVendor", null);
    /** Column name IsCurrentVendor */
    String COLUMNNAME_IsCurrentVendor = "IsCurrentVendor";

	/**
	 * Set Streckengeschäft.
	 * Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDropShip (boolean IsDropShip);

	/**
	 * Get Streckengeschäft.
	 * Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDropShip();

    /** Column definition for IsDropShip */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_IsDropShip = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "IsDropShip", null);
    /** Column name IsDropShip */
    String COLUMNNAME_IsDropShip = "IsDropShip";

	/**
	 * Set Hersteller.
	 * Manufacturer of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setManufacturer (java.lang.String Manufacturer);

	/**
	 * Get Hersteller.
	 * Manufacturer of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getManufacturer();

    /** Column definition for Manufacturer */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_Manufacturer = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "Manufacturer", null);
    /** Column name Manufacturer */
    String COLUMNNAME_Manufacturer = "Manufacturer";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	org.compiere.model.I_M_Product getM_Product();

	void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_M_Product>(I_C_BPartner_Product.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Mindestbestellmenge.
	 * Mindestbestellmenge in Mengeneinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrder_Min (java.math.BigDecimal Order_Min);

	/**
	 * Get Mindestbestellmenge.
	 * Mindestbestellmenge in Mengeneinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getOrder_Min();

    /** Column definition for Order_Min */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_Order_Min = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "Order_Min", null);
    /** Column name Order_Min */
    String COLUMNNAME_Order_Min = "Order_Min";

	/**
	 * Set Packungsgröße.
	 * Größe einer Bestellpackung in Mengeneinheit (z.B. Satz von 5 Einheiten)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrder_Pack (java.math.BigDecimal Order_Pack);

	/**
	 * Get Packungsgröße.
	 * Größe einer Bestellpackung in Mengeneinheit (z.B. Satz von 5 Einheiten)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getOrder_Pack();

    /** Column definition for Order_Pack */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_Order_Pack = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "Order_Pack", null);
    /** Column name Order_Pack */
    String COLUMNNAME_Order_Pack = "Order_Pack";

	/**
	 * Set Produktbeschreibung.
	 * Produktbeschreibung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductDescription (java.lang.String ProductDescription);

	/**
	 * Get Produktbeschreibung.
	 * Produktbeschreibung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductDescription();

    /** Column definition for ProductDescription */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_ProductDescription = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "ProductDescription", null);
    /** Column name ProductDescription */
    public static final String COLUMNNAME_ProductDescription = "ProductDescription";

	/**
	 * Set Produktname.
	 * Name des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductName (java.lang.String ProductName);

	/**
	 * Get Produktname.
	 * Name des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductName();

    /** Column definition for ProductName */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_ProductName = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "ProductName", null);
    /** Column name ProductName */
    public static final String COLUMNNAME_ProductName = "ProductName";

	/**
	 * Set Produktnummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductNo (java.lang.String ProductNo);

	/**
	 * Get Produktnummer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductNo();

    /** Column definition for ProductNo */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_ProductNo = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "ProductNo", null);
    /** Column name ProductNo */
    public static final String COLUMNNAME_ProductNo = "ProductNo";
    
	/**
	 * Set Qualitäts-Einstufung.
	 * Method for rating vendors
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQualityRating (java.math.BigDecimal QualityRating);

	/**
	 * Get Qualitäts-Einstufung.
	 * Method for rating vendors
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getQualityRating();

    /** Column definition for QualityRating */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_QualityRating = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "QualityRating", null);
    /** Column name QualityRating */
    String COLUMNNAME_QualityRating = "QualityRating";

	/**
	 * Set Mindesthaltbarkeit Tage.
	 * Minimum Shelf Life in days based on Product Instance Guarantee Date
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setShelfLifeMinDays (int ShelfLifeMinDays);

	/**
	 * Get Mindesthaltbarkeit Tage.
	 * Minimum Shelf Life in days based on Product Instance Guarantee Date
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getShelfLifeMinDays();

    /** Column definition for ShelfLifeMinDays */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_ShelfLifeMinDays = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "ShelfLifeMinDays", null);
    /** Column name ShelfLifeMinDays */
    String COLUMNNAME_ShelfLifeMinDays = "ShelfLifeMinDays";

	/**
	 * Set Mindesthaltbarkeit %.
	 * Minimum Shelf Life in percent based on Product Instance Guarantee Date
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setShelfLifeMinPct (int ShelfLifeMinPct);

	/**
	 * Get Mindesthaltbarkeit %.
	 * Minimum Shelf Life in percent based on Product Instance Guarantee Date
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getShelfLifeMinPct();

    /** Column definition for ShelfLifeMinPct */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_ShelfLifeMinPct = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "ShelfLifeMinPct", null);
    /** Column name ShelfLifeMinPct */
    String COLUMNNAME_ShelfLifeMinPct = "ShelfLifeMinPct";

	/**
	 * Set UPC/EAN.
	 * Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUPC (java.lang.String UPC);

	/**
	 * Get UPC/EAN.
	 * Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getUPC();

    /** Column definition for UPC */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_UPC = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "UPC", null);
    /** Column name UPC */
    String COLUMNNAME_UPC = "UPC";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "Updated", null);
    /** Column name Updated */
    String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

    /** Column definition for UpdatedBy */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_AD_User>(I_C_BPartner_Product.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Produkt-Kategorie Geschäftspartner.
	 * Product Category of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVendorCategory (java.lang.String VendorCategory);

	/**
	 * Get Produkt-Kategorie Geschäftspartner.
	 * Product Category of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getVendorCategory();

    /** Column definition for VendorCategory */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_VendorCategory = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "VendorCategory", null);
    /** Column name VendorCategory */
    String COLUMNNAME_VendorCategory = "VendorCategory";

	/**
	 * Set Produkt-Nr. Geschäftspartner.
	 * Product Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVendorProductNo (java.lang.String VendorProductNo);

	/**
	 * Get Produkt-Nr. Geschäftspartner.
	 * Product Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getVendorProductNo();

    /** Column definition for VendorProductNo */
    org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_VendorProductNo = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "VendorProductNo", null);
    /** Column name VendorProductNo */
    String COLUMNNAME_VendorProductNo = "VendorProductNo";
}
