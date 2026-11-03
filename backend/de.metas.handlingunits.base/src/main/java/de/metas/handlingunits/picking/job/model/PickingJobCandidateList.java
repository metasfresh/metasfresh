package de.metas.handlingunits.picking.job.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductId;
import de.metas.util.GuavaCollectors;
import de.metas.util.collections.CollectionUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class PickingJobCandidateList
{
	public static final PickingJobCandidateList EMPTY = new PickingJobCandidateList(ImmutableList.of());
	private final ImmutableList<PickingJobCandidate> list;

	private PickingJobCandidateList(@NonNull final List<PickingJobCandidate> list)
	{
		this.list = ImmutableList.copyOf(list);
	}

	public static PickingJobCandidateList ofList(final List<PickingJobCandidate> list)
	{
		return !list.isEmpty() ? new PickingJobCandidateList(list) : EMPTY;
	}

	public static Collector<PickingJobCandidate, ?, PickingJobCandidateList> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(PickingJobCandidateList::ofList);
	}

	public boolean isEmpty() {return list.isEmpty();}

	public Stream<PickingJobCandidate> stream() {return list.stream();}

	public PickingJobCandidateList removeIf(@NonNull Predicate<PickingJobCandidate> predicate)
	{
		final ImmutableList<PickingJobCandidate> changedList = CollectionUtils.removeIf(list, predicate);
		return list.size() == changedList.size() ? this : ofList(changedList);
	}

	public PickingJobCandidateList updateEach(@NonNull UnaryOperator<PickingJobCandidate> updater)
	{
		if (list.isEmpty()) {return this;}
		final ImmutableList<PickingJobCandidate> changedList = CollectionUtils.map(list, updater);
		return list == changedList ? this : ofList(changedList);
	}

	public Set<ProductId> getProductIds()
	{
		return list.stream()
				.flatMap(job -> job.getProductIds().stream())
				.collect(ImmutableSet.toImmutableSet());
	}
}
