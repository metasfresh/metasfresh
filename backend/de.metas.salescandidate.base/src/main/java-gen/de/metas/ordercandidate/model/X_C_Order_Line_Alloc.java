// Generated Model - DO NOT CHANGE
package de.metas.ordercandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Order_Line_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Order_Line_Alloc extends org.compiere.model.PO implements I_C_Order_Line_Alloc, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -617015638L;

    /** Standard Constructor */
    public X_C_Order_Line_Alloc (final Properties ctx, final int C_Order_Line_Alloc_ID, @Nullable final String trxName)
    {
      super (ctx, C_Order_Line_Alloc_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Order_Line_Alloc (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.ordercandidate.model.I_C_OLCand getC_OLCand()
	{
		return get_ValueAsPO(COLUMNNAME_C_OLCand_ID, de.metas.ordercandidate.model.I_C_OLCand.class);
	}

	@Override
	public void setC_OLCand(final de.metas.ordercandidate.model.I_C_OLCand C_OLCand)
	{
		set_ValueFromPO(COLUMNNAME_C_OLCand_ID, de.metas.ordercandidate.model.I_C_OLCand.class, C_OLCand);
	}

	@Override
	public void setC_OLCand_ID (final int C_OLCand_ID)
	{
		if (C_OLCand_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OLCand_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OLCand_ID, C_OLCand_ID);
	}

	@Override
	public int getC_OLCand_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OLCand_ID);
	}

	@Override
	public de.metas.ordercandidate.model.I_C_OLCandProcessor getC_OLCandProcessor()
	{
		return get_ValueAsPO(COLUMNNAME_C_OLCandProcessor_ID, de.metas.ordercandidate.model.I_C_OLCandProcessor.class);
	}

	@Override
	public void setC_OLCandProcessor(final de.metas.ordercandidate.model.I_C_OLCandProcessor C_OLCandProcessor)
	{
		set_ValueFromPO(COLUMNNAME_C_OLCandProcessor_ID, de.metas.ordercandidate.model.I_C_OLCandProcessor.class, C_OLCandProcessor);
	}

	@Override
	public void setC_OLCandProcessor_ID (final int C_OLCandProcessor_ID)
	{
		if (C_OLCandProcessor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OLCandProcessor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OLCandProcessor_ID, C_OLCandProcessor_ID);
	}

	@Override
	public int getC_OLCandProcessor_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OLCandProcessor_ID);
	}

	@Override
	public void setC_Order_Line_Alloc_ID (final int C_Order_Line_Alloc_ID)
	{
		if (C_Order_Line_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Order_Line_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Order_Line_Alloc_ID, C_Order_Line_Alloc_ID);
	}

	@Override
	public int getC_Order_Line_Alloc_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_Line_Alloc_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(final org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	@Override
	public void setC_OrderLine_ID (final int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, C_OrderLine_ID);
	}

	@Override
	public int getC_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_ID);
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
	public void setQtyOrdered (final BigDecimal QtyOrdered)
	{
		set_Value (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	@Override
	public BigDecimal getQtyOrdered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyOrdered);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}