package de.metas.hu_consolidation.mobile.job;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.report.HUToReportWrapper;
import de.metas.handlingunits.report.labels.HULabelPrintRequest;
import de.metas.handlingunits.report.labels.HULabelService;
import de.metas.handlingunits.report.labels.HULabelSourceDocType;
import de.metas.hu_consolidation.mobile.shipment.HUConsolidationShipmentService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HUConsolidationTargetCloser
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final HULabelService huLabelService;
	@NonNull private final HUConsolidationShipmentService shipmentService;

	public HUConsolidationJob closeTarget(@NonNull final HUConsolidationJob job)
	{
		final HUConsolidationTarget currentTarget = job.getCurrentTarget();
		if (currentTarget != null && currentTarget.getLuId() != null)
		{
			printLULabels(ImmutableList.of(currentTarget.getLuId()));
			shipmentService.createShipmentForLUs(job, ImmutableSet.of(currentTarget.getLuId()));
		}

		return job.withCurrentTarget(null);
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

	private void printLULabels(@NonNull final Collection<HuId> luIds)
	{
		final List<I_M_HU> lus = handlingUnitsBL.getByIds(luIds);
		if (lus.isEmpty())
		{
			return;
		}

		huLabelService.print(HULabelPrintRequest.builder()
				.sourceDocType(HULabelSourceDocType.Picking)
				.hus(HUToReportWrapper.ofList(lus))
				.onlyIfAutoPrint(true)
				.failOnMissingLabelConfig(false)
				.build());
	}
}
