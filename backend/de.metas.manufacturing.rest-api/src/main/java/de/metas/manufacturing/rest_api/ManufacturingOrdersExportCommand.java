package de.metas.manufacturing.rest_api;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.adempiere.ad.dao.QueryLimit;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.ManufacturingOrderQuery;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;

import de.metas.common.manufacturing.JsonResponseManufacturingOrder;
import de.metas.common.manufacturing.JsonResponseManufacturingOrderBOMLine;
import de.metas.common.manufacturing.JsonResponseManufacturingOrdersBulk;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.rest_api.JsonQuantity;
import de.metas.common.shipping.JsonProduct;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.error.IssueCreateRequest;
import de.metas.logging.LogManager;
import de.metas.manufacturing.order.exportaudit.APIExportStatus;
import de.metas.manufacturing.order.exportaudit.APITransactionId;
import de.metas.manufacturing.order.exportaudit.ManufacturingOrderExportAudit;
import de.metas.manufacturing.order.exportaudit.ManufacturingOrderExportAudit.ManufacturingOrderExportAuditBuilder;
import de.metas.manufacturing.order.exportaudit.ManufacturingOrderExportAuditItem;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.Product;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.quantity.Quantity;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.StringUtils;
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
					jsonOrders.add(toJson(order));
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

	private JsonResponseManufacturingOrder toJson(@NonNull final I_PP_Order order)
	{
		final PPOrderId orderId = PPOrderId.ofRepoId(order.getPP_Order_ID());
		final Quantity qtyToProduce = ppOrderBOMBL.getQuantities(order).getQtyRequiredToProduce();

		final OrgId orgId = OrgId.ofRepoId(order.getAD_Org_ID());
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);
		final String orgCode = orgDAO.retrieveOrgValue(orgId);

		return JsonResponseManufacturingOrder.builder()
				.orderId(JsonMetasfreshId.of(orderId.getRepoId()))
				.orgCode(orgCode)
				.documentNo(order.getDocumentNo())
				.description(StringUtils.trimBlankToNull(order.getDescription()))
				.finishGoodProduct(toJsonProduct(ProductId.ofRepoId(order.getM_Product_ID())))
				.qtyToProduce(toJsonQuantity(qtyToProduce))
				.dateOrdered(TimeUtil.asZonedDateTime(order.getDateOrdered(), timeZone))
				.dateStartSchedule(TimeUtil.asZonedDateTime(order.getDateStartSchedule(), timeZone))
				.components(getBOMLinesByOrderId(orderId)
						.stream()
						.map(this::toJson)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private JsonResponseManufacturingOrderBOMLine toJson(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		final Quantity qtyRequiredToIssue = ppOrderBOMBL.getQtyRequiredToIssue(bomLine);
		return JsonResponseManufacturingOrderBOMLine.builder()
				.componentType(bomLine.getComponentType())
				.product(toJsonProduct(ProductId.ofRepoId(bomLine.getM_Product_ID())))
				.qty(toJsonQuantity(qtyRequiredToIssue))
				.build();
	}

	private JsonProduct toJsonProduct(@NonNull final ProductId productId)
	{
		final Product product = getProductById(productId);

		return JsonProduct.builder()
				.productNo(product.getProductNo())
				.name(product.getName().translate(adLanguage))
				.description(product.getDescription().translate(adLanguage))
				.documentNote(product.getDocumentNote().translate(adLanguage))
				.packageSize(product.getPackageSize())
				.weight(product.getWeight())
				.stocked(product.isStocked())
				.build();
	}

	private JsonQuantity toJsonQuantity(@NonNull final Quantity qty)
	{
		return JsonQuantity.builder()
				.qty(qty.toBigDecimal())
				.uomCode(qty.getX12DE355().getCode())
				.build();
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
}
