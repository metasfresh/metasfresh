/*
 * #%L
 * de.metas.banking.base
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

package de.metas.banking.bankstatement.importer.wrapper;

import ch.qos.logback.classic.Level;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankId;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.bankstatement.jaxb.camt053_001_02.AccountStatement2;
import de.metas.banking.bankstatement.jaxb.camt053_001_02.BalanceType12Code;
import de.metas.banking.bankstatement.jaxb.camt053_001_02.CashBalance3;
import de.metas.banking.bankstatement.jaxb.camt053_001_02.GenericAccountIdentification1;
import de.metas.banking.service.BankStatementCreateRequest;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static de.metas.banking.bankstatement.jaxb.camt053_001_02.CreditDebitCode.CRDT;

@Value
public class AccountStatement2Wrapper
{
	private static final Logger logger = LogManager.getLogger(AccountStatement2Wrapper.class);

	@NonNull
	IOrgDAO orgDAO;

	@NonNull
	AccountStatement2 accountStatement2;

	@NonNull
	BankAccountService bankAccountService;

	@NonNull
	CurrencyRepository currencyRepository;

	@Builder
	private AccountStatement2Wrapper(
			@NonNull final IOrgDAO orgDAO,
			@NonNull final AccountStatement2 accountStatement2,
			@NonNull final BankAccountService bankAccountService,
			@NonNull final CurrencyRepository currencyRepository)
	{
		this.orgDAO = orgDAO;
		this.accountStatement2 = accountStatement2;
		this.bankAccountService = bankAccountService;
		this.currencyRepository = currencyRepository;
	}

	@NonNull
	public Optional<BankStatementCreateRequest> buildBankStatementCreateRequest()
	{
		final BankId bankId = getBankId().orElse(null);

		if (bankId == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("Skipping AccountStatement={} because no bank was found for it!", accountStatement2);
			return Optional.empty();
		}

		final BankAccountId bankAccountId = getBPartnerBankAccountId(bankId).orElse(null);
		if (bankAccountId == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog(
					"Skipping AccountStatement={} because no Bank Account was found for it!", accountStatement2);

			return Optional.empty();
		}

		if (!isStatementCurrencyValid(bankAccountId))
		{
			Loggables.withLogger(logger, Level.WARN).addLog(
					"Skipping AccountStatement={} because currency of the statement does not match currency of the bank account!", accountStatement2);
			return Optional.empty();
		}

		final BigDecimal beginningBalance = getBeginningBalance();
		final OrgId orgId = getOrgId(bankAccountId);
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		final ZonedDateTime statementDate = getStatementDate(timeZone);

		return Optional.of(BankStatementCreateRequest.builder()
								   .orgId(orgId)
								   .orgBankAccountId(bankAccountId)
								   .statementDate(TimeUtil.asLocalDate(statementDate, timeZone))
								   .name(statementDate.toString())
								   .beginningBalance(beginningBalance)
								   .build());
	}

	@NonNull
	private Optional<BankId> getBankId()
	{
		return getSwiftCode().flatMap(bankAccountService::getBankIdBySwiftCode);
	}

	@NonNull
	private Optional<BankAccountId> getBPartnerBankAccountId(@NonNull final BankId bankId)
	{
		return getAccountNo().flatMap(accountNo -> bankAccountService.getBankAccountId(bankId, accountNo));
	}

	@NonNull
	private Optional<String> getAccountNo()
	{
		return Optional.ofNullable(accountStatement2.getAcct().getId().getOthr())
				.map(GenericAccountIdentification1::getId);

	}

	@NonNull
	private Optional<String> getSwiftCode()
	{
		return Optional.ofNullable(accountStatement2.getAcct().getSvcr())
				.map(branchAndFinancialInstitutionIdentification4 -> branchAndFinancialInstitutionIdentification4.getFinInstnId().getBIC())
				.filter(Check::isNotBlank);
	}

	private boolean isStatementCurrencyValid(@NonNull final BankAccountId bankAccountId)
	{
		final CurrencyCode statementCurrencyCode = getStatementCurrencyCode().orElse(null);
		if (statementCurrencyCode == null)
		{
			Loggables.withLogger(logger, Level.WARN).addLog("Acct.Ccy is missing! Currency cannot be validated!", accountStatement2);
			return false;
		}

		return Optional.of(bankAccountService.getById(bankAccountId))
				.map(BankAccount::getCurrencyId)
				.map(currencyRepository::getById)
				.map(Currency::getCurrencyCode)
				.filter(currencyCode -> currencyCode.equals(statementCurrencyCode))
				.isPresent();
	}

	@NonNull
	private Optional<CurrencyCode> getStatementCurrencyCode()
	{
		return Optional.ofNullable(accountStatement2.getAcct().getCcy())
				.map(CurrencyCode::ofThreeLetterCode);
	}

	@NonNull
	private ZonedDateTime getStatementDate(@NonNull final ZoneId timeZone)
	{
		final XMLGregorianCalendar xmlGregorianCalendar = accountStatement2.getCreDtTm();

		return Instant.ofEpochMilli(xmlGregorianCalendar.toGregorianCalendar().getTimeInMillis())
				.atZone(timeZone);
	}

	@NonNull
	private BigDecimal getBeginningBalance()
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

	private OrgId getOrgId(@NonNull final BankAccountId bankAccountId)
	{
		return bankAccountService.getById(bankAccountId).getOrgId();
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
