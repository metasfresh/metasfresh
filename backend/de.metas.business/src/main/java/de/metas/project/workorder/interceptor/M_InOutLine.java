/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.project.workorder.interceptor;

import de.metas.inout.model.I_M_InOutLine;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLineId;
import de.metas.project.workorder.undertest.WOProjectObjectUnderTest;
import de.metas.project.workorder.undertest.WorkOrderProjectObjectUnderTestService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_InOutLine.class)
@Validator(I_M_InOutLine.class)
@Component
public class M_InOutLine
{
	private final WorkOrderProjectObjectUnderTestService workOrderProjectObjectUnderTestService;

	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	public M_InOutLine(@NonNull final WorkOrderProjectObjectUnderTestService workOrderProjectObjectUnderTestService)
	{
		this.workOrderProjectObjectUnderTestService = workOrderProjectObjectUnderTestService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW })
	public void updateWOProjectObjectUnderTestDeliveredDate(final I_M_InOutLine inOutLine)
	{
		final I_C_OrderLine orderLine = orderLineBL.getOrderLineById(OrderLineId.ofRepoId(inOutLine.getC_OrderLine_ID()));
		final boolean fullyDelivered = orderLine.getQtyOrdered().compareTo(orderLine.getQtyDelivered()) == 0;

		if (fullyDelivered)
		{
			final WOProjectObjectUnderTest woProjectObjectUnderTest = workOrderProjectObjectUnderTestService.getByOrderLineId(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()));

			if (woProjectObjectUnderTest != null)
			{
				workOrderProjectObjectUnderTestService.updateObjectDeliveredDate(woProjectObjectUnderTest, orderLine);
			}
		}
	}
}
