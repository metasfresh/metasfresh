/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_PaySelectionLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_PaySelectionLine extends org.compiere.model.PO implements I_C_PaySelectionLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1643620282L;

    /** Standard Constructor */
    public X_C_PaySelectionLine (Properties ctx, int C_PaySelectionLine_ID, String trxName)
    {
      super (ctx, C_PaySelectionLine_ID, trxName);
      /** if (C_PaySelectionLine_ID == 0)
        {
			setC_Invoice_ID (0);
			setC_PaySelection_ID (0);
			setC_PaySelectionLine_ID (0);
			setDifferenceAmt (BigDecimal.ZERO);
			setDiscountAmt (BigDecimal.ZERO);
			setIsManual (false);
			setIsSOTrx (false);
			setLine (0); // @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM C_PaySelectionLine WHERE C_PaySelection_ID=@C_PaySelection_ID@
			setOpenAmt (BigDecimal.ZERO);
			setPayAmt (BigDecimal.ZERO);
			setPaymentRule (null); // P
        } */
    }

    /** Load Constructor */
    public X_C_PaySelectionLine (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Bankauszug.
		@param C_BankStatement_ID 
		Bank Statement of account
	  */
	@Override
	public void setC_BankStatement_ID (int C_BankStatement_ID)
	{
		if (C_BankStatement_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatement_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatement_ID, Integer.valueOf(C_BankStatement_ID));
	}

	/** Get Bankauszug.
		@return Bank Statement of account
	  */
	@Override
	public int getC_BankStatement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankStatement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Auszugs-Position.
		@param C_BankStatementLine_ID 
		Position auf einem Bankauszug zu dieser Bank
	  */
	@Override
	public void setC_BankStatementLine_ID (int C_BankStatementLine_ID)
	{
		if (C_BankStatementLine_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatementLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatementLine_ID, Integer.valueOf(C_BankStatementLine_ID));
	}

	/** Get Auszugs-Position.
		@return Position auf einem Bankauszug zu dieser Bank
	  */
	@Override
	public int getC_BankStatementLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankStatementLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bankstatementline Reference.
		@param C_BankStatementLine_Ref_ID Bankstatementline Reference	  */
	@Override
	public void setC_BankStatementLine_Ref_ID (int C_BankStatementLine_Ref_ID)
	{
		if (C_BankStatementLine_Ref_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatementLine_Ref_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatementLine_Ref_ID, Integer.valueOf(C_BankStatementLine_Ref_ID));
	}

	/** Get Bankstatementline Reference.
		@return Bankstatementline Reference	  */
	@Override
	public int getC_BankStatementLine_Ref_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankStatementLine_Ref_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount()
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
		Bank Account of the Business Partner
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
		@return Bank Account of the Business Partner
	  */
	@Override
	public int getC_BP_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Währung.
		@param C_Currency_ID 
		Die Währung für diesen Eintrag
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		throw new IllegalArgumentException ("C_Currency_ID is virtual column");	}

	/** Get Währung.
		@return Die Währung für diesen Eintrag
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice()
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

	@Override
	public org.compiere.model.I_C_Payment getC_Payment()
	{
		return get_ValueAsPO(COLUMNNAME_C_Payment_ID, org.compiere.model.I_C_Payment.class);
	}

	@Override
	public void setC_Payment(org.compiere.model.I_C_Payment C_Payment)
	{
		set_ValueFromPO(COLUMNNAME_C_Payment_ID, org.compiere.model.I_C_Payment.class, C_Payment);
	}

	/** Set Zahlung.
		@param C_Payment_ID 
		Zahlung
	  */
	@Override
	public void setC_Payment_ID (int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
	}

	/** Get Zahlung.
		@return Zahlung
	  */
	@Override
	public int getC_Payment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_PaySelection getC_PaySelection()
	{
		return get_ValueAsPO(COLUMNNAME_C_PaySelection_ID, org.compiere.model.I_C_PaySelection.class);
	}

	@Override
	public void setC_PaySelection(org.compiere.model.I_C_PaySelection C_PaySelection)
	{
		set_ValueFromPO(COLUMNNAME_C_PaySelection_ID, org.compiere.model.I_C_PaySelection.class, C_PaySelection);
	}

	/** Set Zahlung Anweisen.
		@param C_PaySelection_ID 
		Zahlung Anweisen
	  */
	@Override
	public void setC_PaySelection_ID (int C_PaySelection_ID)
	{
		if (C_PaySelection_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PaySelection_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PaySelection_ID, Integer.valueOf(C_PaySelection_ID));
	}

	/** Get Zahlung Anweisen.
		@return Zahlung Anweisen
	  */
	@Override
	public int getC_PaySelection_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaySelection_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zahlungsauswahl- Position.
		@param C_PaySelectionLine_ID 
		Payment Selection Line
	  */
	@Override
	public void setC_PaySelectionLine_ID (int C_PaySelectionLine_ID)
	{
		if (C_PaySelectionLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PaySelectionLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PaySelectionLine_ID, Integer.valueOf(C_PaySelectionLine_ID));
	}

	/** Get Zahlungsauswahl- Position.
		@return Payment Selection Line
	  */
	@Override
	public int getC_PaySelectionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaySelectionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Differenz.
		@param DifferenceAmt 
		Difference Amount
	  */
	@Override
	public void setDifferenceAmt (java.math.BigDecimal DifferenceAmt)
	{
		set_ValueNoCheck (COLUMNNAME_DifferenceAmt, DifferenceAmt);
	}

	/** Get Differenz.
		@return Difference Amount
	  */
	@Override
	public java.math.BigDecimal getDifferenceAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DifferenceAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Skonto.
		@param DiscountAmt 
		Calculated amount of discount
	  */
	@Override
	public void setDiscountAmt (java.math.BigDecimal DiscountAmt)
	{
		set_Value (COLUMNNAME_DiscountAmt, DiscountAmt);
	}

	/** Get Skonto.
		@return Calculated amount of discount
	  */
	@Override
	public java.math.BigDecimal getDiscountAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DiscountAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Has Open Credit Memo.
		@param HasOpenCreditMemos 
		Has Open Credit Memo Invoices
	  */
	@Override
	public void setHasOpenCreditMemos (boolean HasOpenCreditMemos)
	{
		throw new IllegalArgumentException ("HasOpenCreditMemos is virtual column");	}

	/** Get Has Open Credit Memo.
		@return Has Open Credit Memo Invoices
	  */
	@Override
	public boolean isHasOpenCreditMemos () 
	{
		Object oo = get_Value(COLUMNNAME_HasOpenCreditMemos);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Has Open Outgoing payments.
		@param HasOpenOutgoingPayments Has Open Outgoing payments	  */
	@Override
	public void setHasOpenOutgoingPayments (boolean HasOpenOutgoingPayments)
	{
		throw new IllegalArgumentException ("HasOpenOutgoingPayments is virtual column");	}

	/** Get Has Open Outgoing payments.
		@return Has Open Outgoing payments	  */
	@Override
	public boolean isHasOpenOutgoingPayments () 
	{
		Object oo = get_Value(COLUMNNAME_HasOpenOutgoingPayments);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Manuell.
		@param IsManual 
		This is a manual process
	  */
	@Override
	public void setIsManual (boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, Boolean.valueOf(IsManual));
	}

	/** Get Manuell.
		@return This is a manual process
	  */
	@Override
	public boolean isManual () 
	{
		Object oo = get_Value(COLUMNNAME_IsManual);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verkaufs-Transaktion.
		@param IsSOTrx 
		This is a Sales Transaction
	  */
	@Override
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Verkaufs-Transaktion.
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

	/** Set Zeile Nr..
		@param Line 
		Unique line for this document
	  */
	@Override
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Zeile Nr..
		@return Unique line for this document
	  */
	@Override
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Offener Betrag.
		@param OpenAmt Offener Betrag	  */
	@Override
	public void setOpenAmt (java.math.BigDecimal OpenAmt)
	{
		set_ValueNoCheck (COLUMNNAME_OpenAmt, OpenAmt);
	}

	/** Get Offener Betrag.
		@return Offener Betrag	  */
	@Override
	public java.math.BigDecimal getOpenAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OpenAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Offene Zahlungszuordnung.
		@param OpenPaymentAllocationForm Offene Zahlungszuordnung	  */
	@Override
	public void setOpenPaymentAllocationForm (java.lang.String OpenPaymentAllocationForm)
	{
		set_Value (COLUMNNAME_OpenPaymentAllocationForm, OpenPaymentAllocationForm);
	}

	/** Get Offene Zahlungszuordnung.
		@return Offene Zahlungszuordnung	  */
	@Override
	public java.lang.String getOpenPaymentAllocationForm () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OpenPaymentAllocationForm);
	}

	/** Set Zahlungsbetrag.
		@param PayAmt 
		Amount being paid
	  */
	@Override
	public void setPayAmt (java.math.BigDecimal PayAmt)
	{
		set_Value (COLUMNNAME_PayAmt, PayAmt);
	}

	/** Get Zahlungsbetrag.
		@return Amount being paid
	  */
	@Override
	public java.math.BigDecimal getPayAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PayAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** 
	 * PaymentRule AD_Reference_ID=195
	 * Reference name: _Payment Rule
	 */
	public static final int PAYMENTRULE_AD_Reference_ID=195;
	/** Cash = B */
	public static final String PAYMENTRULE_Cash = "B";
	/** CreditCard = K */
	public static final String PAYMENTRULE_CreditCard = "K";
	/** DirectDeposit = T */
	public static final String PAYMENTRULE_DirectDeposit = "T";
	/** Check = S */
	public static final String PAYMENTRULE_Check = "S";
	/** OnCredit = P */
	public static final String PAYMENTRULE_OnCredit = "P";
	/** DirectDebit = D */
	public static final String PAYMENTRULE_DirectDebit = "D";
	/** Mixed = M */
	public static final String PAYMENTRULE_Mixed = "M";
	/** PayPal = L */
	public static final String PAYMENTRULE_PayPal = "L";
	/** Set Zahlungsweise.
		@param PaymentRule 
		How you pay the invoice
	  */
	@Override
	public void setPaymentRule (java.lang.String PaymentRule)
	{

		set_Value (COLUMNNAME_PaymentRule, PaymentRule);
	}

	/** Get Zahlungsweise.
		@return How you pay the invoice
	  */
	@Override
	public java.lang.String getPaymentRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PaymentRule);
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