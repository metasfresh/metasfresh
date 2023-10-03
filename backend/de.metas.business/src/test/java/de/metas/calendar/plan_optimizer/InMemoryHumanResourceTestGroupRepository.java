package de.metas.calendar.plan_optimizer;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.resource.HumanResourceTestGroup;
import de.metas.resource.HumanResourceTestGroupId;
import de.metas.resource.HumanResourceTestGroupRepository;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.time.Duration;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

class InMemoryHumanResourceTestGroupRepository implements HumanResourceTestGroupRepository
{
	private final AtomicInteger nextRepoId = new AtomicInteger(1);
	private final HashMap<HumanResourceTestGroupId, HumanResourceTestGroup> map = new HashMap<>();

	@SuppressWarnings("SameParameterValue")
	public HumanResourceTestGroupId createHumanResourceTestGroup(int capacityInHours)
	{
		final HumanResourceTestGroup humanResourceTestGroup = HumanResourceTestGroup.builder()
				.id(HumanResourceTestGroupId.ofRepoId(nextRepoId.getAndIncrement()))
				.orgId(OrgId.ANY)
				.groupIdentifier("N/A")
				.name("N/A")
				.department("N/A")
				.weeklyCapacity(Duration.ofHours(capacityInHours))
				.isActive(true)
				.build();

		map.put(humanResourceTestGroup.getId(), humanResourceTestGroup);
		return humanResourceTestGroup.getId();
	}

	@Override
	public @NonNull ImmutableList<HumanResourceTestGroup> getAll()
	{
		return ImmutableList.copyOf(map.values());
	}

	@Override
	public @NonNull ImmutableList<HumanResourceTestGroup> getByIds(@NonNull final Set<HumanResourceTestGroupId> ids)
	{
		return ids.stream().map(this::getById).collect(ImmutableList.toImmutableList());
	}

	private @NonNull HumanResourceTestGroup getById(@NonNull final HumanResourceTestGroupId id)
	{
		final HumanResourceTestGroup group = map.get(id);
		if (group == null)
		{
			throw new AdempiereException("No group found for " + id);
		}
		return group;
	}
}
