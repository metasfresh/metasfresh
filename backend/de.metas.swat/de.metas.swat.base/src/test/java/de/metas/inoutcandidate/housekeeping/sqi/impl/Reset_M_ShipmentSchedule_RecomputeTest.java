package de.metas.inoutcandidate.housekeeping.sqi.impl;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.inoutcandidate.async.UpdateInvalidShipmentSchedulesWorkpackageProcessor;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateRepository;
import de.metas.util.Services;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The startup reset only un-tags stale recompute markers; it must also enqueue a recompute pass so those markers get processed.
 */
class Reset_M_ShipmentSchedule_RecomputeTest
{
	private IShipmentScheduleInvalidateRepository repo;
	private Reset_M_ShipmentSchedule_Recompute task;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repo = Mockito.mock(IShipmentScheduleInvalidateRepository.class);
		Services.registerService(IShipmentScheduleInvalidateRepository.class, repo);
		task = new Reset_M_ShipmentSchedule_Recompute();
	}

	private List<I_C_Queue_WorkPackage> recomputeWorkpackages()
	{
		return POJOLookupMap.get().getRecords(I_C_Queue_WorkPackage.class).stream()
				.filter(wp -> UpdateInvalidShipmentSchedulesWorkpackageProcessor.class.getName().equals(wp.getC_Queue_PackageProcessor().getClassname()))
				.collect(Collectors.toList());
	}

	@Test
	void enqueuesRecomputeWhenUntaggedFlagsRemain()
	{
		when(repo.resetStaleRecomputeMarkers()).thenReturn(3);
		when(repo.existsUntaggedRecomputeMarkers()).thenReturn(true);

		task.executeTask();

		verify(repo).resetStaleRecomputeMarkers();
		assertThat(recomputeWorkpackages()).hasSize(1);
	}

	@Test
	void noWorkpackageWhenNothingPending()
	{
		when(repo.resetStaleRecomputeMarkers()).thenReturn(0);
		when(repo.existsUntaggedRecomputeMarkers()).thenReturn(false);

		task.executeTask();

		verify(repo).resetStaleRecomputeMarkers();
		assertThat(recomputeWorkpackages()).isEmpty();
	}
}
