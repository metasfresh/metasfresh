package de.metas.deliveryplanning;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.shipping.model.ShipperTransportationId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

/**
 * Owns the derivation that spans three tables: {@code M_Delivery_Planning_Alloc} is the fact, the
 * instruction's {@code DocStatus} qualifies it, and the two resulting flags are stored on
 * {@code M_Delivery_Planning}.
 * <p>
 * It exists because that derivation used to live in {@link DeliveryPlanningAllocRepository}, which meant a
 * repository named for one table issued {@code UPDATE M_Delivery_Planning SET ...} against another
 * aggregate's root - and then had to reset that table's cache by hand, precisely because it had written
 * rows behind their owner's back. A service may call several repositories; a repository may not write a
 * table it does not own.
 */
@Service
public class DeliveryPlanningAllocService
{
	@NonNull private final DeliveryPlanningAllocRepository deliveryPlanningAllocRepository;
	@NonNull private final DeliveryPlanningRepository deliveryPlanningRepository;
	@NonNull private final DeliveryInstructionRepository deliveryInstructionRepository;

	public DeliveryPlanningAllocService(
			@NonNull final DeliveryPlanningAllocRepository deliveryPlanningAllocRepository,
			@NonNull final DeliveryPlanningRepository deliveryPlanningRepository,
			@NonNull final DeliveryInstructionRepository deliveryInstructionRepository)
	{
		this.deliveryPlanningAllocRepository = deliveryPlanningAllocRepository;
		this.deliveryPlanningRepository = deliveryPlanningRepository;
		this.deliveryInstructionRepository = deliveryInstructionRepository;
	}

	/**
	 * Recomputes {@code IsAllocated} and {@code IsReadyForReceipt} for the given plannings and stores them.
	 * <p>
	 * Three queries regardless of how many plannings are given - the allocations, the completed instructions
	 * among those they reference, and the one batch load the write needs - rather than a correlated
	 * per-row subquery. The old shape was a single hand-written SQL statement, which was cheaper still, but
	 * it wrote a foreign table; this is the same result at a load count the batch-loading tests can hold.
	 */
	public void refreshDerivedFlags(@NonNull final Set<DeliveryPlanningId> deliveryPlanningIds)
	{
		if (deliveryPlanningIds.isEmpty())
		{
			return;
		}

		final ImmutableListMultimap<DeliveryPlanningId, DeliveryPlanningAlloc> allocationsByPlanningId =
				deliveryPlanningAllocRepository.getByDeliveryPlanningIds(deliveryPlanningIds);

		final ImmutableSet<ShipperTransportationId> completedInstructionIds = deliveryInstructionRepository.getCompletedAmong(
				allocationsByPlanningId.values().stream()
						.map(DeliveryPlanningAlloc::getDeliveryInstructionId)
						.collect(ImmutableSet.toImmutableSet()));

		final ImmutableMap.Builder<DeliveryPlanningId, AllocationDerivedFlags> flags = ImmutableMap.builder();
		for (final DeliveryPlanningId deliveryPlanningId : deliveryPlanningIds)
		{
			final Collection<DeliveryPlanningAlloc> allocations = allocationsByPlanningId.get(deliveryPlanningId);

			flags.put(deliveryPlanningId, AllocationDerivedFlags.of(
					!allocations.isEmpty(),
					allocations.stream()
							.map(DeliveryPlanningAlloc::getDeliveryInstructionId)
							.anyMatch(completedInstructionIds::contains)));
		}

		deliveryPlanningRepository.updateAllocationDerivedFlags(flags.build());
	}

	/** Every planning currently allocated to the given instruction - used when the instruction itself changes. */
	public void refreshDerivedFlagsOfInstruction(@NonNull final ShipperTransportationId deliveryInstructionId)
	{
		refreshDerivedFlags(deliveryPlanningAllocRepository.getAllocatedPlanningIds(deliveryInstructionId));
	}
}
