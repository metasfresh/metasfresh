package org.adempiere.warehouse.spi.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IOrgDAO;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Warehouse;

import de.metas.order.OrderLineId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Default implementation of {@link IWarehouseAdvisor}.
 *
 * It's just fetching the warehouse from record's M_Warehouse_ID field and if nothing found then organization's warehouse is returned.
 *
 * @author tsa
 *
 */
public class WarehouseAdvisor implements IWarehouseAdvisor
{

	@Override
	public I_M_Warehouse evaluateWarehouse(@NonNull final OrderLineId orderLineId)
	{
		final I_C_OrderLine orderLineRecord = load(orderLineId, I_C_OrderLine.class);
		return evaluateWarehouse(orderLineRecord);
	}

	@Override
	public I_M_Warehouse evaluateWarehouse(@NonNull final I_C_OrderLine orderLine)
	{
		Check.assumeNotNull(orderLine, "Order should not be null");

		if (orderLine.getM_Warehouse_ID() > 0)
		{
			return orderLine.getM_Warehouse();
		}

		return evaluateOrderWarehouse(orderLine.getC_Order());
	}

	@Override
	public I_M_Warehouse evaluateOrderWarehouse(@NonNull final I_C_Order order)
	{
		if (order.getM_Warehouse_ID() > 0)
		{
			return order.getM_Warehouse();
		}

		return findOrderWarehouse(order);
	}

	protected I_M_Warehouse findOrderWarehouse(@NonNull final I_C_Order order)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(order);
		final int adOrgId = order.getAD_Org_ID();

		if (order.isDropShip() && !order.isSOTrx())
		{
			// task 07014: for a dropship purchase order, we take the org info's dropship warehouse. our vendor will send the good directly to our customer and it will never enter any of our physical
			// warehouses, but none the less we own it for a certain time. That'S what the dropship warehouse is for.
			// For a sales order, "dropship" means that the order's receiver is someone other than the partner who ordered. For this scenario, we don't need a particular dropship warehouse.

			final String trxName = InterfaceWrapperHelper.getTrxName(order);
			final I_AD_OrgInfo orgInfo = Services.get(IOrgDAO.class).retrieveOrgInfo(ctx, adOrgId, trxName);
			final I_M_Warehouse dropShipWarehouse = orgInfo.getDropShip_Warehouse();
			if (dropShipWarehouse == null || dropShipWarehouse.getM_Warehouse_ID() <= 0)
			{
				throw new AdempiereException("@NotFound@ @DropShip_Warehouse_ID@ (@AD_Org_ID@: " + order.getAD_Org().getName() + ")");
			}
			return dropShipWarehouse;
		}
		// first check for picking warehouse
		// this check is valid only for sales order; for purchase order will return null
		final I_M_Warehouse warehouse = findPickingWarehouse(order);
		if (warehouse != null && warehouse.getM_Warehouse_ID() > 0)
		{
			return warehouse;
		}

		return Services.get(IWarehouseDAO.class).retrieveOrgWarehouse(ctx, adOrgId);
	}

	/**
	 * Retrieve the picking warehouse based on the order's bPartner. Returns <code>null</code> if the partner is not customer, has no warehouse assigned or the order is not a sales order.
	 */
	private I_M_Warehouse findPickingWarehouse(@NonNull final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			return null;
		}

		if (order.getC_BPartner_ID() <= 0)
		{
			return null;
		}

		final de.metas.interfaces.I_C_BPartner bp = InterfaceWrapperHelper.create(order.getC_BPartner(), de.metas.interfaces.I_C_BPartner.class);
		if (!bp.isCustomer() || bp.getM_Warehouse_ID() <= 0)
		{
			return null;
		}

		final org.adempiere.warehouse.model.I_M_Warehouse partnerWarehouse = InterfaceWrapperHelper.create(bp.getM_Warehouse(), org.adempiere.warehouse.model.I_M_Warehouse.class);

		final boolean isPickingWarehouse = partnerWarehouse.isPickingWarehouse();
		if (isPickingWarehouse)
		{
			return partnerWarehouse;
		}

		// if order is a purchase order, return null
		return null;
	}
}
