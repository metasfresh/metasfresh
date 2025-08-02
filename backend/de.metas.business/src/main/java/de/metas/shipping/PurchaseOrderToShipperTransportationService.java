package de.metas.shipping;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.impl.CreateShipperTransportationRequest;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.sscc18.ISSCC18CodeBL;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

/*
 * #%L
 * de.metas.business
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
@Service
@RequiredArgsConstructor
public class PurchaseOrderToShipperTransportationService
{
	@NonNull private final PurchaseOrderToShipperTransportationRepository repo;

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);
	private final ISSCC18CodeBL sscc18CodeBL = Services.get(ISSCC18CodeBL.class);

	public static PurchaseOrderToShipperTransportationService newInstanceForUnitTesting()
	{
		return new PurchaseOrderToShipperTransportationService(new PurchaseOrderToShipperTransportationRepository());
	}

	public void addPurchaseOrdersToShipperTransportation(@NonNull final ShipperTransportationId shipperTransportationId, @NonNull final IQueryFilter<I_C_Order> queryFilter)
	{
		final ImmutableList<OrderId> validPurchaseOrdersIds = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Order.class)
				.addInArrayFilter(I_C_Order.COLUMNNAME_DocStatus, DocStatus.Completed, DocStatus.Closed)
				.filter(queryFilter)
				.create()
				.idsAsSet(OrderId::ofRepoId)
				.stream()
				.filter(repo::purchaseOrderNotInShipperTransportation)
				.collect(ImmutableList.toImmutableList());

		for (final OrderId purchaseOrderId : validPurchaseOrdersIds)
		{
			addPurchaseOrderToShipperTransportation(purchaseOrderId, shipperTransportationId);
		}
	}

	public void addPurchaseOrderToCurrentShipperTransportation(final @NonNull I_C_Order purchaseOrder)
	{
		final ShipperTransportationId shipperTransportationId = shipperTransportationDAO.getOrCreate(CreateShipperTransportationRequest.builder()
				.shipperId(ShipperId.ofRepoId(purchaseOrder.getM_Shipper_ID()))
				.orgId(OrgId.ofRepoId(purchaseOrder.getAD_Org_ID()))
				.assignAnonymouslyPickedHUs(true)
				.shipDate(TimeUtil.asLocalDate(purchaseOrder.getDatePromised()))
				.shipperBPartnerAndLocationId(BPartnerLocationId.ofRepoId(BPartnerId.ofRepoId(purchaseOrder.getC_BPartner_ID()), purchaseOrder.getC_BPartner_Location_ID()))
				.build());
		addPurchaseOrderToShipperTransportation(purchaseOrder, shipperTransportationId);
	}

	public void addPurchaseOrderToShipperTransportation(final @NonNull OrderId purchaseOrderId, final @Nullable ShipperTransportationId shipperTransportationId)
	{
		addPurchaseOrderToShipperTransportation(orderDAO.getById(purchaseOrderId), shipperTransportationId);
	}

	private void addPurchaseOrderToShipperTransportation(final @NonNull org.compiere.model.I_C_Order order, final @Nullable ShipperTransportationId shipperTransportationId)
	{
		final ShipperTransportationId shipperTransportationIdToUse = shipperTransportationId != null
				? shipperTransportationId
				: shipperTransportationDAO.getOrCreate(CreateShipperTransportationRequest.builder()
				.shipperId(ShipperId.ofRepoId(order.getM_Shipper_ID()))
				.orgId(OrgId.ofRepoId(order.getAD_Org_ID()))
				.shipDate(TimeUtil.asLocalDate(order.getDatePromised()))
				.assignAnonymouslyPickedHUs(true)
				.shipperBPartnerAndLocationId(BPartnerLocationId.ofRepoId(BPartnerId.ofRepoId(order.getC_BPartner_ID()), order.getC_BPartner_Location_ID()))
				.build());

		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order);
		final List<I_C_OrderLine> orderLinesWithLUQty = orderLines.stream()
				.filter(orderBL::isLUQtySet)
				.collect(Collectors.toList());
		final boolean isOrderLinesWithoutLUQtyExist = orderLines.stream()
				.anyMatch(ol -> !orderLinesWithLUQty.contains(ol) && !ol.isPackagingMaterial());

		final I_M_ShipperTransportation shipperTransportation = shipperTransportationDAO.getById(shipperTransportationIdToUse);

		final BPartnerId bPartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(bPartnerId, order.getC_BPartner_Location_ID());
		final OrgId orgId = OrgId.ofRepoId(order.getAD_Org_ID());
		final PurchaseShippingPackageCreateRequest.PurchaseShippingPackageCreateRequestBuilder requestTemplate = PurchaseShippingPackageCreateRequest.builder()
				.orderId(OrderId.ofRepoId(order.getC_Order_ID()))
				.datePromised(order.getDatePromised().toInstant())
				.shipperTransportationId(shipperTransportationIdToUse)
				.shiperId(ShipperId.ofRepoId(shipperTransportation.getM_Shipper_ID()))
				.bPartnerLocationId(bPartnerLocationId)
				.orgId(orgId);
		if (isOrderLinesWithoutLUQtyExist)
		{
			//create a generic package for all order lines without LUQty set on them
			repo.addPurchaseOrderToShipperTransportation(requestTemplate
					// .sscc(sscc18CodeBL.generate(orgId)) //No requirements currently ask for this
					.build());
		}
		orderLinesWithLUQty
				.forEach(ol -> addPurchaseOrderLineToShipperTransportationId(requestTemplate, ol));
	}

	private void addPurchaseOrderLineToShipperTransportationId(@NonNull final PurchaseShippingPackageCreateRequest.PurchaseShippingPackageCreateRequestBuilder requestTemplate, @NonNull final I_C_OrderLine ol)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(ol.getC_OrderLine_ID());
		requestTemplate.orderLineId(orderLineId);
		final OrgId orgId = OrgId.ofRepoId(ol.getAD_Org_ID());
		final int qtyLUs = ol.getQtyLU().intValueExact();
		//TODO IT2 avoid creating duplicates on reopen, via de.metas.shipping.PurchaseOrderToShipperTransportationRepository.getByOrderLineId

		for (int i = 0; i < qtyLUs; i++)
		{
			repo.addPurchaseOrderToShipperTransportation(requestTemplate
					.sscc(sscc18CodeBL.generate(orgId))
					.build());
		}
	}

}
