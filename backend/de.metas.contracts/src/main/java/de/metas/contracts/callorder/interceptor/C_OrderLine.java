/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contracts.callorder.interceptor;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.callorder.CallOrderContractService;
import de.metas.order.IOrderLineBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_OrderLine.class)
public class C_OrderLine
{
	final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	@NonNull
	final CallOrderContractService contractService;

	public C_OrderLine(final @NonNull CallOrderContractService contractService)
	{
		this.contractService = contractService;
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_C_Flatrate_Conditions_ID })
	public void updateCallOrderContractLine(final I_C_OrderLine orderLine)
	{
		final ConditionsId conditionsId = ConditionsId.ofRepoIdOrNull(orderLine.getC_Flatrate_Conditions_ID());

		if (conditionsId != null)
		{
			final boolean isCallOrderContractLine = contractService.isCallOrderContractLine(orderLine);

			if (isCallOrderContractLine)
			{
				orderLineBL.updatePrices(orderLine);
			}
		}
		else
		{
			orderLineBL.updatePrices(orderLine);
		}
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = { I_C_OrderLine.COLUMNNAME_C_Flatrate_Term_ID })
	public void updateOrderLineFromContract(final I_C_OrderLine orderLine)
	{
		final FlatrateTermId contractId = FlatrateTermId.ofRepoIdOrNull(orderLine.getC_Flatrate_Term_ID());

		if (contractId != null)
		{
			final boolean isCallOrderLine = contractService.isCallOrderContractLine(orderLine);

			if (isCallOrderLine)
			{
				orderLineBL.updatePrices(orderLine);
			}
		}
		else
		{
			orderLineBL.updatePrices(orderLine);
		}
	}
}
