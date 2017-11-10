package de.metas.order.compensationGroup;

import org.compiere.model.I_C_OrderLine;
import org.compiere.model.X_C_OrderLine;

import lombok.Builder;
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

public class OrderGroupCompensationChangesHandler
{
	private final OrderGroupRepository groupsRepo;

	@Builder
	private OrderGroupCompensationChangesHandler(@NonNull final OrderGroupRepository groupsRepo)
	{
		this.groupsRepo = groupsRepo;
	}

	public void onOrderLineChanged(final I_C_OrderLine orderLine)
	{
		// Skip if not a group line
		if (!OrderGroupCompensationUtils.isInGroup(orderLine))
		{
			return;
		}

		// Don't touch processed lines (e.g. completed orders)
		if (orderLine.isProcessed())
		{
			return;
		}

		final boolean groupCompensationLine = orderLine.isGroupCompensationLine();
		final String amtType = orderLine.getGroupCompensationAmtType();
		if (!groupCompensationLine)
		{
			onRegularGroupLineChanged(orderLine);
		}
		else if (X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent.equals(amtType))
		{
			onPercentageCompensationLineChanged(orderLine);
		}
	}

	private void onPercentageCompensationLineChanged(final I_C_OrderLine compensationLine)
	{
	}

	private void onRegularGroupLineChanged(final I_C_OrderLine regularLine)
	{
		final GroupId groupId = groupsRepo.extractGroupId(regularLine);
		final Group group = groupsRepo.retrieveGroup(groupId);
		group.updateAllPercentageLines();
		groupsRepo.saveGroup(group);
	}

	public void onOrderLineDeleted(final I_C_OrderLine orderLine)
	{
		// Skip if not a group line
		if (!OrderGroupCompensationUtils.isInGroup(orderLine))
		{
			return;
		}

		// Don't touch processed lines (e.g. completed orders)
		if (orderLine.isProcessed())
		{
			return;
		}

		final boolean groupCompensationLine = orderLine.isGroupCompensationLine();
		if (groupCompensationLine)
		{
			onCompensationLineDeleted(orderLine);
		}
	}

	private void onCompensationLineDeleted(final I_C_OrderLine compensationLine)
	{
		final GroupId groupId = groupsRepo.extractGroupId(compensationLine);
		final Group group = groupsRepo.retrieveGroup(groupId);
		
		if(!group.hasCompensationLines())
		{
			groupsRepo.destroyGroup(group);
		}
	}
}
