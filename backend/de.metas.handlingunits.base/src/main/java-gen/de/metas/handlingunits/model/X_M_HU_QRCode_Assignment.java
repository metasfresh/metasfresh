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

/** Generated Model for M_HU_QRCode_Assignment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_HU_QRCode_Assignment extends org.compiere.model.PO implements I_M_HU_QRCode_Assignment, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 33901466L;

    /** Standard Constructor */
    public X_M_HU_QRCode_Assignment (final Properties ctx, final int M_HU_QRCode_Assignment_ID, @Nullable final String trxName)
    {
      super (ctx, M_HU_QRCode_Assignment_ID, trxName);
    }

    /** Load Constructor */
    public X_M_HU_QRCode_Assignment (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public I_M_HU getM_HU()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_ID, I_M_HU.class);
	}

	@Override
	public void setM_HU(final I_M_HU M_HU)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_ID, I_M_HU.class, M_HU);
	}

	@Override
	public void setM_HU_ID (final int M_HU_ID)
	{
		if (M_HU_ID < 1) 
			set_Value (COLUMNNAME_M_HU_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_ID, M_HU_ID);
	}

	@Override
	public int getM_HU_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_ID);
	}

	@Override
	public void setM_HU_QRCode_Assignment_ID (final int M_HU_QRCode_Assignment_ID)
	{
		if (M_HU_QRCode_Assignment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_HU_QRCode_Assignment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_HU_QRCode_Assignment_ID, M_HU_QRCode_Assignment_ID);
	}

	@Override
	public int getM_HU_QRCode_Assignment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_QRCode_Assignment_ID);
	}

	@Override
	public I_M_HU_QRCode getM_HU_QRCode()
	{
		return get_ValueAsPO(COLUMNNAME_M_HU_QRCode_ID, I_M_HU_QRCode.class);
	}

	@Override
	public void setM_HU_QRCode(final I_M_HU_QRCode M_HU_QRCode)
	{
		set_ValueFromPO(COLUMNNAME_M_HU_QRCode_ID, I_M_HU_QRCode.class, M_HU_QRCode);
	}

	@Override
	public void setM_HU_QRCode_ID (final int M_HU_QRCode_ID)
	{
		if (M_HU_QRCode_ID < 1) 
			set_Value (COLUMNNAME_M_HU_QRCode_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_QRCode_ID, M_HU_QRCode_ID);
	}

	@Override
	public int getM_HU_QRCode_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_HU_QRCode_ID);
	}
}