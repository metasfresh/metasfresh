package de.metas.shipping;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.sql.Timestamp;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.I_M_Package;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_C_Order;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.shipping.api.IShipperTransportationBL;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;

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
@Repository
public class PurchaseOrderToShipperTransportationRepository
{
	public boolean purchaseOrderNotInShipperTransportation(@NonNull final OrderId purchaseOrderId)
	{
		return !Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShippingPackage.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShippingPackage.COLUMNNAME_C_Order_ID, purchaseOrderId)
				.create()
				.anyMatch();
	}

	public void addPurchaseOrderToShipperTransportation(
			@NonNull final OrderId purchaseOrderId,
			@NonNull final ShipperTransportationId shipperTransportationId)
	{

		final I_M_ShipperTransportation shipperTransportation = Services.get(IShipperTransportationDAO.class).getById(shipperTransportationId);
		final org.compiere.model.I_C_Order purchaseOrder = Services.get(IOrderDAO.class).getById(purchaseOrderId);

		final Timestamp deliverydate = purchaseOrder.getDatePromised();

		final I_M_Package mpackage = newInstance(I_M_Package.class);
		mpackage.setM_Shipper_ID(shipperTransportation.getM_Shipper_ID());
		mpackage.setShipDate(deliverydate);
		mpackage.setC_BPartner_ID(purchaseOrder.getC_BPartner_ID());
		mpackage.setC_BPartner_Location_ID(purchaseOrder.getC_BPartner_Location_ID());
		save(mpackage);

		final I_M_ShippingPackage shippingPackage = Services.get(IShipperTransportationBL.class)
				.createShippingPackage(shipperTransportation, mpackage);
		shippingPackage.setC_Order_ID(purchaseOrder.getC_Order_ID());
		shippingPackage.setIsToBeFetched(true);
		save(shippingPackage);
	}

	public ImmutableList<OrderId> retrieveEligiblePurchaseOrders(IQueryFilter<I_C_Order> queryFilter)
	{

		final ImmutableList<OrderId> validPurchaseOrdersIds = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Order.class)
				.filter(queryFilter)
				.addEqualsFilter(I_C_Order.COLUMNNAME_IsSOTrx, false)
				.create()
				.listIds(OrderId::ofRepoId)
				.stream()
				.filter(orderId -> purchaseOrderNotInShipperTransportation(orderId))
				.collect(ImmutableList.toImmutableList());

		return validPurchaseOrdersIds;
	}

}
