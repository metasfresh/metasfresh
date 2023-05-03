package de.metas.resource;

import com.google.common.collect.ImmutableList;
import org.springframework.stereotype.Service;

@Service
public class HumanResourceTestGroupService
{
	private final HumanResourceTestGroupRepository humanResourceTestGroupRepository;

	public HumanResourceTestGroupService(final HumanResourceTestGroupRepository humanResourceTestGroupRepository) {this.humanResourceTestGroupRepository = humanResourceTestGroupRepository;}

	public ImmutableList<HumanResourceTestGroup> getAll()
	{
		return humanResourceTestGroupRepository.getAll();
	}
}
