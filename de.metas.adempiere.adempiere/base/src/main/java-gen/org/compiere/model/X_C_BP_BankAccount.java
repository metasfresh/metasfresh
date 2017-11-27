/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BP_BankAccount
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_BP_BankAccount extends org.compiere.model.PO implements I_C_BP_BankAccount, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1708192350L;

    /** Standard Constructor */
    public X_C_BP_BankAccount (Properties ctx, int C_BP_BankAccount_ID, String trxName)
    {
      super (ctx, C_BP_BankAccount_ID, trxName);
      /** if (C_BP_BankAccount_ID == 0)
        {
			setA_Name (null);
			setC_BP_BankAccount_ID (0);
			setC_BPartner_ID (0);
			setC_Currency_ID (0);
			setIsACH (true); // Y
        } */
    }

    /** Load Constructor */
    public X_C_BP_BankAccount (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User(org.compiere.model.I_AD_User AD_User)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class, AD_User);
	}

	/** Set Ansprechpartner.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Ansprechpartner.
		@return User within the system - Internal or Business Partner Contact
	  */
	@Override
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * BankAccountType AD_Reference_ID=216
	 * Reference name: C_Bank Account Type
	 */
	public static final int BANKACCOUNTTYPE_AD_Reference_ID=216;
	/** Girokonto = C */
	public static final String BANKACCOUNTTYPE_Girokonto = "C";
	/** Sparkonto = S */
	public static final String BANKACCOUNTTYPE_Sparkonto = "S";
	/** Set Kontenart.
		@param BankAccountType 
		Bank Account Type
	  */
	@Override
	public void setBankAccountType (java.lang.String BankAccountType)
	{

		set_Value (COLUMNNAME_BankAccountType, BankAccountType);
	}

	/** Get Kontenart.
		@return Bank Account Type
	  */
	@Override
	public java.lang.String getBankAccountType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BankAccountType);
	}

	/** 
	 * BPBankAcctUse AD_Reference_ID=393
	 * Reference name: C_BPartner BPBankAcctUse
	 */
	public static final int BPBANKACCTUSE_AD_Reference_ID=393;
	/** Nichts = N */
	public static final String BPBANKACCTUSE_Nichts = "N";
	/** Both = B */
	public static final String BPBANKACCTUSE_Both = "B";
	/** DirectDebit = D */
	public static final String BPBANKACCTUSE_DirectDebit = "D";
	/** DirectDeposit = T */
	public static final String BPBANKACCTUSE_DirectDeposit = "T";
	/** Provision = P */
	public static final String BPBANKACCTUSE_Provision = "P";
	/** Set Kontonutzung.
		@param BPBankAcctUse 
		Business Partner Bank Account usage
	  */
	@Override
	public void setBPBankAcctUse (java.lang.String BPBankAcctUse)
	{

		set_Value (COLUMNNAME_BPBankAcctUse, BPBankAcctUse);
	}

	/** Get Kontonutzung.
		@return Business Partner Bank Account usage
	  */
	@Override
	public java.lang.String getBPBankAcctUse () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPBankAcctUse);
	}

	@Override
	public org.compiere.model.I_C_Bank getC_Bank() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Bank_ID, org.compiere.model.I_C_Bank.class);
	}

	@Override
	public void setC_Bank(org.compiere.model.I_C_Bank C_Bank)
	{
		set_ValueFromPO(COLUMNNAME_C_Bank_ID, org.compiere.model.I_C_Bank.class, C_Bank);
	}

	/** Set Bank.
		@param C_Bank_ID 
		Bank
	  */
	@Override
	public void setC_Bank_ID (int C_Bank_ID)
	{
		if (C_Bank_ID < 1) 
			set_Value (COLUMNNAME_C_Bank_ID, null);
		else 
			set_Value (COLUMNNAME_C_Bank_ID, Integer.valueOf(C_Bank_ID));
	}

	/** Get Bank.
		@return Bank
	  */
	@Override
	public int getC_Bank_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Bank_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bankverbindung.
		@param C_BP_BankAccount_ID 
		Bank Account of the Business Partner
	  */
	@Override
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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
		Die Währung für diesen Eintrag
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

	/** Set ACH.
		@param IsACH 
		Automatic Clearing House
	  */
	@Override
	public void setIsACH (boolean IsACH)
	{
		set_Value (COLUMNNAME_IsACH, Boolean.valueOf(IsACH));
	}

	/** Get ACH.
		@return Automatic Clearing House
	  */
	@Override
	public boolean isACH () 
	{
		Object oo = get_Value(COLUMNNAME_IsACH);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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
}