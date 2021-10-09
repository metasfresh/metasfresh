// Generated Model - DO NOT CHANGE
package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_ReceiptSchedule_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_ReceiptSchedule_Alloc extends org.compiere.model.PO implements I_M_ReceiptSchedule_Alloc, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1655085704L;

    /** Standard Constructor */
    public X_M_ReceiptSchedule_Alloc (final Properties ctx, final int M_ReceiptSchedule_Alloc_ID, @Nullable final String trxName)
    {
      super (ctx, M_ReceiptSchedule_Alloc_ID, trxName);
    }

    /** Load Constructor */
    public X_M_ReceiptSchedule_Alloc (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCatch_UOM_ID (final int Catch_UOM_ID)
	{
		if (Catch_UOM_ID < 1) 
			set_Value (COLUMNNAME_Catch_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_Catch_UOM_ID, Catch_UOM_ID);
	}

	@Override
	public int getCatch_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Catch_UOM_ID);
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
	public void setDocStatus (final @Nullable java.lang.String DocStatus)
	{
		throw new IllegalArgumentException ("DocStatus is virtual column");	}

	@Override
	public java.lang.String getDocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(final org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	@Override
	public void setM_InOut_ID (final int M_InOut_ID)
	{
		throw new IllegalArgumentException ("M_InOut_ID is virtual column");	}

	@Override
	public int getM_InOut_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_ID);
	}

	@Override
	public org.compiere.model.I_M_InOutLine getM_InOutLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class);
	}

	@Override
	public void setM_InOutLine(final org.compiere.model.I_M_InOutLine M_InOutLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class, M_InOutLine);
	}

	@Override
	public void setM_InOutLine_ID (final int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, M_InOutLine_ID);
	}

	@Override
	public int getM_InOutLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOutLine_ID);
	}

	@Override
	public void setM_ReceiptSchedule_Alloc_ID (final int M_ReceiptSchedule_Alloc_ID)
	{
		if (M_ReceiptSchedule_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ReceiptSchedule_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ReceiptSchedule_Alloc_ID, M_ReceiptSchedule_Alloc_ID);
	}

	@Override
	public int getM_ReceiptSchedule_Alloc_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ReceiptSchedule_Alloc_ID);
	}

	@Override
	public de.metas.inoutcandidate.model.I_M_ReceiptSchedule getM_ReceiptSchedule()
	{
		return get_ValueAsPO(COLUMNNAME_M_ReceiptSchedule_ID, de.metas.inoutcandidate.model.I_M_ReceiptSchedule.class);
	}

	@Override
	public void setM_ReceiptSchedule(final de.metas.inoutcandidate.model.I_M_ReceiptSchedule M_ReceiptSchedule)
	{
		set_ValueFromPO(COLUMNNAME_M_ReceiptSchedule_ID, de.metas.inoutcandidate.model.I_M_ReceiptSchedule.class, M_ReceiptSchedule);
	}

	@Override
	public void setM_ReceiptSchedule_ID (final int M_ReceiptSchedule_ID)
	{
		if (M_ReceiptSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ReceiptSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ReceiptSchedule_ID, M_ReceiptSchedule_ID);
	}

	@Override
	public int getM_ReceiptSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ReceiptSchedule_ID);
	}

	@Override
	public void setQtyAllocated (final @Nullable BigDecimal QtyAllocated)
	{
		set_ValueNoCheck (COLUMNNAME_QtyAllocated, QtyAllocated);
	}

	@Override
	public BigDecimal getQtyAllocated() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyAllocated);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyAllocatedInCatchUOM (final @Nullable BigDecimal QtyAllocatedInCatchUOM)
	{
		set_Value (COLUMNNAME_QtyAllocatedInCatchUOM, QtyAllocatedInCatchUOM);
	}

	@Override
	public BigDecimal getQtyAllocatedInCatchUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyAllocatedInCatchUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyWithIssues (final BigDecimal QtyWithIssues)
	{
		set_ValueNoCheck (COLUMNNAME_QtyWithIssues, QtyWithIssues);
	}

	@Override
	public BigDecimal getQtyWithIssues() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyWithIssues);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyWithIssuesInCatchUOM (final @Nullable BigDecimal QtyWithIssuesInCatchUOM)
	{
		set_Value (COLUMNNAME_QtyWithIssuesInCatchUOM, QtyWithIssuesInCatchUOM);
	}

	@Override
	public BigDecimal getQtyWithIssuesInCatchUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyWithIssuesInCatchUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQualityDiscountPercent (final @Nullable BigDecimal QualityDiscountPercent)
	{
		throw new IllegalArgumentException ("QualityDiscountPercent is virtual column");	}

	@Override
	public BigDecimal getQualityDiscountPercent() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QualityDiscountPercent);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQualityNote (final @Nullable java.lang.String QualityNote)
	{
		throw new IllegalArgumentException ("QualityNote is virtual column");	}

	@Override
	public java.lang.String getQualityNote() 
	{
		return get_ValueAsString(COLUMNNAME_QualityNote);
	}
}