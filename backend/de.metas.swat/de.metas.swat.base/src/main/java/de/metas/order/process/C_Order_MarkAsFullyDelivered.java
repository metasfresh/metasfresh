/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.order.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.X_C_Order;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class C_Order_MarkAsFullyDelivered extends JavaProcess implements IProcessPrecondition
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

	private final String markAsFullyDeliveredParamName = "MarkAsFullyDelivered";
	@Param(parameterName = markAsFullyDeliveredParamName)
	private Boolean markAsFullyDelivered;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final OrderId orderId = OrderId.ofRepoId(context.getSingleSelectedRecordId());
		final org.compiere.model.I_C_Order order = orderBL.getById(orderId);

		if (!order.getDocStatus().equals(X_C_Order.DOCSTATUS_Completed))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Order with ID=" + order.getC_Order_ID() + " is not completed.");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		// make sure that if the process runs with parameter on false, make sure that qtyordered is set based on qtyentered and all the linked schedules are open
		// look where qtyordered is set base on qtyentered on the current flow
		// qtyentered from C_Order.C_UOM_ID and qtyordered is in C_UOM_ID from product

		final List<I_C_Order> selectedOrders = getSelectedIncludedRecords(I_C_Order.class);

		selectedOrders.stream()
				.map(order -> orderBL.getLinesByOrderIds(ImmutableSet.of(OrderId.ofRepoId(order.getC_Order_ID()))))
				.flatMap(Collection::stream)
				.peek(orderBL::closeLine)
				.peek(orderLine -> shipmentScheduleBL.openShipmentSchedulesFor(ImmutableList.of(TableRecordReference.of(I_C_OrderLine.Table_Name, orderLine.getC_OrderLine_ID()))));

		return MSG_OK;
	}
}
