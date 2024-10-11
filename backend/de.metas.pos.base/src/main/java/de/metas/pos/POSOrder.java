package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.document.DocTypeId;
import de.metas.i18n.BooleanWithReason;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.pricing.PricingSystemId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class POSOrder
{
	@NonNull @Getter private final POSOrderExternalId externalId;
	@Nullable @Getter @Setter private POSOrderId localId;
	@Nullable @Getter @Setter private String documentNo;

	@NonNull @Getter private POSOrderStatus status;
	@NonNull @Getter private final DocTypeId salesOrderDocTypeId;
	@NonNull @Getter private final PricingSystemAndListId pricingSystemAndListId;
	@NonNull @Getter private final BankAccountId cashbookId;
	@NonNull @Getter private final UserId cashierId;
	@NonNull @Getter private final Instant date;
	@NonNull @Getter private final BPartnerLocationAndCaptureId shipToCustomerAndLocationId;
	@NonNull @Getter private final POSShipFrom shipFrom;

	@Getter private final boolean isTaxIncluded;
	@NonNull @Getter private final CurrencyId currencyId;
	@NonNull @Getter Money totalAmt;
	@NonNull @Getter Money taxAmt;
	@NonNull @Getter Money paidAmt;
	@NonNull @Getter Money openAmt;

	@NonNull private final ArrayList<POSOrderLine> lines;
	@NonNull private final ArrayList<POSPayment> payments;

	@NonNull @Getter private final POSTerminalId posTerminalId;

	@Nullable @Getter @Setter private OrderId salesOrderId;

	@Builder
	private POSOrder(
			@NonNull final POSOrderExternalId externalId,
			@Nullable final POSOrderId localId, 
			@Nullable final String documentNo,
			@Nullable final POSOrderStatus status,
			@NonNull final DocTypeId salesOrderDocTypeId,
			@NonNull final PricingSystemAndListId pricingSystemAndListId,
			@NonNull final BankAccountId cashbookId,
			@NonNull final UserId cashierId,
			@NonNull final Instant date,
			@NonNull final BPartnerLocationAndCaptureId shipToCustomerAndLocationId,
			@NonNull final POSShipFrom shipFrom,
			final boolean isTaxIncluded,
			@NonNull final CurrencyId currencyId,
			@Nullable final List<POSOrderLine> lines,
			@Nullable final List<POSPayment> payments,
			@NonNull final POSTerminalId posTerminalId,
			@Nullable final OrderId salesOrderId)
	{
		this.externalId = externalId;
		this.localId = localId;
		this.documentNo = documentNo;
		this.status = status != null ? status : POSOrderStatus.Drafted;
		this.salesOrderDocTypeId = salesOrderDocTypeId;
		this.pricingSystemAndListId = pricingSystemAndListId;
		this.cashbookId = cashbookId;
		this.cashierId = cashierId;
		this.date = date;
		this.shipToCustomerAndLocationId = shipToCustomerAndLocationId;
		this.shipFrom = shipFrom;
		this.isTaxIncluded = isTaxIncluded;
		this.currencyId = currencyId;
		this.lines = lines != null ? new ArrayList<>(lines) : new ArrayList<>();
		this.payments = payments != null ? new ArrayList<>(payments) : new ArrayList<>();
		this.posTerminalId = posTerminalId;
		this.salesOrderId = salesOrderId;

		final Money zero = Money.zero(currencyId);
		this.totalAmt = zero;
		this.taxAmt = zero;
		this.paidAmt = zero;
		this.openAmt = zero;
		updateTotals();
	}

	public POSOrderId getLocalIdNotNull() {return Check.assumeNotNull(this.getLocalId(), "Expected POSOrder to be saved: {}", this);}

	public ClientAndOrgId getClientAndOrgId() {return getShipFrom().getClientAndOrgId();}

	public OrgId getOrgId() {return getShipFrom().getOrgId();}

	public BPartnerId getShipToCustomerId() {return getShipToCustomerAndLocationId().getBpartnerId();}

	public PricingSystemId getPricingSystemId() {return getPricingSystemAndListId().getPricingSystemId();}

	private void updateTotals()
	{
		final Money zero = Money.zero(currencyId);

		Money orderTotalAmt = zero;
		Money orderTaxAmt = zero;
		for (final POSOrderLine line : lines)
		{
			orderTotalAmt = orderTotalAmt.add(line.getLineTotalAmt(isTaxIncluded));
			orderTaxAmt = orderTaxAmt.add(line.getTaxAmt());
		}
		this.totalAmt = orderTotalAmt;
		this.taxAmt = orderTaxAmt;

		//
		// Payments
		this.paidAmt = streamPaymentsNotDeleted().map(POSPayment::getAmount).reduce(zero, Money::add);
		this.openAmt = this.totalAmt.subtract(this.paidAmt);
	}

	public ImmutableList<POSOrderLine> getLines() {return ImmutableList.copyOf(lines);}

	public ImmutableList<POSPayment> getAllPayments() {return ImmutableList.copyOf(payments);}

	public ImmutableList<POSPayment> getPaymentsNotDeleted() {return streamPaymentsNotDeleted().collect(ImmutableList.toImmutableList());}

	public Stream<POSPayment> streamPaymentsNotDeleted() {return payments.stream().filter(payment -> !payment.isDeleted());}

	public BooleanWithReason checkUserCanAddPayment()
	{
		if (getOpenAmt().signum() <= 0)
		{
			return BooleanWithReason.falseBecause("Already completely paid");
		}
		return BooleanWithReason.TRUE;
	}

	public void changeStatusTo(
			@NonNull final POSOrderStatus targetStatus,
			@NonNull final POSOrderProcessingServices services)
	{
		if (POSOrderStatus.equals(this.status, targetStatus))
		{
			return;
		}

		POSOrderStatus newStatus;
		switch (targetStatus)
		{
			case Drafted:
			{
				changeStatusTo_Drafted();
				newStatus = POSOrderStatus.Drafted;
				break;
			}
			case Voided:
			{
				changeStatusTo_Void();
				newStatus = POSOrderStatus.Voided;
				break;
			}
			case WaitingPayment:
			{
				changeStatusTo_WaitingPayment();
				newStatus = POSOrderStatus.WaitingPayment;
				break;
			}
			case Completed:
			{
				newStatus = changeStatusTo_Complete(services);
				break;
			}
			default:
			{
				throw new AdempiereException("Unknown next status " + targetStatus);
			}
		}

		this.status = newStatus;
	}

	private BooleanWithReason checkCanTransitionTo(@NonNull final POSOrderStatus targetStatus)
	{
		return this.status.checkCanTransitionTo(targetStatus);
	}

	public BooleanWithReason checkCanTransitionToDrafted()
	{
		return checkCanTransitionTo(POSOrderStatus.Drafted);
	}

	private void changeStatusTo_Drafted()
	{
		checkCanTransitionToDrafted().assertTrue();
	}

	public BooleanWithReason checkCanTransitionToWaitingPayment()
	{
		final BooleanWithReason canTransition = checkCanTransitionTo(POSOrderStatus.WaitingPayment);
		if (canTransition.isFalse())
		{
			return canTransition;
		}

		if (lines.isEmpty())
		{
			return BooleanWithReason.falseBecause(AdempiereException.MSG_NoLines);
		}

		return BooleanWithReason.TRUE;
	}

	private void changeStatusTo_WaitingPayment()
	{
		checkCanTransitionToWaitingPayment().assertTrue();
	}

	private void changeStatusTo_Void()
	{
		checkCanTransitionToVoided().assertTrue();
	}

	public BooleanWithReason checkCanTransitionToVoided()
	{
		final BooleanWithReason canVoid = this.status.checkCanTransitionTo(POSOrderStatus.Voided);
		if (canVoid.isFalse())
		{
			return canVoid;
		}

		if (salesOrderId != null)
		{
			return BooleanWithReason.falseBecause("Voiding POS orders with generated sales orders is not allowed");
		}

		final BooleanWithReason allowVoidingPayments = streamPaymentsNotDeleted()
				.map(POSPayment::checkAllowVoid)
				.filter(BooleanWithReason::isFalse)
				.findFirst()
				.orElse(BooleanWithReason.TRUE);
		if (allowVoidingPayments.isFalse())
		{
			return allowVoidingPayments;
		}

		return BooleanWithReason.TRUE;
	}

	public BooleanWithReason checkCanTryTransitionToCompleted()
	{
		final BooleanWithReason canComplete = this.status.checkCanTransitionTo(POSOrderStatus.Completed);
		if (canComplete.isFalse())
		{
			return canComplete;
		}

		if (lines.isEmpty())
		{
			return BooleanWithReason.falseBecause(AdempiereException.MSG_NoLines);
		}
		if (!isPaid())
		{
			return BooleanWithReason.falseBecause("not completely paid");
		}

		return BooleanWithReason.TRUE;
	}

	private POSOrderStatus changeStatusTo_Complete(@NonNull final POSOrderProcessingServices services)
	{
		checkCanTryTransitionToCompleted().assertTrue();

		services.processPOSPayments(this);
		if (!isPaymentsProcessedSuccessfully())
		{
			return POSOrderStatus.WaitingPayment;
		}

		services.scheduleCreateSalesOrderInvoiceAndShipment(getLocalIdNotNull(), getCashierId());

		return POSOrderStatus.Completed;
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	private boolean isPaid() {return openAmt.signum() == 0;}

	public void assertWaitingForPayment()
	{
		assertStatus(POSOrderStatus.WaitingPayment);
	}

	public void assertCompleted()
	{
		assertStatus(POSOrderStatus.Completed);
	}

	public void assertStatus(@NonNull final POSOrderStatus expectedStatus)
	{
		if (!POSOrderStatus.equals(this.status, expectedStatus))
		{
			throw new AdempiereException("Expected order status to be " + expectedStatus + " but it is " + status);
		}
	}

	public boolean isPaymentsProcessedSuccessfully()
	{
		return streamPaymentsNotDeleted().allMatch(POSPayment::isSuccessful);
	}

	public void createOrUpdateLine(@NonNull final String externalId, @NonNull final UnaryOperator<POSOrderLine> updater)
	{
		final int lineIdx = getLineIndexByExternalId(externalId);
		final POSOrderLine line = lineIdx >= 0 ? lines.get(lineIdx) : null;
		final POSOrderLine lineChanged = updater.apply(line);

		if (lineIdx >= 0)
		{
			lines.set(lineIdx, lineChanged);
		}
		else
		{
			lines.add(lineChanged);
		}

		updateTotals();
	}

	private int getLineIndexByExternalId(@NonNull final String externalId)
	{
		for (int i = 0; i < lines.size(); i++)
		{
			if (lines.get(i).getExternalId().equals(externalId))
			{
				return i;
			}
		}
		return -1;
	}

	public void preserveOnlyLineExternalIdsInOrder(final List<String> lineExternalIdsInOrder)
	{
		final HashMap<String, POSOrderLine> linesByExternalId = lines.stream().collect(GuavaCollectors.toHashMapByKey(POSOrderLine::getExternalId));
		lines.clear();

		for (final String lineExternalId : lineExternalIdsInOrder)
		{
			final POSOrderLine line = linesByExternalId.remove(lineExternalId);
			if (line != null)
			{
				lines.add(line);
			}
		}

		updateTotals();
	}

	public void updateAllPayments(@NonNull final UnaryOperator<POSPayment> updater)
	{
		for (final ListIterator<POSPayment> it = payments.listIterator(); it.hasNext(); )
		{
			final POSPayment payment = it.next();
			final POSPayment paymentChanged = updater.apply(payment);
			if (paymentChanged == null)
			{
				payment.assertAllowDeleteFromDB();
				it.remove();
			}
			else
			{
				it.set(paymentChanged);
			}
		}

		updateTotals();
	}

	public void createOrUpdatePayment(@NonNull final POSPaymentExternalId externalId, @NonNull final UnaryOperator<POSPayment> updater)
	{
		final int paymentIdx = getPaymentIndexByExternalId(externalId).orElse(-1);
		updatePaymentByIndex(paymentIdx, updater);
	}

	public void updatePaymentByExternalId(@NonNull final POSPaymentExternalId externalId, @NonNull final UnaryOperator<POSPayment> updater)
	{
		final int paymentIdx = getPaymentIndexByExternalId(externalId)
				.orElseThrow(() -> new AdempiereException("No payment found for " + externalId + " in " + payments));

		updatePaymentByIndex(paymentIdx, updater);
	}

	public void updatePaymentById(@NonNull POSPaymentId posPaymentId, @NonNull final UnaryOperator<POSPayment> updater)
	{
		final int paymentIdx = getPaymentIndexById(posPaymentId);
		updatePaymentByIndex(paymentIdx, updater);
	}

	private void updatePaymentByIndex(final int paymentIndex, @NonNull final UnaryOperator<POSPayment> updater)
	{
		final POSPayment payment = paymentIndex >= 0 ? payments.get(paymentIndex) : null;
		final POSPayment paymentChanged = updater.apply(payment);

		if (paymentIndex >= 0)
		{
			payments.set(paymentIndex, paymentChanged);
		}
		else
		{
			payments.add(paymentChanged);
		}

		updateTotals();
	}

	private int getPaymentIndexById(final @NonNull POSPaymentId posPaymentId)
	{
		for (int i = 0; i < payments.size(); i++)
		{
			if (POSPaymentId.equals(payments.get(i).getLocalId(), posPaymentId))
			{
				return i;
			}
		}

		throw new AdempiereException("No payment found for " + posPaymentId + " in " + payments);
	}

	private OptionalInt getPaymentIndexByExternalId(final @NonNull POSPaymentExternalId externalId)
	{
		for (int i = 0; i < payments.size(); i++)
		{
			if (POSPaymentExternalId.equals(payments.get(i).getExternalId(), externalId))
			{
				return OptionalInt.of(i);
			}
		}
		return OptionalInt.empty();
	}

	public void removePaymentsIf(@NonNull final Predicate<POSPayment> predicate)
	{
		updateAllPayments(payment -> {
			// skip payments marked as DELETED
			if (payment.isDeleted())
			{
				return payment;
			}

			if (!predicate.test(payment))
			{
				return payment;
			}

			if (payment.isAllowDeleteFromDB())
			{
				payment.assertAllowDelete();
				return null;
			}
			else
			{
				return payment.changingStatusToDeleted();
			}
		});
	}
}
