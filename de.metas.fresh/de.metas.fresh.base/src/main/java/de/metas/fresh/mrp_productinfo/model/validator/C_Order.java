package de.metas.fresh.mrp_productinfo.model.validator;

/*
 * #%L
 * de.metas.edi
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

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;

import de.metas.fresh.mrp_productinfo.async.spi.impl.UpdateMRPProductInfoTableWorkPackageProcessor;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;

@Interceptor(I_C_Order.class)
public class C_Order
{
	public static final C_Order INSTANCE = new C_Order();

	private C_Order()
	{
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE,
			ModelValidator.TIMING_BEFORE_CLOSE,
			ModelValidator.TIMING_BEFORE_REACTIVATE,
			// yes, the following won't actually occur, but still, be on the safe side
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT })
	public void enqueueOrderLines(final I_C_Order order)
	{
		// note that we need to evaluate both sales and purchase orders
		final List<I_C_OrderLine> orderLines = Services.get(IOrderDAO.class).retrieveOrderLines(order);
		for(final I_C_OrderLine ol:orderLines)
		{
			UpdateMRPProductInfoTableWorkPackageProcessor.schedule(ol);
		}
	}
}
