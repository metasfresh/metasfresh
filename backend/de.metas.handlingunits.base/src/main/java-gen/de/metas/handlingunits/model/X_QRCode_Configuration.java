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

/** Generated Model for QRCode_Configuration
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_QRCode_Configuration extends org.compiere.model.PO implements I_QRCode_Configuration, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1807661472L;

    /** Standard Constructor */
    public X_QRCode_Configuration (final Properties ctx, final int QRCode_Configuration_ID, @Nullable final String trxName)
    {
      super (ctx, QRCode_Configuration_ID, trxName);
    }

    /** Load Constructor */
    public X_QRCode_Configuration (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setIsOneQRCodeForAggregatedHUs (final boolean IsOneQRCodeForAggregatedHUs)
	{
		set_Value (COLUMNNAME_IsOneQRCodeForAggregatedHUs, IsOneQRCodeForAggregatedHUs);
	}

	@Override
	public boolean isOneQRCodeForAggregatedHUs() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOneQRCodeForAggregatedHUs);
	}

	@Override
	public void setIsOneQRCodeForMatchingAttributes (final boolean IsOneQRCodeForMatchingAttributes)
	{
		set_Value (COLUMNNAME_IsOneQRCodeForMatchingAttributes, IsOneQRCodeForMatchingAttributes);
	}

	@Override
	public boolean isOneQRCodeForMatchingAttributes() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOneQRCodeForMatchingAttributes);
	}

	@Override
	public void setName (final String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public String getName()
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setQRCode_Configuration_ID (final int QRCode_Configuration_ID)
	{
		if (QRCode_Configuration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_QRCode_Configuration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_QRCode_Configuration_ID, QRCode_Configuration_ID);
	}

	@Override
	public int getQRCode_Configuration_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_QRCode_Configuration_ID);
	}
}