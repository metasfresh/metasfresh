package de.metas.purchasecandidate.purchaseordercreation.localorder;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.i18n.ADMessageAndParams;
import de.metas.i18n.AdMessageKey;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderFactory;
import de.metas.order.OrderId;
import de.metas.order.OrderLineBuilder;
import de.metas.order.event.OrderUserNotifications;
import de.metas.order.event.OrderUserNotifications.NotificationRequest;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Creates one purchase order from given candidates.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
/* package */ final class PurchaseOrderFromItemFactory
{
	@VisibleForTesting
	final static AdMessageKey MSG_Different_DatePromised = //
			AdMessageKey.of("de.metas.purchasecandidate.Event_PurchaseOrderCreated_Different_DatePromised");

	@VisibleForTesting
	final static AdMessageKey MSG_Different_Quantity = //
			AdMessageKey.of("de.metas.purchasecandidate.Event_PurchaseOrderCreated_Different_Quantity");

	@VisibleForTesting
	final static AdMessageKey MSG_Different_Quantity_AND_DatePromised = //
			AdMessageKey.of("de.metas.purchasecandidate.Event_PurchaseOrderCreated_Different_Quantity_And_DatePromised");

	private final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final OrderFactory orderFactory;

	private final IdentityHashMap<PurchaseOrderItem, OrderLineBuilder> purchaseItem2OrderLine = new IdentityHashMap<>();
	private final OrderUserNotifications userNotifications;

	@Builder
	private PurchaseOrderFromItemFactory(
			@NonNull final PurchaseOrderAggregationKey orderAggregationKey,
			@NonNull final OrderUserNotifications userNotifications,
			@Nullable final DocTypeId docType)
	{
		final BPartnerId vendorId = orderAggregationKey.getVendorId();
		final OrgId orgId = orderAggregationKey.getOrgId();

		this.orderFactory = OrderFactory.newPurchaseOrder()
				.orgId(orgId)
				.warehouseId(orderAggregationKey.getWarehouseId())
				.shipBPartner(vendorId)
				.datePromised(orderAggregationKey.getDatePromised())
				.poReference(orderAggregationKey.getPoReference())
				.externalPurchaseOrderUrl(orderAggregationKey.getExternalPurchaseOrderUrl())
				.externalHeaderId(orderAggregationKey.getExternalId())
				.dateOrdered(SystemTime.asLocalDate(orgDAO.getTimeZone(orgId)));

		if (docType != null)
		{
			orderFactory.docType(docType);
		}

		this.userNotifications = userNotifications;
	}

	public void addCandidate(@NonNull final PurchaseOrderItem purchaseOrderItem)
	{
		final OrderLineBuilder orderLineBuilder = orderFactory
				.orderLineByProductAndUom(
						purchaseOrderItem.getProductId(),
						purchaseOrderItem.getAttributeSetInstanceId(),
						UomId.ofRepoId(purchaseOrderItem.getUomId()) // note that all purchaseOrderItems need to have the same UOM anyways, or the follosing addQty will fail
				)
				.orElseGet(orderFactory::newOrderLine)
				.productId(purchaseOrderItem.getProductId())
				.asiId(purchaseOrderItem.getAttributeSetInstanceId())
				.productDescription(purchaseOrderItem.getProductDescription())
				.activityId(purchaseOrderItem.getActivityId());

		orderLineBuilder.addQty(purchaseOrderItem.getPurchasedQty());

		orderLineBuilder.setDimension(purchaseOrderItem.getDimension());
		if (purchaseOrderItem.getDiscount() != null)
		{
			orderLineBuilder.manualDiscount(purchaseOrderItem.getDiscount().toBigDecimal());
		}
		orderLineBuilder.manualPrice(purchaseOrderItem.getPrice());
		orderLineBuilder.priceUomId(purchaseOrderItem.getPriceUomId());

		purchaseItem2OrderLine.put(purchaseOrderItem, orderLineBuilder);
	}

	public I_C_Order createAndComplete()
	{
		final I_C_Order order = orderFactory.createAndComplete();

		purchaseItem2OrderLine
				.forEach(this::updatePurchaseCandidateFromOrderLineBuilder);

		final Set<UserId> userIdsToNotify = getUserIdsToNotify();
		if (userIdsToNotify.isEmpty())
		{
			return order;
		}

		final ADMessageAndParams adMessageAndParams = createMessageAndParamsOrNull(order);

		final NotificationRequest request = NotificationRequest.builder()
				.order(order)
				.recipientUserIds(userIdsToNotify)
				.adMessageAndParams(adMessageAndParams)
				.build();

		userNotifications.notifyOrderCompleted(request);

		return order;
	}

	private void updatePurchaseCandidateFromOrderLineBuilder(
			@NonNull final PurchaseOrderItem purchaseOrderItem,
			@NonNull final OrderLineBuilder orderLineBuilder)
	{
		purchaseOrderItem.setPurchaseOrderLineId(orderLineBuilder.getCreatedOrderAndLineId());
		final DocTypeId docTypeTargetId = orderFactory.getDocTypeTargetId();
		if (docTypeTargetId != null && docTypeBL.isRequisition(docTypeTargetId))
		{
			purchaseOrderItem.markReqCreatedIfNeeded();
		}
		else
		{
			purchaseOrderItem.markPurchasedIfNeeded();
		}
	}

	@Nullable
	private ADMessageAndParams createMessageAndParamsOrNull(@NonNull final I_C_Order order)
	{
		boolean deviatingDatePromised = false;
		boolean deviatingQuantity = false;
		for (final PurchaseOrderItem purchaseOrderItem : purchaseItem2OrderLine.keySet())
		{
			final ZonedDateTime purchaseDatePromised = purchaseOrderItem.getPurchaseDatePromised();
			final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(order.getAD_Org_ID()));
			if (!Objects.equals(purchaseDatePromised, TimeUtil.asZonedDateTime(order.getDatePromised(), timeZone)))
			{
				deviatingDatePromised = true;
			}
			if (!purchaseOrderItem.purchaseMatchesRequiredQty())
			{
				deviatingQuantity = true;
			}
		}

		if (deviatingDatePromised && deviatingQuantity)
		{
			return ADMessageAndParams.builder()
					.adMessage(MSG_Different_Quantity_AND_DatePromised)
					.params(createCommonMessageParams(order))
					.build();
		}
		else if (deviatingQuantity)
		{
			return ADMessageAndParams.builder()
					.adMessage(MSG_Different_Quantity)
					.params(createCommonMessageParams(order))
					.build();
		}
		else if (deviatingDatePromised)
		{
			return ADMessageAndParams.builder()
					.adMessage(MSG_Different_DatePromised)
					.params(createCommonMessageParams(order))
					.build();
		}
		else
		{
			return null;
		}
	}

	private static List<Object> createCommonMessageParams(@NonNull final I_C_Order order)
	{
		final I_C_BPartner bpartner = Services.get(IOrderBL.class).getBPartner(order);
		final String bpValue = bpartner.getValue();
		final String bpName = bpartner.getName();
		return ImmutableList.of(TableRecordReference.of(order), bpValue, bpName);
	}

	private Set<UserId> getUserIdsToNotify()
	{
		final ImmutableSet<Integer> salesOrderIds = purchaseItem2OrderLine.keySet()
				.stream()
				.map(PurchaseOrderItem::getSalesOrderId)
				.filter(Objects::nonNull)
				.map(OrderId::getRepoId)
				.collect(ImmutableSet.toImmutableSet());

		return ordersRepo.retriveOrderCreatedByUserIds(salesOrderIds);
	}
}
