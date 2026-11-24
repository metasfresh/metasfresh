package de.metas.hu_consolidation.mobile.job;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.report.labels.HULabelPrintAction;
import de.metas.hu_consolidation.mobile.shipment.HUConsolidationShipmentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HUConsolidationTargetCloser
{
	@NonNull private final HUConsolidationLabelPrinter labelPrinter;
	@NonNull private final HUConsolidationShipmentService shipmentService;

	/**
	 * Closes the current target (if it is an actual LU): prepares its label print, then creates the shipment for it.
	 * The label is not printed: the returned {@link CloseTargetResult} holds the print.
	 * <p>
	 * A label print that cannot be prepared (e.g. no label config applies to the LU) does not prevent closing the target:
	 * the returned print then throws {@link HUConsolidationLabelNotPrintedException}.
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
		catch (final DBException dbError)
		{
			// the transaction is broken: closing the target cannot continue
			throw dbError;
		}
		catch (final AdempiereException labelError)
		{
			// e.g. no label config applies; thrown when the returned print runs
			return () -> {
				throw HUConsolidationLabelNotPrintedException.noLabelConfig(labelError);
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
