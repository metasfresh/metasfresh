package de.metas.order.paymentschedule.referenced_docs.material_receipt;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.order.OrderAndLineId;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString
public class MaterialReceiptCollection implements Iterable<MaterialReceipt>
{
	public static final MaterialReceiptCollection EMPTY = new MaterialReceiptCollection(ImmutableList.of());

	private final ImmutableList<MaterialReceipt> list;

	private MaterialReceiptCollection(@NonNull final ImmutableList<MaterialReceipt> list)
	{
		this.list = list;
	}

	public static MaterialReceiptCollection ofCollection(@NonNull final Collection<MaterialReceipt> receipts)
	{
		return !receipts.isEmpty() ? new MaterialReceiptCollection(ImmutableList.copyOf(receipts)) : EMPTY;
	}

	public static Collector<MaterialReceipt, ?, MaterialReceiptCollection> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(MaterialReceiptCollection::ofCollection);
	}

	@Override
	public @NotNull Iterator<MaterialReceipt> iterator()
	{
		return list.iterator();
	}

	public Set<OrderAndLineId> getOrderLineIds()
	{
		if (list.isEmpty()) {return ImmutableSet.of();}

		return list.stream()
				.flatMap(receipt -> receipt.getLines().stream())
				.map(MaterialReceipt.Line::getOrderLineId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	public boolean containsInOutLineId(@NonNull final InOutAndLineId inoutAndLineId)
	{
		return getById(inoutAndLineId.getInOutId())
				.filter(receipt -> receipt.containsInOutLineId(inoutAndLineId.getInOutLineId()))
				.isPresent();
	}

	private Optional<MaterialReceipt> getById(@NonNull final InOutId id)
	{
		return list.stream()
				.filter(receipt -> InOutId.equals(receipt.getId(), id))
				.findFirst();
	}
}
