/*
 * #%L
 * de.metas.payment.revolut
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.payment.revolut.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for Revolut_Payment_Export
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public interface I_Revolut_Payment_Export
{

	String Table_Name = "Revolut_Payment_Export";

	//	/** AD_Table_ID=541752 */
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

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_AccountNo = new ModelColumn<>(I_Revolut_Payment_Export.class, "AccountNo", null);
	String COLUMNNAME_AccountNo = "AccountNo";

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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Street & House No..
	 * Address line 1 for this location
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAddressLine1 (@Nullable java.lang.String AddressLine1);

	/**
	 * Get Street & House No..
	 * Address line 1 for this location
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAddressLine1();

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_AddressLine1 = new ModelColumn<>(I_Revolut_Payment_Export.class, "AddressLine1", null);
	String COLUMNNAME_AddressLine1 = "AddressLine1";

	/**
	 * Set Address 2.
	 * Address line 2 for this location
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAddressLine2 (@Nullable java.lang.String AddressLine2);

	/**
	 * Get Address 2.
	 * Address line 2 for this location
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAddressLine2();

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_AddressLine2 = new ModelColumn<>(I_Revolut_Payment_Export.class, "AddressLine2", null);
	String COLUMNNAME_AddressLine2 = "AddressLine2";

	/**
	 * Set Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAmount (BigDecimal Amount);

	/**
	 * Get Amount.
	 * Amount in a defined currency
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getAmount();

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_Amount = new ModelColumn<>(I_Revolut_Payment_Export.class, "Amount", null);
	String COLUMNNAME_Amount = "Amount";

	/**
	 * Set Swift code.
	 * Swift Code or BIC
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBIC (@Nullable java.lang.String BIC);

	/**
	 * Get Swift code.
	 * Swift Code or BIC
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBIC();

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_BIC = new ModelColumn<>(I_Revolut_Payment_Export.class, "BIC", null);
	String COLUMNNAME_BIC = "BIC";

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

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_City = new ModelColumn<>(I_Revolut_Payment_Export.class, "City", null);
	String COLUMNNAME_City = "City";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_Created = new ModelColumn<>(I_Revolut_Payment_Export.class, "Created", null);
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

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_IBAN = new ModelColumn<>(I_Revolut_Payment_Export.class, "IBAN", null);
	String COLUMNNAME_IBAN = "IBAN";

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

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_IsActive = new ModelColumn<>(I_Revolut_Payment_Export.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_Name = new ModelColumn<>(I_Revolut_Payment_Export.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Reference.
	 * Reference for this record
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPaymentReference (@Nullable java.lang.String PaymentReference);

	/**
	 * Get Reference.
	 * Reference for this record
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPaymentReference();

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_PaymentReference = new ModelColumn<>(I_Revolut_Payment_Export.class, "PaymentReference", null);
	String COLUMNNAME_PaymentReference = "PaymentReference";

	/**
	 * Set Postal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPostalCode (@Nullable java.lang.String PostalCode);

	/**
	 * Get Postal.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPostalCode();

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_PostalCode = new ModelColumn<>(I_Revolut_Payment_Export.class, "PostalCode", null);
	String COLUMNNAME_PostalCode = "PostalCode";

	/**
	 * Set RecipientBankCountryId.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRecipientBankCountryId (int RecipientBankCountryId);

	/**
	 * Get RecipientBankCountryId.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRecipientBankCountryId();

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_RecipientBankCountryId = new ModelColumn<>(I_Revolut_Payment_Export.class, "RecipientBankCountryId", null);
	String COLUMNNAME_RecipientBankCountryId = "RecipientBankCountryId";

	/**
	 * Set RecipientCountryId.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecipientCountryId (int RecipientCountryId);

	/**
	 * Get RecipientCountryId.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecipientCountryId();

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_RecipientCountryId = new ModelColumn<>(I_Revolut_Payment_Export.class, "RecipientCountryId", null);
	String COLUMNNAME_RecipientCountryId = "RecipientCountryId";

	/**
	 * Set RecipientType.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecipientType (java.lang.String RecipientType);

	/**
	 * Get RecipientType.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getRecipientType();

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_RecipientType = new ModelColumn<>(I_Revolut_Payment_Export.class, "RecipientType", null);
	String COLUMNNAME_RecipientType = "RecipientType";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_Record_ID = new ModelColumn<>(I_Revolut_Payment_Export.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

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

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_RegionName = new ModelColumn<>(I_Revolut_Payment_Export.class, "RegionName", null);
	String COLUMNNAME_RegionName = "RegionName";

	/**
	 * Set Revolut Payment Export.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRevolut_Payment_Export_ID (int Revolut_Payment_Export_ID);

	/**
	 * Get Revolut Payment Export.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRevolut_Payment_Export_ID();

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_Revolut_Payment_Export_ID = new ModelColumn<>(I_Revolut_Payment_Export.class, "Revolut_Payment_Export_ID", null);
	String COLUMNNAME_Revolut_Payment_Export_ID = "Revolut_Payment_Export_ID";

	/**
	 * Set Routing No.
	 * Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRoutingNo (@Nullable java.lang.String RoutingNo);

	/**
	 * Get Routing No.
	 * Bank Routing Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRoutingNo();

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_RoutingNo = new ModelColumn<>(I_Revolut_Payment_Export.class, "RoutingNo", null);
	String COLUMNNAME_RoutingNo = "RoutingNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_Revolut_Payment_Export, Object> COLUMN_Updated = new ModelColumn<>(I_Revolut_Payment_Export.class, "Updated", null);
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
