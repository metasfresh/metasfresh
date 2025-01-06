package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for Fact_Acct
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Fact_Acct 
{

	String Table_Name = "Fact_Acct";

//	/** AD_Table_ID=270 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Asset.
	 * Asset used internally or by customers
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Asset_ID (int A_Asset_ID);

	/**
	 * Get Asset.
	 * Asset used internally or by customers
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getA_Asset_ID();

	ModelColumn<I_Fact_Acct, Object> COLUMN_A_Asset_ID = new ModelColumn<>(I_Fact_Acct.class, "A_Asset_ID", null);
	String COLUMNNAME_A_Asset_ID = "A_Asset_ID";

	/**
	 * Set Account conceptual name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAccountConceptualName (java.lang.String AccountConceptualName);

	/**
	 * Get Account conceptual name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAccountConceptualName();

	ModelColumn<I_Fact_Acct, Object> COLUMN_AccountConceptualName = new ModelColumn<>(I_Fact_Acct.class, "AccountConceptualName", null);
	String COLUMNNAME_AccountConceptualName = "AccountConceptualName";

	/**
	 * Set Account.
	 * Account used
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAccount_ID (int Account_ID);

	/**
	 * Get Account.
	 * Account used
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAccount_ID();

	String COLUMNNAME_Account_ID = "Account_ID";

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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Credit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAmtAcctCr (BigDecimal AmtAcctCr);

	/**
	 * Get Credit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmtAcctCr();

	ModelColumn<I_Fact_Acct, Object> COLUMN_AmtAcctCr = new ModelColumn<>(I_Fact_Acct.class, "AmtAcctCr", null);
	String COLUMNNAME_AmtAcctCr = "AmtAcctCr";

	/**
	 * Set Debit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAmtAcctDr (BigDecimal AmtAcctDr);

	/**
	 * Get Debit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmtAcctDr();

	ModelColumn<I_Fact_Acct, Object> COLUMN_AmtAcctDr = new ModelColumn<>(I_Fact_Acct.class, "AmtAcctDr", null);
	String COLUMNNAME_AmtAcctDr = "AmtAcctDr";

	/**
	 * Set Credit Source.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAmtSourceCr (BigDecimal AmtSourceCr);

	/**
	 * Get Credit Source.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmtSourceCr();

	ModelColumn<I_Fact_Acct, Object> COLUMN_AmtSourceCr = new ModelColumn<>(I_Fact_Acct.class, "AmtSourceCr", null);
	String COLUMNNAME_AmtSourceCr = "AmtSourceCr";

	/**
	 * Set Debit Source.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAmtSourceDr (BigDecimal AmtSourceDr);

	/**
	 * Get Debit Source.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmtSourceDr();

	ModelColumn<I_Fact_Acct, Object> COLUMN_AmtSourceDr = new ModelColumn<>(I_Fact_Acct.class, "AmtSourceDr", null);
	String COLUMNNAME_AmtSourceDr = "AmtSourceDr";

	/**
	 * Set Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_AcctSchema_ID();

	org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

	ModelColumn<I_Fact_Acct, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new ModelColumn<>(I_Fact_Acct.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
	String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Activity.
	 * Business Activity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Activity.
	 * Business Activity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Activity_ID();

	String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

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
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

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

	ModelColumn<I_Fact_Acct, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_Fact_Acct.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

	/**
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Harvesting Calendar.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Harvesting_Calendar_ID (int C_Harvesting_Calendar_ID);

	/**
	 * Get Harvesting Calendar.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Harvesting_Calendar_ID();

	@Nullable org.compiere.model.I_C_Calendar getC_Harvesting_Calendar();

	void setC_Harvesting_Calendar(@Nullable org.compiere.model.I_C_Calendar C_Harvesting_Calendar);

	ModelColumn<I_Fact_Acct, org.compiere.model.I_C_Calendar> COLUMN_C_Harvesting_Calendar_ID = new ModelColumn<>(I_Fact_Acct.class, "C_Harvesting_Calendar_ID", org.compiere.model.I_C_Calendar.class);
	String COLUMNNAME_C_Harvesting_Calendar_ID = "C_Harvesting_Calendar_ID";

	/**
	 * Set Location From.
	 * Location that inventory was moved from
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_LocFrom_ID (int C_LocFrom_ID);

	/**
	 * Get Location From.
	 * Location that inventory was moved from
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_LocFrom_ID();

	@Nullable org.compiere.model.I_C_Location getC_LocFrom();

	void setC_LocFrom(@Nullable org.compiere.model.I_C_Location C_LocFrom);

	ModelColumn<I_Fact_Acct, org.compiere.model.I_C_Location> COLUMN_C_LocFrom_ID = new ModelColumn<>(I_Fact_Acct.class, "C_LocFrom_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_LocFrom_ID = "C_LocFrom_ID";

	/**
	 * Set Location To.
	 * Location that inventory was moved to
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_LocTo_ID (int C_LocTo_ID);

	/**
	 * Get Location To.
	 * Location that inventory was moved to
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_LocTo_ID();

	@Nullable org.compiere.model.I_C_Location getC_LocTo();

	void setC_LocTo(@Nullable org.compiere.model.I_C_Location C_LocTo);

	ModelColumn<I_Fact_Acct, org.compiere.model.I_C_Location> COLUMN_C_LocTo_ID = new ModelColumn<>(I_Fact_Acct.class, "C_LocTo_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_LocTo_ID = "C_LocTo_ID";

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

	ModelColumn<I_Fact_Acct, org.compiere.model.I_C_Order> COLUMN_C_OrderSO_ID = new ModelColumn<>(I_Fact_Acct.class, "C_OrderSO_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_OrderSO_ID = "C_OrderSO_ID";

	/**
	 * Set Counterpart Accounting Fact.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCounterpart_Fact_Acct_ID (int Counterpart_Fact_Acct_ID);

	/**
	 * Get Counterpart Accounting Fact.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCounterpart_Fact_Acct_ID();

	@Nullable org.compiere.model.I_Fact_Acct getCounterpart_Fact_Acct();

	void setCounterpart_Fact_Acct(@Nullable org.compiere.model.I_Fact_Acct Counterpart_Fact_Acct);

	ModelColumn<I_Fact_Acct, org.compiere.model.I_Fact_Acct> COLUMN_Counterpart_Fact_Acct_ID = new ModelColumn<>(I_Fact_Acct.class, "Counterpart_Fact_Acct_ID", org.compiere.model.I_Fact_Acct.class);
	String COLUMNNAME_Counterpart_Fact_Acct_ID = "Counterpart_Fact_Acct_ID";

	/**
	 * Set Period.
	 * Period of the Calendar
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Period.
	 * Period of the Calendar
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Period_ID();

	org.compiere.model.I_C_Period getC_Period();

	void setC_Period(org.compiere.model.I_C_Period C_Period);

	ModelColumn<I_Fact_Acct, org.compiere.model.I_C_Period> COLUMN_C_Period_ID = new ModelColumn<>(I_Fact_Acct.class, "C_Period_ID", org.compiere.model.I_C_Period.class);
	String COLUMNNAME_C_Period_ID = "C_Period_ID";

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
	 * Set Project Phase.
	 * Phase of a Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ProjectPhase_ID (int C_ProjectPhase_ID);

	/**
	 * Get Project Phase.
	 * Phase of a Project
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ProjectPhase_ID();

	@Nullable org.compiere.model.I_C_ProjectPhase getC_ProjectPhase();

	void setC_ProjectPhase(@Nullable org.compiere.model.I_C_ProjectPhase C_ProjectPhase);

	ModelColumn<I_Fact_Acct, org.compiere.model.I_C_ProjectPhase> COLUMN_C_ProjectPhase_ID = new ModelColumn<>(I_Fact_Acct.class, "C_ProjectPhase_ID", org.compiere.model.I_C_ProjectPhase.class);
	String COLUMNNAME_C_ProjectPhase_ID = "C_ProjectPhase_ID";

	/**
	 * Set Project Task.
	 * Actual Project Task in a Phase
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ProjectTask_ID (int C_ProjectTask_ID);

	/**
	 * Get Project Task.
	 * Actual Project Task in a Phase
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ProjectTask_ID();

	@Nullable org.compiere.model.I_C_ProjectTask getC_ProjectTask();

	void setC_ProjectTask(@Nullable org.compiere.model.I_C_ProjectTask C_ProjectTask);

	ModelColumn<I_Fact_Acct, org.compiere.model.I_C_ProjectTask> COLUMN_C_ProjectTask_ID = new ModelColumn<>(I_Fact_Acct.class, "C_ProjectTask_ID", org.compiere.model.I_C_ProjectTask.class);
	String COLUMNNAME_C_ProjectTask_ID = "C_ProjectTask_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_Fact_Acct, Object> COLUMN_Created = new ModelColumn<>(I_Fact_Acct.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Sales Region.
	 * Sales coverage region
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_SalesRegion_ID (int C_SalesRegion_ID);

	/**
	 * Get Sales Region.
	 * Sales coverage region
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_SalesRegion_ID();

	@Nullable org.compiere.model.I_C_SalesRegion getC_SalesRegion();

	void setC_SalesRegion(@Nullable org.compiere.model.I_C_SalesRegion C_SalesRegion);

	ModelColumn<I_Fact_Acct, org.compiere.model.I_C_SalesRegion> COLUMN_C_SalesRegion_ID = new ModelColumn<>(I_Fact_Acct.class, "C_SalesRegion_ID", org.compiere.model.I_C_SalesRegion.class);
	String COLUMNNAME_C_SalesRegion_ID = "C_SalesRegion_ID";

	/**
	 * Set Sub Account.
	 * Sub account for Element Value
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_SubAcct_ID (int C_SubAcct_ID);

	/**
	 * Get Sub Account.
	 * Sub account for Element Value
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_SubAcct_ID();

	@Nullable org.compiere.model.I_C_SubAcct getC_SubAcct();

	void setC_SubAcct(@Nullable org.compiere.model.I_C_SubAcct C_SubAcct);

	ModelColumn<I_Fact_Acct, org.compiere.model.I_C_SubAcct> COLUMN_C_SubAcct_ID = new ModelColumn<>(I_Fact_Acct.class, "C_SubAcct_ID", org.compiere.model.I_C_SubAcct.class);
	String COLUMNNAME_C_SubAcct_ID = "C_SubAcct_ID";

	/**
	 * Set Tax.
	 * Tax identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Tax_ID (int C_Tax_ID);

	/**
	 * Get Tax.
	 * Tax identifier
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Tax_ID();

	String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/**
	 * Set UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_UOM_ID (int C_UOM_ID);

	/**
	 * Get UOM.
	 * Unit of Measure
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_UOM_ID();

	String COLUMNNAME_C_UOM_ID = "C_UOM_ID";

	/**
	 * Set Currency Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurrencyRate (@Nullable BigDecimal CurrencyRate);

	/**
	 * Get Currency Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCurrencyRate();

	ModelColumn<I_Fact_Acct, Object> COLUMN_CurrencyRate = new ModelColumn<>(I_Fact_Acct.class, "CurrencyRate", null);
	String COLUMNNAME_CurrencyRate = "CurrencyRate";

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

	ModelColumn<I_Fact_Acct, Object> COLUMN_DateAcct = new ModelColumn<>(I_Fact_Acct.class, "DateAcct", null);
	String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Date.
	 * Transaction Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateTrx (java.sql.Timestamp DateTrx);

	/**
	 * Get Date.
	 * Transaction Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateTrx();

	ModelColumn<I_Fact_Acct, Object> COLUMN_DateTrx = new ModelColumn<>(I_Fact_Acct.class, "DateTrx", null);
	String COLUMNNAME_DateTrx = "DateTrx";

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

	ModelColumn<I_Fact_Acct, Object> COLUMN_Description = new ModelColumn<>(I_Fact_Acct.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Document Base Type.
	 * Logical type of document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocBaseType (@Nullable java.lang.String DocBaseType);

	/**
	 * Get Document Base Type.
	 * Logical type of document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocBaseType();

	ModelColumn<I_Fact_Acct, Object> COLUMN_DocBaseType = new ModelColumn<>(I_Fact_Acct.class, "DocBaseType", null);
	String COLUMNNAME_DocBaseType = "DocBaseType";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocStatus (@Nullable java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocStatus();

	ModelColumn<I_Fact_Acct, Object> COLUMN_DocStatus = new ModelColumn<>(I_Fact_Acct.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (@Nullable java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentNo();

	ModelColumn<I_Fact_Acct, Object> COLUMN_DocumentNo = new ModelColumn<>(I_Fact_Acct.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set Accounting Fact.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFact_Acct_ID (int Fact_Acct_ID);

	/**
	 * Get Accounting Fact.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getFact_Acct_ID();

	ModelColumn<I_Fact_Acct, Object> COLUMN_Fact_Acct_ID = new ModelColumn<>(I_Fact_Acct.class, "Fact_Acct_ID", null);
	String COLUMNNAME_Fact_Acct_ID = "Fact_Acct_ID";

	/**
	 * Set Budget.
	 * General Ledger Budget
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGL_Budget_ID (int GL_Budget_ID);

	/**
	 * Get Budget.
	 * General Ledger Budget
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getGL_Budget_ID();

	@Nullable org.compiere.model.I_GL_Budget getGL_Budget();

	void setGL_Budget(@Nullable org.compiere.model.I_GL_Budget GL_Budget);

	ModelColumn<I_Fact_Acct, org.compiere.model.I_GL_Budget> COLUMN_GL_Budget_ID = new ModelColumn<>(I_Fact_Acct.class, "GL_Budget_ID", org.compiere.model.I_GL_Budget.class);
	String COLUMNNAME_GL_Budget_ID = "GL_Budget_ID";

	/**
	 * Set GL Category.
	 * General Ledger Category
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGL_Category_ID (int GL_Category_ID);

	/**
	 * Get GL Category.
	 * General Ledger Category
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGL_Category_ID();

	org.compiere.model.I_GL_Category getGL_Category();

	void setGL_Category(org.compiere.model.I_GL_Category GL_Category);

	ModelColumn<I_Fact_Acct, org.compiere.model.I_GL_Category> COLUMN_GL_Category_ID = new ModelColumn<>(I_Fact_Acct.class, "GL_Category_ID", org.compiere.model.I_GL_Category.class);
	String COLUMNNAME_GL_Category_ID = "GL_Category_ID";

	/**
	 * Set Harvesting Year.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHarvesting_Year_ID (int Harvesting_Year_ID);

	/**
	 * Get Harvesting Year.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getHarvesting_Year_ID();

	@Nullable org.compiere.model.I_C_Year getHarvesting_Year();

	void setHarvesting_Year(@Nullable org.compiere.model.I_C_Year Harvesting_Year);

	ModelColumn<I_Fact_Acct, org.compiere.model.I_C_Year> COLUMN_Harvesting_Year_ID = new ModelColumn<>(I_Fact_Acct.class, "Harvesting_Year_ID", org.compiere.model.I_C_Year.class);
	String COLUMNNAME_Harvesting_Year_ID = "Harvesting_Year_ID";

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

	ModelColumn<I_Fact_Acct, Object> COLUMN_IsActive = new ModelColumn<>(I_Fact_Acct.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Open Item Reconciled.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOpenItemsReconciled (boolean IsOpenItemsReconciled);

	/**
	 * Get Open Item Reconciled.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOpenItemsReconciled();

	ModelColumn<I_Fact_Acct, Object> COLUMN_IsOpenItemsReconciled = new ModelColumn<>(I_Fact_Acct.class, "IsOpenItemsReconciled", null);
	String COLUMNNAME_IsOpenItemsReconciled = "IsOpenItemsReconciled";

	/**
	 * Set Line ID.
	 * Transaction line ID (internal)
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLine_ID (int Line_ID);

	/**
	 * Get Line ID.
	 * Transaction line ID (internal)
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLine_ID();

	ModelColumn<I_Fact_Acct, Object> COLUMN_Line_ID = new ModelColumn<>(I_Fact_Acct.class, "Line_ID", null);
	String COLUMNNAME_Line_ID = "Line_ID";

	/**
	 * Set Cost Element.
	 * Product Cost Element
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_CostElement_ID (int M_CostElement_ID);

	/**
	 * Get Cost Element.
	 * Product Cost Element
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_CostElement_ID();

	@Nullable org.compiere.model.I_M_CostElement getM_CostElement();

	void setM_CostElement(@Nullable org.compiere.model.I_M_CostElement M_CostElement);

	ModelColumn<I_Fact_Acct, org.compiere.model.I_M_CostElement> COLUMN_M_CostElement_ID = new ModelColumn<>(I_Fact_Acct.class, "M_CostElement_ID", org.compiere.model.I_M_CostElement.class);
	String COLUMNNAME_M_CostElement_ID = "M_CostElement_ID";

	/**
	 * Set Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setM_Locator_ID (int M_Locator_ID);

	/**
	 * Get Locator.
	 * Warehouse Locator
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getM_Locator_ID();

	String COLUMNNAME_M_Locator_ID = "M_Locator_ID";

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

	ModelColumn<I_Fact_Acct, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_Fact_Acct.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Set OI Open Amount.
	 * OI Open amount in accounting currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOI_OpenAmount (@Nullable BigDecimal OI_OpenAmount);

	/**
	 * Get OI Open Amount.
	 * OI Open amount in accounting currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getOI_OpenAmount();

	ModelColumn<I_Fact_Acct, Object> COLUMN_OI_OpenAmount = new ModelColumn<>(I_Fact_Acct.class, "OI_OpenAmount", null);
	String COLUMNNAME_OI_OpenAmount = "OI_OpenAmount";

	/**
	 * Set OI Open Amount (source).
	 * OI Open amount in source currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOI_OpenAmountSource (@Nullable BigDecimal OI_OpenAmountSource);

	/**
	 * Get OI Open Amount (source).
	 * OI Open amount in source currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getOI_OpenAmountSource();

	ModelColumn<I_Fact_Acct, Object> COLUMN_OI_OpenAmountSource = new ModelColumn<>(I_Fact_Acct.class, "OI_OpenAmountSource", null);
	String COLUMNNAME_OI_OpenAmountSource = "OI_OpenAmountSource";

	/**
	 * Set Open Item Transaction Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOI_TrxType (@Nullable java.lang.String OI_TrxType);

	/**
	 * Get Open Item Transaction Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOI_TrxType();

	ModelColumn<I_Fact_Acct, Object> COLUMN_OI_TrxType = new ModelColumn<>(I_Fact_Acct.class, "OI_TrxType", null);
	String COLUMNNAME_OI_TrxType = "OI_TrxType";

	/**
	 * Set Open Item Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOpenItemKey (@Nullable java.lang.String OpenItemKey);

	/**
	 * Get Open Item Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOpenItemKey();

	ModelColumn<I_Fact_Acct, Object> COLUMN_OpenItemKey = new ModelColumn<>(I_Fact_Acct.class, "OpenItemKey", null);
	String COLUMNNAME_OpenItemKey = "OpenItemKey";

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

	ModelColumn<I_Fact_Acct, Object> COLUMN_POReference = new ModelColumn<>(I_Fact_Acct.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

	/**
	 * Set Posting Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPostingType (java.lang.String PostingType);

	/**
	 * Get Posting Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPostingType();

	ModelColumn<I_Fact_Acct, Object> COLUMN_PostingType = new ModelColumn<>(I_Fact_Acct.class, "PostingType", null);
	String COLUMNNAME_PostingType = "PostingType";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty (@Nullable BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_Fact_Acct, Object> COLUMN_Qty = new ModelColumn<>(I_Fact_Acct.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

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

	ModelColumn<I_Fact_Acct, Object> COLUMN_Record_ID = new ModelColumn<>(I_Fact_Acct.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set SubLine ID.
	 * Transaction sub line ID (internal)
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSubLine_ID (int SubLine_ID);

	/**
	 * Get SubLine ID.
	 * Transaction sub line ID (internal)
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSubLine_ID();

	ModelColumn<I_Fact_Acct, Object> COLUMN_SubLine_ID = new ModelColumn<>(I_Fact_Acct.class, "SubLine_ID", null);
	String COLUMNNAME_SubLine_ID = "SubLine_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_Fact_Acct, Object> COLUMN_Updated = new ModelColumn<>(I_Fact_Acct.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser1_ID (int User1_ID);

	/**
	 * Get User List 1.
	 * User defined list element #1
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser1_ID();

	String COLUMNNAME_User1_ID = "User1_ID";

	/**
	 * Set User 2.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUser2_ID (int User2_ID);

	/**
	 * Get User 2.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUser2_ID();

	String COLUMNNAME_User2_ID = "User2_ID";

	/**
	 * Set User Element 1.
	 * User defined accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElement1_ID (int UserElement1_ID);

	/**
	 * Get User Element 1.
	 * User defined accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUserElement1_ID();

	ModelColumn<I_Fact_Acct, Object> COLUMN_UserElement1_ID = new ModelColumn<>(I_Fact_Acct.class, "UserElement1_ID", null);
	String COLUMNNAME_UserElement1_ID = "UserElement1_ID";

	/**
	 * Set User Element 2.
	 * User defined accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElement2_ID (int UserElement2_ID);

	/**
	 * Get User Element 2.
	 * User defined accounting Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getUserElement2_ID();

	ModelColumn<I_Fact_Acct, Object> COLUMN_UserElement2_ID = new ModelColumn<>(I_Fact_Acct.class, "UserElement2_ID", null);
	String COLUMNNAME_UserElement2_ID = "UserElement2_ID";

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

	ModelColumn<I_Fact_Acct, Object> COLUMN_UserElementNumber1 = new ModelColumn<>(I_Fact_Acct.class, "UserElementNumber1", null);
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

	ModelColumn<I_Fact_Acct, Object> COLUMN_UserElementNumber2 = new ModelColumn<>(I_Fact_Acct.class, "UserElementNumber2", null);
	String COLUMNNAME_UserElementNumber2 = "UserElementNumber2";

	/**
	 * Set Assignment.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString1 (@Nullable java.lang.String UserElementString1);

	/**
	 * Get Assignment.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString1();

	ModelColumn<I_Fact_Acct, Object> COLUMN_UserElementString1 = new ModelColumn<>(I_Fact_Acct.class, "UserElementString1", null);
	String COLUMNNAME_UserElementString1 = "UserElementString1";

	/**
	 * Set Ship-from.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString2 (@Nullable java.lang.String UserElementString2);

	/**
	 * Get Ship-from.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString2();

	ModelColumn<I_Fact_Acct, Object> COLUMN_UserElementString2 = new ModelColumn<>(I_Fact_Acct.class, "UserElementString2", null);
	String COLUMNNAME_UserElementString2 = "UserElementString2";

	/**
	 * Set Ship-to.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementString3 (@Nullable java.lang.String UserElementString3);

	/**
	 * Get Ship-to.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUserElementString3();

	ModelColumn<I_Fact_Acct, Object> COLUMN_UserElementString3 = new ModelColumn<>(I_Fact_Acct.class, "UserElementString3", null);
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

	ModelColumn<I_Fact_Acct, Object> COLUMN_UserElementString4 = new ModelColumn<>(I_Fact_Acct.class, "UserElementString4", null);
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

	ModelColumn<I_Fact_Acct, Object> COLUMN_UserElementString5 = new ModelColumn<>(I_Fact_Acct.class, "UserElementString5", null);
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

	ModelColumn<I_Fact_Acct, Object> COLUMN_UserElementString6 = new ModelColumn<>(I_Fact_Acct.class, "UserElementString6", null);
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

	ModelColumn<I_Fact_Acct, Object> COLUMN_UserElementString7 = new ModelColumn<>(I_Fact_Acct.class, "UserElementString7", null);
	String COLUMNNAME_UserElementString7 = "UserElementString7";

	/**
	 * Set VAT Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVATCode (@Nullable java.lang.String VATCode);

	/**
	 * Get VAT Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVATCode();

	ModelColumn<I_Fact_Acct, Object> COLUMN_VATCode = new ModelColumn<>(I_Fact_Acct.class, "VATCode", null);
	String COLUMNNAME_VATCode = "VATCode";

	/**
	 * Set From Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementDate1 (@Nullable java.sql.Timestamp UserElementDate1);

	/**
	 * Get From Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUserElementDate1();

	ModelColumn<I_Fact_Acct, Object> COLUMN_UserElementDate1 = new ModelColumn<>(I_Fact_Acct.class, "UserElementDate1", null);
	String COLUMNNAME_UserElementDate1 = "UserElementDate1";

	/**
	 * Set To Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUserElementDate2 (@Nullable java.sql.Timestamp UserElementDate2);

	/**
	 * Get To Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUserElementDate2();

	ModelColumn<I_Fact_Acct, Object> COLUMN_UserElementDate2 = new ModelColumn<>(I_Fact_Acct.class, "UserElementDate2", null);
	String COLUMNNAME_UserElementDate2 = "UserElementDate2";
}
