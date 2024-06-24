package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_AcctSchema
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_AcctSchema 
{

	String Table_Name = "C_AcctSchema";

//	/** AD_Table_ID=265 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Nur für Organisation.
	 * Kontrierung nur für die angegebene Organisation
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_OrgOnly_ID (int AD_OrgOnly_ID);

	/**
	 * Get Nur für Organisation.
	 * Kontrierung nur für die angegebene Organisation
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_OrgOnly_ID();

	String COLUMNNAME_AD_OrgOnly_ID = "AD_OrgOnly_ID";

	/**
	 * Set Automatische Periodenkontrolle.
	 * If selected, the periods are automatically opened and closed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAutoPeriodControl (boolean AutoPeriodControl);

	/**
	 * Get Automatische Periodenkontrolle.
	 * If selected, the periods are automatically opened and closed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAutoPeriodControl();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_AutoPeriodControl = new ModelColumn<>(I_C_AcctSchema.class, "AutoPeriodControl", null);
	String COLUMNNAME_AutoPeriodControl = "AutoPeriodControl";

	/**
	 * Set Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_AcctSchema_ID();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_C_AcctSchema_ID = new ModelColumn<>(I_C_AcctSchema.class, "C_AcctSchema_ID", null);
	String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Art Reservierung.
	 * Erstelle Reservierungen für Budgetkontrolle
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCommitmentType (java.lang.String CommitmentType);

	/**
	 * Get Art Reservierung.
	 * Erstelle Reservierungen für Budgetkontrolle
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getCommitmentType();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_CommitmentType = new ModelColumn<>(I_C_AcctSchema.class, "CommitmentType", null);
	String COLUMNNAME_CommitmentType = "CommitmentType";

	/**
	 * Set Kostenrechnungsstufe.
	 * The lowest level to accumulate Costing Information
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCostingLevel (java.lang.String CostingLevel);

	/**
	 * Get Kostenrechnungsstufe.
	 * The lowest level to accumulate Costing Information
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getCostingLevel();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_CostingLevel = new ModelColumn<>(I_C_AcctSchema.class, "CostingLevel", null);
	String COLUMNNAME_CostingLevel = "CostingLevel";

	/**
	 * Set Kostenrechnungsmethode.
	 * Indicates how Costs will be calculated
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCostingMethod (java.lang.String CostingMethod);

	/**
	 * Get Kostenrechnungsmethode.
	 * Indicates how Costs will be calculated
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getCostingMethod();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_CostingMethod = new ModelColumn<>(I_C_AcctSchema.class, "CostingMethod", null);
	String COLUMNNAME_CostingMethod = "CostingMethod";

	/**
	 * Set Period.
	 * Period of the Calendar
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Period.
	 * Period of the Calendar
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Period_ID();

	@Nullable org.compiere.model.I_C_Period getC_Period();

	void setC_Period(@Nullable org.compiere.model.I_C_Period C_Period);

	ModelColumn<I_C_AcctSchema, org.compiere.model.I_C_Period> COLUMN_C_Period_ID = new ModelColumn<>(I_C_AcctSchema.class, "C_Period_ID", org.compiere.model.I_C_Period.class);
	String COLUMNNAME_C_Period_ID = "C_Period_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_Created = new ModelColumn<>(I_C_AcctSchema.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Creditor-Nr prefix.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCreditorIdPrefix (int CreditorIdPrefix);

	/**
	 * Get Creditor-Nr prefix.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreditorIdPrefix();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_CreditorIdPrefix = new ModelColumn<>(I_C_AcctSchema.class, "CreditorIdPrefix", null);
	String COLUMNNAME_CreditorIdPrefix = "CreditorIdPrefix";

	/**
	 * Set Debtor-Nr prefix.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDebtorIdPrefix (int DebtorIdPrefix);

	/**
	 * Get Debtor-Nr prefix.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDebtorIdPrefix();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_DebtorIdPrefix = new ModelColumn<>(I_C_AcctSchema.class, "DebtorIdPrefix", null);
	String COLUMNNAME_DebtorIdPrefix = "DebtorIdPrefix";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_Description = new ModelColumn<>(I_C_AcctSchema.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set GAAP.
	 * Generally Accepted Accounting Principles
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGAAP (java.lang.String GAAP);

	/**
	 * Get GAAP.
	 * Generally Accepted Accounting Principles
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getGAAP();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_GAAP = new ModelColumn<>(I_C_AcctSchema.class, "GAAP", null);
	String COLUMNNAME_GAAP = "GAAP";

	/**
	 * Set Use Account Alias.
	 * Ability to select (partial) account combinations by an Alias
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHasAlias (boolean HasAlias);

	/**
	 * Get Use Account Alias.
	 * Ability to select (partial) account combinations by an Alias
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isHasAlias();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_HasAlias = new ModelColumn<>(I_C_AcctSchema.class, "HasAlias", null);
	String COLUMNNAME_HasAlias = "HasAlias";

	/**
	 * Set Use Account Combination Control.
	 * Combination of account elements are checked
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHasCombination (boolean HasCombination);

	/**
	 * Get Use Account Combination Control.
	 * Combination of account elements are checked
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isHasCombination();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_HasCombination = new ModelColumn<>(I_C_AcctSchema.class, "HasCombination", null);
	String COLUMNNAME_HasCombination = "HasCombination";

	/**
	 * Set Umsatzrealisierung bei Rechnung.
	 * Definiert ob der Umsatz bei Rechnung oder erst bei Zahlung realisiert wird
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAccrual (boolean IsAccrual);

	/**
	 * Get Umsatzrealisierung bei Rechnung.
	 * Definiert ob der Umsatz bei Rechnung oder erst bei Zahlung realisiert wird
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAccrual();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_IsAccrual = new ModelColumn<>(I_C_AcctSchema.class, "IsAccrual", null);
	String COLUMNNAME_IsAccrual = "IsAccrual";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_IsActive = new ModelColumn<>(I_C_AcctSchema.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Adjust COGS.
	 * Adjust Cost of Good Sold
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAdjustCOGS (boolean IsAdjustCOGS);

	/**
	 * Get Adjust COGS.
	 * Adjust Cost of Good Sold
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAdjustCOGS();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_IsAdjustCOGS = new ModelColumn<>(I_C_AcctSchema.class, "IsAdjustCOGS", null);
	String COLUMNNAME_IsAdjustCOGS = "IsAdjustCOGS";

	/**
	 * Set Allow Multi Debit and Credit bookings.
	 * Allow compound transactions with multiple bookings on Debit and on Credit. Enable it only if you know what are you doing. Your Account Balance reports won't work.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowMultiDebitAndCredit (boolean IsAllowMultiDebitAndCredit);

	/**
	 * Get Allow Multi Debit and Credit bookings.
	 * Allow compound transactions with multiple bookings on Debit and on Credit. Enable it only if you know what are you doing. Your Account Balance reports won't work.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowMultiDebitAndCredit();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_IsAllowMultiDebitAndCredit = new ModelColumn<>(I_C_AcctSchema.class, "IsAllowMultiDebitAndCredit", null);
	String COLUMNNAME_IsAllowMultiDebitAndCredit = "IsAllowMultiDebitAndCredit";

	/**
	 * Set Allow Negative Posting.
	 * Allow to post negative accounting values
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsAllowNegativePosting (boolean IsAllowNegativePosting);

	/**
	 * Get Negativbuchung erlauben.
	 * Erlaube die Buchung von negativen Werten
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isAllowNegativePosting();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_IsAllowNegativePosting = new ModelColumn<>(I_C_AcctSchema.class, "IsAllowNegativePosting", null);
	String COLUMNNAME_IsAllowNegativePosting = "IsAllowNegativePosting";

	/**
	 * Set Set debtor-id and creditor-id automatically.
	 * If ticked, then metasfresh from there onwards updates a business partner's debitorId and creditorId automatically from the bartner's respective value and an optional prefix, whenever a partner's value is updated or a new partner is created.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAutoSetDebtoridAndCreditorid (boolean IsAutoSetDebtoridAndCreditorid);

	/**
	 * Get Set debtor-id and creditor-id automatically.
	 * If ticked, then metasfresh from there onwards updates a business partner's debitorId and creditorId automatically from the bartner's respective value and an optional prefix, whenever a partner's value is updated or a new partner is created.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAutoSetDebtoridAndCreditorid();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_IsAutoSetDebtoridAndCreditorid = new ModelColumn<>(I_C_AcctSchema.class, "IsAutoSetDebtoridAndCreditorid", null);
	String COLUMNNAME_IsAutoSetDebtoridAndCreditorid = "IsAutoSetDebtoridAndCreditorid";

	/**
	 * Set Correct tax for Discounts/Charges.
	 * Correct the tax for payment discount and charges
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDiscountCorrectsTax (boolean IsDiscountCorrectsTax);

	/**
	 * Get Correct tax for Discounts/Charges.
	 * Correct the tax for payment discount and charges
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDiscountCorrectsTax();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_IsDiscountCorrectsTax = new ModelColumn<>(I_C_AcctSchema.class, "IsDiscountCorrectsTax", null);
	String COLUMNNAME_IsDiscountCorrectsTax = "IsDiscountCorrectsTax";

	/**
	 * Set Bezugsnebenkosten direkt verbuchen.
	 * Verbuchung der Bezugsnebenkosten direkt und nicht erst nach Aufteilung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsExplicitCostAdjustment (boolean IsExplicitCostAdjustment);

	/**
	 * Get Bezugsnebenkosten direkt verbuchen.
	 * Verbuchung der Bezugsnebenkosten direkt und nicht erst nach Aufteilung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isExplicitCostAdjustment();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_IsExplicitCostAdjustment = new ModelColumn<>(I_C_AcctSchema.class, "IsExplicitCostAdjustment", null);
	String COLUMNNAME_IsExplicitCostAdjustment = "IsExplicitCostAdjustment";

	/**
	 * Set Verbuchung bei identischen Konten.
	 * Verbuchung bei identischen Konten (Transit) durchführen
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsPostIfClearingEqual (boolean IsPostIfClearingEqual);

	/**
	 * Get Verbuchung bei identischen Konten.
	 * Verbuchung bei identischen Konten (Transit) durchführen
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isPostIfClearingEqual();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_IsPostIfClearingEqual = new ModelColumn<>(I_C_AcctSchema.class, "IsPostIfClearingEqual", null);
	String COLUMNNAME_IsPostIfClearingEqual = "IsPostIfClearingEqual";

	/**
	 * Set Leistungen seperat verbuchen.
	 * Verbuchung soll unterscheiden soll zwischen Produkt Lieferungen und Dienstleistungen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPostServices (boolean IsPostServices);

	/**
	 * Get Leistungen seperat verbuchen.
	 * Verbuchung soll unterscheiden soll zwischen Produkt Lieferungen und Dienstleistungen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPostServices();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_IsPostServices = new ModelColumn<>(I_C_AcctSchema.class, "IsPostServices", null);
	String COLUMNNAME_IsPostServices = "IsPostServices";

	/**
	 * Set Rabatte seperat verbuchen.
	 * Erzeuge seperate Buchungen für Handelstrabatte
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTradeDiscountPosted (boolean IsTradeDiscountPosted);

	/**
	 * Get Rabatte seperat verbuchen.
	 * Erzeuge seperate Buchungen für Handelstrabatte
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTradeDiscountPosted();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_IsTradeDiscountPosted = new ModelColumn<>(I_C_AcctSchema.class, "IsTradeDiscountPosted", null);
	String COLUMNNAME_IsTradeDiscountPosted = "IsTradeDiscountPosted";

	/**
	 * Set Kostenkategorie.
	 * Type of Cost (e.g. Current, Plan, Future)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_CostType_ID (int M_CostType_ID);

	/**
	 * Get Kostenkategorie.
	 * Type of Cost (e.g. Current, Plan, Future)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_CostType_ID();

	org.compiere.model.I_M_CostType getM_CostType();

	void setM_CostType(org.compiere.model.I_M_CostType M_CostType);

	ModelColumn<I_C_AcctSchema, org.compiere.model.I_M_CostType> COLUMN_M_CostType_ID = new ModelColumn<>(I_C_AcctSchema.class, "M_CostType_ID", org.compiere.model.I_M_CostType.class);
	String COLUMNNAME_M_CostType_ID = "M_CostType_ID";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_Name = new ModelColumn<>(I_C_AcctSchema.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Future Days.
	 * Number of days to be able to post to a future date (based on system date)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPeriod_OpenFuture (int Period_OpenFuture);

	/**
	 * Get Future Days.
	 * Number of days to be able to post to a future date (based on system date)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPeriod_OpenFuture();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_Period_OpenFuture = new ModelColumn<>(I_C_AcctSchema.class, "Period_OpenFuture", null);
	String COLUMNNAME_Period_OpenFuture = "Period_OpenFuture";

	/**
	 * Set History Days.
	 * Number of days to be able to post in the past (based on system date)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPeriod_OpenHistory (int Period_OpenHistory);

	/**
	 * Get History Days.
	 * Number of days to be able to post in the past (based on system date)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPeriod_OpenHistory();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_Period_OpenHistory = new ModelColumn<>(I_C_AcctSchema.class, "Period_OpenHistory", null);
	String COLUMNNAME_Period_OpenHistory = "Period_OpenHistory";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_Processing = new ModelColumn<>(I_C_AcctSchema.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Element-Trenner.
	 * Element Separator
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeparator (java.lang.String Separator);

	/**
	 * Get Element-Trenner.
	 * Element Separator
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getSeparator();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_Separator = new ModelColumn<>(I_C_AcctSchema.class, "Separator", null);
	String COLUMNNAME_Separator = "Separator";

	/**
	 * Set MwSt. Korrektur.
	 * Art der MwSt. Korrektur
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTaxCorrectionType (java.lang.String TaxCorrectionType);

	/**
	 * Get MwSt. Korrektur.
	 * Art der MwSt. Korrektur
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTaxCorrectionType();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_TaxCorrectionType = new ModelColumn<>(I_C_AcctSchema.class, "TaxCorrectionType", null);
	String COLUMNNAME_TaxCorrectionType = "TaxCorrectionType";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_AcctSchema, Object> COLUMN_Updated = new ModelColumn<>(I_C_AcctSchema.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
