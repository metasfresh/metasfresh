/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BankStatementLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_BankStatementLine extends org.compiere.model.PO implements I_C_BankStatementLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 318280785L;

    /** Standard Constructor */
    public X_C_BankStatementLine (Properties ctx, int C_BankStatementLine_ID, String trxName)
    {
      super (ctx, C_BankStatementLine_ID, trxName);
      /** if (C_BankStatementLine_ID == 0)
        {
			setC_BankStatement_ID (0);
			setC_BankStatementLine_ID (0);
			setC_Charge_ID (0);
			setC_Currency_ID (0); // @SQL=SELECT C_Currency_ID FROM C_BP_BankAccount WHERE C_BP_BankAccount_ID=@C_BP_BankAccount_ID@
			setChargeAmt (BigDecimal.ZERO);
			setDateAcct (new Timestamp( System.currentTimeMillis() )); // @StatementDate@
			setInterestAmt (BigDecimal.ZERO);
			setIsManual (true); // Y
			setIsOverUnderPayment (false); // N
			setIsReversal (false);
			setLine (0); // @SQL=SELECT COALESCE(MAX(Line),0)+10 FROM C_BankStatementLine WHERE C_BankStatement_ID=@C_BankStatement_ID@
			setProcessed (false);
			setStatementLineDate (new Timestamp( System.currentTimeMillis() )); // @StatementLineDate@
			setStmtAmt (BigDecimal.ZERO);
			setTrxAmt (BigDecimal.ZERO);
			setValutaDate (new Timestamp( System.currentTimeMillis() )); // @StatementDate@
        } */
    }

    /** Load Constructor */
    public X_C_BankStatementLine (Properties ctx, ResultSet rs, String trxName)
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
			set_ValueNoCheck (COLUMNNAME_C_BankStatement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BankStatement_ID, Integer.valueOf(C_BankStatement_ID));
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
		Line on a statement from this Bank
	  */
	@Override
	public void setC_BankStatementLine_ID (int C_BankStatementLine_ID)
	{
		if (C_BankStatementLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BankStatementLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BankStatementLine_ID, Integer.valueOf(C_BankStatementLine_ID));
	}

	/** Get Auszugs-Position.
		@return Line on a statement from this Bank
	  */
	@Override
	public int getC_BankStatementLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankStatementLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccountTo()
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_BankAccountTo_ID, org.compiere.model.I_C_BP_BankAccount.class);
	}

	@Override
	public void setC_BP_BankAccountTo(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccountTo)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_BankAccountTo_ID, org.compiere.model.I_C_BP_BankAccount.class, C_BP_BankAccountTo);
	}

	/** Set Cashbook/Bank account To.
		@param C_BP_BankAccountTo_ID 
		Cashbook/Bank account To
	  */
	@Override
	public void setC_BP_BankAccountTo_ID (int C_BP_BankAccountTo_ID)
	{
		if (C_BP_BankAccountTo_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccountTo_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccountTo_ID, Integer.valueOf(C_BP_BankAccountTo_ID));
	}

	/** Get Cashbook/Bank account To.
		@return Cashbook/Bank account To
	  */
	@Override
	public int getC_BP_BankAccountTo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_BankAccountTo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Identifies a Business Partner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kosten.
		@param C_Charge_ID 
		Additional document charges
	  */
	@Override
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Kosten.
		@return Additional document charges
	  */
	@Override
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Währung.
		@param C_Currency_ID 
		The Currency for this record
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return The Currency for this record
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
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	/** Set Auftrag.
		@param C_Order_ID 
		Order
	  */
	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Auftrag.
		@return Order
	  */
	@Override
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zahlung.
		@param C_Payment_ID 
		Payment identifier
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
		@return Payment identifier
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
			set_Value (COLUMNNAME_C_PaySelection_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaySelection_ID, Integer.valueOf(C_PaySelection_ID));
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

	/** Set Gebühr.
		@param ChargeAmt Gebühr	  */
	@Override
	public void setChargeAmt (java.math.BigDecimal ChargeAmt)
	{
		set_Value (COLUMNNAME_ChargeAmt, ChargeAmt);
	}

	/** Get Gebühr.
		@return Gebühr	  */
	@Override
	public java.math.BigDecimal getChargeAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ChargeAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Zahlung erstellen.
		@param CreatePayment Zahlung erstellen	  */
	@Override
	public void setCreatePayment (java.lang.String CreatePayment)
	{
		set_Value (COLUMNNAME_CreatePayment, CreatePayment);
	}

	/** Get Zahlung erstellen.
		@return Zahlung erstellen	  */
	@Override
	public java.lang.String getCreatePayment () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreatePayment);
	}

	/** Set Wechselkurs.
		@param CurrencyRate 
		Wechselkurs für Währung
	  */
	@Override
	public void setCurrencyRate (java.math.BigDecimal CurrencyRate)
	{
		set_Value (COLUMNNAME_CurrencyRate, CurrencyRate);
	}

	/** Get Wechselkurs.
		@return Wechselkurs für Währung
	  */
	@Override
	public java.math.BigDecimal getCurrencyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrencyRate);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Buchungsdatum.
		@param DateAcct 
		Accounting Date
	  */
	@Override
	public void setDateAcct (java.sql.Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Buchungsdatum.
		@return Accounting Date
	  */
	@Override
	public java.sql.Timestamp getDateAcct () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateAcct);
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

	/** Set ELV-Betrag.
		@param EftAmt 
		Electronic Funds Transfer Amount
	  */
	@Override
	public void setEftAmt (java.math.BigDecimal EftAmt)
	{
		set_Value (COLUMNNAME_EftAmt, EftAmt);
	}

	/** Get ELV-Betrag.
		@return Electronic Funds Transfer Amount
	  */
	@Override
	public java.math.BigDecimal getEftAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_EftAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set ELV Scheck-Nr..
		@param EftCheckNo 
		Electronic Funds Transfer Check No
	  */
	@Override
	public void setEftCheckNo (java.lang.String EftCheckNo)
	{
		set_Value (COLUMNNAME_EftCheckNo, EftCheckNo);
	}

	/** Get ELV Scheck-Nr..
		@return Electronic Funds Transfer Check No
	  */
	@Override
	public java.lang.String getEftCheckNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EftCheckNo);
	}

	/** Set ELV-Währung.
		@param EftCurrency 
		Electronic Funds Transfer Currency
	  */
	@Override
	public void setEftCurrency (java.lang.String EftCurrency)
	{
		set_Value (COLUMNNAME_EftCurrency, EftCurrency);
	}

	/** Get ELV-Währung.
		@return Electronic Funds Transfer Currency
	  */
	@Override
	public java.lang.String getEftCurrency () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EftCurrency);
	}

	/** Set ELV Memo.
		@param EftMemo 
		Electronic Funds Transfer Memo
	  */
	@Override
	public void setEftMemo (java.lang.String EftMemo)
	{
		set_Value (COLUMNNAME_EftMemo, EftMemo);
	}

	/** Get ELV Memo.
		@return Electronic Funds Transfer Memo
	  */
	@Override
	public java.lang.String getEftMemo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EftMemo);
	}

	/** Set ELV-Zahlungsempfänger.
		@param EftPayee 
		Electronic Funds Transfer Payee information
	  */
	@Override
	public void setEftPayee (java.lang.String EftPayee)
	{
		set_Value (COLUMNNAME_EftPayee, EftPayee);
	}

	/** Get ELV-Zahlungsempfänger.
		@return Electronic Funds Transfer Payee information
	  */
	@Override
	public java.lang.String getEftPayee () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EftPayee);
	}

	/** Set Konto ELV-Zahlungsempfänger.
		@param EftPayeeAccount 
		Electronic Funds Transfer Payyee Account Information
	  */
	@Override
	public void setEftPayeeAccount (java.lang.String EftPayeeAccount)
	{
		set_Value (COLUMNNAME_EftPayeeAccount, EftPayeeAccount);
	}

	/** Get Konto ELV-Zahlungsempfänger.
		@return Electronic Funds Transfer Payyee Account Information
	  */
	@Override
	public java.lang.String getEftPayeeAccount () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EftPayeeAccount);
	}

	/** Set EFT Reference.
		@param EftReference 
		Electronic Funds Transfer Reference
	  */
	@Override
	public void setEftReference (java.lang.String EftReference)
	{
		set_Value (COLUMNNAME_EftReference, EftReference);
	}

	/** Get EFT Reference.
		@return Electronic Funds Transfer Reference
	  */
	@Override
	public java.lang.String getEftReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EftReference);
	}

	/** Set Datum ELV-Position.
		@param EftStatementLineDate 
		Electronic Funds Transfer Statement Line Date
	  */
	@Override
	public void setEftStatementLineDate (java.sql.Timestamp EftStatementLineDate)
	{
		set_Value (COLUMNNAME_EftStatementLineDate, EftStatementLineDate);
	}

	/** Get Datum ELV-Position.
		@return Electronic Funds Transfer Statement Line Date
	  */
	@Override
	public java.sql.Timestamp getEftStatementLineDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_EftStatementLineDate);
	}

	/** Set ELV-TransaktionsID.
		@param EftTrxID 
		Electronic Funds Transfer Transaction ID
	  */
	@Override
	public void setEftTrxID (java.lang.String EftTrxID)
	{
		set_Value (COLUMNNAME_EftTrxID, EftTrxID);
	}

	/** Get ELV-TransaktionsID.
		@return Electronic Funds Transfer Transaction ID
	  */
	@Override
	public java.lang.String getEftTrxID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EftTrxID);
	}

	/** Set ELV-Transaktionsart.
		@param EftTrxType 
		Electronic Funds Transfer Transaction Type
	  */
	@Override
	public void setEftTrxType (java.lang.String EftTrxType)
	{
		set_Value (COLUMNNAME_EftTrxType, EftTrxType);
	}

	/** Get ELV-Transaktionsart.
		@return Electronic Funds Transfer Transaction Type
	  */
	@Override
	public java.lang.String getEftTrxType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EftTrxType);
	}

	/** Set ELV Wertstellungs-Datum.
		@param EftValutaDate 
		Electronic Funds Transfer Valuta (effective) Date
	  */
	@Override
	public void setEftValutaDate (java.sql.Timestamp EftValutaDate)
	{
		set_Value (COLUMNNAME_EftValutaDate, EftValutaDate);
	}

	/** Get ELV Wertstellungs-Datum.
		@return Electronic Funds Transfer Valuta (effective) Date
	  */
	@Override
	public java.sql.Timestamp getEftValutaDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_EftValutaDate);
	}

	/** Set ESR Zahlungsimport.
		@param ESR_Import_ID ESR Zahlungsimport	  */
	@Override
	public void setESR_Import_ID (int ESR_Import_ID)
	{
		if (ESR_Import_ID < 1) 
			set_Value (COLUMNNAME_ESR_Import_ID, null);
		else 
			set_Value (COLUMNNAME_ESR_Import_ID, Integer.valueOf(ESR_Import_ID));
	}

	/** Get ESR Zahlungsimport.
		@return ESR Zahlungsimport	  */
	@Override
	public int getESR_Import_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ESR_Import_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Imported Bill Partner IBAN.
		@param ImportedBillPartnerIBAN Imported Bill Partner IBAN	  */
	@Override
	public void setImportedBillPartnerIBAN (java.lang.String ImportedBillPartnerIBAN)
	{
		set_Value (COLUMNNAME_ImportedBillPartnerIBAN, ImportedBillPartnerIBAN);
	}

	/** Get Imported Bill Partner IBAN.
		@return Imported Bill Partner IBAN	  */
	@Override
	public java.lang.String getImportedBillPartnerIBAN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ImportedBillPartnerIBAN);
	}

	/** Set Importierter Rechnungspartner Name.
		@param ImportedBillPartnerName 
		Name of the Bill Partner as appears in the import file
	  */
	@Override
	public void setImportedBillPartnerName (java.lang.String ImportedBillPartnerName)
	{
		set_Value (COLUMNNAME_ImportedBillPartnerName, ImportedBillPartnerName);
	}

	/** Get Importierter Rechnungspartner Name.
		@return Name of the Bill Partner as appears in the import file
	  */
	@Override
	public java.lang.String getImportedBillPartnerName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ImportedBillPartnerName);
	}

	/** Set Interest Amount.
		@param InterestAmt 
		Interest Amount
	  */
	@Override
	public void setInterestAmt (java.math.BigDecimal InterestAmt)
	{
		set_Value (COLUMNNAME_InterestAmt, InterestAmt);
	}

	/** Get Interest Amount.
		@return Interest Amount
	  */
	@Override
	public java.math.BigDecimal getInterestAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_InterestAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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

	/** Set More than one payment.
		@param IsMultiplePayment 
		The bankstatement line references more than one payment
	  */
	@Override
	public void setIsMultiplePayment (boolean IsMultiplePayment)
	{
		set_Value (COLUMNNAME_IsMultiplePayment, Boolean.valueOf(IsMultiplePayment));
	}

	/** Get More than one payment.
		@return The bankstatement line references more than one payment
	  */
	@Override
	public boolean isMultiplePayment () 
	{
		Object oo = get_Value(COLUMNNAME_IsMultiplePayment);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Multiple Payments or Invoices.
		@param IsMultiplePaymentOrInvoice 
		Bankstatement line with multiple payments/invoices
	  */
	@Override
	public void setIsMultiplePaymentOrInvoice (boolean IsMultiplePaymentOrInvoice)
	{
		set_Value (COLUMNNAME_IsMultiplePaymentOrInvoice, Boolean.valueOf(IsMultiplePaymentOrInvoice));
	}

	/** Get Multiple Payments or Invoices.
		@return Bankstatement line with multiple payments/invoices
	  */
	@Override
	public boolean isMultiplePaymentOrInvoice () 
	{
		Object oo = get_Value(COLUMNNAME_IsMultiplePaymentOrInvoice);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Over/Under Payment.
		@param IsOverUnderPayment 
		Over-Payment (unallocated) or Under-Payment (partial payment)
	  */
	@Override
	public void setIsOverUnderPayment (boolean IsOverUnderPayment)
	{
		set_Value (COLUMNNAME_IsOverUnderPayment, Boolean.valueOf(IsOverUnderPayment));
	}

	/** Get Over/Under Payment.
		@return Over-Payment (unallocated) or Under-Payment (partial payment)
	  */
	@Override
	public boolean isOverUnderPayment () 
	{
		Object oo = get_Value(COLUMNNAME_IsOverUnderPayment);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Umkehrung.
		@param IsReversal 
		This is a reversing transaction
	  */
	@Override
	public void setIsReversal (boolean IsReversal)
	{
		set_Value (COLUMNNAME_IsReversal, Boolean.valueOf(IsReversal));
	}

	/** Get Umkehrung.
		@return This is a reversing transaction
	  */
	@Override
	public boolean isReversal () 
	{
		Object oo = get_Value(COLUMNNAME_IsReversal);
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

	/** 
	 * LineType AD_Reference_ID=541111
	 * Reference name: C_BankStatementLineType
	 */
	public static final int LINETYPE_AD_Reference_ID=541111;
	/** Payment = P */
	public static final String LINETYPE_Payment = "P";
	/** PaySelection = PS */
	public static final String LINETYPE_PaySelection = "PS";
	/** ESR_Import = ESR */
	public static final String LINETYPE_ESR_Import = "ESR";
	/** Set Zeilenart.
		@param LineType Zeilenart	  */
	@Override
	public void setLineType (java.lang.String LineType)
	{

		set_Value (COLUMNNAME_LineType, LineType);
	}

	/** Get Zeilenart.
		@return Zeilenart	  */
	@Override
	public java.lang.String getLineType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LineType);
	}

	/** Set Linked Statement Line.
		@param Link_BankStatementLine_ID Linked Statement Line	  */
	@Override
	public void setLink_BankStatementLine_ID (int Link_BankStatementLine_ID)
	{
		if (Link_BankStatementLine_ID < 1) 
			set_Value (COLUMNNAME_Link_BankStatementLine_ID, null);
		else 
			set_Value (COLUMNNAME_Link_BankStatementLine_ID, Integer.valueOf(Link_BankStatementLine_ID));
	}

	/** Get Linked Statement Line.
		@return Linked Statement Line	  */
	@Override
	public int getLink_BankStatementLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Link_BankStatementLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Match Statement.
		@param MatchStatement Match Statement	  */
	@Override
	public void setMatchStatement (java.lang.String MatchStatement)
	{
		set_Value (COLUMNNAME_MatchStatement, MatchStatement);
	}

	/** Get Match Statement.
		@return Match Statement	  */
	@Override
	public java.lang.String getMatchStatement () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MatchStatement);
	}

	/** Set Memo.
		@param Memo 
		Memo Text
	  */
	@Override
	public void setMemo (java.lang.String Memo)
	{
		set_Value (COLUMNNAME_Memo, Memo);
	}

	/** Get Memo.
		@return Memo Text
	  */
	@Override
	public java.lang.String getMemo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Memo);
	}

	/** Set Over/Under Payment.
		@param OverUnderAmt 
		Over-Payment (unallocated) or Under-Payment (partial payment) Amount
	  */
	@Override
	public void setOverUnderAmt (java.math.BigDecimal OverUnderAmt)
	{
		set_Value (COLUMNNAME_OverUnderAmt, OverUnderAmt);
	}

	/** Get Over/Under Payment.
		@return Over-Payment (unallocated) or Under-Payment (partial payment) Amount
	  */
	@Override
	public java.math.BigDecimal getOverUnderAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_OverUnderAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Datensatz verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Datensatz verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Referenznummer.
		@param ReferenceNo 
		Your customer or vendor number at the Business Partner's site
	  */
	@Override
	public void setReferenceNo (java.lang.String ReferenceNo)
	{
		set_Value (COLUMNNAME_ReferenceNo, ReferenceNo);
	}

	/** Get Referenznummer.
		@return Your customer or vendor number at the Business Partner's site
	  */
	@Override
	public java.lang.String getReferenceNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReferenceNo);
	}

	/** Set Valuta Datum.
		@param StatementLineDate 
		Valuta Datum
	  */
	@Override
	public void setStatementLineDate (java.sql.Timestamp StatementLineDate)
	{
		set_Value (COLUMNNAME_StatementLineDate, StatementLineDate);
	}

	/** Get Valuta Datum.
		@return Valuta Datum
	  */
	@Override
	public java.sql.Timestamp getStatementLineDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_StatementLineDate);
	}

	/** Set Statement amount.
		@param StmtAmt 
		Statement Amount
	  */
	@Override
	public void setStmtAmt (java.math.BigDecimal StmtAmt)
	{
		set_Value (COLUMNNAME_StmtAmt, StmtAmt);
	}

	/** Get Statement amount.
		@return Statement Amount
	  */
	@Override
	public java.math.BigDecimal getStmtAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_StmtAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Bewegungs-Betrag.
		@param TrxAmt 
		Amount of a transaction
	  */
	@Override
	public void setTrxAmt (java.math.BigDecimal TrxAmt)
	{
		set_Value (COLUMNNAME_TrxAmt, TrxAmt);
	}

	/** Get Bewegungs-Betrag.
		@return Amount of a transaction
	  */
	@Override
	public java.math.BigDecimal getTrxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TrxAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Effective date.
		@param ValutaDate 
		Date when money is available
	  */
	@Override
	public void setValutaDate (java.sql.Timestamp ValutaDate)
	{
		set_Value (COLUMNNAME_ValutaDate, ValutaDate);
	}

	/** Get Effective date.
		@return Date when money is available
	  */
	@Override
	public java.sql.Timestamp getValutaDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValutaDate);
	}

	/** Set Write-off Amount.
		@param WriteOffAmt 
		Amount to write-off
	  */
	@Override
	public void setWriteOffAmt (java.math.BigDecimal WriteOffAmt)
	{
		set_Value (COLUMNNAME_WriteOffAmt, WriteOffAmt);
	}

	/** Get Write-off Amount.
		@return Amount to write-off
	  */
	@Override
	public java.math.BigDecimal getWriteOffAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WriteOffAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}