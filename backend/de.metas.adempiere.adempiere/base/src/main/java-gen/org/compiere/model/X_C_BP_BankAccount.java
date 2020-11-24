/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BP_BankAccount
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_BP_BankAccount extends org.compiere.model.PO implements I_C_BP_BankAccount, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -649509635L;

    /** Standard Constructor */
    public X_C_BP_BankAccount (Properties ctx, int C_BP_BankAccount_ID, String trxName)
    {
      super (ctx, C_BP_BankAccount_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BP_BankAccount (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setA_City (java.lang.String A_City)
	{
		set_Value (COLUMNNAME_A_City, A_City);
	}

	@Override
	public java.lang.String getA_City() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_City);
	}

	@Override
	public void setA_Country (java.lang.String A_Country)
	{
		set_Value (COLUMNNAME_A_Country, A_Country);
	}

	@Override
	public java.lang.String getA_Country() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_Country);
	}

	@Override
	public void setA_EMail (java.lang.String A_EMail)
	{
		set_Value (COLUMNNAME_A_EMail, A_EMail);
	}

	@Override
	public java.lang.String getA_EMail() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_EMail);
	}

	@Override
	public void setA_Ident_DL (java.lang.String A_Ident_DL)
	{
		set_Value (COLUMNNAME_A_Ident_DL, A_Ident_DL);
	}

	@Override
	public java.lang.String getA_Ident_DL() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_Ident_DL);
	}

	@Override
	public void setA_Ident_SSN (java.lang.String A_Ident_SSN)
	{
		set_Value (COLUMNNAME_A_Ident_SSN, A_Ident_SSN);
	}

	@Override
	public java.lang.String getA_Ident_SSN() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_Ident_SSN);
	}

	@Override
	public void setA_Name (java.lang.String A_Name)
	{
		set_Value (COLUMNNAME_A_Name, A_Name);
	}

	@Override
	public java.lang.String getA_Name() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_Name);
	}

	@Override
	public void setA_State (java.lang.String A_State)
	{
		set_Value (COLUMNNAME_A_State, A_State);
	}

	@Override
	public java.lang.String getA_State() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_State);
	}

	@Override
	public void setA_Street (java.lang.String A_Street)
	{
		set_Value (COLUMNNAME_A_Street, A_Street);
	}

	@Override
	public java.lang.String getA_Street() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_Street);
	}

	@Override
	public void setA_Zip (java.lang.String A_Zip)
	{
		set_Value (COLUMNNAME_A_Zip, A_Zip);
	}

	@Override
	public java.lang.String getA_Zip() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_A_Zip);
	}

	@Override
	public void setAccountNo (java.lang.String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	@Override
	public java.lang.String getAccountNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AccountNo);
	}

	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
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
	@Override
	public void setBankAccountType (java.lang.String BankAccountType)
	{

		set_Value (COLUMNNAME_BankAccountType, BankAccountType);
	}

	@Override
	public java.lang.String getBankAccountType() 
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
	@Override
	public void setBPBankAcctUse (java.lang.String BPBankAcctUse)
	{

		set_Value (COLUMNNAME_BPBankAcctUse, BPBankAcctUse);
	}

	@Override
	public java.lang.String getBPBankAcctUse() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BPBankAcctUse);
	}

	@Override
	public void setC_Bank_ID (int C_Bank_ID)
	{
		if (C_Bank_ID < 1) 
			set_Value (COLUMNNAME_C_Bank_ID, null);
		else 
			set_Value (COLUMNNAME_C_Bank_ID, Integer.valueOf(C_Bank_ID));
	}

	@Override
	public int getC_Bank_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Bank_ID);
	}

	@Override
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
	}

	@Override
	public int getC_BP_BankAccount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccount_ID);
	}

	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setCreditCardExpMM (int CreditCardExpMM)
	{
		set_Value (COLUMNNAME_CreditCardExpMM, Integer.valueOf(CreditCardExpMM));
	}

	@Override
	public int getCreditCardExpMM() 
	{
		return get_ValueAsInt(COLUMNNAME_CreditCardExpMM);
	}

	@Override
	public void setCreditCardExpYY (int CreditCardExpYY)
	{
		set_Value (COLUMNNAME_CreditCardExpYY, Integer.valueOf(CreditCardExpYY));
	}

	@Override
	public int getCreditCardExpYY() 
	{
		return get_ValueAsInt(COLUMNNAME_CreditCardExpYY);
	}

	@Override
	public void setCreditCardNumber (java.lang.String CreditCardNumber)
	{
		set_Value (COLUMNNAME_CreditCardNumber, CreditCardNumber);
	}

	@Override
	public java.lang.String getCreditCardNumber() 
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
	@Override
	public void setCreditCardType (java.lang.String CreditCardType)
	{

		set_Value (COLUMNNAME_CreditCardType, CreditCardType);
	}

	@Override
	public java.lang.String getCreditCardType() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreditCardType);
	}

	@Override
	public void setCreditCardVV (java.lang.String CreditCardVV)
	{
		set_Value (COLUMNNAME_CreditCardVV, CreditCardVV);
	}

	@Override
	public java.lang.String getCreditCardVV() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreditCardVV);
	}

	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	@Override
	public void setESR_RenderedAccountNo (java.lang.String ESR_RenderedAccountNo)
	{
		set_Value (COLUMNNAME_ESR_RenderedAccountNo, ESR_RenderedAccountNo);
	}

	@Override
	public java.lang.String getESR_RenderedAccountNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ESR_RenderedAccountNo);
	}

	@Override
	public void setESR_RenderedReceiver (java.lang.String ESR_RenderedReceiver)
	{
		set_Value (COLUMNNAME_ESR_RenderedReceiver, ESR_RenderedReceiver);
	}

	@Override
	public java.lang.String getESR_RenderedReceiver() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ESR_RenderedReceiver);
	}

	@Override
	public void setIBAN (java.lang.String IBAN)
	{
		set_Value (COLUMNNAME_IBAN, IBAN);
	}

	@Override
	public java.lang.String getIBAN() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IBAN);
	}

	@Override
	public void setIsACH (boolean IsACH)
	{
		set_Value (COLUMNNAME_IsACH, Boolean.valueOf(IsACH));
	}

	@Override
	public boolean isACH() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsACH);
	}

	@Override
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	@Override
	public boolean isDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefault);
	}

	@Override
	public void setIsDefaultESR (boolean IsDefaultESR)
	{
		set_Value (COLUMNNAME_IsDefaultESR, Boolean.valueOf(IsDefaultESR));
	}

	@Override
	public boolean isDefaultESR() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefaultESR);
	}

	@Override
	public void setIsEsrAccount (boolean IsEsrAccount)
	{
		set_Value (COLUMNNAME_IsEsrAccount, Boolean.valueOf(IsEsrAccount));
	}

	@Override
	public boolean isEsrAccount() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEsrAccount);
	}

	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
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
	@Override
	public void setR_AvsAddr (java.lang.String R_AvsAddr)
	{

		set_ValueNoCheck (COLUMNNAME_R_AvsAddr, R_AvsAddr);
	}

	@Override
	public java.lang.String getR_AvsAddr() 
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
	@Override
	public void setR_AvsZip (java.lang.String R_AvsZip)
	{

		set_ValueNoCheck (COLUMNNAME_R_AvsZip, R_AvsZip);
	}

	@Override
	public java.lang.String getR_AvsZip() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_R_AvsZip);
	}

	@Override
	public void setRoutingNo (java.lang.String RoutingNo)
	{
		set_Value (COLUMNNAME_RoutingNo, RoutingNo);
	}

	@Override
	public java.lang.String getRoutingNo() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RoutingNo);
	}

	@Override
	public void setSEPA_CreditorIdentifier (java.lang.String SEPA_CreditorIdentifier)
	{
		set_Value (COLUMNNAME_SEPA_CreditorIdentifier, SEPA_CreditorIdentifier);
	}

	@Override
	public java.lang.String getSEPA_CreditorIdentifier() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SEPA_CreditorIdentifier);
	}
}