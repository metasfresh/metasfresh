package de.metas.hu_consolidation.mobile.job;

import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.UnaryOperator;

@Repository
public class HUConsolidationJobRepository
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	// FIXME prototyping
	private final AtomicInteger nextJobId = new AtomicInteger(1000000);
	private final ConcurrentHashMap<HUConsolidationJobId, HUConsolidationJob> jobs = new ConcurrentHashMap<>();

	public HUConsolidationJob create(
			@NonNull final HUConsolidationJobReference reference,
			@NonNull final UserId responsibleId)
	{
		final HUConsolidationJobId id = HUConsolidationJobId.ofRepoId(this.nextJobId.getAndIncrement());

		final HUConsolidationJob job = HUConsolidationJob.builder()
				.id(id)
				.shipToBPLocationId(reference.getBpartnerLocationId())
				.pickingSlotIds(reference.getPickingSlotIds())
				.docStatus(HUConsolidationJobStatus.Drafted)
				.responsibleId(responsibleId)
				.build();

		jobs.put(job.getId(), job);

		return job;
	}

	public HUConsolidationJob getById(final HUConsolidationJobId id)
	{
		final HUConsolidationJob job = jobs.get(id);
		if (job == null)
		{
			throw new AdempiereException("No job found for id=" + id + ". Available in memory jobs are: " + jobs);
		}
		return job;
	}

	public HUConsolidationJob updateById(@NonNull final HUConsolidationJobId id, @NonNull final UnaryOperator<HUConsolidationJob> mapper)
	{
		return trxManager.callInThreadInheritedTrx(() -> {
			final HUConsolidationJob job = getById(id);

			final HUConsolidationJob jobChanged = mapper.apply(job);
			if (Objects.equals(job, jobChanged))
			{
				return job;
			}

			save(jobChanged);
			return jobChanged;
		});
	}

	public void save(final HUConsolidationJob job)
	{
		jobs.put(job.getId(), job);
	}
}
