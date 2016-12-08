package org.compiere.model;


/** Generated Interface for C_Country
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_Country 
{

    /** TableName=C_Country */
    public static final String Table_Name = "C_Country";

    /** AD_Table_ID=170 */
    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

    /** Load Meta Data */

	/**
	 * Set Account Type Length.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAccountTypeLength (java.lang.String AccountTypeLength);

	/**
	 * Get Account Type Length.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAccountTypeLength();

    /** Column definition for AccountTypeLength */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_AccountTypeLength = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "AccountTypeLength", null);
    /** Column name AccountTypeLength */
    public static final String COLUMNNAME_AccountTypeLength = "AccountTypeLength";

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Country, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_Country, org.compiere.model.I_AD_Client>(I_C_Country.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sprache.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Language (java.lang.String AD_Language);

	/**
	 * Get Sprache.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAD_Language();

    /** Column definition for AD_Language */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_AD_Language = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "AD_Language", null);
    /** Column name AD_Language */
    public static final String COLUMNNAME_AD_Language = "AD_Language";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Country, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_Country, org.compiere.model.I_AD_Org>(I_C_Country.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Allow Cities out of List.
	 * A flag to allow cities, currently not in the list, to be entered
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAllowCitiesOutOfList (boolean AllowCitiesOutOfList);

	/**
	 * Get Allow Cities out of List.
	 * A flag to allow cities, currently not in the list, to be entered
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isAllowCitiesOutOfList();

    /** Column definition for AllowCitiesOutOfList */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_AllowCitiesOutOfList = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "AllowCitiesOutOfList", null);
    /** Column name AllowCitiesOutOfList */
    public static final String COLUMNNAME_AllowCitiesOutOfList = "AllowCitiesOutOfList";

	/**
	 * Set Land.
	 * Country
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Land.
	 * Country
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_Country_ID();

    /** Column definition for C_Country_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Country, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new org.adempiere.model.ModelColumn<I_C_Country, org.compiere.model.I_C_Country>(I_C_Country.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

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

	public org.compiere.model.I_C_Currency getC_Currency();

	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency);

    /** Column definition for C_Currency_ID */
    public static final org.adempiere.model.ModelColumn<I_C_Country, org.compiere.model.I_C_Currency> COLUMN_C_Currency_ID = new org.adempiere.model.ModelColumn<I_C_Country, org.compiere.model.I_C_Currency>(I_C_Country.class, "C_Currency_ID", org.compiere.model.I_C_Currency.class);
    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Capture Sequence.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCaptureSequence (java.lang.String CaptureSequence);

	/**
	 * Get Capture Sequence.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCaptureSequence();

    /** Column definition for CaptureSequence */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_CaptureSequence = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "CaptureSequence", null);
    /** Column name CaptureSequence */
    public static final String COLUMNNAME_CaptureSequence = "CaptureSequence";

	/**
	 * Set ISO Ländercode.
	 * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCountryCode (java.lang.String CountryCode);

	/**
	 * Get ISO Ländercode.
	 * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCountryCode();

    /** Column definition for CountryCode */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_CountryCode = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "CountryCode", null);
    /** Column name CountryCode */
    public static final String COLUMNNAME_CountryCode = "CountryCode";

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
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "Created", null);
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

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Country, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_Country, org.compiere.model.I_AD_User>(I_C_Country.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Adress-Druckformat.
	 * Format for printing this Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDisplaySequence (java.lang.String DisplaySequence);

	/**
	 * Get Adress-Druckformat.
	 * Format for printing this Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDisplaySequence();

    /** Column definition for DisplaySequence */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_DisplaySequence = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "DisplaySequence", null);
    /** Column name DisplaySequence */
    public static final String COLUMNNAME_DisplaySequence = "DisplaySequence";

	/**
	 * Set Local Address Format.
	 * Format for printing this Address locally
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDisplaySequenceLocal (java.lang.String DisplaySequenceLocal);

	/**
	 * Get Local Address Format.
	 * Format for printing this Address locally
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDisplaySequenceLocal();

    /** Column definition for DisplaySequenceLocal */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_DisplaySequenceLocal = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "DisplaySequenceLocal", null);
    /** Column name DisplaySequenceLocal */
    public static final String COLUMNNAME_DisplaySequenceLocal = "DisplaySequenceLocal";

	/**
	 * Set Bank Account No Format.
	 * Format of the Bank Account
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExpressionBankAccountNo (java.lang.String ExpressionBankAccountNo);

	/**
	 * Get Bank Account No Format.
	 * Format of the Bank Account
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExpressionBankAccountNo();

    /** Column definition for ExpressionBankAccountNo */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_ExpressionBankAccountNo = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "ExpressionBankAccountNo", null);
    /** Column name ExpressionBankAccountNo */
    public static final String COLUMNNAME_ExpressionBankAccountNo = "ExpressionBankAccountNo";

	/**
	 * Set Bank Routing No Format.
	 * Format of the Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExpressionBankRoutingNo (java.lang.String ExpressionBankRoutingNo);

	/**
	 * Get Bank Routing No Format.
	 * Format of the Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExpressionBankRoutingNo();

    /** Column definition for ExpressionBankRoutingNo */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_ExpressionBankRoutingNo = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "ExpressionBankRoutingNo", null);
    /** Column name ExpressionBankRoutingNo */
    public static final String COLUMNNAME_ExpressionBankRoutingNo = "ExpressionBankRoutingNo";

	/**
	 * Set Phone Format.
	 * Format of the phone;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExpressionPhone (java.lang.String ExpressionPhone);

	/**
	 * Get Phone Format.
	 * Format of the phone;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExpressionPhone();

    /** Column definition for ExpressionPhone */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_ExpressionPhone = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "ExpressionPhone", null);
    /** Column name ExpressionPhone */
    public static final String COLUMNNAME_ExpressionPhone = "ExpressionPhone";

	/**
	 * Set Postal Code Format.
	 * Format of the postal code;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExpressionPostal (java.lang.String ExpressionPostal);

	/**
	 * Get Postal Code Format.
	 * Format of the postal code;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExpressionPostal();

    /** Column definition for ExpressionPostal */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_ExpressionPostal = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "ExpressionPostal", null);
    /** Column name ExpressionPostal */
    public static final String COLUMNNAME_ExpressionPostal = "ExpressionPostal";

	/**
	 * Set Additional Postal Format.
	 * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExpressionPostal_Add (java.lang.String ExpressionPostal_Add);

	/**
	 * Get Additional Postal Format.
	 * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExpressionPostal_Add();

    /** Column definition for ExpressionPostal_Add */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_ExpressionPostal_Add = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "ExpressionPostal_Add", null);
    /** Column name ExpressionPostal_Add */
    public static final String COLUMNNAME_ExpressionPostal_Add = "ExpressionPostal_Add";

	/**
	 * Set Additional Postal code.
	 * Has Additional Postal Code
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHasPostal_Add (boolean HasPostal_Add);

	/**
	 * Get Additional Postal code.
	 * Has Additional Postal Code
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isHasPostal_Add();

    /** Column definition for HasPostal_Add */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_HasPostal_Add = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "HasPostal_Add", null);
    /** Column name HasPostal_Add */
    public static final String COLUMNNAME_HasPostal_Add = "HasPostal_Add";

	/**
	 * Set Land hat Regionen.
	 * Country contains Regions
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHasRegion (boolean HasRegion);

	/**
	 * Get Land hat Regionen.
	 * Country contains Regions
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isHasRegion();

    /** Column definition for HasRegion */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_HasRegion = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "HasRegion", null);
    /** Column name HasRegion */
    public static final String COLUMNNAME_HasRegion = "HasRegion";

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
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Reverse Local Address Lines.
	 * Print Local Address in reverse Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAddressLinesLocalReverse (boolean IsAddressLinesLocalReverse);

	/**
	 * Get Reverse Local Address Lines.
	 * Print Local Address in reverse Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAddressLinesLocalReverse();

    /** Column definition for IsAddressLinesLocalReverse */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_IsAddressLinesLocalReverse = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "IsAddressLinesLocalReverse", null);
    /** Column name IsAddressLinesLocalReverse */
    public static final String COLUMNNAME_IsAddressLinesLocalReverse = "IsAddressLinesLocalReverse";

	/**
	 * Set Reverse Address Lines.
	 * Print Address in reverse Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAddressLinesReverse (boolean IsAddressLinesReverse);

	/**
	 * Get Reverse Address Lines.
	 * Print Address in reverse Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAddressLinesReverse();

    /** Column definition for IsAddressLinesReverse */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_IsAddressLinesReverse = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "IsAddressLinesReverse", null);
    /** Column name IsAddressLinesReverse */
    public static final String COLUMNNAME_IsAddressLinesReverse = "IsAddressLinesReverse";

	/**
	 * Set Use Postcode Lookup.
	 * Does this country have a post code web service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsPostcodeLookup (boolean IsPostcodeLookup);

	/**
	 * Get Use Postcode Lookup.
	 * Does this country have a post code web service
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isPostcodeLookup();

    /** Column definition for IsPostcodeLookup */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_IsPostcodeLookup = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "IsPostcodeLookup", null);
    /** Column name IsPostcodeLookup */
    public static final String COLUMNNAME_IsPostcodeLookup = "IsPostcodeLookup";

	/**
	 * Set Lookup ClassName.
	 * The class name of the postcode lookup plugin
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLookupClassName (java.lang.String LookupClassName);

	/**
	 * Get Lookup ClassName.
	 * The class name of the postcode lookup plugin
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLookupClassName();

    /** Column definition for LookupClassName */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_LookupClassName = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "LookupClassName", null);
    /** Column name LookupClassName */
    public static final String COLUMNNAME_LookupClassName = "LookupClassName";

	/**
	 * Set Lookup Client ID.
	 * The ClientID or Login submitted to the Lookup URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLookupClientID (java.lang.String LookupClientID);

	/**
	 * Get Lookup Client ID.
	 * The ClientID or Login submitted to the Lookup URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLookupClientID();

    /** Column definition for LookupClientID */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_LookupClientID = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "LookupClientID", null);
    /** Column name LookupClientID */
    public static final String COLUMNNAME_LookupClientID = "LookupClientID";

	/**
	 * Set Lookup Password.
	 * The password submitted to the Lookup URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLookupPassword (java.lang.String LookupPassword);

	/**
	 * Get Lookup Password.
	 * The password submitted to the Lookup URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLookupPassword();

    /** Column definition for LookupPassword */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_LookupPassword = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "LookupPassword", null);
    /** Column name LookupPassword */
    public static final String COLUMNNAME_LookupPassword = "LookupPassword";

	/**
	 * Set Lookup URL.
	 * The URL of the web service that the plugin connects to in order to retrieve postcode data
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLookupUrl (java.lang.String LookupUrl);

	/**
	 * Get Lookup URL.
	 * The URL of the web service that the plugin connects to in order to retrieve postcode data
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLookupUrl();

    /** Column definition for LookupUrl */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_LookupUrl = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "LookupUrl", null);
    /** Column name LookupUrl */
    public static final String COLUMNNAME_LookupUrl = "LookupUrl";

	/**
	 * Set Media Size.
	 * Java Media Size
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMediaSize (java.lang.String MediaSize);

	/**
	 * Get Media Size.
	 * Java Media Size
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMediaSize();

    /** Column definition for MediaSize */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_MediaSize = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "MediaSize", null);
    /** Column name MediaSize */
    public static final String COLUMNNAME_MediaSize = "MediaSize";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Region.
	 * Name of the Region
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRegionName (java.lang.String RegionName);

	/**
	 * Get Region.
	 * Name of the Region
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRegionName();

    /** Column definition for RegionName */
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_RegionName = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "RegionName", null);
    /** Column name RegionName */
    public static final String COLUMNNAME_RegionName = "RegionName";

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
    public static final org.adempiere.model.ModelColumn<I_C_Country, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_Country, Object>(I_C_Country.class, "Updated", null);
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

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_C_Country, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_Country, org.compiere.model.I_AD_User>(I_C_Country.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
