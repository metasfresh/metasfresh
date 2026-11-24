package de.metas.hu_consolidation.mobile.job;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.report.labels.HULabelPrintAction;
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

	/**
	 * Closes the current target (if it is an actual LU): prepares its label print, then creates the shipment for it.
	 * The label is not printed here: the print is returned in {@link CloseTargetResult}, to be run after the close was committed.
	 * <p>
	 * A label that cannot be printed (e.g. no label config applies to the LU) does not prevent closing the target:
	 * the returned print then throws that error, so the operator gets it while the target stays closed.
	 */
	public CloseTargetResult closeTarget(@NonNull final HUConsolidationJob job)
	{
		final HUConsolidationTarget currentTarget = job.getCurrentTarget();
		if (currentTarget != null && currentTarget.getLuId() != null)
		{
			final HULabelPrintAction printLabelAction = preparePrintLabel(currentTarget);
			shipmentService.createShipmentForLUs(job, ImmutableSet.of(currentTarget.getLuIdNotNull()));
			return new CloseTargetResult(job.withCurrentTarget(null), printLabelAction);
		}

		return new CloseTargetResult(job.withCurrentTarget(null), HULabelPrintAction.NOTHING);
	}

	@NonNull
	private HULabelPrintAction preparePrintLabel(@NonNull final HUConsolidationTarget target)
	{
		try
		{
			return labelPrinter.preparePrintLabel(target);
		}
		catch (final AdempiereException labelError)
		{
			return () -> {
				throw labelError;
			};
		}
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
