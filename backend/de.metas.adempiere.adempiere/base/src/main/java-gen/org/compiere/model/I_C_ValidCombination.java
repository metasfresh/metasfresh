package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

import java.math.BigDecimal;

/** Generated Interface for C_ValidCombination
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_ValidCombination 
{

	String Table_Name = "C_ValidCombination";

//	/** AD_Table_ID=176 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


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

	org.compiere.model.I_C_ElementValue getAccount();

	void setAccount(org.compiere.model.I_C_ElementValue Account);

	ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_ElementValue> COLUMN_Account_ID = new ModelColumn<>(I_C_ValidCombination.class, "Account_ID", org.compiere.model.I_C_ElementValue.class);
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

	ModelColumn<I_C_ValidCombination, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new ModelColumn<>(I_C_ValidCombination.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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

	@Nullable org.compiere.model.I_AD_Org getAD_OrgTrx();

	void setAD_OrgTrx(@Nullable org.compiere.model.I_AD_Org AD_OrgTrx);

	ModelColumn<I_C_ValidCombination, org.compiere.model.I_AD_Org> COLUMN_AD_OrgTrx_ID = new ModelColumn<>(I_C_ValidCombination.class, "AD_OrgTrx_ID", org.compiere.model.I_AD_Org.class);
	String COLUMNNAME_AD_OrgTrx_ID = "AD_OrgTrx_ID";

	/**
	 * Set Alias.
	 * Defines an alternate method of indicating an account combination.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAlias (@Nullable java.lang.String Alias);

	/**
	 * Get Alias.
	 * Defines an alternate method of indicating an account combination.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAlias();

	ModelColumn<I_C_ValidCombination, Object> COLUMN_Alias = new ModelColumn<>(I_C_ValidCombination.class, "Alias", null);
	String COLUMNNAME_Alias = "Alias";

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

	ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new ModelColumn<>(I_C_ValidCombination.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
	String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Activity.
	 * Business Activity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Activity.
	 * Business Activity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Activity_ID();

	@Nullable org.compiere.model.I_C_Activity getC_Activity();

	void setC_Activity(@Nullable org.compiere.model.I_C_Activity C_Activity);

	ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_Activity> COLUMN_C_Activity_ID = new ModelColumn<>(I_C_ValidCombination.class, "C_Activity_ID", org.compiere.model.I_C_Activity.class);
	String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

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

	@Nullable org.compiere.model.I_C_BPartner getC_BPartner();

	void setC_BPartner(@Nullable org.compiere.model.I_C_BPartner C_BPartner);

	ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_BPartner> COLUMN_C_BPartner_ID = new ModelColumn<>(I_C_ValidCombination.class, "C_BPartner_ID", org.compiere.model.I_C_BPartner.class);
	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Campaign_ID (int C_Campaign_ID);

	/**
	 * Get Campaign.
	 * Marketing Campaign
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Campaign_ID();

	@Nullable org.compiere.model.I_C_Campaign getC_Campaign();

	void setC_Campaign(@Nullable org.compiere.model.I_C_Campaign C_Campaign);

	ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_Campaign> COLUMN_C_Campaign_ID = new ModelColumn<>(I_C_ValidCombination.class, "C_Campaign_ID", org.compiere.model.I_C_Campaign.class);
	String COLUMNNAME_C_Campaign_ID = "C_Campaign_ID";

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

	ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_Calendar> COLUMN_C_Harvesting_Calendar_ID = new ModelColumn<>(I_C_ValidCombination.class, "C_Harvesting_Calendar_ID", org.compiere.model.I_C_Calendar.class);
	String COLUMNNAME_C_Harvesting_Calendar_ID = "C_Harvesting_Calendar_ID";

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

	ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_Location> COLUMN_C_LocFrom_ID = new ModelColumn<>(I_C_ValidCombination.class, "C_LocFrom_ID", org.compiere.model.I_C_Location.class);
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

	ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_Location> COLUMN_C_LocTo_ID = new ModelColumn<>(I_C_ValidCombination.class, "C_LocTo_ID", org.compiere.model.I_C_Location.class);
	String COLUMNNAME_C_LocTo_ID = "C_LocTo_ID";

	/**
	 * Set Combination.
	 * Unique combination of account elements
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCombination (@Nullable java.lang.String Combination);

	/**
	 * Get Combination.
	 * Unique combination of account elements
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCombination();

	ModelColumn<I_C_ValidCombination, Object> COLUMN_Combination = new ModelColumn<>(I_C_ValidCombination.class, "Combination", null);
	String COLUMNNAME_Combination = "Combination";

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

	ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_Order> COLUMN_C_OrderSO_ID = new ModelColumn<>(I_C_ValidCombination.class, "C_OrderSO_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_OrderSO_ID = "C_OrderSO_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: Table
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

	ModelColumn<I_C_ValidCombination, Object> COLUMN_Created = new ModelColumn<>(I_C_ValidCombination.class, "Created", null);
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
	 * Set Sales Region.
	 * Sales coverage region
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_SalesRegion_ID (int C_SalesRegion_ID);

	/**
	 * Get Sales Region.
	 * Sales coverage region
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_SalesRegion_ID();

	@Nullable org.compiere.model.I_C_SalesRegion getC_SalesRegion();

	void setC_SalesRegion(@Nullable org.compiere.model.I_C_SalesRegion C_SalesRegion);

	ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_SalesRegion> COLUMN_C_SalesRegion_ID = new ModelColumn<>(I_C_ValidCombination.class, "C_SalesRegion_ID", org.compiere.model.I_C_SalesRegion.class);
	String COLUMNNAME_C_SalesRegion_ID = "C_SalesRegion_ID";

	/**
	 * Set Sub Account.
	 * Sub account for Element Value
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_SubAcct_ID (int C_SubAcct_ID);

	/**
	 * Get Sub Account.
	 * Sub account for Element Value
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_SubAcct_ID();

	@Nullable org.compiere.model.I_C_SubAcct getC_SubAcct();

	void setC_SubAcct(@Nullable org.compiere.model.I_C_SubAcct C_SubAcct);

	ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_SubAcct> COLUMN_C_SubAcct_ID = new ModelColumn<>(I_C_ValidCombination.class, "C_SubAcct_ID", org.compiere.model.I_C_SubAcct.class);
	String COLUMNNAME_C_SubAcct_ID = "C_SubAcct_ID";

	/**
	 * Set Combination.
	 * Valid Account Combination
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_ValidCombination_ID (int C_ValidCombination_ID);

	/**
	 * Get Combination.
	 * Valid Account Combination
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_ValidCombination_ID();

	ModelColumn<I_C_ValidCombination, Object> COLUMN_C_ValidCombination_ID = new ModelColumn<>(I_C_ValidCombination.class, "C_ValidCombination_ID", null);
	String COLUMNNAME_C_ValidCombination_ID = "C_ValidCombination_ID";

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

	ModelColumn<I_C_ValidCombination, Object> COLUMN_Description = new ModelColumn<>(I_C_ValidCombination.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_Year> COLUMN_Harvesting_Year_ID = new ModelColumn<>(I_C_ValidCombination.class, "Harvesting_Year_ID", org.compiere.model.I_C_Year.class);
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

	ModelColumn<I_C_ValidCombination, Object> COLUMN_IsActive = new ModelColumn<>(I_C_ValidCombination.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Fully Qualified.
	 * This account is fully qualified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsFullyQualified (boolean IsFullyQualified);

	/**
	 * Get Fully Qualified.
	 * This account is fully qualified
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isFullyQualified();

	ModelColumn<I_C_ValidCombination, Object> COLUMN_IsFullyQualified = new ModelColumn<>(I_C_ValidCombination.class, "IsFullyQualified", null);
	String COLUMNNAME_IsFullyQualified = "IsFullyQualified";

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

	@Nullable org.compiere.model.I_M_Product getM_Product();

	void setM_Product(@Nullable org.compiere.model.I_M_Product M_Product);

	ModelColumn<I_C_ValidCombination, org.compiere.model.I_M_Product> COLUMN_M_Product_ID = new ModelColumn<>(I_C_ValidCombination.class, "M_Product_ID", org.compiere.model.I_M_Product.class);
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

	ModelColumn<I_C_ValidCombination, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_C_ValidCombination.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_ValidCombination, Object> COLUMN_Updated = new ModelColumn<>(I_C_ValidCombination.class, "Updated", null);
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

	ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_ElementValue> COLUMN_User1_ID = new ModelColumn<>(I_C_ValidCombination.class, "User1_ID", org.compiere.model.I_C_ElementValue.class);
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

	ModelColumn<I_C_ValidCombination, org.compiere.model.I_C_ElementValue> COLUMN_User2_ID = new ModelColumn<>(I_C_ValidCombination.class, "User2_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_User2_ID = "User2_ID";

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

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElementNumber1 = new ModelColumn<>(I_C_ValidCombination.class, "UserElementNumber1", null);
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

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElementNumber2 = new ModelColumn<>(I_C_ValidCombination.class, "UserElementNumber2", null);
	String COLUMNNAME_UserElementNumber2 = "UserElementNumber2";

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

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElement1_ID = new ModelColumn<>(I_C_ValidCombination.class, "UserElement1_ID", null);
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

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElement2_ID = new ModelColumn<>(I_C_ValidCombination.class, "UserElement2_ID", null);
	String COLUMNNAME_UserElement2_ID = "UserElement2_ID";

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

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElementString1 = new ModelColumn<>(I_C_ValidCombination.class, "UserElementString1", null);
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

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElementString2 = new ModelColumn<>(I_C_ValidCombination.class, "UserElementString2", null);
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

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElementString3 = new ModelColumn<>(I_C_ValidCombination.class, "UserElementString3", null);
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

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElementString4 = new ModelColumn<>(I_C_ValidCombination.class, "UserElementString4", null);
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

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElementString5 = new ModelColumn<>(I_C_ValidCombination.class, "UserElementString5", null);
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

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElementString6 = new ModelColumn<>(I_C_ValidCombination.class, "UserElementString6", null);
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

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElementString7 = new ModelColumn<>(I_C_ValidCombination.class, "UserElementString7", null);
	String COLUMNNAME_UserElementString7 = "UserElementString7";

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

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElementDate1 = new ModelColumn<>(I_C_ValidCombination.class, "UserElementDate1", null);
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

	ModelColumn<I_C_ValidCombination, Object> COLUMN_UserElementDate2 = new ModelColumn<>(I_C_ValidCombination.class, "UserElementDate2", null);
	String COLUMNNAME_UserElementDate2 = "UserElementDate2";

}
