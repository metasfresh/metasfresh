/*
 * #%L
 * de.metas.postfinance
 * %%
 * Copyright (C) 2024 metas GmbH
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
package de.metas.postfinance.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Org_PostFinance_PaperBill_References
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Org_PostFinance_PaperBill_References extends org.compiere.model.PO implements I_AD_Org_PostFinance_PaperBill_References, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -718374517L;

    /** Standard Constructor */
    public X_AD_Org_PostFinance_PaperBill_References (final Properties ctx, final int AD_Org_PostFinance_PaperBill_References_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Org_PostFinance_PaperBill_References_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Org_PostFinance_PaperBill_References (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Org_PostFinance_PaperBill_References_ID (final int AD_Org_PostFinance_PaperBill_References_ID)
	{
		if (AD_Org_PostFinance_PaperBill_References_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Org_PostFinance_PaperBill_References_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Org_PostFinance_PaperBill_References_ID, AD_Org_PostFinance_PaperBill_References_ID);
	}

	@Override
	public int getAD_Org_PostFinance_PaperBill_References_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Org_PostFinance_PaperBill_References_ID);
	}

	@Override
	public void setReference_Position (final java.lang.String Reference_Position)
	{
		set_ValueNoCheck (COLUMNNAME_Reference_Position, Reference_Position);
	}

	@Override
	public java.lang.String getReference_Position() 
	{
		return get_ValueAsString(COLUMNNAME_Reference_Position);
	}

	@Override
	public void setReference_Type (final java.lang.String Reference_Type)
	{
		set_ValueNoCheck (COLUMNNAME_Reference_Type, Reference_Type);
	}

	@Override
	public java.lang.String getReference_Type() 
	{
		return get_ValueAsString(COLUMNNAME_Reference_Type);
	}

	@Override
	public void setReference_Value (final java.lang.String Reference_Value)
	{
		set_Value (COLUMNNAME_Reference_Value, Reference_Value);
	}

	@Override
	public java.lang.String getReference_Value() 
	{
		return get_ValueAsString(COLUMNNAME_Reference_Value);
	}
}