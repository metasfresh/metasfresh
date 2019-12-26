package de.metas.handlingunits.model;


/** Generated Interface for M_HU_PI_Item_Product
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_HU_PI_Item_Product 
{

    /** TableName=M_HU_PI_Item_Product */
    public static final String Table_Name = "M_HU_PI_Item_Product";

    /** AD_Table_ID=540508 */
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

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object>(I_M_HU_PI_Item_Product.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object>(I_M_HU_PI_Item_Product.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set TU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEAN_TU (java.lang.String EAN_TU);

	/**
	 * Get TU-EAN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEAN_TU();

    /** Column definition for EAN_TU */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_EAN_TU = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object>(I_M_HU_PI_Item_Product.class, "EAN_TU", null);
    /** Column name EAN_TU */
    public static final String COLUMNNAME_EAN_TU = "EAN_TU";

	/**
	 * Set GTIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGTIN (java.lang.String GTIN);

	/**
	 * Get GTIN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGTIN();

    /** Column definition for GTIN */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_GTIN = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object>(I_M_HU_PI_Item_Product.class, "GTIN", null);
    /** Column name GTIN */
    public static final String COLUMNNAME_GTIN = "GTIN";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object>(I_M_HU_PI_Item_Product.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Jedes Produkt erlauben.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAllowAnyProduct (boolean IsAllowAnyProduct);

	/**
	 * Get Jedes Produkt erlauben.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllowAnyProduct();

    /** Column definition for IsAllowAnyProduct */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_IsAllowAnyProduct = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object>(I_M_HU_PI_Item_Product.class, "IsAllowAnyProduct", null);
    /** Column name IsAllowAnyProduct */
    public static final String COLUMNNAME_IsAllowAnyProduct = "IsAllowAnyProduct";

	/**
	 * Set Standard-Produkt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDefaultForProduct (boolean IsDefaultForProduct);

	/**
	 * Get Standard-Produkt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDefaultForProduct();

    /** Column definition for IsDefaultForProduct */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_IsDefaultForProduct = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object>(I_M_HU_PI_Item_Product.class, "IsDefaultForProduct", null);
    /** Column name IsDefaultForProduct */
    public static final String COLUMNNAME_IsDefaultForProduct = "IsDefaultForProduct";

	/**
	 * Set Unbestimmte Kapazität.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInfiniteCapacity (boolean IsInfiniteCapacity);

	/**
	 * Get Unbestimmte Kapazität.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInfiniteCapacity();

    /** Column definition for IsInfiniteCapacity */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_IsInfiniteCapacity = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object>(I_M_HU_PI_Item_Product.class, "IsInfiniteCapacity", null);
    /** Column name IsInfiniteCapacity */
    public static final String COLUMNNAME_IsInfiniteCapacity = "IsInfiniteCapacity";

	/**
	 * Set LU Fallback-Verpackungscode.
	 * Wird benutzt wenn die Ausgabe eines LU Verpackungscodes erforderlich ist, aber in metasfresh keine HU erfasst wurde.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PackagingCode_LU_Fallback_ID (int M_HU_PackagingCode_LU_Fallback_ID);

	/**
	 * Get LU Fallback-Verpackungscode.
	 * Wird benutzt wenn die Ausgabe eines LU Verpackungscodes erforderlich ist, aber in metasfresh keine HU erfasst wurde.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PackagingCode_LU_Fallback_ID();

	public de.metas.handlingunits.model.I_M_HU_PackagingCode getM_HU_PackagingCode_LU_Fallback();

	public void setM_HU_PackagingCode_LU_Fallback(de.metas.handlingunits.model.I_M_HU_PackagingCode M_HU_PackagingCode_LU_Fallback);

    /** Column definition for M_HU_PackagingCode_LU_Fallback_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, de.metas.handlingunits.model.I_M_HU_PackagingCode> COLUMN_M_HU_PackagingCode_LU_Fallback_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, de.metas.handlingunits.model.I_M_HU_PackagingCode>(I_M_HU_PI_Item_Product.class, "M_HU_PackagingCode_LU_Fallback_ID", de.metas.handlingunits.model.I_M_HU_PackagingCode.class);
    /** Column name M_HU_PackagingCode_LU_Fallback_ID */
    public static final String COLUMNNAME_M_HU_PackagingCode_LU_Fallback_ID = "M_HU_PackagingCode_LU_Fallback_ID";

	/**
	 * Set Packvorschrift Position.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_Item_ID (int M_HU_PI_Item_ID);

	/**
	 * Get Packvorschrift Position.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_Item_ID();

	public de.metas.handlingunits.model.I_M_HU_PI_Item getM_HU_PI_Item();

	public void setM_HU_PI_Item(de.metas.handlingunits.model.I_M_HU_PI_Item M_HU_PI_Item);

    /** Column definition for M_HU_PI_Item_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, de.metas.handlingunits.model.I_M_HU_PI_Item> COLUMN_M_HU_PI_Item_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, de.metas.handlingunits.model.I_M_HU_PI_Item>(I_M_HU_PI_Item_Product.class, "M_HU_PI_Item_ID", de.metas.handlingunits.model.I_M_HU_PI_Item.class);
    /** Column name M_HU_PI_Item_ID */
    public static final String COLUMNNAME_M_HU_PI_Item_ID = "M_HU_PI_Item_ID";

	/**
	 * Set Packvorschrift.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID);

	/**
	 * Get Packvorschrift.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_HU_PI_Item_Product_ID();

    /** Column definition for M_HU_PI_Item_Product_ID */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_M_HU_PI_Item_Product_ID = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object>(I_M_HU_PI_Item_Product.class, "M_HU_PI_Item_Product_ID", null);
    /** Column name M_HU_PI_Item_Product_ID */
    public static final String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

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
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object>(I_M_HU_PI_Item_Product.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object>(I_M_HU_PI_Item_Product.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_UPC = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object>(I_M_HU_PI_Item_Product.class, "UPC", null);
    /** Column name UPC */
    public static final String COLUMNNAME_UPC = "UPC";

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
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object>(I_M_HU_PI_Item_Product.class, "Updated", null);
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

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Gültig ab.
	 * Gültig ab inklusiv (erster Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Gültig ab.
	 * Gültig ab inklusiv (erster Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidFrom();

    /** Column definition for ValidFrom */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object>(I_M_HU_PI_Item_Product.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Gültig bis.
	 * Gültig bis inklusiv (letzter Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValidTo (java.sql.Timestamp ValidTo);

	/**
	 * Get Gültig bis.
	 * Gültig bis inklusiv (letzter Tag)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidTo();

    /** Column definition for ValidTo */
    public static final org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object> COLUMN_ValidTo = new org.adempiere.model.ModelColumn<I_M_HU_PI_Item_Product, Object>(I_M_HU_PI_Item_Product.class, "ValidTo", null);
    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";
}
