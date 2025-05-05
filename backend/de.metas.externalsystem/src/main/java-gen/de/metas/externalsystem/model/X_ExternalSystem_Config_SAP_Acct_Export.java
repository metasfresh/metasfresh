/*
 * #%L
 * de.metas.externalsystem
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
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Config_SAP_Acct_Export
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_SAP_Acct_Export extends org.compiere.model.PO implements I_ExternalSystem_Config_SAP_Acct_Export, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1258326566L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_SAP_Acct_Export (final Properties ctx, final int ExternalSystem_Config_SAP_Acct_Export_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_SAP_Acct_Export_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_SAP_Acct_Export (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Process getAD_Process()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setAD_Process(final org.compiere.model.I_AD_Process AD_Process)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class, AD_Process);
	}

	@Override
	public void setAD_Process_ID (final int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_ID, AD_Process_ID);
	}

	@Override
	public int getAD_Process_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Process_ID);
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

	@Override
	public void setExternalSystem_Config_SAP_Acct_Export_ID (final int ExternalSystem_Config_SAP_Acct_Export_ID)
	{
		if (ExternalSystem_Config_SAP_Acct_Export_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_SAP_Acct_Export_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_SAP_Acct_Export_ID, ExternalSystem_Config_SAP_Acct_Export_ID);
	}

	@Override
	public int getExternalSystem_Config_SAP_Acct_Export_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_SAP_Acct_Export_ID);
	}

	@Override
	public de.metas.externalsystem.model.I_ExternalSystem_Config_SAP getExternalSystem_Config_SAP()
	{
		return get_ValueAsPO(COLUMNNAME_ExternalSystem_Config_SAP_ID, de.metas.externalsystem.model.I_ExternalSystem_Config_SAP.class);
	}

	@Override
	public void setExternalSystem_Config_SAP(final de.metas.externalsystem.model.I_ExternalSystem_Config_SAP ExternalSystem_Config_SAP)
	{
		set_ValueFromPO(COLUMNNAME_ExternalSystem_Config_SAP_ID, de.metas.externalsystem.model.I_ExternalSystem_Config_SAP.class, ExternalSystem_Config_SAP);
	}

	@Override
	public void setExternalSystem_Config_SAP_ID (final int ExternalSystem_Config_SAP_ID)
	{
		if (ExternalSystem_Config_SAP_ID < 1) 
			set_Value (COLUMNNAME_ExternalSystem_Config_SAP_ID, null);
		else 
			set_Value (COLUMNNAME_ExternalSystem_Config_SAP_ID, ExternalSystem_Config_SAP_ID);
	}

	@Override
	public int getExternalSystem_Config_SAP_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_SAP_ID);
	}
}