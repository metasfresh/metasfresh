package de.metas.hu_consolidation.mobile.job;

import de.metas.handlingunits.report.labels.HULabelPrintRequest;
import de.metas.handlingunits.report.labels.HULabelService;
import de.metas.handlingunits.report.labels.HULabelSourceDocType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HUConsolidationLabelPrinter
{
	@NonNull private final HULabelService huLabelService;

	public void printLabel(@NonNull final HUConsolidationTarget target)
	{
		huLabelService.print(toPrintRequest(target));
	}

	/**
	 * Validates that the target's label can be printed (e.g. a label config exists) and returns the printing without running it.
	 * Running the returned action prints immediately and throws if the printing fails.
	 *
	 * @throws org.adempiere.exceptions.AdempiereException if the label cannot be printed
	 */
	@NonNull
	public Runnable preparePrintLabel(@NonNull final HUConsolidationTarget target)
	{
		return huLabelService.preparePrint(toPrintRequest(target));
	}

	private static HULabelPrintRequest toPrintRequest(@NonNull final HUConsolidationTarget target)
	{
		target.assertPrintable();

		return HULabelPrintRequest.builder()
				.sourceDocType(HULabelSourceDocType.Picking)
				.huId(target.getLuIdNotNull())
				.onlyIfAutoPrint(false)
				.failOnMissingLabelConfig(true)
				.build();
	}
}
