package de.metas.ui.web.bankstatement_reconciliation;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;

import de.metas.allocation.api.IAllocationDAO;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.service.IBankStatementBL;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.money.CurrencyId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.GuavaCollectors;
import de.metas.util.ImmutableMapEntry;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Repository
public class BankStatementLineAndPaymentsToReconcileRepository
{
	private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
	private final IBankStatementBL bankStatementBL;
	private final CurrencyRepository currencyRepository;
	private LookupDataSource bpartnerLookup;

	public BankStatementLineAndPaymentsToReconcileRepository(
			@NonNull final IBankStatementBL bankStatementBL,
			@NonNull final CurrencyRepository currencyRepository)
	{
		this.bankStatementBL = bankStatementBL;
		this.currencyRepository = currencyRepository;

		if (!Adempiere.isUnitTestMode()) // FIXME: workaround to be able to test it
		{
			bpartnerLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_C_BPartner.Table_Name);
		}
		else
		{
			bpartnerLookup = null;
		}
	}

	@VisibleForTesting
	public void setBpartnerLookup(@NonNull final LookupDataSource bpartnerLookup)
	{
		Adempiere.assertUnitTestMode();
		this.bpartnerLookup = bpartnerLookup;
	}

	public List<BankStatementLineRow> getBankStatementLineRowsByIds(final Set<BankStatementLineId> ids)
	{
		if (ids.isEmpty())
		{
			return ImmutableList.of();
		}

		return bankStatementBL.getLinesByIds(ids)
				.stream()
				.map(this::toBankStatementLineRow)
				.collect(ImmutableList.toImmutableList());
	}

	private BankStatementLineRow toBankStatementLineRow(final I_C_BankStatementLine record)
	{
		final Amount statementLineAmt = extractStatementLineAmt(record);

		return BankStatementLineRow.builder()
				.bankStatementLineId(BankStatementLineId.ofRepoId(record.getC_BankStatementLine_ID()))
				.lineNo(record.getLine())
				.dateAcct(TimeUtil.asLocalDate(record.getDateAcct()))
				.statementLineAmt(statementLineAmt)
				.description(record.getDescription())
				.reconciled(record.isReconciled())
				.build();
	}

	private Amount extractStatementLineAmt(final I_C_BankStatementLine record)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());
		final CurrencyCode currencyCode = currencyRepository.getCurrencyCodeById(currencyId);
		final Amount statementLineAmt = Amount.of(record.getStmtAmt(), currencyCode);
		return statementLineAmt;
	}

	public List<PaymentToReconcileRow> getPaymentToReconcileRowsByIds(final Set<PaymentId> paymentIds)
	{
		final ImmutableSetMultimap<PaymentId, String> invoiceDocumentNosByPaymentId = getInvoiceDocumentNosByPaymentId(paymentIds);

		return paymentDAO.getByIds(paymentIds)
				.stream()
				.map(record -> toPaymentToReconcileRow(record, invoiceDocumentNosByPaymentId))
				.collect(ImmutableList.toImmutableList());
	}

	private ImmutableSetMultimap<PaymentId, String> getInvoiceDocumentNosByPaymentId(final Set<PaymentId> paymentIds)
	{
		final SetMultimap<PaymentId, InvoiceId> invoiceIdsByPaymentId = allocationDAO.retrieveInvoiceIdsByPaymentIds(paymentIds);
		final ImmutableMap<InvoiceId, String> invoiceDocumentNos = invoiceDAO.getDocumentNosByInvoiceIds(invoiceIdsByPaymentId.values());

		return invoiceIdsByPaymentId.entries()
				.stream()
				.map(GuavaCollectors.mapValue(invoiceDocumentNos::get))
				.filter(ImmutableMapEntry::isValueNotNull)
				.collect(GuavaCollectors.toImmutableSetMultimap());
	}

	private PaymentToReconcileRow toPaymentToReconcileRow(
			@NonNull final I_C_Payment record,
			@NonNull final ImmutableSetMultimap<PaymentId, String> invoiceDocumentNosByPaymentId)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());
		final CurrencyCode currencyCode = currencyRepository.getCurrencyCodeById(currencyId);
		final Amount payAmt = Amount.of(record.getPayAmt(), currencyCode);

		final PaymentId paymentId = PaymentId.ofRepoId(record.getC_Payment_ID());
		String invoiceDocumentNos = joinInvoiceDocumentNos(invoiceDocumentNosByPaymentId.get(paymentId));

		return PaymentToReconcileRow.builder()
				.paymentId(paymentId)
				.inboundPayment(record.isReceipt())
				.documentNo(record.getDocumentNo())
				.dateTrx(TimeUtil.asLocalDate(record.getDateTrx()))
				.bpartner(bpartnerLookup.findById(record.getC_BPartner_ID()))
				.invoiceDocumentNos(invoiceDocumentNos)
				.payAmt(payAmt)
				.reconciled(record.isReconciled())
				.build();
	}

	private static String joinInvoiceDocumentNos(final Collection<String> documentNos)
	{
		if (documentNos == null || documentNos.isEmpty())
		{
			return "";
		}

		return documentNos.stream()
				.map(StringUtils::trimBlankToNull)
				.filter(Objects::nonNull)
				.collect(Collectors.joining(", "));
	}
}
