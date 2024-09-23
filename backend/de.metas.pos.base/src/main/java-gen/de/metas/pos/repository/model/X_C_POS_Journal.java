// Generated Model - DO NOT CHANGE
package de.metas.pos.repository.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_POS_Journal
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_POS_Journal extends org.compiere.model.PO implements I_C_POS_Journal, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1030679631L;

    /** Standard Constructor */
    public X_C_POS_Journal (final Properties ctx, final int C_POS_Journal_ID, @Nullable final String trxName)
    {
      super (ctx, C_POS_Journal_ID, trxName);
    }

    /** Load Constructor */
    public X_C_POS_Journal (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCashBeginningBalance (final BigDecimal CashBeginningBalance)
	{
		set_Value (COLUMNNAME_CashBeginningBalance, CashBeginningBalance);
	}

	@Override
	public BigDecimal getCashBeginningBalance() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CashBeginningBalance);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setCashEndingBalance (final BigDecimal CashEndingBalance)
	{
		set_Value (COLUMNNAME_CashEndingBalance, CashEndingBalance);
	}

	@Override
	public BigDecimal getCashEndingBalance() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_CashEndingBalance);
		return bd != null ? bd : BigDecimal.ZERO;
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
	public void setClosingNote (final @Nullable java.lang.String ClosingNote)
	{
		set_Value (COLUMNNAME_ClosingNote, ClosingNote);
	}

	@Override
	public java.lang.String getClosingNote() 
	{
		return get_ValueAsString(COLUMNNAME_ClosingNote);
	}

	@Override
	public org.compiere.model.I_C_POS getC_POS()
	{
		return get_ValueAsPO(COLUMNNAME_C_POS_ID, org.compiere.model.I_C_POS.class);
	}

	@Override
	public void setC_POS(final org.compiere.model.I_C_POS C_POS)
	{
		set_ValueFromPO(COLUMNNAME_C_POS_ID, org.compiere.model.I_C_POS.class, C_POS);
	}

	@Override
	public void setC_POS_ID (final int C_POS_ID)
	{
		if (C_POS_ID < 1) 
			set_Value (COLUMNNAME_C_POS_ID, null);
		else 
			set_Value (COLUMNNAME_C_POS_ID, C_POS_ID);
	}

	@Override
	public int getC_POS_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_ID);
	}

	@Override
	public void setC_POS_Journal_ID (final int C_POS_Journal_ID)
	{
		if (C_POS_Journal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_POS_Journal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_POS_Journal_ID, C_POS_Journal_ID);
	}

	@Override
	public int getC_POS_Journal_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_Journal_ID);
	}

	@Override
	public void setDateTrx (final java.sql.Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	@Override
	public java.sql.Timestamp getDateTrx() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateTrx);
	}

	@Override
	public void setIsClosed (final boolean IsClosed)
	{
		set_Value (COLUMNNAME_IsClosed, IsClosed);
	}

	@Override
	public boolean isClosed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsClosed);
	}

	@Override
	public void setOpeningNote (final @Nullable java.lang.String OpeningNote)
	{
		set_Value (COLUMNNAME_OpeningNote, OpeningNote);
	}

	@Override
	public java.lang.String getOpeningNote() 
	{
		return get_ValueAsString(COLUMNNAME_OpeningNote);
	}
}