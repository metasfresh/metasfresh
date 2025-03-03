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

import com.google.common.collect.ImmutableSet;
import de.metas.mforecast.ForecastRequest;
import de.metas.mforecast.IForecastDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;

import java.util.List;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class ForecastDAO implements IForecastDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	@NonNull
	public Stream<I_M_Forecast> streamRecordsByIds(@NonNull final ImmutableSet<ForecastId> ids)
	{
		return queryBL.createQueryBuilder(I_M_Forecast.class)
				.addInArrayFilter(I_M_ForecastLine.COLUMNNAME_M_Forecast_ID, ids)
				.create()
				.stream(I_M_Forecast.class);
	}

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
	public I_M_ForecastLine getForecastLineById(final int forecastLineRecordId)
	{
		return InterfaceWrapperHelper.load(forecastLineRecordId, I_M_ForecastLine.class);
	}

	@Override
	@NonNull
	public ForecastId createForecast(@NonNull final ForecastRequest request)
	{
		final I_M_Forecast forecastRecord = InterfaceWrapperHelper.newInstance(I_M_Forecast.class);

		forecastRecord.setM_Warehouse_ID(request.getWarehouseId().getRepoId());
		forecastRecord.setDatePromised(request.getDatePromised());
		forecastRecord.setName(request.getName());

		saveRecord(forecastRecord);

		request.getForecastLineRequests()
				.forEach(lineRequest -> createForecastLine(forecastRecord, lineRequest));

		return ForecastId.ofRepoId(forecastRecord.getM_Forecast_ID());
	}

	private void createForecastLine(
			@NonNull final I_M_Forecast forecastRecord,
			@NonNull final ForecastRequest.ForecastLineRequest request)
	{
		final I_M_ForecastLine forecastLineRecord = InterfaceWrapperHelper.newInstance(I_M_ForecastLine.class);

		forecastLineRecord.setM_Forecast_ID(forecastRecord.getM_Forecast_ID());
		forecastLineRecord.setM_Warehouse_ID(forecastRecord.getM_Warehouse_ID());
		forecastLineRecord.setQty(request.getQuantity().toBigDecimal());
		forecastLineRecord.setC_UOM_ID(request.getQuantity().getUomId().getRepoId());
		forecastLineRecord.setM_Product_ID(request.getProductId().getRepoId());

		saveRecord(forecastLineRecord);
	}
}
