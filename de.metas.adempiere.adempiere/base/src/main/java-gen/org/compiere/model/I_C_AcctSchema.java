package org.compiere.model;


/** Generated Interface for C_AcctSchema
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_AcctSchema 
{

    /** TableName=C_AcctSchema */
    public static final String Table_Name = "C_AcctSchema";

    /** AD_Table_ID=265 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

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
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_AcctSchema, org.compiere.model.I_AD_Client>(I_C_AcctSchema.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_AcctSchema, org.compiere.model.I_AD_Org>(I_C_AcctSchema.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Nur für Organisation.
	 * Kontrierung nur für die angegebene Organisation
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_OrgOnly_ID (int AD_OrgOnly_ID);

	/**
	 * Get Nur für Organisation.
	 * Kontrierung nur für die angegebene Organisation
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_OrgOnly_ID();

	public org.compiere.model.I_AD_Org getAD_OrgOnly();

	public void setAD_OrgOnly(org.compiere.model.I_AD_Org AD_OrgOnly);

    /** Column definition for AD_OrgOnly_ID */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, org.compiere.model.I_AD_Org> COLUMN_AD_OrgOnly_ID = new org.adempiere.model.ModelColumn<I_C_AcctSchema, org.compiere.model.I_AD_Org>(I_C_AcctSchema.class, "AD_OrgOnly_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_OrgOnly_ID */
    public static final String COLUMNNAME_AD_OrgOnly_ID = "AD_OrgOnly_ID";

	/**
	 * Set Automatische Periodenkontrolle.
	 * If selected, the periods are automatically opened and closed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAutoPeriodControl (boolean AutoPeriodControl);

	/**
	 * Get Automatische Periodenkontrolle.
	 * If selected, the periods are automatically opened and closed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAutoPeriodControl();

    /** Column definition for AutoPeriodControl */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_AutoPeriodControl = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "AutoPeriodControl", null);
    /** Column name AutoPeriodControl */
    public static final String COLUMNNAME_AutoPeriodControl = "AutoPeriodControl";

	/**
	 * Set Buchführungs-Schema.
	 * Rules for accounting
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Buchführungs-Schema.
	 * Rules for accounting
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_AcctSchema_ID();

    /** Column definition for C_AcctSchema_ID */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_C_AcctSchema_ID = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "C_AcctSchema_ID", null);
    /** Column name C_AcctSchema_ID */
    public static final String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_AcctSchema, org.compiere.model.I_C_Currency>(I_C_AcctSchema.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Periode.
	 * Period of the Calendar
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Periode.
	 * Period of the Calendar
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period();

	public void setC_Period(org.compiere.model.I_C_Period C_Period);

    /** Column definition for C_Period_ID */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, org.compiere.model.I_C_Period> COLUMN_C_Period_ID = new org.adempiere.model.ModelColumn<I_C_AcctSchema, org.compiere.model.I_C_Period>(I_C_AcctSchema.class, "C_Period_ID", org.compiere.model.I_C_Period.class);
    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/**
	 * Set Art Reservierung.
	 * Erstelle Reservierungen für Budgetkontrolle
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCommitmentType (java.lang.String CommitmentType);

	/**
	 * Get Art Reservierung.
	 * Erstelle Reservierungen für Budgetkontrolle
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCommitmentType();

    /** Column definition for CommitmentType */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_CommitmentType = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "CommitmentType", null);
    /** Column name CommitmentType */
    public static final String COLUMNNAME_CommitmentType = "CommitmentType";

	/**
	 * Set Kostenrechnungsstufe.
	 * The lowest level to accumulate Costing Information
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCostingLevel (java.lang.String CostingLevel);

	/**
	 * Get Kostenrechnungsstufe.
	 * The lowest level to accumulate Costing Information
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCostingLevel();

    /** Column definition for CostingLevel */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_CostingLevel = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "CostingLevel", null);
    /** Column name CostingLevel */
    public static final String COLUMNNAME_CostingLevel = "CostingLevel";

	/**
	 * Set Kostenrechnungsmethode.
	 * Indicates how Costs will be calculated
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCostingMethod (java.lang.String CostingMethod);

	/**
	 * Get Kostenrechnungsmethode.
	 * Indicates how Costs will be calculated
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCostingMethod();

    /** Column definition for CostingMethod */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_CostingMethod = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "CostingMethod", null);
    /** Column name CostingMethod */
    public static final String COLUMNNAME_CostingMethod = "CostingMethod";

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
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_AcctSchema, org.compiere.model.I_AD_User>(I_C_AcctSchema.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set GAAP.
	 * Generally Accepted Accounting Principles
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setGAAP (java.lang.String GAAP);

	/**
	 * Get GAAP.
	 * Generally Accepted Accounting Principles
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getGAAP();

    /** Column definition for GAAP */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_GAAP = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "GAAP", null);
    /** Column name GAAP */
    public static final String COLUMNNAME_GAAP = "GAAP";

	/**
	 * Set Use Account Alias.
	 * Ability to select (partial) account combinations by an Alias
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHasAlias (boolean HasAlias);

	/**
	 * Get Use Account Alias.
	 * Ability to select (partial) account combinations by an Alias
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isHasAlias();

    /** Column definition for HasAlias */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_HasAlias = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "HasAlias", null);
    /** Column name HasAlias */
    public static final String COLUMNNAME_HasAlias = "HasAlias";

	/**
	 * Set Use Account Combination Control.
	 * Combination of account elements are checked
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHasCombination (boolean HasCombination);

	/**
	 * Get Use Account Combination Control.
	 * Combination of account elements are checked
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isHasCombination();

    /** Column definition for HasCombination */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_HasCombination = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "HasCombination", null);
    /** Column name HasCombination */
    public static final String COLUMNNAME_HasCombination = "HasCombination";

	/**
	 * Set Umsatzrealisierung bei Rechnung.
	 * Definiert ob der Umsatz bei Rechnung oder erst bei Zahlung realisiert wird
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAccrual (boolean IsAccrual);

	/**
	 * Get Umsatzrealisierung bei Rechnung.
	 * Definiert ob der Umsatz bei Rechnung oder erst bei Zahlung realisiert wird
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAccrual();

    /** Column definition for IsAccrual */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_IsAccrual = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "IsAccrual", null);
    /** Column name IsAccrual */
    public static final String COLUMNNAME_IsAccrual = "IsAccrual";

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
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Adjust COGS.
	 * Adjust Cost of Good Sold
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAdjustCOGS (boolean IsAdjustCOGS);

	/**
	 * Get Adjust COGS.
	 * Adjust Cost of Good Sold
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAdjustCOGS();

    /** Column definition for IsAdjustCOGS */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_IsAdjustCOGS = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "IsAdjustCOGS", null);
    /** Column name IsAdjustCOGS */
    public static final String COLUMNNAME_IsAdjustCOGS = "IsAdjustCOGS";

	/**
	 * Set Negativbuchung erlauben.
	 * Erlaube die Buchung von negativen Werten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsAllowNegativePosting (boolean IsAllowNegativePosting);

	/**
	 * Get Negativbuchung erlauben.
	 * Erlaube die Buchung von negativen Werten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isAllowNegativePosting();

    /** Column definition for IsAllowNegativePosting */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_IsAllowNegativePosting = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "IsAllowNegativePosting", null);
    /** Column name IsAllowNegativePosting */
    public static final String COLUMNNAME_IsAllowNegativePosting = "IsAllowNegativePosting";

	/**
	 * Set Correct tax for Discounts/Charges.
	 * Correct the tax for payment discount and charges
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDiscountCorrectsTax (boolean IsDiscountCorrectsTax);

	/**
	 * Get Correct tax for Discounts/Charges.
	 * Correct the tax for payment discount and charges
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDiscountCorrectsTax();

    /** Column definition for IsDiscountCorrectsTax */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_IsDiscountCorrectsTax = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "IsDiscountCorrectsTax", null);
    /** Column name IsDiscountCorrectsTax */
    public static final String COLUMNNAME_IsDiscountCorrectsTax = "IsDiscountCorrectsTax";

	/**
	 * Set Bezugsnebenkosten direkt verbuchen.
	 * Verbuchung der Bezugsnebenkosten direkt und nicht erst nach Aufteilung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsExplicitCostAdjustment (boolean IsExplicitCostAdjustment);

	/**
	 * Get Bezugsnebenkosten direkt verbuchen.
	 * Verbuchung der Bezugsnebenkosten direkt und nicht erst nach Aufteilung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isExplicitCostAdjustment();

    /** Column definition for IsExplicitCostAdjustment */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_IsExplicitCostAdjustment = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "IsExplicitCostAdjustment", null);
    /** Column name IsExplicitCostAdjustment */
    public static final String COLUMNNAME_IsExplicitCostAdjustment = "IsExplicitCostAdjustment";

	/**
	 * Set Verbuchung bei identischen Konten.
	 * Verbuchung bei identischen Konten (Transit) durchführen
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsPostIfClearingEqual (boolean IsPostIfClearingEqual);

	/**
	 * Get Verbuchung bei identischen Konten.
	 * Verbuchung bei identischen Konten (Transit) durchführen
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isPostIfClearingEqual();

    /** Column definition for IsPostIfClearingEqual */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_IsPostIfClearingEqual = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "IsPostIfClearingEqual", null);
    /** Column name IsPostIfClearingEqual */
    public static final String COLUMNNAME_IsPostIfClearingEqual = "IsPostIfClearingEqual";

	/**
	 * Set Leistungen seperat verbuchen.
	 * Verbuchung soll unterscheiden soll zwischen Produkt Lieferungen und Dienstleistungen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPostServices (boolean IsPostServices);

	/**
	 * Get Leistungen seperat verbuchen.
	 * Verbuchung soll unterscheiden soll zwischen Produkt Lieferungen und Dienstleistungen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPostServices();

    /** Column definition for IsPostServices */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_IsPostServices = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "IsPostServices", null);
    /** Column name IsPostServices */
    public static final String COLUMNNAME_IsPostServices = "IsPostServices";

	/**
	 * Set Rabatte seperat verbuchen.
	 * Erzeuge seperate Buchungen für Handelstrabatte
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsTradeDiscountPosted (boolean IsTradeDiscountPosted);

	/**
	 * Get Rabatte seperat verbuchen.
	 * Erzeuge seperate Buchungen für Handelstrabatte
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isTradeDiscountPosted();

    /** Column definition for IsTradeDiscountPosted */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_IsTradeDiscountPosted = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "IsTradeDiscountPosted", null);
    /** Column name IsTradeDiscountPosted */
    public static final String COLUMNNAME_IsTradeDiscountPosted = "IsTradeDiscountPosted";

	/**
	 * Set Kostenkategorie.
	 * Type of Cost (e.g. Current, Plan, Future)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_CostType_ID (int M_CostType_ID);

	/**
	 * Get Kostenkategorie.
	 * Type of Cost (e.g. Current, Plan, Future)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_CostType_ID();

	public org.compiere.model.I_M_CostType getM_CostType();

	public void setM_CostType(org.compiere.model.I_M_CostType M_CostType);

    /** Column definition for M_CostType_ID */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, org.compiere.model.I_M_CostType> COLUMN_M_CostType_ID = new org.adempiere.model.ModelColumn<I_C_AcctSchema, org.compiere.model.I_M_CostType>(I_C_AcctSchema.class, "M_CostType_ID", org.compiere.model.I_M_CostType.class);
    /** Column name M_CostType_ID */
    public static final String COLUMNNAME_M_CostType_ID = "M_CostType_ID";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Future Days.
	 * Number of days to be able to post to a future date (based on system date)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPeriod_OpenFuture (int Period_OpenFuture);

	/**
	 * Get Future Days.
	 * Number of days to be able to post to a future date (based on system date)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPeriod_OpenFuture();

    /** Column definition for Period_OpenFuture */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_Period_OpenFuture = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "Period_OpenFuture", null);
    /** Column name Period_OpenFuture */
    public static final String COLUMNNAME_Period_OpenFuture = "Period_OpenFuture";

	/**
	 * Set History Days.
	 * Number of days to be able to post in the past (based on system date)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPeriod_OpenHistory (int Period_OpenHistory);

	/**
	 * Get History Days.
	 * Number of days to be able to post in the past (based on system date)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPeriod_OpenHistory();

    /** Column definition for Period_OpenHistory */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_Period_OpenHistory = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "Period_OpenHistory", null);
    /** Column name Period_OpenHistory */
    public static final String COLUMNNAME_Period_OpenHistory = "Period_OpenHistory";

	/**
	 * Set Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Element-Trenner.
	 * Element Separator
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeparator (java.lang.String Separator);

	/**
	 * Get Element-Trenner.
	 * Element Separator
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSeparator();

    /** Column definition for Separator */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_Separator = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "Separator", null);
    /** Column name Separator */
    public static final String COLUMNNAME_Separator = "Separator";

	/**
	 * Set MwSt. Korrektur.
	 * Art der MwSt. Korrektur
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTaxCorrectionType (java.lang.String TaxCorrectionType);

	/**
	 * Get MwSt. Korrektur.
	 * Art der MwSt. Korrektur
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTaxCorrectionType();

    /** Column definition for TaxCorrectionType */
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_TaxCorrectionType = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "TaxCorrectionType", null);
    /** Column name TaxCorrectionType */
    public static final String COLUMNNAME_TaxCorrectionType = "TaxCorrectionType";

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
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_AcctSchema, Object>(I_C_AcctSchema.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_AcctSchema, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_AcctSchema, org.compiere.model.I_AD_User>(I_C_AcctSchema.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
