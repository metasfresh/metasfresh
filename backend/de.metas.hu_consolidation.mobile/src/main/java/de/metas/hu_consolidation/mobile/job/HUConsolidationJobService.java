package de.metas.hu_consolidation.mobile.job;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.picking.slot.PickingSlotService;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.hu_consolidation.mobile.job.commands.abort.AbortCommand;
import de.metas.hu_consolidation.mobile.job.commands.complete.CompleteCommand;
import de.metas.hu_consolidation.mobile.job.commands.consolidate.ConsolidateCommand;
import de.metas.hu_consolidation.mobile.job.commands.consolidate.ConsolidateRequest;
import de.metas.hu_consolidation.mobile.job.commands.get_pickingslot_content.GetPickingSlotContentCommand;
import de.metas.hu_consolidation.mobile.job.commands.set_target.SetTargetCommand;
import de.metas.hu_consolidation.mobile.job.scan.HUConsolidationGRAIPresence;
import de.metas.hu_consolidation.mobile.job.scan.HUConsolidationJobCrates;
import de.metas.hu_consolidation.mobile.job.scan.HUConsolidationScannedCodeResolvers;
import de.metas.hu_consolidation.mobile.job.scan.HUConsolidationScannedCrate;
import de.metas.hu_consolidation.mobile.rest_api.json.JsonHUConsolidationJobPickingSlotContent;
import de.metas.picking.api.PickingSlotId;
import de.metas.user.UserId;
import de.metas.scannable_code.ScannedCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class HUConsolidationJobService
{
	@NonNull private final HUConsolidationJobRepository jobRepository;
	@NonNull private final PickingSlotService pickingSlotService;
	@NonNull private final HUConsolidationGRAIPresence graiPresence;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final HUConsolidationAvailableTargetsFinder availableTargetsFinder;
	@NonNull private final HUConsolidationTargetCloser targetCloser;
	@NonNull private final HUConsolidationLabelPrinter labelPrinter;
	@NonNull private final HUConsolidationScannedCodeResolvers scannedCodeResolvers;

	public HUConsolidationJob getJobById(final HUConsolidationJobId id)
	{
		return jobRepository.getById(id);
	}

	public List<HUConsolidationJob> getByNotProcessedAndResponsibleId(@NonNull final UserId userId)
	{
		return jobRepository.getByNotProcessedAndResponsibleId(userId);
	}

	public ImmutableSet<PickingSlotId> getInUsePickingSlotIds()
	{
		return jobRepository.getInUsePickingSlotIds();
	}

	public HUConsolidationJob startJob(
			@NonNull final HUConsolidationJobReference reference,
			@NonNull final UserId responsibleId)
	{
		return jobRepository.create(reference, responsibleId);
	}

	public HUConsolidationJob complete(@NonNull final HUConsolidationJob job)
	{
		return CompleteCommand.builder()
				.jobRepository(jobRepository)
				.targetCloser(targetCloser)
				//
				.job(job)
				//
				.build().execute();
	}

	public HUConsolidationJob assignJob(@NonNull final HUConsolidationJobId jobId, @NonNull final UserId callerId)
	{
		return jobRepository.updateById(jobId, job -> job.withResponsibleId(callerId));
	}

	public void abort(@NonNull final HUConsolidationJobId jobId, @NonNull final UserId callerId)
	{
		AbortCommand.builder()
				.jobRepository(jobRepository)
				.targetCloser(targetCloser)
				//
				.jobId(jobId)
				.callerId(callerId)
				//
				.build().execute();
	}

	public void abortAll(@NonNull final UserId callerId)
	{
		// TODO
	}

	public List<HUConsolidationTarget> getAvailableTargets(@NonNull final HUConsolidationJob job)
	{
		return availableTargetsFinder.getAvailableTargets(job.getCustomerId());
	}

	public HUConsolidationJob setTarget(@NonNull final HUConsolidationJobId jobId, @Nullable final HUConsolidationTarget target, @NonNull UserId callerId)
	{
		return SetTargetCommand.builder()
				.jobRepository(jobRepository)
				//
				.callerId(callerId)
				.jobId(jobId)
				.target(target)
				//
				.build().execute();
	}

	public HUConsolidationJob closeTarget(@NonNull final HUConsolidationJobId jobId, @NonNull final UserId callerId)
	{
		// the label printing prepared inside the transaction by the mapper; stays a no-op when this call did not close a target
		final AtomicReference<Runnable> printLabelActionRef = new AtomicReference<>(() -> {});

		final HUConsolidationJob jobClosed = jobRepository.updateById(jobId, job -> {
			job.assertUserCanEdit(callerId);
			final HUConsolidationTargetCloser.ClosedTarget closed = targetCloser.closeTargetDeferringLabelPrint(job);
			printLabelActionRef.set(closed.getPrintLabelAction());
			return closed.getJob();
		});

		// Print after the close was committed, so that a print failure is shown to the operator while the pallet stays closed
		printLabelActionRef.get().run();

		return jobClosed;
	}

	public void printTargetLabel(@NonNull final HUConsolidationJobId jobId, @NotNull final UserId callerId)
	{
		final HUConsolidationJob job = jobRepository.getById(jobId);
		final HUConsolidationTarget currentTarget = job.getCurrentTargetNotNull();
		labelPrinter.printLabel(currentTarget);
	}

	public HUConsolidationJob consolidate(@NonNull final ConsolidateRequest request)
	{
		return ConsolidateCommand.builder()
				.jobRepository(jobRepository)
				.huQRCodesService(huQRCodesService)
				.pickingSlotService(pickingSlotService)
				.request(request)
				.build()
				.execute();
	}

	/**
	 * Consolidates the crate that the scanned code resolves to (searched in all picking slots of the job) into the current target,
	 * using the same path as a tap on the crate in its slot.
	 */
	public HUConsolidationJob consolidateByScan(
			@NonNull final HUConsolidationJobId jobId,
			@NonNull final ScannedCode scannedCode,
			@NonNull final UserId callerId)
	{
		final HUConsolidationJob job = getJobById(jobId);
		job.assertUserCanEdit(callerId);

		final HUConsolidationJobCrates jobCrates = HUConsolidationJobCrates.load(job.getPickingSlotIds(), pickingSlotService);
		final HUConsolidationScannedCrate crate = scannedCodeResolvers.resolve(scannedCode, jobCrates);

		return consolidate(ConsolidateRequest.builder()
				.callerId(callerId)
				.jobId(jobId)
				.fromPickingSlotId(crate.getPickingSlotId())
				.huId(crate.getHuId())
				.build());
	}

	public JsonHUConsolidationJobPickingSlotContent getPickingSlotContent(final HUConsolidationJobId jobId, final PickingSlotId pickingSlotId)
	{
		return GetPickingSlotContentCommand.builder()
				.jobRepository(jobRepository)
				.huQRCodesService(huQRCodesService)
				.pickingSlotService(pickingSlotService)
				.graiPresence(graiPresence)
				.jobId(jobId)
				.pickingSlotId(pickingSlotId)
				.build()
				.execute();
	}
}
