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

import ch.qos.logback.classic.Level;
import de.metas.contracts.model.I_ModCntr_InvoicingGroup;
import de.metas.contracts.modular.interest.EnqueueInterestComputationRequest;
import de.metas.contracts.modular.interest.InterestComputationEnqueuer;
import de.metas.contracts.modular.invgroup.InvoicingGroup;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.IOrgDAO;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.user.UserId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

public class ModCntr_Compute_Interest extends JavaProcess implements IProcessPrecondition, IProcessParametersCallout
{
	public static final String PARAM_INTERIM_DATE = "InterimDate";
	public static final String PARAM_BILLING_DATE = "BillingDate";
	@NonNull private final ModCntrInvoicingGroupRepository invoicingGroupRepository = SpringContextHolder.instance.getBean(ModCntrInvoicingGroupRepository.class);
	@NonNull private final InterestComputationEnqueuer enqueuer = SpringContextHolder.instance.getBean(InterestComputationEnqueuer.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@Param(parameterName = PARAM_INTERIM_DATE, mandatory = true)
	@Nullable private LocalDate p_InterimDate;

	@Param(parameterName = PARAM_BILLING_DATE, mandatory = true)
	@Nullable private LocalDate p_BillingDate;

	@Param(parameterName = I_ModCntr_InvoicingGroup.COLUMNNAME_ModCntr_InvoicingGroup_ID)
	private InvoicingGroupId p_InvoicingGroupId;

	@Nullable @Param(parameterName = I_ModCntr_InvoicingGroup.COLUMNNAME_TotalInterest)
	private BigDecimal p_InterestToDistribute;

	@Param(parameterName = I_ModCntr_InvoicingGroup.COLUMNNAME_C_Currency_ID)
	private int p_InterestToDistributeCurrencyId = -1;

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
		final Instant billingDate = Objects.requireNonNull(TimeUtil.asInstant(p_BillingDate, orgDAO.getTimeZone(Env.getOrgId())));
		final Instant interimDate = Objects.requireNonNull(TimeUtil.asInstant(p_InterimDate, orgDAO.getTimeZone(Env.getOrgId())));
		final InvoicingGroupId invoicingGroupId = p_InvoicingGroupId;
		if (invoicingGroupId != null)
		{
			final Money interestToDistribute = Money.ofOrNull(p_InterestToDistribute, CurrencyId.ofRepoIdOrNull(p_InterestToDistributeCurrencyId));
			if (skipInvoicingGroup(interestToDistribute, invoicingGroupId))
			{
				return MSG_Error;
			}

			final UserId loggedUserId = getLoggedUserId();
			final EnqueueInterestComputationRequest request = EnqueueInterestComputationRequest.builder()
					.interestToDistribute(Objects.requireNonNull(interestToDistribute))
					.billingDate(billingDate)
					.interimDate(interimDate)
					.invoicingGroupId(invoicingGroupId)
					.userId(loggedUserId)
					.build();

			enqueuer.enqueueNow(request, loggedUserId);
		}
		else
		{
			invoicingGroupRepository.streamAll().forEach(invoicingGroup -> enqueue(invoicingGroup, billingDate, interimDate));
		}

		return MSG_OK;
	}

	private void enqueue(
			@NonNull final InvoicingGroup invoicingGroup,
			@NonNull final Instant billingDate,
			@NonNull final Instant interimDate)
	{
		if (skipInvoicingGroup(invoicingGroup.amtToDistribute(), invoicingGroup.id()))
		{
			return;
		}
		final UserId loggedUserId = Env.getLoggedUserId();
		final EnqueueInterestComputationRequest request = EnqueueInterestComputationRequest.builder()
				.interestToDistribute(Objects.requireNonNull(invoicingGroup.amtToDistribute()))
				.billingDate(billingDate)
				.interimDate(interimDate)
				.invoicingGroupId(invoicingGroup.id())
				.userId(loggedUserId)
				.build();
		enqueuer.enqueueNow(request, loggedUserId);
	}

	private boolean skipInvoicingGroup(final @Nullable Money interestToDistribute, final @NonNull InvoicingGroupId id)
	{
		final BigDecimal amtToDistribute = Money.toBigDecimalOrZero(interestToDistribute);
		if (amtToDistribute.signum() == 0)
		{
			Loggables.withLogger(log, Level.INFO)
					.addLog("Interest to distribute is 0 for invoicing group: {}. Skipping.", id);
			return true;
		}
		Loggables.withLogger(log, Level.INFO)
				.addLog("Enqueuing interest to distribute: {} for invoicing group: {}", amtToDistribute, id);
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
				final Money amtToDistribute = invoicingGroupRepository.getById(invoicingGroupId).amtToDistribute();
				p_InterestToDistribute = Money.toBigDecimalOrZero(amtToDistribute);
				p_InterestToDistributeCurrencyId = amtToDistribute == null ? -1 : amtToDistribute.getCurrencyId().getRepoId();
			}
		}

		else if (p_InterimDate != null && p_BillingDate != null && p_InterimDate.isAfter(p_BillingDate))
		{
			if (PARAM_INTERIM_DATE.equals(parameterName))
			{
				p_BillingDate = null;
			}
			else if (PARAM_BILLING_DATE.equals(parameterName))
			{
				p_InterimDate = null;
			}
		}
	}
}
