/*
 * #%L
 * de.metas.servicerepair.base
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

package de.metas.servicerepair.sales_order.interceptor;

import de.metas.handlingunits.model.I_C_Order;
import de.metas.servicerepair.sales_order.RepairSalesOrderInfo;
import de.metas.servicerepair.sales_order.RepairSalesOrderService;
import de.metas.servicerepair.sales_order.RepairSalesProposalInfo;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Order.class)
@Component
public class C_Order
{
	private final RepairSalesOrderService salesOrderService;

	public C_Order(
			@NonNull final RepairSalesOrderService salesOrderService)
	{
		this.salesOrderService = salesOrderService;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void beforeDelete(final I_C_Order order)
	{
		salesOrderService.extractSalesProposalInfo(order).ifPresent(salesOrderService::unlinkProposalFromProject);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void afterComplete(final I_C_Order order)
	{
		salesOrderService.extractSalesOrderInfo(order).ifPresent(salesOrderService::transferVHUsFromProjectToSalesOrderLine);
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT })
	public void beforeVoid(
			@NonNull final I_C_Order order,
			@NonNull final DocTimingType timing)
	{
		final RepairSalesProposalInfo proposal = salesOrderService.extractSalesProposalInfo(order).orElse(null);
		if (proposal != null)
		{
			beforeVoid_Proposal(proposal, timing);
		}
		else
		{
			salesOrderService.extractSalesOrderInfo(order).ifPresent(this::beforeVoid_SalesOrder);
		}
	}

	private void beforeVoid_Proposal(
			@NonNull final RepairSalesProposalInfo proposal,
			@NonNull final DocTimingType timing)
	{
		if (timing.isVoid() || timing.isReverse())
		{
			salesOrderService.unlinkProposalFromProject(proposal);
		}
	}

	private void beforeVoid_SalesOrder(@NonNull final RepairSalesOrderInfo salesOrder)
	{
		salesOrderService.transferVHUsFromSalesOrderToProject(salesOrder);
	}
}
