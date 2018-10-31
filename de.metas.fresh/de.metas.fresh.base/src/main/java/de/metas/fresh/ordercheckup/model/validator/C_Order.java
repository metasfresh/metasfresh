package de.metas.fresh.ordercheckup.model.validator;

/*
 * #%L
 * de.metas.fresh.base
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


import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;

import de.metas.fresh.ordercheckup.IOrderCheckupBL;
import de.metas.fresh.ordercheckup.printing.spi.impl.OrderCheckupPrintingQueueHandler;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.util.Services;

@Interceptor(I_C_Order.class)
public class C_Order
{
	public static final transient C_Order instance = new C_Order();

	private C_Order()
	{
		super();
	}

	@Init
	public void init()
	{
		Services.get(IPrintingQueueBL.class).registerHandler(OrderCheckupPrintingQueueHandler.instance); // task 09028
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void generateReports(final I_C_Order order)
	{
		final IOrderCheckupBL orderCheckupBL = Services.get(IOrderCheckupBL.class);

		// Allow automatically reports generation only if this was configured
		if (!orderCheckupBL.isGenerateReportsOnOrderComplete(order))
		{
			return;
		}

		orderCheckupBL.generateReportsIfEligible(order);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_VOID, ModelValidator.TIMING_AFTER_REACTIVATE, ModelValidator.TIMING_AFTER_REVERSECORRECT, ModelValidator.TIMING_AFTER_REVERSEACCRUAL })
	public void voidReports(final I_C_Order order)
	{
		final IOrderCheckupBL orderCheckupBL = Services.get(IOrderCheckupBL.class);
		orderCheckupBL.voidReports(order);
	}
}
