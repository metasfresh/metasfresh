package de.metas.manufacturing.rest_api;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Trace;
import org.slf4j.MDC;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.common.manufacturing.JsonRequestHULookup;
import de.metas.common.manufacturing.JsonRequestIssueToManufacturingOrder;
import de.metas.common.manufacturing.JsonRequestManufacturingOrdersReport;
import de.metas.common.manufacturing.JsonRequestReceiveFromManufacturingOrder;
import de.metas.common.manufacturing.JsonResponseIssueToManufacturingOrder;
import de.metas.common.manufacturing.JsonResponseManufacturingOrdersReport;
import de.metas.common.manufacturing.JsonResponseManufacturingOrdersReport.JsonResponseManufacturingOrdersReportBuilder;
import de.metas.common.manufacturing.JsonResponseReceiveFromManufacturingOrder;
import de.metas.common.manufacturing.Outcome;
import de.metas.common.rest_api.JsonError;
import de.metas.common.rest_api.JsonErrorItem;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueProducer.ProcessIssueCandidatesPolicy;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.reservation.ReserveHUsRequest;
import de.metas.manufacturing.order.exportaudit.ExportTransactionId;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
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
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IErrorManager errorManager = Services.get(IErrorManager.class);
	private final HUReservationService huReservationService;

	private final JsonRequestManufacturingOrdersReport request;

	private ImmutableMap<PPOrderId, I_PP_Order> _orders; // lazy

	@Builder
	private ManufacturingOrderReportProcessCommand(
			@NonNull final HUReservationService huReservationService,
			@NonNull final JsonRequestManufacturingOrdersReport request)
	{
		this.huReservationService = huReservationService;

		this.request = request;
	}

	private static PPOrderId extractPPOrderId(final I_PP_Order record)
	{
		return PPOrderId.ofRepoId(record.getPP_Order_ID());
	}

	public JsonResponseManufacturingOrdersReport execute()
	{
		final ExportTransactionId transactionKey = ExportTransactionId.random();

		final JsonResponseManufacturingOrdersReportBuilder result = JsonResponseManufacturingOrdersReport.builder()
				.transactionKey(transactionKey.toJson());

		try (final MDC.MDCCloseable ignore = MDC.putCloseable("TransactionIdAPI", transactionKey.toJson()))
		{
			for (final JsonRequestIssueToManufacturingOrder issue : request.getIssues())
			{
				try
				{
					final JsonResponseIssueToManufacturingOrder issueResult = processIssue(issue);
					result.issue(issueResult);
				}
				catch (Exception ex)
				{
					result.issue(JsonResponseIssueToManufacturingOrder.builder()
							.requestId(issue.getRequestId())
							.outcome(Outcome.ERROR)
							.error(createJsonError(ex))
							.build());
				}
			}

			for (final JsonRequestReceiveFromManufacturingOrder receipt : request.getReceipts())
			{
				try
				{
					JsonResponseReceiveFromManufacturingOrder receiptResult = processReceipt(receipt);
					result.receipt(receiptResult);
				}
				catch (Exception ex)
				{
					result.receipt(JsonResponseReceiveFromManufacturingOrder.builder()
							.requestId(receipt.getRequestId())
							.outcome(Outcome.ERROR)
							.error(createJsonError(ex))
							.build());
				}
			}
		}

		return result.build();
	}

	private JsonResponseIssueToManufacturingOrder processIssue(final JsonRequestIssueToManufacturingOrder issue)
	{
		final PPOrderId orderId = extractPPOrderId(issue);

		final String productNo = issue.getProductNo();
		final ProductId productId = productBL.getProductIdByValue(productNo);
		if (productId == null)
		{
			throw new RuntimeException("No product found for `" + productNo + "`");
		}

		final I_C_UOM stockingUOM = productBL.getStockUOM(productId);
		final Quantity qtyToIssue = Quantity.of(issue.getQtyToIssueInStockUOM(), stockingUOM);

		if (issue.getHandlingUnits().isEmpty())
		{
			throw new AdempiereException("No HUs specified in " + issue);
		}

		final List<I_M_HU> hus = resolveHUs(issue.getHandlingUnits());

		huPPOrderBL.createIssueProducer(orderId)
				.fixedQtyToIssue(qtyToIssue)
				.processCandidates(ProcessIssueCandidatesPolicy.ALWAYS)
				.createIssues(hus);

		return JsonResponseIssueToManufacturingOrder.builder()
				.requestId(issue.getRequestId())
				.outcome(Outcome.OK)
				.build();
	}

	private JsonResponseReceiveFromManufacturingOrder processReceipt(final JsonRequestReceiveFromManufacturingOrder receipt)
	{
		final PPOrderId orderId = extractPPOrderId(receipt);
		final LocatorId locatorId = getReceiveToLocatorByOrderId(orderId);

		final ProductId productId = getFinishedGoodProductId(orderId);
		final I_C_UOM stockingUOM = productBL.getStockUOM(productId);
		final Quantity qtyToReceive = Quantity.of(receipt.getQtyToReceiveInStockUOM(), stockingUOM);

		final I_M_HU vhu = huPPOrderBL.receivingMainProduct(orderId)
				.movementDate(receipt.getDate())
				.locatorId(locatorId)
				.lotNumber(receipt.getLotNumber())
				.bestBeforeDate(receipt.getBestBeforeDate())
				.receiveVHU(qtyToReceive);

		final OrderLineId salesOrderLineId = getSalesOrderLineIdByOrderId(orderId).orElse(null);
		if (salesOrderLineId != null)
		{
			huReservationService.makeReservation(ReserveHUsRequest.builder()
					.qtyToReserve(qtyToReceive)
					.salesOrderLineId(salesOrderLineId)
					.productId(productId)
					.huId(HuId.ofRepoId(vhu.getM_HU_ID()))
					.build());
		}

		return JsonResponseReceiveFromManufacturingOrder.builder()
				.requestId(receipt.getRequestId())
				.outcome(Outcome.OK)
				.build();
	}

	private ProductId getFinishedGoodProductId(final PPOrderId orderId)
	{
		final I_PP_Order order = getOrderById(orderId);
		final ProductId productId = ProductId.ofRepoId(order.getM_Product_ID());
		return productId;
	}

	private LocatorId getReceiveToLocatorByOrderId(final PPOrderId orderId)
	{
		final I_PP_Order order = getOrderById(orderId);
		return LocatorId.ofRepoId(order.getM_Warehouse_ID(), order.getM_Locator_ID());
	}

	private Optional<OrderLineId> getSalesOrderLineIdByOrderId(final PPOrderId orderId)
	{
		final I_PP_Order order = getOrderById(orderId);
		return OrderLineId.optionalOfRepoId(order.getC_OrderLine_ID());
	}

	private I_PP_Order getOrderById(final PPOrderId orderId)
	{
		if (_orders == null)
		{
			_orders = Maps.uniqueIndex(
					huPPOrderBL.getByIds(getOrderIds()),
					order -> extractPPOrderId(order));
		}

		final I_PP_Order order = _orders.get(orderId);
		Check.assumeNotNull(order, "{} exists in {}", orderId, _orders);

		return order;
	}

	private Set<PPOrderId> getOrderIds()
	{
		return Stream.concat(
				request.getReceipts().stream().map(receipt -> extractPPOrderId(receipt)),
				request.getIssues().stream().map(issue -> extractPPOrderId(issue)))
				.collect(ImmutableSet.toImmutableSet());
	}

	private static PPOrderId extractPPOrderId(final JsonRequestReceiveFromManufacturingOrder receipt)
	{
		return PPOrderId.ofRepoId(receipt.getOrderId().getValue());
	}

	private static PPOrderId extractPPOrderId(final JsonRequestIssueToManufacturingOrder issue)
	{
		return PPOrderId.ofRepoId(issue.getOrderId().getValue());
	}

	private List<I_M_HU> resolveHUs(@NonNull final Collection<JsonRequestHULookup> requests)
	{
		final ImmutableSet<HuId> huIds = requests.stream()
				.map(this::resolveHUId)
				.collect(ImmutableSet.toImmutableSet());

		final List<I_M_HU> hus = handlingUnitsBL.getByIds(huIds);
		if (hus.isEmpty())
		{
			throw new AdempiereException("No HUs found for " + requests);
		}

		return hus;
	}

	@NonNull
	private HuId resolveHUId(@NonNull final JsonRequestHULookup request)
	{
		final IHUQueryBuilder queryBuilder = handlingUnitsBL.createHUQueryBuilder()
				.setHUStatus(X_M_HU.HUSTATUS_Active);

		if (!Check.isBlank(request.getLotNumber()))
		{
			queryBuilder.addOnlyWithAttribute(AttributeConstants.ATTR_LotNumber, request.getLotNumber());
		}
		if (request.getBestBeforeDate() != null)
		{
			queryBuilder.addOnlyWithAttribute(AttributeConstants.ATTR_BestBeforeDate, request.getBestBeforeDate());
		}

		final HuId huId = queryBuilder.createQueryBuilder()
				.orderBy(I_M_HU.COLUMNNAME_M_HU_ID)
				.create()
				.firstId(HuId::ofRepoIdOrNull);

		if (huId == null)
		{
			throw new AdempiereException("No HU found for " + request);
		}

		return huId;
	}

	private JsonError createJsonError(@NonNull final Exception ex)
	{
		final AdIssueId adIssueId = errorManager.createIssue(ex);

		return JsonError.ofSingleItem(
				JsonErrorItem.builder()
						.message(ex.getLocalizedMessage())
						.stackTrace(Trace.toOneLineStackTraceString(ex.getStackTrace()))
						.adIssueId(JsonMetasfreshId.of(adIssueId.getRepoId()))
						.throwable(ex)
						.build());
	}

}
