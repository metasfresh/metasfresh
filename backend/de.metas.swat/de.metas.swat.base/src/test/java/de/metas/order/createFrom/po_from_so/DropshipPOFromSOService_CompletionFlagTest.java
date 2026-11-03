/*
 * #%L
 * de.metas.swat.base
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

package de.metas.order.createFrom.po_from_so;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.order.model.I_C_Order;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The call-context flag {@link DropshipPOFromSOService#isCompletedWithinSalesOrderCompletion} tells a drop-ship purchase order
 * completed inside its sales order's completion apart from one completed later (e.g. while the sales order is reactivated).
 */
class DropshipPOFromSOService_CompletionFlagTest
{
	private IDocumentBL documentBL;
	private IC_Order_CreatePOFromSOsBL orderCreatePOFromSOsBL;
	private DropshipPOFromSOService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final IC_Order_CreatePOFromSOsDAO orderCreatePOFromSOsDAO = mock(IC_Order_CreatePOFromSOsDAO.class);
		orderCreatePOFromSOsBL = mock(IC_Order_CreatePOFromSOsBL.class);
		documentBL = mock(IDocumentBL.class);
		Services.registerService(IC_Order_CreatePOFromSOsDAO.class, orderCreatePOFromSOsDAO);
		Services.registerService(IC_Order_CreatePOFromSOsBL.class, orderCreatePOFromSOsBL);
		Services.registerService(IDocumentBL.class, documentBL);

		when(orderCreatePOFromSOsBL.getConfigPurchaseQtySource()).thenReturn("QtyOrdered");
		when(orderCreatePOFromSOsDAO.retrieveOrderLines(any(), anyBoolean(), anyString())).thenReturn(Collections.emptyList());
		when(orderCreatePOFromSOsBL.isCompleteDropshipPO()).thenReturn(true);

		// the aggregator finds no lines; the drafted drop-ship PO below stands for the one it created
		service = new DropshipPOFromSOServiceTest.TestableDropshipPOFromSOService();
	}

	@Test
	void flagIsSetWhileThePurchaseOrderIsCompleted_andClearedAfterwards()
	{
		final I_C_Order salesOrder = newInstance(I_C_Order.class);
		salesOrder.setIsSOTrx(true);
		saveRecord(salesOrder);

		final I_C_Order draftPO = newInstance(I_C_Order.class);
		draftPO.setIsSOTrx(false);
		draftPO.setLink_Order_ID(salesOrder.getC_Order_ID());
		draftPO.setDocStatus(IDocument.STATUS_Drafted);
		draftPO.setIsDropShip(true);
		saveRecord(draftPO);

		final List<org.compiere.model.I_C_Order> completedPOs = new ArrayList<>();
		final List<Boolean> flagWhileCompleting = new ArrayList<>();
		doAnswer(invocation -> {
			final org.compiere.model.I_C_Order po = invocation.getArgument(0);
			completedPOs.add(po);
			flagWhileCompleting.add(DropshipPOFromSOService.isCompletedWithinSalesOrderCompletion(po));
			return null;
		}).when(documentBL).processEx(any(), eq(IDocument.ACTION_Complete), eq(IDocument.STATUS_Completed));

		service.createDropshipPOForSO(salesOrder);

		assertThat(flagWhileCompleting).containsExactly(true);
		assertThat(DropshipPOFromSOService.isCompletedWithinSalesOrderCompletion(completedPOs.get(0))).isFalse();
	}

	@Test
	void flagIsNotSetOnAPurchaseOrderCompletedOutsideTheSalesOrderCompletion()
	{
		final I_C_Order po = newInstance(I_C_Order.class);
		po.setIsSOTrx(false);
		po.setIsDropShip(true);
		saveRecord(po);

		assertThat(DropshipPOFromSOService.isCompletedWithinSalesOrderCompletion(po)).isFalse();
	}
}
