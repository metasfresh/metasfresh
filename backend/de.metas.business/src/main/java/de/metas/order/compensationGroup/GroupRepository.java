package de.metas.order.compensationGroup;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.ConditionsId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;

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

public interface GroupRepository
{
	Group retrieveGroup(GroupId groupId);

	void saveGroup(Group group);

	GroupCreator.GroupCreatorBuilder prepareNewGroup();

	Group retrieveOrCreateGroup(RetrieveOrCreateGroupRequest request);
	
	@Value
	@Builder
	class RetrieveOrCreateGroupRequest
	{
		@Nullable OrderId orderId;
		@NonNull @Singular ImmutableSet<OrderLineId> orderLineIds;
		@NonNull GroupTemplate newGroupTemplate;
		@Nullable ConditionsId newContractConditionsId;
	}
}
