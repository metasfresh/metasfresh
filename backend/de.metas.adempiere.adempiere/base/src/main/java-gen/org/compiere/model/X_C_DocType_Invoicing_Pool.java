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

/** Generated Model for C_DocType_Invoicing_Pool
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_DocType_Invoicing_Pool extends org.compiere.model.PO implements I_C_DocType_Invoicing_Pool, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2120429852L;

    /** Standard Constructor */
    public X_C_DocType_Invoicing_Pool (final Properties ctx, final int C_DocType_Invoicing_Pool_ID, @Nullable final String trxName)
    {
      super (ctx, C_DocType_Invoicing_Pool_ID, trxName);
    }

    /** Load Constructor */
    public X_C_DocType_Invoicing_Pool (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_DocType_Invoicing_Pool_ID (final int C_DocType_Invoicing_Pool_ID)
	{
		if (C_DocType_Invoicing_Pool_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_Invoicing_Pool_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_Invoicing_Pool_ID, C_DocType_Invoicing_Pool_ID);
	}

	@Override
	public int getC_DocType_Invoicing_Pool_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_Invoicing_Pool_ID);
	}

	@Override
	public void setIsSOTrx (final boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, IsSOTrx);
	}

	@Override
	public boolean isSOTrx() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSOTrx);
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
	public void setNegative_Amt_C_DocType_ID (final int Negative_Amt_C_DocType_ID)
	{
		if (Negative_Amt_C_DocType_ID < 1) 
			set_Value (COLUMNNAME_Negative_Amt_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_Negative_Amt_C_DocType_ID, Negative_Amt_C_DocType_ID);
	}

	@Override
	public int getNegative_Amt_C_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Negative_Amt_C_DocType_ID);
	}

	@Override
	public void setPositive_Amt_C_DocType_ID (final int Positive_Amt_C_DocType_ID)
	{
		if (Positive_Amt_C_DocType_ID < 1) 
			set_Value (COLUMNNAME_Positive_Amt_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_Positive_Amt_C_DocType_ID, Positive_Amt_C_DocType_ID);
	}

	@Override
	public int getPositive_Amt_C_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Positive_Amt_C_DocType_ID);
	}
}