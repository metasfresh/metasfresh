package de.metas.handlingunits.picking.slot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.picking.api.PickingSlotId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PickingSlotQueuesTest
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
		final PickingSlotQueues queues = PickingSlotQueues.ofList(ImmutableList.of(queue(SLOT_1, HU_1, HU_2), queue(SLOT_2, HU_1)));

		assertThat(queues.getCountHUs(SLOT_1)).isEqualTo(2);
		assertThat(queues.getCountHUs(SLOT_2)).isEqualTo(1); // HU_1 is listed in this slot's queue too
		assertThat(queues.getHuIds()).containsExactlyInAnyOrder(HU_1, HU_2);
		assertThat(queues.getPickingSlotId(HU_1)).isEqualTo(SLOT_1); // an HU keeps its first slot
	}

	@Test
	void slotCount_isZeroForASlotWithoutQueue()
	{
		final PickingSlotQueues queues = PickingSlotQueues.ofList(ImmutableList.of(queue(SLOT_1, HU_1)));

		assertThat(queues.getCountHUs(SLOT_2)).isZero();
	}

	@Test
	void everyHUReportsTheSlotItIsQueuedIn()
	{
		final PickingSlotQueues queues = PickingSlotQueues.ofList(ImmutableList.of(queue(SLOT_1, HU_1), queue(SLOT_2, HU_2)));

		assertThat(queues.getPickingSlotId(HU_2)).isEqualTo(SLOT_2);
		assertThat(queues.getHuIds()).containsExactlyInAnyOrder(HU_1, HU_2);
	}

	@Test
	void huInNoQueue_throws()
	{
		final PickingSlotQueues queues = PickingSlotQueues.ofList(ImmutableList.of(queue(SLOT_1, HU_1)));

		assertThatThrownBy(() -> queues.getPickingSlotId(HU_2)).isInstanceOf(AdempiereException.class);
	}

	@Test
	void emptySlotSet_givesNoQueues_withoutQueueQuery()
	{
		AdempiereTestHelper.get().init();

		final PickingSlotQueues queues = PickingSlotService.newInstanceForUnitTesting().getNotEmptyQueues(ImmutableSet.of());

		assertThat(queues).isSameAs(PickingSlotQueues.EMPTY);
		assertThat(queues.getHuIds()).isEmpty();
	}
}
