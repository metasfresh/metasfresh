/** Generated Model - DO NOT CHANGE */
package de.metas.banking.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Payment_Request
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Payment_Request extends org.compiere.model.PO implements I_C_Payment_Request, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -704023088L;

    /** Standard Constructor */
    public X_C_Payment_Request (Properties ctx, int C_Payment_Request_ID, String trxName)
    {
      super (ctx, C_Payment_Request_ID, trxName);
      /** if (C_Payment_Request_ID == 0)
        {
			setAmount (BigDecimal.ZERO);
			setC_Payment_Request_ID (0);
			setIsSOTrx (false); // N
        } */
    }

    /** Load Constructor */
    public X_C_Payment_Request (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Betrag.
		@param Amount 
		Betrag in einer definierten Währung
	  */
	@Override
	public void setAmount (java.math.BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Betrag.
		@return Betrag in einer definierten Währung
	  */
	@Override
	public java.math.BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	/** Set Rechnung.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Rechnung.
		@return Invoice Identifier
	  */
	@Override
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zahlungsaufforderung.
		@param C_Payment_Request_ID Zahlungsaufforderung	  */
	@Override
	public void setC_Payment_Request_ID (int C_Payment_Request_ID)
	{
		if (C_Payment_Request_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Payment_Request_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Payment_Request_ID, Integer.valueOf(C_Payment_Request_ID));
	}

	/** Get Zahlungsaufforderung.
		@return Zahlungsaufforderung	  */
	@Override
	public int getC_Payment_Request_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_Request_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Eingelesene Zeichenkette.
		@param FullPaymentString 
		Im Fall von ESR oder anderen von Zahlschein gelesenen Zahlungsaufforderungen ist dies der komplette vom Schein eingelesene String
	  */
	@Override
	public void setFullPaymentString (java.lang.String FullPaymentString)
	{
		set_Value (COLUMNNAME_FullPaymentString, FullPaymentString);
	}

	/** Get Eingelesene Zeichenkette.
		@return Im Fall von ESR oder anderen von Zahlschein gelesenen Zahlungsaufforderungen ist dies der komplette vom Schein eingelesene String
	  */
	@Override
	public java.lang.String getFullPaymentString () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FullPaymentString);
	}

	/** Set Sales Transaction.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	@Override
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Sales Transaction.
		@return This is a Sales Transaction
	  */
	@Override
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Referenz.
		@param Reference 
		Bezug für diesen Eintrag
	  */
	@Override
	public void setReference (java.lang.String Reference)
	{
		set_Value (COLUMNNAME_Reference, Reference);
	}

	/** Get Referenz.
		@return Bezug für diesen Eintrag
	  */
	@Override
	public java.lang.String getReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Reference);
	}
}