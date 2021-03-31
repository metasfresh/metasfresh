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

/** Generated Model for C_OLCand_AlbertaTherapyType
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_OLCand_AlbertaTherapyType extends org.compiere.model.PO implements I_C_OLCand_AlbertaTherapyType, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 660149060L;

    /** Standard Constructor */
    public X_C_OLCand_AlbertaTherapyType (final Properties ctx, final int C_OLCand_AlbertaTherapyType_ID, @Nullable final String trxName)
    {
      super (ctx, C_OLCand_AlbertaTherapyType_ID, trxName);
    }

    /** Load Constructor */
    public X_C_OLCand_AlbertaTherapyType (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_OLCand_AlbertaTherapyType_ID (final int C_OLCand_AlbertaTherapyType_ID)
	{
		if (C_OLCand_AlbertaTherapyType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OLCand_AlbertaTherapyType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OLCand_AlbertaTherapyType_ID, C_OLCand_AlbertaTherapyType_ID);
	}

	@Override
	public int getC_OLCand_AlbertaTherapyType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OLCand_AlbertaTherapyType_ID);
	}

	@Override
	public void setC_OLCand_ID (final int C_OLCand_ID)
	{
		if (C_OLCand_ID < 1) 
			set_Value (COLUMNNAME_C_OLCand_ID, null);
		else 
			set_Value (COLUMNNAME_C_OLCand_ID, C_OLCand_ID);
	}

	@Override
	public int getC_OLCand_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OLCand_ID);
	}

	@Override
	public void setTherapyType (final java.lang.String TherapyType)
	{
		set_Value (COLUMNNAME_TherapyType, TherapyType);
	}

	@Override
	public java.lang.String getTherapyType() 
	{
		return get_ValueAsString(COLUMNNAME_TherapyType);
	}
}