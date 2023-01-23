package de.metas.costrevaluation;

import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailId;
import de.metas.costing.CostSegmentAndElement;
import de.metas.money.CurrencyId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class CostRevaluationDetailCreateRequest
{
	@NonNull CostRevaluationLineId lineId;
	@NonNull SeqNo seqNo;
	@NonNull CostRevaluationDetailType type;
	@NonNull CostSegmentAndElement costSegmentAndElement;

	@NonNull Quantity qty;
	@NonNull CostAmount oldCostPrice;
	@NonNull CostAmount newCostPrice;
	@NonNull CostAmount oldAmount;
	@NonNull CostAmount newAmount;
	@NonNull CostAmount deltaAmount;

	@Nullable CostDetailId costDetailId;

	public CurrencyId getCurrencyId()
	{
		return CostAmount.getCommonCurrencyIdOfAll(oldCostPrice, newCostPrice, oldAmount, newAmount);
	}
}
