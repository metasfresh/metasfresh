package de.metas.material.cockpit.model;


/** Generated Interface for T_MD_Stock_WarehouseAndProduct
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_T_MD_Stock_WarehouseAndProduct 
{

    /** TableName=T_MD_Stock_WarehouseAndProduct */
    public static final String Table_Name = "T_MD_Stock_WarehouseAndProduct";

    /** AD_Table_ID=541160 */
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
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, org.compiere.model.I_AD_Client>(I_T_MD_Stock_WarehouseAndProduct.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

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

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, org.compiere.model.I_AD_Org>(I_T_MD_Stock_WarehouseAndProduct.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, Object>(I_T_MD_Stock_WarehouseAndProduct.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Set Zeile Nr..
	 * Einzelne Zeile in dem Dokument
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setLine (int Line);

	/**
	 * Get Zeile Nr..
	 * Einzelne Zeile in dem Dokument
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getLine();

    /** Column definition for Line */
    public static final org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, Object> COLUMN_Line = new org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, Object>(I_T_MD_Stock_WarehouseAndProduct.class, "Line", null);
    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/**
	 * Set Produkt Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Produkt Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Category_ID();

	public org.compiere.model.I_M_Product_Category getM_Product_Category();

	public void setM_Product_Category(org.compiere.model.I_M_Product_Category M_Product_Category);

    /** Column definition for M_Product_Category_ID */
    public static final org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, org.compiere.model.I_M_Product_Category> COLUMN_M_Product_Category_ID = new org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, org.compiere.model.I_M_Product_Category>(I_T_MD_Stock_WarehouseAndProduct.class, "M_Product_Category_ID", org.compiere.model.I_M_Product_Category.class);
    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

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
    public static final org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, org.compiere.model.I_M_Product>(I_T_MD_Stock_WarehouseAndProduct.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Lager.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Lager.
	 * Lager oder Ort für Dienstleistung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Warehouse_ID();

	public org.compiere.model.I_M_Warehouse getM_Warehouse();

	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse);

    /** Column definition for M_Warehouse_ID */
    public static final org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, org.compiere.model.I_M_Warehouse> COLUMN_M_Warehouse_ID = new org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, org.compiere.model.I_M_Warehouse>(I_T_MD_Stock_WarehouseAndProduct.class, "M_Warehouse_ID", org.compiere.model.I_M_Warehouse.class);
    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Produktschlüssel.
	 * Schlüssel des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProductValue (java.lang.String ProductValue);

	/**
	 * Get Produktschlüssel.
	 * Schlüssel des Produktes
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProductValue();

    /** Column definition for ProductValue */
    public static final org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, Object> COLUMN_ProductValue = new org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, Object>(I_T_MD_Stock_WarehouseAndProduct.class, "ProductValue", null);
    /** Column name ProductValue */
    public static final String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set Bestand.
	 * Bestand
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyOnHand (java.math.BigDecimal QtyOnHand);

	/**
	 * Get Bestand.
	 * Bestand
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOnHand();

    /** Column definition for QtyOnHand */
    public static final org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, Object> COLUMN_QtyOnHand = new org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, Object>(I_T_MD_Stock_WarehouseAndProduct.class, "QtyOnHand", null);
    /** Column name QtyOnHand */
    public static final String COLUMNNAME_QtyOnHand = "QtyOnHand";

	/**
	 * Set T_MD_Stock_WarehouseAndProduct_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setT_MD_Stock_WarehouseAndProduct_ID (int T_MD_Stock_WarehouseAndProduct_ID);

	/**
	 * Get T_MD_Stock_WarehouseAndProduct_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getT_MD_Stock_WarehouseAndProduct_ID();

    /** Column definition for T_MD_Stock_WarehouseAndProduct_ID */
    public static final org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, Object> COLUMN_T_MD_Stock_WarehouseAndProduct_ID = new org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, Object>(I_T_MD_Stock_WarehouseAndProduct.class, "T_MD_Stock_WarehouseAndProduct_ID", null);
    /** Column name T_MD_Stock_WarehouseAndProduct_ID */
    public static final String COLUMNNAME_T_MD_Stock_WarehouseAndProduct_ID = "T_MD_Stock_WarehouseAndProduct_ID";

	/**
	 * Set UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUUID (java.lang.String UUID);

	/**
	 * Get UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUUID();

    /** Column definition for UUID */
    public static final org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, Object> COLUMN_UUID = new org.adempiere.model.ModelColumn<I_T_MD_Stock_WarehouseAndProduct, Object>(I_T_MD_Stock_WarehouseAndProduct.class, "UUID", null);
    /** Column name UUID */
    public static final String COLUMNNAME_UUID = "UUID";
}
