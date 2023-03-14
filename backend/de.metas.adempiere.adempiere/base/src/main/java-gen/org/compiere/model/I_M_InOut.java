package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_InOut
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_InOut 
{

	String Table_Name = "M_InOut";

//	/** AD_Table_ID=319 */
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

	ModelColumn<I_M_InOut, Object> COLUMN_AD_InputDataSource_ID = new ModelColumn<>(I_M_InOut.class, "AD_InputDataSource_ID", null);
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
	 * Set B2B Shipment / Receipt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setB2B_InOut_ID (int B2B_InOut_ID);

	/**
	 * Get B2B Shipment / Receipt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getB2B_InOut_ID();

	@Nullable org.compiere.model.I_M_InOut getB2B_InOut();

	void setB2B_InOut(@Nullable org.compiere.model.I_M_InOut B2B_InOut);

	ModelColumn<I_M_InOut, org.compiere.model.I_M_InOut> COLUMN_B2B_InOut_ID = new ModelColumn<>(I_M_InOut.class, "B2B_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_B2B_InOut_ID = "B2B_InOut_ID";

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

	ModelColumn<I_M_InOut, Object> COLUMN_BPartnerAddress = new ModelColumn<>(I_M_InOut.class, "BPartnerAddress", null);
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

	ModelColumn<I_M_InOut, Object> COLUMN_C_Async_Batch_ID = new ModelColumn<>(I_M_InOut.class, "C_Async_Batch_ID", null);
	String COLUMNNAME_C_Async_Batch_ID = "C_Async_Batch_ID";

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

	ModelColumn<I_M_InOut, org.compiere.model.I_C_Location> COLUMN_C_BPartner_Location_Value_ID = new ModelColumn<>(I_M_InOut.class, "C_BPartner_Location_Value_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_BPartner_Location_Value_ID = "C_BPartner_Location_Value_ID";

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

	ModelColumn<I_M_InOut, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_M_InOut.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

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
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Foreign Exchange Contract.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ForeignExchangeContract_ID (int C_ForeignExchangeContract_ID);

	/**
	 * Get Foreign Exchange Contract.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ForeignExchangeContract_ID();

	@Nullable org.compiere.model.I_C_ForeignExchangeContract getC_ForeignExchangeContract();

	void setC_ForeignExchangeContract(@Nullable org.compiere.model.I_C_ForeignExchangeContract C_ForeignExchangeContract);

	ModelColumn<I_M_InOut, org.compiere.model.I_C_ForeignExchangeContract> COLUMN_C_ForeignExchangeContract_ID = new ModelColumn<>(I_M_InOut.class, "C_ForeignExchangeContract_ID", org.compiere.model.I_C_ForeignExchangeContract.class);
	String COLUMNNAME_C_ForeignExchangeContract_ID = "C_ForeignExchangeContract_ID";

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

	ModelColumn<I_M_InOut, Object> COLUMN_ChargeAmt = new ModelColumn<>(I_M_InOut.class, "ChargeAmt", null);
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

	ModelColumn<I_M_InOut, org.compiere.model.I_C_Incoterms> COLUMN_C_Incoterms_ID = new ModelColumn<>(I_M_InOut.class, "C_Incoterms_ID", org.compiere.model.I_C_Incoterms.class);
	String COLUMNNAME_C_Incoterms_ID = "C_Incoterms_ID";

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_ID();

	@Nullable org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(@Nullable org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_M_InOut, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_M_InOut.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

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

	ModelColumn<I_M_InOut, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_M_InOut.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

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
	 * Set Create Confirmation.
	 * Create Confirmations for the Document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreateConfirm (@Nullable java.lang.String CreateConfirm);

	/**
	 * Get Create Confirmation.
	 * Create Confirmations for the Document
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreateConfirm();

	ModelColumn<I_M_InOut, Object> COLUMN_CreateConfirm = new ModelColumn<>(I_M_InOut.class, "CreateConfirm", null);
	String COLUMNNAME_CreateConfirm = "CreateConfirm";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_InOut, Object> COLUMN_Created = new ModelColumn<>(I_M_InOut.class, "Created", null);
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
	 * Set Create From ....
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreateFrom (@Nullable java.lang.String CreateFrom);

	/**
	 * Get Create From ....
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreateFrom();

	ModelColumn<I_M_InOut, Object> COLUMN_CreateFrom = new ModelColumn<>(I_M_InOut.class, "CreateFrom", null);
	String COLUMNNAME_CreateFrom = "CreateFrom";

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

	ModelColumn<I_M_InOut, Object> COLUMN_DateAcct = new ModelColumn<>(I_M_InOut.class, "DateAcct", null);
	String COLUMNNAME_DateAcct = "DateAcct";

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

	ModelColumn<I_M_InOut, Object> COLUMN_DateOrdered = new ModelColumn<>(I_M_InOut.class, "DateOrdered", null);
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

	ModelColumn<I_M_InOut, Object> COLUMN_DatePrinted = new ModelColumn<>(I_M_InOut.class, "DatePrinted", null);
	String COLUMNNAME_DatePrinted = "DatePrinted";

	/**
	 * Set Date received.
	 * Date a product was received
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateReceived (@Nullable java.sql.Timestamp DateReceived);

	/**
	 * Get Date received.
	 * Date a product was received
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateReceived();

	ModelColumn<I_M_InOut, Object> COLUMN_DateReceived = new ModelColumn<>(I_M_InOut.class, "DateReceived", null);
	String COLUMNNAME_DateReceived = "DateReceived";

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

	ModelColumn<I_M_InOut, Object> COLUMN_DeliveryRule = new ModelColumn<>(I_M_InOut.class, "DeliveryRule", null);
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

	ModelColumn<I_M_InOut, Object> COLUMN_DeliveryToAddress = new ModelColumn<>(I_M_InOut.class, "DeliveryToAddress", null);
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

	ModelColumn<I_M_InOut, Object> COLUMN_DeliveryViaRule = new ModelColumn<>(I_M_InOut.class, "DeliveryViaRule", null);
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

	ModelColumn<I_M_InOut, Object> COLUMN_Description = new ModelColumn<>(I_M_InOut.class, "Description", null);
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

	ModelColumn<I_M_InOut, Object> COLUMN_DescriptionBottom = new ModelColumn<>(I_M_InOut.class, "DescriptionBottom", null);
	String COLUMNNAME_DescriptionBottom = "DescriptionBottom";

	/**
	 * Set Process Batch.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocAction (java.lang.String DocAction);

	/**
	 * Get Process Batch.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocAction();

	ModelColumn<I_M_InOut, Object> COLUMN_DocAction = new ModelColumn<>(I_M_InOut.class, "DocAction", null);
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

	ModelColumn<I_M_InOut, Object> COLUMN_DocStatus = new ModelColumn<>(I_M_InOut.class, "DocStatus", null);
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

	ModelColumn<I_M_InOut, Object> COLUMN_DocumentNo = new ModelColumn<>(I_M_InOut.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Note.
	 * Optional note of a document type
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setDocumentTypeNote (@Nullable java.lang.String DocumentTypeNote);

	/**
	 * Get Note.
	 * Optional note of a document type
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getDocumentTypeNote();

	ModelColumn<I_M_InOut, Object> COLUMN_DocumentTypeNote = new ModelColumn<>(I_M_InOut.class, "DocumentTypeNote", null);
	String COLUMNNAME_DocumentTypeNote = "DocumentTypeNote";

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

	ModelColumn<I_M_InOut, org.compiere.model.I_C_Location> COLUMN_DropShip_Location_Value_ID = new ModelColumn<>(I_M_InOut.class, "DropShip_Location_Value_ID", org.compiere.model.I_C_Location.class);
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

	ModelColumn<I_M_InOut, Object> COLUMN_EMail = new ModelColumn<>(I_M_InOut.class, "EMail", null);
	String COLUMNNAME_EMail = "EMail";

	/**
	 * Set SAP PayT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalId (@Nullable java.lang.String ExternalId);

	/**
	 * Get SAP PayT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalId();

	ModelColumn<I_M_InOut, Object> COLUMN_ExternalId = new ModelColumn<>(I_M_InOut.class, "ExternalId", null);
	String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set External resource URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalResourceURL (@Nullable java.lang.String ExternalResourceURL);

	/**
	 * Get External resource URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalResourceURL();

	ModelColumn<I_M_InOut, Object> COLUMN_ExternalResourceURL = new ModelColumn<>(I_M_InOut.class, "ExternalResourceURL", null);
	String COLUMNNAME_ExternalResourceURL = "ExternalResourceURL";

	/**
	 * Set FEC Currency Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFEC_CurrencyRate (@Nullable BigDecimal FEC_CurrencyRate);

	/**
	 * Get FEC Currency Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getFEC_CurrencyRate();

	ModelColumn<I_M_InOut, Object> COLUMN_FEC_CurrencyRate = new ModelColumn<>(I_M_InOut.class, "FEC_CurrencyRate", null);
	String COLUMNNAME_FEC_CurrencyRate = "FEC_CurrencyRate";

	/**
	 * Set FEC Currency From.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFEC_From_Currency_ID (int FEC_From_Currency_ID);

	/**
	 * Get FEC Currency From.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getFEC_From_Currency_ID();

	String COLUMNNAME_FEC_From_Currency_ID = "FEC_From_Currency_ID";

	/**
	 * Set Order Currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFEC_Order_Currency_ID (int FEC_Order_Currency_ID);

	/**
	 * Get Order Currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getFEC_Order_Currency_ID();

	String COLUMNNAME_FEC_Order_Currency_ID = "FEC_Order_Currency_ID";

	/**
	 * Set FEC Currency To.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFEC_To_Currency_ID (int FEC_To_Currency_ID);

	/**
	 * Get FEC Currency To.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getFEC_To_Currency_ID();

	String COLUMNNAME_FEC_To_Currency_ID = "FEC_To_Currency_ID";

	/**
	 * Set Freight Amount.
	 * Freight Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFreightAmt (@Nullable BigDecimal FreightAmt);

	/**
	 * Get Freight Amount.
	 * Freight Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getFreightAmt();

	ModelColumn<I_M_InOut, Object> COLUMN_FreightAmt = new ModelColumn<>(I_M_InOut.class, "FreightAmt", null);
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

	ModelColumn<I_M_InOut, Object> COLUMN_FreightCostRule = new ModelColumn<>(I_M_InOut.class, "FreightCostRule", null);
	String COLUMNNAME_FreightCostRule = "FreightCostRule";

	/**
	 * Set Generate Invoice from Receipt.
	 * Create and process Invoice from this receipt.  The receipt should be correct and completed.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGenerateTo (@Nullable java.lang.String GenerateTo);

	/**
	 * Get Generate Invoice from Receipt.
	 * Create and process Invoice from this receipt.  The receipt should be correct and completed.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGenerateTo();

	ModelColumn<I_M_InOut, Object> COLUMN_GenerateTo = new ModelColumn<>(I_M_InOut.class, "GenerateTo", null);
	String COLUMNNAME_GenerateTo = "GenerateTo";

	/**
	 * Set Incoterm Location.
	 * Location to be specified for commercial clause
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIncotermLocation (@Nullable java.lang.String IncotermLocation);

	/**
	 * Get Incoterm Location.
	 * Location to be specified for commercial clause
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIncotermLocation();

	ModelColumn<I_M_InOut, Object> COLUMN_IncotermLocation = new ModelColumn<>(I_M_InOut.class, "IncotermLocation", null);
	String COLUMNNAME_IncotermLocation = "IncotermLocation";

	/**
	 * Set InOutLine_includedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInOutLine_includedTab (@Nullable java.lang.String InOutLine_includedTab);

	/**
	 * Get InOutLine_includedTab.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInOutLine_includedTab();

	ModelColumn<I_M_InOut, Object> COLUMN_InOutLine_includedTab = new ModelColumn<>(I_M_InOut.class, "InOutLine_includedTab", null);
	String COLUMNNAME_InOutLine_includedTab = "InOutLine_includedTab";

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

	ModelColumn<I_M_InOut, Object> COLUMN_IsActive = new ModelColumn<>(I_M_InOut.class, "IsActive", null);
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

	ModelColumn<I_M_InOut, Object> COLUMN_IsApproved = new ModelColumn<>(I_M_InOut.class, "IsApproved", null);
	String COLUMNNAME_IsApproved = "IsApproved";

	/**
	 * Set Different shipping address.
	 * Drop Shipments are sent from the Vendor directly to the Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsDropShip (boolean IsDropShip);

	/**
	 * Get Different shipping address.
	 * Drop Shipments are sent from the Vendor directly to the Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isDropShip();

	ModelColumn<I_M_InOut, Object> COLUMN_IsDropShip = new ModelColumn<>(I_M_InOut.class, "IsDropShip", null);
	String COLUMNNAME_IsDropShip = "IsDropShip";

	/**
	 * Set Exported To Customs Invoice.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsExportedToCustomsInvoice (boolean IsExportedToCustomsInvoice);

	/**
	 * Get Exported To Customs Invoice.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isExportedToCustomsInvoice();

	ModelColumn<I_M_InOut, Object> COLUMN_IsExportedToCustomsInvoice = new ModelColumn<>(I_M_InOut.class, "IsExportedToCustomsInvoice", null);
	String COLUMNNAME_IsExportedToCustomsInvoice = "IsExportedToCustomsInvoice";

	/**
	 * Set FEC.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsFEC (boolean IsFEC);

	/**
	 * Get FEC.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isFEC();

	ModelColumn<I_M_InOut, Object> COLUMN_IsFEC = new ModelColumn<>(I_M_InOut.class, "IsFEC", null);
	String COLUMNNAME_IsFEC = "IsFEC";

	/**
	 * Set Fill up Spare Parts.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsFillUpSpareParts (boolean IsFillUpSpareParts);

	/**
	 * Get Fill up Spare Parts.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isFillUpSpareParts();

	ModelColumn<I_M_InOut, Object> COLUMN_IsFillUpSpareParts = new ModelColumn<>(I_M_InOut.class, "IsFillUpSpareParts", null);
	String COLUMNNAME_IsFillUpSpareParts = "IsFillUpSpareParts";

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

	ModelColumn<I_M_InOut, Object> COLUMN_IsInDispute = new ModelColumn<>(I_M_InOut.class, "IsInDispute", null);
	String COLUMNNAME_IsInDispute = "IsInDispute";

	/**
	 * Set Approve in/out shipment.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInOutApprovedForInvoicing (boolean IsInOutApprovedForInvoicing);

	/**
	 * Get Approve in/out shipment.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInOutApprovedForInvoicing();

	ModelColumn<I_M_InOut, Object> COLUMN_IsInOutApprovedForInvoicing = new ModelColumn<>(I_M_InOut.class, "IsInOutApprovedForInvoicing", null);
	String COLUMNNAME_IsInOutApprovedForInvoicing = "IsInOutApprovedForInvoicing";

	/**
	 * Set In Transit.
	 * Movement is in transit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInTransit (boolean IsInTransit);

	/**
	 * Get In Transit.
	 * Movement is in transit
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInTransit();

	ModelColumn<I_M_InOut, Object> COLUMN_IsInTransit = new ModelColumn<>(I_M_InOut.class, "IsInTransit", null);
	String COLUMNNAME_IsInTransit = "IsInTransit";

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

	ModelColumn<I_M_InOut, Object> COLUMN_IsPrinted = new ModelColumn<>(I_M_InOut.class, "IsPrinted", null);
	String COLUMNNAME_IsPrinted = "IsPrinted";

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

	ModelColumn<I_M_InOut, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_M_InOut.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

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

	ModelColumn<I_M_InOut, Object> COLUMN_IsUseBPartnerAddress = new ModelColumn<>(I_M_InOut.class, "IsUseBPartnerAddress", null);
	String COLUMNNAME_IsUseBPartnerAddress = "IsUseBPartnerAddress";

	/**
	 * Set Delivery Planning.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Delivery_Planning_ID (int M_Delivery_Planning_ID);

	/**
	 * Get Delivery Planning.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Delivery_Planning_ID();

	@Nullable org.compiere.model.I_M_Delivery_Planning getM_Delivery_Planning();

	void setM_Delivery_Planning(@Nullable org.compiere.model.I_M_Delivery_Planning M_Delivery_Planning);

	ModelColumn<I_M_InOut, org.compiere.model.I_M_Delivery_Planning> COLUMN_M_Delivery_Planning_ID = new ModelColumn<>(I_M_InOut.class, "M_Delivery_Planning_ID", org.compiere.model.I_M_Delivery_Planning.class);
	String COLUMNNAME_M_Delivery_Planning_ID = "M_Delivery_Planning_ID";

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_InOut_ID();

	ModelColumn<I_M_InOut, Object> COLUMN_M_InOut_ID = new ModelColumn<>(I_M_InOut.class, "M_InOut_ID", null);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Date.
	 * Date a product was moved in or out of inventory
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMovementDate (java.sql.Timestamp MovementDate);

	/**
	 * Get Date.
	 * Date a product was moved in or out of inventory
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getMovementDate();

	ModelColumn<I_M_InOut, Object> COLUMN_MovementDate = new ModelColumn<>(I_M_InOut.class, "MovementDate", null);
	String COLUMNNAME_MovementDate = "MovementDate";

	/**
	 * Set Movement Type.
	 * Method of moving the inventory
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMovementType (java.lang.String MovementType);

	/**
	 * Get Movement Type.
	 * Method of moving the inventory
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getMovementType();

	ModelColumn<I_M_InOut, Object> COLUMN_MovementType = new ModelColumn<>(I_M_InOut.class, "MovementType", null);
	String COLUMNNAME_MovementType = "MovementType";

	/**
	 * Set RMA.
	 * Return Material Authorization
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_RMA_ID (int M_RMA_ID);

	/**
	 * Get RMA.
	 * Return Material Authorization
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_RMA_ID();

	@Nullable org.compiere.model.I_M_RMA getM_RMA();

	void setM_RMA(@Nullable org.compiere.model.I_M_RMA M_RMA);

	ModelColumn<I_M_InOut, org.compiere.model.I_M_RMA> COLUMN_M_RMA_ID = new ModelColumn<>(I_M_InOut.class, "M_RMA_ID", org.compiere.model.I_M_RMA.class);
	String COLUMNNAME_M_RMA_ID = "M_RMA_ID";

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

	ModelColumn<I_M_InOut, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_M_InOut.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
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

	ModelColumn<I_M_InOut, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_M_InOut.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
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

	ModelColumn<I_M_InOut, Object> COLUMN_M_Tour_ID = new ModelColumn<>(I_M_InOut.class, "M_Tour_ID", null);
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
	 * Set Pick Date.
	 * Date/Time when picked for Shipment
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPickDate (@Nullable java.sql.Timestamp PickDate);

	/**
	 * Get Pick Date.
	 * Date/Time when picked for Shipment
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPickDate();

	ModelColumn<I_M_InOut, Object> COLUMN_PickDate = new ModelColumn<>(I_M_InOut.class, "PickDate", null);
	String COLUMNNAME_PickDate = "PickDate";

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOReference (@Nullable java.lang.String POReference);

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOReference();

	ModelColumn<I_M_InOut, Object> COLUMN_POReference = new ModelColumn<>(I_M_InOut.class, "POReference", null);
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

	ModelColumn<I_M_InOut, Object> COLUMN_Posted = new ModelColumn<>(I_M_InOut.class, "Posted", null);
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

	ModelColumn<I_M_InOut, Object> COLUMN_PriorityRule = new ModelColumn<>(I_M_InOut.class, "PriorityRule", null);
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

	ModelColumn<I_M_InOut, Object> COLUMN_Processed = new ModelColumn<>(I_M_InOut.class, "Processed", null);
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

	ModelColumn<I_M_InOut, Object> COLUMN_Processing = new ModelColumn<>(I_M_InOut.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Referenced Shipment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRef_InOut_ID (int Ref_InOut_ID);

	/**
	 * Get Referenced Shipment.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRef_InOut_ID();

	ModelColumn<I_M_InOut, Object> COLUMN_Ref_InOut_ID = new ModelColumn<>(I_M_InOut.class, "Ref_InOut_ID", null);
	String COLUMNNAME_Ref_InOut_ID = "Ref_InOut_ID";

	/**
	 * Set Reversal ID.
	 * ID of document reversal
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReversal_ID (int Reversal_ID);

	/**
	 * Get Reversal ID.
	 * ID of document reversal
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getReversal_ID();

	@Nullable org.compiere.model.I_M_InOut getReversal();

	void setReversal(@Nullable org.compiere.model.I_M_InOut Reversal);

	ModelColumn<I_M_InOut, org.compiere.model.I_M_InOut> COLUMN_Reversal_ID = new ModelColumn<>(I_M_InOut.class, "Reversal_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_Reversal_ID = "Reversal_ID";

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

	ModelColumn<I_M_InOut, Object> COLUMN_SendEMail = new ModelColumn<>(I_M_InOut.class, "SendEMail", null);
	String COLUMNNAME_SendEMail = "SendEMail";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_InOut, Object> COLUMN_Updated = new ModelColumn<>(I_M_InOut.class, "Updated", null);
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

	ModelColumn<I_M_InOut, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new ModelColumn<>(I_M_InOut.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
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

	ModelColumn<I_M_InOut, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new ModelColumn<>(I_M_InOut.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
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

	ModelColumn<I_M_InOut, Object> COLUMN_Volume = new ModelColumn<>(I_M_InOut.class, "Volume", null);
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

	ModelColumn<I_M_InOut, Object> COLUMN_Weight = new ModelColumn<>(I_M_InOut.class, "Weight", null);
	String COLUMNNAME_Weight = "Weight";
}
