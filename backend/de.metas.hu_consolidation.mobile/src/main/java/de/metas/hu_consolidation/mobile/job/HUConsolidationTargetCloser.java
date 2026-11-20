package de.metas.hu_consolidation.mobile.job;

import com.google.common.collect.ImmutableSet;
import de.metas.hu_consolidation.mobile.shipment.HUConsolidationShipmentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HUConsolidationTargetCloser
{
	@NonNull private final HUConsolidationLabelPrinter labelPrinter;
	@NonNull private final HUConsolidationShipmentService shipmentService;

	public HUConsolidationJob closeTarget(@NonNull final HUConsolidationJob job)
	{
		final HUConsolidationTarget currentTarget = job.getCurrentTarget();
		if (currentTarget != null && currentTarget.getLuId() != null)
		{
			labelPrinter.printLabel(currentTarget);
			shipmentService.createShipmentForLUs(job, ImmutableSet.of(currentTarget.getLuIdNotNull()));
		}

		return job.withCurrentTarget(null);
	}

	/**
	 * Closes the current target and creates the shipment like {@link #closeTarget(HUConsolidationJob)},
	 * but does NOT run the label printing: it only checks that printing is possible (as today, a failure here fails the close)
	 * and returns the printing, which the caller shall run after its transaction was committed,
	 * so that a print failure reaches the operator instead of being swallowed by an after-commit listener
	 * while the pallet stays closed (same as picking, see PickingJobService#closeLUAndTUPickingTargets).
	 */
	public ClosedTarget closeTargetDeferringLabelPrint(@NonNull final HUConsolidationJob job)
	{
		final HUConsolidationTarget currentTarget = job.getCurrentTarget();
		if (currentTarget != null && currentTarget.getLuId() != null)
		{
			final Runnable printLabelAction = labelPrinter.preparePrintLabel(currentTarget);
			shipmentService.createShipmentForLUs(job, ImmutableSet.of(currentTarget.getLuIdNotNull()));
			return new ClosedTarget(job.withCurrentTarget(null), printLabelAction);
		}

		return new ClosedTarget(job.withCurrentTarget(null), () -> {});
	}

	@Value
	public static class ClosedTarget
	{
		@NonNull HUConsolidationJob job;
		@NonNull Runnable printLabelAction;
	}

	public HUConsolidationJob closeAssumingNoActualTarget(@NonNull final HUConsolidationJob job)
	{
		final HUConsolidationTarget currentTarget = job.getCurrentTarget();
		if (currentTarget != null && currentTarget.getLuId() != null)
		{
			throw new AdempiereException("Having an actual target set is not allowed");
		}

		return job.withCurrentTarget(null);
	}
}
