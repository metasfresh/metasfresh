package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_BP_BankAccount
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_BP_BankAccount 
{

	String Table_Name = "C_BP_BankAccount";

//	/** AD_Table_ID=298 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Konto-Nr..
	 * Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAccountNo (@Nullable java.lang.String AccountNo);

	/**
	 * Get Konto-Nr..
	 * Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAccountNo();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_AccountNo = new ModelColumn<>(I_C_BP_BankAccount.class, "AccountNo", null);
	String COLUMNNAME_AccountNo = "AccountNo";

	/**
	 * Set Ort.
	 * City or the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_City (@Nullable java.lang.String A_City);

	/**
	 * Get Ort.
	 * City or the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_City();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_A_City = new ModelColumn<>(I_C_BP_BankAccount.class, "A_City", null);
	String COLUMNNAME_A_City = "A_City";

	/**
	 * Set Land.
	 * Country
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Country (@Nullable java.lang.String A_Country);

	/**
	 * Get Land.
	 * Country
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_Country();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_A_Country = new ModelColumn<>(I_C_BP_BankAccount.class, "A_Country", null);
	String COLUMNNAME_A_Country = "A_Country";

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set EMail.
	 * Email Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_EMail (@Nullable java.lang.String A_EMail);

	/**
	 * Get EMail.
	 * Email Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_EMail();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_A_EMail = new ModelColumn<>(I_C_BP_BankAccount.class, "A_EMail", null);
	String COLUMNNAME_A_EMail = "A_EMail";

	/**
	 * Set Führerschein-Nr..
	 * Payment Identification - Driver License
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Ident_DL (@Nullable java.lang.String A_Ident_DL);

	/**
	 * Get Führerschein-Nr..
	 * Payment Identification - Driver License
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_Ident_DL();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_A_Ident_DL = new ModelColumn<>(I_C_BP_BankAccount.class, "A_Ident_DL", null);
	String COLUMNNAME_A_Ident_DL = "A_Ident_DL";

	/**
	 * Set Ausweis-Nr..
	 * Payment Identification - Social Security No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Ident_SSN (@Nullable java.lang.String A_Ident_SSN);

	/**
	 * Get Ausweis-Nr..
	 * Payment Identification - Social Security No
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_Ident_SSN();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_A_Ident_SSN = new ModelColumn<>(I_C_BP_BankAccount.class, "A_Ident_SSN", null);
	String COLUMNNAME_A_Ident_SSN = "A_Ident_SSN";

	/**
	 * Set Name.
	 * Name auf Kreditkarte oder des Kontoeigners
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Name (@Nullable java.lang.String A_Name);

	/**
	 * Get Name.
	 * Name auf Kreditkarte oder des Kontoeigners
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_Name();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_A_Name = new ModelColumn<>(I_C_BP_BankAccount.class, "A_Name", null);
	String COLUMNNAME_A_Name = "A_Name";

	/**
	 * Set Bundesstaat / -land.
	 * State of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_State (@Nullable java.lang.String A_State);

	/**
	 * Get Bundesstaat / -land.
	 * State of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_State();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_A_State = new ModelColumn<>(I_C_BP_BankAccount.class, "A_State", null);
	String COLUMNNAME_A_State = "A_State";

	/**
	 * Set Straße.
	 * Street address of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Street (@Nullable java.lang.String A_Street);

	/**
	 * Get Straße.
	 * Street address of the Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_Street();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_A_Street = new ModelColumn<>(I_C_BP_BankAccount.class, "A_Street", null);
	String COLUMNNAME_A_Street = "A_Street";

	/**
	 * Set Postleitzahl.
	 * Zip Code of the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Zip (@Nullable java.lang.String A_Zip);

	/**
	 * Get Postleitzahl.
	 * Zip Code of the Credit Card or Account Holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_Zip();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_A_Zip = new ModelColumn<>(I_C_BP_BankAccount.class, "A_Zip", null);
	String COLUMNNAME_A_Zip = "A_Zip";

	/**
	 * Set Kontenart.
	 * Bank Account Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBankAccountType (@Nullable java.lang.String BankAccountType);

	/**
	 * Get Kontenart.
	 * Bank Account Type
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBankAccountType();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_BankAccountType = new ModelColumn<>(I_C_BP_BankAccount.class, "BankAccountType", null);
	String COLUMNNAME_BankAccountType = "BankAccountType";

	/**
	 * Set Kontonutzung.
	 * Business Partner Bank Account usage
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPBankAcctUse (@Nullable java.lang.String BPBankAcctUse);

	/**
	 * Get Kontonutzung.
	 * Business Partner Bank Account usage
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPBankAcctUse();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_BPBankAcctUse = new ModelColumn<>(I_C_BP_BankAccount.class, "BPBankAcctUse", null);
	String COLUMNNAME_BPBankAcctUse = "BPBankAcctUse";

	/**
	 * Set Bank.
	 * Bank
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Bank_ID (int C_Bank_ID);

	/**
	 * Get Bank.
	 * Bank
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Bank_ID();

	String COLUMNNAME_C_Bank_ID = "C_Bank_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BP_BankAccount_ID();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_C_BP_BankAccount_ID = new ModelColumn<>(I_C_BP_BankAccount.class, "C_BP_BankAccount_ID", null);
	String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_Created = new ModelColumn<>(I_C_BP_BankAccount.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Exp. Monat.
	 * Expiry Month
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditCardExpMM (int CreditCardExpMM);

	/**
	 * Get Exp. Monat.
	 * Expiry Month
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreditCardExpMM();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_CreditCardExpMM = new ModelColumn<>(I_C_BP_BankAccount.class, "CreditCardExpMM", null);
	String COLUMNNAME_CreditCardExpMM = "CreditCardExpMM";

	/**
	 * Set Exp. Jahr.
	 * Expiry Year
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditCardExpYY (int CreditCardExpYY);

	/**
	 * Get Exp. Jahr.
	 * Expiry Year
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreditCardExpYY();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_CreditCardExpYY = new ModelColumn<>(I_C_BP_BankAccount.class, "CreditCardExpYY", null);
	String COLUMNNAME_CreditCardExpYY = "CreditCardExpYY";

	/**
	 * Set Kreditkarten-Nummer.
	 * Kreditkarten-Nummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditCardNumber (@Nullable java.lang.String CreditCardNumber);

	/**
	 * Get Kreditkarten-Nummer.
	 * Kreditkarten-Nummer
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreditCardNumber();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_CreditCardNumber = new ModelColumn<>(I_C_BP_BankAccount.class, "CreditCardNumber", null);
	String COLUMNNAME_CreditCardNumber = "CreditCardNumber";

	/**
	 * Set Kreditkarte.
	 * Credit Card (Visa, MC, AmEx)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditCardType (@Nullable java.lang.String CreditCardType);

	/**
	 * Get Kreditkarte.
	 * Credit Card (Visa, MC, AmEx)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreditCardType();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_CreditCardType = new ModelColumn<>(I_C_BP_BankAccount.class, "CreditCardType", null);
	String COLUMNNAME_CreditCardType = "CreditCardType";

	/**
	 * Set Verifizierungs-Code.
	 * Credit Card Verification code on credit card
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditCardVV (@Nullable java.lang.String CreditCardVV);

	/**
	 * Get Verifizierungs-Code.
	 * Credit Card Verification code on credit card
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreditCardVV();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_CreditCardVV = new ModelColumn<>(I_C_BP_BankAccount.class, "CreditCardVV", null);
	String COLUMNNAME_CreditCardVV = "CreditCardVV";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_Description = new ModelColumn<>(I_C_BP_BankAccount.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set ESR Participant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setESR_RenderedAccountNo (@Nullable java.lang.String ESR_RenderedAccountNo);

	/**
	 * Get ESR Participant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getESR_RenderedAccountNo();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_ESR_RenderedAccountNo = new ModelColumn<>(I_C_BP_BankAccount.class, "ESR_RenderedAccountNo", null);
	String COLUMNNAME_ESR_RenderedAccountNo = "ESR_RenderedAccountNo";

	/**
	 * Set ESR Receiver.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setESR_RenderedReceiver (@Nullable java.lang.String ESR_RenderedReceiver);

	/**
	 * Get ESR Receiver.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getESR_RenderedReceiver();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_ESR_RenderedReceiver = new ModelColumn<>(I_C_BP_BankAccount.class, "ESR_RenderedReceiver", null);
	String COLUMNNAME_ESR_RenderedReceiver = "ESR_RenderedReceiver";

	/**
	 * Set IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIBAN (@Nullable java.lang.String IBAN);

	/**
	 * Get IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIBAN();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_IBAN = new ModelColumn<>(I_C_BP_BankAccount.class, "IBAN", null);
	String COLUMNNAME_IBAN = "IBAN";

	/**
	 * Set ACH.
	 * Automatic Clearing House
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsACH (boolean IsACH);

	/**
	 * Get ACH.
	 * Automatic Clearing House
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isACH();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_IsACH = new ModelColumn<>(I_C_BP_BankAccount.class, "IsACH", null);
	String COLUMNNAME_IsACH = "IsACH";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BP_BankAccount.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsDefault (boolean IsDefault);

	/**
	 * Get Default.
	 * Default value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isDefault();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_IsDefault = new ModelColumn<>(I_C_BP_BankAccount.class, "IsDefault", null);
	String COLUMNNAME_IsDefault = "IsDefault";

	/**
	 * Set Standard ESR Konto.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsDefaultESR (boolean IsDefaultESR);

	/**
	 * Get Standard ESR Konto.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isDefaultESR();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_IsDefaultESR = new ModelColumn<>(I_C_BP_BankAccount.class, "IsDefaultESR", null);
	String COLUMNNAME_IsDefaultESR = "IsDefaultESR";

	/**
	 * Set ESR Account.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsEsrAccount (boolean IsEsrAccount);

	/**
	 * Get ESR Account.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEsrAccount();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_IsEsrAccount = new ModelColumn<>(I_C_BP_BankAccount.class, "IsEsrAccount", null);
	String COLUMNNAME_IsEsrAccount = "IsEsrAccount";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName (@Nullable java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_Name = new ModelColumn<>(I_C_BP_BankAccount.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set QR IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQR_IBAN (@Nullable java.lang.String QR_IBAN);

	/**
	 * Get QR IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getQR_IBAN();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_QR_IBAN = new ModelColumn<>(I_C_BP_BankAccount.class, "QR_IBAN", null);
	String COLUMNNAME_QR_IBAN = "QR_IBAN";

	/**
	 * Set Addresse verifiziert.
	 * This address has been verified
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_AvsAddr (@Nullable java.lang.String R_AvsAddr);

	/**
	 * Get Addresse verifiziert.
	 * This address has been verified
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getR_AvsAddr();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_R_AvsAddr = new ModelColumn<>(I_C_BP_BankAccount.class, "R_AvsAddr", null);
	String COLUMNNAME_R_AvsAddr = "R_AvsAddr";

	/**
	 * Set Postleitzahl verifiziert.
	 * The Zip Code has been verified
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_AvsZip (@Nullable java.lang.String R_AvsZip);

	/**
	 * Get Postleitzahl verifiziert.
	 * The Zip Code has been verified
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getR_AvsZip();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_R_AvsZip = new ModelColumn<>(I_C_BP_BankAccount.class, "R_AvsZip", null);
	String COLUMNNAME_R_AvsZip = "R_AvsZip";

	/**
	 * Set BLZ.
	 * Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRoutingNo (@Nullable java.lang.String RoutingNo);

	/**
	 * Get BLZ.
	 * Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRoutingNo();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_RoutingNo = new ModelColumn<>(I_C_BP_BankAccount.class, "RoutingNo", null);
	String COLUMNNAME_RoutingNo = "RoutingNo";

	/**
	 * Set SEPA Creditor Identifier.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSEPA_CreditorIdentifier (@Nullable java.lang.String SEPA_CreditorIdentifier);

	/**
	 * Get SEPA Creditor Identifier.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSEPA_CreditorIdentifier();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_SEPA_CreditorIdentifier = new ModelColumn<>(I_C_BP_BankAccount.class, "SEPA_CreditorIdentifier", null);
	String COLUMNNAME_SEPA_CreditorIdentifier = "SEPA_CreditorIdentifier";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BP_BankAccount, Object> COLUMN_Updated = new ModelColumn<>(I_C_BP_BankAccount.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
