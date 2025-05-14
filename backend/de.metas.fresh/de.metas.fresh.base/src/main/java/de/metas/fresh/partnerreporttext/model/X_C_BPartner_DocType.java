/*
 * #%L
 * de.metas.fresh.base
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
package de.metas.fresh.partnerreporttext.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BPartner_DocType
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_BPartner_DocType extends org.compiere.model.PO implements I_C_BPartner_DocType, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 835197703L;

    /** Standard Constructor */
    public X_C_BPartner_DocType (final Properties ctx, final int C_BPartner_DocType_ID, @Nullable final String trxName)
    {
      super (ctx, C_BPartner_DocType_ID, trxName);
    }

    /** Load Constructor */
    public X_C_BPartner_DocType (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_DocType_ID (final int C_BPartner_DocType_ID)
	{
		if (C_BPartner_DocType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_DocType_ID, C_BPartner_DocType_ID);
	}

	@Override
	public int getC_BPartner_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_DocType_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public I_C_BPartner_Report_Text getC_BPartner_Report_Text()
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Report_Text_ID, I_C_BPartner_Report_Text.class);
	}

	@Override
	public void setC_BPartner_Report_Text(final I_C_BPartner_Report_Text C_BPartner_Report_Text)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Report_Text_ID, I_C_BPartner_Report_Text.class, C_BPartner_Report_Text);
	}

	@Override
	public void setC_BPartner_Report_Text_ID (final int C_BPartner_Report_Text_ID)
	{
		if (C_BPartner_Report_Text_ID < 1)
			set_Value (COLUMNNAME_C_BPartner_Report_Text_ID, null);
		else
			set_Value (COLUMNNAME_C_BPartner_Report_Text_ID, C_BPartner_Report_Text_ID);
	}

	@Override
	public int getC_BPartner_Report_Text_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Report_Text_ID);
	}

	@Override
	public void setC_DocType_ID (final int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}
}
