package de.metas.hu_consolidation.mobile.job.scan;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.slot.PickingSlotQueue;
import de.metas.handlingunits.picking.slot.PickingSlotQueueItem;
import de.metas.picking.api.PickingSlotId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HUConsolidationJobCratesTest
{
	private static final PickingSlotId SLOT_1 = PickingSlotId.ofRepoId(1);
	private static final PickingSlotId SLOT_2 = PickingSlotId.ofRepoId(2);
	private static final HuId HU_1 = HuId.ofRepoId(101);
	private static final HuId HU_2 = HuId.ofRepoId(102);

	private static PickingSlotQueue queue(final PickingSlotId slotId, final HuId... huIds)
	{
		final ImmutableList.Builder<PickingSlotQueueItem> items = ImmutableList.builder();
		for (final HuId huId : huIds)
		{
			items.add(PickingSlotQueueItem.builder().huId(huId).build());
		}
		return PickingSlotQueue.builder().pickingSlotId(slotId).items(items.build()).build();
	}

	@Test
	void slotCount_isTheSizeOfThatSlotsQueue_evenWhenAnHUIsQueuedInTwoSlots()
	{
		final HUConsolidationJobCrates crates = HUConsolidationJobCrates.ofQueues(ImmutableList.of(
				queue(SLOT_1, HU_1, HU_2),
				queue(SLOT_2, HU_1)));

		assertThat(crates.getCountHUs(SLOT_1)).isEqualTo(2);
		assertThat(crates.getCountHUs(SLOT_2)).isEqualTo(1); // HU_1 is listed in this slot's queue too
		assertThat(crates.getHuIds()).containsExactlyInAnyOrder(HU_1, HU_2);
		assertThat(crates.getPickingSlotId(HU_1)).isEqualTo(SLOT_1); // an HU keeps its first slot
	}

	@Test
	void slotCount_isZeroForASlotWithoutQueue()
	{
		final HUConsolidationJobCrates crates = HUConsolidationJobCrates.ofQueues(ImmutableList.of(queue(SLOT_1, HU_1)));

		assertThat(crates.getCountHUs(SLOT_2)).isZero();
	}
}
