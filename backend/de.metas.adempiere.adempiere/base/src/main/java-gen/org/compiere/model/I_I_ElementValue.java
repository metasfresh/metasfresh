package org.compiere.model;


/** Generated Interface for I_ElementValue
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_I_ElementValue 
{

    /** TableName=I_ElementValue */
    public static final String Table_Name = "I_ElementValue";

    /** AD_Table_ID=534 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

    /** Load Meta Data */

	/**
	 * Set Kontovorzeichen.
	 * Indicates the Natural Sign of the Account as a Debit or Credit
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAccountSign (java.lang.String AccountSign);

	/**
	 * Get Kontovorzeichen.
	 * Indicates the Natural Sign of the Account as a Debit or Credit
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAccountSign();

    /** Column definition for AccountSign */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_AccountSign = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "AccountSign", null);
    /** Column name AccountSign */
    public static final String COLUMNNAME_AccountSign = "AccountSign";

	/**
	 * Set Kontenart.
	 * Indicates the type of account
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAccountType (java.lang.String AccountType);

	/**
	 * Get Kontenart.
	 * Indicates the type of account
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAccountType();

    /** Column definition for AccountType */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_AccountType = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "AccountType", null);
    /** Column name AccountType */
    public static final String COLUMNNAME_AccountType = "AccountType";

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_I_ElementValue, org.compiere.model.I_AD_Client>(I_I_ElementValue.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Spalte.
	 * Column in the table
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Spalte.
	 * Column in the table
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Column_ID();

	public org.compiere.model.I_AD_Column getAD_Column();

	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

    /** Column definition for AD_Column_ID */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new org.adempiere.model.ModelColumn<I_I_ElementValue, org.compiere.model.I_AD_Column>(I_I_ElementValue.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
    /** Column name AD_Column_ID */
    public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_I_ElementValue, org.compiere.model.I_AD_Org>(I_I_ElementValue.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Element.
	 * Accounting Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Element_ID (int C_Element_ID);

	/**
	 * Get Element.
	 * Accounting Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Element_ID();

	public org.compiere.model.I_C_Element getC_Element();

	public void setC_Element(org.compiere.model.I_C_Element C_Element);

    /** Column definition for C_Element_ID */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, org.compiere.model.I_C_Element> COLUMN_C_Element_ID = new org.adempiere.model.ModelColumn<I_I_ElementValue, org.compiere.model.I_C_Element>(I_I_ElementValue.class, "C_Element_ID", org.compiere.model.I_C_Element.class);
    /** Column name C_Element_ID */
    public static final String COLUMNNAME_C_Element_ID = "C_Element_ID";

	/**
	 * Set Kontenart.
	 * Account Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_ElementValue_ID (int C_ElementValue_ID);

	/**
	 * Get Kontenart.
	 * Account Element
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_ElementValue_ID();

	public org.compiere.model.I_C_ElementValue getC_ElementValue();

	public void setC_ElementValue(org.compiere.model.I_C_ElementValue C_ElementValue);

    /** Column definition for C_ElementValue_ID */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, org.compiere.model.I_C_ElementValue> COLUMN_C_ElementValue_ID = new org.adempiere.model.ModelColumn<I_I_ElementValue, org.compiere.model.I_C_ElementValue>(I_I_ElementValue.class, "C_ElementValue_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name C_ElementValue_ID */
    public static final String COLUMNNAME_C_ElementValue_ID = "C_ElementValue_ID";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_I_ElementValue, org.compiere.model.I_AD_User>(I_I_ElementValue.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Standard-Konto.
	 * Name of the Default Account Column
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDefault_Account (java.lang.String Default_Account);

	/**
	 * Get Standard-Konto.
	 * Name of the Default Account Column
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDefault_Account();

    /** Column definition for Default_Account */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_Default_Account = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "Default_Account", null);
    /** Column name Default_Account */
    public static final String COLUMNNAME_Default_Account = "Default_Account";

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
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Element Name.
	 * Name of the Element
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setElementName (java.lang.String ElementName);

	/**
	 * Get Element Name.
	 * Name of the Element
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getElementName();

    /** Column definition for ElementName */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_ElementName = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "ElementName", null);
    /** Column name ElementName */
    public static final String COLUMNNAME_ElementName = "ElementName";

	/**
	 * Set Import - Kontendefinition.
	 * Import Account Value
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_ElementValue_ID (int I_ElementValue_ID);

	/**
	 * Get Import - Kontendefinition.
	 * Import Account Value
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getI_ElementValue_ID();

    /** Column definition for I_ElementValue_ID */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_I_ElementValue_ID = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "I_ElementValue_ID", null);
    /** Column name I_ElementValue_ID */
    public static final String COLUMNNAME_I_ElementValue_ID = "I_ElementValue_ID";

	/**
	 * Set Import-Fehlermeldung.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg);

	/**
	 * Get Import-Fehlermeldung.
	 * Messages generated from import process
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_ErrorMsg();

    /** Column definition for I_ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_I_ErrorMsg = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "I_ErrorMsg", null);
    /** Column name I_ErrorMsg */
    public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Importiert.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_IsImported (boolean I_IsImported);

	/**
	 * Get Importiert.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isI_IsImported();

    /** Column definition for I_IsImported */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_I_IsImported = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "I_IsImported", null);
    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_IsDocControlled = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "IsDocControlled", null);
    /** Column name IsDocControlled */
    public static final String COLUMNNAME_IsDocControlled = "IsDocControlled";

	/**
	 * Set Zusammenfassungseintrag.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsSummary (boolean IsSummary);

	/**
	 * Get Zusammenfassungseintrag.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isSummary();

    /** Column definition for IsSummary */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_IsSummary = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "IsSummary", null);
    /** Column name IsSummary */
    public static final String COLUMNNAME_IsSummary = "IsSummary";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Übergeordnetes Konto.
	 * The parent (summary) account
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setParentElementValue_ID (int ParentElementValue_ID);

	/**
	 * Get Übergeordnetes Konto.
	 * The parent (summary) account
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getParentElementValue_ID();

	public org.compiere.model.I_C_ElementValue getParentElementValue();

	public void setParentElementValue(org.compiere.model.I_C_ElementValue ParentElementValue);

    /** Column definition for ParentElementValue_ID */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, org.compiere.model.I_C_ElementValue> COLUMN_ParentElementValue_ID = new org.adempiere.model.ModelColumn<I_I_ElementValue, org.compiere.model.I_C_ElementValue>(I_I_ElementValue.class, "ParentElementValue_ID", org.compiere.model.I_C_ElementValue.class);
    /** Column name ParentElementValue_ID */
    public static final String COLUMNNAME_ParentElementValue_ID = "ParentElementValue_ID";

	/**
	 * Set Schlüssel Übergeordnetes Konto .
	 * Key if the Parent
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setParentValue (java.lang.String ParentValue);

	/**
	 * Get Schlüssel Übergeordnetes Konto .
	 * Key if the Parent
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getParentValue();

    /** Column definition for ParentValue */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_ParentValue = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "ParentValue", null);
    /** Column name ParentValue */
    public static final String COLUMNNAME_ParentValue = "ParentValue";

	/**
	 * Set Buchen "Ist".
	 * Actual Values can be posted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPostActual (boolean PostActual);

	/**
	 * Get Buchen "Ist".
	 * Actual Values can be posted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isPostActual();

    /** Column definition for PostActual */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_PostActual = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "PostActual", null);
    /** Column name PostActual */
    public static final String COLUMNNAME_PostActual = "PostActual";

	/**
	 * Set Buchen "Budget".
	 * Budget values can be posted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPostBudget (boolean PostBudget);

	/**
	 * Get Buchen "Budget".
	 * Budget values can be posted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isPostBudget();

    /** Column definition for PostBudget */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_PostBudget = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "PostBudget", null);
    /** Column name PostBudget */
    public static final String COLUMNNAME_PostBudget = "PostBudget";

	/**
	 * Set Buchen "Reservierung".
	 * Post commitments to this account
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPostEncumbrance (boolean PostEncumbrance);

	/**
	 * Get Buchen "Reservierung".
	 * Post commitments to this account
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isPostEncumbrance();

    /** Column definition for PostEncumbrance */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_PostEncumbrance = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "PostEncumbrance", null);
    /** Column name PostEncumbrance */
    public static final String COLUMNNAME_PostEncumbrance = "PostEncumbrance";

	/**
	 * Set Buchen "statistisch".
	 * Post statistical quantities to this account?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPostStatistical (boolean PostStatistical);

	/**
	 * Get Buchen "statistisch".
	 * Post statistical quantities to this account?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isPostStatistical();

    /** Column definition for PostStatistical */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_PostStatistical = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "PostStatistical", null);
    /** Column name PostStatistical */
    public static final String COLUMNNAME_PostStatistical = "PostStatistical";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_I_ElementValue, org.compiere.model.I_AD_User>(I_I_ElementValue.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_I_ElementValue, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_I_ElementValue, Object>(I_I_ElementValue.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";
}
