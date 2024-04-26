package de.metas.material.cockpit.availableforsales.interceptor;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.SetMultimap;
import de.metas.Profiles;
import de.metas.common.util.time.SystemTime;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.i18n.AdMessageKey;
import de.metas.material.cockpit.availableforsales.AvailableForSalesConfig;
import de.metas.material.cockpit.availableforsales.AvailableForSalesMultiQuery;
import de.metas.material.cockpit.availableforsales.AvailableForSalesMultiResult;
import de.metas.material.cockpit.availableforsales.AvailableForSalesQuery;
import de.metas.material.cockpit.availableforsales.AvailableForSalesResult;
import de.metas.material.cockpit.availableforsales.AvailableForSalesResult.Quantities;
import de.metas.material.cockpit.availableforsales.AvailableForSalesService;
import de.metas.material.cockpit.availableforsales.EnqueueAvailableForSalesRequest;
import de.metas.material.cockpit.availableforsales.model.I_C_OrderLine;
import de.metas.material.event.commons.AttributesKey;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.user.UserId;
import de.metas.util.ColorId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeyPatternsUtil;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static java.util.stream.Collectors.groupingBy;

/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Component
public class AvailableForSalesUtil
{
	private final AvailableForSalesService availableForSalesService;
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IOrderDAO ordersDAO = Services.get(IOrderDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public AvailableForSalesUtil(@NonNull final AvailableForSalesService availableForSalesService)
	{
		this.availableForSalesService = availableForSalesService;
	}

	public boolean isOrderEligibleForFeature(@NonNull final I_C_Order orderRecord)
	{
		final IOrderBL orderBL = Services.get(IOrderBL.class);
		if (orderBL.isSalesProposalOrQuotation(orderRecord))
		{
			return false;
		}
		return orderRecord.isSOTrx();
	}

	public boolean isOrderLineEligibleForFeature(@NonNull final I_C_OrderLine orderLineRecord)
	{
		if (orderLineRecord.getQtyEntered().signum() <= 0)
		{
			return false;
		}

		final ProductId productId = ProductId.ofRepoIdOrNull(orderLineRecord.getM_Product_ID());
		if (productId == null)
		{
			return false;
		}

		final I_M_Product productRecord = Services.get(IProductDAO.class).getById(productId);
		return productRecord.isStocked();
	}

	public boolean isOrderEligibleForFeature(@NonNull final OrderId orderId)
	{
		final I_C_Order orderRecord = ordersDAO.getById(orderId);

		return isOrderEligibleForFeature(orderRecord);
	}

	public List<CheckAvailableForSalesRequest> createRequests(@NonNull final I_C_Order orderRecord)
	{
		final ImmutableList.Builder<CheckAvailableForSalesRequest> result = ImmutableList.builder();

		final List<I_C_OrderLine> orderLineRecords = ordersDAO.retrieveOrderLines(orderRecord, I_C_OrderLine.class);
		for (final I_C_OrderLine orderLineRecord : orderLineRecords)
		{
			if (isOrderLineEligibleForFeature(orderLineRecord))
			{
				result.add(createRequest(orderLineRecord));
			}
		}
		return result.build();
	}

	public CheckAvailableForSalesRequest createRequest(@NonNull final I_C_OrderLine orderLineRecord)
	{
		final I_C_Order orderRecord = orderLineRecord.getC_Order();
		final Timestamp preparationDate = coalesce(orderRecord.getPreparationDate(), orderRecord.getDatePromised());

		return CheckAvailableForSalesRequest
				.builder()
				.orderLineId(OrderLineId.ofRepoId(orderLineRecord.getC_OrderLine_ID()))
				.productId(ProductId.ofRepoId(orderLineRecord.getM_Product_ID()))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(orderLineRecord.getM_AttributeSetInstance_ID()))
				.preparationDate(preparationDate)
				.build();
	}

	public void syncAvailableForSalesForOrder(
			@NonNull final I_C_Order orderRecord,
			@NonNull final AvailableForSalesConfig config)
	{
		final List<I_C_OrderLine> orderLineRecords = ordersDAO.retrieveOrderLines(orderRecord, I_C_OrderLine.class);
		for (final I_C_OrderLine orderLineRecord : orderLineRecords)
		{
			if (isOrderLineEligibleForFeature(orderLineRecord))
			{
				syncAvailableForSalesForOrderLine(orderLineRecord, config);
			}
		}
	}

	@NonNull
	public EnqueueAvailableForSalesRequest createRequestWithPreparationDateNow(
			@NonNull final Properties ctx,
			@NonNull final AvailableForSalesConfig config,
			@NonNull final ProductId productId,
			@NonNull final OrgId orgId,
			@NonNull final AttributesKey storageAttributesKey)
	{
		return EnqueueAvailableForSalesRequest.of(AvailableForSalesQuery
														  .builder()
														  .dateOfInterest(SystemTime.asInstant())
														  .productId(productId)
														  .storageAttributesKeyPattern(AttributesKeyPatternsUtil.ofAttributeKey(storageAttributesKey))
														  .orgId(orgId)
														  .shipmentDateLookAheadHours(config.getShipmentDateLookAheadHours())
														  .salesOrderLookBehindHours(config.getSalesOrderLookBehindHours())
														  .build(),
												  ctx);
	}

	public void syncAvailableForSalesForOrderLine(
			@NonNull final I_C_OrderLine orderLineRecord,
			@NonNull final AvailableForSalesConfig config)
	{
		final Properties ctx = Env.copyCtx(InterfaceWrapperHelper.getCtx(orderLineRecord));
		final ProductId productId = ProductId.ofRepoId(orderLineRecord.getM_Product_ID());
		final OrgId orgId = OrgId.ofRepoId(orderLineRecord.getAD_Org_ID());

		final AttributesKey storageAttributesKey = AttributesKeys
				.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoIdOrNone(orderLineRecord.getM_AttributeSetInstance_ID()))
				.orElse(AttributesKey.NONE);

		final EnqueueAvailableForSalesRequest enqueueAvailableForSalesRequest = createRequestWithPreparationDateNow(ctx, config, productId, orgId, storageAttributesKey);

		trxManager.runAfterCommit(() -> availableForSalesService
				.enqueueAvailableForSalesRequest(enqueueAvailableForSalesRequest));
	}

	@Value
	@Builder
	public static class CheckAvailableForSalesRequest
	{
		OrderLineId orderLineId;

		ProductId productId;

		AttributeSetInstanceId attributeSetInstanceId;

		Timestamp preparationDate;
	}

	@Value
	@Builder
	private static class CheckAvailableForSalesRequestContext
	{
		@NonNull
		AvailableForSalesConfig config;

		@NonNull
		UserId errorNotificationRecipient;

		@NonNull
		OrgId orgId;
	}

	private class CheckAvailableForSalesRequestsCollector
	{
		private final SetMultimap<CheckAvailableForSalesRequestContext, CheckAvailableForSalesRequest> requests = MultimapBuilder.hashKeys().hashSetValues().build();

		public void collect(
				@NonNull final List<CheckAvailableForSalesRequest> requests,
				@NonNull final AvailableForSalesConfig config,
				@NonNull final UserId errorNotificationRecipient,
				@NonNull final OrgId orgId)
		{
			final CheckAvailableForSalesRequestContext context = CheckAvailableForSalesRequestContext.builder()
					.config(config)
					.errorNotificationRecipient(errorNotificationRecipient)
					.orgId(orgId)
					.build();

			this.requests.putAll(context, requests);
		}

		public void processAsync()
		{
			requests.asMap().forEach(this::processAsync);
		}

		private void processAsync(final CheckAvailableForSalesRequestContext context, final Collection<CheckAvailableForSalesRequest> requests)
		{
			final AvailableForSalesConfig config = context.getConfig();
			final UserId errorNotificationRecipient = context.getErrorNotificationRecipient();
			final OrgId orgId = context.getOrgId();

			retrieveDataAndUpdateOrderLinesAsync(requests, config, errorNotificationRecipient, orgId);
		}

	}

	public void checkAndUpdateOrderLineRecords(
			@NonNull final List<CheckAvailableForSalesRequest> requests,
			@NonNull final AvailableForSalesConfig config,
			@NonNull final OrgId orgId)
	{
		if (requests.isEmpty())
		{
			return; // nothing to do
		}

		if (config.isRunAsync() && SpringContextHolder.instance.isSpringProfileActive(Profiles.PROFILE_Webui))
		{
			final UserId errorNotificationRecipient = Env.getLoggedUserId();

			final ITrx currentTrx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
			if (trxManager.isActive(currentTrx))
			{
				final CheckAvailableForSalesRequestsCollector collector = currentTrx.getPropertyAndProcessAfterCommit(
						CheckAvailableForSalesRequestsCollector.class.getName(),
						CheckAvailableForSalesRequestsCollector::new,
						CheckAvailableForSalesRequestsCollector::processAsync);

				collector.collect(requests, config, errorNotificationRecipient, orgId);
			}
			else
			{
				retrieveDataAndUpdateOrderLinesAsync(requests, config, errorNotificationRecipient, orgId);
			}
		}
		else
		{
			retrieveDataAndUpdateOrderLines(requests, config, orgId);
		}
	}

	/**
	 * @param errorNotificationRecipient user to receive a notification if something goes wrong within the async thread
	 */
	private void retrieveDataAndUpdateOrderLinesAsync(
			@NonNull final Collection<CheckAvailableForSalesRequest> requests,
			@NonNull final AvailableForSalesConfig config,
			@NonNull final UserId errorNotificationRecipient,
			@NonNull final OrgId orgId)
	{
		// We cannot use a thread-inherited transaction that would otherwise be used by default.
		// Because when this method is called, it means that the thread-inherited transaction is already committed
		// Therefore, let's create our own trx to work in
		final Runnable runnable = () -> trxManager.runInNewTrx(() -> retrieveDataAndUpdateOrderLines(requests, config, orgId));

		final ExecutorService executor = Executors.newSingleThreadExecutor();
		final Future<?> future = executor.submit(runnable);
		try
		{
			future.get(config.getAsyncTimeoutMillis(), TimeUnit.MILLISECONDS);
		}
		catch (final InterruptedException | ExecutionException | TimeoutException ex)
		{
			handleAsyncException(errorNotificationRecipient, ex);
		}
	}

	private void handleAsyncException(@NonNull final UserId errorNotificationRecipient, @NonNull final Exception e1)
	{
		final Throwable cause = AdempiereException.extractCause(e1);
		final AdIssueId issueId = Services.get(IErrorManager.class).createIssue(cause);

		final TargetRecordAction targetAction = TargetRecordAction
				.ofRecordAndWindow(
						TableRecordReference.of(I_AD_Issue.Table_Name, issueId),
						IErrorManager.AD_ISSUE_WINDOW_ID.getRepoId());

		final UserNotificationRequest userNotificationRequest = UserNotificationRequest.builder()
				.important(true)
				.recipientUserId(errorNotificationRecipient)
				.subjectADMessage(AdMessageKey.of(I_AD_Issue.COLUMNNAME_AD_Issue_ID))
				.contentPlain(AdempiereException.extractMessage(cause))
				.targetAction(targetAction)
				.build();
		Services.get(INotificationBL.class).send(userNotificationRequest);
	}

	@VisibleForTesting
	void retrieveDataAndUpdateOrderLines(
			@NonNull final Collection<CheckAvailableForSalesRequest> requests,
			@NonNull final AvailableForSalesConfig config,
			@NonNull final OrgId orgId)
	{
		final ImmutableMultimap<AvailableForSalesQuery, OrderLineId> //
				query2OrderLineIds = createQueries(requests, config, orgId);

		final AvailableForSalesMultiQuery availableForSalesMultiQuery = AvailableForSalesMultiQuery
				.builder()
				.availableForSalesQueries(query2OrderLineIds.keySet())
				.build();

		// in here, the thread-inherited transaction is our *new* not-yet-committed/closed transaction
		final ImmutableMap<OrderLineId, Quantities> //
				qtyIncludingSalesOrderLine = retrieveAvailableQty(query2OrderLineIds, availableForSalesMultiQuery);

		for (final Entry<OrderLineId, Quantities> entry : qtyIncludingSalesOrderLine.entrySet())
		{
			final OrderLineId orderLineId = entry.getKey();
			final Quantities quantities = entry.getValue();
			final ColorId insufficientQtyAvailableForSalesColorId = config.getInsufficientQtyAvailableForSalesColorId();

			updateOrderLineRecord(orderLineId, quantities, insufficientQtyAvailableForSalesColorId);
		}
	}

	private ImmutableMultimap<AvailableForSalesQuery, OrderLineId> createQueries(
			@NonNull final Collection<CheckAvailableForSalesRequest> requests,
			@NonNull final AvailableForSalesConfig config,
			@NonNull final OrgId orgId)
	{
		final ImmutableMultimap.Builder<AvailableForSalesQuery, OrderLineId> query2OrderLineId = ImmutableMultimap.builder();

		for (final CheckAvailableForSalesRequest request : requests)
		{
			final Instant dateOfInterest = TimeUtil.asInstant(request.getPreparationDate());
			final ProductId productId = request.getProductId();
			final AttributesKey storageAttributesKey = AttributesKeys
					.createAttributesKeyFromASIStorageAttributes(request.getAttributeSetInstanceId())
					.orElse(AttributesKey.NONE);

			final AvailableForSalesQuery availableForSalesQuery = AvailableForSalesQuery
					.builder()
					.orgId(orgId)
					.dateOfInterest(dateOfInterest)
					.productId(productId)
					.storageAttributesKeyPattern(AttributesKeyPatternsUtil.ofAttributeKey(storageAttributesKey))
					.shipmentDateLookAheadHours(config.getShipmentDateLookAheadHours())
					.salesOrderLookBehindHours(config.getSalesOrderLookBehindHours())
					.build();

			query2OrderLineId.put(availableForSalesQuery, request.getOrderLineId());
		}

		return query2OrderLineId.build();
	}

	@NonNull
	private ImmutableMap<OrderLineId, Quantities> retrieveAvailableQty(
			@NonNull final ImmutableMultimap<AvailableForSalesQuery, OrderLineId> query2OrderLineIds,
			@NonNull final AvailableForSalesMultiQuery availableForSalesMultiQuery)
	{
		final ImmutableMap.Builder<OrderLineId, Quantities> result = ImmutableMap.builder();

		final AvailableForSalesMultiResult multiResult = availableForSalesService.computeAvailableForSales(availableForSalesMultiQuery);
		final Map<AvailableForSalesQuery, List<AvailableForSalesResult>> query2Results = multiResult.getAvailableForSalesResults()
				.stream()
				.collect(groupingBy(AvailableForSalesResult::getAvailableForSalesQuery));

		for (final AvailableForSalesQuery query : query2OrderLineIds.keySet())
		{
			for (final OrderLineId orderLineId : query2OrderLineIds.get(query))
			{
				result.put(orderLineId, computeQuantitiesForQuery(query2Results.get(query)));
			}
		}
		return result.build();
	}

	private void updateOrderLineRecord(
			@NonNull final OrderLineId orderLineId,
			@NonNull final Quantities quantities,
			@NonNull final ColorId insufficientQtyAvailableForSalesColorId)
	{
		final I_C_OrderLine salesOrderLineRecord = ordersDAO.getOrderLineById(orderLineId, I_C_OrderLine.class);

		// We do everything in the order line's UOM right from the start in order to depend on QtyEntered as opposed to QtyOrdered.
		// Because QtyEntered is what the user can see.. (who knows, QtyOrdered might even be zero in some cases)
		final ProductId productId = ProductId.ofRepoId(salesOrderLineRecord.getM_Product_ID());

		final Quantity qtyToBeShippedInOrderLineUOM = orderLineBL.convertQtyToUOM(Quantitys.of(quantities.getQtyToBeShipped(), productId), salesOrderLineRecord);
		final Quantity qtyOnHandInOrderLineUOM = orderLineBL.convertQtyToUOM(Quantitys.of(quantities.getQtyOnHandStock(), productId), salesOrderLineRecord);

		// QtyToBeShippedInOrderLineUOM includes the salesOrderLineRecord.getQtyEntered().
		// We subtract it again to make it comparable with the orderLine's qtyOrdered.
		final BigDecimal qtyToBeShippedEff = qtyToBeShippedInOrderLineUOM.toBigDecimal().subtract(salesOrderLineRecord.getQtyEntered());
		final BigDecimal qtyAvailableForSales = qtyOnHandInOrderLineUOM.toBigDecimal().subtract(qtyToBeShippedEff);

		salesOrderLineRecord.setQtyAvailableForSales(qtyAvailableForSales);

		if (qtyAvailableForSales.compareTo(salesOrderLineRecord.getQtyEntered()) < 0)
		{
			salesOrderLineRecord.setInsufficientQtyAvailableForSalesColor_ID(insufficientQtyAvailableForSalesColorId.getRepoId());
		}
		else
		{
			salesOrderLineRecord.setInsufficientQtyAvailableForSalesColor(null);
		}

		ordersDAO.save(salesOrderLineRecord);
	}

	@NonNull
	private Quantities computeQuantitiesForQuery(@Nullable final List<AvailableForSalesResult> results)
	{
		if (results == null)
		{
			return Quantities.builder()
					.qtyOnHandStock(BigDecimal.ZERO)
					.qtyToBeShipped(BigDecimal.ZERO)
					.build();
		}

		final BigDecimal qtyOnHandStock = results
				.stream()
				.map(AvailableForSalesResult::getQuantities)
				.map(Quantities::getQtyOnHandStock)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		final BigDecimal qtyToBeShipped = results
				.stream()
				.map(AvailableForSalesResult::getQuantities)
				.map(Quantities::getQtyToBeShipped)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return Quantities.builder()
				.qtyOnHandStock(qtyOnHandStock)
				.qtyToBeShipped(qtyToBeShipped)
				.build();
	}
}
