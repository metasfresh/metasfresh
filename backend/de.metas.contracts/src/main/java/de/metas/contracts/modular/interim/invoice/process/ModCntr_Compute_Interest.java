/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.interim.invoice.process;

import de.metas.contracts.model.I_ModCntr_InvoicingGroup;
import de.metas.contracts.modular.interest.EnqueueInterestComputationRequest;
import de.metas.contracts.modular.interest.InterestComputationEnqueuer;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ModCntr_Compute_Interest extends JavaProcess implements IProcessPrecondition, IProcessParametersCallout
{
	@Param(parameterName = "InterimDate")
	private LocalDate p_InterimDate;

	@Param(parameterName = "BillingDate")
	LocalDate p_BillingDate;

	@Param(parameterName = I_ModCntr_InvoicingGroup.COLUMNNAME_ModCntr_InvoicingGroup_ID)
	int p_InvoicingGroupId;

	@Param(parameterName = "InterestToDistribute")
	BigDecimal p_InterestToDistribute;

	@Param(parameterName = "InterestToDistribute_Currency_ID")
	int p_InterestToDistributeCurrencyId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final EnqueueInterestComputationRequest request = EnqueueInterestComputationRequest.builder()
				.interestToDistribute(Money.of(p_InterestToDistribute, CurrencyId.ofRepoId(p_InterestToDistributeCurrencyId)))
				.billingDate(p_BillingDate)
				.interimDate(p_InterimDate)
				.invoicingGroupId(InvoicingGroupId.ofRepoId(p_InvoicingGroupId))
				.build();

		SpringContextHolder.instance.getBean(InterestComputationEnqueuer.class)
				.enqueueNow(request, Env.getLoggedUserId());

		return MSG_OK;
	}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		// todo set `p_InterestToDistributeCurrencyId` + `p_InterestToDistribute` based on inv group
	}
}
