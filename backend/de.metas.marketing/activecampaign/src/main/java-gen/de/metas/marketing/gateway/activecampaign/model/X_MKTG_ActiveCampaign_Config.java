/*
 * #%L
 * marketing-activecampaign
 * %%
 * Copyright (C) 2022 metas GmbH
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
package de.metas.marketing.gateway.activecampaign.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MKTG_ActiveCampaign_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MKTG_ActiveCampaign_Config extends org.compiere.model.PO implements I_MKTG_ActiveCampaign_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -713829835L;

    /** Standard Constructor */
    public X_MKTG_ActiveCampaign_Config (final Properties ctx, final int MKTG_ActiveCampaign_Config_ID, @Nullable final String trxName)
    {
      super (ctx, MKTG_ActiveCampaign_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_MKTG_ActiveCampaign_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setApiKey (final java.lang.String ApiKey)
	{
		set_Value (COLUMNNAME_ApiKey, ApiKey);
	}

	@Override
	public java.lang.String getApiKey() 
	{
		return get_ValueAsString(COLUMNNAME_ApiKey);
	}

	@Override
	public void setBaseURL (final java.lang.String BaseURL)
	{
		set_Value (COLUMNNAME_BaseURL, BaseURL);
	}

	@Override
	public java.lang.String getBaseURL() 
	{
		return get_ValueAsString(COLUMNNAME_BaseURL);
	}

	@Override
	public void setMKTG_ActiveCampaign_Config_ID (final int MKTG_ActiveCampaign_Config_ID)
	{
		if (MKTG_ActiveCampaign_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MKTG_ActiveCampaign_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MKTG_ActiveCampaign_Config_ID, MKTG_ActiveCampaign_Config_ID);
	}

	@Override
	public int getMKTG_ActiveCampaign_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MKTG_ActiveCampaign_Config_ID);
	}

	@Override
	public void setMKTG_Platform_ID (final int MKTG_Platform_ID)
	{
		if (MKTG_Platform_ID < 1) 
			set_Value (COLUMNNAME_MKTG_Platform_ID, null);
		else 
			set_Value (COLUMNNAME_MKTG_Platform_ID, MKTG_Platform_ID);
	}

	@Override
	public int getMKTG_Platform_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MKTG_Platform_ID);
	}
}