package de.metas.fresh.model;


/** Generated Interface for X_MRP_ProductInfo_V
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_X_MRP_ProductInfo_V 
{

    /** TableName=X_MRP_ProductInfo_V */
    public static final String Table_Name = "X_MRP_ProductInfo_V";

    /** AD_Table_ID=540642 */
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
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, org.compiere.model.I_AD_Client>(I_X_MRP_ProductInfo_V.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Datum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateGeneral (java.sql.Timestamp DateGeneral);

	/**
	 * Get Datum.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateGeneral();

    /** Column definition for DateGeneral */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_DateGeneral = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "DateGeneral", null);
    /** Column name DateGeneral */
    public static final String COLUMNNAME_DateGeneral = "DateGeneral";

	/**
	 * Set MRP Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFresh_QtyMRP (java.math.BigDecimal Fresh_QtyMRP);

	/**
	 * Get MRP Menge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFresh_QtyMRP();

    /** Column definition for Fresh_QtyMRP */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_Fresh_QtyMRP = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "Fresh_QtyMRP", null);
    /** Column name Fresh_QtyMRP */
    public static final String COLUMNNAME_Fresh_QtyMRP = "Fresh_QtyMRP";

	/**
	 * Set Zählbestand.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFresh_QtyOnHand_OnDate (java.math.BigDecimal Fresh_QtyOnHand_OnDate);

	/**
	 * Get Zählbestand.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFresh_QtyOnHand_OnDate();

    /** Column definition for Fresh_QtyOnHand_OnDate */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_Fresh_QtyOnHand_OnDate = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "Fresh_QtyOnHand_OnDate", null);
    /** Column name Fresh_QtyOnHand_OnDate */
    public static final String COLUMNNAME_Fresh_QtyOnHand_OnDate = "Fresh_QtyOnHand_OnDate";

	/**
	 * Set Ind9.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFresh_QtyOnHand_OnDate_Ind9 (java.math.BigDecimal Fresh_QtyOnHand_OnDate_Ind9);

	/**
	 * Get Ind9.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFresh_QtyOnHand_OnDate_Ind9();

    /** Column definition for Fresh_QtyOnHand_OnDate_Ind9 */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_Fresh_QtyOnHand_OnDate_Ind9 = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "Fresh_QtyOnHand_OnDate_Ind9", null);
    /** Column name Fresh_QtyOnHand_OnDate_Ind9 */
    public static final String COLUMNNAME_Fresh_QtyOnHand_OnDate_Ind9 = "Fresh_QtyOnHand_OnDate_Ind9";

	/**
	 * Set Stö2.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFresh_QtyOnHand_OnDate_Stö2 (java.math.BigDecimal Fresh_QtyOnHand_OnDate_Stö2);

	/**
	 * Get Stö2.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFresh_QtyOnHand_OnDate_Stö2();

    /** Column definition for Fresh_QtyOnHand_OnDate_Stö2 */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_Fresh_QtyOnHand_OnDate_Stö2 = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "Fresh_QtyOnHand_OnDate_Stö2", null);
    /** Column name Fresh_QtyOnHand_OnDate_Stö2 */
    public static final String COLUMNNAME_Fresh_QtyOnHand_OnDate_Stö2 = "Fresh_QtyOnHand_OnDate_Stö2";

	/**
	 * Set Zusagbar Zählbestand.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFresh_QtyPromised (java.math.BigDecimal Fresh_QtyPromised);

	/**
	 * Get Zusagbar Zählbestand.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFresh_QtyPromised();

    /** Column definition for Fresh_QtyPromised */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_Fresh_QtyPromised = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "Fresh_QtyPromised", null);
    /** Column name Fresh_QtyPromised */
    public static final String COLUMNNAME_Fresh_QtyPromised = "Fresh_QtyPromised";

	/**
	 * Set Zusagbar Tag.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFresh_QtyPromised_OnDate (java.math.BigDecimal Fresh_QtyPromised_OnDate);

	/**
	 * Get Zusagbar Tag.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFresh_QtyPromised_OnDate();

    /** Column definition for Fresh_QtyPromised_OnDate */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_Fresh_QtyPromised_OnDate = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "Fresh_QtyPromised_OnDate", null);
    /** Column name Fresh_QtyPromised_OnDate */
    public static final String COLUMNNAME_Fresh_QtyPromised_OnDate = "Fresh_QtyPromised_OnDate";

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
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "IsActive", null);
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
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_IsPurchased = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "IsPurchased", null);
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
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_IsSold = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "IsSold", null);
    /** Column name IsSold */
    public static final String COLUMNNAME_IsSold = "IsSold";

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
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, org.compiere.model.I_M_Product_Category> COLUMN_M_Product_Category_ID = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, org.compiere.model.I_M_Product_Category>(I_X_MRP_ProductInfo_V.class, "M_Product_Category_ID", org.compiere.model.I_M_Product_Category.class);
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
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, org.compiere.model.I_M_Product>(I_X_MRP_ProductInfo_V.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Zusage Lieferant.
	 * Vom Lieferanten per Webapplikation zugesagte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPMM_QtyPromised_OnDate (java.math.BigDecimal PMM_QtyPromised_OnDate);

	/**
	 * Get Zusage Lieferant.
	 * Vom Lieferanten per Webapplikation zugesagte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPMM_QtyPromised_OnDate();

    /** Column definition for PMM_QtyPromised_OnDate */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_PMM_QtyPromised_OnDate = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "PMM_QtyPromised_OnDate", null);
    /** Column name PMM_QtyPromised_OnDate */
    public static final String COLUMNNAME_PMM_QtyPromised_OnDate = "PMM_QtyPromised_OnDate";

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
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_ProductName = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "ProductName", null);
    /** Column name ProductName */
    public static final String COLUMNNAME_ProductName = "ProductName";

	/**
	 * Set Available Quantity.
	 * Available Quantity (On Hand - Reserved)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyAvailable (java.math.BigDecimal QtyAvailable);

	/**
	 * Get Available Quantity.
	 * Available Quantity (On Hand - Reserved)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyAvailable();

    /** Column definition for QtyAvailable */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_QtyAvailable = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "QtyAvailable", null);
    /** Column name QtyAvailable */
    public static final String COLUMNNAME_QtyAvailable = "QtyAvailable";

	/**
	 * Set Materialentnahme.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyMaterialentnahme (java.math.BigDecimal QtyMaterialentnahme);

	/**
	 * Get Materialentnahme.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyMaterialentnahme();

    /** Column definition for QtyMaterialentnahme */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_QtyMaterialentnahme = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "QtyMaterialentnahme", null);
    /** Column name QtyMaterialentnahme */
    public static final String COLUMNNAME_QtyMaterialentnahme = "QtyMaterialentnahme";

	/**
	 * Set Bestand.
	 * Bestand
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyOnHand (java.math.BigDecimal QtyOnHand);

	/**
	 * Get Bestand.
	 * Bestand
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOnHand();

    /** Column definition for QtyOnHand */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_QtyOnHand = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "QtyOnHand", null);
    /** Column name QtyOnHand */
    public static final String COLUMNNAME_QtyOnHand = "QtyOnHand";

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
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_QtyOrdered = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "QtyOrdered", null);
    /** Column name QtyOrdered */
    public static final String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set Bestellte Menge Tag.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyOrdered_OnDate (java.math.BigDecimal QtyOrdered_OnDate);

	/**
	 * Get Bestellte Menge Tag.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyOrdered_OnDate();

    /** Column definition for QtyOrdered_OnDate */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_QtyOrdered_OnDate = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "QtyOrdered_OnDate", null);
    /** Column name QtyOrdered_OnDate */
    public static final String COLUMNNAME_QtyOrdered_OnDate = "QtyOrdered_OnDate";

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
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_QtyPromised = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "QtyPromised", null);
    /** Column name QtyPromised */
    public static final String COLUMNNAME_QtyPromised = "QtyPromised";

	/**
	 * Set Reservierte Menge.
	 * Reservierte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyReserved (java.math.BigDecimal QtyReserved);

	/**
	 * Get Reservierte Menge.
	 * Reservierte Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyReserved();

    /** Column definition for QtyReserved */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_QtyReserved = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "QtyReserved", null);
    /** Column name QtyReserved */
    public static final String COLUMNNAME_QtyReserved = "QtyReserved";

	/**
	 * Set Reservierte Menge Tag.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQtyReserved_OnDate (java.math.BigDecimal QtyReserved_OnDate);

	/**
	 * Get Reservierte Menge Tag.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQtyReserved_OnDate();

    /** Column definition for QtyReserved_OnDate */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_QtyReserved_OnDate = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "QtyReserved_OnDate", null);
    /** Column name QtyReserved_OnDate */
    public static final String COLUMNNAME_QtyReserved_OnDate = "QtyReserved_OnDate";

	/**
	 * Set Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_X_MRP_ProductInfo_V, Object>(I_X_MRP_ProductInfo_V.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
