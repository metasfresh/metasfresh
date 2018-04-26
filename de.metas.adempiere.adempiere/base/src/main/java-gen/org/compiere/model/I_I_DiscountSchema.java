package org.compiere.model;


/** Generated Interface for I_DiscountSchema
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_I_DiscountSchema 
{

    /** TableName=I_DiscountSchema */
    public static final String Table_Name = "I_DiscountSchema";

    /** AD_Table_ID=540963 */
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
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_AD_Client>(I_I_DiscountSchema.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_AD_Org>(I_I_DiscountSchema.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Base_PricingSystem_ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBase_PricingSystem_ID (int Base_PricingSystem_ID);

	/**
	 * Get Base_PricingSystem_ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBase_PricingSystem_ID();

	public org.compiere.model.I_M_PricingSystem getBase_PricingSystem();

	public void setBase_PricingSystem(org.compiere.model.I_M_PricingSystem Base_PricingSystem);

    /** Column definition for Base_PricingSystem_ID */
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_M_PricingSystem> COLUMN_Base_PricingSystem_ID = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_M_PricingSystem>(I_I_DiscountSchema.class, "Base_PricingSystem_ID", org.compiere.model.I_M_PricingSystem.class);
    /** Column name Base_PricingSystem_ID */
    public static final String COLUMNNAME_Base_PricingSystem_ID = "Base_PricingSystem_ID";

	/**
	 * Set Base_PricingSystem_Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBase_PricingSystem_Value (java.lang.String Base_PricingSystem_Value);

	/**
	 * Get Base_PricingSystem_Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBase_PricingSystem_Value();

    /** Column definition for Base_PricingSystem_Value */
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object> COLUMN_Base_PricingSystem_Value = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object>(I_I_DiscountSchema.class, "Base_PricingSystem_Value", null);
    /** Column name Base_PricingSystem_Value */
    public static final String COLUMNNAME_Base_PricingSystem_Value = "Base_PricingSystem_Value";

	/**
	 * Set Geschäftspartner-Schlüssel.
	 * Suchschlüssel für den Geschäftspartner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBPartner_Value (java.lang.String BPartner_Value);

	/**
	 * Get Geschäftspartner-Schlüssel.
	 * Suchschlüssel für den Geschäftspartner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBPartner_Value();

    /** Column definition for BPartner_Value */
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object> COLUMN_BPartner_Value = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object>(I_I_DiscountSchema.class, "BPartner_Value", null);
    /** Column name BPartner_Value */
    public static final String COLUMNNAME_BPartner_Value = "BPartner_Value";

	/**
	 * Set Break Discount %.
	 * Trade Discount in Percent for the break level
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBreakDiscount (java.lang.String BreakDiscount);

	/**
	 * Get Break Discount %.
	 * Trade Discount in Percent for the break level
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBreakDiscount();

    /** Column definition for BreakDiscount */
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object> COLUMN_BreakDiscount = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object>(I_I_DiscountSchema.class, "BreakDiscount", null);
    /** Column name BreakDiscount */
    public static final String COLUMNNAME_BreakDiscount = "BreakDiscount";

	/**
	 * Set Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Geschäftspartner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_ID();

	public org.compiere.model.I_C_BPartner getC_BPartner();

	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner);

    /** Column definition for C_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_C_BPartner>(I_I_DiscountSchema.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

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
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_C_PaymentTerm> COLUMN_C_PaymentTerm_ID = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_C_PaymentTerm>(I_I_DiscountSchema.class, "C_PaymentTerm_ID", org.compiere.model.I_C_PaymentTerm.class);
    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

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
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object>(I_I_DiscountSchema.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_AD_User>(I_I_DiscountSchema.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Rabatt %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDiscount (java.lang.String Discount);

	/**
	 * Get Rabatt %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDiscount();

    /** Column definition for Discount */
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object> COLUMN_Discount = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object>(I_I_DiscountSchema.class, "Discount", null);
    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Discount Schema Import.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_DiscountSchema_ID (int I_DiscountSchema_ID);

	/**
	 * Get Discount Schema Import.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getI_DiscountSchema_ID();

    /** Column definition for I_DiscountSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object> COLUMN_I_DiscountSchema_ID = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object>(I_I_DiscountSchema.class, "I_DiscountSchema_ID", null);
    /** Column name I_DiscountSchema_ID */
    public static final String COLUMNNAME_I_DiscountSchema_ID = "I_DiscountSchema_ID";

	/**
	 * Set Import-Fehlermeldung.
	 * Meldungen, die durch den Importprozess generiert wurden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg);

	/**
	 * Get Import-Fehlermeldung.
	 * Meldungen, die durch den Importprozess generiert wurden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_ErrorMsg();

    /** Column definition for I_ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object> COLUMN_I_ErrorMsg = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object>(I_I_DiscountSchema.class, "I_ErrorMsg", null);
    /** Column name I_ErrorMsg */
    public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Importiert.
	 * Ist dieser Import verarbeitet worden?
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_IsImported (java.lang.String I_IsImported);

	/**
	 * Get Importiert.
	 * Ist dieser Import verarbeitet worden?
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_IsImported();

    /** Column definition for I_IsImported */
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object> COLUMN_I_IsImported = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object>(I_I_DiscountSchema.class, "I_IsImported", null);
    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

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
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object>(I_I_DiscountSchema.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Rabatt Schema.
	 * Schema um den prozentualen Rabatt zu berechnen
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_DiscountSchema_ID (int M_DiscountSchema_ID);

	/**
	 * Get Rabatt Schema.
	 * Schema um den prozentualen Rabatt zu berechnen
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_DiscountSchema_ID();

	public org.compiere.model.I_M_DiscountSchema getM_DiscountSchema();

	public void setM_DiscountSchema(org.compiere.model.I_M_DiscountSchema M_DiscountSchema);

    /** Column definition for M_DiscountSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_M_DiscountSchema> COLUMN_M_DiscountSchema_ID = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_M_DiscountSchema>(I_I_DiscountSchema.class, "M_DiscountSchema_ID", org.compiere.model.I_M_DiscountSchema.class);
    /** Column name M_DiscountSchema_ID */
    public static final String COLUMNNAME_M_DiscountSchema_ID = "M_DiscountSchema_ID";

	/**
	 * Set Discount Schema Break.
	 * Trade Discount Break
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setM_DiscountSchemaBreak_ID (int M_DiscountSchemaBreak_ID);

	/**
	 * Get Discount Schema Break.
	 * Trade Discount Break
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getM_DiscountSchemaBreak_ID();

	public org.compiere.model.I_M_DiscountSchemaBreak getM_DiscountSchemaBreak();

	public void setM_DiscountSchemaBreak(org.compiere.model.I_M_DiscountSchemaBreak M_DiscountSchemaBreak);

    /** Column definition for M_DiscountSchemaBreak_ID */
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_M_DiscountSchemaBreak> COLUMN_M_DiscountSchemaBreak_ID = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_M_DiscountSchemaBreak>(I_I_DiscountSchema.class, "M_DiscountSchemaBreak_ID", org.compiere.model.I_M_DiscountSchemaBreak.class);
    /** Column name M_DiscountSchemaBreak_ID */
    public static final String COLUMNNAME_M_DiscountSchemaBreak_ID = "M_DiscountSchemaBreak_ID";

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
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_M_Product>(I_I_DiscountSchema.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Zahlungskonditions-Schlüssel.
	 * Suchschlüssel für die Zahlungskondition
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPaymentTermValue (java.lang.String PaymentTermValue);

	/**
	 * Get Zahlungskonditions-Schlüssel.
	 * Suchschlüssel für die Zahlungskondition
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPaymentTermValue();

    /** Column definition for PaymentTermValue */
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object> COLUMN_PaymentTermValue = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object>(I_I_DiscountSchema.class, "PaymentTermValue", null);
    /** Column name PaymentTermValue */
    public static final String COLUMNNAME_PaymentTermValue = "PaymentTermValue";

	/**
	 * Set Standardpreis.
	 * Standardpreis
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPriceStd (java.lang.String PriceStd);

	/**
	 * Get Standardpreis.
	 * Standardpreis
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPriceStd();

    /** Column definition for PriceStd */
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object> COLUMN_PriceStd = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object>(I_I_DiscountSchema.class, "PriceStd", null);
    /** Column name PriceStd */
    public static final String COLUMNNAME_PriceStd = "PriceStd";

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
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object>(I_I_DiscountSchema.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

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
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object> COLUMN_ProductValue = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object>(I_I_DiscountSchema.class, "ProductValue", null);
    /** Column name ProductValue */
    public static final String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQty (java.math.BigDecimal Qty);

	/**
	 * Get Menge.
	 * Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty();

    /** Column definition for Qty */
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object> COLUMN_Qty = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object>(I_I_DiscountSchema.class, "Qty", null);
    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

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
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object> COLUMN_Std_AddAmt = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object>(I_I_DiscountSchema.class, "Std_AddAmt", null);
    /** Column name Std_AddAmt */
    public static final String COLUMNNAME_Std_AddAmt = "Std_AddAmt";

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
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, Object>(I_I_DiscountSchema.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_I_DiscountSchema, org.compiere.model.I_AD_User>(I_I_DiscountSchema.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
