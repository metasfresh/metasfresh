// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_ForeignExchangeContract
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_ForeignExchangeContract extends org.compiere.model.PO implements I_C_ForeignExchangeContract, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2010000274L;

    /** Standard Constructor */
    public X_C_ForeignExchangeContract (final Properties ctx, final int C_ForeignExchangeContract_ID, @Nullable final String trxName)
    {
      super (ctx, C_ForeignExchangeContract_ID, trxName);
    }

    /** Load Constructor */
    public X_C_ForeignExchangeContract (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_ForeignExchangeContract_ID (final int C_ForeignExchangeContract_ID)
	{
		if (C_ForeignExchangeContract_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ForeignExchangeContract_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ForeignExchangeContract_ID, C_ForeignExchangeContract_ID);
	}

	@Override
	public int getC_ForeignExchangeContract_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ForeignExchangeContract_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
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
	public void setDocumentNo (final java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setFEC_Amount (final BigDecimal FEC_Amount)
	{
		set_Value (COLUMNNAME_FEC_Amount, FEC_Amount);
	}

	@Override
	public BigDecimal getFEC_Amount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FEC_Amount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setFEC_Amount_Alloc (final BigDecimal FEC_Amount_Alloc)
	{
		set_Value (COLUMNNAME_FEC_Amount_Alloc, FEC_Amount_Alloc);
	}

	@Override
	public BigDecimal getFEC_Amount_Alloc() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FEC_Amount_Alloc);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setFEC_Amount_Open (final BigDecimal FEC_Amount_Open)
	{
		set_Value (COLUMNNAME_FEC_Amount_Open, FEC_Amount_Open);
	}

	@Override
	public BigDecimal getFEC_Amount_Open() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FEC_Amount_Open);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setFEC_CurrencyRate (final BigDecimal FEC_CurrencyRate)
	{
		set_Value (COLUMNNAME_FEC_CurrencyRate, FEC_CurrencyRate);
	}

	@Override
	public BigDecimal getFEC_CurrencyRate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FEC_CurrencyRate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setFEC_MaturityDate (final java.sql.Timestamp FEC_MaturityDate)
	{
		set_Value (COLUMNNAME_FEC_MaturityDate, FEC_MaturityDate);
	}

	@Override
	public java.sql.Timestamp getFEC_MaturityDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_FEC_MaturityDate);
	}

	@Override
	public void setFEC_ValidityDate (final java.sql.Timestamp FEC_ValidityDate)
	{
		set_Value (COLUMNNAME_FEC_ValidityDate, FEC_ValidityDate);
	}

	@Override
	public java.sql.Timestamp getFEC_ValidityDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_FEC_ValidityDate);
	}

	@Override
	public org.compiere.model.I_M_SectionCode getM_SectionCode()
	{
		return get_ValueAsPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class);
	}

	@Override
	public void setM_SectionCode(final org.compiere.model.I_M_SectionCode M_SectionCode)
	{
		set_ValueFromPO(COLUMNNAME_M_SectionCode_ID, org.compiere.model.I_M_SectionCode.class, M_SectionCode);
	}

	@Override
	public void setM_SectionCode_ID (final int M_SectionCode_ID)
	{
		if (M_SectionCode_ID < 1) 
			set_Value (COLUMNNAME_M_SectionCode_ID, null);
		else 
			set_Value (COLUMNNAME_M_SectionCode_ID, M_SectionCode_ID);
	}

	@Override
	public int getM_SectionCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_SectionCode_ID);
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
	public void setTo_Currency_ID (final int To_Currency_ID)
	{
		if (To_Currency_ID < 1) 
			set_Value (COLUMNNAME_To_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_To_Currency_ID, To_Currency_ID);
	}

	@Override
	public int getTo_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_To_Currency_ID);
	}
}