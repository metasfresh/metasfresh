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

package de.metas.ui.web.payment_allocation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.banking.payment.paymentallocation.InvoiceToAllocate;
import de.metas.banking.payment.paymentallocation.InvoiceToAllocateQuery;
import de.metas.banking.payment.paymentallocation.InvoiceToAllocateQuery.InvoiceToAllocateQueryBuilder;
import de.metas.banking.payment.paymentallocation.PaymentAllocationRepository;
import de.metas.banking.payment.paymentallocation.PaymentToAllocate;
import de.metas.banking.payment.paymentallocation.PaymentToAllocateQuery;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.document.IDocTypeBL;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeCalculation;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeComputeRequest;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyService;
import de.metas.money.CurrencyId;
import de.metas.payment.PaymentId;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Check;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class PaymentAndInvoiceRowsRepo
{
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final CurrencyRepository currenciesRepo;
	private final PaymentAllocationRepository paymentAllocationRepo;
	private final LookupDataSource bpartnersLookup;
	private final InvoiceProcessingServiceCompanyService invoiceProcessorServiceCompanyService;

	public PaymentAndInvoiceRowsRepo(
			@NonNull final CurrencyRepository currenciesRepo,
			@NonNull final PaymentAllocationRepository paymentAllocationRepo,
			@NonNull final InvoiceProcessingServiceCompanyService invoiceProcessorServiceCompanyService)
	{
		this.currenciesRepo = currenciesRepo;
		this.paymentAllocationRepo = paymentAllocationRepo;
		this.invoiceProcessorServiceCompanyService = invoiceProcessorServiceCompanyService;
		bpartnersLookup = LookupDataSourceFactory.instance.searchInTableLookup(I_C_BPartner.Table_Name);
	}

	public PaymentAndInvoiceRows getByBPartnerId(@NonNull final BPartnerId bPartnerId)
	{
		final ZonedDateTime evaluationDate = SystemTime.asZonedDateTime();

		final List<PaymentToAllocate> paymentsToAllocate = paymentAllocationRepo.retrievePaymentsToAllocate(PaymentToAllocateQuery.builder()
				.bpartnerId(bPartnerId)
				.evaluationDate(evaluationDate)
				.build());

		final List<InvoiceToAllocate> invoicesToAllocate = paymentAllocationRepo.retrieveInvoicesToAllocate(InvoiceToAllocateQuery.builder()
				.bpartnerId(bPartnerId)
				.evaluationDate(evaluationDate)
				.build());

		final PaymentRows paymentRows = toPaymentRows(paymentsToAllocate, evaluationDate);
		final InvoiceRows invoiceRows = toInvoiceRows(invoicesToAllocate, evaluationDate);

		return PaymentAndInvoiceRows.builder()
				.paymentRows(paymentRows)
				.invoiceRows(invoiceRows)
				.build();
	}

	public PaymentAndInvoiceRows getByPaymentIds(@NonNull final Set<PaymentId> paymentIds)
	{
		Check.assumeNotEmpty(paymentIds, "paymentIds is not empty");

		final ZonedDateTime evaluationDate = de.metas.common.util.time.SystemTime.asZonedDateTime();

		final List<PaymentToAllocate> paymentsToAllocate = paymentAllocationRepo.retrievePaymentsToAllocate(PaymentToAllocateQuery.builder()
				.evaluationDate(evaluationDate)
				.additionalPaymentIdsToInclude(paymentIds)
				.build());
		if (paymentsToAllocate.isEmpty())
		{
			throw new AdempiereException("@NoOpenPayments@");
		}

		final PaymentRows paymentRows = toPaymentRows(paymentsToAllocate, evaluationDate);
		final InvoiceRows invoiceRows = retrieveInvoiceRowsByPayments(paymentsToAllocate, evaluationDate);

		return PaymentAndInvoiceRows.builder()
				.paymentRows(paymentRows)
				.invoiceRows(invoiceRows)
				.build();
	}

	private PaymentRows toPaymentRows(
			@NonNull final List<PaymentToAllocate> paymentsToAllocate,
			@NonNull final ZonedDateTime evaluationDate)
	{
		final ImmutableList<PaymentRow> rows = paymentsToAllocate
				.stream()
				.map(this::toPaymentRow)
				.collect(ImmutableList.toImmutableList());

		return PaymentRows.builder()
				.repository(this)
				.evaluationDate(evaluationDate)
				.initialRows(rows)
				.build();
	}

	private InvoiceRows toInvoiceRows(
			@NonNull final List<InvoiceToAllocate> invoicesToAllocate,
			@NonNull final ZonedDateTime evaluationDate)
	{
		return InvoiceRows.builder()
				.repository(this)
				.evaluationDate(evaluationDate)
				.initialRows(toInvoiceRowsList(invoicesToAllocate, evaluationDate))
				.build();
	}

	private ImmutableList<InvoiceRow> toInvoiceRowsList(final @NonNull List<InvoiceToAllocate> invoicesToAllocate, final @NonNull ZonedDateTime evaluationDate)
	{
		if(invoicesToAllocate.isEmpty())
		{
			return ImmutableList.of();
		}

		final InvoiceRowLoadingContext loadingContext = InvoiceRowLoadingContext.builder()
				.evaluationDate(evaluationDate)
				.invoiceIdsWithServiceInvoiceAlreadyGenetated(extractInvoiceIdsWithServiceInvoiceAlreadyGenerated(invoicesToAllocate))
				.build();

		return invoicesToAllocate.stream()
				.map(invoiceToAllocate -> toInvoiceRow(invoiceToAllocate, loadingContext))
				.collect(ImmutableList.toImmutableList());
	}

	private ImmutableSet<InvoiceId> extractInvoiceIdsWithServiceInvoiceAlreadyGenerated(final @NonNull List<InvoiceToAllocate> invoicesToAllocate)
	{
		final ImmutableSet<InvoiceId> invoiceIds = invoicesToAllocate.stream()
				.map(InvoiceToAllocate::getInvoiceId)
				.collect(ImmutableSet.toImmutableSet());

		return invoiceProcessorServiceCompanyService.retainIfServiceInvoiceWasAlreadyGenerated(invoiceIds);
	}

	private PaymentRow toPaymentRow(final PaymentToAllocate paymentToAllocate)
	{
		final BPartnerId bpartnerId = paymentToAllocate.getBpartnerId();

		return PaymentRow.builder()
				.paymentId(paymentToAllocate.getPaymentId())
				.clientAndOrgId(paymentToAllocate.getClientAndOrgId())
				.documentNo(paymentToAllocate.getDocumentNo())
				.bpartner(bpartnersLookup.findById(bpartnerId))
				.dateTrx(paymentToAllocate.getDateTrx())
				.paymentAmtMultiplier(paymentToAllocate.getPaymentAmtMultiplier())
				.payAmt(paymentToAllocate.getPayAmt())
				.openAmt(paymentToAllocate.getOpenAmt())
				.paymentDirection(paymentToAllocate.getPaymentDirection())
				.paymentCurrencyContext(paymentToAllocate.getPaymentCurrencyContext())
				.build();
	}

	private InvoiceRows retrieveInvoiceRowsByPayments(
			@NonNull final List<PaymentToAllocate> paymentsToAllocate,
			@NonNull final ZonedDateTime evaluationDate)
	{
		final ImmutableSet<InvoiceToAllocateQuery> queries = paymentsToAllocate.stream()
				.map(paymentToAllocate -> prepareInvoiceToAllocateQuery(paymentToAllocate)
						.evaluationDate(evaluationDate)
						.build())
				.collect(ImmutableSet.toImmutableSet());

		final List<InvoiceToAllocate> invoiceToAllocates = paymentAllocationRepo.retrieveInvoicesToAllocate(queries);
		return toInvoiceRows(invoiceToAllocates, evaluationDate);
	}

	private InvoiceToAllocateQueryBuilder prepareInvoiceToAllocateQuery(final PaymentToAllocate paymentToAllocate)
	{
		final CurrencyCode currencyCode = paymentToAllocate.getOpenAmt().getCurrencyCode();
		final CurrencyId currencyId = currenciesRepo.getCurrencyIdByCurrencyCode(currencyCode);

		return InvoiceToAllocateQuery.builder()
				.bpartnerId(paymentToAllocate.getBpartnerId())
				.currencyId(currencyId)
				.clientAndOrgId(paymentToAllocate.getClientAndOrgId());
	}

	private InvoiceRow toInvoiceRow(
			@NonNull final InvoiceToAllocate invoiceToAllocate,
			@NonNull final InvoiceRowLoadingContext loadingContext)
	{
		final Optional<Amount> serviceFeeAmount = computeServiceFee(invoiceToAllocate, loadingContext).map(InvoiceProcessingFeeCalculation::getFeeAmountIncludingTax);

		return InvoiceRow.builder()
				.invoiceId(invoiceToAllocate.getInvoiceId())
				.clientAndOrgId(invoiceToAllocate.getClientAndOrgId())
				.docTypeName(docTypeBL.getNameById(invoiceToAllocate.getDocTypeId()))
				.documentNo(invoiceToAllocate.getDocumentNo())
				.poReference(invoiceToAllocate.getPoReference())
				.dateInvoiced(invoiceToAllocate.getDateInvoiced())
				.bpartner(bpartnersLookup.findById(invoiceToAllocate.getBpartnerId()))
				.docBaseType(invoiceToAllocate.getDocBaseType())
				.invoiceAmtMultiplier(invoiceToAllocate.getMultiplier())
				.grandTotal(invoiceToAllocate.getGrandTotal())
				.openAmt(invoiceToAllocate.getOpenAmountConverted())
				.discountAmt(invoiceToAllocate.getDiscountAmountConverted())
				.serviceFeeAmt(serviceFeeAmount.orElse(null))
				.currencyConversionTypeId(invoiceToAllocate.getCurrencyConversionTypeId())
				.build();
	}

	private Optional<InvoiceProcessingFeeCalculation> computeServiceFee(
			@NonNull final InvoiceToAllocate invoiceToAllocate,
			@NonNull final InvoiceRowLoadingContext loadingContext)
	{
		if (invoiceToAllocate.getDocBaseType().isSales())
		{
			final InvoiceId invoiceId = invoiceToAllocate.getInvoiceId();
			final boolean serviceInvoiceAlreadyGenerated = loadingContext.isServiceInvoiceAlreadyGenerated(invoiceId);
			return invoiceProcessorServiceCompanyService.computeFee(InvoiceProcessingFeeComputeRequest.builder()
					.orgId(invoiceToAllocate.getClientAndOrgId().getOrgId())
					.evaluationDate(loadingContext.getEvaluationDate())
					.customerId(invoiceToAllocate.getBpartnerId())
					.docTypeId(invoiceToAllocate.getDocTypeId())
					.invoiceId(invoiceId)
					.invoiceGrandTotal(invoiceToAllocate.getGrandTotal())
					.serviceInvoiceWasAlreadyGenerated(OptionalBoolean.ofBoolean(serviceInvoiceAlreadyGenerated))
					.build());
		}
		else
		{
			return Optional.empty();
		}
	}

	public List<InvoiceRow> getInvoiceRowsListByInvoiceId(
			@NonNull final Collection<InvoiceId> invoiceIds,
			@NonNull final ZonedDateTime evaluationDate)
	{
		if (invoiceIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final InvoiceToAllocateQuery query = InvoiceToAllocateQuery.builder()
				.evaluationDate(evaluationDate)
				.onlyInvoiceIds(invoiceIds)
				.build();

		final List<InvoiceToAllocate> invoiceToAllocates = paymentAllocationRepo.retrieveInvoicesToAllocate(query);
		return toInvoiceRowsList(invoiceToAllocates, evaluationDate);
	}

	public Optional<InvoiceRow> getInvoiceRowByInvoiceId(
			@NonNull final InvoiceId invoiceId,
			@NonNull final ZonedDateTime evaluationDate)
	{
		final List<InvoiceRow> invoiceRows = getInvoiceRowsListByInvoiceId(ImmutableList.of(invoiceId), evaluationDate);
		if (invoiceRows.isEmpty())
		{
			return Optional.empty();
		}
		else if (invoiceRows.size() == 1)
		{
			return Optional.of(invoiceRows.get(0));
		}
		else
		{
			throw new AdempiereException("Expected only one row for " + invoiceId + " but got " + invoiceRows);
		}
	}

	public List<PaymentRow> getPaymentRowsListByPaymentId(
			@NonNull final Collection<PaymentId> paymentIds,
			@NonNull final ZonedDateTime evaluationDate)
	{
		if (paymentIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final PaymentToAllocateQuery query = PaymentToAllocateQuery.builder()
				.evaluationDate(evaluationDate)
				.additionalPaymentIdsToInclude(paymentIds)
				.build();

		return paymentAllocationRepo.retrievePaymentsToAllocate(query)
				.stream()
				.map(this::toPaymentRow)
				.collect(ImmutableList.toImmutableList());
	}

	public Optional<PaymentRow> getPaymentRowByPaymentId(
			@NonNull final PaymentId paymentId,
			@NonNull final ZonedDateTime evaluationDate)
	{
		final List<PaymentRow> paymentRows = getPaymentRowsListByPaymentId(ImmutableList.of(paymentId), evaluationDate);
		if (paymentRows.isEmpty())
		{
			return Optional.empty();
		}
		else if (paymentRows.size() == 1)
		{
			return Optional.of(paymentRows.get(0));
		}
		else
		{
			throw new AdempiereException("Expected only one row for " + paymentId + " but got " + paymentRows);
		}
	}

	@Value
	@Builder
	private static class InvoiceRowLoadingContext
	{
		@NonNull ZonedDateTime evaluationDate;
		@NonNull ImmutableSet<InvoiceId> invoiceIdsWithServiceInvoiceAlreadyGenetated;

		public boolean isServiceInvoiceAlreadyGenerated(@NonNull final InvoiceId invoiceId)
		{
			return invoiceIdsWithServiceInvoiceAlreadyGenetated.contains(invoiceId);
		}
	}
}
