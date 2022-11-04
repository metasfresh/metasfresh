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
import de.metas.banking.BankAccountId;
import de.metas.banking.BankId;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.camt53.jaxb.camt053_001_02.AccountStatement2;
import de.metas.banking.camt53.jaxb.camt053_001_02.BalanceType12Code;
import de.metas.banking.camt53.jaxb.camt053_001_02.CashBalance3;
import de.metas.banking.camt53.jaxb.camt053_001_02.GenericAccountIdentification1;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.slf4j.Logger;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static de.metas.banking.camt53.jaxb.camt053_001_02.CreditDebitCode.CRDT;

@Value
public class AccountStatement2Wrapper
{
	private static final Logger logger = LogManager.getLogger(AccountStatement2Wrapper.class);

	private static final AdMessageKey MSG_MISSING_MF_BANK_ACCT_WITH_IBAN = AdMessageKey.of("de.metas.banking.camt53.BankStatementCamt53Service.MissingMetasfreshBankAcctWithIBAN");
	private static final AdMessageKey MSG_MISSING_BANK_STMT_SWIFT_CODE = AdMessageKey.of("de.metas.banking.camt53.BankStatementCamt53Service.MissingBankStatementSwiftCode");
	private static final AdMessageKey MSG_MISSING_MF_BANK_ACCT_WITH_SWIFT_CODE = AdMessageKey.of("de.metas.banking.camt53.BankStatementCamt53Service.MissingMetasfreshBankAcctWithSwiftCode");
	private static final AdMessageKey MSG_MISSING_MF_BANK_ACCT_WITH_ACCOUNT_NO = AdMessageKey.of("de.metas.banking.camt53.BankStatementCamt53Service.MissingMetasfreshBankAcctWithAccountNo");
	private static final AdMessageKey MSG_MISSING_BANK_STMT_ACCOUNT_NO = AdMessageKey.of("de.metas.banking.camt53.BankStatementCamt53Service.MissingBankStatementAccountNo");

	@NonNull
	IOrgDAO orgDAO;

	@NonNull
	AccountStatement2 accountStatement2;

	@NonNull
	BankAccountService bankAccountService;

	@NonNull
	CurrencyRepository currencyRepository;

	@NonNull
	IMsgBL msgBL;

	@Builder
	private AccountStatement2Wrapper(
			@NonNull final IOrgDAO orgDAO,
			@NonNull final AccountStatement2 accountStatement2,
			@NonNull final BankAccountService bankAccountService,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final IMsgBL msgBL)
	{
		this.orgDAO = orgDAO;
		this.accountStatement2 = accountStatement2;
		this.bankAccountService = bankAccountService;
		this.currencyRepository = currencyRepository;
		this.msgBL = msgBL;
	}

	@NonNull
	public ExplainedOptional<BankAccountId> getBPartnerBankAccountId()
	{
		final Optional<String> accountIBANOpt = getAccountIBAN();
		if (accountIBANOpt.isPresent())
		{
			final String iban = accountIBANOpt.get();
			final Optional<BankAccountId> bankAccountIdByIBAN = bankAccountService.getBankAccountIdByIBAN(iban);
			return bankAccountIdByIBAN
					.map(ExplainedOptional::of)
					.orElseGet(() -> {
						final String msg = getMsg(MSG_MISSING_MF_BANK_ACCT_WITH_IBAN, accountStatement2.getId(), iban);
						return ExplainedOptional.emptyBecause(msg);
					});
		}

		final ExplainedOptional<BankId> bankIdOpt = getBankId();
		if (!bankIdOpt.isPresent())
		{
			return ExplainedOptional.emptyBecause(bankIdOpt.getExplanation());
		}

		final Optional<String> accountNoOpt = getAccountNo();
		return accountNoOpt.map(accountNoCandidate -> bankAccountService.getBankAccountId(bankIdOpt.get(), accountNoCandidate)
				.map(ExplainedOptional::of)
				.orElseGet(() -> {
					final String msg = getMsg(MSG_MISSING_MF_BANK_ACCT_WITH_ACCOUNT_NO, accountStatement2.getId(), bankIdOpt.get().getRepoId(), accountNoCandidate);
					return ExplainedOptional.emptyBecause(msg);
				}))
				.orElseGet(() -> {
					final String msg = getMsg(MSG_MISSING_BANK_STMT_ACCOUNT_NO, accountStatement2.getId());
					return ExplainedOptional.emptyBecause(msg);
				});
	}

	@NonNull
	public ZonedDateTime getStatementDate(@NonNull final ZoneId timeZone)
	{
		final XMLGregorianCalendar xmlGregorianCalendar = accountStatement2.getCreDtTm();

		return Instant.ofEpochMilli(xmlGregorianCalendar.toGregorianCalendar().getTimeInMillis())
				.atZone(timeZone);
	}

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

	public OrgId getOrgId(@NonNull final BankAccountId bankAccountId)
	{
		return bankAccountService.getById(bankAccountId).getOrgId();
	}

	@NonNull
	public Optional<CurrencyCode> getStatementCurrencyCode()
	{
		return Optional.ofNullable(accountStatement2.getAcct().getCcy())
				.map(CurrencyCode::ofThreeLetterCode);
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

	@NonNull
	private ExplainedOptional<BankId> getBankId()
	{
		if (!getSwiftCode().isPresent())
		{
			final String msg = getMsg(MSG_MISSING_BANK_STMT_SWIFT_CODE, accountStatement2.getId());
			return ExplainedOptional.emptyBecause(msg);
		}
		final String swiftCode = getSwiftCode().get();

		return bankAccountService.getBankIdBySwiftCode(swiftCode)
				.map(ExplainedOptional::of)
				.orElseGet(() -> {
					final String msg = getMsg(MSG_MISSING_MF_BANK_ACCT_WITH_SWIFT_CODE, accountStatement2.getId(), swiftCode);
					return ExplainedOptional.emptyBecause(msg);
				});
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

	@NonNull
	private Optional<String> getAccountIBAN()
	{
		return Optional.ofNullable(accountStatement2.getAcct().getId().getIBAN());
	}

	@NonNull
	private Optional<String> getSwiftCode()
	{
		return Optional.ofNullable(accountStatement2.getAcct().getSvcr())
				.map(branchAndFinancialInstitutionIdentification4 -> branchAndFinancialInstitutionIdentification4.getFinInstnId().getBIC())
				.filter(Check::isNotBlank);
	}

	@NonNull
	private Optional<String> getAccountNo()
	{
		return Optional.ofNullable(accountStatement2.getAcct().getId().getOthr())
				.map(GenericAccountIdentification1::getId);
	}

	@NonNull
	private String getMsg(@NonNull final AdMessageKey adMessageKey, final Object ...params)
	{
		return msgBL.getMsg(adMessageKey, ImmutableList.copyOf(params));
	}
}
