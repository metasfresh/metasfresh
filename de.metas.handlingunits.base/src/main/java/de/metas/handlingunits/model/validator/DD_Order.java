package de.metas.handlingunits.model.validator;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_DD_Order;

import de.metas.handlingunits.ddorder.api.IHUDDOrderDAO;
import de.metas.request.service.IRequestCreator;

/*
 * #%L
 * de.metas.handlingunits.base
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

@Interceptor(I_DD_Order.class)
public class DD_Order
{

	@DocValidate(timings={ModelValidator.TIMING_BEFORE_REVERSEACCRUAL
			, ModelValidator.TIMING_BEFORE_REVERSECORRECT
			, ModelValidator.TIMING_BEFORE_VOID
			, ModelValidator.TIMING_BEFORE_CLOSE})
	public void clearHUsScheduledToMoveList(final I_DD_Order ddOrder)
	{
		Services.get(IHUDDOrderDAO.class).clearHUsScheduledToMoveList(ddOrder);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void onComplete_BlockWarehouseLines(final I_DD_Order ddOrder)
	{
		Services.get(IRequestCreator.class).createRequestsForInOutLines(ctx, linesWithQualityIssues, trxName);
		
	}
	
	
}
