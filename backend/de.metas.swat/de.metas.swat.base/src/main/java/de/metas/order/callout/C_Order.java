package de.metas.order.callout;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.order.DeliveryViaRule;
import de.metas.order.IOrderBL;
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.X_C_Order;

import java.util.Optional;

import static de.metas.common.util.CoalesceUtil.firstNotBlank;

@Callout(I_C_Order.class)
public class C_Order
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IWarehouseAdvisor warehouseAdvisor = Services.get(IWarehouseAdvisor.class);
	private final IDocumentLocationBL documentLocationBL = SpringContextHolder.instance.getBean(IDocumentLocationBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);

	@CalloutMethod(columnNames = { I_C_Order.COLUMNNAME_C_BPartner_ID })
	public void setDeliveryViaRule(final I_C_Order order, final ICalloutField field)
	{
		final DeliveryViaRule deliveryViaRule = orderBL.findDeliveryViaRule(order).orElse(null);
		if (deliveryViaRule != null)
		{
			order.setDeliveryViaRule(deliveryViaRule.getCode());
		}
	}

	@CalloutMethod(columnNames = { I_C_Order.COLUMNNAME_IsDropShip })
	public void setDropShipWarehouse(final I_C_Order order, final ICalloutField field)
	{
		if (!order.isDropShip())
		{
			return;
		}

		final WarehouseId warehouseId = warehouseAdvisor.evaluateOrderWarehouse(order);
		if (warehouseId != null)
		{
			order.setM_Warehouse_ID(warehouseId.getRepoId());
		}
	}

	@CalloutMethod(columnNames = {
			I_C_Order.COLUMNNAME_C_BPartner_ID,
			I_C_Order.COLUMNNAME_C_BPartner_Location_ID,
			I_C_Order.COLUMNNAME_AD_User_ID },
			skipIfCopying = true)
	public void updateBPartnerAddress(final I_C_Order order)
	{
		documentLocationBL.updateRenderedAddressAndCapturedLocation(OrderDocumentLocationAdapterFactory.locationAdapter(order));
	}

	@CalloutMethod(columnNames = {
			I_C_Order.COLUMNNAME_C_BPartner_ID,
			I_C_Order.COLUMNNAME_C_BPartner_Location_ID },
			skipIfCopying = true)
	public void updateBPartnerAddressForceUpdateCapturedLocation(final I_C_Order order)
	{
		documentLocationBL.updateCapturedLocation(OrderDocumentLocationAdapterFactory.locationAdapter(order));
	}

	@CalloutMethod(columnNames = {
			I_C_Order.COLUMNNAME_Bill_BPartner_ID,
			I_C_Order.COLUMNNAME_Bill_Location_ID,
			I_C_Order.COLUMNNAME_Bill_User_ID },
			skipIfCopying = true)
	public void updateBillToAddress(final I_C_Order order)
	{
		documentLocationBL.updateRenderedAddressAndCapturedLocation(OrderDocumentLocationAdapterFactory.billLocationAdapter(order));
	}

	@CalloutMethod(columnNames = {
			I_C_Order.COLUMNNAME_Bill_BPartner_ID,
			I_C_Order.COLUMNNAME_Bill_Location_ID },
			skipIfCopying = true)
	public void updateBillToAddressForceUpdateCapturedLocation(final I_C_Order order)
	{
		documentLocationBL.updateCapturedLocation(OrderDocumentLocationAdapterFactory.billLocationAdapter(order));
	}

	@CalloutMethod(columnNames = {
			I_C_Order.COLUMNNAME_IsDropShip,
			I_C_Order.COLUMNNAME_DropShip_BPartner_ID,
			I_C_Order.COLUMNNAME_DropShip_Location_ID,
			I_C_Order.COLUMNNAME_DropShip_User_ID,
			I_C_Order.COLUMNNAME_M_Warehouse_ID },
			skipIfCopying = true)
	public void updateDeliveryToAddress(final I_C_Order order)
	{
		documentLocationBL.updateRenderedAddressAndCapturedLocation(OrderDocumentLocationAdapterFactory.deliveryLocationAdapter(order));
	}

	@CalloutMethod(columnNames = {
			I_C_Order.COLUMNNAME_IsDropShip,
			I_C_Order.COLUMNNAME_DropShip_BPartner_ID,
			I_C_Order.COLUMNNAME_DropShip_Location_ID,
			I_C_Order.COLUMNNAME_M_Warehouse_ID },
			skipIfCopying = true)
	public void updateDeliveryToAddressForceUpdateCaptureLocation(final I_C_Order order)
	{
		documentLocationBL.updateCapturedLocation(OrderDocumentLocationAdapterFactory.deliveryLocationAdapter(order));
	}

	@CalloutMethod(columnNames = {
			I_C_Order.COLUMNNAME_IsUseHandOver_Location,
			I_C_Order.COLUMNNAME_HandOver_Partner_ID,
			I_C_Order.COLUMNNAME_HandOver_Location_ID,
			I_C_Order.COLUMNNAME_HandOver_User_ID },
			skipIfCopying = true)
	public void updateHandOverAddress(final I_C_Order order)
	{
		documentLocationBL.updateRenderedAddressAndCapturedLocation(OrderDocumentLocationAdapterFactory.handOverLocationAdapter(order));
	}

	@CalloutMethod(columnNames = {
			I_C_Order.COLUMNNAME_IsUseHandOver_Location,
			I_C_Order.COLUMNNAME_HandOver_Partner_ID,
			I_C_Order.COLUMNNAME_HandOver_Location_ID },
			skipIfCopying = true)
	public void updateHandOverAddressForceUpdateCaptureLocation(final I_C_Order order)
	{
		documentLocationBL.updateCapturedLocation(OrderDocumentLocationAdapterFactory.handOverLocationAdapter(order));
	}

	@CalloutMethod(columnNames = { I_C_Order.COLUMNNAME_C_BPartner_Location_ID, I_C_Order.COLUMNNAME_AD_User_ID })
	public void setContactDetails(final I_C_Order order)
	{
		if (order.getC_BPartner_Location_ID() <= 0)
		{
			return;
		}

		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(order.getC_BPartner_ID(), order.getC_BPartner_Location_ID());
		final I_C_BPartner_Location bPartnerLocationRecord = bpartnerDAO.getBPartnerLocationByIdEvenInactive(bPartnerLocationId);

		Check.assumeNotNull(bPartnerLocationRecord, "C_BPartner_Location cannot be missing for webui selection! Id: {}", bPartnerLocationId);

		final Optional<I_AD_User> userRecord = Optional.ofNullable(UserId.ofRepoIdOrNull(order.getAD_User_ID()))
				.map(userDAO::getById);

		if (userRecord.isPresent())
		{
			final I_AD_User user = userRecord.get();
			order.setBPartnerName(firstNotBlank(user.getName(), bPartnerLocationRecord.getBPartnerName()));
			order.setEMail(firstNotBlank(user.getEMail(), bPartnerLocationRecord.getEMail()));
			order.setPhone(firstNotBlank(user.getPhone(), bPartnerLocationRecord.getPhone()));
		}
		else
		{
			order.setBPartnerName(bPartnerLocationRecord.getBPartnerName());
			order.setEMail(bPartnerLocationRecord.getEMail());
			order.setPhone(bPartnerLocationRecord.getPhone());
		}
	}
	@CalloutMethod(columnNames = {
			I_C_Order.COLUMNNAME_M_Shipper_ID },
			skipIfCopying = true)
	public void updateDeliveryViaRule(final I_C_Order order)
	{
		if(order.getM_Shipper_ID() > 0)
		{
			order.setDeliveryViaRule(X_C_Order.DELIVERYVIARULE_Shipper);
		}
		else
		{
			order.setDeliveryViaRule(X_C_Order.DELIVERYVIARULE_Pickup);
		}
	}
}
