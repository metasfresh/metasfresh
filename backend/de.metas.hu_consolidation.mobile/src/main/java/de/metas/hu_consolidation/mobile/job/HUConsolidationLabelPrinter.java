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
	 * Checks right now that the label can be printed (e.g. a label config exists) and returns the printing itself,
	 * to be run by the caller after its transaction was committed, so that a print failure reaches the caller.
	 */
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
