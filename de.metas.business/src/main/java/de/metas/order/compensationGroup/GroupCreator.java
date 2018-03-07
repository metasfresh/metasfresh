package de.metas.order.compensationGroup;

import java.util.Collection;

import org.adempiere.util.Check;

import de.metas.order.compensationGroup.GroupRepository.RetrieveOrCreateGroupRequest;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

public final class GroupCreator
{
	private final GroupRepository groupsRepo;
	private final GroupCompensationLineCreateRequestFactory compensationLineCreateRequestFactory;

	private Collection<Integer> lineIdsToGroup;
	private GroupTemplate _groupTemplate;

	public GroupCreator(
			@NonNull final GroupRepository groupsRepo,
			@NonNull final GroupCompensationLineCreateRequestFactory compensationLineCreateRequestFactory)
	{
		this.groupsRepo = groupsRepo;
		this.compensationLineCreateRequestFactory = compensationLineCreateRequestFactory;
	}

	public GroupCreator linesToGroup(@NonNull final Collection<Integer> lineIdsToGroup)
	{
		Check.assumeNotEmpty(lineIdsToGroup, "lineIdsToGroup is not empty");
		this.lineIdsToGroup = lineIdsToGroup;
		return this;
	}

	public GroupCreator groupTemplate(final GroupTemplate newGroupTemplate)
	{
		this._groupTemplate = newGroupTemplate;
		return this;
	}

	private GroupTemplate getGroupTemplate()
	{
		Check.assumeNotNull(_groupTemplate, "Parameter _groupTemplate is not null");
		return _groupTemplate;
	}

	public Group createGroup()
	{
		final Group group = groupsRepo.retrieveOrCreateGroup(createRetrieveOrCreateGroupRequest());
		return recreateGroup(group);
	}

	public Group recreateGroup(@NonNull final Group group)
	{
		group.removeAllGeneratedCompensationLines();

		getGroupTemplate()
				.getLines()
				.stream()
				.filter(templateLine -> templateLine.getGroupMatcher().test(group))
				.map(templateLine -> compensationLineCreateRequestFactory.createGroupCompensationLineCreateRequest(templateLine, group))
				.forEach(group::addNewCompensationLine);

		group.moveAllManualCompensationLinesToEnd();

		groupsRepo.saveGroup(group);
		return group;
	}

	private RetrieveOrCreateGroupRequest createRetrieveOrCreateGroupRequest()
	{
		return RetrieveOrCreateGroupRequest.builder()
				.orderLineIds(lineIdsToGroup)
				.newGroupTemplate(getGroupTemplate())
				.build();
	}
}
