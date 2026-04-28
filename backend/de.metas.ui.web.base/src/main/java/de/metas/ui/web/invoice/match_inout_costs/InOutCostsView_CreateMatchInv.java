/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.ui.web.invoice.match_inout_costs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.currency.Amount;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.TranslatableStrings;
import de.metas.money.MoneyService;
import de.metas.order.costs.inout.InOutCostId;
import de.metas.order.costs.invoice.CreateMatchInvoicePlan;
import de.metas.order.costs.invoice.CreateMatchInvoiceRequest;
import de.metas.process.ProcessPreconditionsResolution;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;

public class InOutCostsView_CreateMatchInv extends InOutCostsViewBasedProcess
{
	private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final ExplainedOptional<CreateMatchInvoiceRequest> optionalRequest = createMatchInvoiceRequest();
		if (!optionalRequest.isPresent())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason(optionalRequest.getExplanation());
		}

		final CreateMatchInvoicePlan plan = orderCostService.createMatchInvoiceSimulation(optionalRequest.get());
		final Amount invoicedAmtDiff = plan.getInvoicedAmountDiff().toAmount(moneyService::getCurrencyCodeByCurrencyId);
		return ProcessPreconditionsResolution.accept().withCaptionMapper(captionMapper(invoicedAmtDiff));
	}

	@Nullable
	private ProcessPreconditionsResolution.ProcessCaptionMapper captionMapper(@Nullable final Amount invoicedAmtDiff)
	{
		if (invoicedAmtDiff == null || invoicedAmtDiff.isZero())
		{
			return null;
		}
		else
		{
			return originalProcessCaption -> TranslatableStrings.builder()
					.append(originalProcessCaption)
					.append(" (Diff: ").append(invoicedAmtDiff).append(")")
					.build();
		}
	}

	protected String doIt()
	{
		final CreateMatchInvoiceRequest request = createMatchInvoiceRequest().orElseThrow();
		orderCostService.createMatchInvoice(request);

		invalidateView();

		return MSG_OK;
	}

	private ExplainedOptional<CreateMatchInvoiceRequest> createMatchInvoiceRequest()
	{
		final ImmutableSet<InOutCostId> selectedInOutCostIds = getSelectedInOutCostIds();
		if (selectedInOutCostIds.isEmpty())
		{
			return ExplainedOptional.emptyBecause("No selection");
		}

		return ExplainedOptional.of(
				CreateMatchInvoiceRequest.builder()
						.invoiceAndLineId(getView().getInvoiceLineId())
						.inoutCostIds(selectedInOutCostIds)
						.build());
	}

	private ImmutableSet<InOutCostId> getSelectedInOutCostIds()
	{
		final ImmutableList<InOutCostRow> rows = getSelectedRows();
		return rows.stream().map(InOutCostRow::getInoutCostId).collect(ImmutableSet.toImmutableSet());
	}
}
