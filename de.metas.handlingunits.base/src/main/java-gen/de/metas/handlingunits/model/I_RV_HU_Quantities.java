package de.metas.handlingunits.model;


/** Generated Interface for RV_HU_Quantities
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_RV_HU_Quantities 
{

    /** TableName=RV_HU_Quantities */
    public static final String Table_Name = "RV_HU_Quantities";

    /** AD_Table_ID=540598 */
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, org.compiere.model.I_AD_Client>(I_RV_HU_Quantities.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, org.compiere.model.I_AD_Org>(I_RV_HU_Quantities.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set ASI Key.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setASIKey (java.lang.String ASIKey);

	/**
	 * Get ASI Key.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getASIKey();

    /** Column definition for ASIKey */
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object> COLUMN_ASIKey = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object>(I_RV_HU_Quantities.class, "ASIKey", null);
    /** Column name ASIKey */
    public static final String COLUMNNAME_ASIKey = "ASIKey";

	/**
	 * Set Attributwerte.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setASIKeyName (java.lang.String ASIKeyName);

	/**
	 * Get Attributwerte.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getASIKeyName();

    /** Column definition for ASIKeyName */
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object> COLUMN_ASIKeyName = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object>(I_RV_HU_Quantities.class, "ASIKeyName", null);
    /** Column name ASIKeyName */
    public static final String COLUMNNAME_ASIKeyName = "ASIKeyName";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_UOM_ID();

	public org.compiere.model.I_C_UOM getC_UOM();

	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, org.compiere.model.I_C_UOM>(I_RV_HU_Quantities.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object>(I_RV_HU_Quantities.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, org.compiere.model.I_AD_User>(I_RV_HU_Quantities.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object>(I_RV_HU_Quantities.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Eingekauft.
	 * Die Organisation kauft dieses Produkt ein
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsPurchased (boolean IsPurchased);

	/**
	 * Get Eingekauft.
	 * Die Organisation kauft dieses Produkt ein
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isPurchased();

    /** Column definition for IsPurchased */
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object> COLUMN_IsPurchased = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object>(I_RV_HU_Quantities.class, "IsPurchased", null);
    /** Column name IsPurchased */
    public static final String COLUMNNAME_IsPurchased = "IsPurchased";

	/**
	 * Set Verkauft.
	 * Die Organisation verkauft dieses Produkt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsSold (boolean IsSold);

	/**
	 * Get Verkauft.
	 * Die Organisation verkauft dieses Produkt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isSold();

    /** Column definition for IsSold */
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object> COLUMN_IsSold = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object>(I_RV_HU_Quantities.class, "IsSold", null);
    /** Column name IsSold */
    public static final String COLUMNNAME_IsSold = "IsSold";

	/**
	 * Set Packvorschrift-Produkt Zuordnung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_Item_Product (java.lang.String M_HU_PI_Item_Product);

	/**
	 * Get Packvorschrift-Produkt Zuordnung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getM_HU_PI_Item_Product();

    /** Column definition for M_HU_PI_Item_Product */
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object> COLUMN_M_HU_PI_Item_Product = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object>(I_RV_HU_Quantities.class, "M_HU_PI_Item_Product", null);
    /** Column name M_HU_PI_Item_Product */
    public static final String COLUMNNAME_M_HU_PI_Item_Product = "M_HU_PI_Item_Product";

	/**
	 * Set Produkt-Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Produkt-Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_Category_ID();

	public org.compiere.model.I_M_Product_Category getM_Product_Category();

	public void setM_Product_Category(org.compiere.model.I_M_Product_Category M_Product_Category);

    /** Column definition for M_Product_Category_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, org.compiere.model.I_M_Product_Category> COLUMN_M_Product_Category_ID = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, org.compiere.model.I_M_Product_Category>(I_RV_HU_Quantities.class, "M_Product_Category_ID", org.compiere.model.I_M_Product_Category.class);
    /** Column name M_Product_Category_ID */
    public static final String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

	/**
	 * Set Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Produkt.
	 * Produkt, Leistung, Artikel
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Product_ID();

	public org.compiere.model.I_M_Product getM_Product();

	public void setM_Product(org.compiere.model.I_M_Product M_Product);

    /** Column definition for M_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, org.compiere.model.I_M_Product>(I_RV_HU_Quantities.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

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
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object> COLUMN_ProductName = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object>(I_RV_HU_Quantities.class, "ProductName", null);
    /** Column name ProductName */
    public static final String COLUMNNAME_ProductName = "ProductName";

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
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object> COLUMN_ProductValue = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object>(I_RV_HU_Quantities.class, "ProductValue", null);
    /** Column name ProductValue */
    public static final String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set Available Quantity.
	 * Available Quantity (On Hand - Reserved)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyAvailable (java.math.BigDecimal QtyAvailable);

	/**
	 * Get Available Quantity.
	 * Available Quantity (On Hand - Reserved)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyAvailable();

    /** Column definition for QtyAvailable */
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object> COLUMN_QtyAvailable = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object>(I_RV_HU_Quantities.class, "QtyAvailable", null);
    /** Column name QtyAvailable */
    public static final String COLUMNNAME_QtyAvailable = "QtyAvailable";

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
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object> COLUMN_QtyOnHand = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object>(I_RV_HU_Quantities.class, "QtyOnHand", null);
    /** Column name QtyOnHand */
    public static final String COLUMNNAME_QtyOnHand = "QtyOnHand";

	/**
	 * Set Bestellte Menge.
	 * Bestellte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered);

	/**
	 * Get Bestellte Menge.
	 * Bestellte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered();

    /** Column definition for QtyOrdered */
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object> COLUMN_QtyOrdered = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object>(I_RV_HU_Quantities.class, "QtyOrdered", null);
    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set Reservierte Menge.
	 * Reservierte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQtyReserved (java.math.BigDecimal QtyReserved);

	/**
	 * Get Reservierte Menge.
	 * Reservierte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyReserved();

    /** Column definition for QtyReserved */
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object> COLUMN_QtyReserved = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object>(I_RV_HU_Quantities.class, "QtyReserved", null);
    /** Column name QtyReserved */
    public static final String COLUMNNAME_QtyReserved = "QtyReserved";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, Object>(I_RV_HU_Quantities.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_RV_HU_Quantities, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_RV_HU_Quantities, org.compiere.model.I_AD_User>(I_RV_HU_Quantities.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
