package de.metas.manufacturing.rest_api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.common.manufacturing.JsonRequestHULookup;
import de.metas.common.manufacturing.JsonRequestIssueToManufacturingOrder;
import de.metas.common.manufacturing.JsonRequestManufacturingOrdersReport;
import de.metas.common.manufacturing.JsonRequestReceiveFromManufacturingOrder;
import de.metas.common.manufacturing.JsonResponseIssueToManufacturingOrder;
import de.metas.common.manufacturing.JsonResponseIssueToManufacturingOrderDetail;
import de.metas.common.manufacturing.JsonResponseManufacturingOrdersReport;
import de.metas.common.manufacturing.JsonResponseReceiveFromManufacturingOrder;
import de.metas.common.rest_api.JsonError;
import de.metas.common.rest_api.JsonErrorItem;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.rest_api.JsonQuantity;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueProducer.ProcessIssueCandidatesPolicy;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.reservation.ReserveHUsRequest;
import de.metas.logging.LogManager;
import de.metas.manufacturing.order.exportaudit.APITransactionId;
import de.metas.manufacturing.order.importaudit.ManufacturingOrderReportAudit;
import de.metas.manufacturing.order.importaudit.ManufacturingOrderReportAuditItem;
import de.metas.manufacturing.order.importaudit.ManufacturingOrderReportAuditItem.ManufacturingOrderReportAuditItemBuilder;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Trace;
import org.eevolution.api.PPCostCollectorId;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

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
	private static final Logger logger = LogManager.getLogger(ManufacturingOrderReportProcessCommand.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IErrorManager errorManager = Services.get(IErrorManager.class);
	private final HUReservationService huReservationService;
	private final ManufacturingOrderReportAuditRepository auditRepository;
	private final ObjectMapper jsonObjectMapper;

	// Parameters
	private final JsonRequestManufacturingOrdersReport request;

	// Status
	private APITransactionId transactionKey;
	private ManufacturingOrderReportAudit audit;
	private ImmutableMap<PPOrderId, I_PP_Order> _orders; // lazy

	@Builder
	private ManufacturingOrderReportProcessCommand(
			@NonNull final HUReservationService huReservationService,
			@NonNull final ManufacturingOrderReportAuditRepository auditRepository,
			@NonNull final ObjectMapper jsonObjectMapper,
			@NonNull final JsonRequestManufacturingOrdersReport request)
	{
		this.huReservationService = huReservationService;
		this.auditRepository = auditRepository;
		this.jsonObjectMapper = jsonObjectMapper;

		this.request = request;
	}

	private static PPOrderId extractPPOrderId(final I_PP_Order record)
	{
		return PPOrderId.ofRepoId(record.getPP_Order_ID());
	}

	public JsonResponseManufacturingOrdersReport execute()
	{
		transactionKey = APITransactionId.optionalOfString(request.getTransactionKey()).orElseGet(APITransactionId::random);
		audit = ManufacturingOrderReportAudit.newInstance(transactionKey);
		audit.setJsonRequest(toJsonString(request));

		try (final MDCCloseable ignore = MDC.putCloseable("TransactionIdAPI", transactionKey.toJson()))
		{
			final JsonResponseManufacturingOrdersReport response = trxManager.callInThreadInheritedTrx(this::executeInTrx);
			audit.setJsonResponse(toJsonString(response));
			audit.markAsSuccess();
			return response;
		}
		catch (final Exception ex)
		{
			final JsonErrorItem jsonErrorItem = createJsonErrorItem(ex);
			final JsonResponseManufacturingOrdersReport response = JsonResponseManufacturingOrdersReport.builder()
					.transactionKey(transactionKey.toJson())
					.error(JsonError.ofSingleItem(jsonErrorItem))
					.build();

			audit.setJsonResponse(toJsonString(response));
			audit.markAsFailure(
					jsonErrorItem.getMessage(),
					AdIssueId.ofRepoIdOrNull(jsonErrorItem.getAdIssueId().getValue()));

			return response;
		}
		finally
		{
			auditRepository.saveNew(audit);
		}
	}

	private JsonResponseManufacturingOrdersReport executeInTrx()
	{
		final List<JsonResponseIssueToManufacturingOrder> issues = processIssues();
		final List<JsonResponseReceiveFromManufacturingOrder> receipts = processReceipts();

		return JsonResponseManufacturingOrdersReport.builder()
				.transactionKey(transactionKey.toJson())
				.issues(issues)
				.receipts(receipts)
				.build();
	}

	private List<JsonResponseIssueToManufacturingOrder> processIssues()
	{
		final ArrayList<JsonResponseIssueToManufacturingOrder> result = new ArrayList<>();

		for (final JsonRequestIssueToManufacturingOrder issueRequest : request.getIssues())
		{
			try
			{
				final JsonResponseIssueToManufacturingOrder issueResult = processIssue(issueRequest);
				result.add(issueResult);

				logSuccess(issueResult, issueRequest);
			}
			catch (final Exception ex)
			{
				final AdempiereException metasfreshException = AdempiereException.wrapIfNeeded(ex);
				logError(metasfreshException, issueRequest);
				throw metasfreshException; // fail on first error
			}
		}

		return result;
	}

	private void logSuccess(
			@NonNull final JsonResponseIssueToManufacturingOrder issueResult,
			@NonNull final JsonRequestIssueToManufacturingOrder issueRequest)
	{
		final ManufacturingOrderReportAuditItemBuilder auditItemBuilder = ManufacturingOrderReportAuditItem.builder()
				.orderId(extractPPOrderId(issueRequest))
				.jsonRequest(toJsonString(issueRequest))
				.jsonResponse(toJsonString(issueResult))
				.importStatus(ManufacturingOrderReportAuditItem.ImportStatus.SUCCESS);

		issueResult.getDetails()
				.stream()
				.map(issueDetail -> auditItemBuilder
						.costCollectorId(extractCostCollectorId(issueDetail))
						.build())
				.forEach(audit::addItem);
	}

	private void logError(
			@NonNull final AdempiereException exception,
			@NonNull final JsonRequestIssueToManufacturingOrder issueRequest)
	{
		final AdIssueId adIssueId = errorManager.createIssue(exception);

		audit.addItem(ManufacturingOrderReportAuditItem.builder()
				.orderId(extractPPOrderId(issueRequest))
				.jsonRequest(toJsonString(issueRequest))
				.importStatus(ManufacturingOrderReportAuditItem.ImportStatus.FAILED)
				.errorMsg(exception.getLocalizedMessage())
				.adIssueId(adIssueId)
				.build());
	}

	private JsonResponseIssueToManufacturingOrder processIssue(@NonNull final JsonRequestIssueToManufacturingOrder issue)
	{
		final PPOrderId orderId = extractPPOrderId(issue);

		final String productNo = issue.getProductNo();
		final ProductId productId = productBL.getProductIdByValue(productNo);
		if (productId == null)
		{
			throw new AdempiereException("No product found for productNo=" + productNo)
					.appendParametersToMessage()
					.setParameter("issue", issue);
		}

		final I_C_UOM stockingUOM = productBL.getStockUOM(productId);
		final Quantity qtyToIssue = Quantity.of(issue.getQtyToIssueInStockUOM(), stockingUOM);

		if (issue.getHandlingUnits().isEmpty())
		{
			throw new AdempiereException("No HUs specified in issue")
					.appendParametersToMessage()
					.setParameter("issue", issue);
		}

		final List<I_M_HU> possibleSourceHUs = resolveHUs(productId, issue.getHandlingUnits());

		// e.g. if we have one 1000PCE-HU, but we need to issue just 10PCE, then we need to split those 10PCE from the big HU
		final List<I_M_HU> husToIssue = HUTransformService.newInstance()
				.husToNewCUs(HUTransformService.HUsToNewCUsRequest.builder()
						.sourceHUs(possibleSourceHUs)
						.productId(productId)
						.qtyCU(qtyToIssue)
						.onlyFromUnreservedHUs(true)
						.build());

		final List<I_PP_Order_Qty> processedIssueCandidates = huPPOrderBL.createIssueProducer(orderId)
				.fixedQtyToIssue(qtyToIssue)
				.processCandidates(ProcessIssueCandidatesPolicy.ALWAYS)
				.createIssues(husToIssue);

		return JsonResponseIssueToManufacturingOrder.builder()
				.requestId(issue.getRequestId())
				.details(processedIssueCandidates.stream()
						.map(this::toJsonResponseIssueToManufacturingOrderDetail)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private JsonResponseIssueToManufacturingOrderDetail toJsonResponseIssueToManufacturingOrderDetail(final I_PP_Order_Qty processedIssueCandidate)
	{
		return JsonResponseIssueToManufacturingOrderDetail.builder()
				.costCollectorId(JsonMetasfreshId.of(processedIssueCandidate.getPP_Cost_Collector_ID()))
				.huId(JsonMetasfreshId.of(processedIssueCandidate.getM_HU_ID()))
				.qty(extractQty(processedIssueCandidate))
				.build();
	}

	private JsonQuantity extractQty(final I_PP_Order_Qty candidate)
	{
		final UomId uomId = UomId.ofRepoId(candidate.getC_UOM_ID());
		final X12DE355 uomCode = uomDAO.getX12DE355ById(uomId);
		return JsonQuantity.builder()
				.qty(candidate.getQty())
				.uomCode(uomCode.getCode())
				.build();
	}

	private List<JsonResponseReceiveFromManufacturingOrder> processReceipts()
	{
		final ArrayList<JsonResponseReceiveFromManufacturingOrder> result = new ArrayList<>();

		for (final JsonRequestReceiveFromManufacturingOrder receiptRequest : request.getReceipts())
		{
			try
			{
				final JsonResponseReceiveFromManufacturingOrder receiptResult = processReceipt(receiptRequest);
				result.add(receiptResult);

				logSuccess(receiptResult, receiptRequest);
			}
			catch (final Exception ex)
			{
				final AdempiereException metasfreshException = AdempiereException.wrapIfNeeded(ex);
				logError(metasfreshException, receiptRequest);
				throw metasfreshException; // fail on first error
			}
		}

		return result;
	}

	private void logSuccess(
			@NonNull final JsonResponseReceiveFromManufacturingOrder receiptResult,
			@NonNull final JsonRequestReceiveFromManufacturingOrder receiptRequest)
	{
		final ManufacturingOrderReportAuditItemBuilder auditItemBuilder = ManufacturingOrderReportAuditItem.builder()
				.orderId(extractPPOrderId(receiptRequest))
				.jsonRequest(toJsonString(receiptRequest))
				.jsonResponse(toJsonString(receiptResult))
				.importStatus(ManufacturingOrderReportAuditItem.ImportStatus.SUCCESS);

		receiptResult.getCostCollectorIds()
				.stream()
				.map(costCollectorId -> auditItemBuilder
						.costCollectorId(toPPCostCollectorId(costCollectorId))
						.build())
				.forEach(audit::addItem);
	}

	private void logError(
			final AdempiereException exception,
			final JsonRequestReceiveFromManufacturingOrder receiptRequest)
	{
		final AdIssueId adIssueId = errorManager.createIssue(exception);

		audit.addItem(ManufacturingOrderReportAuditItem.builder()
				.orderId(extractPPOrderId(receiptRequest))
				.jsonRequest(toJsonString(receiptRequest))
				.importStatus(ManufacturingOrderReportAuditItem.ImportStatus.FAILED)
				.errorMsg(exception.getLocalizedMessage())
				.adIssueId(adIssueId)
				.build());
	}

	private JsonResponseReceiveFromManufacturingOrder processReceipt(final JsonRequestReceiveFromManufacturingOrder receipt)
	{
		final PPOrderId orderId = extractPPOrderId(receipt);
		final LocatorId locatorId = getReceiveToLocatorByOrderId(orderId);

		final ProductId productId = getFinishedGoodProductId(orderId);
		final I_C_UOM stockingUOM = productBL.getStockUOM(productId);
		final Quantity qtyToReceive = Quantity.of(receipt.getQtyToReceiveInStockUOM(), stockingUOM);

		final IPPOrderReceiptHUProducer receiptProducer = huPPOrderBL.receivingMainProduct(orderId)
				.movementDate(receipt.getDate())
				.locatorId(locatorId)
				.lotNumber(receipt.getLotNumber())
				.bestBeforeDate(receipt.getBestBeforeDate());

		final I_M_HU vhu = receiptProducer.receiveVHU(qtyToReceive);
		final Set<PPCostCollectorId> costCollectorIds = receiptProducer.getCreatedCostCollectorIds();

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
				.costCollectorIds(costCollectorIds.stream()
						.map(costCollectorId -> toJsonMetasfreshId(costCollectorId))
						.collect(ImmutableSet.toImmutableSet()))
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
		return toPPOrderId(receipt.getOrderId());
	}

	private static PPOrderId extractPPOrderId(final JsonRequestIssueToManufacturingOrder issue)
	{
		return toPPOrderId(issue.getOrderId());
	}

	@Nullable
	private static PPOrderId toPPOrderId(@Nullable final JsonMetasfreshId id)
	{
		return id != null ? PPOrderId.ofRepoIdOrNull(id.getValue()) : null;
	}

	private List<I_M_HU> resolveHUs(
			@NonNull final ProductId productId,
			@NonNull final Collection<JsonRequestHULookup> requests)
	{
		final ImmutableSet<HuId> huIds = requests.stream()
				.map(request -> resolveHUId(productId, request))
				.collect(ImmutableSet.toImmutableSet());

		final List<I_M_HU> hus = handlingUnitsBL.getByIds(huIds);
		if (hus.isEmpty())
		{
			throw new AdempiereException("No HUs found for M_Product_ID=" + productId.getRepoId() + " and requests=" + requests);
		}

		return hus;
	}

	@NonNull
	private HuId resolveHUId(
			@NonNull final ProductId productId,
			@NonNull final JsonRequestHULookup request)
	{
		final IHUQueryBuilder queryBuilder = handlingUnitsBL.createHUQueryBuilder()
				.setHUStatus(X_M_HU.HUSTATUS_Active)
				.addOnlyWithProductId(productId);

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
			throw new AdempiereException("No HU found for M_Product_ID=" + productId.getRepoId() + " and request=" + request);
		}

		return huId;
	}

	private JsonErrorItem createJsonErrorItem(@NonNull final Exception ex)
	{
		final AdIssueId adIssueId = errorManager.createIssue(ex);

		return JsonErrorItem.builder()
				.message(ex.getLocalizedMessage())
				.stackTrace(Trace.toOneLineStackTraceString(ex.getStackTrace()))
				.adIssueId(JsonMetasfreshId.of(adIssueId.getRepoId()))
				.throwable(ex)
				.build();
	}

	private String toJsonString(@NonNull final Object obj)
	{
		try
		{
			return jsonObjectMapper.writeValueAsString(obj);
		}
		catch (final JsonProcessingException ex)
		{
			logger.warn("Failed converting {} to JSON. Returning toString()", obj, ex);
			return obj.toString();
		}
	}

	@Nullable
	private static PPCostCollectorId extractCostCollectorId(@NonNull final JsonResponseIssueToManufacturingOrderDetail issueDetail)
	{
		return toPPCostCollectorId(issueDetail.getCostCollectorId());
	}

	@Nullable
	private static PPCostCollectorId toPPCostCollectorId(@Nullable final JsonMetasfreshId id)
	{
		return id != null ? PPCostCollectorId.ofRepoIdOrNull(id.getValue()) : null;
	}

	@Nullable
	private static JsonMetasfreshId toJsonMetasfreshId(@Nullable final PPCostCollectorId costCollectorId)
	{
		return costCollectorId != null ? JsonMetasfreshId.of(costCollectorId.getRepoId()) : null;
	}
}
