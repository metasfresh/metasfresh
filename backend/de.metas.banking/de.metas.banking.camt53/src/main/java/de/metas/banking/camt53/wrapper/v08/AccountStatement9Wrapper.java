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
import de.metas.banking.api.BankAccountService;
import de.metas.banking.camt53.jaxb.camt053_001_08.AccountStatement9;
import de.metas.banking.camt53.jaxb.camt053_001_08.CashBalance8;
import de.metas.banking.camt53.jaxb.camt053_001_08.GenericAccountIdentification1;
import de.metas.banking.camt53.jaxb.camt053_001_08.ReportEntry10;
import de.metas.banking.camt53.wrapper.AccountStatementWrapper;
import de.metas.banking.camt53.wrapper.IStatementLineWrapper;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.i18n.IMsgBL;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static de.metas.banking.camt53.jaxb.camt053_001_08.CreditDebitCode.CRDT;

@Value
@EqualsAndHashCode(callSuper = true)
public class AccountStatement9Wrapper extends AccountStatementWrapper
{
	// in camt.053.001.08 BalanceType10Choice/Cd is a plain string (ExternalBalanceType1Code), no longer a generated enum
	private static final String BALANCE_TYPE_CODE_OPBD = "OPBD";
	private static final String BALANCE_TYPE_CODE_PRCD = "PRCD";

	@NonNull
	AccountStatement9 accountStatement9;

	@Builder
	private AccountStatement9Wrapper(
			@NonNull final AccountStatement9 accountStatement9,
			@NonNull final BankAccountService bankAccountService,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final IMsgBL msgBL)
	{
		super(bankAccountService, currencyRepository, msgBL);

		this.accountStatement9 = accountStatement9;
	}

	@Override
	@NonNull
	public ZonedDateTime getStatementDate(@NonNull final ZoneId timeZone)
	{
		final XMLGregorianCalendar xmlGregorianCalendar = accountStatement9.getCreDtTm();

		return Instant.ofEpochMilli(xmlGregorianCalendar.toGregorianCalendar().getTimeInMillis())
				.atZone(timeZone);
	}

	@Override
	@NonNull
	public BigDecimal getBeginningBalance()
	{
		final CashBalance8 beginningCashBalance = findOPBDCashBalance()
				.orElseGet(() -> findPRCDCashBalance().orElse(null));

		return Optional.ofNullable(beginningCashBalance)
				.map(CashBalance8::getAmt)
				.map(currencyAndAmount -> isCRDTCashBalance(beginningCashBalance)
						? currencyAndAmount.getValue()
						: currencyAndAmount.getValue().negate())
				.orElse(BigDecimal.ZERO);
	}

	@Override
	@NonNull
	public Optional<CurrencyCode> getStatementCurrencyCode()
	{
		return Optional.ofNullable(accountStatement9.getAcct().getCcy())
				.map(CurrencyCode::ofThreeLetterCode);
	}

	@Override
	@NonNull
	public String getId()
	{
		return accountStatement9.getId();
	}

	@Override
	@NonNull
	public ImmutableList<IStatementLineWrapper> getStatementLines()
	{
		return accountStatement9.getNtry()
				.stream()
				.map(this::buildBatchReportEntryWrapper)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public boolean hasNoBankStatementLines()
	{
		return accountStatement9.getNtry().isEmpty();
	}

	@Override
	@NonNull
	protected Optional<String> getAccountIBAN()
	{
		return Optional.ofNullable(accountStatement9.getAcct().getId().getIBAN());
	}

	@Override
	@NonNull
	protected Optional<String> getSwiftCode()
	{
		return Optional.ofNullable(accountStatement9.getAcct().getSvcr())
				.map(svcr -> svcr.getFinInstnId().getBICFI())
				.filter(Check::isNotBlank);
	}

	@Override
	@NonNull
	protected Optional<String> getAccountNo()
	{
		return Optional.ofNullable(accountStatement9.getAcct().getId().getOthr())
				.map(GenericAccountIdentification1::getId);
	}

	@NonNull
	private IStatementLineWrapper buildBatchReportEntryWrapper(@NonNull final ReportEntry10 reportEntry)
	{
		return BatchReportEntry10Wrapper.builder()
				.currencyRepository(getCurrencyRepository())
				.entry(reportEntry)
				.build();
	}

	@NonNull
	private Optional<CashBalance8> findOPBDCashBalance()
	{
		return accountStatement9.getBal()
				.stream()
				.filter(AccountStatement9Wrapper::isOPBDCashBalance)
				.findFirst();
	}

	@NonNull
	private Optional<CashBalance8> findPRCDCashBalance()
	{
		return accountStatement9.getBal()
				.stream()
				.filter(AccountStatement9Wrapper::isPRCDCashBalance)
				.findFirst();
	}

	private static boolean isPRCDCashBalance(@NonNull final CashBalance8 cashBalance)
	{
		return BALANCE_TYPE_CODE_PRCD.equals(cashBalance.getTp().getCdOrPrtry().getCd());
	}

	private static boolean isOPBDCashBalance(@NonNull final CashBalance8 cashBalance)
	{
		return BALANCE_TYPE_CODE_OPBD.equals(cashBalance.getTp().getCdOrPrtry().getCd());
	}

	private static boolean isCRDTCashBalance(@NonNull final CashBalance8 cashBalance)
	{
		return CRDT.equals(cashBalance.getCdtDbtInd());
	}
}
