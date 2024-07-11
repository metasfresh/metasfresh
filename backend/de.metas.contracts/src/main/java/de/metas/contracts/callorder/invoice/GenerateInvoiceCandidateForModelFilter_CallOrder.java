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

package de.metas.contracts.callorder.invoice;

import de.metas.contracts.callorder.CallOrderContractService;
import de.metas.invoice.filter.IGenerateInvoiceCandidateForModelFilter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Service;

@Service
public class GenerateInvoiceCandidateForModelFilter_CallOrder implements IGenerateInvoiceCandidateForModelFilter
{
	@NonNull
	private final CallOrderContractService callOrderContractService;

	public GenerateInvoiceCandidateForModelFilter_CallOrder(@NonNull final CallOrderContractService callOrderContractService)
	{
		this.callOrderContractService = callOrderContractService;
	}

	@Override
	public boolean isEligible(final Object model)
	{
		if (!applies(model))
		{
			throw new AdempiereException("GenerateInvoiceEligibilityFilter_CallOrder called for an unsupported model type!")
					.appendParametersToMessage()
					.setParameter("model", model);
		}

		//dev-note: see de.metas.contracts.callorder.invoice.GenerateInvoiceCandidateForModelFilter_CallOrder.applies()
		final I_C_OrderLine orderLine = (I_C_OrderLine)model;

		final boolean isCallOrderContractLine = callOrderContractService.isCallOrderContractLine(orderLine);

		return !isCallOrderContractLine;
	}

	@Override
	public boolean applies(@NonNull final Object model)
	{
		return model instanceof I_C_OrderLine;
	}
}
