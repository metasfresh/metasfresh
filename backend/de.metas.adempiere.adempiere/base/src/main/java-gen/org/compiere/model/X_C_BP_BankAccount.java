// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_BP_BankAccount
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BP_BankAccount extends org.compiere.model.PO implements I_C_BP_BankAccount, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1222779174L;

    /** Standard Constructor */
    public X_C_BP_BankAccount (final Properties ctx, final int C_BP_BankAccount_ID, @Nullable final String trxName)
    {
      super (ctx, C_BP_BankAccount_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BP_BankAccount (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAccountNo (final @Nullable java.lang.String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	@Override
	public java.lang.String getAccountNo() 
	{
		return get_ValueAsString(COLUMNNAME_AccountNo);
	}

	@Override
	public void setA_City (final @Nullable java.lang.String A_City)
	{
		set_Value (COLUMNNAME_A_City, A_City);
	}

	@Override
	public java.lang.String getA_City() 
	{
		return get_ValueAsString(COLUMNNAME_A_City);
	}

	@Override
	public void setA_Country (final @Nullable java.lang.String A_Country)
	{
		set_Value (COLUMNNAME_A_Country, A_Country);
	}

	@Override
	public java.lang.String getA_Country() 
	{
		return get_ValueAsString(COLUMNNAME_A_Country);
	}

	@Override
	public org.compiere.model.I_AD_Org_Mapping getAD_Org_Mapping()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Org_Mapping_ID, org.compiere.model.I_AD_Org_Mapping.class);
	}

	@Override
	public void setAD_Org_Mapping(final org.compiere.model.I_AD_Org_Mapping AD_Org_Mapping)
	{
		set_ValueFromPO(COLUMNNAME_AD_Org_Mapping_ID, org.compiere.model.I_AD_Org_Mapping.class, AD_Org_Mapping);
	}

	@Override
	public void setAD_Org_Mapping_ID (final int AD_Org_Mapping_ID)
	{
		if (AD_Org_Mapping_ID < 1) 
			set_Value (COLUMNNAME_AD_Org_Mapping_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Org_Mapping_ID, AD_Org_Mapping_ID);
	}

	@Override
	public int getAD_Org_Mapping_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Org_Mapping_ID);
	}

	@Override
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public void setA_EMail (final @Nullable java.lang.String A_EMail)
	{
		set_Value (COLUMNNAME_A_EMail, A_EMail);
	}

	@Override
	public java.lang.String getA_EMail() 
	{
		return get_ValueAsString(COLUMNNAME_A_EMail);
	}

	@Override
	public void setA_Ident_DL (final @Nullable java.lang.String A_Ident_DL)
	{
		set_Value (COLUMNNAME_A_Ident_DL, A_Ident_DL);
	}

	@Override
	public java.lang.String getA_Ident_DL() 
	{
		return get_ValueAsString(COLUMNNAME_A_Ident_DL);
	}

	@Override
	public void setA_Ident_SSN (final @Nullable java.lang.String A_Ident_SSN)
	{
		set_Value (COLUMNNAME_A_Ident_SSN, A_Ident_SSN);
	}

	@Override
	public java.lang.String getA_Ident_SSN() 
	{
		return get_ValueAsString(COLUMNNAME_A_Ident_SSN);
	}

	@Override
	public void setA_Name (final @Nullable java.lang.String A_Name)
	{
		set_Value (COLUMNNAME_A_Name, A_Name);
	}

	@Override
	public java.lang.String getA_Name() 
	{
		return get_ValueAsString(COLUMNNAME_A_Name);
	}

	@Override
	public void setA_State (final @Nullable java.lang.String A_State)
	{
		set_Value (COLUMNNAME_A_State, A_State);
	}

	@Override
	public java.lang.String getA_State() 
	{
		return get_ValueAsString(COLUMNNAME_A_State);
	}

	@Override
	public void setA_Street (final @Nullable java.lang.String A_Street)
	{
		set_Value (COLUMNNAME_A_Street, A_Street);
	}

	@Override
	public java.lang.String getA_Street() 
	{
		return get_ValueAsString(COLUMNNAME_A_Street);
	}

	@Override
	public void setA_Zip (final @Nullable java.lang.String A_Zip)
	{
		set_Value (COLUMNNAME_A_Zip, A_Zip);
	}

	@Override
	public java.lang.String getA_Zip() 
	{
		return get_ValueAsString(COLUMNNAME_A_Zip);
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
	public void setBankAccountType (final @Nullable java.lang.String BankAccountType)
	{
		set_Value (COLUMNNAME_BankAccountType, BankAccountType);
	}

	@Override
	public java.lang.String getBankAccountType() 
	{
		return get_ValueAsString(COLUMNNAME_BankAccountType);
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
	public void setBPBankAcctUse (final @Nullable java.lang.String BPBankAcctUse)
	{
		set_Value (COLUMNNAME_BPBankAcctUse, BPBankAcctUse);
	}

	@Override
	public java.lang.String getBPBankAcctUse() 
	{
		return get_ValueAsString(COLUMNNAME_BPBankAcctUse);
	}

	@Override
	public void setC_Bank_ID (final int C_Bank_ID)
	{
		if (C_Bank_ID < 1) 
			set_Value (COLUMNNAME_C_Bank_ID, null);
		else 
			set_Value (COLUMNNAME_C_Bank_ID, C_Bank_ID);
	}

	@Override
	public int getC_Bank_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Bank_ID);
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
	public void setC_BP_BankAccount_ID (final int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_ID, C_BP_BankAccount_ID);
	}

	@Override
	public int getC_BP_BankAccount_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_BankAccount_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setCreditCardExpMM (final int CreditCardExpMM)
	{
		set_Value (COLUMNNAME_CreditCardExpMM, CreditCardExpMM);
	}

	@Override
	public int getCreditCardExpMM() 
	{
		return get_ValueAsInt(COLUMNNAME_CreditCardExpMM);
	}

	@Override
	public void setCreditCardExpYY (final int CreditCardExpYY)
	{
		set_Value (COLUMNNAME_CreditCardExpYY, CreditCardExpYY);
	}

	@Override
	public int getCreditCardExpYY() 
	{
		return get_ValueAsInt(COLUMNNAME_CreditCardExpYY);
	}

	@Override
	public void setCreditCardNumber (final @Nullable java.lang.String CreditCardNumber)
	{
		set_Value (COLUMNNAME_CreditCardNumber, CreditCardNumber);
	}

	@Override
	public java.lang.String getCreditCardNumber() 
	{
		return get_ValueAsString(COLUMNNAME_CreditCardNumber);
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
	public void setCreditCardType (final @Nullable java.lang.String CreditCardType)
	{
		set_Value (COLUMNNAME_CreditCardType, CreditCardType);
	}

	@Override
	public java.lang.String getCreditCardType() 
	{
		return get_ValueAsString(COLUMNNAME_CreditCardType);
	}

	@Override
	public void setCreditCardVV (final @Nullable java.lang.String CreditCardVV)
	{
		set_Value (COLUMNNAME_CreditCardVV, CreditCardVV);
	}

	@Override
	public java.lang.String getCreditCardVV() 
	{
		return get_ValueAsString(COLUMNNAME_CreditCardVV);
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
	public void setESR_RenderedAccountNo (final @Nullable java.lang.String ESR_RenderedAccountNo)
	{
		set_Value (COLUMNNAME_ESR_RenderedAccountNo, ESR_RenderedAccountNo);
	}

	@Override
	public java.lang.String getESR_RenderedAccountNo() 
	{
		return get_ValueAsString(COLUMNNAME_ESR_RenderedAccountNo);
	}

	@Override
	public void setESR_RenderedReceiver (final @Nullable java.lang.String ESR_RenderedReceiver)
	{
		set_Value (COLUMNNAME_ESR_RenderedReceiver, ESR_RenderedReceiver);
	}

	@Override
	public java.lang.String getESR_RenderedReceiver() 
	{
		return get_ValueAsString(COLUMNNAME_ESR_RenderedReceiver);
	}

	@Override
	public void setIBAN (final @Nullable java.lang.String IBAN)
	{
		set_Value (COLUMNNAME_IBAN, IBAN);
	}

	@Override
	public java.lang.String getIBAN() 
	{
		return get_ValueAsString(COLUMNNAME_IBAN);
	}

	@Override
	public void setIsACH (final boolean IsACH)
	{
		set_Value (COLUMNNAME_IsACH, IsACH);
	}

	@Override
	public boolean isACH() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsACH);
	}

	@Override
	public void setIsDefault (final boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, IsDefault);
	}

	@Override
	public boolean isDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefault);
	}

	@Override
	public void setIsDefaultESR (final boolean IsDefaultESR)
	{
		set_Value (COLUMNNAME_IsDefaultESR, IsDefaultESR);
	}

	@Override
	public boolean isDefaultESR() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefaultESR);
	}

	@Override
	public void setIsEsrAccount (final boolean IsEsrAccount)
	{
		set_Value (COLUMNNAME_IsEsrAccount, IsEsrAccount);
	}

	@Override
	public boolean isEsrAccount() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEsrAccount);
	}

	@Override
	public void setName (final @Nullable java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setQR_IBAN (final @Nullable java.lang.String QR_IBAN)
	{
		set_Value (COLUMNNAME_QR_IBAN, QR_IBAN);
	}

	@Override
	public java.lang.String getQR_IBAN() 
	{
		return get_ValueAsString(COLUMNNAME_QR_IBAN);
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
	public void setR_AvsAddr (final @Nullable java.lang.String R_AvsAddr)
	{
		set_ValueNoCheck (COLUMNNAME_R_AvsAddr, R_AvsAddr);
	}

	@Override
	public java.lang.String getR_AvsAddr() 
	{
		return get_ValueAsString(COLUMNNAME_R_AvsAddr);
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
	public void setR_AvsZip (final @Nullable java.lang.String R_AvsZip)
	{
		set_ValueNoCheck (COLUMNNAME_R_AvsZip, R_AvsZip);
	}

	@Override
	public java.lang.String getR_AvsZip() 
	{
		return get_ValueAsString(COLUMNNAME_R_AvsZip);
	}

	@Override
	public void setRoutingNo (final @Nullable java.lang.String RoutingNo)
	{
		set_Value (COLUMNNAME_RoutingNo, RoutingNo);
	}

	@Override
	public java.lang.String getRoutingNo() 
	{
		return get_ValueAsString(COLUMNNAME_RoutingNo);
	}

	@Override
	public void setSEPA_CreditorIdentifier (final @Nullable java.lang.String SEPA_CreditorIdentifier)
	{
		set_Value (COLUMNNAME_SEPA_CreditorIdentifier, SEPA_CreditorIdentifier);
	}

	@Override
	public java.lang.String getSEPA_CreditorIdentifier() 
	{
		return get_ValueAsString(COLUMNNAME_SEPA_CreditorIdentifier);
	}
}