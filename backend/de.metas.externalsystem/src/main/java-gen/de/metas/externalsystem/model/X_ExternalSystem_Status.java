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

/** Generated Model for ExternalSystem_Status
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Status extends org.compiere.model.PO implements I_ExternalSystem_Status, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 825183235L;

    /** Standard Constructor */
    public X_ExternalSystem_Status (final Properties ctx, final int ExternalSystem_Status_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Status_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Status (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_PInstance getAD_PInstance()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance(final org.compiere.model.I_AD_PInstance AD_PInstance)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance);
	}

	@Override
	public void setAD_PInstance_ID (final int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_ID, AD_PInstance_ID);
	}

	@Override
	public int getAD_PInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PInstance_ID);
	}

	@Override
	public de.metas.externalsystem.model.I_ExternalSystem_Service_Instance getExternalSystem_Service_Instance()
	{
		return get_ValueAsPO(COLUMNNAME_ExternalSystem_Service_Instance_ID, de.metas.externalsystem.model.I_ExternalSystem_Service_Instance.class);
	}

	@Override
	public void setExternalSystem_Service_Instance(final de.metas.externalsystem.model.I_ExternalSystem_Service_Instance ExternalSystem_Service_Instance)
	{
		set_ValueFromPO(COLUMNNAME_ExternalSystem_Service_Instance_ID, de.metas.externalsystem.model.I_ExternalSystem_Service_Instance.class, ExternalSystem_Service_Instance);
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

	@Override
	public void setExternalSystem_Status_ID (final int ExternalSystem_Status_ID)
	{
		if (ExternalSystem_Status_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Status_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Status_ID, ExternalSystem_Status_ID);
	}

	@Override
	public int getExternalSystem_Status_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Status_ID);
	}

	@Override
	public void setExternalSystemMessage (final @Nullable java.lang.String ExternalSystemMessage)
	{
		set_Value (COLUMNNAME_ExternalSystemMessage, ExternalSystemMessage);
	}

	@Override
	public java.lang.String getExternalSystemMessage() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalSystemMessage);
	}

	/** 
	 * ExternalSystemStatus AD_Reference_ID=541502
	 * Reference name: ExpectedStatus
	 */
	public static final int EXTERNALSYSTEMSTATUS_AD_Reference_ID=541502;
	/** Active = Active */
	public static final String EXTERNALSYSTEMSTATUS_Active = "Active";
	/** Inactive = Inactive */
	public static final String EXTERNALSYSTEMSTATUS_Inactive = "Inactive";
	/** Error  = Error  */
	public static final String EXTERNALSYSTEMSTATUS_Error = "Error ";
	/** Down = Down */
	public static final String EXTERNALSYSTEMSTATUS_Down = "Down";
	@Override
	public void setExternalSystemStatus (final java.lang.String ExternalSystemStatus)
	{
		set_Value (COLUMNNAME_ExternalSystemStatus, ExternalSystemStatus);
	}

	@Override
	public java.lang.String getExternalSystemStatus() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalSystemStatus);
	}
}