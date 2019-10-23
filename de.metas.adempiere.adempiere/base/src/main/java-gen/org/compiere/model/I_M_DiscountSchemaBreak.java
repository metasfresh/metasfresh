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
	 * Set Preissystem.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBase_PricingSystem_ID (int Base_PricingSystem_ID);

	/**
	 * Get Preissystem.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBase_PricingSystem_ID();

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
	 * Set Mengenstufe.
	 * Mindestmenge ab der die Kondition gilt
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setBreakValue (java.math.BigDecimal BreakValue);

	/**
	 * Get Mengenstufe.
	 * Mindestmenge ab der die Kondition gilt
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

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Zahlungsbedingung.
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_PaymentTerm_ID();

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
	 * Set Gültig.
	 * Element ist gültig
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsValid (boolean IsValid);

	/**
	 * Get Gültig.
	 * Element ist gültig
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isValid();

    /** Column definition for IsValid */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object> COLUMN_IsValid = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object>(I_M_DiscountSchemaBreak.class, "IsValid", null);
    /** Column name IsValid */
    public static final String COLUMNNAME_IsValid = "IsValid";

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

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Hersteller.
	 * Hersteller des Produktes
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setManufacturer_ID (int Manufacturer_ID);

	/**
	 * Get Hersteller.
	 * Hersteller des Produktes
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getManufacturer_ID();

    /** Column name Manufacturer_ID */
    public static final String COLUMNNAME_Manufacturer_ID = "Manufacturer_ID";

	/**
	 * Set Not valid reason.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNotValidReason (java.lang.String NotValidReason);

	/**
	 * Get Not valid reason.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getNotValidReason();

    /** Column definition for NotValidReason */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object> COLUMN_NotValidReason = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object>(I_M_DiscountSchemaBreak.class, "NotValidReason", null);
    /** Column name NotValidReason */
    public static final String COLUMNNAME_NotValidReason = "NotValidReason";

	/**
	 * Set Skonto %.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPaymentDiscount (java.math.BigDecimal PaymentDiscount);

	/**
	 * Get Skonto %.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPaymentDiscount();

    /** Column definition for PaymentDiscount */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object> COLUMN_PaymentDiscount = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object>(I_M_DiscountSchemaBreak.class, "PaymentDiscount", null);
    /** Column name PaymentDiscount */
    public static final String COLUMNNAME_PaymentDiscount = "PaymentDiscount";

	/**
	 * Set Preisgrundlage.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceBase (java.lang.String PriceBase);

	/**
	 * Get Preisgrundlage.
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
	 * Set Festpreis.
	 * Festpreis, ohne ggf. zusätzliche Rabatte
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceStdFixed (java.math.BigDecimal PriceStdFixed);

	/**
	 * Get Festpreis.
	 * Festpreis, ohne ggf. zusätzliche Rabatte
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPriceStdFixed();

    /** Column definition for PriceStdFixed */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object> COLUMN_PriceStdFixed = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object>(I_M_DiscountSchemaBreak.class, "PriceStdFixed", null);
    /** Column name PriceStdFixed */
    public static final String COLUMNNAME_PriceStdFixed = "PriceStdFixed";

	/**
	 * Set Preisaufschlag.
	 * Aufschlag auf den Preis, der aus dem Preissystem resultieren würde
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPricingSystemSurchargeAmt (java.math.BigDecimal PricingSystemSurchargeAmt);

	/**
	 * Get Preisaufschlag.
	 * Aufschlag auf den Preis, der aus dem Preissystem resultieren würde
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getPricingSystemSurchargeAmt();

    /** Column definition for PricingSystemSurchargeAmt */
    public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object> COLUMN_PricingSystemSurchargeAmt = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, Object>(I_M_DiscountSchemaBreak.class, "PricingSystemSurchargeAmt", null);
    /** Column name PricingSystemSurchargeAmt */
    public static final String COLUMNNAME_PricingSystemSurchargeAmt = "PricingSystemSurchargeAmt";

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

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
