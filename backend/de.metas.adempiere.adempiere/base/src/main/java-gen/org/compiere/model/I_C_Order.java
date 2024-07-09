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

	ModelColumn<I_C_Order, Object> COLUMN_AD_InputDataSource_ID = new ModelColumn<>(I_C_Order.class, "AD_InputDataSource_ID", null);
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
	 * Set Rechnungsstandort (Address).
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_Location_Value_ID (int Bill_Location_Value_ID);

	/**
	 * Get Rechnungsstandort (Address).
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_Location_Value_ID();

	@Nullable org.compiere.model.I_C_Location getBill_Location_Value();

	void setBill_Location_Value(@Nullable org.compiere.model.I_C_Location Bill_Location_Value);

	ModelColumn<I_C_Order, org.compiere.model.I_C_Location> COLUMN_Bill_Location_Value_ID = new ModelColumn<>(I_C_Order.class, "Bill_Location_Value_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_Bill_Location_Value_ID = "Bill_Location_Value_ID";

	/**
	 * Set Diff. Invoice Address.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBillToAddress (@Nullable java.lang.String BillToAddress);

	/**
	 * Get Diff. Invoice Address.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBillToAddress();

	ModelColumn<I_C_Order, Object> COLUMN_BillToAddress = new ModelColumn<>(I_C_Order.class, "BillToAddress", null);
	String COLUMNNAME_BillToAddress = "BillToAddress";

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

	ModelColumn<I_C_Order, Object> COLUMN_BPartnerName = new ModelColumn<>(I_C_Order.class, "BPartnerName", null);
	String COLUMNNAME_BPartnerName = "BPartnerName";

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
	 * Set Async Batch.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Async_Batch_ID (int C_Async_Batch_ID);

	/**
	 * Get Async Batch.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Async_Batch_ID();

	ModelColumn<I_C_Order, Object> COLUMN_C_Async_Batch_ID = new ModelColumn<>(I_C_Order.class, "C_Async_Batch_ID", null);
	String COLUMNNAME_C_Async_Batch_ID = "C_Async_Batch_ID";

	/**
	 * Set Auction.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Auction_ID (int C_Auction_ID);

	/**
	 * Get Auction.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Auction_ID();

	@Nullable org.compiere.model.I_C_Auction getC_Auction();

	void setC_Auction(@Nullable org.compiere.model.I_C_Auction C_Auction);

	ModelColumn<I_C_Order, org.compiere.model.I_C_Auction> COLUMN_C_Auction_ID = new ModelColumn<>(I_C_Order.class, "C_Auction_ID", org.compiere.model.I_C_Auction.class);
	String COLUMNNAME_C_Auction_ID = "C_Auction_ID";

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

	ModelColumn<I_C_Order, org.compiere.model.I_C_Location> COLUMN_C_BPartner_Location_Value_ID = new ModelColumn<>(I_C_Order.class, "C_BPartner_Location_Value_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_BPartner_Location_Value_ID = "C_BPartner_Location_Value_ID";

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
	 * Set Pharmacy.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Pharmacy_ID (int C_BPartner_Pharmacy_ID);

	/**
	 * Get Pharmacy.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Pharmacy_ID();

	String COLUMNNAME_C_BPartner_Pharmacy_ID = "C_BPartner_Pharmacy_ID";

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
	 * Set Costs.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Costs.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Charge_ID();

	String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/**
	 * Set Conversiontype.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ConversionType_ID (int C_ConversionType_ID);

	/**
	 * Get Conversiontype.
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
	 * Set Frame Agreement Order.
	 * Reference to corresponding FrameAgreement Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_FrameAgreement_Order_ID (int C_FrameAgreement_Order_ID);

	/**
	 * Get Frame Agreement Order.
	 * Reference to corresponding FrameAgreement Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_FrameAgreement_Order_ID();

	@Nullable org.compiere.model.I_C_Order getC_FrameAgreement_Order();

	void setC_FrameAgreement_Order(@Nullable org.compiere.model.I_C_Order C_FrameAgreement_Order);

	ModelColumn<I_C_Order, org.compiere.model.I_C_Order> COLUMN_C_FrameAgreement_Order_ID = new ModelColumn<>(I_C_Order.class, "C_FrameAgreement_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_FrameAgreement_Order_ID = "C_FrameAgreement_Order_ID";

	/**
	 * Set Charge amount.
	 * Charge Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setChargeAmt (@Nullable BigDecimal ChargeAmt);

	/**
	 * Get Charge amount.
	 * Charge Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getChargeAmt();

	ModelColumn<I_C_Order, Object> COLUMN_ChargeAmt = new ModelColumn<>(I_C_Order.class, "ChargeAmt", null);
	String COLUMNNAME_ChargeAmt = "ChargeAmt";

	/**
	 * Set Harvesting Calendar.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Harvesting_Calendar_ID (int C_Harvesting_Calendar_ID);

	/**
	 * Get Harvesting Calendar.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Harvesting_Calendar_ID();

	@Nullable org.compiere.model.I_C_Calendar getC_Harvesting_Calendar();

	void setC_Harvesting_Calendar(@Nullable org.compiere.model.I_C_Calendar C_Harvesting_Calendar);

	ModelColumn<I_C_Order, org.compiere.model.I_C_Calendar> COLUMN_C_Harvesting_Calendar_ID = new ModelColumn<>(I_C_Order.class, "C_Harvesting_Calendar_ID", org.compiere.model.I_C_Calendar.class);
	String COLUMNNAME_C_Harvesting_Calendar_ID = "C_Harvesting_Calendar_ID";

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

	ModelColumn<I_C_Order, org.compiere.model.I_C_Incoterms> COLUMN_C_Incoterms_ID = new ModelColumn<>(I_C_Order.class, "C_Incoterms_ID", org.compiere.model.I_C_Incoterms.class);
	String COLUMNNAME_C_Incoterms_ID = "C_Incoterms_ID";

	/**
	 * Set Discount %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCompleteOrderDiscount (@Nullable BigDecimal CompleteOrderDiscount);

	/**
	 * Get Discount %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCompleteOrderDiscount();

	ModelColumn<I_C_Order, Object> COLUMN_CompleteOrderDiscount = new ModelColumn<>(I_C_Order.class, "CompleteOrderDiscount", null);
	String COLUMNNAME_CompleteOrderDiscount = "CompleteOrderDiscount";

	/**
	 * Set Copy BOM Lines From.
	 * Copy BOM Lines from an exising BOM
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCopyFrom (@Nullable java.lang.String CopyFrom);

	/**
	 * Get Copy BOM Lines From.
	 * Copy BOM Lines from an exising BOM
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCopyFrom();

	ModelColumn<I_C_Order, Object> COLUMN_CopyFrom = new ModelColumn<>(I_C_Order.class, "CopyFrom", null);
	String COLUMNNAME_CopyFrom = "CopyFrom";

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
	 * Set Payment Instruction.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_PaymentInstruction_ID (int C_PaymentInstruction_ID);

	/**
	 * Get Payment Instruction.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_PaymentInstruction_ID();

	@Nullable org.compiere.model.I_C_PaymentInstruction getC_PaymentInstruction();

	void setC_PaymentInstruction(@Nullable org.compiere.model.I_C_PaymentInstruction C_PaymentInstruction);

	ModelColumn<I_C_Order, org.compiere.model.I_C_PaymentInstruction> COLUMN_C_PaymentInstruction_ID = new ModelColumn<>(I_C_Order.class, "C_PaymentInstruction_ID", org.compiere.model.I_C_PaymentInstruction.class);
	String COLUMNNAME_C_PaymentInstruction_ID = "C_PaymentInstruction_ID";

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
	 * Set POS Terminal.
	 * Point of Sales Terminal
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_POS_ID (int C_POS_ID);

	/**
	 * Get POS Terminal.
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
	 * Set Copy/Create.
	 * Copy existing OR create Print Format from Table
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreateCopy (@Nullable java.lang.String CreateCopy);

	/**
	 * Get Copy/Create.
	 * Copy existing OR create Print Format from Table
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
	 * Set Create Order.
	 * Erzeugt aus dem ausgewählten Angebot einen neuen Auftrag
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreateNewFromProposal (@Nullable java.lang.String CreateNewFromProposal);

	/**
	 * Get Create Order.
	 * Erzeugt aus dem ausgewählten Angebot einen neuen Auftrag
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreateNewFromProposal();

	ModelColumn<I_C_Order, Object> COLUMN_CreateNewFromProposal = new ModelColumn<>(I_C_Order.class, "CreateNewFromProposal", null);
	String COLUMNNAME_CreateNewFromProposal = "CreateNewFromProposal";

	/**
	 * Set Tax Departure Country.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Tax_Departure_Country_ID (int C_Tax_Departure_Country_ID);

	/**
	 * Get Tax Departure Country.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Tax_Departure_Country_ID();

	@Nullable org.compiere.model.I_C_Country getC_Tax_Departure_Country();

	void setC_Tax_Departure_Country(@Nullable org.compiere.model.I_C_Country C_Tax_Departure_Country);

	ModelColumn<I_C_Order, org.compiere.model.I_C_Country> COLUMN_C_Tax_Departure_Country_ID = new ModelColumn<>(I_C_Order.class, "C_Tax_Departure_Country_ID", org.compiere.model.I_C_Country.class);
	String COLUMNNAME_C_Tax_Departure_Country_ID = "C_Tax_Departure_Country_ID";

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
	 * Set Date Promised From.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDatePromised (java.sql.Timestamp DatePromised);

	/**
	 * Get Date Promised From.
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
	 * Set Delivery Rule.
	 * Defines the timing of Delivery
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDeliveryRule (java.lang.String DeliveryRule);

	/**
	 * Get Delivery Rule.
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
	 * Set Diff. Shipment Address.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryToAddress (@Nullable java.lang.String DeliveryToAddress);

	/**
	 * Get Diff. Shipment Address.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDeliveryToAddress();

	ModelColumn<I_C_Order, Object> COLUMN_DeliveryToAddress = new ModelColumn<>(I_C_Order.class, "DeliveryToAddress", null);
	String COLUMNNAME_DeliveryToAddress = "DeliveryToAddress";

	/**
	 * Set Delivery Via.
	 * How the order will be delivered
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDeliveryViaRule (java.lang.String DeliveryViaRule);

	/**
	 * Get Delivery Via.
	 * How the order will be delivered
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
	 * Set End note.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescriptionBottom (@Nullable java.lang.String DescriptionBottom);

	/**
	 * Get End note.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescriptionBottom();

	ModelColumn<I_C_Order, Object> COLUMN_DescriptionBottom = new ModelColumn<>(I_C_Order.class, "DescriptionBottom", null);
	String COLUMNNAME_DescriptionBottom = "DescriptionBottom";

	/**
	 * Set Description Bottom List.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescriptionBottom_BoilerPlate_ID (int DescriptionBottom_BoilerPlate_ID);

	/**
	 * Get Description Bottom List.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDescriptionBottom_BoilerPlate_ID();

	ModelColumn<I_C_Order, Object> COLUMN_DescriptionBottom_BoilerPlate_ID = new ModelColumn<>(I_C_Order.class, "DescriptionBottom_BoilerPlate_ID", null);
	String COLUMNNAME_DescriptionBottom_BoilerPlate_ID = "DescriptionBottom_BoilerPlate_ID";

	/**
	 * Set Process Batch.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocAction (java.lang.String DocAction);

	/**
	 * Get Process Batch.
	 * Der zukünftige Status des Belegs
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
	 * Set Doc Sub Type.
	 * Document Sub Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setDocSubType (@Nullable java.lang.String DocSubType);

	/**
	 * Get Doc Sub Type.
	 * Document Sub Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getDocSubType();

	ModelColumn<I_C_Order, Object> COLUMN_DocSubType = new ModelColumn<>(I_C_Order.class, "DocSubType", null);
	String COLUMNNAME_DocSubType = "DocSubType";

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
	 * Set Lieferadresse (Address).
	 * Business Partner Location for shipping to
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropShip_Location_Value_ID (int DropShip_Location_Value_ID);

	/**
	 * Get Lieferadresse (Address).
	 * Business Partner Location for shipping to
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropShip_Location_Value_ID();

	@Nullable org.compiere.model.I_C_Location getDropShip_Location_Value();

	void setDropShip_Location_Value(@Nullable org.compiere.model.I_C_Location DropShip_Location_Value);

	ModelColumn<I_C_Order, org.compiere.model.I_C_Location> COLUMN_DropShip_Location_Value_ID = new ModelColumn<>(I_C_Order.class, "DropShip_Location_Value_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_DropShip_Location_Value_ID = "DropShip_Location_Value_ID";

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

	ModelColumn<I_C_Order, Object> COLUMN_EMail = new ModelColumn<>(I_C_Order.class, "EMail", null);
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

	ModelColumn<I_C_Order, Object> COLUMN_ExternalId = new ModelColumn<>(I_C_Order.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set URL of the purchase order in an external system.
	 * If a purchase order was synched from an external system, this field can be used to store its URL
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalPurchaseOrderURL (@Nullable java.lang.String ExternalPurchaseOrderURL);

	/**
	 * Get URL of the purchase order in an external system.
	 * If a purchase order was synched from an external system, this field can be used to store its URL
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalPurchaseOrderURL();

	ModelColumn<I_C_Order, Object> COLUMN_ExternalPurchaseOrderURL = new ModelColumn<>(I_C_Order.class, "ExternalPurchaseOrderURL", null);
	String COLUMNNAME_ExternalPurchaseOrderURL = "ExternalPurchaseOrderURL";

	/**
	 * Set Freight Amount.
	 * Freight Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFreightAmt (BigDecimal FreightAmt);

	/**
	 * Get Freight Amount.
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
	 * Set Freight Cost Rule.
	 * Method for charging Freight
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFreightCostRule (java.lang.String FreightCostRule);

	/**
	 * Get Freight Cost Rule.
	 * Method for charging Freight
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getFreightCostRule();

	ModelColumn<I_C_Order, Object> COLUMN_FreightCostRule = new ModelColumn<>(I_C_Order.class, "FreightCostRule", null);
	String COLUMNNAME_FreightCostRule = "FreightCostRule";

	/**
	 * Set Fully delivered and completely invoiced.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setFullyDeliveredAndCompletelyInvoiced (boolean FullyDeliveredAndCompletelyInvoiced);

	/**
	 * Get Fully delivered and completely invoiced.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	boolean isFullyDeliveredAndCompletelyInvoiced();

	ModelColumn<I_C_Order, Object> COLUMN_FullyDeliveredAndCompletelyInvoiced = new ModelColumn<>(I_C_Order.class, "FullyDeliveredAndCompletelyInvoiced", null);
	String COLUMNNAME_FullyDeliveredAndCompletelyInvoiced = "FullyDeliveredAndCompletelyInvoiced";

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

	ModelColumn<I_C_Order, Object> COLUMN_GrandTotal = new ModelColumn<>(I_C_Order.class, "GrandTotal", null);
	String COLUMNNAME_GrandTotal = "GrandTotal";

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
	 * Set unloading address.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHandOver_Location_ID (int HandOver_Location_ID);

	/**
	 * Get unloading address.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getHandOver_Location_ID();

	String COLUMNNAME_HandOver_Location_ID = "HandOver_Location_ID";

	/**
	 * Set Übergabeadresse (Address).
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHandOver_Location_Value_ID (int HandOver_Location_Value_ID);

	/**
	 * Get Übergabeadresse (Address).
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getHandOver_Location_Value_ID();

	@Nullable org.compiere.model.I_C_Location getHandOver_Location_Value();

	void setHandOver_Location_Value(@Nullable org.compiere.model.I_C_Location HandOver_Location_Value);

	ModelColumn<I_C_Order, org.compiere.model.I_C_Location> COLUMN_HandOver_Location_Value_ID = new ModelColumn<>(I_C_Order.class, "HandOver_Location_Value_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_HandOver_Location_Value_ID = "HandOver_Location_Value_ID";

	/**
	 * Set Handover partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHandOver_Partner_ID (int HandOver_Partner_ID);

	/**
	 * Get Handover partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getHandOver_Partner_ID();

	String COLUMNNAME_HandOver_Partner_ID = "HandOver_Partner_ID";

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
	 * Set Harvesting Year.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHarvesting_Year_ID (int Harvesting_Year_ID);

	/**
	 * Get Harvesting Year.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getHarvesting_Year_ID();

	@Nullable org.compiere.model.I_C_Year getHarvesting_Year();

	void setHarvesting_Year(@Nullable org.compiere.model.I_C_Year Harvesting_Year);

	ModelColumn<I_C_Order, org.compiere.model.I_C_Year> COLUMN_Harvesting_Year_ID = new ModelColumn<>(I_C_Order.class, "Harvesting_Year_ID", org.compiere.model.I_C_Year.class);
	String COLUMNNAME_Harvesting_Year_ID = "Harvesting_Year_ID";

	/**
	 * Set Incoterm Location.
	 * Anzugebender Ort für Handelsklausel
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIncotermLocation (@Nullable java.lang.String IncotermLocation);

	/**
	 * Get Incoterm Location.
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
	 * Set Internal Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInternalDescription (@Nullable java.lang.String InternalDescription);

	/**
	 * Get Internal Description.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInternalDescription();

	ModelColumn<I_C_Order, Object> COLUMN_InternalDescription = new ModelColumn<>(I_C_Order.class, "InternalDescription", null);
	String COLUMNNAME_InternalDescription = "InternalDescription";

	/**
	 * Set Additional Text for Invoice.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoiceAdditionalText (@Nullable java.lang.String InvoiceAdditionalText);

	/**
	 * Get Additional Text for Invoice.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInvoiceAdditionalText();

	ModelColumn<I_C_Order, Object> COLUMN_InvoiceAdditionalText = new ModelColumn<>(I_C_Order.class, "InvoiceAdditionalText", null);
	String COLUMNNAME_InvoiceAdditionalText = "InvoiceAdditionalText";

	/**
	 * Set Invoice Rule.
	 * Frequency and method of invoicing
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoiceRule (java.lang.String InvoiceRule);

	/**
	 * Get Invoice Rule.
	 * Frequency and method of invoicing
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getInvoiceRule();

	ModelColumn<I_C_Order, Object> COLUMN_InvoiceRule = new ModelColumn<>(I_C_Order.class, "InvoiceRule", null);
	String COLUMNNAME_InvoiceRule = "InvoiceRule";

	/**
	 * Set Status Billing.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setInvoiceStatus (@Nullable java.lang.String InvoiceStatus);

	/**
	 * Get Status Billing.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getInvoiceStatus();

	ModelColumn<I_C_Order, Object> COLUMN_InvoiceStatus = new ModelColumn<>(I_C_Order.class, "InvoiceStatus", null);
	String COLUMNNAME_InvoiceStatus = "InvoiceStatus";

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
	 * Set Credit Approved.
	 * Credit  has been approved
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCreditApproved (boolean IsCreditApproved);

	/**
	 * Get Credit Approved.
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
	 * Set Is Delivered.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDelivered (boolean IsDelivered);

	/**
	 * Get Is Delivered.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDelivered();

	ModelColumn<I_C_Order, Object> COLUMN_IsDelivered = new ModelColumn<>(I_C_Order.class, "IsDelivered", null);
	String COLUMNNAME_IsDelivered = "IsDelivered";

	/**
	 * Set Discount Printed.
	 * Print Discount on Invoice and Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDiscountPrinted (boolean IsDiscountPrinted);

	/**
	 * Get Discount Printed.
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
	 * Set Different shipping address.
	 * Drop Shipments are sent from the Vendor directly to the Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDropShip (boolean IsDropShip);

	/**
	 * Get Different shipping address.
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
	 * Set Invoiced.
	 * Is this invoiced?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInvoiced (boolean IsInvoiced);

	/**
	 * Get Invoiced.
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
	 * Set Do not show Country of Origin.
	 * If is NO, then the Country of Origin of the products is displayed in the invoice report
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsNotShowOriginCountry (boolean IsNotShowOriginCountry);

	/**
	 * Get Do not show Country of Origin.
	 * If is NO, then the Country of Origin of the products is displayed in the invoice report
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isNotShowOriginCountry();

	ModelColumn<I_C_Order, Object> COLUMN_IsNotShowOriginCountry = new ModelColumn<>(I_C_Order.class, "IsNotShowOriginCountry", null);
	String COLUMNNAME_IsNotShowOriginCountry = "IsNotShowOriginCountry";

	/**
	 * Set Goods on consignment.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOnConsignment (boolean IsOnConsignment);

	/**
	 * Get Goods on consignment.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnConsignment();

	ModelColumn<I_C_Order, Object> COLUMN_IsOnConsignment = new ModelColumn<>(I_C_Order.class, "IsOnConsignment", null);
	String COLUMNNAME_IsOnConsignment = "IsOnConsignment";

	/**
	 * Set Printed.
	 * Indicates if this document / line is printed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPrinted (boolean IsPrinted);

	/**
	 * Get Printed.
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
	 * Set Selected.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSelected (boolean IsSelected);

	/**
	 * Get Selected.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSelected();

	ModelColumn<I_C_Order, Object> COLUMN_IsSelected = new ModelColumn<>(I_C_Order.class, "IsSelected", null);
	String COLUMNNAME_IsSelected = "IsSelected";

	/**
	 * Set Self-Service.
	 * This is a Self-Service entry or this entry can be changed via Self-Service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSelfService (boolean IsSelfService);

	/**
	 * Get Self-Service.
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

	ModelColumn<I_C_Order, Object> COLUMN_IsTaxIncluded = new ModelColumn<>(I_C_Order.class, "IsTaxIncluded", null);
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
	 * Set Benutze abw. Lieferung Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsUseDeliveryToAddress (boolean IsUseDeliveryToAddress);

	/**
	 * Get Benutze abw. Lieferung Adresse.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUseDeliveryToAddress();

	ModelColumn<I_C_Order, Object> COLUMN_IsUseDeliveryToAddress = new ModelColumn<>(I_C_Order.class, "IsUseDeliveryToAddress", null);
	String COLUMNNAME_IsUseDeliveryToAddress = "IsUseDeliveryToAddress";

	/**
	 * Set Handover.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsUseHandOver_Location (boolean IsUseHandOver_Location);

	/**
	 * Get Handover.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUseHandOver_Location();

	ModelColumn<I_C_Order, Object> COLUMN_IsUseHandOver_Location = new ModelColumn<>(I_C_Order.class, "IsUseHandOver_Location", null);
	String COLUMNNAME_IsUseHandOver_Location = "IsUseHandOver_Location";

	/**
	 * Set Linked Order.
	 * This field links a sales order to the purchase order that is generated from it.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLink_Order_ID (int Link_Order_ID);

	/**
	 * Get Linked Order.
	 * This field links a sales order to the purchase order that is generated from it.
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
	 * Set Freight Category.
	 * Category of the Freight
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_FreightCategory_ID (int M_FreightCategory_ID);

	/**
	 * Get Freight Category.
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
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

	/**
	 * Set Price List.
	 * Unique identifier of a Price List
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_ID (int M_PriceList_ID);

	/**
	 * Get Price List.
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
	 * Set Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_SectionCode_ID (int M_SectionCode_ID);

	/**
	 * Get Section Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_SectionCode_ID();

	@Nullable org.compiere.model.I_M_SectionCode getM_SectionCode();

	void setM_SectionCode(@Nullable org.compiere.model.I_M_SectionCode M_SectionCode);

	ModelColumn<I_C_Order, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_C_Order.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

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
	 * Set Order Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setOrderStatus (@Nullable java.lang.String OrderStatus);

	/**
	 * Get Order Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getOrderStatus();

	ModelColumn<I_C_Order, Object> COLUMN_OrderStatus = new ModelColumn<>(I_C_Order.class, "OrderStatus", null);
	String COLUMNNAME_OrderStatus = "OrderStatus";

	/**
	 * Set Order Type.
	 * Type of Order: MRP records grouped by source (Sales Order, Purchase Order, Distribution Order, Requisition)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrderType (@Nullable java.lang.String OrderType);

	/**
	 * Get Order Type.
	 * Type of Order: MRP records grouped by source (Sales Order, Purchase Order, Distribution Order, Requisition)
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
	 * Set Physical Clearance Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPhysicalClearanceDate (@Nullable java.sql.Timestamp PhysicalClearanceDate);

	/**
	 * Get Physical Clearance Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPhysicalClearanceDate();

	ModelColumn<I_C_Order, Object> COLUMN_PhysicalClearanceDate = new ModelColumn<>(I_C_Order.class, "PhysicalClearanceDate", null);
	String COLUMNNAME_PhysicalClearanceDate = "PhysicalClearanceDate";


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

	ModelColumn<I_C_Order, Object> COLUMN_Phone = new ModelColumn<>(I_C_Order.class, "Phone", null);
	String COLUMNNAME_Phone = "Phone";

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
	 * Set Date ready.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPreparationDate (@Nullable java.sql.Timestamp PreparationDate);

	/**
	 * Get Date ready.
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
	 * Set Priority.
	 * Priority of a document
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPriorityRule (java.lang.String PriorityRule);

	/**
	 * Get Priority.
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
	 * Set Project Manager.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProjectManager_ID (int ProjectManager_ID);

	/**
	 * Get Project Manager.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getProjectManager_ID();

	String COLUMNNAME_ProjectManager_ID = "ProjectManager_ID";

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
	 * Set Qty without Trading Unit.
	 * Mengen-Schnelleingabe
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty_FastInput (@Nullable BigDecimal Qty_FastInput);

	/**
	 * Get Qty without Trading Unit.
	 * Mengen-Schnelleingabe
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty_FastInput();

	ModelColumn<I_C_Order, Object> COLUMN_Qty_FastInput = new ModelColumn<>(I_C_Order.class, "Qty_FastInput", null);
	String COLUMNNAME_Qty_FastInput = "Qty_FastInput";

	/**
	 * Set Inbound by.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReceivedVia (@Nullable java.lang.String ReceivedVia);

	/**
	 * Get Inbound by.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReceivedVia();

	ModelColumn<I_C_Order, Object> COLUMN_ReceivedVia = new ModelColumn<>(I_C_Order.class, "ReceivedVia", null);
	String COLUMNNAME_ReceivedVia = "ReceivedVia";

	/**
	 * Set Order Date (Ref. Order).
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setRef_DateOrder (@Nullable java.sql.Timestamp Ref_DateOrder);

	/**
	 * Get Order Date (Ref. Order).
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
	 * Set Reference Quote / Order.
	 * Verknüpfung zwischen einem Angebot und dem zugehörigen Auftrag
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRef_Proposal_ID (int Ref_Proposal_ID);

	/**
	 * Get Reference Quote / Order.
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
	 * Set Requestor.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRequestor_ID (int Requestor_ID);

	/**
	 * Get Requestor.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRequestor_ID();

	String COLUMNNAME_Requestor_ID = "Requestor_ID";

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
	 * Set Send EMail.
	 * Enable sending Document EMail
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSendEMail (boolean SendEMail);

	/**
	 * Get Send EMail.
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
	 * Set Total Lines.
	 * Total of all document lines
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTotalLines (BigDecimal TotalLines);

	/**
	 * Get Total Lines.
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

	String COLUMNNAME_User2_ID = "User2_ID";

	/**
	 * Set UserElementString1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString1 (@Nullable java.lang.String UserElementString1);

	/**
	 * Get UserElementString1.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString1();

	ModelColumn<I_C_Order, Object> COLUMN_UserElementString1 = new ModelColumn<>(I_C_Order.class, "UserElementString1", null);
	String COLUMNNAME_UserElementString1 = "UserElementString1";

	/**
	 * Set UserElementString2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString2 (@Nullable java.lang.String UserElementString2);

	/**
	 * Get UserElementString2.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString2();

	ModelColumn<I_C_Order, Object> COLUMN_UserElementString2 = new ModelColumn<>(I_C_Order.class, "UserElementString2", null);
	String COLUMNNAME_UserElementString2 = "UserElementString2";

	/**
	 * Set UserElementString3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString3 (@Nullable java.lang.String UserElementString3);

	/**
	 * Get UserElementString3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString3();

	ModelColumn<I_C_Order, Object> COLUMN_UserElementString3 = new ModelColumn<>(I_C_Order.class, "UserElementString3", null);
	String COLUMNNAME_UserElementString3 = "UserElementString3";

	/**
	 * Set UserElementString4.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString4 (@Nullable java.lang.String UserElementString4);

	/**
	 * Get UserElementString4.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString4();

	ModelColumn<I_C_Order, Object> COLUMN_UserElementString4 = new ModelColumn<>(I_C_Order.class, "UserElementString4", null);
	String COLUMNNAME_UserElementString4 = "UserElementString4";

	/**
	 * Set UserElementString5.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString5 (@Nullable java.lang.String UserElementString5);

	/**
	 * Get UserElementString5.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString5();

	ModelColumn<I_C_Order, Object> COLUMN_UserElementString5 = new ModelColumn<>(I_C_Order.class, "UserElementString5", null);
	String COLUMNNAME_UserElementString5 = "UserElementString5";

	/**
	 * Set UserElementString6.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString6 (@Nullable java.lang.String UserElementString6);

	/**
	 * Get UserElementString6.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString6();

	ModelColumn<I_C_Order, Object> COLUMN_UserElementString6 = new ModelColumn<>(I_C_Order.class, "UserElementString6", null);
	String COLUMNNAME_UserElementString6 = "UserElementString6";

	/**
	 * Set UserElementString7.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString7 (@Nullable java.lang.String UserElementString7);

	/**
	 * Get UserElementString7.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString7();

	ModelColumn<I_C_Order, Object> COLUMN_UserElementString7 = new ModelColumn<>(I_C_Order.class, "UserElementString7", null);
	String COLUMNNAME_UserElementString7 = "UserElementString7";

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
