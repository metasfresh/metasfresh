package de.metas.hu_consolidation.mobile.job;

import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.UnaryOperator;

@Repository
public class HUConsolidationJobRepository
{
	private final AtomicInteger nextJobId = new AtomicInteger(1000000);
	private final ConcurrentHashMap<HUConsolidationJobId, HUConsolidationJob> jobs = new ConcurrentHashMap<>();

	public HUConsolidationJob create(
			@NonNull final HUConsolidationJobReference reference,
			@NonNull final UserId responsibleId)
	{
		final HUConsolidationJobId id = HUConsolidationJobId.ofRepoId(this.nextJobId.getAndIncrement());

		final HUConsolidationJob job = HUConsolidationJob.builder()
				.id(id)
				.responsibleId(responsibleId)
				.shipToAddress(reference.getBpartnerLocationId())
				.pickingSlotIds(reference.getPickingSlotIds())
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
		final HUConsolidationJob job = getById(id);

		final HUConsolidationJob jobChanged = mapper.apply(job);
		if (Objects.equals(job, jobChanged))
		{
			return job;
		}

		jobs.put(jobChanged.getId(), jobChanged);
		return jobChanged;
	}

	public void save(final HUConsolidationJob job)
	{
		jobs.put(job.getId(), job);
	}
}
