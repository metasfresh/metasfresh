package de.metas.contracts.model;


/** Generated Interface for C_Flatrate_Conditions
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Flatrate_Conditions 
{

    /** TableName=C_Flatrate_Conditions */
    String Table_Name = "C_Flatrate_Conditions";

    /** AD_Table_ID=540311 */
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
	int getAD_Client_ID();

	org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	org.compiere.model.I_AD_Org getAD_Org();

	void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Vertragsbedingungen.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID);

	/**
	 * Get Vertragsbedingungen.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Conditions_ID();

    /** Column definition for C_Flatrate_Conditions_ID */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_C_Flatrate_Conditions_ID = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "C_Flatrate_Conditions_ID", null);
    /** Column name C_Flatrate_Conditions_ID */
    String COLUMNNAME_C_Flatrate_Conditions_ID = "C_Flatrate_Conditions_ID";

	/**
	 * Set C_Flatrate_Matching_IncludedT.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated void setC_Flatrate_Matching_IncludedT (java.lang.String C_Flatrate_Matching_IncludedT);

	/**
	 * Get C_Flatrate_Matching_IncludedT.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	java.lang.String getC_Flatrate_Matching_IncludedT();

    /** Column definition for C_Flatrate_Matching_IncludedT */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_C_Flatrate_Matching_IncludedT = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "C_Flatrate_Matching_IncludedT", null);
    /** Column name C_Flatrate_Matching_IncludedT */
    String COLUMNNAME_C_Flatrate_Matching_IncludedT = "C_Flatrate_Matching_IncludedT";

	/**
	 * Set Vertragsverlängerung/-übergang.
	 * Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Transition_ID (int C_Flatrate_Transition_ID);

	/**
	 * Get Vertragsverlängerung/-übergang.
	 * Regelt z.B. die Vertragslaufzeit, Kündigungsfristen, autmatische Verlängerung usw.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Transition_ID();

	de.metas.contracts.model.I_C_Flatrate_Transition getC_Flatrate_Transition();

	void setC_Flatrate_Transition(de.metas.contracts.model.I_C_Flatrate_Transition C_Flatrate_Transition);

    /** Column definition for C_Flatrate_Transition_ID */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, de.metas.contracts.model.I_C_Flatrate_Transition> COLUMN_C_Flatrate_Transition_ID = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "C_Flatrate_Transition_ID", de.metas.contracts.model.I_C_Flatrate_Transition.class);
    /** Column name C_Flatrate_Transition_ID */
    String COLUMNNAME_C_Flatrate_Transition_ID = "C_Flatrate_Transition_ID";

	/**
	 * Set Basis für Verrechnungs-Zahlbetrag.
	 * Entscheidet, ob der Verrechnungsbetrag auf Basis der Produktpreise (tats. erbrachte Leistungen) oder als prozentualer Aufschlag/Abschlag ermittelt wird.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setClearingAmtBaseOn (java.lang.String ClearingAmtBaseOn);

	/**
	 * Get Basis für Verrechnungs-Zahlbetrag.
	 * Entscheidet, ob der Verrechnungsbetrag auf Basis der Produktpreise (tats. erbrachte Leistungen) oder als prozentualer Aufschlag/Abschlag ermittelt wird.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getClearingAmtBaseOn();

    /** Column definition for ClearingAmtBaseOn */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_ClearingAmtBaseOn = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "ClearingAmtBaseOn", null);
    /** Column name ClearingAmtBaseOn */
    String COLUMNNAME_ClearingAmtBaseOn = "ClearingAmtBaseOn";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

    /** Column definition for Created */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "Created", null);
    /** Column name Created */
    String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

    /** Column definition for CreatedBy */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get Maßeinheit.
	 * Maßeinheit
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	org.compiere.model.I_C_UOM getC_UOM();

	void setC_UOM(org.compiere.model.I_C_UOM C_UOM);

    /** Column definition for C_UOM_ID */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Belegverarbeitung.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocAction (java.lang.String DocAction);

	/**
	 * Get Belegverarbeitung.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocAction();

    /** Column definition for DocAction */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_DocAction = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "DocAction", null);
    /** Column name DocAction */
    String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocStatus();

    /** Column definition for DocStatus */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "DocStatus", null);
    /** Column name DocStatus */
    String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Rechnungsstellung.
	 * "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoiceRule (java.lang.String InvoiceRule);

	/**
	 * Get Rechnungsstellung.
	 * "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getInvoiceRule();

    /** Column definition for InvoiceRule */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_InvoiceRule = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "InvoiceRule", null);
    /** Column name InvoiceRule */
    String COLUMNNAME_InvoiceRule = "InvoiceRule";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

    /** Column definition for IsActive */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "IsActive", null);
    /** Column name IsActive */
    String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Gegenüberstellung mit erbr. Leist..
	 * Legt fest, ob die pauschal abgerechenten Beträge den tatsächlich erbrachten Leistungen gegenüber gestellt werden sollen
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsClosingWithActualSum (boolean IsClosingWithActualSum);

	/**
	 * Get Gegenüberstellung mit erbr. Leist..
	 * Legt fest, ob die pauschal abgerechenten Beträge den tatsächlich erbrachten Leistungen gegenüber gestellt werden sollen
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isClosingWithActualSum();

    /** Column definition for IsClosingWithActualSum */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsClosingWithActualSum = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "IsClosingWithActualSum", null);
    /** Column name IsClosingWithActualSum */
    String COLUMNNAME_IsClosingWithActualSum = "IsClosingWithActualSum";

	/**
	 * Set Abschlusskorrektur vorsehen.
	 * Legt fest, ob innerhalb der Vertragslaufzeit (in der Regel zu deren Ende) noch korrigierte Pauschalen-Mengen erfasst werden können
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsClosingWithCorrectionSum (boolean IsClosingWithCorrectionSum);

	/**
	 * Get Abschlusskorrektur vorsehen.
	 * Legt fest, ob innerhalb der Vertragslaufzeit (in der Regel zu deren Ende) noch korrigierte Pauschalen-Mengen erfasst werden können
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isClosingWithCorrectionSum();

    /** Column definition for IsClosingWithCorrectionSum */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsClosingWithCorrectionSum = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "IsClosingWithCorrectionSum", null);
    /** Column name IsClosingWithCorrectionSum */
    String COLUMNNAME_IsClosingWithCorrectionSum = "IsClosingWithCorrectionSum";

	/**
	 * Set Verrechung erst nach Abschlusskorrektur.
	 * Legt fest, ob Nach- bzw. Rückzahlungen erst nach Erfassung der Abschlusskorrektur in Rechnung zu stellen sind
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCorrectionAmtAtClosing (boolean IsCorrectionAmtAtClosing);

	/**
	 * Get Verrechung erst nach Abschlusskorrektur.
	 * Legt fest, ob Nach- bzw. Rückzahlungen erst nach Erfassung der Abschlusskorrektur in Rechnung zu stellen sind
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCorrectionAmtAtClosing();

    /** Column definition for IsCorrectionAmtAtClosing */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsCorrectionAmtAtClosing = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "IsCorrectionAmtAtClosing", null);
    /** Column name IsCorrectionAmtAtClosing */
    String COLUMNNAME_IsCorrectionAmtAtClosing = "IsCorrectionAmtAtClosing";

	/**
	 * Set Keine Rechnungserstellung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCreateNoInvoice (boolean IsCreateNoInvoice);

	/**
	 * Get Keine Rechnungserstellung.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCreateNoInvoice();

    /** Column definition for IsCreateNoInvoice */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsCreateNoInvoice = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "IsCreateNoInvoice", null);
    /** Column name IsCreateNoInvoice */
    String COLUMNNAME_IsCreateNoInvoice = "IsCreateNoInvoice";

	/**
	 * Set Gratis.
	 * Es wird unabhängig vom gewählten Preissystem ein Rabatt von 100% gewährt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsFreeOfCharge (boolean IsFreeOfCharge);

	/**
	 * Get Gratis.
	 * Es wird unabhängig vom gewählten Preissystem ein Rabatt von 100% gewährt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isFreeOfCharge();

    /** Column definition for IsFreeOfCharge */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsFreeOfCharge = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "IsFreeOfCharge", null);
    /** Column name IsFreeOfCharge */
    String COLUMNNAME_IsFreeOfCharge = "IsFreeOfCharge";

	/**
	 * Set Manueller Preis.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManualPrice (boolean IsManualPrice);

	/**
	 * Get Manueller Preis.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManualPrice();

    /** Column definition for IsManualPrice */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsManualPrice = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "IsManualPrice", null);
    /** Column name IsManualPrice */
    String COLUMNNAME_IsManualPrice = "IsManualPrice";

	/**
	 * Set Planspiel.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSimulation (boolean IsSimulation);

	/**
	 * Get Planspiel.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSimulation();

    /** Column definition for IsSimulation */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsSimulation = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "IsSimulation", null);
    /** Column name IsSimulation */
    String COLUMNNAME_IsSimulation = "IsSimulation";

	/**
	 * Set Korridor - Überschreitung.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMargin_Max (java.math.BigDecimal Margin_Max);

	/**
	 * Get Korridor - Überschreitung.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getMargin_Max();

    /** Column definition for Margin_Max */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Margin_Max = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "Margin_Max", null);
    /** Column name Margin_Max */
    String COLUMNNAME_Margin_Max = "Margin_Max";

	/**
	 * Set Korridor - Unterschreitung.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMargin_Min (java.math.BigDecimal Margin_Min);

	/**
	 * Get Korridor - Unterschreitung.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getMargin_Min();

    /** Column definition for Margin_Min */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Margin_Min = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "Margin_Min", null);
    /** Column name Margin_Min */
    String COLUMNNAME_Margin_Min = "Margin_Min";

	/**
	 * Set Preissystem.
	 * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Preissystem.
	 * Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_PricingSystem_ID();

	org.compiere.model.I_M_PricingSystem getM_PricingSystem();

	void setM_PricingSystem(org.compiere.model.I_M_PricingSystem M_PricingSystem);

    /** Column definition for M_PricingSystem_ID */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, org.compiere.model.I_M_PricingSystem> COLUMN_M_PricingSystem_ID = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "M_PricingSystem_ID", org.compiere.model.I_M_PricingSystem.class);
    /** Column name M_PricingSystem_ID */
    String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	/**
	 * Set Produkt für Verrechnung.
	 * Produkt, unter dem ggf. die Differenz zu tatsächlich erbrachten Leistungen in Rechnung gestellt wird.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_Actual_ID (int M_Product_Actual_ID);

	/**
	 * Get Produkt für Verrechnung.
	 * Produkt, unter dem ggf. die Differenz zu tatsächlich erbrachten Leistungen in Rechnung gestellt wird.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_Actual_ID();

	org.compiere.model.I_M_Product getM_Product_Actual();

	void setM_Product_Actual(org.compiere.model.I_M_Product M_Product_Actual);

    /** Column definition for M_Product_Actual_ID */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, org.compiere.model.I_M_Product> COLUMN_M_Product_Actual_ID = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "M_Product_Actual_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_Actual_ID */
    String COLUMNNAME_M_Product_Actual_ID = "M_Product_Actual_ID";

	/**
	 * Set Produkt für Abschlusskorrektur.
	 * Produkt, unter dem ggf. die Differenz zu den in der Abschlusskorrektur gemeldeten Mengen in Rechnung gestellt wird.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_Correction_ID (int M_Product_Correction_ID);

	/**
	 * Get Produkt für Abschlusskorrektur.
	 * Produkt, unter dem ggf. die Differenz zu den in der Abschlusskorrektur gemeldeten Mengen in Rechnung gestellt wird.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_Correction_ID();

	org.compiere.model.I_M_Product getM_Product_Correction();

	void setM_Product_Correction(org.compiere.model.I_M_Product M_Product_Correction);

    /** Column definition for M_Product_Correction_ID */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, org.compiere.model.I_M_Product> COLUMN_M_Product_Correction_ID = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "M_Product_Correction_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_Correction_ID */
    String COLUMNNAME_M_Product_Correction_ID = "M_Product_Correction_ID";

	/**
	 * Set Produkt für pauschale Berechnung.
	 * Produkt, unter dem die pauschal abzurechnenden Leistungen in Rechnung gestellt werden
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_Flatrate_ID (int M_Product_Flatrate_ID);

	/**
	 * Get Produkt für pauschale Berechnung.
	 * Produkt, unter dem die pauschal abzurechnenden Leistungen in Rechnung gestellt werden
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_Flatrate_ID();

    /** Column definition for M_Product_Flatrate_ID */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, org.compiere.model.I_M_Product> COLUMN_M_Product_Flatrate_ID = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "M_Product_Flatrate_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_Flatrate_ID */
    String COLUMNNAME_M_Product_Flatrate_ID = "M_Product_Flatrate_ID";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

    /** Column definition for Name */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "Name", null);
    /** Column name Name */
    String COLUMNNAME_Name = "Name";

	/**
	 * Set Behaviour when extending contract.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnFlatrateTermExtend (java.lang.String OnFlatrateTermExtend);

	/**
	 * Get Behaviour when extending contract.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getOnFlatrateTermExtend();

    /** Column definition for OnFlatrateTermExtend */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_OnFlatrateTermExtend = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "OnFlatrateTermExtend", null);
    /** Column name OnFlatrateTermExtend */
    String COLUMNNAME_OnFlatrateTermExtend = "OnFlatrateTermExtend";

	/**
	 * Set Drucktext.
	 * Bezeichnung, die auf dem Dokument oder der Korrespondenz gedruckt werden soll
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrintName (java.lang.String PrintName);

	/**
	 * Get Drucktext.
	 * Bezeichnung, die auf dem Dokument oder der Korrespondenz gedruckt werden soll
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getPrintName();

    /** Column definition for PrintName */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_PrintName = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "PrintName", null);
    /** Column name PrintName */
    String COLUMNNAME_PrintName = "PrintName";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

    /** Column definition for Processed */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "Processed", null);
    /** Column name Processed */
    String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

    /** Column definition for Processing */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "Processing", null);
    /** Column name Processing */
    String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Verrechnungsmodus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setType_Clearing (java.lang.String Type_Clearing);

	/**
	 * Get Verrechnungsmodus.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getType_Clearing();

    /** Column definition for Type_Clearing */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Type_Clearing = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "Type_Clearing", null);
    /** Column name Type_Clearing */
    String COLUMNNAME_Type_Clearing = "Type_Clearing";

	/**
	 * Set Vertragsart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setType_Conditions (java.lang.String Type_Conditions);

	/**
	 * Get Vertragsart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getType_Conditions();

    /** Column definition for Type_Conditions */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Type_Conditions = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "Type_Conditions", null);
    /** Column name Type_Conditions */
    String COLUMNNAME_Type_Conditions = "Type_Conditions";

	/**
	 * Set Verrechnungsart.
	 * Art der Verrechnung bei der Gegenüberstellung mit tatsächliche erbrachten Leistungen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setType_Flatrate (java.lang.String Type_Flatrate);

	/**
	 * Get Verrechnungsart.
	 * Art der Verrechnung bei der Gegenüberstellung mit tatsächliche erbrachten Leistungen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getType_Flatrate();

    /** Column definition for Type_Flatrate */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Type_Flatrate = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "Type_Flatrate", null);
    /** Column name Type_Flatrate */
    String COLUMNNAME_Type_Flatrate = "Type_Flatrate";

	/**
	 * Set Einheiten-Typ.
	 * Dient der Zusammenfassung ähnlicher Maßeinheiten
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setUOMType (java.lang.String UOMType);

	/**
	 * Get Einheiten-Typ.
	 * Dient der Zusammenfassung ähnlicher Maßeinheiten
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getUOMType();

    /** Column definition for UOMType */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_UOMType = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "UOMType", null);
    /** Column name UOMType */
    String COLUMNNAME_UOMType = "UOMType";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "Updated", null);
    /** Column name Updated */
    String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

    /** Column definition for UpdatedBy */
    org.adempiere.model.ModelColumn<I_C_Flatrate_Conditions, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<>(I_C_Flatrate_Conditions.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
