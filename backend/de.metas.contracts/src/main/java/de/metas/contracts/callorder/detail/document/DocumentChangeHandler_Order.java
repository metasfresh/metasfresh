/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contracts.callorder.detail.document;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.callorder.CallOrderContractService;
import de.metas.contracts.callorder.CallOrderService;
import de.metas.contracts.callorder.detail.CallOrderDetailRepo;
import de.metas.contracts.callorder.detail.UpsertCallOrderDetailRequest;
import de.metas.contracts.order.model.I_C_Order;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentChangeHandler_Order implements DocumentChangeHandler<I_C_Order>
{
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	@NonNull
	private final CallOrderContractService callOrderContractService;
	@NonNull
	private final CallOrderService callOrderService;
	@NonNull
	private final CallOrderDetailRepo detailRepo;

	public DocumentChangeHandler_Order(
			@NonNull final CallOrderContractService callOrderContractService,
			@NonNull final CallOrderService callOrderService,
			@NonNull final CallOrderDetailRepo detailRepo)
	{
		this.callOrderContractService = callOrderContractService;
		this.callOrderService = callOrderService;
		this.detailRepo = detailRepo;
	}

	@Override
	public void onComplete(final I_C_Order order)
	{
		if (!callOrderService.isCallOrder(order))
		{
			return;
		}

		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order, I_C_OrderLine.class);
		for (final I_C_OrderLine callOrderLine : orderLines)
		{
			final FlatrateTermId callOrderContractId = callOrderContractService.validateCallOrderLine(callOrderLine, order);

			final UpsertCallOrderDetailRequest request = UpsertCallOrderDetailRequest.builder()
					.callOrderContractId(callOrderContractId)
					.orderLine(callOrderLine)
					.build();

			callOrderService.handleCallOrderDetailUpsert(request);
		}
	}

	@Override
	public void onReverse(final I_C_Order model)
	{
		throw new AdempiereException("Orders cannot be reversed!");
	}

	@Override
	public void onReactivate(final I_C_Order order)
	{
		if (!callOrderService.isCallOrder(order))
		{
			return;
		}

		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

		detailRepo.resetEnteredQtyForOrder(orderId);
	}
}
