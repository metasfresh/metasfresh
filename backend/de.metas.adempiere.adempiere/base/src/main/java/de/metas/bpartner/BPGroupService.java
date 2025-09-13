/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.bpartner;

import de.metas.organization.OrgId;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.DBUniqueConstraintException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BPGroupService
{
	private final BPGroupRepository bpGroupRepository;

	/**
	 * If different threads try to create the same group concurrently, then return just the first created group to everyone.
	 */
	public BPGroupId getOrCreateBPGroup(
			@NonNull final OrgId orgId,
			@NonNull final String groupName)
	{
		Check.assumeNotEmpty(groupName, "groupName is not empty");

		final Optional<BPGroup> optionalBPGroup = bpGroupRepository.getByNameAndOrgId(groupName, orgId);

		final BPGroup bpGroup;
		if (optionalBPGroup.isPresent())
		{
			bpGroup = optionalBPGroup.get();
			bpGroup.setName(groupName);
		}
		else
		{
			bpGroup = BPGroup.of(orgId, null, groupName);
		}

		try
		{
			return bpGroupRepository.save(bpGroup);
		}
		catch (final DBUniqueConstraintException e)
		{
			// meanwhile a group with that name was created, so we can return the existing one
			return bpGroupRepository.getByNameAndOrgId(bpGroup.getName(), bpGroup.getOrgId())
					.map(BPGroup::getId)
					.orElseThrow(() -> e);
		}
	}

}
