/** Generated Model - DO NOT CHANGE */
package de.metas.flatrate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Flatrate_DataEntry
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Flatrate_DataEntry extends org.compiere.model.PO implements I_C_Flatrate_DataEntry, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1237507434L;

    /** Standard Constructor */
    public X_C_Flatrate_DataEntry (Properties ctx, int C_Flatrate_DataEntry_ID, String trxName)
    {
      super (ctx, C_Flatrate_DataEntry_ID, trxName);
      /** if (C_Flatrate_DataEntry_ID == 0)
        {
			setC_Flatrate_DataEntry_ID (0);
			setC_Flatrate_Term_ID (0);
			setC_Period_ID (0);
			setC_UOM_ID (0);
			setDocAction (null); // CO
			setDocStatus (null); // DR
			setM_Product_DataEntry_ID (0);
			setProcessed (false); // N
			setProcessing (false); // N
			setType (null);
        } */
    }

    /** Load Constructor */
    public X_C_Flatrate_DataEntry (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set Istmenge.
		@param ActualQty 
		Tatsächliche Menge der erbrachten Leistung (z.B gelieferte Teile)
	  */
	@Override
	public void setActualQty (java.math.BigDecimal ActualQty)
	{
		set_Value (COLUMNNAME_ActualQty, ActualQty);
	}

	/** Get Istmenge.
		@return Tatsächliche Menge der erbrachten Leistung (z.B gelieferte Teile)
	  */
	@Override
	public java.math.BigDecimal getActualQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualQty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Diff. Menge.
		@param ActualQtyDiffAbs 
		Absolute Differenz zwischen Plan- und Istmenge
	  */
	@Override
	public void setActualQtyDiffAbs (java.math.BigDecimal ActualQtyDiffAbs)
	{
		set_Value (COLUMNNAME_ActualQtyDiffAbs, ActualQtyDiffAbs);
	}

	/** Get Diff. Menge.
		@return Absolute Differenz zwischen Plan- und Istmenge
	  */
	@Override
	public java.math.BigDecimal getActualQtyDiffAbs () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualQtyDiffAbs);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Diff. Menge %.
		@param ActualQtyDiffPercent 
		Prozentuale Abweichung zwischen Plan- und Istmenge
	  */
	@Override
	public void setActualQtyDiffPercent (java.math.BigDecimal ActualQtyDiffPercent)
	{
		set_Value (COLUMNNAME_ActualQtyDiffPercent, ActualQtyDiffPercent);
	}

	/** Get Diff. Menge %.
		@return Prozentuale Abweichung zwischen Plan- und Istmenge
	  */
	@Override
	public java.math.BigDecimal getActualQtyDiffPercent () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualQtyDiffPercent);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Diff. Menge % eff..
		@param ActualQtyDiffPercentEff 
		Prozentuale Abweichung, ggf. verringert um den Korridor-Prozentbetrag. Dieser wert ist der prozentuale Auf- oder Abschlag auf den Pauschalenbetrag
	  */
	@Override
	public void setActualQtyDiffPercentEff (java.math.BigDecimal ActualQtyDiffPercentEff)
	{
		set_Value (COLUMNNAME_ActualQtyDiffPercentEff, ActualQtyDiffPercentEff);
	}

	/** Get Diff. Menge % eff..
		@return Prozentuale Abweichung, ggf. verringert um den Korridor-Prozentbetrag. Dieser wert ist der prozentuale Auf- oder Abschlag auf den Pauschalenbetrag
	  */
	@Override
	public java.math.BigDecimal getActualQtyDiffPercentEff () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualQtyDiffPercentEff);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Differenz/Maßeinheit.
		@param ActualQtyDiffPerUOM 
		Differenz zwischen Plan- und Istmenge pro Maßeinheit
	  */
	@Override
	public void setActualQtyDiffPerUOM (java.math.BigDecimal ActualQtyDiffPerUOM)
	{
		set_Value (COLUMNNAME_ActualQtyDiffPerUOM, ActualQtyDiffPerUOM);
	}

	/** Get Differenz/Maßeinheit.
		@return Differenz zwischen Plan- und Istmenge pro Maßeinheit
	  */
	@Override
	public java.math.BigDecimal getActualQtyDiffPerUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualQtyDiffPerUOM);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Istmenge/Maßeinheit.
		@param ActualQtyPerUnit 
		Tatsächliche Menge der erbrachten Leistung (z.B "Stück geliefert") pro pauschal abgerechnete Einheit (z.B. pro Pflegetag).
	  */
	@Override
	public void setActualQtyPerUnit (java.math.BigDecimal ActualQtyPerUnit)
	{
		set_Value (COLUMNNAME_ActualQtyPerUnit, ActualQtyPerUnit);
	}

	/** Get Istmenge/Maßeinheit.
		@return Tatsächliche Menge der erbrachten Leistung (z.B "Stück geliefert") pro pauschal abgerechnete Einheit (z.B. pro Pflegetag).
	  */
	@Override
	public java.math.BigDecimal getActualQtyPerUnit () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ActualQtyPerUnit);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_AD_User getAD_User_Reported() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_Reported_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User_Reported(org.compiere.model.I_AD_User AD_User_Reported)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_Reported_ID, org.compiere.model.I_AD_User.class, AD_User_Reported);
	}

	/** Set Gemeldet durch.
		@param AD_User_Reported_ID 
		Ansprechpartner auf Kunden-Seite, der die Pauschalen-Menge gemeldet hat.
	  */
	@Override
	public void setAD_User_Reported_ID (int AD_User_Reported_ID)
	{
		if (AD_User_Reported_ID < 1) 
			set_Value (COLUMNNAME_AD_User_Reported_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_Reported_ID, Integer.valueOf(AD_User_Reported_ID));
	}

	/** Get Gemeldet durch.
		@return Ansprechpartner auf Kunden-Seite, der die Pauschalen-Menge gemeldet hat.
	  */
	@Override
	public int getAD_User_Reported_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_Reported_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class);
	}

	@Override
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency)
	{
		set_ValueFromPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class, C_Currency);
	}

	/** Set Währung.
		@param C_Currency_ID 
		Die Währung für diesen Eintrag
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return Die Währung für diesen Eintrag
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Abrechnungssatz.
		@param C_Flatrate_DataEntry_ID Abrechnungssatz	  */
	@Override
	public void setC_Flatrate_DataEntry_ID (int C_Flatrate_DataEntry_ID)
	{
		if (C_Flatrate_DataEntry_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_DataEntry_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_DataEntry_ID, Integer.valueOf(C_Flatrate_DataEntry_ID));
	}

	/** Get Abrechnungssatz.
		@return Abrechnungssatz	  */
	@Override
	public int getC_Flatrate_DataEntry_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_DataEntry_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.flatrate.model.I_C_Flatrate_Term getC_Flatrate_Term() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.flatrate.model.I_C_Flatrate_Term.class);
	}

	@Override
	public void setC_Flatrate_Term(de.metas.flatrate.model.I_C_Flatrate_Term C_Flatrate_Term)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.flatrate.model.I_C_Flatrate_Term.class, C_Flatrate_Term);
	}

	/** Set Pauschale - Vertragsperiode.
		@param C_Flatrate_Term_ID Pauschale - Vertragsperiode	  */
	@Override
	public void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Term_ID, Integer.valueOf(C_Flatrate_Term_ID));
	}

	/** Get Pauschale - Vertragsperiode.
		@return Pauschale - Vertragsperiode	  */
	@Override
	public int getC_Flatrate_Term_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Term_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate getC_Invoice_Candidate_Corr() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Candidate_Corr_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate.class);
	}

	@Override
	public void setC_Invoice_Candidate_Corr(de.metas.invoicecandidate.model.I_C_Invoice_Candidate C_Invoice_Candidate_Corr)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Candidate_Corr_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate.class, C_Invoice_Candidate_Corr);
	}

	/** Set Rechnungskand. Nachzahlung/Erstattung.
		@param C_Invoice_Candidate_Corr_ID Rechnungskand. Nachzahlung/Erstattung	  */
	@Override
	public void setC_Invoice_Candidate_Corr_ID (int C_Invoice_Candidate_Corr_ID)
	{
		if (C_Invoice_Candidate_Corr_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_Corr_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_Corr_ID, Integer.valueOf(C_Invoice_Candidate_Corr_ID));
	}

	/** Get Rechnungskand. Nachzahlung/Erstattung.
		@return Rechnungskand. Nachzahlung/Erstattung	  */
	@Override
	public int getC_Invoice_Candidate_Corr_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Candidate_Corr_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate getC_Invoice_Candidate() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Candidate_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate.class);
	}

	@Override
	public void setC_Invoice_Candidate(de.metas.invoicecandidate.model.I_C_Invoice_Candidate C_Invoice_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Candidate_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate.class, C_Invoice_Candidate);
	}

	/** Set Rechnungskandidat.
		@param C_Invoice_Candidate_ID 
		Eindeutige Identifikationsnummer eines Rechnungskandidaten
	  */
	@Override
	public void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID)
	{
		if (C_Invoice_Candidate_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, Integer.valueOf(C_Invoice_Candidate_ID));
	}

	/** Get Rechnungskandidat.
		@return Eindeutige Identifikationsnummer eines Rechnungskandidaten
	  */
	@Override
	public int getC_Invoice_Candidate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Candidate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class);
	}

	@Override
	public void setC_Period(org.compiere.model.I_C_Period C_Period)
	{
		set_ValueFromPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class, C_Period);
	}

	/** Set Periode.
		@param C_Period_ID 
		Periode des Kalenders
	  */
	@Override
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Periode.
		@return Periode des Kalenders
	  */
	@Override
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Maßeinheit
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Gemeldet am.
		@param Date_Reported 
		Datum, an dem die Pauschalen-Zahl von Seiten des Kunden gemeldet wurde
	  */
	@Override
	public void setDate_Reported (java.sql.Timestamp Date_Reported)
	{
		set_Value (COLUMNNAME_Date_Reported, Date_Reported);
	}

	/** Get Gemeldet am.
		@return Datum, an dem die Pauschalen-Zahl von Seiten des Kunden gemeldet wurde
	  */
	@Override
	public java.sql.Timestamp getDate_Reported () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_Date_Reported);
	}

	/** 
	 * DocAction AD_Reference_ID=135
	 * Reference name: _Document Action
	 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse_Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse_Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re_Activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** None = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** WaitComplete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Set Belegverarbeitung.
		@param DocAction 
		Der zukünftige Status des Belegs
	  */
	@Override
	public void setDocAction (java.lang.String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Belegverarbeitung.
		@return Der zukünftige Status des Belegs
	  */
	@Override
	public java.lang.String getDocAction () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocAction);
	}

	/** 
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Belegstatus.
		@param DocStatus 
		The current status of the document
	  */
	@Override
	public void setDocStatus (java.lang.String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Belegstatus.
		@return The current status of the document
	  */
	@Override
	public java.lang.String getDocStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Pauschalenbetrag.
		@param FlatrateAmt 
		Pauschal zu Zahlender Betrag, ohne Berücksichtigung der Korrektur wegen Über- oder Unterschreibung.
	  */
	@Override
	public void setFlatrateAmt (java.math.BigDecimal FlatrateAmt)
	{
		set_Value (COLUMNNAME_FlatrateAmt, FlatrateAmt);
	}

	/** Get Pauschalenbetrag.
		@return Pauschal zu Zahlender Betrag, ohne Berücksichtigung der Korrektur wegen Über- oder Unterschreibung.
	  */
	@Override
	public java.math.BigDecimal getFlatrateAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FlatrateAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Nachzahlung/Erstattung.
		@param FlatrateAmtCorr 
		Aufgrund von Über-/Unterschreitung der Planmenge zusätzlich zu zahlender bzw. gutzuschreibender Betrag.
	  */
	@Override
	public void setFlatrateAmtCorr (java.math.BigDecimal FlatrateAmtCorr)
	{
		set_Value (COLUMNNAME_FlatrateAmtCorr, FlatrateAmtCorr);
	}

	/** Get Nachzahlung/Erstattung.
		@return Aufgrund von Über-/Unterschreitung der Planmenge zusätzlich zu zahlender bzw. gutzuschreibender Betrag.
	  */
	@Override
	public java.math.BigDecimal getFlatrateAmtCorr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FlatrateAmtCorr);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Betrag/Maßeinheit.
		@param FlatrateAmtPerUOM Betrag/Maßeinheit	  */
	@Override
	public void setFlatrateAmtPerUOM (java.math.BigDecimal FlatrateAmtPerUOM)
	{
		set_Value (COLUMNNAME_FlatrateAmtPerUOM, FlatrateAmtPerUOM);
	}

	/** Get Betrag/Maßeinheit.
		@return Betrag/Maßeinheit	  */
	@Override
	public java.math.BigDecimal getFlatrateAmtPerUOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FlatrateAmtPerUOM);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Planspiel.
		@param IsSimulation Planspiel	  */
	@Override
	public void setIsSimulation (boolean IsSimulation)
	{
		throw new IllegalArgumentException ("IsSimulation is virtual column");	}

	/** Get Planspiel.
		@return Planspiel	  */
	@Override
	public boolean isSimulation () 
	{
		Object oo = get_Value(COLUMNNAME_IsSimulation);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product_DataEntry() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_DataEntry_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product_DataEntry(org.compiere.model.I_M_Product M_Product_DataEntry)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_DataEntry_ID, org.compiere.model.I_M_Product.class, M_Product_DataEntry);
	}

	/** Set Produkt.
		@param M_Product_DataEntry_ID 
		Produkt, zu dem die Depotgebühr erhoben wird
	  */
	@Override
	public void setM_Product_DataEntry_ID (int M_Product_DataEntry_ID)
	{
		if (M_Product_DataEntry_ID < 1) 
			set_Value (COLUMNNAME_M_Product_DataEntry_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_DataEntry_ID, Integer.valueOf(M_Product_DataEntry_ID));
	}

	/** Get Produkt.
		@return Produkt, zu dem die Depotgebühr erhoben wird
	  */
	@Override
	public int getM_Product_DataEntry_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_DataEntry_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Notiz.
		@param Note 
		Optional weitere Information für ein Dokument
	  */
	@Override
	public void setNote (java.lang.String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	/** Get Notiz.
		@return Optional weitere Information für ein Dokument
	  */
	@Override
	public java.lang.String getNote () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Note);
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	@Override
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Geplante Menge.
		@param Qty_Planned 
		Vorab eingeplante Pauschalen-Menge
	  */
	@Override
	public void setQty_Planned (java.math.BigDecimal Qty_Planned)
	{
		set_Value (COLUMNNAME_Qty_Planned, Qty_Planned);
	}

	/** Get Geplante Menge.
		@return Vorab eingeplante Pauschalen-Menge
	  */
	@Override
	public java.math.BigDecimal getQty_Planned () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty_Planned);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Gemeldete Menge.
		@param Qty_Reported 
		Vom Kunden gemeldete Menge, die ggf. als Pauschale in Rechnung gestellt wird.
	  */
	@Override
	public void setQty_Reported (java.math.BigDecimal Qty_Reported)
	{
		set_Value (COLUMNNAME_Qty_Reported, Qty_Reported);
	}

	/** Get Gemeldete Menge.
		@return Vom Kunden gemeldete Menge, die ggf. als Pauschale in Rechnung gestellt wird.
	  */
	@Override
	public java.math.BigDecimal getQty_Reported () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty_Reported);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** 
	 * Type AD_Reference_ID=540263
	 * Reference name: C_Flatrate_DataEntry Type
	 */
	public static final int TYPE_AD_Reference_ID=540263;
	/** Invoicing_PeriodBased = IP */
	public static final String TYPE_Invoicing_PeriodBased = "IP";
	/** Correction_PeriodBased = CP */
	public static final String TYPE_Correction_PeriodBased = "CP";
	/** Procurement_PeriodBased = PC */
	public static final String TYPE_Procurement_PeriodBased = "PC";
	/** Set Art.
		@param Type 
		Type of Validation (SQL, Java Script, Java Language)
	  */
	@Override
	public void setType (java.lang.String Type)
	{

		set_ValueNoCheck (COLUMNNAME_Type, Type);
	}

	/** Get Art.
		@return Type of Validation (SQL, Java Script, Java Language)
	  */
	@Override
	public java.lang.String getType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Type);
	}
}