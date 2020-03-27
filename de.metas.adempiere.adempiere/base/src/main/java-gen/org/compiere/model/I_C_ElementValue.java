package org.compiere.model;


/** Generated Interface for C_ElementValue
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_ElementValue 
{

    /** TableName=C_ElementValue */
    public static final String Table_Name = "C_ElementValue";

    /** AD_Table_ID=188 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(2);

    /** Load Meta Data */

	/**
	 * Set Kontovorzeichen.
	 * Indicates the Natural Sign of the Account as a Debit or Credit
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAccountSign (java.lang.String AccountSign);

	/**
	 * Get Kontovorzeichen.
	 * Indicates the Natural Sign of the Account as a Debit or Credit
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAccountSign();

    /** Column definition for AccountSign */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_AccountSign = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "AccountSign", null);
    /** Column name AccountSign */
    public static final String COLUMNNAME_AccountSign = "AccountSign";

	/**
	 * Set Kontenart.
	 * Indicates the type of account
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAccountType (java.lang.String AccountType);

	/**
	 * Get Kontenart.
	 * Indicates the type of account
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAccountType();

    /** Column definition for AccountType */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_AccountType = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "AccountType", null);
    /** Column name AccountType */
    public static final String COLUMNNAME_AccountType = "AccountType";

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Activity_ID (int C_Activity_ID);

	/**
	 * Get Kostenstelle.
	 * Kostenstelle
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Activity_ID();

    /** Column name C_Activity_ID */
    public static final String COLUMNNAME_C_Activity_ID = "C_Activity_ID";

	/**
	 * Set Bankverbindung.
	 * Bankverbindung des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Bankverbindung.
	 * Bankverbindung des Geschäftspartners
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BP_BankAccount_ID();

	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount();

	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount);

    /** Column definition for C_BP_BankAccount_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, org.compiere.model.I_C_BP_BankAccount> COLUMN_C_BP_BankAccount_ID = new org.adempiere.model.ModelColumn<I_C_ElementValue, org.compiere.model.I_C_BP_BankAccount>(I_C_ElementValue.class, "C_BP_BankAccount_ID", org.compiere.model.I_C_BP_BankAccount.class);
    /** Column name C_BP_BankAccount_ID */
    public static final String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Währung.
	 * The Currency for this record
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Currency_ID();

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Element.
	 * Accounting Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Element_ID (int C_Element_ID);

	/**
	 * Get Element.
	 * Accounting Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Element_ID();

	public org.compiere.model.I_C_Element getC_Element();

	public void setC_Element(org.compiere.model.I_C_Element C_Element);

    /** Column definition for C_Element_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, org.compiere.model.I_C_Element> COLUMN_C_Element_ID = new org.adempiere.model.ModelColumn<I_C_ElementValue, org.compiere.model.I_C_Element>(I_C_ElementValue.class, "C_Element_ID", org.compiere.model.I_C_Element.class);
    /** Column name C_Element_ID */
    public static final String COLUMNNAME_C_Element_ID = "C_Element_ID";

	/**
	 * Set Kontenart.
	 * Account Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_ElementValue_ID (int C_ElementValue_ID);

	/**
	 * Get Kontenart.
	 * Account Element
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_ElementValue_ID();

    /** Column definition for C_ElementValue_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_C_ElementValue_ID = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "C_ElementValue_ID", null);
    /** Column name C_ElementValue_ID */
    public static final String COLUMNNAME_C_ElementValue_ID = "C_ElementValue_ID";

	/**
	 * Set Steuer.
	 * Steuerart
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Tax_ID (int C_Tax_ID);

	/**
	 * Get Steuer.
	 * Steuerart
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Tax_ID();

    /** Column name C_Tax_ID */
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Automatic tax account.
	 * If an account is flagged as automatic tax account, the system will allow and help user to do manual tax bookings in GL journal
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAutoTaxAccount (boolean IsAutoTaxAccount);

	/**
	 * Get Automatic tax account.
	 * If an account is flagged as automatic tax account, the system will allow and help user to do manual tax bookings in GL journal
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAutoTaxAccount();

    /** Column definition for IsAutoTaxAccount */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_IsAutoTaxAccount = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "IsAutoTaxAccount", null);
    /** Column name IsAutoTaxAccount */
    public static final String COLUMNNAME_IsAutoTaxAccount = "IsAutoTaxAccount";

	/**
	 * Set Bankkonto.
	 * Indicates if this is the Bank Account
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsBankAccount (boolean IsBankAccount);

	/**
	 * Get Bankkonto.
	 * Indicates if this is the Bank Account
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isBankAccount();

    /** Column definition for IsBankAccount */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_IsBankAccount = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "IsBankAccount", null);
    /** Column name IsBankAccount */
    public static final String COLUMNNAME_IsBankAccount = "IsBankAccount";

	/**
	 * Set Belegartgesteuert.
	 * Control account - If an account is controlled by a document, you cannot post manually to it
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsDocControlled (boolean IsDocControlled);

	/**
	 * Get Belegartgesteuert.
	 * Control account - If an account is controlled by a document, you cannot post manually to it
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isDocControlled();

    /** Column definition for IsDocControlled */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_IsDocControlled = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "IsDocControlled", null);
    /** Column name IsDocControlled */
    public static final String COLUMNNAME_IsDocControlled = "IsDocControlled";

	/**
	 * Set Foreign Currency Account.
	 * Balances in foreign currency accounts are held in the nominated currency
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsForeignCurrency (boolean IsForeignCurrency);

	/**
	 * Get Foreign Currency Account.
	 * Balances in foreign currency accounts are held in the nominated currency
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isForeignCurrency();

    /** Column definition for IsForeignCurrency */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_IsForeignCurrency = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "IsForeignCurrency", null);
    /** Column name IsForeignCurrency */
    public static final String COLUMNNAME_IsForeignCurrency = "IsForeignCurrency";

	/**
	 * Set Kostenstelle ist Pflichtangabe.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsMandatoryActivity (boolean IsMandatoryActivity);

	/**
	 * Get Kostenstelle ist Pflichtangabe.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isMandatoryActivity();

    /** Column definition for IsMandatoryActivity */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_IsMandatoryActivity = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "IsMandatoryActivity", null);
    /** Column name IsMandatoryActivity */
    public static final String COLUMNNAME_IsMandatoryActivity = "IsMandatoryActivity";

	/**
	 * Set Zusammenfassungseintrag.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSummary (boolean IsSummary);

	/**
	 * Get Zusammenfassungseintrag.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSummary();

    /** Column definition for IsSummary */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_IsSummary = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "IsSummary", null);
    /** Column name IsSummary */
    public static final String COLUMNNAME_IsSummary = "IsSummary";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Parent.
	 * Parent of Entity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setParent_ID (int Parent_ID);

	/**
	 * Get Parent.
	 * Parent of Entity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getParent_ID();

	public org.compiere.model.I_C_ElementValue getParent();

	public void setParent(org.compiere.model.I_C_ElementValue Parent);

    /** Column definition for Parent_ID */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, org.compiere.model.I_C_ElementValue> COLUMN_Parent_ID = new org.adempiere.model.ModelColumn<I_C_ElementValue, org.compiere.model.I_C_ElementValue>(I_C_ElementValue.class, "Parent_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name Parent_ID */
    public static final String COLUMNNAME_Parent_ID = "Parent_ID";

	/**
	 * Set Buchen "Ist".
	 * Actual Values can be posted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPostActual (boolean PostActual);

	/**
	 * Get Buchen "Ist".
	 * Actual Values can be posted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPostActual();

    /** Column definition for PostActual */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_PostActual = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "PostActual", null);
    /** Column name PostActual */
    public static final String COLUMNNAME_PostActual = "PostActual";

	/**
	 * Set Buchen "Budget".
	 * Budget values can be posted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPostBudget (boolean PostBudget);

	/**
	 * Get Buchen "Budget".
	 * Budget values can be posted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPostBudget();

    /** Column definition for PostBudget */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_PostBudget = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "PostBudget", null);
    /** Column name PostBudget */
    public static final String COLUMNNAME_PostBudget = "PostBudget";

	/**
	 * Set Buchen "Reservierung".
	 * Post commitments to this account
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPostEncumbrance (boolean PostEncumbrance);

	/**
	 * Get Buchen "Reservierung".
	 * Post commitments to this account
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPostEncumbrance();

    /** Column definition for PostEncumbrance */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_PostEncumbrance = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "PostEncumbrance", null);
    /** Column name PostEncumbrance */
    public static final String COLUMNNAME_PostEncumbrance = "PostEncumbrance";

	/**
	 * Set Buchen "statistisch".
	 * Post statistical quantities to this account?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPostStatistical (boolean PostStatistical);

	/**
	 * Get Buchen "statistisch".
	 * Post statistical quantities to this account?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPostStatistical();

    /** Column definition for PostStatistical */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_PostStatistical = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "PostStatistical", null);
    /** Column name PostStatistical */
    public static final String COLUMNNAME_PostStatistical = "PostStatistical";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Gültig ab.
	 * Valid from including this date (first day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Gültig ab.
	 * Valid from including this date (first day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidFrom();

    /** Column definition for ValidFrom */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_ValidFrom = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "ValidFrom", null);
    /** Column name ValidFrom */
    public static final String COLUMNNAME_ValidFrom = "ValidFrom";

	/**
	 * Set Gültig bis.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValidTo (java.sql.Timestamp ValidTo);

	/**
	 * Get Gültig bis.
	 * Valid to including this date (last day)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getValidTo();

    /** Column definition for ValidTo */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_ValidTo = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "ValidTo", null);
    /** Column name ValidTo */
    public static final String COLUMNNAME_ValidTo = "ValidTo";

	/**
	 * Set Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_C_ElementValue, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_C_ElementValue, Object>(I_C_ElementValue.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
