/*
 * #%L
 * camt53
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

package de.metas.banking.camt53.wrapper;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.banking.camt53.jaxb.camt053_001_02.ActiveOrHistoricCurrencyAndAmount;
import de.metas.banking.camt53.jaxb.camt053_001_02.AmountAndCurrencyExchange3;
import de.metas.banking.camt53.jaxb.camt053_001_02.AmountAndCurrencyExchangeDetails3;
import de.metas.banking.camt53.jaxb.camt053_001_02.CurrencyExchange5;
import de.metas.banking.camt53.jaxb.camt053_001_02.DateAndDateTimeChoice;
import de.metas.banking.camt53.jaxb.camt053_001_02.EntryDetails1;
import de.metas.banking.camt53.jaxb.camt053_001_02.EntryTransaction2;
import de.metas.banking.camt53.jaxb.camt053_001_02.RemittanceInformation5;
import de.metas.banking.camt53.jaxb.camt053_001_02.ReportEntry2;
import de.metas.banking.camt53.jaxb.camt053_001_02.TransactionInterest2;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.metas.banking.camt53.jaxb.camt053_001_02.CreditDebitCode.CRDT;

@Value
public class NoBatchReportEntry2Wrapper
{
	private static final Logger logger = LogManager.getLogger(NoBatchReportEntry2Wrapper.class);

	@NonNull
	CurrencyRepository currencyRepository;

	@NonNull
	ReportEntry2 entry;

	@Builder
	private NoBatchReportEntry2Wrapper(
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final ReportEntry2 entry)
	{
		Check.assume(Check.isEmpty(entry.getNtryDtls()) || entry.getNtryDtls().size() == 1, "Batched transactions are not supported!");

		this.currencyRepository = currencyRepository;
		this.entry = entry;
	}

	@NonNull
	public Optional<EntryTransaction2> getEntryTransaction()
	{
		return Optional.of(entry.getNtryDtls())
				.filter(list -> !list.isEmpty())
				.map(list -> list.get(0))
				.map(EntryDetails1::getTxDtls)
				.filter(list -> !list.isEmpty())
				.map(list -> list.get(0));
	}

	@NonNull
	public ImmutableSet<String> getInvoiceDocNoCandidates()
	{
		final String unstructuredRemittanceInfo = String.join(" ", getEntryTransaction()
				.map(EntryTransaction2::getRmtInf)
				.map(RemittanceInformation5::getUstrd)
				.orElseGet(ImmutableList::of));

		return ImmutableSet.copyOf(unstructuredRemittanceInfo.split(" "));
	}

	@NonNull
	public Optional<Instant> getStatementLineDate()
	{
		return Optional.ofNullable(entry.getValDt())
				.map(DateAndDateTimeChoice::getDt)
				.map(xmlGregorianCalendar -> xmlGregorianCalendar.toGregorianCalendar().toInstant());
	}

	@NonNull
	public Optional<Money> getInterestAmount()
	{
		final ActiveOrHistoricCurrencyAndAmount activeOrHistoricCurrencyAndAmount = Optional.ofNullable(entry.getIntrst())
				.filter(list -> !list.isEmpty())
				.map(list -> list.get(0))
				.map(TransactionInterest2::getAmt)
				.orElse(null);

		if (activeOrHistoricCurrencyAndAmount == null || activeOrHistoricCurrencyAndAmount.getValue() == null)
		{
			return Optional.empty();
		}

		return Optional.of(activeOrHistoricCurrencyAndAmount.getCcy())
				.map(CurrencyCode::ofThreeLetterCode)
				.map(currencyRepository::getCurrencyIdByCurrencyCode)
				.map(currencyId -> Money.of(activeOrHistoricCurrencyAndAmount.getValue(), currencyId));
	}

	@NonNull
	public Optional<BigDecimal> getCurrencyRate()
	{
		return getEntryTransaction()
				.map(EntryTransaction2::getAmtDtls)
				.map(AmountAndCurrencyExchange3::getCntrValAmt)
				.map(AmountAndCurrencyExchangeDetails3::getCcyXchg)
				.map(CurrencyExchange5::getXchgRate);
	}

	@NonNull
	public String getLineDescription()
	{
		final String trxDetails = getEntryTransaction()
				.map(EntryTransaction2::getAddtlTxInf)
				.filter(Check::isNotBlank)
				.orElse(null);

		return Stream.of(trxDetails, entry.getAddtlNtryInf())
				.filter(Check::isNotBlank)
				.collect(Collectors.joining("\n"));
	}

	@NonNull
	public Money getStatementAmount()
	{
		final BigDecimal value = getStatementAmountValue();
		final CurrencyId currencyId = getStatementAmountCurrencyId(entry.getAmt().getCcy());

		return Money.of(value, currencyId);
	}

	@NonNull
	private BigDecimal getStatementAmountValue()
	{
		return Optional.ofNullable(entry.getAmt().getValue())
				.map(value -> CRDT == entry.getCdtDbtInd()
						? value
						: value.negate())
				.orElse(BigDecimal.ZERO);
	}

	@NonNull
	private CurrencyId getStatementAmountCurrencyId(@NonNull final String currencyCodeStr)
	{
		final CurrencyCode currencyCode = CurrencyCode.ofThreeLetterCode(currencyCodeStr);
		return currencyRepository.getCurrencyIdByCurrencyCode(currencyCode);
	}
}
