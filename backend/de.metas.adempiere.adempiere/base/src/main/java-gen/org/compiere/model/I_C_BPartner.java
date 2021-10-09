package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_BPartner
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BPartner 
{

	String Table_Name = "C_BPartner";

//	/** AD_Table_ID=291 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Akquisitionskosten.
	 * The cost of gaining the prospect as a customer
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAcqusitionCost (@Nullable BigDecimal AcqusitionCost);

	/**
	 * Get Akquisitionskosten.
	 * The cost of gaining the prospect as a customer
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAcqusitionCost();

	ModelColumn<I_C_BPartner, Object> COLUMN_AcqusitionCost = new ModelColumn<>(I_C_BPartner.class, "AcqusitionCost", null);
	String COLUMNNAME_AcqusitionCost = "AcqusitionCost";

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
	 * Set Straße und Nr..
	 * Adresszeile 1 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setAddress1 (@Nullable java.lang.String Address1);

	/**
	 * Get Straße und Nr..
	 * Adresszeile 1 für diesen Standort
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getAddress1();

	ModelColumn<I_C_BPartner, Object> COLUMN_Address1 = new ModelColumn<>(I_C_BPartner.class, "Address1", null);
	String COLUMNNAME_Address1 = "Address1";

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

	ModelColumn<I_C_BPartner, Object> COLUMN_AD_Language = new ModelColumn<>(I_C_BPartner.class, "AD_Language", null);
	String COLUMNNAME_AD_Language = "AD_Language";

	/**
	 * Set Linked Organization.
	 * The Business Partner is another Organization for explicit Inter-Org transactions
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_OrgBP_ID (int AD_OrgBP_ID);

	/**
	 * Get Linked Organization.
	 * The Business Partner is another Organization for explicit Inter-Org transactions
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_OrgBP_ID();

	String COLUMNNAME_AD_OrgBP_ID = "AD_OrgBP_ID";

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
	 * Set Org Mapping.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_Mapping_ID (int AD_Org_Mapping_ID);

	/**
	 * Get Org Mapping.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Org_Mapping_ID();

	@Nullable org.compiere.model.I_AD_Org_Mapping getAD_Org_Mapping();

	void setAD_Org_Mapping(@Nullable org.compiere.model.I_AD_Org_Mapping AD_Org_Mapping);

	ModelColumn<I_C_BPartner, org.compiere.model.I_AD_Org_Mapping> COLUMN_AD_Org_Mapping_ID = new ModelColumn<>(I_C_BPartner.class, "AD_Org_Mapping_ID", org.compiere.model.I_AD_Org_Mapping.class);
	String COLUMNNAME_AD_Org_Mapping_ID = "AD_Org_Mapping_ID";

	/**
	 * Set Role.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAlbertaRole (@Nullable java.lang.String AlbertaRole);

	/**
	 * Get Role.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAlbertaRole();

	ModelColumn<I_C_BPartner, Object> COLUMN_AlbertaRole = new ModelColumn<>(I_C_BPartner.class, "AlbertaRole", null);
	String COLUMNNAME_AlbertaRole = "AlbertaRole";

	/**
	 * Set Title.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setAlbertaTitle (@Nullable java.lang.String AlbertaTitle);

	/**
	 * Get Title.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getAlbertaTitle();

	ModelColumn<I_C_BPartner, Object> COLUMN_AlbertaTitle = new ModelColumn<>(I_C_BPartner.class, "AlbertaTitle", null);
	String COLUMNNAME_AlbertaTitle = "AlbertaTitle";

	/**
	 * Set Consolidate Shipments allowed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAllowConsolidateInOut (boolean AllowConsolidateInOut);

	/**
	 * Get Consolidate Shipments allowed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowConsolidateInOut();

	ModelColumn<I_C_BPartner, Object> COLUMN_AllowConsolidateInOut = new ModelColumn<>(I_C_BPartner.class, "AllowConsolidateInOut", null);
	String COLUMNNAME_AllowConsolidateInOut = "AllowConsolidateInOut";

	/**
	 * Set Partner Parent.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartner_Parent_ID (int BPartner_Parent_ID);

	/**
	 * Get Partner Parent.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBPartner_Parent_ID();

	String COLUMNNAME_BPartner_Parent_ID = "BPartner_Parent_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	ModelColumn<I_C_BPartner, Object> COLUMN_C_BPartner_ID = new ModelColumn<>(I_C_BPartner.class, "C_BPartner_ID", null);
	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Sales partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_SalesRep_ID (int C_BPartner_SalesRep_ID);

	/**
	 * Get Sales partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_SalesRep_ID();

	String COLUMNNAME_C_BPartner_SalesRep_ID = "C_BPartner_SalesRep_ID";

	/**
	 * Set Business Partner Group.
	 * Business Partner Group
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BP_Group_ID (int C_BP_Group_ID);

	/**
	 * Get Business Partner Group.
	 * Business Partner Group
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BP_Group_ID();

	org.compiere.model.I_C_BP_Group getC_BP_Group();

	void setC_BP_Group(org.compiere.model.I_C_BP_Group C_BP_Group);

	ModelColumn<I_C_BPartner, org.compiere.model.I_C_BP_Group> COLUMN_C_BP_Group_ID = new ModelColumn<>(I_C_BPartner.class, "C_BP_Group_ID", org.compiere.model.I_C_BP_Group.class);
	String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/**
	 * Set Mahnung.
	 * Dunning Rules for overdue invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Dunning_ID (int C_Dunning_ID);

	/**
	 * Get Mahnung.
	 * Dunning Rules for overdue invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Dunning_ID();

	@Nullable org.compiere.model.I_C_Dunning getC_Dunning();

	void setC_Dunning(@Nullable org.compiere.model.I_C_Dunning C_Dunning);

	ModelColumn<I_C_BPartner, org.compiere.model.I_C_Dunning> COLUMN_C_Dunning_ID = new ModelColumn<>(I_C_BPartner.class, "C_Dunning_ID", org.compiere.model.I_C_Dunning.class);
	String COLUMNNAME_C_Dunning_ID = "C_Dunning_ID";

	/**
	 * Set Certificate of Registration.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCertificateOfRegistrationCustomer (boolean CertificateOfRegistrationCustomer);

	/**
	 * Get Certificate of Registration.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isCertificateOfRegistrationCustomer();

	ModelColumn<I_C_BPartner, Object> COLUMN_CertificateOfRegistrationCustomer = new ModelColumn<>(I_C_BPartner.class, "CertificateOfRegistrationCustomer", null);
	String COLUMNNAME_CertificateOfRegistrationCustomer = "CertificateOfRegistrationCustomer";

	/**
	 * Set Certificate of Registration.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCertificateOfRegistrationVendor (boolean CertificateOfRegistrationVendor);

	/**
	 * Get Certificate of Registration.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isCertificateOfRegistrationVendor();

	ModelColumn<I_C_BPartner, Object> COLUMN_CertificateOfRegistrationVendor = new ModelColumn<>(I_C_BPartner.class, "CertificateOfRegistrationVendor", null);
	String COLUMNNAME_CertificateOfRegistrationVendor = "CertificateOfRegistrationVendor";

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
	 * Set Terminplan Rechnung.
	 * Schedule for generating Invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_InvoiceSchedule_ID (int C_InvoiceSchedule_ID);

	/**
	 * Get Terminplan Rechnung.
	 * Schedule for generating Invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_InvoiceSchedule_ID();

	@Nullable org.compiere.model.I_C_InvoiceSchedule getC_InvoiceSchedule();

	void setC_InvoiceSchedule(@Nullable org.compiere.model.I_C_InvoiceSchedule C_InvoiceSchedule);

	ModelColumn<I_C_BPartner, org.compiere.model.I_C_InvoiceSchedule> COLUMN_C_InvoiceSchedule_ID = new ModelColumn<>(I_C_BPartner.class, "C_InvoiceSchedule_ID", org.compiere.model.I_C_InvoiceSchedule.class);
	String COLUMNNAME_C_InvoiceSchedule_ID = "C_InvoiceSchedule_ID";

	/**
	 * Set City Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setCity (@Nullable java.lang.String City);

	/**
	 * Get City Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getCity();

	ModelColumn<I_C_BPartner, Object> COLUMN_City = new ModelColumn<>(I_C_BPartner.class, "City", null);
	String COLUMNNAME_City = "City";

	/**
	 * Set Company Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCompanyName (@Nullable java.lang.String CompanyName);

	/**
	 * Get Company Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCompanyName();

	ModelColumn<I_C_BPartner, Object> COLUMN_CompanyName = new ModelColumn<>(I_C_BPartner.class, "CompanyName", null);
	String COLUMNNAME_CompanyName = "CompanyName";

	/**
	 * Set Contact Status Information.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setContactStatusInfoCustomer (boolean ContactStatusInfoCustomer);

	/**
	 * Get Contact Status Information.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isContactStatusInfoCustomer();

	ModelColumn<I_C_BPartner, Object> COLUMN_ContactStatusInfoCustomer = new ModelColumn<>(I_C_BPartner.class, "ContactStatusInfoCustomer", null);
	String COLUMNNAME_ContactStatusInfoCustomer = "ContactStatusInfoCustomer";

	/**
	 * Set Contact Status Information.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setContactStatusInfoVendor (boolean ContactStatusInfoVendor);

	/**
	 * Get Contact Status Information.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isContactStatusInfoVendor();

	ModelColumn<I_C_BPartner, Object> COLUMN_ContactStatusInfoVendor = new ModelColumn<>(I_C_BPartner.class, "ContactStatusInfoVendor", null);
	String COLUMNNAME_ContactStatusInfoVendor = "ContactStatusInfoVendor";

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

	ModelColumn<I_C_BPartner, Object> COLUMN_Created = new ModelColumn<>(I_C_BPartner.class, "Created", null);
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
	 * Set Auftrag anlegen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreateSO (@Nullable java.lang.String CreateSO);

	/**
	 * Get Auftrag anlegen.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreateSO();

	ModelColumn<I_C_BPartner, Object> COLUMN_CreateSO = new ModelColumn<>(I_C_BPartner.class, "CreateSO", null);
	String COLUMNNAME_CreateSO = "CreateSO";

	/**
	 * Set Credit limit indicator.
	 * Percent of Credit used from the limit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setCreditLimitIndicator (@Nullable java.lang.String CreditLimitIndicator);

	/**
	 * Get Credit limit indicator.
	 * Percent of Credit used from the limit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getCreditLimitIndicator();

	ModelColumn<I_C_BPartner, Object> COLUMN_CreditLimitIndicator = new ModelColumn<>(I_C_BPartner.class, "CreditLimitIndicator", null);
	String COLUMNNAME_CreditLimitIndicator = "CreditLimitIndicator";

	/**
	 * Set Kreditoren-Nr.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditorId (int CreditorId);

	/**
	 * Get Kreditoren-Nr.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreditorId();

	ModelColumn<I_C_BPartner, Object> COLUMN_CreditorId = new ModelColumn<>(I_C_BPartner.class, "CreditorId", null);
	String COLUMNNAME_CreditorId = "CreditorId";

	/**
	 * Set Tax Group.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_TaxGroup_ID (int C_TaxGroup_ID);

	/**
	 * Get Tax Group.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_TaxGroup_ID();

	@Nullable org.eevolution.model.I_C_TaxGroup getC_TaxGroup();

	void setC_TaxGroup(@Nullable org.eevolution.model.I_C_TaxGroup C_TaxGroup);

	ModelColumn<I_C_BPartner, org.eevolution.model.I_C_TaxGroup> COLUMN_C_TaxGroup_ID = new ModelColumn<>(I_C_BPartner.class, "C_TaxGroup_ID", org.eevolution.model.I_C_TaxGroup.class);
	String COLUMNNAME_C_TaxGroup_ID = "C_TaxGroup_ID";

	/**
	 * Set Eigene-Kd. Nr. .
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCustomerNoAtVendor (@Nullable java.lang.String CustomerNoAtVendor);

	/**
	 * Get Eigene-Kd. Nr. .
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCustomerNoAtVendor();

	ModelColumn<I_C_BPartner, Object> COLUMN_CustomerNoAtVendor = new ModelColumn<>(I_C_BPartner.class, "CustomerNoAtVendor", null);
	String COLUMNNAME_CustomerNoAtVendor = "CustomerNoAtVendor";

	/**
	 * Set Haddex Check Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateHaddexCheck (@Nullable java.sql.Timestamp DateHaddexCheck);

	/**
	 * Get Haddex Check Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateHaddexCheck();

	ModelColumn<I_C_BPartner, Object> COLUMN_DateHaddexCheck = new ModelColumn<>(I_C_BPartner.class, "DateHaddexCheck", null);
	String COLUMNNAME_DateHaddexCheck = "DateHaddexCheck";

	/**
	 * Set Debitoren-Nr.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDebtorId (int DebtorId);

	/**
	 * Get Debitoren-Nr.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDebtorId();

	ModelColumn<I_C_BPartner, Object> COLUMN_DebtorId = new ModelColumn<>(I_C_BPartner.class, "DebtorId", null);
	String COLUMNNAME_DebtorId = "DebtorId";

	/**
	 * Set Default Ship To City.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setDefaultShipTo_City (@Nullable java.lang.String DefaultShipTo_City);

	/**
	 * Get Default Ship To City.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getDefaultShipTo_City();

	ModelColumn<I_C_BPartner, Object> COLUMN_DefaultShipTo_City = new ModelColumn<>(I_C_BPartner.class, "DefaultShipTo_City", null);
	String COLUMNNAME_DefaultShipTo_City = "DefaultShipTo_City";

	/**
	 * Set Default Ship To Postal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setDefaultShipTo_Postal (@Nullable java.lang.String DefaultShipTo_Postal);

	/**
	 * Get Default Ship To Postal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getDefaultShipTo_Postal();

	ModelColumn<I_C_BPartner, Object> COLUMN_DefaultShipTo_Postal = new ModelColumn<>(I_C_BPartner.class, "DefaultShipTo_Postal", null);
	String COLUMNNAME_DefaultShipTo_Postal = "DefaultShipTo_Postal";

	/**
	 * Set Lieferart.
	 * Defines the timing of Delivery
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryRule (@Nullable java.lang.String DeliveryRule);

	/**
	 * Get Lieferart.
	 * Defines the timing of Delivery
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDeliveryRule();

	ModelColumn<I_C_BPartner, Object> COLUMN_DeliveryRule = new ModelColumn<>(I_C_BPartner.class, "DeliveryRule", null);
	String COLUMNNAME_DeliveryRule = "DeliveryRule";

	/**
	 * Set Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryViaRule (@Nullable java.lang.String DeliveryViaRule);

	/**
	 * Get Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDeliveryViaRule();

	ModelColumn<I_C_BPartner, Object> COLUMN_DeliveryViaRule = new ModelColumn<>(I_C_BPartner.class, "DeliveryViaRule", null);
	String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

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

	ModelColumn<I_C_BPartner, Object> COLUMN_Description = new ModelColumn<>(I_C_BPartner.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Kopien.
	 * Number of copies to be printed
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentCopies (int DocumentCopies);

	/**
	 * Get Kopien.
	 * Number of copies to be printed
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDocumentCopies();

	ModelColumn<I_C_BPartner, Object> COLUMN_DocumentCopies = new ModelColumn<>(I_C_BPartner.class, "DocumentCopies", null);
	String COLUMNNAME_DocumentCopies = "DocumentCopies";

	/**
	 * Set Dunning Grace Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDunningGrace (@Nullable java.sql.Timestamp DunningGrace);

	/**
	 * Get Dunning Grace Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDunningGrace();

	ModelColumn<I_C_BPartner, Object> COLUMN_DunningGrace = new ModelColumn<>(I_C_BPartner.class, "DunningGrace", null);
	String COLUMNNAME_DunningGrace = "DunningGrace";

	/**
	 * Set D-U-N-S.
	 * Dun & Bradstreet Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDUNS (@Nullable java.lang.String DUNS);

	/**
	 * Get D-U-N-S.
	 * Dun & Bradstreet Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDUNS();

	ModelColumn<I_C_BPartner, Object> COLUMN_DUNS = new ModelColumn<>(I_C_BPartner.class, "DUNS", null);
	String COLUMNNAME_DUNS = "DUNS";

	/**
	 * Set eMail.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setEMail (@Nullable java.lang.String EMail);

	/**
	 * Get eMail.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getEMail();

	ModelColumn<I_C_BPartner, Object> COLUMN_EMail = new ModelColumn<>(I_C_BPartner.class, "EMail", null);
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

	ModelColumn<I_C_BPartner, Object> COLUMN_ExcludeFromPromotions = new ModelColumn<>(I_C_BPartner.class, "ExcludeFromPromotions", null);
	String COLUMNNAME_ExcludeFromPromotions = "ExcludeFromPromotions";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalId (@Nullable java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalId();

	ModelColumn<I_C_BPartner, Object> COLUMN_ExternalId = new ModelColumn<>(I_C_BPartner.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

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

	ModelColumn<I_C_BPartner, Object> COLUMN_Firstname = new ModelColumn<>(I_C_BPartner.class, "Firstname", null);
	String COLUMNNAME_Firstname = "Firstname";

	/**
	 * Set Erster Verkauf.
	 * Date of First Sale
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFirstSale (@Nullable java.sql.Timestamp FirstSale);

	/**
	 * Get Erster Verkauf.
	 * Date of First Sale
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getFirstSale();

	ModelColumn<I_C_BPartner, Object> COLUMN_FirstSale = new ModelColumn<>(I_C_BPartner.class, "FirstSale", null);
	String COLUMNNAME_FirstSale = "FirstSale";

	/**
	 * Set Fester Rabatt %.
	 * Flat discount percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFlatDiscount (@Nullable BigDecimal FlatDiscount);

	/**
	 * Get Fester Rabatt %.
	 * Flat discount percentage
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getFlatDiscount();

	ModelColumn<I_C_BPartner, Object> COLUMN_FlatDiscount = new ModelColumn<>(I_C_BPartner.class, "FlatDiscount", null);
	String COLUMNNAME_FlatDiscount = "FlatDiscount";

	/**
	 * Set Frachtkostenberechnung.
	 * Method for charging Freight
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFreightCostRule (@Nullable java.lang.String FreightCostRule);

	/**
	 * Get Frachtkostenberechnung.
	 * Method for charging Freight
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFreightCostRule();

	ModelColumn<I_C_BPartner, Object> COLUMN_FreightCostRule = new ModelColumn<>(I_C_BPartner.class, "FreightCostRule", null);
	String COLUMNNAME_FreightCostRule = "FreightCostRule";

	/**
	 * Set GDP Certificate.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGDPCertificateCustomer (boolean GDPCertificateCustomer);

	/**
	 * Get GDP Certificate.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isGDPCertificateCustomer();

	ModelColumn<I_C_BPartner, Object> COLUMN_GDPCertificateCustomer = new ModelColumn<>(I_C_BPartner.class, "GDPCertificateCustomer", null);
	String COLUMNNAME_GDPCertificateCustomer = "GDPCertificateCustomer";

	/**
	 * Set GDP Certificate.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGDPCertificateVendor (boolean GDPCertificateVendor);

	/**
	 * Get GDP Certificate.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isGDPCertificateVendor();

	ModelColumn<I_C_BPartner, Object> COLUMN_GDPCertificateVendor = new ModelColumn<>(I_C_BPartner.class, "GDPCertificateVendor", null);
	String COLUMNNAME_GDPCertificateVendor = "GDPCertificateVendor";

	/**
	 * Set Global ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGlobalId (@Nullable java.lang.String GlobalId);

	/**
	 * Get Global ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGlobalId();

	ModelColumn<I_C_BPartner, Object> COLUMN_GlobalId = new ModelColumn<>(I_C_BPartner.class, "GlobalId", null);
	String COLUMNNAME_GlobalId = "GlobalId";

	/**
	 * Set Haddex Control Nr..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHaddexControlNr (@Nullable java.lang.String HaddexControlNr);

	/**
	 * Get Haddex Control Nr..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHaddexControlNr();

	ModelColumn<I_C_BPartner, Object> COLUMN_HaddexControlNr = new ModelColumn<>(I_C_BPartner.class, "HaddexControlNr", null);
	String COLUMNNAME_HaddexControlNr = "HaddexControlNr";

	/**
	 * Set Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInternalName (@Nullable java.lang.String InternalName);

	/**
	 * Get Internal Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInternalName();

	ModelColumn<I_C_BPartner, Object> COLUMN_InternalName = new ModelColumn<>(I_C_BPartner.class, "InternalName", null);
	String COLUMNNAME_InternalName = "InternalName";

	/**
	 * Set Druckformat Rechnung.
	 * Print Format for printing Invoices
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoice_PrintFormat_ID (int Invoice_PrintFormat_ID);

	/**
	 * Get Druckformat Rechnung.
	 * Print Format for printing Invoices
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getInvoice_PrintFormat_ID();

	@Nullable org.compiere.model.I_AD_PrintFormat getInvoice_PrintFormat();

	void setInvoice_PrintFormat(@Nullable org.compiere.model.I_AD_PrintFormat Invoice_PrintFormat);

	ModelColumn<I_C_BPartner, org.compiere.model.I_AD_PrintFormat> COLUMN_Invoice_PrintFormat_ID = new ModelColumn<>(I_C_BPartner.class, "Invoice_PrintFormat_ID", org.compiere.model.I_AD_PrintFormat.class);
	String COLUMNNAME_Invoice_PrintFormat_ID = "Invoice_PrintFormat_ID";

	/**
	 * Set Rechnungsstellung.
	 * "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoiceRule (@Nullable java.lang.String InvoiceRule);

	/**
	 * Get Rechnungsstellung.
	 * "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInvoiceRule();

	ModelColumn<I_C_BPartner, Object> COLUMN_InvoiceRule = new ModelColumn<>(I_C_BPartner.class, "InvoiceRule", null);
	String COLUMNNAME_InvoiceRule = "InvoiceRule";

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

	ModelColumn<I_C_BPartner, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BPartner.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Aggregate Purchase Orders.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAggregatePO (boolean IsAggregatePO);

	/**
	 * Get Aggregate Purchase Orders.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAggregatePO();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsAggregatePO = new ModelColumn<>(I_C_BPartner.class, "IsAggregatePO", null);
	String COLUMNNAME_IsAggregatePO = "IsAggregatePO";

	/**
	 * Set Is Alberta doctor.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsAlbertaDoctor (boolean IsAlbertaDoctor);

	/**
	 * Get Is Alberta doctor.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isAlbertaDoctor();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsAlbertaDoctor = new ModelColumn<>(I_C_BPartner.class, "IsAlbertaDoctor", null);
	String COLUMNNAME_IsAlbertaDoctor = "IsAlbertaDoctor";

	/**
	 * Set Allow campaign prices.
	 * Wenn auf "ja" gesetzt, dann werden bei der Preisberechnung auch Aktionspreise berücksichtigt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowActionPrice (boolean IsAllowActionPrice);

	/**
	 * Get Allow campaign prices.
	 * Wenn auf "ja" gesetzt, dann werden bei der Preisberechnung auch Aktionspreise berücksichtigt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowActionPrice();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsAllowActionPrice = new ModelColumn<>(I_C_BPartner.class, "IsAllowActionPrice", null);
	String COLUMNNAME_IsAllowActionPrice = "IsAllowActionPrice";

	/**
	 * Set PLV from base.
	 * Allow derivative PLV from base
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowPriceMutation (boolean IsAllowPriceMutation);

	/**
	 * Get PLV from base.
	 * Allow derivative PLV from base
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowPriceMutation();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsAllowPriceMutation = new ModelColumn<>(I_C_BPartner.class, "IsAllowPriceMutation", null);
	String COLUMNNAME_IsAllowPriceMutation = "IsAllowPriceMutation";

	/**
	 * Set Archived.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsArchived (boolean IsArchived);

	/**
	 * Get Archived.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isArchived();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsArchived = new ModelColumn<>(I_C_BPartner.class, "IsArchived", null);
	String COLUMNNAME_IsArchived = "IsArchived";

	/**
	 * Set Company.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsCompany (boolean IsCompany);

	/**
	 * Get Company.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isCompany();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsCompany = new ModelColumn<>(I_C_BPartner.class, "IsCompany", null);
	String COLUMNNAME_IsCompany = "IsCompany";

	/**
	 * Set Autom. Reference in Order.
	 * Erlaubt es, bei einem neuen Auftrag automatisch das Referenz-Feld des Auftrags vorzubelegen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCreateDefaultPOReference (boolean IsCreateDefaultPOReference);

	/**
	 * Get Autom. Reference in Order.
	 * Erlaubt es, bei einem neuen Auftrag automatisch das Referenz-Feld des Auftrags vorzubelegen.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCreateDefaultPOReference();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsCreateDefaultPOReference = new ModelColumn<>(I_C_BPartner.class, "IsCreateDefaultPOReference", null);
	String COLUMNNAME_IsCreateDefaultPOReference = "IsCreateDefaultPOReference";

	/**
	 * Set Kunde.
	 * Indicates if this Business Partner is a Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCustomer (boolean IsCustomer);

	/**
	 * Get Kunde.
	 * Indicates if this Business Partner is a Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCustomer();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsCustomer = new ModelColumn<>(I_C_BPartner.class, "IsCustomer", null);
	String COLUMNNAME_IsCustomer = "IsCustomer";

	/**
	 * Set Rabatte drucken.
	 * Print Discount on Invoice and Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsDiscountPrinted (boolean IsDiscountPrinted);

	/**
	 * Get Rabatte drucken.
	 * Print Discount on Invoice and Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isDiscountPrinted();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsDiscountPrinted = new ModelColumn<>(I_C_BPartner.class, "IsDiscountPrinted", null);
	String COLUMNNAME_IsDiscountPrinted = "IsDiscountPrinted";

	/**
	 * Set EDI DESADV Receipient.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsEdiDesadvRecipient (boolean IsEdiDesadvRecipient);

	/**
	 * Get EDI DESADV Receipient.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEdiDesadvRecipient();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsEdiDesadvRecipient = new ModelColumn<>(I_C_BPartner.class, "IsEdiDesadvRecipient", null);
	String COLUMNNAME_IsEdiDesadvRecipient = "IsEdiDesadvRecipient";

	/**
	 * Set Mitarbeiter.
	 * Indicates if  this Business Partner is an employee
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsEmployee (boolean IsEmployee);

	/**
	 * Get Mitarbeiter.
	 * Indicates if  this Business Partner is an employee
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEmployee();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsEmployee = new ModelColumn<>(I_C_BPartner.class, "IsEmployee", null);
	String COLUMNNAME_IsEmployee = "IsEmployee";

	/**
	 * Set Haddex Prüfung erforderlich.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsHaddexCheck (boolean IsHaddexCheck);

	/**
	 * Get Haddex Prüfung erforderlich.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isHaddexCheck();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsHaddexCheck = new ModelColumn<>(I_C_BPartner.class, "IsHaddexCheck", null);
	String COLUMNNAME_IsHaddexCheck = "IsHaddexCheck";

	/**
	 * Set Manufacturer.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManufacturer (boolean IsManufacturer);

	/**
	 * Get Manufacturer.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManufacturer();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsManufacturer = new ModelColumn<>(I_C_BPartner.class, "IsManufacturer", null);
	String COLUMNNAME_IsManufacturer = "IsManufacturer";

	/**
	 * Set One time transaction.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOneTime (boolean IsOneTime);

	/**
	 * Get One time transaction.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOneTime();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsOneTime = new ModelColumn<>(I_C_BPartner.class, "IsOneTime", null);
	String COLUMNNAME_IsOneTime = "IsOneTime";

	/**
	 * Set PO Tax exempt.
	 * Business partner is exempt from tax on purchases
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPOTaxExempt (boolean IsPOTaxExempt);

	/**
	 * Get PO Tax exempt.
	 * Business partner is exempt from tax on purchases
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPOTaxExempt();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsPOTaxExempt = new ModelColumn<>(I_C_BPartner.class, "IsPOTaxExempt", null);
	String COLUMNNAME_IsPOTaxExempt = "IsPOTaxExempt";

	/**
	 * Set Aktiver Interessent/Kunde.
	 * Indicates this is a Prospect
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsProspect (boolean IsProspect);

	/**
	 * Get Aktiver Interessent/Kunde.
	 * Indicates this is a Prospect
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProspect();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsProspect = new ModelColumn<>(I_C_BPartner.class, "IsProspect", null);
	String COLUMNNAME_IsProspect = "IsProspect";

	/**
	 * Set Sales partner required.
	 * Specifies for a bill partner whether a sales partner has to be specified in a sales order.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSalesPartnerRequired (boolean IsSalesPartnerRequired);

	/**
	 * Get Sales partner required.
	 * Specifies for a bill partner whether a sales partner has to be specified in a sales order.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSalesPartnerRequired();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsSalesPartnerRequired = new ModelColumn<>(I_C_BPartner.class, "IsSalesPartnerRequired", null);
	String COLUMNNAME_IsSalesPartnerRequired = "IsSalesPartnerRequired";

	/**
	 * Set Sales partner.
	 * Indicates if  the business partner is a sales representative or company agent
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSalesRep (boolean IsSalesRep);

	/**
	 * Get Sales partner.
	 * Indicates if  the business partner is a sales representative or company agent
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSalesRep();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsSalesRep = new ModelColumn<>(I_C_BPartner.class, "IsSalesRep", null);
	String COLUMNNAME_IsSalesRep = "IsSalesRep";

	/**
	 * Set SEPA Signed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSEPASigned (boolean IsSEPASigned);

	/**
	 * Get SEPA Signed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSEPASigned();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsSEPASigned = new ModelColumn<>(I_C_BPartner.class, "IsSEPASigned", null);
	String COLUMNNAME_IsSEPASigned = "IsSEPASigned";

	/**
	 * Set Shipping Notification Email.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsShippingNotificationEmail (boolean IsShippingNotificationEmail);

	/**
	 * Get Shipping Notification Email.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isShippingNotificationEmail();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsShippingNotificationEmail = new ModelColumn<>(I_C_BPartner.class, "IsShippingNotificationEmail", null);
	String COLUMNNAME_IsShippingNotificationEmail = "IsShippingNotificationEmail";

	/**
	 * Set Zusammenfassungseintrag.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSummary (boolean IsSummary);

	/**
	 * Get Zusammenfassungseintrag.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSummary();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsSummary = new ModelColumn<>(I_C_BPartner.class, "IsSummary", null);
	String COLUMNNAME_IsSummary = "IsSummary";

	/**
	 * Set steuerbefreit.
	 * Steuersatz steuerbefreit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsTaxExempt (boolean IsTaxExempt);

	/**
	 * Get steuerbefreit.
	 * Steuersatz steuerbefreit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isTaxExempt();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsTaxExempt = new ModelColumn<>(I_C_BPartner.class, "IsTaxExempt", null);
	String COLUMNNAME_IsTaxExempt = "IsTaxExempt";

	/**
	 * Set Lieferant.
	 * Indicates if this Business Partner is a Vendor
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsVendor (boolean IsVendor);

	/**
	 * Get Lieferant.
	 * Indicates if this Business Partner is a Vendor
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isVendor();

	ModelColumn<I_C_BPartner, Object> COLUMN_IsVendor = new ModelColumn<>(I_C_BPartner.class, "IsVendor", null);
	String COLUMNNAME_IsVendor = "IsVendor";

	/**
	 * Set Kundencockpit_includedTab1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setKundencockpit_includedTab1 (@Nullable java.lang.String Kundencockpit_includedTab1);

	/**
	 * Get Kundencockpit_includedTab1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getKundencockpit_includedTab1();

	ModelColumn<I_C_BPartner, Object> COLUMN_Kundencockpit_includedTab1 = new ModelColumn<>(I_C_BPartner.class, "Kundencockpit_includedTab1", null);
	String COLUMNNAME_Kundencockpit_includedTab1 = "Kundencockpit_includedTab1";

	/**
	 * Set Kundencockpit_includedTab2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setKundencockpit_includedTab2 (@Nullable java.lang.String Kundencockpit_includedTab2);

	/**
	 * Get Kundencockpit_includedTab2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getKundencockpit_includedTab2();

	ModelColumn<I_C_BPartner, Object> COLUMN_Kundencockpit_includedTab2 = new ModelColumn<>(I_C_BPartner.class, "Kundencockpit_includedTab2", null);
	String COLUMNNAME_Kundencockpit_includedTab2 = "Kundencockpit_includedTab2";

	/**
	 * Set Kundencockpit_includedTab3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setKundencockpit_includedTab3 (@Nullable java.lang.String Kundencockpit_includedTab3);

	/**
	 * Get Kundencockpit_includedTab3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getKundencockpit_includedTab3();

	ModelColumn<I_C_BPartner, Object> COLUMN_Kundencockpit_includedTab3 = new ModelColumn<>(I_C_BPartner.class, "Kundencockpit_includedTab3", null);
	String COLUMNNAME_Kundencockpit_includedTab3 = "Kundencockpit_includedTab3";

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

	ModelColumn<I_C_BPartner, Object> COLUMN_Lastname = new ModelColumn<>(I_C_BPartner.class, "Lastname", null);
	String COLUMNNAME_Lastname = "Lastname";

	/**
	 * Set Logo.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLogo_ID (int Logo_ID);

	/**
	 * Get Logo.
	 *
	 * <br>Type: Image
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLogo_ID();

	@Nullable org.compiere.model.I_AD_Image getLogo();

	void setLogo(@Nullable org.compiere.model.I_AD_Image Logo);

	ModelColumn<I_C_BPartner, org.compiere.model.I_AD_Image> COLUMN_Logo_ID = new ModelColumn<>(I_C_BPartner.class, "Logo_ID", org.compiere.model.I_AD_Image.class);
	String COLUMNNAME_Logo_ID = "Logo_ID";

	/**
	 * Set Discount Schema.
	 * Schema to calculate the trade discount percentage
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_DiscountSchema_ID (int M_DiscountSchema_ID);

	/**
	 * Get Discount Schema.
	 * Schema to calculate the trade discount percentage
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_DiscountSchema_ID();

	@Nullable org.compiere.model.I_M_DiscountSchema getM_DiscountSchema();

	void setM_DiscountSchema(@Nullable org.compiere.model.I_M_DiscountSchema M_DiscountSchema);

	ModelColumn<I_C_BPartner, org.compiere.model.I_M_DiscountSchema> COLUMN_M_DiscountSchema_ID = new ModelColumn<>(I_C_BPartner.class, "M_DiscountSchema_ID", org.compiere.model.I_M_DiscountSchema.class);
	String COLUMNNAME_M_DiscountSchema_ID = "M_DiscountSchema_ID";

	/**
	 * Set Memo.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMemo (@Nullable java.lang.String Memo);

	/**
	 * Get Memo.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMemo();

	ModelColumn<I_C_BPartner, Object> COLUMN_Memo = new ModelColumn<>(I_C_BPartner.class, "Memo", null);
	String COLUMNNAME_Memo = "Memo";

	/**
	 * Set Memo_Delivery.
	 * Memo Lieferung
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMemo_Delivery (@Nullable java.lang.String Memo_Delivery);

	/**
	 * Get Memo_Delivery.
	 * Memo Lieferung
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMemo_Delivery();

	ModelColumn<I_C_BPartner, Object> COLUMN_Memo_Delivery = new ModelColumn<>(I_C_BPartner.class, "Memo_Delivery", null);
	String COLUMNNAME_Memo_Delivery = "Memo_Delivery";

	/**
	 * Set Memo_Invoicing.
	 * Memo Abrechnung
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMemo_Invoicing (@Nullable java.lang.String Memo_Invoicing);

	/**
	 * Get Memo_Invoicing.
	 * Memo Abrechnung
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMemo_Invoicing();

	ModelColumn<I_C_BPartner, Object> COLUMN_Memo_Invoicing = new ModelColumn<>(I_C_BPartner.class, "Memo_Invoicing", null);
	String COLUMNNAME_Memo_Invoicing = "Memo_Invoicing";

	/**
	 * Set Frachtkostenpauschale.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_FreightCost_ID (int M_FreightCost_ID);

	/**
	 * Get Frachtkostenpauschale.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_FreightCost_ID();

	ModelColumn<I_C_BPartner, Object> COLUMN_M_FreightCost_ID = new ModelColumn<>(I_C_BPartner.class, "M_FreightCost_ID", null);
	String COLUMNNAME_M_FreightCost_ID = "M_FreightCost_ID";

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

	ModelColumn<I_C_BPartner, Object> COLUMN_MKTG_Campaign_ID = new ModelColumn<>(I_C_BPartner.class, "MKTG_Campaign_ID", null);
	String COLUMNNAME_MKTG_Campaign_ID = "MKTG_Campaign_ID";

	/**
	 * Set Preisliste.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Preisliste.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_PriceList_ID();

	String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

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
	 * Set Exclude from MRP.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMRP_Exclude (@Nullable java.lang.String MRP_Exclude);

	/**
	 * Get Exclude from MRP.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMRP_Exclude();

	ModelColumn<I_C_BPartner, Object> COLUMN_MRP_Exclude = new ModelColumn<>(I_C_BPartner.class, "MRP_Exclude", null);
	String COLUMNNAME_MRP_Exclude = "MRP_Exclude";

	/**
	 * Set Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Shipper_ID (int M_Shipper_ID);

	/**
	 * Get Shipper.
	 * Method or manner of product delivery
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Shipper_ID();

	@Nullable org.compiere.model.I_M_Shipper getM_Shipper();

	void setM_Shipper(@Nullable org.compiere.model.I_M_Shipper M_Shipper);

	ModelColumn<I_C_BPartner, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_C_BPartner.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set NAICS/SIC.
	 * Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNAICS (@Nullable java.lang.String NAICS);

	/**
	 * Get NAICS/SIC.
	 * Standard Industry Code or its successor NAIC - http://www.osha.gov/oshstats/sicser.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNAICS();

	ModelColumn<I_C_BPartner, Object> COLUMN_NAICS = new ModelColumn<>(I_C_BPartner.class, "NAICS", null);
	String COLUMNNAME_NAICS = "NAICS";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_C_BPartner, Object> COLUMN_Name = new ModelColumn<>(I_C_BPartner.class, "Name", null);
	String COLUMNNAME_Name = "Name";

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

	ModelColumn<I_C_BPartner, Object> COLUMN_Name2 = new ModelColumn<>(I_C_BPartner.class, "Name2", null);
	String COLUMNNAME_Name2 = "Name2";

	/**
	 * Set Name3.
	 * Zusätzliche Bezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName3 (@Nullable java.lang.String Name3);

	/**
	 * Get Name3.
	 * Zusätzliche Bezeichnung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName3();

	ModelColumn<I_C_BPartner, Object> COLUMN_Name3 = new ModelColumn<>(I_C_BPartner.class, "Name3", null);
	String COLUMNNAME_Name3 = "Name3";

	/**
	 * Set Mitarbeiter.
	 * Anzahl der Mitarbeiter
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNumberEmployees (int NumberEmployees);

	/**
	 * Get Mitarbeiter.
	 * Anzahl der Mitarbeiter
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getNumberEmployees();

	ModelColumn<I_C_BPartner, Object> COLUMN_NumberEmployees = new ModelColumn<>(I_C_BPartner.class, "NumberEmployees", null);
	String COLUMNNAME_NumberEmployees = "NumberEmployees";

	/**
	 * Set Payment Rule.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPaymentRule (java.lang.String PaymentRule);

	/**
	 * Get Payment Rule.
	 * How you pay the invoice
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPaymentRule();

	ModelColumn<I_C_BPartner, Object> COLUMN_PaymentRule = new ModelColumn<>(I_C_BPartner.class, "PaymentRule", null);
	String COLUMNNAME_PaymentRule = "PaymentRule";

	/**
	 * Set Zahlungsweise.
	 * Purchase payment option
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPaymentRulePO (java.lang.String PaymentRulePO);

	/**
	 * Get Zahlungsweise.
	 * Purchase payment option
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPaymentRulePO();

	ModelColumn<I_C_BPartner, Object> COLUMN_PaymentRulePO = new ModelColumn<>(I_C_BPartner.class, "PaymentRulePO", null);
	String COLUMNNAME_PaymentRulePO = "PaymentRulePO";

	/**
	 * Set Phone (alternative).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPhone2 (@Nullable java.lang.String Phone2);

	/**
	 * Get Phone (alternative).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPhone2();

	ModelColumn<I_C_BPartner, Object> COLUMN_Phone2 = new ModelColumn<>(I_C_BPartner.class, "Phone2", null);
	String COLUMNNAME_Phone2 = "Phone2";

	/**
	 * Set Delivery Via Rule.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPO_DeliveryViaRule (@Nullable java.lang.String PO_DeliveryViaRule);

	/**
	 * Get Delivery Via Rule.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPO_DeliveryViaRule();

	ModelColumn<I_C_BPartner, Object> COLUMN_PO_DeliveryViaRule = new ModelColumn<>(I_C_BPartner.class, "PO_DeliveryViaRule", null);
	String COLUMNNAME_PO_DeliveryViaRule = "PO_DeliveryViaRule";

	/**
	 * Set PO Discount Schema.
	 * Schema to calculate the purchase trade discount percentage
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPO_DiscountSchema_ID (int PO_DiscountSchema_ID);

	/**
	 * Get PO Discount Schema.
	 * Schema to calculate the purchase trade discount percentage
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPO_DiscountSchema_ID();

	@Nullable org.compiere.model.I_M_DiscountSchema getPO_DiscountSchema();

	void setPO_DiscountSchema(@Nullable org.compiere.model.I_M_DiscountSchema PO_DiscountSchema);

	ModelColumn<I_C_BPartner, org.compiere.model.I_M_DiscountSchema> COLUMN_PO_DiscountSchema_ID = new ModelColumn<>(I_C_BPartner.class, "PO_DiscountSchema_ID", org.compiere.model.I_M_DiscountSchema.class);
	String COLUMNNAME_PO_DiscountSchema_ID = "PO_DiscountSchema_ID";

	/**
	 * Set PO_Incoterm.
	 * Internationale Handelsklauseln (engl. International Commercial Terms)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPO_Incoterm (@Nullable java.lang.String PO_Incoterm);

	/**
	 * Get PO_Incoterm.
	 * Internationale Handelsklauseln (engl. International Commercial Terms)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPO_Incoterm();

	ModelColumn<I_C_BPartner, Object> COLUMN_PO_Incoterm = new ModelColumn<>(I_C_BPartner.class, "PO_Incoterm", null);
	String COLUMNNAME_PO_Incoterm = "PO_Incoterm";

	/**
	 * Set Zahlungskondition.
	 * Payment rules for a purchase order
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPO_PaymentTerm_ID (int PO_PaymentTerm_ID);

	/**
	 * Get Zahlungskondition.
	 * Payment rules for a purchase order
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPO_PaymentTerm_ID();

	String COLUMNNAME_PO_PaymentTerm_ID = "PO_PaymentTerm_ID";

	/**
	 * Set Einkaufspreisliste.
	 * Price List used by this Business Partner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPO_PriceList_ID (int PO_PriceList_ID);

	/**
	 * Get Einkaufspreisliste.
	 * Price List used by this Business Partner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPO_PriceList_ID();

	String COLUMNNAME_PO_PriceList_ID = "PO_PriceList_ID";

	/**
	 * Set Purchase Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPO_PricingSystem_ID (int PO_PricingSystem_ID);

	/**
	 * Get Purchase Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPO_PricingSystem_ID();

	String COLUMNNAME_PO_PricingSystem_ID = "PO_PricingSystem_ID";

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOReference (@Nullable java.lang.String POReference);

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOReference();

	ModelColumn<I_C_BPartner, Object> COLUMN_POReference = new ModelColumn<>(I_C_BPartner.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Auftrag Referenz-Wert Vorlage.
	 * Der Wert dieses Feldes wird mit der Auftrags-Belegnummer kombiniert, um die Auftragsreferenz zu erzeugen
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOReferencePattern (@Nullable java.lang.String POReferencePattern);

	/**
	 * Get Auftrag Referenz-Wert Vorlage.
	 * Der Wert dieses Feldes wird mit der Auftrags-Belegnummer kombiniert, um die Auftragsreferenz zu erzeugen
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOReferencePattern();

	ModelColumn<I_C_BPartner, Object> COLUMN_POReferencePattern = new ModelColumn<>(I_C_BPartner.class, "POReferencePattern", null);
	String COLUMNNAME_POReferencePattern = "POReferencePattern";

	/**
	 * Set Postal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setPostal (@Nullable java.lang.String Postal);

	/**
	 * Get Postal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getPostal();

	ModelColumn<I_C_BPartner, Object> COLUMN_Postal = new ModelColumn<>(I_C_BPartner.class, "Postal", null);
	String COLUMNNAME_Postal = "Postal";

	/**
	 * Set Möglicher Gesamtertrag.
	 * Total Revenue expected
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPotentialLifeTimeValue (@Nullable BigDecimal PotentialLifeTimeValue);

	/**
	 * Get Möglicher Gesamtertrag.
	 * Total Revenue expected
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPotentialLifeTimeValue();

	ModelColumn<I_C_BPartner, Object> COLUMN_PotentialLifeTimeValue = new ModelColumn<>(I_C_BPartner.class, "PotentialLifeTimeValue", null);
	String COLUMNNAME_PotentialLifeTimeValue = "PotentialLifeTimeValue";

	/**
	 * Set QMS Certificate.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQMSCertificateCustomer (boolean QMSCertificateCustomer);

	/**
	 * Get QMS Certificate.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isQMSCertificateCustomer();

	ModelColumn<I_C_BPartner, Object> COLUMN_QMSCertificateCustomer = new ModelColumn<>(I_C_BPartner.class, "QMSCertificateCustomer", null);
	String COLUMNNAME_QMSCertificateCustomer = "QMSCertificateCustomer";

	/**
	 * Set QMS Certificate.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQMSCertificateVendor (boolean QMSCertificateVendor);

	/**
	 * Get QMS Certificate.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isQMSCertificateVendor();

	ModelColumn<I_C_BPartner, Object> COLUMN_QMSCertificateVendor = new ModelColumn<>(I_C_BPartner.class, "QMSCertificateVendor", null);
	String COLUMNNAME_QMSCertificateVendor = "QMSCertificateVendor";

	/**
	 * Set Qualification .
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQualification (@Nullable java.lang.String Qualification);

	/**
	 * Get Qualification .
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getQualification();

	ModelColumn<I_C_BPartner, Object> COLUMN_Qualification = new ModelColumn<>(I_C_BPartner.class, "Qualification", null);
	String COLUMNNAME_Qualification = "Qualification";

	/**
	 * Set Rating.
	 * Classification or Importance
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRating (@Nullable java.lang.String Rating);

	/**
	 * Get Rating.
	 * Classification or Importance
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRating();

	ModelColumn<I_C_BPartner, Object> COLUMN_Rating = new ModelColumn<>(I_C_BPartner.class, "Rating", null);
	String COLUMNNAME_Rating = "Rating";

	/**
	 * Set Referenznummer.
	 * Your customer or vendor number at the Business Partner's site
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReferenceNo (@Nullable java.lang.String ReferenceNo);

	/**
	 * Get Referenznummer.
	 * Your customer or vendor number at the Business Partner's site
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReferenceNo();

	ModelColumn<I_C_BPartner, Object> COLUMN_ReferenceNo = new ModelColumn<>(I_C_BPartner.class, "ReferenceNo", null);
	String COLUMNNAME_ReferenceNo = "ReferenceNo";

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

	ModelColumn<I_C_BPartner, Object> COLUMN_Referrer = new ModelColumn<>(I_C_BPartner.class, "Referrer", null);
	String COLUMNNAME_Referrer = "Referrer";

	/**
	 * Set Wiedervorlage Datum Aussen.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReminderDateExtern (@Nullable java.sql.Timestamp ReminderDateExtern);

	/**
	 * Get Wiedervorlage Datum Aussen.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getReminderDateExtern();

	ModelColumn<I_C_BPartner, Object> COLUMN_ReminderDateExtern = new ModelColumn<>(I_C_BPartner.class, "ReminderDateExtern", null);
	String COLUMNNAME_ReminderDateExtern = "ReminderDateExtern";

	/**
	 * Set Wiedervorlage Datum Innen.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReminderDateIntern (@Nullable java.sql.Timestamp ReminderDateIntern);

	/**
	 * Get Wiedervorlage Datum Innen.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getReminderDateIntern();

	ModelColumn<I_C_BPartner, Object> COLUMN_ReminderDateIntern = new ModelColumn<>(I_C_BPartner.class, "ReminderDateIntern", null);
	String COLUMNNAME_ReminderDateIntern = "ReminderDateIntern";

	/**
	 * Set Statistik Gruppe.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSalesgroup (@Nullable java.lang.String Salesgroup);

	/**
	 * Get Statistik Gruppe.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSalesgroup();

	ModelColumn<I_C_BPartner, Object> COLUMN_Salesgroup = new ModelColumn<>(I_C_BPartner.class, "Salesgroup", null);
	String COLUMNNAME_Salesgroup = "Salesgroup";

	/**
	 * Set Sales partner code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSalesPartnerCode (@Nullable java.lang.String SalesPartnerCode);

	/**
	 * Get Sales partner code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSalesPartnerCode();

	ModelColumn<I_C_BPartner, Object> COLUMN_SalesPartnerCode = new ModelColumn<>(I_C_BPartner.class, "SalesPartnerCode", null);
	String COLUMNNAME_SalesPartnerCode = "SalesPartnerCode";

	/**
	 * Set Account manager.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Account manager.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSalesRep_ID();

	String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/**
	 * Set Sales Responsible.
	 * Sales Responsible Internal
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSalesRepIntern_ID (int SalesRepIntern_ID);

	/**
	 * Get Sales Responsible.
	 * Sales Responsible Internal
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSalesRepIntern_ID();

	String COLUMNNAME_SalesRepIntern_ID = "SalesRepIntern_ID";

	/**
	 * Set Verkaufsvolumen in 1.000.
	 * Total Volume of Sales in Thousands of Currency
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSalesVolume (int SalesVolume);

	/**
	 * Get Verkaufsvolumen in 1.000.
	 * Total Volume of Sales in Thousands of Currency
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSalesVolume();

	ModelColumn<I_C_BPartner, Object> COLUMN_SalesVolume = new ModelColumn<>(I_C_BPartner.class, "SalesVolume", null);
	String COLUMNNAME_SalesVolume = "SalesVolume";

	/**
	 * Set Self Disclosure.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSelfDisclosureCustomer (boolean SelfDisclosureCustomer);

	/**
	 * Get Self Disclosure.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isSelfDisclosureCustomer();

	ModelColumn<I_C_BPartner, Object> COLUMN_SelfDisclosureCustomer = new ModelColumn<>(I_C_BPartner.class, "SelfDisclosureCustomer", null);
	String COLUMNNAME_SelfDisclosureCustomer = "SelfDisclosureCustomer";

	/**
	 * Set Self Disclosure.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSelfDisclosureVendor (boolean SelfDisclosureVendor);

	/**
	 * Get Self Disclosure.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isSelfDisclosureVendor();

	ModelColumn<I_C_BPartner, Object> COLUMN_SelfDisclosureVendor = new ModelColumn<>(I_C_BPartner.class, "SelfDisclosureVendor", null);
	String COLUMNNAME_SelfDisclosureVendor = "SelfDisclosureVendor";

	/**
	 * Set E-Mail senden.
	 * Enable sending Document EMail
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSendEMail (boolean SendEMail);

	/**
	 * Get E-Mail senden.
	 * Enable sending Document EMail
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSendEMail();

	ModelColumn<I_C_BPartner, Object> COLUMN_SendEMail = new ModelColumn<>(I_C_BPartner.class, "SendEMail", null);
	String COLUMNNAME_SendEMail = "SendEMail";

	/**
	 * Set Anteil.
	 * Share of Customer's business as a percentage
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShareOfCustomer (int ShareOfCustomer);

	/**
	 * Get Anteil.
	 * Share of Customer's business as a percentage
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getShareOfCustomer();

	ModelColumn<I_C_BPartner, Object> COLUMN_ShareOfCustomer = new ModelColumn<>(I_C_BPartner.class, "ShareOfCustomer", null);
	String COLUMNNAME_ShareOfCustomer = "ShareOfCustomer";

	/**
	 * Set Mindesthaltbarkeit %.
	 * Minimum Shelf Life in percent based on Product Instance Guarantee Date
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShelfLifeMinPct (int ShelfLifeMinPct);

	/**
	 * Get Mindesthaltbarkeit %.
	 * Minimum Shelf Life in percent based on Product Instance Guarantee Date
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getShelfLifeMinPct();

	ModelColumn<I_C_BPartner, Object> COLUMN_ShelfLifeMinPct = new ModelColumn<>(I_C_BPartner.class, "ShelfLifeMinPct", null);
	String COLUMNNAME_ShelfLifeMinPct = "ShelfLifeMinPct";

	/**
	 * Set Best Before Policy.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShipmentAllocation_BestBefore_Policy (@Nullable java.lang.String ShipmentAllocation_BestBefore_Policy);

	/**
	 * Get Best Before Policy.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShipmentAllocation_BestBefore_Policy();

	ModelColumn<I_C_BPartner, Object> COLUMN_ShipmentAllocation_BestBefore_Policy = new ModelColumn<>(I_C_BPartner.class, "ShipmentAllocation_BestBefore_Policy", null);
	String COLUMNNAME_ShipmentAllocation_BestBefore_Policy = "ShipmentAllocation_BestBefore_Policy";

	/**
	 * Set Short Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShortDescription (@Nullable java.lang.String ShortDescription);

	/**
	 * Get Short Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShortDescription();

	ModelColumn<I_C_BPartner, Object> COLUMN_ShortDescription = new ModelColumn<>(I_C_BPartner.class, "ShortDescription", null);
	String COLUMNNAME_ShortDescription = "ShortDescription";

	/**
	 * Set Beschreibung Auftrag.
	 * Description to be used on orders
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSO_Description (@Nullable java.lang.String SO_Description);

	/**
	 * Get Beschreibung Auftrag.
	 * Description to be used on orders
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSO_Description();

	ModelColumn<I_C_BPartner, Object> COLUMN_SO_Description = new ModelColumn<>(I_C_BPartner.class, "SO_Description", null);
	String COLUMNNAME_SO_Description = "SO_Description";

	/**
	 * Set Zielbelegart.
	 * Zielbelegart für die Umwandlung von Dokumenten
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSO_DocTypeTarget_ID (int SO_DocTypeTarget_ID);

	/**
	 * Get Zielbelegart.
	 * Zielbelegart für die Umwandlung von Dokumenten
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSO_DocTypeTarget_ID();

	String COLUMNNAME_SO_DocTypeTarget_ID = "SO_DocTypeTarget_ID";

	/**
	 * Set Notiz Auftragsart.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSO_TargetDocTypeReason (@Nullable java.lang.String SO_TargetDocTypeReason);

	/**
	 * Get Notiz Auftragsart.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSO_TargetDocTypeReason();

	ModelColumn<I_C_BPartner, Object> COLUMN_SO_TargetDocTypeReason = new ModelColumn<>(I_C_BPartner.class, "SO_TargetDocTypeReason", null);
	String COLUMNNAME_SO_TargetDocTypeReason = "SO_TargetDocTypeReason";

	/**
	 * Set Tax ID.
	 * Tax Identification
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTaxID (@Nullable java.lang.String TaxID);

	/**
	 * Get Tax ID.
	 * Tax Identification
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTaxID();

	ModelColumn<I_C_BPartner, Object> COLUMN_TaxID = new ModelColumn<>(I_C_BPartner.class, "TaxID", null);
	String COLUMNNAME_TaxID = "TaxID";

	/**
	 * Set Short title.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setTitleShort (@Nullable java.lang.String TitleShort);

	/**
	 * Get Short title.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getTitleShort();

	ModelColumn<I_C_BPartner, Object> COLUMN_TitleShort = new ModelColumn<>(I_C_BPartner.class, "TitleShort", null);
	String COLUMNNAME_TitleShort = "TitleShort";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BPartner, Object> COLUMN_Updated = new ModelColumn<>(I_C_BPartner.class, "Updated", null);
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
	 * Set URL.
	 * Full URL address - e.g. https://www.metasfresh.com
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setURL (@Nullable java.lang.String URL);

	/**
	 * Get URL.
	 * Full URL address - e.g. https://www.metasfresh.com
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getURL();

	ModelColumn<I_C_BPartner, Object> COLUMN_URL = new ModelColumn<>(I_C_BPartner.class, "URL", null);
	String COLUMNNAME_URL = "URL";

	/**
	 * Set URL2.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setURL2 (@Nullable java.lang.String URL2);

	/**
	 * Get URL2.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getURL2();

	ModelColumn<I_C_BPartner, Object> COLUMN_URL2 = new ModelColumn<>(I_C_BPartner.class, "URL2", null);
	String COLUMNNAME_URL2 = "URL2";

	/**
	 * Set URL3.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setURL3 (@Nullable java.lang.String URL3);

	/**
	 * Get URL3.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getURL3();

	ModelColumn<I_C_BPartner, Object> COLUMN_URL3 = new ModelColumn<>(I_C_BPartner.class, "URL3", null);
	String COLUMNNAME_URL3 = "URL3";

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_C_BPartner, Object> COLUMN_Value = new ModelColumn<>(I_C_BPartner.class, "Value", null);
	String COLUMNNAME_Value = "Value";

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

	ModelColumn<I_C_BPartner, Object> COLUMN_VATaxID = new ModelColumn<>(I_C_BPartner.class, "VATaxID", null);
	String COLUMNNAME_VATaxID = "VATaxID";

	/**
	 * Set Vendor Category.
	 * Lieferanten Kategorie
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVendorCategory (@Nullable java.lang.String VendorCategory);

	/**
	 * Get Vendor Category.
	 * Lieferanten Kategorie
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVendorCategory();

	ModelColumn<I_C_BPartner, Object> COLUMN_VendorCategory = new ModelColumn<>(I_C_BPartner.class, "VendorCategory", null);
	String COLUMNNAME_VendorCategory = "VendorCategory";
}
