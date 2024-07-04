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

/** Generated Model for C_Doc_Outbound_Log_PostFinance_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Doc_Outbound_Log_PostFinance_Log extends org.compiere.model.PO implements I_C_Doc_Outbound_Log_PostFinance_Log, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = -1823393467L;

	/** Standard Constructor */
	public X_C_Doc_Outbound_Log_PostFinance_Log (final Properties ctx, final int C_Doc_Outbound_Log_PostFinance_Log_ID, @Nullable final String trxName)
	{
		super (ctx, C_Doc_Outbound_Log_PostFinance_Log_ID, trxName);
	}

	/** Load Constructor */
	public X_C_Doc_Outbound_Log_PostFinance_Log (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Issue_ID (final int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1)
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else
			set_Value (COLUMNNAME_AD_Issue_ID, AD_Issue_ID);
	}

	@Override
	public int getAD_Issue_ID()
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public void setC_Doc_Outbound_Log_ID (final int C_Doc_Outbound_Log_ID)
	{
		if (C_Doc_Outbound_Log_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Doc_Outbound_Log_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Doc_Outbound_Log_ID, C_Doc_Outbound_Log_ID);
	}

	@Override
	public int getC_Doc_Outbound_Log_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Doc_Outbound_Log_ID);
	}

	@Override
	public void setC_Doc_Outbound_Log_PostFinance_Log_ID (final int C_Doc_Outbound_Log_PostFinance_Log_ID)
	{
		if (C_Doc_Outbound_Log_PostFinance_Log_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Doc_Outbound_Log_PostFinance_Log_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Doc_Outbound_Log_PostFinance_Log_ID, C_Doc_Outbound_Log_PostFinance_Log_ID);
	}

	@Override
	public int getC_Doc_Outbound_Log_PostFinance_Log_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Doc_Outbound_Log_PostFinance_Log_ID);
	}

	@Override
	public void setIsError (final boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, IsError);
	}

	@Override
	public boolean isError()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsError);
	}

	@Override
	public void setMsgText (final java.lang.String MsgText)
	{
		set_Value (COLUMNNAME_MsgText, MsgText);
	}

	@Override
	public java.lang.String getMsgText()
	{
		return get_ValueAsString(COLUMNNAME_MsgText);
	}

	@Override
	public void setPostFinance_Transaction_Id (final @Nullable java.lang.String PostFinance_Transaction_Id)
	{
		set_Value (COLUMNNAME_PostFinance_Transaction_Id, PostFinance_Transaction_Id);
	}

	@Override
	public java.lang.String getPostFinance_Transaction_Id()
	{
		return get_ValueAsString(COLUMNNAME_PostFinance_Transaction_Id);
	}
}