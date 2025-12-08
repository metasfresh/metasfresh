package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_BPartner_QuickInput
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BPartner_QuickInput 
{

	String Table_Name = "C_BPartner_QuickInput";

//	/** AD_Table_ID=540804 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


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
	 * Set Language.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Language (@Nullable java.lang.String AD_Language);

	/**
	 * Get Language.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAD_Language();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_AD_Language = new ModelColumn<>(I_C_BPartner_QuickInput.class, "AD_Language", null);
	String COLUMNNAME_AD_Language = "AD_Language";

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
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_ID (int AD_User_ID);

	/**
	 * Get Contact.
	 * User within the system - Internal or Business Partner Contact
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_ID();

	String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/**
	 * Set Partner Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartnerName (@Nullable java.lang.String BPartnerName);

	/**
	 * Get Partner Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPartnerName();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_BPartnerName = new ModelColumn<>(I_C_BPartner_QuickInput.class, "BPartnerName", null);
	String COLUMNNAME_BPartnerName = "BPartnerName";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Kunden E-Mail Adresse.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_Email (@Nullable java.lang.String C_BPartner_Location_Email);

	/**
	 * Get Kunden E-Mail Adresse.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getC_BPartner_Location_Email();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_C_BPartner_Location_Email = new ModelColumn<>(I_C_BPartner_QuickInput.class, "C_BPartner_Location_Email", null);
	String COLUMNNAME_C_BPartner_Location_Email = "C_BPartner_Location_Email";

	/**
	 * Set Telefax.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_Fax (@Nullable java.lang.String C_BPartner_Location_Fax);

	/**
	 * Get Telefax.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getC_BPartner_Location_Fax();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_C_BPartner_Location_Fax = new ModelColumn<>(I_C_BPartner_QuickInput.class, "C_BPartner_Location_Fax", null);
	String COLUMNNAME_C_BPartner_Location_Fax = "C_BPartner_Location_Fax";

	/**
	 * Set Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Mobil.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_Mobile (@Nullable java.lang.String C_BPartner_Location_Mobile);

	/**
	 * Get Mobil.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getC_BPartner_Location_Mobile();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_C_BPartner_Location_Mobile = new ModelColumn<>(I_C_BPartner_QuickInput.class, "C_BPartner_Location_Mobile", null);
	String COLUMNNAME_C_BPartner_Location_Mobile = "C_BPartner_Location_Mobile";

	/**
	 * Set Telefon.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_Phone (@Nullable java.lang.String C_BPartner_Location_Phone);

	/**
	 * Get Telefon.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getC_BPartner_Location_Phone();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_C_BPartner_Location_Phone = new ModelColumn<>(I_C_BPartner_QuickInput.class, "C_BPartner_Location_Phone", null);
	String COLUMNNAME_C_BPartner_Location_Phone = "C_BPartner_Location_Phone";

	/**
	 * Set New BPartner quick input.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_QuickInput_ID (int C_BPartner_QuickInput_ID);

	/**
	 * Get New BPartner quick input.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_QuickInput_ID();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_C_BPartner_QuickInput_ID = new ModelColumn<>(I_C_BPartner_QuickInput.class, "C_BPartner_QuickInput_ID", null);
	String COLUMNNAME_C_BPartner_QuickInput_ID = "C_BPartner_QuickInput_ID";

	/**
	 * Set Business Partner Group.
	 * Business Partner Group
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BP_Group_ID (int C_BP_Group_ID);

	/**
	 * Get Business Partner Group.
	 * Business Partner Group
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BP_Group_ID();

	@Nullable org.compiere.model.I_C_BP_Group getC_BP_Group();

	void setC_BP_Group(@Nullable org.compiere.model.I_C_BP_Group C_BP_Group);

	ModelColumn<I_C_BPartner_QuickInput, org.compiere.model.I_C_BP_Group> COLUMN_C_BP_Group_ID = new ModelColumn<>(I_C_BPartner_QuickInput.class, "C_BP_Group_ID", org.compiere.model.I_C_BP_Group.class);
	String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/**
	 * Set Document Type.
	 * Target document type for conversing documents
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID);

	/**
	 * Get Document Type.
	 * Target document type for conversing documents
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocTypeTarget_ID();

	String COLUMNNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";

	/**
	 * Set Greeting (ID).
	 * Greeting to print on correspondence
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Greeting_ID (int C_Greeting_ID);

	/**
	 * Get Greeting (ID).
	 * Greeting to print on correspondence
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Greeting_ID();

	String COLUMNNAME_C_Greeting_ID = "C_Greeting_ID";

	/**
	 * Set Location.
	 * Location or Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Location_ID (int C_Location_ID);

	/**
	 * Get Location.
	 * Location or Address
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Location_ID();

	@Nullable org.compiere.model.I_C_Location getC_Location();

	void setC_Location(@Nullable org.compiere.model.I_C_Location C_Location);

	ModelColumn<I_C_BPartner_QuickInput, org.compiere.model.I_C_Location> COLUMN_C_Location_ID = new ModelColumn<>(I_C_BPartner_QuickInput.class, "C_Location_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_Location_ID = "C_Location_ID";

	/**
	 * Set Company Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCompanyname (@Nullable java.lang.String Companyname);

	/**
	 * Get Company Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCompanyname();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_Companyname = new ModelColumn<>(I_C_BPartner_QuickInput.class, "Companyname", null);
	String COLUMNNAME_Companyname = "Companyname";

	/**
	 * Set Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_PaymentTerm_ID();

	String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_Created = new ModelColumn<>(I_C_BPartner_QuickInput.class, "Created", null);
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
	 * Set eMail.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEMail (@Nullable java.lang.String EMail);

	/**
	 * Get eMail.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEMail();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_EMail = new ModelColumn<>(I_C_BPartner_QuickInput.class, "EMail", null);
	String COLUMNNAME_EMail = "EMail";

	/**
	 * Set Exclude From Promotions.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExcludeFromPromotions (boolean ExcludeFromPromotions);

	/**
	 * Get Exclude From Promotions.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isExcludeFromPromotions();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_ExcludeFromPromotions = new ModelColumn<>(I_C_BPartner_QuickInput.class, "ExcludeFromPromotions", null);
	String COLUMNNAME_ExcludeFromPromotions = "ExcludeFromPromotions";

	/**
	 * Set Firstname.
	 * Firstname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFirstname (@Nullable java.lang.String Firstname);

	/**
	 * Get Firstname.
	 * Firstname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFirstname();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_Firstname = new ModelColumn<>(I_C_BPartner_QuickInput.class, "Firstname", null);
	String COLUMNNAME_Firstname = "Firstname";

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

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BPartner_QuickInput.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Company.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCompany (boolean IsCompany);

	/**
	 * Get Company.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCompany();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_IsCompany = new ModelColumn<>(I_C_BPartner_QuickInput.class, "IsCompany", null);
	String COLUMNNAME_IsCompany = "IsCompany";

	/**
	 * Set Kunde.
	 * Zeigt an, ob dieser Geschäftspartner ein Kunde ist
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCustomer (boolean IsCustomer);

	/**
	 * Get Kunde.
	 * Zeigt an, ob dieser Geschäftspartner ein Kunde ist
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCustomer();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_IsCustomer = new ModelColumn<>(I_C_BPartner_QuickInput.class, "IsCustomer", null);
	String COLUMNNAME_IsCustomer = "IsCustomer";

	/**
	 * Set Newsletter.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsNewsletter (boolean IsNewsletter);

	/**
	 * Get Newsletter.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isNewsletter();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_IsNewsletter = new ModelColumn<>(I_C_BPartner_QuickInput.class, "IsNewsletter", null);
	String COLUMNNAME_IsNewsletter = "IsNewsletter";

	/**
	 * Set Lieferant.
	 * Zeigt an, ob dieser Geschaäftspartner ein Lieferant ist
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsVendor (boolean IsVendor);

	/**
	 * Get Lieferant.
	 * Zeigt an, ob dieser Geschaäftspartner ein Lieferant ist
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isVendor();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_IsVendor = new ModelColumn<>(I_C_BPartner_QuickInput.class, "IsVendor", null);
	String COLUMNNAME_IsVendor = "IsVendor";

	/**
	 * Set Lastname.
	 * Lastname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLastname (@Nullable java.lang.String Lastname);

	/**
	 * Get Lastname.
	 * Lastname
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLastname();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_Lastname = new ModelColumn<>(I_C_BPartner_QuickInput.class, "Lastname", null);
	String COLUMNNAME_Lastname = "Lastname";

	/**
	 * Set Marketing Campaign.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMKTG_Campaign_ID (int MKTG_Campaign_ID);

	/**
	 * Get Marketing Campaign.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getMKTG_Campaign_ID();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_MKTG_Campaign_ID = new ModelColumn<>(I_C_BPartner_QuickInput.class, "MKTG_Campaign_ID", null);
	String COLUMNNAME_MKTG_Campaign_ID = "MKTG_Campaign_ID";

	/**
	 * Set Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_PricingSystem_ID();

	String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	/**
	 * Set Name 2.
	 * Zusätzliche Bezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName2 (@Nullable java.lang.String Name2);

	/**
	 * Get Name 2.
	 * Zusätzliche Bezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName2();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_Name2 = new ModelColumn<>(I_C_BPartner_QuickInput.class, "Name2", null);
	String COLUMNNAME_Name2 = "Name2";

	/**
	 * Set Payment Rule.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPaymentRule (@Nullable java.lang.String PaymentRule);

	/**
	 * Get Payment Rule.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPaymentRule();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_PaymentRule = new ModelColumn<>(I_C_BPartner_QuickInput.class, "PaymentRule", null);
	String COLUMNNAME_PaymentRule = "PaymentRule";

	/**
	 * Set Phone.
	 * Identifies a telephone number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPhone (@Nullable java.lang.String Phone);

	/**
	 * Get Phone.
	 * Identifies a telephone number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPhone();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_Phone = new ModelColumn<>(I_C_BPartner_QuickInput.class, "Phone", null);
	String COLUMNNAME_Phone = "Phone";

	/**
	 * Set Zahlungskondition.
	 * Zahlungskondition für die Bestellung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPO_PaymentTerm_ID (int PO_PaymentTerm_ID);

	/**
	 * Get Zahlungskondition.
	 * Zahlungskondition für die Bestellung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPO_PaymentTerm_ID();

	String COLUMNNAME_PO_PaymentTerm_ID = "PO_PaymentTerm_ID";

	/**
	 * Set Purchase Pricing System.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPO_PricingSystem_ID (int PO_PricingSystem_ID);

	/**
	 * Get Purchase Pricing System.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPO_PricingSystem_ID();

	String COLUMNNAME_PO_PricingSystem_ID = "PO_PricingSystem_ID";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_Processed = new ModelColumn<>(I_C_BPartner_QuickInput.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Referrer.
	 * Referring web address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReferrer (@Nullable java.lang.String Referrer);

	/**
	 * Get Referrer.
	 * Referring web address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReferrer();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_Referrer = new ModelColumn<>(I_C_BPartner_QuickInput.class, "Referrer", null);
	String COLUMNNAME_Referrer = "Referrer";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner_QuickInput.class, "Updated", null);
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

	/**
	 * Set VAT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVATaxID (@Nullable java.lang.String VATaxID);

	/**
	 * Get VAT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVATaxID();

	ModelColumn<I_C_BPartner_QuickInput, Object> COLUMN_VATaxID = new ModelColumn<>(I_C_BPartner_QuickInput.class, "VATaxID", null);
	String COLUMNNAME_VATaxID = "VATaxID";
}
