package org.compiere.model;


/** Generated Interface for C_BPartner_Product
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_BPartner_Product 
{

    /** TableName=C_BPartner_Product */
    public static final String Table_Name = "C_BPartner_Product";

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
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_AD_Client>(I_C_BPartner_Product.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_AD_Org>(I_C_BPartner_Product.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Identifies a Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_C_BPartner>(I_C_BPartner_Product.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Geschäftspartner-Produkt.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Product_ID (int C_BPartner_Product_ID);

	/**
	 * Get Geschäftspartner-Produkt.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Product_ID();

    /** Column definition for C_BPartner_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_C_BPartner_Product_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "C_BPartner_Product_ID", null);
    /** Column name C_BPartner_Product_ID */
    public static final String COLUMNNAME_C_BPartner_Product_ID = "C_BPartner_Product_ID";

	/**
	 * Set C_BPartner_Vendor_ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Vendor_ID (int C_BPartner_Vendor_ID);

	/**
	 * Get C_BPartner_Vendor_ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Vendor_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner_Vendor();

	public void setC_BPartner_Vendor(org.compiere.model.I_C_BPartner C_BPartner_Vendor);

    /** Column definition for C_BPartner_Vendor_ID */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_Vendor_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_C_BPartner>(I_C_BPartner_Product.class, "C_BPartner_Vendor_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_Vendor_ID */
    public static final String COLUMNNAME_C_BPartner_Vendor_ID = "C_BPartner_Vendor_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_AD_User>(I_C_BPartner_Product.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Auszeichnungsname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCustomerLabelName (java.lang.String CustomerLabelName);

	/**
	 * Get Auszeichnungsname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCustomerLabelName();

    /** Column definition for CustomerLabelName */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_CustomerLabelName = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "CustomerLabelName", null);
    /** Column name CustomerLabelName */
    public static final String COLUMNNAME_CustomerLabelName = "CustomerLabelName";

	/**
	 * Set Zugesicherte Lieferzeit.
	 * Zugesicherte Anzahl Tage zwischen Bestellung und Lieferung
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDeliveryTime_Promised (int DeliveryTime_Promised);

	/**
	 * Get Zugesicherte Lieferzeit.
	 * Zugesicherte Anzahl Tage zwischen Bestellung und Lieferung
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDeliveryTime_Promised();

    /** Column definition for DeliveryTime_Promised */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_DeliveryTime_Promised = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "DeliveryTime_Promised", null);
    /** Column name DeliveryTime_Promised */
    public static final String COLUMNNAME_DeliveryTime_Promised = "DeliveryTime_Promised";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Gegenwärtiger Lieferant.
	 * Diesen Lieferanten für Bepreisung und Lagerauffüllung verwenden
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCurrentVendor (boolean IsCurrentVendor);

	/**
	 * Get Gegenwärtiger Lieferant.
	 * Diesen Lieferanten für Bepreisung und Lagerauffüllung verwenden
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCurrentVendor();

    /** Column definition for IsCurrentVendor */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_IsCurrentVendor = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "IsCurrentVendor", null);
    /** Column name IsCurrentVendor */
    public static final String COLUMNNAME_IsCurrentVendor = "IsCurrentVendor";

	/**
	 * Set Streckengeschäft.
	 * Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDropShip (boolean IsDropShip);

	/**
	 * Get Streckengeschäft.
	 * Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDropShip();

    /** Column definition for IsDropShip */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_IsDropShip = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "IsDropShip", null);
    /** Column name IsDropShip */
    public static final String COLUMNNAME_IsDropShip = "IsDropShip";

	/**
	 * Set Ban on sales documents.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSalesBan (boolean IsSalesBan);

	/**
	 * Get Ban on sales documents.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSalesBan();

    /** Column definition for IsSalesBan */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_IsSalesBan = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "IsSalesBan", null);
    /** Column name IsSalesBan */
    public static final String COLUMNNAME_IsSalesBan = "IsSalesBan";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_M_Product>(I_C_BPartner_Product.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Hersteller.
	 * Manufacturer of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setManufacturer (java.lang.String Manufacturer);

	/**
	 * Get Hersteller.
	 * Manufacturer of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getManufacturer();

    /** Column definition for Manufacturer */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_Manufacturer = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "Manufacturer", null);
    /** Column name Manufacturer */
    public static final String COLUMNNAME_Manufacturer = "Manufacturer";

	/**
	 * Set Mindestbestellmenge.
	 * Mindestbestellmenge in Mengeneinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrder_Min (java.math.BigDecimal Order_Min);

	/**
	 * Get Mindestbestellmenge.
	 * Mindestbestellmenge in Mengeneinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getOrder_Min();

    /** Column definition for Order_Min */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_Order_Min = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "Order_Min", null);
    /** Column name Order_Min */
    public static final String COLUMNNAME_Order_Min = "Order_Min";

	/**
	 * Set Packungsgröße.
	 * Größe einer Bestellpackung in Mengeneinheit (z.B. Satz von 5 Einheiten)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrder_Pack (java.math.BigDecimal Order_Pack);

	/**
	 * Get Packungsgröße.
	 * Größe einer Bestellpackung in Mengeneinheit (z.B. Satz von 5 Einheiten)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getOrder_Pack();

    /** Column definition for Order_Pack */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_Order_Pack = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "Order_Pack", null);
    /** Column name Order_Pack */
    public static final String COLUMNNAME_Order_Pack = "Order_Pack";

	/**
	 * Set Qualitäts-Einstufung.
	 * Method for rating vendors
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQualityRating (java.math.BigDecimal QualityRating);

	/**
	 * Get Qualitäts-Einstufung.
	 * Method for rating vendors
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQualityRating();

    /** Column definition for QualityRating */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_QualityRating = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "QualityRating", null);
    /** Column name QualityRating */
    public static final String COLUMNNAME_QualityRating = "QualityRating";

	/**
	 * Set Sales Ban Reason.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSalesBanReason (java.lang.String SalesBanReason);

	/**
	 * Get Sales Ban Reason.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSalesBanReason();

    /** Column definition for SalesBanReason */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_SalesBanReason = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "SalesBanReason", null);
    /** Column name SalesBanReason */
    public static final String COLUMNNAME_SalesBanReason = "SalesBanReason";

	/**
	 * Set Mindesthaltbarkeit Tage.
	 * Minimum Shelf Life in days based on Product Instance Guarantee Date
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setShelfLifeMinDays (int ShelfLifeMinDays);

	/**
	 * Get Mindesthaltbarkeit Tage.
	 * Minimum Shelf Life in days based on Product Instance Guarantee Date
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getShelfLifeMinDays();

    /** Column definition for ShelfLifeMinDays */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_ShelfLifeMinDays = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "ShelfLifeMinDays", null);
    /** Column name ShelfLifeMinDays */
    public static final String COLUMNNAME_ShelfLifeMinDays = "ShelfLifeMinDays";

	/**
	 * Set Mindesthaltbarkeit %.
	 * Minimum Shelf Life in percent based on Product Instance Guarantee Date
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setShelfLifeMinPct (int ShelfLifeMinPct);

	/**
	 * Get Mindesthaltbarkeit %.
	 * Minimum Shelf Life in percent based on Product Instance Guarantee Date
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getShelfLifeMinPct();

    /** Column definition for ShelfLifeMinPct */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_ShelfLifeMinPct = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "ShelfLifeMinPct", null);
    /** Column name ShelfLifeMinPct */
    public static final String COLUMNNAME_ShelfLifeMinPct = "ShelfLifeMinPct";

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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, org.compiere.model.I_AD_User>(I_C_BPartner_Product.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Produkt-Kategorie Geschäftspartner.
	 * Product Category of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVendorCategory (java.lang.String VendorCategory);

	/**
	 * Get Produkt-Kategorie Geschäftspartner.
	 * Product Category of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVendorCategory();

    /** Column definition for VendorCategory */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_VendorCategory = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "VendorCategory", null);
    /** Column name VendorCategory */
    public static final String COLUMNNAME_VendorCategory = "VendorCategory";

	/**
	 * Set Produkt-Nr. Geschäftspartner.
	 * Product Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVendorProductNo (java.lang.String VendorProductNo);

	/**
	 * Get Produkt-Nr. Geschäftspartner.
	 * Product Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVendorProductNo();

    /** Column definition for VendorProductNo */
    public static final org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object> COLUMN_VendorProductNo = new org.adempiere.model.ModelColumn<I_C_BPartner_Product, Object>(I_C_BPartner_Product.class, "VendorProductNo", null);
    /** Column name VendorProductNo */
    public static final String COLUMNNAME_VendorProductNo = "VendorProductNo";
}
