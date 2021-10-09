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
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Customer_Trade_Margin_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Customer_Trade_Margin_Line extends org.compiere.model.PO implements I_C_Customer_Trade_Margin_Line, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 560976520L;

    /** Standard Constructor */
    public X_C_Customer_Trade_Margin_Line (final Properties ctx, final int C_Customer_Trade_Margin_Line_ID, @Nullable final String trxName)
    {
      super (ctx, C_Customer_Trade_Margin_Line_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Customer_Trade_Margin_Line (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_Customer_ID (final int C_BPartner_Customer_ID)
	{
		if (C_BPartner_Customer_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Customer_ID, C_BPartner_Customer_ID);
	}

	@Override
	public int getC_BPartner_Customer_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Customer_ID);
	}

	@Override
	public de.metas.contracts.model.I_C_Customer_Trade_Margin getC_Customer_Trade_Margin()
	{
		return get_ValueAsPO(COLUMNNAME_C_Customer_Trade_Margin_ID, de.metas.contracts.model.I_C_Customer_Trade_Margin.class);
	}

	@Override
	public void setC_Customer_Trade_Margin(final de.metas.contracts.model.I_C_Customer_Trade_Margin C_Customer_Trade_Margin)
	{
		set_ValueFromPO(COLUMNNAME_C_Customer_Trade_Margin_ID, de.metas.contracts.model.I_C_Customer_Trade_Margin.class, C_Customer_Trade_Margin);
	}

	@Override
	public void setC_Customer_Trade_Margin_ID (final int C_Customer_Trade_Margin_ID)
	{
		if (C_Customer_Trade_Margin_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Customer_Trade_Margin_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Customer_Trade_Margin_ID, C_Customer_Trade_Margin_ID);
	}

	@Override
	public int getC_Customer_Trade_Margin_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Customer_Trade_Margin_ID);
	}

	@Override
	public void setC_Customer_Trade_Margin_Line_ID (final int C_Customer_Trade_Margin_Line_ID)
	{
		if (C_Customer_Trade_Margin_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Customer_Trade_Margin_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Customer_Trade_Margin_Line_ID, C_Customer_Trade_Margin_Line_ID);
	}

	@Override
	public int getC_Customer_Trade_Margin_Line_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Customer_Trade_Margin_Line_ID);
	}

	@Override
	public void setMargin (final int Margin)
	{
		set_Value (COLUMNNAME_Margin, Margin);
	}

	@Override
	public int getMargin() 
	{
		return get_ValueAsInt(COLUMNNAME_Margin);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}
}