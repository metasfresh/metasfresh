/*
 * #%L
 * de.metas.postfinance.base
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

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PostFinance_BPartner_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PostFinance_BPartner_Config extends org.compiere.model.PO implements I_PostFinance_BPartner_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 201959444L;

    /** Standard Constructor */
    public X_PostFinance_BPartner_Config (final Properties ctx, final int PostFinance_BPartner_Config_ID, @Nullable final String trxName)
    {
      super (ctx, PostFinance_BPartner_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_PostFinance_BPartner_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setPostFinance_BPartner_Config_ID (final int PostFinance_BPartner_Config_ID)
	{
		if (PostFinance_BPartner_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PostFinance_BPartner_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PostFinance_BPartner_Config_ID, PostFinance_BPartner_Config_ID);
	}

	@Override
	public int getPostFinance_BPartner_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PostFinance_BPartner_Config_ID);
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
}