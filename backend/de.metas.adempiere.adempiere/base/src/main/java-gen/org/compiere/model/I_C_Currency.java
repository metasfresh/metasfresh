package org.compiere.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Currency
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Currency 
{

	String Table_Name = "C_Currency";

//	/** AD_Table_ID=141 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


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
	 * Set Currency.
	 * The Currency for this record
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Currency_ID (int C_Currency_ID);

	/**
	 * Get Currency.
	 * The Currency for this record
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Currency_ID();

	ModelColumn<I_C_Currency, Object> COLUMN_C_Currency_ID = new ModelColumn<>(I_C_Currency.class, "C_Currency_ID", null);
	String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/**
	 * Set Costing Precision.
	 * Rounding used costing calculations
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCostingPrecision (int CostingPrecision);

	/**
	 * Get Costing Precision.
	 * Rounding used costing calculations
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCostingPrecision();

	ModelColumn<I_C_Currency, Object> COLUMN_CostingPrecision = new ModelColumn<>(I_C_Currency.class, "CostingPrecision", null);
	String COLUMNNAME_CostingPrecision = "CostingPrecision";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Currency, Object> COLUMN_Created = new ModelColumn<>(I_C_Currency.class, "Created", null);
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
	 * Set Currency Symbol.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCurSymbol (@Nullable java.lang.String CurSymbol);

	/**
	 * Get Currency Symbol.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCurSymbol();

	ModelColumn<I_C_Currency, Object> COLUMN_CurSymbol = new ModelColumn<>(I_C_Currency.class, "CurSymbol", null);
	String COLUMNNAME_CurSymbol = "CurSymbol";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDescription (java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDescription();

	ModelColumn<I_C_Currency, Object> COLUMN_Description = new ModelColumn<>(I_C_Currency.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set EMU Entry Date.
	 * Date when the currency joined / will join the EMU
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEMUEntryDate (@Nullable java.sql.Timestamp EMUEntryDate);

	/**
	 * Get EMU Entry Date.
	 * Date when the currency joined / will join the EMU
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getEMUEntryDate();

	ModelColumn<I_C_Currency, Object> COLUMN_EMUEntryDate = new ModelColumn<>(I_C_Currency.class, "EMUEntryDate", null);
	String COLUMNNAME_EMUEntryDate = "EMUEntryDate";

	/**
	 * Set EMU Rate.
	 * Official rate to the Euro
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEMURate (@Nullable BigDecimal EMURate);

	/**
	 * Get EMU Rate.
	 * Official rate to the Euro
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getEMURate();

	ModelColumn<I_C_Currency, Object> COLUMN_EMURate = new ModelColumn<>(I_C_Currency.class, "EMURate", null);
	String COLUMNNAME_EMURate = "EMURate";

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

	ModelColumn<I_C_Currency, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Currency.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Apply 5 Cent Cash Rounding.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsApply5CentCashRounding (boolean IsApply5CentCashRounding);

	/**
	 * Get Apply 5 Cent Cash Rounding.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isApply5CentCashRounding();

	ModelColumn<I_C_Currency, Object> COLUMN_IsApply5CentCashRounding = new ModelColumn<>(I_C_Currency.class, "IsApply5CentCashRounding", null);
	String COLUMNNAME_IsApply5CentCashRounding = "IsApply5CentCashRounding";

	/**
	 * Set EMU Member.
	 * This currency is member if the European Monetary Union
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsEMUMember (boolean IsEMUMember);

	/**
	 * Get EMU Member.
	 * This currency is member if the European Monetary Union
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEMUMember();

	ModelColumn<I_C_Currency, Object> COLUMN_IsEMUMember = new ModelColumn<>(I_C_Currency.class, "IsEMUMember", null);
	String COLUMNNAME_IsEMUMember = "IsEMUMember";

	/**
	 * Set The Euro Currency.
	 * This currency is the Euro
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsEuro (boolean IsEuro);

	/**
	 * Get The Euro Currency.
	 * This currency is the Euro
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEuro();

	ModelColumn<I_C_Currency, Object> COLUMN_IsEuro = new ModelColumn<>(I_C_Currency.class, "IsEuro", null);
	String COLUMNNAME_IsEuro = "IsEuro";

	/**
	 * Set ISO Currency Code.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setISO_Code (java.lang.String ISO_Code);

	/**
	 * Get ISO Currency Code.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getISO_Code();

	ModelColumn<I_C_Currency, Object> COLUMN_ISO_Code = new ModelColumn<>(I_C_Currency.class, "ISO_Code", null);
	String COLUMNNAME_ISO_Code = "ISO_Code";

	/**
	 * Set Round Off Factor.
	 * Used to Round Off Payment Amount
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRoundOffFactor (@Nullable BigDecimal RoundOffFactor);

	/**
	 * Get Round Off Factor.
	 * Used to Round Off Payment Amount
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getRoundOffFactor();

	ModelColumn<I_C_Currency, Object> COLUMN_RoundOffFactor = new ModelColumn<>(I_C_Currency.class, "RoundOffFactor", null);
	String COLUMNNAME_RoundOffFactor = "RoundOffFactor";

	/**
	 * Set Standard Precision.
	 * Rule for rounding  calculated amounts
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStdPrecision (int StdPrecision);

	/**
	 * Get Standard Precision.
	 * Rule for rounding  calculated amounts
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getStdPrecision();

	ModelColumn<I_C_Currency, Object> COLUMN_StdPrecision = new ModelColumn<>(I_C_Currency.class, "StdPrecision", null);
	String COLUMNNAME_StdPrecision = "StdPrecision";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Currency, Object> COLUMN_Updated = new ModelColumn<>(I_C_Currency.class, "Updated", null);
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
