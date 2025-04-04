/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
package de.metas.picking.model;

import de.metas.picking.model.I_PickingProfile_PickingJobLineConfig;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for PickingProfile_PickingJobLineConfig
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PickingProfile_PickingJobLineConfig extends org.compiere.model.PO implements I_PickingProfile_PickingJobLineConfig, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = -925747516L;

    /** Standard Constructor */
    public X_PickingProfile_PickingJobLineConfig (final Properties ctx, final int PickingProfile_PickingJobLineConfig_ID, @Nullable final String trxName)
    {
      super (ctx, PickingProfile_PickingJobLineConfig_ID, trxName);
    }

    /** Load Constructor */
    public X_PickingProfile_PickingJobLineConfig (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setIsShowLastPickedBestBeforeDate (final boolean IsShowLastPickedBestBeforeDate)
	{
		set_Value (COLUMNNAME_IsShowLastPickedBestBeforeDate, IsShowLastPickedBestBeforeDate);
	}

	@Override
	public boolean isShowLastPickedBestBeforeDate() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsShowLastPickedBestBeforeDate);
	}

	@Override
	public org.compiere.model.I_MobileUI_UserProfile_Picking getMobileUI_UserProfile_Picking()
	{
		return get_ValueAsPO(COLUMNNAME_MobileUI_UserProfile_Picking_ID, org.compiere.model.I_MobileUI_UserProfile_Picking.class);
	}

	@Override
	public void setMobileUI_UserProfile_Picking(final org.compiere.model.I_MobileUI_UserProfile_Picking MobileUI_UserProfile_Picking)
	{
		set_ValueFromPO(COLUMNNAME_MobileUI_UserProfile_Picking_ID, org.compiere.model.I_MobileUI_UserProfile_Picking.class, MobileUI_UserProfile_Picking);
	}

	@Override
	public void setMobileUI_UserProfile_Picking_ID (final int MobileUI_UserProfile_Picking_ID)
	{
		if (MobileUI_UserProfile_Picking_ID < 1) 
			set_Value (COLUMNNAME_MobileUI_UserProfile_Picking_ID, null);
		else 
			set_Value (COLUMNNAME_MobileUI_UserProfile_Picking_ID, MobileUI_UserProfile_Picking_ID);
	}

	@Override
	public int getMobileUI_UserProfile_Picking_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_MobileUI_UserProfile_Picking_ID);
	}

	@Override
	public void setPickingProfile_PickingJobLineConfig_ID (final int PickingProfile_PickingJobLineConfig_ID)
	{
		if (PickingProfile_PickingJobLineConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PickingProfile_PickingJobLineConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PickingProfile_PickingJobLineConfig_ID, PickingProfile_PickingJobLineConfig_ID);
	}

	@Override
	public int getPickingProfile_PickingJobLineConfig_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PickingProfile_PickingJobLineConfig_ID);
	}
}