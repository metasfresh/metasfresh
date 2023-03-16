package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Country
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Country 
{

	String Table_Name = "C_Country";

//	/** AD_Table_ID=170 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Account Type Length.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAccountTypeLength (@Nullable java.lang.String AccountTypeLength);

	/**
	 * Get Account Type Length.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAccountTypeLength();

	ModelColumn<I_C_Country, Object> COLUMN_AccountTypeLength = new ModelColumn<>(I_C_Country.class, "AccountTypeLength", null);
	String COLUMNNAME_AccountTypeLength = "AccountTypeLength";

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
	 * Set Language.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Language (@Nullable java.lang.String AD_Language);

	/**
	 * Get Language.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAD_Language();

	ModelColumn<I_C_Country, Object> COLUMN_AD_Language = new ModelColumn<>(I_C_Country.class, "AD_Language", null);
	String COLUMNNAME_AD_Language = "AD_Language";

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
	 * Set Allow Cities out of List.
	 * A flag to allow cities, currently not in the list, to be entered
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAllowCitiesOutOfList (boolean AllowCitiesOutOfList);

	/**
	 * Get Allow Cities out of List.
	 * A flag to allow cities, currently not in the list, to be entered
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isAllowCitiesOutOfList();

	ModelColumn<I_C_Country, Object> COLUMN_AllowCitiesOutOfList = new ModelColumn<>(I_C_Country.class, "AllowCitiesOutOfList", null);
	String COLUMNNAME_AllowCitiesOutOfList = "AllowCitiesOutOfList";

	/**
	 * Set Capture Sequence.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCaptureSequence (@Nullable java.lang.String CaptureSequence);

	/**
	 * Get Capture Sequence.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCaptureSequence();

	ModelColumn<I_C_Country, Object> COLUMN_CaptureSequence = new ModelColumn<>(I_C_Country.class, "CaptureSequence", null);
	String COLUMNNAME_CaptureSequence = "CaptureSequence";

	/**
	 * Set Country.
	 * Country
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Country.
	 * Country
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Country_ID();

	ModelColumn<I_C_Country, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new ModelColumn<>(I_C_Country.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
	String COLUMNNAME_C_Country_ID = "C_Country_ID";

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
	 * Set ISO Country Code.
	 * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCountryCode (java.lang.String CountryCode);

	/**
	 * Get ISO Country Code.
	 * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getCountryCode();

	ModelColumn<I_C_Country, Object> COLUMN_CountryCode = new ModelColumn<>(I_C_Country.class, "CountryCode", null);
	String COLUMNNAME_CountryCode = "CountryCode";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Country, Object> COLUMN_Created = new ModelColumn<>(I_C_Country.class, "Created", null);
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

	ModelColumn<I_C_Country, Object> COLUMN_Description = new ModelColumn<>(I_C_Country.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Address Print Format.
	 * Format for printing this Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDisplaySequence (java.lang.String DisplaySequence);

	/**
	 * Get Address Print Format.
	 * Format for printing this Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDisplaySequence();

	ModelColumn<I_C_Country, Object> COLUMN_DisplaySequence = new ModelColumn<>(I_C_Country.class, "DisplaySequence", null);
	String COLUMNNAME_DisplaySequence = "DisplaySequence";

	/**
	 * Set Local Address Format.
	 * Format for printing this Address locally
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDisplaySequenceLocal (@Nullable java.lang.String DisplaySequenceLocal);

	/**
	 * Get Local Address Format.
	 * Format for printing this Address locally
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDisplaySequenceLocal();

	ModelColumn<I_C_Country, Object> COLUMN_DisplaySequenceLocal = new ModelColumn<>(I_C_Country.class, "DisplaySequenceLocal", null);
	String COLUMNNAME_DisplaySequenceLocal = "DisplaySequenceLocal";

	/**
	 * Set Bank Account No Format.
	 * Format of the Bank Account
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExpressionBankAccountNo (@Nullable java.lang.String ExpressionBankAccountNo);

	/**
	 * Get Bank Account No Format.
	 * Format of the Bank Account
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExpressionBankAccountNo();

	ModelColumn<I_C_Country, Object> COLUMN_ExpressionBankAccountNo = new ModelColumn<>(I_C_Country.class, "ExpressionBankAccountNo", null);
	String COLUMNNAME_ExpressionBankAccountNo = "ExpressionBankAccountNo";

	/**
	 * Set Bank Routing No Format.
	 * Format of the Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExpressionBankRoutingNo (@Nullable java.lang.String ExpressionBankRoutingNo);

	/**
	 * Get Bank Routing No Format.
	 * Format of the Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExpressionBankRoutingNo();

	ModelColumn<I_C_Country, Object> COLUMN_ExpressionBankRoutingNo = new ModelColumn<>(I_C_Country.class, "ExpressionBankRoutingNo", null);
	String COLUMNNAME_ExpressionBankRoutingNo = "ExpressionBankRoutingNo";

	/**
	 * Set Phone Format.
	 * Format of the phone;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExpressionPhone (@Nullable java.lang.String ExpressionPhone);

	/**
	 * Get Phone Format.
	 * Format of the phone;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExpressionPhone();

	ModelColumn<I_C_Country, Object> COLUMN_ExpressionPhone = new ModelColumn<>(I_C_Country.class, "ExpressionPhone", null);
	String COLUMNNAME_ExpressionPhone = "ExpressionPhone";

	/**
	 * Set Postal Code Format.
	 * Format of the postal code;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExpressionPostal (@Nullable java.lang.String ExpressionPostal);

	/**
	 * Get Postal Code Format.
	 * Format of the postal code;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExpressionPostal();

	ModelColumn<I_C_Country, Object> COLUMN_ExpressionPostal = new ModelColumn<>(I_C_Country.class, "ExpressionPostal", null);
	String COLUMNNAME_ExpressionPostal = "ExpressionPostal";

	/**
	 * Set Additional Postal Format.
	 * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExpressionPostal_Add (@Nullable java.lang.String ExpressionPostal_Add);

	/**
	 * Get Additional Postal Format.
	 * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExpressionPostal_Add();

	ModelColumn<I_C_Country, Object> COLUMN_ExpressionPostal_Add = new ModelColumn<>(I_C_Country.class, "ExpressionPostal_Add", null);
	String COLUMNNAME_ExpressionPostal_Add = "ExpressionPostal_Add";

	/**
	 * Set Additional Postal code.
	 * Has Additional Postal Code
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHasPostal_Add (boolean HasPostal_Add);

	/**
	 * Get Additional Postal code.
	 * Has Additional Postal Code
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isHasPostal_Add();

	ModelColumn<I_C_Country, Object> COLUMN_HasPostal_Add = new ModelColumn<>(I_C_Country.class, "HasPostal_Add", null);
	String COLUMNNAME_HasPostal_Add = "HasPostal_Add";

	/**
	 * Set Country has Region.
	 * Country contains Regions
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setHasRegion (boolean HasRegion);

	/**
	 * Get Country has Region.
	 * Country contains Regions
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isHasRegion();

	ModelColumn<I_C_Country, Object> COLUMN_HasRegion = new ModelColumn<>(I_C_Country.class, "HasRegion", null);
	String COLUMNNAME_HasRegion = "HasRegion";

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

	ModelColumn<I_C_Country, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Country.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Reverse Local Address Lines.
	 * Print Local Address in reverse Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAddressLinesLocalReverse (boolean IsAddressLinesLocalReverse);

	/**
	 * Get Reverse Local Address Lines.
	 * Print Local Address in reverse Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAddressLinesLocalReverse();

	ModelColumn<I_C_Country, Object> COLUMN_IsAddressLinesLocalReverse = new ModelColumn<>(I_C_Country.class, "IsAddressLinesLocalReverse", null);
	String COLUMNNAME_IsAddressLinesLocalReverse = "IsAddressLinesLocalReverse";

	/**
	 * Set Reverse Address Lines.
	 * Print Address in reverse Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAddressLinesReverse (boolean IsAddressLinesReverse);

	/**
	 * Get Reverse Address Lines.
	 * Print Address in reverse Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAddressLinesReverse();

	ModelColumn<I_C_Country, Object> COLUMN_IsAddressLinesReverse = new ModelColumn<>(I_C_Country.class, "IsAddressLinesReverse", null);
	String COLUMNNAME_IsAddressLinesReverse = "IsAddressLinesReverse";

	/**
	 * Set Enforce Correction Invoice.
	 * If active invoices that have this country as billto-location can't be voided. Instead the process Generate Correction Invoice is available.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsEnforceCorrectionInvoice (boolean IsEnforceCorrectionInvoice);

	/**
	 * Get Enforce Correction Invoice.
	 * If active invoices that have this country as billto-location can't be voided. Instead the process Generate Correction Invoice is available.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEnforceCorrectionInvoice();

	ModelColumn<I_C_Country, Object> COLUMN_IsEnforceCorrectionInvoice = new ModelColumn<>(I_C_Country.class, "IsEnforceCorrectionInvoice", null);
	String COLUMNNAME_IsEnforceCorrectionInvoice = "IsEnforceCorrectionInvoice";

	/**
	 * Set Media Size.
	 * Java Media Size
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMediaSize (@Nullable java.lang.String MediaSize);

	/**
	 * Get Media Size.
	 * Java Media Size
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMediaSize();

	ModelColumn<I_C_Country, Object> COLUMN_MediaSize = new ModelColumn<>(I_C_Country.class, "MediaSize", null);
	String COLUMNNAME_MediaSize = "MediaSize";

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

	ModelColumn<I_C_Country, Object> COLUMN_Name = new ModelColumn<>(I_C_Country.class, "Name", null);
	String COLUMNNAME_Name = "Name";

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

	ModelColumn<I_C_Country, Object> COLUMN_RegionName = new ModelColumn<>(I_C_Country.class, "RegionName", null);
	String COLUMNNAME_RegionName = "RegionName";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Country, Object> COLUMN_Updated = new ModelColumn<>(I_C_Country.class, "Updated", null);
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
