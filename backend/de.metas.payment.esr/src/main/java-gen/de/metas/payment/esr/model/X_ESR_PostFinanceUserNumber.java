// Generated Model - DO NOT CHANGE
package de.metas.payment.esr.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ESR_PostFinanceUserNumber
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ESR_PostFinanceUserNumber extends org.compiere.model.PO implements I_ESR_PostFinanceUserNumber, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 806167320L;

    /** Standard Constructor */
    public X_ESR_PostFinanceUserNumber (final Properties ctx, final int ESR_PostFinanceUserNumber_ID, @Nullable final String trxName)
    {
      super (ctx, ESR_PostFinanceUserNumber_ID, trxName);
    }

    /** Load Constructor */
    public X_ESR_PostFinanceUserNumber (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BP_BankAccount_ID (final int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, C_BP_BankAccount_ID);
	}

	@Override
	public int getC_BP_BankAccount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccount_ID);
	}

	@Override
	public void setESR_PostFinanceUserNumber_ID (final int ESR_PostFinanceUserNumber_ID)
	{
		if (ESR_PostFinanceUserNumber_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ESR_PostFinanceUserNumber_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ESR_PostFinanceUserNumber_ID, ESR_PostFinanceUserNumber_ID);
	}

	@Override
	public int getESR_PostFinanceUserNumber_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ESR_PostFinanceUserNumber_ID);
	}

	@Override
	public void setESR_RenderedAccountNo (final java.lang.String ESR_RenderedAccountNo)
	{
		set_Value (COLUMNNAME_ESR_RenderedAccountNo, ESR_RenderedAccountNo);
	}

	@Override
	public java.lang.String getESR_RenderedAccountNo() 
	{
		return get_ValueAsString(COLUMNNAME_ESR_RenderedAccountNo);
	}
}