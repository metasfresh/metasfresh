// Generated Model - DO NOT CHANGE
package de.metas.acct.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for SAP_GLJournal
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_SAP_GLJournal extends org.compiere.model.PO implements I_SAP_GLJournal, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2051816088L;

    /** Standard Constructor */
    public X_SAP_GLJournal (final Properties ctx, final int SAP_GLJournal_ID, @Nullable final String trxName)
    {
      super (ctx, SAP_GLJournal_ID, trxName);
    }

    /** Load Constructor */
    public X_SAP_GLJournal (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAcct_Currency_ID (final int Acct_Currency_ID)
	{
		if (Acct_Currency_ID < 1) 
			set_Value (COLUMNNAME_Acct_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_Acct_Currency_ID, Acct_Currency_ID);
	}

	@Override
	public int getAcct_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Acct_Currency_ID);
	}

	@Override
	public org.compiere.model.I_C_AcctSchema getC_AcctSchema()
	{
		return get_ValueAsPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class);
	}

	@Override
	public void setC_AcctSchema(final org.compiere.model.I_C_AcctSchema C_AcctSchema)
	{
		set_ValueFromPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema);
	}

	@Override
	public void setC_AcctSchema_ID (final int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, C_AcctSchema_ID);
	}

	@Override
	public int getC_AcctSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_AcctSchema_ID);
	}

	@Override
	public void setC_ConversionType_ID (final int C_ConversionType_ID)
	{
		if (C_ConversionType_ID < 1) 
			set_Value (COLUMNNAME_C_ConversionType_ID, null);
		else 
			set_Value (COLUMNNAME_C_ConversionType_ID, C_ConversionType_ID);
	}

	@Override
	public int getC_ConversionType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_ConversionType_ID);
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
	public void setC_DocType_ID (final int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}

	@Override
	public void setCurrencyRate (final BigDecimal CurrencyRate)
	{
		set_Value (COLUMNNAME_CurrencyRate, CurrencyRate);
	}

	@Override
	public BigDecimal getCurrencyRate() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CurrencyRate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDateAcct (final java.sql.Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	@Override
	public java.sql.Timestamp getDateAcct() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateAcct);
	}

	@Override
	public void setDateDoc (final java.sql.Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	@Override
	public java.sql.Timestamp getDateDoc() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateDoc);
	}

	@Override
	public void setDescription (final String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public String getDescription() 
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
	public void setDocAction (final String DocAction)
	{
		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	@Override
	public String getDocAction() 
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
	public void setDocStatus (final String DocStatus)
	{
		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	@Override
	public String getDocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
	}

	@Override
	public void setDocumentNo (final String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public org.compiere.model.I_GL_Category getGL_Category()
	{
		return get_ValueAsPO(COLUMNNAME_GL_Category_ID, org.compiere.model.I_GL_Category.class);
	}

	@Override
	public void setGL_Category(final org.compiere.model.I_GL_Category GL_Category)
	{
		set_ValueFromPO(COLUMNNAME_GL_Category_ID, org.compiere.model.I_GL_Category.class, GL_Category);
	}

	@Override
	public void setGL_Category_ID (final int GL_Category_ID)
	{
		if (GL_Category_ID < 0) 
			set_Value (COLUMNNAME_GL_Category_ID, null);
		else 
			set_Value (COLUMNNAME_GL_Category_ID, GL_Category_ID);
	}

	@Override
	public int getGL_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GL_Category_ID);
	}

	@Override
	public void setIsApproved (final boolean IsApproved)
	{
		set_ValueNoCheck (COLUMNNAME_IsApproved, IsApproved);
	}

	@Override
	public boolean isApproved() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApproved);
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
	public void setPosted (final boolean Posted)
	{
		set_ValueNoCheck (COLUMNNAME_Posted, Posted);
	}

	@Override
	public boolean isPosted() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Posted);
	}

	@Override
	public void setPostingError_Issue_ID (final int PostingError_Issue_ID)
	{
		if (PostingError_Issue_ID < 1) 
			set_Value (COLUMNNAME_PostingError_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_PostingError_Issue_ID, PostingError_Issue_ID);
	}

	@Override
	public int getPostingError_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PostingError_Issue_ID);
	}

	/** 
	 * PostingType AD_Reference_ID=125
	 * Reference name: _Posting Type
	 */
	public static final int POSTINGTYPE_AD_Reference_ID=125;
	/** Actual = A */
	public static final String POSTINGTYPE_Actual = "A";
	/** Budget = B */
	public static final String POSTINGTYPE_Budget = "B";
	/** Commitment = E */
	public static final String POSTINGTYPE_Commitment = "E";
	/** Statistical = S */
	public static final String POSTINGTYPE_Statistical = "S";
	/** Reservation = R */
	public static final String POSTINGTYPE_Reservation = "R";
	/** Actual Year End = Y */
	public static final String POSTINGTYPE_ActualYearEnd = "Y";
	@Override
	public void setPostingType (final String PostingType)
	{
		set_Value (COLUMNNAME_PostingType, PostingType);
	}

	@Override
	public String getPostingType() 
	{
		return get_ValueAsString(COLUMNNAME_PostingType);
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
	public org.compiere.model.I_GL_Journal getReversal()
	{
		return get_ValueAsPO(COLUMNNAME_Reversal_ID, org.compiere.model.I_GL_Journal.class);
	}

	@Override
	public void setReversal(final org.compiere.model.I_GL_Journal Reversal)
	{
		set_ValueFromPO(COLUMNNAME_Reversal_ID, org.compiere.model.I_GL_Journal.class, Reversal);
	}

	@Override
	public void setReversal_ID (final int Reversal_ID)
	{
		if (Reversal_ID < 1) 
			set_Value (COLUMNNAME_Reversal_ID, null);
		else 
			set_Value (COLUMNNAME_Reversal_ID, Reversal_ID);
	}

	@Override
	public int getReversal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Reversal_ID);
	}

	@Override
	public void setSAP_GLJournal_ID (final int SAP_GLJournal_ID)
	{
		if (SAP_GLJournal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SAP_GLJournal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SAP_GLJournal_ID, SAP_GLJournal_ID);
	}

	@Override
	public int getSAP_GLJournal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SAP_GLJournal_ID);
	}

	@Override
	public void setTotalCr (final BigDecimal TotalCr)
	{
		set_ValueNoCheck (COLUMNNAME_TotalCr, TotalCr);
	}

	@Override
	public BigDecimal getTotalCr() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalCr);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTotalDr (final BigDecimal TotalDr)
	{
		set_ValueNoCheck (COLUMNNAME_TotalDr, TotalDr);
	}

	@Override
	public BigDecimal getTotalDr() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalDr);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}