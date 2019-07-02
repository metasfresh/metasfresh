package de.metas.freighcost.interceptor;

import java.math.BigDecimal;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.document.engine.DocStatus;
import de.metas.freighcost.FreightCostRule;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderFreightCostsService;
import de.metas.util.Services;
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

@Interceptor(I_C_Order.class)
@Component("de.metas.freighcost.interceptor.C_Order")
public class C_Order
{
	private final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);
	private final OrderFreightCostsService orderFreightCostService;

	public C_Order(@NonNull final OrderFreightCostsService orderFreightCostService)
	{
		this.orderFreightCostService = orderFreightCostService;
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void beforeComplete(final I_C_Order order)
	{
		orderFreightCostService.evalAddFreightCostLine(order);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_REACTIVATE)
	public void afterReActivate(final I_C_Order order)
	{
		BigDecimal deletedFreightAmt = BigDecimal.ZERO;

		// making sure that (all) freight cost order lines are deleted
		for (final I_C_OrderLine orderLine : ordersRepo.retrieveOrderLines(order))
		{
			if (orderFreightCostService.isFreightCostOrderLine(orderLine))
			{
				deletedFreightAmt = deletedFreightAmt.add(orderLine.getPriceActual());
				ordersRepo.delete(orderLine);
			}
		}

		final FreightCostRule freightCostRule = FreightCostRule.ofCode(order.getFreightCostRule());
		if (freightCostRule.isFixPrice() && deletedFreightAmt.signum() != 0)
		{
			// reinsert the freight amount value in the field
			order.setFreightAmt(deletedFreightAmt);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE)
	public void beforeChange(final I_C_Order order)
	{
		// find out if we have a prepay order that enters the status "waiting payment"
		if (InterfaceWrapperHelper.isValueChanged(order, I_C_Order.COLUMNNAME_DocStatus)
				&& DocStatus.WaitingPayment.equals(DocStatus.ofCode(order.getDocStatus())))
		{
			orderFreightCostService.evalAddFreightCostLine(order);
		}
	}
}
