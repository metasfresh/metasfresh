/*
 * #%L
 * de.metas.contracts
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
package de.metas.contracts.commission.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_MediatedCommissionSettingsLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_MediatedCommissionSettingsLine extends org.compiere.model.PO implements I_C_MediatedCommissionSettingsLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -278369472L;

    /** Standard Constructor */
    public X_C_MediatedCommissionSettingsLine (final Properties ctx, final int C_MediatedCommissionSettingsLine_ID, @Nullable final String trxName)
    {
      super (ctx, C_MediatedCommissionSettingsLine_ID, trxName);
    }

    /** Load Constructor */
    public X_C_MediatedCommissionSettingsLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public I_C_MediatedCommissionSettings getC_MediatedCommissionSettings()
	{
		return get_ValueAsPO(COLUMNNAME_C_MediatedCommissionSettings_ID, I_C_MediatedCommissionSettings.class);
	}

	@Override
	public void setC_MediatedCommissionSettings(final I_C_MediatedCommissionSettings C_MediatedCommissionSettings)
	{
		set_ValueFromPO(COLUMNNAME_C_MediatedCommissionSettings_ID, I_C_MediatedCommissionSettings.class, C_MediatedCommissionSettings);
	}

	@Override
	public void setC_MediatedCommissionSettings_ID (final int C_MediatedCommissionSettings_ID)
	{
		if (C_MediatedCommissionSettings_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_MediatedCommissionSettings_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_MediatedCommissionSettings_ID, C_MediatedCommissionSettings_ID);
	}

	@Override
	public int getC_MediatedCommissionSettings_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_MediatedCommissionSettings_ID);
	}

	@Override
	public void setC_MediatedCommissionSettingsLine_ID (final int C_MediatedCommissionSettingsLine_ID)
	{
		if (C_MediatedCommissionSettingsLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_MediatedCommissionSettingsLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_MediatedCommissionSettingsLine_ID, C_MediatedCommissionSettingsLine_ID);
	}

	@Override
	public int getC_MediatedCommissionSettingsLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_MediatedCommissionSettingsLine_ID);
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
	public void setPercentOfBasePoints (final BigDecimal PercentOfBasePoints)
	{
		set_Value (COLUMNNAME_PercentOfBasePoints, PercentOfBasePoints);
	}

	@Override
	public BigDecimal getPercentOfBasePoints() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PercentOfBasePoints);
		return bd != null ? bd : BigDecimal.ZERO;
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