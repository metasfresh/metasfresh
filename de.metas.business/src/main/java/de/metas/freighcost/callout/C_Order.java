package de.metas.freighcost.callout;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.model.I_M_FreightCostDetail;

import de.metas.adempiere.model.I_C_Order;
import de.metas.freighcost.FreightCostRule;
import de.metas.order.OrderFreightCostsService;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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

@Callout(I_C_Order.class)
public class C_Order
{
	private final OrderFreightCostsService orderFreightCostService;

	public C_Order(@NonNull final OrderFreightCostsService orderFreightCostService)
	{
		this.orderFreightCostService = orderFreightCostService;
	}

	/**
	 * Callout checks if there are {@link I_M_FreightCostDetail} records for the given BPartner, Location and Shipper.
	 */
	@CalloutMethod(columnNames = I_C_Order.COLUMNNAME_FreightCostRule)
	public void onFreightCostRuleChanged(final I_C_Order order)
	{
		updateFreightAmtIfNeeded(order);
	}

	@CalloutMethod(columnNames = I_C_Order.COLUMNNAME_DeliveryViaRule)
	public void onDeliveryViaRuleChanged(final I_C_Order order)
	{
		updateFreightAmtIfNeeded(order);
	}

	@CalloutMethod(columnNames = I_C_Order.COLUMNNAME_M_Shipper_ID)
	public void onShipperChanged(final I_C_Order order)
	{
		updateFreightAmtIfNeeded(order);
	}

	private void updateFreightAmtIfNeeded(final I_C_Order order)
	{
		// Apply on Sales Orders only
		if (!order.isSOTrx())
		{
			return;
		}

		// Don't overwrite a fix price
		final FreightCostRule freightCostRule = FreightCostRule.ofNullableCode(order.getFreightCostRule());
		if (freightCostRule.isFixPrice())
		{
			return;
		}
		orderFreightCostService.updateFreightAmt(order);
	}
}
