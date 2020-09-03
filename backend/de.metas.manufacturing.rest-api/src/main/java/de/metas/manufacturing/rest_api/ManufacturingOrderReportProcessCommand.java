package de.metas.manufacturing.rest_api;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.ComponentIssueCreateRequest;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.ReceiptCostCollectorCandidate;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.slf4j.MDC;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.common.rest_api.JsonQuantity;
import de.metas.manufacturing.order.exportaudit.ExportTransactionId;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

class ManufacturingOrderReportProcessCommand
{
	private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);
	private final IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
	private final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final JsonRequestManufacturingOrdersReport request;

	private ImmutableMap<PPOrderId, I_PP_Order> _orders;
	private ImmutableListMultimap<PPOrderId, I_PP_Order_BOMLine> _bomLines;

	@Builder
	private ManufacturingOrderReportProcessCommand(
			@NonNull final JsonRequestManufacturingOrdersReport request)
	{
		this.request = request;
	}

	public void execute()
	{
		final ExportTransactionId transactionKey = ExportTransactionId.random();
		try (final MDC.MDCCloseable ignore = MDC.putCloseable("TransactionIdAPI", transactionKey.toJson()))
		{
			for (final JsonRequestIssueToManufacturingOrder issue : request.getIssues())
			{
				processIssue(issue);
			}

			for (final JsonRequestReceiveFromManufacturingOrder receipt : request.getReceipts())
			{
				processReceipt(receipt);
			}
		}

	}

	private void processIssue(final JsonRequestIssueToManufacturingOrder issue)
	{
		// TODO: use HUPPOrderIssueProducer

		final PPOrderId orderId = issue.getOrderId();
		final I_PP_Order order = getOrderById(orderId);
		final ProductId componentId = productDAO.retrieveProductIdByValue(issue.getProductCode());
		final Quantity qtyToIssue = toQuantity(issue.getQtyToIssue());

		ppCostCollectorBL.createIssue(ComponentIssueCreateRequest.builder()
				.orderBOMLine(getOrderBOMLine(orderId, componentId))
				.locatorId(extractLocatorId(order))
				.movementDate(issue.getDate())
				.qtyIssue(qtyToIssue)
				.build());
	}

	private void processReceipt(final JsonRequestReceiveFromManufacturingOrder receipt)
	{
		// TODO: use de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer.createDraftReceiptCandidatesAndPlanningHUs()
		// and then de.metas.handlingunits.pporder.api.HUPPOrderIssueReceiptCandidatesProcessor
		//
		// better introduce a class similar with de.metas.handlingunits.pporder.api.HUPPOrderIssueProducer.createIssues(Collection<I_M_HU>),
		// but for receiving from manufacturing order.

		final PPOrderId orderId = receipt.getOrderId();
		final I_PP_Order order = getOrderById(orderId);
		final Quantity qtyToReceive = toQuantity(receipt.getQtyToReceive());

		ppCostCollectorBL.createReceipt(ReceiptCostCollectorCandidate.builder()
				.order(order)
				.movementDate(receipt.getDate())
				.productId(ProductId.ofRepoId(order.getM_Product_ID()))
				.qtyToReceive(qtyToReceive)
				.build());
	}

	private I_PP_Order getOrderById(final PPOrderId orderId)
	{
		I_PP_Order order = getOrdersById().get(orderId);
		Check.assumeNotNull(order, "order shall exist for {}", orderId);
		return order;
	}

	private ImmutableMap<PPOrderId, I_PP_Order> getOrdersById()
	{
		if (_orders == null)
		{
			final ImmutableSet<PPOrderId> orderIds = request.getOrderIds();
			_orders = Maps.uniqueIndex(
					ppOrderDAO.getByIds(orderIds),
					order -> PPOrderId.ofRepoId(order.getPP_Order_ID()));
		}
		return _orders;
	}

	private I_PP_Order_BOMLine getOrderBOMLine(final PPOrderId orderId, final ProductId componentId)
	{
		final ImmutableListMultimap<PPOrderId, I_PP_Order_BOMLine> bomLines = getOrderBOMLines();

		return bomLines.get(orderId)
				.stream()
				.filter(bomLine -> bomLine.getM_Product_ID() == componentId.getRepoId())
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No Order BOM Line found for " + orderId + " and " + componentId));
	}

	private ImmutableListMultimap<PPOrderId, I_PP_Order_BOMLine> getOrderBOMLines()
	{
		if (_bomLines == null)
		{
			final ImmutableSet<PPOrderId> orderIds = request.getOrderIds();
			_bomLines = ppOrderBOMDAO.retrieveOrderBOMLines(orderIds, I_PP_Order_BOMLine.class)
					.stream()
					.collect(ImmutableListMultimap.toImmutableListMultimap(
							bomLine -> PPOrderId.ofRepoId(bomLine.getPP_Order_ID()),
							bomLine -> bomLine));
		}
		return _bomLines;
	}

	private static LocatorId extractLocatorId(I_PP_Order order)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoId(order.getM_Warehouse_ID());
		return LocatorId.ofRepoId(warehouseId, order.getM_Locator_ID());
	}

	private Quantity toQuantity(final JsonQuantity json)
	{
		final X12DE355 x12de355 = X12DE355.ofCode(json.getUomCode());
		final I_C_UOM uom = uomDAO.getByX12DE355(x12de355);

		return Quantity.of(json.getQty(), uom);
	}
}
