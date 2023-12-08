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

package de.metas.banking.camt53.wrapper.v04;

import com.google.common.collect.ImmutableList;
import de.metas.banking.camt53.jaxb.camt053_001_04.ActiveOrHistoricCurrencyAndAmount;
import de.metas.banking.camt53.jaxb.camt053_001_04.AmountAndCurrencyExchange3;
import de.metas.banking.camt53.jaxb.camt053_001_04.AmountAndCurrencyExchangeDetails3;
import de.metas.banking.camt53.jaxb.camt053_001_04.CurrencyExchange5;
import de.metas.banking.camt53.jaxb.camt053_001_04.DateAndDateTimeChoice;
import de.metas.banking.camt53.jaxb.camt053_001_04.EntryDetails3;
import de.metas.banking.camt53.jaxb.camt053_001_04.EntryTransaction4;
import de.metas.banking.camt53.jaxb.camt053_001_04.InterestRecord1;
import de.metas.banking.camt53.jaxb.camt053_001_04.RemittanceInformation7;
import de.metas.banking.camt53.jaxb.camt053_001_04.ReportEntry4;
import de.metas.banking.camt53.jaxb.camt053_001_04.TransactionInterest3;
import de.metas.banking.camt53.wrapper.BatchReportEntryWrapper;
import de.metas.banking.camt53.wrapper.ITransactionDtlsWrapper;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.money.Money;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import static de.metas.banking.camt53.jaxb.camt053_001_04.CreditDebitCode.CRDT;

@Value
@EqualsAndHashCode(callSuper = true)
public class BatchReportEntry4Wrapper extends BatchReportEntryWrapper
{
	@NonNull
	ReportEntry4 entry;

	@Builder
	private BatchReportEntry4Wrapper(
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final ReportEntry4 entry)
	{
		super(currencyRepository);
		this.entry = entry;
	}

	@NonNull
	public List<EntryTransaction4> getEntryTransaction()
	{
		return CollectionUtils.firstOptional(entry.getNtryDtls())
				.map(EntryDetails3::getTxDtls)
				.orElseGet(ImmutableList::of);
	}

	public @NonNull Optional<ZonedDateTime> getStatementLineDate(@NonNull final ZoneId zoneId)
	{
		final TimeZone timeZone = TimeZone.getTimeZone(zoneId);

		return Optional.ofNullable(entry.getValDt())
				.map(DateAndDateTimeChoice::getDt)
				.map(xmlGregorianCalendar -> xmlGregorianCalendar.toGregorianCalendar(timeZone, null, null).toZonedDateTime());
	}

	@NonNull
	public Optional<Money> getInterestAmount()
	{
		final TransactionInterest3 interest = entry.getIntrst();
		if (interest != null)
		{
			final InterestRecord1 interesetRecord = CollectionUtils.first(interest.getRcrd());
			return toMoney(interesetRecord.getAmt());
		}

		return Optional.empty();
	}

	@NonNull
	private Optional<Money> toMoney(@Nullable final ActiveOrHistoricCurrencyAndAmount amt)
	{
		if (amt == null || amt.getValue() == null)
		{
			return Optional.empty();
		}

		return Optional.of(amt.getCcy())
				.map(CurrencyCode::ofThreeLetterCode)
				.map(this::getCurrencyIdByCurrencyCode)
				.map(currencyId -> Money.of(amt.getValue(), currencyId));
	}

	@NonNull
	public Optional<BigDecimal> getCurrencyRate()
	{
		return getEntryTransaction()
				.stream()
				.findFirst()
				.map(EntryTransaction4::getAmtDtls)
				.map(AmountAndCurrencyExchange3::getCntrValAmt)
				.map(AmountAndCurrencyExchangeDetails3::getCcyXchg)
				.map(CurrencyExchange5::getXchgRate);
	}

	/**
	 * @return true if this is a "credit" line (i.e. we get money)
	 */
	@Override
	public boolean isCRDT()
	{
		return CRDT == entry.getCdtDbtInd();
	}

	@Override
	@Nullable
	public String getAcctSvcrRef()
	{
		return entry.getAcctSvcrRef();
	}

	@Override
	@NonNull
	public String getDbtrNames()
	{
		return entry.getNtryDtls().stream()
				.flatMap(entryDetails3 -> entryDetails3.getTxDtls().stream())
				.map(EntryTransaction4::getRltdPties)
				.filter(party -> party != null && party.getDbtr() != null)
				.map(party -> party.getDbtr().getNm())
				.filter(Check::isNotBlank)
				.collect(Collectors.joining(" "));
	}

	@Override
	@NonNull
	public String getCdtrNames()
	{
		return entry.getNtryDtls().stream()
				.flatMap(entryDetails3 -> entryDetails3.getTxDtls().stream())
				.map(EntryTransaction4::getRltdPties)
				.filter(party -> party != null && party.getCdtr() != null)
				.map(party -> party.getCdtr().getNm())
				.filter(Check::isNotBlank)
				.collect(Collectors.joining(" "));
	}

	@Override
	@Nullable
	public String getLineReference()
	{
		return entry.getNtryRef();
	}

	@Override
	@NonNull
	protected String getUnstructuredRemittanceInfo(@NonNull final String delimiter)
	{
		return String.join(delimiter,
				getEntryTransaction()
						.stream()
						.findFirst()
						.map(EntryTransaction4::getRmtInf)
						.map(RemittanceInformation7::getUstrd)
						.orElseGet(ImmutableList::of));
	}

	@Override
	@NonNull
	protected String getLineDescription(@NonNull final String delimiter)
	{
		final ArrayList<String> trxDetails = new ArrayList<>();
		trxDetails.add(entry.getAddtlNtryInf());

		getEntryTransaction()
				.forEach(ntryDetails -> {
					final String addtlTxInf = ntryDetails.getAddtlTxInf();
					if (Check.isNotBlank(addtlTxInf))
					{
						trxDetails.add(addtlTxInf);
					}
				});


		return trxDetails.stream()
				.filter(Check::isNotBlank)
				.collect(Collectors.joining(delimiter));
	}

	@Override
	@Nullable
	protected String getCcy()
	{
		return entry.getAmt().getCcy();
	}

	@Override
	@Nullable
	protected BigDecimal getAmtValue()
	{
		return entry.getAmt().getValue();
	}

	public boolean isBatchTransaction()
	{
		return getEntryTransaction().size() > 1;
	}

	@Override
	public List<ITransactionDtlsWrapper> getTransactionDtlsWrapper()
	{
		return getEntryTransaction()
				.stream()
				.map(tr -> TransactionDtls4Wrapper.builder().entryDtls(tr).build())
				.collect(ImmutableList.toImmutableList());
	}
}
