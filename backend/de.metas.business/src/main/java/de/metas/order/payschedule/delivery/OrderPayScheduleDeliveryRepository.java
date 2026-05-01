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

package de.metas.order.payschedule.delivery;

import com.google.common.collect.ImmutableList;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.proforma.ProformaOrderAllocRepository;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.payment.paymentterm.ReferenceDateType;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderPaySchedule;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_C_PaymentTerm_Break;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.X_C_PaymentTerm_Break;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Data-access layer for the Delivery sub-rows of {@code C_OrderPaySchedule}.
 *
 * <p>All mutating methods are called exclusively by
 * {@link OrderPayScheduleDeliveryService} (sole-writer pattern — see iter-2 §E).
 *
 * <p><strong>LC-step identification convention</strong> (iter-2 compatible):
 * The LC step row has {@code ReferenceDateType = 'LC'}
 * ({@link X_C_PaymentTerm_Break#REFERENCEDATETYPE_LCDate}).
 * Delivery rows are all rows whose {@code ReferenceDateType} is NOT {@code 'LC'}.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
@Repository
public class OrderPayScheduleDeliveryRepository
{
	// Services — all obtained lazily so the no-arg constructor works in unit tests
	// (AdempiereTestHelper.init() registers in-memory POJOs for IQueryBL etc.)
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	@NonNull private final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);

	// Spring beans — accessed via SpringContextHolder so a no-arg constructor is possible
	@NonNull private final SpringContextHolder.Lazy<ProformaOrderAllocRepository> proformaAllocRepoSupplier =
			SpringContextHolder.instance.lazyBean(ProformaOrderAllocRepository.class);
	@NonNull private final SpringContextHolder.Lazy<ReceiptTaxCalculator> receiptTaxCalculatorSupplier =
			SpringContextHolder.instance.lazyBean(ReceiptTaxCalculator.class);

	// -----------------------------------------------------------------------
	// Task 22a
	// -----------------------------------------------------------------------

	/**
	 * Loads a {@code C_Invoice} record by its ID. Used by
	 * {@link OrderPayScheduleDeliveryService} to determine invoice DocStatus and
	 * {@code IsPartialInvoice} flag.
	 */
	public I_C_Invoice getInvoice(@NonNull final InvoiceId id)
	{
		return InterfaceWrapperHelper.load(id, I_C_Invoice.class);
	}

	/**
	 * Returns all Delivery-type {@code C_OrderPaySchedule} rows for the given order.
	 *
	 * <p><b>LC step identification (iter-2 compatible):</b>
	 * The LC step row is identified by {@code ReferenceDateType = 'LC'}.
	 * This method returns all rows whose {@code ReferenceDateType != 'LC'} (the Delivery rows),
	 * including both receipt sub-rows ({@code M_InOut_ID > 0}) and the remainder row
	 * ({@code M_InOut_ID = 0}).
	 */
	public List<I_C_OrderPaySchedule> getDeliveryRowsByOrderId(@NonNull final OrderId orderId)
	{
		return queryBL.createQueryBuilder(I_C_OrderPaySchedule.class)
				.addEqualsFilter(I_C_OrderPaySchedule.COLUMNNAME_C_Order_ID, orderId)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_C_OrderPaySchedule.COLUMNNAME_ReferenceDateType, ReferenceDateType.LetterOfCreditDate)
				.create()
				.list();
	}

	// -----------------------------------------------------------------------
	// Task 22b
	// -----------------------------------------------------------------------

	/**
	 * Converges the existing Delivery sub-rows for the given order to the {@code desired} set
	 * (insert / update / delete as needed — "upsert" semantics).
	 * Matching is by {@code M_InOut_ID} (0 for the remainder row).
	 */
	public void writeDeliveryRows(@NonNull final OrderId orderId, @NonNull final List<DesiredDeliveryRow> desired)
	{
		final List<I_C_OrderPaySchedule> existing = getDeliveryRowsByOrderId(orderId);
		final Map<Object, I_C_OrderPaySchedule> existingByKey = new HashMap<>();
		for (final I_C_OrderPaySchedule row : existing)
		{
			existingByKey.put(keyOf(row), row);
		}

		final Set<Object> desiredKeys = new HashSet<>();
		for (final DesiredDeliveryRow d : desired)
		{
			final Object key = keyOf(d);
			desiredKeys.add(key);

			I_C_OrderPaySchedule row = existingByKey.get(key);
			if (row == null)
			{
				row = createNewDeliveryRow(orderId, existing);
			}

			row.setBaseAmt(d.getBaseAmt());
			row.setDueAmt(d.getDueAmt());
			row.setStatus(d.getStatus());
			row.setM_InOut_ID(d.getMInOutId() != null ? d.getMInOutId().getRepoId() : 0);
			row.setC_Invoice_ID(d.getCInvoiceId() != null ? d.getCInvoiceId().getRepoId() : 0);
			row.setDueDate(TimeUtil.asTimestamp(d.getDueDate()));

			InterfaceWrapperHelper.save(row);
		}

		// Delete obsolete rows (exist in DB but not in desired set)
		for (final Map.Entry<Object, I_C_OrderPaySchedule> e : existingByKey.entrySet())
		{
			if (!desiredKeys.contains(e.getKey()))
			{
				InterfaceWrapperHelper.delete(e.getValue());
			}
		}
	}

	/**
	 * Creates a new {@code C_OrderPaySchedule} row for a Delivery sub-row.
	 * Inherits mandatory FK columns (currency, payment term, break, due date, offset, seq, ref-date-type)
	 * from an existing Delivery sibling row when available.
	 * Falls back to querying the order's Delivery payment-term break directly.
	 *
	 * <p>Mirrors the convention used by iter-2's
	 * {@code OrderPayScheduleRepository.createLine()}.
	 */
	private I_C_OrderPaySchedule createNewDeliveryRow(
			@NonNull final OrderId orderId,
			@NonNull final List<I_C_OrderPaySchedule> existingSiblings)
	{
		final I_C_OrderPaySchedule newRow = InterfaceWrapperHelper.newInstance(I_C_OrderPaySchedule.class);
		newRow.setC_Order_ID(orderId.getRepoId());

		if (!existingSiblings.isEmpty())
		{
			// Inherit context from the first sibling — all Delivery rows of one order share
			// the same payment-term break, currency and due-date offset.
			final I_C_OrderPaySchedule sibling = existingSiblings.get(0);
			newRow.setC_PaymentTerm_ID(sibling.getC_PaymentTerm_ID());
			newRow.setC_PaymentTerm_Break_ID(sibling.getC_PaymentTerm_Break_ID());
			newRow.setC_Currency_ID(sibling.getC_Currency_ID());
			// DueDate is set by writeDeliveryRows from DesiredDeliveryRow.dueDate (caller-provided)
			newRow.setPercent(sibling.getPercent());
			newRow.setOffsetDays(sibling.getOffsetDays());
			newRow.setSeqNo(sibling.getSeqNo());
			newRow.setReferenceDateType(sibling.getReferenceDateType());
		}
		else
		{
			// No sibling yet — load from the order and its Delivery payment-term break
			final I_C_Order order = InterfaceWrapperHelper.load(orderId, I_C_Order.class);
			final I_C_PaymentTerm_Break deliveryBreak = findDeliveryBreakForPaymentTerm(order.getC_PaymentTerm_ID());
			if (deliveryBreak != null)
			{
				newRow.setC_PaymentTerm_ID(deliveryBreak.getC_PaymentTerm_ID());
				newRow.setC_PaymentTerm_Break_ID(deliveryBreak.getC_PaymentTerm_Break_ID());
				newRow.setC_Currency_ID(order.getC_Currency_ID());
				// DueDate is set by writeDeliveryRows from DesiredDeliveryRow.dueDate (caller-provided)
				newRow.setPercent(deliveryBreak.getPercent());
				newRow.setOffsetDays(deliveryBreak.getOffsetDays());
				newRow.setSeqNo(10);
				newRow.setReferenceDateType(deliveryBreak.getReferenceDateType());
			}
			else
			{
				// Last resort — bare context; callers should always have a sibling or a break
				newRow.setC_PaymentTerm_ID(order.getC_PaymentTerm_ID());
				newRow.setC_Currency_ID(order.getC_Currency_ID());
				// DueDate is set by writeDeliveryRows from DesiredDeliveryRow.dueDate (caller-provided)
				newRow.setOffsetDays(0);
				newRow.setPercent(0);
				newRow.setSeqNo(10);
				newRow.setReferenceDateType(X_C_PaymentTerm_Break.REFERENCEDATETYPE_InvoiceDate);
			}
		}

		return newRow;
	}

	/** Finds the non-LC payment-term break for the given payment term ID. */
	@Nullable
	private I_C_PaymentTerm_Break findDeliveryBreakForPaymentTerm(final int paymentTermId)
	{
		return queryBL.createQueryBuilder(I_C_PaymentTerm_Break.class)
				.addEqualsFilter(I_C_PaymentTerm_Break.COLUMNNAME_C_PaymentTerm_ID, paymentTermId)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_C_PaymentTerm_Break.COLUMNNAME_ReferenceDateType, ReferenceDateType.LetterOfCreditDate)
				.create()
				.firstOnly(I_C_PaymentTerm_Break.class);
	}

	/** Map key for a DB row — M_InOut_ID (boxed) or "remainder" sentinel. */
	private static Object keyOf(@NonNull final I_C_OrderPaySchedule row)
	{
		return row.getM_InOut_ID() > 0 ? Integer.valueOf(row.getM_InOut_ID()) : "remainder";
	}

	/** Map key for a desired row — M_InOut_ID (boxed) or "remainder" sentinel. */
	private static Object keyOf(@NonNull final DesiredDeliveryRow d)
	{
		return d.getMInOutId() != null ? Integer.valueOf(d.getMInOutId().getRepoId()) : "remainder";
	}

	// -----------------------------------------------------------------------
	// Task 22c — loadInputs
	// -----------------------------------------------------------------------

	/**
	 * Loads the full {@link DeliveryStepInputs} snapshot for the given order.
	 * Assembles order grand total, LC%/Delivery%, completed receipts (with per-receipt
	 * tax via {@link ReceiptTaxCalculator}), matched invoices (via {@code M_MatchInv}),
	 * and the iter-2 proforma-prepayment payment.
	 */
	public DeliveryStepInputs loadInputs(@NonNull final OrderId orderId)
	{
		final I_C_Order order = InterfaceWrapperHelper.load(orderId, I_C_Order.class);
		final CurrencyId currencyId = CurrencyId.ofRepoId(order.getC_Currency_ID());
		final Money orderGrandTotal = Money.of(order.getGrandTotal(), currencyId);

		// 1. Payment term break percentages (LC% and Delivery%)
		final BreakPercentages pct = loadBreakPercentages(order.getC_PaymentTerm_ID());

		// 2. Iter-2 proforma-prepayment payment (null if LC step not yet paid)
		final PaymentId proformaPrepaymentPaymentId = loadProformaPrepaymentPaymentId(orderId);

		// 3. Completed, non-reversal purchase receipts for the order.
		// Reversal_ID IS NULL: the receipt is not a reversal document itself.
		// X_M_InOut.setReversal_ID stores NULL for values < 1, so we must use IS NULL (not = 0).
		// DocStatus IN (CO,CL) already excludes the reversed original.
		// "IP" (In Progress) is included because TIMING_AFTER_COMPLETE fires inside
		// MInOut.completeIt() BEFORE DocumentEngine sets DocStatus = CO. At that moment
		// the completing receipt still has DocStatus = IP in the DB, so omitting IP would
		// miss it and produce only the remainder row instead of the expected receipt + remainder.
		final List<I_M_InOut> receipts = queryBL.createQueryBuilder(I_M_InOut.class)
				.addEqualsFilter(I_M_InOut.COLUMNNAME_C_Order_ID, orderId)
				.addEqualsFilter(I_M_InOut.COLUMNNAME_IsSOTrx, false)
				.addInArrayFilter(I_M_InOut.COLUMNNAME_DocStatus, "CO", "CL", "IP")
				.addEqualsFilter(I_M_InOut.COLUMNNAME_Reversal_ID, null)
				.create()
				.list();

		// 4. Build ReceiptInfo per receipt
		final ImmutableList.Builder<DeliveryStepInputs.ReceiptInfo> receiptInfos = ImmutableList.builder();
		for (final I_M_InOut receipt : receipts)
		{
			final InOutId inOutId = InOutId.ofRepoId(receipt.getM_InOut_ID());
			final Money withTaxValue = receiptTaxCalculatorSupplier.get().computeWithTaxValue(inOutId);

			// 4a. Find the invoice matched to this receipt via M_MatchInv
			final InvoiceId matchedInvoiceId = findMatchedInvoiceId(inOutId);

			String invoiceDocStatus = null;
			Money invoiceOpenAmt = null;

			if (matchedInvoiceId != null)
			{
				final I_C_Invoice invoice = getInvoice(matchedInvoiceId);
				invoiceDocStatus = invoice.getDocStatus();
				if (!"RE".equals(invoiceDocStatus))
				{
					// IAllocationDAO.retrieveOpenAmtInInvoiceCurrency returns Money in the invoice's currency
					invoiceOpenAmt = allocationDAO.retrieveOpenAmtInInvoiceCurrency(invoice, false);
				}
			}

			receiptInfos.add(DeliveryStepInputs.ReceiptInfo.builder()
					.mInOutId(inOutId)
					.movementDate(TimeUtil.asLocalDate(receipt.getMovementDate()))
					.withTaxValue(withTaxValue)
					.matchedInvoiceId(matchedInvoiceId)
					.invoiceDocStatus(invoiceDocStatus)
					.invoiceOpenAmt(invoiceOpenAmt)
					// iter3PrepayAlloc left null — populated in a future iteration
					.build());
		}

		return DeliveryStepInputs.builder()
				.orderId(orderId)
				.orderGrandTotal(orderGrandTotal)
				.lcPercent(pct.getLcPercent())
				.deliveryPercent(pct.getDeliveryPercent())
				.completedReceipts(receiptInfos.build())
				.proformaPrepaymentPaymentId(proformaPrepaymentPaymentId)
				.build();
	}

	/**
	 * Returns the single invoice matched to the given receipt via {@code M_MatchInv},
	 * or {@code null} when no match exists or when lines map to multiple invoices.
	 */
	@Nullable
	private InvoiceId findMatchedInvoiceId(@NonNull final InOutId inOutId)
	{
		final List<I_M_MatchInv> matchRows = queryBL.createQueryBuilder(I_M_MatchInv.class)
				.addEqualsFilter(I_M_MatchInv.COLUMNNAME_M_InOut_ID, inOutId.getRepoId())
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		InvoiceId result = null;
		for (final I_M_MatchInv m : matchRows)
		{
			final int invoiceIdInt = m.getC_Invoice_ID();
			if (invoiceIdInt <= 0)
			{
				continue;
			}
			final InvoiceId candidate = InvoiceId.ofRepoId(invoiceIdInt);
			if (result == null)
			{
				result = candidate;
			}
			else if (!result.equals(candidate))
			{
				// Multiple invoices on the same receipt — ambiguous; treat as unmatched
				return null;
			}
		}
		return result;
	}

	/**
	 * Loads LC% and Delivery% from the payment term's {@code C_PaymentTerm_Break} rows.
	 * Returns zero for breaks that are absent (defensive default).
	 */
	@NonNull
	private BreakPercentages loadBreakPercentages(final int paymentTermId)
	{
		final List<I_C_PaymentTerm_Break> breaks = queryBL.createQueryBuilder(I_C_PaymentTerm_Break.class)
				.addEqualsFilter(I_C_PaymentTerm_Break.COLUMNNAME_C_PaymentTerm_ID, paymentTermId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		BigDecimal lcPct = BigDecimal.ZERO;
		BigDecimal deliveryPct = BigDecimal.ZERO;

		for (final I_C_PaymentTerm_Break b : breaks)
		{
			if (ReferenceDateType.LetterOfCreditDate.getCode().equals(b.getReferenceDateType()))
			{
				lcPct = BigDecimal.valueOf(b.getPercent());
			}
			else
			{
				deliveryPct = BigDecimal.valueOf(b.getPercent());
			}
		}

		return BreakPercentages.builder()
				.lcPercent(lcPct)
				.deliveryPercent(deliveryPct)
				.build();
	}

	/**
	 * Returns the {@link PaymentId} of the iter-2 proforma-prepayment for the given order,
	 * or {@code null} if the LC step has not been paid yet.
	 *
	 * <p>Traversal (mirrors {@code OrderPayScheduleLCService.getPrepayment}):
	 * {@code C_Proforma_Order_Alloc(C_Order_ID=orderId)} → proforma {@code C_Invoice}
	 * → {@code C_Payment(Proforma_Invoice_ID=thatInvoice, DocStatus IN CO/CL)}.
	 */
	@Nullable
	private PaymentId loadProformaPrepaymentPaymentId(@NonNull final OrderId orderId)
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
	// Private inner types
	// -----------------------------------------------------------------------

	/** LC% + Delivery% from {@code C_PaymentTerm_Break} rows. */
	@Value
	@Builder
	private static class BreakPercentages
	{
		@NonNull BigDecimal lcPercent;
		@NonNull BigDecimal deliveryPercent;
	}

	// -----------------------------------------------------------------------
	// Public inner type — desired row descriptor
	// -----------------------------------------------------------------------

	/**
	 * Describes the desired state of a single Delivery sub-row (or remainder row).
	 * Created by {@link OrderPayScheduleDeliveryService#computeDesired(DeliveryStepInputs)}
	 * and consumed by {@link #writeDeliveryRows(OrderId, List)}.
	 */
	@Value
	@Builder
	public static class DesiredDeliveryRow
	{
		/**
		 * The eligible base amount for this sub-row.
		 * {@code baseAmt = receipt.withTaxValue} for sub-rows; {@code orderGrandTotal - sum(receipts)} for the remainder row.
		 */
		@NonNull BigDecimal baseAmt;

		/**
		 * The actual due amount for this sub-row.
		 * {@code dueAmt = round(baseAmt × deliveryPercent / 100, 2)}.
		 */
		@NonNull BigDecimal dueAmt;

		/**
		 * Status string — one of {@code "Pending"}, {@code "Awaiting_Pay"}, {@code "Paid"}.
		 * Mapped to {@code X_C_OrderPaySchedule.STATUS_*} constants by
		 * {@link #writeDeliveryRows}.
		 */
		@NonNull String status;

		/**
		 * Receipt FK — set on sub-rows, {@code null} on the remainder row.
		 */
		@Nullable InOutId mInOutId;

		/**
		 * Invoice FK — set once a financial invoice is matched and completed for this receipt,
		 * {@code null} until then.
		 */
		@Nullable InvoiceId cInvoiceId;

		/**
		 * The DueDate to persist on {@code C_OrderPaySchedule.DueDate}.
		 * For receipt sub-rows: the receipt's {@code MovementDate} (delivery-step reference date).
		 * For the remainder row: {@code 9999-12-01} sentinel (Pending row, no real DueDate yet).
		 *
		 * <p>Provided by the caller ({@link OrderPayScheduleDeliveryService#computeDesired})
		 * so the repository never calls {@code System.currentTimeMillis()} (architecture §8).
		 */
		@NonNull LocalDate dueDate;
	}
}
