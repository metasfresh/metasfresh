package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.distribution.ddorder.movement.schedule.DDOrderDropToRequest;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveSchedule;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleId;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.scannable_code.ScannedCode;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.qrcode.resolver.LocatorScannedCodeResolverService;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

public class DistributionJobDropToCommand
{
	// Services
	@NonNull private final ITrxManager trxManager;
	@NonNull private final DistributionRestService distributionJobService;
	@NonNull private final DDOrderMoveScheduleService ddOrderMoveScheduleService;
	@NonNull private final DistributionJobLoaderSupportingServices loadingSupportServices;
	@NonNull private final LocatorScannedCodeResolverService locatorScannedCodeResolver;

	// Params
	@NonNull private final UserId userId;
	@Nullable final DistributionJobId onlyJobId;
	@Nullable private final DistributionJobStepId onlyStepId;
	@Nullable private final ScannedCode dropToQRCode;
	private final boolean completeJobsIfFullyMoved;

	// State
	private LocatorId _dropToLocatorId;

	@Builder
	public DistributionJobDropToCommand(
			@NonNull final ITrxManager trxManager,
			@NonNull final DistributionRestService distributionJobService,
			@NonNull final DDOrderMoveScheduleService ddOrderMoveScheduleService,
			@NonNull final DistributionJobLoaderSupportingServices loadingSupportServices,
			@NonNull final LocatorScannedCodeResolverService locatorScannedCodeResolver,
			//
			@NonNull final UserId userId,
			@Nullable final DistributionJobId onlyJobId,
			@Nullable final DistributionJobStepId onlyStepId,
			@Nullable final ScannedCode dropToQRCode,
			final boolean completeJobsIfFullyMoved)
	{
		if (onlyStepId != null && onlyJobId == null)
		{
			throw new AdempiereException("onlyJobId is mandatory if onlyStepId is set");
		}

		this.trxManager = trxManager;
		this.distributionJobService = distributionJobService;
		this.ddOrderMoveScheduleService = ddOrderMoveScheduleService;
		this.loadingSupportServices = loadingSupportServices;
		this.locatorScannedCodeResolver = locatorScannedCodeResolver;

		this.userId = userId;
		this.onlyJobId = onlyJobId;
		this.onlyStepId = onlyStepId;
		this.dropToQRCode = dropToQRCode;
		this.completeJobsIfFullyMoved = completeJobsIfFullyMoved;
	}

	public DistributionJobDropToResponse execute()
	{
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private DistributionJobDropToResponse executeInTrx()
	{
		List<DistributionJob> jobs = retrieveJobs();
		final ImmutableSet<DDOrderMoveScheduleId> scheduleIds = getScheduleIds(jobs);

		final List<DDOrderMoveSchedule> schedules = dropToLocator(scheduleIds);

		jobs = updateJobsFromSchedules(jobs, schedules);

		if (completeJobsIfFullyMoved)
		{
			jobs = tryCompleteJobsIfFullyMoved(jobs);
		}

		return DistributionJobDropToResponse.ofList(jobs);
	}

	private List<DDOrderMoveSchedule> dropToLocator(@NonNull final Set<DDOrderMoveScheduleId> scheduleIds)
	{
		Check.assumeNotEmpty(scheduleIds, "scheduleIds is not empty");

		return ddOrderMoveScheduleService.dropTo(
				DDOrderDropToRequest.builder()
						.scheduleIds(scheduleIds)
						.dropToLocatorId(getDropToLocatorId())
						.build()
		);
	}

	private List<DistributionJob> retrieveJobs()
	{
		final List<DistributionJob> jobs;

		if (onlyJobId != null)
		{
			final DistributionJob job = distributionJobService.getJobById(onlyJobId);
			jobs = ImmutableList.of(job);
		}
		else
		{
			jobs = distributionJobService.listJobs(DistributionJobQueries.ddOrdersAssignedToUser(userId));
		}

		if (jobs.isEmpty())
		{
			throw new AdempiereException("Nothing to move");
		}

		jobs.forEach(job -> job.assertCanEdit(userId));

		return jobs;
	}

	private ImmutableSet<DDOrderMoveScheduleId> getScheduleIds(final List<DistributionJob> jobs)
	{
		Check.assumeNotEmpty(jobs, "jobs is not empty");

		final ImmutableSet<DDOrderMoveScheduleId> scheduleIds = jobs.stream()
				.flatMap(job -> getScheduleIds(job).stream())
				.collect(ImmutableSet.toImmutableSet());
		if (scheduleIds.isEmpty())
		{
			throw new AdempiereException("Nothing to move");
		}

		return scheduleIds;
	}

	private ImmutableSet<DDOrderMoveScheduleId> getScheduleIds(final DistributionJob job)
	{
		if (onlyJobId != null)
		{
			if (!DistributionJobId.equals(job.getId(), onlyJobId))
			{
				return ImmutableSet.of(); // shall not happen
			}

			if (onlyStepId != null)
			{
				final DistributionJobStep step = job.getStepById(onlyStepId);
				if (!step.isInTransit())
				{
					throw new AdempiereException("Step " + onlyStepId + " is not in transit"); // shall not happen
				}
				return ImmutableSet.of(step.getScheduleId());
			}
			else
			{
				return job.getInTransitScheduleIds();
			}
		}
		else
		{
			return job.getInTransitScheduleIds();
		}
	}

	@Nullable
	private LocatorId getDropToLocatorId()
	{
		if (dropToQRCode == null)
		{
			return null;
		}

		LocatorId dropToLocatorId = this._dropToLocatorId;
		if (dropToLocatorId == null)
		{
			dropToLocatorId = this._dropToLocatorId = locatorScannedCodeResolver.resolve(dropToQRCode).getLocatorId();
		}
		return dropToLocatorId;
	}

	private List<DistributionJob> updateJobsFromSchedules(final List<DistributionJob> jobs, final List<DDOrderMoveSchedule> schedules)
	{
		final ImmutableMap<DistributionJobStepId, DDOrderMoveSchedule> schedulesByStepId = Maps.uniqueIndex(schedules, schedule -> DistributionJobStepId.ofScheduleId(schedule.getId()));

		return jobs.stream()
				.map(job -> updateJob(job, schedulesByStepId))
				.collect(ImmutableList.toImmutableList());
	}

	private DistributionJob updateJob(final DistributionJob job, final ImmutableMap<DistributionJobStepId, DDOrderMoveSchedule> schedulesByStepId)
	{
		return job.withChangedSteps(step -> {
			final DDOrderMoveSchedule schedule = schedulesByStepId.get(step.getId());
			return schedule != null
					? DistributionJobLoader.toDistributionJobStep(schedule, loadingSupportServices)
					: step;
		});
	}

	private List<DistributionJob> tryCompleteJobsIfFullyMoved(@NonNull final List<DistributionJob> jobs)
	{
		return jobs.stream()
				.map(this::completeJobIfFullyMoved)
				.collect(ImmutableList.toImmutableList());
	}

	private DistributionJob completeJobIfFullyMoved(@NonNull final DistributionJob job)
	{
		if (!job.isFullyMoved())
		{
			return job;
		}

		return distributionJobService.complete(job);
	}
}
