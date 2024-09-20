package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.document.DocTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemAndListId;
import de.metas.pricing.PricingSystemId;
import de.metas.user.UserId;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
	@NonNull @Getter BigDecimal totalAmt;
	@NonNull @Getter BigDecimal taxAmt;
	@NonNull @Getter BigDecimal paidAmt;
	@NonNull @Getter BigDecimal openAmt;

	@NonNull private final ArrayList<POSOrderLine> lines;
	@NonNull private final ArrayList<POSPayment> payments;

	@NonNull @Getter private final POSConfigId configId;

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
			@Nullable final List<POSPayment> payments, final @NonNull POSConfigId configId)
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
		this.configId = configId;

		this.totalAmt = BigDecimal.ZERO;
		this.taxAmt = BigDecimal.ZERO;
		this.paidAmt = BigDecimal.ZERO;
		this.openAmt = BigDecimal.ZERO;
		updateTotals();
	}

	public OrgId getOrgId() {return getShipFrom().getOrgId();}

	public BPartnerId getShipToCustomerId() {return getShipToCustomerAndLocationId().getBpartnerId();}

	public PricingSystemId getPricingSystemId() {return getPricingSystemAndListId().getPricingSystemId();}

	private void updateTotals()
	{
		BigDecimal orderTotalAmt = BigDecimal.ZERO;
		BigDecimal orderTaxAmt = BigDecimal.ZERO;
		for (final POSOrderLine line : lines)
		{
			orderTotalAmt = orderTotalAmt.add(line.getLineTotalAmt(isTaxIncluded));
			orderTaxAmt = orderTaxAmt.add(line.getTaxAmt());
		}
		this.totalAmt = orderTotalAmt;
		this.taxAmt = orderTaxAmt;

		//
		// Payments
		BigDecimal paidAmt = BigDecimal.ZERO;
		for (final POSPayment payment : payments)
		{
			paidAmt = paidAmt.add(payment.getAmount());
		}
		this.paidAmt = paidAmt;
		this.openAmt = this.totalAmt.subtract(paidAmt);
	}

	public ImmutableList<POSOrderLine> getLines() {return ImmutableList.copyOf(lines);}

	public ImmutableList<POSPayment> getPayments() {return ImmutableList.copyOf(payments);}

	public void changeStatusTo(
			@NonNull final POSOrderStatus nextStatus,
			@NonNull final POSOrderProcessingServices services)
	{
		if (POSOrderStatus.equals(this.status, nextStatus))
		{
			return;
		}

		this.status.assertCanTransitionTo(nextStatus);

		switch (nextStatus)
		{
			case Drafted:
				break;
			case WaitingPayment:
				break;
			case Completed:
				changeStatusTo_Complete(services);
				break;
			case Voided:
				break;
			default:
				throw new AdempiereException("Unknown next status " + nextStatus);
		}

		this.status = nextStatus;
	}

	private void changeStatusTo_Complete(@NonNull final POSOrderProcessingServices services)
	{
		// TODO implement:
		// * create payments & process synchronous (to make sure we got the money from card)
		// * async create sales order, invoice, shipment and allocate the payments to that invoice 
		// throw new UnsupportedOperationException("not implemented");

		assertPaid();
		services.createPayments(this);
		services.scheduleCreateSalesOrderInvoiceAndShipment(this);
	}

	public void assertPaid()
	{
		if (openAmt.signum() != 0)
		{
			throw new AdempiereException("POS Order shall be paid");
		}
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

	public void createOrUpdatePayment(@NonNull final String externalId, @NonNull final UnaryOperator<POSPayment> updater)
	{
		final int paymentIdx = getPaymentIndexByExternalId(externalId);
		final POSPayment payment = paymentIdx >= 0 ? payments.get(paymentIdx) : null;
		final POSPayment paymentChanged = updater.apply(payment);

		if (paymentIdx >= 0)
		{
			payments.set(paymentIdx, paymentChanged);
		}
		else
		{
			payments.add(paymentChanged);
		}

		updateTotals();
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
}
