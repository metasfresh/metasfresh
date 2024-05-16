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

	private static final long serialVersionUID = 925058310L;

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
	public void setInterestDays (final int InterestDays)
	{
		set_Value (COLUMNNAME_InterestDays, InterestDays);
	}

	@Override
	public int getInterestDays() 
	{
		return get_ValueAsInt(COLUMNNAME_InterestDays);
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
	public I_ModCntr_Log getInterimInvoice_ModCntr_Log()
	{
		return get_ValueAsPO(COLUMNNAME_InterimInvoice_ModCntr_Log_ID, I_ModCntr_Log.class);
	}

	@Override
	public void setInterimInvoice_ModCntr_Log(final I_ModCntr_Log InterimInvoice_ModCntr_Log)
	{
		set_ValueFromPO(COLUMNNAME_InterimInvoice_ModCntr_Log_ID, I_ModCntr_Log.class, InterimInvoice_ModCntr_Log);
	}

	@Override
	public void setInterimInvoice_ModCntr_Log_ID (final int InterimInvoice_ModCntr_Log_ID)
	{
		if (InterimInvoice_ModCntr_Log_ID < 1) 
			set_Value (COLUMNNAME_InterimInvoice_ModCntr_Log_ID, null);
		else 
			set_Value (COLUMNNAME_InterimInvoice_ModCntr_Log_ID, InterimInvoice_ModCntr_Log_ID);
	}

	@Override
	public int getInterimInvoice_ModCntr_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_InterimInvoice_ModCntr_Log_ID);
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
	public I_ModCntr_Interest_Run getModCntr_Interest_Run()
	{
		return get_ValueAsPO(COLUMNNAME_ModCntr_Interest_Run_ID, I_ModCntr_Interest_Run.class);
	}

	@Override
	public void setModCntr_Interest_Run(final I_ModCntr_Interest_Run ModCntr_Interest_Run)
	{
		set_ValueFromPO(COLUMNNAME_ModCntr_Interest_Run_ID, I_ModCntr_Interest_Run.class, ModCntr_Interest_Run);
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
	public I_ModCntr_Log getShippingNotification_ModCntr_Log()
	{
		return get_ValueAsPO(COLUMNNAME_ShippingNotification_ModCntr_Log_ID, I_ModCntr_Log.class);
	}

	@Override
	public void setShippingNotification_ModCntr_Log(final I_ModCntr_Log ShippingNotification_ModCntr_Log)
	{
		set_ValueFromPO(COLUMNNAME_ShippingNotification_ModCntr_Log_ID, I_ModCntr_Log.class, ShippingNotification_ModCntr_Log);
	}

	@Override
	public void setShippingNotification_ModCntr_Log_ID (final int ShippingNotification_ModCntr_Log_ID)
	{
		if (ShippingNotification_ModCntr_Log_ID < 1) 
			set_Value (COLUMNNAME_ShippingNotification_ModCntr_Log_ID, null);
		else 
			set_Value (COLUMNNAME_ShippingNotification_ModCntr_Log_ID, ShippingNotification_ModCntr_Log_ID);
	}

	@Override
	public int getShippingNotification_ModCntr_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ShippingNotification_ModCntr_Log_ID);
	}
}