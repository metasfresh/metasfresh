/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.manufacturing.rest_api.v2;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrder;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrderBOMLine;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrdersBulk;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.error.IssueCreateRequest;
import de.metas.logging.LogManager;
import de.metas.manufacturing.order.exportaudit.APIExportStatus;
import de.metas.manufacturing.order.exportaudit.APITransactionId;
import de.metas.manufacturing.order.exportaudit.ManufacturingOrderExportAudit;
import de.metas.manufacturing.order.exportaudit.ManufacturingOrderExportAudit.ManufacturingOrderExportAuditBuilder;
import de.metas.manufacturing.order.exportaudit.ManufacturingOrderExportAuditItem;
import de.metas.manufacturing.rest_api.ExportSequenceNumberProvider;
import de.metas.manufacturing.rest_api.ManufacturingOrderExportAuditRepository;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.Product;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.ManufacturingOrderQuery;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

final class ManufacturingOrdersExportCommand
{
	// services
	private static final Logger logger = LogManager.getLogger(ManufacturingOrdersExportCommand.class);

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);
	private final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
	private final IErrorManager errorManager = Services.get(IErrorManager.class);

	private final ExportSequenceNumberProvider exportSequenceNumberProvider;

	private final ManufacturingOrderExportAuditRepository orderAuditRepo;
	private final ProductRepository productRepo;

	// params
	private final ManufacturingOrderQuery query;
	private final String adLanguage;

	// state
	private List<I_PP_Order> _orders;
	private ImmutableListMultimap<PPOrderId, I_PP_Order_BOMLine> _bomLinesByOrderId;
	private HashMap<ProductId, Product> _productsById;

	@Builder
	private ManufacturingOrdersExportCommand(
			@NonNull final ManufacturingOrderExportAuditRepository orderAuditRepo,
			@NonNull final ProductRepository productRepo,
			@NonNull final ExportSequenceNumberProvider exportSequenceNumberProvider,
			//
			@NonNull final Instant canBeExportedFrom,
			@NonNull final QueryLimit limit,
			@NonNull final String adLanguage)
	{
		this.orderAuditRepo = orderAuditRepo;
		this.productRepo = productRepo;
		this.exportSequenceNumberProvider = exportSequenceNumberProvider;

		this.query = ManufacturingOrderQuery.builder()
				.limit(limit)
				.canBeExportedFrom(canBeExportedFrom)
				.exportStatus(APIExportStatus.Pending)
				.onlyCompleted(true)
				.build();
		this.adLanguage = adLanguage;
	}

	public JsonResponseManufacturingOrdersBulk execute()
	{
		final APITransactionId transactionKey = APITransactionId.random();
		try (final MDCCloseable ignore = MDC.putCloseable("TransactionIdAPI", transactionKey.toJson()))
		{
			final List<I_PP_Order> orders = getOrders();
			if (orders.isEmpty())
			{
				return JsonResponseManufacturingOrdersBulk.empty(transactionKey.toJson());
			}

			final ManufacturingOrderExportAuditBuilder auditCollector = ManufacturingOrderExportAudit.builder().transactionId(transactionKey);
			final ArrayList<JsonResponseManufacturingOrder> jsonOrders = new ArrayList<>();

			for (final I_PP_Order order : orders)
			{
				try
				{
					final MapToJsonResponseManufacturingOrderRequest request = buildMapToResponseManufacturingOrderRequest(order);

					jsonOrders.add(JsonConverter.toJson(request));
					auditCollector.item(createExportedAuditItem(order));
				}
				catch (final Exception ex)
				{
					final AdIssueId adIssueId = createAdIssueId(ex);
					auditCollector.item(createExportErrorAuditItem(order, adIssueId));
				}
			}

			final ManufacturingOrderExportAudit audit = auditCollector.build();
			orderAuditRepo.save(audit);
			ppOrderDAO.exportStatusMassUpdate(audit.getExportStatusesByOrderId());

			final int exportSequenceNumber = exportSequenceNumberProvider.provideNextExportSequenceNumber();

			return JsonResponseManufacturingOrdersBulk.builder()
					.transactionKey(transactionKey.toJson())
					.exportSequenceNumber(exportSequenceNumber)
					.items(jsonOrders)
					.hasMoreItems(query.getLimit().isLimitHitOrExceeded(jsonOrders))
					.build();
		}
	}

	private static ManufacturingOrderExportAuditItem createExportedAuditItem(@NonNull final I_PP_Order order)
	{
		return ManufacturingOrderExportAuditItem.builder()
				.orderId(PPOrderId.ofRepoId(order.getPP_Order_ID()))
				.orgId(OrgId.ofRepoIdOrAny(order.getAD_Org_ID()))
				.exportStatus(APIExportStatus.Exported)
				.build();
	}

	private static ManufacturingOrderExportAuditItem createExportErrorAuditItem(final I_PP_Order order, final AdIssueId adIssueId)
	{
		return ManufacturingOrderExportAuditItem.builder()
				.orderId(PPOrderId.ofRepoId(order.getPP_Order_ID()))
				.orgId(OrgId.ofRepoIdOrAny(order.getAD_Org_ID()))
				.exportStatus(APIExportStatus.ExportError)
				.issueId(adIssueId)
				.build();
	}

	private AdIssueId createAdIssueId(final Throwable ex)
	{
		return errorManager.createIssue(IssueCreateRequest.builder()
				.throwable(ex)
				.loggerName(logger.getName())
				.sourceClassname(ManufacturingOrderAPIService.class.getName())
				.summary(ex.getMessage())
				.build());
	}

	private List<I_PP_Order_BOMLine> getBOMLinesByOrderId(final PPOrderId orderId)
	{
		return getBOMLinesByOrderId().get(orderId);
	}

	private ImmutableListMultimap<PPOrderId, I_PP_Order_BOMLine> getBOMLinesByOrderId()
	{
		if (_bomLinesByOrderId != null)
		{
			return _bomLinesByOrderId;
		}

		final ImmutableSet<PPOrderId> orderIds = getOrders()
				.stream()
				.map(order -> PPOrderId.ofRepoId(order.getPP_Order_ID()))
				.collect(ImmutableSet.toImmutableSet());

		_bomLinesByOrderId = ppOrderBOMDAO.retrieveOrderBOMLines(orderIds, I_PP_Order_BOMLine.class)
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						bomLine -> PPOrderId.ofRepoId(bomLine.getPP_Order_ID()),
						bomLine -> bomLine));

		return _bomLinesByOrderId;
	}

	private Product getProductById(@NonNull final ProductId productId)
	{
		if (_productsById == null)
		{
			final HashSet<ProductId> allProductIds = new HashSet<>();
			allProductIds.add(productId);
			getOrders().stream()
					.map(order -> ProductId.ofRepoId(order.getM_Product_ID()))
					.forEach(allProductIds::add);

			getBOMLinesByOrderId()
					.values()
					.stream()
					.map(bomLine -> ProductId.ofRepoId(bomLine.getM_Product_ID()))
					.forEach(allProductIds::add);

			_productsById = productRepo.getByIds(allProductIds)
					.stream()
					.collect(GuavaCollectors.toHashMapByKey(Product::getId));
		}

		return _productsById.computeIfAbsent(productId, productRepo::getById);
	}

	private List<I_PP_Order> getOrders()
	{
		if (_orders == null)
		{
			_orders = ppOrderDAO.retrieveManufacturingOrders(query);
		}
		return _orders;
	}

	@NonNull
	private MapToJsonResponseManufacturingOrderRequest buildMapToResponseManufacturingOrderRequest(@NonNull final I_PP_Order ppOrder)
	{
		return  MapToJsonResponseManufacturingOrderRequest
				.builder()
				.productRepository(productRepo)
				.orgDAO(orgDAO)
				.ppOrderBOMBL(ppOrderBOMBL)
				.order(ppOrder)
				.components(getBOMLines(PPOrderId.ofRepoId(ppOrder.getPP_Order_ID())))
				.build();
	}

	@NonNull
	private ImmutableList<JsonResponseManufacturingOrderBOMLine> getBOMLines(@NonNull final PPOrderId ppOrderId)
	{
		return getBOMLinesByOrderId(ppOrderId)
				.stream()
				.map(bomLine -> JsonConverter.toJson(bomLine, ppOrderBOMBL, productRepo))
				.collect(ImmutableList.toImmutableList());
	}
}
