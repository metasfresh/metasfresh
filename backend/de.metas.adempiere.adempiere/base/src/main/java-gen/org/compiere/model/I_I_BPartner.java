package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for I_BPartner
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_I_BPartner 
{

	String Table_Name = "I_BPartner";

//	/** AD_Table_ID=533 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Account No.
	 * Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAccountNo (@Nullable java.lang.String AccountNo);

	/**
	 * Get Account No.
	 * Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAccountNo();

	ModelColumn<I_I_BPartner, Object> COLUMN_AccountNo = new ModelColumn<>(I_I_BPartner.class, "AccountNo", null);
	String COLUMNNAME_AccountNo = "AccountNo";

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
	 * Set Street & House No..
	 * Address line 1 for this location
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAddress1 (@Nullable java.lang.String Address1);

	/**
	 * Get Street & House No..
	 * Address line 1 for this location
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAddress1();

	ModelColumn<I_I_BPartner, Object> COLUMN_Address1 = new ModelColumn<>(I_I_BPartner.class, "Address1", null);
	String COLUMNNAME_Address1 = "Address1";

	/**
	 * Set Address 2.
	 * Address line 2 for this location
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAddress2 (@Nullable java.lang.String Address2);

	/**
	 * Get Address 2.
	 * Address line 2 for this location
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAddress2();

	ModelColumn<I_I_BPartner, Object> COLUMN_Address2 = new ModelColumn<>(I_I_BPartner.class, "Address2", null);
	String COLUMNNAME_Address2 = "Address2";

	/**
	 * Set Adress 3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAddress3 (@Nullable java.lang.String Address3);

	/**
	 * Get Adress 3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAddress3();

	ModelColumn<I_I_BPartner, Object> COLUMN_Address3 = new ModelColumn<>(I_I_BPartner.class, "Address3", null);
	String COLUMNNAME_Address3 = "Address3";

	/**
	 * Set Adress 4.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAddress4 (@Nullable java.lang.String Address4);

	/**
	 * Get Adress 4.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAddress4();

	ModelColumn<I_I_BPartner, Object> COLUMN_Address4 = new ModelColumn<>(I_I_BPartner.class, "Address4", null);
	String COLUMNNAME_Address4 = "Address4";

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
	 * Set Language.
	 * Language for this entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Language (@Nullable java.lang.String AD_Language);

	/**
	 * Get Language.
	 * Language for this entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAD_Language();

	ModelColumn<I_I_BPartner, Object> COLUMN_AD_Language = new ModelColumn<>(I_I_BPartner.class, "AD_Language", null);
	String COLUMNNAME_AD_Language = "AD_Language";

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
	 * Set Print Format.
	 * Data Print Format
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_PrintFormat_ID (int AD_PrintFormat_ID);

	/**
	 * Get Print Format.
	 * Data Print Format
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_PrintFormat_ID();

	@Nullable org.compiere.model.I_AD_PrintFormat getAD_PrintFormat();

	void setAD_PrintFormat(@Nullable org.compiere.model.I_AD_PrintFormat AD_PrintFormat);

	ModelColumn<I_I_BPartner, org.compiere.model.I_AD_PrintFormat> COLUMN_AD_PrintFormat_ID = new ModelColumn<>(I_I_BPartner.class, "AD_PrintFormat_ID", org.compiere.model.I_AD_PrintFormat.class);
	String COLUMNNAME_AD_PrintFormat_ID = "AD_PrintFormat_ID";

	/**
	 * Set Contact ExternalId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_ExternalId (@Nullable java.lang.String AD_User_ExternalId);

	/**
	 * Get Contact ExternalId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAD_User_ExternalId();

	ModelColumn<I_I_BPartner, Object> COLUMN_AD_User_ExternalId = new ModelColumn<>(I_I_BPartner.class, "AD_User_ExternalId", null);
	String COLUMNNAME_AD_User_ExternalId = "AD_User_ExternalId";

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
	 * Set Memo1.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_Memo1 (@Nullable java.lang.String AD_User_Memo1);

	/**
	 * Get Memo1.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAD_User_Memo1();

	ModelColumn<I_I_BPartner, Object> COLUMN_AD_User_Memo1 = new ModelColumn<>(I_I_BPartner.class, "AD_User_Memo1", null);
	String COLUMNNAME_AD_User_Memo1 = "AD_User_Memo1";

	/**
	 * Set Memo2.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_Memo2 (@Nullable java.lang.String AD_User_Memo2);

	/**
	 * Get Memo2.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAD_User_Memo2();

	ModelColumn<I_I_BPartner, Object> COLUMN_AD_User_Memo2 = new ModelColumn<>(I_I_BPartner.class, "AD_User_Memo2", null);
	String COLUMNNAME_AD_User_Memo2 = "AD_User_Memo2";

	/**
	 * Set Memo3.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_Memo3 (@Nullable java.lang.String AD_User_Memo3);

	/**
	 * Get Memo3.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAD_User_Memo3();

	ModelColumn<I_I_BPartner, Object> COLUMN_AD_User_Memo3 = new ModelColumn<>(I_I_BPartner.class, "AD_User_Memo3", null);
	String COLUMNNAME_AD_User_Memo3 = "AD_User_Memo3";

	/**
	 * Set AD_User_Memo4.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_User_Memo4 (@Nullable java.lang.String AD_User_Memo4);

	/**
	 * Get AD_User_Memo4.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAD_User_Memo4();

	ModelColumn<I_I_BPartner, Object> COLUMN_AD_User_Memo4 = new ModelColumn<>(I_I_BPartner.class, "AD_User_Memo4", null);
	String COLUMNNAME_AD_User_Memo4 = "AD_User_Memo4";

	/**
	 * Set Aggregation Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAggregationName (@Nullable java.lang.String AggregationName);

	/**
	 * Get Aggregation Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAggregationName();

	ModelColumn<I_I_BPartner, Object> COLUMN_AggregationName = new ModelColumn<>(I_I_BPartner.class, "AggregationName", null);
	String COLUMNNAME_AggregationName = "AggregationName";

	/**
	 * Set Account Name.
	 * Name on Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Name (@Nullable java.lang.String A_Name);

	/**
	 * Get Account Name.
	 * Name on Credit Card or Account holder
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getA_Name();

	ModelColumn<I_I_BPartner, Object> COLUMN_A_Name = new ModelColumn<>(I_I_BPartner.class, "A_Name", null);
	String COLUMNNAME_A_Name = "A_Name";

	/**
	 * Set Birthday.
	 * Birthday or Anniversary day
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBirthday (@Nullable java.sql.Timestamp Birthday);

	/**
	 * Get Birthday.
	 * Birthday or Anniversary day
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getBirthday();

	ModelColumn<I_I_BPartner, Object> COLUMN_Birthday = new ModelColumn<>(I_I_BPartner.class, "Birthday", null);
	String COLUMNNAME_Birthday = "Birthday";

	/**
	 * Set BP Contact Greeting.
	 * Greeting for Business Partner Contact
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPContactGreeting (@Nullable java.lang.String BPContactGreeting);

	/**
	 * Get BP Contact Greeting.
	 * Greeting for Business Partner Contact
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPContactGreeting();

	ModelColumn<I_I_BPartner, Object> COLUMN_BPContactGreeting = new ModelColumn<>(I_I_BPartner.class, "BPContactGreeting", null);
	String COLUMNNAME_BPContactGreeting = "BPContactGreeting";

	/**
	 * Set Partner Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBPValue (@Nullable java.lang.String BPValue);

	/**
	 * Get Partner Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBPValue();

	ModelColumn<I_I_BPartner, Object> COLUMN_BPValue = new ModelColumn<>(I_I_BPartner.class, "BPValue", null);
	String COLUMNNAME_BPValue = "BPValue";

	/**
	 * Set Aggregation Definition.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Aggregation_ID (int C_Aggregation_ID);

	/**
	 * Get Aggregation Definition.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Aggregation_ID();

	ModelColumn<I_I_BPartner, Object> COLUMN_C_Aggregation_ID = new ModelColumn<>(I_I_BPartner.class, "C_Aggregation_ID", null);
	String COLUMNNAME_C_Aggregation_ID = "C_Aggregation_ID";

	/**
	 * Set Partner ExternalId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ExternalId (@Nullable java.lang.String C_BPartner_ExternalId);

	/**
	 * Get Partner ExternalId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getC_BPartner_ExternalId();

	ModelColumn<I_I_BPartner, Object> COLUMN_C_BPartner_ExternalId = new ModelColumn<>(I_I_BPartner.class, "C_BPartner_ExternalId", null);
	String COLUMNNAME_C_BPartner_ExternalId = "C_BPartner_ExternalId";

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
	 * Set Location ExternalId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ExternalId (@Nullable java.lang.String C_BPartner_Location_ExternalId);

	/**
	 * Get Location ExternalId.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getC_BPartner_Location_ExternalId();

	ModelColumn<I_I_BPartner, Object> COLUMN_C_BPartner_Location_ExternalId = new ModelColumn<>(I_I_BPartner.class, "C_BPartner_Location_ExternalId", null);
	String COLUMNNAME_C_BPartner_Location_ExternalId = "C_BPartner_Location_ExternalId";

	/**
	 * Set Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
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
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Memo (@Nullable java.lang.String C_BPartner_Memo);

	/**
	 * Get Businesspartner-Memo.
	 * Memo Text
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getC_BPartner_Memo();

	ModelColumn<I_I_BPartner, Object> COLUMN_C_BPartner_Memo = new ModelColumn<>(I_I_BPartner.class, "C_BPartner_Memo", null);
	String COLUMNNAME_C_BPartner_Memo = "C_BPartner_Memo";

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

	ModelColumn<I_I_BPartner, org.compiere.model.I_C_BP_Group> COLUMN_C_BP_Group_ID = new ModelColumn<>(I_I_BPartner.class, "C_BP_Group_ID", org.compiere.model.I_C_BP_Group.class);
	String COLUMNNAME_C_BP_Group_ID = "C_BP_Group_ID";

	/**
	 * Set Geschäftspartner - Druck - Format.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BP_PrintFormat_ID (int C_BP_PrintFormat_ID);

	/**
	 * Get Geschäftspartner - Druck - Format.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BP_PrintFormat_ID();

	@Nullable org.compiere.model.I_C_BP_PrintFormat getC_BP_PrintFormat();

	void setC_BP_PrintFormat(@Nullable org.compiere.model.I_C_BP_PrintFormat C_BP_PrintFormat);

	ModelColumn<I_I_BPartner, org.compiere.model.I_C_BP_PrintFormat> COLUMN_C_BP_PrintFormat_ID = new ModelColumn<>(I_I_BPartner.class, "C_BP_PrintFormat_ID", org.compiere.model.I_C_BP_PrintFormat.class);
	String COLUMNNAME_C_BP_PrintFormat_ID = "C_BP_PrintFormat_ID";

	/**
	 * Set Country.
	 * Country
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Country.
	 * Country
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Country_ID();

	@Nullable org.compiere.model.I_C_Country getC_Country();

	void setC_Country(@Nullable org.compiere.model.I_C_Country C_Country);

	ModelColumn<I_I_BPartner, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new ModelColumn<>(I_I_BPartner.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
	String COLUMNNAME_C_Country_ID = "C_Country_ID";

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

	ModelColumn<I_I_BPartner, org.compiere.model.I_C_DataImport> COLUMN_C_DataImport_ID = new ModelColumn<>(I_I_BPartner.class, "C_DataImport_ID", org.compiere.model.I_C_DataImport.class);
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

	ModelColumn<I_I_BPartner, org.compiere.model.I_C_DataImport_Run> COLUMN_C_DataImport_Run_ID = new ModelColumn<>(I_I_BPartner.class, "C_DataImport_Run_ID", org.compiere.model.I_C_DataImport_Run.class);
	String COLUMNNAME_C_DataImport_Run_ID = "C_DataImport_Run_ID";

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

	ModelColumn<I_I_BPartner, org.compiere.model.I_C_InvoiceSchedule> COLUMN_C_InvoiceSchedule_ID = new ModelColumn<>(I_I_BPartner.class, "C_InvoiceSchedule_ID", org.compiere.model.I_C_InvoiceSchedule.class);
	String COLUMNNAME_C_InvoiceSchedule_ID = "C_InvoiceSchedule_ID";

	/**
	 * Set City Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCity (@Nullable java.lang.String City);

	/**
	 * Get City Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCity();

	ModelColumn<I_I_BPartner, Object> COLUMN_City = new ModelColumn<>(I_I_BPartner.class, "City", null);
	String COLUMNNAME_City = "City";

	/**
	 * Set Position.
	 * Job Position
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Job_ID (int C_Job_ID);

	/**
	 * Get Position.
	 * Job Position
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Job_ID();

	@Nullable org.compiere.model.I_C_Job getC_Job();

	void setC_Job(@Nullable org.compiere.model.I_C_Job C_Job);

	ModelColumn<I_I_BPartner, org.compiere.model.I_C_Job> COLUMN_C_Job_ID = new ModelColumn<>(I_I_BPartner.class, "C_Job_ID", org.compiere.model.I_C_Job.class);
	String COLUMNNAME_C_Job_ID = "C_Job_ID";

	/**
	 * Set Comments.
	 * Comments or additional information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setComments (@Nullable java.lang.String Comments);

	/**
	 * Get Comments.
	 * Comments or additional information
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getComments();

	ModelColumn<I_I_BPartner, Object> COLUMN_Comments = new ModelColumn<>(I_I_BPartner.class, "Comments", null);
	String COLUMNNAME_Comments = "Comments";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_Companyname = new ModelColumn<>(I_I_BPartner.class, "Companyname", null);
	String COLUMNNAME_Companyname = "Companyname";

	/**
	 * Set Contact Description.
	 * Description of Contact
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setContactDescription (@Nullable java.lang.String ContactDescription);

	/**
	 * Get Contact Description.
	 * Description of Contact
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getContactDescription();

	ModelColumn<I_I_BPartner, Object> COLUMN_ContactDescription = new ModelColumn<>(I_I_BPartner.class, "ContactDescription", null);
	String COLUMNNAME_ContactDescription = "ContactDescription";

	/**
	 * Set Contact Name.
	 * Business Partner Contact Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setContactName (@Nullable java.lang.String ContactName);

	/**
	 * Get Contact Name.
	 * Business Partner Contact Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getContactName();

	ModelColumn<I_I_BPartner, Object> COLUMN_ContactName = new ModelColumn<>(I_I_BPartner.class, "ContactName", null);
	String COLUMNNAME_ContactName = "ContactName";

	/**
	 * Set ISO Country Code.
	 * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCountryCode (@Nullable java.lang.String CountryCode);

	/**
	 * Get ISO Country Code.
	 * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCountryCode();

	ModelColumn<I_I_BPartner, Object> COLUMN_CountryCode = new ModelColumn<>(I_I_BPartner.class, "CountryCode", null);
	String COLUMNNAME_CountryCode = "CountryCode";

	/**
	 * Set Country.
	 * Country Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCountryName (@Nullable java.lang.String CountryName);

	/**
	 * Get Country.
	 * Country Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCountryName();

	ModelColumn<I_I_BPartner, Object> COLUMN_CountryName = new ModelColumn<>(I_I_BPartner.class, "CountryName", null);
	String COLUMNNAME_CountryName = "CountryName";

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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getCreated();

	ModelColumn<I_I_BPartner, Object> COLUMN_Created = new ModelColumn<>(I_I_BPartner.class, "Created", null);
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
	 * Set Credit limit.
	 * Amount of Credit allowed
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditLimit (@Nullable BigDecimal CreditLimit);

	/**
	 * Get Credit limit.
	 * Amount of Credit allowed
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCreditLimit();

	ModelColumn<I_I_BPartner, Object> COLUMN_CreditLimit = new ModelColumn<>(I_I_BPartner.class, "CreditLimit", null);
	String COLUMNNAME_CreditLimit = "CreditLimit";

	/**
	 * Set Credit limit 2.
	 * Amount of Credit allowed
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditLimit2 (@Nullable BigDecimal CreditLimit2);

	/**
	 * Get Credit limit 2.
	 * Amount of Credit allowed
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCreditLimit2();

	ModelColumn<I_I_BPartner, Object> COLUMN_CreditLimit2 = new ModelColumn<>(I_I_BPartner.class, "CreditLimit2", null);
	String COLUMNNAME_CreditLimit2 = "CreditLimit2";

	/**
	 * Set Creditor ID.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditorId (int CreditorId);

	/**
	 * Get Creditor ID.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCreditorId();

	ModelColumn<I_I_BPartner, Object> COLUMN_CreditorId = new ModelColumn<>(I_I_BPartner.class, "CreditorId", null);
	String COLUMNNAME_CreditorId = "CreditorId";

	/**
	 * Set Region.
	 * Identifies a geographical Region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Region_ID (int C_Region_ID);

	/**
	 * Get Region.
	 * Identifies a geographical Region
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Region_ID();

	@Nullable org.compiere.model.I_C_Region getC_Region();

	void setC_Region(@Nullable org.compiere.model.I_C_Region C_Region);

	ModelColumn<I_I_BPartner, org.compiere.model.I_C_Region> COLUMN_C_Region_ID = new ModelColumn<>(I_I_BPartner.class, "C_Region_ID", org.compiere.model.I_C_Region.class);
	String COLUMNNAME_C_Region_ID = "C_Region_ID";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_CustomerNoAtVendor = new ModelColumn<>(I_I_BPartner.class, "CustomerNoAtVendor", null);
	String COLUMNNAME_CustomerNoAtVendor = "CustomerNoAtVendor";

	/**
	 * Set Debtor ID.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDebtorId (int DebtorId);

	/**
	 * Get Debtor ID.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDebtorId();

	ModelColumn<I_I_BPartner, Object> COLUMN_DebtorId = new ModelColumn<>(I_I_BPartner.class, "DebtorId", null);
	String COLUMNNAME_DebtorId = "DebtorId";

	/**
	 * Set Delivery Information.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDelivery_Info(@Nullable java.lang.String Delivery_Info);

	/**
	 * Get Delivery Information.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable
	java.lang.String getDelivery_Info();

	ModelColumn<I_I_BPartner, Object> COLUMN_Delivery_Info = new ModelColumn<>(I_I_BPartner.class, "Delivery_Info", null);
	String COLUMNNAME_Delivery_Info = "Delivery_Info";

	/**
	 * Set Delivery Via.
	 * How the order will be delivered
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeliveryViaRule(@Nullable java.lang.String DeliveryViaRule);

	/**
	 * Get Delivery Via.
	 * How the order will be delivered
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDeliveryViaRule();

	ModelColumn<I_I_BPartner, Object> COLUMN_DeliveryViaRule = new ModelColumn<>(I_I_BPartner.class, "DeliveryViaRule", null);
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

	ModelColumn<I_I_BPartner, Object> COLUMN_Description = new ModelColumn<>(I_I_BPartner.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_DUNS = new ModelColumn<>(I_I_BPartner.class, "DUNS", null);
	String COLUMNNAME_DUNS = "DUNS";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_EMail = new ModelColumn<>(I_I_BPartner.class, "EMail", null);
	String COLUMNNAME_EMail = "EMail";

	/**
	 * Set Fax.
	 * Facsimile number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFax (@Nullable java.lang.String Fax);

	/**
	 * Get Fax.
	 * Facsimile number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFax();

	ModelColumn<I_I_BPartner, Object> COLUMN_Fax = new ModelColumn<>(I_I_BPartner.class, "Fax", null);
	String COLUMNNAME_Fax = "Fax";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_Firstname = new ModelColumn<>(I_I_BPartner.class, "Firstname", null);
	String COLUMNNAME_Firstname = "Firstname";

	/**
	 * Set First Sale.
	 * Date of First Sale
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFirstSale (@Nullable java.sql.Timestamp FirstSale);

	/**
	 * Get First Sale.
	 * Date of First Sale
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getFirstSale();

	ModelColumn<I_I_BPartner, Object> COLUMN_FirstSale = new ModelColumn<>(I_I_BPartner.class, "FirstSale", null);
	String COLUMNNAME_FirstSale = "FirstSale";

	/**
	 * Set GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGLN (@Nullable java.lang.String GLN);

	/**
	 * Get GLN.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGLN();

	ModelColumn<I_I_BPartner, Object> COLUMN_GLN = new ModelColumn<>(I_I_BPartner.class, "GLN", null);
	String COLUMNNAME_GLN = "GLN";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_GlobalId = new ModelColumn<>(I_I_BPartner.class, "GlobalId", null);
	String COLUMNNAME_GlobalId = "GlobalId";

	/**
	 * Set Group Key.
	 * Business Partner Group Key
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGroupValue (@Nullable java.lang.String GroupValue);

	/**
	 * Get Group Key.
	 * Business Partner Group Key
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getGroupValue();

	ModelColumn<I_I_BPartner, Object> COLUMN_GroupValue = new ModelColumn<>(I_I_BPartner.class, "GroupValue", null);
	String COLUMNNAME_GroupValue = "GroupValue";

	/**
	 * Set IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIBAN (@Nullable java.lang.String IBAN);

	/**
	 * Get IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIBAN();

	ModelColumn<I_I_BPartner, Object> COLUMN_IBAN = new ModelColumn<>(I_I_BPartner.class, "IBAN", null);
	String COLUMNNAME_IBAN = "IBAN";

	/**
	 * Set Import Business Partner.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setI_BPartner_ID (int I_BPartner_ID);

	/**
	 * Get Import Business Partner.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getI_BPartner_ID();

	ModelColumn<I_I_BPartner, Object> COLUMN_I_BPartner_ID = new ModelColumn<>(I_I_BPartner.class, "I_BPartner_ID", null);
	String COLUMNNAME_I_BPartner_ID = "I_BPartner_ID";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_I_ErrorMsg = new ModelColumn<>(I_I_BPartner.class, "I_ErrorMsg", null);
	String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_I_IsImported = new ModelColumn<>(I_I_BPartner.class, "I_IsImported", null);
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

	ModelColumn<I_I_BPartner, Object> COLUMN_I_LineContent = new ModelColumn<>(I_I_BPartner.class, "I_LineContent", null);
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

	ModelColumn<I_I_BPartner, Object> COLUMN_I_LineNo = new ModelColumn<>(I_I_BPartner.class, "I_LineNo", null);
	String COLUMNNAME_I_LineNo = "I_LineNo";

	/**
	 * Set Interest Area Name.
	 * Name of the Interest Area
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInterestAreaName (@Nullable java.lang.String InterestAreaName);

	/**
	 * Get Interest Area Name.
	 * Name of the Interest Area
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInterestAreaName();

	ModelColumn<I_I_BPartner, Object> COLUMN_InterestAreaName = new ModelColumn<>(I_I_BPartner.class, "InterestAreaName", null);
	String COLUMNNAME_InterestAreaName = "InterestAreaName";

	/**
	 * Set Invoice Schedule Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoiceSchedule (@Nullable java.lang.String InvoiceSchedule);

	/**
	 * Get Invoice Schedule Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInvoiceSchedule();

	ModelColumn<I_I_BPartner, Object> COLUMN_InvoiceSchedule = new ModelColumn<>(I_I_BPartner.class, "InvoiceSchedule", null);
	String COLUMNNAME_InvoiceSchedule = "InvoiceSchedule";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_IsActive = new ModelColumn<>(I_I_BPartner.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Active Status.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActiveStatus (boolean IsActiveStatus);

	/**
	 * Get Active Status.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActiveStatus();

	ModelColumn<I_I_BPartner, Object> COLUMN_IsActiveStatus = new ModelColumn<>(I_I_BPartner.class, "IsActiveStatus", null);
	String COLUMNNAME_IsActiveStatus = "IsActiveStatus";

	/**
	 * Set Invoice Address.
	 * Business Partner Invoice/Bill Address
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBillTo (boolean IsBillTo);

	/**
	 * Get Invoice Address.
	 * Business Partner Invoice/Bill Address
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBillTo();

	ModelColumn<I_I_BPartner, Object> COLUMN_IsBillTo = new ModelColumn<>(I_I_BPartner.class, "IsBillTo", null);
	String COLUMNNAME_IsBillTo = "IsBillTo";

	/**
	 * Set Invoice Contact Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBillToContact_Default (boolean IsBillToContact_Default);

	/**
	 * Get Invoice Contact Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBillToContact_Default();

	ModelColumn<I_I_BPartner, Object> COLUMN_IsBillToContact_Default = new ModelColumn<>(I_I_BPartner.class, "IsBillToContact_Default", null);
	String COLUMNNAME_IsBillToContact_Default = "IsBillToContact_Default";

	/**
	 * Set Is Invoice Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBillToDefault (boolean IsBillToDefault);

	/**
	 * Get Is Invoice Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBillToDefault();

	ModelColumn<I_I_BPartner, Object> COLUMN_IsBillToDefault = new ModelColumn<>(I_I_BPartner.class, "IsBillToDefault", null);
	String COLUMNNAME_IsBillToDefault = "IsBillToDefault";

	/**
	 * Set Customer.
	 * Indicates if this Business Partner is a Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsCustomer (boolean IsCustomer);

	/**
	 * Get Customer.
	 * Indicates if this Business Partner is a Customer
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isCustomer();

	ModelColumn<I_I_BPartner, Object> COLUMN_IsCustomer = new ModelColumn<>(I_I_BPartner.class, "IsCustomer", null);
	String COLUMNNAME_IsCustomer = "IsCustomer";

	/**
	 * Set Default Contact.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDefaultContact (boolean IsDefaultContact);

	/**
	 * Get Default Contact.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDefaultContact();

	ModelColumn<I_I_BPartner, Object> COLUMN_IsDefaultContact = new ModelColumn<>(I_I_BPartner.class, "IsDefaultContact", null);
	String COLUMNNAME_IsDefaultContact = "IsDefaultContact";

	/**
	 * Set Employee.
	 * Indicates if  this Business Partner is an employee
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsEmployee (boolean IsEmployee);

	/**
	 * Get Employee.
	 * Indicates if  this Business Partner is an employee
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isEmployee();

	ModelColumn<I_I_BPartner, Object> COLUMN_IsEmployee = new ModelColumn<>(I_I_BPartner.class, "IsEmployee", null);
	String COLUMNNAME_IsEmployee = "IsEmployee";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_IsSEPASigned = new ModelColumn<>(I_I_BPartner.class, "IsSEPASigned", null);
	String COLUMNNAME_IsSEPASigned = "IsSEPASigned";

	/**
	 * Set Ship Address.
	 * Business Partner Shipment Address
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsShipTo (boolean IsShipTo);

	/**
	 * Get Ship Address.
	 * Business Partner Shipment Address
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isShipTo();

	ModelColumn<I_I_BPartner, Object> COLUMN_IsShipTo = new ModelColumn<>(I_I_BPartner.class, "IsShipTo", null);
	String COLUMNNAME_IsShipTo = "IsShipTo";

	/**
	 * Set ShipTo Contact Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsShipToContact_Default (boolean IsShipToContact_Default);

	/**
	 * Get ShipTo Contact Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isShipToContact_Default();

	ModelColumn<I_I_BPartner, Object> COLUMN_IsShipToContact_Default = new ModelColumn<>(I_I_BPartner.class, "IsShipToContact_Default", null);
	String COLUMNNAME_IsShipToContact_Default = "IsShipToContact_Default";

	/**
	 * Set Is Ship To Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsShipToDefault (boolean IsShipToDefault);

	/**
	 * Get Is Ship To Default.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isShipToDefault();

	ModelColumn<I_I_BPartner, Object> COLUMN_IsShipToDefault = new ModelColumn<>(I_I_BPartner.class, "IsShipToDefault", null);
	String COLUMNNAME_IsShipToDefault = "IsShipToDefault";

	/**
	 * Set Vendor.
	 * Indicates if this Business Partner is a Vendor
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsVendor (boolean IsVendor);

	/**
	 * Get Vendor.
	 * Indicates if this Business Partner is a Vendor
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isVendor();

	ModelColumn<I_I_BPartner, Object> COLUMN_IsVendor = new ModelColumn<>(I_I_BPartner.class, "IsVendor", null);
	String COLUMNNAME_IsVendor = "IsVendor";

	/**
	 * Set JobName.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setJobName (@Nullable java.lang.String JobName);

	/**
	 * Get JobName.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getJobName();

	ModelColumn<I_I_BPartner, Object> COLUMN_JobName = new ModelColumn<>(I_I_BPartner.class, "JobName", null);
	String COLUMNNAME_JobName = "JobName";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_Lastname = new ModelColumn<>(I_I_BPartner.class, "Lastname", null);
	String COLUMNNAME_Lastname = "Lastname";

	/**
	 * Set Lead Time Offset.
	 * Optional Lead Time offset before starting production
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLeadTimeOffset (int LeadTimeOffset);

	/**
	 * Get Lead Time Offset.
	 * Optional Lead Time offset before starting production
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLeadTimeOffset();

	ModelColumn<I_I_BPartner, Object> COLUMN_LeadTimeOffset = new ModelColumn<>(I_I_BPartner.class, "LeadTimeOffset", null);
	String COLUMNNAME_LeadTimeOffset = "LeadTimeOffset";

	/**
	 * Set Location BPartner Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setlocation_bpartner_name (@Nullable java.lang.String location_bpartner_name);

	/**
	 * Get Location BPartner Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getlocation_bpartner_name();

	ModelColumn<I_I_BPartner, Object> COLUMN_location_bpartner_name = new ModelColumn<>(I_I_BPartner.class, "location_bpartner_name", null);
	String COLUMNNAME_location_bpartner_name = "location_bpartner_name";

	/**
	 * Set Location Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setlocation_name (@Nullable java.lang.String location_name);

	/**
	 * Get Location Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getlocation_name();

	ModelColumn<I_I_BPartner, Object> COLUMN_location_name = new ModelColumn<>(I_I_BPartner.class, "location_name", null);
	String COLUMNNAME_location_name = "location_name";

	/**
	 * Set Memo Shipment.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMemo_Delivery (@Nullable java.lang.String Memo_Delivery);

	/**
	 * Get Memo Shipment.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMemo_Delivery();

	ModelColumn<I_I_BPartner, Object> COLUMN_Memo_Delivery = new ModelColumn<>(I_I_BPartner.class, "Memo_Delivery", null);
	String COLUMNNAME_Memo_Delivery = "Memo_Delivery";

	/**
	 * Set Memo Invoicing.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMemo_Invoicing (@Nullable java.lang.String Memo_Invoicing);

	/**
	 * Get Memo Invoicing.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMemo_Invoicing();

	ModelColumn<I_I_BPartner, Object> COLUMN_Memo_Invoicing = new ModelColumn<>(I_I_BPartner.class, "Memo_Invoicing", null);
	String COLUMNNAME_Memo_Invoicing = "Memo_Invoicing";

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

	ModelColumn<I_I_BPartner, org.compiere.model.I_M_Shipper> COLUMN_M_Shipper_ID = new ModelColumn<>(I_I_BPartner.class, "M_Shipper_ID", org.compiere.model.I_M_Shipper.class);
	String COLUMNNAME_M_Shipper_ID = "M_Shipper_ID";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_NAICS = new ModelColumn<>(I_I_BPartner.class, "NAICS", null);
	String COLUMNNAME_NAICS = "NAICS";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName (@Nullable java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName();

	ModelColumn<I_I_BPartner, Object> COLUMN_Name = new ModelColumn<>(I_I_BPartner.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Name 2.
	 * Additional Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName2 (@Nullable java.lang.String Name2);

	/**
	 * Get Name 2.
	 * Additional Name
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName2();

	ModelColumn<I_I_BPartner, Object> COLUMN_Name2 = new ModelColumn<>(I_I_BPartner.class, "Name2", null);
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

	ModelColumn<I_I_BPartner, Object> COLUMN_Name3 = new ModelColumn<>(I_I_BPartner.class, "Name3", null);
	String COLUMNNAME_Name3 = "Name3";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_OrgValue = new ModelColumn<>(I_I_BPartner.class, "OrgValue", null);
	String COLUMNNAME_OrgValue = "OrgValue";

	/**
	 * Set Password.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPassword (@Nullable java.lang.String Password);

	/**
	 * Get Password.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPassword();

	ModelColumn<I_I_BPartner, Object> COLUMN_Password = new ModelColumn<>(I_I_BPartner.class, "Password", null);
	String COLUMNNAME_Password = "Password";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_PaymentRule = new ModelColumn<>(I_I_BPartner.class, "PaymentRule", null);
	String COLUMNNAME_PaymentRule = "PaymentRule";

	/**
	 * Set Payment Rule.
	 * Purchase payment option
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPaymentRulePO (@Nullable java.lang.String PaymentRulePO);

	/**
	 * Get Payment Rule.
	 * Purchase payment option
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPaymentRulePO();

	ModelColumn<I_I_BPartner, Object> COLUMN_PaymentRulePO = new ModelColumn<>(I_I_BPartner.class, "PaymentRulePO", null);
	String COLUMNNAME_PaymentRulePO = "PaymentRulePO";

	/**
	 * Set Payment Term Customer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPaymentTerm (@Nullable java.lang.String PaymentTerm);

	/**
	 * Get Payment Term Customer.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPaymentTerm();

	ModelColumn<I_I_BPartner, Object> COLUMN_PaymentTerm = new ModelColumn<>(I_I_BPartner.class, "PaymentTerm", null);
	String COLUMNNAME_PaymentTerm = "PaymentTerm";

	/**
	 * Set Payment Term Key.
	 * Key of the Payment Term
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPaymentTermValue (@Nullable java.lang.String PaymentTermValue);

	/**
	 * Get Payment Term Key.
	 * Key of the Payment Term
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPaymentTermValue();

	ModelColumn<I_I_BPartner, Object> COLUMN_PaymentTermValue = new ModelColumn<>(I_I_BPartner.class, "PaymentTermValue", null);
	String COLUMNNAME_PaymentTermValue = "PaymentTermValue";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_Phone = new ModelColumn<>(I_I_BPartner.class, "Phone", null);
	String COLUMNNAME_Phone = "Phone";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_Phone2 = new ModelColumn<>(I_I_BPartner.class, "Phone2", null);
	String COLUMNNAME_Phone2 = "Phone2";

	/**
	 * Set P.O. box number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOBox (@Nullable java.lang.String POBox);

	/**
	 * Get P.O. box number.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOBox();

	ModelColumn<I_I_BPartner, Object> COLUMN_POBox = new ModelColumn<>(I_I_BPartner.class, "POBox", null);
	String COLUMNNAME_POBox = "POBox";

	/**
	 * Set PO Payment Term.
	 * Payment rules for a purchase order
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPO_PaymentTerm_ID (int PO_PaymentTerm_ID);

	/**
	 * Get PO Payment Term.
	 * Payment rules for a purchase order
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
	 * Set PO_PricingSystem_Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPO_PricingSystem_Value (@Nullable java.lang.String PO_PricingSystem_Value);

	/**
	 * Get PO_PricingSystem_Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPO_PricingSystem_Value();

	ModelColumn<I_I_BPartner, Object> COLUMN_PO_PricingSystem_Value = new ModelColumn<>(I_I_BPartner.class, "PO_PricingSystem_Value", null);
	String COLUMNNAME_PO_PricingSystem_Value = "PO_PricingSystem_Value";

	/**
	 * Set Postal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostal (@Nullable java.lang.String Postal);

	/**
	 * Get Postal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPostal();

	ModelColumn<I_I_BPartner, Object> COLUMN_Postal = new ModelColumn<>(I_I_BPartner.class, "Postal", null);
	String COLUMNNAME_Postal = "Postal";

	/**
	 * Set Additional Postal.
	 * Additional ZIP or Postal code
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostal_Add (@Nullable java.lang.String Postal_Add);

	/**
	 * Get Additional Postal.
	 * Additional ZIP or Postal code
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPostal_Add();

	ModelColumn<I_I_BPartner, Object> COLUMN_Postal_Add = new ModelColumn<>(I_I_BPartner.class, "Postal_Add", null);
	String COLUMNNAME_Postal_Add = "Postal_Add";

	/**
	 * Set PricingSystem_Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPricingSystem_Value (@Nullable java.lang.String PricingSystem_Value);

	/**
	 * Get PricingSystem_Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPricingSystem_Value();

	ModelColumn<I_I_BPartner, Object> COLUMN_PricingSystem_Value = new ModelColumn<>(I_I_BPartner.class, "PricingSystem_Value", null);
	String COLUMNNAME_PricingSystem_Value = "PricingSystem_Value";

	/**
	 * Set PrintForma_Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPrintFormat_Name (@Nullable java.lang.String PrintFormat_Name);

	/**
	 * Get PrintForma_Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPrintFormat_Name();

	ModelColumn<I_I_BPartner, Object> COLUMN_PrintFormat_Name = new ModelColumn<>(I_I_BPartner.class, "PrintFormat_Name", null);
	String COLUMNNAME_PrintFormat_Name = "PrintFormat_Name";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_Processed = new ModelColumn<>(I_I_BPartner.class, "Processed", null);
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

	ModelColumn<I_I_BPartner, Object> COLUMN_Processing = new ModelColumn<>(I_I_BPartner.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_Qualification = new ModelColumn<>(I_I_BPartner.class, "Qualification", null);
	String COLUMNNAME_Qualification = "Qualification";

	/**
	 * Set Region Name.
	 * Name of the Region
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRegionName (@Nullable java.lang.String RegionName);

	/**
	 * Get Region Name.
	 * Name of the Region
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRegionName();

	ModelColumn<I_I_BPartner, Object> COLUMN_RegionName = new ModelColumn<>(I_I_BPartner.class, "RegionName", null);
	String COLUMNNAME_RegionName = "RegionName";

	/**
	 * Set Interest Area.
	 * Interest Area or Topic
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setR_InterestArea_ID (int R_InterestArea_ID);

	/**
	 * Get Interest Area.
	 * Interest Area or Topic
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getR_InterestArea_ID();

	@Nullable org.compiere.model.I_R_InterestArea getR_InterestArea();

	void setR_InterestArea(@Nullable org.compiere.model.I_R_InterestArea R_InterestArea);

	ModelColumn<I_I_BPartner, org.compiere.model.I_R_InterestArea> COLUMN_R_InterestArea_ID = new ModelColumn<>(I_I_BPartner.class, "R_InterestArea_ID", org.compiere.model.I_R_InterestArea.class);
	String COLUMNNAME_R_InterestArea_ID = "R_InterestArea_ID";

	/**
	 * Set Statistic Group.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSalesgroup (@Nullable java.lang.String Salesgroup);

	/**
	 * Get Statistic Group.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSalesgroup();

	ModelColumn<I_I_BPartner, Object> COLUMN_Salesgroup = new ModelColumn<>(I_I_BPartner.class, "Salesgroup", null);
	String COLUMNNAME_Salesgroup = "Salesgroup";

	/**
	 * Set Min Shelf Life Days.
	 * Minimum Shelf Life in days based on Product Instance Guarantee Date
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShelfLifeMinDays (int ShelfLifeMinDays);

	/**
	 * Get Min Shelf Life Days.
	 * Minimum Shelf Life in days based on Product Instance Guarantee Date
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getShelfLifeMinDays();

	ModelColumn<I_I_BPartner, Object> COLUMN_ShelfLifeMinDays = new ModelColumn<>(I_I_BPartner.class, "ShelfLifeMinDays", null);
	String COLUMNNAME_ShelfLifeMinDays = "ShelfLifeMinDays";

	/**
	 * Set Shipper name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShipperName (@Nullable java.lang.String ShipperName);

	/**
	 * Get Shipper name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShipperName();

	ModelColumn<I_I_BPartner, Object> COLUMN_ShipperName = new ModelColumn<>(I_I_BPartner.class, "ShipperName", null);
	String COLUMNNAME_ShipperName = "ShipperName";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_ShortDescription = new ModelColumn<>(I_I_BPartner.class, "ShortDescription", null);
	String COLUMNNAME_ShortDescription = "ShortDescription";

	/**
	 * Set Swift code.
	 * Swift Code or BIC
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSwiftCode (@Nullable java.lang.String SwiftCode);

	/**
	 * Get Swift code.
	 * Swift Code or BIC
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSwiftCode();

	ModelColumn<I_I_BPartner, Object> COLUMN_SwiftCode = new ModelColumn<>(I_I_BPartner.class, "SwiftCode", null);
	String COLUMNNAME_SwiftCode = "SwiftCode";

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

	ModelColumn<I_I_BPartner, Object> COLUMN_TaxID = new ModelColumn<>(I_I_BPartner.class, "TaxID", null);
	String COLUMNNAME_TaxID = "TaxID";

	/**
	 * Set Title.
	 * Name this entity is referred to as
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTitle (@Nullable java.lang.String Title);

	/**
	 * Get Title.
	 * Name this entity is referred to as
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTitle();

	ModelColumn<I_I_BPartner, Object> COLUMN_Title = new ModelColumn<>(I_I_BPartner.class, "Title", null);
	String COLUMNNAME_Title = "Title";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getUpdated();

	ModelColumn<I_I_BPartner, Object> COLUMN_Updated = new ModelColumn<>(I_I_BPartner.class, "Updated", null);
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
	 * Set URL.
	 * Full URL address - e.g. https://www.metasfresh.com
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setURL (@Nullable java.lang.String URL);

	/**
	 * Get URL.
	 * Full URL address - e.g. https://www.metasfresh.com
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getURL();

	ModelColumn<I_I_BPartner, Object> COLUMN_URL = new ModelColumn<>(I_I_BPartner.class, "URL", null);
	String COLUMNNAME_URL = "URL";

	/**
	 * Set URL3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setURL3 (@Nullable java.lang.String URL3);

	/**
	 * Get URL3.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getURL3();

	ModelColumn<I_I_BPartner, Object> COLUMN_URL3 = new ModelColumn<>(I_I_BPartner.class, "URL3", null);
	String COLUMNNAME_URL3 = "URL3";

	/**
	 * Set Vendor Category.
	 * Vendor Category
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVendorCategory (@Nullable java.lang.String VendorCategory);

	/**
	 * Get Vendor Category.
	 * Vendor Category
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVendorCategory();

	ModelColumn<I_I_BPartner, Object> COLUMN_VendorCategory = new ModelColumn<>(I_I_BPartner.class, "VendorCategory", null);
	String COLUMNNAME_VendorCategory = "VendorCategory";
}
