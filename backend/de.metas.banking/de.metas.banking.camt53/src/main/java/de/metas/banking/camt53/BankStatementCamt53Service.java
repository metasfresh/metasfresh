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
import de.metas.attachments.AttachmentEntryDataResource;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankStatementId;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.camt53.wrapper.IAccountStatementWrapper;
import de.metas.banking.camt53.wrapper.IStatementLineWrapper;
import de.metas.banking.camt53.wrapper.v02.NoBatchBankToCustomerStatementV02Wrapper;
import de.metas.banking.camt53.wrapper.v04.NoBatchBankToCustomerStatementV04Wrapper;
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
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.IQuery;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
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
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

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
			return getAccountStatements(importBankStatementRequest.getCamt53File())
					.stream()
					.map(accountStatementWrapper -> importBankStatement(accountStatementWrapper, importBankStatementRequest))
					.filter(Optional::isPresent)
					.map(Optional::get)
					.collect(ImmutableSet.toImmutableSet());
		}
	}

	@NonNull
	private Optional<BankStatementId> importBankStatement(
			@NonNull final IAccountStatementWrapper accountStatementWrapper,
			@NonNull final ImportBankStatementRequest importBankStatementRequest)
	{
		if (accountStatementWrapper.hasNoBankStatementLines())
		{
			final String msg = msgBL.getMsg(MSG_MISSING_BANK_STATEMENT_LINES, ImmutableList.of(accountStatementWrapper.getId()));
			Loggables.withLogger(logger, Level.DEBUG).addLog(msg);

			return Optional.empty();
		}

		final BankStatementCreateRequest bankStatementCreateRequest = buildBankStatementCreateRequest(
				accountStatementWrapper,
				importBankStatementRequest.getBankStatementImportFileId())
				.orElse(null);

		if (bankStatementCreateRequest == null)
		{
			return Optional.empty();
		}

		final BankStatementId bankStatementId = bankStatementDAO.createBankStatement(bankStatementCreateRequest);

		Loggables.withLogger(logger, Level.DEBUG).addLog(
				"One bank statement with id={} created for BankStatementCreateRequest={}", bankStatementId, bankStatementCreateRequest);

		final Function<IStatementLineWrapper, ImportBankStatementLineRequest> getImportBankStatementLineRequest = entry -> ImportBankStatementLineRequest.builder()
				.entryWrapper(entry)
				.bankStatementId(bankStatementId)
				.orgId(bankStatementCreateRequest.getOrgId())
				.isMatchAmounts(importBankStatementRequest.isMatchAmounts())
				.build();

		accountStatementWrapper.getStatementLines()
				.forEach(entry -> importBankStatementLine(getImportBankStatementLineRequest.apply(entry)));

		return Optional.of(bankStatementId);
	}

	@NonNull
	private Optional<BankStatementCreateRequest> buildBankStatementCreateRequest(
			@NonNull final IAccountStatementWrapper accountStatementWrapper,
			@NonNull final BankStatementImportFileId bankStatementImportFileId)
	{
		final ExplainedOptional<BankAccountId> bankAccountIdOpt = accountStatementWrapper.getBPartnerBankAccountId();

		if (!bankAccountIdOpt.isPresent())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog(bankAccountIdOpt.getExplanation().getDefaultValue());

			return Optional.empty();
		}

		final BankAccountId bankAccountId = bankAccountIdOpt.get();

		if (!isStatementCurrencyMatchingBankAccount(bankAccountId, accountStatementWrapper))
		{
			final String msg = msgBL.getMsg(MSG_SKIPPED_STMT_NO_MATCHING_CURRENCY, ImmutableList.of(accountStatementWrapper.getId()));
			Loggables.withLogger(logger, Level.WARN).addLog(msg);

			return Optional.empty();
		}

		final BigDecimal beginningBalance = accountStatementWrapper.getBeginningBalance();
		final OrgId orgId = getOrgId(bankAccountId);
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		final ZonedDateTime statementDate = accountStatementWrapper.getStatementDate(timeZone);

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
		final IStatementLineWrapper entryWrapper = importBankStatementLineRequest.getEntryWrapper();
		final OrgId orgId = importBankStatementLineRequest.getOrgId();

		final ZonedDateTime statementLineDate = entryWrapper.getStatementLineDate(orgDAO.getTimeZone(orgId))
				.map(instant -> instant.atZone(orgDAO.getTimeZone(orgId)))
				.orElse(null);

		if (statementLineDate == null)
		{
			final String msg = msgBL.getMsg(MSG_MISSING_REPORT_ENTRY_DATE, ImmutableList.of(entryWrapper.getLineReference(), importBankStatementLineRequest.getBankStatementId()));
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
		final IStatementLineWrapper entryWrapper = importBankStatementLineRequest.getEntryWrapper();

		final ImmutableSet<String> documentReferenceCandidates = entryWrapper.getDocumentReferenceCandidates();

		if (documentReferenceCandidates.isEmpty())
		{
			return Optional.empty();
		}

		final UnpaidInvoiceMatchingAmtQuery unpaidInvoiceMatchingAmtQuery = buildUnpaidInvoiceMatchingAmtQuery(statementLineDate,
																											   importBankStatementLineRequest,
																											   documentReferenceCandidates);

		return Optional.of(paymentAllocationService.retrieveUnpaidInvoices(unpaidInvoiceMatchingAmtQuery))
				.flatMap(invoices -> getSingleInvoice(invoices, entryWrapper));
	}

	private boolean isStatementCurrencyMatchingBankAccount(@NonNull final BankAccountId bankAccountId, @NonNull final IAccountStatementWrapper statementWrapper)
	{
		final CurrencyCode statementCurrencyCode = statementWrapper.getStatementCurrencyCode().orElse(null);
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
	private ImmutableList<IAccountStatementWrapper> getAccountStatements(@NonNull final AttachmentEntryDataResource camt53File)
	{
		try
		{
			final Camt53Version camt53Version = getCamt53Version(camt53File.getInputStream())
					.orElseThrow(() -> new AdempiereException("Unsupported Camt53 version !"));

			final XMLStreamReader xmlStreamReader = getXMLStreamReader(camt53File.getInputStream());

			return switch (camt53Version)
					{
						case V02 -> getAccountStatementsV02(xmlStreamReader);
						case V04 -> getAccountStatementsV04(xmlStreamReader);
					};
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@NonNull
	private static Optional<Camt53Version> getCamt53Version(@NonNull final InputStream dataInputStream) throws XMLStreamException
	{
		final XMLStreamReader xmlStreamReader = getXMLStreamReader(dataInputStream);

		return XMLStreamConstants.START_ELEMENT == xmlStreamReader.next()
				? Optional.ofNullable(xmlStreamReader.getNamespaceURI())
				.map(namespaceURI -> Camt53Version.ofCode(namespaceURI.substring(namespaceURI.lastIndexOf(':') + 1)))
				: Optional.empty();
	}

	@NonNull
	private static XMLStreamReader getXMLStreamReader(@NonNull final InputStream data) throws XMLStreamException
	{
		final XMLInputFactory xif = XMLInputFactory.newInstance();
		return xif.createXMLStreamReader(data);
	}

	@NonNull
	private static Optional<I_C_Invoice> getSingleInvoice(
			@NonNull final ImmutableList<I_C_Invoice> invoiceList,
			@NonNull final IStatementLineWrapper entryWrapper)
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
					"Multiple invoices found for ReportEntry2={}! MatchedInvoicedIds={}", entryWrapper.getLineReference(), matchedInvoiceIds);

			return Optional.empty();
		}
		else
		{
			return Optional.of(invoiceList.get(0));
		}
	}

	@NonNull
	private UnpaidInvoiceMatchingAmtQuery buildUnpaidInvoiceMatchingAmtQuery(
			@NonNull final ZonedDateTime statementLineDate,
			@NonNull final ImportBankStatementLineRequest importBankStatementLineRequest,
			@NonNull final ImmutableSet<String> documentReferenceCandidates)
	{
		final IStatementLineWrapper entryWrapper = importBankStatementLineRequest.getEntryWrapper();

		final UnpaidInvoiceMatchingAmtQuery.UnpaidInvoiceMatchingAmtQueryBuilder unpaidInvoiceMatchingAmtQueryBuilder = UnpaidInvoiceMatchingAmtQuery.builder()
				.onlyDocStatuses(ImmutableSet.of(DocStatus.Completed, DocStatus.Closed))
				.queryLimit(QueryLimit.TWO);

		if (importBankStatementLineRequest.isMatchAmounts())
		{
			unpaidInvoiceMatchingAmtQueryBuilder
					.openAmountAtDate(entryWrapper.getStatementAmount())
					.openAmountEvaluationDate(statementLineDate);
		}

		return unpaidInvoiceMatchingAmtQueryBuilder
				.additionalFilter(getInvoiceDocOrEsrReferenceNoFilter(documentReferenceCandidates))
				.build();
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

	@NonNull
	private ICompositeQueryFilter<I_C_Invoice> getInvoiceDocOrEsrReferenceNoFilter(@NonNull final ImmutableSet<String> invoiceDocOrEsrRefNoCandidates)
	{
		final IQuery<I_ESR_ImportLine> esrImportLineQuery = queryBL
				.createQueryBuilder(I_ESR_ImportLine.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_ESR_ImportLine.COLUMNNAME_ESRReferenceNumber, invoiceDocOrEsrRefNoCandidates)
				.create();

		final ICompositeQueryFilter<I_C_Invoice> invoiceEsrReferenceNumberCandidatesFilter = queryBL
				.createCompositeQueryFilter(I_C_Invoice.class)
				.addInSubQueryFilter(I_C_Invoice.COLUMNNAME_C_Invoice_ID, I_ESR_ImportLine.COLUMNNAME_C_Invoice_ID, esrImportLineQuery);

		final ICompositeQueryFilter<I_C_Invoice> invoiceDocNumberCandidatesFilter = queryBL
				.createCompositeQueryFilter(I_C_Invoice.class)
				.addInArrayFilter(I_C_Invoice.COLUMNNAME_DocumentNo, invoiceDocOrEsrRefNoCandidates);

		return queryBL
				.createCompositeQueryFilter(I_C_Invoice.class)
				.setJoinOr()
				.addFilter(invoiceEsrReferenceNumberCandidatesFilter)
				.addFilter(invoiceDocNumberCandidatesFilter);
	}

	@NonNull
	private ImmutableList<IAccountStatementWrapper> getAccountStatementsV04(@NonNull final XMLStreamReader xmlStreamReader)
	{
		try
		{
			final JAXBContext context = JAXBContext.newInstance(de.metas.banking.camt53.jaxb.camt053_001_04.ObjectFactory.class);
			final Unmarshaller unmarshaller = context.createUnmarshaller();

			@SuppressWarnings("unchecked") final JAXBElement<de.metas.banking.camt53.jaxb.camt053_001_04.Document> docV04 =
					(JAXBElement<de.metas.banking.camt53.jaxb.camt053_001_04.Document>)unmarshaller.unmarshal(xmlStreamReader);

			return NoBatchBankToCustomerStatementV04Wrapper
					.of(docV04.getValue().getBkToCstmrStmt())
					.getAccountStatementWrappers(bankAccountService, currencyRepository, msgBL);
		}
		catch (final JAXBException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@NonNull
	private ImmutableList<IAccountStatementWrapper> getAccountStatementsV02(@NonNull final XMLStreamReader xmlStreamReader)
	{
		try
		{
			final JAXBContext context = JAXBContext.newInstance(de.metas.banking.camt53.jaxb.camt053_001_02.ObjectFactory.class);
			final Unmarshaller unmarshaller = context.createUnmarshaller();

			@SuppressWarnings("unchecked") final JAXBElement<de.metas.banking.camt53.jaxb.camt053_001_02.Document> docV02 =
					(JAXBElement<de.metas.banking.camt53.jaxb.camt053_001_02.Document>)unmarshaller.unmarshal(xmlStreamReader);

			return NoBatchBankToCustomerStatementV02Wrapper
					.of(docV02.getValue().getBkToCstmrStmt())
					.getAccountStatements(bankAccountService, currencyRepository, msgBL);
		}
		catch (final JAXBException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@NonNull
	private OrgId getOrgId(@NonNull final BankAccountId bankAccountId)
	{
		return bankAccountService.getById(bankAccountId).getOrgId();
	}

	@Value
	@Builder
	private static class ImportBankStatementLineRequest
	{
		@NonNull
		IStatementLineWrapper entryWrapper;

		@NonNull
		BankStatementId bankStatementId;

		@NonNull
		OrgId orgId;

		boolean isMatchAmounts;
	}
}
