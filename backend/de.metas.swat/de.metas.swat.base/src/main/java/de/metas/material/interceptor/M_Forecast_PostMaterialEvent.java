/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.material.interceptor;

import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.PostMaterialEventService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_Forecast.class)
@Component
public class M_Forecast_PostMaterialEvent
{
	private final M_ForecastEventCreator forecastEventCreator;
	private final PostMaterialEventService materialEventService;

	public M_Forecast_PostMaterialEvent(
			@NonNull final ModelProductDescriptorExtractor productDescriptorFactory,
			@NonNull final PostMaterialEventService materialEventService)
	{
		this.forecastEventCreator = new M_ForecastEventCreator(productDescriptorFactory);
		this.materialEventService = materialEventService;
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void fireForecastCreatedEventOnComplete(@NonNull final I_M_Forecast forecast, @NonNull final DocTimingType timing)
	{
		forecastEventCreator.createEventWithLinesAndTiming(forecast, timing)
				.ifPresent(materialEventService::enqueueEventAfterNextCommit);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REACTIVATE, ModelValidator.TIMING_AFTER_VOID })
	public void fireForecastDeletedEventOnReactivate(@NonNull final I_M_Forecast forecast, @NonNull final DocTimingType timing)
	{
		forecastEventCreator.createDeletedEvent(forecast, timing)
				.ifPresent(materialEventService::enqueueEventAfterNextCommit);
	}
}
