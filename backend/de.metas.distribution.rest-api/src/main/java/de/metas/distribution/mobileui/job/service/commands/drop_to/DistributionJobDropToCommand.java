package de.metas.distribution.mobileui.job.service.commands.drop_to;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.dao.ValueRestriction;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderQuery;
import de.metas.distribution.ddorder.movement.schedule.commands.drop_to.DDOrderDropToRequest;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveSchedule;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleId;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.distribution.mobileui.external_services.warehouse.DistributionWarehouseService;
import de.metas.distribution.mobileui.job.model.DistributionJob;
import de.metas.distribution.mobileui.job.model.DistributionJobId;
import de.metas.distribution.mobileui.job.model.DistributionJobStep;
import de.metas.distribution.mobileui.job.model.DistributionJobStepId;
import de.metas.distribution.mobileui.job.service.DistributionJobLoader;
import de.metas.distribution.mobileui.job.service.DistributionJobLoaderSupportingServices;
import de.metas.distribution.mobileui.job.service.DistributionJobQueries;
import de.metas.distribution.mobileui.job.service.DistributionRestService;
import de.metas.scannable_code.ScannedCode;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;

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
	@NonNull private final DistributionWarehouseService warehouseService;

	// Params
	@NonNull private final UserId userId;
	@Nullable final DistributionJobId onlyJobId;
	@Nullable private final DistributionJobStepId onlyStepId;
	@Nullable private final LocatorId inTransitLocatorId;
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
			@NonNull final DistributionWarehouseService warehouseService,
			//
			@NonNull final UserId userId,
			@Nullable final DistributionJobId onlyJobId,
			@Nullable final DistributionJobStepId onlyStepId,
			@Nullable final LocatorId inTransitLocatorId,
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
		this.warehouseService = warehouseService;

		this.userId = userId;
		this.onlyJobId = onlyJobId;
		this.onlyStepId = onlyStepId;
		this.inTransitLocatorId = inTransitLocatorId;
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

		final ImmutableList<DDOrderMoveSchedule> schedules = dropToLocator(scheduleIds);

		jobs = updateJobsFromSchedules(jobs, schedules);

		if (completeJobsIfFullyMoved)
		{
			jobs = tryCompleteJobsIfFullyMoved(jobs);
		}

		return DistributionJobDropToResponse.ofList(jobs);
	}

	private ImmutableList<DDOrderMoveSchedule> dropToLocator(@NonNull final Set<DDOrderMoveScheduleId> scheduleIds)
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
		final Set<DDOrderId> onlyDDOrderIds = getOnlyDDOrderIds();

		final DDOrderQuery.DDOrderQueryBuilder queryBuilder = DistributionJobQueries.newDDOrdersQuery()
				.onlyDDOrderIds(onlyDDOrderIds);
		if (onlyDDOrderIds.isEmpty())
		{
			queryBuilder.responsibleId(ValueRestriction.equalsTo(userId));
		}
		else
		{
			queryBuilder.responsibleId(ValueRestriction.equalsToOrNull(userId));
		}

		final List<DistributionJob> jobs = distributionJobService.listJobs(queryBuilder.build());
		if (jobs.isEmpty())
		{
			throw new AdempiereException("Nothing to move"); // TODO trl
		}

		jobs.forEach(job -> {
			if (job.getResponsibleId() != null)
			{
				job.assertCanEdit(userId);
			}
		});

		return jobs;
	}

	@NonNull
	private Set<DDOrderId> getOnlyDDOrderIds()
	{
		if (onlyJobId != null)
		{
			return ImmutableSet.of(onlyJobId.toDDOrderId());
		}
		else if (inTransitLocatorId != null)
		{
			final Set<DDOrderId> onlyDDOrderIds = ddOrderMoveScheduleService.retrieveDDOrderIdsInTransit(inTransitLocatorId);
			if (onlyDDOrderIds.isEmpty())
			{
				throw new AdempiereException("Nothing to move"); // TODO trl
			}
			return onlyDDOrderIds;
		}
		else
		{
			return ImmutableSet.of();
		}
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
		return job.streamSteps()
				.filter(this::isStepEligible)
				.map(DistributionJobStep::getScheduleId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private boolean isStepEligible(final DistributionJobStep step)
	{
		if (onlyStepId != null && !DistributionJobStepId.equals(step.getId(), onlyStepId))
		{
			return false;
		}

		if (!step.isInTransit())
		{
			if (onlyStepId != null)
			{
				throw new AdempiereException("Step " + onlyStepId + " is not in transit"); // shall not happen
			}

			return false;
		}

		//noinspection RedundantIfStatement
		if (inTransitLocatorId != null && !LocatorId.equals(step.getInTransitLocatorId(), inTransitLocatorId))
		{
			return false;
		}

		return true;
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
			dropToLocatorId = this._dropToLocatorId = warehouseService.resolveLocator(dropToQRCode).getLocatorId();
		}
		return dropToLocatorId;
	}

	private ImmutableList<DistributionJob> updateJobsFromSchedules(final List<DistributionJob> jobs, final ImmutableList<DDOrderMoveSchedule> schedules)
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
