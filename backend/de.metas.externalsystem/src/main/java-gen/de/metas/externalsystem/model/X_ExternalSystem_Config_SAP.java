/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for ExternalSystem_Config_SAP
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_SAP extends org.compiere.model.PO implements I_ExternalSystem_Config_SAP, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -303109327L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_SAP (final Properties ctx, final int ExternalSystem_Config_SAP_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_SAP_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_SAP (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.externalsystem.model.I_ExternalSystem_Config getExternalSystem_Config()
	{
		return get_ValueAsPO(COLUMNNAME_ExternalSystem_Config_ID, de.metas.externalsystem.model.I_ExternalSystem_Config.class);
	}

	@Override
	public void setExternalSystem_Config(final de.metas.externalsystem.model.I_ExternalSystem_Config ExternalSystem_Config)
	{
		set_ValueFromPO(COLUMNNAME_ExternalSystem_Config_ID, de.metas.externalsystem.model.I_ExternalSystem_Config.class, ExternalSystem_Config);
	}

	@Override
	public void setExternalSystem_Config_ID (final int ExternalSystem_Config_ID)
	{
		if (ExternalSystem_Config_ID < 1) 
			set_Value (COLUMNNAME_ExternalSystem_Config_ID, null);
		else 
			set_Value (COLUMNNAME_ExternalSystem_Config_ID, ExternalSystem_Config_ID);
	}

	@Override
	public int getExternalSystem_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ID);
	}

	@Override
	public void setExternalSystem_Config_SAP_ID (final int ExternalSystem_Config_SAP_ID)
	{
		if (ExternalSystem_Config_SAP_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_SAP_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_SAP_ID, ExternalSystem_Config_SAP_ID);
	}

	@Override
	public int getExternalSystem_Config_SAP_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_SAP_ID);
	}

	@Override
	public void setExternalSystemValue (final java.lang.String ExternalSystemValue)
	{
		set_Value (COLUMNNAME_ExternalSystemValue, ExternalSystemValue);
	}

	@Override
	public java.lang.String getExternalSystemValue() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalSystemValue);
	}

	@Override
	public void setSFTP_HostName (final java.lang.String SFTP_HostName)
	{
		set_Value (COLUMNNAME_SFTP_HostName, SFTP_HostName);
	}

	@Override
	public java.lang.String getSFTP_HostName() 
	{
		return get_ValueAsString(COLUMNNAME_SFTP_HostName);
	}

	@Override
	public void setSFTP_Password (final java.lang.String SFTP_Password)
	{
		set_Value (COLUMNNAME_SFTP_Password, SFTP_Password);
	}

	@Override
	public java.lang.String getSFTP_Password() 
	{
		return get_ValueAsString(COLUMNNAME_SFTP_Password);
	}

	@Override
	public void setSFTP_Port (final java.lang.String SFTP_Port)
	{
		set_Value (COLUMNNAME_SFTP_Port, SFTP_Port);
	}

	@Override
	public java.lang.String getSFTP_Port() 
	{
		return get_ValueAsString(COLUMNNAME_SFTP_Port);
	}

	@Override
	public void setSFTP_TargetDirectory (final @Nullable java.lang.String SFTP_TargetDirectory)
	{
		set_Value (COLUMNNAME_SFTP_TargetDirectory, SFTP_TargetDirectory);
	}

	@Override
	public java.lang.String getSFTP_TargetDirectory() 
	{
		return get_ValueAsString(COLUMNNAME_SFTP_TargetDirectory);
	}

	@Override
	public void setSFTP_Username (final java.lang.String SFTP_Username)
	{
		set_Value (COLUMNNAME_SFTP_Username, SFTP_Username);
	}

	@Override
	public java.lang.String getSFTP_Username() 
	{
		return get_ValueAsString(COLUMNNAME_SFTP_Username);
	}
}