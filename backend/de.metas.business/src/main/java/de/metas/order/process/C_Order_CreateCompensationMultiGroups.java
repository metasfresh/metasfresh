package de.metas.order.process;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_OrderLine;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;

import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.model.I_M_Product_Category;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * Group selected order lines in multiple groups, one group for each {@link I_M_Product_Category#getM_Product_Category_Parent_ID()}.
 * 
 * Task https://github.com/metasfresh/metasfresh-webui-api/issues/853
 */
public class C_Order_CreateCompensationMultiGroups extends OrderCompensationGroupProcess
{
	@Autowired
	private GroupTemplateRepository groupTemplateRepo;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		return acceptIfEligibleOrder(context)
				.and(() -> acceptIfOrderLinesNotInGroup(context));
	}

	@Override
	protected String doIt()
	{
		final List<I_C_OrderLine> selectedOrderLines = getSelectedOrderLines();
		Check.assumeNotEmpty(selectedOrderLines, "selectedOrderLines is not empty");

		final ListMultimap<GroupTemplate, OrderLineId> orderLineIdsByGroupTemplate = extractOrderLineIdsByGroupTemplate(selectedOrderLines);
		if (orderLineIdsByGroupTemplate.isEmpty())
		{
			throw new AdempiereException("Nothing to group"); // TODO trl
		}

		final List<Group> groups = orderLineIdsByGroupTemplate.asMap()
				.entrySet()
				.stream()
				.map(e -> createGroup(e.getKey(), e.getValue()))
				.collect(ImmutableList.toImmutableList());

		final OrderId orderId = OrderGroupRepository.extractOrderIdFromGroups(groups);
		groupsRepo.renumberOrderLinesForOrderId(orderId);

		return MSG_OK;
	}

	private ListMultimap<GroupTemplate, OrderLineId> extractOrderLineIdsByGroupTemplate(final List<I_C_OrderLine> orderLines)
	{
		final List<I_C_OrderLine> orderLinesSorted = orderLines.stream()
				.sorted(Comparator.comparing(I_C_OrderLine::getLine))
				.collect(ImmutableList.toImmutableList());

		final ListMultimap<GroupTemplate, OrderLineId> orderLineIdsByGroupTemplate = LinkedListMultimap.create();
		for (final I_C_OrderLine orderLine : orderLinesSorted)
		{
			final GroupTemplate groupTemplate = extractGroupTemplate(orderLine);
			if (groupTemplate == null)
			{
				continue;
			}

			orderLineIdsByGroupTemplate.put(groupTemplate, OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()));
		}
		return orderLineIdsByGroupTemplate;
	}

	@Nullable
	private GroupTemplate extractGroupTemplate(final I_C_OrderLine orderLine)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(orderLine.getM_Product_ID());
		if (productId == null)
		{
			return null;
		}
		
		final IProductDAO productsRepo = Services.get(IProductDAO.class);
		final ProductCategoryId productCategoryId = productsRepo.retrieveProductCategoryByProductId(productId);
		final I_M_Product_Category productCategory = productsRepo.getProductCategoryById(productCategoryId, I_M_Product_Category.class);
		final GroupTemplateId groupTemplateId = GroupTemplateId.ofRepoIdOrNull(productCategory.getC_CompensationGroup_Schema_ID());
		if (groupTemplateId == null)
		{
			return null;
		}

		return groupTemplateRepo.getById(groupTemplateId);
	}

	private Group createGroup(final GroupTemplate groupTemplate, final Collection<OrderLineId> orderLineIds)
	{
		return groupsRepo.prepareNewGroup()
				.groupTemplate(groupTemplate)
				.createGroup(orderLineIds);
	}

}
