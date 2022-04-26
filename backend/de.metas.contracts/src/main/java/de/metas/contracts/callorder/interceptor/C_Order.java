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

package de.metas.contracts.callorder.interceptor;

import de.metas.contracts.callorder.CallOrderService;
import de.metas.contracts.callorder.detail.document.DocumentChangeHandler_Order;
import de.metas.contracts.order.model.I_C_Order;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Order.class)
@Component
public class C_Order
{
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	private final CallOrderService callOrderService;
	private final DocumentChangeHandler_Order orderDocumentChangeHandler;

	public C_Order(
			@NonNull final CallOrderService callOrderService,
			@NonNull final DocumentChangeHandler_Order orderDocumentChangeHandler)
	{
		this.callOrderService = callOrderService;
		this.orderDocumentChangeHandler = orderDocumentChangeHandler;
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void handleComplete(final I_C_Order order)
	{
		if (callOrderService.isCallOrder(OrderId.ofRepoId(order.getC_Order_ID())))
		{
			orderDocumentChangeHandler.onComplete(order);
		}
		else
		{
			handleOrderComplete(order);
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REACTIVATE })
	public void handleReactivate(final I_C_Order order)
	{
		orderDocumentChangeHandler.onReactivate(order);
	}

	private void handleOrderComplete(@NonNull final I_C_Order order)
	{
		orderDAO.retrieveOrderLines(order, I_C_OrderLine.class)
				.stream()
				.filter(ol -> ol.getC_Flatrate_Conditions_ID() > 0)
				.forEach(callOrderService::createCallOrderContractIfRequired);
	}
}
