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

package de.metas.order.createFrom.po_from_so.impl;

import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLineId;
import de.metas.order.createFrom.po_from_so.IC_Order_CreatePOFromSOsBL;
import de.metas.order.createFrom.po_from_so.PurchaseTypeEnum;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.shipping.IShipperDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests which sales order lines {@link CreatePOLineFromSOLinesAggregator} puts on which purchase order line:
 * drop-ship purchase orders get one purchase order line per sales order line, all other purchase types merge
 * sales order lines of the same product and attributes.
 */
class CreatePOLineFromSOLinesAggregatorTest
{
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(1000);

	private IOrderDAO orderDAO;
	private I_C_Order purchaseOrder;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final IC_Order_CreatePOFromSOsBL orderCreatePOFromSOsBL = mock(IC_Order_CreatePOFromSOsBL.class);
		when(orderCreatePOFromSOsBL.getCompositeListener()).thenReturn((poLine, soLine, po) -> { /* no-op */ });
		Services.registerService(IC_Order_CreatePOFromSOsBL.class, orderCreatePOFromSOsBL);

		Services.registerService(IShipperDAO.class, mock(IShipperDAO.class));
		Services.registerService(IOrderBL.class, mock(IOrderBL.class));

		orderDAO = mock(IOrderDAO.class);
		Services.registerService(IOrderDAO.class, orderDAO);

		final IProductBL productBL = mock(IProductBL.class);
		when(productBL.getStockUOMId(any(ProductId.class))).thenReturn(UomId.ofRepoId(100));
		Services.registerService(IProductBL.class, productBL);

		final IAttributeSetInstanceBL asiBL = mock(IAttributeSetInstanceBL.class);
		when(asiBL.buildDescription(any(), anyBoolean())).thenReturn("");
		Services.registerService(IAttributeSetInstanceBL.class, asiBL);

		final IOrderLineBL orderLineBL = mock(IOrderLineBL.class);
		when(orderLineBL.createOrderLine(any(I_C_Order.class)))
				.thenAnswer(invocation -> {
					final de.metas.interfaces.I_C_OrderLine line = newInstance(de.metas.interfaces.I_C_OrderLine.class);
					saveRecord(line);
					return line;
				});
		Services.registerService(IOrderLineBL.class, orderLineBL);

		purchaseOrder = newInstance(I_C_Order.class);
		purchaseOrder.setIsSOTrx(false);
		saveRecord(purchaseOrder);
	}

	@Test
	void dropship_onePOLinePerSOLine()
	{
		final I_C_OrderLine soLine1 = buildSoLine(new BigDecimal("924"));
		final I_C_OrderLine soLine2 = buildSoLine(new BigDecimal("671"));

		final CreatePOLineFromSOLinesAggregator agg = aggregate(PurchaseTypeEnum.DROPSHIP, soLine1, soLine2);

		assertThat(agg.purchaseOrderLine2saleOrderLines).hasSize(2);
		assertThat(agg.purchaseOrderLine2saleOrderLines.values())
				.allSatisfy(soLines -> assertThat(soLines).hasSize(1));
		assertThat(agg.purchaseOrderLine2saleOrderLines.entrySet())
				.extracting(e -> e.getKey().getQtyEntered(), e -> e.getValue().get(0))
				.containsExactlyInAnyOrder(
						tuple(new BigDecimal("924"), soLine1),
						tuple(new BigDecimal("671"), soLine2));

		agg.purchaseOrderLine2saleOrderLines.forEach((poLine, soLines) ->
				verify(orderDAO).allocatePOLineToSOLine(
						OrderLineId.ofRepoId(poLine.getC_OrderLine_ID()),
						OrderLineId.ofRepoId(soLines.get(0).getC_OrderLine_ID())));
		verify(orderDAO, times(2)).allocatePOLineToSOLine(any(), any());
	}

	@Test
	void standard_mergesSameProductAndAttributes()
	{
		assertMergedIntoOnePOLine(PurchaseTypeEnum.STANDARD);
	}

	@Test
	void mediated_mergesSameProductAndAttributes()
	{
		assertMergedIntoOnePOLine(PurchaseTypeEnum.MEDIATED);
	}

	private void assertMergedIntoOnePOLine(final PurchaseTypeEnum purchaseType)
	{
		final I_C_OrderLine soLine1 = buildSoLine(new BigDecimal("924"));
		final I_C_OrderLine soLine2 = buildSoLine(new BigDecimal("671"));

		final CreatePOLineFromSOLinesAggregator agg = aggregate(purchaseType, soLine1, soLine2);

		assertThat(agg.purchaseOrderLine2saleOrderLines).hasSize(1);
		final I_C_OrderLine poLine = agg.purchaseOrderLine2saleOrderLines.keySet().iterator().next();
		assertThat(poLine.getQtyEntered()).isEqualByComparingTo("1595");
		assertThat(agg.purchaseOrderLine2saleOrderLines.get(poLine)).containsExactly(soLine1, soLine2);

		final OrderLineId poLineId = OrderLineId.ofRepoId(poLine.getC_OrderLine_ID());
		verify(orderDAO).allocatePOLineToSOLine(poLineId, OrderLineId.ofRepoId(soLine1.getC_OrderLine_ID()));
		verify(orderDAO).allocatePOLineToSOLine(poLineId, OrderLineId.ofRepoId(soLine2.getC_OrderLine_ID()));
		verify(orderDAO, times(2)).allocatePOLineToSOLine(any(), any());
	}

	private CreatePOLineFromSOLinesAggregator aggregate(final PurchaseTypeEnum purchaseType, final I_C_OrderLine... soLines)
	{
		final CreatePOLineFromSOLinesAggregator agg = new CreatePOLineFromSOLinesAggregator(
				purchaseOrder,
				I_C_OrderLine.COLUMNNAME_QtyOrdered,
				purchaseType);
		agg.setItemAggregationKeyBuilder(CreatePOLineFromSOLinesAggregationKeyBuilder.forPurchaseType(purchaseType));
		agg.setGroupsBufferSize(100);
		for (final I_C_OrderLine soLine : soLines)
		{
			agg.add(soLine);
		}
		agg.closeAllGroups();
		return agg;
	}

	private I_C_OrderLine buildSoLine(final BigDecimal qty)
	{
		final I_C_Order salesOrder = newInstance(I_C_Order.class);
		salesOrder.setIsSOTrx(true);
		saveRecord(salesOrder);

		final I_C_OrderLine soLine = newInstance(I_C_OrderLine.class);
		soLine.setC_Order_ID(salesOrder.getC_Order_ID());
		soLine.setM_Product_ID(PRODUCT_ID.getRepoId());
		soLine.setQtyOrdered(qty);
		saveRecord(soLine);
		return soLine;
	}
}
