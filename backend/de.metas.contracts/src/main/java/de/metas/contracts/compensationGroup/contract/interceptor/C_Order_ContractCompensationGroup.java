package de.metas.contracts.compensationGroup.contract.interceptor;

import de.metas.contracts.compensationGroup.contract.ContractCompensationGroupService;
import de.metas.order.OrderId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * (Re)creates a contract's compensation group on sales order completion, and (same code path, not covered
 * by an automated test) on purchase order completion — including the auto-created, auto-completed
 * drop-ship purchase order. Removes the group again on reactivation, refusing the reactivation while a
 * discount line is already invoiced.
 * <p>
 * Runs at {@link ModelValidator#TIMING_BEFORE_PREPARE}, not before-complete: adding the lines this early lets
 * {@code MOrder.calculateTaxTotal()} (called later within the same {@code prepareIt()}) include them — the
 * only point where newly added lines get their taxes and totals computed. See
 * {@link ContractCompensationGroupService}'s class Javadoc for the resulting interceptor-order guarantee.
 */
@Interceptor(I_C_Order.class)
@Component
@RequiredArgsConstructor
public class C_Order_ContractCompensationGroup
{
	@NonNull private final ContractCompensationGroupService contractCompensationGroupService;

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_PREPARE)
	public void beforePrepare(final I_C_Order order)
	{
		contractCompensationGroupService.recreateContractGroups(order);
	}

	/**
	 * Refuses the reactivation while any compensation line of a contract-created group is (partially)
	 * invoiced. Note: {@code MOrder.reActivateIt()} ignores this timing's return value (only later timings'
	 * return values abort the action) — the refusal only takes effect because this method throws.
	 */
	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REACTIVATE)
	public void beforeReactivate(final I_C_Order order)
	{
		contractCompensationGroupService.assertNoInvoicedContractGroupLines(order);
	}

	/**
	 * Removes the order's contract-created compensation group(s); the next completion rebuilds them from the
	 * order's then-current lines.
	 * <p>
	 * {@code MOrder.reActivateIt()} sets {@code Processed=false} on this same {@code order} instance but does
	 * not save it before firing this timing — an independent load (as {@code OrderGroupRepository.saveGroup}'s
	 * processed assertion does, triggered by ungrouping the regular lines below) would still see the previous,
	 * still-processed DB row. Saving first makes that assertion see the order as not (yet re-)processed.
	 */
	@DocValidate(timings = ModelValidator.TIMING_AFTER_REACTIVATE)
	public void afterReactivate(final I_C_Order order)
	{
		InterfaceWrapperHelper.saveRecord(order);
		contractCompensationGroupService.removeContractGroups(OrderId.ofRepoId(order.getC_Order_ID()));
	}
}
