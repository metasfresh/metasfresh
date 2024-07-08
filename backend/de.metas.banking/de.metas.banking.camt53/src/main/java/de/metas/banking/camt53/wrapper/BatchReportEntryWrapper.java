/*
 * #%L
 * de.metas.banking.camt53
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.banking.camt53.wrapper;

import com.google.common.collect.ImmutableSet;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.money.CurrencyId;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

@NonFinal
@Value
public abstract class BatchReportEntryWrapper implements IStatementLineWrapper
{
	@NonNull CurrencyRepository currencyRepository;

	protected BatchReportEntryWrapper(@NonNull final CurrencyRepository currencyRepository)
	{
		this.currencyRepository = currencyRepository;
	}

	@Override
	@NonNull
	public ImmutableSet<String> getDocumentReferenceCandidates()
	{
		final String string = getUnstructuredRemittanceInfo(" ")
				+ " "
				+ getLineDescription(" ");

		return ImmutableSet.copyOf(string.split(" "));
	}

	@Override
	@NonNull
	public String getUnstructuredRemittanceInfo()
	{
		return getUnstructuredRemittanceInfo("\n");
	}

	@Override
	@NonNull
	public String getLineDescription()
	{
		return getLineDescription("\n");
	}

	@Override
	@NonNull
	public Amount getStatementAmount()
	{
		final BigDecimal value = getStatementAmountValue();
		final CurrencyCode currencyCode = CurrencyCode.ofThreeLetterCode(getCcy());

		return Amount.of(value, currencyCode);
	}

	@NonNull
	protected CurrencyId getCurrencyIdByCurrencyCode(@NonNull final CurrencyCode currencyCode)
	{
		return currencyRepository.getCurrencyIdByCurrencyCode(currencyCode);
	}

	@NonNull
	private BigDecimal getStatementAmountValue()
	{
		return Optional.ofNullable(getAmtValue())
				.map(value -> isCRDT()
						? value
						: value.negate())
				.orElse(BigDecimal.ZERO);
	}

	@NonNull
	protected abstract String getUnstructuredRemittanceInfo(@NonNull final String delimiter);

	@NonNull
	protected abstract String getLineDescription(@NonNull final String delimiter);

	@Nullable
	protected abstract String getCcy();

	@Nullable
	protected abstract BigDecimal getAmtValue();
}
