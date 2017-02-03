/** Generated Model - DO NOT CHANGE */
package de.metas.invoicecandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for C_Invoice_Line_Alloc
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Invoice_Line_Alloc extends org.compiere.model.PO implements I_C_Invoice_Line_Alloc, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -279808115L;

    /** Standard Constructor */
    public X_C_Invoice_Line_Alloc (Properties ctx, int C_Invoice_Line_Alloc_ID, String trxName)
    {
      super (ctx, C_Invoice_Line_Alloc_ID, trxName);
      /** if (C_Invoice_Line_Alloc_ID == 0)
        {
			setC_Invoice_Candidate_ID (0);
			setC_Invoice_Line_Alloc_ID (0);
			setC_InvoiceLine_ID (0);
			setQtyInvoiced (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_C_Invoice_Line_Alloc (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg getC_Invoice_Candidate_Agg() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Candidate_Agg_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg.class);
	}

	@Override
	public void setC_Invoice_Candidate_Agg(de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg C_Invoice_Candidate_Agg)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Candidate_Agg_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg.class, C_Invoice_Candidate_Agg);
	}

	/** Set Aggregator.
		@param C_Invoice_Candidate_Agg_ID 
		Definiert Richtlinien zur Aggregation von Datensätzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz
	  */
	@Override
	public void setC_Invoice_Candidate_Agg_ID (int C_Invoice_Candidate_Agg_ID)
	{
		if (C_Invoice_Candidate_Agg_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_Agg_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_Agg_ID, Integer.valueOf(C_Invoice_Candidate_Agg_ID));
	}

	/** Get Aggregator.
		@return Definiert Richtlinien zur Aggregation von Datensätzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz
	  */
	@Override
	public int getC_Invoice_Candidate_Agg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Candidate_Agg_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_ID, Integer.valueOf(C_Invoice_Candidate_ID));
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

	/** Set Rechnungskandidat - Rechungszeile.
		@param C_Invoice_Line_Alloc_ID Rechnungskandidat - Rechungszeile	  */
	@Override
	public void setC_Invoice_Line_Alloc_ID (int C_Invoice_Line_Alloc_ID)
	{
		if (C_Invoice_Line_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Line_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Line_Alloc_ID, Integer.valueOf(C_Invoice_Line_Alloc_ID));
	}

	/** Get Rechnungskandidat - Rechungszeile.
		@return Rechnungskandidat - Rechungszeile	  */
	@Override
	public int getC_Invoice_Line_Alloc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_Line_Alloc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoiceLine_ID, org.compiere.model.I_C_InvoiceLine.class);
	}

	@Override
	public void setC_InvoiceLine(org.compiere.model.I_C_InvoiceLine C_InvoiceLine)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoiceLine_ID, org.compiere.model.I_C_InvoiceLine.class, C_InvoiceLine);
	}

	/** Set Rechnungsposition.
		@param C_InvoiceLine_ID 
		Rechnungszeile
	  */
	@Override
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
	}

	/** Get Rechnungsposition.
		@return Rechnungszeile
	  */
	@Override
	public int getC_InvoiceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

		throw new IllegalArgumentException ("DocStatus is virtual column");	}

	/** Get Belegstatus.
		@return The current status of the document
	  */
	@Override
	public java.lang.String getDocStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Notiz.
		@param Note 
		Optional weitere Information für ein Dokument
	  */
	@Override
	public void setNote (java.lang.String Note)
	{
		set_ValueNoCheck (COLUMNNAME_Note, Note);
	}

	/** Get Notiz.
		@return Optional weitere Information für ein Dokument
	  */
	@Override
	public java.lang.String getNote () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Note);
	}

	/** Set Preis Abw..
		@param PriceEntered_Override Preis Abw.	  */
	@Override
	public void setPriceEntered_Override (java.math.BigDecimal PriceEntered_Override)
	{
		set_Value (COLUMNNAME_PriceEntered_Override, PriceEntered_Override);
	}

	/** Get Preis Abw..
		@return Preis Abw.	  */
	@Override
	public java.math.BigDecimal getPriceEntered_Override () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceEntered_Override);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Berechn. Menge.
		@param QtyInvoiced 
		Menge, die bereits in Rechnung gestellt wurde
	  */
	@Override
	public void setQtyInvoiced (java.math.BigDecimal QtyInvoiced)
	{
		set_Value (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	/** Get Berechn. Menge.
		@return Menge, die bereits in Rechnung gestellt wurde
	  */
	@Override
	public java.math.BigDecimal getQtyInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInvoiced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Zu berechn. Menge abw..
		@param QtyToInvoice_Override 
		Der Benutzer kann eine abweichende zu berechnede Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.
	  */
	@Override
	public void setQtyToInvoice_Override (java.math.BigDecimal QtyToInvoice_Override)
	{
		set_Value (COLUMNNAME_QtyToInvoice_Override, QtyToInvoice_Override);
	}

	/** Get Zu berechn. Menge abw..
		@return Der Benutzer kann eine abweichende zu berechnede Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.
	  */
	@Override
	public java.math.BigDecimal getQtyToInvoice_Override () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyToInvoice_Override);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}