/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MobileUI_UserProfile_Picking
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MobileUI_UserProfile_Picking extends org.compiere.model.PO implements I_MobileUI_UserProfile_Picking, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1244133445L;

    /** Standard Constructor */
    public X_MobileUI_UserProfile_Picking (final Properties ctx, final int MobileUI_UserProfile_Picking_ID, @Nullable final String trxName)
    {
      super (ctx, MobileUI_UserProfile_Picking_ID, trxName);
    }

    /** Load Constructor */
    public X_MobileUI_UserProfile_Picking (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setMobileUI_UserProfile_Picking_ID (final int MobileUI_UserProfile_Picking_ID)
	{
		if (MobileUI_UserProfile_Picking_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MobileUI_UserProfile_Picking_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MobileUI_UserProfile_Picking_ID, MobileUI_UserProfile_Picking_ID);
	}

	@Override
	public int getMobileUI_UserProfile_Picking_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_UserProfile_Picking_ID);
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
}