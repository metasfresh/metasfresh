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
import de.metas.document.location.IDocumentLocationBL;
import de.metas.order.DeliveryViaRule;
import de.metas.order.IOrderBL;
import de.metas.order.location.adapter.OrderBillLocationAdapter;
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import de.metas.order.location.adapter.OrderMainLocationAdapter;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.SpringContextHolder;

@Callout(I_C_Order.class)
public class C_Order
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IWarehouseAdvisor warehouseAdvisor = Services.get(IWarehouseAdvisor.class);
	private final IDocumentLocationBL documentLocationBL = SpringContextHolder.instance.getBean(IDocumentLocationBL.class);

	@CalloutMethod(columnNames = { I_C_Order.COLUMNNAME_C_BPartner_ID })
	public void setDeliveryViaRule(final I_C_Order order, final ICalloutField field)
	{
		final DeliveryViaRule deliveryViaRule = orderBL.evaluateOrderDeliveryViaRule(order);
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
			I_C_Order.COLUMNNAME_HandOver_Location_ID},
			skipIfCopying = true)
	public void updateHandOverAddressForceUpdateCaptureLocation(final I_C_Order order)
	{
		documentLocationBL.updateCapturedLocation(OrderDocumentLocationAdapterFactory.handOverLocationAdapter(order));
	}

}
