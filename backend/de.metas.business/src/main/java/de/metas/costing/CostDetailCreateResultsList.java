package de.metas.costing;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.costing.methods.CostAmountDetailed;
import de.metas.costing.methods.CostAmountType;
import de.metas.util.GuavaCollectors;
import de.metas.util.collections.CollectionUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class CostDetailCreateResultsList
{
	public static final CostDetailCreateResultsList EMPTY = new CostDetailCreateResultsList(ImmutableList.of());

	private final ImmutableList<CostDetailCreateResult> list;

	private CostDetailCreateResultsList(@NonNull final List<CostDetailCreateResult> list)
	{
		this.list = ImmutableList.copyOf(list);
	}

	public static CostDetailCreateResultsList ofList(@NonNull final List<CostDetailCreateResult> list)
	{
		if (list.isEmpty())
		{
			return EMPTY;
		}
		return new CostDetailCreateResultsList(list);
	}

	public static CostDetailCreateResultsList ofNullable(@Nullable final CostDetailCreateResult result)
	{
		return result != null ? of(result) : EMPTY;
	}

	public static CostDetailCreateResultsList of(@NonNull final CostDetailCreateResult result) {return ofList(ImmutableList.of(result));}

	public static Collector<CostDetailCreateResult, ?, CostDetailCreateResultsList> collect() {return GuavaCollectors.collectUsingListAccumulator(CostDetailCreateResultsList::ofList);}

	public Stream<CostDetailCreateResult> stream() {return list.stream();}

	public CostDetailCreateResult getSingleResult() {return CollectionUtils.singleElement(list);}

	public Optional<CostAmountAndQty> getAmtAndQtyToPost(@NonNull final CostAmountType type, @NonNull AcctSchema as)
	{
		return list.stream()
				.filter(result -> isAccountable(result, as))
				.map(result -> result.getAmtAndQty(type))
				.reduce(CostAmountAndQty::add);
	}

	public CostAmount getMainAmountToPost(@NonNull final AcctSchema as)
	{
		return getAmtAndQtyToPost(CostAmountType.MAIN, as)
				.map(CostAmountAndQty::getAmt)
				.orElseThrow();
	}

	public CostAmountDetailed getTotalAmountToPost(@NonNull final AcctSchema as)
	{
		return toAggregatedCostAmount().getTotalAmountToPost(as);
	}

	public AggregatedCostAmount toAggregatedCostAmount()
	{
		final CostSegment costSegment = CollectionUtils.extractSingleElement(list, CostDetailCreateResult::getCostSegment);

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

	private static boolean isAccountable(@NonNull CostDetailCreateResult result, @NonNull final AcctSchema as)
	{
		return result.getCostElement().isAccountable(as.getCosting());
	}
}
