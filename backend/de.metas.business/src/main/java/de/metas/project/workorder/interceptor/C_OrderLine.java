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

import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.project.workorder.undertest.WOProjectObjectUnderTest;
import de.metas.project.workorder.undertest.WorkOrderProjectObjectUnderTestService;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

@Interceptor(I_C_OrderLine.class)
@Component
public class C_OrderLine
{
	private final WorkOrderProjectObjectUnderTestService workOrderProjectObjectUnderTestService;
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	public C_OrderLine(@NonNull final WorkOrderProjectObjectUnderTestService workOrderProjectObjectUnderTestService)
	{
		this.workOrderProjectObjectUnderTestService = workOrderProjectObjectUnderTestService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = I_C_OrderLine.COLUMNNAME_QtyDelivered )
	public void updateWOProjectObjectUnderTestDeliveredDate(final I_C_OrderLine orderLine)
	{
		final Quantity qtyToDeliver = orderLineBL.getQtyToDeliver(OrderAndLineId.of(OrderId.ofRepoId(orderLine.getC_Order_ID()), OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID())));

		if (qtyToDeliver.isZero() || orderLine.getDateDelivered() != null)
		{
			workOrderProjectObjectUnderTestService.getByOrderLineId(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()))
					.stream()
					.map(oldObjectUnderTest -> WOProjectObjectUnderTest.builder()
							.productId(oldObjectUnderTest.getProductId())
							.projectId(oldObjectUnderTest.getProjectId())
							.orderLineProvisionId(oldObjectUnderTest.getOrderLineProvisionId())
							.numberOfObjectsUnderTest(oldObjectUnderTest.getNumberOfObjectsUnderTest())
							.objectUnderTestId(oldObjectUnderTest.getObjectUnderTestId())
							.externalId(oldObjectUnderTest.getExternalId())
							.orgId(oldObjectUnderTest.getOrgId())
							.woObjectType(oldObjectUnderTest.getWoObjectType())
							.woManufacturer(oldObjectUnderTest.getWoManufacturer())
							.woObjectName(oldObjectUnderTest.getWoObjectName())
							.woDeliveryNote(oldObjectUnderTest.getWoDeliveryNote())
							.woObjectWhereabouts(oldObjectUnderTest.getWoObjectWhereabouts())
							.objectDeliveredDate(TimeUtil.asInstant(orderLine.getDateDelivered()))
							.build())
					.forEach(workOrderProjectObjectUnderTestService::update);
		}
	}
}
