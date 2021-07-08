package de.metas.contracts.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

import java.math.BigDecimal;

/** Generated Interface for C_Flatrate_Conditions
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Flatrate_Conditions 
{

	String Table_Name = "C_Flatrate_Conditions";

//	/** AD_Table_ID=540311 */
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
	 * Set Contract Terms.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID);

	/**
	 * Get Contract Terms.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Conditions_ID();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_C_Flatrate_Conditions_ID = new ModelColumn<>(I_C_Flatrate_Conditions.class, "C_Flatrate_Conditions_ID", null);
	String COLUMNNAME_C_Flatrate_Conditions_ID = "C_Flatrate_Conditions_ID";

	/**
	 * Set C_Flatrate_Matching_IncludedT.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_Flatrate_Matching_IncludedT (@Nullable java.lang.String C_Flatrate_Matching_IncludedT);

	/**
	 * Get C_Flatrate_Matching_IncludedT.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getC_Flatrate_Matching_IncludedT();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_C_Flatrate_Matching_IncludedT = new ModelColumn<>(I_C_Flatrate_Conditions.class, "C_Flatrate_Matching_IncludedT", null);
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

	@Nullable de.metas.contracts.model.I_C_Flatrate_Transition getC_Flatrate_Transition();

	void setC_Flatrate_Transition(@Nullable de.metas.contracts.model.I_C_Flatrate_Transition C_Flatrate_Transition);

	ModelColumn<I_C_Flatrate_Conditions, de.metas.contracts.model.I_C_Flatrate_Transition> COLUMN_C_Flatrate_Transition_ID = new ModelColumn<>(I_C_Flatrate_Conditions.class, "C_Flatrate_Transition_ID", de.metas.contracts.model.I_C_Flatrate_Transition.class);
	String COLUMNNAME_C_Flatrate_Transition_ID = "C_Flatrate_Transition_ID";

	/**
	 * Set Basis für Verrechnungs-Zahlbetrag.
	 * Entscheidet, ob der Verrechnungsbetrag auf Basis der Produktpreise (tats. erbrachte Leistungen) oder als prozentualer Aufschlag/Abschlag ermittelt wird.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setClearingAmtBaseOn (@Nullable java.lang.String ClearingAmtBaseOn);

	/**
	 * Get Basis für Verrechnungs-Zahlbetrag.
	 * Entscheidet, ob der Verrechnungsbetrag auf Basis der Produktpreise (tats. erbrachte Leistungen) oder als prozentualer Aufschlag/Abschlag ermittelt wird.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getClearingAmtBaseOn();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_ClearingAmtBaseOn = new ModelColumn<>(I_C_Flatrate_Conditions.class, "ClearingAmtBaseOn", null);
	String COLUMNNAME_ClearingAmtBaseOn = "ClearingAmtBaseOn";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Created = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Created", null);
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
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Process Batch.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocAction (java.lang.String DocAction);

	/**
	 * Get Process Batch.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocAction();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_DocAction = new ModelColumn<>(I_C_Flatrate_Conditions.class, "DocAction", null);
	String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocStatus();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_DocStatus = new ModelColumn<>(I_C_Flatrate_Conditions.class, "DocStatus", null);
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

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_InvoiceRule = new ModelColumn<>(I_C_Flatrate_Conditions.class, "InvoiceRule", null);
	String COLUMNNAME_InvoiceRule = "InvoiceRule";

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

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Flatrate_Conditions.class, "IsActive", null);
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

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsClosingWithActualSum = new ModelColumn<>(I_C_Flatrate_Conditions.class, "IsClosingWithActualSum", null);
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

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsClosingWithCorrectionSum = new ModelColumn<>(I_C_Flatrate_Conditions.class, "IsClosingWithCorrectionSum", null);
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

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsCorrectionAmtAtClosing = new ModelColumn<>(I_C_Flatrate_Conditions.class, "IsCorrectionAmtAtClosing", null);
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

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsCreateNoInvoice = new ModelColumn<>(I_C_Flatrate_Conditions.class, "IsCreateNoInvoice", null);
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

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsFreeOfCharge = new ModelColumn<>(I_C_Flatrate_Conditions.class, "IsFreeOfCharge", null);
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

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsManualPrice = new ModelColumn<>(I_C_Flatrate_Conditions.class, "IsManualPrice", null);
	String COLUMNNAME_IsManualPrice = "IsManualPrice";

	/**
	 * Set Simulation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSimulation (boolean IsSimulation);

	/**
	 * Get Simulation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSimulation();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_IsSimulation = new ModelColumn<>(I_C_Flatrate_Conditions.class, "IsSimulation", null);
	String COLUMNNAME_IsSimulation = "IsSimulation";

	/**
	 * Set Korridor - Überschreitung.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMargin_Max (BigDecimal Margin_Max);

	/**
	 * Get Korridor - Überschreitung.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getMargin_Max();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Margin_Max = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Margin_Max", null);
	String COLUMNNAME_Margin_Max = "Margin_Max";

	/**
	 * Set Korridor - Unterschreitung.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMargin_Min (BigDecimal Margin_Min);

	/**
	 * Get Korridor - Unterschreitung.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getMargin_Min();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Margin_Min = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Margin_Min", null);
	String COLUMNNAME_Margin_Min = "Margin_Min";

	/**
	 * Set Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_PricingSystem_ID();

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

	String COLUMNNAME_M_Product_Flatrate_ID = "M_Product_Flatrate_ID";

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

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Name = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set When extending contract.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOnFlatrateTermExtend (java.lang.String OnFlatrateTermExtend);

	/**
	 * Get When extending contract.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getOnFlatrateTermExtend();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_OnFlatrateTermExtend = new ModelColumn<>(I_C_Flatrate_Conditions.class, "OnFlatrateTermExtend", null);
	String COLUMNNAME_OnFlatrateTermExtend = "OnFlatrateTermExtend";

	/**
	 * Set Drucktext.
	 * Bezeichnung, die auf dem Dokument oder der Korrespondenz gedruckt werden soll
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrintName (@Nullable java.lang.String PrintName);

	/**
	 * Get Drucktext.
	 * Bezeichnung, die auf dem Dokument oder der Korrespondenz gedruckt werden soll
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPrintName();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_PrintName = new ModelColumn<>(I_C_Flatrate_Conditions.class, "PrintName", null);
	String COLUMNNAME_PrintName = "PrintName";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Processed = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Processed", null);
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

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Processing = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Processing", null);
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

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Type_Clearing = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Type_Clearing", null);
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

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Type_Conditions = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Type_Conditions", null);
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

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Type_Flatrate = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Type_Flatrate", null);
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

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_UOMType = new ModelColumn<>(I_C_Flatrate_Conditions.class, "UOMType", null);
	String COLUMNNAME_UOMType = "UOMType";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Flatrate_Conditions, Object> COLUMN_Updated = new ModelColumn<>(I_C_Flatrate_Conditions.class, "Updated", null);
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
