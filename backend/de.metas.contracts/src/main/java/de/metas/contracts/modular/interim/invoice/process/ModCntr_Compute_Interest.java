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
import de.metas.contracts.modular.invgroup.InvoicingGroup;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
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

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class ModCntr_Compute_Interest extends JavaProcess implements IProcessPrecondition, IProcessParametersCallout
{
	@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository = SpringContextHolder.instance.getBean(ModCntrInvoicingGroupRepository.class);

	@Param(parameterName = "InterimDate", mandatory = true)
	private LocalDate p_InterimDate;

	@Param(parameterName = "BillingDate", mandatory = true)
	LocalDate p_BillingDate;

	@Param(parameterName = I_ModCntr_InvoicingGroup.COLUMNNAME_ModCntr_InvoicingGroup_ID)
	InvoicingGroupId p_InvoicingGroupId;

	@Nullable @Param(parameterName = I_ModCntr_InvoicingGroup.COLUMNNAME_TotalInterest)
	BigDecimal p_InterestToDistribute;

	@Param(parameterName = I_ModCntr_InvoicingGroup.COLUMNNAME_C_Currency_ID)
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
		final InvoicingGroupId invoicingGroupId = p_InvoicingGroupId;
		if (invoicingGroupId != null)
		{
			final Money interestToDistribute = Money.ofOrNull(p_InterestToDistribute, CurrencyId.ofRepoIdOrNull(p_InterestToDistributeCurrencyId));
			if (getAndLogInterestToDistribute(interestToDistribute, invoicingGroupId))
			{
				return MSG_Error;
			}
			final EnqueueInterestComputationRequest request = EnqueueInterestComputationRequest.builder()
					.interestToDistribute(Objects.requireNonNull(interestToDistribute))
					.billingDate(p_BillingDate)
					.interimDate(p_InterimDate)
					.invoicingGroupId(invoicingGroupId)
					.build();

			SpringContextHolder.instance.getBean(InterestComputationEnqueuer.class)
					.enqueueNow(request, Env.getLoggedUserId());
		}

		else
		{
			modCntrInvoicingGroupRepository.streamAll()
					.forEach(this::enqueue);
		}

		return MSG_OK;
	}

	private void enqueue(@NonNull final InvoicingGroup invoicingGroup)
	{
		if (getAndLogInterestToDistribute(invoicingGroup.amtToDistribute(), invoicingGroup.id()))
		{
			return;
		}
		final EnqueueInterestComputationRequest request = EnqueueInterestComputationRequest.builder()
				.interestToDistribute(Objects.requireNonNull(invoicingGroup.amtToDistribute()))
				.billingDate(p_BillingDate)
				.interimDate(p_InterimDate)
				.invoicingGroupId(invoicingGroup.id())
				.build();

		SpringContextHolder.instance.getBean(InterestComputationEnqueuer.class)
				.enqueueNow(request, Env.getLoggedUserId());
	}

	private boolean getAndLogInterestToDistribute(final @Nullable Money interestToDistribute, final @NonNull InvoicingGroupId id)
	{
		final BigDecimal amtToDistribute = Money.toBigDecimalOrZero(interestToDistribute);
		if (amtToDistribute.compareTo(BigDecimal.ZERO) == 0)
		{
			log.info("Interest to distribute is 0 for invoicing group: {}. Skipping.", id);
			return true;
		}
		log.info("Enqueuing interest to distribute: {} for invoicing group: {}", amtToDistribute, id);
		return false;
	}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		if (I_ModCntr_InvoicingGroup.COLUMNNAME_ModCntr_InvoicingGroup_ID.equals(parameterName))
		{
			final InvoicingGroupId invoicingGroupId = p_InvoicingGroupId;
			if (invoicingGroupId == null)
			{
				p_InterestToDistribute = null;
				p_InterestToDistributeCurrencyId = -1;
			}
			else
			{
				final Money amtToDistribute = modCntrInvoicingGroupRepository.getById(invoicingGroupId)
						.amtToDistribute();
				p_InterestToDistribute = Money.toBigDecimalOrZero(amtToDistribute);
				p_InterestToDistributeCurrencyId = amtToDistribute == null ? -1 : amtToDistribute.getCurrencyId().getRepoId();
			}
		}
	}
}
