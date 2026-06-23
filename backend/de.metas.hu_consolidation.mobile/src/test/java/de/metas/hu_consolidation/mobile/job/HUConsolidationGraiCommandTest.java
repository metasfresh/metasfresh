package de.metas.hu_consolidation.mobile.job;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.grai.GRAISet;
import de.metas.handlingunits.grai.HUGraiService;
import de.metas.hu_consolidation.mobile.job.commands.set_target_grais.SetTargetGraisCommand;
import de.metas.picking.api.PickingSlotId;
import de.metas.user.UserId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.adempiere.test.AdempiereTestWatcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for set/get GRAIs on the HU Consolidation job's current target LU.
 * Verifies that the command delegates to {@link HUGraiService} using the job's current target LU ID.
 */
@ExtendWith(AdempiereTestWatcher.class)
class HUConsolidationGraiCommandTest
{
	private static final HuId LU_ID = HuId.ofRepoId(12345);
	private static final UserId USER_ID = UserId.ofRepoId(1);

	private HUConsolidationJobRepository jobRepository;
	private HUGraiService huGraiService;
	private HUConsolidationJob job;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();

		jobRepository = new HUConsolidationJobRepository();
		huGraiService = mock(HUGraiService.class);

		// Create a job with an existing LU as current target
		final HUConsolidationJobReference reference = HUConsolidationJobReference.builder()
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(1, 2))
				.pickingSlotId(PickingSlotId.ofRepoId(3))
				.build();
		job = jobRepository.create(reference, USER_ID);

		// Set an existing-LU target on the job (via save)
		final HUConsolidationTarget existingLuTarget = HUConsolidationTarget.builder()
				.caption("Test LU")
				.luId(LU_ID)
				.build();
		jobRepository.save(job.withCurrentTarget(existingLuTarget));
		job = jobRepository.getById(job.getId());
	}

	@Test
	void setTargetGrais_delegatesToHUGraiService()
	{
		// GIVEN
		final GRAISet graiSet = GRAISet.ofStrings(ImmutableSet.of("7613204.00307.1000000001", "7613204.00307.1000000002"));

		// WHEN
		SetTargetGraisCommand.builder()
				.jobRepository(jobRepository)
				.huGraiService(huGraiService)
				.jobId(job.getId())
				.callerId(USER_ID)
				.graiSet(graiSet)
				.build()
				.execute();

		// THEN — the command must have called HUGraiService.setGrais with the target LU id
		verify(huGraiService).setGrais(eq(LU_ID), eq(graiSet));
	}

	@Test
	void getTargetGrais_delegatesToHUGraiService()
	{
		// GIVEN — service returns two GRAIs for the LU
		final GRAI grai1 = GRAI.ofCanonicalString("7613204.00307.1000000001");
		final GRAI grai2 = GRAI.ofCanonicalString("7613204.00307.1000000002");
		final GRAISet expectedGrais = GRAISet.ofCollection(ImmutableSet.of(grai1, grai2));
		when(huGraiService.getGrais(LU_ID)).thenReturn(expectedGrais);

		// WHEN — read back using the service method that the command builds upon
		final GRAISet actualGrais = huGraiService.getGrais(LU_ID);

		// THEN
		assertThat(actualGrais).isEqualTo(expectedGrais);
		assertThat(actualGrais.toStringList()).containsExactlyInAnyOrder(
				grai1.toCanonicalString(),
				grai2.toCanonicalString());
	}
}
