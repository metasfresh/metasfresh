/*
 * #%L
 * de.metas.contracts
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
package de.metas.contracts.commission.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_MediatedCommissionSettings
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_MediatedCommissionSettings extends org.compiere.model.PO implements I_C_MediatedCommissionSettings, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1726230519L;

    /** Standard Constructor */
    public X_C_MediatedCommissionSettings (final Properties ctx, final int C_MediatedCommissionSettings_ID, @Nullable final String trxName)
    {
      super (ctx, C_MediatedCommissionSettings_ID, trxName);
    }

    /** Load Constructor */
    public X_C_MediatedCommissionSettings (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_MediatedCommissionSettings_ID (final int C_MediatedCommissionSettings_ID)
	{
		if (C_MediatedCommissionSettings_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_MediatedCommissionSettings_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_MediatedCommissionSettings_ID, C_MediatedCommissionSettings_ID);
	}

	@Override
	public int getC_MediatedCommissionSettings_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_MediatedCommissionSettings_ID);
	}

	@Override
	public void setCommission_Product_ID (final int Commission_Product_ID)
	{
		if (Commission_Product_ID < 1) 
			set_Value (COLUMNNAME_Commission_Product_ID, null);
		else 
			set_Value (COLUMNNAME_Commission_Product_ID, Commission_Product_ID);
	}

	@Override
	public int getCommission_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Commission_Product_ID);
	}

	@Override
	public void setDescription (final @Nullable String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public String getDescription()
	{
		return get_ValueAsString(COLUMNNAME_Description);
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
	public void setPointsPrecision (final int PointsPrecision)
	{
		set_Value (COLUMNNAME_PointsPrecision, PointsPrecision);
	}

	@Override
	public int getPointsPrecision() 
	{
		return get_ValueAsInt(COLUMNNAME_PointsPrecision);
	}
}