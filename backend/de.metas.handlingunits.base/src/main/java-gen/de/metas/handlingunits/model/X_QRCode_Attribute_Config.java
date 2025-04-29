/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2025 metas GmbH
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
package de.metas.handlingunits.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for QRCode_Attribute_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_QRCode_Attribute_Config extends org.compiere.model.PO implements I_QRCode_Attribute_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1935730608L;

    /** Standard Constructor */
    public X_QRCode_Attribute_Config (final Properties ctx, final int QRCode_Attribute_Config_ID, @Nullable final String trxName)
    {
      super (ctx, QRCode_Attribute_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_QRCode_Attribute_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Attribute_ID (final int AD_Attribute_ID)
	{
		if (AD_Attribute_ID < 1) 
			set_Value (COLUMNNAME_AD_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Attribute_ID, AD_Attribute_ID);
	}

	@Override
	public int getAD_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Attribute_ID);
	}

	@Override
	public void setQRCode_Attribute_Config_ID (final int QRCode_Attribute_Config_ID)
	{
		if (QRCode_Attribute_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_QRCode_Attribute_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_QRCode_Attribute_Config_ID, QRCode_Attribute_Config_ID);
	}

	@Override
	public int getQRCode_Attribute_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_QRCode_Attribute_Config_ID);
	}

	@Override
	public I_QRCode_Configuration getQRCode_Configuration()
	{
		return get_ValueAsPO(COLUMNNAME_QRCode_Configuration_ID, I_QRCode_Configuration.class);
	}

	@Override
	public void setQRCode_Configuration(final I_QRCode_Configuration QRCode_Configuration)
	{
		set_ValueFromPO(COLUMNNAME_QRCode_Configuration_ID, I_QRCode_Configuration.class, QRCode_Configuration);
	}

	@Override
	public void setQRCode_Configuration_ID (final int QRCode_Configuration_ID)
	{
		if (QRCode_Configuration_ID < 1) 
			set_Value (COLUMNNAME_QRCode_Configuration_ID, null);
		else 
			set_Value (COLUMNNAME_QRCode_Configuration_ID, QRCode_Configuration_ID);
	}

	@Override
	public int getQRCode_Configuration_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_QRCode_Configuration_ID);
	}
}