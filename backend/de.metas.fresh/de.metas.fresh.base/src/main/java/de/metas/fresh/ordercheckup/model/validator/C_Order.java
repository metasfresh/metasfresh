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
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Order.class)
@Component
public class C_Order
{
	@NonNull private final IOrderCheckupBL orderCheckupBL = Services.get(IOrderCheckupBL.class);

	public C_Order()
	{
		Services.get(IPrintingQueueBL.class).registerHandler(OrderCheckupPrintingQueueHandler.instance); // task 09028
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void generateReports(final I_C_Order order)
	{
		// Allow automatically reports generation only if this was configured
		if (!orderCheckupBL.isGenerateReportsOnOrderComplete(order))
		{
			return;
		}

		if (order.isReprintOrderCheckup())
		{
			regenerateReports(order);
		}
		else
		{
			restoreOrRegenerateReports(order);
		}
	}

	/**
	 * Generates a fresh report run for the order — the behaviour applied when {@code IsReprintOrderCheckup} is
	 * set, and the fallback of {@link #restoreOrRegenerateReports}.
	 */
	private void regenerateReports(final I_C_Order order)
	{
		orderCheckupBL.generateReportsIfEligible(order);
	}

	/**
	 * {@code order.IsReprintOrderCheckup} is unset: prefer reactivating the headers of the order's most recent
	 * report generation over printing a new one, so that completing a reactivated order does not trigger a
	 * reprint. Falls back to {@link #regenerateReports} when there is nothing to restore -- the order's first
	 * completion, or a generation that predates the {@code IsReprintOrderCheckup} column.
	 */
	private void restoreOrRegenerateReports(final I_C_Order order)
	{
		if (!orderCheckupBL.restoreMostRecentGeneration(order))
		{
			regenerateReports(order);
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_VOID, ModelValidator.TIMING_AFTER_REACTIVATE, ModelValidator.TIMING_AFTER_REVERSECORRECT, ModelValidator.TIMING_AFTER_REVERSEACCRUAL })
	public void voidReports(final I_C_Order order)
	{
		orderCheckupBL.voidReports(order);
	}
}
