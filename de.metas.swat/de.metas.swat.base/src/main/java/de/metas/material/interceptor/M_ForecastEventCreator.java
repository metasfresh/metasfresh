package de.metas.material.interceptor;

import java.util.List;

import org.adempiere.ad.modelvalidator.DocTimingType;
import org.compiere.Adempiere;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;

import com.google.common.base.Preconditions;

import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.forecast.Forecast;
import de.metas.material.event.forecast.Forecast.ForecastBuilder;
import de.metas.material.event.forecast.ForecastCreatedEvent;
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
	public ForecastCreatedEvent createEventWithLinesAndTiming(
			@NonNull final List<I_M_ForecastLine> forecastLineRecords,
			@NonNull final DocTimingType timing)
	{
		Preconditions.checkArgument(!forecastLineRecords.isEmpty(), "Param 'forecastLines' may not be empty; timing=%s", timing);

		final I_M_Forecast forecastRecord = forecastLineRecords.get(0).getM_Forecast();

		final ForecastBuilder forecastBuilder = Forecast.builder()
				.forecastId(forecastRecord.getM_Forecast_ID())
				.docStatus(timing.getDocStatus());

		for (final I_M_ForecastLine forecastLineRecord : forecastLineRecords)
		{
			forecastBuilder.forecastLine(createForecastLine(forecastLineRecord, forecastRecord));
		}

		final ForecastCreatedEvent forecastCreatedEvent = ForecastCreatedEvent
				.builder()
				.forecast(forecastBuilder.build())
				.eventDescriptor(EventDescriptor.createNew(forecastRecord))
				.build();

		return forecastCreatedEvent;
	}

	private ForecastLine createForecastLine(
			@NonNull final I_M_ForecastLine forecastLine,
			@NonNull final I_M_Forecast forecast)
	{
		final ModelProductDescriptorExtractor productDescriptorFactory = Adempiere.getBean(ModelProductDescriptorExtractor.class);
		final ProductDescriptor productDescriptor = productDescriptorFactory.createProductDescriptor(forecastLine);

		final int bPartnerId = forecastLine.getC_BPartner_ID() > 0 ? forecastLine.getC_BPartner_ID() : forecast.getC_BPartner_ID();

		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.date(forecastLine.getDatePromised())
				.productDescriptor(productDescriptor)
				.bPartnerId(bPartnerId)
				.warehouseId(forecastLine.getM_Warehouse_ID())
				.quantity(forecastLine.getQty())
				.build();

		return ForecastLine.builder()
				.forecastLineId(forecastLine.getM_ForecastLine_ID())
				.materialDescriptor(materialDescriptor)
				.build();
	}
}
