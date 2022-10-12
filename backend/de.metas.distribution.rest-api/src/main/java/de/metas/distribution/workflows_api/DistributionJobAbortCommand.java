package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.ad.trx.api.ITrxManager;

import java.util.List;

public class DistributionJobAbortCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final DDOrderService ddOrderService;
	private final DistributionJobHUReservationService distributionJobHUReservationService;

	private final ImmutableList<DistributionJob> jobs;

	@Builder
	private DistributionJobAbortCommand(
			@NonNull final DDOrderService ddOrderService,
			@NonNull final DistributionJobHUReservationService distributionJobHUReservationService,
			@NonNull @Singular final List<DistributionJob> jobs)
	{
		this.ddOrderService = ddOrderService;
		this.distributionJobHUReservationService = distributionJobHUReservationService;
		this.jobs = ImmutableList.copyOf(jobs);
	}

	public static class DistributionJobAbortCommandBuilder
	{
		public void execute() {build().execute();}
	}

	public void execute()
	{
		if (jobs.isEmpty())
		{
			return;
		}

		trxManager.runInThreadInheritedTrx(this::executeInTrx);
	}

	private void executeInTrx()
	{
		for (final DistributionJob job : jobs)
		{
			abort(job);
		}
	}

	private void abort(final DistributionJob job)
	{
		distributionJobHUReservationService.releaseAllReservations(job);
		ddOrderService.unassignFromResponsible(job.getDdOrderId());
	}
}
