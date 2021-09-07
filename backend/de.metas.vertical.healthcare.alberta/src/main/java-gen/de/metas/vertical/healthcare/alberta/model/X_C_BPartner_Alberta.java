/*
 * #%L
 * de.metas.vertical.healthcare.alberta
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
package de.metas.vertical.healthcare.alberta.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_Alberta
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_Alberta extends org.compiere.model.PO implements I_C_BPartner_Alberta, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1953553072L;

    /** Standard Constructor */
    public X_C_BPartner_Alberta (final Properties ctx, final int C_BPartner_Alberta_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_Alberta_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_Alberta (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_Alberta_ID (final int C_BPartner_Alberta_ID)
	{
		if (C_BPartner_Alberta_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Alberta_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_Alberta_ID, C_BPartner_Alberta_ID);
	}

	@Override
	public int getC_BPartner_Alberta_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Alberta_ID);
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
	public void setIsArchived (final boolean IsArchived)
	{
		set_Value (COLUMNNAME_IsArchived, IsArchived);
	}

	@Override
	public boolean isArchived() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsArchived);
	}

	@Override
	public void setTimestamp (final @Nullable java.sql.Timestamp Timestamp)
	{
		set_Value (COLUMNNAME_Timestamp, Timestamp);
	}

	@Override
	public java.sql.Timestamp getTimestamp() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_Timestamp);
	}

	@Override
	public void setTitle (final @Nullable java.lang.String Title)
	{
		set_Value (COLUMNNAME_Title, Title);
	}

	@Override
	public java.lang.String getTitle() 
	{
		return get_ValueAsString(COLUMNNAME_Title);
	}

	@Override
	public void setTitleShort (final @Nullable java.lang.String TitleShort)
	{
		set_Value (COLUMNNAME_TitleShort, TitleShort);
	}

	@Override
	public java.lang.String getTitleShort() 
	{
		return get_ValueAsString(COLUMNNAME_TitleShort);
	}
}