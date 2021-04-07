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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import de.metas.interfaces.I_C_OrderLine;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_ForecastLine;

import de.metas.mforecast.IForecastDAO;
import de.metas.util.Check;
import de.metas.util.Services;

public class ForecastDAO implements IForecastDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public List<I_M_ForecastLine> retrieveLinesByForecastId(final int forecastId)
	{
		Check.assumeGreaterThanZero(forecastId, "forecastId");

		return queryBL.createQueryBuilder(I_M_ForecastLine.class)
				.addEqualsFilter(I_M_ForecastLine.COLUMNNAME_M_Forecast_ID, forecastId)
				.filter(ActiveRecordQueryFilter.getInstance(I_M_ForecastLine.class))
				.orderBy(I_M_ForecastLine.COLUMNNAME_M_ForecastLine_ID)
				.create()
				.list();
	}

	@Override
	public I_M_ForecastLine getForecastLineById (final int forecastLineRecordId)
	{
			return InterfaceWrapperHelper.load(forecastLineRecordId, I_M_ForecastLine.class);
	}
}
