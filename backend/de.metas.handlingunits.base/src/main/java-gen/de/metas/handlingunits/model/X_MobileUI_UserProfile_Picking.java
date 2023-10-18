/*
 * #%L
 * de.metas.handlingunits.base
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
package de.metas.handlingunits.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for MobileUI_UserProfile_Picking
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_MobileUI_UserProfile_Picking extends org.compiere.model.PO implements I_MobileUI_UserProfile_Picking, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1722363558L;

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

	/** 
	 * createShipmentPolicy AD_Reference_ID=541839
	 * Reference name: Create Shipment Policy
	 */
	public static final int CREATESHIPMENTPOLICY_AD_Reference_ID=541839;
	/** DO_NOT_CREATE = NO */
	public static final String CREATESHIPMENTPOLICY_DO_NOT_CREATE = "NO";
	/** CREATE_DRAFT = DR */
	public static final String CREATESHIPMENTPOLICY_CREATE_DRAFT = "DR";
	/** CREATE_AND_COMPLETE = CO */
	public static final String CREATESHIPMENTPOLICY_CREATE_AND_COMPLETE = "CO";
	@Override
	public void setcreateShipmentPolicy (final java.lang.String createShipmentPolicy)
	{
		set_Value (COLUMNNAME_createShipmentPolicy, createShipmentPolicy);
	}

	@Override
	public java.lang.String getcreateShipmentPolicy() 
	{
		return get_ValueAsString(COLUMNNAME_createShipmentPolicy);
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