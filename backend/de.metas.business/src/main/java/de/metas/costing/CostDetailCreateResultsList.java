package de.metas.costing;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.costing.methods.CostAmountAndQtyDetailed;
import de.metas.costing.methods.CostAmountDetailed;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
public class CostDetailCreateResultsList
{
	private final CostSegment costSegment;
	private final ImmutableList<CostDetailCreateResult> list;

	private CostDetailCreateResultsList(@NonNull final List<CostDetailCreateResult> list)
	{
		Check.assumeNotEmpty(list, "list is not empty");
		this.costSegment = CollectionUtils.extractSingleElement(list, CostDetailCreateResult::getCostSegment);
		this.list = ImmutableList.copyOf(list);
	}

	public static CostDetailCreateResultsList of(@NonNull final List<CostDetailCreateResult> list)
	{
		return new CostDetailCreateResultsList(list);
	}

	public AggregatedCostAmount toAggregatedCostAmount()
	{
		final Map<CostElement, CostAmountDetailed> amountsByCostElement = list.stream()
				.collect(Collectors.toMap(
						CostDetailCreateResult::getCostElement, // keyMapper
						CostDetailCreateResult::getAmt, // valueMapper
						CostAmountDetailed::add)); // mergeFunction

		return AggregatedCostAmount.builder()
				.costSegment(costSegment)
				.amounts(amountsByCostElement)
				.build();
	}

	public CostAmount getMainAmountToPost(@NonNull final AcctSchema as)
	{
		return getTotalAmountToPost(as).getMainAmt();
	}

	public CostAmountDetailed getTotalAmountToPost(@NonNull final AcctSchema as)
	{
		return toAggregatedCostAmount().getTotalAmountToPost(as);
	}

	public CostAmountAndQtyDetailed getTotalAmountAndQtyToPost(@NonNull final AcctSchema as)
	{
		return list.stream()
				.filter(detail -> detail.getCostElement().isAccountable(as.getCosting()))
				.map(CostDetailCreateResult::getAmtAndQty)
				.reduce(CostAmountAndQtyDetailed::add)
				.orElseThrow();
	}
}
