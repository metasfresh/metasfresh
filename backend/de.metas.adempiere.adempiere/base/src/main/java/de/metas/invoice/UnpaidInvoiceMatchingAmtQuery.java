/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.invoice;

import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.document.engine.DocStatus;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.QueryLimit;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Optional;

@Value
public class UnpaidInvoiceMatchingAmtQuery
{
	@Nullable
	IQueryFilter<I_C_Invoice> additionalFilter;

	@NonNull
	ImmutableSet<DocStatus> onlyDocStatuses;

	@NonNull
	QueryLimit queryLimit;

	@Nullable
	Amount openAmountAtDate;

	@Nullable
	ZonedDateTime openAmountEvaluationDate;

	@Builder
	private UnpaidInvoiceMatchingAmtQuery(
			@Nullable final IQueryFilter<I_C_Invoice> additionalFilter,
			@NonNull final ImmutableSet<DocStatus> onlyDocStatuses,
			@NonNull final QueryLimit queryLimit,
			@Nullable final Amount openAmountAtDate,
			@Nullable final ZonedDateTime openAmountEvaluationDate)
	{
		if (openAmountAtDate != null)
		{
			Check.assumeNotNull(openAmountEvaluationDate, "OpenAmountEvaluationDate must be specified when OpenAmountAtDate is specified");
		}

		this.additionalFilter = additionalFilter;
		this.onlyDocStatuses = onlyDocStatuses;
		this.queryLimit = queryLimit;
		this.openAmountAtDate = openAmountAtDate;
		this.openAmountEvaluationDate = openAmountEvaluationDate;
	}

	@NonNull
	public Optional<CurrencyCode> getCurrencyCode()
	{
		return Optional.ofNullable(openAmountAtDate).map(Amount::getCurrencyCode);
	}

	@NonNull
	public UnpaidInvoiceQuery getUnpaidInvoiceQuery()
	{
		return UnpaidInvoiceQuery.builder()
				.additionalFilter(additionalFilter)
				.onlyDocStatuses(onlyDocStatuses)
				.queryLimit(queryLimit)
				.build();
	}
}
