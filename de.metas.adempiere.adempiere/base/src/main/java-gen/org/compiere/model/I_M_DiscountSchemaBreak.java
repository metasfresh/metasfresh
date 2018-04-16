package org.compiere.model;


/** Generated Interface for M_DiscountSchemaBreak
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_DiscountSchemaBreak 
{

    /** TableName=M_DiscountSchemaBreak */
    public static final String Table_Name = "M_DiscountSchemaBreak";

    /** AD_Table_ID=476 */
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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_AD_Client>(I_M_DiscountSchemaBreak.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_AD_Org>(I_M_DiscountSchemaBreak.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Base_PricingSystem_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBase_PricingSystem_ID (int Base_PricingSystem_ID);

	/**
	 * Get Base_PricingSystem_ID.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBase_PricingSystem_ID();

	public org.compiere.model.I_M_PricingSystem getBase_PricingSystem();

	public void setBase_PricingSystem(org.compiere.model.I_M_PricingSystem Base_PricingSystem);

    /** Column definition for Base_PricingSystem_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_M_PricingSystem> COLUMN_Base_PricingSystem_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_M_PricingSystem>(I_M_DiscountSchemaBreak.class, "Base_PricingSystem_ID", org.compiere.model.I_M_PricingSystem.class);
    /** Column name Base_PricingSystem_ID */
    public static final String COLUMNNAME_Base_PricingSystem_ID = "Base_PricingSystem_ID";

	/**
	 * Set Break Discount %.
	 * Trade Discount in Percent for the break level
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setBreakDiscount (java.math.BigDecimal BreakDiscount);

	/**
	 * Get Break Discount %.
	 * Trade Discount in Percent for the break level
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getBreakDiscount();

    /** Column definition for BreakDiscount */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object> COLUMN_BreakDiscount = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object>(I_M_DiscountSchemaBreak.class, "BreakDiscount", null);
    /** Column name BreakDiscount */
    public static final String COLUMNNAME_BreakDiscount = "BreakDiscount";

	/**
	 * Set Break Value.
	 * Low Value of trade discount break level
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setBreakValue (java.math.BigDecimal BreakValue);

	/**
	 * Get Break Value.
	 * Low Value of trade discount break level
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getBreakValue();

    /** Column definition for BreakValue */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object> COLUMN_BreakValue = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object>(I_M_DiscountSchemaBreak.class, "BreakValue", null);
    /** Column name BreakValue */
    public static final String COLUMNNAME_BreakValue = "BreakValue";

	/**
	 * Set Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_PaymentTerm_ID();

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm();

	public void setC_PaymentTerm(org.compiere.model.I_C_PaymentTerm C_PaymentTerm);

    /** Column definition for C_PaymentTerm_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_C_PaymentTerm> COLUMN_C_PaymentTerm_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_C_PaymentTerm>(I_M_DiscountSchemaBreak.class, "C_PaymentTerm_ID", org.compiere.model.I_C_PaymentTerm.class);
    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object>(I_M_DiscountSchemaBreak.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_AD_User>(I_M_DiscountSchemaBreak.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object>(I_M_DiscountSchemaBreak.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Fester Rabatt.
	 * Use flat discount defined on Business Partner Level
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsBPartnerFlatDiscount (boolean IsBPartnerFlatDiscount);

	/**
	 * Get Fester Rabatt.
	 * Use flat discount defined on Business Partner Level
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isBPartnerFlatDiscount();

    /** Column definition for IsBPartnerFlatDiscount */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object> COLUMN_IsBPartnerFlatDiscount = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object>(I_M_DiscountSchemaBreak.class, "IsBPartnerFlatDiscount", null);
    /** Column name IsBPartnerFlatDiscount */
    public static final String COLUMNNAME_IsBPartnerFlatDiscount = "IsBPartnerFlatDiscount";

	/**
	 * Set IsPriceOverride.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPriceOverride (boolean IsPriceOverride);

	/**
	 * Get IsPriceOverride.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPriceOverride();

    /** Column definition for IsPriceOverride */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object> COLUMN_IsPriceOverride = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object>(I_M_DiscountSchemaBreak.class, "IsPriceOverride", null);
    /** Column name IsPriceOverride */
    public static final String COLUMNNAME_IsPriceOverride = "IsPriceOverride";

	/**
	 * Set Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Attribute_ID (int M_Attribute_ID);

	/**
	 * Get Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_Attribute_ID();

	public org.compiere.model.I_M_Attribute getM_Attribute();

	public void setM_Attribute(org.compiere.model.I_M_Attribute M_Attribute);

    /** Column definition for M_Attribute_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_M_Attribute> COLUMN_M_Attribute_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_M_Attribute>(I_M_DiscountSchemaBreak.class, "M_Attribute_ID", org.compiere.model.I_M_Attribute.class);
    /** Column name M_Attribute_ID */
    public static final String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

	/**
	 * Set Merkmals-Wert.
	 * Product Attribute Value
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_AttributeValue_ID (int M_AttributeValue_ID);

	/**
	 * Get Merkmals-Wert.
	 * Product Attribute Value
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_AttributeValue_ID();

	public org.compiere.model.I_M_AttributeValue getM_AttributeValue();

	public void setM_AttributeValue(org.compiere.model.I_M_AttributeValue M_AttributeValue);

    /** Column definition for M_AttributeValue_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_M_AttributeValue> COLUMN_M_AttributeValue_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_M_AttributeValue>(I_M_DiscountSchemaBreak.class, "M_AttributeValue_ID", org.compiere.model.I_M_AttributeValue.class);
    /** Column name M_AttributeValue_ID */
    public static final String COLUMNNAME_M_AttributeValue_ID = "M_AttributeValue_ID";

	/**
	 * Set Rabatt Schema.
	 * Schema um den prozentualen Rabatt zu berechnen
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID);

	/**
	 * Get Rabatt Schema.
	 * Schema um den prozentualen Rabatt zu berechnen
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_DiscountSchema_ID();

	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema();

	public void setM_DiscountSchema(org.compiere.model.I_M_DiscountSchema M_DiscountSchema);

    /** Column definition for M_DiscountSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_M_DiscountSchema> COLUMN_M_DiscountSchema_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_M_DiscountSchema>(I_M_DiscountSchemaBreak.class, "M_DiscountSchema_ID", org.compiere.model.I_M_DiscountSchema.class);
    /** Column name M_DiscountSchema_ID */
    public static final String COLUMNNAME_M_DiscountSchema_ID = "M_DiscountSchema_ID";

	/**
	 * Set Discount Schema Break.
	 * Trade Discount Break
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_DiscountSchemaBreak_ID (int M_DiscountSchemaBreak_ID);

	/**
	 * Get Discount Schema Break.
	 * Trade Discount Break
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_DiscountSchemaBreak_ID();

    /** Column definition for M_DiscountSchemaBreak_ID */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object> COLUMN_M_DiscountSchemaBreak_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object>(I_M_DiscountSchemaBreak.class, "M_DiscountSchemaBreak_ID", null);
    /** Column name M_DiscountSchemaBreak_ID */
    public static final String COLUMNNAME_M_DiscountSchemaBreak_ID = "M_DiscountSchemaBreak_ID";

	/**
	 * Set Produkt Kategorie.
	 * Kategorie eines Produktes
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Produkt Kategorie.
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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_M_Product_Category> COLUMN_M_Product_Category_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_M_Product_Category>(I_M_DiscountSchemaBreak.class, "M_Product_Category_ID", org.compiere.model.I_M_Product_Category.class);
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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_M_Product>(I_M_DiscountSchemaBreak.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set PriceBase.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceBase (java.lang.String PriceBase);

	/**
	 * Get PriceBase.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPriceBase();

    /** Column definition for PriceBase */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object> COLUMN_PriceBase = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object>(I_M_DiscountSchemaBreak.class, "PriceBase", null);
    /** Column name PriceBase */
    public static final String COLUMNNAME_PriceBase = "PriceBase";

	/**
	 * Set Standardpreis.
	 * Standardpreis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceStd (java.math.BigDecimal PriceStd);

	/**
	 * Get Standardpreis.
	 * Standardpreis
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceStd();

    /** Column definition for PriceStd */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object> COLUMN_PriceStd = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object>(I_M_DiscountSchemaBreak.class, "PriceStd", null);
    /** Column name PriceStd */
    public static final String COLUMNNAME_PriceStd = "PriceStd";

	/**
	 * Set QualityIssuePercentage.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQualityIssuePercentage (java.math.BigDecimal QualityIssuePercentage);

	/**
	 * Get QualityIssuePercentage.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQualityIssuePercentage();

    /** Column definition for QualityIssuePercentage */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object> COLUMN_QualityIssuePercentage = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object>(I_M_DiscountSchemaBreak.class, "QualityIssuePercentage", null);
    /** Column name QualityIssuePercentage */
    public static final String COLUMNNAME_QualityIssuePercentage = "QualityIssuePercentage";

	/**
	 * Set Reihenfolge.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object>(I_M_DiscountSchemaBreak.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Aufschlag auf Standardpreis.
	 * Amount added to a price as a surcharge
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStd_AddAmt (java.math.BigDecimal Std_AddAmt);

	/**
	 * Get Aufschlag auf Standardpreis.
	 * Amount added to a price as a surcharge
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getStd_AddAmt();

    /** Column definition for Std_AddAmt */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object> COLUMN_Std_AddAmt = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object>(I_M_DiscountSchemaBreak.class, "Std_AddAmt", null);
    /** Column name Std_AddAmt */
    public static final String COLUMNNAME_Std_AddAmt = "Std_AddAmt";

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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object>(I_M_DiscountSchemaBreak.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_AD_User>(I_M_DiscountSchemaBreak.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
