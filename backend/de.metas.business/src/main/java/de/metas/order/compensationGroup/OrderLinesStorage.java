/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.order.compensationGroup;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.order.OrderLineId;
import de.metas.product.acct.api.ActivityId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_OrderLine;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

final class OrderLinesStorage
{
	@Getter
	private final GroupId groupId;
	private final ActivityId activityId;
	private final boolean performDatabaseChanges;
	private final ImmutableMap<OrderLineId, I_C_OrderLine> orderLinesById;

	private static final ModelDynAttributeAccessor<I_C_OrderLine, Boolean> ATTR_IsRepoUpdate = new ModelDynAttributeAccessor<>(
			OrderGroupRepository.class.getName(),
			"IsRepoUpdate",
			Boolean.class);

	@Builder
	private OrderLinesStorage(
			@NonNull final GroupId groupId,
			@Nullable final ActivityId activityId,
			@NonNull @Singular final List<I_C_OrderLine> orderLines,
			@NonNull final Boolean performDatabaseChanges)
	{
		this.groupId = groupId;
		this.activityId = activityId;
		this.performDatabaseChanges = performDatabaseChanges;
		orderLinesById = orderLines.stream()
				.peek(OrderGroupCompensationUtils::assertCompensationLine)
				.collect(GuavaCollectors.toImmutableMapByKey(orderLineRecord -> OrderLineId.ofRepoId(orderLineRecord.getC_OrderLine_ID())));
	}

	public void save(final I_C_OrderLine compensationLinePO)
	{
		Check.assume(!compensationLinePO.isProcessed(), "Changing a processed line is not allowed: {}", compensationLinePO); // shall not happen

		setGroupNoToOrderLine(compensationLinePO);

		setActivityToOrderLine(compensationLinePO);

		if (performDatabaseChanges)
		{
			ATTR_IsRepoUpdate.setValue(compensationLinePO, Boolean.TRUE);
			try
			{
				saveRecord(compensationLinePO);
			}
			finally
			{
				ATTR_IsRepoUpdate.reset(compensationLinePO);
			}
		}
	}

	private void setGroupNoToOrderLine(final I_C_OrderLine compensationLinePO)
	{
		if (compensationLinePO.getC_Order_CompensationGroup_ID() <= 0)
		{
			compensationLinePO.setC_Order_CompensationGroup_ID(groupId.getOrderCompensationGroupId());
		}
		else if (compensationLinePO.getC_Order_CompensationGroup_ID() != groupId.getOrderCompensationGroupId())
		{
			throw new AdempiereException("Order line has already another groupNo set: " + compensationLinePO)
					.setParameter("expectedGroupId", groupId.getOrderCompensationGroupId())
					.appendParametersToMessage();
		}
	}

	private void setActivityToOrderLine(final I_C_OrderLine compensationLinePO)
	{
		compensationLinePO.setC_Activity_ID(ActivityId.toRepoId(activityId));
	}

	public I_C_OrderLine getOrderLineByIdIfPresent(final OrderLineId orderLineId)
	{
		return orderLinesById.get(orderLineId);
	}

	public void deleteAllNotIn(final Collection<OrderLineId> orderLineIdsToSkipDeleting)
	{
		if (!performDatabaseChanges)
		{
			return;
		}

		final List<I_C_OrderLine> orderLinesToDelete = orderLinesById.values()
				.stream()
				.filter(orderLine -> !orderLineIdsToSkipDeleting.contains(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID())))
				.collect(ImmutableList.toImmutableList());
		orderLinesToDelete.forEach(this::deleteOrderLineRecord);
	}

	private void deleteOrderLineRecord(final I_C_OrderLine orderLine)
	{
		ATTR_IsRepoUpdate.setValue(orderLine, Boolean.TRUE);
		try
		{
			delete(orderLine);
		}
		finally
		{
			ATTR_IsRepoUpdate.reset(orderLine);
		}
	}

	static boolean isRepositoryUpdate(final I_C_OrderLine orderLine)
	{
		return ATTR_IsRepoUpdate.isSet(orderLine);
	}
}
