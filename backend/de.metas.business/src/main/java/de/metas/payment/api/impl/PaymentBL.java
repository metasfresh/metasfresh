/*
 * #%L
 * de.metas.business
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

package de.metas.payment.api.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.allocation.api.IAllocationBL;
import de.metas.banking.BankAccountId;
import de.metas.banking.BankStatementId;
import de.metas.banking.BankStatementLineId;
import de.metas.banking.BankStatementLineRefId;
import de.metas.banking.api.BankAccountService;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.FixedConversionRate;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentCurrencyContext;
import de.metas.payment.PaymentId;
import de.metas.payment.TenderType;
import de.metas.payment.api.DefaultPaymentBuilder;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.api.PaymentQuery;
import de.metas.payment.api.PaymentReconcileReference;
import de.metas.payment.api.PaymentReconcileRequest;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.Mutable;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.math.BigDecimal.ZERO;

public class PaymentBL implements IPaymentBL
{
	private static final Logger logger = LogManager.getLogger(PaymentBL.class);
	private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	private final IAllocationBL allocationBL = Services.get(IAllocationBL.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final ICurrencyBL currencyConversionBL = Services.get(ICurrencyBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	@Override
	public I_C_Payment getById(@NonNull final PaymentId paymentId)
	{
		return paymentDAO.getById(paymentId);
	}

	@Override
	public List<I_C_Payment> getByIds(@NonNull final Set<PaymentId> paymentIds)
	{
		return paymentDAO.getByIds(paymentIds);
	}

	@Override
	public void save(@NonNull final I_C_Payment payment)
	{
		paymentDAO.save(payment);
	}

	@Override
	public ImmutableSet<PaymentId> getPaymentIds(@NonNull final PaymentQuery query)
	{
		return paymentDAO.retrievePaymentIds(query);
	}

	private CurrencyId fetchC_Currency_Invoice_ID(final I_C_Payment payment)
	{
		final int C_Invoice_ID = payment.getC_Invoice_ID();
		final int C_Order_ID = payment.getC_Order_ID();

		CurrencyId C_Currency_Invoice_ID = null;

		if (C_Invoice_ID > 0)
		{
			C_Currency_Invoice_ID = CurrencyId.ofRepoIdOrNull(payment.getC_Invoice().getC_Currency_ID());
		} // get Invoice Info
		else if (C_Order_ID > 0)
		{
			C_Currency_Invoice_ID = CurrencyId.ofRepoIdOrNull(payment.getC_Order().getC_Currency_ID());
		}
		logger.debug("C_Currency_Invoice_ID = " + C_Currency_Invoice_ID + ", C_Invoice_ID=" + C_Invoice_ID);

		return C_Currency_Invoice_ID;
	}

	/**
	 * Get Open Amount invoice
	 *
	 * @param creditMemoAdjusted true if we want to get absolute values for Credit Memos
	 */
	private BigDecimal fetchOpenAmount(final I_C_Payment payment, final boolean creditMemoAdjusted)
	{
		BigDecimal InvoiceOpenAmt = ZERO;

		final int C_Invoice_ID = payment.getC_Invoice_ID();

		if (C_Invoice_ID > 0)
		{
			InvoiceOpenAmt = paymentDAO.getInvoiceOpenAmount(payment, creditMemoAdjusted);
		}

		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(payment.getC_Currency_ID());
		final CurrencyId invoiceCurrencyId = fetchC_Currency_Invoice_ID(payment);
		final LocalDate ConvDate = TimeUtil.asLocalDate(payment.getDateTrx());
		final CurrencyConversionTypeId conversionTypeId = CurrencyConversionTypeId.ofRepoIdOrNull(payment.getC_ConversionType_ID());
		final ClientId clientId = ClientId.ofRepoId(payment.getAD_Client_ID());
		final OrgId orgId = OrgId.ofRepoId(payment.getAD_Org_ID());

		// Get Currency Rate
		BigDecimal CurrencyRate = BigDecimal.ONE;
		if (currencyId != null
				&& invoiceCurrencyId != null
				&& !currencyId.equals(invoiceCurrencyId))
		{
			CurrencyRate = currencyBL.getCurrencyRate(
					invoiceCurrencyId,
					currencyId,
					ConvDate,
					conversionTypeId,
					clientId,
					orgId)
					.getConversionRate();

			//
			final CurrencyPrecision precision = currencyDAO.getStdPrecision(currencyId);
			InvoiceOpenAmt = precision.round(InvoiceOpenAmt.multiply(CurrencyRate));
		}

		return InvoiceOpenAmt;
	}

	@Override
	public void updateAmounts(final I_C_Payment payment, final String colName, final boolean creditMemoAdjusted)
	{
		final int C_Invoice_ID = payment.getC_Invoice_ID();
		final int C_Order_ID = payment.getC_Order_ID();
		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(payment.getDocStatus());

		// New Payment
		if (payment.getC_Payment_ID() <= 0 && payment.getC_BPartner_ID() <= 0 && C_Invoice_ID <= 0)
		{
			return;
		}

		// Changed Column IsOverUnderPayment
		if (I_C_Payment.COLUMNNAME_IsOverUnderPayment.equals(colName))
		{
			onIsOverUnderPaymentChange(payment, creditMemoAdjusted);
		}
		// set all amount on zero if no order nor invoice is set
		else if (C_Invoice_ID <= 0 && C_Order_ID <= 0)
		{
			if (payment.getDiscountAmt().signum() != 0)
			{
				payment.setDiscountAmt(ZERO);
			}
			if (payment.getWriteOffAmt().signum() != 0)
			{
				payment.setWriteOffAmt(ZERO);
			}
			if (payment.getOverUnderAmt().signum() != 0)
			{
				payment.setOverUnderAmt(ZERO);
			}
		}
		// Changed Column C_Currency_ID or C_ConversionType_ID
		else if (I_C_Payment.COLUMNNAME_C_Currency_ID.equals(colName) || I_C_Payment.COLUMNNAME_C_ConversionType_ID.equals(colName))
		{
			onCurrencyChange(payment);
		}
		// Changed Column PayAmt
		else if (I_C_Payment.COLUMNNAME_PayAmt.equals(colName))
		{
			onPayAmtChange(payment, true);
		}
		// calculate PayAmt
		else if (docStatus.isDrafted())
		{
			final BigDecimal InvoiceOpenAmt = fetchOpenAmount(payment, creditMemoAdjusted);
			final BigDecimal DiscountAmt = payment.getDiscountAmt();
			final BigDecimal WriteOffAmt = payment.getWriteOffAmt();
			final BigDecimal OverUnderAmt = payment.getOverUnderAmt();

			final BigDecimal PayAmt = InvoiceOpenAmt.subtract(DiscountAmt).subtract(WriteOffAmt).subtract(OverUnderAmt);
			payment.setPayAmt(PayAmt);
		}

	}

	@Override
	public void onIsOverUnderPaymentChange(final I_C_Payment payment, final boolean creditMemoAdjusted)
	{
		payment.setOverUnderAmt(ZERO);
		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(payment.getDocStatus());

		if (docStatus.isDrafted())
		{
			final BigDecimal InvoiceOpenAmt = fetchOpenAmount(payment, creditMemoAdjusted);
			final BigDecimal DiscountAmt = payment.getDiscountAmt();
			final BigDecimal PayAmt = payment.getPayAmt();

			if (payment.isOverUnderPayment())
			{
				final BigDecimal OverUnderAmt = InvoiceOpenAmt.subtract(PayAmt).subtract(DiscountAmt);
				payment.setWriteOffAmt(ZERO);
				payment.setOverUnderAmt(OverUnderAmt);
			}
			else
			{
				final BigDecimal WriteOffAmt = InvoiceOpenAmt.subtract(PayAmt).subtract(DiscountAmt);
				payment.setWriteOffAmt(WriteOffAmt);
				payment.setOverUnderAmt(ZERO);
			}
		}
	}

	@Override
	public void onCurrencyChange(final I_C_Payment payment)
	{
		final int C_Invoice_ID = payment.getC_Invoice_ID();
		final int C_Order_ID = payment.getC_Order_ID();
		// Get Currency Info
		final CurrencyId currencyId = CurrencyId.ofRepoIdOrNull(payment.getC_Currency_ID());
		final CurrencyId invoiceCurrencyId = fetchC_Currency_Invoice_ID(payment);
		final LocalDate convDate = TimeUtil.asLocalDate(payment.getDateTrx());
		final CurrencyConversionTypeId conversionTypeId = CurrencyConversionTypeId.ofRepoIdOrNull(payment.getC_ConversionType_ID());
		final ClientId clientId = ClientId.ofRepoId(payment.getAD_Client_ID());
		final OrgId orgId = OrgId.ofRepoId(payment.getAD_Org_ID());

		// Get Currency Rate
		BigDecimal currencyRate = BigDecimal.ONE;
		if (currencyId != null
				&& invoiceCurrencyId != null
				&& !currencyId.equals(invoiceCurrencyId))
		{
			currencyRate = currencyBL.getCurrencyRate(
					invoiceCurrencyId,
					currencyId,
					convDate,
					conversionTypeId,
					clientId,
					orgId)
					.getConversionRate();
		}

		BigDecimal PayAmt = payment.getPayAmt();
		BigDecimal DiscountAmt = payment.getDiscountAmt();
		BigDecimal WriteOffAmt = payment.getWriteOffAmt();
		BigDecimal OverUnderAmt = payment.getOverUnderAmt();

		final CurrencyPrecision precision = currencyId != null
				? currencyDAO.getStdPrecision(currencyId)
				: CurrencyPrecision.TWO;

		PayAmt = precision.round(PayAmt.multiply(currencyRate));
		payment.setPayAmt(PayAmt);

		DiscountAmt = precision.round(DiscountAmt.multiply(currencyRate));
		payment.setDiscountAmt(DiscountAmt);

		WriteOffAmt = precision.round(WriteOffAmt.multiply(currencyRate));
		payment.setWriteOffAmt(WriteOffAmt);

		OverUnderAmt = precision.round(OverUnderAmt.multiply(currencyRate));
		payment.setOverUnderAmt(OverUnderAmt);

		// No Invoice or Order - Set Discount, Witeoff, Under/Over to 0
		if (C_Invoice_ID <= 0 && C_Order_ID <= 0)
		{
			if (ZERO.compareTo(DiscountAmt) != 0)
			{
				payment.setDiscountAmt(ZERO);
			}

			if (ZERO.compareTo(WriteOffAmt) != 0)
			{
				payment.setWriteOffAmt(ZERO);
			}
			if (ZERO.compareTo(OverUnderAmt) != 0)
			{
				payment.setOverUnderAmt(ZERO);
			}
		}

	}

	@Override
	public void onPayAmtChange(final I_C_Payment payment, final boolean creditMemoAdjusted)
	{
		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(payment.getDocStatus());
		if (docStatus.isDrafted())
		{
			final BigDecimal DiscountAmt = payment.getDiscountAmt();
			final BigDecimal PayAmt = payment.getPayAmt();
			final BigDecimal InvoiceOpenAmt = fetchOpenAmount(payment, creditMemoAdjusted);
			BigDecimal WriteOffAmt = payment.getWriteOffAmt();
			BigDecimal OverUnderAmt = payment.getOverUnderAmt();

			if (payment.isOverUnderPayment())
			{
				OverUnderAmt = InvoiceOpenAmt.subtract(PayAmt).subtract(DiscountAmt).subtract(WriteOffAmt);
				payment.setOverUnderAmt(OverUnderAmt);
			}

			WriteOffAmt = InvoiceOpenAmt.subtract(PayAmt).subtract(DiscountAmt).subtract(OverUnderAmt);
			payment.setWriteOffAmt(WriteOffAmt);
			payment.setDiscountAmt(DiscountAmt);
		}

	}

	@Override
	public DefaultPaymentBuilder newInboundReceiptBuilder()
	{
		return DefaultPaymentBuilder.newInboundReceiptBuilder();
	}

	@Override
	public DefaultPaymentBuilder newOutboundPaymentBuilder()
	{
		return DefaultPaymentBuilder.newOutboundPaymentBuilder();
	}

	@Override
	public DefaultPaymentBuilder newBuilderOfInvoice(@NonNull final I_C_Invoice invoice)
	{
		return DefaultPaymentBuilder.newBuilderOfInvoice(invoice);
	}

	@Override
	public boolean isMatchInvoice(final I_C_Payment payment, final I_C_Invoice invoice)
	{
		final List<I_C_AllocationLine> allocations = paymentDAO.retrieveAllocationLines(payment);
		final List<I_C_Invoice> invoices = new ArrayList<>();
		for (final I_C_AllocationLine alloc : allocations)
		{
			invoices.add(alloc.getC_Invoice());
		}

		for (final I_C_Invoice inv : invoices)
		{
			if (inv.getC_Invoice_ID() == invoice.getC_Invoice_ID())
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean canAllocateOrderPaymentToInvoice(@Nullable final I_C_Order order)
	{
		if (order == null)
		{
			return false;
		}
		if (order.getC_Payment_ID() <= 0)
		{
			return false;
		}

		final boolean isPrepayOrder = docTypeBL.isPrepay(DocTypeId.ofRepoId(order.getC_DocType_ID()));
		if (isPrepayOrder)
		{
			return true;
		}

		final I_C_Payment payment = paymentDAO.getById(PaymentId.ofRepoId(order.getC_Payment_ID()));
		return payment.getC_Order_ID() == order.getC_Order_ID();
	}

	private void allocateInvoicesAgainstPaymentsIfNecessary(@NonNull final List<I_C_Payment> payments)
	{
		for (final I_C_Payment payment : payments)
		{
			final I_C_Order order = orderDAO.getById(OrderId.ofRepoId(payment.getC_Order_ID()));
			final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(InvoiceId.ofRepoId(payment.getC_Invoice_ID()));

			if (canAllocateOrderPaymentToInvoice(order))
			{
				if (invoice != null && DocStatus.ofCode(payment.getDocStatus()).isCompleted())
				{
					allocationBL.autoAllocateSpecificPayment(invoice, payment, true);
				}
			}
		}
	}

	private List<ExternalId> getExternalIdsList(@NonNull final List<I_C_Payment> payments)
	{
		final List<I_C_Payment> paymentsWithExternalIds = payments
				.stream()
				.filter(p -> !(p.getExternalOrderId() == null))
				.collect(Collectors.toList());
		return CollectionUtils.extractDistinctElements(paymentsWithExternalIds, p -> ExternalId.ofOrNull(p.getExternalOrderId()));
	}

	private List<OrderId> getOrderIdsList(@NonNull final List<I_C_Payment> payments)
	{
		final List<I_C_Payment> paymentsWithOrderIds = payments
				.stream()
				.filter(p -> !(p.getC_Order_ID() == 0))
				.collect(Collectors.toList());
		return CollectionUtils.extractDistinctElements(paymentsWithOrderIds, p -> OrderId.ofRepoId(p.getC_Order_ID()));
	}

	private void setPaymentOrderIds(@NonNull final List<I_C_Payment> payments, @NonNull final Map<ExternalId, OrderId> ids)
	{
		for (final I_C_Payment payment : payments)
		{
			final OrderId orderId = ids.get(ExternalId.ofOrNull(payment.getExternalOrderId()));
			if (orderId != null)
			{
				payment.setC_Order_ID(orderId.getRepoId());
				save(payment);
			}
		}
	}

	public void setPaymentOrderAndInvoiceIdsAndAllocateItIfNecessary(@NonNull final List<I_C_Payment> payments)
	{
		final List<ExternalId> externalIds = getExternalIdsList(payments);
		final Map<ExternalId, OrderId> orderIdsForExternalIds = orderDAO.getOrderIdsForExternalIds(externalIds);
		setPaymentOrderIds(payments, orderIdsForExternalIds);

		final List<OrderId> orderIds = getOrderIdsList(payments);
		final Map<OrderId, InvoiceId> orderIdInvoiceIdMap = invoiceDAO.getInvoiceIdsForOrderIds(orderIds);
		setPaymentInvoiceIds(payments, orderIdInvoiceIdMap);

		final List<I_C_Payment> paymentsWithOrderIdsAndInvoiceIds = payments
				.stream()
				.filter(p -> p.getC_Order_ID() > 0 && p.getC_Invoice_ID() > 0)
				.collect(Collectors.toList());

		allocateInvoicesAgainstPaymentsIfNecessary(paymentsWithOrderIdsAndInvoiceIds);
	}

	private void setPaymentInvoiceIds(@NonNull final List<I_C_Payment> payments, @NonNull final Map<OrderId, InvoiceId> ids)
	{
		for (final I_C_Payment payment : payments)
		{
			final InvoiceId invoiceId = ids.get(OrderId.ofRepoIdOrNull(payment.getC_Order_ID()));
			if (invoiceId != null)
			{
				payment.setC_Invoice_ID(invoiceId.getRepoId());
				save(payment);
			}
		}
	}

	@Override
	public void testAllocation(@NonNull final PaymentId paymentId)
	{
		final I_C_Payment payment = getById(paymentId);
		final boolean updated = testAllocation(payment);
		if (updated)
		{
			paymentDAO.save(payment);
		}
	}

	@Override
	public boolean testAllocation(final I_C_Payment payment)
	{
		BigDecimal alloc = paymentDAO.getAllocatedAmt(payment);
		final boolean hasAllocations = alloc != null; // metas: tsa: 01955
		if (alloc == null)
		{
			alloc = ZERO;
		}

		BigDecimal total = payment.getPayAmt();

		// metas: tsa: begin: 01955:
		// If is an zero payment and it has no allocations and the AutoPayZeroAmt flag is not set
		// then don't touch the payment
		if (total.signum() == 0
				&& !hasAllocations
				&& !sysConfigBL.getBooleanValue("org.compiere.model.MInvoice.AutoPayZeroAmt", true, payment.getAD_Client_ID()))
		{
			// don't touch the IsAllocated flag, return not changed
			return false;
		}
		// metas: tsa: end: 01955
		if (!payment.isReceipt())
		{
			total = total.negate();
		}
		final boolean test = total.compareTo(alloc) == 0;
		final boolean change = test != payment.isAllocated();
		if (change)
		{
			payment.setIsAllocated(test);
		}

		logger.debug("Allocated={} ({}={})", test, alloc, total);
		return change;
	}    // testAllocation

	@Override
	public boolean isCashTrx(@NonNull final I_C_Payment payment)
	{
		final TenderType tenderType = TenderType.ofCode(payment.getTenderType());
		return tenderType.isCash();
	}

	@Override
	public void fullyWriteOffPayments(@NonNull final Iterator<I_C_Payment> payments, @NonNull Instant writeOffDate)
	{
		while (payments.hasNext())
		{
			final I_C_Payment payment = payments.next();
			fullyWriteOffPayment(payment, writeOffDate);
		}
	}

	private void fullyWriteOffPayment(@NonNull final I_C_Payment payment, final @NonNull Instant writeOffDate)
	{
		final PaymentId paymentId = PaymentId.ofRepoId(payment.getC_Payment_ID());

		final Money moneyToWriteOff = Money.of(paymentDAO.getAvailableAmount(paymentId),
											   CurrencyId.ofRepoId(payment.getC_Currency_ID()));

		logger.debug("Writing off {} for the payment{}",
					 moneyToWriteOff,
					 payment);

		final I_C_AllocationHdr allocationHdr = paymentWriteOff(payment, moneyToWriteOff.toBigDecimal(), TimeUtil.asDate(writeOffDate) );

		logger.debug("C_AllocationHdr {} created for the payment {}", allocationHdr, payment);
	}

	@Override
	public I_C_AllocationHdr paymentWriteOff(final I_C_Payment payment, final BigDecimal writeOffAmt, final Date date)
	{
		Check.assumeNotNull(payment, "payment not null");
		Check.assume(writeOffAmt != null && writeOffAmt.signum() != 0, "WriteOffAmt != 0 but it was {}", writeOffAmt);
		Check.assumeNotNull(date, "date not null");

		final Timestamp dateTS = TimeUtil.asTimestamp(date);

		final Mutable<I_C_AllocationHdr> allocHdrRef = new Mutable<>();
		trxManager.run(ITrx.TRXNAME_ThreadInherited, new TrxRunnableAdapter()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				final I_C_AllocationHdr allocHdr = allocationBL.newBuilder()
						.orgId(payment.getAD_Org_ID())
						.currencyId(payment.getC_Currency_ID())
						.dateAcct(dateTS)
						.dateTrx(dateTS)
						//
						.addLine()
						.orgId(payment.getAD_Org_ID())
						.bpartnerId(payment.getC_BPartner_ID())
						.paymentId(payment.getC_Payment_ID())
						.paymentWriteOffAmt(writeOffAmt)
						.lineDone()
						//
						.createAndComplete();
				allocHdrRef.setValue(allocHdr);
			}
		});

		return allocHdrRef.getValue();
	}

	@Override
	public void updateDiscountAndPayAmtFromInvoiceIfAny(final I_C_Payment payment)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoIdOrNull(payment.getC_Invoice_ID());
		if (invoiceId == null)
		{
			return;
		}
		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(invoiceId);
		if (invoice.getC_DocType_ID() <= 0)
		{
			return;
		}
		final I_C_DocType docType = docTypeDAO.getById(DocTypeId.ofRepoIdOrNull(invoice.getC_DocType_ID()));
		paymentDAO.updateDiscountAndPayment(payment, invoice.getC_Invoice_ID(), docType);
	}

	@Override
	public void markNotReconciled(
			@NonNull final Collection<PaymentId> paymentIds)
	{
		if (paymentIds.isEmpty())
		{
			return;
		}

		final List<I_C_Payment> payments = paymentDAO.getByIds(ImmutableSet.copyOf(paymentIds));
		for (final I_C_Payment payment : payments)
		{
			markNotReconciledNoSave(payment);
			paymentDAO.save(payment);
		}
	}

	public static void markNotReconciledNoSave(
			@NonNull final I_C_Payment payment)
	{
		payment.setIsReconciled(false);
		payment.setC_BankStatement_ID(-1);
		payment.setC_BankStatementLine_ID(-1);
		payment.setC_BankStatementLine_Ref_ID(-1);
	}

	@Override
	public void markReconciled(
			@NonNull final Collection<PaymentReconcileRequest> requests)
	{
		final Collection<I_C_Payment> preloadedPayments = ImmutableList.of();
		markReconciled(requests, preloadedPayments);
	}

	@Override
	public void markReconciled(
			@NonNull final Collection<PaymentReconcileRequest> requests,
			@NonNull final Collection<I_C_Payment> preloadedPayments)
	{
		if (requests.isEmpty())
		{
			return;
		}

		final HashMap<PaymentId, I_C_Payment> payments = new HashMap<>();
		for (final I_C_Payment payment : preloadedPayments)
		{
			final PaymentId paymentId = PaymentId.ofRepoId(payment.getC_Payment_ID());
			payments.put(paymentId, payment);
		}

		final ImmutableSet<PaymentId> paymentIdsToLoad = requests.stream()
				.map(PaymentReconcileRequest::getPaymentId)
				.filter(paymentId -> !payments.containsKey(paymentId))
				.collect(ImmutableSet.toImmutableSet());
		if (!paymentIdsToLoad.isEmpty())
		{
			for (final I_C_Payment payment : paymentDAO.getByIds(paymentIdsToLoad))
			{
				final PaymentId paymentId = PaymentId.ofRepoId(payment.getC_Payment_ID());
				payments.put(paymentId, payment);
			}
		}

		for (final PaymentReconcileRequest request : requests)
		{
			markReconciledAndSave(
					payments.get(request.getPaymentId()),
					request.getReconcileRef());
		}
	}

	@Override
	public void markReconciledAndSave(
			@NonNull final I_C_Payment payment,
			@NonNull final PaymentReconcileReference reconcileRef)
	{
		if (payment.isReconciled())
		{
			final PaymentReconcileReference currentReconcileRef = extractPaymentReconcileReference(payment);
			throw new AdempiereException("Payment with DocumentNo=" + payment.getDocumentNo() + " is already reconciled. Maybe there is a duplicated bankstatement line?")
					.setParameter("C_Payment", payment)
					.setParameter("currentlyExistingReconcileRef", currentReconcileRef)
					.setParameter("newReconcileRef", reconcileRef)
					.appendParametersToMessage();
		}

		payment.setIsReconciled(true);

		final PaymentReconcileReference.Type type = reconcileRef.getType();
		if (PaymentReconcileReference.Type.BANK_STATEMENT_LINE.equals(type))
		{
			payment.setC_BankStatement_ID(reconcileRef.getBankStatementId().getRepoId());
			payment.setC_BankStatementLine_ID(reconcileRef.getBankStatementLineId().getRepoId());
			payment.setC_BankStatementLine_Ref_ID(-1);
		}
		else if (PaymentReconcileReference.Type.BANK_STATEMENT_LINE_REF.equals(type))
		{
			payment.setC_BankStatement_ID(reconcileRef.getBankStatementId().getRepoId());
			payment.setC_BankStatementLine_ID(reconcileRef.getBankStatementLineId().getRepoId());
			payment.setC_BankStatementLine_Ref_ID(reconcileRef.getBankStatementLineRefId().getRepoId());
		}
		else if (PaymentReconcileReference.Type.REVERSAL.equals(type))
		{
			final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(payment.getDocStatus());
			if (!docStatus.isReversed())
			{
				throw new AdempiereException("Payment shall be reversed but it's DocStatus is `" + docStatus + "`: " + payment);
			}

			final PaymentId reversalId = PaymentId.ofRepoIdOrNull(payment.getReversal_ID());
			if (!Objects.equals(reversalId, reconcileRef.getReversalId()))
			{
				throw new AdempiereException("Payment shall be reversed by `" + reconcileRef.getReversalId() + "` but it was reversed by `" + reversalId + "`: " + payment);
			}

			payment.setC_BankStatement_ID(-1);
			payment.setC_BankStatementLine_ID(-1);
			payment.setC_BankStatementLine_Ref_ID(-1);
		}
		else
		{
			throw new AdempiereException("Unknown reconciliation type: " + type);
		}

		paymentDAO.save(payment);
	}

	@VisibleForTesting
	static PaymentReconcileReference extractPaymentReconcileReference(final I_C_Payment payment)
	{
		try
		{
			final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(payment.getDocStatus());
			if (docStatus.isReversed())
			{
				final PaymentId reversalId = PaymentId.ofRepoId(payment.getReversal_ID());
				return PaymentReconcileReference.reversal(reversalId);
			}

			final BankStatementId bankStatementId = BankStatementId.ofRepoId(payment.getC_BankStatement_ID());
			final BankStatementLineId bankStatementLineId = BankStatementLineId.ofRepoId(payment.getC_BankStatementLine_ID());
			final BankStatementLineRefId bankStatementLineRefId = BankStatementLineRefId.ofRepoIdOrNull(payment.getC_BankStatementLine_Ref_ID());
			if (bankStatementLineRefId == null)
			{
				return PaymentReconcileReference.bankStatementLine(bankStatementId, bankStatementLineId);
			}
			else
			{
				return PaymentReconcileReference.bankStatementLineRef(bankStatementId, bankStatementLineId, bankStatementLineRefId);
			}
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed extracting payment reconcile reference from " + payment, ex);
		}
	}

	@Override
	public @NonNull TenderType getTenderType(final @NonNull BankAccountId bankAccountId)
	{
		final BankAccountService bankAccountService = SpringContextHolder.instance.getBean(BankAccountService.class);
		if (bankAccountService.isCashBank(bankAccountId))
		{
			return TenderType.Cash;
		}
		else
		{
			return TenderType.Check;
		}
	}

	@Override
	public Optional<PaymentId> getByExtIdOrgId(
			@NonNull final ExternalId externalId,
			@NonNull final OrgId orgId)
	{
		final Optional<I_C_Payment> payment = paymentDAO.getByExternalId(externalId, orgId);
		return Optional.ofNullable(payment.isPresent() ? PaymentId.ofRepoId(payment.get().getC_Payment_ID()) : null);
	}

	@Override
	public CurrencyConversionContext extractCurrencyConversionContext(
			@NonNull final I_C_Payment payment)
	{
		final PaymentCurrencyContext paymentCurrencyContext = PaymentCurrencyContext.ofPaymentRecord(payment);
		CurrencyConversionContext conversionCtx = currencyConversionBL.createCurrencyConversionContext(
				TimeUtil.asLocalDate(payment.getDateAcct()),
				paymentCurrencyContext.getCurrencyConversionTypeId(),
				ClientId.ofRepoId(payment.getAD_Client_ID()),
				OrgId.ofRepoId(payment.getAD_Org_ID()));

		final FixedConversionRate fixedConversionRate = paymentCurrencyContext.toFixedConversionRateOrNull();
		if (fixedConversionRate != null)
		{
			conversionCtx = conversionCtx.withFixedConversionRate(fixedConversionRate);
		}

		return conversionCtx;
	}

	@Override
	public void validateDocTypeIsInSync(@NonNull final I_C_Payment payment)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(payment.getC_DocType_ID());

		if (docTypeId == null)
		{
			return;
		}

		final I_C_DocType docType = docTypeBL.getById(docTypeId);

		// Invoice
		final I_C_Invoice invoice = InvoiceId.ofRepoIdOptional(payment.getC_Invoice_ID())
				.map(invoiceBL::getById)
				.orElse(null);

		if (invoice != null && invoice.isSOTrx() != docType.isSOTrx())
		{
			// task: 07564 the SOtrx flags don't match, but that's OK *if* the invoice i a credit memo (either for the vendor or customer side)
			if (!invoiceBL.isCreditMemo(invoice))
			{
				throw new AdempiereException("@PaymentDocTypeInvoiceInconsistent@");
			}
		}

		// globalqss - Allow prepayment to Purchase Orders
		// Order Waiting Payment (can only be SO)
		// if (C_Order_ID != 0 && dt != null && !dt.isSOTrx())
		// return "PaymentDocTypeInvoiceInconsistent";
		// Order
		final OrderId orderId = OrderId.ofRepoIdOrNull(payment.getC_Order_ID());

		if (orderId == null)
		{
			return;
		}

		final I_C_Order order = orderDAO.getById(orderId);

		if (order.isSOTrx() != docType.isSOTrx())
		{
			throw new AdempiereException("@PaymentDocTypeInvoiceInconsistent@");
		}
	}
}
