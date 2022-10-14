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
import com.google.common.collect.ImmutableList;
import de.metas.banking.BankStatementId;
import de.metas.banking.jaxb.camt053_001_02.ActiveOrHistoricCurrencyAndAmount;
import de.metas.banking.jaxb.camt053_001_02.AmountAndCurrencyExchange3;
import de.metas.banking.jaxb.camt053_001_02.AmountAndCurrencyExchangeDetails3;
import de.metas.banking.jaxb.camt053_001_02.CurrencyExchange5;
import de.metas.banking.jaxb.camt053_001_02.DateAndDateTimeChoice;
import de.metas.banking.jaxb.camt053_001_02.EntryDetails1;
import de.metas.banking.jaxb.camt053_001_02.EntryTransaction2;
import de.metas.banking.jaxb.camt053_001_02.RemittanceInformation5;
import de.metas.banking.jaxb.camt053_001_02.ReportEntry2;
import de.metas.banking.jaxb.camt053_001_02.TransactionInterest2;
import de.metas.banking.service.BankStatementLineCreateRequest;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.banking.model.I_C_Invoice;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.metas.banking.jaxb.camt053_001_02.CreditDebitCode.CRDT;

@Value
public class NoBatchReportEntry2Wrapper
{
	private static final Logger logger = LogManager.getLogger(NoBatchReportEntry2Wrapper.class);

	@NonNull
	IOrgDAO orgDAO;

	@NonNull
	IInvoiceDAO invoiceDAO;

	@NonNull
	ReportEntry2 entry;

	@NonNull
	CurrencyRepository currencyRepository;

	@Builder
	private NoBatchReportEntry2Wrapper(
			@NonNull final IOrgDAO orgDAO,
			@NonNull final IInvoiceDAO invoiceDAO,
			@NonNull final ReportEntry2 entry,
			@NonNull final CurrencyRepository currencyRepository)
	{
		Check.assume(Check.isEmpty(entry.getNtryDtls()) || entry.getNtryDtls().size() == 1, "Batched transactions are not supported!");

		this.orgDAO = orgDAO;
		this.invoiceDAO = invoiceDAO;
		this.entry = entry;
		this.currencyRepository = currencyRepository;
	}

	@NonNull
	public Optional<BankStatementLineCreateRequest> buildBankStatementLineCreateRequest(
			@NonNull final BankStatementId bankStatementId,
			@NonNull final OrgId orgId)
	{
		final Timestamp statementLineDate = getStatementLineDate().orElse(null);

		if (statementLineDate == null)
		{
			Loggables.withLogger(logger, Level.INFO).addLog(
					"Skipping this ReportEntry={} because StatementLineDate is missing! BankStatementId={}", entry, bankStatementId);
			return Optional.empty();
		}

		final Money stmtAmount = getStatementAmount();
		
		final BankStatementLineCreateRequest.BankStatementLineCreateRequestBuilder bankStatementLineCreateRequestBuilder = BankStatementLineCreateRequest.builder()
				.orgId(orgId)
				.bankStatementId(bankStatementId)
				.lineDescription(getLineDescription())
				.statementAmt(stmtAmount)
				.trxAmt(stmtAmount)
				.currencyRate(getCurrencyRate().orElse(null))
				.interestAmt(getInterestAmount().orElse(null))
				.statementLineDate(TimeUtil.asLocalDate(statementLineDate, orgDAO.getTimeZone(orgId)));

		getInvoiceCheckingAmounts(orgId, stmtAmount)
				.ifPresent(invoice -> bankStatementLineCreateRequestBuilder
						.invoiceId(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
						.bpartnerId(BPartnerId.ofRepoId(invoice.getC_BPartner_ID())));

		return Optional.of(bankStatementLineCreateRequestBuilder.build());
	}

	@NonNull
	private Optional<I_C_Invoice> getInvoiceCheckingAmounts(
			@NonNull final OrgId orgId,
			@NonNull final Money stmtAmount)
	{
		return getReferencedInvoiceRecord(orgId)
				.filter(invoiceRecord -> invoiceAmountMatchesStmtAmount(invoiceRecord, stmtAmount));
	}

	@NonNull
	private Optional<I_C_Invoice> getReferencedInvoiceRecord(@NonNull final OrgId orgId)
	{
		return getRemittanceUnstructuredInfo()
				.map(invoiceDocNo -> invoiceDAO.getByDocumentNo(invoiceDocNo, orgId, I_C_Invoice.class))
				.flatMap(this::getSingleInvoice);
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
	public Optional<String> getRemittanceUnstructuredInfo()
	{
		final List<String> remittanceInfoLines = getEntryTransaction()
				.map(EntryTransaction2::getRmtInf)
				.map(RemittanceInformation5::getUstrd)
				.orElseGet(ImmutableList::of);

		final String joinedRemittanceLines = String.join("\n", remittanceInfoLines);

		return Optional.of(joinedRemittanceLines)
				.filter(Check::isNotBlank);
	}

	@NonNull
	public Optional<Timestamp> getStatementLineDate()
	{
		return Optional.ofNullable(entry.getValDt())
				.map(DateAndDateTimeChoice::getDt)
				.map(xmlGregorianCalendar -> new Timestamp(xmlGregorianCalendar.toGregorianCalendar().getTimeInMillis()));
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
	public Optional<I_C_Invoice> getSingleInvoice(@NonNull final List<I_C_Invoice> invoiceList)
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
					"Multiple invoices found for ReportEntry2={}! MatchedInvoicedIds={}", entry.getNtryRef(), matchedInvoiceIds);
			
			return Optional.empty();
		}
		else
		{
			return Optional.of(invoiceList.get(0));
		}
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

	private boolean invoiceAmountMatchesStmtAmount(
			@NonNull final I_C_Invoice invoiceRecord,
			@NonNull final Money stmtAmount)
	{
		if (invoiceRecord.getC_Currency_ID() != stmtAmount.getCurrencyId().getRepoId())
		{
			Loggables.withLogger(logger, Level.INFO).addLog(
					"Skipping invoice due to currency! InvoiceId={}, StmtCurrency={}, InvoiceCurrency={}",
					invoiceRecord.getC_Invoice_ID(), stmtAmount.getCurrencyId(), invoiceRecord.getC_Currency_ID());
			
			return false;
		}
		
		final BigDecimal invoiceAmount = NumberUtils.stripTrailingDecimalZeros(invoiceRecord.getGrandTotal());
		final BigDecimal stmtAmountAbs = NumberUtils.stripTrailingDecimalZeros(stmtAmount.toBigDecimal()).abs();

		final boolean invoiceAmountMatchesStmtAmount = invoiceAmount.equals(stmtAmountAbs);
		if (!invoiceAmountMatchesStmtAmount)
		{
			Loggables.withLogger(logger, Level.INFO).addLog(
					"Skipping invoice due to amounts not matching! InvoiceId={}, StmtCurrency={}, InvoiceCurrency={}",
					invoiceRecord.getC_Invoice_ID(), stmtAmount, invoiceRecord.getGrandTotal());
		}

		return invoiceAmountMatchesStmtAmount;
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
