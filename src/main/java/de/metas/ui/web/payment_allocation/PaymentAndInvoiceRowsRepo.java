package de.metas.ui.web.payment_allocation;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.banking.payment.paymentallocation.InvoiceToAllocate;
import de.metas.banking.payment.paymentallocation.InvoiceToAllocateQuery;
import de.metas.banking.payment.paymentallocation.InvoiceToAllocateQuery.InvoiceToAllocateQueryBuilder;
import de.metas.banking.payment.paymentallocation.PaymentAllocationRepository;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentBL;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class PaymentAndInvoiceRowsRepo
{
	private final IPaymentBL paymentsService = Services.get(IPaymentBL.class);
	private final ICurrencyDAO currenciesRepo = Services.get(ICurrencyDAO.class);
	private final PaymentAllocationRepository paymentAllocationRepo;
	private final LookupDataSource bpartnersLookup;

	public PaymentAndInvoiceRowsRepo(
			@NonNull final PaymentAllocationRepository paymentAllocationRepo)
	{
		this.paymentAllocationRepo = paymentAllocationRepo;
		bpartnersLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_C_BPartner.Table_Name);
	}

	public PaymentAndInvoiceRows getByPaymentIds(@NonNull final Set<PaymentId> paymentIds)
	{
		Check.assumeNotEmpty(paymentIds, "paymentIds is not empty");

		final List<I_C_Payment> paymentRecords = paymentsService.getByIds(paymentIds);

		final PaymentRows paymentRows = toPaymentRows(paymentRecords);
		final InvoiceRows invoiceRows = retrieveInvoiceRows(paymentRecords);

		return PaymentAndInvoiceRows.builder()
				.paymentRows(paymentRows)
				.invoiceRows(invoiceRows)
				.build();
	}

	private PaymentRows toPaymentRows(final List<I_C_Payment> paymentRecords)
	{
		final ImmutableList<PaymentRow> rows = paymentRecords
				.stream()
				.filter(this::isEligible)
				.map(this::toPaymentRow)
				.collect(ImmutableList.toImmutableList());

		return PaymentRows.builder()
				.rows(rows)
				.build();
	}

	private boolean isEligible(final I_C_Payment record)
	{
		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(record.getDocStatus());
		return docStatus.isCompleted();
	}

	private PaymentRow toPaymentRow(final I_C_Payment record)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(record.getC_BPartner_ID());
		final CurrencyCode currencyCode = currenciesRepo.getCurrencyCodeById(currencyId);

		Amount.of(record.getPayAmt(), currencyCode);

		return PaymentRow.builder()
				.paymentId(PaymentId.ofRepoId(record.getC_Payment_ID()))
				.documentNo(record.getDocumentNo())
				.bpartner(bpartnersLookup.findById(bpartnerId))
				.dateTrx(TimeUtil.asLocalDate(record.getDateTrx()))
				.amount(Amount.of(record.getPayAmt(), currencyCode))
				.build();
	}

	private InvoiceRows retrieveInvoiceRows(final List<I_C_Payment> paymentRecords)
	{
		final ZonedDateTime date = SystemTime.asZonedDateTime();
		final ImmutableSet<InvoiceToAllocateQuery> queries = paymentRecords.stream()
				.map(paymentRecord -> prepareInvoiceToAllocateQuery(paymentRecord)
						.date(date)
						.build())
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableList<InvoiceRow> rows = paymentAllocationRepo.retrieveInvoicesToAllocate(queries)
				.stream()
				.map(this::toInvoiceRow)
				.collect(ImmutableList.toImmutableList());

		return InvoiceRows.builder()
				.rows(rows)
				.build();
	}

	private InvoiceToAllocateQueryBuilder prepareInvoiceToAllocateQuery(final I_C_Payment paymentRecord)
	{
		return InvoiceToAllocateQuery.builder()
				.bpartnerId(BPartnerId.ofRepoId(paymentRecord.getC_BPartner_ID()))
				.currencyId(CurrencyId.ofRepoId(paymentRecord.getC_Currency_ID()))
				.adClientId(ClientId.ofRepoId(paymentRecord.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(paymentRecord.getAD_Org_ID()));
	}

	private InvoiceRow toInvoiceRow(final InvoiceToAllocate invoiceToAllocate)
	{
		return InvoiceRow.builder()
				.invoiceId(invoiceToAllocate.getInvoiceId())
				.documentNo(invoiceToAllocate.getDocumentNo())
				.dateInvoiced(invoiceToAllocate.getDateInvoiced())
				.bpartner(bpartnersLookup.findById(invoiceToAllocate.getBpartnerId()))
				.grandTotal(invoiceToAllocate.getGrandTotal())
				.openAmt(invoiceToAllocate.getOpenAmountConverted())
				.discountAmt(invoiceToAllocate.getDiscountAmountConverted())
				.build();
	}
}
