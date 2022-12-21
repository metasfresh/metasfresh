package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for GL_JournalLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_GL_JournalLine 
{

	String Table_Name = "GL_JournalLine";

//	/** AD_Table_ID=226 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Asset Group.
	 * Group of Assets
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Asset_Group_ID (int A_Asset_Group_ID);

	/**
	 * Get Asset Group.
	 * Group of Assets
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getA_Asset_Group_ID();

	@Nullable org.compiere.model.I_A_Asset_Group getA_Asset_Group();

	void setA_Asset_Group(@Nullable org.compiere.model.I_A_Asset_Group A_Asset_Group);

	ModelColumn<I_GL_JournalLine, org.compiere.model.I_A_Asset_Group> COLUMN_A_Asset_Group_ID = new ModelColumn<>(I_GL_JournalLine.class, "A_Asset_Group_ID", org.compiere.model.I_A_Asset_Group.class);
	String COLUMNNAME_A_Asset_Group_ID = "A_Asset_Group_ID";

	/**
	 * Set Asset.
	 * Asset used internally or by customers
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Asset_ID (int A_Asset_ID);

	/**
	 * Get Asset.
	 * Asset used internally or by customers
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getA_Asset_ID();

	@Nullable org.compiere.model.I_A_Asset getA_Asset();

	void setA_Asset(@Nullable org.compiere.model.I_A_Asset A_Asset);

	ModelColumn<I_GL_JournalLine, org.compiere.model.I_A_Asset> COLUMN_A_Asset_ID = new ModelColumn<>(I_GL_JournalLine.class, "A_Asset_ID", org.compiere.model.I_A_Asset.class);
	String COLUMNNAME_A_Asset_ID = "A_Asset_ID";

	/**
	 * Set Create Asset.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_CreateAsset (boolean A_CreateAsset);

	/**
	 * Get Create Asset.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isA_CreateAsset();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_A_CreateAsset = new ModelColumn<>(I_GL_JournalLine.class, "A_CreateAsset", null);
	String COLUMNNAME_A_CreateAsset = "A_CreateAsset";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setA_Processed (boolean A_Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isA_Processed();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_A_Processed = new ModelColumn<>(I_GL_JournalLine.class, "A_Processed", null);
	String COLUMNNAME_A_Processed = "A_Processed";

	/**
	 * Set Credit Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAccount_CR_ID (int Account_CR_ID);

	/**
	 * Get Credit Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAccount_CR_ID();

	@Nullable org.compiere.model.I_C_ValidCombination getAccount_CR();

	void setAccount_CR(@Nullable org.compiere.model.I_C_ValidCombination Account_CR);

	ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_ValidCombination> COLUMN_Account_CR_ID = new ModelColumn<>(I_GL_JournalLine.class, "Account_CR_ID", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_Account_CR_ID = "Account_CR_ID";

	/**
	 * Set Debit Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAccount_DR_ID (int Account_DR_ID);

	/**
	 * Get Debit Account.
	 *
	 * <br>Type: Account
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAccount_DR_ID();

	@Nullable org.compiere.model.I_C_ValidCombination getAccount_DR();

	void setAccount_DR(@Nullable org.compiere.model.I_C_ValidCombination Account_DR);

	ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_ValidCombination> COLUMN_Account_DR_ID = new ModelColumn<>(I_GL_JournalLine.class, "Account_DR_ID", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_Account_DR_ID = "Account_DR_ID";

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

	ModelColumn<I_GL_JournalLine, Object> COLUMN_AmtAcctCr = new ModelColumn<>(I_GL_JournalLine.class, "AmtAcctCr", null);
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

	ModelColumn<I_GL_JournalLine, Object> COLUMN_AmtAcctDr = new ModelColumn<>(I_GL_JournalLine.class, "AmtAcctDr", null);
	String COLUMNNAME_AmtAcctDr = "AmtAcctDr";

	/**
	 * Set Sum Credit (Group).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setAmtAcctGroupCr (@Nullable BigDecimal AmtAcctGroupCr);

	/**
	 * Get Sum Credit (Group).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	BigDecimal getAmtAcctGroupCr();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_AmtAcctGroupCr = new ModelColumn<>(I_GL_JournalLine.class, "AmtAcctGroupCr", null);
	String COLUMNNAME_AmtAcctGroupCr = "AmtAcctGroupCr";

	/**
	 * Set Sum Debit (Group).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setAmtAcctGroupDr (@Nullable BigDecimal AmtAcctGroupDr);

	/**
	 * Get Sum Debit (Group).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	BigDecimal getAmtAcctGroupDr();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_AmtAcctGroupDr = new ModelColumn<>(I_GL_JournalLine.class, "AmtAcctGroupDr", null);
	String COLUMNNAME_AmtAcctGroupDr = "AmtAcctGroupDr";

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

	ModelColumn<I_GL_JournalLine, Object> COLUMN_AmtSourceCr = new ModelColumn<>(I_GL_JournalLine.class, "AmtSourceCr", null);
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

	ModelColumn<I_GL_JournalLine, Object> COLUMN_AmtSourceDr = new ModelColumn<>(I_GL_JournalLine.class, "AmtSourceDr", null);
	String COLUMNNAME_AmtSourceDr = "AmtSourceDr";

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
	 * Set Conversiontype.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_ConversionType_ID (int C_ConversionType_ID);

	/**
	 * Get Conversiontype.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
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
	 * Set Automatic tax account (Credit).
	 * If an account is flagged as automatic tax account, the system will allow and help user to do manual tax bookings in GL journal
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCR_AutoTaxAccount (boolean CR_AutoTaxAccount);

	/**
	 * Get Automatic tax account (Credit).
	 * If an account is flagged as automatic tax account, the system will allow and help user to do manual tax bookings in GL journal
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCR_AutoTaxAccount();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_CR_AutoTaxAccount = new ModelColumn<>(I_GL_JournalLine.class, "CR_AutoTaxAccount", null);
	String COLUMNNAME_CR_AutoTaxAccount = "CR_AutoTaxAccount";

	/**
	 * Set Order (Credit).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCR_C_Order_ID (int CR_C_Order_ID);

	/**
	 * Get Order (Credit).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCR_C_Order_ID();

	@Nullable org.compiere.model.I_C_Order getCR_C_Order();

	void setCR_C_Order(@Nullable org.compiere.model.I_C_Order CR_C_Order);

	ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_Order> COLUMN_CR_C_Order_ID = new ModelColumn<>(I_GL_JournalLine.class, "CR_C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_CR_C_Order_ID = "CR_C_Order_ID";

	/**
	 * Set Material (Credit).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCR_M_Product_ID (int CR_M_Product_ID);

	/**
	 * Get Material (Credit).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCR_M_Product_ID();

	String COLUMNNAME_CR_M_Product_ID = "CR_M_Product_ID";

	/**
	 * Set Section Code (Credit).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCR_M_SectionCode_ID (int CR_M_SectionCode_ID);

	/**
	 * Get Section Code (Credit).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getCR_M_SectionCode_ID();

	@Nullable org.compiere.model.I_M_SectionCode getCR_M_SectionCode();

	void setCR_M_SectionCode(@Nullable org.compiere.model.I_M_SectionCode CR_M_SectionCode);

	ModelColumn<I_GL_JournalLine, org.compiere.model.I_M_SectionCode> COLUMN_CR_M_SectionCode_ID = new ModelColumn<>(I_GL_JournalLine.class, "CR_M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_CR_M_SectionCode_ID = "CR_M_SectionCode_ID";

	/**
	 * Set Tax account (credit).
	 *
	 * <br>Type: Account
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

	ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_ValidCombination> COLUMN_CR_Tax_Acct_ID = new ModelColumn<>(I_GL_JournalLine.class, "CR_Tax_Acct_ID", org.compiere.model.I_C_ValidCombination.class);
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
	 * Set Taxamount (Credit).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCR_TaxAmt (@Nullable BigDecimal CR_TaxAmt);

	/**
	 * Get Taxamount (Credit).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCR_TaxAmt();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_CR_TaxAmt = new ModelColumn<>(I_GL_JournalLine.class, "CR_TaxAmt", null);
	String COLUMNNAME_CR_TaxAmt = "CR_TaxAmt";

	/**
	 * Set Tax Base Amt (Credit).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCR_TaxBaseAmt (@Nullable BigDecimal CR_TaxBaseAmt);

	/**
	 * Get Tax Base Amt (Credit).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCR_TaxBaseAmt();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_CR_TaxBaseAmt = new ModelColumn<>(I_GL_JournalLine.class, "CR_TaxBaseAmt", null);
	String COLUMNNAME_CR_TaxBaseAmt = "CR_TaxBaseAmt";

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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getCR_TaxTotalAmt();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_CR_TaxTotalAmt = new ModelColumn<>(I_GL_JournalLine.class, "CR_TaxTotalAmt", null);
	String COLUMNNAME_CR_TaxTotalAmt = "CR_TaxTotalAmt";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_Created = new ModelColumn<>(I_GL_JournalLine.class, "Created", null);
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
	 * Set Currency Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCurrencyRate (BigDecimal CurrencyRate);

	/**
	 * Get Currency Rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getCurrencyRate();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_CurrencyRate = new ModelColumn<>(I_GL_JournalLine.class, "CurrencyRate", null);
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

	ModelColumn<I_GL_JournalLine, Object> COLUMN_DateAcct = new ModelColumn<>(I_GL_JournalLine.class, "DateAcct", null);
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

	ModelColumn<I_GL_JournalLine, Object> COLUMN_Description = new ModelColumn<>(I_GL_JournalLine.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Automatic tax account (Debit).
	 * If an account is flagged as automatic tax account, the system will allow and help user to do manual tax bookings in GL journal
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDR_AutoTaxAccount (boolean DR_AutoTaxAccount);

	/**
	 * Get Automatic tax account (Debit).
	 * If an account is flagged as automatic tax account, the system will allow and help user to do manual tax bookings in GL journal
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDR_AutoTaxAccount();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_DR_AutoTaxAccount = new ModelColumn<>(I_GL_JournalLine.class, "DR_AutoTaxAccount", null);
	String COLUMNNAME_DR_AutoTaxAccount = "DR_AutoTaxAccount";

	/**
	 * Set Order (Debit).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDR_C_Order_ID (int DR_C_Order_ID);

	/**
	 * Get Order (Debit).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDR_C_Order_ID();

	@Nullable org.compiere.model.I_C_Order getDR_C_Order();

	void setDR_C_Order(@Nullable org.compiere.model.I_C_Order DR_C_Order);

	ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_Order> COLUMN_DR_C_Order_ID = new ModelColumn<>(I_GL_JournalLine.class, "DR_C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_DR_C_Order_ID = "DR_C_Order_ID";

	/**
	 * Set Material (Debit).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDR_M_Product_ID (int DR_M_Product_ID);

	/**
	 * Get Material (Debit).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDR_M_Product_ID();

	String COLUMNNAME_DR_M_Product_ID = "DR_M_Product_ID";

	/**
	 * Set Section Code (Debit).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDR_M_SectionCode_ID (int DR_M_SectionCode_ID);

	/**
	 * Get Section Code (Debit).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getDR_M_SectionCode_ID();

	@Nullable org.compiere.model.I_M_SectionCode getDR_M_SectionCode();

	void setDR_M_SectionCode(@Nullable org.compiere.model.I_M_SectionCode DR_M_SectionCode);

	ModelColumn<I_GL_JournalLine, org.compiere.model.I_M_SectionCode> COLUMN_DR_M_SectionCode_ID = new ModelColumn<>(I_GL_JournalLine.class, "DR_M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_DR_M_SectionCode_ID = "DR_M_SectionCode_ID";

	/**
	 * Set Tax Account (Debit).
	 *
	 * <br>Type: Account
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

	ModelColumn<I_GL_JournalLine, org.compiere.model.I_C_ValidCombination> COLUMN_DR_Tax_Acct_ID = new ModelColumn<>(I_GL_JournalLine.class, "DR_Tax_Acct_ID", org.compiere.model.I_C_ValidCombination.class);
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
	 * Set Taxamount (Debit).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDR_TaxAmt (@Nullable BigDecimal DR_TaxAmt);

	/**
	 * Get Taxamount (Debit).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDR_TaxAmt();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_DR_TaxAmt = new ModelColumn<>(I_GL_JournalLine.class, "DR_TaxAmt", null);
	String COLUMNNAME_DR_TaxAmt = "DR_TaxAmt";

	/**
	 * Set Tax Base Amount (Debit).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDR_TaxBaseAmt (@Nullable BigDecimal DR_TaxBaseAmt);

	/**
	 * Get Tax Base Amount (Debit).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getDR_TaxBaseAmt();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_DR_TaxBaseAmt = new ModelColumn<>(I_GL_JournalLine.class, "DR_TaxBaseAmt", null);
	String COLUMNNAME_DR_TaxBaseAmt = "DR_TaxBaseAmt";

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

	ModelColumn<I_GL_JournalLine, Object> COLUMN_DR_TaxTotalAmt = new ModelColumn<>(I_GL_JournalLine.class, "DR_TaxTotalAmt", null);
	String COLUMNNAME_DR_TaxTotalAmt = "DR_TaxTotalAmt";

	/**
	 * Set GL Journal en_US 315.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGL_Journal_ID (int GL_Journal_ID);

	/**
	 * Get GL Journal en_US 315.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGL_Journal_ID();

	org.compiere.model.I_GL_Journal getGL_Journal();

	void setGL_Journal(org.compiere.model.I_GL_Journal GL_Journal);

	ModelColumn<I_GL_JournalLine, org.compiere.model.I_GL_Journal> COLUMN_GL_Journal_ID = new ModelColumn<>(I_GL_JournalLine.class, "GL_Journal_ID", org.compiere.model.I_GL_Journal.class);
	String COLUMNNAME_GL_Journal_ID = "GL_Journal_ID";

	/**
	 * Set Journal Line (Group).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGL_JournalLine_Group (int GL_JournalLine_Group);

	/**
	 * Get Journal Line (Group).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGL_JournalLine_Group();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_GL_JournalLine_Group = new ModelColumn<>(I_GL_JournalLine.class, "GL_JournalLine_Group", null);
	String COLUMNNAME_GL_JournalLine_Group = "GL_JournalLine_Group";

	/**
	 * Set Journal Line.
	 * General Ledger Journal Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGL_JournalLine_ID (int GL_JournalLine_ID);

	/**
	 * Get Journal Line.
	 * General Ledger Journal Line
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getGL_JournalLine_ID();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_GL_JournalLine_ID = new ModelColumn<>(I_GL_JournalLine.class, "GL_JournalLine_ID", null);
	String COLUMNNAME_GL_JournalLine_ID = "GL_JournalLine_ID";

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

	ModelColumn<I_GL_JournalLine, Object> COLUMN_IsActive = new ModelColumn<>(I_GL_JournalLine.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Haben erlaubt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowAccountCR (boolean IsAllowAccountCR);

	/**
	 * Get Haben erlaubt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowAccountCR();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_IsAllowAccountCR = new ModelColumn<>(I_GL_JournalLine.class, "IsAllowAccountCR", null);
	String COLUMNNAME_IsAllowAccountCR = "IsAllowAccountCR";

	/**
	 * Set Soll erlaubt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAllowAccountDR (boolean IsAllowAccountDR);

	/**
	 * Get Soll erlaubt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAllowAccountDR();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_IsAllowAccountDR = new ModelColumn<>(I_GL_JournalLine.class, "IsAllowAccountDR", null);
	String COLUMNNAME_IsAllowAccountDR = "IsAllowAccountDR";

	/**
	 * Set Generated.
	 * This Line is generated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsGenerated (boolean IsGenerated);

	/**
	 * Get Generated.
	 * This Line is generated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isGenerated();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_IsGenerated = new ModelColumn<>(I_GL_JournalLine.class, "IsGenerated", null);
	String COLUMNNAME_IsGenerated = "IsGenerated";

	/**
	 * Set Split Accounting.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSplitAcctTrx (boolean IsSplitAcctTrx);

	/**
	 * Get Split Accounting.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSplitAcctTrx();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_IsSplitAcctTrx = new ModelColumn<>(I_GL_JournalLine.class, "IsSplitAcctTrx", null);
	String COLUMNNAME_IsSplitAcctTrx = "IsSplitAcctTrx";

	/**
	 * Set SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLine (int Line);

	/**
	 * Get SeqNo..
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLine();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_Line = new ModelColumn<>(I_GL_JournalLine.class, "Line", null);
	String COLUMNNAME_Line = "Line";

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

	ModelColumn<I_GL_JournalLine, org.compiere.model.I_M_SectionCode> COLUMN_M_SectionCode_ID = new ModelColumn<>(I_GL_JournalLine.class, "M_SectionCode_ID", org.compiere.model.I_M_SectionCode.class);
	String COLUMNNAME_M_SectionCode_ID = "M_SectionCode_ID";

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

	ModelColumn<I_GL_JournalLine, Object> COLUMN_Processed = new ModelColumn<>(I_GL_JournalLine.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Quantity.
	 * Quantity
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQty (@Nullable BigDecimal Qty);

	/**
	 * Get Quantity.
	 * Quantity
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getQty();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_Qty = new ModelColumn<>(I_GL_JournalLine.class, "Qty", null);
	String COLUMNNAME_Qty = "Qty";

	/**
	 * Set Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setType (java.lang.String Type);

	/**
	 * Get Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getType();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_Type = new ModelColumn<>(I_GL_JournalLine.class, "Type", null);
	String COLUMNNAME_Type = "Type";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_GL_JournalLine, Object> COLUMN_Updated = new ModelColumn<>(I_GL_JournalLine.class, "Updated", null);
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
}
