/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.interceptor;

import de.metas.contracts.modular.ModularContractService;
import de.metas.order.IOrderDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import static de.metas.contracts.modular.ModularContractService.ModelAction.COMPLETED;
import static de.metas.contracts.modular.ModularContractService.ModelAction.REACTIVATED;
import static de.metas.contracts.modular.ModularContractService.ModelAction.REVERSED;
import static de.metas.contracts.modular.ModularContractService.ModelAction.VOIDED;

/**
 * Glue-code that invokes the {@link ModularContractService} on certain order events.
 * <p/>
 * Note: even now where we don't yet implemented modular sales contracts, we need to act on sales orders, too.<br/>
 * That's because the eventual sales (and their totalamounts) can play a role in the purchase contracts final settlement.
 */
@Component
@Interceptor(I_C_Order.class)
public class C_Order
{
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	private final ModularContractService contractService;

	public C_Order(@NonNull final ModularContractService contractService)
	{
		this.contractService = contractService;
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void afterComplete(@NonNull final I_C_Order orderRecord)
	{
		invokeHandlerForEachLine(orderRecord, COMPLETED);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_VOID)
	public void afterVoid(@NonNull final I_C_Order orderRecord)
	{
		invokeHandlerForEachLine(orderRecord, VOIDED);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSEACCRUAL, ModelValidator.TIMING_AFTER_REVERSECORRECT })
	public void afterReverse(@NonNull final I_C_Order orderRecord)
	{
		invokeHandlerForEachLine(orderRecord, REVERSED);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REACTIVATE })
	public void afterReactivate(@NonNull final I_C_Order orderRecord)
	{
		invokeHandlerForEachLine(orderRecord, REACTIVATED);
	}

	private void invokeHandlerForEachLine(
			@NonNull final I_C_Order orderRecord,
			@NonNull final ModularContractService.ModelAction modelAction)
	{
		orderDAO.retrieveOrderLines(orderRecord)
				.forEach(line -> contractService.invokeWithModel(line, modelAction));
	}
}
