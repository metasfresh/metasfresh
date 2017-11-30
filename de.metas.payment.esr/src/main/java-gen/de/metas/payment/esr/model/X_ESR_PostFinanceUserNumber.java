/** Generated Model - DO NOT CHANGE */
package de.metas.payment.esr.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ESR_PostFinanceUserNumber
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_ESR_PostFinanceUserNumber extends org.compiere.model.PO implements I_ESR_PostFinanceUserNumber, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1288909553L;

    /** Standard Constructor */
    public X_ESR_PostFinanceUserNumber (Properties ctx, int ESR_PostFinanceUserNumber_ID, String trxName)
    {
      super (ctx, ESR_PostFinanceUserNumber_ID, trxName);
      /** if (ESR_PostFinanceUserNumber_ID == 0)
        {
			setC_BP_BankAccount_ID (0);
			setESR_PostFinanceUserNumber_ID (0);
			setESR_RenderedAccountNo (null);
        } */
    }

    /** Load Constructor */
    public X_ESR_PostFinanceUserNumber (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class);
	}

	@Override
	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class, C_BP_BankAccount);
	}

	/** Set Bankverbindung.
		@param C_BP_BankAccount_ID 
		Bankverbindung des Geschäftspartners
	  */
	@Override
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
	}

	/** Get Bankverbindung.
		@return Bankverbindung des Geschäftspartners
	  */
	@Override
	public int getC_BP_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ESR_PostFinanceUserNumber.
		@param ESR_PostFinanceUserNumber_ID ESR_PostFinanceUserNumber	  */
	@Override
	public void setESR_PostFinanceUserNumber_ID (int ESR_PostFinanceUserNumber_ID)
	{
		if (ESR_PostFinanceUserNumber_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ESR_PostFinanceUserNumber_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ESR_PostFinanceUserNumber_ID, Integer.valueOf(ESR_PostFinanceUserNumber_ID));
	}

	/** Get ESR_PostFinanceUserNumber.
		@return ESR_PostFinanceUserNumber	  */
	@Override
	public int getESR_PostFinanceUserNumber_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ESR_PostFinanceUserNumber_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ESR Teilnehmernummer.
		@param ESR_RenderedAccountNo ESR Teilnehmernummer	  */
	@Override
	public void setESR_RenderedAccountNo (java.lang.String ESR_RenderedAccountNo)
	{
		set_Value (COLUMNNAME_ESR_RenderedAccountNo, ESR_RenderedAccountNo);
	}

	/** Get ESR Teilnehmernummer.
		@return ESR Teilnehmernummer	  */
	@Override
	public java.lang.String getESR_RenderedAccountNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ESR_RenderedAccountNo);
	}
}