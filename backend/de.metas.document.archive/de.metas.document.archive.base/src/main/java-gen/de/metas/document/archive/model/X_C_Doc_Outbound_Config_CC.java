/*
 * #%L
 * de.metas.document.archive.base
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
package de.metas.document.archive.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_Doc_Outbound_Config_CC
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Doc_Outbound_Config_CC extends org.compiere.model.PO implements I_C_Doc_Outbound_Config_CC, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = -1546880780L;

	/** Standard Constructor */
	public X_C_Doc_Outbound_Config_CC (final Properties ctx, final int C_Doc_Outbound_Config_CC_ID, @Nullable final String trxName)
	{
		super (ctx, C_Doc_Outbound_Config_CC_ID, trxName);
	}

	/** Load Constructor */
	public X_C_Doc_Outbound_Config_CC (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_PrintFormat getAD_PrintFormat()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class);
	}

	@Override
	public void setAD_PrintFormat(final org.compiere.model.I_AD_PrintFormat AD_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class, AD_PrintFormat);
	}

	@Override
	public void setAD_PrintFormat_ID (final int AD_PrintFormat_ID)
	{
		if (AD_PrintFormat_ID < 1)
			set_Value (COLUMNNAME_AD_PrintFormat_ID, null);
		else
			set_Value (COLUMNNAME_AD_PrintFormat_ID, AD_PrintFormat_ID);
	}

	@Override
	public int getAD_PrintFormat_ID()
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrintFormat_ID);
	}

	@Override
	public org.compiere.model.I_AD_Column getBPartner_ColumnName()
	{
		return get_ValueAsPO(COLUMNNAME_BPartner_ColumnName_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setBPartner_ColumnName(final org.compiere.model.I_AD_Column BPartner_ColumnName)
	{
		set_ValueFromPO(COLUMNNAME_BPartner_ColumnName_ID, org.compiere.model.I_AD_Column.class, BPartner_ColumnName);
	}

	@Override
	public void setBPartner_ColumnName_ID (final int BPartner_ColumnName_ID)
	{
		if (BPartner_ColumnName_ID < 1)
			set_Value (COLUMNNAME_BPartner_ColumnName_ID, null);
		else
			set_Value (COLUMNNAME_BPartner_ColumnName_ID, BPartner_ColumnName_ID);
	}

	@Override
	public int getBPartner_ColumnName_ID()
	{
		return get_ValueAsInt(COLUMNNAME_BPartner_ColumnName_ID);
	}

	@Override
	public void setC_Doc_Outbound_Config_CC_ID (final int C_Doc_Outbound_Config_CC_ID)
	{
		if (C_Doc_Outbound_Config_CC_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Doc_Outbound_Config_CC_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Doc_Outbound_Config_CC_ID, C_Doc_Outbound_Config_CC_ID);
	}

	@Override
	public int getC_Doc_Outbound_Config_CC_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Doc_Outbound_Config_CC_ID);
	}

	@Override
	public de.metas.document.archive.model.I_C_Doc_Outbound_Config getC_Doc_Outbound_Config()
	{
		return get_ValueAsPO(COLUMNNAME_C_Doc_Outbound_Config_ID, de.metas.document.archive.model.I_C_Doc_Outbound_Config.class);
	}

	@Override
	public void setC_Doc_Outbound_Config(final de.metas.document.archive.model.I_C_Doc_Outbound_Config C_Doc_Outbound_Config)
	{
		set_ValueFromPO(COLUMNNAME_C_Doc_Outbound_Config_ID, de.metas.document.archive.model.I_C_Doc_Outbound_Config.class, C_Doc_Outbound_Config);
	}

	@Override
	public void setC_Doc_Outbound_Config_ID (final int C_Doc_Outbound_Config_ID)
	{
		if (C_Doc_Outbound_Config_ID < 1)
			set_Value (COLUMNNAME_C_Doc_Outbound_Config_ID, null);
		else
			set_Value (COLUMNNAME_C_Doc_Outbound_Config_ID, C_Doc_Outbound_Config_ID);
	}

	@Override
	public int getC_Doc_Outbound_Config_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Doc_Outbound_Config_ID);
	}
}