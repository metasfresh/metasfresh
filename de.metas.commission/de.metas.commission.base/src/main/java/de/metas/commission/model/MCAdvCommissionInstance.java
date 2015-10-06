package de.metas.commission.model;

/*
 * #%L
 * de.metas.commission.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.ResultSet;
import java.util.Properties;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Provisionsberechnung_%282009_0023_G106%29'>(2009 0023 G106)</a>"
 */
public class MCAdvCommissionInstance extends X_C_AdvCommissionInstance
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4536112949440589629L;

	public MCAdvCommissionInstance(final Properties ctx, final int C_AdvCommissionInstance_ID, final String trxName)
	{
		super(ctx, C_AdvCommissionInstance_ID, trxName);
	}

	public MCAdvCommissionInstance(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();

		sb.append("MAdvCommissionInstance[Id=");
		sb.append(get_ID());
		sb.append(", Name=");
		sb.append(getName());
		sb.append(", LevelHi=");
		sb.append(getLevelHierarchy());
		sb.append(", LevelFo=");
		sb.append(getLevelForecast());
		sb.append(", LevelCa=");
		sb.append(getLevelCalculation());
		sb.append(", C_Sponsor_Customer_ID=");
		sb.append(getC_Sponsor_Customer_ID());
		sb.append(", C_Sponsor_SalesRep_ID=");
		sb.append(getC_Sponsor_SalesRep_ID());
		sb.append(", C_AdvCommissionTerm_ID=");
		sb.append(getC_AdvCommissionTerm_ID());
		sb.append(", AD_Table_ID=");
		sb.append(getAD_Table_ID());
		sb.append(", Record_ID=");
		sb.append(getRecord_ID());
		sb.append("]");

		return sb.toString();
	}
}
