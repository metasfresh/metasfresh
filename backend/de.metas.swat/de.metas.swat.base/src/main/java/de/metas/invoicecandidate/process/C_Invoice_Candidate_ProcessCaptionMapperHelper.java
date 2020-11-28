/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.invoicecandidate.process;

import com.google.common.collect.ImmutableList;
import de.metas.currency.Amount;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import de.metas.process.ProcessPreconditionsResolution.ProcessCaptionMapper;
import de.metas.util.Check;
import lombok.NonNull;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

@Service
public class C_Invoice_Candidate_ProcessCaptionMapperHelper
{
	private final MoneyService moneyService;

	public C_Invoice_Candidate_ProcessCaptionMapperHelper(final MoneyService moneyService)
	{
		this.moneyService = moneyService;
	}

	@Nullable
	public ProcessCaptionMapper getProcessCaptionMapperForNetAmountsFromQuery(final IQuery<I_C_Invoice_Candidate> query)
	{
		final List<Amount> netAmountsToInvoiceList = computeNetAmountsToInvoiceForQuery(query);
		if (netAmountsToInvoiceList.isEmpty())
		{
			return null;
		}

		final ITranslatableString netAmountsToInvoiceString = joinAmountsToTranslatableString(netAmountsToInvoiceList);

		return originalProcessCaption -> TranslatableStrings.builder()
				.append(originalProcessCaption)
				.append(" (").append(netAmountsToInvoiceString).append(")")
				.build();
	}

	private ImmutableList<Amount> computeNetAmountsToInvoiceForQuery(@NonNull final IQuery<I_C_Invoice_Candidate> query)
	{
		return query
				.sumMoney(I_C_Invoice_Candidate.COLUMNNAME_NetAmtToInvoice, I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID)
				.stream()
				.filter(amt -> amt != null && amt.signum() != 0)
				.map(moneyService::toAmount)
				.collect(ImmutableList.toImmutableList());
	}

	private static ITranslatableString joinAmountsToTranslatableString(final List<Amount> amounts)
	{
		Check.assumeNotEmpty(amounts, "amounts is not empty");

		final TranslatableStringBuilder builder = TranslatableStrings.builder();
		for (final Amount amt : amounts)
		{
			if (!builder.isEmpty())
			{
				builder.append(" ");
			}

			builder.append(amt);
		}

		return builder.build();
	}
}
