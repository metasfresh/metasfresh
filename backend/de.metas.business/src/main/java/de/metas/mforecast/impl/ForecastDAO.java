package de.metas.mforecast.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;

import de.metas.mforecast.IForecastDAO;

public class ForecastDAO implements IForecastDAO

{
	@Override
	public List<I_M_ForecastLine> retrieveLines(final I_M_Forecast forecast)
	{
		final IQueryBuilder<I_M_ForecastLine> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_ForecastLine.class, forecast)
				.filter(new EqualsQueryFilter<I_M_ForecastLine>(I_M_ForecastLine.COLUMNNAME_M_Forecast_ID, forecast.getM_Forecast_ID()))
				.filterByClientId()
				.filter(ActiveRecordQueryFilter.getInstance(I_M_ForecastLine.class));
		queryBuilder.orderBy()
				.addColumn(I_M_ForecastLine.COLUMNNAME_M_ForecastLine_ID);

		return queryBuilder.create()
				.list(I_M_ForecastLine.class);
	}
}
