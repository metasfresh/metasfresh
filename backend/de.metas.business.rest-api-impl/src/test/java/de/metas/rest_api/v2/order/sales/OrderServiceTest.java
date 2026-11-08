/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.rest_api.v2.order.sales;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.rest_api.utils.CurrencyService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.v2.payment.PaymentService;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderServiceTest
{
	private static final OrderId ORDER_ID = OrderId.ofRepoId(1000001);

	private IDocumentBL documentBL;
	private I_C_Order orderRecord;
	private OrderService orderService;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		documentBL = mock(IDocumentBL.class);
		Services.registerService(IDocumentBL.class, documentBL);

		orderRecord = mock(I_C_Order.class);
		final IOrderDAO orderDAO = mock(IOrderDAO.class);
		when(orderDAO.hasDeliveredItems(ORDER_ID)).thenReturn(false);
		when(orderDAO.getById(ORDER_ID)).thenReturn(orderRecord);
		Services.registerService(IOrderDAO.class, orderDAO);

		orderService = new OrderService(mock(JsonServiceFactory.class), mock(CurrencyService.class), mock(PaymentService.class));
	}

	/**
	 * If the document engine finds no thread-inherited trx, it runs the doc action in its own trx and commits it
	 * before {@link IDocumentBL#processEx} saves the order header. The doc action's side effects (e.g. invoice candidate
	 * recompute markers) then become visible while the header still has its old DocStatus.
	 * Running the reversal in a thread-inherited trx commits both together.
	 */
	@Test
	void reverseOrder_processesTheDocumentInAThreadInheritedTrx()
	{
		final AtomicBoolean processedInThreadInheritedTrx = new AtomicBoolean(false);
		doAnswer(invocation -> {
			processedInThreadInheritedTrx.set(Services.get(ITrxManager.class).hasThreadInheritedTrx());
			return null;
		}).when(documentBL).processEx(orderRecord, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);

		final I_C_Order result = orderService.reverseOrder(ORDER_ID);

		assertThat(result).isSameAs(orderRecord);
		assertThat(processedInThreadInheritedTrx).as("processEx shall run in a thread-inherited trx").isTrue();
	}

	@Test
	void reverseOrder_propagatesTheDocumentEngineException()
	{
		final AdempiereException engineException = new AdempiereException("reversal failed");
		doThrow(engineException).when(documentBL).processEx(any(), eq(IDocument.ACTION_Reverse_Correct), any());

		assertThatThrownBy(() -> orderService.reverseOrder(ORDER_ID)).isSameAs(engineException);
	}
}
