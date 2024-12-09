<<<<<<< HEAD
/** Generated Model - DO NOT CHANGE */
=======
// Generated Model - DO NOT CHANGE
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
<<<<<<< HEAD

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
=======
import javax.annotation.Nullable;

/** Generated Model for C_PaySelectionLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_PaySelectionLine extends org.compiere.model.PO implements I_C_PaySelectionLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -207168288L;

    /** Standard Constructor */
    public X_C_PaySelectionLine (final Properties ctx, final int C_PaySelectionLine_ID, @Nullable final String trxName)
    {
      super (ctx, C_PaySelectionLine_ID, trxName);
    }

    /** Load Constructor */
    public X_C_PaySelectionLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, rs, trxName);
    }


<<<<<<< HEAD
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
=======
	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setC_BankStatement_ID (final int C_BankStatement_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_BankStatement_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatement_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_Value (COLUMNNAME_C_BankStatement_ID, C_BankStatement_ID);
	}

	@Override
	public int getC_BankStatement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatement_ID);
	}

	@Override
	public void setC_BankStatementLine_ID (final int C_BankStatementLine_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_BankStatementLine_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatementLine_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_Value (COLUMNNAME_C_BankStatementLine_ID, C_BankStatementLine_ID);
	}

	@Override
	public int getC_BankStatementLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatementLine_ID);
	}

	@Override
	public void setC_BankStatementLine_Ref_ID (final int C_BankStatementLine_Ref_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_BankStatementLine_Ref_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatementLine_Ref_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_Value (COLUMNNAME_C_BankStatementLine_Ref_ID, C_BankStatementLine_Ref_ID);
	}

	@Override
	public int getC_BankStatementLine_Ref_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatementLine_Ref_ID);
	}

	@Override
	public void setC_BP_BankAccount_ID (final int C_BP_BankAccount_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, C_BP_BankAccount_ID);
	}

	@Override
	public int getC_BP_BankAccount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccount_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		throw new IllegalArgumentException ("C_Currency_ID is virtual column");	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
<<<<<<< HEAD
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
=======
	public void setC_Invoice(final org.compiere.model.I_C_Invoice C_Invoice)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

<<<<<<< HEAD
	/** Set Rechnung.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
=======
	@Override
	public void setC_Invoice_ID (final int C_Invoice_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_Value (COLUMNNAME_C_Invoice_ID, C_Invoice_ID);
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	@Override
	public void setC_Payment_ID (final int C_Payment_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_Value (COLUMNNAME_C_Payment_ID, C_Payment_ID);
	}

	@Override
	public int getC_Payment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Payment_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public org.compiere.model.I_C_PaySelection getC_PaySelection()
	{
		return get_ValueAsPO(COLUMNNAME_C_PaySelection_ID, org.compiere.model.I_C_PaySelection.class);
	}

	@Override
<<<<<<< HEAD
	public void setC_PaySelection(org.compiere.model.I_C_PaySelection C_PaySelection)
=======
	public void setC_PaySelection(final org.compiere.model.I_C_PaySelection C_PaySelection)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_C_PaySelection_ID, org.compiere.model.I_C_PaySelection.class, C_PaySelection);
	}

<<<<<<< HEAD
	/** Set Zahlung Anweisen.
		@param C_PaySelection_ID 
		Zahlung Anweisen
	  */
	@Override
	public void setC_PaySelection_ID (int C_PaySelection_ID)
=======
	@Override
	public void setC_PaySelection_ID (final int C_PaySelection_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_PaySelection_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PaySelection_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_ValueNoCheck (COLUMNNAME_C_PaySelection_ID, C_PaySelection_ID);
	}

	@Override
	public int getC_PaySelection_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaySelection_ID);
	}

	@Override
	public void setC_PaySelectionLine_ID (final int C_PaySelectionLine_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_PaySelectionLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PaySelectionLine_ID, null);
		else 
<<<<<<< HEAD
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
=======
			set_ValueNoCheck (COLUMNNAME_C_PaySelectionLine_ID, C_PaySelectionLine_ID);
	}

	@Override
	public int getC_PaySelectionLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaySelectionLine_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Description, Description);
	}

<<<<<<< HEAD
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
=======
	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setDifferenceAmt (final BigDecimal DifferenceAmt)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueNoCheck (COLUMNNAME_DifferenceAmt, DifferenceAmt);
	}

<<<<<<< HEAD
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
=======
	@Override
	public BigDecimal getDifferenceAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DifferenceAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDiscountAmt (final BigDecimal DiscountAmt)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_DiscountAmt, DiscountAmt);
	}

<<<<<<< HEAD
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
=======
	@Override
	public BigDecimal getDiscountAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DiscountAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setHasOpenCreditMemos (final boolean HasOpenCreditMemos)
	{
		throw new IllegalArgumentException ("HasOpenCreditMemos is virtual column");	}

	@Override
	public boolean isHasOpenCreditMemos() 
	{
		return get_ValueAsBoolean(COLUMNNAME_HasOpenCreditMemos);
	}

	@Override
	public void setHasOpenCreditMemos_Color_ID (final int HasOpenCreditMemos_Color_ID)
	{
		throw new IllegalArgumentException ("HasOpenCreditMemos_Color_ID is virtual column");	}

	@Override
	public int getHasOpenCreditMemos_Color_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_HasOpenCreditMemos_Color_ID);
	}

	@Override
	public void setHasOpenOutgoingPayments (final boolean HasOpenOutgoingPayments)
	{
		throw new IllegalArgumentException ("HasOpenOutgoingPayments is virtual column");	}

	@Override
	public boolean isHasOpenOutgoingPayments() 
	{
		return get_ValueAsBoolean(COLUMNNAME_HasOpenOutgoingPayments);
	}

	@Override
	public void setIsManual (final boolean IsManual)
	{
		set_Value (COLUMNNAME_IsManual, IsManual);
	}

	@Override
	public boolean isManual() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsManual);
	}

	@Override
	public void setIsSOTrx (final boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, IsSOTrx);
	}

	@Override
	public boolean isSOTrx() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSOTrx);
	}

	@Override
	public void setLine (final int Line)
	{
		set_Value (COLUMNNAME_Line, Line);
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
	}

	@Override
	public void setOpenAmt (final BigDecimal OpenAmt)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueNoCheck (COLUMNNAME_OpenAmt, OpenAmt);
	}

<<<<<<< HEAD
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
=======
	@Override
	public BigDecimal getOpenAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_OpenAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPayAmt (final BigDecimal PayAmt)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_PayAmt, PayAmt);
	}

<<<<<<< HEAD
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
=======
	@Override
	public BigDecimal getPayAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PayAmt);
		return bd != null ? bd : BigDecimal.ZERO;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
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
=======
	/** PayPal Extern = V */
	public static final String PAYMENTRULE_PayPalExtern = "V";
	/** Kreditkarte Extern = U */
	public static final String PAYMENTRULE_KreditkarteExtern = "U";
	/** Sofortüberweisung = R */
	public static final String PAYMENTRULE_Sofortueberweisung = "R";
	@Override
	public void setPaymentRule (final java.lang.String PaymentRule)
	{
		set_Value (COLUMNNAME_PaymentRule, PaymentRule);
	}

	@Override
	public java.lang.String getPaymentRule() 
	{
		return get_ValueAsString(COLUMNNAME_PaymentRule);
	}

	@Override
	public void setReference (final @Nullable java.lang.String Reference)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Reference, Reference);
	}

<<<<<<< HEAD
	/** Get Referenz.
		@return Bezug für diesen Eintrag
	  */
	@Override
	public java.lang.String getReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Reference);
=======
	@Override
	public java.lang.String getReference() 
	{
		return get_ValueAsString(COLUMNNAME_Reference);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}