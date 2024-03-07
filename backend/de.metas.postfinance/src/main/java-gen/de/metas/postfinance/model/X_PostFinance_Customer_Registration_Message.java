/*
 * #%L
 * de.metas.postfinance
 * %%
 * Copyright (C) 2024 metas GmbH
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
package de.metas.postfinance.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for PostFinance_Customer_Registration_Message
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PostFinance_Customer_Registration_Message extends org.compiere.model.PO implements I_PostFinance_Customer_Registration_Message, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 641442955L;

    /** Standard Constructor */
    public X_PostFinance_Customer_Registration_Message (final Properties ctx, final int PostFinance_Customer_Registration_Message_ID, @Nullable final String trxName)
    {
      super (ctx, PostFinance_Customer_Registration_Message_ID, trxName);
    }

    /** Load Constructor */
    public X_PostFinance_Customer_Registration_Message (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAdditionalData (final @Nullable java.lang.String AdditionalData)
	{
		set_Value (COLUMNNAME_AdditionalData, AdditionalData);
	}

	@Override
	public java.lang.String getAdditionalData() 
	{
		return get_ValueAsString(COLUMNNAME_AdditionalData);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setPostFinance_Customer_Registration_Message_ID (final int PostFinance_Customer_Registration_Message_ID)
	{
		if (PostFinance_Customer_Registration_Message_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PostFinance_Customer_Registration_Message_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PostFinance_Customer_Registration_Message_ID, PostFinance_Customer_Registration_Message_ID);
	}

	@Override
	public int getPostFinance_Customer_Registration_Message_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PostFinance_Customer_Registration_Message_ID);
	}

	@Override
	public void setPostFinance_Receiver_eBillId (final java.lang.String PostFinance_Receiver_eBillId)
	{
		set_Value (COLUMNNAME_PostFinance_Receiver_eBillId, PostFinance_Receiver_eBillId);
	}

	@Override
	public java.lang.String getPostFinance_Receiver_eBillId() 
	{
		return get_ValueAsString(COLUMNNAME_PostFinance_Receiver_eBillId);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setQRCode (final @Nullable java.lang.String QRCode)
	{
		set_Value (COLUMNNAME_QRCode, QRCode);
	}

	@Override
	public java.lang.String getQRCode() 
	{
		return get_ValueAsString(COLUMNNAME_QRCode);
	}

	/** 
	 * SubscriptionType AD_Reference_ID=541852
	 * Reference name: PostFinance Subscription Type
	 */
	public static final int SUBSCRIPTIONTYPE_AD_Reference_ID=541852;
	/** Registration = 1 */
	public static final String SUBSCRIPTIONTYPE_Registration = "1";
	/** DirectRegistration = 2 */
	public static final String SUBSCRIPTIONTYPE_DirectRegistration = "2";
	/** Deregistration = 3 */
	public static final String SUBSCRIPTIONTYPE_Deregistration = "3";
	@Override
	public void setSubscriptionType (final java.lang.String SubscriptionType)
	{
		set_Value (COLUMNNAME_SubscriptionType, SubscriptionType);
	}

	@Override
	public java.lang.String getSubscriptionType() 
	{
		return get_ValueAsString(COLUMNNAME_SubscriptionType);
	}
}