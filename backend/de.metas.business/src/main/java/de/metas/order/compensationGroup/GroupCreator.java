package de.metas.order.compensationGroup;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.ConditionsId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.GroupRepository.RetrieveOrCreateGroupRequest;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Collection;

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
	// services
	private final GroupRepository groupsRepo;
	private final GroupCompensationLineCreateRequestFactory compensationLineCreateRequestFactory;

	private final GroupTemplate groupTemplate;

	@Builder
	private GroupCreator(
			@NonNull final GroupRepository groupsRepo,
			@NonNull final GroupCompensationLineCreateRequestFactory compensationLineCreateRequestFactory,
			//
			@NonNull final GroupTemplate groupTemplate)
	{
		this.groupsRepo = groupsRepo;
		this.compensationLineCreateRequestFactory = compensationLineCreateRequestFactory;

		this.groupTemplate = groupTemplate;
	}

	public static class GroupCreatorBuilder
	{
		public Group createGroup(@NonNull final Collection<OrderLineId> lineIdsToGroup)
		{
			return build().createGroup(lineIdsToGroup, null, null);
		}

		public Group createGroup(
				@NonNull final OrderId orderId,
				@Nullable final ConditionsId conditionsId)
		{
			return build().createGroup(null, orderId, conditionsId);
		}

		public void recreateGroup(@NonNull final Group group) {build().recreateGroup(group);}
	}

	public Group createGroup(
			@Nullable final Collection<OrderLineId> lineIdsToGroup,
			@Nullable final OrderId orderId,
			@Nullable final ConditionsId contractConditionsId)
	{
		final Group group = groupsRepo.retrieveOrCreateGroup(
				RetrieveOrCreateGroupRequest.builder()
						.orderId(orderId)
						.orderLineIds(lineIdsToGroup != null ? ImmutableSet.copyOf(lineIdsToGroup) : ImmutableSet.of())
						.newGroupTemplate(groupTemplate)
						.newContractConditionsId(contractConditionsId)
						.build());

		recreateGroup(group);

		return group;
	}

	public void recreateGroup(@NonNull final Group group)
	{
		group.removeAllGeneratedLines();

		groupTemplate.getCompensationLines()
				.stream()
				.filter(compensationLineTemplate -> compensationLineTemplate.isMatching(group))
				.map(templateLine -> compensationLineCreateRequestFactory.createGroupCompensationLineCreateRequest(templateLine, group))
				.forEach(group::addNewCompensationLine);

		group.updateAllCompensationLines();
		groupsRepo.saveGroup(group);
	}
}
