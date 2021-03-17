package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Order
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Order 
{

	String Table_Name = "C_Order";

//	/** AD_Table_ID=259 */
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
	 * Set AmountRefunded.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmountRefunded (@Nullable BigDecimal AmountRefunded);

	/**
	 * Get AmountRefunded.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmountRefunded();

	ModelColumn<I_C_Order, Object> COLUMN_AmountRefunded = new ModelColumn<>(I_C_Order.class, "AmountRefunded", null);
	String COLUMNNAME_AmountRefunded = "AmountRefunded";

	/**
	 * Set AmountTendered.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmountTendered (@Nullable BigDecimal AmountTendered);

	/**
	 * Get AmountTendered.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmountTendered();

	ModelColumn<I_C_Order, Object> COLUMN_AmountTendered = new ModelColumn<>(I_C_Order.class, "AmountTendered", null);
	String COLUMNNAME_AmountTendered = "AmountTendered";

	/**
	 * Set Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_BPartner_ID();

	String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Set Invoice Partner Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setBill_BPartner_Memo (@Nullable java.lang.String Bill_BPartner_Memo);

	/**
	 * Get Invoice Partner Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getBill_BPartner_Memo();

	ModelColumn<I_C_Order, Object> COLUMN_Bill_BPartner_Memo = new ModelColumn<>(I_C_Order.class, "Bill_BPartner_Memo", null);
	String COLUMNNAME_Bill_BPartner_Memo = "Bill_BPartner_Memo";

	/**
	 * Set Bill Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_Location_ID (int Bill_Location_ID);

	/**
	 * Get Bill Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_Location_ID();

	String COLUMNNAME_Bill_Location_ID = "Bill_Location_ID";

	/**
	 * Set Bill Contact.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_User_ID (int Bill_User_ID);

	/**
	 * Get Bill Contact.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_User_ID();

	String COLUMNNAME_Bill_User_ID = "Bill_User_ID";

	/**
	 * Set Abw. Rechnungsadresse.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBillToAddress (@Nullable java.lang.String BillToAddress);

	/**
	 * Get Abw. Rechnungsadresse.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBillToAddress();

	ModelColumn<I_C_Order, Object> COLUMN_BillToAddress = new ModelColumn<>(I_C_Order.class, "BillToAddress", null);
	String COLUMNNAME_BillToAddress = "BillToAddress";

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

	ModelColumn<I_C_Order, Object> COLUMN_BPartnerAddress = new ModelColumn<>(I_C_Order.class, "BPartnerAddress", null);
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
	 * Set Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BP_BankAccount_ID();

	String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

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
	 * Set Businesspartner-Memo.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_BPartner_Memo (@Nullable java.lang.String C_BPartner_Memo);

	/**
	 * Get Businesspartner-Memo.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getC_BPartner_Memo();

	ModelColumn<I_C_Order, Object> COLUMN_C_BPartner_Memo = new ModelColumn<>(I_C_Order.class, "C_BPartner_Memo", null);
	String COLUMNNAME_C_BPartner_Memo = "C_BPartner_Memo";

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

	ModelColumn<I_C_Order, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_C_Order.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Cash Journal Line.
	 * Cash Journal Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_CashLine_ID (int C_CashLine_ID);

	/**
	 * Get Cash Journal Line.
	 * Cash Journal Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_CashLine_ID();

	@Nullable org.compiere.model.I_C_CashLine getC_CashLine();

	void setC_CashLine(@Nullable org.compiere.model.I_C_CashLine C_CashLine);

	ModelColumn<I_C_Order, org.compiere.model.I_C_CashLine> COLUMN_C_CashLine_ID = new ModelColumn<>(I_C_Order.class, "C_CashLine_ID", org.compiere.model.I_C_CashLine.class);
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
	 * Set Zielbelegart.
	 * Target document type for conversing documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID);

	/**
	 * Get Zielbelegart.
	 * Target document type for conversing documents
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocTypeTarget_ID();

	String COLUMNNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	ModelColumn<I_C_Order, Object> COLUMN_C_Order_ID = new ModelColumn<>(I_C_Order.class, "C_Order_ID", null);
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
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_PaymentTerm_ID();

	String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/**
	 * Set POS-Terminal.
	 * Point of Sales Terminal
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_POS_ID (int C_POS_ID);

	/**
	 * Get POS-Terminal.
	 * Point of Sales Terminal
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_POS_ID();

	@Nullable org.compiere.model.I_C_POS getC_POS();

	void setC_POS(@Nullable org.compiere.model.I_C_POS C_POS);

	ModelColumn<I_C_Order, org.compiere.model.I_C_POS> COLUMN_C_POS_ID = new ModelColumn<>(I_C_Order.class, "C_POS_ID", org.compiere.model.I_C_POS.class);
	String COLUMNNAME_C_POS_ID = "C_POS_ID";

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

	ModelColumn<I_C_Order, Object> COLUMN_ChargeAmt = new ModelColumn<>(I_C_Order.class, "ChargeAmt", null);
	String COLUMNNAME_ChargeAmt = "ChargeAmt";

	/**
	 * Set Rabatt %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCompleteOrderDiscount (@Nullable BigDecimal CompleteOrderDiscount);

	/**
	 * Get Rabatt %.
	 * Abschlag in Prozent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCompleteOrderDiscount();

	ModelColumn<I_C_Order, Object> COLUMN_CompleteOrderDiscount = new ModelColumn<>(I_C_Order.class, "CompleteOrderDiscount", null);
	String COLUMNNAME_CompleteOrderDiscount = "CompleteOrderDiscount";

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

	ModelColumn<I_C_Order, Object> COLUMN_CopyFrom = new ModelColumn<>(I_C_Order.class, "CopyFrom", null);
	String COLUMNNAME_CopyFrom = "CopyFrom";

	/**
	 * Set Create Copy.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreateCopy (@Nullable java.lang.String CreateCopy);

	/**
	 * Get Create Copy.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreateCopy();

	ModelColumn<I_C_Order, Object> COLUMN_CreateCopy = new ModelColumn<>(I_C_Order.class, "CreateCopy", null);
	String COLUMNNAME_CreateCopy = "CreateCopy";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Order, Object> COLUMN_Created = new ModelColumn<>(I_C_Order.class, "Created", null);
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
	 * Set Erzeuge Auftrag.
	 * Erzeugt aus dem bestehenden Angebot einen neuen Auftrag
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreateNewFromProposal (@Nullable java.lang.String CreateNewFromProposal);

	/**
	 * Get Erzeuge Auftrag.
	 * Erzeugt aus dem bestehenden Angebot einen neuen Auftrag
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreateNewFromProposal();

	ModelColumn<I_C_Order, Object> COLUMN_CreateNewFromProposal = new ModelColumn<>(I_C_Order.class, "CreateNewFromProposal", null);
	String COLUMNNAME_CreateNewFromProposal = "CreateNewFromProposal";

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

	ModelColumn<I_C_Order, Object> COLUMN_DateAcct = new ModelColumn<>(I_C_Order.class, "DateAcct", null);
	String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateOrdered (java.sql.Timestamp DateOrdered);

	/**
	 * Get Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateOrdered();

	ModelColumn<I_C_Order, Object> COLUMN_DateOrdered = new ModelColumn<>(I_C_Order.class, "DateOrdered", null);
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

	ModelColumn<I_C_Order, Object> COLUMN_DatePrinted = new ModelColumn<>(I_C_Order.class, "DatePrinted", null);
	String COLUMNNAME_DatePrinted = "DatePrinted";

	/**
	 * Set Zugesagter Termin.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDatePromised (java.sql.Timestamp DatePromised);

	/**
	 * Get Zugesagter Termin.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDatePromised();

	ModelColumn<I_C_Order, Object> COLUMN_DatePromised = new ModelColumn<>(I_C_Order.class, "DatePromised", null);
	String COLUMNNAME_DatePromised = "DatePromised";

	/**
	 * Set Delivery info.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryInfo (@Nullable java.lang.String DeliveryInfo);

	/**
	 * Get Delivery info.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDeliveryInfo();

	ModelColumn<I_C_Order, Object> COLUMN_DeliveryInfo = new ModelColumn<>(I_C_Order.class, "DeliveryInfo", null);
	String COLUMNNAME_DeliveryInfo = "DeliveryInfo";

	/**
	 * Set Lieferart.
	 * Defines the timing of Delivery
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDeliveryRule (java.lang.String DeliveryRule);

	/**
	 * Get Lieferart.
	 * Defines the timing of Delivery
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDeliveryRule();

	ModelColumn<I_C_Order, Object> COLUMN_DeliveryRule = new ModelColumn<>(I_C_Order.class, "DeliveryRule", null);
	String COLUMNNAME_DeliveryRule = "DeliveryRule";

	/**
	 * Set Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDeliveryViaRule (java.lang.String DeliveryViaRule);

	/**
	 * Get Lieferung.
	 * Wie der Auftrag geliefert wird
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDeliveryViaRule();

	ModelColumn<I_C_Order, Object> COLUMN_DeliveryViaRule = new ModelColumn<>(I_C_Order.class, "DeliveryViaRule", null);
	String COLUMNNAME_DeliveryViaRule = "DeliveryViaRule";

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

	ModelColumn<I_C_Order, Object> COLUMN_Description = new ModelColumn<>(I_C_Order.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Schlusstext.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescriptionBottom (@Nullable java.lang.String DescriptionBottom);

	/**
	 * Get Schlusstext.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescriptionBottom();

	ModelColumn<I_C_Order, Object> COLUMN_DescriptionBottom = new ModelColumn<>(I_C_Order.class, "DescriptionBottom", null);
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

	ModelColumn<I_C_Order, Object> COLUMN_DocAction = new ModelColumn<>(I_C_Order.class, "DocAction", null);
	String COLUMNNAME_DocAction = "DocAction";

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

	ModelColumn<I_C_Order, Object> COLUMN_DocStatus = new ModelColumn<>(I_C_Order.class, "DocStatus", null);
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

	ModelColumn<I_C_Order, Object> COLUMN_DocumentNo = new ModelColumn<>(I_C_Order.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Ship Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropShip_BPartner_ID (int DropShip_BPartner_ID);

	/**
	 * Get Ship Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropShip_BPartner_ID();

	String COLUMNNAME_DropShip_BPartner_ID = "DropShip_BPartner_ID";

	/**
	 * Set Drop Shipment Partner Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setDropShip_BPartner_Memo (@Nullable java.lang.String DropShip_BPartner_Memo);

	/**
	 * Get Drop Shipment Partner Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getDropShip_BPartner_Memo();

	ModelColumn<I_C_Order, Object> COLUMN_DropShip_BPartner_Memo = new ModelColumn<>(I_C_Order.class, "DropShip_BPartner_Memo", null);
	String COLUMNNAME_DropShip_BPartner_Memo = "DropShip_BPartner_Memo";

	/**
	 * Set Ship Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropShip_Location_ID (int DropShip_Location_ID);

	/**
	 * Get Ship Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropShip_Location_ID();

	String COLUMNNAME_DropShip_Location_ID = "DropShip_Location_ID";

	/**
	 * Set Ship Contact.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropShip_User_ID (int DropShip_User_ID);

	/**
	 * Get Ship Contact.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropShip_User_ID();

	String COLUMNNAME_DropShip_User_ID = "DropShip_User_ID";

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

	ModelColumn<I_C_Order, Object> COLUMN_ExternalId = new ModelColumn<>(I_C_Order.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set Frachtbetrag.
	 * Freight Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFreightAmt (BigDecimal FreightAmt);

	/**
	 * Get Frachtbetrag.
	 * Freight Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getFreightAmt();

	ModelColumn<I_C_Order, Object> COLUMN_FreightAmt = new ModelColumn<>(I_C_Order.class, "FreightAmt", null);
	String COLUMNNAME_FreightAmt = "FreightAmt";

	/**
	 * Set Frachtkostenberechnung.
	 * Methode zur Frachtkostenberechnung
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFreightCostRule (java.lang.String FreightCostRule);

	/**
	 * Get Frachtkostenberechnung.
	 * Methode zur Frachtkostenberechnung
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getFreightCostRule();

	ModelColumn<I_C_Order, Object> COLUMN_FreightCostRule = new ModelColumn<>(I_C_Order.class, "FreightCostRule", null);
	String COLUMNNAME_FreightCostRule = "FreightCostRule";

	/**
	 * Set Summe Gesamt.
	 * Total amount of document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGrandTotal (BigDecimal GrandTotal);

	/**
	 * Get Summe Gesamt.
	 * Total amount of document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getGrandTotal();

	ModelColumn<I_C_Order, Object> COLUMN_GrandTotal = new ModelColumn<>(I_C_Order.class, "GrandTotal", null);
	String COLUMNNAME_GrandTotal = "GrandTotal";

	/**
	 * Set Übergabe-Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setHandOver_BPartner_Memo (@Nullable java.lang.String HandOver_BPartner_Memo);

	/**
	 * Get Übergabe-Memo.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getHandOver_BPartner_Memo();

	ModelColumn<I_C_Order, Object> COLUMN_HandOver_BPartner_Memo = new ModelColumn<>(I_C_Order.class, "HandOver_BPartner_Memo", null);
	String COLUMNNAME_HandOver_BPartner_Memo = "HandOver_BPartner_Memo";

	/**
	 * Set Handover contact.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHandOver_User_ID (int HandOver_User_ID);

	/**
	 * Get Handover contact.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getHandOver_User_ID();

	String COLUMNNAME_HandOver_User_ID = "HandOver_User_ID";

	/**
	 * Set Übergabe adresse.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHandOverAddress (@Nullable java.lang.String HandOverAddress);

	/**
	 * Get Übergabe adresse.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHandOverAddress();

	ModelColumn<I_C_Order, Object> COLUMN_HandOverAddress = new ModelColumn<>(I_C_Order.class, "HandOverAddress", null);
	String COLUMNNAME_HandOverAddress = "HandOverAddress";

	/**
	 * Set Incoterm.
	 * Internationale Handelsklauseln (engl. International Commercial Terms)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIncoterm (@Nullable java.lang.String Incoterm);

	/**
	 * Get Incoterm.
	 * Internationale Handelsklauseln (engl. International Commercial Terms)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIncoterm();

	ModelColumn<I_C_Order, Object> COLUMN_Incoterm = new ModelColumn<>(I_C_Order.class, "Incoterm", null);
	String COLUMNNAME_Incoterm = "Incoterm";

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

	ModelColumn<I_C_Order, Object> COLUMN_IncotermLocation = new ModelColumn<>(I_C_Order.class, "IncotermLocation", null);
	String COLUMNNAME_IncotermLocation = "IncotermLocation";

	/**
	 * Set Rechnungsstellung.
	 * "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoiceRule (java.lang.String InvoiceRule);

	/**
	 * Get Rechnungsstellung.
	 * "Rechnungsstellung" definiert, wie oft und in welcher Form ein Geschäftspartner Rechnungen erhält.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getInvoiceRule();

	ModelColumn<I_C_Order, Object> COLUMN_InvoiceRule = new ModelColumn<>(I_C_Order.class, "InvoiceRule", null);
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

	ModelColumn<I_C_Order, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Order.class, "IsActive", null);
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

	ModelColumn<I_C_Order, Object> COLUMN_IsApproved = new ModelColumn<>(I_C_Order.class, "IsApproved", null);
	String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Kredit gebilligt.
	 * Credit  has been approved
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCreditApproved (boolean IsCreditApproved);

	/**
	 * Get Kredit gebilligt.
	 * Credit  has been approved
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCreditApproved();

	ModelColumn<I_C_Order, Object> COLUMN_IsCreditApproved = new ModelColumn<>(I_C_Order.class, "IsCreditApproved", null);
	String COLUMNNAME_IsCreditApproved = "IsCreditApproved";

	/**
	 * Set Zugestellt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDelivered (boolean IsDelivered);

	/**
	 * Get Zugestellt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDelivered();

	ModelColumn<I_C_Order, Object> COLUMN_IsDelivered = new ModelColumn<>(I_C_Order.class, "IsDelivered", null);
	String COLUMNNAME_IsDelivered = "IsDelivered";

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

	ModelColumn<I_C_Order, Object> COLUMN_IsDiscountPrinted = new ModelColumn<>(I_C_Order.class, "IsDiscountPrinted", null);
	String COLUMNNAME_IsDiscountPrinted = "IsDiscountPrinted";

	/**
	 * Set Streckengeschäft.
	 * Drop Shipments are sent from the Vendor directly to the Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDropShip (boolean IsDropShip);

	/**
	 * Get Streckengeschäft.
	 * Drop Shipments are sent from the Vendor directly to the Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDropShip();

	ModelColumn<I_C_Order, Object> COLUMN_IsDropShip = new ModelColumn<>(I_C_Order.class, "IsDropShip", null);
	String COLUMNNAME_IsDropShip = "IsDropShip";

	/**
	 * Set Berechnete Menge.
	 * Is this invoiced?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInvoiced (boolean IsInvoiced);

	/**
	 * Get Berechnete Menge.
	 * Is this invoiced?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInvoiced();

	ModelColumn<I_C_Order, Object> COLUMN_IsInvoiced = new ModelColumn<>(I_C_Order.class, "IsInvoiced", null);
	String COLUMNNAME_IsInvoiced = "IsInvoiced";

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

	ModelColumn<I_C_Order, Object> COLUMN_IsPrinted = new ModelColumn<>(I_C_Order.class, "IsPrinted", null);
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

	ModelColumn<I_C_Order, Object> COLUMN_IsSalesPartnerRequired = new ModelColumn<>(I_C_Order.class, "IsSalesPartnerRequired", null);
	String COLUMNNAME_IsSalesPartnerRequired = "IsSalesPartnerRequired";

	/**
	 * Set Selektiert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSelected (boolean IsSelected);

	/**
	 * Get Selektiert.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSelected();

	ModelColumn<I_C_Order, Object> COLUMN_IsSelected = new ModelColumn<>(I_C_Order.class, "IsSelected", null);
	String COLUMNNAME_IsSelected = "IsSelected";

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

	ModelColumn<I_C_Order, Object> COLUMN_IsSelfService = new ModelColumn<>(I_C_Order.class, "IsSelfService", null);
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

	ModelColumn<I_C_Order, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_C_Order.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Preis inklusive Steuern.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTaxIncluded (boolean IsTaxIncluded);

	/**
	 * Get Preis inklusive Steuern.
	 * Tax is included in the price
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTaxIncluded();

	ModelColumn<I_C_Order, Object> COLUMN_IsTaxIncluded = new ModelColumn<>(I_C_Order.class, "IsTaxIncluded", null);
	String COLUMNNAME_IsTaxIncluded = "IsTaxIncluded";

	/**
	 * Set Transferred.
	 * Transferred to General Ledger (i.e. accounted)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTransferred (boolean IsTransferred);

	/**
	 * Get Transferred.
	 * Transferred to General Ledger (i.e. accounted)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTransferred();

	ModelColumn<I_C_Order, Object> COLUMN_IsTransferred = new ModelColumn<>(I_C_Order.class, "IsTransferred", null);
	String COLUMNNAME_IsTransferred = "IsTransferred";

	/**
	 * Set Benutze abw. Rechnungsadresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsUseBillToAddress (boolean IsUseBillToAddress);

	/**
	 * Get Benutze abw. Rechnungsadresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUseBillToAddress();

	ModelColumn<I_C_Order, Object> COLUMN_IsUseBillToAddress = new ModelColumn<>(I_C_Order.class, "IsUseBillToAddress", null);
	String COLUMNNAME_IsUseBillToAddress = "IsUseBillToAddress";

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

	ModelColumn<I_C_Order, Object> COLUMN_IsUseBPartnerAddress = new ModelColumn<>(I_C_Order.class, "IsUseBPartnerAddress", null);
	String COLUMNNAME_IsUseBPartnerAddress = "IsUseBPartnerAddress";

	/**
	 * Set Zugehörige Bestellung.
	 * Mit diesem Feld kann ein Auftrag die ihm zugehörige Bestellung referenzieren.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLink_Order_ID (int Link_Order_ID);

	/**
	 * Get Zugehörige Bestellung.
	 * Mit diesem Feld kann ein Auftrag die ihm zugehörige Bestellung referenzieren.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLink_Order_ID();

	@Nullable org.compiere.model.I_C_Order getLink_Order();

	void setLink_Order(@Nullable org.compiere.model.I_C_Order Link_Order);

	ModelColumn<I_C_Order, org.compiere.model.I_C_Order> COLUMN_Link_Order_ID = new ModelColumn<>(I_C_Order.class, "Link_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_Link_Order_ID = "Link_Order_ID";

	/**
	 * Set Fracht-Kategorie.
	 * Category of the Freight
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_FreightCategory_ID (int M_FreightCategory_ID);

	/**
	 * Get Fracht-Kategorie.
	 * Category of the Freight
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_FreightCategory_ID();

	@Nullable org.compiere.model.I_M_FreightCategory getM_FreightCategory();

	void setM_FreightCategory(@Nullable org.compiere.model.I_M_FreightCategory M_FreightCategory);

	ModelColumn<I_C_Order, org.compiere.model.I_M_FreightCategory> COLUMN_M_FreightCategory_ID = new ModelColumn<>(I_C_Order.class, "M_FreightCategory_ID", org.compiere.model.I_M_FreightCategory.class);
	String COLUMNNAME_M_FreightCategory_ID = "M_FreightCategory_ID";

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
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_PricingSystem_ID (int M_PricingSystem_ID);

	/**
	 * Get Pricing System.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_PricingSystem_ID();

	String COLUMNNAME_M_PricingSystem_ID = "M_PricingSystem_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

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

	ModelColumn<I_C_Order, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_C_Order.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Tour.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Tour_ID (int M_Tour_ID);

	/**
	 * Get Tour.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Tour_ID();

	ModelColumn<I_C_Order, Object> COLUMN_M_Tour_ID = new ModelColumn<>(I_C_Order.class, "M_Tour_ID", null);
	String COLUMNNAME_M_Tour_ID = "M_Tour_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Orderline_includedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrderline_includedTab (@Nullable java.lang.String Orderline_includedTab);

	/**
	 * Get Orderline_includedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOrderline_includedTab();

	ModelColumn<I_C_Order, Object> COLUMN_Orderline_includedTab = new ModelColumn<>(I_C_Order.class, "Orderline_includedTab", null);
	String COLUMNNAME_Orderline_includedTab = "Orderline_includedTab";

	/**
	 * Set OrderType.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrderType (@Nullable java.lang.String OrderType);

	/**
	 * Get OrderType.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOrderType();

	ModelColumn<I_C_Order, Object> COLUMN_OrderType = new ModelColumn<>(I_C_Order.class, "OrderType", null);
	String COLUMNNAME_OrderType = "OrderType";

	/**
	 * Set Payment BPartner.
	 * Business Partner responsible for the payment
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPay_BPartner_ID (int Pay_BPartner_ID);

	/**
	 * Get Payment BPartner.
	 * Business Partner responsible for the payment
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPay_BPartner_ID();

	ModelColumn<I_C_Order, Object> COLUMN_Pay_BPartner_ID = new ModelColumn<>(I_C_Order.class, "Pay_BPartner_ID", null);
	String COLUMNNAME_Pay_BPartner_ID = "Pay_BPartner_ID";

	/**
	 * Set Payment Location.
	 * Location of the Business Partner responsible for the payment
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPay_Location_ID (int Pay_Location_ID);

	/**
	 * Get Payment Location.
	 * Location of the Business Partner responsible for the payment
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPay_Location_ID();

	ModelColumn<I_C_Order, Object> COLUMN_Pay_Location_ID = new ModelColumn<>(I_C_Order.class, "Pay_Location_ID", null);
	String COLUMNNAME_Pay_Location_ID = "Pay_Location_ID";

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

	ModelColumn<I_C_Order, Object> COLUMN_PaymentRule = new ModelColumn<>(I_C_Order.class, "PaymentRule", null);
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

	ModelColumn<I_C_Order, Object> COLUMN_POReference = new ModelColumn<>(I_C_Order.class, "POReference", null);
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

	ModelColumn<I_C_Order, Object> COLUMN_Posted = new ModelColumn<>(I_C_Order.class, "Posted", null);
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
	 * Set Bereitstellungsdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPreparationDate (@Nullable java.sql.Timestamp PreparationDate);

	/**
	 * Get Bereitstellungsdatum.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPreparationDate();

	ModelColumn<I_C_Order, Object> COLUMN_PreparationDate = new ModelColumn<>(I_C_Order.class, "PreparationDate", null);
	String COLUMNNAME_PreparationDate = "PreparationDate";

	/**
	 * Set Print Totals.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPRINTER_OPTS_IsPrintTotals (java.lang.String PRINTER_OPTS_IsPrintTotals);

	/**
	 * Get Print Totals.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPRINTER_OPTS_IsPrintTotals();

	ModelColumn<I_C_Order, Object> COLUMN_PRINTER_OPTS_IsPrintTotals = new ModelColumn<>(I_C_Order.class, "PRINTER_OPTS_IsPrintTotals", null);
	String COLUMNNAME_PRINTER_OPTS_IsPrintTotals = "PRINTER_OPTS_IsPrintTotals";

	/**
	 * Set Priorität.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriorityRule (java.lang.String PriorityRule);

	/**
	 * Get Priorität.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPriorityRule();

	ModelColumn<I_C_Order, Object> COLUMN_PriorityRule = new ModelColumn<>(I_C_Order.class, "PriorityRule", null);
	String COLUMNNAME_PriorityRule = "PriorityRule";

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

	ModelColumn<I_C_Order, Object> COLUMN_Processed = new ModelColumn<>(I_C_Order.class, "Processed", null);
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

	ModelColumn<I_C_Order, Object> COLUMN_Processing = new ModelColumn<>(I_C_Order.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Promotion Code.
	 * User entered promotion code at sales time
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPromotionCode (@Nullable java.lang.String PromotionCode);

	/**
	 * Get Promotion Code.
	 * User entered promotion code at sales time
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPromotionCode();

	ModelColumn<I_C_Order, Object> COLUMN_PromotionCode = new ModelColumn<>(I_C_Order.class, "PromotionCode", null);
	String COLUMNNAME_PromotionCode = "PromotionCode";

	/**
	 * Set Menge-Schnelleingabe.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty_FastInput (@Nullable BigDecimal Qty_FastInput);

	/**
	 * Get Menge-Schnelleingabe.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty_FastInput();

	ModelColumn<I_C_Order, Object> COLUMN_Qty_FastInput = new ModelColumn<>(I_C_Order.class, "Qty_FastInput", null);
	String COLUMNNAME_Qty_FastInput = "Qty_FastInput";

	/**
	 * Set Eingegangen via.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReceivedVia (@Nullable java.lang.String ReceivedVia);

	/**
	 * Get Eingegangen via.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReceivedVia();

	ModelColumn<I_C_Order, Object> COLUMN_ReceivedVia = new ModelColumn<>(I_C_Order.class, "ReceivedVia", null);
	String COLUMNNAME_ReceivedVia = "ReceivedVia";

	/**
	 * Set Auftragsdatum  (Ref. Auftrag).
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setRef_DateOrder (@Nullable java.sql.Timestamp Ref_DateOrder);

	/**
	 * Get Auftragsdatum  (Ref. Auftrag).
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.sql.Timestamp getRef_DateOrder();

	ModelColumn<I_C_Order, Object> COLUMN_Ref_DateOrder = new ModelColumn<>(I_C_Order.class, "Ref_DateOrder", null);
	String COLUMNNAME_Ref_DateOrder = "Ref_DateOrder";

	/**
	 * Set Referenced Order.
	 * Reference to corresponding Sales/Purchase Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRef_Order_ID (int Ref_Order_ID);

	/**
	 * Get Referenced Order.
	 * Reference to corresponding Sales/Purchase Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRef_Order_ID();

	@Nullable org.compiere.model.I_C_Order getRef_Order();

	void setRef_Order(@Nullable org.compiere.model.I_C_Order Ref_Order);

	ModelColumn<I_C_Order, org.compiere.model.I_C_Order> COLUMN_Ref_Order_ID = new ModelColumn<>(I_C_Order.class, "Ref_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_Ref_Order_ID = "Ref_Order_ID";

	/**
	 * Set Referenz Angebot/Auftrag.
	 * Verknüpfung zwischen einem Angebot und dem zugehörigen Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRef_Proposal_ID (int Ref_Proposal_ID);

	/**
	 * Get Referenz Angebot/Auftrag.
	 * Verknüpfung zwischen einem Angebot und dem zugehörigen Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRef_Proposal_ID();

	@Nullable org.compiere.model.I_C_Order getRef_Proposal();

	void setRef_Proposal(@Nullable org.compiere.model.I_C_Order Ref_Proposal);

	ModelColumn<I_C_Order, org.compiere.model.I_C_Order> COLUMN_Ref_Proposal_ID = new ModelColumn<>(I_C_Order.class, "Ref_Proposal_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_Ref_Proposal_ID = "Ref_Proposal_ID";

	/**
	 * Set Return Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setreturnDate (@Nullable java.sql.Timestamp returnDate);

	/**
	 * Get Return Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getreturnDate();

	ModelColumn<I_C_Order, Object> COLUMN_returnDate = new ModelColumn<>(I_C_Order.class, "returnDate", null);
	String COLUMNNAME_returnDate = "returnDate";

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

	ModelColumn<I_C_Order, Object> COLUMN_SalesPartnerCode = new ModelColumn<>(I_C_Order.class, "SalesPartnerCode", null);
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
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSalesRepIntern_ID (int SalesRepIntern_ID);

	/**
	 * Get Sales Responsible.
	 * Sales Responsible Internal
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSalesRepIntern_ID();

	String COLUMNNAME_SalesRepIntern_ID = "SalesRepIntern_ID";

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

	ModelColumn<I_C_Order, Object> COLUMN_SendEMail = new ModelColumn<>(I_C_Order.class, "SendEMail", null);
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

	ModelColumn<I_C_Order, Object> COLUMN_TotalLines = new ModelColumn<>(I_C_Order.class, "TotalLines", null);
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

	ModelColumn<I_C_Order, Object> COLUMN_Updated = new ModelColumn<>(I_C_Order.class, "Updated", null);
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

	ModelColumn<I_C_Order, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new ModelColumn<>(I_C_Order.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
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

	ModelColumn<I_C_Order, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new ModelColumn<>(I_C_Order.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_User2_ID = "User2_ID";

	/**
	 * Set Volume.
	 * Volume of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVolume (@Nullable BigDecimal Volume);

	/**
	 * Get Volume.
	 * Volume of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getVolume();

	ModelColumn<I_C_Order, Object> COLUMN_Volume = new ModelColumn<>(I_C_Order.class, "Volume", null);
	String COLUMNNAME_Volume = "Volume";

	/**
	 * Set Weight.
	 * Weight of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWeight (@Nullable BigDecimal Weight);

	/**
	 * Get Weight.
	 * Weight of a product
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getWeight();

	ModelColumn<I_C_Order, Object> COLUMN_Weight = new ModelColumn<>(I_C_Order.class, "Weight", null);
	String COLUMNNAME_Weight = "Weight";
}
