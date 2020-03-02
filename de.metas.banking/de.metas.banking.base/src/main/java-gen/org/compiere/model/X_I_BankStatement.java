/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_BankStatement
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_I_BankStatement extends org.compiere.model.PO implements I_I_BankStatement, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 447556155L;

    /** Standard Constructor */
    public X_I_BankStatement (Properties ctx, int I_BankStatement_ID, String trxName)
    {
      super (ctx, I_BankStatement_ID, trxName);
      /** if (I_BankStatement_ID == 0)
        {
			setI_BankStatement_ID (0);
			setI_IsImported (false);
        } */
    }

    /** Load Constructor */
    public X_I_BankStatement (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Issue getAD_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class);
	}

	@Override
	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue)
	{
		set_ValueFromPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class, AD_Issue);
	}

	/** Set System-Problem.
		@param AD_Issue_ID 
		Automatically created or manually entered System Issue
	  */
	@Override
	public void setAD_Issue_ID (int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, Integer.valueOf(AD_Issue_ID));
	}

	/** Get System-Problem.
		@return Automatically created or manually entered System Issue
	  */
	@Override
	public int getAD_Issue_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Issue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bank Account No.
		@param BankAccountNo 
		Bank Account Number
	  */
	@Override
	public void setBankAccountNo (java.lang.String BankAccountNo)
	{
		set_Value (COLUMNNAME_BankAccountNo, BankAccountNo);
	}

	/** Get Bank Account No.
		@return Bank Account Number
	  */
	@Override
	public java.lang.String getBankAccountNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BankAccountNo);
	}

	/** Set Name Rechnungspartner.
		@param Bill_BPartner_Name Name Rechnungspartner	  */
	@Override
	public void setBill_BPartner_Name (java.lang.String Bill_BPartner_Name)
	{
		set_Value (COLUMNNAME_Bill_BPartner_Name, Bill_BPartner_Name);
	}

	/** Get Name Rechnungspartner.
		@return Name Rechnungspartner	  */
	@Override
	public java.lang.String getBill_BPartner_Name () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Bill_BPartner_Name);
	}

	/** Set Geschäftspartner-Schlüssel.
		@param BPartnerValue 
		Key of the Business Partner
	  */
	@Override
	public void setBPartnerValue (java.lang.String BPartnerValue)
	{
		set_Value (COLUMNNAME_BPartnerValue, BPartnerValue);
	}

	/** Get Geschäftspartner-Schlüssel.
		@return Key of the Business Partner
	  */
	@Override
	public java.lang.String getBPartnerValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPartnerValue);
	}

	@Override
	public org.compiere.model.I_C_BankStatement getC_BankStatement()
	{
		return get_ValueAsPO(COLUMNNAME_C_BankStatement_ID, org.compiere.model.I_C_BankStatement.class);
	}

	@Override
	public void setC_BankStatement(org.compiere.model.I_C_BankStatement C_BankStatement)
	{
		set_ValueFromPO(COLUMNNAME_C_BankStatement_ID, org.compiere.model.I_C_BankStatement.class, C_BankStatement);
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

	@Override
	public org.compiere.model.I_C_BankStatementLine getC_BankStatementLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_BankStatementLine_ID, org.compiere.model.I_C_BankStatementLine.class);
	}

	@Override
	public void setC_BankStatementLine(org.compiere.model.I_C_BankStatementLine C_BankStatementLine)
	{
		set_ValueFromPO(COLUMNNAME_C_BankStatementLine_ID, org.compiere.model.I_C_BankStatementLine.class, C_BankStatementLine);
	}

	/** Set Auszugs-Position.
		@param C_BankStatementLine_ID 
		Line on a statement from this Bank
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
	public org.compiere.model.I_C_DataImport getC_DataImport()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class);
	}

	@Override
	public void setC_DataImport(org.compiere.model.I_C_DataImport C_DataImport)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class, C_DataImport);
	}

	/** Set Daten Import.
		@param C_DataImport_ID Daten Import	  */
	@Override
	public void setC_DataImport_ID (int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_ID, Integer.valueOf(C_DataImport_ID));
	}

	/** Get Daten Import.
		@return Daten Import	  */
	@Override
	public int getC_DataImport_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DataImport_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DataImport_Run getC_DataImport_Run()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class);
	}

	@Override
	public void setC_DataImport_Run(org.compiere.model.I_C_DataImport_Run C_DataImport_Run)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class, C_DataImport_Run);
	}

	/** Set Data Import Run.
		@param C_DataImport_Run_ID Data Import Run	  */
	@Override
	public void setC_DataImport_Run_ID (int C_DataImport_Run_ID)
	{
		if (C_DataImport_Run_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, Integer.valueOf(C_DataImport_Run_ID));
	}

	/** Get Data Import Run.
		@return Data Import Run	  */
	@Override
	public int getC_DataImport_Run_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DataImport_Run_ID);
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

	/** Set Gebühren-Bezeichnung.
		@param ChargeName 
		Name of the Charge
	  */
	@Override
	public void setChargeName (java.lang.String ChargeName)
	{
		set_Value (COLUMNNAME_ChargeName, ChargeName);
	}

	/** Get Gebühren-Bezeichnung.
		@return Name of the Charge
	  */
	@Override
	public java.lang.String getChargeName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ChargeName);
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

	/** Set Credit Statement Amount.
		@param CreditStmtAmt Credit Statement Amount	  */
	@Override
	public void setCreditStmtAmt (java.math.BigDecimal CreditStmtAmt)
	{
		set_Value (COLUMNNAME_CreditStmtAmt, CreditStmtAmt);
	}

	/** Get Credit Statement Amount.
		@return Credit Statement Amount	  */
	@Override
	public java.math.BigDecimal getCreditStmtAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CreditStmtAmt);
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

	/** Set Debit Statement Amount.
		@param DebitStmtAmt Debit Statement Amount	  */
	@Override
	public void setDebitStmtAmt (java.math.BigDecimal DebitStmtAmt)
	{
		set_Value (COLUMNNAME_DebitStmtAmt, DebitStmtAmt);
	}

	/** Get Debit Statement Amount.
		@return Debit Statement Amount	  */
	@Override
	public java.math.BigDecimal getDebitStmtAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DebitStmtAmt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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

	/** Set Auszugsdatum ELV.
		@param EftStatementDate 
		Electronic Funds Transfer Statement Date
	  */
	@Override
	public void setEftStatementDate (java.sql.Timestamp EftStatementDate)
	{
		set_Value (COLUMNNAME_EftStatementDate, EftStatementDate);
	}

	/** Get Auszugsdatum ELV.
		@return Electronic Funds Transfer Statement Date
	  */
	@Override
	public java.sql.Timestamp getEftStatementDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_EftStatementDate);
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

	/** Set ELV Auszugs-Referenz.
		@param EftStatementReference 
		Electronic Funds Transfer Statement Reference
	  */
	@Override
	public void setEftStatementReference (java.lang.String EftStatementReference)
	{
		set_Value (COLUMNNAME_EftStatementReference, EftStatementReference);
	}

	/** Get ELV Auszugs-Referenz.
		@return Electronic Funds Transfer Statement Reference
	  */
	@Override
	public java.lang.String getEftStatementReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EftStatementReference);
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

	/** Set IBAN.
		@param IBAN 
		International Bank Account Number
	  */
	@Override
	public void setIBAN (java.lang.String IBAN)
	{
		set_Value (COLUMNNAME_IBAN, IBAN);
	}

	/** Get IBAN.
		@return International Bank Account Number
	  */
	@Override
	public java.lang.String getIBAN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IBAN);
	}

	/** Set Import - Bankauszug.
		@param I_BankStatement_ID 
		Import of the Bank Statement
	  */
	@Override
	public void setI_BankStatement_ID (int I_BankStatement_ID)
	{
		if (I_BankStatement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_BankStatement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_BankStatement_ID, Integer.valueOf(I_BankStatement_ID));
	}

	/** Get Import - Bankauszug.
		@return Import of the Bank Statement
	  */
	@Override
	public int getI_BankStatement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_BankStatement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set IBAN_To.
		@param IBAN_To IBAN_To	  */
	@Override
	public void setIBAN_To (java.lang.String IBAN_To)
	{
		set_Value (COLUMNNAME_IBAN_To, IBAN_To);
	}

	/** Get IBAN_To.
		@return IBAN_To	  */
	@Override
	public java.lang.String getIBAN_To () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IBAN_To);
	}

	/** Set Import-Fehlermeldung.
		@param I_ErrorMsg 
		Messages generated from import process
	  */
	@Override
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import-Fehlermeldung.
		@return Messages generated from import process
	  */
	@Override
	public java.lang.String getI_ErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** Set Importiert.
		@param I_IsImported 
		Has this import been processed
	  */
	@Override
	public void setI_IsImported (boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	/** Get Importiert.
		@return Has this import been processed
	  */
	@Override
	public boolean isI_IsImported () 
	{
		Object oo = get_Value(COLUMNNAME_I_IsImported);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Import Line Content.
		@param I_LineContent Import Line Content	  */
	@Override
	public void setI_LineContent (java.lang.String I_LineContent)
	{
		set_Value (COLUMNNAME_I_LineContent, I_LineContent);
	}

	/** Get Import Line Content.
		@return Import Line Content	  */
	@Override
	public java.lang.String getI_LineContent () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_LineContent);
	}

	/** Set Import Line No.
		@param I_LineNo Import Line No	  */
	@Override
	public void setI_LineNo (int I_LineNo)
	{
		set_Value (COLUMNNAME_I_LineNo, Integer.valueOf(I_LineNo));
	}

	/** Get Import Line No.
		@return Import Line No	  */
	@Override
	public int getI_LineNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_LineNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Invoice Document No.
		@param InvoiceDocumentNo 
		Document Number of the Invoice
	  */
	@Override
	public void setInvoiceDocumentNo (java.lang.String InvoiceDocumentNo)
	{
		set_Value (COLUMNNAME_InvoiceDocumentNo, InvoiceDocumentNo);
	}

	/** Get Invoice Document No.
		@return Document Number of the Invoice
	  */
	@Override
	public java.lang.String getInvoiceDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InvoiceDocumentNo);
	}

	/** Set ISO Währungscode.
		@param ISO_Code 
		Three letter ISO 4217 Code of the Currency
	  */
	@Override
	public void setISO_Code (java.lang.String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	/** Get ISO Währungscode.
		@return Three letter ISO 4217 Code of the Currency
	  */
	@Override
	public java.lang.String getISO_Code () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ISO_Code);
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

	/** Set Positions-Beschreibung.
		@param LineDescription 
		Description of the Line
	  */
	@Override
	public void setLineDescription (java.lang.String LineDescription)
	{
		set_Value (COLUMNNAME_LineDescription, LineDescription);
	}

	/** Get Positions-Beschreibung.
		@return Description of the Line
	  */
	@Override
	public java.lang.String getLineDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LineDescription);
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

	/** Set Name.
		@param Name Name	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Name	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Payment Document No.
		@param PaymentDocumentNo 
		Document number of the Payment
	  */
	@Override
	public void setPaymentDocumentNo (java.lang.String PaymentDocumentNo)
	{
		set_Value (COLUMNNAME_PaymentDocumentNo, PaymentDocumentNo);
	}

	/** Get Payment Document No.
		@return Document number of the Payment
	  */
	@Override
	public java.lang.String getPaymentDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PaymentDocumentNo);
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

	/** Set Auszugsdatum.
		@param StatementDate 
		Date of the statement
	  */
	@Override
	public void setStatementDate (java.sql.Timestamp StatementDate)
	{
		set_Value (COLUMNNAME_StatementDate, StatementDate);
	}

	/** Get Auszugsdatum.
		@return Date of the statement
	  */
	@Override
	public java.sql.Timestamp getStatementDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_StatementDate);
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
}