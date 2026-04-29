/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.order.payschedule.delivery.allocation;

import de.metas.allocation.api.IAllocationBL;
import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.proforma.ProformaOrderAllocRepository;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_C_PaymentTerm_Break;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/**
 * Computes and creates the prepayment allocation for a completed financial purchase invoice
 * (iter-3, AC #4, #6, #8, #10, #12, #15).
 *
 * <h2>Allocation rule (REQUIREMENTS.md §3.3)</h2>
 * <table>
 *   <tr><th>{@code IsPartialInvoice}</th><th>Allocation amount</th></tr>
 *   <tr><td>PARTIAL</td><td>{@code MIN(receipt × LC%, remainingPrepay)}</td></tr>
 *   <tr><td>FINAL</td><td>{@code remainingPrepay} (consumes all remaining prepay)</td></tr>
 * </table>
 *
 * <p>Edge: {@code remainingPrepay <= 0} → no allocation, no error (AC #15).
 *
 * <p><strong>CRITICAL (R12 / AC #15):</strong> never delegate to
 * {@code IAllocationBL.autoAllocateAvailablePayments(invoice)} — it is greedy and
 * would consume the full prepay on the first invoice, violating AC #6.
 * Always use {@code IAllocationBL.newBuilder()} with an explicit amount.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
@Service
public class DeliveryPrepaymentAllocationService
{
	/** Scale for intermediate per-line tax calculations (round at the end). */
	private static final int INTERMEDIATE_SCALE = 12;

	/** Final scale for matched-receipt-value and allocation amounts. */
	private static final int FINAL_SCALE = 2;

	// Services (field-level, assigned eagerly to match metasfresh convention)
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IAllocationBL allocationBL = Services.get(IAllocationBL.class);
	@NonNull private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	@NonNull private final ITaxBL taxBL = Services.get(ITaxBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	// Spring beans accessed via SpringContextHolder so a no-arg constructor works in unit tests
	@NonNull private final SpringContextHolder.Lazy<ProformaOrderAllocRepository> proformaAllocRepoSupplier =
			SpringContextHolder.instance.lazyBean(ProformaOrderAllocRepository.class);

	// -----------------------------------------------------------------------
	// Task 26 — pure computation function
	// -----------------------------------------------------------------------

	/**
	 * Pure function: computes the allocation amount to create between the proforma-prepayment
	 * and the given invoice, based on the matched receipt value and the partial-invoice flag.
	 *
	 * <p>Public for unit testability (no Spring context needed).
	 *
	 * @param receiptWithTax  value of the receipt(s) matched to this invoice, including tax
	 * @param flag            {@link IsPartialInvoiceFlag#PARTIAL} or {@link IsPartialInvoiceFlag#FINAL}
	 * @param remainingPrepay remaining available amount on the proforma-prepayment payment
	 * @param lcPercent       LC percentage (e.g. {@code Percent.of(30)} for a 30/70 payment term)
	 * @return the allocation amount; zero when {@code remainingPrepay <= 0}; never negative
	 */
	public Money computeAllocation(
			@NonNull final Money receiptWithTax,
			@NonNull final IsPartialInvoiceFlag flag,
			@NonNull final Money remainingPrepay,
			@NonNull final Percent lcPercent)
	{
		if (remainingPrepay.signum() <= 0)
		{
			return Money.zero(remainingPrepay.getCurrencyId());
		}

		switch (flag)
		{
			case PARTIAL:
			{
				// proportional = receipt × LC%, rounded to 2 decimal places HALF_UP
				final BigDecimal proportionalAmt =
						lcPercent.computePercentageOf(receiptWithTax.toBigDecimal(), FINAL_SCALE, RoundingMode.HALF_UP);
				final Money proportional = Money.of(proportionalAmt, receiptWithTax.getCurrencyId());
				return proportional.min(remainingPrepay);
			}
			case FINAL:
			{
				// Final invoice consumes all remaining prepay
				return remainingPrepay;
			}
			default:
				throw new IllegalStateException("Unhandled IsPartialInvoiceFlag: " + flag);
		}
	}

	// -----------------------------------------------------------------------
	// Task 27a — proforma-prepayment payment lookup
	// -----------------------------------------------------------------------

	/**
	 * Finds the iter-2 proforma-prepayment payment for the given order:
	 * {@code C_Order → C_Proforma_Order_Alloc → C_Invoice (proforma) → C_Payment(Proforma_Invoice_ID=thatInvoice)}.
	 *
	 * <p>Returns {@code null} when:
	 * <ul>
	 *   <li>No active {@code C_Proforma_Order_Alloc} row exists for the order.
	 *   <li>The proforma invoice has not been paid yet (LC step still Pending or Awaiting_Pay).
	 * </ul>
	 *
	 * <p>Mirrors {@link de.metas.order.payschedule.delivery.OrderPayScheduleDeliveryRepository}
	 * {@code loadProformaPrepaymentPaymentId} logic, exposed here for use from the allocation flow.
	 */
	@Nullable
	public PaymentId lookupProformaPrepaymentPayment(@NonNull final OrderId orderId)
	{
		final InvoiceId proformaInvoiceId = proformaAllocRepoSupplier.get()
				.findProformaInvoiceIdByOrderId(orderId)
				.orElse(null);
		if (proformaInvoiceId == null)
		{
			return null;
		}

		final I_C_Payment completedPayment = paymentDAO
				.findCompletedOrClosedByProformaInvoiceId(proformaInvoiceId)
				.orElse(null);
		if (completedPayment == null)
		{
			return null;
		}

		return PaymentId.ofRepoId(completedPayment.getC_Payment_ID());
	}

	// -----------------------------------------------------------------------
	// Task 27b — matched receipt value via M_MatchInv traversal
	// -----------------------------------------------------------------------

	/**
	 * Computes the with-tax value of the receipt(s) matched to the given invoice
	 * via {@code M_MatchInv}.
	 *
	 * <p>An invoice may match only a fraction of a receipt (partial-quantity match).
	 * For each {@code M_MatchInv} row linked to an invoice line:
	 * <pre>
	 *   lineNet     = inOutLine.MovementQty × orderLine.priceActual
	 *   lineGross   = lineNet × (1 + taxRate / 100)
	 *   matchedFrac = matchInv.Qty / inOutLine.MovementQty
	 *   contribution = matchedFrac × lineGross
	 * </pre>
	 * The contributions are summed across all match rows and rounded to 2 dp (HALF_UP).
	 *
	 * <p>Currency is taken from the first qualifying order line; all PO lines share
	 * the same currency, so this is always consistent.
	 *
	 * <p>Returns zero (in the invoice's currency) when no {@code M_MatchInv} rows exist
	 * or all matched lines lack an order line (dropship lines are skipped).
	 */
	@NonNull
	public Money computeMatchedReceiptValueWithTax(@NonNull final I_C_Invoice invoice)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
		final CurrencyId fallbackCurrencyId = CurrencyId.ofRepoId(invoice.getC_Currency_ID());

		// Retrieve all M_MatchInv rows for this invoice (one per invoice-line × receipt-line pair)
		final List<I_M_MatchInv> matchRows = queryBL.createQueryBuilder(I_M_MatchInv.class)
				.addEqualsFilter(I_M_MatchInv.COLUMNNAME_C_Invoice_ID, invoiceId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		BigDecimal total = BigDecimal.ZERO;
		CurrencyId currencyId = null;

		for (final I_M_MatchInv match : matchRows)
		{
			final int inOutLineId = match.getM_InOutLine_ID();
			if (inOutLineId <= 0)
			{
				continue;
			}

			final I_M_InOutLine inOutLine = loadOutOfTrx(inOutLineId, I_M_InOutLine.class);
			final int orderLineId = inOutLine.getC_OrderLine_ID();
			if (orderLineId <= 0)
			{
				// Dropship line — no PO line, skip
				continue;
			}

			final I_C_OrderLine orderLine = loadOutOfTrx(orderLineId, I_C_OrderLine.class);

			// Capture currency from the first qualifying order line
			if (currencyId == null && orderLine.getC_Currency_ID() > 0)
			{
				currencyId = CurrencyId.ofRepoId(orderLine.getC_Currency_ID());
			}

			final BigDecimal receiptLineQty = inOutLine.getMovementQty();
			if (receiptLineQty.signum() == 0)
			{
				continue;
			}

			final BigDecimal priceActual = orderLine.getPriceActual();
			final BigDecimal lineNet = receiptLineQty.multiply(priceActual);

			// Apply tax per order line (same approach as ReceiptTaxCalculator)
			final int taxRepoId = orderLine.getC_Tax_ID();
			final BigDecimal lineGross;
			if (taxRepoId <= 0)
			{
				lineGross = lineNet;
			}
			else
			{
				final Tax tax = taxBL.getTaxById(TaxId.ofRepoId(taxRepoId));
				lineGross = tax.calculateGross(lineNet, INTERMEDIATE_SCALE);
			}

			// Matched fraction: how much of the receipt line was invoiced in this match row
			final BigDecimal matchQty = match.getQty();
			final BigDecimal matchedFraction = matchQty.divide(receiptLineQty, INTERMEDIATE_SCALE, RoundingMode.HALF_UP);
			final BigDecimal contribution = matchedFraction.multiply(lineGross);

			total = total.add(contribution);
		}

		final BigDecimal rounded = total.setScale(FINAL_SCALE, RoundingMode.HALF_UP);
		return Money.of(rounded, currencyId != null ? currencyId : fallbackCurrencyId);
	}

	// -----------------------------------------------------------------------
	// Task 27c — orchestrator: allocateForInvoice
	// -----------------------------------------------------------------------

	/**
	 * Orchestrates the prepayment allocation for a newly completed financial purchase invoice.
	 *
	 * <p>Steps:
	 * <ol>
	 *   <li>Look up the LC% for the order's payment term.
	 *   <li>Look up the iter-2 proforma-prepayment payment for the order.
	 *   <li>Get its remaining available amount ({@code IPaymentDAO.getAvailableAmount}).
	 *   <li>Compute the matched receipt value via {@code M_MatchInv} traversal.
	 *   <li>Determine the allocation amount using
	 *       {@link #computeAllocation(Money, IsPartialInvoiceFlag, Money, Percent)}.
	 *   <li>Create and complete a {@code C_AllocationHdr} + {@code C_AllocationLine} via
	 *       {@code IAllocationBL.newBuilder()} (explicit amount — never auto-allocate).
	 * </ol>
	 *
	 * <p>Early-exits silently (no error) when:
	 * <ul>
	 *   <li>No proforma-prepayment payment exists for this order ({@code lookupProformaPrepaymentPayment} returns null).
	 *   <li>The payment's available amount is ≤ 0 (AC #15).
	 *   <li>The computed allocation amount is ≤ 0 (receipt value is zero; defensive guard).
	 * </ul>
	 *
	 * <p><strong>Call only on TIMING_AFTER_COMPLETE</strong> — not on reversal; reversal of the
	 * allocation is handled automatically by the standard allocation-reversal cascade
	 * ({@code MAllocationHdr.reverseCorrectIt()}).
	 *
	 * @param invoice  the completing financial AP invoice
	 * @param orderId  order linked to the invoice (resolved by the caller via {@code M_MatchInv})
	 */
	public void allocateForInvoice(
			@NonNull final I_C_Invoice invoice,
			@NonNull final OrderId orderId)
	{
		// 1. Look up the LC% for this order's payment term
		final Percent lcPercent = lookupLcPercent(orderId);

		// 2. Find the proforma-prepayment payment
		final PaymentId prepayPaymentId = lookupProformaPrepaymentPayment(orderId);
		if (prepayPaymentId == null)
		{
			return;
		}

		// 3. Get remaining available amount on the payment
		final I_C_Payment prepayPayment = paymentDAO.getById(prepayPaymentId);
		final CurrencyId paymentCurrencyId = CurrencyId.ofRepoId(prepayPayment.getC_Currency_ID());
		final BigDecimal availableAmtBD = paymentDAO.getAvailableAmount(prepayPaymentId);
		final Money remainingPrepay = Money.of(availableAmtBD, paymentCurrencyId);

		if (remainingPrepay.signum() <= 0)
		{
			// AC #15: no prepay left — do nothing
			return;
		}

		// 4. Compute matched receipt value via M_MatchInv
		final Money receiptWithTax = computeMatchedReceiptValueWithTax(invoice);

		// 5. Compute the allocation amount per the §3.3 rule
		final IsPartialInvoiceFlag flag = IsPartialInvoiceFlag.fromBoolean(invoice.isPartialInvoice());
		final Money allocAmt = computeAllocation(receiptWithTax, flag, remainingPrepay, lcPercent);

		if (allocAmt.signum() <= 0)
		{
			return;
		}

		// 6. Create and complete the allocation (explicit amount — NEVER autoAllocate)
		final Timestamp dateAcct = TimeUtil.asTimestamp(invoice.getDateAcct());
		final Timestamp dateTrx = TimeUtil.asTimestamp(invoice.getDateInvoiced());
		final OrgId orgId = OrgId.ofRepoId(invoice.getAD_Org_ID());
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());

		trxManager.runInThreadInheritedTrx(() ->
				allocationBL.newBuilder()
						.orgId(orgId)
						.currencyId(allocAmt.getCurrencyId())
						.dateAcct(dateAcct)
						.dateTrx(dateTrx)
						//
						.addLine()
						.orgId(orgId)
						.bpartnerId(bpartnerId)
						.invoiceId(invoiceId)
						.paymentId(prepayPaymentId)
						.amount(allocAmt.toBigDecimal())
						.lineDone()
						//
						.createAndComplete()
		);
	}

	// -----------------------------------------------------------------------
	// Private helpers
	// -----------------------------------------------------------------------

	/**
	 * Looks up the LC% for the given order's payment term.
	 * Returns {@link Percent#ZERO} when no LC break is found (defensive default).
	 *
	 * <p>The LC break row has {@code ReferenceDateType = 'LC'}
	 * ({@link de.metas.payment.paymentterm.ReferenceDateType#LetterOfCreditDate}).
	 */
	@NonNull
	private Percent lookupLcPercent(@NonNull final OrderId orderId)
	{
		final I_C_Order order = InterfaceWrapperHelper.load(orderId, I_C_Order.class);
		final int paymentTermId = order.getC_PaymentTerm_ID();

		final I_C_PaymentTerm_Break lcBreak = queryBL.createQueryBuilder(I_C_PaymentTerm_Break.class)
				.addEqualsFilter(I_C_PaymentTerm_Break.COLUMNNAME_C_PaymentTerm_ID, paymentTermId)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PaymentTerm_Break.COLUMNNAME_ReferenceDateType, ReferenceDateType.LetterOfCreditDate)
				.create()
				.firstOnly(I_C_PaymentTerm_Break.class);

		if (lcBreak == null)
		{
			return Percent.ZERO;
		}

		return Percent.of(BigDecimal.valueOf(lcBreak.getPercent()));
	}
}
