package de.metas.ordercandidate.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_OLCand
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_OLCand 
{

	String Table_Name = "C_OLCand";

//	/** AD_Table_ID=540244 */
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
	 * Set Data destination.
	 * Specifies which part of metasfresh shall process the given record
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_DataDestination_ID (int AD_DataDestination_ID);

	/**
	 * Get Data destination.
	 * Specifies which part of metasfresh shall process the given record
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_DataDestination_ID();

	ModelColumn<I_C_OLCand, Object> COLUMN_AD_DataDestination_ID = new ModelColumn<>(I_C_OLCand.class, "AD_DataDestination_ID", null);
	String COLUMNNAME_AD_DataDestination_ID = "AD_DataDestination_ID";

	/**
	 * Set Inputsource.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_InputDataSource_ID (int AD_InputDataSource_ID);

	/**
	 * Get Inputsource.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_InputDataSource_ID();

	ModelColumn<I_C_OLCand, Object> COLUMN_AD_InputDataSource_ID = new ModelColumn<>(I_C_OLCand.class, "AD_InputDataSource_ID", null);
	String COLUMNNAME_AD_InputDataSource_ID = "AD_InputDataSource_ID";

	/**
	 * Set Issues.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Issue_ID();

	String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

	/**
	 * Set Note.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Note_ID (int AD_Note_ID);

	/**
	 * Get Note.
	 *
	 * <br>Type: Search
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
	 * Set Recorded By.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_User_EnteredBy_ID (int AD_User_EnteredBy_ID);

	/**
	 * Get Recorded By.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_User_EnteredBy_ID();

	String COLUMNNAME_AD_User_EnteredBy_ID = "AD_User_EnteredBy_ID";

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
	 * Set Sales rep from.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setApplySalesRepFrom (java.lang.String ApplySalesRepFrom);

	/**
	 * Get Sales rep from.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getApplySalesRepFrom();

	ModelColumn<I_C_OLCand, Object> COLUMN_ApplySalesRepFrom = new ModelColumn<>(I_C_OLCand.class, "ApplySalesRepFrom", null);
	String COLUMNNAME_ApplySalesRepFrom = "ApplySalesRepFrom";

	/**
	 * Set Bill Business Partner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Bill Business Partner.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_BPartner_ID();

	String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

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

	ModelColumn<I_C_OLCand, org.compiere.model.I_C_Location> COLUMN_Bill_Location_Value_ID = new ModelColumn<>(I_C_OLCand.class, "Bill_Location_Value_ID", org.compiere.model.I_C_Location.class);
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

	ModelColumn<I_C_OLCand, Object> COLUMN_BPartnerName = new ModelColumn<>(I_C_OLCand.class, "BPartnerName", null);
	String COLUMNNAME_BPartnerName = "BPartnerName";

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

	ModelColumn<I_C_OLCand, Object> COLUMN_C_Async_Batch_ID = new ModelColumn<>(I_C_OLCand.class, "C_Async_Batch_ID", null);
	String COLUMNNAME_C_Async_Batch_ID = "C_Async_Batch_ID";

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

	ModelColumn<I_C_OLCand, org.compiere.model.I_C_Auction> COLUMN_C_Auction_ID = new ModelColumn<>(I_C_OLCand.class, "C_Auction_ID", org.compiere.model.I_C_Auction.class);
	String COLUMNNAME_C_Auction_ID = "C_Auction_ID";

	/**
	 * Set Eff. Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_BPartner_Effective_ID (int C_BPartner_Effective_ID);

	/**
	 * Get Eff. Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getC_BPartner_Effective_ID();

	String COLUMNNAME_C_BPartner_Effective_ID = "C_BPartner_Effective_ID";

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

	ModelColumn<I_C_OLCand, org.compiere.model.I_C_Location> COLUMN_C_BPartner_Location_Value_ID = new ModelColumn<>(I_C_OLCand.class, "C_BPartner_Location_Value_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_BPartner_Location_Value_ID = "C_BPartner_Location_Value_ID";

	/**
	 * Set Alt. Business Partner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Override_ID (int C_BPartner_Override_ID);

	/**
	 * Get Alt. Business Partner.
	 * Bezeichnet einen Geschäftspartner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Override_ID();

	String COLUMNNAME_C_BPartner_Override_ID = "C_BPartner_Override_ID";

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
	 * Set Internal sales rep.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_SalesRep_Internal_ID (int C_BPartner_SalesRep_Internal_ID);

	/**
	 * Get Internal sales rep.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_SalesRep_Internal_ID();

	String COLUMNNAME_C_BPartner_SalesRep_Internal_ID = "C_BPartner_SalesRep_Internal_ID";

	/**
	 * Set Location eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setC_BP_Location_Effective_ID (int C_BP_Location_Effective_ID);

	/**
	 * Get Location eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getC_BP_Location_Effective_ID();

	String COLUMNNAME_C_BP_Location_Effective_ID = "C_BP_Location_Effective_ID";

	/**
	 * Set Location override.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BP_Location_Override_ID (int C_BP_Location_Override_ID);

	/**
	 * Get Location override.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BP_Location_Override_ID();

	String COLUMNNAME_C_BP_Location_Override_ID = "C_BP_Location_Override_ID";

	/**
	 * Set Standort abw..
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BP_Location_Override_Value_ID (int C_BP_Location_Override_Value_ID);

	/**
	 * Get Standort abw..
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BP_Location_Override_Value_ID();

	@Nullable org.compiere.model.I_C_Location getC_BP_Location_Override_Value();

	void setC_BP_Location_Override_Value(@Nullable org.compiere.model.I_C_Location C_BP_Location_Override_Value);

	ModelColumn<I_C_OLCand, org.compiere.model.I_C_Location> COLUMN_C_BP_Location_Override_Value_ID = new ModelColumn<>(I_C_OLCand.class, "C_BP_Location_Override_Value_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_BP_Location_Override_Value_ID = "C_BP_Location_Override_Value_ID";

	/**
	 * Set Costs.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Charge_ID (int C_Charge_ID);

	/**
	 * Get Costs.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Charge_ID();

	String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

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
	 * Set Auftrags-Belegart.
	 * Document type used for the orders generated from this order candidate
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocTypeOrder_ID (int C_DocTypeOrder_ID);

	/**
	 * Get Auftrags-Belegart.
	 * Document type used for the orders generated from this order candidate
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocTypeOrder_ID();

	String COLUMNNAME_C_DocTypeOrder_ID = "C_DocTypeOrder_ID";

	/**
	 * Set Contract Terms.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID);

	/**
	 * Get Contract Terms.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Conditions_ID();

	ModelColumn<I_C_OLCand, Object> COLUMN_C_Flatrate_Conditions_ID = new ModelColumn<>(I_C_OLCand.class, "C_Flatrate_Conditions_ID", null);
	String COLUMNNAME_C_Flatrate_Conditions_ID = "C_Flatrate_Conditions_ID";

	/**
	 * Set Orderline Candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_OLCand_ID (int C_OLCand_ID);

	/**
	 * Get Orderline Candidate.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_OLCand_ID();

	ModelColumn<I_C_OLCand, Object> COLUMN_C_OLCand_ID = new ModelColumn<>(I_C_OLCand.class, "C_OLCand_ID", null);
	String COLUMNNAME_C_OLCand_ID = "C_OLCand_ID";

	/**
	 * Set Grouping key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCompensationGroupKey (@Nullable java.lang.String CompensationGroupKey);

	/**
	 * Get Grouping key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCompensationGroupKey();

	ModelColumn<I_C_OLCand, Object> COLUMN_CompensationGroupKey = new ModelColumn<>(I_C_OLCand.class, "CompensationGroupKey", null);
	String COLUMNNAME_CompensationGroupKey = "CompensationGroupKey";

	/**
	 * Set CompensationGroupOrderBy.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCompensationGroupOrderBy (@Nullable java.lang.String CompensationGroupOrderBy);

	/**
	 * Get CompensationGroupOrderBy.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCompensationGroupOrderBy();

	ModelColumn<I_C_OLCand, Object> COLUMN_CompensationGroupOrderBy = new ModelColumn<>(I_C_OLCand.class, "CompensationGroupOrderBy", null);
	String COLUMNNAME_CompensationGroupOrderBy = "CompensationGroupOrderBy";

	/**
	 * Set Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/**
	 * Get Payment Term.
	 * The terms of Payment (timing, discount)
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_OLCand, Object> COLUMN_Created = new ModelColumn<>(I_C_OLCand.class, "Created", null);
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
	 * Set Tax Category.
	 * Tax Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/**
	 * Get Tax Category.
	 * Tax Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_TaxCategory_ID();

	String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

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
	 * Set UOM.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_Internal_ID (int C_UOM_Internal_ID);

	/**
	 * Get UOM.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_Internal_ID();

	String COLUMNNAME_C_UOM_Internal_ID = "C_UOM_Internal_ID";

	/**
	 * Set Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateCandidate (java.sql.Timestamp DateCandidate);

	/**
	 * Get Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateCandidate();

	ModelColumn<I_C_OLCand, Object> COLUMN_DateCandidate = new ModelColumn<>(I_C_OLCand.class, "DateCandidate", null);
	String COLUMNNAME_DateCandidate = "DateCandidate";

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

	ModelColumn<I_C_OLCand, Object> COLUMN_DateOrdered = new ModelColumn<>(I_C_OLCand.class, "DateOrdered", null);
	String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set Date Promised From.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDatePromised (@Nullable java.sql.Timestamp DatePromised);

	/**
	 * Get Date Promised From.
	 * Date Order was promised
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDatePromised();

	ModelColumn<I_C_OLCand, Object> COLUMN_DatePromised = new ModelColumn<>(I_C_OLCand.class, "DatePromised", null);
	String COLUMNNAME_DatePromised = "DatePromised";

	/**
	 * Set Date Promised eff..
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setDatePromised_Effective (@Nullable java.sql.Timestamp DatePromised_Effective);

	/**
	 * Get Date Promised eff..
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	@Nullable java.sql.Timestamp getDatePromised_Effective();

	ModelColumn<I_C_OLCand, Object> COLUMN_DatePromised_Effective = new ModelColumn<>(I_C_OLCand.class, "DatePromised_Effective", null);
	String COLUMNNAME_DatePromised_Effective = "DatePromised_Effective";

	/**
	 * Set Date Promised override.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDatePromised_Override (@Nullable java.sql.Timestamp DatePromised_Override);

	/**
	 * Get Date Promised override.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDatePromised_Override();

	ModelColumn<I_C_OLCand, Object> COLUMN_DatePromised_Override = new ModelColumn<>(I_C_OLCand.class, "DatePromised_Override", null);
	String COLUMNNAME_DatePromised_Override = "DatePromised_Override";

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

	ModelColumn<I_C_OLCand, Object> COLUMN_DeliveryRule = new ModelColumn<>(I_C_OLCand.class, "DeliveryRule", null);
	String COLUMNNAME_DeliveryRule = "DeliveryRule";

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

	ModelColumn<I_C_OLCand, Object> COLUMN_DeliveryViaRule = new ModelColumn<>(I_C_OLCand.class, "DeliveryViaRule", null);
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

	ModelColumn<I_C_OLCand, Object> COLUMN_Description = new ModelColumn<>(I_C_OLCand.class, "Description", null);
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

	ModelColumn<I_C_OLCand, Object> COLUMN_DescriptionBottom = new ModelColumn<>(I_C_OLCand.class, "DescriptionBottom", null);
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

	ModelColumn<I_C_OLCand, Object> COLUMN_DescriptionHeader = new ModelColumn<>(I_C_OLCand.class, "DescriptionHeader", null);
	String COLUMNNAME_DescriptionHeader = "DescriptionHeader";

	/**
	 * Set Discount %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDiscount (@Nullable BigDecimal Discount);

	/**
	 * Get Discount %.
	 * Discount in percent
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDiscount();

	ModelColumn<I_C_OLCand, Object> COLUMN_Discount = new ModelColumn<>(I_C_OLCand.class, "Discount", null);
	String COLUMNNAME_Discount = "Discount";

	/**
	 * Set Ship Business Partner eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setDropShip_BPartner_Effective_ID (int DropShip_BPartner_Effective_ID);

	/**
	 * Get Ship Business Partner eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getDropShip_BPartner_Effective_ID();

	String COLUMNNAME_DropShip_BPartner_Effective_ID = "DropShip_BPartner_Effective_ID";

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
	 * Set Ship Business Partner override.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropShip_BPartner_Override_ID (int DropShip_BPartner_Override_ID);

	/**
	 * Get Ship Business Partner override.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropShip_BPartner_Override_ID();

	String COLUMNNAME_DropShip_BPartner_Override_ID = "DropShip_BPartner_Override_ID";

	/**
	 * Set Ship Location eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setDropShip_Location_Effective_ID (int DropShip_Location_Effective_ID);

	/**
	 * Get Ship Location eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	int getDropShip_Location_Effective_ID();

	String COLUMNNAME_DropShip_Location_Effective_ID = "DropShip_Location_Effective_ID";

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
	 * Set Ship Location override.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropShip_Location_Override_ID (int DropShip_Location_Override_ID);

	/**
	 * Get Ship Location override.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropShip_Location_Override_ID();

	String COLUMNNAME_DropShip_Location_Override_ID = "DropShip_Location_Override_ID";

	/**
	 * Set Lieferadresse abw..
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDropShip_Location_Override_Value_ID (int DropShip_Location_Override_Value_ID);

	/**
	 * Get Lieferadresse abw..
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDropShip_Location_Override_Value_ID();

	@Nullable org.compiere.model.I_C_Location getDropShip_Location_Override_Value();

	void setDropShip_Location_Override_Value(@Nullable org.compiere.model.I_C_Location DropShip_Location_Override_Value);

	ModelColumn<I_C_OLCand, org.compiere.model.I_C_Location> COLUMN_DropShip_Location_Override_Value_ID = new ModelColumn<>(I_C_OLCand.class, "DropShip_Location_Override_Value_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_DropShip_Location_Override_Value_ID = "DropShip_Location_Override_Value_ID";

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

	ModelColumn<I_C_OLCand, org.compiere.model.I_C_Location> COLUMN_DropShip_Location_Value_ID = new ModelColumn<>(I_C_OLCand.class, "DropShip_Location_Value_ID", org.compiere.model.I_C_Location.class);
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

	ModelColumn<I_C_OLCand, Object> COLUMN_EMail = new ModelColumn<>(I_C_OLCand.class, "EMail", null);
	String COLUMNNAME_EMail = "EMail";

	/**
	 * Set Error Message.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setErrorMsg (@Nullable java.lang.String ErrorMsg);

	/**
	 * Get Error Message.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getErrorMsg();

	ModelColumn<I_C_OLCand, Object> COLUMN_ErrorMsg = new ModelColumn<>(I_C_OLCand.class, "ErrorMsg", null);
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

	ModelColumn<I_C_OLCand, Object> COLUMN_ExternalHeaderId = new ModelColumn<>(I_C_OLCand.class, "ExternalHeaderId", null);
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

	ModelColumn<I_C_OLCand, Object> COLUMN_ExternalLineId = new ModelColumn<>(I_C_OLCand.class, "ExternalLineId", null);
	String COLUMNNAME_ExternalLineId = "ExternalLineId";

	/**
	 * Set Group compensation discount percentage.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGroupCompensationDiscountPercentage (@Nullable BigDecimal GroupCompensationDiscountPercentage);

	/**
	 * Get Group compensation discount percentage.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getGroupCompensationDiscountPercentage();

	ModelColumn<I_C_OLCand, Object> COLUMN_GroupCompensationDiscountPercentage = new ModelColumn<>(I_C_OLCand.class, "GroupCompensationDiscountPercentage", null);
	String COLUMNNAME_GroupCompensationDiscountPercentage = "GroupCompensationDiscountPercentage";

	/**
	 * Set Grouping error message.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGroupingErrorMessage (@Nullable java.lang.String GroupingErrorMessage);

	/**
	 * Get Grouping error message.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGroupingErrorMessage();

	ModelColumn<I_C_OLCand, Object> COLUMN_GroupingErrorMessage = new ModelColumn<>(I_C_OLCand.class, "GroupingErrorMessage", null);
	String COLUMNNAME_GroupingErrorMessage = "GroupingErrorMessage";

	/**
	 * Set Handover Location eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setHandOver_Location_Effective_ID (int HandOver_Location_Effective_ID);

	/**
	 * Get Handover Location eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getHandOver_Location_Effective_ID();

	String COLUMNNAME_HandOver_Location_Effective_ID = "HandOver_Location_Effective_ID";

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
	 * Set Handover Location override.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHandOver_Location_Override_ID (int HandOver_Location_Override_ID);

	/**
	 * Get Handover Location override.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getHandOver_Location_Override_ID();

	String COLUMNNAME_HandOver_Location_Override_ID = "HandOver_Location_Override_ID";

	/**
	 * Set Übergabeadresse abw..
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHandOver_Location_Override_Value_ID (int HandOver_Location_Override_Value_ID);

	/**
	 * Get Übergabeadresse abw..
	 *
	 * <br>Type: Location
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getHandOver_Location_Override_Value_ID();

	@Nullable org.compiere.model.I_C_Location getHandOver_Location_Override_Value();

	void setHandOver_Location_Override_Value(@Nullable org.compiere.model.I_C_Location HandOver_Location_Override_Value);

	ModelColumn<I_C_OLCand, org.compiere.model.I_C_Location> COLUMN_HandOver_Location_Override_Value_ID = new ModelColumn<>(I_C_OLCand.class, "HandOver_Location_Override_Value_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_HandOver_Location_Override_Value_ID = "HandOver_Location_Override_Value_ID";

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

	ModelColumn<I_C_OLCand, org.compiere.model.I_C_Location> COLUMN_HandOver_Location_Value_ID = new ModelColumn<>(I_C_OLCand.class, "HandOver_Location_Value_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_HandOver_Location_Value_ID = "HandOver_Location_Value_ID";

	/**
	 * Set Handover Partner eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setHandOver_Partner_Effective_ID (int HandOver_Partner_Effective_ID);

	/**
	 * Get Handover Partner eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getHandOver_Partner_Effective_ID();

	String COLUMNNAME_HandOver_Partner_Effective_ID = "HandOver_Partner_Effective_ID";

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
	 * Set Handover partner override.
	 * Selectable partners have a relation with "handover location=Yes" to the buyer
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHandOver_Partner_Override_ID (int HandOver_Partner_Override_ID);

	/**
	 * Get Handover partner override.
	 * Selectable partners have a relation with "handover location=Yes" to the buyer
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getHandOver_Partner_Override_ID();

	String COLUMNNAME_HandOver_Partner_Override_ID = "HandOver_Partner_Override_ID";

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

	ModelColumn<I_C_OLCand, Object> COLUMN_HeaderAggregationKey = new ModelColumn<>(I_C_OLCand.class, "HeaderAggregationKey", null);
	String COLUMNNAME_HeaderAggregationKey = "HeaderAggregationKey";

	/**
	 * Set Import warning message.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setImportWarningMessage (@Nullable java.lang.String ImportWarningMessage);

	/**
	 * Get Import warning message.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getImportWarningMessage();

	ModelColumn<I_C_OLCand, Object> COLUMN_ImportWarningMessage = new ModelColumn<>(I_C_OLCand.class, "ImportWarningMessage", null);
	String COLUMNNAME_ImportWarningMessage = "ImportWarningMessage";

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

	ModelColumn<I_C_OLCand, Object> COLUMN_InvoicableQtyBasedOn = new ModelColumn<>(I_C_OLCand.class, "InvoicableQtyBasedOn", null);
	String COLUMNNAME_InvoicableQtyBasedOn = "InvoicableQtyBasedOn";

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

	ModelColumn<I_C_OLCand, Object> COLUMN_IsActive = new ModelColumn<>(I_C_OLCand.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_C_OLCand, Object> COLUMN_IsError = new ModelColumn<>(I_C_OLCand.class, "IsError", null);
	String COLUMNNAME_IsError = "IsError";

	/**
	 * Set Explicit Product Price Attributes.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsExplicitProductPriceAttribute (boolean IsExplicitProductPriceAttribute);

	/**
	 * Get Explicit Product Price Attributes.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isExplicitProductPriceAttribute();

	ModelColumn<I_C_OLCand, Object> COLUMN_IsExplicitProductPriceAttribute = new ModelColumn<>(I_C_OLCand.class, "IsExplicitProductPriceAttribute", null);
	String COLUMNNAME_IsExplicitProductPriceAttribute = "IsExplicitProductPriceAttribute";

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

	ModelColumn<I_C_OLCand, Object> COLUMN_IsGroupCompensationLine = new ModelColumn<>(I_C_OLCand.class, "IsGroupCompensationLine", null);
	String COLUMNNAME_IsGroupCompensationLine = "IsGroupCompensationLine";

	/**
	 * Set Has grouping errors.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsGroupingError (boolean IsGroupingError);

	/**
	 * Get Has grouping errors.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isGroupingError();

	ModelColumn<I_C_OLCand, Object> COLUMN_IsGroupingError = new ModelColumn<>(I_C_OLCand.class, "IsGroupingError", null);
	String COLUMNNAME_IsGroupingError = "IsGroupingError";

	/**
	 * Set Discount Manual.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManualDiscount (boolean IsManualDiscount);

	/**
	 * Get Discount Manual.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManualDiscount();

	ModelColumn<I_C_OLCand, Object> COLUMN_IsManualDiscount = new ModelColumn<>(I_C_OLCand.class, "IsManualDiscount", null);
	String COLUMNNAME_IsManualDiscount = "IsManualDiscount";

	/**
	 * Set Manual Price.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsManualPrice (boolean IsManualPrice);

	/**
	 * Get Manual Price.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isManualPrice();

	ModelColumn<I_C_OLCand, Object> COLUMN_IsManualPrice = new ModelColumn<>(I_C_OLCand.class, "IsManualPrice", null);
	String COLUMNNAME_IsManualPrice = "IsManualPrice";

	/**
	 * Set Manual packaging capacity.
	 * If "no", then the internal packaging capactity is applied
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsManualQtyItemCapacity (boolean IsManualQtyItemCapacity);

	/**
	 * Get Manual packaging capacity.
	 * If "no", then the internal packaging capactity is applied
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isManualQtyItemCapacity();

	ModelColumn<I_C_OLCand, Object> COLUMN_IsManualQtyItemCapacity = new ModelColumn<>(I_C_OLCand.class, "IsManualQtyItemCapacity", null);
	String COLUMNNAME_IsManualQtyItemCapacity = "IsManualQtyItemCapacity";


	/**
	 * Set EDI Import Error.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsReplicationTrxError (boolean IsReplicationTrxError);

	/**
	 * Get EDI Import Error.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isReplicationTrxError();

	ModelColumn<I_C_OLCand, Object> COLUMN_IsReplicationTrxError = new ModelColumn<>(I_C_OLCand.class, "IsReplicationTrxError", null);
	String COLUMNNAME_IsReplicationTrxError = "IsReplicationTrxError";

	/**
	 * Set EDI Import Finished.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setIsReplicationTrxFinished (boolean IsReplicationTrxFinished);

	/**
	 * Get EDI Import Finished.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isReplicationTrxFinished();

	ModelColumn<I_C_OLCand, Object> COLUMN_IsReplicationTrxFinished = new ModelColumn<>(I_C_OLCand.class, "IsReplicationTrxFinished", null);
	String COLUMNNAME_IsReplicationTrxFinished = "IsReplicationTrxFinished";

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

	ModelColumn<I_C_OLCand, Object> COLUMN_Line = new ModelColumn<>(I_C_OLCand.class, "Line", null);
	String COLUMNNAME_Line = "Line";

	/**
	 * Set Attribute Set.
	 * Product Attribute Set
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_AttributeSet_ID (int M_AttributeSet_ID);

	/**
	 * Get Attribute Set.
	 * Product Attribute Set
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_AttributeSet_ID();

	@Nullable org.compiere.model.I_M_AttributeSet getM_AttributeSet();

	void setM_AttributeSet(@Nullable org.compiere.model.I_M_AttributeSet M_AttributeSet);

	ModelColumn<I_C_OLCand, org.compiere.model.I_M_AttributeSet> COLUMN_M_AttributeSet_ID = new ModelColumn<>(I_C_OLCand.class, "M_AttributeSet_ID", org.compiere.model.I_M_AttributeSet.class);
	String COLUMNNAME_M_AttributeSet_ID = "M_AttributeSet_ID";

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

	ModelColumn<I_C_OLCand, org.compiere.model.I_M_AttributeSetInstance> COLUMN_M_AttributeSetInstance_ID = new ModelColumn<>(I_C_OLCand.class, "M_AttributeSetInstance_ID", org.compiere.model.I_M_AttributeSetInstance.class);
	String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	/**
	 * Set Packing Instruction eff..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setM_HU_PI_Item_Product_Effective_ID (int M_HU_PI_Item_Product_Effective_ID);

	/**
	 * Get Packing Instruction eff..
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getM_HU_PI_Item_Product_Effective_ID();

	ModelColumn<I_C_OLCand, Object> COLUMN_M_HU_PI_Item_Product_Effective_ID = new ModelColumn<>(I_C_OLCand.class, "M_HU_PI_Item_Product_Effective_ID", null);
	String COLUMNNAME_M_HU_PI_Item_Product_Effective_ID = "M_HU_PI_Item_Product_Effective_ID";

	/**
	 * Set Packing Instruction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID);

	/**
	 * Get Packing Instruction.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Item_Product_ID();

	ModelColumn<I_C_OLCand, Object> COLUMN_M_HU_PI_Item_Product_ID = new ModelColumn<>(I_C_OLCand.class, "M_HU_PI_Item_Product_ID", null);
	String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";

	/**
	 * Set Packing Instruction Override.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_HU_PI_Item_Product_Override_ID (int M_HU_PI_Item_Product_Override_ID);

	/**
	 * Get Packing Instruction Override.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_HU_PI_Item_Product_Override_ID();

	ModelColumn<I_C_OLCand, Object> COLUMN_M_HU_PI_Item_Product_Override_ID = new ModelColumn<>(I_C_OLCand.class, "M_HU_PI_Item_Product_Override_ID", null);
	String COLUMNNAME_M_HU_PI_Item_Product_Override_ID = "M_HU_PI_Item_Product_Override_ID";

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
	 * Set Product eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setM_Product_Effective_ID (int M_Product_Effective_ID);

	/**
	 * Get Product eff..
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	int getM_Product_Effective_ID();

	String COLUMNNAME_M_Product_Effective_ID = "M_Product_Effective_ID";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Product override.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Product_Override_ID (int M_Product_Override_ID);

	/**
	 * Get Product override.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Product_Override_ID();

	String COLUMNNAME_M_Product_Override_ID = "M_Product_Override_ID";

	/**
	 * Set Attribute price.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ProductPrice_Attribute_ID (int M_ProductPrice_Attribute_ID);

	/**
	 * Get Attribute price.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_ProductPrice_Attribute_ID();

	ModelColumn<I_C_OLCand, Object> COLUMN_M_ProductPrice_Attribute_ID = new ModelColumn<>(I_C_OLCand.class, "M_ProductPrice_Attribute_ID", null);
	String COLUMNNAME_M_ProductPrice_Attribute_ID = "M_ProductPrice_Attribute_ID";

	/**
	 * Set Product Price.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_ProductPrice_ID (int M_ProductPrice_ID);

	/**
	 * Get Product Price.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_ProductPrice_ID();

	@Nullable org.compiere.model.I_M_ProductPrice getM_ProductPrice();

	void setM_ProductPrice(@Nullable org.compiere.model.I_M_ProductPrice M_ProductPrice);

	ModelColumn<I_C_OLCand, org.compiere.model.I_M_ProductPrice> COLUMN_M_ProductPrice_ID = new ModelColumn<>(I_C_OLCand.class, "M_ProductPrice_ID", org.compiere.model.I_M_ProductPrice.class);
	String COLUMNNAME_M_ProductPrice_ID = "M_ProductPrice_ID";

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

	ModelColumn<I_C_OLCand, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_C_OLCand.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
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

	ModelColumn<I_C_OLCand, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_C_OLCand.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

	/**
	 * Set Destination warehouse locator.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Warehouse_Dest_ID (int M_Warehouse_Dest_ID);

	/**
	 * Get Destination warehouse locator.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Warehouse_Dest_ID();

	String COLUMNNAME_M_Warehouse_Dest_ID = "M_Warehouse_Dest_ID";

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

	ModelColumn<I_C_OLCand, Object> COLUMN_PaymentRule = new ModelColumn<>(I_C_OLCand.class, "PaymentRule", null);
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

	ModelColumn<I_C_OLCand, Object> COLUMN_Phone = new ModelColumn<>(I_C_OLCand.class, "Phone", null);
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

	ModelColumn<I_C_OLCand, Object> COLUMN_POReference = new ModelColumn<>(I_C_OLCand.class, "POReference", null);
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

	ModelColumn<I_C_OLCand, Object> COLUMN_PresetDateInvoiced = new ModelColumn<>(I_C_OLCand.class, "PresetDateInvoiced", null);
	String COLUMNNAME_PresetDateInvoiced = "PresetDateInvoiced";

	/**
	 * Set Preset Date Shipped.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPresetDateShipped (@Nullable java.sql.Timestamp PresetDateShipped);

	/**
	 * Get Preset Date Shipped.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPresetDateShipped();

	ModelColumn<I_C_OLCand, Object> COLUMN_PresetDateShipped = new ModelColumn<>(I_C_OLCand.class, "PresetDateShipped", null);
	String COLUMNNAME_PresetDateShipped = "PresetDateShipped";

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

	ModelColumn<I_C_OLCand, Object> COLUMN_PriceActual = new ModelColumn<>(I_C_OLCand.class, "PriceActual", null);
	String COLUMNNAME_PriceActual = "PriceActual";

	/**
	 * Set Price diff..
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setPriceDifference (@Nullable BigDecimal PriceDifference);

	/**
	 * Get Price diff..
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	BigDecimal getPriceDifference();

	ModelColumn<I_C_OLCand, Object> COLUMN_PriceDifference = new ModelColumn<>(I_C_OLCand.class, "PriceDifference", null);
	String COLUMNNAME_PriceDifference = "PriceDifference";

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

	ModelColumn<I_C_OLCand, Object> COLUMN_PriceEntered = new ModelColumn<>(I_C_OLCand.class, "PriceEntered", null);
	String COLUMNNAME_PriceEntered = "PriceEntered";

	/**
	 * Set Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPriceInternal (@Nullable BigDecimal PriceInternal);

	/**
	 * Get Price.
	 *
	 * <br>Type: CostPrice
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPriceInternal();

	ModelColumn<I_C_OLCand, Object> COLUMN_PriceInternal = new ModelColumn<>(I_C_OLCand.class, "PriceInternal", null);
	String COLUMNNAME_PriceInternal = "PriceInternal";

	/**
	 * Set Price UOM.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrice_UOM_Internal_ID (int Price_UOM_Internal_ID);

	/**
	 * Get Price UOM.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPrice_UOM_Internal_ID();

	String COLUMNNAME_Price_UOM_Internal_ID = "Price_UOM_Internal_ID";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_C_OLCand, Object> COLUMN_Processed = new ModelColumn<>(I_C_OLCand.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Product Description.
	 * Product Description
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductDescription (@Nullable java.lang.String ProductDescription);

	/**
	 * Get Product Description.
	 * Product Description
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductDescription();

	ModelColumn<I_C_OLCand, Object> COLUMN_ProductDescription = new ModelColumn<>(I_C_OLCand.class, "ProductDescription", null);
	String COLUMNNAME_ProductDescription = "ProductDescription";

	/**
	 * Set Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setQtyEntered (BigDecimal QtyEntered);

	/**
	 * Get Qty.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEntered();

	ModelColumn<I_C_OLCand, Object> COLUMN_QtyEntered = new ModelColumn<>(I_C_OLCand.class, "QtyEntered", null);
	String COLUMNNAME_QtyEntered = "QtyEntered";

	/**
	 * Set Packaging capacity.
	 * Capacity in the respective product's unit of measuerement
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyItemCapacity (@Nullable BigDecimal QtyItemCapacity);

	/**
	 * Get Packaging capacity.
	 * Capacity in the respective product's unit of measuerement
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyItemCapacity();

	ModelColumn<I_C_OLCand, Object> COLUMN_QtyItemCapacity = new ModelColumn<>(I_C_OLCand.class, "QtyItemCapacity", null);
	String COLUMNNAME_QtyItemCapacity = "QtyItemCapacity";

	/**
	 * Set Verpackungskapazität int..
	 * Packaging capacity stored in the metasfresh master data
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyItemCapacityInternal (@Nullable BigDecimal QtyItemCapacityInternal);

	/**
	 * Get Verpackungskapazität int..
	 * Packaging capacity stored in the metasfresh master data
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyItemCapacityInternal();

	ModelColumn<I_C_OLCand, Object> COLUMN_QtyItemCapacityInternal = new ModelColumn<>(I_C_OLCand.class, "QtyItemCapacityInternal", null);
	String COLUMNNAME_QtyItemCapacityInternal = "QtyItemCapacityInternal";

	/**
	 * Set Qty Shipped.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyShipped (@Nullable BigDecimal QtyShipped);

	/**
	 * Get Qty Shipped.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyShipped();

	ModelColumn<I_C_OLCand, Object> COLUMN_QtyShipped = new ModelColumn<>(I_C_OLCand.class, "QtyShipped", null);
	String COLUMNNAME_QtyShipped = "QtyShipped";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_C_OLCand, Object> COLUMN_Record_ID = new ModelColumn<>(I_C_OLCand.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set EDI Import Error Message.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setReplicationTrxErrorMsg (@Nullable java.lang.String ReplicationTrxErrorMsg);

	/**
	 * Get EDI Import Error Message.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	@Nullable java.lang.String getReplicationTrxErrorMsg();

	ModelColumn<I_C_OLCand, Object> COLUMN_ReplicationTrxErrorMsg = new ModelColumn<>(I_C_OLCand.class, "ReplicationTrxErrorMsg", null);
	String COLUMNNAME_ReplicationTrxErrorMsg = "ReplicationTrxErrorMsg";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_OLCand, Object> COLUMN_Updated = new ModelColumn<>(I_C_OLCand.class, "Updated", null);
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
	 * Set Qty Override.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQtyEntered_Override (@Nullable BigDecimal QtyEntered_Override);

	/**
	 * Get Qty Override.
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQtyEntered_Override();

	ModelColumn<I_C_OLCand, Object> COLUMN_QtyEntered_Override = new ModelColumn<>(I_C_OLCand.class, "QtyEntered_Override", null);
	String COLUMNNAME_QtyEntered_Override = "QtyEntered_Override";
}
