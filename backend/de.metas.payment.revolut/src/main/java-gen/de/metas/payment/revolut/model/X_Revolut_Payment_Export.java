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

// Generated Model - DO NOT CHANGE
package de.metas.payment.revolut.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for Revolut_Payment_Export
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_Revolut_Payment_Export extends org.compiere.model.PO implements I_Revolut_Payment_Export, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = -252258323L;

	/** Standard Constructor */
	public X_Revolut_Payment_Export (final Properties ctx, final int Revolut_Payment_Export_ID, @Nullable final String trxName)
	{
		super (ctx, Revolut_Payment_Export_ID, trxName);
	}

	/** Load Constructor */
	public X_Revolut_Payment_Export (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
	{
		super (ctx, rs, trxName);
	}


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAccountNo (final @Nullable java.lang.String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	@Override
	public java.lang.String getAccountNo()
	{
		return get_ValueAsString(COLUMNNAME_AccountNo);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1)
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID()
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setAddressLine1 (final @Nullable java.lang.String AddressLine1)
	{
		set_Value (COLUMNNAME_AddressLine1, AddressLine1);
	}

	@Override
	public java.lang.String getAddressLine1()
	{
		return get_ValueAsString(COLUMNNAME_AddressLine1);
	}

	@Override
	public void setAddressLine2 (final @Nullable java.lang.String AddressLine2)
	{
		set_Value (COLUMNNAME_AddressLine2, AddressLine2);
	}

	@Override
	public java.lang.String getAddressLine2()
	{
		return get_ValueAsString(COLUMNNAME_AddressLine2);
	}

	@Override
	public void setAmount (final BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	@Override
	public BigDecimal getAmount()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setSwiftCode (final @Nullable java.lang.String SwiftCode)
	{
		set_Value (COLUMNNAME_SwiftCode, SwiftCode);
	}

	@Override
	public java.lang.String getSwiftCode()
	{
		return get_ValueAsString(COLUMNNAME_SwiftCode);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1)
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setCity (final @Nullable java.lang.String City)
	{
		set_Value (COLUMNNAME_City, City);
	}

	@Override
	public java.lang.String getCity()
	{
		return get_ValueAsString(COLUMNNAME_City);
	}

	@Override
	public void setIBAN (final @Nullable java.lang.String IBAN)
	{
		set_Value (COLUMNNAME_IBAN, IBAN);
	}

	@Override
	public java.lang.String getIBAN()
	{
		return get_ValueAsString(COLUMNNAME_IBAN);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName()
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setPaymentReference (final @Nullable java.lang.String PaymentReference)
	{
		set_Value (COLUMNNAME_PaymentReference, PaymentReference);
	}

	@Override
	public java.lang.String getPaymentReference()
	{
		return get_ValueAsString(COLUMNNAME_PaymentReference);
	}

	@Override
	public void setPostalCode (final @Nullable java.lang.String PostalCode)
	{
		set_Value (COLUMNNAME_PostalCode, PostalCode);
	}

	@Override
	public java.lang.String getPostalCode()
	{
		return get_ValueAsString(COLUMNNAME_PostalCode);
	}

	@Override
	public void setRecipientBankCountryId (final int RecipientBankCountryId)
	{
		set_Value (COLUMNNAME_RecipientBankCountryId, RecipientBankCountryId);
	}

	@Override
	public int getRecipientBankCountryId()
	{
		return get_ValueAsInt(COLUMNNAME_RecipientBankCountryId);
	}

	@Override
	public void setRecipientCountryId (final int RecipientCountryId)
	{
		set_Value (COLUMNNAME_RecipientCountryId, RecipientCountryId);
	}

	@Override
	public int getRecipientCountryId()
	{
		return get_ValueAsInt(COLUMNNAME_RecipientCountryId);
	}

	/**
	 * RecipientType AD_Reference_ID=541372
	 * Reference name: RecipientTypeList
	 */
	public static final int RECIPIENTTYPE_AD_Reference_ID=541372;
	/** COMPANY = COMPANY */
	public static final String RECIPIENTTYPE_COMPANY = "COMPANY";
	/** INDIVIDUAL = INDIVIDUAL */
	public static final String RECIPIENTTYPE_INDIVIDUAL = "INDIVIDUAL";
	@Override
	public void setRecipientType (final java.lang.String RecipientType)
	{
		set_Value (COLUMNNAME_RecipientType, RecipientType);
	}

	@Override
	public java.lang.String getRecipientType()
	{
		return get_ValueAsString(COLUMNNAME_RecipientType);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0)
			set_Value (COLUMNNAME_Record_ID, null);
		else
			set_Value (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID()
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setRegionName (final @Nullable java.lang.String RegionName)
	{
		set_Value (COLUMNNAME_RegionName, RegionName);
	}

	@Override
	public java.lang.String getRegionName()
	{
		return get_ValueAsString(COLUMNNAME_RegionName);
	}

	@Override
	public void setRevolut_Payment_Export_ID (final int Revolut_Payment_Export_ID)
	{
		if (Revolut_Payment_Export_ID < 1)
			set_ValueNoCheck (COLUMNNAME_Revolut_Payment_Export_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_Revolut_Payment_Export_ID, Revolut_Payment_Export_ID);
	}

	@Override
	public int getRevolut_Payment_Export_ID()
	{
		return get_ValueAsInt(COLUMNNAME_Revolut_Payment_Export_ID);
	}

	@Override
	public void setRoutingNo (final @Nullable java.lang.String RoutingNo)
	{
		set_Value (COLUMNNAME_RoutingNo, RoutingNo);
	}

	@Override
	public java.lang.String getRoutingNo()
	{
		return get_ValueAsString(COLUMNNAME_RoutingNo);
	}
}