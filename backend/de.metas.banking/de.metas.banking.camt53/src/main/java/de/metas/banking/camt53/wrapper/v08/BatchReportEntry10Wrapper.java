/*
 * #%L
 * de.metas.banking.camt53
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.banking.camt53.wrapper.v08;

import com.google.common.collect.ImmutableList;
import de.metas.banking.camt53.jaxb.camt053_001_08.ActiveOrHistoricCurrencyAndAmount;
import de.metas.banking.camt53.jaxb.camt053_001_08.AmountAndCurrencyExchange3;
import de.metas.banking.camt53.jaxb.camt053_001_08.AmountAndCurrencyExchangeDetails3;
import de.metas.banking.camt53.jaxb.camt053_001_08.CurrencyExchange5;
import de.metas.banking.camt53.jaxb.camt053_001_08.DateAndDateTime2Choice;
import de.metas.banking.camt53.jaxb.camt053_001_08.EntryDetails9;
import de.metas.banking.camt53.jaxb.camt053_001_08.EntryTransaction10;
import de.metas.banking.camt53.jaxb.camt053_001_08.InterestRecord2;
import de.metas.banking.camt53.jaxb.camt053_001_08.RemittanceInformation16;
import de.metas.banking.camt53.jaxb.camt053_001_08.ReportEntry10;
import de.metas.banking.camt53.jaxb.camt053_001_08.TransactionInterest4;
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
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import static de.metas.banking.camt53.jaxb.camt053_001_08.CreditDebitCode.CRDT;

@Value
@EqualsAndHashCode(callSuper = true)
public class BatchReportEntry10Wrapper extends BatchReportEntryWrapper
{
	@NonNull
	ReportEntry10 entry;

	@Builder
	private BatchReportEntry10Wrapper(
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final ReportEntry10 entry)
	{
		super(currencyRepository);
		this.entry = entry;
	}

	@NonNull
	public List<EntryTransaction10> getEntryTransaction()
	{
		return CollectionUtils.firstOptional(entry.getNtryDtls())
				.map(EntryDetails9::getTxDtls)
				.orElseGet(ImmutableList::of);
	}

	public @NonNull Optional<ZonedDateTime> getStatementLineDate(@NonNull final ZoneId zoneId)
	{
		final TimeZone timeZone = TimeZone.getTimeZone(zoneId);

		return Optional.ofNullable(entry.getValDt())
				.map(BatchReportEntry10Wrapper::getDateOrDateTime)
				.map(xmlGregorianCalendar -> xmlGregorianCalendar.toGregorianCalendar(timeZone, null, null).toZonedDateTime());
	}

	/**
	 * In camt.053.001.08, {@link DateAndDateTime2Choice} offers {@code Dt} (date) or {@code DtTm} (date-time); exactly one is set.
	 */
	@Nullable
	private static XMLGregorianCalendar getDateOrDateTime(@NonNull final DateAndDateTime2Choice choice)
	{
		return choice.getDt() != null ? choice.getDt() : choice.getDtTm();
	}

	@NonNull
	public Optional<Money> getInterestAmount()
	{
		final TransactionInterest4 interest = entry.getIntrst();
		if (interest != null)
		{
			final InterestRecord2 interesetRecord = CollectionUtils.first(interest.getRcrd());
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
				.map(EntryTransaction10::getAmtDtls)
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
				.map(EntryTransaction10::getRltdPties)
				.filter(party -> party != null && party.getDbtr() != null && party.getDbtr().getPty() != null)
				.map(party -> party.getDbtr().getPty().getNm())
				.filter(Check::isNotBlank)
				.collect(Collectors.joining(" "));
	}

	@Override
	@NonNull
	public String getCdtrNames()
	{
		return entry.getNtryDtls().stream()
				.flatMap(entryDetails3 -> entryDetails3.getTxDtls().stream())
				.map(EntryTransaction10::getRltdPties)
				.filter(party -> party != null && party.getCdtr() != null && party.getCdtr().getPty() != null)
				.map(party -> party.getCdtr().getPty().getNm())
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
		return String.join(delimiter, getUnstructuredRemittanceInfoList());
	}

	@Override
	@NonNull
	protected List<String> getUnstructuredRemittanceInfoList()
	{
		return getEntryTransaction()
				.stream()
				.findFirst()
				.map(EntryTransaction10::getRmtInf)
				.map(RemittanceInformation16::getUstrd)
				.orElse(ImmutableList.of())
				.stream()
				.map(str -> Arrays.asList(str.split(" ")))
				.flatMap(List::stream)
				.filter(Check::isNotBlank)
				.toList();
	}

	@Override
	@NonNull
	protected String getLineDescription(@NonNull final String delimiter)
	{
		return getLineDescriptionList().stream()
				.filter(Check::isNotBlank)
				.collect(Collectors.joining(delimiter));
	}

	@Override
	@NonNull
	protected List<String> getLineDescriptionList()
	{
		final List<String> lineDesc = new ArrayList<>();

		final String addtlNtryInfStr = entry.getAddtlNtryInf();
		if (addtlNtryInfStr != null)
		{
			lineDesc.addAll(Arrays.stream(addtlNtryInfStr.split(" "))
					.filter(Check::isNotBlank)
					.toList());
		}

		final List<String> trxDetails = getEntryTransaction()
				.stream()
				.map(EntryTransaction10::getAddtlTxInf)
				.filter(Objects::nonNull)
				.map(str -> Arrays.asList(str.split(" ")))
				.flatMap(List::stream)
				.filter(Check::isNotBlank)
				.toList();

		lineDesc.addAll(trxDetails);

		return lineDesc;
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

	public boolean isBatchTransaction() {return getEntryTransaction().size() > 1;}

	@Override
	public List<ITransactionDtlsWrapper> getTransactionDtlsWrapper()
	{
		return getEntryTransaction()
				.stream()
				.map(tr -> TransactionDtls10Wrapper.builder().entryDtls(tr).build())
				.collect(ImmutableList.toImmutableList());
	}
}
