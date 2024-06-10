package de.metas.invoicecandidate.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Invoice_Candidate
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Invoice_Candidate 
{

	String Table_Name = "C_Invoice_Candidate";

//	/** AD_Table_ID=540270 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Act Load Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setActualLoadingDate (@Nullable java.sql.Timestamp ActualLoadingDate);

	/**
	 * Get Act Load Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getActualLoadingDate();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_ActualLoadingDate = new ModelColumn<>(I_C_Invoice_Candidate.class, "ActualLoadingDate", null);
	String COLUMNNAME_ActualLoadingDate = "ActualLoadingDate";

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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_AD_InputDataSource_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "AD_InputDataSource_ID", null);
	String COLUMNNAME_AD_InputDataSource_ID = "AD_InputDataSource_ID";

	/**
	 * Set Note.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Note_ID (int AD_Note_ID);

	/**
	 * Get Note.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Note_ID();

	String COLUMNNAME_AD_Note_ID = "AD_Note_ID";

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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Responsible.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_InCharge_ID (int AD_User_InCharge_ID);

	/**
	 * Get Responsible.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_User_InCharge_ID();

	String COLUMNNAME_AD_User_InCharge_ID = "AD_User_InCharge_ID";

	/**
	 * Set Approved for Invoicing.
	 * After an invoice candidate has been approved for invoicing, its prices and taxes are no longer updated automatically from the corresponding purchase or sales order line.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setApprovalForInvoicing (boolean ApprovalForInvoicing);

	/**
	 * Get Approved for Invoicing.
	 * After an invoice candidate has been approved for invoicing, its prices and taxes are no longer updated automatically from the corresponding purchase or sales order line.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isApprovalForInvoicing();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_ApprovalForInvoicing = new ModelColumn<>(I_C_Invoice_Candidate.class, "ApprovalForInvoicing", null);
	String COLUMNNAME_ApprovalForInvoicing = "ApprovalForInvoicing";

	/**
	 * Set Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getBill_BPartner_ID();

	String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Set Name Bill Partner.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setBill_BPartner_Name (@Nullable java.lang.String Bill_BPartner_Name);

	/**
	 * Get Name Bill Partner.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getBill_BPartner_Name();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Bill_BPartner_Name = new ModelColumn<>(I_C_Invoice_Candidate.class, "Bill_BPartner_Name", null);
	String COLUMNNAME_Bill_BPartner_Name = "Bill_BPartner_Name";

	/**
	 * Set Bill Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBill_Location_ID (int Bill_Location_ID);

	/**
	 * Get Bill Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getBill_Location_ID();

	String COLUMNNAME_Bill_Location_ID = "Bill_Location_ID";

	/**
	 * Set Rechungsadresse abw..
	 * Standort des Geschäftspartners für die Rechnungsstellung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_Location_Override_ID (int Bill_Location_Override_ID);

	/**
	 * Get Rechungsadresse abw..
	 * Standort des Geschäftspartners für die Rechnungsstellung
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_Location_Override_ID();

	String COLUMNNAME_Bill_Location_Override_ID = "Bill_Location_Override_ID";

	/**
	 * Set Rechungsadresse abw..
	 * Standort des Geschäftspartners für die Rechnungsstellung
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_Location_Override_Value_ID (int Bill_Location_Override_Value_ID);

	/**
	 * Get Rechungsadresse abw..
	 * Standort des Geschäftspartners für die Rechnungsstellung
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_Location_Override_Value_ID();

	@Nullable org.compiere.model.I_C_Location getBill_Location_Override_Value();

	void setBill_Location_Override_Value(@Nullable org.compiere.model.I_C_Location Bill_Location_Override_Value);

	ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Location> COLUMN_Bill_Location_Override_Value_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "Bill_Location_Override_Value_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_Bill_Location_Override_Value_ID = "Bill_Location_Override_Value_ID";

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

	ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Location> COLUMN_Bill_Location_Value_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "Bill_Location_Value_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_Bill_Location_Value_ID = "Bill_Location_Value_ID";

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
	 * Set Rechnungskontakt abw..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_User_ID_Override_ID (int Bill_User_ID_Override_ID);

	/**
	 * Get Rechnungskontakt abw..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_User_ID_Override_ID();

	String COLUMNNAME_Bill_User_ID_Override_ID = "Bill_User_ID_Override_ID";

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
	 * Set Auction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Auction_ID (int C_Auction_ID);

	/**
	 * Get Auction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Auction_ID();

	@Nullable org.compiere.model.I_C_Auction getC_Auction();

	void setC_Auction(@Nullable org.compiere.model.I_C_Auction C_Auction);

	ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Auction> COLUMN_C_Auction_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_Auction_ID", org.compiere.model.I_C_Auction.class);
	String COLUMNNAME_C_Auction_ID = "C_Auction_ID";

	/**
	 * Set Business Partner (2).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner2_ID (int C_BPartner2_ID);

	/**
	 * Get Business Partner (2).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner2_ID();

	String COLUMNNAME_C_BPartner2_ID = "C_BPartner2_ID";

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
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Campaign_ID (int C_Campaign_ID);

	/**
	 * Get Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Campaign_ID();

	@Nullable org.compiere.model.I_C_Campaign getC_Campaign();

	void setC_Campaign(@Nullable org.compiere.model.I_C_Campaign C_Campaign);

	ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Costs.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Costs.
	 *
	 * <br>Type: Search
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set DocType Invoice.
	 * Document type used for invoices generated from this sales document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocTypeInvoice_ID (int C_DocTypeInvoice_ID);

	/**
	 * Get DocType Invoice.
	 * Document type used for invoices generated from this sales document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocTypeInvoice_ID();

	String COLUMNNAME_C_DocTypeInvoice_ID = "C_DocTypeInvoice_ID";

	/**
	 * Set Invoicing Pool.
	 * An invoicing pool is used to aggregate invoices and credit memos into a single document. It contains specific document types for aggregating positive invoice amounts (e.g., purchase invoice) and negative amounts (e.g., credit memo).
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_DocType_Invoicing_Pool_ID (int C_DocType_Invoicing_Pool_ID);

	/**
	 * Get Invoicing Pool.
	 * An invoicing pool is used to aggregate invoices and credit memos into a single document. It contains specific document types for aggregating positive invoice amounts (e.g., purchase invoice) and negative amounts (e.g., credit memo).
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	int getC_DocType_Invoicing_Pool_ID();

	@Nullable org.compiere.model.I_C_DocType_Invoicing_Pool getC_DocType_Invoicing_Pool();

	@Deprecated
	void setC_DocType_Invoicing_Pool(@Nullable org.compiere.model.I_C_DocType_Invoicing_Pool C_DocType_Invoicing_Pool);

	ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_DocType_Invoicing_Pool> COLUMN_C_DocType_Invoicing_Pool_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_DocType_Invoicing_Pool_ID", org.compiere.model.I_C_DocType_Invoicing_Pool.class);
	String COLUMNNAME_C_DocType_Invoicing_Pool_ID = "C_DocType_Invoicing_Pool_ID";

	/**
	 * Set Flatrate Term.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Term_ID (int C_Flatrate_Term_ID);

	/**
	 * Get Flatrate Term.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Term_ID();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_C_Flatrate_Term_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_Flatrate_Term_ID", null);
	String COLUMNNAME_C_Flatrate_Term_ID = "C_Flatrate_Term_ID";

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

	ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Calendar> COLUMN_C_Harvesting_Calendar_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_Harvesting_Calendar_ID", org.compiere.model.I_C_Calendar.class);
	String COLUMNNAME_C_Harvesting_Calendar_ID = "C_Harvesting_Calendar_ID";

	/**
	 * Set Invoicecandidate Controller.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_ILCandHandler_ID (int C_ILCandHandler_ID);

	/**
	 * Get Invoicecandidate Controller.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_ILCandHandler_ID();

	de.metas.invoicecandidate.model.I_C_ILCandHandler getC_ILCandHandler();

	void setC_ILCandHandler(de.metas.invoicecandidate.model.I_C_ILCandHandler C_ILCandHandler);

	ModelColumn<I_C_Invoice_Candidate, de.metas.invoicecandidate.model.I_C_ILCandHandler> COLUMN_C_ILCandHandler_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_ILCandHandler_ID", de.metas.invoicecandidate.model.I_C_ILCandHandler.class);
	String COLUMNNAME_C_ILCandHandler_ID = "C_ILCandHandler_ID";

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

	ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Incoterms> COLUMN_C_Incoterms_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_Incoterms_ID", org.compiere.model.I_C_Incoterms.class);
	String COLUMNNAME_C_Incoterms_ID = "C_Incoterms_ID";

	/**
	 * Set Aggregator.
	 * Definiert Richtlinien zur Aggregation von Datensätzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Candidate_Agg_ID (int C_Invoice_Candidate_Agg_ID);

	/**
	 * Get Aggregator.
	 * Definiert Richtlinien zur Aggregation von Datensätzen mit ggf. unterschiedlichen Produkten zu einem einzigen Datensatz
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Candidate_Agg_ID();

	@Nullable de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg getC_Invoice_Candidate_Agg();

	void setC_Invoice_Candidate_Agg(@Nullable de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg C_Invoice_Candidate_Agg);

	ModelColumn<I_C_Invoice_Candidate, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg> COLUMN_C_Invoice_Candidate_Agg_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_Invoice_Candidate_Agg_ID", de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg.class);
	String COLUMNNAME_C_Invoice_Candidate_Agg_ID = "C_Invoice_Candidate_Agg_ID";

	/**
	 * Set Aggregation Group eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Candidate_HeaderAggregation_Effective_ID (int C_Invoice_Candidate_HeaderAggregation_Effective_ID);

	/**
	 * Get Aggregation Group eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Candidate_HeaderAggregation_Effective_ID();

	@Nullable de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation getC_Invoice_Candidate_HeaderAggregation_Effective();

	void setC_Invoice_Candidate_HeaderAggregation_Effective(@Nullable de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation C_Invoice_Candidate_HeaderAggregation_Effective);

	ModelColumn<I_C_Invoice_Candidate, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation> COLUMN_C_Invoice_Candidate_HeaderAggregation_Effective_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_Invoice_Candidate_HeaderAggregation_Effective_ID", de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation.class);
	String COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Effective_ID = "C_Invoice_Candidate_HeaderAggregation_Effective_ID";

	/**
	 * Set Aggregration Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Candidate_HeaderAggregation_ID (int C_Invoice_Candidate_HeaderAggregation_ID);

	/**
	 * Get Aggregration Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Candidate_HeaderAggregation_ID();

	@Nullable de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation getC_Invoice_Candidate_HeaderAggregation();

	void setC_Invoice_Candidate_HeaderAggregation(@Nullable de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation C_Invoice_Candidate_HeaderAggregation);

	ModelColumn<I_C_Invoice_Candidate, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation> COLUMN_C_Invoice_Candidate_HeaderAggregation_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_Invoice_Candidate_HeaderAggregation_ID", de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation.class);
	String COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_ID = "C_Invoice_Candidate_HeaderAggregation_ID";

	/**
	 * Set Header Aggregation override.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Candidate_HeaderAggregation_Override_ID (int C_Invoice_Candidate_HeaderAggregation_Override_ID);

	/**
	 * Get Header Aggregation override.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Candidate_HeaderAggregation_Override_ID();

	@Nullable de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation getC_Invoice_Candidate_HeaderAggregation_Override();

	void setC_Invoice_Candidate_HeaderAggregation_Override(@Nullable de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation C_Invoice_Candidate_HeaderAggregation_Override);

	ModelColumn<I_C_Invoice_Candidate, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation> COLUMN_C_Invoice_Candidate_HeaderAggregation_Override_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_Invoice_Candidate_HeaderAggregation_Override_ID", de.metas.invoicecandidate.model.I_C_Invoice_Candidate_HeaderAggregation.class);
	String COLUMNNAME_C_Invoice_Candidate_HeaderAggregation_Override_ID = "C_Invoice_Candidate_HeaderAggregation_Override_ID";

	/**
	 * Set Invoice candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Candidate_ID (int C_Invoice_Candidate_ID);

	/**
	 * Get Invoice candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Candidate_ID();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_C_Invoice_Candidate_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_Invoice_Candidate_ID", null);
	String COLUMNNAME_C_Invoice_Candidate_ID = "C_Invoice_Candidate_ID";

	/**
	 * Set Invoice Schedule.
	 * Schedule for generating Invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_InvoiceSchedule_ID (int C_InvoiceSchedule_ID);

	/**
	 * Get Invoice Schedule.
	 * Schedule for generating Invoices
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_InvoiceSchedule_ID();

	@Nullable org.compiere.model.I_C_InvoiceSchedule getC_InvoiceSchedule();

	void setC_InvoiceSchedule(@Nullable org.compiere.model.I_C_InvoiceSchedule C_InvoiceSchedule);

	ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_InvoiceSchedule> COLUMN_C_InvoiceSchedule_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_InvoiceSchedule_ID", org.compiere.model.I_C_InvoiceSchedule.class);
	String COLUMNNAME_C_InvoiceSchedule_ID = "C_InvoiceSchedule_ID";

	/**
	 * Set Order Partner.
	 * The partner from C_Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_Order_BPartner (int C_Order_BPartner);

	/**
	 * Get Order Partner.
	 * The partner from C_Order
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getC_Order_BPartner();

	String COLUMNNAME_C_Order_BPartner = "C_Order_BPartner";

	/**
	 * Set Order Compensation Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Order_CompensationGroup_ID (int C_Order_CompensationGroup_ID);

	/**
	 * Get Order Compensation Group.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Order_CompensationGroup_ID();

	@Nullable org.compiere.model.I_C_Order_CompensationGroup getC_Order_CompensationGroup();

	void setC_Order_CompensationGroup(@Nullable org.compiere.model.I_C_Order_CompensationGroup C_Order_CompensationGroup);

	ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Order_CompensationGroup> COLUMN_C_Order_CompensationGroup_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_Order_CompensationGroup_ID", org.compiere.model.I_C_Order_CompensationGroup.class);
	String COLUMNNAME_C_Order_CompensationGroup_ID = "C_Order_CompensationGroup_ID";

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

	ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderLine_ID (int C_OrderLine_ID);

	/**
	 * Get Orderline.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderLine_ID();

	@Nullable org.compiere.model.I_C_OrderLine getC_OrderLine();

	void setC_OrderLine(@Nullable org.compiere.model.I_C_OrderLine C_OrderLine);

	ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_OrderLine> COLUMN_C_OrderLine_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_OrderLine_ID", org.compiere.model.I_C_OrderLine.class);
	String COLUMNNAME_C_OrderLine_ID = "C_OrderLine_ID";

	/**
	 * Set Sales Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_OrderSO_ID (int C_OrderSO_ID);

	/**
	 * Get Sales Order.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_OrderSO_ID();

	@Nullable org.compiere.model.I_C_Order getC_OrderSO();

	void setC_OrderSO(@Nullable org.compiere.model.I_C_Order C_OrderSO);

	ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Order> COLUMN_C_OrderSO_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_OrderSO_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_OrderSO_ID = "C_OrderSO_ID";

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

	ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_PaymentInstruction> COLUMN_C_PaymentInstruction_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_PaymentInstruction_ID", org.compiere.model.I_C_PaymentInstruction.class);
	String COLUMNNAME_C_PaymentInstruction_ID = "C_PaymentInstruction_ID";

	/**
	 * Set Zahlungsbedingung eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_PaymentTerm_Effective_ID (int C_PaymentTerm_Effective_ID);

	/**
	 * Get Zahlungsbedingung eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	int getC_PaymentTerm_Effective_ID();

	String COLUMNNAME_C_PaymentTerm_Effective_ID = "C_PaymentTerm_Effective_ID";

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
	 * Set Zahlungsbedingung abw..
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_PaymentTerm_Override_ID (int C_PaymentTerm_Override_ID);

	/**
	 * Get Zahlungsbedingung abw..
	 * Die Bedingungen für die Bezahlung dieses Vorgangs
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_PaymentTerm_Override_ID();

	String COLUMNNAME_C_PaymentTerm_Override_ID = "C_PaymentTerm_Override_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Created = new ModelColumn<>(I_C_Invoice_Candidate.class, "Created", null);
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
	 * Set Shipping Location.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Shipping_Location_ID (int C_Shipping_Location_ID);

	/**
	 * Get Shipping Location.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Shipping_Location_ID();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_C_Shipping_Location_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_Shipping_Location_ID", null);
	String COLUMNNAME_C_Shipping_Location_ID = "C_Shipping_Location_ID";

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

	ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Country> COLUMN_C_Tax_Departure_Country_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_Tax_Departure_Country_ID", org.compiere.model.I_C_Country.class);
	String COLUMNNAME_C_Tax_Departure_Country_ID = "C_Tax_Departure_Country_ID";

	/**
	 * Set Tax eff..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_Tax_Effective_ID (int C_Tax_Effective_ID);

	/**
	 * Get Tax eff..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	int getC_Tax_Effective_ID();

	String COLUMNNAME_C_Tax_Effective_ID = "C_Tax_Effective_ID";

	/**
	 * Set Tax.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Tax_ID (int C_Tax_ID);

	/**
	 * Get Tax.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Tax_ID();

	String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/**
	 * Set Tax override.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Tax_Override_ID (int C_Tax_Override_ID);

	/**
	 * Get Tax override.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Tax_Override_ID();

	String COLUMNNAME_C_Tax_Override_ID = "C_Tax_Override_ID";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set VAT Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_VAT_Code_ID (int C_VAT_Code_ID);

	/**
	 * Get VAT Code.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_VAT_Code_ID();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_C_VAT_Code_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_VAT_Code_ID", null);
	String COLUMNNAME_C_VAT_Code_ID = "C_VAT_Code_ID";

	/**
	 * Set VAT Code override.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_VAT_Code_Override_ID (int C_VAT_Code_Override_ID);

	/**
	 * Get VAT Code override.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_VAT_Code_Override_ID();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_C_VAT_Code_Override_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "C_VAT_Code_Override_ID", null);
	String COLUMNNAME_C_VAT_Code_Override_ID = "C_VAT_Code_Override_ID";

	/**
	 * Set Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateAcct (@Nullable java.sql.Timestamp DateAcct);

	/**
	 * Get Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateAcct();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DateAcct = new ModelColumn<>(I_C_Invoice_Candidate.class, "DateAcct", null);
	String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Date.
	 * Date printed on Invoice
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateInvoiced (@Nullable java.sql.Timestamp DateInvoiced);

	/**
	 * Get Date.
	 * Date printed on Invoice
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateInvoiced();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DateInvoiced = new ModelColumn<>(I_C_Invoice_Candidate.class, "DateInvoiced", null);
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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DateOrdered = new ModelColumn<>(I_C_Invoice_Candidate.class, "DateOrdered", null);
	String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Date Promised From.
	 * Date Order was promised
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setDatePromised (@Nullable java.sql.Timestamp DatePromised);

	/**
	 * Get Date Promised From.
	 * Date Order was promised
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.sql.Timestamp getDatePromised();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DatePromised = new ModelColumn<>(I_C_Invoice_Candidate.class, "DatePromised", null);
	String COLUMNNAME_DatePromised = "DatePromised";

	/**
	 * Set Date Invoice from.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateToInvoice (@Nullable java.sql.Timestamp DateToInvoice);

	/**
	 * Get Date Invoice from.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateToInvoice();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DateToInvoice = new ModelColumn<>(I_C_Invoice_Candidate.class, "DateToInvoice", null);
	String COLUMNNAME_DateToInvoice = "DateToInvoice";

	/**
	 * Set Date Invoice from eff..
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setDateToInvoice_Effective (@Nullable java.sql.Timestamp DateToInvoice_Effective);

	/**
	 * Get Date Invoice from eff..
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.sql.Timestamp getDateToInvoice_Effective();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DateToInvoice_Effective = new ModelColumn<>(I_C_Invoice_Candidate.class, "DateToInvoice_Effective", null);
	String COLUMNNAME_DateToInvoice_Effective = "DateToInvoice_Effective";

	/**
	 * Set Date Invoice from override.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateToInvoice_Override (@Nullable java.sql.Timestamp DateToInvoice_Override);

	/**
	 * Get Date Invoice from override.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateToInvoice_Override();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DateToInvoice_Override = new ModelColumn<>(I_C_Invoice_Candidate.class, "DateToInvoice_Override", null);
	String COLUMNNAME_DateToInvoice_Override = "DateToInvoice_Override";

	/**
	 * Set Shipmentdate.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryDate (@Nullable java.sql.Timestamp DeliveryDate);

	/**
	 * Get Shipmentdate.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDeliveryDate();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DeliveryDate = new ModelColumn<>(I_C_Invoice_Candidate.class, "DeliveryDate", null);
	String COLUMNNAME_DeliveryDate = "DeliveryDate";

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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Description = new ModelColumn<>(I_C_Invoice_Candidate.class, "Description", null);
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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DescriptionBottom = new ModelColumn<>(I_C_Invoice_Candidate.class, "DescriptionBottom", null);
	String COLUMNNAME_DescriptionBottom = "DescriptionBottom";

	/**
	 * Set Description (GL Journal).
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescriptionHeader (@Nullable java.lang.String DescriptionHeader);

	/**
	 * Get Description (GL Journal).
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescriptionHeader();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_DescriptionHeader = new ModelColumn<>(I_C_Invoice_Candidate.class, "DescriptionHeader", null);
	String COLUMNNAME_DescriptionHeader = "DescriptionHeader";

	/**
	 * Set Discount %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDiscount (BigDecimal Discount);

	/**
	 * Get Discount %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscount();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Discount = new ModelColumn<>(I_C_Invoice_Candidate.class, "Discount", null);
	String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Discount override %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscount_Override (@Nullable BigDecimal Discount_Override);

	/**
	 * Get Discount override %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscount_Override();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Discount_Override = new ModelColumn<>(I_C_Invoice_Candidate.class, "Discount_Override", null);
	String COLUMNNAME_Discount_Override = "Discount_Override";

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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_EMail = new ModelColumn<>(I_C_Invoice_Candidate.class, "EMail", null);
	String COLUMNNAME_EMail = "EMail";

	/**
	 * Set Error Message.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setErrorMsg (@Nullable java.lang.String ErrorMsg);

	/**
	 * Get Error Message.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getErrorMsg();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_ErrorMsg = new ModelColumn<>(I_C_Invoice_Candidate.class, "ErrorMsg", null);
	String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/**
	 * Set External Header ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalHeaderId (@Nullable java.lang.String ExternalHeaderId);

	/**
	 * Get External Header ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalHeaderId();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_ExternalHeaderId = new ModelColumn<>(I_C_Invoice_Candidate.class, "ExternalHeaderId", null);
	String COLUMNNAME_ExternalHeaderId = "ExternalHeaderId";

	/**
	 * Set External Line ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalLineId (@Nullable java.lang.String ExternalLineId);

	/**
	 * Get External Line ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalLineId();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_ExternalLineId = new ModelColumn<>(I_C_Invoice_Candidate.class, "ExternalLineId", null);
	String COLUMNNAME_ExternalLineId = "ExternalLineId";

	/**
	 * Set Ship Location.
	 * BParter location of primary shipment/receipt
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFirst_Ship_BPLocation_ID (int First_Ship_BPLocation_ID);

	/**
	 * Get Ship Location.
	 * BParter location of primary shipment/receipt
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getFirst_Ship_BPLocation_ID();

	String COLUMNNAME_First_Ship_BPLocation_ID = "First_Ship_BPLocation_ID";

	/**
	 * Set Compensation Amount Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGroupCompensationAmtType (@Nullable java.lang.String GroupCompensationAmtType);

	/**
	 * Get Compensation Amount Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGroupCompensationAmtType();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_GroupCompensationAmtType = new ModelColumn<>(I_C_Invoice_Candidate.class, "GroupCompensationAmtType", null);
	String COLUMNNAME_GroupCompensationAmtType = "GroupCompensationAmtType";

	/**
	 * Set Compensation base amount.
	 * Base amount for calculating percentage group compensation
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGroupCompensationBaseAmt (@Nullable BigDecimal GroupCompensationBaseAmt);

	/**
	 * Get Compensation base amount.
	 * Base amount for calculating percentage group compensation
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getGroupCompensationBaseAmt();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_GroupCompensationBaseAmt = new ModelColumn<>(I_C_Invoice_Candidate.class, "GroupCompensationBaseAmt", null);
	String COLUMNNAME_GroupCompensationBaseAmt = "GroupCompensationBaseAmt";

	/**
	 * Set Compensation percentage.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGroupCompensationPercentage (@Nullable BigDecimal GroupCompensationPercentage);

	/**
	 * Get Compensation percentage.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getGroupCompensationPercentage();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_GroupCompensationPercentage = new ModelColumn<>(I_C_Invoice_Candidate.class, "GroupCompensationPercentage", null);
	String COLUMNNAME_GroupCompensationPercentage = "GroupCompensationPercentage";

	/**
	 * Set Compensation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGroupCompensationType (@Nullable java.lang.String GroupCompensationType);

	/**
	 * Get Compensation Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGroupCompensationType();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_GroupCompensationType = new ModelColumn<>(I_C_Invoice_Candidate.class, "GroupCompensationType", null);
	String COLUMNNAME_GroupCompensationType = "GroupCompensationType";

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

	ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_C_Year> COLUMN_Harvesting_Year_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "Harvesting_Year_ID", org.compiere.model.I_C_Year.class);
	String COLUMNNAME_Harvesting_Year_ID = "Harvesting_Year_ID";

	/**
	 * Set Header  merge characteristic.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHeaderAggregationKey (@Nullable java.lang.String HeaderAggregationKey);

	/**
	 * Get Header  merge characteristic.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHeaderAggregationKey();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_HeaderAggregationKey = new ModelColumn<>(I_C_Invoice_Candidate.class, "HeaderAggregationKey", null);
	String COLUMNNAME_HeaderAggregationKey = "HeaderAggregationKey";

	/**
	 * Set Header aggregation builder.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHeaderAggregationKeyBuilder_ID (int HeaderAggregationKeyBuilder_ID);

	/**
	 * Get Header aggregation builder.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getHeaderAggregationKeyBuilder_ID();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_HeaderAggregationKeyBuilder_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "HeaderAggregationKeyBuilder_ID", null);
	String COLUMNNAME_HeaderAggregationKeyBuilder_ID = "HeaderAggregationKeyBuilder_ID";

	/**
	 * Set Header Aggregation Preset.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHeaderAggregationKey_Calc (@Nullable java.lang.String HeaderAggregationKey_Calc);

	/**
	 * Get Header Aggregation Preset.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHeaderAggregationKey_Calc();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_HeaderAggregationKey_Calc = new ModelColumn<>(I_C_Invoice_Candidate.class, "HeaderAggregationKey_Calc", null);
	String COLUMNNAME_HeaderAggregationKey_Calc = "HeaderAggregationKey_Calc";

	/**
	 * Set Incoterm Location.
	 * Anzugebender Ort für Handelsklausel
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIncotermLocation (@Nullable java.lang.String IncotermLocation);

	/**
	 * Get Incoterm Location.
	 * Anzugebender Ort für Handelsklausel
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIncotermLocation();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IncotermLocation = new ModelColumn<>(I_C_Invoice_Candidate.class, "IncotermLocation", null);
	String COLUMNNAME_IncotermLocation = "IncotermLocation";

	/**
	 * Set Invoicable Quantity per.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setInvoicableQtyBasedOn (java.lang.String InvoicableQtyBasedOn);

	/**
	 * Get Invoicable Quantity per.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getInvoicableQtyBasedOn();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_InvoicableQtyBasedOn = new ModelColumn<>(I_C_Invoice_Candidate.class, "InvoicableQtyBasedOn", null);
	String COLUMNNAME_InvoicableQtyBasedOn = "InvoicableQtyBasedOn";

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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_InvoiceAdditionalText = new ModelColumn<>(I_C_Invoice_Candidate.class, "InvoiceAdditionalText", null);
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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_InvoiceRule = new ModelColumn<>(I_C_Invoice_Candidate.class, "InvoiceRule", null);
	String COLUMNNAME_InvoiceRule = "InvoiceRule";

	/**
	 * Set Invoice Rule eff..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setInvoiceRule_Effective (@Nullable java.lang.String InvoiceRule_Effective);

	/**
	 * Get Invoice Rule eff..
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getInvoiceRule_Effective();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_InvoiceRule_Effective = new ModelColumn<>(I_C_Invoice_Candidate.class, "InvoiceRule_Effective", null);
	String COLUMNNAME_InvoiceRule_Effective = "InvoiceRule_Effective";

	/**
	 * Set Invoice Rule override.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoiceRule_Override (@Nullable java.lang.String InvoiceRule_Override);

	/**
	 * Get Invoice Rule override.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInvoiceRule_Override();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_InvoiceRule_Override = new ModelColumn<>(I_C_Invoice_Candidate.class, "InvoiceRule_Override", null);
	String COLUMNNAME_InvoiceRule_Override = "InvoiceRule_Override";

	/**
	 * Set Invoice Schedule Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoiceScheduleAmtStatus (@Nullable java.lang.String InvoiceScheduleAmtStatus);

	/**
	 * Get Invoice Schedule Status.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInvoiceScheduleAmtStatus();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_InvoiceScheduleAmtStatus = new ModelColumn<>(I_C_Invoice_Candidate.class, "InvoiceScheduleAmtStatus", null);
	String COLUMNNAME_InvoiceScheduleAmtStatus = "InvoiceScheduleAmtStatus";

	/**
	 * Set Error message.
	 * Error that occured while metasfresh tried to invoice this record.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoicingErrorMsg (@Nullable java.lang.String InvoicingErrorMsg);

	/**
	 * Get Error message.
	 * Error that occured while metasfresh tried to invoice this record.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInvoicingErrorMsg();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_InvoicingErrorMsg = new ModelColumn<>(I_C_Invoice_Candidate.class, "InvoicingErrorMsg", null);
	String COLUMNNAME_InvoicingErrorMsg = "InvoicingErrorMsg";

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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Delivery Closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDeliveryClosed (boolean IsDeliveryClosed);

	/**
	 * Get Delivery Closed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDeliveryClosed();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsDeliveryClosed = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsDeliveryClosed", null);
	String COLUMNNAME_IsDeliveryClosed = "IsDeliveryClosed";

	/**
	 * Set EDI INVOIC Receipient.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsEdiInvoicRecipient (boolean IsEdiInvoicRecipient);

	/**
	 * Get EDI INVOIC Receipient.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEdiInvoicRecipient();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsEdiInvoicRecipient = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsEdiInvoicRecipient", null);
	String COLUMNNAME_IsEdiInvoicRecipient = "IsEdiInvoicRecipient";

	/**
	 * Set Error.
	 * An Error occurred in the execution
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsError (boolean IsError);

	/**
	 * Get Error.
	 * An Error occurred in the execution
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isError();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsError = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsError", null);
	String COLUMNNAME_IsError = "IsError";

	/**
	 * Set IsFreightCost.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsFreightCost (boolean IsFreightCost);

	/**
	 * Get IsFreightCost.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isFreightCost();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsFreightCost = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsFreightCost", null);
	String COLUMNNAME_IsFreightCost = "IsFreightCost";

	/**
	 * Set Group Discount Line.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsGroupCompensationLine (boolean IsGroupCompensationLine);

	/**
	 * Get Group Discount Line.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isGroupCompensationLine();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsGroupCompensationLine = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsGroupCompensationLine", null);
	String COLUMNNAME_IsGroupCompensationLine = "IsGroupCompensationLine";

	/**
	 * Set In Dispute.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsInDispute (boolean IsInDispute);

	/**
	 * Get In Dispute.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isInDispute();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsInDispute = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsInDispute", null);
	String COLUMNNAME_IsInDispute = "IsInDispute";

	/**
	 * Set In Effect.
	 * Invoice candidates that are not in effect are no longer eligible for invoicing. Normally, an invoice candidates goes out of effect when the source document is reopened.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInEffect (boolean IsInEffect);

	/**
	 * Get In Effect.
	 * Invoice candidates that are not in effect are no longer eligible for invoicing. Normally, an invoice candidates goes out of effect when the source document is reopened.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInEffect();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsInEffect = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsInEffect", null);
	String COLUMNNAME_IsInEffect = "IsInEffect";

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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsInOutApprovedForInvoicing = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsInOutApprovedForInvoicing", null);
	String COLUMNNAME_IsInOutApprovedForInvoicing = "IsInOutApprovedForInvoicing";

	/**
	 * Set Invoicing error.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsInvoicingError (boolean IsInvoicingError);

	/**
	 * Get Invoicing error.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isInvoicingError();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsInvoicingError = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsInvoicingError", null);
	String COLUMNNAME_IsInvoicingError = "IsInvoicingError";

	/**
	 * Set Invoice manually allocated.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManual (boolean IsManual);

	/**
	 * Get Invoice manually allocated.
	 * Dies ist ein manueller Vorgang
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManual();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsManual = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsManual", null);
	String COLUMNNAME_IsManual = "IsManual";

	/**
	 * Set Material Tracking.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsMaterialTracking (boolean IsMaterialTracking);

	/**
	 * Get Material Tracking.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isMaterialTracking();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsMaterialTracking = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsMaterialTracking", null);
	String COLUMNNAME_IsMaterialTracking = "IsMaterialTracking";

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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsNotShowOriginCountry = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsNotShowOriginCountry", null);
	String COLUMNNAME_IsNotShowOriginCountry = "IsNotShowOriginCountry";

	/**
	 * Set Packaging Material .
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPackagingMaterial (boolean IsPackagingMaterial);

	/**
	 * Get Packaging Material .
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPackagingMaterial();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsPackagingMaterial = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsPackagingMaterial", null);
	String COLUMNNAME_IsPackagingMaterial = "IsPackagingMaterial";

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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsPrinted = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsPrinted", null);
	String COLUMNNAME_IsPrinted = "IsPrinted";

	/**
	 * Set Simulation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSimulation (boolean IsSimulation);

	/**
	 * Get Simulation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSimulation();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsSimulation = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsSimulation", null);
	String COLUMNNAME_IsSimulation = "IsSimulation";

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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Tax Included.
	 * Tax Included
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsTaxIncluded (boolean IsTaxIncluded);

	/**
	 * Get Tax Included.
	 * Tax Included
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isTaxIncluded();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsTaxIncluded = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsTaxIncluded", null);
	String COLUMNNAME_IsTaxIncluded = "IsTaxIncluded";

	/**
	 * Set Price incl. Tax override.
	 * Tax is included in the price
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsTaxIncluded_Override (@Nullable java.lang.String IsTaxIncluded_Override);

	/**
	 * Get Price incl. Tax override.
	 * Tax is included in the price
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsTaxIncluded_Override();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsTaxIncluded_Override = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsTaxIncluded_Override", null);
	String COLUMNNAME_IsTaxIncluded_Override = "IsTaxIncluded_Override";

	/**
	 * Set To Clear.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsToClear (boolean IsToClear);

	/**
	 * Get To Clear.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isToClear();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsToClear = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsToClear", null);
	String COLUMNNAME_IsToClear = "IsToClear";

	/**
	 * Set Recompute.
	 * Wert wird bei einer Benutzer-Änderung am Rechnungskandidaten vom System automatisch auf "Ja" gesetzt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsToRecompute (boolean IsToRecompute);

	/**
	 * Get Recompute.
	 * Wert wird bei einer Benutzer-Änderung am Rechnungskandidaten vom System automatisch auf "Ja" gesetzt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	boolean isToRecompute();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_IsToRecompute = new ModelColumn<>(I_C_Invoice_Candidate.class, "IsToRecompute", null);
	String COLUMNNAME_IsToRecompute = "IsToRecompute";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLine();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Line = new ModelColumn<>(I_C_Invoice_Candidate.class, "Line", null);
	String COLUMNNAME_Line = "Line";

	/**
	 * Set Zeilen-Aggregationsmerkmal.
	 * Wird vom System gesetzt und legt fest, welche Kandidaten zu einer Rechnungszeile zusammen gefasst (aggregiert) werden sollen.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLineAggregationKey (@Nullable java.lang.String LineAggregationKey);

	/**
	 * Get Zeilen-Aggregationsmerkmal.
	 * Wird vom System gesetzt und legt fest, welche Kandidaten zu einer Rechnungszeile zusammen gefasst (aggregiert) werden sollen.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLineAggregationKey();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_LineAggregationKey = new ModelColumn<>(I_C_Invoice_Candidate.class, "LineAggregationKey", null);
	String COLUMNNAME_LineAggregationKey = "LineAggregationKey";

	/**
	 * Set Line aggregation builder.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLineAggregationKeyBuilder_ID (int LineAggregationKeyBuilder_ID);

	/**
	 * Get Line aggregation builder.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLineAggregationKeyBuilder_ID();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_LineAggregationKeyBuilder_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "LineAggregationKeyBuilder_ID", null);
	String COLUMNNAME_LineAggregationKeyBuilder_ID = "LineAggregationKeyBuilder_ID";

	/**
	 * Set Agrregation Suffix.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLineAggregationKey_Suffix (@Nullable java.lang.String LineAggregationKey_Suffix);

	/**
	 * Get Agrregation Suffix.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLineAggregationKey_Suffix();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_LineAggregationKey_Suffix = new ModelColumn<>(I_C_Invoice_Candidate.class, "LineAggregationKey_Suffix", null);
	String COLUMNNAME_LineAggregationKey_Suffix = "LineAggregationKey_Suffix";

	/**
	 * Set Line Net Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLineNetAmt (@Nullable BigDecimal LineNetAmt);

	/**
	 * Get Line Net Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getLineNetAmt();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_LineNetAmt = new ModelColumn<>(I_C_Invoice_Candidate.class, "LineNetAmt", null);
	String COLUMNNAME_LineNetAmt = "LineNetAmt";

	/**
	 * Set Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID);

	/**
	 * Get Attributes.
	 * Attribute Instances for Products
	 *
	 * <br>Type: PAttribute
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSetInstance_ID();

	@Nullable org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance();

	void setM_AttributeSetInstance(@Nullable org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance);

	ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_InOut_ID (int M_InOut_ID);

	/**
	 * Get Shipment/ Receipt.
	 * Material Shipment Document
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_InOut_ID();

	@Nullable org.compiere.model.I_M_InOut getM_InOut();

	void setM_InOut(@Nullable org.compiere.model.I_M_InOut M_InOut);

	ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_M_InOut> COLUMN_M_InOut_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "M_InOut_ID", org.compiere.model.I_M_InOut.class);
	String COLUMNNAME_M_InOut_ID = "M_InOut_ID";

	/**
	 * Set Modules.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setModCntr_Module_ID (int ModCntr_Module_ID);

	/**
	 * Get Modules.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getModCntr_Module_ID();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_ModCntr_Module_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "ModCntr_Module_ID", null);
	String COLUMNNAME_ModCntr_Module_ID = "ModCntr_Module_ID";

	/**
	 * Set Price List Version.
	 * Identifies a unique instance of a Price List
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_PriceList_Version_ID (int M_PriceList_Version_ID);

	/**
	 * Get Price List Version.
	 * Identifies a unique instance of a Price List
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_PriceList_Version_ID();

	String COLUMNNAME_M_PriceList_Version_ID = "M_PriceList_Version_ID";

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
	 * Set Product Category.
	 * Category of a Product
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Product Category.
	 * Category of a Product
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getM_Product_Category_ID();

	String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

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
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_SectionCode_ID (int M_SectionCode_ID);

	/**
	 * Get Section Code.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_SectionCode_ID();

	@Nullable org.compiere.model.I_M_SectionCode getM_SectionCode();

	void setM_SectionCode(@Nullable org.compiere.model.I_M_SectionCode M_SectionCode);

	ModelColumn<I_C_Invoice_Candidate, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set Shipment Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID);

	/**
	 * Get Shipment Candidate.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_ShipmentSchedule_ID();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_M_ShipmentSchedule_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "M_ShipmentSchedule_ID", null);
	String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";

	/**
	 * Set Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_ID (int M_Warehouse_ID);

	/**
	 * Get Warehouse.
	 * Storage Warehouse and Service Point
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_ID();

	String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/**
	 * Set Invoiced Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNetAmtInvoiced (@Nullable BigDecimal NetAmtInvoiced);

	/**
	 * Get Invoiced Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getNetAmtInvoiced();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_NetAmtInvoiced = new ModelColumn<>(I_C_Invoice_Candidate.class, "NetAmtInvoiced", null);
	String COLUMNNAME_NetAmtInvoiced = "NetAmtInvoiced";

	/**
	 * Set Net Amount to Invoice.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNetAmtToInvoice (@Nullable BigDecimal NetAmtToInvoice);

	/**
	 * Get Net Amount to Invoice.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getNetAmtToInvoice();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_NetAmtToInvoice = new ModelColumn<>(I_C_Invoice_Candidate.class, "NetAmtToInvoice", null);
	String COLUMNNAME_NetAmtToInvoice = "NetAmtToInvoice";

	/**
	 * Set Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNote (@Nullable java.lang.String Note);

	/**
	 * Get Note.
	 * Optional additional user defined information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNote();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Note = new ModelColumn<>(I_C_Invoice_Candidate.class, "Note", null);
	String COLUMNNAME_Note = "Note";

	/**
	 * Set Packing Material.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setpackingmaterialname (@Nullable java.lang.String packingmaterialname);

	/**
	 * Get Packing Material.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getpackingmaterialname();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_packingmaterialname = new ModelColumn<>(I_C_Invoice_Candidate.class, "packingmaterialname", null);
	String COLUMNNAME_packingmaterialname = "packingmaterialname";

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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_PaymentRule = new ModelColumn<>(I_C_Invoice_Candidate.class, "PaymentRule", null);
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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_POReference = new ModelColumn<>(I_C_Invoice_Candidate.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Preset Date Invoiced.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPresetDateInvoiced (@Nullable java.sql.Timestamp PresetDateInvoiced);

	/**
	 * Get Preset Date Invoiced.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPresetDateInvoiced();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_PresetDateInvoiced = new ModelColumn<>(I_C_Invoice_Candidate.class, "PresetDateInvoiced", null);
	String COLUMNNAME_PresetDateInvoiced = "PresetDateInvoiced";

	/**
	 * Set Price Actual.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceActual (@Nullable BigDecimal PriceActual);

	/**
	 * Get Price Actual.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceActual();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_PriceActual = new ModelColumn<>(I_C_Invoice_Candidate.class, "PriceActual", null);
	String COLUMNNAME_PriceActual = "PriceActual";

	/**
	 * Set Price net eff..
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceActual_Net_Effective (@Nullable BigDecimal PriceActual_Net_Effective);

	/**
	 * Get Price net eff..
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceActual_Net_Effective();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_PriceActual_Net_Effective = new ModelColumn<>(I_C_Invoice_Candidate.class, "PriceActual_Net_Effective", null);
	String COLUMNNAME_PriceActual_Net_Effective = "PriceActual_Net_Effective";

	/**
	 * Set Price eff. override.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceActual_Override (@Nullable BigDecimal PriceActual_Override);

	/**
	 * Get Price eff. override.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceActual_Override();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_PriceActual_Override = new ModelColumn<>(I_C_Invoice_Candidate.class, "PriceActual_Override", null);
	String COLUMNNAME_PriceActual_Override = "PriceActual_Override";

	/**
	 * Set Price imp..
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceEntered (@Nullable BigDecimal PriceEntered);

	/**
	 * Get Price imp..
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceEntered();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_PriceEntered = new ModelColumn<>(I_C_Invoice_Candidate.class, "PriceEntered", null);
	String COLUMNNAME_PriceEntered = "PriceEntered";

	/**
	 * Set Price override.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceEntered_Override (@Nullable BigDecimal PriceEntered_Override);

	/**
	 * Get Price override.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceEntered_Override();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_PriceEntered_Override = new ModelColumn<>(I_C_Invoice_Candidate.class, "PriceEntered_Override", null);
	String COLUMNNAME_PriceEntered_Override = "PriceEntered_Override";

	/**
	 * Set Price Unit.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrice_UOM_ID (int Price_UOM_ID);

	/**
	 * Get Price Unit.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPrice_UOM_ID();

	String COLUMNNAME_Price_UOM_ID = "Price_UOM_ID";

	/**
	 * Set Priority.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriority (@Nullable java.lang.String Priority);

	/**
	 * Get Priority.
	 * Indicates if this request is of a high, medium or low priority.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPriority();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Priority = new ModelColumn<>(I_C_Invoice_Candidate.class, "Priority", null);
	String COLUMNNAME_Priority = "Priority";

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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Processed = new ModelColumn<>(I_C_Invoice_Candidate.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Invoiced completely.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed_Calc (boolean Processed_Calc);

	/**
	 * Get Invoiced completely.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed_Calc();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Processed_Calc = new ModelColumn<>(I_C_Invoice_Candidate.class, "Processed_Calc", null);
	String COLUMNNAME_Processed_Calc = "Processed_Calc";

	/**
	 * Set Invoiced completely override.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessed_Override (@Nullable java.lang.String Processed_Override);

	/**
	 * Get Invoiced completely override.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProcessed_Override();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Processed_Override = new ModelColumn<>(I_C_Invoice_Candidate.class, "Processed_Override", null);
	String COLUMNNAME_Processed_Override = "Processed_Override";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	boolean isProcessing();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Processing = new ModelColumn<>(I_C_Invoice_Candidate.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Product Description.
	 * Product Description
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductDescription (@Nullable java.lang.String ProductDescription);

	/**
	 * Get Product Description.
	 * Product Description
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductDescription();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_ProductDescription = new ModelColumn<>(I_C_Invoice_Candidate.class, "ProductDescription", null);
	String COLUMNNAME_ProductDescription = "ProductDescription";

	/**
	 * Set Product Type.
	 * Type of product
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setProductType (@Nullable java.lang.String ProductType);

	/**
	 * Get Product Type.
	 * Type of product
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.lang.String getProductType();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_ProductType = new ModelColumn<>(I_C_Invoice_Candidate.class, "ProductType", null);
	String COLUMNNAME_ProductType = "ProductType";

	/**
	 * Set Shipped Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDelivered (@Nullable BigDecimal QtyDelivered);

	/**
	 * Get Shipped Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDelivered();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyDelivered = new ModelColumn<>(I_C_Invoice_Candidate.class, "QtyDelivered", null);
	String COLUMNNAME_QtyDelivered = "QtyDelivered";

	/**
	 * Set Delivered quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyDeliveredInUOM (@Nullable BigDecimal QtyDeliveredInUOM);

	/**
	 * Get Delivered quantity.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyDeliveredInUOM();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyDeliveredInUOM = new ModelColumn<>(I_C_Invoice_Candidate.class, "QtyDeliveredInUOM", null);
	String COLUMNNAME_QtyDeliveredInUOM = "QtyDeliveredInUOM";

	/**
	 * Set Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyEntered (@Nullable BigDecimal QtyEntered);

	/**
	 * Get Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEntered();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyEntered = new ModelColumn<>(I_C_Invoice_Candidate.class, "QtyEntered", null);
	String COLUMNNAME_QtyEntered = "QtyEntered";

	/**
	 * Set Quantity Invoiced.
	 * Invoiced quantity in the product's UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyInvoiced (@Nullable BigDecimal QtyInvoiced);

	/**
	 * Get Quantity Invoiced.
	 * Invoiced quantity in the product's UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyInvoiced();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyInvoiced = new ModelColumn<>(I_C_Invoice_Candidate.class, "QtyInvoiced", null);
	String COLUMNNAME_QtyInvoiced = "QtyInvoiced";

	/**
	 * Set Invoiced.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyInvoicedInUOM (@Nullable BigDecimal QtyInvoicedInUOM);

	/**
	 * Get Invoiced.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyInvoicedInUOM();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyInvoicedInUOM = new ModelColumn<>(I_C_Invoice_Candidate.class, "QtyInvoicedInUOM", null);
	String COLUMNNAME_QtyInvoicedInUOM = "QtyInvoicedInUOM";

	/**
	 * Set Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyOrdered (BigDecimal QtyOrdered);

	/**
	 * Get Qty Ordered.
	 * Qty Ordered
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrdered();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyOrdered = new ModelColumn<>(I_C_Invoice_Candidate.class, "QtyOrdered", null);
	String COLUMNNAME_QtyOrdered = "QtyOrdered";

	/**
	 * Set Over-/ Under Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyOrderedOverUnder (@Nullable BigDecimal QtyOrderedOverUnder);

	/**
	 * Get Over-/ Under Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyOrderedOverUnder();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyOrderedOverUnder = new ModelColumn<>(I_C_Invoice_Candidate.class, "QtyOrderedOverUnder", null);
	String COLUMNNAME_QtyOrderedOverUnder = "QtyOrderedOverUnder";

	/**
	 * Set Quantity (stock unit).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyPicked (@Nullable BigDecimal QtyPicked);

	/**
	 * Get Quantity (stock unit).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyPicked();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyPicked = new ModelColumn<>(I_C_Invoice_Candidate.class, "QtyPicked", null);
	String COLUMNNAME_QtyPicked = "QtyPicked";

	/**
	 * Set QtyPickedInUOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyPickedInUOM (@Nullable BigDecimal QtyPickedInUOM);

	/**
	 * Get QtyPickedInUOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyPickedInUOM();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyPickedInUOM = new ModelColumn<>(I_C_Invoice_Candidate.class, "QtyPickedInUOM", null);
	String COLUMNNAME_QtyPickedInUOM = "QtyPickedInUOM";

	/**
	 * Set To invoice eff.( stock unit).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyToInvoice (BigDecimal QtyToInvoice);

	/**
	 * Get To invoice eff.( stock unit).
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToInvoice();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyToInvoice = new ModelColumn<>(I_C_Invoice_Candidate.class, "QtyToInvoice", null);
	String COLUMNNAME_QtyToInvoice = "QtyToInvoice";

	/**
	 * Set Invoice Qty before Quality Discount.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyToInvoiceBeforeDiscount (BigDecimal QtyToInvoiceBeforeDiscount);

	/**
	 * Get Invoice Qty before Quality Discount.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToInvoiceBeforeDiscount();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyToInvoiceBeforeDiscount = new ModelColumn<>(I_C_Invoice_Candidate.class, "QtyToInvoiceBeforeDiscount", null);
	String COLUMNNAME_QtyToInvoiceBeforeDiscount = "QtyToInvoiceBeforeDiscount";

	/**
	 * Set Invoicable quantity in price UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyToInvoiceInPriceUOM (@Nullable BigDecimal QtyToInvoiceInPriceUOM);

	/**
	 * Get Invoicable quantity in price UOM.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToInvoiceInPriceUOM();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyToInvoiceInPriceUOM = new ModelColumn<>(I_C_Invoice_Candidate.class, "QtyToInvoiceInPriceUOM", null);
	String COLUMNNAME_QtyToInvoiceInPriceUOM = "QtyToInvoiceInPriceUOM";

	/**
	 * Set To invoice eff..
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyToInvoiceInUOM (@Nullable BigDecimal QtyToInvoiceInUOM);

	/**
	 * Get To invoice eff..
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToInvoiceInUOM();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyToInvoiceInUOM = new ModelColumn<>(I_C_Invoice_Candidate.class, "QtyToInvoiceInUOM", null);
	String COLUMNNAME_QtyToInvoiceInUOM = "QtyToInvoiceInUOM";

	/**
	 * Set Abzurechnen.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyToInvoiceInUOM_Calc (@Nullable BigDecimal QtyToInvoiceInUOM_Calc);

	/**
	 * Get Abzurechnen.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToInvoiceInUOM_Calc();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyToInvoiceInUOM_Calc = new ModelColumn<>(I_C_Invoice_Candidate.class, "QtyToInvoiceInUOM_Calc", null);
	String COLUMNNAME_QtyToInvoiceInUOM_Calc = "QtyToInvoiceInUOM_Calc";

	/**
	 * Set To invoice Override.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyToInvoiceInUOM_Override (@Nullable BigDecimal QtyToInvoiceInUOM_Override);

	/**
	 * Get To invoice Override.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToInvoiceInUOM_Override();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyToInvoiceInUOM_Override = new ModelColumn<>(I_C_Invoice_Candidate.class, "QtyToInvoiceInUOM_Override", null);
	String COLUMNNAME_QtyToInvoiceInUOM_Override = "QtyToInvoiceInUOM_Override";

	/**
	 * Set Qty to invoice override.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyToInvoice_Override (@Nullable BigDecimal QtyToInvoice_Override);

	/**
	 * Get Qty to invoice override.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToInvoice_Override();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyToInvoice_Override = new ModelColumn<>(I_C_Invoice_Candidate.class, "QtyToInvoice_Override", null);
	String COLUMNNAME_QtyToInvoice_Override = "QtyToInvoice_Override";

	/**
	 * Set Qty to Invoice override fulfilled.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyToInvoice_OverrideFulfilled (@Nullable BigDecimal QtyToInvoice_OverrideFulfilled);

	/**
	 * Get Qty to Invoice override fulfilled.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyToInvoice_OverrideFulfilled();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyToInvoice_OverrideFulfilled = new ModelColumn<>(I_C_Invoice_Candidate.class, "QtyToInvoice_OverrideFulfilled", null);
	String COLUMNNAME_QtyToInvoice_OverrideFulfilled = "QtyToInvoice_OverrideFulfilled";

	/**
	 * Set Qty with Issues.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyWithIssues (@Nullable BigDecimal QtyWithIssues);

	/**
	 * Get Qty with Issues.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyWithIssues();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyWithIssues = new ModelColumn<>(I_C_Invoice_Candidate.class, "QtyWithIssues", null);
	String COLUMNNAME_QtyWithIssues = "QtyWithIssues";

	/**
	 * Set Qty with Issues eff..
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyWithIssues_Effective (@Nullable BigDecimal QtyWithIssues_Effective);

	/**
	 * Get Qty with Issues eff..
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyWithIssues_Effective();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QtyWithIssues_Effective = new ModelColumn<>(I_C_Invoice_Candidate.class, "QtyWithIssues_Effective", null);
	String COLUMNNAME_QtyWithIssues_Effective = "QtyWithIssues_Effective";

	/**
	 * Set Qualitydiscount %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQualityDiscountPercent (@Nullable BigDecimal QualityDiscountPercent);

	/**
	 * Get Qualitydiscount %.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQualityDiscountPercent();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QualityDiscountPercent = new ModelColumn<>(I_C_Invoice_Candidate.class, "QualityDiscountPercent", null);
	String COLUMNNAME_QualityDiscountPercent = "QualityDiscountPercent";

	/**
	 * Set Quality Discount % eff..
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setQualityDiscountPercent_Effective (@Nullable BigDecimal QualityDiscountPercent_Effective);

	/**
	 * Get Quality Discount % eff..
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	BigDecimal getQualityDiscountPercent_Effective();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QualityDiscountPercent_Effective = new ModelColumn<>(I_C_Invoice_Candidate.class, "QualityDiscountPercent_Effective", null);
	String COLUMNNAME_QualityDiscountPercent_Effective = "QualityDiscountPercent_Effective";

	/**
	 * Set Quality Discount % override.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQualityDiscountPercent_Override (@Nullable BigDecimal QualityDiscountPercent_Override);

	/**
	 * Get Quality Discount % override.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQualityDiscountPercent_Override();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QualityDiscountPercent_Override = new ModelColumn<>(I_C_Invoice_Candidate.class, "QualityDiscountPercent_Override", null);
	String COLUMNNAME_QualityDiscountPercent_Override = "QualityDiscountPercent_Override";

	/**
	 * Set Rechnungspositionsart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQualityInvoiceLineGroupType (@Nullable java.lang.String QualityInvoiceLineGroupType);

	/**
	 * Get Rechnungspositionsart.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getQualityInvoiceLineGroupType();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_QualityInvoiceLineGroupType = new ModelColumn<>(I_C_Invoice_Candidate.class, "QualityInvoiceLineGroupType", null);
	String COLUMNNAME_QualityInvoiceLineGroupType = "QualityInvoiceLineGroupType";

	/**
	 * Set Quality Discount Reason.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReasonDiscount (@Nullable java.lang.String ReasonDiscount);

	/**
	 * Get Quality Discount Reason.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReasonDiscount();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_ReasonDiscount = new ModelColumn<>(I_C_Invoice_Candidate.class, "ReasonDiscount", null);
	String COLUMNNAME_ReasonDiscount = "ReasonDiscount";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Record_ID = new ModelColumn<>(I_C_Invoice_Candidate.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Account manager.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setSalesRep_ID (int SalesRep_ID);

	/**
	 * Get Account manager.
	 * Sales Representative or Company Agent
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	int getSalesRep_ID();

	String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/**
	 * Set Update Status.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSchedulerResult (@Nullable java.lang.String SchedulerResult);

	/**
	 * Get Update Status.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSchedulerResult();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_SchedulerResult = new ModelColumn<>(I_C_Invoice_Candidate.class, "SchedulerResult", null);
	String COLUMNNAME_SchedulerResult = "SchedulerResult";

	/**
	 * Set Split Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSplitAmt (BigDecimal SplitAmt);

	/**
	 * Get Split Amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getSplitAmt();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_SplitAmt = new ModelColumn<>(I_C_Invoice_Candidate.class, "SplitAmt", null);
	String COLUMNNAME_SplitAmt = "SplitAmt";

	/**
	 * Set Stock unit.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setStockingUOM_ID (int StockingUOM_ID);

	/**
	 * Get Stock unit.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getStockingUOM_ID();

	String COLUMNNAME_StockingUOM_ID = "StockingUOM_ID";

	/**
	 * Set Total of Order.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setTotalOfOrder (@Nullable BigDecimal TotalOfOrder);

	/**
	 * Get Total of Order.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	BigDecimal getTotalOfOrder();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_TotalOfOrder = new ModelColumn<>(I_C_Invoice_Candidate.class, "TotalOfOrder", null);
	String COLUMNNAME_TotalOfOrder = "TotalOfOrder";

	/**
	 * Set Order Total without Discount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setTotalOfOrderExcludingDiscount (@Nullable BigDecimal TotalOfOrderExcludingDiscount);

	/**
	 * Get Order Total without Discount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	BigDecimal getTotalOfOrderExcludingDiscount();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_TotalOfOrderExcludingDiscount = new ModelColumn<>(I_C_Invoice_Candidate.class, "TotalOfOrderExcludingDiscount", null);
	String COLUMNNAME_TotalOfOrderExcludingDiscount = "TotalOfOrderExcludingDiscount";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_Updated = new ModelColumn<>(I_C_Invoice_Candidate.class, "Updated", null);
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
	 * Set UserElementDate1.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementDate1 (@Nullable java.sql.Timestamp UserElementDate1);

	/**
	 * Get UserElementDate1.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUserElementDate1();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_UserElementDate1 = new ModelColumn<>(I_C_Invoice_Candidate.class, "UserElementDate1", null);
	String COLUMNNAME_UserElementDate1 = "UserElementDate1";

	/**
	 * Set UserElementDate2.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementDate2 (@Nullable java.sql.Timestamp UserElementDate2);

	/**
	 * Get UserElementDate2.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUserElementDate2();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_UserElementDate2 = new ModelColumn<>(I_C_Invoice_Candidate.class, "UserElementDate2", null);
	String COLUMNNAME_UserElementDate2 = "UserElementDate2";

	/**
	 * Set UserElementNumber1.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementNumber1 (@Nullable BigDecimal UserElementNumber1);

	/**
	 * Get UserElementNumber1.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getUserElementNumber1();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_UserElementNumber1 = new ModelColumn<>(I_C_Invoice_Candidate.class, "UserElementNumber1", null);
	String COLUMNNAME_UserElementNumber1 = "UserElementNumber1";

	/**
	 * Set UserElementNumber2.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementNumber2 (@Nullable BigDecimal UserElementNumber2);

	/**
	 * Get UserElementNumber2.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getUserElementNumber2();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_UserElementNumber2 = new ModelColumn<>(I_C_Invoice_Candidate.class, "UserElementNumber2", null);
	String COLUMNNAME_UserElementNumber2 = "UserElementNumber2";

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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_UserElementString1 = new ModelColumn<>(I_C_Invoice_Candidate.class, "UserElementString1", null);
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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_UserElementString2 = new ModelColumn<>(I_C_Invoice_Candidate.class, "UserElementString2", null);
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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_UserElementString3 = new ModelColumn<>(I_C_Invoice_Candidate.class, "UserElementString3", null);
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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_UserElementString4 = new ModelColumn<>(I_C_Invoice_Candidate.class, "UserElementString4", null);
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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_UserElementString5 = new ModelColumn<>(I_C_Invoice_Candidate.class, "UserElementString5", null);
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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_UserElementString6 = new ModelColumn<>(I_C_Invoice_Candidate.class, "UserElementString6", null);
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

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_UserElementString7 = new ModelColumn<>(I_C_Invoice_Candidate.class, "UserElementString7", null);
	String COLUMNNAME_UserElementString7 = "UserElementString7";

	/**
	 * Set Invoicing group.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoicingGroup (@Nullable String InvoicingGroup);

	/**
	 * Get Invoicing group.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getInvoicingGroup();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_InvoicingGroup = new ModelColumn<>(I_C_Invoice_Candidate.class, "InvoicingGroup", null);
	String COLUMNNAME_InvoicingGroup = "InvoicingGroup";


	/**
	 * Set Product Name.
	 * Name of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductName (@Nullable String ProductName);

	/**
	 * Get Product Name.
	 * Name of the Product
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getProductName();

	ModelColumn<I_C_Invoice_Candidate, Object> COLUMN_ProductName = new ModelColumn<>(I_C_Invoice_Candidate.class, "ProductName", null);
	String COLUMNNAME_ProductName = "ProductName";
}
