package de.metas.costing;

import com.google.common.collect.ImmutableList;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class CostsRevaluationResult
{
	@NonNull CurrentCostBeforeEvaluation currentCostBeforeEvaluation;
	@NonNull @Singular ImmutableList<CostDetailAdjustment> costDetailAdjustments;
	@NonNull CurrentCostAfterEvaluation currentCostAfterEvaluation;

	//
	//
	//

	@Value
	public static class CurrentCostBeforeEvaluation
	{
		@NonNull Quantity qty;
		@NonNull CostAmount costPriceOld;
		@NonNull CostAmount costPriceNew;

		@Builder
		private CurrentCostBeforeEvaluation(
				@NonNull final Quantity qty,
				@NonNull final CostAmount costPriceOld,
				@NonNull final CostAmount costPriceNew)
		{
			CostAmount.assertCurrencyMatching(costPriceOld, costPriceNew);

			this.qty = qty;
			this.costPriceOld = costPriceOld;
			this.costPriceNew = costPriceNew;
		}
	}

	@Value
	@Builder
	public static class CurrentCostAfterEvaluation
	{
		@NonNull Quantity qty;
		@NonNull CostAmount costPriceComputed;
	}

}
