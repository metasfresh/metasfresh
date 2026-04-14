package de.metas.order.paymentschedule;

import com.google.common.collect.ImmutableList;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermBreak;
import de.metas.payment.paymentterm.PaymentTermBreakId;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;

@EqualsAndHashCode
@ToString
public class OrderPaySchedule
{
	@NonNull @Getter OrderId orderId;
	@NonNull @Getter private final ImmutableList<OrderPayScheduleLine> lines;

	private OrderPaySchedule(@NonNull final OrderId orderId, @NonNull final List<OrderPayScheduleLine> lines)
	{
		this.orderId = orderId;
		this.lines = ImmutableList.copyOf(lines);
	}

	public static OrderPaySchedule ofList(@NonNull final OrderId orderId, @NonNull final List<OrderPayScheduleLine> lines)
	{
		return new OrderPaySchedule(orderId, lines);
	}

	public static Collector<OrderPayScheduleLine, ?, Optional<OrderPaySchedule>> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator((lines) -> {
			if (lines.isEmpty())
			{
				return Optional.empty();
			}
			final OrderId orderId = lines.get(0).getOrderId();
			return Optional.of(new OrderPaySchedule(orderId, lines));
		});
	}

	public OrderPayScheduleLine getLineByPaymentTermBreakId(@NonNull final PaymentTermBreakId paymentTermBreakId)
	{
		return lines.stream()
				.filter(line -> PaymentTermBreakId.equals(line.getPaymentTermBreakId(), paymentTermBreakId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No line found for " + paymentTermBreakId));
	}

	public OrderPayScheduleLine getLineById(@NonNull final OrderPayScheduleId payScheduleLineId)
	{
		return lines.stream()
				.filter(line -> line.getId().equals(payScheduleLineId))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("OrderPayScheduleLine not found for ID: " + payScheduleLineId));
	}

	public void updateStatusFromContext(final OrderSchedulingContext context)
	{
		updateStatusFromContext(context, false);
	}

	public void updateStatusFromContext(final OrderSchedulingContext context, final boolean updateAmounts)
	{
		final PaymentTerm paymentTerm = context.getPaymentTerm();

		if (updateAmounts)
		{
			// Recalculate amounts similar to OrderPayScheduleCreateCommand logic
			recalculateAmounts(context, paymentTerm);
		}

		for (final OrderPayScheduleLine line : lines)
		{
			if (line.getStatus().isPending())
			{
				final PaymentTermBreak termBreak = paymentTerm.getBreakById(line.getPaymentTermBreakId());
				final DueDateAndStatus dueDateAndStatus = context.computeDueDate(termBreak);
				line.applyAndProcess(dueDateAndStatus);
			}
		}
	}

	private void recalculateAmounts(final OrderSchedulingContext context, final PaymentTerm paymentTerm)
	{
		// Determine which base amount to use for ALL schedules
		// If payment term has LC break AND Proforma is allocated, use Proforma amount for everything
		// Otherwise use order grandTotal
		final Money baseAmount = getBaseAmountForAllSchedules(context, paymentTerm);

		Money totalScheduledAmount = Money.zero(baseAmount.getCurrencyId());

		// Recalculate all lines except the last
		for (int i = 0; i < lines.size() - 1; i++)
		{
			final OrderPayScheduleLine line = lines.get(i);
			final PaymentTermBreak termBreak = paymentTerm.getBreakById(line.getPaymentTermBreakId());

			final Money newDueAmount = baseAmount.multiply(termBreak.getPercent(), context.getPrecision());
			line.setDueAmount(newDueAmount);

			totalScheduledAmount = totalScheduledAmount.add(newDueAmount);
		}

		// Recalculate the last line as a remainder
		if (!lines.isEmpty())
		{
			final OrderPayScheduleLine lastLine = lines.get(lines.size() - 1);
			final Money lastLineDueAmount = baseAmount.subtract(totalScheduledAmount);
			lastLine.setDueAmount(lastLineDueAmount);
		}
	}

	private static Money getBaseAmountForAllSchedules(
			@NonNull final OrderSchedulingContext context,
			@NonNull final PaymentTerm paymentTerm)
	{
		// Check if the payment term has any LC break
		final boolean hasLCBreak = paymentTerm.getSortedBreaks()
				.stream()
				.anyMatch(PaymentTermBreak::isLetterOfCredit);

		// If it has LC break and Proforma is allocated, use Proforma amount for everything
		if (hasLCBreak && context.getProformaAmount() != null)
		{
			return context.getProformaAmount();
		}

		// Otherwise use order grandTotal
		return context.getGrandTotal();
	}

	public void markAsPaid(final OrderPayScheduleId orderPayScheduleId)
	{
		final OrderPayScheduleLine line = getLineById(orderPayScheduleId);

		final DueDateAndStatus dueDateAndStatus = DueDateAndStatus.paid(line.getDueDate());
		line.applyAndProcess(dueDateAndStatus);
	}
}
