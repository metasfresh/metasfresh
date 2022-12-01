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

/** Generated Model for ExternalSystem_Config_LeichMehl_ProductMapping
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_LeichMehl_ProductMapping extends org.compiere.model.PO implements I_ExternalSystem_Config_LeichMehl_ProductMapping, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1655928919L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_LeichMehl_ProductMapping (final Properties ctx, final int ExternalSystem_Config_LeichMehl_ProductMapping_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_LeichMehl_ProductMapping_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_LeichMehl_ProductMapping (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
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
	public void setExternalSystem_Config_LeichMehl_ProductMapping_ID (final int ExternalSystem_Config_LeichMehl_ProductMapping_ID)
	{
		if (ExternalSystem_Config_LeichMehl_ProductMapping_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_LeichMehl_ProductMapping_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_LeichMehl_ProductMapping_ID, ExternalSystem_Config_LeichMehl_ProductMapping_ID);
	}

	@Override
	public int getExternalSystem_Config_LeichMehl_ProductMapping_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_LeichMehl_ProductMapping_ID);
	}

	@Override
	public void setM_Product_Category_ID (final int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, M_Product_Category_ID);
	}

	@Override
	public int getM_Product_Category_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_Category_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setPLU_File (final java.lang.String PLU_File)
	{
		set_Value (COLUMNNAME_PLU_File, PLU_File);
	}

	@Override
	public java.lang.String getPLU_File() 
	{
		return get_ValueAsString(COLUMNNAME_PLU_File);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}
}