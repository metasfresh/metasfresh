package de.metas.order.costs.calculation_methods;

import de.metas.money.Money;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

@Value
public class FixedAmountCostCalculationMethodParams implements CostCalculationMethodParams
{
	@NonNull Money fixedAmount;

	@Builder
	private FixedAmountCostCalculationMethodParams(
			@NonNull final Money fixedAmount)
	{
		if (fixedAmount.signum() <= 0)
		{
			throw new AdempiereException("Fixed amount shall be positive");
		}

		this.fixedAmount = fixedAmount;
	}
}
