// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_ForeignExchangeContract
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_ForeignExchangeContract extends org.compiere.model.PO implements I_C_ForeignExchangeContract, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -563059722L;

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
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
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