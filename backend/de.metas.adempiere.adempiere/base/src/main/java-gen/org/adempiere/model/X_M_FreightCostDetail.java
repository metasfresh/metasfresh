/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
package org.adempiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_FreightCostDetail
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_M_FreightCostDetail extends org.compiere.model.PO implements I_M_FreightCostDetail, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = 1629601490L;

    /** Standard Constructor */
    public X_M_FreightCostDetail (final Properties ctx, final int M_FreightCostDetail_ID, @Nullable final String trxName)
    {
      super (ctx, M_FreightCostDetail_ID, trxName);
    }

    /** Load Constructor */
    public X_M_FreightCostDetail (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_CountryArea getC_CountryArea()
	{
		return get_ValueAsPO(COLUMNNAME_C_CountryArea_ID, org.compiere.model.I_C_CountryArea.class);
	}

	@Override
	public void setC_CountryArea(final org.compiere.model.I_C_CountryArea C_CountryArea)
	{
		set_ValueFromPO(COLUMNNAME_C_CountryArea_ID, org.compiere.model.I_C_CountryArea.class, C_CountryArea);
	}

	@Override
	public void setC_CountryArea_ID (final int C_CountryArea_ID)
	{
		if (C_CountryArea_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_CountryArea_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_CountryArea_ID, C_CountryArea_ID);
	}

	@Override
	public int getC_CountryArea_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_CountryArea_ID);
	}

	@Override
	public org.compiere.model.I_C_Country getC_Country()
	{
		return get_ValueAsPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_Country(final org.compiere.model.I_C_Country C_Country)
	{
		set_ValueFromPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class, C_Country);
	}

	@Override
	public void setC_Country_ID (final int C_Country_ID)
	{
		if (C_Country_ID < 1)
			set_Value (COLUMNNAME_C_Country_ID, null);
		else
			set_Value (COLUMNNAME_C_Country_ID, C_Country_ID);
	}

	@Override
	public int getC_Country_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Country_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1)
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
	}

	@Override
	public void setFreightAmt (final BigDecimal FreightAmt)
	{
		set_Value (COLUMNNAME_FreightAmt, FreightAmt);
	}

	@Override
	public BigDecimal getFreightAmt()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FreightAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setM_FreightCostDetail_ID (final int M_FreightCostDetail_ID)
	{
		if (M_FreightCostDetail_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_FreightCostDetail_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_FreightCostDetail_ID, M_FreightCostDetail_ID);
	}

	@Override
	public int getM_FreightCostDetail_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_FreightCostDetail_ID);
	}

	@Override
	public org.adempiere.model.I_M_FreightCostShipper getM_FreightCostShipper()
	{
		return get_ValueAsPO(COLUMNNAME_M_FreightCostShipper_ID, org.adempiere.model.I_M_FreightCostShipper.class);
	}

	@Override
	public void setM_FreightCostShipper(final org.adempiere.model.I_M_FreightCostShipper M_FreightCostShipper)
	{
		set_ValueFromPO(COLUMNNAME_M_FreightCostShipper_ID, org.adempiere.model.I_M_FreightCostShipper.class, M_FreightCostShipper);
	}

	@Override
	public void setM_FreightCostShipper_ID (final int M_FreightCostShipper_ID)
	{
		if (M_FreightCostShipper_ID < 1)
			set_ValueNoCheck (COLUMNNAME_M_FreightCostShipper_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_M_FreightCostShipper_ID, M_FreightCostShipper_ID);
	}

	@Override
	public int getM_FreightCostShipper_ID()
	{
		return get_ValueAsInt(COLUMNNAME_M_FreightCostShipper_ID);
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

	@Override
	public void setShipmentValueAmt (final BigDecimal ShipmentValueAmt)
	{
		set_Value (COLUMNNAME_ShipmentValueAmt, ShipmentValueAmt);
	}

	@Override
	public BigDecimal getShipmentValueAmt()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ShipmentValueAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}