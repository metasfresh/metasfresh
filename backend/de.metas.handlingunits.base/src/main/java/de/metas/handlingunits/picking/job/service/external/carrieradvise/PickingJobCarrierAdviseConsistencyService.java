package de.metas.handlingunits.picking.job.service.external.carrieradvise;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.job.carrieradvise.CarrierAdviseConsistencyService;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * {@code external/} facade exposing carrier-advise consistency to the picking-job services.
 * <p>
 * Per the {@code external/} facade rule (see this package's parent {@code service/CLAUDE.md}), a picking-job
 * service must not inject the {@link CarrierAdviseConsistencyService} {@code @Service} directly. This facade owns
 * it (and the {@link PickingJobHUService} used to load the {@link I_M_HU} for each {@link HuId}) and exposes the
 * single narrow operation the close flow needs.
 */
@Service
@RequiredArgsConstructor
public class PickingJobCarrierAdviseConsistencyService
{
	public static PickingJobCarrierAdviseConsistencyService newInstanceForUnitTesting(
			@NonNull final CarrierAdviseConsistencyService carrierAdviseConsistencyService)
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(
				PickingJobCarrierAdviseConsistencyService.class,
				() -> new PickingJobCarrierAdviseConsistencyService(
						carrierAdviseConsistencyService,
						PickingJobHUService.newInstanceForUnitTesting()));
	}

	@NonNull private final CarrierAdviseConsistencyService carrierAdviseConsistencyService;
	@NonNull private final PickingJobHUService huService;

	/**
	 * Asserts carrier-advise consistency for each of the given closed top-level HUs (parcels). Empty set → no-op.
	 * Throws on the first inconsistency (aborting the caller's target-close).
	 */
	public void assertConsistentForClosedHUs(@NonNull final Set<HuId> topLevelHuIds)
	{
		for (final HuId topLevelHuId : topLevelHuIds)
		{
			final I_M_HU topLevelHU = huService.getById(topLevelHuId);
			carrierAdviseConsistencyService.assertConsistentForClosedHU(topLevelHU);
		}
	}
}
