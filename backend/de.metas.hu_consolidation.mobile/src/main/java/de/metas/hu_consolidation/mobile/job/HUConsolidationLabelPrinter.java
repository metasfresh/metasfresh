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
		target.assertPrintable();

		huLabelService.print(HULabelPrintRequest.builder()
				.sourceDocType(HULabelSourceDocType.Picking)
				.huId(target.getLuIdNotNull())
				.onlyIfAutoPrint(false)
				.failOnMissingLabelConfig(true)
				.build());

	}
}
