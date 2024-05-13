// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ModCntr_Interest
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ModCntr_Interest extends org.compiere.model.PO implements I_ModCntr_Interest, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1327321966L;

    /** Standard Constructor */
    public X_ModCntr_Interest (final Properties ctx, final int ModCntr_Interest_ID, @Nullable final String trxName)
    {
      super (ctx, ModCntr_Interest_ID, trxName);
    }

    /** Load Constructor */
    public X_ModCntr_Interest (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(final org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
	public void setC_Invoice_ID (final int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, C_Invoice_ID);
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	@Override
	public void setFinalInterest (final @Nullable BigDecimal FinalInterest)
	{
		set_Value (COLUMNNAME_FinalInterest, FinalInterest);
	}

	@Override
	public BigDecimal getFinalInterest() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FinalInterest);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setInterestDays (final @Nullable BigDecimal InterestDays)
	{
		set_Value (COLUMNNAME_InterestDays, InterestDays);
	}

	@Override
	public BigDecimal getInterestDays() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_InterestDays);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setInterestScore (final @Nullable BigDecimal InterestScore)
	{
		set_Value (COLUMNNAME_InterestScore, InterestScore);
	}

	@Override
	public BigDecimal getInterestScore() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_InterestScore);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setInterimAmt (final BigDecimal InterimAmt)
	{
		set_Value (COLUMNNAME_InterimAmt, InterimAmt);
	}

	@Override
	public BigDecimal getInterimAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_InterimAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setMatchedAmt (final BigDecimal MatchedAmt)
	{
		set_Value (COLUMNNAME_MatchedAmt, MatchedAmt);
	}

	@Override
	public BigDecimal getMatchedAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_MatchedAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setModCntr_Interest_ID (final int ModCntr_Interest_ID)
	{
		if (ModCntr_Interest_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ModCntr_Interest_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ModCntr_Interest_ID, ModCntr_Interest_ID);
	}

	@Override
	public int getModCntr_Interest_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_Interest_ID);
	}

	@Override
	public de.metas.contracts.model.I_ModCntr_Interest_Run getModCntr_Interest_Run()
	{
		return get_ValueAsPO(COLUMNNAME_ModCntr_Interest_Run_ID, de.metas.contracts.model.I_ModCntr_Interest_Run.class);
	}

	@Override
	public void setModCntr_Interest_Run(final de.metas.contracts.model.I_ModCntr_Interest_Run ModCntr_Interest_Run)
	{
		set_ValueFromPO(COLUMNNAME_ModCntr_Interest_Run_ID, de.metas.contracts.model.I_ModCntr_Interest_Run.class, ModCntr_Interest_Run);
	}

	@Override
	public void setModCntr_Interest_Run_ID (final int ModCntr_Interest_Run_ID)
	{
		if (ModCntr_Interest_Run_ID < 1) 
			set_Value (COLUMNNAME_ModCntr_Interest_Run_ID, null);
		else 
			set_Value (COLUMNNAME_ModCntr_Interest_Run_ID, ModCntr_Interest_Run_ID);
	}

	@Override
	public int getModCntr_Interest_Run_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_Interest_Run_ID);
	}

	@Override
	public de.metas.contracts.model.I_ModCntr_Log getModCntr_Log()
	{
		return get_ValueAsPO(COLUMNNAME_ModCntr_Log_ID, de.metas.contracts.model.I_ModCntr_Log.class);
	}

	@Override
	public void setModCntr_Log(final de.metas.contracts.model.I_ModCntr_Log ModCntr_Log)
	{
		set_ValueFromPO(COLUMNNAME_ModCntr_Log_ID, de.metas.contracts.model.I_ModCntr_Log.class, ModCntr_Log);
	}

	@Override
	public void setModCntr_Log_ID (final int ModCntr_Log_ID)
	{
		if (ModCntr_Log_ID < 1) 
			set_Value (COLUMNNAME_ModCntr_Log_ID, null);
		else 
			set_Value (COLUMNNAME_ModCntr_Log_ID, ModCntr_Log_ID);
	}

	@Override
	public int getModCntr_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_Log_ID);
	}
}