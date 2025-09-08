package de.metas.distribution.ddordercandidate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.quantity.Quantity;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
public class DDOrderCandidateAllocList implements Iterable<DDOrderCandidateAlloc>
{
	public static final DDOrderCandidateAllocList EMPTY = new DDOrderCandidateAllocList(ImmutableList.of());
	@NonNull private final ImmutableList<DDOrderCandidateAlloc> list;

	private DDOrderCandidateAllocList(@NonNull final Collection<DDOrderCandidateAlloc> list)
	{
		this.list = ImmutableList.copyOf(list);
	}

	public static DDOrderCandidateAllocList of(@NonNull final Collection<DDOrderCandidateAlloc> list)
	{
		return list.isEmpty() ? EMPTY : new DDOrderCandidateAllocList(list);
	}

	public static Collector<DDOrderCandidateAlloc, ?, DDOrderCandidateAllocList> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(DDOrderCandidateAllocList::of);
	}

	public boolean isEmpty() {return list.isEmpty();}

	public Set<DDOrderCandidateId> getDDOrderCandidateIds()
	{
		return list.stream()
				.map(DDOrderCandidateAlloc::getDdOrderCandidateId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public Set<DDOrderId> getDDOrderIds()
	{
		return list.stream().map(DDOrderCandidateAlloc::getDdOrderId).collect(ImmutableSet.toImmutableSet());
	}

	public Set<DDOrderLineId> getDDOrderLineIds()
	{
		return list.stream().map(DDOrderCandidateAlloc::getDdOrderLineId).collect(ImmutableSet.toImmutableSet());
	}

	@Override
	@NonNull
	public Iterator<DDOrderCandidateAlloc> iterator()
	{
		return list.iterator();
	}

	public Map<DDOrderCandidateId, DDOrderCandidateAllocList> groupByCandidateId()
	{
		if (list.isEmpty())
		{
			return ImmutableMap.of();
		}

		return list.stream().collect(Collectors.groupingBy(DDOrderCandidateAlloc::getDdOrderCandidateId, collect()));
	}

	public Optional<Quantity> getQtySum()
	{
		return list.stream()
				.map(DDOrderCandidateAlloc::getQty)
				.reduce(Quantity::add);
	}

	public Set<Integer> getIds()
	{
		if (list.isEmpty())
		{
			return ImmutableSet.of();
		}

		return list.stream()
				.map(DDOrderCandidateAlloc::getId)
				.filter(id -> id > 0)
				.collect(ImmutableSet.toImmutableSet());
	}
}
