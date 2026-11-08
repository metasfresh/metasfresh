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
import de.metas.document.engine.impl.PlainDocumentBL;
import de.metas.order.OrderId;
import de.metas.rest_api.utils.CurrencyService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.v2.payment.PaymentService;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class OrderServiceTest
{
	private OrderService orderService;
	private OrderId orderId;

	/**
	 * The order's DocStatus as persisted at the moment the reversal's transaction is committed
	 */
	private final List<String> persistedDocStatusOnCommit = new ArrayList<>();

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setDocStatus(IDocument.STATUS_Completed);
		order.setDocAction(IDocument.ACTION_Close);
		order.setProcessed(true);
		InterfaceWrapperHelper.saveRecord(order);
		orderId = OrderId.ofRepoId(order.getC_Order_ID());

		// stands in for the order's reversal: changes the order in memory, and, like the invoice candidate invalidation of a real reversal,
		// has work that runs after its transaction is committed
		final PlainDocumentBL documentBL = (PlainDocumentBL)Services.get(IDocumentBL.class);
		documentBL.registerProcessInterceptor(I_C_Order.Table_Name, IDocument.ACTION_Reverse_Correct, (doc, action) -> {
			doc.setDocStatus(IDocument.STATUS_Reversed);
			Services.get(ITrxManager.class)
					.getCurrentTrxListenerManagerOrAutoCommit()
					.runAfterCommit(() -> persistedDocStatusOnCommit.add(InterfaceWrapperHelper.loadOutOfTrx(orderId, I_C_Order.class).getDocStatus()));
			return true;
		});

		orderService = new OrderService(mock(JsonServiceFactory.class), mock(CurrencyService.class), mock(PaymentService.class));
	}

	/**
	 * The REST call has no transaction of its own. The reversed order must be committed together with the reversal,
	 * otherwise the work that runs after the reversal's commit reads the order as still completed.
	 */
	@Test
	void reverseOrder_orderIsCommittedTogetherWithTheReversal()
	{
		final I_C_Order result = orderService.reverseOrder(orderId);

		assertThat(result.getDocStatus()).isEqualTo(IDocument.STATUS_Reversed);
		assertThat(persistedDocStatusOnCommit).containsExactly(IDocument.STATUS_Reversed);
	}
}
