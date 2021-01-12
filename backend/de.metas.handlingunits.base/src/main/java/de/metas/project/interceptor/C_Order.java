/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.project.interceptor;

import de.metas.handlingunits.model.I_C_Order;
import de.metas.order.OrderId;
import de.metas.project.ProjectId;
import de.metas.project.service.HUProjectService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Order.class)
@Component
public class C_Order
{
	private final HUProjectService projectService;

	public C_Order(
			@NonNull final HUProjectService projectService)
	{
		this.projectService = projectService;
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void afterComplete(final I_C_Order order)
	{
		final ProjectId projectId = ProjectId.ofRepoIdOrNull(order.getC_Project_ID());
		if (projectId == null)
		{
			return;
		}

		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());
		projectService.transferHUReservationsFromProjectToOrder(orderId);
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT })
	public void beforeVoid(final I_C_Order order)
	{
		final ProjectId projectId = ProjectId.ofRepoIdOrNull(order.getC_Project_ID());
		if (projectId == null)
		{
			return;
		}

		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());
		projectService.transferHUReservationsFromOrderToProject(orderId);
	}
}
