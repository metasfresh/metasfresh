/*
 * #%L
 * de.metas.externalsystem
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
package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for ExternalSystem_Config_ProCareManagement_TaxCategory
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_ExternalSystem_Config_ProCareManagement_TaxCategory extends org.compiere.model.PO implements I_ExternalSystem_Config_ProCareManagement_TaxCategory, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1374148568L;

    /** Standard Constructor */
    public X_ExternalSystem_Config_ProCareManagement_TaxCategory (final Properties ctx, final int ExternalSystem_Config_ProCareManagement_TaxCategory_ID, @Nullable final String trxName)
    {
      super (ctx, ExternalSystem_Config_ProCareManagement_TaxCategory_ID, trxName);
    }

    /** Load Constructor */
    public X_ExternalSystem_Config_ProCareManagement_TaxCategory (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_TaxCategory_ID (final int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1) 
			set_Value (COLUMNNAME_C_TaxCategory_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxCategory_ID, C_TaxCategory_ID);
	}

	@Override
	public int getC_TaxCategory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_TaxCategory_ID);
	}

	@Override
	public I_ExternalSystem_Config_ProCareManagement getExternalSystem_Config_ProCareManagement()
	{
		return get_ValueAsPO(COLUMNNAME_ExternalSystem_Config_ProCareManagement_ID, I_ExternalSystem_Config_ProCareManagement.class);
	}

	@Override
	public void setExternalSystem_Config_ProCareManagement(final I_ExternalSystem_Config_ProCareManagement ExternalSystem_Config_ProCareManagement)
	{
		set_ValueFromPO(COLUMNNAME_ExternalSystem_Config_ProCareManagement_ID, I_ExternalSystem_Config_ProCareManagement.class, ExternalSystem_Config_ProCareManagement);
	}

	@Override
	public void setExternalSystem_Config_ProCareManagement_ID (final int ExternalSystem_Config_ProCareManagement_ID)
	{
		if (ExternalSystem_Config_ProCareManagement_ID < 1) 
			set_Value (COLUMNNAME_ExternalSystem_Config_ProCareManagement_ID, null);
		else 
			set_Value (COLUMNNAME_ExternalSystem_Config_ProCareManagement_ID, ExternalSystem_Config_ProCareManagement_ID);
	}

	@Override
	public int getExternalSystem_Config_ProCareManagement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ProCareManagement_ID);
	}

	@Override
	public void setExternalSystem_Config_ProCareManagement_TaxCategory_ID (final int ExternalSystem_Config_ProCareManagement_TaxCategory_ID)
	{
		if (ExternalSystem_Config_ProCareManagement_TaxCategory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ProCareManagement_TaxCategory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ExternalSystem_Config_ProCareManagement_TaxCategory_ID, ExternalSystem_Config_ProCareManagement_TaxCategory_ID);
	}

	@Override
	public int getExternalSystem_Config_ProCareManagement_TaxCategory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_ExternalSystem_Config_ProCareManagement_TaxCategory_ID);
	}

	@Override
	public void setTaxRates (final String TaxRates)
	{
		set_Value (COLUMNNAME_TaxRates, TaxRates);
	}

	@Override
	public String getTaxRates() 
	{
		return get_ValueAsString(COLUMNNAME_TaxRates);
	}
}