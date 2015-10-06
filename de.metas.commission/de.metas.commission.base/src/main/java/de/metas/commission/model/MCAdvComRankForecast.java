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

import org.adempiere.util.proxy.Cached;
import org.compiere.model.Query;

import de.metas.adempiere.util.CacheCtx;

public class MCAdvComRankForecast extends X_C_AdvComRankForecast
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3609806335954437374L;

	public MCAdvComRankForecast(final Properties ctx, final int C_AdvComRankForecast_ID, final String trxName)
	{
		super(ctx, C_AdvComRankForecast_ID, trxName);
	}

	public MCAdvComRankForecast(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Cached
	public static final MCAdvComRankForecast retrieveForSponsor(
			final @CacheCtx Properties ctx,
			final I_C_Sponsor sponsor,
			final I_C_AdvComSystem comSystem,
			final String trxName)
	{
		final String wc = I_C_AdvComRankForecast.COLUMNNAME_C_Sponsor_ID + "=? AND " + I_C_AdvComRankForecast.COLUMNNAME_C_AdvComSystem_ID + "=?";

		return new Query(ctx, I_C_AdvComRankForecast.Table_Name, wc, trxName)
				.setParameters(sponsor.getC_Sponsor_ID(), comSystem.getC_AdvComSystem_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly();
	}
}
