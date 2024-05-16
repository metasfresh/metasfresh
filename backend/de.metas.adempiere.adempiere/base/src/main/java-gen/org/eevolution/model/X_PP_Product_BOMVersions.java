/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
package org.eevolution.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PP_Product_BOMVersions
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_PP_Product_BOMVersions extends org.compiere.model.PO implements I_PP_Product_BOMVersions, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1030246596L;

    /** Standard Constructor */
    public X_PP_Product_BOMVersions (final Properties ctx, final int PP_Product_BOMVersions_ID, @Nullable final String trxName)
    {
      super (ctx, PP_Product_BOMVersions_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_Product_BOMVersions (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
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

	@Override
	public void setPP_Product_BOMVersions_ID (final int PP_Product_BOMVersions_ID)
	{
		if (PP_Product_BOMVersions_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Product_BOMVersions_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Product_BOMVersions_ID, PP_Product_BOMVersions_ID);
	}

	@Override
	public int getPP_Product_BOMVersions_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Product_BOMVersions_ID);
	}
}