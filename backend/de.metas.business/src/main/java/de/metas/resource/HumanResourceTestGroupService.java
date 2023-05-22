package de.metas.resource;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class HumanResourceTestGroupService
{
	private final HumanResourceTestGroupRepository humanResourceTestGroupRepository;

	public HumanResourceTestGroupService(final HumanResourceTestGroupRepository humanResourceTestGroupRepository) {this.humanResourceTestGroupRepository = humanResourceTestGroupRepository;}

	@NonNull
	public ImmutableList<HumanResourceTestGroup> getAll()
	{
		return humanResourceTestGroupRepository.getAll();
	}

	@NonNull
	public ImmutableList<HumanResourceTestGroup> getByIds(@NonNull final Set<HumanResourceTestGroupId> ids)
	{
		return humanResourceTestGroupRepository.getByIds(ids);
	}
}
