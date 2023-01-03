package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_Invoice
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Invoice 
{

	String Table_Name = "C_Invoice";

//	/** AD_Table_ID=318 */
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
	 * Set Inputsource.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_InputDataSource_ID (int AD_InputDataSource_ID);

	/**
	 * Get Inputsource.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_InputDataSource_ID();

	ModelColumn<I_C_Invoice, Object> COLUMN_AD_InputDataSource_ID = new ModelColumn<>(I_C_Invoice.class, "AD_InputDataSource_ID", null);
	String COLUMNNAME_AD_InputDataSource_ID = "AD_InputDataSource_ID";

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
	 * Set Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_OrgTrx_ID (int AD_OrgTrx_ID);

	/**
	 * Get Trx Organization.
	 * Performing or initiating organization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_OrgTrx_ID();

	String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

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
	 * Set Beneficiary.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBeneficiary_BPartner_ID (int Beneficiary_BPartner_ID);

	/**
	 * Get Beneficiary.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBeneficiary_BPartner_ID();

	String COLUMNNAME_Beneficiary_BPartner_ID = "Beneficiary_BPartner_ID";

	/**
	 * Set Beneficiary contact.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBeneficiary_Contact_ID (int Beneficiary_Contact_ID);

	/**
	 * Get Beneficiary contact.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBeneficiary_Contact_ID();

	String COLUMNNAME_Beneficiary_Contact_ID = "Beneficiary_Contact_ID";

	/**
	 * Set Beneficiary Address.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBeneficiary_Location_ID (int Beneficiary_Location_ID);

	/**
	 * Get Beneficiary Address.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBeneficiary_Location_ID();

	String COLUMNNAME_Beneficiary_Location_ID = "Beneficiary_Location_ID";

	/**
	 * Set Address.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartnerAddress (@Nullable java.lang.String BPartnerAddress);

	/**
	 * Get Address.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPartnerAddress();

	ModelColumn<I_C_Invoice, Object> COLUMN_BPartnerAddress = new ModelColumn<>(I_C_Invoice.class, "BPartnerAddress", null);
	String COLUMNNAME_BPartnerAddress = "BPartnerAddress";

	/**
	 * Set Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Activity.
	 * Business Activity
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Activity_ID();

	String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

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
	 * Set Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Standort (Address).
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_Value_ID (int C_BPartner_Location_Value_ID);

	/**
	 * Get Standort (Address).
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_Value_ID();

	@Nullable org.compiere.model.I_C_Location getC_BPartner_Location_Value();

	void setC_BPartner_Location_Value(@Nullable org.compiere.model.I_C_Location C_BPartner_Location_Value);

	ModelColumn<I_C_Invoice, org.compiere.model.I_C_Location> COLUMN_C_BPartner_Location_Value_ID = new ModelColumn<>(I_C_Invoice.class, "C_BPartner_Location_Value_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_BPartner_Location_Value_ID = "C_BPartner_Location_Value_ID";

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
	 * Set Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Campaign_ID (int C_Campaign_ID);

	/**
	 * Get Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Campaign_ID();

	@Nullable org.compiere.model.I_C_Campaign getC_Campaign();

	void setC_Campaign(@Nullable org.compiere.model.I_C_Campaign C_Campaign);

	ModelColumn<I_C_Invoice, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_C_Invoice.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Cash Journal Line.
	 * Kassenbuch Zeile
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_CashLine_ID (int C_CashLine_ID);

	/**
	 * Get Cash Journal Line.
	 * Kassenbuch Zeile
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_CashLine_ID();

	@Nullable org.compiere.model.I_C_CashLine getC_CashLine();

	void setC_CashLine(@Nullable org.compiere.model.I_C_CashLine C_CashLine);

	ModelColumn<I_C_Invoice, org.compiere.model.I_C_CashLine> COLUMN_C_CashLine_ID = new ModelColumn<>(I_C_Invoice.class, "C_CashLine_ID", org.compiere.model.I_C_CashLine.class);
	String COLUMNNAME_C_CashLine_ID = "C_CashLine_ID";

	/**
	 * Set Kosten.
	 * Additional document charges
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Kosten.
	 * Additional document charges
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Charge_ID();

	String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Kursart.
	 * Currency Conversion Rate Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ConversionType_ID (int C_ConversionType_ID);

	/**
	 * Get Kursart.
	 * Currency Conversion Rate Type
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ConversionType_ID();

	String COLUMNNAME_C_ConversionType_ID = "C_ConversionType_ID";

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
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Document Type.
	 * Target document type for conversing documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID);

	/**
	 * Get Document Type.
	 * Target document type for conversing documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocTypeTarget_ID();

	String COLUMNNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";

	/**
	 * Set Mahnstufe.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DunningLevel_ID (int C_DunningLevel_ID);

	/**
	 * Get Mahnstufe.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DunningLevel_ID();

	@Nullable org.compiere.model.I_C_DunningLevel getC_DunningLevel();

	void setC_DunningLevel(@Nullable org.compiere.model.I_C_DunningLevel C_DunningLevel);

	ModelColumn<I_C_Invoice, org.compiere.model.I_C_DunningLevel> COLUMN_C_DunningLevel_ID = new ModelColumn<>(I_C_Invoice.class, "C_DunningLevel_ID", org.compiere.model.I_C_DunningLevel.class);
	String COLUMNNAME_C_DunningLevel_ID = "C_DunningLevel_ID";

	/**
	 * Set Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setChargeAmt (@Nullable BigDecimal ChargeAmt);

	/**
	 * Get Gebühr.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getChargeAmt();

	ModelColumn<I_C_Invoice, Object> COLUMN_ChargeAmt = new ModelColumn<>(I_C_Invoice.class, "ChargeAmt", null);
	String COLUMNNAME_ChargeAmt = "ChargeAmt";

	/**
	 * Set Incoterms.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Incoterms_ID (int C_Incoterms_ID);

	/**
	 * Get Incoterms.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Incoterms_ID();

	@Nullable org.compiere.model.I_C_Incoterms getC_Incoterms();

	void setC_Incoterms(@Nullable org.compiere.model.I_C_Incoterms C_Incoterms);

	ModelColumn<I_C_Invoice, org.compiere.model.I_C_Incoterms> COLUMN_C_Incoterms_ID = new ModelColumn<>(I_C_Invoice.class, "C_Incoterms_ID", org.compiere.model.I_C_Incoterms.class);
	String COLUMNNAME_C_Incoterms_ID = "C_Incoterms_ID";

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_ID();

	ModelColumn<I_C_Invoice, Object> COLUMN_C_Invoice_ID = new ModelColumn<>(I_C_Invoice.class, "C_Invoice_ID", null);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Copy From.
	 * Copy From Record
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCopyFrom (@Nullable java.lang.String CopyFrom);

	/**
	 * Get Copy From.
	 * Copy From Record
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCopyFrom();

	ModelColumn<I_C_Invoice, Object> COLUMN_CopyFrom = new ModelColumn<>(I_C_Invoice.class, "CopyFrom", null);
	String COLUMNNAME_CopyFrom = "CopyFrom";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	@Nullable org.compiere.model.I_C_Order getC_Order();

	void setC_Order(@Nullable org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_C_Invoice, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_C_Invoice.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Payment.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Payment_ID (int C_Payment_ID);

	/**
	 * Get Payment.
	 * Payment identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Payment_ID();

	String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/**
	 * Set Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_PaymentTerm_ID();

	String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Set Nachbelastung.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreateAdjustmentCharge (@Nullable java.lang.String CreateAdjustmentCharge);

	/**
	 * Get Nachbelastung.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreateAdjustmentCharge();

	ModelColumn<I_C_Invoice, Object> COLUMN_CreateAdjustmentCharge = new ModelColumn<>(I_C_Invoice.class, "CreateAdjustmentCharge", null);
	String COLUMNNAME_CreateAdjustmentCharge = "CreateAdjustmentCharge";

	/**
	 * Set Erzeuge Gutschrift.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreateCreditMemo (@Nullable java.lang.String CreateCreditMemo);

	/**
	 * Get Erzeuge Gutschrift.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreateCreditMemo();

	ModelColumn<I_C_Invoice, Object> COLUMN_CreateCreditMemo = new ModelColumn<>(I_C_Invoice.class, "CreateCreditMemo", null);
	String COLUMNNAME_CreateCreditMemo = "CreateCreditMemo";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Invoice, Object> COLUMN_Created = new ModelColumn<>(I_C_Invoice.class, "Created", null);
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
	 * Set CreateDta.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreateDta (@Nullable java.lang.String CreateDta);

	/**
	 * Get CreateDta.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreateDta();

	ModelColumn<I_C_Invoice, Object> COLUMN_CreateDta = new ModelColumn<>(I_C_Invoice.class, "CreateDta", null);
	String COLUMNNAME_CreateDta = "CreateDta";

	/**
	 * Set Position(en) kopieren von.
	 * Process which will generate a new document lines based on an existing document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreateFrom (@Nullable java.lang.String CreateFrom);

	/**
	 * Get Position(en) kopieren von.
	 * Process which will generate a new document lines based on an existing document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreateFrom();

	ModelColumn<I_C_Invoice, Object> COLUMN_CreateFrom = new ModelColumn<>(I_C_Invoice.class, "CreateFrom", null);
	String COLUMNNAME_CreateFrom = "CreateFrom";

	/**
	 * Set Gutschrift Grund.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditMemoReason (@Nullable java.lang.String CreditMemoReason);

	/**
	 * Get Gutschrift Grund.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreditMemoReason();

	ModelColumn<I_C_Invoice, Object> COLUMN_CreditMemoReason = new ModelColumn<>(I_C_Invoice.class, "CreditMemoReason", null);
	String COLUMNNAME_CreditMemoReason = "CreditMemoReason";

	/**
	 * Set Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateAcct (java.sql.Timestamp DateAcct);

	/**
	 * Get Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateAcct();

	ModelColumn<I_C_Invoice, Object> COLUMN_DateAcct = new ModelColumn<>(I_C_Invoice.class, "DateAcct", null);
	String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Date.
	 * Date printed on Invoice
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateInvoiced (java.sql.Timestamp DateInvoiced);

	/**
	 * Get Date.
	 * Date printed on Invoice
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateInvoiced();

	ModelColumn<I_C_Invoice, Object> COLUMN_DateInvoiced = new ModelColumn<>(I_C_Invoice.class, "DateInvoiced", null);
	String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/**
	 * Set Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateOrdered (@Nullable java.sql.Timestamp DateOrdered);

	/**
	 * Get Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateOrdered();

	ModelColumn<I_C_Invoice, Object> COLUMN_DateOrdered = new ModelColumn<>(I_C_Invoice.class, "DateOrdered", null);
	String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Date printed.
	 * Date the document was printed.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDatePrinted (@Nullable java.sql.Timestamp DatePrinted);

	/**
	 * Get Date printed.
	 * Date the document was printed.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDatePrinted();

	ModelColumn<I_C_Invoice, Object> COLUMN_DatePrinted = new ModelColumn<>(I_C_Invoice.class, "DatePrinted", null);
	String COLUMNNAME_DatePrinted = "DatePrinted";

	/**
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_C_Invoice, Object> COLUMN_Description = new ModelColumn<>(I_C_Invoice.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set DescriptionBottom.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescriptionBottom (@Nullable java.lang.String DescriptionBottom);

	/**
	 * Get DescriptionBottom.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescriptionBottom();

	ModelColumn<I_C_Invoice, Object> COLUMN_DescriptionBottom = new ModelColumn<>(I_C_Invoice.class, "DescriptionBottom", null);
	String COLUMNNAME_DescriptionBottom = "DescriptionBottom";

	/**
	 * Set Process Batch.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocAction (java.lang.String DocAction);

	/**
	 * Get Process Batch.
	 * The targeted status of the document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocAction();

	ModelColumn<I_C_Invoice, Object> COLUMN_DocAction = new ModelColumn<>(I_C_Invoice.class, "DocAction", null);
	String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Document Base Type.
	 * Logical type of document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setDocBaseType (@Nullable java.lang.String DocBaseType);

	/**
	 * Get Document Base Type.
	 * Logical type of document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getDocBaseType();

	ModelColumn<I_C_Invoice, Object> COLUMN_DocBaseType = new ModelColumn<>(I_C_Invoice.class, "DocBaseType", null);
	String COLUMNNAME_DocBaseType = "DocBaseType";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocStatus();

	ModelColumn<I_C_Invoice, Object> COLUMN_DocStatus = new ModelColumn<>(I_C_Invoice.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocumentNo();

	ModelColumn<I_C_Invoice, Object> COLUMN_DocumentNo = new ModelColumn<>(I_C_Invoice.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

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

	ModelColumn<I_C_Invoice, Object> COLUMN_DunningGrace = new ModelColumn<>(I_C_Invoice.class, "DunningGrace", null);
	String COLUMNNAME_DunningGrace = "DunningGrace";

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

	ModelColumn<I_C_Invoice, Object> COLUMN_EMail = new ModelColumn<>(I_C_Invoice.class, "EMail", null);
	String COLUMNNAME_EMail = "EMail";

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

	ModelColumn<I_C_Invoice, Object> COLUMN_ExternalId = new ModelColumn<>(I_C_Invoice.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set Generate To.
	 * Generate To
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGenerateTo (@Nullable java.lang.String GenerateTo);

	/**
	 * Get Generate To.
	 * Generate To
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGenerateTo();

	ModelColumn<I_C_Invoice, Object> COLUMN_GenerateTo = new ModelColumn<>(I_C_Invoice.class, "GenerateTo", null);
	String COLUMNNAME_GenerateTo = "GenerateTo";

	/**
	 * Set Grand Total.
	 * Total amount of document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGrandTotal (BigDecimal GrandTotal);

	/**
	 * Get Grand Total.
	 * Total amount of document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getGrandTotal();

	ModelColumn<I_C_Invoice, Object> COLUMN_GrandTotal = new ModelColumn<>(I_C_Invoice.class, "GrandTotal", null);
	String COLUMNNAME_GrandTotal = "GrandTotal";

	/**
	 * Set IncotermLocation.
	 * Anzugebender Ort für Handelsklausel
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIncotermLocation (@Nullable java.lang.String IncotermLocation);

	/**
	 * Get IncotermLocation.
	 * Anzugebender Ort für Handelsklausel
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIncotermLocation();

	ModelColumn<I_C_Invoice, Object> COLUMN_IncotermLocation = new ModelColumn<>(I_C_Invoice.class, "IncotermLocation", null);
	String COLUMNNAME_IncotermLocation = "IncotermLocation";

	/**
	 * Set Inkasso-Status.
	 * Invoice Collection Status
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoiceCollectionType (@Nullable java.lang.String InvoiceCollectionType);

	/**
	 * Get Inkasso-Status.
	 * Invoice Collection Status
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInvoiceCollectionType();

	ModelColumn<I_C_Invoice, Object> COLUMN_InvoiceCollectionType = new ModelColumn<>(I_C_Invoice.class, "InvoiceCollectionType", null);
	String COLUMNNAME_InvoiceCollectionType = "InvoiceCollectionType";

	/**
	 * Set Invoice_includedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoice_includedTab (@Nullable java.lang.String Invoice_includedTab);

	/**
	 * Get Invoice_includedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInvoice_includedTab();

	ModelColumn<I_C_Invoice, Object> COLUMN_Invoice_includedTab = new ModelColumn<>(I_C_Invoice.class, "Invoice_includedTab", null);
	String COLUMNNAME_Invoice_includedTab = "Invoice_includedTab";

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

	ModelColumn<I_C_Invoice, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Invoice.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsApproved (boolean IsApproved);

	/**
	 * Get Approved.
	 * Indicates if this document requires approval
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isApproved();

	ModelColumn<I_C_Invoice, Object> COLUMN_IsApproved = new ModelColumn<>(I_C_Invoice.class, "IsApproved", null);
	String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Rabatte drucken.
	 * Print Discount on Invoice and Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDiscountPrinted (boolean IsDiscountPrinted);

	/**
	 * Get Rabatte drucken.
	 * Print Discount on Invoice and Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDiscountPrinted();

	ModelColumn<I_C_Invoice, Object> COLUMN_IsDiscountPrinted = new ModelColumn<>(I_C_Invoice.class, "IsDiscountPrinted", null);
	String COLUMNNAME_IsDiscountPrinted = "IsDiscountPrinted";

	/**
	 * Set In Dispute.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInDispute (boolean IsInDispute);

	/**
	 * Get In Dispute.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInDispute();

	ModelColumn<I_C_Invoice, Object> COLUMN_IsInDispute = new ModelColumn<>(I_C_Invoice.class, "IsInDispute", null);
	String COLUMNNAME_IsInDispute = "IsInDispute";

	/**
	 * Set Gezahlt.
	 * The document is paid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPaid (boolean IsPaid);

	/**
	 * Get Gezahlt.
	 * The document is paid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPaid();

	ModelColumn<I_C_Invoice, Object> COLUMN_IsPaid = new ModelColumn<>(I_C_Invoice.class, "IsPaid", null);
	String COLUMNNAME_IsPaid = "IsPaid";

	/**
	 * Set Pay Schedule valid.
	 * Is the Payment Schedule is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPayScheduleValid (boolean IsPayScheduleValid);

	/**
	 * Get Pay Schedule valid.
	 * Is the Payment Schedule is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPayScheduleValid();

	ModelColumn<I_C_Invoice, Object> COLUMN_IsPayScheduleValid = new ModelColumn<>(I_C_Invoice.class, "IsPayScheduleValid", null);
	String COLUMNNAME_IsPayScheduleValid = "IsPayScheduleValid";

	/**
	 * Set andrucken.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPrinted (boolean IsPrinted);

	/**
	 * Get andrucken.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPrinted();

	ModelColumn<I_C_Invoice, Object> COLUMN_IsPrinted = new ModelColumn<>(I_C_Invoice.class, "IsPrinted", null);
	String COLUMNNAME_IsPrinted = "IsPrinted";

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

	ModelColumn<I_C_Invoice, Object> COLUMN_IsSalesPartnerRequired = new ModelColumn<>(I_C_Invoice.class, "IsSalesPartnerRequired", null);
	String COLUMNNAME_IsSalesPartnerRequired = "IsSalesPartnerRequired";

	/**
	 * Set Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSelfService (boolean IsSelfService);

	/**
	 * Get Selbstbedienung.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSelfService();

	ModelColumn<I_C_Invoice, Object> COLUMN_IsSelfService = new ModelColumn<>(I_C_Invoice.class, "IsSelfService", null);
	String COLUMNNAME_IsSelfService = "IsSelfService";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSOTrx();

	ModelColumn<I_C_Invoice, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_C_Invoice.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Price incl. Tax.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTaxIncluded (boolean IsTaxIncluded);

	/**
	 * Get Price incl. Tax.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTaxIncluded();

	ModelColumn<I_C_Invoice, Object> COLUMN_IsTaxIncluded = new ModelColumn<>(I_C_Invoice.class, "IsTaxIncluded", null);
	String COLUMNNAME_IsTaxIncluded = "IsTaxIncluded";

	/**
	 * Set Transfer to General Ledger.
	 * Indicates whether the transactions associated with this document are transferred to the General Ledger.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTransferred (boolean IsTransferred);

	/**
	 * Get Transfer to General Ledger.
	 * Indicates whether the transactions associated with this document are transferred to the General Ledger.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTransferred();

	ModelColumn<I_C_Invoice, Object> COLUMN_IsTransferred = new ModelColumn<>(I_C_Invoice.class, "IsTransferred", null);
	String COLUMNNAME_IsTransferred = "IsTransferred";

	/**
	 * Set Benutze abw. Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsUseBPartnerAddress (boolean IsUseBPartnerAddress);

	/**
	 * Get Benutze abw. Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUseBPartnerAddress();

	ModelColumn<I_C_Invoice, Object> COLUMN_IsUseBPartnerAddress = new ModelColumn<>(I_C_Invoice.class, "IsUseBPartnerAddress", null);
	String COLUMNNAME_IsUseBPartnerAddress = "IsUseBPartnerAddress";

	/**
	 * Set Price List.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Price List.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_PriceList_ID();

	String COLUMNNAME_M_PriceList_ID = "M_PriceList_ID";

	/**
	 * Set Warenrücksendung - Freigabe (RMA).
	 * Return Material Authorization
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_RMA_ID (int M_RMA_ID);

	/**
	 * Get Warenrücksendung - Freigabe (RMA).
	 * Return Material Authorization
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_RMA_ID();

	@Nullable org.compiere.model.I_M_RMA getM_RMA();

	void setM_RMA(@Nullable org.compiere.model.I_M_RMA M_RMA);

	ModelColumn<I_C_Invoice, org.compiere.model.I_M_RMA> COLUMN_M_RMA_ID = new ModelColumn<>(I_C_Invoice.class, "M_RMA_ID", org.compiere.model.I_M_RMA.class);
	String COLUMNNAME_M_RMA_ID = "M_RMA_ID";

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
	 * Set Payment Rule.
	 * How you pay the invoice
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPaymentRule (java.lang.String PaymentRule);

	/**
	 * Get Payment Rule.
	 * How you pay the invoice
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPaymentRule();

	ModelColumn<I_C_Invoice, Object> COLUMN_PaymentRule = new ModelColumn<>(I_C_Invoice.class, "PaymentRule", null);
	String COLUMNNAME_PaymentRule = "PaymentRule";

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

	ModelColumn<I_C_Invoice, Object> COLUMN_POReference = new ModelColumn<>(I_C_Invoice.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Posting status.
	 * Posting status
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPosted (boolean Posted);

	/**
	 * Get Posting status.
	 * Posting status
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPosted();

	ModelColumn<I_C_Invoice, Object> COLUMN_Posted = new ModelColumn<>(I_C_Invoice.class, "Posted", null);
	String COLUMNNAME_Posted = "Posted";

	/**
	 * Set Posting Error.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostingError_Issue_ID (int PostingError_Issue_ID);

	/**
	 * Get Posting Error.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPostingError_Issue_ID();

	String COLUMNNAME_PostingError_Issue_ID = "PostingError_Issue_ID";

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

	ModelColumn<I_C_Invoice, Object> COLUMN_Processed = new ModelColumn<>(I_C_Invoice.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessing();

	ModelColumn<I_C_Invoice, Object> COLUMN_Processing = new ModelColumn<>(I_C_Invoice.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Referenced Invoice.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRef_Invoice_ID (int Ref_Invoice_ID);

	/**
	 * Get Referenced Invoice.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRef_Invoice_ID();

	@Nullable org.compiere.model.I_C_Invoice getRef_Invoice();

	void setRef_Invoice(@Nullable org.compiere.model.I_C_Invoice Ref_Invoice);

	ModelColumn<I_C_Invoice, org.compiere.model.I_C_Invoice> COLUMN_Ref_Invoice_ID = new ModelColumn<>(I_C_Invoice.class, "Ref_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_Ref_Invoice_ID = "Ref_Invoice_ID";

	/**
	 * Set Reversal ID.
	 * ID of document reversal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReversal_ID (int Reversal_ID);

	/**
	 * Get Reversal ID.
	 * ID of document reversal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getReversal_ID();

	@Nullable org.compiere.model.I_C_Invoice getReversal();

	void setReversal(@Nullable org.compiere.model.I_C_Invoice Reversal);

	ModelColumn<I_C_Invoice, org.compiere.model.I_C_Invoice> COLUMN_Reversal_ID = new ModelColumn<>(I_C_Invoice.class, "Reversal_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_Reversal_ID = "Reversal_ID";

	/**
	 * Set Debtor invoice count.
	 * Number of associated debtor invoices
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setSales_Invoice_Count (int Sales_Invoice_Count);

	/**
	 * Get Debtor invoice count.
	 * Number of associated debtor invoices
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	int getSales_Invoice_Count();

	ModelColumn<I_C_Invoice, Object> COLUMN_Sales_Invoice_Count = new ModelColumn<>(I_C_Invoice.class, "Sales_Invoice_Count", null);
	String COLUMNNAME_Sales_Invoice_Count = "Sales_Invoice_Count";

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

	ModelColumn<I_C_Invoice, Object> COLUMN_SalesPartnerCode = new ModelColumn<>(I_C_Invoice.class, "SalesPartnerCode", null);
	String COLUMNNAME_SalesPartnerCode = "SalesPartnerCode";

	/**
	 * Set Account manager.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Account manager.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSalesRep_ID();

	String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

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

	ModelColumn<I_C_Invoice, Object> COLUMN_SendEMail = new ModelColumn<>(I_C_Invoice.class, "SendEMail", null);
	String COLUMNNAME_SendEMail = "SendEMail";

	/**
	 * Set Summe Zeilen.
	 * Total of all document lines
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTotalLines (BigDecimal TotalLines);

	/**
	 * Get Summe Zeilen.
	 * Total of all document lines
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotalLines();

	ModelColumn<I_C_Invoice, Object> COLUMN_TotalLines = new ModelColumn<>(I_C_Invoice.class, "TotalLines", null);
	String COLUMNNAME_TotalLines = "TotalLines";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Invoice, Object> COLUMN_Updated = new ModelColumn<>(I_C_Invoice.class, "Updated", null);
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
	 * Set User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser1_ID (int User1_ID);

	/**
	 * Get User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser1_ID();

	@Nullable org.compiere.model.I_C_ElementValue getUser1();

	void setUser1(@Nullable org.compiere.model.I_C_ElementValue User1);

	ModelColumn<I_C_Invoice, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new ModelColumn<>(I_C_Invoice.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_User1_ID = "User1_ID";

	/**
	 * Set User 2.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser2_ID (int User2_ID);

	/**
	 * Get User 2.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser2_ID();

	@Nullable org.compiere.model.I_C_ElementValue getUser2();

	void setUser2(@Nullable org.compiere.model.I_C_ElementValue User2);

	ModelColumn<I_C_Invoice, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new ModelColumn<>(I_C_Invoice.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_User2_ID = "User2_ID";

	/**
	 * Set UserFlag.
	 * Can be used to flag records and thus make them selectable from the UI via advanced search.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserFlag (@Nullable java.lang.String UserFlag);

	/**
	 * Get UserFlag.
	 * Can be used to flag records and thus make them selectable from the UI via advanced search.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserFlag();

	ModelColumn<I_C_Invoice, Object> COLUMN_UserFlag = new ModelColumn<>(I_C_Invoice.class, "UserFlag", null);
	String COLUMNNAME_UserFlag = "UserFlag";
}
