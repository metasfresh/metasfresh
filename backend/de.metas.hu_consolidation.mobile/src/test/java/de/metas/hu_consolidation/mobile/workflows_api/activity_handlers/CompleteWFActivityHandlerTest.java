package de.metas.hu_consolidation.mobile.workflows_api.activity_handlers;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.grai.HUGraiService;
import de.metas.handlingunits.grai.HUGraiSnapshot;
import de.metas.handlingunits.grai.HUGraiSnapshot.TU;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJob;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobRepository;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobReference;
import de.metas.hu_consolidation.mobile.job.HUConsolidationJobService;
import de.metas.hu_consolidation.mobile.job.HUConsolidationTarget;
import de.metas.i18n.ExplainedOptional;
import de.metas.picking.api.PickingSlotId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.workflow.rest_api.model.WFActivityStatus;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.X_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for the GRAI-completeness gate in {@link CompleteWFActivityHandler}.
 *
 * <p>When {@code GRAIRequired != No} and the current target LU has unfilled TU GRAI slots,
 * completion must be blocked: {@code computeActivityState} returns not-ready and
 * {@code checkGraisComplete} throws an {@link AdempiereException}.
 */
@ExtendWith(AdempiereTestWatcher.class)
class CompleteWFActivityHandlerTest
{
	private static final BPartnerId BPARTNER_ID = BPartnerId.ofRepoId(1);
	private static final UserId USER_ID = UserId.ofRepoId(1);
	private static final HuId TARGET_LU_ID = HuId.ofRepoId(100);
	private static final GRAI GRAI_A = GRAI.ofCanonicalString("7613204.00307.1000000001");

	private CompleteWFActivityHandler handler;
	private IBPartnerDAO bpartnerDAO;
	private HUGraiService huGraiService;
	private HUConsolidationJob jobWithTarget;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		bpartnerDAO = mock(IBPartnerDAO.class);
		Services.registerService(IBPartnerDAO.class, bpartnerDAO);

		huGraiService = mock(HUGraiService.class);

		handler = new CompleteWFActivityHandler(mock(HUConsolidationJobService.class), huGraiService);

		final HUConsolidationJobRepository jobRepository = new HUConsolidationJobRepository();
		final HUConsolidationJobReference reference = HUConsolidationJobReference.builder()
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(BPARTNER_ID, 2))
				.pickingSlotId(PickingSlotId.ofRepoId(3))
				.build();
		final HUConsolidationJob baseJob = jobRepository.create(reference, USER_ID);

		final HUConsolidationTarget target = HUConsolidationTarget.builder()
				.caption("Test LU")
				.luId(TARGET_LU_ID)
				.build();
		jobWithTarget = baseJob.withCurrentTarget(target);
	}

	// ------------------------------------------------------------------
	// GRAIRequired=Yes, unfilled TU slot → block
	// ------------------------------------------------------------------

	@Test
	void computeActivityState_graiRequiredYes_unfilled_returnsNotCompleted()
	{
		givenBPartnerGRAIRequired(X_C_BPartner.GRAIREQUIRED_Yes);
		givenTargetLUSnapshot(snapshotWithUnfilledTU(TARGET_LU_ID));

		final WFActivityStatus status = handler.computeActivityState(jobWithTarget);

		assertThat(status).isEqualTo(WFActivityStatus.NOT_STARTED);
	}

	@Test
	void checkGraisComplete_graiRequiredYes_unfilled_throws()
	{
		givenBPartnerGRAIRequired(X_C_BPartner.GRAIREQUIRED_Yes);
		givenTargetLUSnapshot(snapshotWithUnfilledTU(TARGET_LU_ID));

		assertThatThrownBy(() -> handler.checkGraisComplete(jobWithTarget))
				.isInstanceOf(AdempiereException.class);
	}

	// ------------------------------------------------------------------
	// GRAIRequired=YesWithDummyGRAIs, unfilled → block (treated as Yes, no auto-fill)
	// ------------------------------------------------------------------

	@Test
	void checkGraisComplete_graiRequiredYesWithDummyGRAIs_unfilled_throws()
	{
		givenBPartnerGRAIRequired(X_C_BPartner.GRAIREQUIRED_YesWithDummyGRAIs);
		givenTargetLUSnapshot(snapshotWithUnfilledTU(TARGET_LU_ID));

		assertThatThrownBy(() -> handler.checkGraisComplete(jobWithTarget))
				.isInstanceOf(AdempiereException.class);
	}

	// ------------------------------------------------------------------
	// GRAIRequired=Yes, all slots filled → allow
	// ------------------------------------------------------------------

	@Test
	void computeActivityState_graiRequiredYes_allFilled_returnsCompleted()
	{
		givenBPartnerGRAIRequired(X_C_BPartner.GRAIREQUIRED_Yes);
		givenTargetLUSnapshot(snapshotWithFilledTU(TARGET_LU_ID, GRAI_A));

		final WFActivityStatus status = handler.computeActivityState(jobWithTarget);

		assertThat(status).isEqualTo(WFActivityStatus.COMPLETED);
	}

	@Test
	void checkGraisComplete_graiRequiredYes_allFilled_doesNotThrow()
	{
		givenBPartnerGRAIRequired(X_C_BPartner.GRAIREQUIRED_Yes);
		givenTargetLUSnapshot(snapshotWithFilledTU(TARGET_LU_ID, GRAI_A));

		// must not throw
		handler.checkGraisComplete(jobWithTarget);
	}

	// ------------------------------------------------------------------
	// GRAIRequired=No → always allow
	// ------------------------------------------------------------------

	@Test
	void computeActivityState_graiRequiredNo_returnsCompleted()
	{
		givenBPartnerGRAIRequired(X_C_BPartner.GRAIREQUIRED_No);
		// no snapshot mock needed — gate must short-circuit when GRAIRequired=No

		final WFActivityStatus status = handler.computeActivityState(jobWithTarget);

		assertThat(status).isEqualTo(WFActivityStatus.COMPLETED);
	}

	@Test
	void checkGraisComplete_graiRequiredNo_doesNotThrow()
	{
		givenBPartnerGRAIRequired(X_C_BPartner.GRAIREQUIRED_No);

		// must not throw
		handler.checkGraisComplete(jobWithTarget);
	}

	// ------------------------------------------------------------------
	// Helpers
	// ------------------------------------------------------------------

	private void givenBPartnerGRAIRequired(final String graiRequiredCode)
	{
		final I_C_BPartner bpartner = mock(I_C_BPartner.class);
		when(bpartner.getGRAIRequired()).thenReturn(graiRequiredCode);
		when(bpartnerDAO.getById(BPARTNER_ID)).thenReturn(bpartner);
	}

	private void givenTargetLUSnapshot(final HUGraiSnapshot snapshot)
	{
		when(huGraiService.getSnapshot(TARGET_LU_ID)).thenReturn(ExplainedOptional.of(snapshot));
	}

	/** One TU slot with no GRAI assigned — unfilled. */
	private static HUGraiSnapshot snapshotWithUnfilledTU(final HuId luId)
	{
		return HUGraiSnapshot.builder()
				.huId(luId)
				.tus(ImmutableList.of(TU.of(HuId.ofRepoId(luId.getRepoId() + 1), null)))
				.aggregateBlocks(ImmutableList.of())
				.build();
	}

	/** One TU slot with a GRAI assigned — fully filled. */
	private static HUGraiSnapshot snapshotWithFilledTU(final HuId luId, final GRAI grai)
	{
		return HUGraiSnapshot.builder()
				.huId(luId)
				.tus(ImmutableList.of(TU.of(HuId.ofRepoId(luId.getRepoId() + 1), grai)))
				.aggregateBlocks(ImmutableList.of())
				.build();
	}
}
