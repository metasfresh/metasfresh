/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import static org.compiere.model.I_I_Payment.COLUMNNAME_IsApproved;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Env;

/** Generated Model for I_Payment
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_I_Payment extends org.compiere.model.PO implements I_I_Payment, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1156829327L;

    /** Standard Constructor */
    public X_I_Payment (Properties ctx, int I_Payment_ID, String trxName)
    {
      super (ctx, I_Payment_ID, trxName);
      /** if (I_Payment_ID == 0)
        {
			setI_IsImported (false);
			setI_Payment_ID (0);
        } */
    }

    /** Load Constructor */
    public X_I_Payment (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
      */
    @Override
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    @Override
    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_I_Payment[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Name.
		@param A_Name 
		Name on Credit Card or Account holder
	  */
	@Override
	public void setA_Name (java.lang.String A_Name)
	{
		set_Value (COLUMNNAME_A_Name, A_Name);
	}

	/** Get Name.
		@return Name on Credit Card or Account holder
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
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1)
		{
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
		}
	}

	/** Get Geschäftspartner.
		@return Identifies a Business Partner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
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
		{
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
		}
	}

	/** Get Bankverbindung.
		@return Bankverbindung des Geschäftspartners
	  */
	@Override
	public int getC_BP_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_BankAccount_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Charge_ID, org.compiere.model.I_C_Charge.class);
	}

	@Override
	public void setC_Charge(org.compiere.model.I_C_Charge C_Charge)
	{
		set_ValueFromPO(COLUMNNAME_C_Charge_ID, org.compiere.model.I_C_Charge.class, C_Charge);
	}

	/** Set Kosten.
		@param C_Charge_ID 
		Additional document charges
	  */
	@Override
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1)
		{
			set_Value (COLUMNNAME_C_Charge_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
		}
	}

	/** Get Kosten.
		@return Additional document charges
	  */
	@Override
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class);
	}

	@Override
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency)
	{
		set_ValueFromPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class, C_Currency);
	}

	/** Set Währung.
		@param C_Currency_ID 
		The Currency for this record
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1)
		{
			set_Value (COLUMNNAME_C_Currency_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
		}
	}

	/** Get Währung.
		@return The Currency for this record
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType)
	{
		set_ValueFromPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class, C_DocType);
	}

	/** Set Belegart.
		@param C_DocType_ID 
		Document type or rules
	  */
	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0)
		{
			set_Value (COLUMNNAME_C_DocType_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
		}
	}

	/** Get Belegart.
		@return Document type or rules
	  */
	@Override
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
		{
			return 0;
		}
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
		{
			return Env.ZERO;
		}
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
		{
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
		}
	}

	/** Get Rechnung.
		@return Invoice Identifier
	  */
	@Override
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Payment getC_Payment() throws RuntimeException
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
		{
			set_Value (COLUMNNAME_C_Payment_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
		}
	}

	/** Get Zahlung.
		@return Payment identifier
	  */
	@Override
	public int getC_Payment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
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
		{
			return 0;
		}
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
		{
			return 0;
		}
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
	/** VISA = V */
	public static final String CREDITCARDTYPE_VISA = "V";
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

	/** Set Vorgangsdatum.
		@param DateTrx 
		Transaction Date
	  */
	@Override
	public void setDateTrx (java.sql.Timestamp DateTrx)
	{
		set_Value (COLUMNNAME_DateTrx, DateTrx);
	}

	/** Get Vorgangsdatum.
		@return Transaction Date
	  */
	@Override
	public java.sql.Timestamp getDateTrx () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateTrx);
	}

	/** Set Discount Amount.
		@param DiscountAmt 
		Calculated amount of discount
	  */
	@Override
	public void setDiscountAmt (java.math.BigDecimal DiscountAmt)
	{
		set_Value (COLUMNNAME_DiscountAmt, DiscountAmt);
	}

	/** Get Discount Amount.
		@return Calculated amount of discount
	  */
	@Override
	public java.math.BigDecimal getDiscountAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DiscountAmt);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** Set Belegart-Bezeichnung.
		@param DocTypeName 
		Name of the Document Type
	  */
	@Override
	public void setDocTypeName (java.lang.String DocTypeName)
	{
		set_Value (COLUMNNAME_DocTypeName, DocTypeName);
	}

	/** Get Belegart-Bezeichnung.
		@return Name of the Document Type
	  */
	@Override
	public java.lang.String getDocTypeName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocTypeName);
	}

	/** Set Beleg Nr..
		@param DocumentNo 
		Document sequence number of the document
	  */
	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Beleg Nr..
		@return Document sequence number of the document
	  */
	@Override
	public java.lang.String getDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
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
			{
				return ((Boolean)oo).booleanValue();
			} 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Import - Zahlung.
		@param I_Payment_ID 
		Import Payment
	  */
	@Override
	public void setI_Payment_ID (int I_Payment_ID)
	{
		if (I_Payment_ID < 1)
		{
			set_ValueNoCheck (COLUMNNAME_I_Payment_ID, null);
		}
		else
		{
			set_ValueNoCheck (COLUMNNAME_I_Payment_ID, Integer.valueOf(I_Payment_ID));
		}
	}

	/** Get Import - Zahlung.
		@return Import Payment
	  */
	@Override
	public int getI_Payment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_Payment_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set Freigegeben.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	@Override
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
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
			{
				return ((Boolean)oo).booleanValue();
			} 
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
			{
				return ((Boolean)oo).booleanValue();
			} 
			return "Y".equals(oo);
		}
		return false;
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
			{
				return ((Boolean)oo).booleanValue();
			} 
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
			{
				return ((Boolean)oo).booleanValue();
			} 
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
			{
				return ((Boolean)oo).booleanValue();
			} 
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
		{
			return Env.ZERO;
		}
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
		{
			return Env.ZERO;
		}
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

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			} 
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
			{
				return ((Boolean)oo).booleanValue();
			} 
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
		set_Value (COLUMNNAME_R_AuthCode, R_AuthCode);
	}

	/** Get Authorization Code.
		@return Authorization Code returned
	  */
	@Override
	public java.lang.String getR_AuthCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_AuthCode);
	}

	/** Set Info.
		@param R_Info 
		Response info
	  */
	@Override
	public void setR_Info (java.lang.String R_Info)
	{
		set_Value (COLUMNNAME_R_Info, R_Info);
	}

	/** Get Info.
		@return Response info
	  */
	@Override
	public java.lang.String getR_Info () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_Info);
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

	/** Set Response Message.
		@param R_RespMsg 
		Response message
	  */
	@Override
	public void setR_RespMsg (java.lang.String R_RespMsg)
	{
		set_Value (COLUMNNAME_R_RespMsg, R_RespMsg);
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
		set_Value (COLUMNNAME_R_Result, R_Result);
	}

	/** Get Ergebnis.
		@return Result of transmission
	  */
	@Override
	public java.lang.String getR_Result () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_Result);
	}

	/** Set Swipe.
		@param Swipe 
		Track 1 and 2 of the Credit Card
	  */
	@Override
	public void setSwipe (java.lang.String Swipe)
	{
		set_Value (COLUMNNAME_Swipe, Swipe);
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
		{
			return Env.ZERO;
		}
		return bd;
	}

//	/** 
//	 * TenderType AD_Reference_ID=214
//	 * Reference name: C_Payment Tender Type
//	 */
//	public static final int TENDERTYPE_AD_Reference_ID=214;
//	/** Kreditkarte = C */
//	public static final String TENDERTYPE_Kreditkarte = "C";
//	/** Scheck = K */
//	public static final String TENDERTYPE_Scheck = "K";
//	/** Überweisung = A */
//	public static final String TENDERTYPE_Ueberweisung = "A";
//	/** Bankeinzug = D */
//	public static final String TENDERTYPE_Bankeinzug = "D";
//	/** Account = T */
//	public static final String TENDERTYPE_Account = "T";
//	/** Bar = X */
//	public static final String TENDERTYPE_Bar = "X";
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
	/** Verkauf = S */
	public static final String TRXTYPE_Verkauf = "S";
	/** Delayed Capture = D */
	public static final String TRXTYPE_DelayedCapture = "D";
	/** Kredit (Zahlung) = C */
	public static final String TRXTYPE_KreditZahlung = "C";
	/** Voice Authorization = F */
	public static final String TRXTYPE_VoiceAuthorization = "F";
	/** Autorisierung = A */
	public static final String TRXTYPE_Autorisierung = "A";
	/** Löschen = V */
	public static final String TRXTYPE_Loeschen = "V";
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
		{
			return Env.ZERO;
		}
		return bd;
	}
}