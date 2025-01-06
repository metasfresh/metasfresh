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
import de.metas.servicerepair.sales_order.RepairSalesOrderService;
import de.metas.servicerepair.sales_order.RepairSalesProposalInfo;
import de.metas.servicerepair.sales_order.handleordervoid.RepairOrderUnProcessHandler;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

	/**
	 * Note that the HU-Reservations are changed not here, but in {@link RepairOrderUnProcessHandler}.
	 */
	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT })
	public void beforeVoid(
			@NonNull final I_C_Order order)
	{
		final Optional<RepairSalesProposalInfo> proposal = salesOrderService.extractSalesProposalInfo(order);
		proposal.ifPresent(salesOrderService::unlinkProposalFromProject);
	}
}
