package de.metas.order.process;

import java.util.List;
import java.util.Set;

import org.compiere.Adempiere;
import org.compiere.model.I_C_OrderLine;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.model.I_C_Order;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.OrderGroupRepository;
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

public abstract class OrderCompensationGroupProcess extends JavaProcess implements IProcessPrecondition
{
	@Autowired
	protected OrderGroupRepository groupsRepo;

	public OrderCompensationGroupProcess()
	{
		Adempiere.autowire(this);
	}

	protected static ProcessPreconditionsResolution acceptIfEligibleOrder(final IProcessPreconditionsContext context)
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

	protected final ProcessPreconditionsResolution acceptIfOrderLinesHaveSameGroupId(final IProcessPreconditionsContext context)
	{
		final Set<Integer> orderLineIds = getSelectedOrderLineIds(context);
		if (orderLineIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Select some order lines");
		}

		final Set<GroupId> groupIds = groupsRepo.extractGroupIdsFromOrderLineIds(orderLineIds);
		if (groupIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Select a line which is part of a group");
		}
		else if (groupIds.size() > 1)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Selected lines shall be part of the same group");
		}
		
		return ProcessPreconditionsResolution.accept();
	}

	protected final ProcessPreconditionsResolution acceptIfOrderLinesNotInGroup(final IProcessPreconditionsContext context)
	{
		final Set<Integer> orderLineIds = getSelectedOrderLineIds(context);
		if (orderLineIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Select some order lines");
		}

		final Set<GroupId> groupIds = groupsRepo.extractGroupIdsFromOrderLineIds(orderLineIds);
		if (!groupIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Select lines which are not already grouped");
		}

		return ProcessPreconditionsResolution.accept();
	}

	private static final Set<Integer> getSelectedOrderLineIds(final IProcessPreconditionsContext context)
	{
		return context.getSelectedIncludedRecords()
				.stream()
				.filter(recordRef -> I_C_OrderLine.Table_Name.equals(recordRef.getTableName()))
				.map(recordRef -> recordRef.getRecord_ID())
				.collect(ImmutableSet.toImmutableSet());
	}

	protected final Set<Integer> getSelectedOrderLineIds()
	{
		return getSelectedIncludedRecordIds(I_C_OrderLine.class);
	}

	protected final List<I_C_OrderLine> getSelectedOrderLines()
	{
		return getSelectedIncludedRecords(I_C_OrderLine.class);
	}

}
