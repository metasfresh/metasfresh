/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.payment_allocation.process;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.money.MoneyService;
import de.metas.payment.PaymentAmtMultiplier;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.payment_allocation.PaymentRow;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.SpringContextHolder;

import java.time.LocalDate;

public class PaymentsView_PaymentWriteOff extends PaymentsViewBasedProcess implements IProcessPrecondition
{
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);

	@Param(parameterName = "DateTrx", mandatory = true)
	private LocalDate p_DateTrx;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getInvoiceRowsSelectedForAllocation().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No invoices shall be selected");
		}

		return creatPlan()
				.resolve(plan -> ProcessPreconditionsResolution.accept().deriveWithCaptionOverride(computeCaption(plan.getAmountToWriteOff())),
						 ProcessPreconditionsResolution::rejectWithInternalReason);
	}

	private ExplainedOptional<Plan> creatPlan()
	{
		return createPlan(getPaymentRowsSelectedForAllocation());
	}

	private static ITranslatableString computeCaption(final Amount writeOffAmt)
	{
		return TranslatableStrings.builder()
				.appendADElement("PaymentWriteOffAmt").append(": ").append(writeOffAmt)
				.build();
	}

	@Override
	protected String doIt()
	{
		final Plan plan = creatPlan().orElseThrow();

		final PaymentAmtMultiplier amtMultiplier = plan.getAmtMultiplier();
		final Amount amountToWriteOff = amtMultiplier.convertToRealValue(plan.getAmountToWriteOff());

		paymentBL.paymentWriteOff(
				plan.getPaymentId(),
				amountToWriteOff.toMoney(moneyService::getCurrencyIdByCurrencyCode),
				p_DateTrx.atStartOfDay(SystemTime.zoneId()).toInstant(),
				null);

		return MSG_OK;
	}

	//
	//
	//

	private static ExplainedOptional<Plan> createPlan(@NonNull final ImmutableList<PaymentRow> paymentRows)
	{
		if (paymentRows.size() != 1)
		{
			return ExplainedOptional.emptyBecause("Only one payment row can be selected for write-off");
		}

		final PaymentRow paymentRow = CollectionUtils.singleElement(paymentRows);
		final Amount openAmt = paymentRow.getOpenAmt();
		if (openAmt.signum() == 0)
		{
			return ExplainedOptional.emptyBecause("Zero open amount. Nothing to write-off");
		}

		return ExplainedOptional.of(
				Plan.builder()
						.paymentId(paymentRow.getPaymentId())
						.amtMultiplier(paymentRow.getPaymentAmtMultiplier())
						.amountToWriteOff(openAmt)
						.build());
	}

	@Value
	@Builder
	private static class Plan
	{
		@NonNull PaymentId paymentId;
		@NonNull PaymentAmtMultiplier amtMultiplier;
		@NonNull Amount amountToWriteOff;
	}
}
