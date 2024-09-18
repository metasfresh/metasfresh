package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.money.CurrencyId;
import de.metas.user.UserId;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.UnaryOperator;

@EqualsAndHashCode
@ToString
public class POSOrder
{
	@NonNull @Getter private final POSOrderExternalId externalId;
	@Getter private final int localId;

	@NonNull @Getter POSOrderStatus status;
	@NonNull @Getter private final UserId cashierId;
	@NonNull @Getter private final Instant date;
	@NonNull @Getter private final BPartnerLocationAndCaptureId shipToCustomerAndLocationId;
	@NonNull @Getter private final POSShipFrom shipFrom;

	@Getter private final boolean isTaxIncluded;
	@NonNull @Getter private final CurrencyId currencyId;
	@NonNull @Getter BigDecimal totalAmt;
	@NonNull @Getter BigDecimal taxAmt;

	@NonNull private final ArrayList<POSOrderLine> lines;

	@Builder
	private POSOrder(
			@NonNull final POSOrderExternalId externalId,
			final int localId,
			@Nullable final POSOrderStatus status,
			@NonNull final UserId cashierId,
			@NonNull final Instant date,
			@NonNull final BPartnerLocationAndCaptureId shipToCustomerAndLocationId,
			@NonNull final POSShipFrom shipFrom,
			final boolean isTaxIncluded,
			@NonNull final CurrencyId currencyId,
			@Nullable final List<POSOrderLine> lines)
	{
		this.externalId = externalId;
		this.localId = localId;
		this.status = status != null ? status : POSOrderStatus.Drafted;
		this.cashierId = cashierId;
		this.date = date;
		this.shipToCustomerAndLocationId = shipToCustomerAndLocationId;
		this.shipFrom = shipFrom;
		this.isTaxIncluded = isTaxIncluded;
		this.currencyId = currencyId;
		this.lines = lines != null ? new ArrayList<>(lines) : new ArrayList<>();

		this.totalAmt = BigDecimal.ZERO;
		this.taxAmt = BigDecimal.ZERO;
		updateTotals();
	}

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
	}

	public ImmutableList<POSOrderLine> getLines() {return ImmutableList.copyOf(lines);}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isDrafted() {return status.isDrafted();}

	public void changeStatusTo(POSOrderStatus nextStatus)
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
				throw new UnsupportedOperationException("not implemented");
				//break;
			case Voided:
				break;
			default:
				throw new AdempiereException("Unknown next status " + nextStatus);
		}

		this.status = nextStatus;
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
}
