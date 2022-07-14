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

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for LeichMehl_PluFile_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_LeichMehl_PluFile_Config extends org.compiere.model.PO implements I_LeichMehl_PluFile_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -622477195L;

    /** Standard Constructor */
    public X_LeichMehl_PluFile_Config (final Properties ctx, final int LeichMehl_PluFile_Config_ID, @Nullable final String trxName)
    {
      super (ctx, LeichMehl_PluFile_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_LeichMehl_PluFile_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl getExternalSystem_Config_LeichMehl()
	{
		return get_ValueAsPO(COLUMNNAME_ExternalSystem_Config_LeichMehl_ID, de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl.class);
	}

	@Override
	public void setExternalSystem_Config_LeichMehl(final de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl ExternalSystem_Config_LeichMehl)
	{
		set_ValueFromPO(COLUMNNAME_ExternalSystem_Config_LeichMehl_ID, de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl.class, ExternalSystem_Config_LeichMehl);
	}

	@Override
	public void setExternalSystem_Config_LeichMehl_ID (final int ExternalSystem_Config_LeichMehl_ID)
	{
		if (ExternalSystem_Config_LeichMehl_ID < 1) 
			set_Value (COLUMNNAME_ExternalSystem_Config_LeichMehl_ID, null);
		else 
			set_Value (COLUMNNAME_ExternalSystem_Config_LeichMehl_ID, ExternalSystem_Config_LeichMehl_ID);
	}

	@Override
	public int getExternalSystem_Config_LeichMehl_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_LeichMehl_ID);
	}

	@Override
	public void setLeichMehl_PluFile_Config_ID (final int LeichMehl_PluFile_Config_ID)
	{
		if (LeichMehl_PluFile_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_LeichMehl_PluFile_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_LeichMehl_PluFile_Config_ID, LeichMehl_PluFile_Config_ID);
	}

	@Override
	public int getLeichMehl_PluFile_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_LeichMehl_PluFile_Config_ID);
	}

	@Override
	public void setReplacement (final java.lang.String Replacement)
	{
		set_Value (COLUMNNAME_Replacement, Replacement);
	}

	@Override
	public java.lang.String getReplacement() 
	{
		return get_ValueAsString(COLUMNNAME_Replacement);
	}

	/** 
	 * ReplacementSource AD_Reference_ID=541598
	 * Reference name: ReplacementSourceList
	 */
	public static final int REPLACEMENTSOURCE_AD_Reference_ID=541598;
	/** Product = P */
	public static final String REPLACEMENTSOURCE_Product = "P";
	/** PPOrder = PP */
	public static final String REPLACEMENTSOURCE_PPOrder = "PP";
	/** BOMLine = BL */
	public static final String REPLACEMENTSOURCE_BOMLine = "BL";
	@Override
	public void setReplacementSource (final java.lang.String ReplacementSource)
	{
		set_Value (COLUMNNAME_ReplacementSource, ReplacementSource);
	}

	@Override
	public java.lang.String getReplacementSource() 
	{
		return get_ValueAsString(COLUMNNAME_ReplacementSource);
	}

	@Override
	public void setReplaceRegExp (final @Nullable java.lang.String ReplaceRegExp)
	{
		set_Value (COLUMNNAME_ReplaceRegExp, ReplaceRegExp);
	}

	@Override
	public java.lang.String getReplaceRegExp() 
	{
		return get_ValueAsString(COLUMNNAME_ReplaceRegExp);
	}

	@Override
	public void setTargetFieldName (final java.lang.String TargetFieldName)
	{
		set_Value (COLUMNNAME_TargetFieldName, TargetFieldName);
	}

	@Override
	public java.lang.String getTargetFieldName() 
	{
		return get_ValueAsString(COLUMNNAME_TargetFieldName);
	}

	@Override
	public void setTargetFieldType (final java.lang.String TargetFieldType)
	{
		set_Value (COLUMNNAME_TargetFieldType, TargetFieldType);
	}

	@Override
	public java.lang.String getTargetFieldType() 
	{
		return get_ValueAsString(COLUMNNAME_TargetFieldType);
	}
}