package de.metas.hu_consolidation.mobile.job;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.report.labels.HULabelConfigQuery;
import de.metas.handlingunits.report.labels.HULabelPrintAction;
import de.metas.handlingunits.report.labels.HULabelPrintRequest;
import de.metas.handlingunits.report.labels.HULabelService;
import de.metas.handlingunits.report.labels.HULabelSourceDocType;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HUConsolidationLabelPrinter
{
	private static final HULabelSourceDocType SOURCE_DOC_TYPE = HULabelSourceDocType.Picking;

	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
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

	/**
	 * @return true if a label config applies to the target's LU, i.e. the label of {@link #preparePrintLabel} is set up
	 */
	public boolean isLabelConfigured(@NonNull final HUConsolidationTarget target)
	{
		final I_M_HU lu = handlingUnitsBL.getById(target.getLuIdNotNull());
		return huLabelService.getFirstMatching(HULabelConfigQuery.builder()
						.sourceDocType(SOURCE_DOC_TYPE)
						.huUnitType(HuUnitType.LU)
						.bpartnerId(BPartnerId.ofRepoIdOrNull(lu.getC_BPartner_ID()))
						.build())
				.isPresent();
	}

	private static HULabelPrintRequest toPrintRequest(@NonNull final HUConsolidationTarget target)
	{
		target.assertPrintable();

		return HULabelPrintRequest.builder()
				.sourceDocType(SOURCE_DOC_TYPE)
				.huId(target.getLuIdNotNull())
				.onlyIfAutoPrint(false)
				.failOnMissingLabelConfig(true)
				// only the pallet's own label must be configured: crates on it without a label config are skipped (as in picking)
				.skipIncludedHUsWithoutLabelConfig(true)
				.build();
	}
}
