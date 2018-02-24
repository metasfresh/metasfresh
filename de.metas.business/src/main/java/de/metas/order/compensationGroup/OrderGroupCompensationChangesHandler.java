package de.metas.order.compensationGroup;

import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

import de.metas.order.compensationGroup.OrderGroupRepository.OrderLinesStorage;
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

@Component
public class OrderGroupCompensationChangesHandler
{
	private final OrderGroupRepository groupsRepo;
	private final GroupTemplateRepository groupTemplateRepo;

	public OrderGroupCompensationChangesHandler(
			@NonNull final OrderGroupRepository groupsRepo,
			@NonNull final GroupTemplateRepository groupTemplateRepo)
	{
		this.groupsRepo = groupsRepo;
		this.groupTemplateRepo = groupTemplateRepo;
	}

	public void onOrderLineChanged(final I_C_OrderLine orderLine)
	{
		if (!isEligible(orderLine))
		{
			return;
		}

		final GroupId groupId = OrderGroupRepository.extractGroupId(orderLine);
		final Group group = groupsRepo.retrieveGroup(groupId);
		if (group.getGroupTemplateId() > 0 && !orderLine.isGroupCompensationLine())
		{
			final GroupTemplate groupTemplate = groupTemplateRepo.getById(group.getGroupTemplateId());
			groupsRepo.prepareNewGroup()
					.groupTemplate(groupTemplate)
					.recreateGroup(group);
		}
		else
		{
			group.updateAllPercentageLines();
			groupsRepo.saveGroup(group);
		}
	}
	
	public void recreateGroupOnRegularOrderLineChanged(final I_C_OrderLine orderLine)
	{
		if (!isEligible(orderLine))
		{
			return;
		}
		
		// skip if not regular line
		if(orderLine.isGroupCompensationLine())
		{
			return;
		}
		
		final GroupId groupId = OrderGroupRepository.extractGroupId(orderLine);
		final Group group = groupsRepo.retrieveGroup(groupId);
		if (group.getGroupTemplateId() <= 0)
		{
			return;
		}
		
		final GroupTemplate groupTemplate = groupTemplateRepo.getById(group.getGroupTemplateId());
		groupsRepo.prepareNewGroup()
				.groupTemplate(groupTemplate)
				.recreateGroup(group);
	}

	private boolean isEligible(final I_C_OrderLine orderLine)
	{
		// Skip if not a group line
		if (!OrderGroupCompensationUtils.isInGroup(orderLine))
		{
			return false;
		}

		// Don't touch processed lines (e.g. completed orders)
		if (orderLine.isProcessed())
		{
			return false;
		}

		return true;
	}

	public void onOrderLineDeleted(final I_C_OrderLine orderLine)
	{
		if (!isEligible(orderLine))
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
		final GroupId groupId = OrderGroupRepository.extractGroupId(compensationLine);
		final Group group = groupsRepo.retrieveGroupIfExists(groupId);

		// If no group found => nothing to do
		// Usually this case happens when we delete the order, so all the lines together.
		if (group == null)
		{
			return;
		}

		if (!group.hasCompensationLines())
		{
			groupsRepo.destroyGroup(group);
		}
		else
		{
			group.updateAllPercentageLines();
			groupsRepo.saveGroup(group);
		}
	}

	public void updateCompensationLineNoSave(final I_C_OrderLine orderLine)
	{
		final Group group = groupsRepo.createPartialGroupFromCompensationLine(orderLine);
		group.updateAllPercentageLines();

		final OrderLinesStorage orderLinesStorage = groupsRepo.createNotSaveableSingleOrderLineStorage(orderLine);
		groupsRepo.saveGroup(group, orderLinesStorage);

	}

	public IQueryBuilder<I_C_OrderLine> retrieveGroupOrderLinesQuery(final GroupId groupId)
	{
		return groupsRepo.retrieveGroupOrderLinesQuery(groupId);
	}
}
