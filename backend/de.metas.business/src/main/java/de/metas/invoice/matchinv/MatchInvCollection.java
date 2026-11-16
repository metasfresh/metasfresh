package de.metas.invoice.matchinv;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.quantity.Quantity;
import de.metas.util.GuavaCollectors;
import de.metas.util.collections.CollectionUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class MatchInvCollection implements Iterable<MatchInv>
{
	public static final MatchInvCollection EMPTY = new MatchInvCollection(ImmutableList.of());

	private final ImmutableList<MatchInv> list;

	private MatchInvCollection(@NonNull final ImmutableList<MatchInv> list)
	{
		this.list = list;
	}

	public static MatchInvCollection ofList(@NonNull final List<MatchInv> list)
	{
		return list.isEmpty() ? EMPTY : new MatchInvCollection(ImmutableList.copyOf(list));
	}

	public static Collector<MatchInv, ?, MatchInvCollection> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(MatchInvCollection::ofList);
	}

	@Override
	@NonNull
	public Iterator<MatchInv> iterator() {return list.iterator();}

	public Stream<MatchInv> stream() {return list.stream();}

	public MatchInvCollection filter(@NonNull Predicate<MatchInv> predicate)
	{
		if (list.isEmpty()) {return this;}

		final ImmutableList<MatchInv> newList = list.stream().filter(predicate).collect(ImmutableList.toImmutableList());
		if (list.size() == newList.size())
		{
			return this;
		}

		return ofList(newList);
	}

	public Optional<Quantity> getQtyMatched(final @NonNull InvoiceLineId invoiceLineId)
	{
		return list.stream()
				.filter(matchInv -> InvoiceLineId.equals(matchInv.getInvoiceAndLineId().toInvoiceLineId(), invoiceLineId))
				.map(matchInv -> matchInv.getQty().getStockQty())
				.reduce(Quantity::add);
	}

	public boolean isEmpty() {return list.isEmpty();}

	public ImmutableSet<InOutId> getInOutIds()
	{
		return list.stream()
				.map(MatchInv::getInOutId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public ImmutableSet<InOutLineId> getInOutLineIds()
	{
		return list.stream()
				.map(matchInv -> matchInv.getInoutLineId().getInOutLineId())
				.collect(ImmutableSet.toImmutableSet());
	}

	public ImmutableSet<InvoiceId> getInvoiceIds()
	{
		return list.stream()
				.map(MatchInv::getInvoiceId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public int size() {return list.size();}

	@NonNull
	public MatchInv singleElement()
	{
		return CollectionUtils.singleElement(list);
	}
}
