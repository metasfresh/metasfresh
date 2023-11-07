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

package de.metas.banking.camt53.wrapper.v02;

import com.google.common.collect.ImmutableList;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.camt53.jaxb.camt053_001_02.AccountStatement2;
import de.metas.banking.camt53.jaxb.camt053_001_02.BalanceType12Code;
import de.metas.banking.camt53.jaxb.camt053_001_02.CashBalance3;
import de.metas.banking.camt53.jaxb.camt053_001_02.GenericAccountIdentification1;
import de.metas.banking.camt53.jaxb.camt053_001_02.ReportEntry2;
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

import static de.metas.banking.camt53.jaxb.camt053_001_02.CreditDebitCode.CRDT;

@Value
@EqualsAndHashCode(callSuper = true)
public class AccountStatement2Wrapper extends AccountStatementWrapper
{
	@NonNull
	AccountStatement2 accountStatement2;

	@Builder
	private AccountStatement2Wrapper(
			@NonNull final AccountStatement2 accountStatement2,
			@NonNull final BankAccountService bankAccountService,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final IMsgBL msgBL)
	{
		super(bankAccountService, currencyRepository, msgBL);

		this.accountStatement2 = accountStatement2;
	}

	@Override
	@NonNull
	public ZonedDateTime getStatementDate(@NonNull final ZoneId timeZone)
	{
		final XMLGregorianCalendar xmlGregorianCalendar = accountStatement2.getCreDtTm();

		return Instant.ofEpochMilli(xmlGregorianCalendar.toGregorianCalendar().getTimeInMillis())
				.atZone(timeZone);
	}

	@Override
	@NonNull
	public BigDecimal getBeginningBalance()
	{
		final CashBalance3 beginningCashBalance = findOPBDCashBalance()
				.orElseGet(() -> findPRCDCashBalance().orElse(null));

		return Optional.ofNullable(beginningCashBalance)
				.map(CashBalance3::getAmt)
				.map(currencyAndAmount -> isCRDTCashBalance(beginningCashBalance)
						? currencyAndAmount.getValue()
						: currencyAndAmount.getValue().negate())
				.orElse(BigDecimal.ZERO);
	}

	@Override
	@NonNull
	public Optional<CurrencyCode> getStatementCurrencyCode()
	{
		return Optional.ofNullable(accountStatement2.getAcct().getCcy())
				.map(CurrencyCode::ofThreeLetterCode);
	}

	@Override
	@NonNull
	public String getId()
	{
		return accountStatement2.getId();
	}

	@Override
	@NonNull
	public ImmutableList<IStatementLineWrapper> getStatementLines()
	{
		return accountStatement2.getNtry()
				.stream()
				.map(this::buildBatchReportEntryWrapper)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public boolean hasNoBankStatementLines()
	{
		return accountStatement2.getNtry().isEmpty();
	}

	@Override
	@NonNull
	protected Optional<String> getAccountIBAN()
	{
		return Optional.ofNullable(accountStatement2.getAcct().getId().getIBAN());
	}

	@Override
	@NonNull
	protected Optional<String> getSwiftCode()
	{
		return Optional.ofNullable(accountStatement2.getAcct().getSvcr())
				.map(branchAndFinancialInstitutionIdentification4 -> branchAndFinancialInstitutionIdentification4.getFinInstnId().getBIC())
				.filter(Check::isNotBlank);
	}

	@Override
	@NonNull
	protected Optional<String> getAccountNo()
	{
		return Optional.ofNullable(accountStatement2.getAcct().getId().getOthr())
				.map(GenericAccountIdentification1::getId);
	}

	@NonNull
	private IStatementLineWrapper buildBatchReportEntryWrapper(@NonNull final ReportEntry2 reportEntry)
	{
		return BatchReportEntry2Wrapper.builder()
				.currencyRepository(getCurrencyRepository())
				.entry(reportEntry)
				.build();
	}

	@NonNull
	private Optional<CashBalance3> findOPBDCashBalance()
	{
		return accountStatement2.getBal()
				.stream()
				.filter(AccountStatement2Wrapper::isOPBDCashBalance)
				.findFirst();
	}

	@NonNull
	private Optional<CashBalance3> findPRCDCashBalance()
	{
		return accountStatement2.getBal()
				.stream()
				.filter(AccountStatement2Wrapper::isPRCDCashBalance)
				.findFirst();
	}

	private static boolean isPRCDCashBalance(@NonNull final CashBalance3 cashBalance)
	{
		final BalanceType12Code balanceTypeCode = cashBalance.getTp().getCdOrPrtry().getCd();

		return BalanceType12Code.PRCD.equals(balanceTypeCode);
	}

	private static boolean isOPBDCashBalance(@NonNull final CashBalance3 cashBalance)
	{
		final BalanceType12Code balanceTypeCode = cashBalance.getTp().getCdOrPrtry().getCd();

		return BalanceType12Code.OPBD.equals(balanceTypeCode);
	}

	private static boolean isCRDTCashBalance(@NonNull final CashBalance3 cashBalance)
	{
		return CRDT.equals(cashBalance.getCdtDbtInd());
	}
}
