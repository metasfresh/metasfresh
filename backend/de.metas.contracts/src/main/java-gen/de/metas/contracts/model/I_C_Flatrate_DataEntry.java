package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_Flatrate_DataEntry
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Flatrate_DataEntry 
{

	String Table_Name = "C_Flatrate_DataEntry";

//	/** AD_Table_ID=540309 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Istmenge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setActualQty (@Nullable BigDecimal ActualQty);

	/**
	 * Get Istmenge.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getActualQty();

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_ActualQty = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "ActualQty", null);
	String COLUMNNAME_ActualQty = "ActualQty";

	/**
	 * Set Diff. Menge.
	 * Absolute Differenz zwischen Plan- und Istmenge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setActualQtyDiffAbs (@Nullable BigDecimal ActualQtyDiffAbs);

	/**
	 * Get Diff. Menge.
	 * Absolute Differenz zwischen Plan- und Istmenge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getActualQtyDiffAbs();

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_ActualQtyDiffAbs = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "ActualQtyDiffAbs", null);
	String COLUMNNAME_ActualQtyDiffAbs = "ActualQtyDiffAbs";

	/**
	 * Set Diff. Menge %.
	 * Prozentuale Abweichung zwischen Plan- und Istmenge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setActualQtyDiffPercent (@Nullable BigDecimal ActualQtyDiffPercent);

	/**
	 * Get Diff. Menge %.
	 * Prozentuale Abweichung zwischen Plan- und Istmenge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getActualQtyDiffPercent();

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_ActualQtyDiffPercent = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "ActualQtyDiffPercent", null);
	String COLUMNNAME_ActualQtyDiffPercent = "ActualQtyDiffPercent";

	/**
	 * Set Diff. Menge % eff..
	 * Prozentuale Abweichung, ggf. verringert um den Korridor-Prozentbetrag. Dieser wert ist der prozentuale Auf- oder Abschlag auf den Pauschalenbetrag
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setActualQtyDiffPercentEff (@Nullable BigDecimal ActualQtyDiffPercentEff);

	/**
	 * Get Diff. Menge % eff..
	 * Prozentuale Abweichung, ggf. verringert um den Korridor-Prozentbetrag. Dieser wert ist der prozentuale Auf- oder Abschlag auf den Pauschalenbetrag
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getActualQtyDiffPercentEff();

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_ActualQtyDiffPercentEff = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "ActualQtyDiffPercentEff", null);
	String COLUMNNAME_ActualQtyDiffPercentEff = "ActualQtyDiffPercentEff";

	/**
	 * Set Differenz/Maßeinheit.
	 * Differenz zwischen Plan- und Istmenge pro Maßeinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setActualQtyDiffPerUOM (@Nullable BigDecimal ActualQtyDiffPerUOM);

	/**
	 * Get Differenz/Maßeinheit.
	 * Differenz zwischen Plan- und Istmenge pro Maßeinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getActualQtyDiffPerUOM();

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_ActualQtyDiffPerUOM = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "ActualQtyDiffPerUOM", null);
	String COLUMNNAME_ActualQtyDiffPerUOM = "ActualQtyDiffPerUOM";

	/**
	 * Set Istmenge/Maßeinheit.
	 * Tatsächliche Menge der erbrachten Leistung (z.B "Stück geliefert") pro pauschal abgerechnete Einheit (z.B. pro Pflegetag).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setActualQtyPerUnit (@Nullable BigDecimal ActualQtyPerUnit);

	/**
	 * Get Istmenge/Maßeinheit.
	 * Tatsächliche Menge der erbrachten Leistung (z.B "Stück geliefert") pro pauschal abgerechnete Einheit (z.B. pro Pflegetag).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getActualQtyPerUnit();

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_ActualQtyPerUnit = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "ActualQtyPerUnit", null);
	String COLUMNNAME_ActualQtyPerUnit = "ActualQtyPerUnit";

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
	 * Set Gemeldet durch.
	 * Ansprechpartner auf Kunden-Seite, der die Pauschalen-Menge gemeldet hat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_Reported_ID (int AD_User_Reported_ID);

	/**
	 * Get Gemeldet durch.
	 * Ansprechpartner auf Kunden-Seite, der die Pauschalen-Menge gemeldet hat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_Reported_ID();

	String COLUMNNAME_AD_User_Reported_ID = "AD_User_Reported_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Abrechnungssatz.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_DataEntry_ID (int C_Flatrate_DataEntry_ID);

	/**
	 * Get Abrechnungssatz.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_DataEntry_ID();

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_C_Flatrate_DataEntry_ID = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "C_Flatrate_DataEntry_ID", null);
	String COLUMNNAME_C_Flatrate_DataEntry_ID = "C_Flatrate_DataEntry_ID";

	/**
	 * Set Flatrate Term.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Flatrate Term.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Term_ID();

	de.metas.contracts.model.I_C_Flatrate_Term getC_Flatrate_Term();

	void setC_Flatrate_Term(de.metas.contracts.model.I_C_Flatrate_Term C_Flatrate_Term);

	ModelColumn<I_C_Flatrate_DataEntry, de.metas.contracts.model.I_C_Flatrate_Term> COLUMN_C_Flatrate_Term_ID = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "C_Flatrate_Term_ID", de.metas.contracts.model.I_C_Flatrate_Term.class);
	String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set Rechnungskand. Nachzahlung/Erstattung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Candidate_Corr_ID (int C_Invoice_Candidate_Corr_ID);

	/**
	 * Get Rechnungskand. Nachzahlung/Erstattung.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Candidate_Corr_ID();

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_C_Invoice_Candidate_Corr_ID = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "C_Invoice_Candidate_Corr_ID", null);
	String COLUMNNAME_C_Invoice_Candidate_Corr_ID = "C_Invoice_Candidate_Corr_ID";

	/**
	 * Set Invoice candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID);

	/**
	 * Get Invoice candidate.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Candidate_ID();

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_C_Invoice_Candidate_ID = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "C_Invoice_Candidate_ID", null);
	String COLUMNNAME_C_Invoice_Candidate_ID = "C_Invoice_Candidate_ID";

	/**
	 * Set Period.
	 * Period of the Calendar
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Period.
	 * Period of the Calendar
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Period_ID();

	org.compiere.model.I_C_Period getC_Period();

	void setC_Period(org.compiere.model.I_C_Period C_Period);

	ModelColumn<I_C_Flatrate_DataEntry, org.compiere.model.I_C_Period> COLUMN_C_Period_ID = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "C_Period_ID", org.compiere.model.I_C_Period.class);
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

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_Created = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Search
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
	 * Set Gemeldet am.
	 * Datum, an dem die Pauschalen-Zahl von Seiten des Kunden gemeldet wurde
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDate_Reported (@Nullable java.sql.Timestamp Date_Reported);

	/**
	 * Get Gemeldet am.
	 * Datum, an dem die Pauschalen-Zahl von Seiten des Kunden gemeldet wurde
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDate_Reported();

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_Date_Reported = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "Date_Reported", null);
	String COLUMNNAME_Date_Reported = "Date_Reported";

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

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_DocAction = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "DocAction", null);
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

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_DocStatus = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Pauschalenbetrag.
	 * Pauschal zu Zahlender Betrag, ohne Berücksichtigung der Korrektur wegen Über- oder Unterschreibung.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFlatrateAmt (@Nullable BigDecimal FlatrateAmt);

	/**
	 * Get Pauschalenbetrag.
	 * Pauschal zu Zahlender Betrag, ohne Berücksichtigung der Korrektur wegen Über- oder Unterschreibung.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getFlatrateAmt();

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_FlatrateAmt = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "FlatrateAmt", null);
	String COLUMNNAME_FlatrateAmt = "FlatrateAmt";

	/**
	 * Set Nachzahlung/Erstattung.
	 * Aufgrund von Über-/Unterschreitung der Planmenge zusätzlich zu zahlender bzw. gutzuschreibender Betrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFlatrateAmtCorr (@Nullable BigDecimal FlatrateAmtCorr);

	/**
	 * Get Nachzahlung/Erstattung.
	 * Aufgrund von Über-/Unterschreitung der Planmenge zusätzlich zu zahlender bzw. gutzuschreibender Betrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getFlatrateAmtCorr();

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_FlatrateAmtCorr = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "FlatrateAmtCorr", null);
	String COLUMNNAME_FlatrateAmtCorr = "FlatrateAmtCorr";

	/**
	 * Set Betrag/Maßeinheit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFlatrateAmtPerUOM (@Nullable BigDecimal FlatrateAmtPerUOM);

	/**
	 * Get Betrag/Maßeinheit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getFlatrateAmtPerUOM();

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_FlatrateAmtPerUOM = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "FlatrateAmtPerUOM", null);
	String COLUMNNAME_FlatrateAmtPerUOM = "FlatrateAmtPerUOM";

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

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Simulation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsSimulation (boolean IsSimulation);

	/**
	 * Get Simulation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isSimulation();

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_IsSimulation = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "IsSimulation", null);
	String COLUMNNAME_IsSimulation = "IsSimulation";

	/**
	 * Set Produkt.
	 * Produkt, zu dem die Depotgebühr erhoben wird
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_DataEntry_ID (int M_Product_DataEntry_ID);

	/**
	 * Get Produkt.
	 * Produkt, zu dem die Depotgebühr erhoben wird
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_DataEntry_ID();

	String COLUMNNAME_M_Product_DataEntry_ID = "M_Product_DataEntry_ID";

	/**
	 * Set Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNote (@Nullable java.lang.String Note);

	/**
	 * Get Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNote();

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_Note = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "Note", null);
	String COLUMNNAME_Note = "Note";

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

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_Processed = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "Processed", null);
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

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_Processing = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Geplante Menge.
	 * Vorab eingeplante Pauschalen-Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty_Planned (@Nullable BigDecimal Qty_Planned);

	/**
	 * Get Geplante Menge.
	 * Vorab eingeplante Pauschalen-Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty_Planned();

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_Qty_Planned = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "Qty_Planned", null);
	String COLUMNNAME_Qty_Planned = "Qty_Planned";

	/**
	 * Set Gemeldete Menge.
	 * Vom Kunden gemeldete Menge, die ggf. als Pauschale in Rechnung gestellt wird.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty_Reported (@Nullable BigDecimal Qty_Reported);

	/**
	 * Get Gemeldete Menge.
	 * Vom Kunden gemeldete Menge, die ggf. als Pauschale in Rechnung gestellt wird.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty_Reported();

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_Qty_Reported = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "Qty_Reported", null);
	String COLUMNNAME_Qty_Reported = "Qty_Reported";

	/**
	 * Set Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setType (java.lang.String Type);

	/**
	 * Get Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getType();

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_Type = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "Type", null);
	String COLUMNNAME_Type = "Type";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_Updated = new ModelColumn<>(I_C_Flatrate_DataEntry.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
