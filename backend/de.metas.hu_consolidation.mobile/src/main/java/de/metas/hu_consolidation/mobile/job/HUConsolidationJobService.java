package de.metas.hu_consolidation.mobile.job;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.HUToReportWrapper;
import de.metas.handlingunits.report.labels.HULabelPrintRequest;
import de.metas.handlingunits.report.labels.HULabelService;
import de.metas.handlingunits.report.labels.HULabelSourceDocType;
import de.metas.hu_consolidation.mobile.service.HUConsolidationShipmentService;
import de.metas.hu_consolidation.mobile.service.commands.ConsolidateCommand;
import de.metas.hu_consolidation.mobile.service.commands.ConsolidateRequest;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.util.Util;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HUConsolidationJobService
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHUPIItemProductDAO huPIItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	@NonNull private final HUConsolidationJobRepository jobRepository;
	@NonNull private final PickingSlotService pickingSlotService;
	@NonNull private final HULabelService huLabelService;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final HUConsolidationShipmentService shipmentService;

	public HUConsolidationJob getJobById(final HUConsolidationJobId id)
	{
		return jobRepository.getById(id);
	}

	public HUConsolidationJob startJob(
			@NonNull final HUConsolidationJobReference reference,
			@NonNull final UserId responsibleId)
	{
		return jobRepository.create(reference, responsibleId);
	}

	public HUConsolidationJob complete(@NonNull final HUConsolidationJob job)
	{
		throw new UnsupportedOperationException(); // TODO
	}

	public HUConsolidationJob assignJob(@NonNull final HUConsolidationJobId jobId, @NonNull final UserId callerId)
	{
		return jobRepository.updateById(jobId, job -> job.withResponsibleId(callerId));
	}

	public void abort(@NonNull final HUConsolidationJob huConsolidationJob)
	{
		throw new UnsupportedOperationException(); // TODO
	}

	public void abortAll(@NonNull final UserId callerId)
	{
		// TODO
	}

	public List<HUConsolidationTarget> getAvailableTargets(@NonNull final HUConsolidationJob job)
	{
		return handlingUnitsBL.getLUPIs(getTUPIItems(job), job.getCustomerId())
				.stream()
				.map(HUConsolidationTarget::ofNewLU)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ImmutableSet<HuPackingInstructionsItemId> getTUPIItems(@NonNull final HUConsolidationJob job)
	{
		return huPIItemProductDAO.retrieveForBPartner(job.getCustomerId())
				.stream()
				.map(I_M_HU_PI_Item_Product::getM_HU_PI_Item_ID)
				.map(HuPackingInstructionsItemId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public HUConsolidationJob setTarget(@NonNull final HUConsolidationJob job, @Nullable final HUConsolidationTarget target)
	{
		final HUConsolidationJob jobChanged = job.withCurrentTarget(target);
		if (Util.equals(job, jobChanged))
		{
			return job;
		}

		jobRepository.save(jobChanged);
		return jobChanged;
	}

	public HUConsolidationJob closeTarget(@NonNull final HUConsolidationJob job)
	{
		final HUConsolidationTarget currentTarget = job.getCurrentTarget();
		if (currentTarget != null && currentTarget.getLuId() != null)
		{
			printLULabels(ImmutableList.of(currentTarget.getLuId()));
			shipmentService.createShipmentForLUs(job, ImmutableSet.of(currentTarget.getLuId()));
		}

		return setTarget(job, null);
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

	public HUConsolidationJob consolidate(@NonNull final ConsolidateRequest request)
	{
		return ConsolidateCommand.builder()
				.huQRCodesService(huQRCodesService)
				.pickingSlotService(pickingSlotService)
				.request(request)
				.build()
				.execute();
	}

}
