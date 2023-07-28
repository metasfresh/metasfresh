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
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.X_C_Order;

import java.util.Collection;
import java.util.List;

public class C_Order_MarkAsFullyDelivered extends JavaProcess implements IProcessPrecondition
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

	private final String PARAM_MarkAsFullyDelivered = "MarkAsFullyDelivered";
	@Param(parameterName = PARAM_MarkAsFullyDelivered)
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
			return ProcessPreconditionsResolution.rejectWithInternalReason("Order is not completed.");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final List<I_C_Order> selectedOrders = getProcessInfo().getSelectedIncludedRecords()
				.stream()
				.map(tableRecordReference -> orderBL.getById(OrderId.ofRepoId(tableRecordReference.getRecord_ID())))
				.collect(ImmutableList.toImmutableList());

		final ImmutableSet<I_C_OrderLine> orderLines = selectedOrders.stream()
				.map(order -> orderBL.getLinesByOrderIds(ImmutableSet.of(OrderId.ofRepoId(order.getC_Order_ID()))))
				.flatMap(Collection::stream)
				.collect(ImmutableSet.toImmutableSet());

		//

		if (markAsFullyDelivered)
		{
			orderLines.forEach(orderBL::closeLine);
		}
		else
		{
			orderLines.forEach(orderBL::reopenLine);
			orderLines.forEach(this::setQtyOrderedBasedOnQtyEntered);
			// shipmentScheduleBL.openShipmentSchedulesFor(ImmutableList.copyOf(TableRecordReference
			// 																		 .ofRecordIds(I_C_OrderLine.Table_Name, orderLines.stream()
			// 																				 .map(I_C_OrderLine::getC_OrderLine_ID)
			// 																				 .collect(ImmutableList.toImmutableList()))));
		}

		return MSG_OK;
	}

	private void setQtyOrderedBasedOnQtyEntered(@NonNull final I_C_OrderLine orderLine)
	{
		final Quantity qtyEnteredToStockUOM = orderLineBL.convertQtyEnteredToStockUOM(orderLine);
		orderLine.setQtyOrdered(qtyEnteredToStockUOM.toBigDecimal());
		InterfaceWrapperHelper.saveRecord(orderLine);
	}
}
