/*
 * #%L
 * de.metas.externalsystem
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
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Config_Shopware6_UOM
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_Shopware6_UOM extends org.compiere.model.PO implements I_ExternalSystem_Config_Shopware6_UOM, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1127010332L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_Shopware6_UOM (final Properties ctx, final int ExternalSystem_Config_Shopware6_UOM_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_Shopware6_UOM_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_Shopware6_UOM (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public I_ExternalSystem_Config_Shopware6 getExternalSystem_Config_Shopware6()
	{
		return get_ValueAsPO(COLUMNNAME_ExternalSystem_Config_Shopware6_ID, I_ExternalSystem_Config_Shopware6.class);
	}

	@Override
	public void setExternalSystem_Config_Shopware6(final I_ExternalSystem_Config_Shopware6 ExternalSystem_Config_Shopware6)
	{
		set_ValueFromPO(COLUMNNAME_ExternalSystem_Config_Shopware6_ID, I_ExternalSystem_Config_Shopware6.class, ExternalSystem_Config_Shopware6);
	}

	@Override
	public void setExternalSystem_Config_Shopware6_ID (final int ExternalSystem_Config_Shopware6_ID)
	{
		if (ExternalSystem_Config_Shopware6_ID < 1) 
			set_Value (COLUMNNAME_ExternalSystem_Config_Shopware6_ID, null);
		else 
			set_Value (COLUMNNAME_ExternalSystem_Config_Shopware6_ID, ExternalSystem_Config_Shopware6_ID);
	}

	@Override
	public int getExternalSystem_Config_Shopware6_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_Shopware6_ID);
	}

	@Override
	public void setExternalSystem_Config_Shopware6_UOM_ID (final int ExternalSystem_Config_Shopware6_UOM_ID)
	{
		if (ExternalSystem_Config_Shopware6_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_Shopware6_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_Shopware6_UOM_ID, ExternalSystem_Config_Shopware6_UOM_ID);
	}

	@Override
	public int getExternalSystem_Config_Shopware6_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_Shopware6_UOM_ID);
	}

	@Override
	public void setShopwareCode (final String ShopwareCode)
	{
		set_Value (COLUMNNAME_ShopwareCode, ShopwareCode);
	}

	@Override
	public String getShopwareCode()
	{
		return get_ValueAsString(COLUMNNAME_ShopwareCode);
	}
}