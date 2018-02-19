package de.metas.order.process;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Product;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupCompensationLine;
import de.metas.order.compensationGroup.GroupRegularLine;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.model.I_M_Product_Category;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;

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
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh-webui-api/issues/853
 */
public class C_Order_CreateCompensationMultiGroups extends JavaProcess implements IProcessPrecondition
{
	@Autowired
	private OrderGroupRepository groupsRepo;
	@Autowired
	private GroupTemplateRepository groupTemplateRepo;

	public C_Order_CreateCompensationMultiGroups()
	{
		Adempiere.autowire(this);
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("one and only one order shall be selected");
		}

		// Only draft orders
		final I_C_Order order = context.getSelectedModel(I_C_Order.class);
		if (order.isProcessed())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only draft orders are allowed");
		}

		// Only sales orders
		if (!order.isSOTrx())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("only sales orders are allowed");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final List<I_C_OrderLine> selectedOrderLines = getSelectedIncludedRecords(I_C_OrderLine.class);
		if (selectedOrderLines.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		final ListMultimap<GroupTemplate, Integer> orderLineIdsByGroupTemplate = extractOrderLineIdsByGroupTemplate(selectedOrderLines);
		if (orderLineIdsByGroupTemplate.isEmpty())
		{
			throw new AdempiereException("Nothing to group"); // TODO trl
		}

		final List<Group> groups = orderLineIdsByGroupTemplate.asMap()
				.entrySet()
				.stream()
				.map(e -> createGroup(e.getKey(), e.getValue()))
				.collect(ImmutableList.toImmutableList());

		renumberOrderLines(groups);

		return MSG_OK;
	}

	private ListMultimap<GroupTemplate, Integer> extractOrderLineIdsByGroupTemplate(final List<I_C_OrderLine> orderLines)
	{
		final List<I_C_OrderLine> orderLinesSorted = orderLines.stream()
				.sorted(Comparator.comparing(I_C_OrderLine::getLine))
				.collect(ImmutableList.toImmutableList());

		final ListMultimap<GroupTemplate, Integer> orderLineIdsByGroupTemplate = LinkedListMultimap.create();
		for (final I_C_OrderLine orderLine : orderLinesSorted)
		{
			final GroupTemplate groupTemplate = extractGroupTemplate(orderLine);
			if (groupTemplate == null)
			{
				continue;
			}

			orderLineIdsByGroupTemplate.put(groupTemplate, orderLine.getC_OrderLine_ID());
		}
		return orderLineIdsByGroupTemplate;
	}

	private GroupTemplate extractGroupTemplate(final I_C_OrderLine orderLine)
	{
		final I_M_Product product = orderLine.getM_Product();
		if (product == null)
		{
			return null;
		}
		final I_M_Product_Category productCategory = InterfaceWrapperHelper.loadOutOfTrx(product.getM_Product_Category_ID(), I_M_Product_Category.class);
		final int groupTemplateId = productCategory.getC_CompensationGroup_Schema_ID();
		if (groupTemplateId <= 0)
		{
			return null;
		}

		return groupTemplateRepo.getById(groupTemplateId);
	}

	private Group createGroup(final GroupTemplate groupTemplate, final Collection<Integer> orderLineIds)
	{
		return groupsRepo.prepareNewGroup()
				.linesToGroup(orderLineIds)
				.newGroupTemplate(groupTemplate)
				.createGroup();
	}

	private void renumberOrderLines(final List<Group> groups)
	{
		final int orderId = OrderGroupRepository.extractOrderIdFromGroups(groups);

		final LinkedHashMap<Integer, I_C_OrderLine> allOrderLinesById = Services.get(IOrderDAO.class).retrieveOrderLines(orderId)
				.stream()
				.sorted(Comparator.comparing(I_C_OrderLine::getLine))
				.map(orderLine -> GuavaCollectors.entry(orderLine.getC_OrderLine_ID(), orderLine))
				.collect(GuavaCollectors.toLinkedHashMap());

		int nextLineNo = 10;

		//
		// Renumber grouped order lines first
		for (final Group group : groups)
		{
			for (final GroupRegularLine line : group.getRegularLines())
			{
				final int orderLineId = line.getRepoId();
				final I_C_OrderLine orderLine = allOrderLinesById.remove(orderLineId);
				if (orderLine == null)
				{
					// shall not happen
					continue;
				}

				orderLine.setLine(nextLineNo);
				InterfaceWrapperHelper.save(orderLine);
				nextLineNo += 10;
			}

			for (final GroupCompensationLine line : group.getCompensationLines())
			{
				final int orderLineId = line.getRepoId();
				final I_C_OrderLine orderLine = allOrderLinesById.remove(orderLineId);
				if (orderLine == null)
				{
					// shall not happen
					continue;
				}

				orderLine.setLine(nextLineNo);
				InterfaceWrapperHelper.save(orderLine);
				nextLineNo += 10;
			}
		}

		//
		// Remaining ungrouped order lines
		for (final I_C_OrderLine orderLine : allOrderLinesById.values())
		{
			orderLine.setLine(nextLineNo);
			InterfaceWrapperHelper.save(orderLine);
			nextLineNo += 10;
		}
	}
}
