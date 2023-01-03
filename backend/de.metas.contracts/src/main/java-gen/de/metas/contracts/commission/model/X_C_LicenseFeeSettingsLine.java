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

/** Generated Model for C_LicenseFeeSettingsLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_LicenseFeeSettingsLine extends org.compiere.model.PO implements I_C_LicenseFeeSettingsLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -694923129L;

    /** Standard Constructor */
    public X_C_LicenseFeeSettingsLine (final Properties ctx, final int C_LicenseFeeSettingsLine_ID, @Nullable final String trxName)
    {
      super (ctx, C_LicenseFeeSettingsLine_ID, trxName);
    }

    /** Load Constructor */
    public X_C_LicenseFeeSettingsLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_BP_Group getC_BP_Group_Match()
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_Group_Match_ID, org.compiere.model.I_C_BP_Group.class);
	}

	@Override
	public void setC_BP_Group_Match(final org.compiere.model.I_C_BP_Group C_BP_Group_Match)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_Group_Match_ID, org.compiere.model.I_C_BP_Group.class, C_BP_Group_Match);
	}

	@Override
	public void setC_BP_Group_Match_ID (final int C_BP_Group_Match_ID)
	{
		if (C_BP_Group_Match_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Group_Match_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Group_Match_ID, C_BP_Group_Match_ID);
	}

	@Override
	public int getC_BP_Group_Match_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BP_Group_Match_ID);
	}

	@Override
	public de.metas.contracts.commission.model.I_C_LicenseFeeSettings getC_LicenseFeeSettings()
	{
		return get_ValueAsPO(COLUMNNAME_C_LicenseFeeSettings_ID, de.metas.contracts.commission.model.I_C_LicenseFeeSettings.class);
	}

	@Override
	public void setC_LicenseFeeSettings(final de.metas.contracts.commission.model.I_C_LicenseFeeSettings C_LicenseFeeSettings)
	{
		set_ValueFromPO(COLUMNNAME_C_LicenseFeeSettings_ID, de.metas.contracts.commission.model.I_C_LicenseFeeSettings.class, C_LicenseFeeSettings);
	}

	@Override
	public void setC_LicenseFeeSettings_ID (final int C_LicenseFeeSettings_ID)
	{
		if (C_LicenseFeeSettings_ID < 1) 
			set_Value (COLUMNNAME_C_LicenseFeeSettings_ID, null);
		else 
			set_Value (COLUMNNAME_C_LicenseFeeSettings_ID, C_LicenseFeeSettings_ID);
	}

	@Override
	public int getC_LicenseFeeSettings_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_LicenseFeeSettings_ID);
	}

	@Override
	public void setC_LicenseFeeSettingsLine_ID (final int C_LicenseFeeSettingsLine_ID)
	{
		if (C_LicenseFeeSettingsLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_LicenseFeeSettingsLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_LicenseFeeSettingsLine_ID, C_LicenseFeeSettingsLine_ID);
	}

	@Override
	public int getC_LicenseFeeSettingsLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_LicenseFeeSettingsLine_ID);
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