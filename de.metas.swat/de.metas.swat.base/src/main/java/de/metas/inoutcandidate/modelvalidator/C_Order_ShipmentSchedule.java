package de.metas.inoutcandidate.modelvalidator;

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

import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;

import de.metas.inoutcandidate.async.CreateMissingShipmentSchedulesWorkpackageProcessor;

@Validator(I_C_Order.class)
public class C_Order_ShipmentSchedule
{
	/**
	 * Invalidate <code>M_ShipmentSchedule</code>s on order docstatus changes
	 * 
	 * @param order
	 */
	@DocValidate(timings = {
			ModelValidator.TIMING_AFTER_CLOSE,
			ModelValidator.TIMING_AFTER_COMPLETE,
			ModelValidator.TIMING_AFTER_PREPARE,
			ModelValidator.TIMING_AFTER_REACTIVATE,
			ModelValidator.TIMING_AFTER_REVERSEACCRUAL,
			ModelValidator.TIMING_AFTER_REVERSECORRECT,
			ModelValidator.TIMING_AFTER_VOID,
			ModelValidator.TIMING_AFTER_REACTIVATE })
	public void inValidateScheds(final I_C_Order order)
	{
		// default behavior (can do no harm)
		// note: createScheduleEntry and deleteScheduleEntry invocations above
		// did also invalidate the schedule lines for the given products
		// final IOrderPA orderService = Services.get(IOrderPA.class);

		// NOTE: on atm we don't need it
		// final Collection<I_C_OrderLine> orderLines = orderService.retrieveOrderLines(order, I_C_OrderLine.class);
		// Services.get(IShipmentScheduleBL.class).invalidateProducts(
		// orderLines, InterfaceWrapperHelper.getTrxName(order));
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void createMissingShipmentSchedules(final I_C_Order order)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(order);
		final String trxName = InterfaceWrapperHelper.getTrxName(order);
		
		CreateMissingShipmentSchedulesWorkpackageProcessor.schedule(ctx, trxName);
	}

}
