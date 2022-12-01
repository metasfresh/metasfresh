// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Flatrate_DataEntry
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Flatrate_DataEntry extends org.compiere.model.PO implements I_C_Flatrate_DataEntry, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -915094046L;

    /** Standard Constructor */
    public X_C_Flatrate_DataEntry (final Properties ctx, final int C_Flatrate_DataEntry_ID, @Nullable final String trxName)
    {
      super (ctx, C_Flatrate_DataEntry_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Flatrate_DataEntry (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setActualQty (final @Nullable BigDecimal ActualQty)
	{
		set_Value (COLUMNNAME_ActualQty, ActualQty);
	}

	@Override
	public BigDecimal getActualQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualQty);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setActualQtyDiffAbs (final @Nullable BigDecimal ActualQtyDiffAbs)
	{
		set_Value (COLUMNNAME_ActualQtyDiffAbs, ActualQtyDiffAbs);
	}

	@Override
	public BigDecimal getActualQtyDiffAbs() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualQtyDiffAbs);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setActualQtyDiffPercent (final @Nullable BigDecimal ActualQtyDiffPercent)
	{
		set_Value (COLUMNNAME_ActualQtyDiffPercent, ActualQtyDiffPercent);
	}

	@Override
	public BigDecimal getActualQtyDiffPercent() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualQtyDiffPercent);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setActualQtyDiffPercentEff (final @Nullable BigDecimal ActualQtyDiffPercentEff)
	{
		set_Value (COLUMNNAME_ActualQtyDiffPercentEff, ActualQtyDiffPercentEff);
	}

	@Override
	public BigDecimal getActualQtyDiffPercentEff() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualQtyDiffPercentEff);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setActualQtyDiffPerUOM (final @Nullable BigDecimal ActualQtyDiffPerUOM)
	{
		set_Value (COLUMNNAME_ActualQtyDiffPerUOM, ActualQtyDiffPerUOM);
	}

	@Override
	public BigDecimal getActualQtyDiffPerUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualQtyDiffPerUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setActualQtyPerUnit (final @Nullable BigDecimal ActualQtyPerUnit)
	{
		set_Value (COLUMNNAME_ActualQtyPerUnit, ActualQtyPerUnit);
	}

	@Override
	public BigDecimal getActualQtyPerUnit() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ActualQtyPerUnit);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAD_User_Reported_ID (final int AD_User_Reported_ID)
	{
		if (AD_User_Reported_ID < 1) 
			set_Value (COLUMNNAME_AD_User_Reported_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_Reported_ID, AD_User_Reported_ID);
	}

	@Override
	public int getAD_User_Reported_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_Reported_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setC_Flatrate_DataEntry_ID (final int C_Flatrate_DataEntry_ID)
	{
		if (C_Flatrate_DataEntry_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_DataEntry_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_DataEntry_ID, C_Flatrate_DataEntry_ID);
	}

	@Override
	public int getC_Flatrate_DataEntry_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_DataEntry_ID);
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Term getC_Flatrate_Term()
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.contracts.model.I_C_Flatrate_Term.class);
	}

	@Override
	public void setC_Flatrate_Term(final de.metas.contracts.model.I_C_Flatrate_Term C_Flatrate_Term)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.contracts.model.I_C_Flatrate_Term.class, C_Flatrate_Term);
	}

	@Override
	public void setC_Flatrate_Term_ID (final int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Term_ID, C_Flatrate_Term_ID);
	}

	@Override
	public int getC_Flatrate_Term_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Term_ID);
	}

	@Override
	public void setC_Invoice_Candidate_Corr_ID (final int C_Invoice_Candidate_Corr_ID)
	{
		if (C_Invoice_Candidate_Corr_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_Corr_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_Corr_ID, C_Invoice_Candidate_Corr_ID);
	}

	@Override
	public int getC_Invoice_Candidate_Corr_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_Corr_ID);
	}

	@Override
	public void setC_Invoice_Candidate_ID (final int C_Invoice_Candidate_ID)
	{
		if (C_Invoice_Candidate_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, C_Invoice_Candidate_ID);
	}

	@Override
	public int getC_Invoice_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_ID);
	}

	@Override
	public org.compiere.model.I_C_Period getC_Period()
	{
		return get_ValueAsPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class);
	}

	@Override
	public void setC_Period(final org.compiere.model.I_C_Period C_Period)
	{
		set_ValueFromPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class, C_Period);
	}

	@Override
	public void setC_Period_ID (final int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, C_Period_ID);
	}

	@Override
	public int getC_Period_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Period_ID);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setDate_Reported (final @Nullable java.sql.Timestamp Date_Reported)
	{
		set_Value (COLUMNNAME_Date_Reported, Date_Reported);
	}

	@Override
	public java.sql.Timestamp getDate_Reported() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Date_Reported);
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
	/** UnClose = UC */
	public static final String DOCACTION_UnClose = "UC";
	@Override
	public void setDocAction (final java.lang.String DocAction)
	{
		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	@Override
	public java.lang.String getDocAction() 
	{
		return get_ValueAsString(COLUMNNAME_DocAction);
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
	@Override
	public void setDocStatus (final java.lang.String DocStatus)
	{
		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	@Override
	public java.lang.String getDocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
	}

	@Override
	public void setFlatrateAmt (final @Nullable BigDecimal FlatrateAmt)
	{
		set_Value (COLUMNNAME_FlatrateAmt, FlatrateAmt);
	}

	@Override
	public BigDecimal getFlatrateAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FlatrateAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setFlatrateAmtCorr (final @Nullable BigDecimal FlatrateAmtCorr)
	{
		set_Value (COLUMNNAME_FlatrateAmtCorr, FlatrateAmtCorr);
	}

	@Override
	public BigDecimal getFlatrateAmtCorr() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FlatrateAmtCorr);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setFlatrateAmtPerUOM (final @Nullable BigDecimal FlatrateAmtPerUOM)
	{
		set_Value (COLUMNNAME_FlatrateAmtPerUOM, FlatrateAmtPerUOM);
	}

	@Override
	public BigDecimal getFlatrateAmtPerUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FlatrateAmtPerUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIsSimulation (final boolean IsSimulation)
	{
		throw new IllegalArgumentException ("IsSimulation is virtual column");	}

	@Override
	public boolean isSimulation() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSimulation);
	}

	@Override
	public void setM_Product_DataEntry_ID (final int M_Product_DataEntry_ID)
	{
		if (M_Product_DataEntry_ID < 1) 
			set_Value (COLUMNNAME_M_Product_DataEntry_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_DataEntry_ID, M_Product_DataEntry_ID);
	}

	@Override
	public int getM_Product_DataEntry_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_DataEntry_ID);
	}

	@Override
	public void setNote (final @Nullable java.lang.String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	@Override
	public java.lang.String getNote() 
	{
		return get_ValueAsString(COLUMNNAME_Note);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setQty_Planned (final @Nullable BigDecimal Qty_Planned)
	{
		set_Value (COLUMNNAME_Qty_Planned, Qty_Planned);
	}

	@Override
	public BigDecimal getQty_Planned() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty_Planned);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQty_Reported (final @Nullable BigDecimal Qty_Reported)
	{
		set_Value (COLUMNNAME_Qty_Reported, Qty_Reported);
	}

	@Override
	public BigDecimal getQty_Reported() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty_Reported);
		return bd != null ? bd : BigDecimal.ZERO;
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
	@Override
	public void setType (final java.lang.String Type)
	{
		set_ValueNoCheck (COLUMNNAME_Type, Type);
	}

	@Override
	public java.lang.String getType() 
	{
		return get_ValueAsString(COLUMNNAME_Type);
	}
}