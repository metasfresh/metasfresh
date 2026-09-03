package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for I_GLJournal
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_I_GLJournal 
{

	String Table_Name = "I_GLJournal";

//	/** AD_Table_ID=599 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Account.
	 * Account used
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAccountFrom_ID (int AccountFrom_ID);

	/**
	 * Get Account.
	 * Account used
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAccountFrom_ID();

	@Nullable org.compiere.model.I_C_ElementValue getAccountFrom();

	void setAccountFrom(@Nullable org.compiere.model.I_C_ElementValue AccountFrom);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue> COLUMN_AccountFrom_ID = new ModelColumn<>(I_I_GLJournal.class, "AccountFrom_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_AccountFrom_ID = "AccountFrom_ID";

	/**
	 * Set Konto Zu.
	 * Verwendetes Konto
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAccountTo_ID (int AccountTo_ID);

	/**
	 * Get Konto Zu.
	 * Verwendetes Konto
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAccountTo_ID();

	@Nullable org.compiere.model.I_C_ElementValue getAccountTo();

	void setAccountTo(@Nullable org.compiere.model.I_C_ElementValue AccountTo);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue> COLUMN_AccountTo_ID = new ModelColumn<>(I_I_GLJournal.class, "AccountTo_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_AccountTo_ID = "AccountTo_ID";

	/**
	 * Set Account Value From.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAccountValueFrom (@Nullable java.lang.String AccountValueFrom);

	/**
	 * Get Account Value From.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAccountValueFrom();

	ModelColumn<I_I_GLJournal, Object> COLUMN_AccountValueFrom = new ModelColumn<>(I_I_GLJournal.class, "AccountValueFrom", null);
	String COLUMNNAME_AccountValueFrom = "AccountValueFrom";

	/**
	 * Set Account Value To.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAccountValueTo (@Nullable java.lang.String AccountValueTo);

	/**
	 * Get Account Value To.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAccountValueTo();

	ModelColumn<I_I_GLJournal, Object> COLUMN_AccountValueTo = new ModelColumn<>(I_I_GLJournal.class, "AccountValueTo", null);
	String COLUMNNAME_AccountValueTo = "AccountValueTo";

	/**
	 * Set Account Schema Name.
	 * Name of the Accounting Schema
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAcctSchemaName (@Nullable java.lang.String AcctSchemaName);

	/**
	 * Get Account Schema Name.
	 * Name of the Accounting Schema
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAcctSchemaName();

	ModelColumn<I_I_GLJournal, Object> COLUMN_AcctSchemaName = new ModelColumn<>(I_I_GLJournal.class, "AcctSchemaName", null);
	String COLUMNNAME_AcctSchemaName = "AcctSchemaName";

	/**
	 * Set Activity Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setActivityValue (@Nullable java.lang.String ActivityValue);

	/**
	 * Get Activity Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getActivityValue();

	ModelColumn<I_I_GLJournal, Object> COLUMN_ActivityValue = new ModelColumn<>(I_I_GLJournal.class, "ActivityValue", null);
	String COLUMNNAME_ActivityValue = "ActivityValue";

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Issue_ID();

	String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

	/**
	 * Set Document Org.
	 * Document Organization (independent from account organization)
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_OrgDoc_ID (int AD_OrgDoc_ID);

	/**
	 * Get Document Org.
	 * Document Organization (independent from account organization)
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_OrgDoc_ID();

	String COLUMNNAME_AD_OrgDoc_ID = "AD_OrgDoc_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
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
	 * Set Credit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmtAcctCr (@Nullable BigDecimal AmtAcctCr);

	/**
	 * Get Credit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmtAcctCr();

	ModelColumn<I_I_GLJournal, Object> COLUMN_AmtAcctCr = new ModelColumn<>(I_I_GLJournal.class, "AmtAcctCr", null);
	String COLUMNNAME_AmtAcctCr = "AmtAcctCr";

	/**
	 * Set Debit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmtAcctDr (@Nullable BigDecimal AmtAcctDr);

	/**
	 * Get Debit.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmtAcctDr();

	ModelColumn<I_I_GLJournal, Object> COLUMN_AmtAcctDr = new ModelColumn<>(I_I_GLJournal.class, "AmtAcctDr", null);
	String COLUMNNAME_AmtAcctDr = "AmtAcctDr";

	/**
	 * Set Credit Source.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmtSourceCr (@Nullable BigDecimal AmtSourceCr);

	/**
	 * Get Credit Source.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmtSourceCr();

	ModelColumn<I_I_GLJournal, Object> COLUMN_AmtSourceCr = new ModelColumn<>(I_I_GLJournal.class, "AmtSourceCr", null);
	String COLUMNNAME_AmtSourceCr = "AmtSourceCr";

	/**
	 * Set Debit Source.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAmtSourceDr (@Nullable BigDecimal AmtSourceDr);

	/**
	 * Get Debit Source.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmtSourceDr();

	ModelColumn<I_I_GLJournal, Object> COLUMN_AmtSourceDr = new ModelColumn<>(I_I_GLJournal.class, "AmtSourceDr", null);
	String COLUMNNAME_AmtSourceDr = "AmtSourceDr";

	/**
	 * Set Batch Description.
	 * Description of the Batch
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBatchDescription (@Nullable java.lang.String BatchDescription);

	/**
	 * Get Batch Description.
	 * Description of the Batch
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBatchDescription();

	ModelColumn<I_I_GLJournal, Object> COLUMN_BatchDescription = new ModelColumn<>(I_I_GLJournal.class, "BatchDescription", null);
	String COLUMNNAME_BatchDescription = "BatchDescription";

	/**
	 * Set Batch Document No.
	 * Document Number of the Batch
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBatchDocumentNo (@Nullable java.lang.String BatchDocumentNo);

	/**
	 * Get Batch Document No.
	 * Document Number of the Batch
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBatchDocumentNo();

	ModelColumn<I_I_GLJournal, Object> COLUMN_BatchDocumentNo = new ModelColumn<>(I_I_GLJournal.class, "BatchDocumentNo", null);
	String COLUMNNAME_BatchDocumentNo = "BatchDocumentNo";

	/**
	 * Set Partner No..
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPartnerValue (@Nullable java.lang.String BPartnerValue);

	/**
	 * Get Partner No..
	 * Key of the Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPartnerValue();

	ModelColumn<I_I_GLJournal, Object> COLUMN_BPartnerValue = new ModelColumn<>(I_I_GLJournal.class, "BPartnerValue", null);
	String COLUMNNAME_BPartnerValue = "BPartnerValue";

	/**
	 * Set Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_AcctSchema_ID();

	@Nullable org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	void setC_AcctSchema(@Nullable org.compiere.model.I_C_AcctSchema C_AcctSchema);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new ModelColumn<>(I_I_GLJournal.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
	String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

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
	 * Set Category Name.
	 * Name of the Category
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCategoryName (@Nullable java.lang.String CategoryName);

	/**
	 * Get Category Name.
	 * Name of the Category
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCategoryName();

	ModelColumn<I_I_GLJournal, Object> COLUMN_CategoryName = new ModelColumn<>(I_I_GLJournal.class, "CategoryName", null);
	String COLUMNNAME_CategoryName = "CategoryName";

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

	ModelColumn<I_I_GLJournal, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_I_GLJournal.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

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
	 * Set Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DataImport_ID (int C_DataImport_ID);

	/**
	 * Get Data import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DataImport_ID();

	@Nullable org.compiere.model.I_C_DataImport getC_DataImport();

	void setC_DataImport(@Nullable org.compiere.model.I_C_DataImport C_DataImport);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_C_DataImport> COLUMN_C_DataImport_ID = new ModelColumn<>(I_I_GLJournal.class, "C_DataImport_ID", org.compiere.model.I_C_DataImport.class);
	String COLUMNNAME_C_DataImport_ID = "C_DataImport_ID";

	/**
	 * Set Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DataImport_Run_ID (int C_DataImport_Run_ID);

	/**
	 * Get Data Import Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DataImport_Run_ID();

	@Nullable org.compiere.model.I_C_DataImport_Run getC_DataImport_Run();

	void setC_DataImport_Run(@Nullable org.compiere.model.I_C_DataImport_Run C_DataImport_Run);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_C_DataImport_Run> COLUMN_C_DataImport_Run_ID = new ModelColumn<>(I_I_GLJournal.class, "C_DataImport_Run_ID", org.compiere.model.I_C_DataImport_Run.class);
	String COLUMNNAME_C_DataImport_Run_ID = "C_DataImport_Run_ID";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Client Key.
	 * Key of the Client
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setClientValue (@Nullable java.lang.String ClientValue);

	/**
	 * Get Client Key.
	 * Key of the Client
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getClientValue();

	ModelColumn<I_I_GLJournal, Object> COLUMN_ClientValue = new ModelColumn<>(I_I_GLJournal.class, "ClientValue", null);
	String COLUMNNAME_ClientValue = "ClientValue";

	/**
	 * Set Location From.
	 * Location that inventory was moved from
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_LocFrom_ID (int C_LocFrom_ID);

	/**
	 * Get Location From.
	 * Location that inventory was moved from
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_LocFrom_ID();

	@Nullable org.compiere.model.I_C_Location getC_LocFrom();

	void setC_LocFrom(@Nullable org.compiere.model.I_C_Location C_LocFrom);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_C_Location> COLUMN_C_LocFrom_ID = new ModelColumn<>(I_I_GLJournal.class, "C_LocFrom_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_LocFrom_ID = "C_LocFrom_ID";

	/**
	 * Set Location To.
	 * Location that inventory was moved to
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_LocTo_ID (int C_LocTo_ID);

	/**
	 * Get Location To.
	 * Location that inventory was moved to
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_LocTo_ID();

	@Nullable org.compiere.model.I_C_Location getC_LocTo();

	void setC_LocTo(@Nullable org.compiere.model.I_C_Location C_LocTo);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_C_Location> COLUMN_C_LocTo_ID = new ModelColumn<>(I_I_GLJournal.class, "C_LocTo_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_LocTo_ID = "C_LocTo_ID";

	/**
	 * Set Currency Type Key.
	 * Key value for the Currency Conversion Rate Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setConversionTypeValue (@Nullable java.lang.String ConversionTypeValue);

	/**
	 * Get Currency Type Key.
	 * Key value for the Currency Conversion Rate Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getConversionTypeValue();

	ModelColumn<I_I_GLJournal, Object> COLUMN_ConversionTypeValue = new ModelColumn<>(I_I_GLJournal.class, "ConversionTypeValue", null);
	String COLUMNNAME_ConversionTypeValue = "ConversionTypeValue";

	/**
	 * Set Period.
	 * Period of the Calendar
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Period_ID (int C_Period_ID);

	/**
	 * Get Period.
	 * Period of the Calendar
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Period_ID();

	@Nullable org.compiere.model.I_C_Period getC_Period();

	void setC_Period(@Nullable org.compiere.model.I_C_Period C_Period);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_C_Period> COLUMN_C_Period_ID = new ModelColumn<>(I_I_GLJournal.class, "C_Period_ID", org.compiere.model.I_C_Period.class);
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_I_GLJournal, Object> COLUMN_Created = new ModelColumn<>(I_I_GLJournal.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Tax account (credit).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCR_Tax_Acct_ID (int CR_Tax_Acct_ID);

	/**
	 * Get Tax account (credit).
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCR_Tax_Acct_ID();

	@Nullable org.compiere.model.I_C_ValidCombination getCR_Tax_Acct();

	void setCR_Tax_Acct(@Nullable org.compiere.model.I_C_ValidCombination CR_Tax_Acct);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ValidCombination> COLUMN_CR_Tax_Acct_ID = new ModelColumn<>(I_I_GLJournal.class, "CR_Tax_Acct_ID", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_CR_Tax_Acct_ID = "CR_Tax_Acct_ID";

	/**
	 * Set Sum (Credit).
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCR_Tax_ID (int CR_Tax_ID);

	/**
	 * Get Sum (Credit).
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCR_Tax_ID();

	String COLUMNNAME_CR_Tax_ID = "CR_Tax_ID";

	/**
	 * Set CR Tax Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCR_TaxName (@Nullable java.lang.String CR_TaxName);

	/**
	 * Get CR Tax Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCR_TaxName();

	ModelColumn<I_I_GLJournal, Object> COLUMN_CR_TaxName = new ModelColumn<>(I_I_GLJournal.class, "CR_TaxName", null);
	String COLUMNNAME_CR_TaxName = "CR_TaxName";

	/**
	 * Set Sum (Credit).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCR_TaxTotalAmt (@Nullable BigDecimal CR_TaxTotalAmt);

	/**
	 * Get Sum (Credit).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCR_TaxTotalAmt();

	ModelColumn<I_I_GLJournal, Object> COLUMN_CR_TaxTotalAmt = new ModelColumn<>(I_I_GLJournal.class, "CR_TaxTotalAmt", null);
	String COLUMNNAME_CR_TaxTotalAmt = "CR_TaxTotalAmt";

	/**
	 * Set Sales Region.
	 * Sales coverage region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_SalesRegion_ID (int C_SalesRegion_ID);

	/**
	 * Get Sales Region.
	 * Sales coverage region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_SalesRegion_ID();

	@Nullable org.compiere.model.I_C_SalesRegion getC_SalesRegion();

	void setC_SalesRegion(@Nullable org.compiere.model.I_C_SalesRegion C_SalesRegion);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_C_SalesRegion> COLUMN_C_SalesRegion_ID = new ModelColumn<>(I_I_GLJournal.class, "C_SalesRegion_ID", org.compiere.model.I_C_SalesRegion.class);
	String COLUMNNAME_C_SalesRegion_ID = "C_SalesRegion_ID";

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

	ModelColumn<I_I_GLJournal, Object> COLUMN_CurrencyRate = new ModelColumn<>(I_I_GLJournal.class, "CurrencyRate", null);
	String COLUMNNAME_CurrencyRate = "CurrencyRate";

	/**
	 * Set Kombination Aus.
	 * Gültige Kontenkombination
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ValidCombinationFrom_ID (int C_ValidCombinationFrom_ID);

	/**
	 * Get Kombination Aus.
	 * Gültige Kontenkombination
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ValidCombinationFrom_ID();

	@Nullable org.compiere.model.I_C_ElementValue getC_ValidCombinationFrom();

	void setC_ValidCombinationFrom(@Nullable org.compiere.model.I_C_ElementValue C_ValidCombinationFrom);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue> COLUMN_C_ValidCombinationFrom_ID = new ModelColumn<>(I_I_GLJournal.class, "C_ValidCombinationFrom_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_C_ValidCombinationFrom_ID = "C_ValidCombinationFrom_ID";

	/**
	 * Set Valid Combination Tax From.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ValidCombinationTaxFrom_ID (int C_ValidCombinationTaxFrom_ID);

	/**
	 * Get Valid Combination Tax From.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ValidCombinationTaxFrom_ID();

	@Nullable org.compiere.model.I_C_ElementValue getC_ValidCombinationTaxFrom();

	void setC_ValidCombinationTaxFrom(@Nullable org.compiere.model.I_C_ElementValue C_ValidCombinationTaxFrom);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue> COLUMN_C_ValidCombinationTaxFrom_ID = new ModelColumn<>(I_I_GLJournal.class, "C_ValidCombinationTaxFrom_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_C_ValidCombinationTaxFrom_ID = "C_ValidCombinationTaxFrom_ID";

	/**
	 * Set Valid Combination Tax To.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ValidCombinationTaxTo_ID (int C_ValidCombinationTaxTo_ID);

	/**
	 * Get Valid Combination Tax To.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ValidCombinationTaxTo_ID();

	@Nullable org.compiere.model.I_C_ElementValue getC_ValidCombinationTaxTo();

	void setC_ValidCombinationTaxTo(@Nullable org.compiere.model.I_C_ElementValue C_ValidCombinationTaxTo);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue> COLUMN_C_ValidCombinationTaxTo_ID = new ModelColumn<>(I_I_GLJournal.class, "C_ValidCombinationTaxTo_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_C_ValidCombinationTaxTo_ID = "C_ValidCombinationTaxTo_ID";

	/**
	 * Set Kombination Zu.
	 * Gültige Kontenkombination
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_ValidCombinationTo_ID (int C_ValidCombinationTo_ID);

	/**
	 * Get Kombination Zu.
	 * Gültige Kontenkombination
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_ValidCombinationTo_ID();

	@Nullable org.compiere.model.I_C_ElementValue getC_ValidCombinationTo();

	void setC_ValidCombinationTo(@Nullable org.compiere.model.I_C_ElementValue C_ValidCombinationTo);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue> COLUMN_C_ValidCombinationTo_ID = new ModelColumn<>(I_I_GLJournal.class, "C_ValidCombinationTo_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_C_ValidCombinationTo_ID = "C_ValidCombinationTo_ID";

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

	ModelColumn<I_I_GLJournal, Object> COLUMN_DateAcct = new ModelColumn<>(I_I_GLJournal.class, "DateAcct", null);
	String COLUMNNAME_DateAcct = "DateAcct";

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

	ModelColumn<I_I_GLJournal, Object> COLUMN_Description = new ModelColumn<>(I_I_GLJournal.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Document Type Name.
	 * Name of the Document Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocTypeName (@Nullable java.lang.String DocTypeName);

	/**
	 * Get Document Type Name.
	 * Name of the Document Type
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocTypeName();

	ModelColumn<I_I_GLJournal, Object> COLUMN_DocTypeName = new ModelColumn<>(I_I_GLJournal.class, "DocTypeName", null);
	String COLUMNNAME_DocTypeName = "DocTypeName";

	/**
	 * Set Tax Account (Debit).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDR_Tax_Acct_ID (int DR_Tax_Acct_ID);

	/**
	 * Get Tax Account (Debit).
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDR_Tax_Acct_ID();

	@Nullable org.compiere.model.I_C_ValidCombination getDR_Tax_Acct();

	void setDR_Tax_Acct(@Nullable org.compiere.model.I_C_ValidCombination DR_Tax_Acct);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ValidCombination> COLUMN_DR_Tax_Acct_ID = new ModelColumn<>(I_I_GLJournal.class, "DR_Tax_Acct_ID", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_DR_Tax_Acct_ID = "DR_Tax_Acct_ID";

	/**
	 * Set Tax Account (Debit).
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDR_Tax_ID (int DR_Tax_ID);

	/**
	 * Get Tax Account (Debit).
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDR_Tax_ID();

	String COLUMNNAME_DR_Tax_ID = "DR_Tax_ID";

	/**
	 * Set DR Tax Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDR_TaxName (@Nullable java.lang.String DR_TaxName);

	/**
	 * Get DR Tax Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDR_TaxName();

	ModelColumn<I_I_GLJournal, Object> COLUMN_DR_TaxName = new ModelColumn<>(I_I_GLJournal.class, "DR_TaxName", null);
	String COLUMNNAME_DR_TaxName = "DR_TaxName";

	/**
	 * Set Sum (Debit).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDR_TaxTotalAmt (@Nullable BigDecimal DR_TaxTotalAmt);

	/**
	 * Get Sum (Debit).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDR_TaxTotalAmt();

	ModelColumn<I_I_GLJournal, Object> COLUMN_DR_TaxTotalAmt = new ModelColumn<>(I_I_GLJournal.class, "DR_TaxTotalAmt", null);
	String COLUMNNAME_DR_TaxTotalAmt = "DR_TaxTotalAmt";

	/**
	 * Set Budget.
	 * General Ledger Budget
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGL_Budget_ID (int GL_Budget_ID);

	/**
	 * Get Budget.
	 * General Ledger Budget
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getGL_Budget_ID();

	@Nullable org.compiere.model.I_GL_Budget getGL_Budget();

	void setGL_Budget(@Nullable org.compiere.model.I_GL_Budget GL_Budget);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_GL_Budget> COLUMN_GL_Budget_ID = new ModelColumn<>(I_I_GLJournal.class, "GL_Budget_ID", org.compiere.model.I_GL_Budget.class);
	String COLUMNNAME_GL_Budget_ID = "GL_Budget_ID";

	/**
	 * Set GL Category.
	 * General Ledger Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGL_Category_ID (int GL_Category_ID);

	/**
	 * Get GL Category.
	 * General Ledger Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getGL_Category_ID();

	@Nullable org.compiere.model.I_GL_Category getGL_Category();

	void setGL_Category(@Nullable org.compiere.model.I_GL_Category GL_Category);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_GL_Category> COLUMN_GL_Category_ID = new ModelColumn<>(I_I_GLJournal.class, "GL_Category_ID", org.compiere.model.I_GL_Category.class);
	String COLUMNNAME_GL_Category_ID = "GL_Category_ID";

	/**
	 * Set Journal Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGL_JournalBatch_ID (int GL_JournalBatch_ID);

	/**
	 * Get Journal Run.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getGL_JournalBatch_ID();

	@Nullable org.compiere.model.I_GL_JournalBatch getGL_JournalBatch();

	void setGL_JournalBatch(@Nullable org.compiere.model.I_GL_JournalBatch GL_JournalBatch);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_GL_JournalBatch> COLUMN_GL_JournalBatch_ID = new ModelColumn<>(I_I_GLJournal.class, "GL_JournalBatch_ID", org.compiere.model.I_GL_JournalBatch.class);
	String COLUMNNAME_GL_JournalBatch_ID = "GL_JournalBatch_ID";

	/**
	 * Set GL Journal.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGL_Journal_ID (int GL_Journal_ID);

	/**
	 * Get GL Journal.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getGL_Journal_ID();

	@Nullable org.compiere.model.I_GL_Journal getGL_Journal();

	void setGL_Journal(@Nullable org.compiere.model.I_GL_Journal GL_Journal);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_GL_Journal> COLUMN_GL_Journal_ID = new ModelColumn<>(I_I_GLJournal.class, "GL_Journal_ID", org.compiere.model.I_GL_Journal.class);
	String COLUMNNAME_GL_Journal_ID = "GL_Journal_ID";

	/**
	 * Set Journal Line.
	 * General Ledger Journal Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGL_JournalLine_ID (int GL_JournalLine_ID);

	/**
	 * Get Journal Line.
	 * General Ledger Journal Line
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getGL_JournalLine_ID();

	@Nullable org.compiere.model.I_GL_JournalLine getGL_JournalLine();

	void setGL_JournalLine(@Nullable org.compiere.model.I_GL_JournalLine GL_JournalLine);

	ModelColumn<I_I_GLJournal, org.compiere.model.I_GL_JournalLine> COLUMN_GL_JournalLine_ID = new ModelColumn<>(I_I_GLJournal.class, "GL_JournalLine_ID", org.compiere.model.I_GL_JournalLine.class);
	String COLUMNNAME_GL_JournalLine_ID = "GL_JournalLine_ID";

	/**
	 * Set Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_ErrorMsg (@Nullable java.lang.String I_ErrorMsg);

	/**
	 * Get Import Error Message.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getI_ErrorMsg();

	ModelColumn<I_I_GLJournal, Object> COLUMN_I_ErrorMsg = new ModelColumn<>(I_I_GLJournal.class, "I_ErrorMsg", null);
	String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Import GL Journal.
	 * Import General Ledger Journal
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setI_GLJournal_ID (int I_GLJournal_ID);

	/**
	 * Get Import GL Journal.
	 * Import General Ledger Journal
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getI_GLJournal_ID();

	ModelColumn<I_I_GLJournal, Object> COLUMN_I_GLJournal_ID = new ModelColumn<>(I_I_GLJournal.class, "I_GLJournal_ID", null);
	String COLUMNNAME_I_GLJournal_ID = "I_GLJournal_ID";

	/**
	 * Set Imported.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setI_IsImported (boolean I_IsImported);

	/**
	 * Get Imported.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isI_IsImported();

	ModelColumn<I_I_GLJournal, Object> COLUMN_I_IsImported = new ModelColumn<>(I_I_GLJournal.class, "I_IsImported", null);
	String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_LineContent (@Nullable java.lang.String I_LineContent);

	/**
	 * Get Import Line Content.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getI_LineContent();

	ModelColumn<I_I_GLJournal, Object> COLUMN_I_LineContent = new ModelColumn<>(I_I_GLJournal.class, "I_LineContent", null);
	String COLUMNNAME_I_LineContent = "I_LineContent";

	/**
	 * Set Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setI_LineNo (int I_LineNo);

	/**
	 * Get Import Line No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getI_LineNo();

	ModelColumn<I_I_GLJournal, Object> COLUMN_I_LineNo = new ModelColumn<>(I_I_GLJournal.class, "I_LineNo", null);
	String COLUMNNAME_I_LineNo = "I_LineNo";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_I_GLJournal, Object> COLUMN_IsActive = new ModelColumn<>(I_I_GLJournal.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Create New Batch.
	 * If selected a new batch is created
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsCreateNewBatch (boolean IsCreateNewBatch);

	/**
	 * Get Create New Batch.
	 * If selected a new batch is created
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isCreateNewBatch();

	ModelColumn<I_I_GLJournal, Object> COLUMN_IsCreateNewBatch = new ModelColumn<>(I_I_GLJournal.class, "IsCreateNewBatch", null);
	String COLUMNNAME_IsCreateNewBatch = "IsCreateNewBatch";

	/**
	 * Set Create New Journal.
	 * If selected a new journal within the batch is created
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsCreateNewJournal (boolean IsCreateNewJournal);

	/**
	 * Get Create New Journal.
	 * If selected a new journal within the batch is created
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isCreateNewJournal();

	ModelColumn<I_I_GLJournal, Object> COLUMN_IsCreateNewJournal = new ModelColumn<>(I_I_GLJournal.class, "IsCreateNewJournal", null);
	String COLUMNNAME_IsCreateNewJournal = "IsCreateNewJournal";

	/**
	 * Set ISO Currency Code.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setISO_Code (@Nullable java.lang.String ISO_Code);

	/**
	 * Get ISO Currency Code.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getISO_Code();

	ModelColumn<I_I_GLJournal, Object> COLUMN_ISO_Code = new ModelColumn<>(I_I_GLJournal.class, "ISO_Code", null);
	String COLUMNNAME_ISO_Code = "ISO_Code";

	/**
	 * Set Journal Document No.
	 * Document number of the Journal
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setJournalDocumentNo (@Nullable java.lang.String JournalDocumentNo);

	/**
	 * Get Journal Document No.
	 * Document number of the Journal
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getJournalDocumentNo();

	ModelColumn<I_I_GLJournal, Object> COLUMN_JournalDocumentNo = new ModelColumn<>(I_I_GLJournal.class, "JournalDocumentNo", null);
	String COLUMNNAME_JournalDocumentNo = "JournalDocumentNo";

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

	ModelColumn<I_I_GLJournal, Object> COLUMN_Line = new ModelColumn<>(I_I_GLJournal.class, "Line", null);
	String COLUMNNAME_Line = "Line";

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
	 * Set Trx Org Key.
	 * Key of the Transaction Organization
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrgTrxValue (@Nullable java.lang.String OrgTrxValue);

	/**
	 * Get Trx Org Key.
	 * Key of the Transaction Organization
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOrgTrxValue();

	ModelColumn<I_I_GLJournal, Object> COLUMN_OrgTrxValue = new ModelColumn<>(I_I_GLJournal.class, "OrgTrxValue", null);
	String COLUMNNAME_OrgTrxValue = "OrgTrxValue";

	/**
	 * Set Organisation Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOrgValue (@Nullable java.lang.String OrgValue);

	/**
	 * Get Organisation Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getOrgValue();

	ModelColumn<I_I_GLJournal, Object> COLUMN_OrgValue = new ModelColumn<>(I_I_GLJournal.class, "OrgValue", null);
	String COLUMNNAME_OrgValue = "OrgValue";

	/**
	 * Set Posting Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostingType (@Nullable java.lang.String PostingType);

	/**
	 * Get Posting Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPostingType();

	ModelColumn<I_I_GLJournal, Object> COLUMN_PostingType = new ModelColumn<>(I_I_GLJournal.class, "PostingType", null);
	String COLUMNNAME_PostingType = "PostingType";

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

	ModelColumn<I_I_GLJournal, Object> COLUMN_Processed = new ModelColumn<>(I_I_GLJournal.class, "Processed", null);
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

	ModelColumn<I_I_GLJournal, Object> COLUMN_Processing = new ModelColumn<>(I_I_GLJournal.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Product Value.
	 * Product identifier;
 "val-<search key>", "ext-<external id>" or internal M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProductValue (@Nullable java.lang.String ProductValue);

	/**
	 * Get Product Value.
	 * Product identifier;
 "val-<search key>", "ext-<external id>" or internal M_Product_ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProductValue();

	ModelColumn<I_I_GLJournal, Object> COLUMN_ProductValue = new ModelColumn<>(I_I_GLJournal.class, "ProductValue", null);
	String COLUMNNAME_ProductValue = "ProductValue";

	/**
	 * Set Project Key.
	 * Key of the Project
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProjectValue (@Nullable java.lang.String ProjectValue);

	/**
	 * Get Project Key.
	 * Key of the Project
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getProjectValue();

	ModelColumn<I_I_GLJournal, Object> COLUMN_ProjectValue = new ModelColumn<>(I_I_GLJournal.class, "ProjectValue", null);
	String COLUMNNAME_ProjectValue = "ProjectValue";

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

	ModelColumn<I_I_GLJournal, Object> COLUMN_Qty = new ModelColumn<>(I_I_GLJournal.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Set SKU.
	 * Stock Keeping Unit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSKU (@Nullable java.lang.String SKU);

	/**
	 * Get SKU.
	 * Stock Keeping Unit
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSKU();

	ModelColumn<I_I_GLJournal, Object> COLUMN_SKU = new ModelColumn<>(I_I_GLJournal.class, "SKU", null);
	String COLUMNNAME_SKU = "SKU";

	/**
	 * Set Tax Account Value From.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTaxAccountValueFrom (@Nullable java.lang.String TaxAccountValueFrom);

	/**
	 * Get Tax Account Value From.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTaxAccountValueFrom();

	ModelColumn<I_I_GLJournal, Object> COLUMN_TaxAccountValueFrom = new ModelColumn<>(I_I_GLJournal.class, "TaxAccountValueFrom", null);
	String COLUMNNAME_TaxAccountValueFrom = "TaxAccountValueFrom";

	/**
	 * Set Tax Account Value To.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTaxAccountValueTo (@Nullable java.lang.String TaxAccountValueTo);

	/**
	 * Get Tax Account Value To.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTaxAccountValueTo();

	ModelColumn<I_I_GLJournal, Object> COLUMN_TaxAccountValueTo = new ModelColumn<>(I_I_GLJournal.class, "TaxAccountValueTo", null);
	String COLUMNNAME_TaxAccountValueTo = "TaxAccountValueTo";

	/**
	 * Set UPC.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUPC (@Nullable java.lang.String UPC);

	/**
	 * Get UPC.
	 * Bar Code (Universal Product Code or its superset European Article Number)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUPC();

	ModelColumn<I_I_GLJournal, Object> COLUMN_UPC = new ModelColumn<>(I_I_GLJournal.class, "UPC", null);
	String COLUMNNAME_UPC = "UPC";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_I_GLJournal, Object> COLUMN_Updated = new ModelColumn<>(I_I_GLJournal.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
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

	ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new ModelColumn<>(I_I_GLJournal.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
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

	ModelColumn<I_I_GLJournal, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new ModelColumn<>(I_I_GLJournal.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_User2_ID = "User2_ID";
}
