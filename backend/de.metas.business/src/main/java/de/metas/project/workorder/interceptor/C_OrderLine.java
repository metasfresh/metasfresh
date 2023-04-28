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

import de.metas.order.OrderLineId;
import de.metas.project.workorder.undertest.WOProjectObjectUnderTest;
import de.metas.project.workorder.undertest.WorkOrderProjectObjectUnderTestService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_OrderLine.class)
@Component
public class C_OrderLine
{
	private final WorkOrderProjectObjectUnderTestService workOrderProjectObjectUnderTestService;

	public C_OrderLine(@NonNull final WorkOrderProjectObjectUnderTestService workOrderProjectObjectUnderTestService)
	{
		this.workOrderProjectObjectUnderTestService = workOrderProjectObjectUnderTestService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_C_OrderLine.COLUMNNAME_QtyDelivered )
	public void updateWOProjectObjectUnderTestDeliveredDate(final I_C_OrderLine orderLine)
	{
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
