package de.metas.material.interceptor;

import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.forecast.ForecastCreatedEvent;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.mm.attributes.asi_aware.product.ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2026 metas GmbH
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
@ExtendWith(AdempiereTestWatcher.class)
public class M_ForecastEventCreatorTest
{
	private M_ForecastEventCreator forecastEventCreator;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final ModelProductDescriptorExtractor productDescriptorFactory = new ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory();
		forecastEventCreator = new M_ForecastEventCreator(productDescriptorFactory);
	}

	@Test
	public void createEventWithLinesAndTiming_budgetForecast_returnsEmpty()
	{
		final I_M_Forecast forecastModel = newInstance(I_M_Forecast.class);
		forecastModel.setIsBudgetForecast(true);
		save(forecastModel);

		// forecast HAS a line, so absent the IsBudgetForecast guard, buildForecast() would NOT
		// take the "no lines" early-return and would produce a present event; only the guard
		// makes this case return empty despite the line existing.
		final I_M_ForecastLine forecastLineRecord = ForecastLineTestFixture.newForecastLine(forecastModel, new BigDecimal("21"));
		save(forecastLineRecord);

		final Optional<ForecastCreatedEvent> result = forecastEventCreator.createEventWithLinesAndTiming(
				forecastModel,
				DocTimingType.AFTER_COMPLETE);

		assertThat(result).isEmpty();
	}

	@Test
	public void createEventWithLinesAndTiming_nonBudgetForecast_returnsPresent()
	{
		final I_M_Forecast forecastModel = newInstance(I_M_Forecast.class);
		forecastModel.setIsBudgetForecast(false);
		save(forecastModel);

		final I_M_ForecastLine forecastLineRecord = ForecastLineTestFixture.newForecastLine(forecastModel, new BigDecimal("21"));
		save(forecastLineRecord);

		final Optional<ForecastCreatedEvent> result = forecastEventCreator.createEventWithLinesAndTiming(
				forecastModel,
				DocTimingType.AFTER_COMPLETE);

		assertThat(result).isPresent();
	}
}
