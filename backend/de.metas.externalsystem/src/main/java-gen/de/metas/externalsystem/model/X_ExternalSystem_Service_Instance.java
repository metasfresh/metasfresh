/*
 * #%L
 * de.metas.externalsystem
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
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Service_Instance
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Service_Instance extends org.compiere.model.PO implements I_ExternalSystem_Service_Instance, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 907964294L;

    /** Standard Constructor */
    public X_ExternalSystem_Service_Instance (final Properties ctx, final int ExternalSystem_Service_Instance_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Service_Instance_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Service_Instance (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * ExpectedStatus AD_Reference_ID=541502
	 * Reference name: ExpectedStatus
	 */
	public static final int EXPECTEDSTATUS_AD_Reference_ID=541502;
	/** Active = Active */
	public static final String EXPECTEDSTATUS_Active = "Active";
	/** Inactive = Inactive */
	public static final String EXPECTEDSTATUS_Inactive = "Inactive";
	/** Error  = Error  */
	public static final String EXPECTEDSTATUS_Error = "Error ";
	/** Down = Down */
	public static final String EXPECTEDSTATUS_Down = "Down";
	@Override
	public void setExpectedStatus (final java.lang.String ExpectedStatus)
	{
		set_Value (COLUMNNAME_ExpectedStatus, ExpectedStatus);
	}

	@Override
	public java.lang.String getExpectedStatus() 
	{
		return get_ValueAsString(COLUMNNAME_ExpectedStatus);
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
	public de.metas.externalsystem.model.I_ExternalSystem_Service getExternalSystem_Service()
	{
		return get_ValueAsPO(COLUMNNAME_ExternalSystem_Service_ID, de.metas.externalsystem.model.I_ExternalSystem_Service.class);
	}

	@Override
	public void setExternalSystem_Service(final de.metas.externalsystem.model.I_ExternalSystem_Service ExternalSystem_Service)
	{
		set_ValueFromPO(COLUMNNAME_ExternalSystem_Service_ID, de.metas.externalsystem.model.I_ExternalSystem_Service.class, ExternalSystem_Service);
	}

	@Override
	public void setExternalSystem_Service_ID (final int ExternalSystem_Service_ID)
	{
		if (ExternalSystem_Service_ID < 1) 
			set_Value (COLUMNNAME_ExternalSystem_Service_ID, null);
		else 
			set_Value (COLUMNNAME_ExternalSystem_Service_ID, ExternalSystem_Service_ID);
	}

	@Override
	public int getExternalSystem_Service_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Service_ID);
	}

	@Override
	public void setExternalSystem_Service_Instance_ID (final int ExternalSystem_Service_Instance_ID)
	{
		if (ExternalSystem_Service_Instance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Service_Instance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Service_Instance_ID, ExternalSystem_Service_Instance_ID);
	}

	@Override
	public int getExternalSystem_Service_Instance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Service_Instance_ID);
	}
}