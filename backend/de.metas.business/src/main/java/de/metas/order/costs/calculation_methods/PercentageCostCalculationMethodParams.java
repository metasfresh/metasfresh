package de.metas.order.costs.calculation_methods;

import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

@Value
public class PercentageCostCalculationMethodParams implements CostCalculationMethodParams
{
	@NonNull Percent percentage;

	@Builder
	private PercentageCostCalculationMethodParams(
			@NonNull final Percent percentage)
	{
		if (percentage.signum() <= 0)
		{
			throw new AdempiereException("Percentage of Amount shall be positive");
		}
		this.percentage = percentage;
	}
}
