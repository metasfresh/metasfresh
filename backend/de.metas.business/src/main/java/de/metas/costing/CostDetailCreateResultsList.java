package de.metas.costing;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.costing.methods.CostAmountDetailed;
import de.metas.costing.methods.CostAmountType;
import de.metas.product.ProductId;
import de.metas.util.GuavaCollectors;
import de.metas.util.collections.CollectionUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
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

	public boolean isEmpty() {return list.isEmpty();}

	/**
	 * Restricts this list to the rows of a single product. A CC-170 {@code CostDifferenceDistribution} reversal
	 * loads the initial document's {@code CostDetail} rows for the main product AND each co-product (distinct cost
	 * segments), so {@link #toAggregatedCostAmount()} (which requires a single segment) cannot be applied to the whole
	 * mixed-product list. The caller narrows to one product first; the co-products' reversal legs are re-emitted per
	 * product by {@code Doc_PPCostCollector.createCoProductDifferenceFacts}. For a single-product document this returns
	 * the list unchanged.
	 */
	public CostDetailCreateResultsList filterByProductId(@NonNull final ProductId productId)
	{
		final ImmutableList<CostDetailCreateResult> filtered = list.stream()
				.filter(result -> productId.equals(result.getCostSegment().getProductId()))
				.collect(ImmutableList.toImmutableList());
		return filtered.size() == list.size() ? this : ofList(filtered);
	}

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
				.orElseThrow(() -> new NoSuchElementException("No value present"));
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
