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
