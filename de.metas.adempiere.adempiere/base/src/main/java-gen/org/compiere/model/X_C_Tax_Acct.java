/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Tax_Acct
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Tax_Acct extends org.compiere.model.PO implements I_C_Tax_Acct, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1603193630L;

    /** Standard Constructor */
    public X_C_Tax_Acct (Properties ctx, int C_Tax_Acct_ID, String trxName)
    {
      super (ctx, C_Tax_Acct_ID, trxName);
      /** if (C_Tax_Acct_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setC_Tax_Acct_ID (0);
			setC_Tax_ID (0);
			setT_Credit_Acct (0);
			setT_Due_Acct (0);
			setT_Expense_Acct (0);
			setT_Liability_Acct (0);
			setT_Receivables_Acct (0);
        } */
    }

    /** Load Constructor */
    public X_C_Tax_Acct (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_AcctSchema getC_AcctSchema()
	{
		return get_ValueAsPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class);
	}

	@Override
	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema)
	{
		set_ValueFromPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema);
	}

	/** Set Buchführungs-Schema.
		@param C_AcctSchema_ID 
		Rules for accounting
	  */
	@Override
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Buchführungs-Schema.
		@return Rules for accounting
	  */
	@Override
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_Tax_Acct.
		@param C_Tax_Acct_ID C_Tax_Acct	  */
	@Override
	public void setC_Tax_Acct_ID (int C_Tax_Acct_ID)
	{
		if (C_Tax_Acct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Tax_Acct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Tax_Acct_ID, Integer.valueOf(C_Tax_Acct_ID));
	}

	/** Get C_Tax_Acct.
		@return C_Tax_Acct	  */
	@Override
	public int getC_Tax_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Steuer.
		@param C_Tax_ID 
		Tax identifier
	  */
	@Override
	public void setC_Tax_ID (int C_Tax_ID)
	{
		if (C_Tax_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Tax_ID, Integer.valueOf(C_Tax_ID));
	}

	/** Get Steuer.
		@return Tax identifier
	  */
	@Override
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getT_Credit_A()
	{
		return get_ValueAsPO(COLUMNNAME_T_Credit_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_Credit_A(org.compiere.model.I_C_ValidCombination T_Credit_A)
	{
		set_ValueFromPO(COLUMNNAME_T_Credit_Acct, org.compiere.model.I_C_ValidCombination.class, T_Credit_A);
	}

	/** Set Vorsteuer.
		@param T_Credit_Acct 
		Konto für Vorsteuer
	  */
	@Override
	public void setT_Credit_Acct (int T_Credit_Acct)
	{
		set_Value (COLUMNNAME_T_Credit_Acct, Integer.valueOf(T_Credit_Acct));
	}

	/** Get Vorsteuer.
		@return Konto für Vorsteuer
	  */
	@Override
	public int getT_Credit_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_Credit_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getT_Due_A()
	{
		return get_ValueAsPO(COLUMNNAME_T_Due_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_Due_A(org.compiere.model.I_C_ValidCombination T_Due_A)
	{
		set_ValueFromPO(COLUMNNAME_T_Due_Acct, org.compiere.model.I_C_ValidCombination.class, T_Due_A);
	}

	/** Set Geschuldete MwSt..
		@param T_Due_Acct 
		Konto für geschuldete MwSt.
	  */
	@Override
	public void setT_Due_Acct (int T_Due_Acct)
	{
		set_Value (COLUMNNAME_T_Due_Acct, Integer.valueOf(T_Due_Acct));
	}

	/** Get Geschuldete MwSt..
		@return Konto für geschuldete MwSt.
	  */
	@Override
	public int getT_Due_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_Due_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getT_Expense_A()
	{
		return get_ValueAsPO(COLUMNNAME_T_Expense_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_Expense_A(org.compiere.model.I_C_ValidCombination T_Expense_A)
	{
		set_ValueFromPO(COLUMNNAME_T_Expense_Acct, org.compiere.model.I_C_ValidCombination.class, T_Expense_A);
	}

	/** Set Sonstige Steuern.
		@param T_Expense_Acct 
		Account for paid tax you cannot reclaim
	  */
	@Override
	public void setT_Expense_Acct (int T_Expense_Acct)
	{
		set_Value (COLUMNNAME_T_Expense_Acct, Integer.valueOf(T_Expense_Acct));
	}

	/** Get Sonstige Steuern.
		@return Account for paid tax you cannot reclaim
	  */
	@Override
	public int getT_Expense_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_Expense_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getT_Liability_A()
	{
		return get_ValueAsPO(COLUMNNAME_T_Liability_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_Liability_A(org.compiere.model.I_C_ValidCombination T_Liability_A)
	{
		set_ValueFromPO(COLUMNNAME_T_Liability_Acct, org.compiere.model.I_C_ValidCombination.class, T_Liability_A);
	}

	/** Set Verbindlichkeiten aus Steuern.
		@param T_Liability_Acct 
		Konto für Verbindlichkeiten aus Steuern
	  */
	@Override
	public void setT_Liability_Acct (int T_Liability_Acct)
	{
		set_Value (COLUMNNAME_T_Liability_Acct, Integer.valueOf(T_Liability_Acct));
	}

	/** Get Verbindlichkeiten aus Steuern.
		@return Konto für Verbindlichkeiten aus Steuern
	  */
	@Override
	public int getT_Liability_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_Liability_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getT_PayDiscount_Exp_A()
	{
		return get_ValueAsPO(COLUMNNAME_T_PayDiscount_Exp_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_PayDiscount_Exp_A(org.compiere.model.I_C_ValidCombination T_PayDiscount_Exp_A)
	{
		set_ValueFromPO(COLUMNNAME_T_PayDiscount_Exp_Acct, org.compiere.model.I_C_ValidCombination.class, T_PayDiscount_Exp_A);
	}

	/** Set Steuerkorrektur gewährte Skonti.
		@param T_PayDiscount_Exp_Acct 
		Steuerabhängiges Konto zur Verbuchung gewährter Skonti
	  */
	@Override
	public void setT_PayDiscount_Exp_Acct (int T_PayDiscount_Exp_Acct)
	{
		set_Value (COLUMNNAME_T_PayDiscount_Exp_Acct, Integer.valueOf(T_PayDiscount_Exp_Acct));
	}

	/** Get Steuerkorrektur gewährte Skonti.
		@return Steuerabhängiges Konto zur Verbuchung gewährter Skonti
	  */
	@Override
	public int getT_PayDiscount_Exp_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_PayDiscount_Exp_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getT_PayDiscount_Rev_A()
	{
		return get_ValueAsPO(COLUMNNAME_T_PayDiscount_Rev_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_PayDiscount_Rev_A(org.compiere.model.I_C_ValidCombination T_PayDiscount_Rev_A)
	{
		set_ValueFromPO(COLUMNNAME_T_PayDiscount_Rev_Acct, org.compiere.model.I_C_ValidCombination.class, T_PayDiscount_Rev_A);
	}

	/** Set Steuerkorrektur erhaltene Skonti.
		@param T_PayDiscount_Rev_Acct 
		Steuerabhängiges Konto zur Verbuchung erhaltener Skonti
	  */
	@Override
	public void setT_PayDiscount_Rev_Acct (int T_PayDiscount_Rev_Acct)
	{
		set_Value (COLUMNNAME_T_PayDiscount_Rev_Acct, Integer.valueOf(T_PayDiscount_Rev_Acct));
	}

	/** Get Steuerkorrektur erhaltene Skonti.
		@return Steuerabhängiges Konto zur Verbuchung erhaltener Skonti
	  */
	@Override
	public int getT_PayDiscount_Rev_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_PayDiscount_Rev_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getT_Receivables_A()
	{
		return get_ValueAsPO(COLUMNNAME_T_Receivables_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_Receivables_A(org.compiere.model.I_C_ValidCombination T_Receivables_A)
	{
		set_ValueFromPO(COLUMNNAME_T_Receivables_Acct, org.compiere.model.I_C_ValidCombination.class, T_Receivables_A);
	}

	/** Set Steuerüberzahlungen.
		@param T_Receivables_Acct 
		Konto für Steuerüberzahlungen
	  */
	@Override
	public void setT_Receivables_Acct (int T_Receivables_Acct)
	{
		set_Value (COLUMNNAME_T_Receivables_Acct, Integer.valueOf(T_Receivables_Acct));
	}

	/** Get Steuerüberzahlungen.
		@return Konto für Steuerüberzahlungen
	  */
	@Override
	public int getT_Receivables_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_Receivables_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getT_Revenue_A()
	{
		return get_ValueAsPO(COLUMNNAME_T_Revenue_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setT_Revenue_A(org.compiere.model.I_C_ValidCombination T_Revenue_A)
	{
		set_ValueFromPO(COLUMNNAME_T_Revenue_Acct, org.compiere.model.I_C_ValidCombination.class, T_Revenue_A);
	}

	/** Set Erlös Konto.
		@param T_Revenue_Acct 
		Steuerabhängiges Konto zur Verbuchung Erlöse
	  */
	@Override
	public void setT_Revenue_Acct (int T_Revenue_Acct)
	{
		set_Value (COLUMNNAME_T_Revenue_Acct, Integer.valueOf(T_Revenue_Acct));
	}

	/** Get Erlös Konto.
		@return Steuerabhängiges Konto zur Verbuchung Erlöse
	  */
	@Override
	public int getT_Revenue_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_T_Revenue_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}