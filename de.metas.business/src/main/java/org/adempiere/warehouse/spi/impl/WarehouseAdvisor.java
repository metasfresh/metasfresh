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
import org.adempiere.service.OrgId;
import org.adempiere.util.Services;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.spi.IWarehouseAdvisor;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.order.OrderLineId;
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
	public WarehouseId evaluateWarehouse(@NonNull final OrderLineId orderLineId)
	{
		final I_C_OrderLine orderLineRecord = load(orderLineId, I_C_OrderLine.class);
		return evaluateWarehouse(orderLineRecord);
	}

	@Override
	public WarehouseId evaluateWarehouse(@NonNull final I_C_OrderLine orderLine)
	{
		final WarehouseId orderLineWarehouseId = WarehouseId.ofRepoIdOrNull(orderLine.getM_Warehouse_ID());
		if (orderLineWarehouseId != null)
		{
			return orderLineWarehouseId;
		}

		return evaluateOrderWarehouse(orderLine.getC_Order());
	}

	@Override
	public WarehouseId evaluateOrderWarehouse(@NonNull final I_C_Order order)
	{
		WarehouseId orderWarehouseId = WarehouseId.ofRepoIdOrNull(order.getM_Warehouse_ID());
		if (orderWarehouseId != null)
		{
			return orderWarehouseId;
		}

		return findOrderWarehouseId(order);
	}

	protected WarehouseId findOrderWarehouseId(@NonNull final I_C_Order order)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(order);
		final OrgId adOrgId = OrgId.ofRepoId(order.getAD_Org_ID());

		final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);
		if (order.isDropShip() && !order.isSOTrx())
		{
			// task 07014: for a dropship purchase order, we take the org info's dropship warehouse. our vendor will send the good directly to our customer and it will never enter any of our physical
			// warehouses, but none the less we own it for a certain time. That'S what the dropship warehouse is for.
			// For a sales order, "dropship" means that the order's receiver is someone other than the partner who ordered. For this scenario, we don't need a particular dropship warehouse.

			final WarehouseId dropShipWarehouseId = orgsRepo.getOrgDropshipWarehouseId(adOrgId);
			if (dropShipWarehouseId == null)
			{
				throw new AdempiereException("@NotFound@ @DropShip_Warehouse_ID@ (@AD_Org_ID@: " + order.getAD_Org().getName() + ")");
			}
			return dropShipWarehouseId;
		}

		// first check for picking warehouse
		// this check is valid only for sales order; for purchase order will return null
		final WarehouseId pickingWarehouseId = findPickingWarehouseId(order);
		if (pickingWarehouseId != null)
		{
			return pickingWarehouseId;
		}

		//
		final WarehouseId orgWarehouseId = orgsRepo.getOrgWarehouseId(adOrgId);
		if (orgWarehouseId != null)
		{
			return orgWarehouseId;
		}

		//
		return null;
	}

	/**
	 * Retrieve the picking warehouse based on the order's bPartner. Returns <code>null</code> if the partner is not customer, has no warehouse assigned or the order is not a sales order.
	 */
	private WarehouseId findPickingWarehouseId(@NonNull final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			return null;
		}

		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(order.getC_BPartner_ID());
		if (bpartnerId == null)
		{
			return null;
		}

		final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
		final I_C_BPartner bp = bpartnersRepo.getById(bpartnerId);
		if (!bp.isCustomer())
		{
			return null;
		}

		final WarehouseId customerWarehouseId = WarehouseId.ofRepoIdOrNull(bp.getM_Warehouse_ID());
		if (customerWarehouseId != null && isPickingWarehouse(customerWarehouseId))
		{
			return customerWarehouseId;
		}

		// if order is a purchase order, return null
		return null;
	}

	private boolean isPickingWarehouse(final WarehouseId warehouseId)
	{
		final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
		final org.adempiere.warehouse.model.I_M_Warehouse warehouse = warehousesRepo.getById(warehouseId, org.adempiere.warehouse.model.I_M_Warehouse.class);
		return warehouse.isPickingWarehouse();
	}
}
