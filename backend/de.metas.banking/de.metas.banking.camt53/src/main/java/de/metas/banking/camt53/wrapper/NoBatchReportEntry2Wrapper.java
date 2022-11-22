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
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import java.util.TimeZone;
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
		final String string = getUnstructuredRemittanceInfo(" ")
				+ " "
				+ getLineDescription(" ");

		return ImmutableSet.copyOf(string.split(" "));
	}

	@NonNull
	public String getUnstructuredRemittanceInfo()
	{
		return getUnstructuredRemittanceInfo("\n");
	}

	@NonNull
	private String getUnstructuredRemittanceInfo(@NonNull final String delimiter)
	{
		return String.join(delimiter, getEntryTransaction()
				.map(EntryTransaction2::getRmtInf)
				.map(RemittanceInformation5::getUstrd)
				.orElseGet(ImmutableList::of));
	}

	@NonNull
	public Optional<Instant> getStatementLineDate(@NonNull final ZoneId zoneId)
	{
		final TimeZone timeZone = TimeZone.getTimeZone(zoneId);
		
		return Optional.ofNullable(entry.getValDt())
				.map(DateAndDateTimeChoice::getDt)
				.map(xmlGregorianCalendar -> xmlGregorianCalendar.toGregorianCalendar(timeZone, null, null).toInstant());
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
		return getLineDescription("\n");
	}

	@NonNull
	private String getLineDescription(@NonNull final String delimiter)
	{
		final String trxDetails = getEntryTransaction()
				.map(EntryTransaction2::getAddtlTxInf)
				.filter(Check::isNotBlank)
				.orElse(null);

		return Stream.of(trxDetails, entry.getAddtlNtryInf())
				.filter(Check::isNotBlank)
				.collect(Collectors.joining(delimiter));
	}

	@NonNull
	public Amount getStatementAmount()
	{
		final BigDecimal value = getStatementAmountValue();
		final CurrencyCode currencyCode = CurrencyCode.ofThreeLetterCode(entry.getAmt().getCcy());

		return Amount.of(value, currencyCode);
	}

	@NonNull
	private BigDecimal getStatementAmountValue()
	{
		return Optional.ofNullable(entry.getAmt().getValue())
				.map(value -> isCRDT()
						? value
						: value.negate())
				.orElse(BigDecimal.ZERO);
	}

	/**
	 * @return true if this is a "credit" line (i.e. we get money) 
	 */
	public boolean isCRDT()
	{
		return CRDT == entry.getCdtDbtInd();
	}

	public String getAcctSvcrRef()
	{
		return entry.getAcctSvcrRef();
	}

	@NonNull
	public String getDbtrNames()
	{
		return entry.getNtryDtls().stream()
				.flatMap(entryDetails1 -> entryDetails1.getTxDtls().stream())
				.map(EntryTransaction2::getRltdPties)
				.filter(party -> party!= null && party.getDbtr() != null)
				.map(party -> party.getDbtr().getNm())
				.filter(Check::isNotBlank)
				.collect(Collectors.joining(" "));
	}

	@NonNull
	public String getCdtrNames()
	{
		return entry.getNtryDtls().stream()
				.flatMap(entryDetails1 -> entryDetails1.getTxDtls().stream())
				.map(EntryTransaction2::getRltdPties)
				.filter(party -> party!= null && party.getCdtr() != null)
				.map(party -> party.getCdtr().getNm())
				.filter(Check::isNotBlank)
				.collect(Collectors.joining(" "));
	}
}
