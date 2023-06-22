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
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_Order.class)
public class C_Order
{
	private final ModularContractService contractService;

	public C_Order(@NonNull final ModularContractService contractService)
	{
		this.contractService = contractService;
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	void afterComplete(@NonNull final I_C_Order orderRecord)
	{
		if (orderRecord.isSOTrx())
		{
			return; // no need to bother the service with sales orders unless we implemented modular sales order contracts
		}
		contractService.invokeWithModel(orderRecord, ModularContractService.ModelAction.COMPLETED);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSEACCRUAL, ModelValidator.TIMING_AFTER_REVERSECORRECT })
	void afterReverse(@NonNull final I_C_Order orderRecord)
	{
		if (orderRecord.isSOTrx())
		{
			return; // no need to bother the service with sales orders unless we implemented modular sales order contracts
		}
		contractService.invokeWithModel(orderRecord, ModularContractService.ModelAction.REVERSED);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REACTIVATE })
	void afterReactivate(@NonNull final I_C_Order orderRecord)
	{
		if (orderRecord.isSOTrx())
		{
			return; // no need to bother the service with sales orders unless we implemented modular sales order contracts
		}
		contractService.invokeWithModel(orderRecord, ModularContractService.ModelAction.REACTIVATED);
	}
}
