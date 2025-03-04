package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_ElementValue
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_ElementValue 
{

	String Table_Name = "C_ElementValue";

//	/** AD_Table_ID=188 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Kontovorzeichen.
	 * Indicates the Natural Sign of the Account as a Debit or Credit
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAccountSign (java.lang.String AccountSign);

	/**
	 * Get Kontovorzeichen.
	 * Indicates the Natural Sign of the Account as a Debit or Credit
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAccountSign();

	ModelColumn<I_C_ElementValue, Object> COLUMN_AccountSign = new ModelColumn<>(I_C_ElementValue.class, "AccountSign", null);
	String COLUMNNAME_AccountSign = "AccountSign";

	/**
	 * Set Kontenart.
	 * Indicates the type of account
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAccountType (java.lang.String AccountType);

	/**
	 * Get Kontenart.
	 * Indicates the type of account
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAccountType();

	ModelColumn<I_C_ElementValue, Object> COLUMN_AccountType = new ModelColumn<>(I_C_ElementValue.class, "AccountType", null);
	String COLUMNNAME_AccountType = "AccountType";

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
	 * Set Element.
	 * Accounting Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Element_ID (int C_Element_ID);

	/**
	 * Get Element.
	 * Accounting Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Element_ID();

	String COLUMNNAME_C_Element_ID = "C_Element_ID";

	/**
	 * Set Kontenart.
	 * Account Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_ElementValue_ID (int C_ElementValue_ID);

	/**
	 * Get Kontenart.
	 * Account Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_ElementValue_ID();

	ModelColumn<I_C_ElementValue, Object> COLUMN_C_ElementValue_ID = new ModelColumn<>(I_C_ElementValue.class, "C_ElementValue_ID", null);
	String COLUMNNAME_C_ElementValue_ID = "C_ElementValue_ID";

	/**
	 * Set Tax.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Tax_ID (int C_Tax_ID);

	/**
	 * Get Tax.
	 * Tax identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Tax_ID();

	String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_ElementValue, Object> COLUMN_Created = new ModelColumn<>(I_C_ElementValue.class, "Created", null);
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
	 * Set Default Account.
	 * Name of the Default Account Column
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDefault_Account (@Nullable java.lang.String Default_Account);

	/**
	 * Get Default Account.
	 * Name of the Default Account Column
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDefault_Account();

	ModelColumn<I_C_ElementValue, Object> COLUMN_Default_Account = new ModelColumn<>(I_C_ElementValue.class, "Default_Account", null);
	String COLUMNNAME_Default_Account = "Default_Account";

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

	ModelColumn<I_C_ElementValue, Object> COLUMN_Description = new ModelColumn<>(I_C_ElementValue.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_C_ElementValue, Object> COLUMN_IsActive = new ModelColumn<>(I_C_ElementValue.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Automatic tax account.
	 * If an account is flagged as automatic tax account, the system will allow and help user to do manual tax bookings in GL journal
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAutoTaxAccount (boolean IsAutoTaxAccount);

	/**
	 * Get Automatic tax account.
	 * If an account is flagged as automatic tax account, the system will allow and help user to do manual tax bookings in GL journal
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAutoTaxAccount();

	ModelColumn<I_C_ElementValue, Object> COLUMN_IsAutoTaxAccount = new ModelColumn<>(I_C_ElementValue.class, "IsAutoTaxAccount", null);
	String COLUMNNAME_IsAutoTaxAccount = "IsAutoTaxAccount";

	/**
	 * Set Bankkonto.
	 * Indicates if this is the Bank Account
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsBankAccount (boolean IsBankAccount);

	/**
	 * Get Bankkonto.
	 * Indicates if this is the Bank Account
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isBankAccount();

	ModelColumn<I_C_ElementValue, Object> COLUMN_IsBankAccount = new ModelColumn<>(I_C_ElementValue.class, "IsBankAccount", null);
	String COLUMNNAME_IsBankAccount = "IsBankAccount";

	/**
	 * Set Belegartgesteuert.
	 * Control account - If an account is controlled by a document, you cannot post manually to it
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsDocControlled (boolean IsDocControlled);

	/**
	 * Get Belegartgesteuert.
	 * Control account - If an account is controlled by a document, you cannot post manually to it
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isDocControlled();

	ModelColumn<I_C_ElementValue, Object> COLUMN_IsDocControlled = new ModelColumn<>(I_C_ElementValue.class, "IsDocControlled", null);
	String COLUMNNAME_IsDocControlled = "IsDocControlled";

	/**
	 * Set Foreign Currency Account.
	 * Balances in foreign currency accounts are held in the nominated currency
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsForeignCurrency (boolean IsForeignCurrency);

	/**
	 * Get Foreign Currency Account.
	 * Balances in foreign currency accounts are held in the nominated currency
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isForeignCurrency();

	ModelColumn<I_C_ElementValue, Object> COLUMN_IsForeignCurrency = new ModelColumn<>(I_C_ElementValue.class, "IsForeignCurrency", null);
	String COLUMNNAME_IsForeignCurrency = "IsForeignCurrency";

	/**
	 * Set Kostenstelle ist Pflichtangabe.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsMandatoryActivity (boolean IsMandatoryActivity);

	/**
	 * Get Kostenstelle ist Pflichtangabe.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isMandatoryActivity();

	ModelColumn<I_C_ElementValue, Object> COLUMN_IsMandatoryActivity = new ModelColumn<>(I_C_ElementValue.class, "IsMandatoryActivity", null);
	String COLUMNNAME_IsMandatoryActivity = "IsMandatoryActivity";

	/**
	 * Set Summary Level.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSummary (boolean IsSummary);

	/**
	 * Get Summary Level.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSummary();

	ModelColumn<I_C_ElementValue, Object> COLUMN_IsSummary = new ModelColumn<>(I_C_ElementValue.class, "IsSummary", null);
	String COLUMNNAME_IsSummary = "IsSummary";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_C_ElementValue, Object> COLUMN_Name = new ModelColumn<>(I_C_ElementValue.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Parent.
	 * Parent of Entity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setParent_ID (int Parent_ID);

	/**
	 * Get Parent.
	 * Parent of Entity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getParent_ID();

	@Nullable org.compiere.model.I_C_ElementValue getParent();

	void setParent(@Nullable org.compiere.model.I_C_ElementValue Parent);

	ModelColumn<I_C_ElementValue, org.compiere.model.I_C_ElementValue> COLUMN_Parent_ID = new ModelColumn<>(I_C_ElementValue.class, "Parent_ID", org.compiere.model.I_C_ElementValue.class);
	String COLUMNNAME_Parent_ID = "Parent_ID";

	/**
	 * Set Buchen "Ist".
	 * Actual Values can be posted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPostActual (boolean PostActual);

	/**
	 * Get Buchen "Ist".
	 * Actual Values can be posted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPostActual();

	ModelColumn<I_C_ElementValue, Object> COLUMN_PostActual = new ModelColumn<>(I_C_ElementValue.class, "PostActual", null);
	String COLUMNNAME_PostActual = "PostActual";

	/**
	 * Set Buchen "Budget".
	 * Budget values can be posted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPostBudget (boolean PostBudget);

	/**
	 * Get Buchen "Budget".
	 * Budget values can be posted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPostBudget();

	ModelColumn<I_C_ElementValue, Object> COLUMN_PostBudget = new ModelColumn<>(I_C_ElementValue.class, "PostBudget", null);
	String COLUMNNAME_PostBudget = "PostBudget";

	/**
	 * Set Buchen "Reservierung".
	 * Post commitments to this account
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPostEncumbrance (boolean PostEncumbrance);

	/**
	 * Get Buchen "Reservierung".
	 * Post commitments to this account
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPostEncumbrance();

	ModelColumn<I_C_ElementValue, Object> COLUMN_PostEncumbrance = new ModelColumn<>(I_C_ElementValue.class, "PostEncumbrance", null);
	String COLUMNNAME_PostEncumbrance = "PostEncumbrance";

	/**
	 * Set Buchen "statistisch".
	 * Post statistical quantities to this account?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPostStatistical (boolean PostStatistical);

	/**
	 * Get Buchen "statistisch".
	 * Post statistical quantities to this account?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPostStatistical();

	ModelColumn<I_C_ElementValue, Object> COLUMN_PostStatistical = new ModelColumn<>(I_C_ElementValue.class, "PostStatistical", null);
	String COLUMNNAME_PostStatistical = "PostStatistical";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_C_ElementValue, Object> COLUMN_SeqNo = new ModelColumn<>(I_C_ElementValue.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_ElementValue, Object> COLUMN_Updated = new ModelColumn<>(I_C_ElementValue.class, "Updated", null);
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
	 * Set Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValidFrom (@Nullable java.sql.Timestamp ValidFrom);

	/**
	 * Get Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValidFrom();

	ModelColumn<I_C_ElementValue, Object> COLUMN_ValidFrom = new ModelColumn<>(I_C_ElementValue.class, "ValidFrom", null);
	String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setValidTo (@Nullable java.sql.Timestamp ValidTo);

	/**
	 * Get Valid to.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getValidTo();

	ModelColumn<I_C_ElementValue, Object> COLUMN_ValidTo = new ModelColumn<>(I_C_ElementValue.class, "ValidTo", null);
	String COLUMNNAME_ValidTo = "ValidTo";

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_C_ElementValue, Object> COLUMN_Value = new ModelColumn<>(I_C_ElementValue.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
