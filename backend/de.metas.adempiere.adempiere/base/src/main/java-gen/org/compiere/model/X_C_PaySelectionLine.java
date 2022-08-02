// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
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
	public void setC_BankStatement_ID (final int C_BankStatement_ID)
	{
		if (C_BankStatement_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatement_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatement_ID, C_BankStatement_ID);
	}

	@Override
	public int getC_BankStatement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatement_ID);
	}

	@Override
	public void setC_BankStatementLine_ID (final int C_BankStatementLine_ID)
	{
		if (C_BankStatementLine_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatementLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatementLine_ID, C_BankStatementLine_ID);
	}

	@Override
	public int getC_BankStatementLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatementLine_ID);
	}

	@Override
	public void setC_BankStatementLine_Ref_ID (final int C_BankStatementLine_Ref_ID)
	{
		if (C_BankStatementLine_Ref_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatementLine_Ref_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatementLine_Ref_ID, C_BankStatementLine_Ref_ID);
	}

	@Override
	public int getC_BankStatementLine_Ref_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BankStatementLine_Ref_ID);
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
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
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
	public void setC_Payment_ID (final int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, C_Payment_ID);
	}

	@Override
	public int getC_Payment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Payment_ID);
	}

	@Override
	public org.compiere.model.I_C_PaySelection getC_PaySelection()
	{
		return get_ValueAsPO(COLUMNNAME_C_PaySelection_ID, org.compiere.model.I_C_PaySelection.class);
	}

	@Override
	public void setC_PaySelection(final org.compiere.model.I_C_PaySelection C_PaySelection)
	{
		set_ValueFromPO(COLUMNNAME_C_PaySelection_ID, org.compiere.model.I_C_PaySelection.class, C_PaySelection);
	}

	@Override
	public void setC_PaySelection_ID (final int C_PaySelection_ID)
	{
		if (C_PaySelection_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PaySelection_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PaySelection_ID, C_PaySelection_ID);
	}

	@Override
	public int getC_PaySelection_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaySelection_ID);
	}

	@Override
	public void setC_PaySelectionLine_ID (final int C_PaySelectionLine_ID)
	{
		if (C_PaySelectionLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PaySelectionLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PaySelectionLine_ID, C_PaySelectionLine_ID);
	}

	@Override
	public int getC_PaySelectionLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaySelectionLine_ID);
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
	public void setDifferenceAmt (final BigDecimal DifferenceAmt)
	{
		set_ValueNoCheck (COLUMNNAME_DifferenceAmt, DifferenceAmt);
	}

	@Override
	public BigDecimal getDifferenceAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DifferenceAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDiscountAmt (final BigDecimal DiscountAmt)
	{
		set_Value (COLUMNNAME_DiscountAmt, DiscountAmt);
	}

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
	{
		set_ValueNoCheck (COLUMNNAME_OpenAmt, OpenAmt);
	}

	@Override
	public BigDecimal getOpenAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_OpenAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setPayAmt (final BigDecimal PayAmt)
	{
		set_Value (COLUMNNAME_PayAmt, PayAmt);
	}

	@Override
	public BigDecimal getPayAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PayAmt);
		return bd != null ? bd : BigDecimal.ZERO;
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
	{
		set_Value (COLUMNNAME_Reference, Reference);
	}

	@Override
	public java.lang.String getReference() 
	{
		return get_ValueAsString(COLUMNNAME_Reference);
	}
}