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

package de.metas.mforecast.process;

import de.metas.mforecast.generator.ForecastCalculationMethod;
import de.metas.mforecast.generator.ForecastGeneratorRequest;
import de.metas.mforecast.generator.ForecastLineGeneratorService;
import de.metas.mforecast.impl.ForecastId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

public class M_Forecast_GenerateLines extends JavaProcess implements IProcessPrecondition
{
	@NonNull private final ForecastLineGeneratorService generatorService = SpringContextHolder.instance.getBean(ForecastLineGeneratorService.class);

	@Param(parameterName = "M_Product_Category_ID")
	private int p_productCategoryId;

	@Param(parameterName = "M_Product_ID")
	private int p_productId;

	@Param(parameterName = "Forecast_CalculationMethod")
	private String p_calculationMethod;

	@Param(parameterName = "IsDeleteExistingLines")
	private boolean p_deleteExistingLines;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ForecastId forecastId = ForecastId.ofRepoId(getRecord_ID());

		final ForecastGeneratorRequest request = ForecastGeneratorRequest.builder()
				.productCategoryId(ProductCategoryId.ofRepoIdOrNull(p_productCategoryId))
				.productId(ProductId.ofRepoIdOrNull(p_productId))
				.calculationMethodOverride(ForecastCalculationMethod.ofNullableCode(p_calculationMethod))
				.deleteExistingLines(p_deleteExistingLines)
				.build();

		final int count = generatorService.generateLines(forecastId, request);
		return "@Created@ " + count;
	}
}
