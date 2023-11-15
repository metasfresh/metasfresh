/*
 * #%L
 * de.metas.contracts
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
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ModCntr_Log_Status
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ModCntr_Log_Status extends org.compiere.model.PO implements I_ModCntr_Log_Status, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2005233058L;

    /** Standard Constructor */
    public X_ModCntr_Log_Status (final Properties ctx, final int ModCntr_Log_Status_ID, @Nullable final String trxName)
    {
      super (ctx, ModCntr_Log_Status_ID, trxName);
    }

    /** Load Constructor */
    public X_ModCntr_Log_Status (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setC_Queue_WorkPackage_ID (final int C_Queue_WorkPackage_ID)
	{
		if (C_Queue_WorkPackage_ID < 1) 
			set_Value (COLUMNNAME_C_Queue_WorkPackage_ID, null);
		else 
			set_Value (COLUMNNAME_C_Queue_WorkPackage_ID, C_Queue_WorkPackage_ID);
	}

	@Override
	public int getC_Queue_WorkPackage_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Queue_WorkPackage_ID);
	}

	@Override
	public void setModCntr_Log_Status_ID (final int ModCntr_Log_Status_ID)
	{
		if (ModCntr_Log_Status_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ModCntr_Log_Status_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ModCntr_Log_Status_ID, ModCntr_Log_Status_ID);
	}

	@Override
	public int getModCntr_Log_Status_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ModCntr_Log_Status_ID);
	}

	/** 
	 * ProcessingStatus AD_Reference_ID=541817
	 * Reference name: ModularLogsProcessingStatus
	 */
	public static final int PROCESSINGSTATUS_AD_Reference_ID=541817;
	/** Enqueued = EN */
	public static final String PROCESSINGSTATUS_Enqueued = "EN";
	/** Errored = ER */
	public static final String PROCESSINGSTATUS_Errored = "ER";
	/** SuccessfullyProcessed = SP */
	public static final String PROCESSINGSTATUS_SuccessfullyProcessed = "SP";
	@Override
	public void setProcessingStatus (final java.lang.String ProcessingStatus)
	{
		set_Value (COLUMNNAME_ProcessingStatus, ProcessingStatus);
	}

	@Override
	public java.lang.String getProcessingStatus() 
	{
		return get_ValueAsString(COLUMNNAME_ProcessingStatus);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}
}