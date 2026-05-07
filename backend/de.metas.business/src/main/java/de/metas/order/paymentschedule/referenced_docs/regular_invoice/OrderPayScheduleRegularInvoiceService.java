package de.metas.order.paymentschedule.referenced_docs.regular_invoice;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.allocation.api.IAllocationBL;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.document.engine.DocStatus;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.matchinv.MatchInvQuery;
import de.metas.invoice.matchinv.MatchInvType;
import de.metas.invoice.matchinv.service.MatchInvoiceRepository;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceLineBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.core.OrderPaySchedule;
import de.metas.order.paymentschedule.core.OrderPayScheduleLine;
import de.metas.order.paymentschedule.core.repository.OrderPayScheduleRepository;
import de.metas.order.paymentschedule.referenced_docs.material_receipt.MaterialReceipt;
import de.metas.order.paymentschedule.referenced_docs.material_receipt.OrderPayScheduleMaterialReceiptService;
import de.metas.order.paymentschedule.referenced_docs.prepayment.OrderPaySchedulePrepaymentService;
import de.metas.order.paymentschedule.referenced_docs.prepayment.Prepayment;
import de.metas.order.paymentschedule.referenced_docs.proforma_invoice.OrderPayScheduleProformaService;
import de.metas.organization.OrgId;
import de.metas.tax.api.ITaxBL;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_InOut;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderPayScheduleRegularInvoiceService
{
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IInvoiceLineBL invoiceLineBL = Services.get(IInvoiceLineBL.class);
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	@NonNull private final IAllocationBL allocationBL = Services.get(IAllocationBL.class);
	@NonNull private final ITaxBL taxBL = Services.get(ITaxBL.class);

	@NonNull private final OrderPayScheduleRepository payScheduleRepository;
	@NonNull private final OrderPayScheduleProformaService proformaService;
	@NonNull private final OrderPaySchedulePrepaymentService prepaymentService;
	@NonNull private final MatchInvoiceRepository matchInvoiceRepository;
	@NonNull private final OrderPayScheduleMaterialReceiptService materialReceiptService;
	@NonNull private final MoneyService moneyService;

	public static OrderPayScheduleRegularInvoiceService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return SpringContextHolder.getBeanOrSupply(
				OrderPayScheduleRegularInvoiceService.class,
				() -> new OrderPayScheduleRegularInvoiceService(
						new OrderPayScheduleRepository(),
						OrderPayScheduleProformaService.newInstanceForUnitTesting(),
						OrderPaySchedulePrepaymentService.newInstanceForUnitTesting(),
						new MatchInvoiceRepository(),
						new OrderPayScheduleMaterialReceiptService(),
						new MoneyService(new CurrencyRepository())
				)
		);
	}

	// Package-visible for unit tests in the same package; production callers use fromRecordIfRegularInvoice/getRegularInvoicesByOrderId.
	boolean isRegularInvoice(@NonNull final I_C_Invoice invoiceRecord, boolean validateDocStatus)
	{
		// SOTrx guard: only purchase invoices
		if (invoiceRecord.isSOTrx())
		{
			return false;
		}

		// Financial guard: proforma invoices have IsFinancial='N'
		if (!invoiceRecord.isFinancial())
		{
			return false;
		}

		if (validateDocStatus && !DocStatus.ofNullableCodeOrUnknown(invoiceRecord.getDocStatus()).isCompletedOrClosed())
		{
			return false;
		}

		// APC guard: purchase credit memos do not drive the delivery step
		final InvoiceDocBaseType docBaseType = invoiceBL.getInvoiceDocBaseType(invoiceRecord);
		return !docBaseType.isVendorCreditMemo();
	}

	public Optional<RegularInvoice> getByReceipt(@NonNull final MaterialReceipt receipt)
	{
		return getByReceipt(receipt, null);
	}

	/**
	 * Why this overload exists:
	 * <p>
	 * {@code MInvoice.completeIt()} fires {@code @DocValidate(TIMING_AFTER_COMPLETE)} listeners at line 1133, which is
	 * <em>before</em> {@code DocumentEngine.completeIt()} flips {@code DocStatus} from {@code "IP"} to {@code "CO"}
	 * at line 454–455. Any AFTER_COMPLETE listener that re-reads the invoice from the DB sees
	 * {@code DocStatus="IP"} — i.e. "not completed yet" — even though we are inside the after-complete callback.
	 * <p>
	 * The interceptor already holds the in-memory invoice (which is authoritatively completing right now).
	 * We thread it down through this overload so the lookup can bypass the stale DB read for exactly this one
	 * invoice. All other invoices are looked up and filtered through the standard
	 * {@link RegularInvoice#isCompletedOrClosed()} check as usual.
	 * <p>
	 * A framework-level cleanup (set {@code DocStatus=CO} before firing AFTER_COMPLETE in all
	 * {@code M*.completeIt()} implementations) is intentionally out of scope for the iter-3 PR due to its
	 * broad blast radius. Until that is addressed, this thread-through is the iter-3 workaround.
	 *
	 * @param receipt         the material receipt whose matching regular invoice is sought
	 * @param completingInvoice the in-memory invoice that is currently completing (may be {@code null}
	 *                          when not called from an AFTER_COMPLETE listener)
	 */
	public Optional<RegularInvoice> getByReceipt(@NonNull final MaterialReceipt receipt, @Nullable final RegularInvoice completingInvoice)
	{
		final Set<InvoiceId> invoiceIds = findInvoiceIdsByInOutIds(ImmutableSet.of(receipt.getId()));

		RegularInvoice result = null;
		for (final InvoiceId invoiceId : invoiceIds)
		{
			if (result == null)
			{
				// Fast path: if the caller has already built the in-memory RegularInvoice for this invoice
				// (e.g. from an AFTER_COMPLETE interceptor), use it directly — the DB row still has
				// DocStatus="IP" at this point (see javadoc above), so a fresh DB read would falsely reject it.
				if (completingInvoice != null && InvoiceId.equals(completingInvoice.getId(), invoiceId))
				{
					result = completingInvoice;
				}
				else
				{
					final I_C_Invoice invoiceRecord = invoiceBL.getById(invoiceId);
					result = fromRecordIfRegularInvoice(invoiceRecord, receipt.getOrderId())
							.filter(RegularInvoice::isCompletedOrClosed)
							.orElse(null);
				}
			}
			else if (!InvoiceId.equals(result.getId(), invoiceId))
			{
				// Multiple invoices on the same receipt — ambiguous; treat as unmatched
				return Optional.empty();
			}
		}

		return Optional.ofNullable(result);
	}

	private ImmutableSet<InvoiceId> findInvoiceIdsByInOutIds(final @NotNull Collection<InOutId> inoutIds)
	{
		if (inoutIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return matchInvoiceRepository.list(MatchInvQuery.builder().type(MatchInvType.Material).inoutIds(inoutIds).build())
				.getInvoiceIds();
	}

	private ImmutableSet<InOutId> findInOutIdsByInvoiceId(@NonNull final InvoiceId invoiceId)
	{
		return matchInvoiceRepository.list(MatchInvQuery.builder().type(MatchInvType.Material).invoiceId(invoiceId).build())
				.getInOutIds();
	}

	public Optional<RegularInvoice> fromRecordIfRegularInvoice(final I_C_Invoice invoiceRecord)
	{
		final OrderId orderId = findOrderId(extractInvoiceId(invoiceRecord));
		return orderId == null ? Optional.empty() : fromRecordIfRegularInvoice(invoiceRecord, orderId);
	}

	private Optional<RegularInvoice> fromRecordIfRegularInvoice(@NonNull final I_C_Invoice invoiceRecord, @NonNull final OrderId orderId)
	{
		if (!isRegularInvoice(invoiceRecord, false))
		{
			return Optional.empty();
		}

		final ImmutableListMultimap<InvoiceId, I_C_InvoiceLine> lineRecords = retrieveInvoiceLineRecords(ImmutableList.of(invoiceRecord));

		return Optional.of(fromRecord(invoiceRecord, orderId, lineRecords));
	}

	private RegularInvoice fromRecord(
			@NonNull final I_C_Invoice invoiceRecord,
			@NonNull final OrderId orderId,
			@NonNull final Multimap<InvoiceId, I_C_InvoiceLine> invoiceLinesByInvoiceId)
	{
		final InvoiceId invoiceId = extractInvoiceId(invoiceRecord);
		final CurrencyId currencyId = CurrencyId.ofRepoId(invoiceRecord.getC_Currency_ID());

		return RegularInvoice.builder()
				.id(invoiceId)
				.orderId(orderId)
				.isPartialInvoice(invoiceRecord.isPartialInvoice())
				.orgId(OrgId.ofRepoId(invoiceRecord.getAD_Org_ID()))
				.bpartnerId(BPartnerId.ofRepoId(invoiceRecord.getC_BPartner_ID()))
				.dateInvoiced(TimeUtil.asLocalDateNonNull(invoiceRecord.getDateInvoiced()))
				.dateAcct(TimeUtil.asLocalDateNonNull(invoiceRecord.getDateAcct()))
				.currencyId(currencyId)
				.docStatus(DocStatus.ofNullableCodeOrUnknown(invoiceRecord.getDocStatus()))
				.isPaid(invoiceRecord.isPaid())
				.lines(invoiceLinesByInvoiceId.get(invoiceId)
						.stream()
						.map(lineRecord -> fromRecord(lineRecord, currencyId))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private RegularInvoice.Line fromRecord(@NonNull final I_C_InvoiceLine lineRecord, @NonNull final CurrencyId currencyId)
	{
		return RegularInvoice.Line.builder()
				.id(InvoiceLineId.ofRepoId(lineRecord.getC_InvoiceLine_ID()))
				.qtyInvoiced(invoiceLineBL.getQtyInvoicedStockUOM(lineRecord))
				.lineGrossAmt(Money.of(lineRecord.getLineNetAmt().add(lineRecord.getTaxAmtInfo()), currencyId))
				.build();
	}

	// Package-visible for unit tests in the same package; production callers use retroAllocateUnallocatedInvoices.
	List<RegularInvoice> findRegularInvoicesNotAlreadyAllocatedToPrepayment(@NonNull final Prepayment prepayment)
	{
		return getRegularInvoicesByOrderId(prepayment.getOrderId())
				.stream()
				.filter(invoice -> !allocationBL.hasActiveAllocationBetween(invoice.getId(), prepayment.getId()))
				.collect(Collectors.toList());
	}

	@NonNull
	// Package-visible for unit tests in the same package; production callers use findRegularInvoicesNotAlreadyAllocatedToPrepayment.
	List<RegularInvoice> getRegularInvoicesByOrderId(@NonNull final OrderId orderId)
	{
		final List<InOutId> inoutIds = inOutDAO.retrieveInOutIdsByOrderId(orderId);
		final Set<InvoiceId> invoiceIds = findInvoiceIdsByInOutIds(inoutIds);
		return getRegularInvoicesByIds(invoiceIds, orderId);
	}

	private List<RegularInvoice> getRegularInvoicesByIds(final Set<InvoiceId> invoiceIds, final OrderId orderId)
	{
		if (invoiceIds.isEmpty()) {return ImmutableList.of();}

		final List<I_C_Invoice> regularInvoiceRecords = invoiceBL.getByIds(invoiceIds)
				.stream()
				.filter(invoiceRecord -> isRegularInvoice(invoiceRecord, true))
				.collect(Collectors.toList());

		final ImmutableListMultimap<InvoiceId, I_C_InvoiceLine> lineRecords = retrieveInvoiceLineRecords(regularInvoiceRecords);

		return regularInvoiceRecords.stream()
				.map(invoiceRecord -> fromRecord(invoiceRecord, orderId, lineRecords))
				.sorted(Comparator.comparing(RegularInvoice::getDateInvoiced)
						.thenComparing(RegularInvoice::getId))
				.collect(ImmutableList.toImmutableList());
	}

	private ImmutableListMultimap<InvoiceId, I_C_InvoiceLine> retrieveInvoiceLineRecords(final Collection<I_C_Invoice> invoices)
	{
		if (invoices.isEmpty()) {return ImmutableListMultimap.of();}

		final ImmutableSet<InvoiceId> invoiceIds = invoices.stream()
				.map(OrderPayScheduleRegularInvoiceService::extractInvoiceId)
				.collect(ImmutableSet.toImmutableSet());

		return Multimaps.index(
				invoiceBL.getLinesByInvoiceIds(invoiceIds),
				OrderPayScheduleRegularInvoiceService::extractInvoiceId
		);
	}

	private static @NotNull InvoiceId extractInvoiceId(final I_C_Invoice invoiceRecord)
	{
		return InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID());
	}

	private static @NotNull InvoiceId extractInvoiceId(final I_C_InvoiceLine lineRecord)
	{
		return InvoiceId.ofRepoId(lineRecord.getC_Invoice_ID());
	}

	@Nullable
	private OrderId findOrderId(@NonNull final InvoiceId invoiceId)
	{
		final ImmutableSet<InOutId> inoutIds = findInOutIdsByInvoiceId(invoiceId);
		if (inoutIds.isEmpty())
		{
			return null;
		}

		final ImmutableSet<OrderId> orderIds = inOutDAO.getByIds(inoutIds, I_M_InOut.class)
				.stream()
				.map(inout -> OrderId.ofRepoIdOrNull(inout.getC_Order_ID()))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		if (orderIds.size() != 1)
		{
			// No order or ambiguous (multiple orders) - skip
			return null;
		}

		return orderIds.iterator().next();
	}

	public void allocateForInvoice(@NonNull final RegularInvoice regularVendorInvoice)
	{
		final OrderId orderId = regularVendorInvoice.getOrderId();
		final Prepayment prepayment = proformaService.getIdByOrderId(orderId)
				.flatMap(proformaInvoiceId -> prepaymentService.getByProformaInvoiceId(proformaInvoiceId, orderId))
				.orElse(null);
		if (prepayment == null)
		{
			return;
		}

		allocateForInvoice(regularVendorInvoice, prepayment);
	}

	private void allocateForInvoice(
			@NonNull final RegularInvoice regularVendorInvoice,
			@NonNull final Prepayment prepayment)
	{
		final Money signedAllocAmt = computeAmountToAllocate(regularVendorInvoice, prepayment);
		if (signedAllocAmt.signum() == 0)
		{
			return;
		}

		allocationBL.newBuilder()
				.orgId(regularVendorInvoice.getOrgId())
				.currencyId(signedAllocAmt.getCurrencyId())
				.dateAcct(regularVendorInvoice.getDateAcct())
				.dateTrx(regularVendorInvoice.getDateInvoiced())
				//
				.addLine()
				.bpartnerId(regularVendorInvoice.getBpartnerId())
				.invoiceId(regularVendorInvoice.getId())
				.paymentId(prepayment.getId())
				.amount(signedAllocAmt.toBigDecimal())
				.lineDone()
				//
				.createAndComplete();
	}

	@NonNull
	// Package-visible for unit tests in the same package; production callers use allocateForInvoice(...).
	Money computeAmountToAllocate(
			@NonNull final RegularInvoice regularVendorInvoice,
			@NonNull final Prepayment prepayment)
	{
		final Money remainingPrepay = prepaymentService.getAvailableAmount(prepayment);
		if (remainingPrepay.signum() <= 0)
		{
			// no prepay left - do nothing
			return Money.zero(regularVendorInvoice.getCurrencyId());
		}

		final Money receiptValue = newRegularInvoiceValueCalculator()
				.computeValueMatchedByReceipts(regularVendorInvoice);

		final Money allocAmt;
		if (regularVendorInvoice.isPartialInvoice())
		{
			// proportional = receipt × LC%, rounded to 2 decimal places HALF_UP
			final Percent lcPercent = lookupLcPercent(regularVendorInvoice.getOrderId());
			final CurrencyPrecision precision = moneyService.getStdPrecision(receiptValue.getCurrencyId());
			final Money proportional = receiptValue.multiply(lcPercent, precision);
			allocAmt = proportional.min(remainingPrepay);
		}
		else
		{
			// Final invoice consumes all remaining prepay
			allocAmt = remainingPrepay;
		}

		if (allocAmt.signum() <= 0)
		{
			return Money.zero(regularVendorInvoice.getCurrencyId());
		}

		// C_AllocationLine.Amount sign convention: negative for AP, positive for AR
		// (matches MPayment.allocateInvoice which calls allocationAmt.negate() for AP).
		return allocAmt.negate();
	}

	private RegularInvoiceValueCalculator newRegularInvoiceValueCalculator()
	{
		return RegularInvoiceValueCalculator.builder()
				.taxBL(taxBL)
				.moneyService(moneyService)
				.matchInvoiceRepository(matchInvoiceRepository)
				.materialReceiptService(materialReceiptService)
				.build();
	}

	public void retroAllocateUnallocatedInvoices(@NonNull final Prepayment prepayment)
	{
		for (final RegularInvoice invoice : findRegularInvoicesNotAlreadyAllocatedToPrepayment(prepayment))
		{
			allocateForInvoice(invoice, prepayment);
		}
	}

	@NonNull
	private Percent lookupLcPercent(@NonNull final OrderId orderId)
	{
		return payScheduleRepository.getByOrderId(orderId)
				.flatMap(OrderPaySchedule::getSingleLCLine)
				.map(OrderPayScheduleLine::getPercent)
				.orElse(Percent.ZERO);
	}
}
