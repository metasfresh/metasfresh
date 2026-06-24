package de.metas.hu_consolidation.mobile.job.commands.consolidate;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.picking.slot.PickingSlotQueue;
import de.metas.handlingunits.picking.slot.PickingSlotQueueItem;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobId;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobRepository;
import de.metas.picking.api.PickingSlotId;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

/**
 * Unit test for GRAI-based TU selection in {@link ConsolidateCommand#getHuIdsToConsolidate()}.
 *
 * <p>Scope: resolves that GRAI resolution feeds the existing "in-slot" guard correctly.
 * Full consolidation (HU transforms, job state) requires a live stack and is covered
 * by the mobile-webui Playwright E2E tests.</p>
 */
@ExtendWith(AdempiereTestWatcher.class)
class ConsolidateCommand_GraiTest
{
	private static final PickingSlotId FROM_SLOT_ID = PickingSlotId.ofRepoId(1);
	private static final HuId TU_IN_SLOT_ID = HuId.ofRepoId(100);
	private static final HuId OTHER_TU_ID = HuId.ofRepoId(999);

	private static final GRAI KNOWN_GRAI = GRAI.parse("1234567.00001.SERIAL01");
	private static final GRAI UNKNOWN_GRAI = GRAI.parse("9999999.00099.UNKNOWN");
	private static final GRAI GRAI_OF_TU_NOT_IN_SLOT = GRAI.parse("1234567.00001.NOTHERE");

	private IHandlingUnitsBL handlingUnitsBL;
	private PickingSlotService pickingSlotService;
	private PickingSlotQueue slotQueueWithTu;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		handlingUnitsBL = Mockito.mock(IHandlingUnitsBL.class);
		Services.registerService(IHandlingUnitsBL.class, handlingUnitsBL);

		pickingSlotService = Mockito.mock(PickingSlotService.class);

		slotQueueWithTu = PickingSlotQueue.builder()
				.pickingSlotId(FROM_SLOT_ID)
				.items(ImmutableList.of(PickingSlotQueueItem.builder().huId(TU_IN_SLOT_ID).build()))
				.build();

		// KNOWN_GRAI resolves to TU_IN_SLOT_ID
		when(handlingUnitsBL.getHuIdByGrai(KNOWN_GRAI)).thenReturn(Optional.of(TU_IN_SLOT_ID));
		// UNKNOWN_GRAI resolves to nothing
		when(handlingUnitsBL.getHuIdByGrai(UNKNOWN_GRAI)).thenReturn(Optional.empty());
		// GRAI_OF_TU_NOT_IN_SLOT resolves to OTHER_TU_ID which is NOT in the slot
		when(handlingUnitsBL.getHuIdByGrai(GRAI_OF_TU_NOT_IN_SLOT)).thenReturn(Optional.of(OTHER_TU_ID));

		when(pickingSlotService.getPickingSlotQueue(FROM_SLOT_ID)).thenReturn(slotQueueWithTu);
	}

	private ConsolidateCommand buildCommand(final HuId huId, final GRAI grai)
	{
		final ConsolidateRequest request = ConsolidateRequest.builder()
				.callerId(UserId.ofRepoId(1))
				.jobId(HUConsolidationJobId.ofRepoId(1))
				.fromPickingSlotId(FROM_SLOT_ID)
				.huId(huId)
				.grai(grai)
				.build();

		return ConsolidateCommand.builder()
				.jobRepository(Mockito.mock(HUConsolidationJobRepository.class))
				.huQRCodesService(Mockito.mock(HUQRCodesService.class))
				.pickingSlotService(pickingSlotService)
				.request(request)
				.build();
	}

	@Test
	void grai_known_and_tu_in_slot__resolves_to_that_tu()
	{
		final ConsolidateCommand command = buildCommand(null, KNOWN_GRAI);

		final Set<HuId> result = command.getHuIdsToConsolidate();

		assertThat(result).containsExactly(TU_IN_SLOT_ID);
	}

	@Test
	void grai_unknown__throws_hu_not_found()
	{
		final ConsolidateCommand command = buildCommand(null, UNKNOWN_GRAI);

		assertThatThrownBy(command::getHuIdsToConsolidate)
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("HuNotFound");
	}

	@Test
	void grai_tu_not_in_slot__throws_lu_not_at_slot()
	{
		final ConsolidateCommand command = buildCommand(null, GRAI_OF_TU_NOT_IN_SLOT);

		assertThatThrownBy(command::getHuIdsToConsolidate)
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("LuNotAtPickingSlot");
	}

	@Test
	void no_grai_no_huId__returns_all_in_slot()
	{
		final ConsolidateCommand command = buildCommand(null, null);

		final Set<HuId> result = command.getHuIdsToConsolidate();

		// All HUs in the slot are returned — the existing all-in-slot fallback behavior is unchanged
		assertThat(result).containsExactly(TU_IN_SLOT_ID);
	}

	@Test
	void huId_takes_priority_over_grai__uses_huId()
	{
		// Both huId AND grai are set; huId wins (task spec: "explicit huId takes priority")
		final ConsolidateCommand command = buildCommand(TU_IN_SLOT_ID, KNOWN_GRAI);

		final Set<HuId> result = command.getHuIdsToConsolidate();

		assertThat(result).containsExactly(TU_IN_SLOT_ID);
		// GRAI lookup must NOT have been invoked
		Mockito.verify(handlingUnitsBL, Mockito.never()).getHuIdByGrai(Mockito.any());
	}
}
