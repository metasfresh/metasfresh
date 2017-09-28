package de.metas.contracts.model;


/** Generated Interface for C_Flatrate_DataEntry
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Flatrate_DataEntry 
{

    /** TableName=C_Flatrate_DataEntry */
    public static final String Table_Name = "C_Flatrate_DataEntry";

    /** AD_Table_ID=540309 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

    /** Load Meta Data */

	/**
	 * Set Istmenge.
	 * Tatsächliche Menge der erbrachten Leistung (z.B gelieferte Teile)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setActualQty (java.math.BigDecimal ActualQty);

	/**
	 * Get Istmenge.
	 * Tatsächliche Menge der erbrachten Leistung (z.B gelieferte Teile)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getActualQty();

    /** Column definition for ActualQty */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_ActualQty = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "ActualQty", null);
    /** Column name ActualQty */
    public static final String COLUMNNAME_ActualQty = "ActualQty";

	/**
	 * Set Diff. Menge.
	 * Absolute Differenz zwischen Plan- und Istmenge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setActualQtyDiffAbs (java.math.BigDecimal ActualQtyDiffAbs);

	/**
	 * Get Diff. Menge.
	 * Absolute Differenz zwischen Plan- und Istmenge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getActualQtyDiffAbs();

    /** Column definition for ActualQtyDiffAbs */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_ActualQtyDiffAbs = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "ActualQtyDiffAbs", null);
    /** Column name ActualQtyDiffAbs */
    public static final String COLUMNNAME_ActualQtyDiffAbs = "ActualQtyDiffAbs";

	/**
	 * Set Diff. Menge %.
	 * Prozentuale Abweichung zwischen Plan- und Istmenge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setActualQtyDiffPercent (java.math.BigDecimal ActualQtyDiffPercent);

	/**
	 * Get Diff. Menge %.
	 * Prozentuale Abweichung zwischen Plan- und Istmenge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getActualQtyDiffPercent();

    /** Column definition for ActualQtyDiffPercent */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_ActualQtyDiffPercent = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "ActualQtyDiffPercent", null);
    /** Column name ActualQtyDiffPercent */
    public static final String COLUMNNAME_ActualQtyDiffPercent = "ActualQtyDiffPercent";

	/**
	 * Set Diff. Menge % eff..
	 * Prozentuale Abweichung, ggf. verringert um den Korridor-Prozentbetrag. Dieser wert ist der prozentuale Auf- oder Abschlag auf den Pauschalenbetrag
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setActualQtyDiffPercentEff (java.math.BigDecimal ActualQtyDiffPercentEff);

	/**
	 * Get Diff. Menge % eff..
	 * Prozentuale Abweichung, ggf. verringert um den Korridor-Prozentbetrag. Dieser wert ist der prozentuale Auf- oder Abschlag auf den Pauschalenbetrag
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getActualQtyDiffPercentEff();

    /** Column definition for ActualQtyDiffPercentEff */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_ActualQtyDiffPercentEff = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "ActualQtyDiffPercentEff", null);
    /** Column name ActualQtyDiffPercentEff */
    public static final String COLUMNNAME_ActualQtyDiffPercentEff = "ActualQtyDiffPercentEff";

	/**
	 * Set Differenz/Maßeinheit.
	 * Differenz zwischen Plan- und Istmenge pro Maßeinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setActualQtyDiffPerUOM (java.math.BigDecimal ActualQtyDiffPerUOM);

	/**
	 * Get Differenz/Maßeinheit.
	 * Differenz zwischen Plan- und Istmenge pro Maßeinheit
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getActualQtyDiffPerUOM();

    /** Column definition for ActualQtyDiffPerUOM */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_ActualQtyDiffPerUOM = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "ActualQtyDiffPerUOM", null);
    /** Column name ActualQtyDiffPerUOM */
    public static final String COLUMNNAME_ActualQtyDiffPerUOM = "ActualQtyDiffPerUOM";

	/**
	 * Set Istmenge/Maßeinheit.
	 * Tatsächliche Menge der erbrachten Leistung (z.B "Stück geliefert") pro pauschal abgerechnete Einheit (z.B. pro Pflegetag).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setActualQtyPerUnit (java.math.BigDecimal ActualQtyPerUnit);

	/**
	 * Get Istmenge/Maßeinheit.
	 * Tatsächliche Menge der erbrachten Leistung (z.B "Stück geliefert") pro pauschal abgerechnete Einheit (z.B. pro Pflegetag).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getActualQtyPerUnit();

    /** Column definition for ActualQtyPerUnit */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_ActualQtyPerUnit = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "ActualQtyPerUnit", null);
    /** Column name ActualQtyPerUnit */
    public static final String COLUMNNAME_ActualQtyPerUnit = "ActualQtyPerUnit";

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
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, org.compiere.model.I_AD_Client>(I_C_Flatrate_DataEntry.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, org.compiere.model.I_AD_Org>(I_C_Flatrate_DataEntry.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Gemeldet durch.
	 * Ansprechpartner auf Kunden-Seite, der die Pauschalen-Menge gemeldet hat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_User_Reported_ID (int AD_User_Reported_ID);

	/**
	 * Get Gemeldet durch.
	 * Ansprechpartner auf Kunden-Seite, der die Pauschalen-Menge gemeldet hat.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_User_Reported_ID();

	public org.compiere.model.I_AD_User getAD_User_Reported();

	public void setAD_User_Reported(org.compiere.model.I_AD_User AD_User_Reported);

    /** Column definition for AD_User_Reported_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, org.compiere.model.I_AD_User> COLUMN_AD_User_Reported_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, org.compiere.model.I_AD_User>(I_C_Flatrate_DataEntry.class, "AD_User_Reported_ID", org.compiere.model.I_AD_User.class);
    /** Column name AD_User_Reported_ID */
    public static final String COLUMNNAME_AD_User_Reported_ID = "AD_User_Reported_ID";

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

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, org.compiere.model.I_C_Currency>(I_C_Flatrate_DataEntry.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Abrechnungssatz.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Flatrate_DataEntry_ID (int C_Flatrate_DataEntry_ID);

	/**
	 * Get Abrechnungssatz.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Flatrate_DataEntry_ID();

    /** Column definition for C_Flatrate_DataEntry_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_C_Flatrate_DataEntry_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "C_Flatrate_DataEntry_ID", null);
    /** Column name C_Flatrate_DataEntry_ID */
    public static final String COLUMNNAME_C_Flatrate_DataEntry_ID = "C_Flatrate_DataEntry_ID";

	/**
	 * Set Pauschale - Vertragsperiode.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Pauschale - Vertragsperiode.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Flatrate_Term_ID();

	public de.metas.contracts.model.I_C_Flatrate_Term getC_Flatrate_Term();

	public void setC_Flatrate_Term(de.metas.contracts.model.I_C_Flatrate_Term C_Flatrate_Term);

    /** Column definition for C_Flatrate_Term_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, de.metas.contracts.model.I_C_Flatrate_Term> COLUMN_C_Flatrate_Term_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, de.metas.contracts.model.I_C_Flatrate_Term>(I_C_Flatrate_DataEntry.class, "C_Flatrate_Term_ID", de.metas.contracts.model.I_C_Flatrate_Term.class);
    /** Column name C_Flatrate_Term_ID */
    public static final String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

	/**
	 * Set Rechnungskand. Nachzahlung/Erstattung.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_Corr_ID (int C_Invoice_Candidate_Corr_ID);

	/**
	 * Get Rechnungskand. Nachzahlung/Erstattung.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_Corr_ID();

	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate getC_Invoice_Candidate_Corr();

	public void setC_Invoice_Candidate_Corr(de.metas.invoicecandidate.model.I_C_Invoice_Candidate C_Invoice_Candidate_Corr);

    /** Column definition for C_Invoice_Candidate_Corr_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, de.metas.invoicecandidate.model.I_C_Invoice_Candidate> COLUMN_C_Invoice_Candidate_Corr_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, de.metas.invoicecandidate.model.I_C_Invoice_Candidate>(I_C_Flatrate_DataEntry.class, "C_Invoice_Candidate_Corr_ID", de.metas.invoicecandidate.model.I_C_Invoice_Candidate.class);
    /** Column name C_Invoice_Candidate_Corr_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_Corr_ID = "C_Invoice_Candidate_Corr_ID";

	/**
	 * Set Rechnungskandidat.
	 * Eindeutige Identifikationsnummer eines Rechnungskandidaten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID);

	/**
	 * Get Rechnungskandidat.
	 * Eindeutige Identifikationsnummer eines Rechnungskandidaten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_Candidate_ID();

	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate getC_Invoice_Candidate();

	public void setC_Invoice_Candidate(de.metas.invoicecandidate.model.I_C_Invoice_Candidate C_Invoice_Candidate);

    /** Column definition for C_Invoice_Candidate_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, de.metas.invoicecandidate.model.I_C_Invoice_Candidate> COLUMN_C_Invoice_Candidate_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, de.metas.invoicecandidate.model.I_C_Invoice_Candidate>(I_C_Flatrate_DataEntry.class, "C_Invoice_Candidate_ID", de.metas.invoicecandidate.model.I_C_Invoice_Candidate.class);
    /** Column name C_Invoice_Candidate_ID */
    public static final String COLUMNNAME_C_Invoice_Candidate_ID = "C_Invoice_Candidate_ID";

	/**
	 * Set Periode.
	 * Periode des Kalenders
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Periode.
	 * Periode des Kalenders
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Period_ID();

	public org.compiere.model.I_C_Period getC_Period();

	public void setC_Period(org.compiere.model.I_C_Period C_Period);

    /** Column definition for C_Period_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, org.compiere.model.I_C_Period> COLUMN_C_Period_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, org.compiere.model.I_C_Period>(I_C_Flatrate_DataEntry.class, "C_Period_ID", org.compiere.model.I_C_Period.class);
    /** Column name C_Period_ID */
    public static final String COLUMNNAME_C_Period_ID = "C_Period_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, org.compiere.model.I_AD_User>(I_C_Flatrate_DataEntry.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, org.compiere.model.I_C_UOM> COLUMN_C_UOM_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, org.compiere.model.I_C_UOM>(I_C_Flatrate_DataEntry.class, "C_UOM_ID", org.compiere.model.I_C_UOM.class);
    /** Column name C_UOM_ID */
    public static final String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Gemeldet am.
	 * Datum, an dem die Pauschalen-Zahl von Seiten des Kunden gemeldet wurde
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDate_Reported (java.sql.Timestamp Date_Reported);

	/**
	 * Get Gemeldet am.
	 * Datum, an dem die Pauschalen-Zahl von Seiten des Kunden gemeldet wurde
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDate_Reported();

    /** Column definition for Date_Reported */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_Date_Reported = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "Date_Reported", null);
    /** Column name Date_Reported */
    public static final String COLUMNNAME_Date_Reported = "Date_Reported";

	/**
	 * Set Belegverarbeitung.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocAction (java.lang.String DocAction);

	/**
	 * Get Belegverarbeitung.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocAction();

    /** Column definition for DocAction */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_DocAction = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "DocAction", null);
    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Belegstatus.
	 * The current status of the document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocStatus();

    /** Column definition for DocStatus */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_DocStatus = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "DocStatus", null);
    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Pauschalenbetrag.
	 * Pauschal zu Zahlender Betrag, ohne Berücksichtigung der Korrektur wegen Über- oder Unterschreibung.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFlatrateAmt (java.math.BigDecimal FlatrateAmt);

	/**
	 * Get Pauschalenbetrag.
	 * Pauschal zu Zahlender Betrag, ohne Berücksichtigung der Korrektur wegen Über- oder Unterschreibung.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFlatrateAmt();

    /** Column definition for FlatrateAmt */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_FlatrateAmt = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "FlatrateAmt", null);
    /** Column name FlatrateAmt */
    public static final String COLUMNNAME_FlatrateAmt = "FlatrateAmt";

	/**
	 * Set Nachzahlung/Erstattung.
	 * Aufgrund von Über-/Unterschreitung der Planmenge zusätzlich zu zahlender bzw. gutzuschreibender Betrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFlatrateAmtCorr (java.math.BigDecimal FlatrateAmtCorr);

	/**
	 * Get Nachzahlung/Erstattung.
	 * Aufgrund von Über-/Unterschreitung der Planmenge zusätzlich zu zahlender bzw. gutzuschreibender Betrag.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFlatrateAmtCorr();

    /** Column definition for FlatrateAmtCorr */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_FlatrateAmtCorr = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "FlatrateAmtCorr", null);
    /** Column name FlatrateAmtCorr */
    public static final String COLUMNNAME_FlatrateAmtCorr = "FlatrateAmtCorr";

	/**
	 * Set Betrag/Maßeinheit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFlatrateAmtPerUOM (java.math.BigDecimal FlatrateAmtPerUOM);

	/**
	 * Get Betrag/Maßeinheit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getFlatrateAmtPerUOM();

    /** Column definition for FlatrateAmtPerUOM */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_FlatrateAmtPerUOM = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "FlatrateAmtPerUOM", null);
    /** Column name FlatrateAmtPerUOM */
    public static final String COLUMNNAME_FlatrateAmtPerUOM = "FlatrateAmtPerUOM";

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
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Planspiel.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	public void setIsSimulation (boolean IsSimulation);

	/**
	 * Get Planspiel.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	public boolean isSimulation();

    /** Column definition for IsSimulation */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_IsSimulation = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "IsSimulation", null);
    /** Column name IsSimulation */
    public static final String COLUMNNAME_IsSimulation = "IsSimulation";

	/**
	 * Set Produkt.
	 * Produkt, zu dem die Depotgebühr erhoben wird
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Product_DataEntry_ID (int M_Product_DataEntry_ID);

	/**
	 * Get Produkt.
	 * Produkt, zu dem die Depotgebühr erhoben wird
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Product_DataEntry_ID();

	public org.compiere.model.I_M_Product getM_Product_DataEntry();

	public void setM_Product_DataEntry(org.compiere.model.I_M_Product M_Product_DataEntry);

    /** Column definition for M_Product_DataEntry_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, org.compiere.model.I_M_Product> COLUMN_M_Product_DataEntry_ID = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, org.compiere.model.I_M_Product>(I_C_Flatrate_DataEntry.class, "M_Product_DataEntry_ID", org.compiere.model.I_M_Product.class);
    /** Column name M_Product_DataEntry_ID */
    public static final String COLUMNNAME_M_Product_DataEntry_ID = "M_Product_DataEntry_ID";

	/**
	 * Set Notiz.
	 * Optional weitere Information für ein Dokument
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setNote (java.lang.String Note);

	/**
	 * Get Notiz.
	 * Optional weitere Information für ein Dokument
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getNote();

    /** Column definition for Note */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_Note = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "Note", null);
    /** Column name Note */
    public static final String COLUMNNAME_Note = "Note";

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
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Geplante Menge.
	 * Vorab eingeplante Pauschalen-Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQty_Planned (java.math.BigDecimal Qty_Planned);

	/**
	 * Get Geplante Menge.
	 * Vorab eingeplante Pauschalen-Menge
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty_Planned();

    /** Column definition for Qty_Planned */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_Qty_Planned = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "Qty_Planned", null);
    /** Column name Qty_Planned */
    public static final String COLUMNNAME_Qty_Planned = "Qty_Planned";

	/**
	 * Set Gemeldete Menge.
	 * Vom Kunden gemeldete Menge, die ggf. als Pauschale in Rechnung gestellt wird.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setQty_Reported (java.math.BigDecimal Qty_Reported);

	/**
	 * Get Gemeldete Menge.
	 * Vom Kunden gemeldete Menge, die ggf. als Pauschale in Rechnung gestellt wird.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getQty_Reported();

    /** Column definition for Qty_Reported */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_Qty_Reported = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "Qty_Reported", null);
    /** Column name Qty_Reported */
    public static final String COLUMNNAME_Qty_Reported = "Qty_Reported";

	/**
	 * Set Art.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setType (java.lang.String Type);

	/**
	 * Get Art.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getType();

    /** Column definition for Type */
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_Type = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "Type", null);
    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

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
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, Object>(I_C_Flatrate_DataEntry.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Flatrate_DataEntry, org.compiere.model.I_AD_User>(I_C_Flatrate_DataEntry.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
