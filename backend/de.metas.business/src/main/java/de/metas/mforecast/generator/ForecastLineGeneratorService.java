/*
 * #%L
 * de.metas.business
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

package de.metas.mforecast.generator;

import de.metas.common.util.CoalesceUtil;
import de.metas.mforecast.impl.ForecastId;
import de.metas.product.IProductBL;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ForecastLineGeneratorService
{
	@NonNull private final ForecastLineGeneratorRepository repository;
	@NonNull private final ForecastCalculationStrategyFactory strategyFactory;
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);

	public ForecastLineGeneratorService(
			@NonNull final ForecastLineGeneratorRepository repository,
			@NonNull final ForecastCalculationStrategyFactory strategyFactory)
	{
		this.repository = repository;
		this.strategyFactory = strategyFactory;
	}

	public int generateLines(@NonNull final ForecastId forecastId, @NonNull final ForecastGeneratorRequest request)
	{
		final GeneratorForecast forecast = repository.getById(forecastId);

		if (request.isDeleteExistingLines())
		{
			final int deleted = repository.deleteExistingLines(forecastId);
			Loggables.addLog("Deleted {} existing forecast lines", deleted);
		}

		final List<ProductPlanningForForecast> products = repository.loadEligibleProducts(
				forecast.getOrgId(), request, forecast.getReferenceDate(), forecast.getWarehouseId());
		Loggables.addLog("Found {} eligible products", products.size());

		int created = 0;
		int skippedNoMethod = 0;
		int skippedZeroQty = 0;

		for (final ProductPlanningForForecast pp : products)
		{
			final ForecastCalculationMethod method = request.getCalculationMethodOverride()
					.orElse(pp.getForecastCalculationMethod());

			if (method == null)
			{
				skippedNoMethod++;
				continue;
			}

			final ForecastPrecisionUnit precisionUnit = CoalesceUtil.coalesceNotNull(
					pp.getForecastPrecisionUnit(), ForecastPrecisionUnit.WEEK);

			final int horizonUnits = computeForecastHorizon(pp, precisionUnit);
			if (horizonUnits <= 0)
			{
				skippedNoMethod++;
				continue;
			}

			final ForecastCalculationStrategy strategy = strategyFactory.getStrategy(method);

			final ForecastCalculationContext ctx = ForecastCalculationContext.builder()
					.productId(pp.getProductId())
					.referenceDate(forecast.getReferenceDate())
					.precisionUnit(precisionUnit)
					.forecastHorizonInPrecisionUnits(horizonUnits)
					.orgId(forecast.getOrgId())
					.build();

			final BigDecimal forecastQty = strategy.computeForecastQty(ctx);

			if (forecastQty.signum() <= 0)
			{
				skippedZeroQty++;
				continue;
			}

			final GeneratedForecastLine line = GeneratedForecastLine.builder()
					.productId(pp.getProductId())
					.warehouseId(pp.getWarehouseId())
					.attributeSetInstanceId(pp.getAttributeSetInstanceId())
					.uomId(productBL.getStockUOMId(pp.getProductId()))
					.qty(forecastQty)
					.qtyCalculated(forecastQty)
					.datePromised(forecast.getReferenceDate())
					.build();

			repository.saveForecastLine(forecastId, line);
			created++;
		}

		Loggables.addLog("Created {} forecast lines (skipped: {} no method, {} zero qty)",
				created, skippedNoMethod, skippedZeroQty);
		return created;
	}

	private int computeForecastHorizon(
			@NonNull final ProductPlanningForForecast pp,
			@NonNull final ForecastPrecisionUnit precisionUnit)
	{
		final int frequency;
		if (pp.getForecastFrequency() != null && pp.getForecastFrequency() > 0)
		{
			frequency = pp.getForecastFrequency();
		}
		else if (pp.getDeliveryTimePromised() != null && pp.getDeliveryTimePromised().signum() > 0)
		{
			frequency = daysToUnits(pp.getDeliveryTimePromised().intValue(), precisionUnit);
		}
		else
		{
			frequency = 1;
		}

		final int buffer = pp.getForecastBufferTime() != null ? pp.getForecastBufferTime() : 0;
		return frequency + buffer;
	}

	private static int daysToUnits(final int days, @NonNull final ForecastPrecisionUnit unit)
	{
		switch (unit)
		{
			case WEEK:
				return Math.max(1, (int) Math.ceil(days / 7.0));
			case MONTH:
				return Math.max(1, (int) Math.ceil(days / 30.0));
			default:
				throw new AdempiereException("Unknown precision unit: " + unit);
		}
	}
}
