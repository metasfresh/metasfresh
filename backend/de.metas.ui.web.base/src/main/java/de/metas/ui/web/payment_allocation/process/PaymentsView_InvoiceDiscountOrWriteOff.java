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
import de.metas.allocation.api.IAllocationBL;
import de.metas.currency.Amount;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoice.InvoiceAmtMultiplier;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.payment_allocation.InvoiceRow;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;

import java.time.LocalDate;

public abstract class PaymentsView_InvoiceDiscountOrWriteOff extends PaymentsViewBasedProcess implements IProcessPrecondition
{
	// services
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IAllocationBL allocationBL = Services.get(IAllocationBL.class);
	private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);

	private final PlanAction action;

	@Param(parameterName = "DateTrx", mandatory = true)
	private LocalDate p_DateTrx;

	protected PaymentsView_InvoiceDiscountOrWriteOff(@NonNull final PlanAction action)
	{
		this.action = action;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getPaymentRowsSelectedForAllocation().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No payments shall be selected");
		}

		return creatPlan()
				.resolve(plan -> ProcessPreconditionsResolution.accept().deriveWithCaptionOverride(computeCaption(plan)),
						 ProcessPreconditionsResolution::rejectWithInternalReason);
	}

	private ExplainedOptional<Plan> creatPlan()
	{
		return createPlan(getInvoiceRowsSelectedForAllocation(), action);
	}

	private static ITranslatableString computeCaption(@NonNull final Plan plan)
	{
		return TranslatableStrings.builder()
				.appendADElement(plan.getAction().getColumnName()).append(": ").append(plan.getAmountToDiscountOrWriteOff())
				.build();
	}

	@Override
	protected String doIt()
	{
		final Plan plan = creatPlan().orElseThrow();

		final I_C_Invoice invoice = invoiceBL.getById(plan.getInvoiceId());

		final InvoiceAmtMultiplier amtMultiplier = plan.getAmtMultiplier();
		final Money amountToDiscountOrWriteOff = amtMultiplier.convertToRealValue(plan.getAmountToDiscountOrWriteOff())
				.toMoney(moneyService::getCurrencyIdByCurrencyCode);

		allocationBL.invoiceDiscountAndWriteOff(
				IAllocationBL.InvoiceDiscountAndWriteOffRequest.builder()
						.invoice(invoice)
						.dateTrx(TimeUtil.asInstant(p_DateTrx))
						.discountAmt(plan.getAction() == PlanAction.Discount ? amountToDiscountOrWriteOff : null)
						.writeOffAmt(plan.getAction() == PlanAction.WriteOff ? amountToDiscountOrWriteOff : null)
						.build());

		return MSG_OK;
	}

	//
	//
	//
	private static ExplainedOptional<Plan> createPlan(@NonNull final ImmutableList<InvoiceRow> invoiceRows, final PlanAction action)
	{
		if (invoiceRows.size() != 1)
		{
			return ExplainedOptional.emptyBecause("Only one invoice row can be selected");
		}

		final InvoiceRow invoiceRow = CollectionUtils.singleElement(invoiceRows);
		if (invoiceRow.getDocBaseType().isCreditMemo())
		{
			return ExplainedOptional.emptyBecause("Credit Memos are not eligible");
		}

		final Amount openAmt = invoiceRow.getOpenAmt();
		if (openAmt.signum() == 0)
		{
			return ExplainedOptional.emptyBecause("Zero open amount");
		}

		return ExplainedOptional.of(
				Plan.builder()
						.invoiceId(invoiceRow.getInvoiceId())
						.action(action)
						.amtMultiplier(invoiceRow.getInvoiceAmtMultiplier())
						.amountToDiscountOrWriteOff(openAmt)
						.build());
	}

	@AllArgsConstructor
	enum PlanAction
	{
		Discount("DiscountAmt"),
		WriteOff("WriteOffAmt"),
		;

		// used mainly to build the action caption
		@Getter
		private final String columnName;
	}

	@Value
	@Builder
	private static class Plan
	{
		@NonNull InvoiceId invoiceId;
		@NonNull PlanAction action;
		@NonNull InvoiceAmtMultiplier amtMultiplier;
		@NonNull Amount amountToDiscountOrWriteOff;
	}
}
