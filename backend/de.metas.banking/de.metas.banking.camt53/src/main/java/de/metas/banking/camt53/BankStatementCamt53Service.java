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

package de.metas.banking.camt53;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankStatementId;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.camt53.jaxb.camt053_001_02.AccountStatement2;
import de.metas.banking.camt53.jaxb.camt053_001_02.BankToCustomerStatementV02;
import de.metas.banking.camt53.jaxb.camt053_001_02.Document;
import de.metas.banking.camt53.jaxb.camt053_001_02.ObjectFactory;
import de.metas.banking.camt53.jaxb.camt053_001_02.ReportEntry2;
import de.metas.banking.camt53.wrapper.AccountStatement2Wrapper;
import de.metas.banking.camt53.wrapper.NoBatchBankToCustomerStatementV02Wrapper;
import de.metas.banking.camt53.wrapper.NoBatchReportEntry2Wrapper;
import de.metas.banking.importfile.BankStatementImportFileId;
import de.metas.banking.importfile.log.BankStatementImportFileLogRepository;
import de.metas.banking.importfile.log.BankStatementImportFileLoggable;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationService;
import de.metas.banking.service.BankStatementCreateRequest;
import de.metas.banking.service.BankStatementLineCreateRequest;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.UnpaidInvoiceMatchingAmtQuery;
import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BankStatementCamt53Service
{
	private static final AdMessageKey MSG_MISSING_BANK_STATEMENT_LINES = AdMessageKey.of("de.metas.banking.camt53.BankStatementCamt53Service.MissingBankStatementLines");
	private static final AdMessageKey MSG_SKIPPED_STMT_NO_MATCHING_CURRENCY = AdMessageKey.of("de.metas.banking.camt53.BankStatementCamt53Service.SkippedStmtNoMatchingCurrency");
	private static final AdMessageKey MSG_MISSING_REPORT_ENTRY_DATE = AdMessageKey.of("de.metas.banking.camt53.BankStatementCamt53Service.MissingReportEntryDate");

	private static final Logger logger = LogManager.getLogger(BankStatementCamt53Service.class);

	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final BankAccountService bankAccountService;
	private final CurrencyRepository currencyRepository;
	private final PaymentAllocationService paymentAllocationService;
	private final MoneyService moneyService;
	private final BankStatementImportFileLogRepository bankStatementImportFileLogRepository;

	public BankStatementCamt53Service(
			@NonNull final BankAccountService bankAccountService,
			@NonNull final CurrencyRepository currencyRepository,
			@NonNull final PaymentAllocationService paymentAllocationService,
			@NonNull final MoneyService moneyService,
			@NonNull final BankStatementImportFileLogRepository bankStatementImportFileLogRepository)
	{
		this.bankAccountService = bankAccountService;
		this.currencyRepository = currencyRepository;
		this.paymentAllocationService = paymentAllocationService;
		this.moneyService = moneyService;
		this.bankStatementImportFileLogRepository = bankStatementImportFileLogRepository;
	}

	@NonNull
	public ImmutableSet<BankStatementId> importBankToCustomerStatement(@NonNull final ImportBankStatementRequest importBankStatementRequest)
	{
		final BankStatementImportFileLoggable bankStatementImportFileLoggable = createLogger(importBankStatementRequest.getBankStatementImportFileId());

		try (final IAutoCloseable ignored = Loggables.temporarySetLoggable(bankStatementImportFileLoggable))
		{
			final NoBatchBankToCustomerStatementV02Wrapper noBatchBankToCustomerStatementV02Wrapper = NoBatchBankToCustomerStatementV02Wrapper
					.of(loadCamt53Document(importBankStatementRequest.getCamt53File()));

			return noBatchBankToCustomerStatementV02Wrapper
					.getAccountStatements()
					.stream()
					.map(accountStatement2 -> importBankStatement(accountStatement2, importBankStatementRequest))
					.filter(Optional::isPresent)
					.map(Optional::get)
					.collect(ImmutableSet.toImmutableSet());
		}
	}

	@NonNull
	private Optional<BankStatementId> importBankStatement(
			@NonNull final AccountStatement2 accountStatement2,
			@NonNull final ImportBankStatementRequest importBankStatementRequest)
	{
		if (accountStatement2.getNtry().isEmpty())
		{
			final String msg = msgBL.getMsg(MSG_MISSING_BANK_STATEMENT_LINES, ImmutableList.of(accountStatement2.getId()));
			Loggables.withLogger(logger, Level.DEBUG).addLog(msg);

			return Optional.empty();
		}

		final AccountStatement2Wrapper accountStatement2Wrapper = buildAccountStatement2Wrapper(accountStatement2);

		final BankStatementCreateRequest bankStatementCreateRequest = buildBankStatementCreateRequest(
				accountStatement2Wrapper,
				importBankStatementRequest.getBankStatementImportFileId())
				.orElse(null);

		if (bankStatementCreateRequest == null)
		{
			return Optional.empty();
		}

		final BankStatementId bankStatementId = bankStatementDAO.createBankStatement(bankStatementCreateRequest);

		Loggables.withLogger(logger, Level.DEBUG).addLog(
				"One bank statement with id={} created for BankStatementCreateRequest={}", bankStatementId, bankStatementCreateRequest);

		final Function<NoBatchReportEntry2Wrapper, ImportBankStatementLineRequest> getImportBankStatementLineRequest = entry -> ImportBankStatementLineRequest.builder()
				.entryWrapper(entry)
				.bankStatementId(bankStatementId)
				.orgId(bankStatementCreateRequest.getOrgId())
				.isMatchAmounts(importBankStatementRequest.isMatchAmounts())
				.build();

		accountStatement2.getNtry()
				.stream()
				.map(this::buildNoBatchReportEntry2Wrapper)
				.forEach(entry -> importBankStatementLine(getImportBankStatementLineRequest.apply(entry)));

		return Optional.of(bankStatementId);
	}

	@NonNull
	private Optional<BankStatementCreateRequest> buildBankStatementCreateRequest(
			@NonNull final AccountStatement2Wrapper accountStatement2Wrapper,
			@NonNull final BankStatementImportFileId bankStatementImportFileId)
	{
		final ExplainedOptional<BankAccountId> bankAccountIdOpt = accountStatement2Wrapper.getBPartnerBankAccountId();

		if (!bankAccountIdOpt.isPresent())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog(bankAccountIdOpt.getExplanation().getDefaultValue());

			return Optional.empty();
		}

		final BankAccountId bankAccountId = bankAccountIdOpt.get();

		if (!isStatementCurrencyMatchingBankAccount(bankAccountId, accountStatement2Wrapper))
		{
			final String msg = msgBL.getMsg(MSG_SKIPPED_STMT_NO_MATCHING_CURRENCY, ImmutableList.of(accountStatement2Wrapper.getAccountStatement2().getId()));
			Loggables.withLogger(logger, Level.WARN).addLog(msg);

			return Optional.empty();
		}

		final BigDecimal beginningBalance = accountStatement2Wrapper.getBeginningBalance();
		final OrgId orgId = accountStatement2Wrapper.getOrgId(bankAccountId);
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		final ZonedDateTime statementDate = accountStatement2Wrapper.getStatementDate(timeZone);

		return Optional.of(BankStatementCreateRequest.builder()
								   .orgId(orgId)
								   .orgBankAccountId(bankAccountId)
								   .statementDate(TimeUtil.asLocalDate(statementDate, timeZone))
								   .name(statementDate.toString())
								   .beginningBalance(beginningBalance)
								   .bankStatementImportFileId(bankStatementImportFileId)
								   .build());
	}

	private void importBankStatementLine(@NonNull final ImportBankStatementLineRequest importBankStatementLineRequest)
	{
		buildBankStatementLineCreateRequest(importBankStatementLineRequest)
				.ifPresent(bankStatementDAO::createBankStatementLine);
	}

	@NonNull
	private Optional<BankStatementLineCreateRequest> buildBankStatementLineCreateRequest(@NonNull final ImportBankStatementLineRequest importBankStatementLineRequest)
	{
		final NoBatchReportEntry2Wrapper entryWrapper = importBankStatementLineRequest.getEntryWrapper();
		final OrgId orgId = importBankStatementLineRequest.getOrgId();

		final ZonedDateTime statementLineDate = entryWrapper.getStatementLineDate(orgDAO.getTimeZone(orgId))
				.map(instant -> instant.atZone(orgDAO.getTimeZone(orgId)))
				.orElse(null);

		if (statementLineDate == null)
		{
			final String msg = msgBL.getMsg(MSG_MISSING_REPORT_ENTRY_DATE, ImmutableList.of(entryWrapper.getEntry().getNtryRef(), importBankStatementLineRequest.getBankStatementId()));
			Loggables.withLogger(logger, Level.INFO).addLog(msg);

			return Optional.empty();
		}

		final Money stmtAmount = entryWrapper.getStatementAmount().toMoney(moneyService::getCurrencyIdByCurrencyCode);

		final BankStatementLineCreateRequest.BankStatementLineCreateRequestBuilder bankStatementLineCreateRequestBuilder = BankStatementLineCreateRequest.builder()
				.orgId(orgId)
				.bankStatementId(importBankStatementLineRequest.getBankStatementId())
				.lineDescription(entryWrapper.getLineDescription())
				.memo(entryWrapper.getUnstructuredRemittanceInfo())
				.referenceNo(entryWrapper.getAcctSvcrRef())
				.updateAmountsFromInvoice(false) // don't change the amounts; they are coming from the bank
				.statementAmt(stmtAmount)
				.trxAmt(stmtAmount)
				.currencyRate(entryWrapper.getCurrencyRate().orElse(null))
				.interestAmt(entryWrapper.getInterestAmount().orElse(null))
				.statementLineDate(statementLineDate.toLocalDate());

		if (entryWrapper.isCRDT())
		{ // if this is CREDIT (i.e. we get money), then we are interested in the name of the debitor from whom we the money is coming
			bankStatementLineCreateRequestBuilder.importedBillPartnerName(entryWrapper.getDbtrNames());
		}
		else
		{
			bankStatementLineCreateRequestBuilder.importedBillPartnerName(entryWrapper.getCdtrNames());
		}

		getReferencedInvoiceRecord(importBankStatementLineRequest, statementLineDate)
				.ifPresent(invoice -> bankStatementLineCreateRequestBuilder
						.invoiceId(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
						.bpartnerId(BPartnerId.ofRepoId(invoice.getC_BPartner_ID())));

		return Optional.of(bankStatementLineCreateRequestBuilder.build());
	}

	@NonNull
	private Optional<I_C_Invoice> getReferencedInvoiceRecord(
			@NonNull final ImportBankStatementLineRequest importBankStatementLineRequest,
			@NonNull final ZonedDateTime statementLineDate)
	{
		final NoBatchReportEntry2Wrapper entryWrapper = importBankStatementLineRequest.getEntryWrapper();

		final ImmutableSet<String> invoiceDocNoCandidates = entryWrapper.getInvoiceDocNoCandidates();

		if (invoiceDocNoCandidates.isEmpty())
		{
			return Optional.empty();
		}

		final UnpaidInvoiceMatchingAmtQuery unpaidInvoiceMatchingAmtQuery = buildUnpaidInvoiceMatchingAmtQuery(statementLineDate, importBankStatementLineRequest);

		return Optional.of(paymentAllocationService.retrieveUnpaidInvoices(unpaidInvoiceMatchingAmtQuery))
				.flatMap(invoices -> getSingleInvoice(invoices, entryWrapper));
	}

	@NonNull
	private NoBatchReportEntry2Wrapper buildNoBatchReportEntry2Wrapper(@NonNull final ReportEntry2 reportEntry2)
	{
		return NoBatchReportEntry2Wrapper.builder()
				.currencyRepository(currencyRepository)
				.entry(reportEntry2)
				.build();
	}

	@NonNull
	private AccountStatement2Wrapper buildAccountStatement2Wrapper(@NonNull final AccountStatement2 accountStatement2)
	{
		return AccountStatement2Wrapper.builder()
				.accountStatement2(accountStatement2)
				.bankAccountService(bankAccountService)
				.currencyRepository(currencyRepository)
				.orgDAO(orgDAO)
				.msgBL(msgBL)
				.build();
	}

	private boolean isStatementCurrencyMatchingBankAccount(@NonNull final BankAccountId bankAccountId, @NonNull final AccountStatement2Wrapper statement2Wrapper)
	{
		final CurrencyCode statementCurrencyCode = statement2Wrapper.getStatementCurrencyCode().orElse(null);
		if (statementCurrencyCode == null)
		{
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
	private static BankToCustomerStatementV02 loadCamt53Document(@NonNull final InputStream dataInputStream)
	{
		try
		{
			final JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
			final Unmarshaller unmarshaller = context.createUnmarshaller();

			@SuppressWarnings("unchecked")
			final JAXBElement<Document> e = (JAXBElement<Document>)unmarshaller.unmarshal(getXMLStreamReader(dataInputStream));

			return e.getValue().getBkToCstmrStmt();
		}
		catch (final JAXBException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@NonNull
	private static XMLStreamReader getXMLStreamReader(@NonNull final InputStream data)
	{
		try
		{
			final XMLInputFactory xif = XMLInputFactory.newInstance();
			return xif.createXMLStreamReader(data);
		}
		catch (final XMLStreamException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@NonNull
	private static Optional<I_C_Invoice> getSingleInvoice(
			@NonNull final ImmutableList<I_C_Invoice> invoiceList,
			@NonNull final NoBatchReportEntry2Wrapper entryWrapper)
	{
		if (invoiceList.isEmpty())
		{
			return Optional.empty();
		}
		else if (invoiceList.size() > 1)
		{
			final String matchedInvoiceIds = invoiceList.stream()
					.map(org.compiere.model.I_C_Invoice::getC_Invoice_ID)
					.map(String::valueOf)
					.collect(Collectors.joining(","));

			Loggables.withLogger(logger, Level.WARN).addLog(
					"Multiple invoices found for ReportEntry2={}! MatchedInvoicedIds={}", entryWrapper.getEntry().getNtryRef(), matchedInvoiceIds);

			return Optional.empty();
		}
		else
		{
			return Optional.of(invoiceList.get(0));
		}
	}

	@NonNull
	private static UnpaidInvoiceMatchingAmtQuery buildUnpaidInvoiceMatchingAmtQuery(
			@NonNull final ZonedDateTime statementLineDate,
			@NonNull final ImportBankStatementLineRequest importBankStatementLineRequest)
	{
		final NoBatchReportEntry2Wrapper entryWrapper = importBankStatementLineRequest.getEntryWrapper();

		final UnpaidInvoiceMatchingAmtQuery.UnpaidInvoiceMatchingAmtQueryBuilder unpaidInvoiceMatchingAmtQueryBuilder = UnpaidInvoiceMatchingAmtQuery.builder()
				.onlyDocumentNos(entryWrapper.getInvoiceDocNoCandidates())
				.onlyDocStatuses(ImmutableSet.of(DocStatus.Completed, DocStatus.Closed))
				.queryLimit(QueryLimit.TWO);

		if (importBankStatementLineRequest.isMatchAmounts())
		{
			unpaidInvoiceMatchingAmtQueryBuilder
					.openAmountAtDate(entryWrapper.getStatementAmount())
					.openAmountEvaluationDate(statementLineDate);
		}

		return unpaidInvoiceMatchingAmtQueryBuilder.build();
	}

	@NonNull
	private BankStatementImportFileLoggable createLogger(@NonNull final BankStatementImportFileId id)
	{
		return BankStatementImportFileLoggable.builder()
				.bankStatementImportFileLogRepository(bankStatementImportFileLogRepository)
				.bankStatementImportFileId(id)
				.clientId(Env.getClientId())
				.userId(Env.getLoggedUserId())
				.bufferSize(100)
				.build();
	}

	@Value
	@Builder
	private static class ImportBankStatementLineRequest
	{
		@NonNull
		NoBatchReportEntry2Wrapper entryWrapper;

		@NonNull
		BankStatementId bankStatementId;

		@NonNull
		OrgId orgId;

		boolean isMatchAmounts;
	}
}
