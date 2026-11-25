package de.metas.frontend_testing.pickingslot;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.picking.api.PickingSlotId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test leaves crates in its picking slots; every non-empty picking slot is offered as an HU consolidation job to every later test.
 * Emptying the test's own slots afterwards keeps the job list of the next tests short.
 */
class EmptyPickingSlotsCommandTest
{
	private PickingSlotService pickingSlotService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		pickingSlotService = PickingSlotService.newInstanceForUnitTesting();
	}

	private PickingSlotId pickingSlotWithHUs(final int countHUs)
	{
		final I_M_PickingSlot slot = InterfaceWrapperHelper.newInstance(I_M_PickingSlot.class);
		InterfaceWrapperHelper.saveRecord(slot);
		final PickingSlotId slotId = PickingSlotId.ofRepoId(slot.getM_PickingSlot_ID());

		final ImmutableSet.Builder<HuId> huIds = ImmutableSet.builder();
		for (int i = 0; i < countHUs; i++)
		{
			final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class);
			InterfaceWrapperHelper.saveRecord(hu);
			huIds.add(HuId.ofRepoId(hu.getM_HU_ID()));
		}
		pickingSlotService.addToPickingSlotQueue(slotId, huIds.build());
		return slotId;
	}

	@Test
	void theGivenSlotsAreEmptied_otherSlotsKeepTheirHUs()
	{
		final PickingSlotId slot1 = pickingSlotWithHUs(2);
		final PickingSlotId slot2 = pickingSlotWithHUs(1);
		final PickingSlotId otherSlot = pickingSlotWithHUs(1);

		EmptyPickingSlotsCommand.builder()
				.pickingSlotService(pickingSlotService)
				.pickingSlotIds(ImmutableSet.of(slot1, slot2))
				.build()
				.execute();

		assertThat(pickingSlotService.getPickingSlotQueue(slot1).isEmpty()).isTrue();
		assertThat(pickingSlotService.getPickingSlotQueue(slot2).isEmpty()).isTrue();
		assertThat(pickingSlotService.getPickingSlotQueue(otherSlot).getCountHUs()).isEqualTo(1);
	}
}
