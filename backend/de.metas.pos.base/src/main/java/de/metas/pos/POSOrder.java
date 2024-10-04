package de.metas.pos;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.document.DocTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.UnaryOperator;

@EqualsAndHashCode
@ToString
public class POSOrder
{
	@NonNull @Getter private final POSOrderExternalId externalId;
	@Nullable @Getter @Setter private POSOrderId localId;

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
		Money paidAmt = zero;
		for (final POSPayment payment : payments)
		{
			paidAmt = paidAmt.add(payment.getAmount());
		}
		this.paidAmt = paidAmt;
		this.openAmt = this.totalAmt.subtract(this.paidAmt);
	}

	public ImmutableList<POSOrderLine> getLines() {return ImmutableList.copyOf(lines);}

	public ImmutableList<POSPayment> getPayments() {return ImmutableList.copyOf(payments);}

	public void changeStatusTo(
			@NonNull final POSOrderStatus targetStatus,
			@NonNull final POSOrderProcessingServices services)
	{
		if (POSOrderStatus.equals(this.status, targetStatus))
		{
			return;
		}

		this.status.assertCanTransitionTo(targetStatus);

		POSOrderStatus newStatus;
		switch (targetStatus)
		{
			case Drafted:
			case Voided:
			case WaitingPayment:
				newStatus = targetStatus;
				break;
			case Completed:
				newStatus = changeStatusTo_Complete(services);
				break;
			default:
				throw new AdempiereException("Unknown next status " + targetStatus);
		}

		this.status = newStatus;
	}

	private POSOrderStatus changeStatusTo_Complete(@NonNull final POSOrderProcessingServices services)
	{
		// TODO implement:
		// * create payments & process synchronous (to make sure we got the money from card)
		// * async create sales order, invoice, shipment and allocate the payments to that invoice 
		// throw new UnsupportedOperationException("not implemented");
		if (lines.isEmpty())
		{
			throw AdempiereException.noLines();

		}

		assertPaid();
		services.processPOSPayments(this);
		if (!isPaymentsProcessedSuccessfully())
		{
			return POSOrderStatus.WaitingPayment;
		}

		services.scheduleCreateSalesOrderInvoiceAndShipment(getLocalIdNotNull());
		return POSOrderStatus.Completed;
	}

	public void assertPaid()
	{
		if (openAmt.signum() != 0)
		{
			throw new AdempiereException("POS Order shall be paid");
		}
	}

	public boolean isPaymentsProcessedSuccessfully()
	{
		return payments.stream().allMatch(payment -> payment.getPaymentProcessingStatus().isSuccessful());
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
				it.remove();
			}
			else
			{
				it.set(paymentChanged);
			}
		}
	}

	public void createOrUpdatePayment(@NonNull final String externalId, @NonNull final UnaryOperator<POSPayment> updater)
	{
		final int paymentIdx = getPaymentIndexByExternalId(externalId);
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

	private int getPaymentIndexByExternalId(final @NonNull String externalId)
	{
		for (int i = 0; i < payments.size(); i++)
		{
			if (payments.get(i).getExternalId().equals(externalId))
			{
				return i;
			}
		}
		return -1;
	}

	public void preserveOnlyPaymentExternalIds(@NonNull final Collection<String> paymentExternalIdsToKeep)
	{
		final HashMap<String, POSPayment> paymentsByExternalId = payments.stream().collect(GuavaCollectors.toHashMapByKey(POSPayment::getExternalId));
		payments.clear();

		for (final String paymentExternalId : paymentExternalIdsToKeep)
		{
			final POSPayment payment = paymentsByExternalId.remove(paymentExternalId);
			if (payment != null)
			{
				payments.add(payment);
			}
		}

		updateTotals();
	}

	public Set<PaymentId> getPaymentReceiptIds()
	{
		return payments.stream()
				.map(POSPayment::getPaymentReceiptId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}
}
