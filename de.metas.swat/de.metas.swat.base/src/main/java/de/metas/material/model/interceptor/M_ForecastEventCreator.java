package de.metas.material.model.interceptor;

import java.util.List;

import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;

import com.google.common.base.Preconditions;

import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.forecast.Forecast;
import de.metas.material.event.forecast.Forecast.ForecastBuilder;
import de.metas.material.event.forecast.ForecastEvent;
import de.metas.material.event.forecast.ForecastLine;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@UtilityClass
public class M_ForecastEventCreator
{
	public ForecastEvent createEventWithLinesAndTiming(
			@NonNull final List<I_M_ForecastLine> forecastLines, 
			@NonNull final DocTimingType timing)
	{
		Preconditions.checkArgument(!forecastLines.isEmpty(), "Param 'forecastLines' may not be empty; timing=%s", timing);

		final I_M_Forecast forecastModel = forecastLines.get(0).getM_Forecast();

		final ForecastBuilder forecastBuilder = Forecast.builder()
				.forecastId(forecastModel.getM_Forecast_ID())
				.docStatus(timing.getDocStatus());

		for (final I_M_ForecastLine forecastLine : forecastLines)
		{
			forecastBuilder.forecastLine(createForecastLine(forecastLine));
		}

		final ForecastEvent forecastEvent = ForecastEvent
				.builder()
				.forecast(forecastBuilder.build())
				.eventDescr(EventDescr.createNew(forecastModel))
				.build();

		return forecastEvent;
	}

	private ForecastLine createForecastLine(@NonNull final I_M_ForecastLine forecastLine)
	{
		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.date(forecastLine.getDatePromised())
				.productId(forecastLine.getM_Product_ID())
				.warehouseId(forecastLine.getM_Warehouse_ID())
				.quantity(forecastLine.getQty())
				.build();

		return ForecastLine.builder()
				.forecastLineId(forecastLine.getM_ForecastLine_ID())
				.materialDescriptor(materialDescriptor)
				.reference(TableRecordReference.of(forecastLine))
				.build();
	}
}
