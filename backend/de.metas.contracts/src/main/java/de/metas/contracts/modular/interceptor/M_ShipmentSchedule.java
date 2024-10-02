/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.contracts.modular.interceptor;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.IFlatrateBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.IOrderBL;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_M_ShipmentSchedule.class)
public class M_ShipmentSchedule
{
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void updateIsShippingNotificationRequired(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(shipmentSchedule.getC_OrderLine_ID());
		if (orderLineId == null)
		{
			return;
		}
		final I_C_OrderLine orderLine = orderBL.getOrderLineById(orderLineId);
		final ConditionsId conditionsId = ConditionsId.ofRepoIdOrNull(orderLine.getC_Flatrate_Conditions_ID());
		if (conditionsId == null)
		{
			return;
		}

		shipmentSchedule.setIsShipmentNotificationRequired(flatrateBL.isModularContract(conditionsId));
	}
}
