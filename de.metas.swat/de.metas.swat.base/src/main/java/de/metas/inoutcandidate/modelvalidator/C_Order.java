package de.metas.inoutcandidate.modelvalidator;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;

import de.metas.inoutcandidate.api.IShipmentConstraintsBL;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class C_Order
{
	private static final String MSG_CannotCompleteOrder_DeliveryStop = "CannotCompleteOrder_DeliveryStop";

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_PREPARE)
	public void assertNotDeliveryStopped(final I_C_Order order)
	{
		// Makes sense only for sales orders
		if(!order.isSOTrx())
		{
			return;
		}
		
		final int billPartnerId = order.getBill_BPartner_ID();
		final int deliveryStopShipmentConstraintId = Services.get(IShipmentConstraintsBL.class).getDeliveryStopShipmentConstraintId(billPartnerId);
		final boolean isDeliveryStop = deliveryStopShipmentConstraintId > 0;
		if (isDeliveryStop)
		{
			throw new AdempiereException(MSG_CannotCompleteOrder_DeliveryStop, new Object[] {});
		}
	}
}
