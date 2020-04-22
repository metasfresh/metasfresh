/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Payment
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Payment extends org.compiere.model.PO implements I_C_Payment, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 275325209L;

    /** Standard Constructor */
    public X_C_Payment (Properties ctx, int C_Payment_ID, String trxName)
    {
      super (ctx, C_Payment_ID, trxName);
      /** if (C_Payment_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_Currency_ID (0);
			setC_DocType_ID (0);
			setC_Payment_ID (0);
			setDateAcct (new Timestamp( System.currentTimeMillis() )); // @#Date@
			setDateTrx (new Timestamp( System.currentTimeMillis() )); // @#Date@
			setDocAction (null); // CO
			setDocStatus (null); // DR
			setDocumentNo (null);
			setIsAllocated (false);
			setIsApproved (false); // N
			setIsAutoAllocateAvailableAmt (false); // N
			setIsDelayedCapture (false);
			setIsOnline (false);
			setIsOnlineApproved (false); // N
			setIsOverUnderPayment (false); // N
			setIsPrepayment (false);
			setIsReceipt (false);
			setIsReconciled (false);
			setIsSelfService (false);
			setPayAmt (BigDecimal.ZERO); // 0
			setPosted (false); // N
			setProcessed (false);
			setTenderType (null); // A
			setTrxType (null); // S
        } */
    }

    /** Load Constructor */
    public X_C_Payment (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Ort.
		@param A_City 
		City or the Credit Card or Account Holder
	  */
	@Override
	public void setA_City (java.lang.String A_City)
	{
		set_Value (COLUMNNAME_A_City, A_City);
	}

	/** Get Ort.
		@return City or the Credit Card or Account Holder
	  */
	@Override
	public java.lang.String getA_City () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_City);
	}

	/** Set Land.
		@param A_Country 
		Country
	  */
	@Override
	public void setA_Country (java.lang.String A_Country)
	{
		set_Value (COLUMNNAME_A_Country, A_Country);
	}

	/** Get Land.
		@return Country
	  */
	@Override
	public java.lang.String getA_Country () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_Country);
	}

	/** Set EMail.
		@param A_EMail 
		Email Address
	  */
	@Override
	public void setA_EMail (java.lang.String A_EMail)
	{
		set_Value (COLUMNNAME_A_EMail, A_EMail);
	}

	/** Get EMail.
		@return Email Address
	  */
	@Override
	public java.lang.String getA_EMail () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_EMail);
	}

	/** Set Führerschein-Nr..
		@param A_Ident_DL 
		Payment Identification - Driver License
	  */
	@Override
	public void setA_Ident_DL (java.lang.String A_Ident_DL)
	{
		set_Value (COLUMNNAME_A_Ident_DL, A_Ident_DL);
	}

	/** Get Führerschein-Nr..
		@return Payment Identification - Driver License
	  */
	@Override
	public java.lang.String getA_Ident_DL () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_Ident_DL);
	}

	/** Set Ausweis-Nr..
		@param A_Ident_SSN 
		Payment Identification - Social Security No
	  */
	@Override
	public void setA_Ident_SSN (java.lang.String A_Ident_SSN)
	{
		set_Value (COLUMNNAME_A_Ident_SSN, A_Ident_SSN);
	}

	/** Get Ausweis-Nr..
		@return Payment Identification - Social Security No
	  */
	@Override
	public java.lang.String getA_Ident_SSN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_Ident_SSN);
	}

	/** Set Name auf Kreditkarte.
		@param A_Name 
		Name auf Kreditkarte oder des Kontoeigners
	  */
	@Override
	public void setA_Name (java.lang.String A_Name)
	{
		set_Value (COLUMNNAME_A_Name, A_Name);
	}

	/** Get Name auf Kreditkarte.
		@return Name auf Kreditkarte oder des Kontoeigners
	  */
	@Override
	public java.lang.String getA_Name () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_Name);
	}

	/** Set Bundesstaat / -land.
		@param A_State 
		State of the Credit Card or Account holder
	  */
	@Override
	public void setA_State (java.lang.String A_State)
	{
		set_Value (COLUMNNAME_A_State, A_State);
	}

	/** Get Bundesstaat / -land.
		@return State of the Credit Card or Account holder
	  */
	@Override
	public java.lang.String getA_State () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_State);
	}

	/** Set Straße.
		@param A_Street 
		Street address of the Credit Card or Account holder
	  */
	@Override
	public void setA_Street (java.lang.String A_Street)
	{
		set_Value (COLUMNNAME_A_Street, A_Street);
	}

	/** Get Straße.
		@return Street address of the Credit Card or Account holder
	  */
	@Override
	public java.lang.String getA_Street () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_Street);
	}

	/** Set Postleitzahl.
		@param A_Zip 
		Zip Code of the Credit Card or Account Holder
	  */
	@Override
	public void setA_Zip (java.lang.String A_Zip)
	{
		set_Value (COLUMNNAME_A_Zip, A_Zip);
	}

	/** Get Postleitzahl.
		@return Zip Code of the Credit Card or Account Holder
	  */
	@Override
	public java.lang.String getA_Zip () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_Zip);
	}

	/** Set Konto-Nr..
		@param AccountNo 
		Account Number
	  */
	@Override
	public void setAccountNo (java.lang.String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	/** Get Konto-Nr..
		@return Account Number
	  */
	@Override
	public java.lang.String getAccountNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AccountNo);
	}

	/** Set Buchende Organisation.
		@param AD_OrgTrx_ID 
		Performing or initiating organization
	  */
	@Override
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Buchende Organisation.
		@return Performing or initiating organization
	  */
	@Override
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kostenstelle.
		@param C_Activity_ID 
		Kostenstelle
	  */
	@Override
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Kostenstelle.
		@return Kostenstelle
	  */
	@Override
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Bankauszugszeile Referenz.
		@param C_BankStatementLine_Ref_ID Bankauszugszeile Referenz	  */
	@Override
	public void setC_BankStatementLine_Ref_ID (int C_BankStatementLine_Ref_ID)
	{
		if (C_BankStatementLine_Ref_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatementLine_Ref_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatementLine_Ref_ID, Integer.valueOf(C_BankStatementLine_Ref_ID));
	}

	/** Get Bankauszugszeile Referenz.
		@return Bankauszugszeile Referenz	  */
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

	@Override
	public org.compiere.model.I_C_Campaign getC_Campaign()
	{
		return get_ValueAsPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class);
	}

	@Override
	public void setC_Campaign(org.compiere.model.I_C_Campaign C_Campaign)
	{
		set_ValueFromPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class, C_Campaign);
	}

	/** Set Werbemassnahme.
		@param C_Campaign_ID 
		Marketing Campaign
	  */
	@Override
	public void setC_Campaign_ID (int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1) 
			set_Value (COLUMNNAME_C_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
	}

	/** Get Werbemassnahme.
		@return Marketing Campaign
	  */
	@Override
	public int getC_Campaign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Campaign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_CashBook getC_CashBook()
	{
		return get_ValueAsPO(COLUMNNAME_C_CashBook_ID, org.compiere.model.I_C_CashBook.class);
	}

	@Override
	public void setC_CashBook(org.compiere.model.I_C_CashBook C_CashBook)
	{
		set_ValueFromPO(COLUMNNAME_C_CashBook_ID, org.compiere.model.I_C_CashBook.class, C_CashBook);
	}

	/** Set Kassenbuch.
		@param C_CashBook_ID 
		Cash Book for recording petty cash transactions
	  */
	@Override
	public void setC_CashBook_ID (int C_CashBook_ID)
	{
		if (C_CashBook_ID < 1) 
			set_Value (COLUMNNAME_C_CashBook_ID, null);
		else 
			set_Value (COLUMNNAME_C_CashBook_ID, Integer.valueOf(C_CashBook_ID));
	}

	/** Get Kassenbuch.
		@return Cash Book for recording petty cash transactions
	  */
	@Override
	public int getC_CashBook_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_CashBook_ID);
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

	/** Set Kursart.
		@param C_ConversionType_ID 
		Currency Conversion Rate Type
	  */
	@Override
	public void setC_ConversionType_ID (int C_ConversionType_ID)
	{
		if (C_ConversionType_ID < 1) 
			set_Value (COLUMNNAME_C_ConversionType_ID, null);
		else 
			set_Value (COLUMNNAME_C_ConversionType_ID, Integer.valueOf(C_ConversionType_ID));
	}

	/** Get Kursart.
		@return Currency Conversion Rate Type
	  */
	@Override
	public int getC_ConversionType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ConversionType_ID);
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

	/** Set Belegart.
		@param C_DocType_ID 
		Document type or rules
	  */
	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Belegart.
		@return Document type or rules
	  */
	@Override
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_Payment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
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
	public org.compiere.model.I_C_PaymentBatch getC_PaymentBatch()
	{
		return get_ValueAsPO(COLUMNNAME_C_PaymentBatch_ID, org.compiere.model.I_C_PaymentBatch.class);
	}

	@Override
	public void setC_PaymentBatch(org.compiere.model.I_C_PaymentBatch C_PaymentBatch)
	{
		set_ValueFromPO(COLUMNNAME_C_PaymentBatch_ID, org.compiere.model.I_C_PaymentBatch.class, C_PaymentBatch);
	}

	/** Set Zahlungslauf.
		@param C_PaymentBatch_ID 
		Payment batch for EFT
	  */
	@Override
	public void setC_PaymentBatch_ID (int C_PaymentBatch_ID)
	{
		if (C_PaymentBatch_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentBatch_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentBatch_ID, Integer.valueOf(C_PaymentBatch_ID));
	}

	/** Get Zahlungslauf.
		@return Payment batch for EFT
	  */
	@Override
	public int getC_PaymentBatch_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentBatch_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Projekt.
		@param C_Project_ID 
		Financial Project
	  */
	@Override
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Projekt.
		@return Financial Project
	  */
	@Override
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
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

	/** Set Check No.
		@param CheckNo 
		Check Number
	  */
	@Override
	public void setCheckNo (java.lang.String CheckNo)
	{
		set_Value (COLUMNNAME_CheckNo, CheckNo);
	}

	/** Get Check No.
		@return Check Number
	  */
	@Override
	public java.lang.String getCheckNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CheckNo);
	}

	/** Set Exp. Monat.
		@param CreditCardExpMM 
		Expiry Month
	  */
	@Override
	public void setCreditCardExpMM (int CreditCardExpMM)
	{
		set_Value (COLUMNNAME_CreditCardExpMM, Integer.valueOf(CreditCardExpMM));
	}

	/** Get Exp. Monat.
		@return Expiry Month
	  */
	@Override
	public int getCreditCardExpMM () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CreditCardExpMM);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Exp. Jahr.
		@param CreditCardExpYY 
		Expiry Year
	  */
	@Override
	public void setCreditCardExpYY (int CreditCardExpYY)
	{
		set_Value (COLUMNNAME_CreditCardExpYY, Integer.valueOf(CreditCardExpYY));
	}

	/** Get Exp. Jahr.
		@return Expiry Year
	  */
	@Override
	public int getCreditCardExpYY () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CreditCardExpYY);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kreditkarten-Nummer.
		@param CreditCardNumber 
		Kreditkarten-Nummer
	  */
	@Override
	public void setCreditCardNumber (java.lang.String CreditCardNumber)
	{
		set_Value (COLUMNNAME_CreditCardNumber, CreditCardNumber);
	}

	/** Get Kreditkarten-Nummer.
		@return Kreditkarten-Nummer
	  */
	@Override
	public java.lang.String getCreditCardNumber () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreditCardNumber);
	}

	/** 
	 * CreditCardType AD_Reference_ID=149
	 * Reference name: C_Payment CreditCard Type
	 */
	public static final int CREDITCARDTYPE_AD_Reference_ID=149;
	/** Amex = A */
	public static final String CREDITCARDTYPE_Amex = "A";
	/** MasterCard = M */
	public static final String CREDITCARDTYPE_MasterCard = "M";
	/** Visa = V */
	public static final String CREDITCARDTYPE_Visa = "V";
	/** ATM = C */
	public static final String CREDITCARDTYPE_ATM = "C";
	/** Diners = D */
	public static final String CREDITCARDTYPE_Diners = "D";
	/** Discover = N */
	public static final String CREDITCARDTYPE_Discover = "N";
	/** Purchase Card = P */
	public static final String CREDITCARDTYPE_PurchaseCard = "P";
	/** Set Kreditkarte.
		@param CreditCardType 
		Credit Card (Visa, MC, AmEx)
	  */
	@Override
	public void setCreditCardType (java.lang.String CreditCardType)
	{

		set_Value (COLUMNNAME_CreditCardType, CreditCardType);
	}

	/** Get Kreditkarte.
		@return Credit Card (Visa, MC, AmEx)
	  */
	@Override
	public java.lang.String getCreditCardType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreditCardType);
	}

	/** Set Verifizierungs-Code.
		@param CreditCardVV 
		Credit Card Verification code on credit card
	  */
	@Override
	public void setCreditCardVV (java.lang.String CreditCardVV)
	{
		set_Value (COLUMNNAME_CreditCardVV, CreditCardVV);
	}

	/** Get Verifizierungs-Code.
		@return Credit Card Verification code on credit card
	  */
	@Override
	public java.lang.String getCreditCardVV () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreditCardVV);
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

	/** Set Datum.
		@param DateTrx 
		Vorgangsdatum
	  */
	@Override
	public void setDateTrx (java.sql.Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Datum.
		@return Vorgangsdatum
	  */
	@Override
	public java.sql.Timestamp getDateTrx () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateTrx);
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

	/** 
	 * DocAction AD_Reference_ID=135
	 * Reference name: _Document Action
	 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse_Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse_Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re_Activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** None = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** WaitComplete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** UnClose = UC */
	public static final String DOCACTION_UnClose = "UC";
	/** Set Belegverarbeitung.
		@param DocAction 
		The targeted status of the document
	  */
	@Override
	public void setDocAction (java.lang.String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Belegverarbeitung.
		@return The targeted status of the document
	  */
	@Override
	public java.lang.String getDocAction () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocAction);
	}

	/** 
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Belegstatus.
		@param DocStatus 
		The current status of the document
	  */
	@Override
	public void setDocStatus (java.lang.String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Belegstatus.
		@return The current status of the document
	  */
	@Override
	public java.lang.String getDocStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Nr..
		@param DocumentNo 
		Document sequence number of the document
	  */
	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Nr..
		@return Document sequence number of the document
	  */
	@Override
	public java.lang.String getDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Externe Auftragsnummer.
		@param ExternalOrderId Externe Auftragsnummer	  */
	@Override
	public void setExternalOrderId (java.lang.String ExternalOrderId)
	{
		set_Value (COLUMNNAME_ExternalOrderId, ExternalOrderId);
	}

	/** Get Externe Auftragsnummer.
		@return Externe Auftragsnummer	  */
	@Override
	public java.lang.String getExternalOrderId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalOrderId);
	}

	/** Set Zugeordnet.
		@param IsAllocated 
		Zeigt an ob eine Zahlung bereits zugeordnet wurde
	  */
	@Override
	public void setIsAllocated (boolean IsAllocated)
	{
		set_Value (COLUMNNAME_IsAllocated, Boolean.valueOf(IsAllocated));
	}

	/** Get Zugeordnet.
		@return Zeigt an ob eine Zahlung bereits zugeordnet wurde
	  */
	@Override
	public boolean isAllocated () 
	{
		Object oo = get_Value(COLUMNNAME_IsAllocated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Freigegeben.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	@Override
	public void setIsApproved (boolean IsApproved)
	{
		set_ValueNoCheck (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Freigegeben.
		@return Indicates if this document requires approval
	  */
	@Override
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set isAutoAllocateAvailableAmt.
		@param IsAutoAllocateAvailableAmt isAutoAllocateAvailableAmt	  */
	@Override
	public void setIsAutoAllocateAvailableAmt (boolean IsAutoAllocateAvailableAmt)
	{
		set_Value (COLUMNNAME_IsAutoAllocateAvailableAmt, Boolean.valueOf(IsAutoAllocateAvailableAmt));
	}

	/** Get isAutoAllocateAvailableAmt.
		@return isAutoAllocateAvailableAmt	  */
	@Override
	public boolean isAutoAllocateAvailableAmt () 
	{
		Object oo = get_Value(COLUMNNAME_IsAutoAllocateAvailableAmt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Delayed Capture.
		@param IsDelayedCapture 
		Charge after Shipment
	  */
	@Override
	public void setIsDelayedCapture (boolean IsDelayedCapture)
	{
		set_Value (COLUMNNAME_IsDelayedCapture, Boolean.valueOf(IsDelayedCapture));
	}

	/** Get Delayed Capture.
		@return Charge after Shipment
	  */
	@Override
	public boolean isDelayedCapture () 
	{
		Object oo = get_Value(COLUMNNAME_IsDelayedCapture);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Online Access.
		@param IsOnline 
		Can be accessed online 
	  */
	@Override
	public void setIsOnline (boolean IsOnline)
	{
		set_Value (COLUMNNAME_IsOnline, Boolean.valueOf(IsOnline));
	}

	/** Get Online Access.
		@return Can be accessed online 
	  */
	@Override
	public boolean isOnline () 
	{
		Object oo = get_Value(COLUMNNAME_IsOnline);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Online Payment Approved.
		@param IsOnlineApproved 
		If set means that the payment was succesfully paid online
	  */
	@Override
	public void setIsOnlineApproved (boolean IsOnlineApproved)
	{
		set_Value (COLUMNNAME_IsOnlineApproved, Boolean.valueOf(IsOnlineApproved));
	}

	/** Get Online Payment Approved.
		@return If set means that the payment was succesfully paid online
	  */
	@Override
	public boolean isOnlineApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsOnlineApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Über-/Unterzahlung.
		@param IsOverUnderPayment 
		Überzahlung (nicht zugewiesen) oder Unterzahlung (Teilzahlung)
	  */
	@Override
	public void setIsOverUnderPayment (boolean IsOverUnderPayment)
	{
		set_Value (COLUMNNAME_IsOverUnderPayment, Boolean.valueOf(IsOverUnderPayment));
	}

	/** Get Über-/Unterzahlung.
		@return Überzahlung (nicht zugewiesen) oder Unterzahlung (Teilzahlung)
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

	/** Set Vorauszahlung.
		@param IsPrepayment 
		Die Zahlung ist eine Vorauszahlung
	  */
	@Override
	public void setIsPrepayment (boolean IsPrepayment)
	{
		set_Value (COLUMNNAME_IsPrepayment, Boolean.valueOf(IsPrepayment));
	}

	/** Get Vorauszahlung.
		@return Die Zahlung ist eine Vorauszahlung
	  */
	@Override
	public boolean isPrepayment () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrepayment);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Zahlungseingang.
		@param IsReceipt 
		This is a sales transaction (receipt)
	  */
	@Override
	public void setIsReceipt (boolean IsReceipt)
	{
		set_Value (COLUMNNAME_IsReceipt, Boolean.valueOf(IsReceipt));
	}

	/** Get Zahlungseingang.
		@return This is a sales transaction (receipt)
	  */
	@Override
	public boolean isReceipt () 
	{
		Object oo = get_Value(COLUMNNAME_IsReceipt);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Abgeglichen.
		@param IsReconciled 
		Zeigt an ob eine Zahlung bereits mit einem Kontoauszug abgeglichen wurde
	  */
	@Override
	public void setIsReconciled (boolean IsReconciled)
	{
		set_Value (COLUMNNAME_IsReconciled, Boolean.valueOf(IsReconciled));
	}

	/** Get Abgeglichen.
		@return Zeigt an ob eine Zahlung bereits mit einem Kontoauszug abgeglichen wurde
	  */
	@Override
	public boolean isReconciled () 
	{
		Object oo = get_Value(COLUMNNAME_IsReconciled);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Selbstbedienung.
		@param IsSelfService 
		This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	@Override
	public void setIsSelfService (boolean IsSelfService)
	{
		set_Value (COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
	}

	/** Get Selbstbedienung.
		@return This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	@Override
	public boolean isSelfService () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelfService);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Micr.
		@param Micr 
		Combination of routing no, account and check no
	  */
	@Override
	public void setMicr (java.lang.String Micr)
	{
		set_Value (COLUMNNAME_Micr, Micr);
	}

	/** Get Micr.
		@return Combination of routing no, account and check no
	  */
	@Override
	public java.lang.String getMicr () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Micr);
	}

	/** Set Original Transaction ID.
		@param Orig_TrxID 
		Original Transaction ID
	  */
	@Override
	public void setOrig_TrxID (java.lang.String Orig_TrxID)
	{
		set_Value (COLUMNNAME_Orig_TrxID, Orig_TrxID);
	}

	/** Get Original Transaction ID.
		@return Original Transaction ID
	  */
	@Override
	public java.lang.String getOrig_TrxID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Orig_TrxID);
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

	/** Set PO Number.
		@param PONum 
		Purchase Order Number
	  */
	@Override
	public void setPONum (java.lang.String PONum)
	{
		set_Value (COLUMNNAME_PONum, PONum);
	}

	/** Get PO Number.
		@return Purchase Order Number
	  */
	@Override
	public java.lang.String getPONum () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PONum);
	}

	/** Set Buchungsstatus.
		@param Posted 
		Buchungsstatus
	  */
	@Override
	public void setPosted (boolean Posted)
	{
		set_Value (COLUMNNAME_Posted, Boolean.valueOf(Posted));
	}

	/** Get Buchungsstatus.
		@return Buchungsstatus
	  */
	@Override
	public boolean isPosted () 
	{
		Object oo = get_Value(COLUMNNAME_Posted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Verarbeiten.
		@param Processing Verarbeiten	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Verarbeiten.
		@return Verarbeiten	  */
	@Override
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Authorization Code.
		@param R_AuthCode 
		Authorization Code returned
	  */
	@Override
	public void setR_AuthCode (java.lang.String R_AuthCode)
	{
		set_ValueNoCheck (COLUMNNAME_R_AuthCode, R_AuthCode);
	}

	/** Get Authorization Code.
		@return Authorization Code returned
	  */
	@Override
	public java.lang.String getR_AuthCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_AuthCode);
	}

	/** Set Authorization Code (DC).
		@param R_AuthCode_DC 
		Authorization Code Delayed Capture returned
	  */
	@Override
	public void setR_AuthCode_DC (java.lang.String R_AuthCode_DC)
	{
		set_ValueNoCheck (COLUMNNAME_R_AuthCode_DC, R_AuthCode_DC);
	}

	/** Get Authorization Code (DC).
		@return Authorization Code Delayed Capture returned
	  */
	@Override
	public java.lang.String getR_AuthCode_DC () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_AuthCode_DC);
	}

	/** 
	 * R_AvsAddr AD_Reference_ID=213
	 * Reference name: C_Payment AVS
	 */
	public static final int R_AVSADDR_AD_Reference_ID=213;
	/** Match = Y */
	public static final String R_AVSADDR_Match = "Y";
	/** NoMatch = N */
	public static final String R_AVSADDR_NoMatch = "N";
	/** Unavailable = X */
	public static final String R_AVSADDR_Unavailable = "X";
	/** Set Addresse verifiziert.
		@param R_AvsAddr 
		This address has been verified
	  */
	@Override
	public void setR_AvsAddr (java.lang.String R_AvsAddr)
	{

		set_ValueNoCheck (COLUMNNAME_R_AvsAddr, R_AvsAddr);
	}

	/** Get Addresse verifiziert.
		@return This address has been verified
	  */
	@Override
	public java.lang.String getR_AvsAddr () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_AvsAddr);
	}

	/** 
	 * R_AvsZip AD_Reference_ID=213
	 * Reference name: C_Payment AVS
	 */
	public static final int R_AVSZIP_AD_Reference_ID=213;
	/** Match = Y */
	public static final String R_AVSZIP_Match = "Y";
	/** NoMatch = N */
	public static final String R_AVSZIP_NoMatch = "N";
	/** Unavailable = X */
	public static final String R_AVSZIP_Unavailable = "X";
	/** Set Postleitzahl verifiziert.
		@param R_AvsZip 
		The Zip Code has been verified
	  */
	@Override
	public void setR_AvsZip (java.lang.String R_AvsZip)
	{

		set_ValueNoCheck (COLUMNNAME_R_AvsZip, R_AvsZip);
	}

	/** Get Postleitzahl verifiziert.
		@return The Zip Code has been verified
	  */
	@Override
	public java.lang.String getR_AvsZip () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_AvsZip);
	}

	/** Set CVV Match.
		@param R_CVV2Match 
		Credit Card Verification Code Match
	  */
	@Override
	public void setR_CVV2Match (boolean R_CVV2Match)
	{
		set_ValueNoCheck (COLUMNNAME_R_CVV2Match, Boolean.valueOf(R_CVV2Match));
	}

	/** Get CVV Match.
		@return Credit Card Verification Code Match
	  */
	@Override
	public boolean isR_CVV2Match () 
	{
		Object oo = get_Value(COLUMNNAME_R_CVV2Match);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Info.
		@param R_Info 
		Response info
	  */
	@Override
	public void setR_Info (java.lang.String R_Info)
	{
		set_ValueNoCheck (COLUMNNAME_R_Info, R_Info);
	}

	/** Get Info.
		@return Response info
	  */
	@Override
	public java.lang.String getR_Info () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_Info);
	}

	/** Set Referenz.
		@param R_PnRef 
		Payment reference
	  */
	@Override
	public void setR_PnRef (java.lang.String R_PnRef)
	{
		set_Value (COLUMNNAME_R_PnRef, R_PnRef);
	}

	/** Get Referenz.
		@return Payment reference
	  */
	@Override
	public java.lang.String getR_PnRef () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_PnRef);
	}

	/** Set Reference (DC).
		@param R_PnRef_DC 
		Payment Reference Delayed Capture
	  */
	@Override
	public void setR_PnRef_DC (java.lang.String R_PnRef_DC)
	{
		set_ValueNoCheck (COLUMNNAME_R_PnRef_DC, R_PnRef_DC);
	}

	/** Get Reference (DC).
		@return Payment Reference Delayed Capture
	  */
	@Override
	public java.lang.String getR_PnRef_DC () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_PnRef_DC);
	}

	/** Set Response Message.
		@param R_RespMsg 
		Response message
	  */
	@Override
	public void setR_RespMsg (java.lang.String R_RespMsg)
	{
		set_ValueNoCheck (COLUMNNAME_R_RespMsg, R_RespMsg);
	}

	/** Get Response Message.
		@return Response message
	  */
	@Override
	public java.lang.String getR_RespMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_RespMsg);
	}

	/** Set Ergebnis.
		@param R_Result 
		Result of transmission
	  */
	@Override
	public void setR_Result (java.lang.String R_Result)
	{
		set_ValueNoCheck (COLUMNNAME_R_Result, R_Result);
	}

	/** Get Ergebnis.
		@return Result of transmission
	  */
	@Override
	public java.lang.String getR_Result () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_Result);
	}

	/** Set Referenced Payment.
		@param Ref_Payment_ID Referenced Payment	  */
	@Override
	public void setRef_Payment_ID (int Ref_Payment_ID)
	{
		if (Ref_Payment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Ref_Payment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Ref_Payment_ID, Integer.valueOf(Ref_Payment_ID));
	}

	/** Get Referenced Payment.
		@return Referenced Payment	  */
	@Override
	public int getRef_Payment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ref_Payment_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Storno-Gegenbeleg.
		@param Reversal_ID 
		ID of document reversal
	  */
	@Override
	public void setReversal_ID (int Reversal_ID)
	{
		if (Reversal_ID < 1) 
			set_Value (COLUMNNAME_Reversal_ID, null);
		else 
			set_Value (COLUMNNAME_Reversal_ID, Integer.valueOf(Reversal_ID));
	}

	/** Get Storno-Gegenbeleg.
		@return ID of document reversal
	  */
	@Override
	public int getReversal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Reversal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set BLZ.
		@param RoutingNo 
		Bank Routing Number
	  */
	@Override
	public void setRoutingNo (java.lang.String RoutingNo)
	{
		set_Value (COLUMNNAME_RoutingNo, RoutingNo);
	}

	/** Get BLZ.
		@return Bank Routing Number
	  */
	@Override
	public java.lang.String getRoutingNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RoutingNo);
	}

	/** Set Swipe.
		@param Swipe 
		Track 1 and 2 of the Credit Card
	  */
	@Override
	public void setSwipe (java.lang.String Swipe)
	{
		set_ValueNoCheck (COLUMNNAME_Swipe, Swipe);
	}

	/** Get Swipe.
		@return Track 1 and 2 of the Credit Card
	  */
	@Override
	public java.lang.String getSwipe () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Swipe);
	}

	/** Set Steuerbetrag.
		@param TaxAmt 
		Tax Amount for a document
	  */
	@Override
	public void setTaxAmt (java.math.BigDecimal TaxAmt)
	{
		set_Value (COLUMNNAME_TaxAmt, TaxAmt);
	}

	/** Get Steuerbetrag.
		@return Tax Amount for a document
	  */
	@Override
	public java.math.BigDecimal getTaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** 
	 * TenderType AD_Reference_ID=214
	 * Reference name: C_Payment Tender Type
	 */
	public static final int TENDERTYPE_AD_Reference_ID=214;
	/** CreditCard = C */
	public static final String TENDERTYPE_CreditCard = "C";
	/** Check = K */
	public static final String TENDERTYPE_Check = "K";
	/** DirectDeposit = A */
	public static final String TENDERTYPE_DirectDeposit = "A";
	/** DirectDebit = D */
	public static final String TENDERTYPE_DirectDebit = "D";
	/** Account = T */
	public static final String TENDERTYPE_Account = "T";
	/** Cash = X */
	public static final String TENDERTYPE_Cash = "X";
	/** Set Zahlmittel.
		@param TenderType 
		Method of Payment
	  */
	@Override
	public void setTenderType (java.lang.String TenderType)
	{

		set_Value (COLUMNNAME_TenderType, TenderType);
	}

	/** Get Zahlmittel.
		@return Method of Payment
	  */
	@Override
	public java.lang.String getTenderType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TenderType);
	}

	/** 
	 * TrxType AD_Reference_ID=215
	 * Reference name: C_Payment Trx Type
	 */
	public static final int TRXTYPE_AD_Reference_ID=215;
	/** Sales = S */
	public static final String TRXTYPE_Sales = "S";
	/** DelayedCapture = D */
	public static final String TRXTYPE_DelayedCapture = "D";
	/** CreditPayment = C */
	public static final String TRXTYPE_CreditPayment = "C";
	/** VoiceAuthorization = F */
	public static final String TRXTYPE_VoiceAuthorization = "F";
	/** Authorization = A */
	public static final String TRXTYPE_Authorization = "A";
	/** Void = V */
	public static final String TRXTYPE_Void = "V";
	/** Rückzahlung = R */
	public static final String TRXTYPE_Rueckzahlung = "R";
	/** Set Transaction Type.
		@param TrxType 
		Type of credit card transaction
	  */
	@Override
	public void setTrxType (java.lang.String TrxType)
	{

		set_Value (COLUMNNAME_TrxType, TrxType);
	}

	/** Get Transaction Type.
		@return Type of credit card transaction
	  */
	@Override
	public java.lang.String getTrxType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_TrxType);
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser1()
	{
		return get_ValueAsPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser1(org.compiere.model.I_C_ElementValue User1)
	{
		set_ValueFromPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class, User1);
	}

	/** Set Nutzer 1.
		@param User1_ID 
		User defined list element #1
	  */
	@Override
	public void setUser1_ID (int User1_ID)
	{
		if (User1_ID < 1) 
			set_Value (COLUMNNAME_User1_ID, null);
		else 
			set_Value (COLUMNNAME_User1_ID, Integer.valueOf(User1_ID));
	}

	/** Get Nutzer 1.
		@return User defined list element #1
	  */
	@Override
	public int getUser1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser2()
	{
		return get_ValueAsPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser2(org.compiere.model.I_C_ElementValue User2)
	{
		set_ValueFromPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class, User2);
	}

	/** Set Nutzer 2.
		@param User2_ID 
		User defined list element #2
	  */
	@Override
	public void setUser2_ID (int User2_ID)
	{
		if (User2_ID < 1) 
			set_Value (COLUMNNAME_User2_ID, null);
		else 
			set_Value (COLUMNNAME_User2_ID, Integer.valueOf(User2_ID));
	}

	/** Get Nutzer 2.
		@return User defined list element #2
	  */
	@Override
	public int getUser2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Prüfziffer.
		@param VoiceAuthCode 
		Voice Authorization Code from credit card company
	  */
	@Override
	public void setVoiceAuthCode (java.lang.String VoiceAuthCode)
	{
		set_Value (COLUMNNAME_VoiceAuthCode, VoiceAuthCode);
	}

	/** Get Prüfziffer.
		@return Voice Authorization Code from credit card company
	  */
	@Override
	public java.lang.String getVoiceAuthCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VoiceAuthCode);
	}

	/** Set Abschreiben.
		@param WriteOffAmt 
		Amount to write-off
	  */
	@Override
	public void setWriteOffAmt (java.math.BigDecimal WriteOffAmt)
	{
		set_Value (COLUMNNAME_WriteOffAmt, WriteOffAmt);
	}

	/** Get Abschreiben.
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