package de.metas.contracts.compensationGroup.contract.interceptor;

import de.metas.contracts.compensationGroup.contract.ContractCompensationGroupService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
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
 * (Re)creates a contract's compensation group on sales and purchase order completion (including the
 * auto-created, auto-completed drop-ship purchase order).
 * <p>
 * Runs at {@link ModelValidator#TIMING_BEFORE_PREPARE}, not before-complete: adding the lines this early lets
 * {@code MOrder.calculateTaxTotal()} (called later within the same {@code prepareIt()}) include them — the
 * only point where newly added lines get their taxes and totals computed.
 */
@Interceptor(I_C_Order.class)
@Component
public class C_Order_ContractCompensationGroup
{
	private final ContractCompensationGroupService contractCompensationGroupService;

	public C_Order_ContractCompensationGroup(@NonNull final ContractCompensationGroupService contractCompensationGroupService)
	{
		this.contractCompensationGroupService = contractCompensationGroupService;
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_PREPARE)
	public void beforePrepare(final I_C_Order order)
	{
		contractCompensationGroupService.recreateContractGroups(order);
	}
}
