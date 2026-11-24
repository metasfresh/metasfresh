package de.metas.hu_consolidation.mobile.job;

import de.metas.handlingunits.report.labels.HULabelPrintAction;
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
	private static final HULabelSourceDocType SOURCE_DOC_TYPE = HULabelSourceDocType.Picking;

	@NonNull private final HULabelService huLabelService;

	public void printLabel(@NonNull final HUConsolidationTarget target)
	{
		huLabelService.print(toPrintRequest(target));
	}

	/**
	 * Prepares the printing of the target's label, see {@link de.metas.handlingunits.report.labels.HULabelService#preparePrint}.
	 */
	@NonNull
	public HULabelPrintAction preparePrintLabel(@NonNull final HUConsolidationTarget target)
	{
		return huLabelService.preparePrint(toPrintRequest(target));
	}

	private static HULabelPrintRequest toPrintRequest(@NonNull final HUConsolidationTarget target)
	{
		target.assertPrintable();

		return HULabelPrintRequest.builder()
				.sourceDocType(SOURCE_DOC_TYPE)
				.huId(target.getLuIdNotNull())
				.onlyIfAutoPrint(false)
				// a pallet without any label config prints nothing and does not fail, as in picking; crates on it without a label config are skipped
				.failOnMissingLabelConfig(false)
				.skipIncludedHUsWithoutLabelConfig(true)
				.build();
	}
}
