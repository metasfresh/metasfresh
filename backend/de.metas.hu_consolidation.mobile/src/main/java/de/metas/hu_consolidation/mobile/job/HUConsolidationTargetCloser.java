package de.metas.hu_consolidation.mobile.job;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.report.labels.HULabelPrintAction;
import de.metas.hu_consolidation.mobile.shipment.HUConsolidationShipmentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class HUConsolidationTargetCloser
{
	@NonNull private final HUConsolidationLabelPrinter labelPrinter;
	@NonNull private final HUConsolidationShipmentService shipmentService;

	public HUConsolidationJob closeTarget(@NonNull final HUConsolidationJob job)
	{
		return close(job, target -> {
			labelPrinter.printLabel(target);
			return HULabelPrintAction.NOTHING;
		}).getJob();
	}

	/**
	 * Like {@link #closeTarget(HUConsolidationJob)}, but does not print the label: the label printing is returned in {@link CloseTargetResult}.
	 *
	 * @throws org.adempiere.exceptions.AdempiereException before the shipment is created, if the label cannot be printed
	 */
	public CloseTargetResult closeTargetDeferringLabelPrint(@NonNull final HUConsolidationJob job)
	{
		return close(job, labelPrinter::preparePrintLabel);
	}

	/**
	 * @param labelPrintStep called first, with the LU target to be closed, before the shipment is created;
	 *                       either prints now or prepares the print; returns the print that is still to run (a no-op if none)
	 */
	private CloseTargetResult close(
			@NonNull final HUConsolidationJob job,
			@NonNull final Function<HUConsolidationTarget, HULabelPrintAction> labelPrintStep)
	{
		final HUConsolidationTarget currentTarget = job.getCurrentTarget();
		if (currentTarget != null && currentTarget.getLuId() != null)
		{
			final HULabelPrintAction printLabelAction = labelPrintStep.apply(currentTarget);
			shipmentService.createShipmentForLUs(job, ImmutableSet.of(currentTarget.getLuIdNotNull()));
			return new CloseTargetResult(job.withCurrentTarget(null), printLabelAction);
		}

		return new CloseTargetResult(job.withCurrentTarget(null), HULabelPrintAction.NOTHING);
	}

	@Value
	public static class CloseTargetResult
	{
		@NonNull HUConsolidationJob job;
		@NonNull HULabelPrintAction printLabelAction;
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
