package org.compiere.model;


/** Generated Interface for M_ProductPrice
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_ProductPrice 
{

    /** TableName=M_ProductPrice */
    public static final String Table_Name = "M_ProductPrice";

    /** AD_Table_ID=251 */
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
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_AD_Client>(I_M_ProductPrice.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_AD_Org>(I_M_ProductPrice.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Steuerkategorie.
	 * Steuerkategorie
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/**
	 * Get Steuerkategorie.
	 * Steuerkategorie
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_TaxCategory_ID();

	public org.compiere.model.I_C_TaxCategory getC_TaxCategory();

	public void setC_TaxCategory(org.compiere.model.I_C_TaxCategory C_TaxCategory);

    /** Column definition for C_TaxCategory_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_C_TaxCategory> COLUMN_C_TaxCategory_ID = new org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_C_TaxCategory>(I_M_ProductPrice.class, "C_TaxCategory_ID", org.compiere.model.I_C_TaxCategory.class);
    /** Column name C_TaxCategory_ID */
    public static final String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_C_UOM>(I_M_ProductPrice.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_AD_User>(I_M_ProductPrice.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set isAttributeDependant.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAttributeDependant (boolean IsAttributeDependant);

	/**
	 * Get isAttributeDependant.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAttributeDependant();

    /** Column definition for IsAttributeDependant */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_IsAttributeDependant = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "IsAttributeDependant", null);
    /** Column name IsAttributeDependant */
    public static final String COLUMNNAME_IsAttributeDependant = "IsAttributeDependant";

	/**
	 * Set Standard.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDefault (boolean IsDefault);

	/**
	 * Get Standard.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDefault();

    /** Column definition for IsDefault */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_IsDefault = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "IsDefault", null);
    /** Column name IsDefault */
    public static final String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Saison Fix Preis.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSeasonFixedPrice (boolean IsSeasonFixedPrice);

	/**
	 * Get Saison Fix Preis.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSeasonFixedPrice();

    /** Column definition for IsSeasonFixedPrice */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_IsSeasonFixedPrice = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "IsSeasonFixedPrice", null);
    /** Column name IsSeasonFixedPrice */
    public static final String COLUMNNAME_IsSeasonFixedPrice = "IsSeasonFixedPrice";

	/**
	 * Set Ausprägung Merkmals-Satz.
	 * Instanz des Merkmals-Satzes zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Ausprägung Merkmals-Satz.
	 * Instanz des Merkmals-Satzes zum Produkt
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeSetInstance_ID();

	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

    /** Column definition for M_AttributeSetInstance_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_M_AttributeSetInstance>(I_M_ProductPrice.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
    /** Column name M_AttributeSetInstance_ID */
    public static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Discount Pricelist.
	 * Line of the pricelist trade discount schema
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_DiscountSchemaLine_ID (int M_DiscountSchemaLine_ID);

	/**
	 * Get Discount Pricelist.
	 * Line of the pricelist trade discount schema
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_DiscountSchemaLine_ID();

	public org.compiere.model.I_M_DiscountSchemaLine getM_DiscountSchemaLine();

	public void setM_DiscountSchemaLine(org.compiere.model.I_M_DiscountSchemaLine M_DiscountSchemaLine);

    /** Column definition for M_DiscountSchemaLine_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_M_DiscountSchemaLine> COLUMN_M_DiscountSchemaLine_ID = new org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_M_DiscountSchemaLine>(I_M_ProductPrice.class, "M_DiscountSchemaLine_ID", org.compiere.model.I_M_DiscountSchemaLine.class);
    /** Column name M_DiscountSchemaLine_ID */
    public static final String COLUMNNAME_M_DiscountSchemaLine_ID = "M_DiscountSchemaLine_ID";

	/**
	 * Set Version Preisliste.
	 * Identifies a unique instance of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_PriceList_Version_ID (int M_PriceList_Version_ID);

	/**
	 * Get Version Preisliste.
	 * Identifies a unique instance of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_PriceList_Version_ID();

	public org.compiere.model.I_M_PriceList_Version getM_PriceList_Version();

	public void setM_PriceList_Version(org.compiere.model.I_M_PriceList_Version M_PriceList_Version);

    /** Column definition for M_PriceList_Version_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_M_PriceList_Version> COLUMN_M_PriceList_Version_ID = new org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_M_PriceList_Version>(I_M_ProductPrice.class, "M_PriceList_Version_ID", org.compiere.model.I_M_PriceList_Version.class);
    /** Column name M_PriceList_Version_ID */
    public static final String COLUMNNAME_M_PriceList_Version_ID = "M_PriceList_Version_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_M_Product>(I_M_ProductPrice.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Base product price.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_ProductPrice_Base_ID (int M_ProductPrice_Base_ID);

	/**
	 * Get Base product price.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_ProductPrice_Base_ID();

	public org.compiere.model.I_M_ProductPrice getM_ProductPrice_Base();

	public void setM_ProductPrice_Base(org.compiere.model.I_M_ProductPrice M_ProductPrice_Base);

    /** Column definition for M_ProductPrice_Base_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_M_ProductPrice> COLUMN_M_ProductPrice_Base_ID = new org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_M_ProductPrice>(I_M_ProductPrice.class, "M_ProductPrice_Base_ID", org.compiere.model.I_M_ProductPrice.class);
    /** Column name M_ProductPrice_Base_ID */
    public static final String COLUMNNAME_M_ProductPrice_Base_ID = "M_ProductPrice_Base_ID";

	/**
	 * Set M_ProductPrice_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_ProductPrice_ID (int M_ProductPrice_ID);

	/**
	 * Get M_ProductPrice_ID.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_ProductPrice_ID();

    /** Column definition for M_ProductPrice_ID */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_M_ProductPrice_ID = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "M_ProductPrice_ID", null);
    /** Column name M_ProductPrice_ID */
    public static final String COLUMNNAME_M_ProductPrice_ID = "M_ProductPrice_ID";

	/**
	 * Set Matching order.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMatchSeqNo (int MatchSeqNo);

	/**
	 * Get Matching order.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMatchSeqNo();

    /** Column definition for MatchSeqNo */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_MatchSeqNo = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "MatchSeqNo", null);
    /** Column name MatchSeqNo */
    public static final String COLUMNNAME_MatchSeqNo = "MatchSeqNo";

	/**
	 * Set Mindestpreis.
	 * Lowest price for a product
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPriceLimit (java.math.BigDecimal PriceLimit);

	/**
	 * Get Mindestpreis.
	 * Lowest price for a product
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceLimit();

    /** Column definition for PriceLimit */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_PriceLimit = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "PriceLimit", null);
    /** Column name PriceLimit */
    public static final String COLUMNNAME_PriceLimit = "PriceLimit";

	/**
	 * Set Auszeichnungspreis.
	 * Auszeichnungspreis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPriceList (java.math.BigDecimal PriceList);

	/**
	 * Get Auszeichnungspreis.
	 * Auszeichnungspreis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceList();

    /** Column definition for PriceList */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_PriceList = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "PriceList", null);
    /** Column name PriceList */
    public static final String COLUMNNAME_PriceList = "PriceList";

	/**
	 * Set Standardpreis.
	 * Standard Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPriceStd (java.math.BigDecimal PriceStd);

	/**
	 * Get Standardpreis.
	 * Standard Price
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceStd();

    /** Column definition for PriceStd */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_PriceStd = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "PriceStd", null);
    /** Column name PriceStd */
    public static final String COLUMNNAME_PriceStd = "PriceStd";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

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
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_ProductPrice, org.compiere.model.I_AD_User>(I_M_ProductPrice.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Staffelpreis.
	 * Legt fest, ob zu diesem Artikel Staffelpreise gehören.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setUseScalePrice (boolean UseScalePrice);

	/**
	 * Get Staffelpreis.
	 * Legt fest, ob zu diesem Artikel Staffelpreise gehören.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isUseScalePrice();

    /** Column definition for UseScalePrice */
    public static final org.adempiere.model.ModelColumn<I_M_ProductPrice, Object> COLUMN_UseScalePrice = new org.adempiere.model.ModelColumn<I_M_ProductPrice, Object>(I_M_ProductPrice.class, "UseScalePrice", null);
    /** Column name UseScalePrice */
    public static final String COLUMNNAME_UseScalePrice = "UseScalePrice";
}
