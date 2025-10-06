package de.metas.inventory;

import com.google.common.collect.ImmutableSet;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.compiere.model.I_M_InventoryLine;

import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public final class InventoryAndLineIdSet
{
	public static final InventoryAndLineIdSet EMPTY = new InventoryAndLineIdSet(ImmutableSet.of());

	@NonNull private final ImmutableSet<InventoryAndLineId> set;
	@NonNull @Getter private final ImmutableSet<InventoryId> inventoryIds;

	private InventoryAndLineIdSet(@NonNull final Collection<InventoryAndLineId> set)
	{
		this.set = ImmutableSet.copyOf(set);
		this.inventoryIds = set.stream().map(InventoryAndLineId::getInventoryId).collect(ImmutableSet.toImmutableSet());
	}

	public static InventoryAndLineIdSet ofCollection(@NonNull final Collection<InventoryAndLineId> inventoryAndLineIds)
	{
		return inventoryAndLineIds.isEmpty() ? EMPTY : new InventoryAndLineIdSet(inventoryAndLineIds);
	}

	public static @NonNull InventoryAndLineIdSet of(final @NonNull InventoryAndLineId inventoryAndLineId)
	{
		return new InventoryAndLineIdSet(ImmutableSet.of(inventoryAndLineId));
	}

	public static Collector<InventoryAndLineId, ?, InventoryAndLineIdSet> collect()
	{
		return GuavaCollectors.collectUsingHashSetAccumulator(InventoryAndLineIdSet::ofCollection);
	}

	public boolean isEmpty() {return set.isEmpty();}

	public boolean contains(final InventoryAndLineId inventoryAndLineId)
	{
		return set.contains(inventoryAndLineId);
	}

	public boolean contains(final I_M_InventoryLine inventoryLineRecord)
	{
		return contains(InventoryAndLineId.of(inventoryLineRecord));
	}

	public Stream<InventoryAndLineId> stream()
	{
		return set.stream();
	}
}
