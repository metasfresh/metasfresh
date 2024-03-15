package de.metas.order.compensationGroup;

import de.metas.order.OrderId;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

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

@Component
public class OrderGroupCompensationChangesHandler
{
	private final OrderGroupRepository groupsRepo;
	private final GroupTemplateRepository groupTemplateRepo;
	private final FlatrateConditionsExcludedProductsRepository flatrateConditionsExcludedProductsRepo;

	public OrderGroupCompensationChangesHandler(
			@NonNull final OrderGroupRepository groupsRepo,
			@NonNull final GroupTemplateRepository groupTemplateRepo,
			@NonNull final FlatrateConditionsExcludedProductsRepository flatrateConditionsExcludedProductsRepo)
	{
		this.groupsRepo = groupsRepo;
		this.groupTemplateRepo = groupTemplateRepo;
		this.flatrateConditionsExcludedProductsRepo = flatrateConditionsExcludedProductsRepo;
	}

	public void scheduleOrderLinesRenumbering(@NonNull final OrderId orderId)
	{
		groupsRepo.scheduleOrderLinesRenumbering(orderId);
	}

	public void onOrderLineChanged(final I_C_OrderLine orderLine)
	{
		if (!isEligible(orderLine))
		{
			return;
		}

		final GroupId groupId = OrderGroupRepository.extractGroupId(orderLine);
		final Group group = groupsRepo.retrieveGroup(groupId);
		if (group.isBasedOnGroupTemplate() && !orderLine.isGroupCompensationLine())
		{
			recreateGroupFromTemplate(group);
		}
		else
		{
			group.updateAllCompensationLines();
			groupsRepo.saveGroup(group);
		}
	}

	public void recreateGroupOnOrderLineChanged(final I_C_OrderLine orderLine)
	{
		if (!isEligible(orderLine))
		{
			return;
		}

		final GroupId groupId = OrderGroupRepository.extractGroupId(orderLine);
		final Group group = groupsRepo.retrieveGroup(groupId);
		if (group.isBasedOnGroupTemplate())
		{
			recreateGroupFromTemplate(group);
		}
	}

	private void recreateGroupFromTemplate(final Group group)
	{
		final GroupTemplate groupTemplate = groupTemplateRepo.getById(group.getGroupTemplateId());
		groupsRepo.prepareNewGroup()
				.groupTemplate(groupTemplate)
				.recreateGroup(group);

		final OrderId orderId = OrderGroupRepository.extractOrderIdFromGroup(group);
		groupsRepo.renumberOrderLinesForOrderId(orderId);
	}

	private boolean isEligible(final I_C_OrderLine orderLine)
	{
		// Skip if given line is currently changed by the repository (to avoid race conditions)
		if (OrderGroupRepository.isRepositoryUpdate(orderLine))
		{
			return false;
		}

		// Skip if not a group line
		if (!OrderGroupCompensationUtils.isInGroup(orderLine))
		{
			return false;
		}

		// Don't touch processed lines (e.g. completed orders)
		return !orderLine.isProcessed();
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
			group.updateAllCompensationLines();
			groupsRepo.saveGroup(group);
		}
	}

	public void updateCompensationLineNoSave(final I_C_OrderLine orderLine)
	{
		final Group group = groupsRepo.createPartialGroupFromCompensationLine(orderLine);
		group.updateAllCompensationLines();

		final OrderLinesStorage orderLinesStorage = groupsRepo.createNotSaveableSingleOrderLineStorage(orderLine);
		groupsRepo.saveGroup(group, orderLinesStorage);

	}

	@Nullable
	public GroupTemplateId getGroupTemplateId(@NonNull final GroupId groupId)
	{
		return groupsRepo.getGroupTemplateId(groupId);
	}

	public boolean isProductExcludedFromFlatrateConditions(@Nullable final GroupTemplateId groupTemplateId, @NonNull final ProductId productId)
	{
		if (groupTemplateId == null)
		{
			return false;
		}

		return flatrateConditionsExcludedProductsRepo.isProductExcludedFromFlatrateConditions(groupTemplateId, productId);
	}

	public IQueryBuilder<I_C_OrderLine> retrieveGroupOrderLinesQuery(final GroupId groupId)
	{
		return groupsRepo.retrieveGroupOrderLinesQuery(groupId);
	}
}
