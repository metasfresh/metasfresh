package de.metas.hu_consolidation.mobile.job.scan;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.picking.api.PickingSlotId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HUConsolidationJobCratesTest
{
	private static final PickingSlotId SLOT_1 = PickingSlotId.ofRepoId(1);
	private static final PickingSlotId SLOT_2 = PickingSlotId.ofRepoId(2);
	private static final HuId HU_1 = HuId.ofRepoId(101);
	private static final HuId HU_2 = HuId.ofRepoId(102);

	@Test
	void slotCount_isTheSizeOfThatSlotsQueue_evenWhenAnHUIsQueuedInTwoSlots()
	{
		final HUConsolidationJobCrates crates = HUConsolidationJobCrates.ofQueues(ImmutableList.of(
				GRAICrateTestFixtures.queue(SLOT_1, HU_1, HU_2),
				GRAICrateTestFixtures.queue(SLOT_2, HU_1)));

		assertThat(crates.getCountHUs(SLOT_1)).isEqualTo(2);
		assertThat(crates.getCountHUs(SLOT_2)).isEqualTo(1); // HU_1 is listed in this slot's queue too
		assertThat(crates.getHuIds()).containsExactlyInAnyOrder(HU_1, HU_2);
		assertThat(crates.getPickingSlotId(HU_1)).isEqualTo(SLOT_1); // an HU keeps its first slot
	}

	@Test
	void slotCount_isZeroForASlotWithoutQueue()
	{
		final HUConsolidationJobCrates crates = HUConsolidationJobCrates.ofQueues(ImmutableList.of(GRAICrateTestFixtures.queue(SLOT_1, HU_1)));

		assertThat(crates.getCountHUs(SLOT_2)).isZero();
	}

	@Test
	void everyHUReportsTheSlotItIsQueuedIn()
	{
		final HUConsolidationJobCrates crates = HUConsolidationJobCrates.ofQueues(ImmutableList.of(
				GRAICrateTestFixtures.queue(SLOT_1, HU_1),
				GRAICrateTestFixtures.queue(SLOT_2, HU_2)));

		assertThat(crates.getPickingSlotId(HU_2)).isEqualTo(SLOT_2);
		assertThat(crates.getHuIds()).containsExactlyInAnyOrder(HU_1, HU_2);
	}

	@Test
	void emptySlotSet_givesNoCrates_withoutQueueQuery()
	{
		AdempiereTestHelper.get().init();

		final HUConsolidationJobCrates crates = HUConsolidationJobCrates.load(ImmutableSet.of(), PickingSlotService.newInstanceForUnitTesting());

		assertThat(crates).isSameAs(HUConsolidationJobCrates.EMPTY);
		assertThat(crates.getHuIds()).isEmpty();
	}
}
